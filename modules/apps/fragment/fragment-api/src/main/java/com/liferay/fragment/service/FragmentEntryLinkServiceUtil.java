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
 * Provides the remote service utility for FragmentEntryLink. This utility wraps
 * <code>com.liferay.fragment.service.impl.FragmentEntryLinkServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryLinkService
 * @generated
 */
public class FragmentEntryLinkServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.fragment.service.impl.FragmentEntryLinkServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #addFragmentEntryLink(long, long, long, long, long, String,
	 String, String, String, String, String, int, String,
	 ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.fragment.model.FragmentEntryLink
			addFragmentEntryLink(
				long groupId, long originalFragmentEntryLinkId,
				long fragmentEntryId, long classNameId, long classPK,
				String css, String html, String js, String editableValues,
				String namespace, int position, String rendererKey,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFragmentEntryLink(
			groupId, originalFragmentEntryLinkId, fragmentEntryId, classNameId,
			classPK, css, html, js, editableValues, namespace, position,
			rendererKey, serviceContext);
	}

	public static com.liferay.fragment.model.FragmentEntryLink
			addFragmentEntryLink(
				long groupId, long originalFragmentEntryLinkId,
				long fragmentEntryId, long classNameId, long classPK,
				String css, String html, String js, String configuration,
				String editableValues, String namespace, int position,
				String rendererKey,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addFragmentEntryLink(
			groupId, originalFragmentEntryLinkId, fragmentEntryId, classNameId,
			classPK, css, html, js, configuration, editableValues, namespace,
			position, rendererKey, serviceContext);
	}

	public static com.liferay.fragment.model.FragmentEntryLink
			deleteFragmentEntryLink(long fragmentEntryLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteFragmentEntryLink(fragmentEntryLinkId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.fragment.model.FragmentEntryLink
			updateFragmentEntryLink(
				long fragmentEntryLinkId, String editableValues)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateFragmentEntryLink(
			fragmentEntryLinkId, editableValues);
	}

	public static com.liferay.fragment.model.FragmentEntryLink
			updateFragmentEntryLink(
				long fragmentEntryLinkId, String editableValues,
				boolean updateClassedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateFragmentEntryLink(
			fragmentEntryLinkId, editableValues, updateClassedModel);
	}

	public static void updateFragmentEntryLinks(
			long groupId, long classNameId, long classPK,
			long[] fragmentEntryIds, String editableValues,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateFragmentEntryLinks(
			groupId, classNameId, classPK, fragmentEntryIds, editableValues,
			serviceContext);
	}

	public static void updateFragmentEntryLinks(
			java.util.Map<Long, String> fragmentEntryLinksEditableValuesMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateFragmentEntryLinks(
			fragmentEntryLinksEditableValuesMap);
	}

	public static FragmentEntryLinkService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<FragmentEntryLinkService, FragmentEntryLinkService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(FragmentEntryLinkService.class);

		ServiceTracker<FragmentEntryLinkService, FragmentEntryLinkService>
			serviceTracker =
				new ServiceTracker
					<FragmentEntryLinkService, FragmentEntryLinkService>(
						bundle.getBundleContext(),
						FragmentEntryLinkService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}