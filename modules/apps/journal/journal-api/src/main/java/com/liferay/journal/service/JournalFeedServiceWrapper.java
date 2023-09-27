/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.service;

import com.liferay.journal.model.JournalFeed;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link JournalFeedService}.
 *
 * @author Brian Wing Shun Chan
 * @see JournalFeedService
 * @generated
 */
public class JournalFeedServiceWrapper
	implements JournalFeedService, ServiceWrapper<JournalFeedService> {

	public JournalFeedServiceWrapper() {
		this(null);
	}

	public JournalFeedServiceWrapper(JournalFeedService journalFeedService) {
		_journalFeedService = journalFeedService;
	}

	@Override
	public JournalFeed addFeed(
			long groupId, String feedId, boolean autoFeedId, String name,
			String description, long ddmStructureId, String ddmTemplateKey,
			String ddmRendererTemplateKey, int delta, String orderByCol,
			String orderByType, String targetLayoutFriendlyUrl,
			String targetPortletId, String contentField, String feedType,
			double feedVersion,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _journalFeedService.addFeed(
			groupId, feedId, autoFeedId, name, description, ddmStructureId,
			ddmTemplateKey, ddmRendererTemplateKey, delta, orderByCol,
			orderByType, targetLayoutFriendlyUrl, targetPortletId, contentField,
			feedType, feedVersion, serviceContext);
	}

	@Override
	public void deleteFeed(long feedId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_journalFeedService.deleteFeed(feedId);
	}

	@Override
	public void deleteFeed(long groupId, String feedId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_journalFeedService.deleteFeed(groupId, feedId);
	}

	@Override
	public JournalFeed getFeed(long feedId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _journalFeedService.getFeed(feedId);
	}

	@Override
	public JournalFeed getFeed(long groupId, String feedId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _journalFeedService.getFeed(groupId, feedId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _journalFeedService.getOSGiServiceIdentifier();
	}

	@Override
	public JournalFeed updateFeed(
			long groupId, String feedId, String name, String description,
			long ddmStructureId, String ddmTemplateKey,
			String ddmRendererTemplateKey, int delta, String orderByCol,
			String orderByType, String targetLayoutFriendlyUrl,
			String targetPortletId, String contentField, String feedType,
			double feedVersion,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _journalFeedService.updateFeed(
			groupId, feedId, name, description, ddmStructureId, ddmTemplateKey,
			ddmRendererTemplateKey, delta, orderByCol, orderByType,
			targetLayoutFriendlyUrl, targetPortletId, contentField, feedType,
			feedVersion, serviceContext);
	}

	@Override
	public JournalFeedService getWrappedService() {
		return _journalFeedService;
	}

	@Override
	public void setWrappedService(JournalFeedService journalFeedService) {
		_journalFeedService = journalFeedService;
	}

	private JournalFeedService _journalFeedService;

}