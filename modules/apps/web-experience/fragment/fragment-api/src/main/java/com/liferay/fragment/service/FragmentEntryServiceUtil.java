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

package com.liferay.fragment.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for FragmentEntry. This utility wraps
 * {@link com.liferay.fragment.service.impl.FragmentEntryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryService
 * @see com.liferay.fragment.service.base.FragmentEntryServiceBaseImpl
 * @see com.liferay.fragment.service.impl.FragmentEntryServiceImpl
 * @generated
 */
@ProviderType
public class FragmentEntryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.fragment.service.impl.FragmentEntryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.fragment.model.FragmentEntry addFragmentEntry(
		long groupId, long fragmentCollectionId, java.lang.String name,
		int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addFragmentEntry(groupId, fragmentCollectionId, name,
			status, serviceContext);
	}

	public static com.liferay.fragment.model.FragmentEntry addFragmentEntry(
		long groupId, long fragmentCollectionId,
		java.lang.String fragmentEntryKey, java.lang.String name, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addFragmentEntry(groupId, fragmentCollectionId,
			fragmentEntryKey, name, status, serviceContext);
	}

	public static com.liferay.fragment.model.FragmentEntry addFragmentEntry(
		long groupId, long fragmentCollectionId, java.lang.String name,
		java.lang.String css, java.lang.String html, java.lang.String js,
		int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addFragmentEntry(groupId, fragmentCollectionId, name, css,
			html, js, status, serviceContext);
	}

	public static com.liferay.fragment.model.FragmentEntry addFragmentEntry(
		long groupId, long fragmentCollectionId,
		java.lang.String fragmentEntryKey, java.lang.String name,
		java.lang.String css, java.lang.String html, java.lang.String js,
		int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addFragmentEntry(groupId, fragmentCollectionId,
			fragmentEntryKey, name, css, html, js, status, serviceContext);
	}

	public static void deleteFragmentEntries(long[] fragmentEntriesIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteFragmentEntries(fragmentEntriesIds);
	}

	public static com.liferay.fragment.model.FragmentEntry deleteFragmentEntry(
		long fragmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteFragmentEntry(fragmentEntryId);
	}

	public static com.liferay.fragment.model.FragmentEntry fetchFragmentEntry(
		long fragmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchFragmentEntry(fragmentEntryId);
	}

	public static int getFragmentCollectionsCount(long groupId,
		long fragmentCollectionId) {
		return getService()
				   .getFragmentCollectionsCount(groupId, fragmentCollectionId);
	}

	public static int getFragmentCollectionsCount(long groupId,
		long fragmentCollectionId, java.lang.String name) {
		return getService()
				   .getFragmentCollectionsCount(groupId, fragmentCollectionId,
			name);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentEntry> getFragmentEntries(
		long fragmentCollectionId) {
		return getService().getFragmentEntries(fragmentCollectionId);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentEntry> getFragmentEntries(
		long fragmentCollectionId, int status) {
		return getService().getFragmentEntries(fragmentCollectionId, status);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentEntry> getFragmentEntries(
		long groupId, long fragmentCollectionId, int start, int end) {
		return getService()
				   .getFragmentEntries(groupId, fragmentCollectionId, start, end);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentEntry> getFragmentEntries(
		long groupId, long fragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.fragment.model.FragmentEntry> orderByComparator) {
		return getService()
				   .getFragmentEntries(groupId, fragmentCollectionId, start,
			end, orderByComparator);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentEntry> getFragmentEntries(
		long groupId, long fragmentCollectionId, java.lang.String name,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.fragment.model.FragmentEntry> orderByComparator) {
		return getService()
				   .getFragmentEntries(groupId, fragmentCollectionId, name,
			start, end, orderByComparator);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.lang.String[] getTempFileNames(long groupId,
		java.lang.String folderName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getTempFileNames(groupId, folderName);
	}

	public static com.liferay.fragment.model.FragmentEntry updateFragmentEntry(
		long fragmentEntryId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateFragmentEntry(fragmentEntryId, name);
	}

	public static com.liferay.fragment.model.FragmentEntry updateFragmentEntry(
		long fragmentEntryId, java.lang.String name, java.lang.String css,
		java.lang.String html, java.lang.String js, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateFragmentEntry(fragmentEntryId, name, css, html, js,
			status, serviceContext);
	}

	public static FragmentEntryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<FragmentEntryService, FragmentEntryService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(FragmentEntryService.class);

		ServiceTracker<FragmentEntryService, FragmentEntryService> serviceTracker =
			new ServiceTracker<FragmentEntryService, FragmentEntryService>(bundle.getBundleContext(),
				FragmentEntryService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}