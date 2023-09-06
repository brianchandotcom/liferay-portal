/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.service;

import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link LayoutPageTemplateCollectionService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateCollectionService
 * @generated
 */
public class LayoutPageTemplateCollectionServiceWrapper
	implements LayoutPageTemplateCollectionService,
			   ServiceWrapper<LayoutPageTemplateCollectionService> {

	public LayoutPageTemplateCollectionServiceWrapper() {
		this(null);
	}

	public LayoutPageTemplateCollectionServiceWrapper(
		LayoutPageTemplateCollectionService
			layoutPageTemplateCollectionService) {

		_layoutPageTemplateCollectionService =
			layoutPageTemplateCollectionService;
	}

	@Override
	public LayoutPageTemplateCollection addLayoutPageTemplateCollection(
			long groupId, long parentLayoutPageTemplateCollection, String name,
			String description, int type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateCollectionService.
			addLayoutPageTemplateCollection(
				groupId, parentLayoutPageTemplateCollection, name, description,
				type, serviceContext);
	}

	@Override
	public LayoutPageTemplateCollection addLayoutPageTemplateCollection(
			long groupId, long parentLayoutPageTemplateCollection, String name,
			String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateCollectionService.
			addLayoutPageTemplateCollection(
				groupId, parentLayoutPageTemplateCollection, name, description,
				serviceContext);
	}

	@Override
	public LayoutPageTemplateCollection addLayoutPageTemplateCollection(
			long groupId, String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateCollectionService.
			addLayoutPageTemplateCollection(
				groupId, name, description, serviceContext);
	}

	@Override
	public LayoutPageTemplateCollection deleteLayoutPageTemplateCollection(
			long layoutPageTemplateCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateCollectionService.
			deleteLayoutPageTemplateCollection(layoutPageTemplateCollectionId);
	}

	@Override
	public void deleteLayoutPageTemplateCollections(
			long[] layoutPageTemplateCollectionIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_layoutPageTemplateCollectionService.
			deleteLayoutPageTemplateCollections(
				layoutPageTemplateCollectionIds);
	}

	@Override
	public LayoutPageTemplateCollection fetchLayoutPageTemplateCollection(
			long layoutPageTemplateCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateCollectionService.
			fetchLayoutPageTemplateCollection(layoutPageTemplateCollectionId);
	}

	@Override
	public java.util.List<LayoutPageTemplateCollection>
		getLayoutPageTemplateCollections(long groupId, int type) {

		return _layoutPageTemplateCollectionService.
			getLayoutPageTemplateCollections(groupId, type);
	}

	@Override
	public java.util.List<LayoutPageTemplateCollection>
		getLayoutPageTemplateCollections(
			long groupId, int start, int end, int type) {

		return _layoutPageTemplateCollectionService.
			getLayoutPageTemplateCollections(groupId, start, end, type);
	}

	@Override
	public java.util.List<LayoutPageTemplateCollection>
		getLayoutPageTemplateCollections(
			long groupId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateCollection> orderByComparator,
			int type) {

		return _layoutPageTemplateCollectionService.
			getLayoutPageTemplateCollections(
				groupId, start, end, orderByComparator, type);
	}

	@Override
	public java.util.List<LayoutPageTemplateCollection>
		getLayoutPageTemplateCollections(
			long groupId, String name, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<LayoutPageTemplateCollection> orderByComparator,
			int type) {

		return _layoutPageTemplateCollectionService.
			getLayoutPageTemplateCollections(
				groupId, name, start, end, orderByComparator, type);
	}

	@Override
	public int getLayoutPageTemplateCollectionsCount(long groupId, int type) {
		return _layoutPageTemplateCollectionService.
			getLayoutPageTemplateCollectionsCount(groupId, type);
	}

	@Override
	public int getLayoutPageTemplateCollectionsCount(
		long groupId, String name, int type) {

		return _layoutPageTemplateCollectionService.
			getLayoutPageTemplateCollectionsCount(groupId, name, type);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _layoutPageTemplateCollectionService.getOSGiServiceIdentifier();
	}

	@Override
	public LayoutPageTemplateCollection updateLayoutPageTemplateCollection(
			long layoutPageTemplateCollectionId, String name,
			String description, int type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutPageTemplateCollectionService.
			updateLayoutPageTemplateCollection(
				layoutPageTemplateCollectionId, name, description, type);
	}

	@Override
	public LayoutPageTemplateCollectionService getWrappedService() {
		return _layoutPageTemplateCollectionService;
	}

	@Override
	public void setWrappedService(
		LayoutPageTemplateCollectionService
			layoutPageTemplateCollectionService) {

		_layoutPageTemplateCollectionService =
			layoutPageTemplateCollectionService;
	}

	private LayoutPageTemplateCollectionService
		_layoutPageTemplateCollectionService;

}