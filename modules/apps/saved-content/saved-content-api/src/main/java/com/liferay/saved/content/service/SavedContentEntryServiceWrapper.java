/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saved.content.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.saved.content.model.SavedContentEntry;

/**
 * Provides a wrapper for {@link SavedContentEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see SavedContentEntryService
 * @generated
 */
public class SavedContentEntryServiceWrapper
	implements SavedContentEntryService,
			   ServiceWrapper<SavedContentEntryService> {

	public SavedContentEntryServiceWrapper() {
		this(null);
	}

	public SavedContentEntryServiceWrapper(
		SavedContentEntryService savedContentEntryService) {

		_savedContentEntryService = savedContentEntryService;
	}

	@Override
	public SavedContentEntry addSavedContentEntry(
			long groupId, String className, long classPK,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _savedContentEntryService.addSavedContentEntry(
			groupId, className, classPK, serviceContext);
	}

	@Override
	public void deleteSavedContentEntry(SavedContentEntry savedContentEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		_savedContentEntryService.deleteSavedContentEntry(savedContentEntry);
	}

	@Override
	public SavedContentEntry fetchSavedContentEntry(
			long groupId, String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _savedContentEntryService.fetchSavedContentEntry(
			groupId, className, classPK);
	}

	@Override
	public java.util.List<SavedContentEntry> getGroupUserSavedContentEntries(
			long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _savedContentEntryService.getGroupUserSavedContentEntries(
			groupId, start, end);
	}

	@Override
	public java.util.List<SavedContentEntry> getGroupUserSavedContentEntries(
			long groupId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<SavedContentEntry>
				orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _savedContentEntryService.getGroupUserSavedContentEntries(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getGroupUserSavedContentEntriesCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _savedContentEntryService.getGroupUserSavedContentEntriesCount(
			groupId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _savedContentEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public SavedContentEntry getSavedContentEntry(
			long groupId, String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _savedContentEntryService.getSavedContentEntry(
			groupId, className, classPK);
	}

	@Override
	public SavedContentEntryService getWrappedService() {
		return _savedContentEntryService;
	}

	@Override
	public void setWrappedService(
		SavedContentEntryService savedContentEntryService) {

		_savedContentEntryService = savedContentEntryService;
	}

	private SavedContentEntryService _savedContentEntryService;

}