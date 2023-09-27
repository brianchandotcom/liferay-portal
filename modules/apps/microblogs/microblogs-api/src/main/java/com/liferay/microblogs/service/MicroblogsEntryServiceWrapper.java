/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.microblogs.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link MicroblogsEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see MicroblogsEntryService
 * @generated
 */
public class MicroblogsEntryServiceWrapper
	implements MicroblogsEntryService, ServiceWrapper<MicroblogsEntryService> {

	public MicroblogsEntryServiceWrapper() {
		this(null);
	}

	public MicroblogsEntryServiceWrapper(
		MicroblogsEntryService microblogsEntryService) {

		_microblogsEntryService = microblogsEntryService;
	}

	@Override
	public com.liferay.microblogs.model.MicroblogsEntry addMicroblogsEntry(
			long userId, String content, int type, long parentMicroblogsEntryId,
			int socialRelationType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _microblogsEntryService.addMicroblogsEntry(
			userId, content, type, parentMicroblogsEntryId, socialRelationType,
			serviceContext);
	}

	@Override
	public com.liferay.microblogs.model.MicroblogsEntry deleteMicroblogsEntry(
			long microblogsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _microblogsEntryService.deleteMicroblogsEntry(microblogsEntryId);
	}

	@Override
	public java.util.List<com.liferay.microblogs.model.MicroblogsEntry>
			getMicroblogsEntries(int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _microblogsEntryService.getMicroblogsEntries(start, end);
	}

	@Override
	public java.util.List<com.liferay.microblogs.model.MicroblogsEntry>
			getMicroblogsEntries(String assetTagName, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _microblogsEntryService.getMicroblogsEntries(
			assetTagName, start, end);
	}

	@Override
	public int getMicroblogsEntriesCount()
		throws com.liferay.portal.kernel.exception.PortalException {

		return _microblogsEntryService.getMicroblogsEntriesCount();
	}

	@Override
	public int getMicroblogsEntriesCount(String assetTagName)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _microblogsEntryService.getMicroblogsEntriesCount(assetTagName);
	}

	@Override
	public com.liferay.microblogs.model.MicroblogsEntry getMicroblogsEntry(
			long microblogsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _microblogsEntryService.getMicroblogsEntry(microblogsEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _microblogsEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.microblogs.model.MicroblogsEntry>
			getUserMicroblogsEntries(
				long microblogsEntryUserId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _microblogsEntryService.getUserMicroblogsEntries(
			microblogsEntryUserId, start, end);
	}

	@Override
	public java.util.List<com.liferay.microblogs.model.MicroblogsEntry>
			getUserMicroblogsEntries(
				long microblogsEntryUserId, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _microblogsEntryService.getUserMicroblogsEntries(
			microblogsEntryUserId, type, start, end);
	}

	@Override
	public int getUserMicroblogsEntriesCount(long microblogsEntryUserId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _microblogsEntryService.getUserMicroblogsEntriesCount(
			microblogsEntryUserId);
	}

	@Override
	public int getUserMicroblogsEntriesCount(
			long microblogsEntryUserId, int type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _microblogsEntryService.getUserMicroblogsEntriesCount(
			microblogsEntryUserId, type);
	}

	@Override
	public com.liferay.microblogs.model.MicroblogsEntry updateMicroblogsEntry(
			long microblogsEntryId, String content, int socialRelationType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _microblogsEntryService.updateMicroblogsEntry(
			microblogsEntryId, content, socialRelationType, serviceContext);
	}

	@Override
	public MicroblogsEntryService getWrappedService() {
		return _microblogsEntryService;
	}

	@Override
	public void setWrappedService(
		MicroblogsEntryService microblogsEntryService) {

		_microblogsEntryService = microblogsEntryService;
	}

	private MicroblogsEntryService _microblogsEntryService;

}