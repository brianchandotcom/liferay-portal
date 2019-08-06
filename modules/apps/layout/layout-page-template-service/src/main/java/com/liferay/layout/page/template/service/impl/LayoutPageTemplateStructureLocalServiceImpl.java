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
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelLocalService;
import com.liferay.layout.page.template.service.base.LayoutPageTemplateStructureLocalServiceBaseImpl;
import com.liferay.layout.page.template.util.LayoutPageTemplateStructureHelperUtil;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.constants.SegmentsExperienceConstants;

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
			long userId, long groupId, long classNameId, long classPK,
			String data, ServiceContext serviceContext)
		throws PortalException {

		// Layout page template structure

		User user = userLocalService.getUser(userId);

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
		layoutPageTemplateStructure.setClassNameId(classNameId);
		layoutPageTemplateStructure.setClassPK(classPK);

		layoutPageTemplateStructurePersistence.update(
			layoutPageTemplateStructure);

		int count =
			_fragmentEntryLinkLocalService.
				getClassedModelFragmentEntryLinksCount(
					groupId, classNameId, classPK);

		if (count > 0) {
			_fragmentEntryLinkLocalService.updateClassedModel(
				classNameId, classPK);
		}

		// Layout page template structure rel

		if (!ExportImportThreadLocal.isImportInProcess()) {
			_layoutPageTemplateStructureRelLocalService.
				addLayoutPageTemplateStructureRel(
					userId, groupId, layoutPageTemplateStructureId,
					SegmentsExperienceConstants.ID_DEFAULT, data,
					serviceContext);
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
			long groupId, long classNameId, long classPK)
		throws PortalException {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			layoutPageTemplateStructurePersistence.findByG_C_C(
				groupId, classNameId, classPK);

		layoutPageTemplateStructureLocalService.
			deleteLayoutPageTemplateStructure(layoutPageTemplateStructure);

		return layoutPageTemplateStructure;
	}

	@Override
	public LayoutPageTemplateStructure fetchLayoutPageTemplateStructure(
		long groupId, long classNameId, long classPK) {

		return layoutPageTemplateStructurePersistence.fetchByG_C_C(
			groupId, classNameId, classPK);
	}

	@Override
	public LayoutPageTemplateStructure fetchLayoutPageTemplateStructure(
			long groupId, long classNameId, long classPK,
			boolean rebuildStructure)
		throws PortalException {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			fetchLayoutPageTemplateStructure(groupId, classNameId, classPK);

		if ((layoutPageTemplateStructure != null) || !rebuildStructure) {
			return layoutPageTemplateStructure;
		}

		return rebuildLayoutPageTemplateStructure(
			groupId, classNameId, classPK);
	}

	@Override
	public LayoutPageTemplateStructure rebuildLayoutPageTemplateStructure(
			long groupId, long classNameId, long classPK)
		throws PortalException {

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinks(
				groupId, classNameId, classPK);

		JSONObject jsonObject =
			LayoutPageTemplateStructureHelperUtil.
				generateContentLayoutStructure(fragmentEntryLinks);

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			fetchLayoutPageTemplateStructure(groupId, classNameId, classPK);

		if (layoutPageTemplateStructure != null) {
			return updateLayoutPageTemplateStructure(
				groupId, classNameId, classPK, jsonObject.toString());
		}

		return addLayoutPageTemplateStructure(
			PrincipalThreadLocal.getUserId(), groupId, classNameId, classPK,
			jsonObject.toString(),
			ServiceContextThreadLocal.getServiceContext());
	}

	@Override
	public LayoutPageTemplateStructure updateLayoutPageTemplateStructure(
			long groupId, long classNameId, long classPK,
			long segmentsExperienceId, String data)
		throws PortalException {

		// Layout page template structure

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			layoutPageTemplateStructurePersistence.findByG_C_C(
				groupId, classNameId, classPK);

		layoutPageTemplateStructure.setModifiedDate(new Date());

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

		_updateClassedModel(classNameId, classPK);

		return layoutPageTemplateStructure;
	}

	@Override
	public LayoutPageTemplateStructure updateLayoutPageTemplateStructure(
			long groupId, long classNameId, long classPK, String data)
		throws PortalException {

		return layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructure(
				groupId, classNameId, classPK,
				SegmentsExperienceConstants.ID_DEFAULT, data);
	}

	private void _updateClassedModel(long classNameId, long classPK)
		throws PortalException {

		if (classNameId == _portal.getClassNameId(Layout.class)) {
			Layout layout = _layoutLocalService.getLayout(classPK);

			_layoutLocalService.updateLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(), layout.getTypeSettings());
		}
		else if (classNameId == _portal.getClassNameId(
					LayoutPageTemplateEntry.class)) {

			LayoutPageTemplateEntry layoutPageTemplateEntry =
				_layoutPageTemplateEntryLocalService.getLayoutPageTemplateEntry(
					classPK);

			layoutPageTemplateEntry.setModifiedDate(new Date());

			_layoutPageTemplateEntryLocalService.updateLayoutPageTemplateEntry(
				layoutPageTemplateEntry);
		}
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

}