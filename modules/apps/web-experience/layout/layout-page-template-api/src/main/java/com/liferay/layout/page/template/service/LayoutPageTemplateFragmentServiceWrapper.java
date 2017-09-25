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
 * Provides a wrapper for {@link LayoutPageTemplateFragmentService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateFragmentService
 * @generated
 */
@ProviderType
public class LayoutPageTemplateFragmentServiceWrapper
	implements LayoutPageTemplateFragmentService,
		ServiceWrapper<LayoutPageTemplateFragmentService> {
	public LayoutPageTemplateFragmentServiceWrapper(
		LayoutPageTemplateFragmentService layoutPageTemplateFragmentService) {
		_layoutPageTemplateFragmentService = layoutPageTemplateFragmentService;
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateFragment addLayoutPageTemplateFragment(
		com.liferay.layout.page.template.model.LayoutPageTemplateFragment layoutPageTemplateFragment,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateFragmentService.addLayoutPageTemplateFragment(layoutPageTemplateFragment,
			serviceContext);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateFragment addLayoutPageTemplateFragment(
		long groupId, long layoutPageTemplateId, long fragmentId, int position,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateFragmentService.addLayoutPageTemplateFragment(groupId,
			layoutPageTemplateId, fragmentId, position, serviceContext);
	}

	@Override
	public java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateFragment> deleteByLayoutPageTemplate(
		long groupId, long layoutPageTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateFragmentService.deleteByLayoutPageTemplate(groupId,
			layoutPageTemplateId);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateFragment deleteLayoutPageTemplateFragment(
		long groupId, long layoutPageTemplateId, long fragmentId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateFragmentService.deleteLayoutPageTemplateFragment(groupId,
			layoutPageTemplateId, fragmentId, serviceContext);
	}

	@Override
	public java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateFragment> getLayoutPageTemplateFragmentsByPageTemplate(
		long groupId, long layoutPageTemplateId) {
		return _layoutPageTemplateFragmentService.getLayoutPageTemplateFragmentsByPageTemplate(groupId,
			layoutPageTemplateId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _layoutPageTemplateFragmentService.getOSGiServiceIdentifier();
	}

	@Override
	public LayoutPageTemplateFragmentService getWrappedService() {
		return _layoutPageTemplateFragmentService;
	}

	@Override
	public void setWrappedService(
		LayoutPageTemplateFragmentService layoutPageTemplateFragmentService) {
		_layoutPageTemplateFragmentService = layoutPageTemplateFragmentService;
	}

	private LayoutPageTemplateFragmentService _layoutPageTemplateFragmentService;
}