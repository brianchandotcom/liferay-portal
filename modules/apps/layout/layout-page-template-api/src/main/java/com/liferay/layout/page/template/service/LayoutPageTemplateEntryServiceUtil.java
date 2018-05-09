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
 * Provides the remote service utility for LayoutPageTemplateEntry. This utility wraps
 * {@link com.liferay.layout.page.template.service.impl.LayoutPageTemplateEntryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateEntryService
 * @see com.liferay.layout.page.template.service.base.LayoutPageTemplateEntryServiceBaseImpl
 * @see com.liferay.layout.page.template.service.impl.LayoutPageTemplateEntryServiceImpl
 * @generated
 */
@ProviderType
public class LayoutPageTemplateEntryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.layout.page.template.service.impl.LayoutPageTemplateEntryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntry addLayoutPageTemplateEntry(
		long groupId, long layoutPageTemplateCollectionId, String name,
		int type, long[] fragmentEntryIds, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addLayoutPageTemplateEntry(groupId,
			layoutPageTemplateCollectionId, name, type, fragmentEntryIds,
			status, serviceContext);
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntry addLayoutPageTemplateEntry(
		long groupId, long layoutPageTemplateCollectionId, String name,
		int type, long[] fragmentEntryIds,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addLayoutPageTemplateEntry(groupId,
			layoutPageTemplateCollectionId, name, type, fragmentEntryIds,
			serviceContext);
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntry addLayoutPageTemplateEntry(
		long groupId, long layoutPageTemplateCollectionId, String name,
		long[] fragmentEntryIds, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addLayoutPageTemplateEntry(groupId,
			layoutPageTemplateCollectionId, name, fragmentEntryIds, status,
			serviceContext);
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntry addLayoutPageTemplateEntry(
		long groupId, long layoutPageTemplateCollectionId, String name,
		long[] fragmentEntryIds,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addLayoutPageTemplateEntry(groupId,
			layoutPageTemplateCollectionId, name, fragmentEntryIds,
			serviceContext);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> deleteLayoutPageTemplateEntries(
		long[] layoutPageTemplateEntryIds) {
		return getService()
				   .deleteLayoutPageTemplateEntries(layoutPageTemplateEntryIds);
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntry deleteLayoutPageTemplateEntry(
		long layoutPageTemplateEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteLayoutPageTemplateEntry(layoutPageTemplateEntryId);
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntry fetchDefaultLayoutPageTemplateEntry(
		long groupId, long classNameId, long classTypeId) {
		return getService()
				   .fetchDefaultLayoutPageTemplateEntry(groupId, classNameId,
			classTypeId);
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntry fetchLayoutPageTemplateEntry(
		long layoutPageTemplateEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .fetchLayoutPageTemplateEntry(layoutPageTemplateEntryId);
	}

	public static int getLayoutPageTemplateCollectionsCount(long groupId,
		long layoutPageTemplateCollectionId) {
		return getService()
				   .getLayoutPageTemplateCollectionsCount(groupId,
			layoutPageTemplateCollectionId);
	}

	public static int getLayoutPageTemplateCollectionsCount(long groupId,
		long layoutPageTemplateCollectionId, int status) {
		return getService()
				   .getLayoutPageTemplateCollectionsCount(groupId,
			layoutPageTemplateCollectionId, status);
	}

	public static int getLayoutPageTemplateCollectionsCount(long groupId,
		long layoutPageTemplateCollectionId, String name) {
		return getService()
				   .getLayoutPageTemplateCollectionsCount(groupId,
			layoutPageTemplateCollectionId, name);
	}

	public static int getLayoutPageTemplateCollectionsCount(long groupId,
		long layoutPageTemplateCollectionId, String name, int status) {
		return getService()
				   .getLayoutPageTemplateCollectionsCount(groupId,
			layoutPageTemplateCollectionId, name, status);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, int type, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> orderByComparator) {
		return getService()
				   .getLayoutPageTemplateEntries(groupId, type, status, start,
			end, orderByComparator);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> orderByComparator) {
		return getService()
				   .getLayoutPageTemplateEntries(groupId, type, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, int start, int end) {
		return getService()
				   .getLayoutPageTemplateEntries(groupId,
			layoutPageTemplateCollectionId, start, end);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, int status,
		int start, int end) {
		return getService()
				   .getLayoutPageTemplateEntries(groupId,
			layoutPageTemplateCollectionId, status, start, end);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> orderByComparator) {
		return getService()
				   .getLayoutPageTemplateEntries(groupId,
			layoutPageTemplateCollectionId, status, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> orderByComparator) {
		return getService()
				   .getLayoutPageTemplateEntries(groupId,
			layoutPageTemplateCollectionId, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long classNameId, long classTypeId, int type) {
		return getService()
				   .getLayoutPageTemplateEntries(groupId, classNameId,
			classTypeId, type);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long classNameId, long classTypeId, int type, int status) {
		return getService()
				   .getLayoutPageTemplateEntries(groupId, classNameId,
			classTypeId, type, status);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long classNameId, long classTypeId, int type, int status,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> orderByComparator) {
		return getService()
				   .getLayoutPageTemplateEntries(groupId, classNameId,
			classTypeId, type, status, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long classNameId, long classTypeId, int type, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> orderByComparator) {
		return getService()
				   .getLayoutPageTemplateEntries(groupId, classNameId,
			classTypeId, type, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long classNameId, long classTypeId, String name,
		int type, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> orderByComparator) {
		return getService()
				   .getLayoutPageTemplateEntries(groupId, classNameId,
			classTypeId, name, type, status, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long classNameId, long classTypeId, String name,
		int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> orderByComparator) {
		return getService()
				   .getLayoutPageTemplateEntries(groupId, classNameId,
			classTypeId, name, type, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, String name,
		int start, int status, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> orderByComparator) {
		return getService()
				   .getLayoutPageTemplateEntries(groupId,
			layoutPageTemplateCollectionId, name, start, status, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, String name,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> orderByComparator) {
		return getService()
				   .getLayoutPageTemplateEntries(groupId,
			layoutPageTemplateCollectionId, name, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, String name, int type, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> orderByComparator) {
		return getService()
				   .getLayoutPageTemplateEntries(groupId, name, type, status,
			start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, String name, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.layout.page.template.model.LayoutPageTemplateEntry> orderByComparator) {
		return getService()
				   .getLayoutPageTemplateEntries(groupId, name, type, start,
			end, orderByComparator);
	}

	public static int getLayoutPageTemplateEntriesCount(long groupId, int type) {
		return getService().getLayoutPageTemplateEntriesCount(groupId, type);
	}

	public static int getLayoutPageTemplateEntriesCount(long groupId, int type,
		int status) {
		return getService()
				   .getLayoutPageTemplateEntriesCount(groupId, type, status);
	}

	public static int getLayoutPageTemplateEntriesCount(long groupId,
		long layoutPageTemplateFolder) {
		return getService()
				   .getLayoutPageTemplateEntriesCount(groupId,
			layoutPageTemplateFolder);
	}

	public static int getLayoutPageTemplateEntriesCount(long groupId,
		long layoutPageTemplateFolder, int status) {
		return getService()
				   .getLayoutPageTemplateEntriesCount(groupId,
			layoutPageTemplateFolder, status);
	}

	public static int getLayoutPageTemplateEntriesCount(long groupId,
		long classNameId, long classTypeId, int type) {
		return getService()
				   .getLayoutPageTemplateEntriesCount(groupId, classNameId,
			classTypeId, type);
	}

	public static int getLayoutPageTemplateEntriesCount(long groupId,
		long classNameId, long classTypeId, int type, int status) {
		return getService()
				   .getLayoutPageTemplateEntriesCount(groupId, classNameId,
			classTypeId, type, status);
	}

	public static int getLayoutPageTemplateEntriesCount(long groupId,
		long classNameId, long classTypeId, String name, int type) {
		return getService()
				   .getLayoutPageTemplateEntriesCount(groupId, classNameId,
			classTypeId, name, type);
	}

	public static int getLayoutPageTemplateEntriesCount(long groupId,
		long classNameId, long classTypeId, String name, int type, int status) {
		return getService()
				   .getLayoutPageTemplateEntriesCount(groupId, classNameId,
			classTypeId, name, type, status);
	}

	public static int getLayoutPageTemplateEntriesCount(long groupId,
		long layoutPageTemplateFolder, String name) {
		return getService()
				   .getLayoutPageTemplateEntriesCount(groupId,
			layoutPageTemplateFolder, name);
	}

	public static int getLayoutPageTemplateEntriesCount(long groupId,
		long layoutPageTemplateFolder, String name, int status) {
		return getService()
				   .getLayoutPageTemplateEntriesCount(groupId,
			layoutPageTemplateFolder, name, status);
	}

	public static int getLayoutPageTemplateEntriesCount(long groupId,
		String name, int type) {
		return getService()
				   .getLayoutPageTemplateEntriesCount(groupId, name, type);
	}

	public static int getLayoutPageTemplateEntriesCount(long groupId,
		String name, int type, int status) {
		return getService()
				   .getLayoutPageTemplateEntriesCount(groupId, name, type,
			status);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
		long layoutPageTemplateEntryId, boolean defaultTemplate)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateLayoutPageTemplateEntry(layoutPageTemplateEntryId,
			defaultTemplate);
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
		long layoutPageTemplateEntryId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateLayoutPageTemplateEntry(layoutPageTemplateEntryId,
			status);
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
		long layoutPageTemplateEntryId, long classNameId, long classTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateLayoutPageTemplateEntry(layoutPageTemplateEntryId,
			classNameId, classTypeId);
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
		long layoutPageTemplateEntryId, long[] fragmentEntryIds,
		String editableValues,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateLayoutPageTemplateEntry(layoutPageTemplateEntryId,
			fragmentEntryIds, editableValues, serviceContext);
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
		long layoutPageTemplateEntryId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateLayoutPageTemplateEntry(layoutPageTemplateEntryId,
			name);
	}

	public static com.liferay.layout.page.template.model.LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
		long layoutPageTemplateEntryId, String name, long[] fragmentEntryIds,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateLayoutPageTemplateEntry(layoutPageTemplateEntryId,
			name, fragmentEntryIds, serviceContext);
	}

	public static LayoutPageTemplateEntryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<LayoutPageTemplateEntryService, LayoutPageTemplateEntryService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(LayoutPageTemplateEntryService.class);

		ServiceTracker<LayoutPageTemplateEntryService, LayoutPageTemplateEntryService> serviceTracker =
			new ServiceTracker<LayoutPageTemplateEntryService, LayoutPageTemplateEntryService>(bundle.getBundleContext(),
				LayoutPageTemplateEntryService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}