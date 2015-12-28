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

package com.liferay.microblogs.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for MicroblogsEntry. This utility wraps
 * {@link com.liferay.microblogs.service.impl.MicroblogsEntryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see MicroblogsEntryService
 * @see com.liferay.microblogs.service.base.MicroblogsEntryServiceBaseImpl
 * @see com.liferay.microblogs.service.impl.MicroblogsEntryServiceImpl
 * @generated
 */
@ProviderType
public class MicroblogsEntryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.microblogs.service.impl.MicroblogsEntryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.microblogs.model.MicroblogsEntry addMicroblogsEntry(
		long userId, java.lang.String content, int type,
		long parentMicroblogsEntryId, int socialRelationType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addMicroblogsEntry(userId, content, type,
			parentMicroblogsEntryId, socialRelationType, serviceContext);
	}

	public static com.liferay.microblogs.model.MicroblogsEntry deleteMicroblogsEntry(
		long microblogsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteMicroblogsEntry(microblogsEntryId);
	}

	public static java.util.List<com.liferay.microblogs.model.MicroblogsEntry> getMicroblogsEntries(
		java.lang.String assetTagName, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getMicroblogsEntries(assetTagName, start, end);
	}

	public static java.util.List<com.liferay.microblogs.model.MicroblogsEntry> getMicroblogsEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getMicroblogsEntries(start, end);
	}

	public static int getMicroblogsEntriesCount()
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getMicroblogsEntriesCount();
	}

	public static int getMicroblogsEntriesCount(java.lang.String assetTagName)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getMicroblogsEntriesCount(assetTagName);
	}

	public static com.liferay.microblogs.model.MicroblogsEntry getMicroblogsEntry(
		long microblogsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getMicroblogsEntry(microblogsEntryId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.microblogs.model.MicroblogsEntry> getUserMicroblogsEntries(
		long microblogsEntryUserId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getUserMicroblogsEntries(microblogsEntryUserId, start, end);
	}

	public static java.util.List<com.liferay.microblogs.model.MicroblogsEntry> getUserMicroblogsEntries(
		long microblogsEntryUserId, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getUserMicroblogsEntries(microblogsEntryUserId, type,
			start, end);
	}

	public static int getUserMicroblogsEntriesCount(long microblogsEntryUserId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getUserMicroblogsEntriesCount(microblogsEntryUserId);
	}

	public static int getUserMicroblogsEntriesCount(
		long microblogsEntryUserId, int type)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getUserMicroblogsEntriesCount(microblogsEntryUserId, type);
	}

	public static com.liferay.microblogs.model.MicroblogsEntry updateMicroblogsEntry(
		long microblogsEntryId, java.lang.String content,
		int socialRelationType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateMicroblogsEntry(microblogsEntryId, content,
			socialRelationType, serviceContext);
	}

	public static MicroblogsEntryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<MicroblogsEntryService, MicroblogsEntryService> _serviceTracker =
		ServiceTrackerFactory.open(MicroblogsEntryService.class);
}