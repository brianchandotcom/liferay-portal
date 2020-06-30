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

package com.liferay.analytics.message.storage.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AnalyticsMessageLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AnalyticsMessageLocalService
 * @generated
 */
public class AnalyticsMessageLocalServiceWrapper
	implements AnalyticsMessageLocalService,
			   ServiceWrapper<AnalyticsMessageLocalService> {

	public AnalyticsMessageLocalServiceWrapper(
		AnalyticsMessageLocalService analyticsMessageLocalService) {

		_analyticsMessageLocalService = analyticsMessageLocalService;
	}

	/**
	 * Adds the analytics message to the database. Also notifies the appropriate model listeners.
	 *
	 * @param analyticsMessage the analytics message
	 * @return the analytics message that was added
	 */
	@Override
	public com.liferay.analytics.message.storage.model.AnalyticsMessage
		addAnalyticsMessage(
			com.liferay.analytics.message.storage.model.AnalyticsMessage
				analyticsMessage) {

		return _analyticsMessageLocalService.addAnalyticsMessage(
			analyticsMessage);
	}

	@Override
	public com.liferay.analytics.message.storage.model.AnalyticsMessage
			addAnalyticsMessage(long companyId, long userId, byte[] body)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _analyticsMessageLocalService.addAnalyticsMessage(
			companyId, userId, body);
	}

	/**
	 * Creates a new analytics message with the primary key. Does not add the analytics message to the database.
	 *
	 * @param analyticsMessageId the primary key for the new analytics message
	 * @return the new analytics message
	 */
	@Override
	public com.liferay.analytics.message.storage.model.AnalyticsMessage
		createAnalyticsMessage(long analyticsMessageId) {

		return _analyticsMessageLocalService.createAnalyticsMessage(
			analyticsMessageId);
	}

	/**
	 * Deletes the analytics message from the database. Also notifies the appropriate model listeners.
	 *
	 * @param analyticsMessage the analytics message
	 * @return the analytics message that was removed
	 */
	@Override
	public com.liferay.analytics.message.storage.model.AnalyticsMessage
		deleteAnalyticsMessage(
			com.liferay.analytics.message.storage.model.AnalyticsMessage
				analyticsMessage) {

		return _analyticsMessageLocalService.deleteAnalyticsMessage(
			analyticsMessage);
	}

	/**
	 * Deletes the analytics message with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param analyticsMessageId the primary key of the analytics message
	 * @return the analytics message that was removed
	 * @throws PortalException if a analytics message with the primary key could not be found
	 */
	@Override
	public com.liferay.analytics.message.storage.model.AnalyticsMessage
			deleteAnalyticsMessage(long analyticsMessageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _analyticsMessageLocalService.deleteAnalyticsMessage(
			analyticsMessageId);
	}

	@Override
	public void deleteAnalyticsMessages(
		java.util.List
			<com.liferay.analytics.message.storage.model.AnalyticsMessage>
				analyticsMessages) {

		_analyticsMessageLocalService.deleteAnalyticsMessages(
			analyticsMessages);
	}

	@Override
	public void deleteAnalyticsMessages(long companyId) {
		_analyticsMessageLocalService.deleteAnalyticsMessages(companyId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _analyticsMessageLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _analyticsMessageLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _analyticsMessageLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.analytics.message.storage.model.impl.AnalyticsMessageModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _analyticsMessageLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.analytics.message.storage.model.impl.AnalyticsMessageModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _analyticsMessageLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _analyticsMessageLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _analyticsMessageLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.analytics.message.storage.model.AnalyticsMessage
		fetchAnalyticsMessage(long analyticsMessageId) {

		return _analyticsMessageLocalService.fetchAnalyticsMessage(
			analyticsMessageId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _analyticsMessageLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the analytics message with the primary key.
	 *
	 * @param analyticsMessageId the primary key of the analytics message
	 * @return the analytics message
	 * @throws PortalException if a analytics message with the primary key could not be found
	 */
	@Override
	public com.liferay.analytics.message.storage.model.AnalyticsMessage
			getAnalyticsMessage(long analyticsMessageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _analyticsMessageLocalService.getAnalyticsMessage(
			analyticsMessageId);
	}

	/**
	 * Returns a range of all the analytics messages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.analytics.message.storage.model.impl.AnalyticsMessageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics messages
	 * @param end the upper bound of the range of analytics messages (not inclusive)
	 * @return the range of analytics messages
	 */
	@Override
	public java.util.List
		<com.liferay.analytics.message.storage.model.AnalyticsMessage>
			getAnalyticsMessages(int start, int end) {

		return _analyticsMessageLocalService.getAnalyticsMessages(start, end);
	}

	@Override
	public java.util.List
		<com.liferay.analytics.message.storage.model.AnalyticsMessage>
			getAnalyticsMessages(long companyId, int start, int end) {

		return _analyticsMessageLocalService.getAnalyticsMessages(
			companyId, start, end);
	}

	/**
	 * Returns the number of analytics messages.
	 *
	 * @return the number of analytics messages
	 */
	@Override
	public int getAnalyticsMessagesCount() {
		return _analyticsMessageLocalService.getAnalyticsMessagesCount();
	}

	@Override
	public
		com.liferay.analytics.message.storage.model.
			AnalyticsMessageBodyBlobModel getBodyBlobModel(
				java.io.Serializable primaryKey) {

		return _analyticsMessageLocalService.getBodyBlobModel(primaryKey);
	}

	@Override
	public java.util.List<Long> getCompanyIds() {
		return _analyticsMessageLocalService.getCompanyIds();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _analyticsMessageLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _analyticsMessageLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _analyticsMessageLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.io.InputStream openBodyInputStream(long analyticsMessageId) {
		return _analyticsMessageLocalService.openBodyInputStream(
			analyticsMessageId);
	}

	/**
	 * Updates the analytics message in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param analyticsMessage the analytics message
	 * @return the analytics message that was updated
	 */
	@Override
	public com.liferay.analytics.message.storage.model.AnalyticsMessage
		updateAnalyticsMessage(
			com.liferay.analytics.message.storage.model.AnalyticsMessage
				analyticsMessage) {

		return _analyticsMessageLocalService.updateAnalyticsMessage(
			analyticsMessage);
	}

	@Override
	public AnalyticsMessageLocalService getWrappedService() {
		return _analyticsMessageLocalService;
	}

	@Override
	public void setWrappedService(
		AnalyticsMessageLocalService analyticsMessageLocalService) {

		_analyticsMessageLocalService = analyticsMessageLocalService;
	}

	private AnalyticsMessageLocalService _analyticsMessageLocalService;

}