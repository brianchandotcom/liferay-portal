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

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.layout.page.template.constants.LayoutPageTemplateActionKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplate;
import com.liferay.layout.page.template.service.base.LayoutPageTemplateServiceBaseImpl;
import com.liferay.layout.page.template.service.permission.LayoutPageTemplatePermission;
import com.liferay.layout.page.template.service.permission.LayoutPageTemplateResourcePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Jürgen Kappler
 */
public class LayoutPageTemplateServiceImpl
	extends LayoutPageTemplateServiceBaseImpl {

	@Override
	public LayoutPageTemplate addLayoutPageTemplate(
			long groupId, long layoutPageTemplateFolderId, String name,
			Map<Integer, FragmentEntry> layoutPageTemplateFragments,
			ServiceContext serviceContext)
		throws PortalException {

		LayoutPageTemplateResourcePermission.check(
			getPermissionChecker(), groupId,
			LayoutPageTemplateActionKeys.ADD_LAYOUT_PAGE_TEMPLATE);

		return layoutPageTemplateLocalService.addLayoutPageTemplate(
			getUserId(), groupId, layoutPageTemplateFolderId, name,
			layoutPageTemplateFragments, serviceContext);
	}

	@Override
	public LayoutPageTemplate deleteLayoutPageTemplate(long pageTemplateId)
		throws PortalException {

		LayoutPageTemplatePermission.check(
			getPermissionChecker(), pageTemplateId, ActionKeys.DELETE);

		return layoutPageTemplateLocalService.deleteLayoutPageTemplate(
			pageTemplateId);
	}

	@Override
	public List<LayoutPageTemplate> deletePageTemplates(long[] pageTemplatesIds)
		throws PortalException {

		List<LayoutPageTemplate> undeletablePageTemplates = new ArrayList<>();

		for (long pageTemplateId : pageTemplatesIds) {
			try {
				LayoutPageTemplatePermission.check(
					getPermissionChecker(), pageTemplateId, ActionKeys.DELETE);

				layoutPageTemplateLocalService.deleteLayoutPageTemplate(
					pageTemplateId);
			}
			catch (PortalException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(pe, pe);
				}

				LayoutPageTemplate pageTemplate =
					layoutPageTemplatePersistence.fetchByPrimaryKey(
						pageTemplateId);

				undeletablePageTemplates.add(pageTemplate);
			}
		}

		return undeletablePageTemplates;
	}

	@Override
	public LayoutPageTemplate fetchLayoutPageTemplate(long pageTemplateId)
		throws PortalException {

		LayoutPageTemplate pageTemplate =
			layoutPageTemplateLocalService.fetchLayoutPageTemplate(
				pageTemplateId);

		if (pageTemplate != null) {
			LayoutPageTemplatePermission.check(
				getPermissionChecker(), pageTemplate, ActionKeys.VIEW);
		}

		return pageTemplate;
	}

	@Override
	public List<LayoutPageTemplate> fetchPageTemplates(
			long layoutPageTemplateFolderId)
		throws PortalException {

		return layoutPageTemplateLocalService.fetchLayoutPageTemplates(
			layoutPageTemplateFolderId);
	}

	@Override
	public int getLayoutPageTemplateFoldersCount(
		long groupId, long layoutPageTemplateFolderId) {

		return layoutPageTemplatePersistence.filterCountByG_L(
			groupId, layoutPageTemplateFolderId);
	}

	@Override
	public int getLayoutPageTemplateFoldersCount(
		long groupId, long layoutPageTemplateFolderId, String name) {

		return layoutPageTemplatePersistence.filterCountByG_L_LikeN(
			groupId, layoutPageTemplateFolderId, name);
	}

	@Override
	public List<LayoutPageTemplate> getPageTemplates(
			long groupId, long layoutPageTemplateFolderId, int start, int end)
		throws PortalException {

		return layoutPageTemplatePersistence.filterFindByG_L(
			groupId, layoutPageTemplateFolderId, start, end);
	}

	@Override
	public List<LayoutPageTemplate> getPageTemplates(
			long groupId, long layoutPageTemplateFolderId, int start, int end,
			OrderByComparator<LayoutPageTemplate> orderByComparator)
		throws PortalException {

		return layoutPageTemplatePersistence.filterFindByG_L(
			groupId, layoutPageTemplateFolderId, start, end, orderByComparator);
	}

	@Override
	public List<LayoutPageTemplate> getPageTemplates(
		long groupId, long layoutPageTemplateFolderId, String name, int start,
		int end, OrderByComparator<LayoutPageTemplate> orderByComparator) {

		return layoutPageTemplatePersistence.filterFindByG_L_LikeN(
			groupId, layoutPageTemplateFolderId, name, start, end,
			orderByComparator);
	}

	@Override
	public LayoutPageTemplate updateLayoutPageTemplate(
			long pageTemplateId, String name,
			Map<Integer, FragmentEntry> layoutPageTemplateFragments,
			ServiceContext serviceContext)
		throws PortalException {

		LayoutPageTemplatePermission.check(
			getPermissionChecker(), pageTemplateId, ActionKeys.UPDATE);

		return layoutPageTemplateLocalService.updateLayoutPageTemplate(
			getUserId(), pageTemplateId, name, layoutPageTemplateFragments,
			serviceContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutPageTemplateServiceImpl.class);

}