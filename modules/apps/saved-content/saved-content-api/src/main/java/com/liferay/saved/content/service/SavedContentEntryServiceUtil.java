/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saved.content.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.saved.content.model.SavedContentEntry;

import java.util.List;

/**
 * Provides the remote service utility for SavedContentEntry. This utility wraps
 * <code>com.liferay.saved.content.service.impl.SavedContentEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see SavedContentEntryService
 * @generated
 */
public class SavedContentEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.saved.content.service.impl.SavedContentEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static SavedContentEntry addSavedContentEntry(
			long groupId, String className, long classPK,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addSavedContentEntry(
			groupId, className, classPK, serviceContext);
	}

	public static void deleteSavedContentEntry(
			SavedContentEntry savedContentEntry)
		throws PortalException {

		getService().deleteSavedContentEntry(savedContentEntry);
	}

	public static SavedContentEntry fetchSavedContentEntry(
			long groupId, String className, long classPK)
		throws PortalException {

		return getService().fetchSavedContentEntry(groupId, className, classPK);
	}

	public static List<SavedContentEntry> getGroupUserSavedContentEntries(
			long groupId, int start, int end)
		throws PortalException {

		return getService().getGroupUserSavedContentEntries(
			groupId, start, end);
	}

	public static List<SavedContentEntry> getGroupUserSavedContentEntries(
			long groupId, int start, int end,
			OrderByComparator<SavedContentEntry> orderByComparator)
		throws PortalException {

		return getService().getGroupUserSavedContentEntries(
			groupId, start, end, orderByComparator);
	}

	public static int getGroupUserSavedContentEntriesCount(long groupId)
		throws PortalException {

		return getService().getGroupUserSavedContentEntriesCount(groupId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static SavedContentEntry getSavedContentEntry(
			long groupId, String className, long classPK)
		throws PortalException {

		return getService().getSavedContentEntry(groupId, className, classPK);
	}

	public static SavedContentEntryService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<SavedContentEntryService> _serviceSnapshot =
		new Snapshot<>(
			SavedContentEntryServiceUtil.class, SavedContentEntryService.class);

}