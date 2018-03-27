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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for LayoutPageTemplateCollection. This utility wraps
 * {@link com.liferay.layout.page.template.service.impl.LayoutPageTemplateCollectionServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateCollectionService
 * @see com.liferay.layout.page.template.service.base.LayoutPageTemplateCollectionServiceBaseImpl
 * @see com.liferay.layout.page.template.service.impl.LayoutPageTemplateCollectionServiceImpl
 * @generated
 */
@ProviderType
public class LayoutPageTemplateCollectionServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.layout.page.template.service.impl.LayoutPageTemplateCollectionServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.layout.page.template.model.LayoutPageTemplateCollection addLayoutPageTemplateCollection(
		long groupId, java.lang.String name, java.lang.String description,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addLayoutPageTemplateCollection(groupId, name, description,
			serviceContext);
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateCollection deleteLayoutPageTemplateCollection(
		long layoutPageTemplateCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteLayoutPageTemplateCollection(layoutPageTemplateCollectionId);
	}

	public static void deleteLayoutPageTemplateCollections(
		long[] layoutPageTemplateCollectionIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.deleteLayoutPageTemplateCollections(layoutPageTemplateCollectionIds);
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateCollection fetchLayoutPageTemplateCollection(
		long layoutPageTemplateCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .fetchLayoutPageTemplateCollection(layoutPageTemplateCollectionId);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateCollection> getBasicLayoutPageTemplateCollections(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateCollection> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getBasicLayoutPageTemplateCollections(groupId, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateCollection> getLayoutPageTemplateCollections(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getLayoutPageTemplateCollections(groupId);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateCollection> getLayoutPageTemplateCollections(
		long groupId, int type)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getLayoutPageTemplateCollections(groupId, type);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateCollection> getLayoutPageTemplateCollections(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getLayoutPageTemplateCollections(groupId, start, end);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateCollection> getLayoutPageTemplateCollections(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateCollection> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getLayoutPageTemplateCollections(groupId, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateCollection> getLayoutPageTemplateCollections(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateCollection> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getLayoutPageTemplateCollections(groupId, name, start, end,
			orderByComparator);
	}

	public static int getLayoutPageTemplateCollectionsCount(long groupId) {
		return getService().getLayoutPageTemplateCollectionsCount(groupId);
	}

	public static int getLayoutPageTemplateCollectionsCount(long groupId,
		java.lang.String name) {
		return getService().getLayoutPageTemplateCollectionsCount(groupId, name);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateCollection updateLayoutPageTemplateCollection(
		long layoutPageTemplateCollectionId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateLayoutPageTemplateCollection(layoutPageTemplateCollectionId,
			name, description);
	}

	public static LayoutPageTemplateCollectionService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<LayoutPageTemplateCollectionService, LayoutPageTemplateCollectionService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(LayoutPageTemplateCollectionService.class);

		ServiceTracker<LayoutPageTemplateCollectionService, LayoutPageTemplateCollectionService> serviceTracker =
			new ServiceTracker<LayoutPageTemplateCollectionService, LayoutPageTemplateCollectionService>(bundle.getBundleContext(),
				LayoutPageTemplateCollectionService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}