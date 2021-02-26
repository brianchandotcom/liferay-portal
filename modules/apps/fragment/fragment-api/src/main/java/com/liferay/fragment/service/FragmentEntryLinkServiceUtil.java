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

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Map;

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
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #addFragmentEntryLink(long, long, long, long, long, String,
	 String, String, String, String, String, int, String,
	 ServiceContext)}
	 */
	@Deprecated
	public static FragmentEntryLink addFragmentEntryLink(
			long groupId, long originalFragmentEntryLinkId,
			long fragmentEntryId, long segmentsExperienceId, long classNameId,
			long classPK, String css, String html, String js,
			String configuration, String editableValues, String namespace,
			int position, String rendererKey,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addFragmentEntryLink(
			groupId, originalFragmentEntryLinkId, fragmentEntryId,
			segmentsExperienceId, classNameId, classPK, css, html, js,
			configuration, editableValues, namespace, position, rendererKey,
			serviceContext);
	}

	public static FragmentEntryLink addFragmentEntryLink(
			long groupId, long originalFragmentEntryLinkId,
			long fragmentEntryId, long segmentsExperienceId, long plid,
			String css, String html, String js, String configuration,
			String editableValues, String namespace, int position,
			String rendererKey,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addFragmentEntryLink(
			groupId, originalFragmentEntryLinkId, fragmentEntryId,
			segmentsExperienceId, plid, css, html, js, configuration,
			editableValues, namespace, position, rendererKey, serviceContext);
	}

	public static FragmentEntryLink deleteFragmentEntryLink(
			long fragmentEntryLinkId)
		throws PortalException {

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

	public static FragmentEntryLink updateFragmentEntryLink(
			long fragmentEntryLinkId, String editableValues)
		throws PortalException {

		return getService().updateFragmentEntryLink(
			fragmentEntryLinkId, editableValues);
	}

	public static FragmentEntryLink updateFragmentEntryLink(
			long fragmentEntryLinkId, String editableValues,
			boolean updateClassedModel)
		throws PortalException {

		return getService().updateFragmentEntryLink(
			fragmentEntryLinkId, editableValues, updateClassedModel);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #updateFragmentEntryLinks(long, long, long[], String,
	 ServiceContext)}
	 */
	@Deprecated
	public static void updateFragmentEntryLinks(
			long groupId, long classNameId, long classPK,
			long[] fragmentEntryIds, String editableValues,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		getService().updateFragmentEntryLinks(
			groupId, classNameId, classPK, fragmentEntryIds, editableValues,
			serviceContext);
	}

	public static void updateFragmentEntryLinks(
			long groupId, long plid, long[] fragmentEntryIds,
			String editableValues,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		getService().updateFragmentEntryLinks(
			groupId, plid, fragmentEntryIds, editableValues, serviceContext);
	}

	public static void updateFragmentEntryLinks(
			Map<Long, String> fragmentEntryLinksEditableValuesMap)
		throws PortalException {

		getService().updateFragmentEntryLinks(
			fragmentEntryLinksEditableValuesMap);
	}

	public static FragmentEntryLinkService getService() {
		return _service;
	}

	private static volatile FragmentEntryLinkService _service;

}