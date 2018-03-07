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
 * Provides a wrapper for {@link LayoutPageTemplateCollectionService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateCollectionService
 * @generated
 */
@ProviderType
public class LayoutPageTemplateCollectionServiceWrapper
	implements LayoutPageTemplateCollectionService,
		ServiceWrapper<LayoutPageTemplateCollectionService> {
	public LayoutPageTemplateCollectionServiceWrapper(
		LayoutPageTemplateCollectionService layoutPageTemplateCollectionService) {
		_layoutPageTemplateCollectionService = layoutPageTemplateCollectionService;
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateCollection addLayoutPageTemplateCollection(
		long groupId, java.lang.String name, java.lang.String description,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateCollectionService.addLayoutPageTemplateCollection(groupId,
			name, description, serviceContext);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateCollection deleteLayoutPageTemplateCollection(
		long layoutPageTemplateCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateCollectionService.deleteLayoutPageTemplateCollection(layoutPageTemplateCollectionId);
	}

	@Override
	public void deleteLayoutPageTemplateCollections(
		long[] layoutPageTemplateCollectionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_layoutPageTemplateCollectionService.deleteLayoutPageTemplateCollections(layoutPageTemplateCollectionIds);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateCollection fetchLayoutPageTemplateCollection(
		long layoutPageTemplateCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateCollectionService.fetchLayoutPageTemplateCollection(layoutPageTemplateCollectionId);
	}

	@Override
	public java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateCollection> getBasicLayoutPageTemplateCollections(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateCollection> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateCollectionService.getBasicLayoutPageTemplateCollections(groupId,
			start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateCollection> getLayoutPageTemplateCollections(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateCollectionService.getLayoutPageTemplateCollections(groupId);
	}

	@Override
	public java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateCollection> getLayoutPageTemplateCollections(
		long groupId, int type)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateCollectionService.getLayoutPageTemplateCollections(groupId,
			type);
	}

	@Override
	public java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateCollection> getLayoutPageTemplateCollections(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateCollectionService.getLayoutPageTemplateCollections(groupId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateCollection> getLayoutPageTemplateCollections(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateCollection> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateCollectionService.getLayoutPageTemplateCollections(groupId,
			start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateCollection> getLayoutPageTemplateCollections(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateCollection> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateCollectionService.getLayoutPageTemplateCollections(groupId,
			name, start, end, orderByComparator);
	}

	@Override
	public int getLayoutPageTemplateCollectionsCount(long groupId) {
		return _layoutPageTemplateCollectionService.getLayoutPageTemplateCollectionsCount(groupId);
	}

	@Override
	public int getLayoutPageTemplateCollectionsCount(long groupId,
		java.lang.String name) {
		return _layoutPageTemplateCollectionService.getLayoutPageTemplateCollectionsCount(groupId,
			name);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _layoutPageTemplateCollectionService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateCollection updateLayoutPageTemplateCollection(
		long layoutPageTemplateCollectionId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateCollectionService.updateLayoutPageTemplateCollection(layoutPageTemplateCollectionId,
			name, description);
	}

	@Override
	public LayoutPageTemplateCollectionService getWrappedService() {
		return _layoutPageTemplateCollectionService;
	}

	@Override
	public void setWrappedService(
		LayoutPageTemplateCollectionService layoutPageTemplateCollectionService) {
		_layoutPageTemplateCollectionService = layoutPageTemplateCollectionService;
	}

	private LayoutPageTemplateCollectionService _layoutPageTemplateCollectionService;
}