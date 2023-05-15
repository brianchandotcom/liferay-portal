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

import com.liferay.microblogs.model.MicroblogsEntry;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the remote service utility for MicroblogsEntry. This utility wraps
 * <code>com.liferay.microblogs.service.impl.MicroblogsEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see MicroblogsEntryService
 * @generated
 */
public class MicroblogsEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.microblogs.service.impl.MicroblogsEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static MicroblogsEntry addMicroblogsEntry(
			long userId, String content, int type, long parentMicroblogsEntryId,
			int socialRelationType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addMicroblogsEntry(
			userId, content, type, parentMicroblogsEntryId, socialRelationType,
			serviceContext);
	}

	public static MicroblogsEntry deleteMicroblogsEntry(long microblogsEntryId)
		throws PortalException {

		return getService().deleteMicroblogsEntry(microblogsEntryId);
	}

	public static List<MicroblogsEntry> getMicroblogsEntries(int start, int end)
		throws PortalException {

		return getService().getMicroblogsEntries(start, end);
	}

	public static List<MicroblogsEntry> getMicroblogsEntries(
			String assetTagName, int start, int end)
		throws PortalException {

		return getService().getMicroblogsEntries(assetTagName, start, end);
	}

	public static int getMicroblogsEntriesCount() throws PortalException {
		return getService().getMicroblogsEntriesCount();
	}

	public static int getMicroblogsEntriesCount(String assetTagName)
		throws PortalException {

		return getService().getMicroblogsEntriesCount(assetTagName);
	}

	public static MicroblogsEntry getMicroblogsEntry(long microblogsEntryId)
		throws PortalException {

		return getService().getMicroblogsEntry(microblogsEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static List<MicroblogsEntry> getUserMicroblogsEntries(
			long microblogsEntryUserId, int start, int end)
		throws PortalException {

		return getService().getUserMicroblogsEntries(
			microblogsEntryUserId, start, end);
	}

	public static List<MicroblogsEntry> getUserMicroblogsEntries(
			long microblogsEntryUserId, int type, int start, int end)
		throws PortalException {

		return getService().getUserMicroblogsEntries(
			microblogsEntryUserId, type, start, end);
	}

	public static int getUserMicroblogsEntriesCount(long microblogsEntryUserId)
		throws PortalException {

		return getService().getUserMicroblogsEntriesCount(
			microblogsEntryUserId);
	}

	public static int getUserMicroblogsEntriesCount(
			long microblogsEntryUserId, int type)
		throws PortalException {

		return getService().getUserMicroblogsEntriesCount(
			microblogsEntryUserId, type);
	}

	public static MicroblogsEntry updateMicroblogsEntry(
			long microblogsEntryId, String content, int socialRelationType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateMicroblogsEntry(
			microblogsEntryId, content, socialRelationType, serviceContext);
	}

	public static MicroblogsEntryService getService() {
		return _service;
	}

	public static void setService(MicroblogsEntryService service) {
		_service = service;
	}

	private static volatile MicroblogsEntryService _service;

}