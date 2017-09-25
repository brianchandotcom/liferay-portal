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

package com.liferay.layout.page.template.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link LayoutPageTemplateFolderService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateFolderService
 * @generated
 */
@ProviderType
public class LayoutPageTemplateFolderServiceWrapper
	implements LayoutPageTemplateFolderService,
		ServiceWrapper<LayoutPageTemplateFolderService> {
	public LayoutPageTemplateFolderServiceWrapper(
		LayoutPageTemplateFolderService layoutPageTemplateFolderService) {
		_layoutPageTemplateFolderService = layoutPageTemplateFolderService;
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateFolder addLayoutPageTemplateFolder(
		long groupId, java.lang.String name, java.lang.String description,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateFolderService.addLayoutPageTemplateFolder(groupId,
			name, description, serviceContext);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateFolder deleteLayoutPageTemplateFolder(
		long layoutPageTemplateFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateFolderService.deleteLayoutPageTemplateFolder(layoutPageTemplateFolderId);
	}

	@Override
	public java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateFolder> deleteLayoutPageTemplateFolders(
		long[] layoutPageTemplateFolderIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateFolderService.deleteLayoutPageTemplateFolders(layoutPageTemplateFolderIds);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateFolder fetchLayoutPageTemplateFolder(
		long layoutPageTemplateFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateFolderService.fetchLayoutPageTemplateFolder(layoutPageTemplateFolderId);
	}

	@Override
	public java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateFolder> getLayoutPageTemplateFolders(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateFolderService.getLayoutPageTemplateFolders(groupId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateFolder> getLayoutPageTemplateFolders(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateFolder> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateFolderService.getLayoutPageTemplateFolders(groupId,
			start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateFolder> getLayoutPageTemplateFolders(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateFolder> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateFolderService.getLayoutPageTemplateFolders(groupId,
			name, start, end, orderByComparator);
	}

	@Override
	public int getLayoutPageTemplateFoldersCount(long groupId) {
		return _layoutPageTemplateFolderService.getLayoutPageTemplateFoldersCount(groupId);
	}

	@Override
	public int getLayoutPageTemplateFoldersCount(long groupId,
		java.lang.String name) {
		return _layoutPageTemplateFolderService.getLayoutPageTemplateFoldersCount(groupId,
			name);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _layoutPageTemplateFolderService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateFolder updateLayoutPageTemplateFolder(
		long layoutPageTemplateFolderId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateFolderService.updateLayoutPageTemplateFolder(layoutPageTemplateFolderId,
			name, description);
	}

	@Override
	public LayoutPageTemplateFolderService getWrappedService() {
		return _layoutPageTemplateFolderService;
	}

	@Override
	public void setWrappedService(
		LayoutPageTemplateFolderService layoutPageTemplateFolderService) {
		_layoutPageTemplateFolderService = layoutPageTemplateFolderService;
	}

	private LayoutPageTemplateFolderService _layoutPageTemplateFolderService;
}