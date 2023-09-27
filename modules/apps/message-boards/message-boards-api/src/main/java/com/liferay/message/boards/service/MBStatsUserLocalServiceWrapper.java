/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.service;

import com.liferay.message.boards.model.MBStatsUser;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link MBStatsUserLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see MBStatsUserLocalService
 * @generated
 */
public class MBStatsUserLocalServiceWrapper
	implements MBStatsUserLocalService,
			   ServiceWrapper<MBStatsUserLocalService> {

	public MBStatsUserLocalServiceWrapper() {
		this(null);
	}

	public MBStatsUserLocalServiceWrapper(
		MBStatsUserLocalService mbStatsUserLocalService) {

		_mbStatsUserLocalService = mbStatsUserLocalService;
	}

	@Override
	public java.util.Date getLastPostDateByUserId(long groupId, long userId) {
		return _mbStatsUserLocalService.getLastPostDateByUserId(
			groupId, userId);
	}

	@Override
	public int getMessageCount(long groupId, long userId) {
		return _mbStatsUserLocalService.getMessageCount(groupId, userId);
	}

	@Override
	public long getMessageCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbStatsUserLocalService.getMessageCountByGroupId(groupId);
	}

	@Override
	public long getMessageCountByUserId(long userId) {
		return _mbStatsUserLocalService.getMessageCountByUserId(userId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _mbStatsUserLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<MBStatsUser> getStatsUsersByGroupId(
			long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbStatsUserLocalService.getStatsUsersByGroupId(
			groupId, start, end);
	}

	@Override
	public int getStatsUsersByGroupIdCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbStatsUserLocalService.getStatsUsersByGroupIdCount(groupId);
	}

	@Override
	public String[] getUserRank(long groupId, String languageId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mbStatsUserLocalService.getUserRank(
			groupId, languageId, userId);
	}

	@Override
	public MBStatsUserLocalService getWrappedService() {
		return _mbStatsUserLocalService;
	}

	@Override
	public void setWrappedService(
		MBStatsUserLocalService mbStatsUserLocalService) {

		_mbStatsUserLocalService = mbStatsUserLocalService;
	}

	private MBStatsUserLocalService _mbStatsUserLocalService;

}