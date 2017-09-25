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
 * Provides a wrapper for {@link LayoutPageTemplateService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateService
 * @generated
 */
@ProviderType
public class LayoutPageTemplateServiceWrapper
	implements LayoutPageTemplateService,
		ServiceWrapper<LayoutPageTemplateService> {
	public LayoutPageTemplateServiceWrapper(
		LayoutPageTemplateService layoutPageTemplateService) {
		_layoutPageTemplateService = layoutPageTemplateService;
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplate addLayoutPageTemplate(
		long groupId, long layoutPageTemplateFolderId, java.lang.String name,
		java.util.List<com.liferay.fragment.model.FragmentEntry> layoutPageTemplateFragments,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateService.addLayoutPageTemplate(groupId,
			layoutPageTemplateFolderId, name, layoutPageTemplateFragments,
			serviceContext);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplate deleteLayoutPageTemplate(
		long layoutPageTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateService.deleteLayoutPageTemplate(layoutPageTemplateId);
	}

	@Override
	public java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplate> deleteLayoutPageTemplates(
		long[] layoutPageTemplateIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateService.deleteLayoutPageTemplates(layoutPageTemplateIds);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplate fetchLayoutPageTemplate(
		long layoutPageTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateService.fetchLayoutPageTemplate(layoutPageTemplateId);
	}

	@Override
	public int getLayoutPageTemplateFoldersCount(long groupId,
		long layoutPageTemplateFolderId) {
		return _layoutPageTemplateService.getLayoutPageTemplateFoldersCount(groupId,
			layoutPageTemplateFolderId);
	}

	@Override
	public int getLayoutPageTemplateFoldersCount(long groupId,
		long layoutPageTemplateFolderId, java.lang.String name) {
		return _layoutPageTemplateService.getLayoutPageTemplateFoldersCount(groupId,
			layoutPageTemplateFolderId, name);
	}

	@Override
	public java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplate> getLayoutPageTemplates(
		long groupId, long layoutPageTemplateFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateService.getLayoutPageTemplates(groupId,
			layoutPageTemplateFolderId, start, end);
	}

	@Override
	public java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplate> getLayoutPageTemplates(
		long groupId, long layoutPageTemplateFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplate> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateService.getLayoutPageTemplates(groupId,
			layoutPageTemplateFolderId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplate> getLayoutPageTemplates(
		long groupId, long layoutPageTemplateFolderId, java.lang.String name,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplate> orderByComparator) {
		return _layoutPageTemplateService.getLayoutPageTemplates(groupId,
			layoutPageTemplateFolderId, name, start, end, orderByComparator);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _layoutPageTemplateService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplate updateLayoutPageTemplate(
		long layoutPageTemplateId, java.lang.String name,
		java.util.List<com.liferay.fragment.model.FragmentEntry> layoutPageTemplateFragments,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateService.updateLayoutPageTemplate(layoutPageTemplateId,
			name, layoutPageTemplateFragments, serviceContext);
	}

	@Override
	public LayoutPageTemplateService getWrappedService() {
		return _layoutPageTemplateService;
	}

	@Override
	public void setWrappedService(
		LayoutPageTemplateService layoutPageTemplateService) {
		_layoutPageTemplateService = layoutPageTemplateService;
	}

	private LayoutPageTemplateService _layoutPageTemplateService;
}