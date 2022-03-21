/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.layout.page.template.service.impl;

import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelLocalService;
import com.liferay.layout.page.template.service.base.LayoutPageTemplateStructureLocalServiceBaseImpl;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = "model.class.name=com.liferay.layout.page.template.model.LayoutPageTemplateStructure",
	service = AopService.class
)
public class LayoutPageTemplateStructureLocalServiceImpl
	extends LayoutPageTemplateStructureLocalServiceBaseImpl {

	@Override
	public LayoutPageTemplateStructure addLayoutPageTemplateStructure(
			long userId, long groupId, long plid, long segmentsExperienceId,
			String data, ServiceContext serviceContext)
		throws PortalException {

		// Layout page template structure

		User user = _userLocalService.getUser(userId);

		long layoutPageTemplateStructureId = counterLocalService.increment();

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			layoutPageTemplateStructurePersistence.create(
				layoutPageTemplateStructureId);

		layoutPageTemplateStructure.setUuid(serviceContext.getUuid());
		layoutPageTemplateStructure.setGroupId(groupId);
		layoutPageTemplateStructure.setCompanyId(user.getCompanyId());
		layoutPageTemplateStructure.setUserId(user.getUserId());
		layoutPageTemplateStructure.setUserName(user.getFullName());
		layoutPageTemplateStructure.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		layoutPageTemplateStructure.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		layoutPageTemplateStructure.setClassNameId(
			_portal.getClassNameId(Layout.class));
		layoutPageTemplateStructure.setClassPK(plid);

		layoutPageTemplateStructure =
			layoutPageTemplateStructurePersistence.update(
				layoutPageTemplateStructure);

		int count =
			_fragmentEntryLinkLocalService.getFragmentEntryLinksCountByPlid(
				groupId, plid);

		if (count > 0) {
			_updateLayoutStatus(userId, plid);
		}

		// Layout page template structure rel

		if (!ExportImportThreadLocal.isImportInProcess()) {
			_layoutPageTemplateStructureRelLocalService.
				addLayoutPageTemplateStructureRel(
					userId, groupId, layoutPageTemplateStructureId,
					segmentsExperienceId, data, serviceContext);
		}

		return layoutPageTemplateStructure;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public LayoutPageTemplateStructure deleteLayoutPageTemplateStructure(
		LayoutPageTemplateStructure layoutPageTemplateStructure) {

		// Layout page template structure

		layoutPageTemplateStructurePersistence.remove(
			layoutPageTemplateStructure);

		// Layout page template structure rels

		List<LayoutPageTemplateStructureRel> layoutPageTemplateStructureRels =
			_layoutPageTemplateStructureRelLocalService.
				getLayoutPageTemplateStructureRels(
					layoutPageTemplateStructure.
						getLayoutPageTemplateStructureId());

		for (LayoutPageTemplateStructureRel layoutPageTemplateStructureRel :
				layoutPageTemplateStructureRels) {

			_layoutPageTemplateStructureRelLocalService.
				deleteLayoutPageTemplateStructureRel(
					layoutPageTemplateStructureRel);
		}

		return layoutPageTemplateStructure;
	}

	@Override
	public LayoutPageTemplateStructure deleteLayoutPageTemplateStructure(
			long groupId, long plid)
		throws PortalException {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			layoutPageTemplateStructurePersistence.findByG_C_C(
				groupId, _portal.getClassNameId(Layout.class), plid);

		layoutPageTemplateStructureLocalService.
			deleteLayoutPageTemplateStructure(layoutPageTemplateStructure);

		return layoutPageTemplateStructure;
	}

	@Override
	public LayoutPageTemplateStructure fetchLayoutPageTemplateStructure(
		long groupId, long plid) {

		return layoutPageTemplateStructurePersistence.fetchByG_C_C(
			groupId, _portal.getClassNameId(Layout.class), plid);
	}

	@Override
	public LayoutPageTemplateStructure fetchLayoutPageTemplateStructure(
			long groupId, long plid, boolean rebuildStructure)
		throws PortalException {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			fetchLayoutPageTemplateStructure(groupId, plid);

		if ((layoutPageTemplateStructure != null) || !rebuildStructure) {
			return layoutPageTemplateStructure;
		}

		return layoutPageTemplateStructureLocalService.
			rebuildLayoutPageTemplateStructure(groupId, plid);
	}

	@Override
	public LayoutPageTemplateStructure rebuildLayoutPageTemplateStructure(
			long groupId, long plid)
		throws PortalException {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			fetchLayoutPageTemplateStructure(groupId, plid);

		if (layoutPageTemplateStructure != null) {
			return updateLayoutPageTemplateStructureData(
				groupId, plid,
				_generateContentLayoutStructureData(groupId, plid));
		}

		return addLayoutPageTemplateStructure(
			PrincipalThreadLocal.getUserId(), groupId, plid,
			SegmentsExperienceConstants.ID_DEFAULT,
			_generateContentLayoutStructureData(groupId, plid),
			ServiceContextThreadLocal.getServiceContext());
	}

	@Override
	public LayoutPageTemplateStructure updateLayoutPageTemplateStructureData(
			long groupId, long plid, long segmentsExperienceId, String data)
		throws PortalException {

		// Layout page template structure

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			layoutPageTemplateStructurePersistence.findByG_C_C(
				groupId, _portal.getClassNameId(Layout.class), plid);

		layoutPageTemplateStructure.setModifiedDate(new Date());

		layoutPageTemplateStructure =
			layoutPageTemplateStructurePersistence.update(
				layoutPageTemplateStructure);

		// Layout page template structure rel

		LayoutPageTemplateStructureRel layoutPageTemplateStructureRel =
			_layoutPageTemplateStructureRelLocalService.
				fetchLayoutPageTemplateStructureRel(
					layoutPageTemplateStructure.
						getLayoutPageTemplateStructureId(),
					segmentsExperienceId);

		if (layoutPageTemplateStructureRel == null) {
			_layoutPageTemplateStructureRelLocalService.
				addLayoutPageTemplateStructureRel(
					PrincipalThreadLocal.getUserId(), groupId,
					layoutPageTemplateStructure.
						getLayoutPageTemplateStructureId(),
					segmentsExperienceId, data,
					ServiceContextThreadLocal.getServiceContext());
		}
		else {
			_layoutPageTemplateStructureRelLocalService.
				updateLayoutPageTemplateStructureRel(
					layoutPageTemplateStructure.
						getLayoutPageTemplateStructureId(),
					segmentsExperienceId, data);
		}

		_updateLayoutStatus(PrincipalThreadLocal.getUserId(), plid);

		return layoutPageTemplateStructure;
	}

	@Override
	public LayoutPageTemplateStructure updateLayoutPageTemplateStructureData(
			long groupId, long plid, String data)
		throws PortalException {

		long defaultSegmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				plid);

		return layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructureData(
				groupId, plid, defaultSegmentsExperienceId, data);
	}

	private String _generateContentLayoutStructureData(long groupId, long plid)
		throws PortalException {

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinksByPlid(
				groupId, plid);

		int type = _getLayoutPageTemplateEntryType(
			_layoutLocalService.getLayout(plid));

		if (fragmentEntryLinks.isEmpty() &&
			(type == LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT)) {

			LayoutStructure layoutStructure = new LayoutStructure();

			LayoutStructureItem rootLayoutStructureItem =
				layoutStructure.addRootLayoutStructureItem();

			layoutStructure.addDropZoneLayoutStructureItem(
				rootLayoutStructureItem.getItemId(), 0);

			return layoutStructure.toString();
		}

		if (fragmentEntryLinks.isEmpty()) {
			LayoutStructure layoutStructure = new LayoutStructure();

			layoutStructure.addRootLayoutStructureItem();

			return layoutStructure.toString();
		}

		LayoutStructure layoutStructure = new LayoutStructure();

		LayoutStructureItem rootLayoutStructureItem =
			layoutStructure.addRootLayoutStructureItem();

		LayoutStructureItem containerStyledLayoutStructureItem =
			layoutStructure.addContainerStyledLayoutStructureItem(
				rootLayoutStructureItem.getItemId(), 0);

		for (int i = 0; i < fragmentEntryLinks.size(); i++) {
			FragmentEntryLink fragmentEntryLink = fragmentEntryLinks.get(i);

			layoutStructure.addFragmentStyledLayoutStructureItem(
				fragmentEntryLink.getFragmentEntryLinkId(),
				containerStyledLayoutStructureItem.getItemId(), i);
		}

		return layoutStructure.toString();
	}

	private int _getLayoutPageTemplateEntryType(Layout layout) {
		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.
				fetchLayoutPageTemplateEntryByPlid(layout.getClassPK());

		if (layoutPageTemplateEntry != null) {
			return layoutPageTemplateEntry.getType();
		}

		Layout draftLayout = layout.fetchDraftLayout();

		if (draftLayout != null) {
			LayoutPageTemplateEntry draftLayoutPageTemplateEntry =
				_layoutPageTemplateEntryLocalService.
					fetchLayoutPageTemplateEntryByPlid(
						draftLayout.getClassPK());

			if (draftLayoutPageTemplateEntry != null) {
				return draftLayoutPageTemplateEntry.getType();
			}
		}

		return LayoutPageTemplateEntryTypeConstants.TYPE_BASIC;
	}

	private void _updateLayoutStatus(long userId, long plid)
		throws PortalException {

		_layoutLocalService.updateStatus(
			userId, plid, WorkflowConstants.STATUS_DRAFT,
			ServiceContextThreadLocal.getServiceContext());
	}

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutPageTemplateStructureRelLocalService
		_layoutPageTemplateStructureRelLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Reference
	private UserLocalService _userLocalService;

}