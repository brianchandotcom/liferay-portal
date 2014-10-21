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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for JournalFeed. This utility wraps
 * {@link com.liferay.portlet.journal.service.impl.JournalFeedLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see JournalFeedLocalService
 * @see com.liferay.portlet.journal.service.base.JournalFeedLocalServiceBaseImpl
 * @see com.liferay.portlet.journal.service.impl.JournalFeedLocalServiceImpl
 * @generated
 */
@ProviderType
public class JournalFeedLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.journal.service.impl.JournalFeedLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portlet.journal.model.JournalFeed addFeed(
		long userId, long groupId, java.lang.String feedId, boolean autoFeedId,
		java.lang.String name, java.lang.String description,
		java.lang.String ddmStructureKey, java.lang.String ddmTemplateKey,
		java.lang.String ddmRendererTemplateKey, int delta,
		java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedFormat, double feedVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addFeed(userId, groupId, feedId, autoFeedId, name,
			description, ddmStructureKey, ddmTemplateKey,
			ddmRendererTemplateKey, delta, orderByCol, orderByType,
			targetLayoutFriendlyUrl, targetPortletId, contentField, feedFormat,
			feedVersion, serviceContext);
	}

	public static void addFeedResources(
		com.liferay.portlet.journal.model.JournalFeed feed,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addFeedResources(feed, addGroupPermissions, addGuestPermissions);
	}

	public static void addFeedResources(
		com.liferay.portlet.journal.model.JournalFeed feed,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().addFeedResources(feed, groupPermissions, guestPermissions);
	}

	public static void addFeedResources(long feedId,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addFeedResources(feedId, addGroupPermissions, addGuestPermissions);
	}

	public static void addFeedResources(long feedId,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().addFeedResources(feedId, groupPermissions, guestPermissions);
	}

	/**
	* Adds the journal feed to the database. Also notifies the appropriate model listeners.
	*
	* @param journalFeed the journal feed
	* @return the journal feed that was added
	*/
	public static com.liferay.portlet.journal.model.JournalFeed addJournalFeed(
		com.liferay.portlet.journal.model.JournalFeed journalFeed) {
		return getService().addJournalFeed(journalFeed);
	}

	/**
	* Creates a new journal feed with the primary key. Does not add the journal feed to the database.
	*
	* @param id the primary key for the new journal feed
	* @return the new journal feed
	*/
	public static com.liferay.portlet.journal.model.JournalFeed createJournalFeed(
		long id) {
		return getService().createJournalFeed(id);
	}

	public static void deleteFeed(
		com.liferay.portlet.journal.model.JournalFeed feed)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteFeed(feed);
	}

	public static void deleteFeed(long feedId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteFeed(feedId);
	}

	public static void deleteFeed(long groupId, java.lang.String feedId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteFeed(groupId, feedId);
	}

	/**
	* Deletes the journal feed with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param id the primary key of the journal feed
	* @return the journal feed that was removed
	* @throws PortalException if a journal feed with the primary key could not be found
	*/
	public static com.liferay.portlet.journal.model.JournalFeed deleteJournalFeed(
		long id) throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteJournalFeed(id);
	}

	/**
	* Deletes the journal feed from the database. Also notifies the appropriate model listeners.
	*
	* @param journalFeed the journal feed
	* @return the journal feed that was removed
	*/
	public static com.liferay.portlet.journal.model.JournalFeed deleteJournalFeed(
		com.liferay.portlet.journal.model.JournalFeed journalFeed) {
		return getService().deleteJournalFeed(journalFeed);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalFeedModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalFeedModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.portlet.journal.model.JournalFeed fetchFeed(
		long groupId, java.lang.String feedId) {
		return getService().fetchFeed(groupId, feedId);
	}

	public static com.liferay.portlet.journal.model.JournalFeed fetchJournalFeed(
		long id) {
		return getService().fetchJournalFeed(id);
	}

	/**
	* Returns the journal feed matching the UUID and group.
	*
	* @param uuid the journal feed's UUID
	* @param groupId the primary key of the group
	* @return the matching journal feed, or <code>null</code> if a matching journal feed could not be found
	*/
	public static com.liferay.portlet.journal.model.JournalFeed fetchJournalFeedByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService().fetchJournalFeedByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portal.kernel.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portlet.journal.model.JournalFeed getFeed(
		long feedId) throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFeed(feedId);
	}

	public static com.liferay.portlet.journal.model.JournalFeed getFeed(
		long groupId, java.lang.String feedId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFeed(groupId, feedId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalFeed> getFeeds() {
		return getService().getFeeds();
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalFeed> getFeeds(
		long groupId) {
		return getService().getFeeds(groupId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalFeed> getFeeds(
		long groupId, int start, int end) {
		return getService().getFeeds(groupId, start, end);
	}

	public static int getFeedsCount(long groupId) {
		return getService().getFeedsCount(groupId);
	}

	/**
	* Returns the journal feed with the primary key.
	*
	* @param id the primary key of the journal feed
	* @return the journal feed
	* @throws PortalException if a journal feed with the primary key could not be found
	*/
	public static com.liferay.portlet.journal.model.JournalFeed getJournalFeed(
		long id) throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getJournalFeed(id);
	}

	/**
	* Returns the journal feed matching the UUID and group.
	*
	* @param uuid the journal feed's UUID
	* @param groupId the primary key of the group
	* @return the matching journal feed
	* @throws PortalException if a matching journal feed could not be found
	*/
	public static com.liferay.portlet.journal.model.JournalFeed getJournalFeedByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getJournalFeedByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the journal feeds.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalFeedModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of journal feeds
	* @param end the upper bound of the range of journal feeds (not inclusive)
	* @return the range of journal feeds
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalFeed> getJournalFeeds(
		int start, int end) {
		return getService().getJournalFeeds(start, end);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalFeed> getJournalFeedsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService().getJournalFeedsByUuidAndCompanyId(uuid, companyId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalFeed> getJournalFeedsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.journal.model.JournalFeed> orderByComparator) {
		return getService()
				   .getJournalFeedsByUuidAndCompanyId(uuid, companyId, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of journal feeds.
	*
	* @return the number of journal feeds
	*/
	public static int getJournalFeedsCount() {
		return getService().getJournalFeedsCount();
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalFeed> search(
		long companyId, long groupId, java.lang.String feedId,
		java.lang.String name, java.lang.String description,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.journal.model.JournalFeed> obc) {
		return getService()
				   .search(companyId, groupId, feedId, name, description,
			andOperator, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalFeed> search(
		long companyId, long groupId, java.lang.String keywords, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.journal.model.JournalFeed> obc) {
		return getService().search(companyId, groupId, keywords, start, end, obc);
	}

	public static int searchCount(long companyId, long groupId,
		java.lang.String feedId, java.lang.String name,
		java.lang.String description, boolean andOperator) {
		return getService()
				   .searchCount(companyId, groupId, feedId, name, description,
			andOperator);
	}

	public static int searchCount(long companyId, long groupId,
		java.lang.String keywords) {
		return getService().searchCount(companyId, groupId, keywords);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static com.liferay.portlet.journal.model.JournalFeed updateFeed(
		long groupId, java.lang.String feedId, java.lang.String name,
		java.lang.String description, java.lang.String ddmStructureKey,
		java.lang.String ddmTemplateKey,
		java.lang.String ddmRendererTemplateKey, int delta,
		java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedFormat, double feedVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateFeed(groupId, feedId, name, description,
			ddmStructureKey, ddmTemplateKey, ddmRendererTemplateKey, delta,
			orderByCol, orderByType, targetLayoutFriendlyUrl, targetPortletId,
			contentField, feedFormat, feedVersion, serviceContext);
	}

	/**
	* Updates the journal feed in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param journalFeed the journal feed
	* @return the journal feed that was updated
	*/
	public static com.liferay.portlet.journal.model.JournalFeed updateJournalFeed(
		com.liferay.portlet.journal.model.JournalFeed journalFeed) {
		return getService().updateJournalFeed(journalFeed);
	}

	public static JournalFeedLocalService getService() {
		if (_service == null) {
			_service = (JournalFeedLocalService)PortalBeanLocatorUtil.locate(JournalFeedLocalService.class.getName());

			ReferenceRegistry.registerReference(JournalFeedLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(JournalFeedLocalService service) {
	}

	private static JournalFeedLocalService _service;
}