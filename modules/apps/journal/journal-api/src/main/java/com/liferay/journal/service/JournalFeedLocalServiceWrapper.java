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

package com.liferay.journal.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link JournalFeedLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see JournalFeedLocalService
 * @generated
 */
public class JournalFeedLocalServiceWrapper
	implements JournalFeedLocalService,
			   ServiceWrapper<JournalFeedLocalService> {

	public JournalFeedLocalServiceWrapper(
		JournalFeedLocalService journalFeedLocalService) {

		_journalFeedLocalService = journalFeedLocalService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link JournalFeedLocalServiceUtil} to access the journal feed local service. Add custom service methods to <code>com.liferay.journal.service.impl.JournalFeedLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.journal.model.JournalFeed addFeed(
			long userId, long groupId, String feedId, boolean autoFeedId,
			String name, String description, String ddmStructureKey,
			String ddmTemplateKey, String ddmRendererTemplateKey, int delta,
			String orderByCol, String orderByType,
			String targetLayoutFriendlyUrl, String targetPortletId,
			String contentField, String feedFormat, double feedVersion,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _journalFeedLocalService.addFeed(
			userId, groupId, feedId, autoFeedId, name, description,
			ddmStructureKey, ddmTemplateKey, ddmRendererTemplateKey, delta,
			orderByCol, orderByType, targetLayoutFriendlyUrl, targetPortletId,
			contentField, feedFormat, feedVersion, serviceContext);
	}

	@Override
	public void addFeedResources(
			com.liferay.journal.model.JournalFeed feed,
			boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		_journalFeedLocalService.addFeedResources(
			feed, addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addFeedResources(
			com.liferay.journal.model.JournalFeed feed,
			com.liferay.portal.kernel.service.permission.ModelPermissions
				modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		_journalFeedLocalService.addFeedResources(feed, modelPermissions);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 #addFeedResources(JournalFeed, ModelPermissions)}
	 */
	@Deprecated
	@Override
	public void addFeedResources(
			com.liferay.journal.model.JournalFeed feed,
			String[] groupPermissions, String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		_journalFeedLocalService.addFeedResources(
			feed, groupPermissions, guestPermissions);
	}

	@Override
	public void addFeedResources(
			long feedId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		_journalFeedLocalService.addFeedResources(
			feedId, addGroupPermissions, addGuestPermissions);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 #addFeedResources(JournalFeed, ModelPermissions)}
	 */
	@Deprecated
	@Override
	public void addFeedResources(
			long feedId, String[] groupPermissions, String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		_journalFeedLocalService.addFeedResources(
			feedId, groupPermissions, guestPermissions);
	}

	/**
	 * Adds the journal feed to the database. Also notifies the appropriate model listeners.
	 *
	 * @param journalFeed the journal feed
	 * @return the journal feed that was added
	 */
	@Override
	public com.liferay.journal.model.JournalFeed addJournalFeed(
		com.liferay.journal.model.JournalFeed journalFeed) {

		return _journalFeedLocalService.addJournalFeed(journalFeed);
	}

	/**
	 * Creates a new journal feed with the primary key. Does not add the journal feed to the database.
	 *
	 * @param id the primary key for the new journal feed
	 * @return the new journal feed
	 */
	@Override
	public com.liferay.journal.model.JournalFeed createJournalFeed(long id) {
		return _journalFeedLocalService.createJournalFeed(id);
	}

	@Override
	public void deleteFeed(com.liferay.journal.model.JournalFeed feed)
		throws com.liferay.portal.kernel.exception.PortalException {

		_journalFeedLocalService.deleteFeed(feed);
	}

	@Override
	public void deleteFeed(long feedId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_journalFeedLocalService.deleteFeed(feedId);
	}

	@Override
	public void deleteFeed(long groupId, String feedId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_journalFeedLocalService.deleteFeed(groupId, feedId);
	}

	/**
	 * Deletes the journal feed from the database. Also notifies the appropriate model listeners.
	 *
	 * @param journalFeed the journal feed
	 * @return the journal feed that was removed
	 */
	@Override
	public com.liferay.journal.model.JournalFeed deleteJournalFeed(
		com.liferay.journal.model.JournalFeed journalFeed) {

		return _journalFeedLocalService.deleteJournalFeed(journalFeed);
	}

	/**
	 * Deletes the journal feed with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param id the primary key of the journal feed
	 * @return the journal feed that was removed
	 * @throws PortalException if a journal feed with the primary key could not be found
	 */
	@Override
	public com.liferay.journal.model.JournalFeed deleteJournalFeed(long id)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _journalFeedLocalService.deleteJournalFeed(id);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _journalFeedLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _journalFeedLocalService.dynamicQuery();
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

		return _journalFeedLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.journal.model.impl.JournalFeedModelImpl</code>.
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

		return _journalFeedLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.journal.model.impl.JournalFeedModelImpl</code>.
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

		return _journalFeedLocalService.dynamicQuery(
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

		return _journalFeedLocalService.dynamicQueryCount(dynamicQuery);
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

		return _journalFeedLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.journal.model.JournalFeed fetchFeed(
		long groupId, String feedId) {

		return _journalFeedLocalService.fetchFeed(groupId, feedId);
	}

	@Override
	public com.liferay.journal.model.JournalFeed fetchJournalFeed(long id) {
		return _journalFeedLocalService.fetchJournalFeed(id);
	}

	/**
	 * Returns the journal feed matching the UUID and group.
	 *
	 * @param uuid the journal feed's UUID
	 * @param groupId the primary key of the group
	 * @return the matching journal feed, or <code>null</code> if a matching journal feed could not be found
	 */
	@Override
	public com.liferay.journal.model.JournalFeed
		fetchJournalFeedByUuidAndGroupId(String uuid, long groupId) {

		return _journalFeedLocalService.fetchJournalFeedByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _journalFeedLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _journalFeedLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.journal.model.JournalFeed getFeed(long feedId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _journalFeedLocalService.getFeed(feedId);
	}

	@Override
	public com.liferay.journal.model.JournalFeed getFeed(
			long groupId, String feedId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _journalFeedLocalService.getFeed(groupId, feedId);
	}

	@Override
	public java.util.List<com.liferay.journal.model.JournalFeed> getFeeds() {
		return _journalFeedLocalService.getFeeds();
	}

	@Override
	public java.util.List<com.liferay.journal.model.JournalFeed> getFeeds(
		long groupId) {

		return _journalFeedLocalService.getFeeds(groupId);
	}

	@Override
	public java.util.List<com.liferay.journal.model.JournalFeed> getFeeds(
		long groupId, int start, int end) {

		return _journalFeedLocalService.getFeeds(groupId, start, end);
	}

	@Override
	public int getFeedsCount(long groupId) {
		return _journalFeedLocalService.getFeedsCount(groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _journalFeedLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the journal feed with the primary key.
	 *
	 * @param id the primary key of the journal feed
	 * @return the journal feed
	 * @throws PortalException if a journal feed with the primary key could not be found
	 */
	@Override
	public com.liferay.journal.model.JournalFeed getJournalFeed(long id)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _journalFeedLocalService.getJournalFeed(id);
	}

	/**
	 * Returns the journal feed matching the UUID and group.
	 *
	 * @param uuid the journal feed's UUID
	 * @param groupId the primary key of the group
	 * @return the matching journal feed
	 * @throws PortalException if a matching journal feed could not be found
	 */
	@Override
	public com.liferay.journal.model.JournalFeed getJournalFeedByUuidAndGroupId(
			String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _journalFeedLocalService.getJournalFeedByUuidAndGroupId(
			uuid, groupId);
	}

	/**
	 * Returns a range of all the journal feeds.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.journal.model.impl.JournalFeedModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal feeds
	 * @param end the upper bound of the range of journal feeds (not inclusive)
	 * @return the range of journal feeds
	 */
	@Override
	public java.util.List<com.liferay.journal.model.JournalFeed>
		getJournalFeeds(int start, int end) {

		return _journalFeedLocalService.getJournalFeeds(start, end);
	}

	/**
	 * Returns all the journal feeds matching the UUID and company.
	 *
	 * @param uuid the UUID of the journal feeds
	 * @param companyId the primary key of the company
	 * @return the matching journal feeds, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.journal.model.JournalFeed>
		getJournalFeedsByUuidAndCompanyId(String uuid, long companyId) {

		return _journalFeedLocalService.getJournalFeedsByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of journal feeds matching the UUID and company.
	 *
	 * @param uuid the UUID of the journal feeds
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of journal feeds
	 * @param end the upper bound of the range of journal feeds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching journal feeds, or an empty list if no matches were found
	 */
	@Override
	public java.util.List<com.liferay.journal.model.JournalFeed>
		getJournalFeedsByUuidAndCompanyId(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.journal.model.JournalFeed> orderByComparator) {

		return _journalFeedLocalService.getJournalFeedsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of journal feeds.
	 *
	 * @return the number of journal feeds
	 */
	@Override
	public int getJournalFeedsCount() {
		return _journalFeedLocalService.getJournalFeedsCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _journalFeedLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _journalFeedLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.util.List<com.liferay.journal.model.JournalFeed> search(
		long companyId, long groupId, String keywords, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.journal.model.JournalFeed> obc) {

		return _journalFeedLocalService.search(
			companyId, groupId, keywords, start, end, obc);
	}

	@Override
	public java.util.List<com.liferay.journal.model.JournalFeed> search(
		long companyId, long groupId, String feedId, String name,
		String description, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.journal.model.JournalFeed> obc) {

		return _journalFeedLocalService.search(
			companyId, groupId, feedId, name, description, andOperator, start,
			end, obc);
	}

	@Override
	public int searchCount(long companyId, long groupId, String keywords) {
		return _journalFeedLocalService.searchCount(
			companyId, groupId, keywords);
	}

	@Override
	public int searchCount(
		long companyId, long groupId, String feedId, String name,
		String description, boolean andOperator) {

		return _journalFeedLocalService.searchCount(
			companyId, groupId, feedId, name, description, andOperator);
	}

	@Override
	public com.liferay.journal.model.JournalFeed updateFeed(
			long groupId, String feedId, String name, String description,
			String ddmStructureKey, String ddmTemplateKey,
			String ddmRendererTemplateKey, int delta, String orderByCol,
			String orderByType, String targetLayoutFriendlyUrl,
			String targetPortletId, String contentField, String feedFormat,
			double feedVersion,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _journalFeedLocalService.updateFeed(
			groupId, feedId, name, description, ddmStructureKey, ddmTemplateKey,
			ddmRendererTemplateKey, delta, orderByCol, orderByType,
			targetLayoutFriendlyUrl, targetPortletId, contentField, feedFormat,
			feedVersion, serviceContext);
	}

	/**
	 * Updates the journal feed in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param journalFeed the journal feed
	 * @return the journal feed that was updated
	 */
	@Override
	public com.liferay.journal.model.JournalFeed updateJournalFeed(
		com.liferay.journal.model.JournalFeed journalFeed) {

		return _journalFeedLocalService.updateJournalFeed(journalFeed);
	}

	@Override
	public JournalFeedLocalService getWrappedService() {
		return _journalFeedLocalService;
	}

	@Override
	public void setWrappedService(
		JournalFeedLocalService journalFeedLocalService) {

		_journalFeedLocalService = journalFeedLocalService;
	}

	private JournalFeedLocalService _journalFeedLocalService;

}