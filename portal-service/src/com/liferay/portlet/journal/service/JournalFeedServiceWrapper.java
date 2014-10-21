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

package com.liferay.portlet.journal.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link JournalFeedService}.
 *
 * @author Brian Wing Shun Chan
 * @see JournalFeedService
 * @generated
 */
@ProviderType
public class JournalFeedServiceWrapper implements JournalFeedService,
	ServiceWrapper<JournalFeedService> {
	public JournalFeedServiceWrapper(JournalFeedService journalFeedService) {
		_journalFeedService = journalFeedService;
	}

	@Override
	public com.liferay.portlet.journal.model.JournalFeed addFeed(long groupId,
		java.lang.String feedId, boolean autoFeedId, java.lang.String name,
		java.lang.String description, java.lang.String ddmStructureKey,
		java.lang.String ddmTemplateKey,
		java.lang.String ddmRendererTemplateKey, int delta,
		java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedType, double feedVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _journalFeedService.addFeed(groupId, feedId, autoFeedId, name,
			description, ddmStructureKey, ddmTemplateKey,
			ddmRendererTemplateKey, delta, orderByCol, orderByType,
			targetLayoutFriendlyUrl, targetPortletId, contentField, feedType,
			feedVersion, serviceContext);
	}

	@Override
	public void deleteFeed(long feedId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_journalFeedService.deleteFeed(feedId);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #deleteFeed(long, String)}
	*/
	@Deprecated
	@Override
	public void deleteFeed(long groupId, long feedId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_journalFeedService.deleteFeed(groupId, feedId);
	}

	@Override
	public void deleteFeed(long groupId, java.lang.String feedId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_journalFeedService.deleteFeed(groupId, feedId);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _journalFeedService.getBeanIdentifier();
	}

	@Override
	public com.liferay.portlet.journal.model.JournalFeed getFeed(long feedId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _journalFeedService.getFeed(feedId);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getFeed(long, String)}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.journal.model.JournalFeed getFeed(long groupId,
		long feedId) throws com.liferay.portal.kernel.exception.PortalException {
		return _journalFeedService.getFeed(groupId, feedId);
	}

	@Override
	public com.liferay.portlet.journal.model.JournalFeed getFeed(long groupId,
		java.lang.String feedId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _journalFeedService.getFeed(groupId, feedId);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_journalFeedService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public com.liferay.portlet.journal.model.JournalFeed updateFeed(
		long groupId, java.lang.String feedId, java.lang.String name,
		java.lang.String description, java.lang.String ddmStructureKey,
		java.lang.String ddmTemplateKey,
		java.lang.String ddmRendererTemplateKey, int delta,
		java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedType, double feedVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _journalFeedService.updateFeed(groupId, feedId, name,
			description, ddmStructureKey, ddmTemplateKey,
			ddmRendererTemplateKey, delta, orderByCol, orderByType,
			targetLayoutFriendlyUrl, targetPortletId, contentField, feedType,
			feedVersion, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public JournalFeedService getWrappedJournalFeedService() {
		return _journalFeedService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedJournalFeedService(
		JournalFeedService journalFeedService) {
		_journalFeedService = journalFeedService;
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