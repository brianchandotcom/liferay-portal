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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for FragmentComposition. This utility wraps
 * <code>com.liferay.fragment.service.impl.FragmentCompositionServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentCompositionService
 * @generated
 */
public class FragmentCompositionServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.fragment.service.impl.FragmentCompositionServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.fragment.model.FragmentComposition
			addFragmentComposition(
				long groupId, long fragmentCollectionId,
				String fragmentCompositionKey, String name, String description,
				String fragments, long previewFileEntryId, int status,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFragmentComposition(
			groupId, fragmentCollectionId, fragmentCompositionKey, name,
			description, fragments, previewFileEntryId, status, serviceContext);
	}

	public static com.liferay.fragment.model.FragmentComposition
			deleteFragmentComposition(long fragmentCompositionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteFragmentComposition(fragmentCompositionId);
	}

	public static com.liferay.fragment.model.FragmentComposition
		fetchFragmentComposition(long fragmentCompositionId) {

		return getService().fetchFragmentComposition(fragmentCompositionId);
	}

	public static com.liferay.fragment.model.FragmentComposition
		fetchFragmentComposition(long groupId, String fragmentCompositionKey) {

		return getService().fetchFragmentComposition(
			groupId, fragmentCompositionKey);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentComposition>
		getFragmentCompositions(long fragmentCollectionId) {

		return getService().getFragmentCompositions(fragmentCollectionId);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentComposition>
		getFragmentCompositions(long fragmentCollectionId, int start, int end) {

		return getService().getFragmentCompositions(
			fragmentCollectionId, start, end);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentComposition>
		getFragmentCompositions(
			long groupId, long fragmentCollectionId, int status) {

		return getService().getFragmentCompositions(
			groupId, fragmentCollectionId, status);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentComposition>
		getFragmentCompositions(
			long groupId, long fragmentCollectionId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentComposition>
					orderByComparator) {

		return getService().getFragmentCompositions(
			groupId, fragmentCollectionId, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.fragment.model.FragmentComposition>
		getFragmentCompositions(
			long groupId, long fragmentCollectionId, String name, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.fragment.model.FragmentComposition>
					orderByComparator) {

		return getService().getFragmentCompositions(
			groupId, fragmentCollectionId, name, start, end, orderByComparator);
	}

	public static int getFragmentCompositionsCount(long fragmentCollectionId) {
		return getService().getFragmentCompositionsCount(fragmentCollectionId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.fragment.model.FragmentComposition
			updateFragmentComposition(
				long fragmentCompositionId, long previewFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateFragmentComposition(
			fragmentCompositionId, previewFileEntryId);
	}

	public static com.liferay.fragment.model.FragmentComposition
			updateFragmentComposition(
				long fragmentCompositionId, String name, String description,
				String fragments, long previewFileEntryId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateFragmentComposition(
			fragmentCompositionId, name, description, fragments,
			previewFileEntryId, status);
	}

	public static FragmentCompositionService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<FragmentCompositionService, FragmentCompositionService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			FragmentCompositionService.class);

		ServiceTracker<FragmentCompositionService, FragmentCompositionService>
			serviceTracker =
				new ServiceTracker
					<FragmentCompositionService, FragmentCompositionService>(
						bundle.getBundleContext(),
						FragmentCompositionService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}