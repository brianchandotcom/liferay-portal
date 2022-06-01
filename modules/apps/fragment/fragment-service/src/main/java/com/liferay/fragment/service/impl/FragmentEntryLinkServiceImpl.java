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
import com.liferay.portal.kernel.service.permission.LayoutPermission;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

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

	@Override
	public FragmentEntryLink addFragmentEntryLink(
			long groupId, long originalFragmentEntryLinkId,
			long fragmentEntryId, long segmentsExperienceId, long plid,
			String css, String html, String js, String configuration,
			String editableValues, String namespace, int position,
			String rendererKey, ServiceContext serviceContext)
		throws PortalException {

		boolean allowUpdateLayoutRestrictedPermission = GetterUtil.getBoolean(
			PropsUtil.get("feature.flag.LPS-132571"));

		_checkPermission(
			groupId, plid, false, allowUpdateLayoutRestrictedPermission);

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
			fragmentEntryLink.getGroupId(), fragmentEntryLink.getPlid(), false,
			false);

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

		boolean allowUpdateLayoutRestrictedPermission = GetterUtil.getBoolean(
			PropsUtil.get("feature.flag.LPS-132571"));

		_checkPermission(
			fragmentEntryLink.getGroupId(), fragmentEntryLink.getPlid(), true,
			allowUpdateLayoutRestrictedPermission);

		return fragmentEntryLinkLocalService.updateFragmentEntryLink(
			fragmentEntryLinkId, editableValues, updateClassedModel);
	}

	private void _checkPermission(
			long groupId, long plid, boolean checkUpdateLayoutContentPermission,
			boolean checkUpdateLayoutRestrictedPermission)
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

		if (GetterUtil.getBoolean(
				BaseModelPermissionCheckerUtil.containsBaseModelPermission(
					getPermissionChecker(), groupId, className, classPK,
					ActionKeys.UPDATE))) {

			return;
		}

		if (!Objects.equals(className, Layout.class.getName()) ||
			(!checkUpdateLayoutContentPermission &&
			 !checkUpdateLayoutRestrictedPermission)) {

			throw new PrincipalException.MustHavePermission(
				getUserId(), className, classPK, ActionKeys.UPDATE);
		}

		if (_layoutPermission.contains(
				getPermissionChecker(), classPK, ActionKeys.UPDATE) ||
			(checkUpdateLayoutContentPermission &&
			 _layoutPermission.contains(
				 getPermissionChecker(), classPK,
				 ActionKeys.UPDATE_LAYOUT_CONTENT))) {

			return;
		}

		if (checkUpdateLayoutRestrictedPermission &&
			_layoutPermission.containsLayoutBasicUpdatePermission(
				getPermissionChecker(), classPK)) {

			return;
		}

		throw new PrincipalException.MustHavePermission(
			getUserId(), className, classPK, ActionKeys.UPDATE);
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutPermission _layoutPermission;

}