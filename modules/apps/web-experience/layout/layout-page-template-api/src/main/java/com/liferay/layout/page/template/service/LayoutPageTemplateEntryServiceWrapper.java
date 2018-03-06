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
 * Provides a wrapper for {@link LayoutPageTemplateEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateEntryService
 * @generated
 */
@ProviderType
public class LayoutPageTemplateEntryServiceWrapper
	implements LayoutPageTemplateEntryService,
		ServiceWrapper<LayoutPageTemplateEntryService> {
	public LayoutPageTemplateEntryServiceWrapper(
		LayoutPageTemplateEntryService layoutPageTemplateEntryService) {
		_layoutPageTemplateEntryService = layoutPageTemplateEntryService;
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry addLayoutPageTemplateEntry(
		long groupId, long layoutPageTemplateCollectionId,
		java.lang.String name, long[] fragmentEntryIds,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateEntryService.addLayoutPageTemplateEntry(groupId,
			layoutPageTemplateCollectionId, name, fragmentEntryIds,
			serviceContext);
	}

	@Override
	public java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> deleteLayoutPageTemplateEntries(
		long[] layoutPageTemplateEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateEntryService.deleteLayoutPageTemplateEntries(layoutPageTemplateEntryIds);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry deleteLayoutPageTemplateEntry(
		long layoutPageTemplateEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateEntryService.deleteLayoutPageTemplateEntry(layoutPageTemplateEntryId);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry fetchDefaultLayoutPageTemplateEntry(
		long groupId, long classNameId) {
		return _layoutPageTemplateEntryService.fetchDefaultLayoutPageTemplateEntry(groupId,
			classNameId);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry fetchLayoutPageTemplateEntry(
		long layoutPageTemplateEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateEntryService.fetchLayoutPageTemplateEntry(layoutPageTemplateEntryId);
	}

	@Override
	public int getLayoutPageTemplateCollectionsCount(long groupId,
		long layoutPageTemplateCollectionId) {
		return _layoutPageTemplateEntryService.getLayoutPageTemplateCollectionsCount(groupId,
			layoutPageTemplateCollectionId);
	}

	@Override
	public int getLayoutPageTemplateCollectionsCount(long groupId,
		long layoutPageTemplateCollectionId, java.lang.String name) {
		return _layoutPageTemplateEntryService.getLayoutPageTemplateCollectionsCount(groupId,
			layoutPageTemplateCollectionId, name);
	}

	@Override
	public java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateEntryService.getLayoutPageTemplateEntries(groupId,
			layoutPageTemplateCollectionId, start, end);
	}

	@Override
	public java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateEntryService.getLayoutPageTemplateEntries(groupId,
			layoutPageTemplateCollectionId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId,
		java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> orderByComparator) {
		return _layoutPageTemplateEntryService.getLayoutPageTemplateEntries(groupId,
			layoutPageTemplateCollectionId, name, start, end, orderByComparator);
	}

	@Override
	public int getLayoutPageTemplateEntriesCount(long groupId,
		long layoutPageTemplateFolder) {
		return _layoutPageTemplateEntryService.getLayoutPageTemplateEntriesCount(groupId,
			layoutPageTemplateFolder);
	}

	@Override
	public int getLayoutPageTemplateEntriesCount(long groupId,
		long layoutPageTemplateFolder, java.lang.String name) {
		return _layoutPageTemplateEntryService.getLayoutPageTemplateEntriesCount(groupId,
			layoutPageTemplateFolder, name);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _layoutPageTemplateEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
		long layoutPageTemplateEntryId, long[] fragmentEntryIds,
		java.lang.String editableValues,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateEntryService.updateLayoutPageTemplateEntry(layoutPageTemplateEntryId,
			fragmentEntryIds, editableValues, serviceContext);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
		long layoutPageTemplateEntryId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateEntryService.updateLayoutPageTemplateEntry(layoutPageTemplateEntryId,
			name);
	}

	@Override
	public com.liferay.layout.page.template.model.LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
		long layoutPageTemplateEntryId, java.lang.String name,
		long[] fragmentEntryIds,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateEntryService.updateLayoutPageTemplateEntry(layoutPageTemplateEntryId,
			name, fragmentEntryIds, serviceContext);
	}

	@Override
	public LayoutPageTemplateEntryService getWrappedService() {
		return _layoutPageTemplateEntryService;
	}

	@Override
	public void setWrappedService(
		LayoutPageTemplateEntryService layoutPageTemplateEntryService) {
		_layoutPageTemplateEntryService = layoutPageTemplateEntryService;
	}

	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;
}