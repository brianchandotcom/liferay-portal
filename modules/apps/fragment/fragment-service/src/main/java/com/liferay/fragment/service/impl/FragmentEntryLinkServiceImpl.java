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

package com.liferay.fragment.service.impl;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.base.FragmentEntryLinkServiceBaseImpl;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionCheckerUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;

import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	property = {
		"json.web.service.context.name=fragment",
		"json.web.service.context.path=FragmentEntryLink"
	},
	service = AopService.class
)
public class FragmentEntryLinkServiceImpl
	extends FragmentEntryLinkServiceBaseImpl {

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #addFragmentEntryLink(long, long, long, long, long, String,
	 *             String, String, String, String, String, int, String,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public FragmentEntryLink addFragmentEntryLink(
			long groupId, long originalFragmentEntryLinkId,
			long fragmentEntryId, long segmentsExperienceId, long classNameId,
			long classPK, String css, String html, String js,
			String configuration, String editableValues, String namespace,
			int position, String rendererKey, ServiceContext serviceContext)
		throws PortalException {

		return addFragmentEntryLink(
			groupId, originalFragmentEntryLinkId, fragmentEntryId,
			segmentsExperienceId, classPK, css, html, js, configuration,
			editableValues, namespace, position, rendererKey, serviceContext);
	}

	@Override
	public FragmentEntryLink addFragmentEntryLink(
			long groupId, long originalFragmentEntryLinkId,
			long fragmentEntryId, long segmentsExperienceId, long plid,
			String css, String html, String js, String configuration,
			String editableValues, String namespace, int position,
			String rendererKey, ServiceContext serviceContext)
		throws PortalException {

		_checkPermission(groupId, plid, false);

		return fragmentEntryLinkLocalService.addFragmentEntryLink(
			getUserId(), groupId, originalFragmentEntryLinkId, fragmentEntryId,
			segmentsExperienceId, plid, css, html, js, configuration,
			editableValues, namespace, position, rendererKey, serviceContext);
	}

	@Override
	public FragmentEntryLink deleteFragmentEntryLink(long fragmentEntryLinkId)
		throws PortalException {

		FragmentEntryLink fragmentEntryLink =
			fragmentEntryLinkPersistence.findByPrimaryKey(fragmentEntryLinkId);

		_checkPermission(
			fragmentEntryLink.getGroupId(), fragmentEntryLink.getPlid(), false);

		return fragmentEntryLinkLocalService.deleteFragmentEntryLink(
			fragmentEntryLinkId);
	}

	@Override
	public FragmentEntryLink updateFragmentEntryLink(
			long fragmentEntryLinkId, String editableValues)
		throws PortalException {

		return updateFragmentEntryLink(
			fragmentEntryLinkId, editableValues, true);
	}

	@Override
	public FragmentEntryLink updateFragmentEntryLink(
			long fragmentEntryLinkId, String editableValues,
			boolean updateClassedModel)
		throws PortalException {

		FragmentEntryLink fragmentEntryLink =
			fragmentEntryLinkPersistence.findByPrimaryKey(fragmentEntryLinkId);

		_checkPermission(
			fragmentEntryLink.getGroupId(), fragmentEntryLink.getPlid(), true);

		return fragmentEntryLinkLocalService.updateFragmentEntryLink(
			fragmentEntryLinkId, editableValues, updateClassedModel);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #updateFragmentEntryLinks(long, long, long[], String,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public void updateFragmentEntryLinks(
			long groupId, long classNameId, long classPK,
			long[] fragmentEntryIds, String editableValues,
			ServiceContext serviceContext)
		throws PortalException {

		updateFragmentEntryLinks(
			groupId, classPK, fragmentEntryIds, editableValues, serviceContext);
	}

	@Override
	public void updateFragmentEntryLinks(
			long groupId, long plid, long[] fragmentEntryIds,
			String editableValues, ServiceContext serviceContext)
		throws PortalException {

		_checkPermission(groupId, plid, true);

		fragmentEntryLinkLocalService.updateFragmentEntryLinks(
			getUserId(), groupId, plid, fragmentEntryIds, editableValues,
			serviceContext);
	}

	@Override
	public void updateFragmentEntryLinks(
			Map<Long, String> fragmentEntryLinksEditableValuesMap)
		throws PortalException {

		for (Map.Entry<Long, String> entry :
				fragmentEntryLinksEditableValuesMap.entrySet()) {

			FragmentEntryLink fragmentEntryLink =
				fragmentEntryLinkPersistence.findByPrimaryKey(entry.getKey());

			_checkPermission(
				fragmentEntryLink.getGroupId(), fragmentEntryLink.getPlid(),
				true);
		}

		fragmentEntryLinkLocalService.updateFragmentEntryLinks(
			fragmentEntryLinksEditableValuesMap);
	}

	private void _checkPermission(
			long groupId, long plid, boolean checkUpdateLayoutContentPermission)
		throws PortalException {

		String className = Layout.class.getName();
		long classPK = plid;

		long layoutPageTemplateEntryPlid = plid;

		Layout layout = _layoutLocalService.fetchLayout(plid);

		if (layout.isDraftLayout()) {
			layoutPageTemplateEntryPlid = layout.getClassPK();
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.
				fetchLayoutPageTemplateEntryByPlid(layoutPageTemplateEntryPlid);

		if (layoutPageTemplateEntry != null) {
			className = LayoutPageTemplateEntry.class.getName();
			classPK = layoutPageTemplateEntry.getLayoutPageTemplateEntryId();
		}

		Boolean containsPermission = Boolean.valueOf(
			BaseModelPermissionCheckerUtil.containsBaseModelPermission(
				getPermissionChecker(), groupId, className, classPK,
				ActionKeys.UPDATE));

		if (checkUpdateLayoutContentPermission &&
			Objects.equals(className, Layout.class.getName())) {

			containsPermission =
				containsPermission ||
				LayoutPermissionUtil.contains(
					getPermissionChecker(), classPK,
					ActionKeys.UPDATE_LAYOUT_CONTENT);
		}

		if ((containsPermission == null) || !containsPermission) {
			throw new PrincipalException.MustHavePermission(
				getUserId(), className, classPK, ActionKeys.UPDATE);
		}
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

}