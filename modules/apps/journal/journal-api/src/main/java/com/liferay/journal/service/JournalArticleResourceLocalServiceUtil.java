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

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for JournalArticleResource. This utility wraps
 * {@link com.liferay.journal.service.impl.JournalArticleResourceLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticleResourceLocalService
 * @see com.liferay.journal.service.base.JournalArticleResourceLocalServiceBaseImpl
 * @see com.liferay.journal.service.impl.JournalArticleResourceLocalServiceImpl
 * @generated
 */
@ProviderType
public class JournalArticleResourceLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.journal.service.impl.JournalArticleResourceLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the journal article resource to the database. Also notifies the appropriate model listeners.
	*
	* @param journalArticleResource the journal article resource
	* @return the journal article resource that was added
	*/
	public static com.liferay.journal.model.JournalArticleResource addJournalArticleResource(
		com.liferay.journal.model.JournalArticleResource journalArticleResource) {
		return getService().addJournalArticleResource(journalArticleResource);
	}

	/**
	* Creates a new journal article resource with the primary key. Does not add the journal article resource to the database.
	*
	* @param resourcePrimKey the primary key for the new journal article resource
	* @return the new journal article resource
	*/
	public static com.liferay.journal.model.JournalArticleResource createJournalArticleResource(
		long resourcePrimKey) {
		return getService().createJournalArticleResource(resourcePrimKey);
	}

	public static void deleteArticleResource(long groupId,
		java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteArticleResource(groupId, articleId);
	}

	/**
	* Deletes the journal article resource from the database. Also notifies the appropriate model listeners.
	*
	* @param journalArticleResource the journal article resource
	* @return the journal article resource that was removed
	*/
	public static com.liferay.journal.model.JournalArticleResource deleteJournalArticleResource(
		com.liferay.journal.model.JournalArticleResource journalArticleResource) {
		return getService().deleteJournalArticleResource(journalArticleResource);
	}

	/**
	* Deletes the journal article resource with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourcePrimKey the primary key of the journal article resource
	* @return the journal article resource that was removed
	* @throws PortalException if a journal article resource with the primary key could not be found
	*/
	public static com.liferay.journal.model.JournalArticleResource deleteJournalArticleResource(
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteJournalArticleResource(resourcePrimKey);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.journal.model.impl.JournalArticleResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.journal.model.impl.JournalArticleResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.journal.model.JournalArticleResource fetchArticleResource(
		long groupId, java.lang.String articleId) {
		return getService().fetchArticleResource(groupId, articleId);
	}

	public static com.liferay.journal.model.JournalArticleResource fetchArticleResource(
		java.lang.String uuid, long groupId) {
		return getService().fetchArticleResource(uuid, groupId);
	}

	public static com.liferay.journal.model.JournalArticleResource fetchJournalArticleResource(
		long resourcePrimKey) {
		return getService().fetchJournalArticleResource(resourcePrimKey);
	}

	/**
	* Returns the journal article resource matching the UUID and group.
	*
	* @param uuid the journal article resource's UUID
	* @param groupId the primary key of the group
	* @return the matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	*/
	public static com.liferay.journal.model.JournalArticleResource fetchJournalArticleResourceByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService()
				   .fetchJournalArticleResourceByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.journal.model.JournalArticleResource getArticleResource(
		long articleResourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getArticleResource(articleResourcePrimKey);
	}

	public static long getArticleResourcePrimKey(long groupId,
		java.lang.String articleId) {
		return getService().getArticleResourcePrimKey(groupId, articleId);
	}

	public static long getArticleResourcePrimKey(java.lang.String uuid,
		long groupId, java.lang.String articleId) {
		return getService().getArticleResourcePrimKey(uuid, groupId, articleId);
	}

	public static java.util.List<com.liferay.journal.model.JournalArticleResource> getArticleResources(
		long groupId) {
		return getService().getArticleResources(groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the journal article resource with the primary key.
	*
	* @param resourcePrimKey the primary key of the journal article resource
	* @return the journal article resource
	* @throws PortalException if a journal article resource with the primary key could not be found
	*/
	public static com.liferay.journal.model.JournalArticleResource getJournalArticleResource(
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getJournalArticleResource(resourcePrimKey);
	}

	/**
	* Returns the journal article resource matching the UUID and group.
	*
	* @param uuid the journal article resource's UUID
	* @param groupId the primary key of the group
	* @return the matching journal article resource
	* @throws PortalException if a matching journal article resource could not be found
	*/
	public static com.liferay.journal.model.JournalArticleResource getJournalArticleResourceByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getJournalArticleResourceByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the journal article resources.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.journal.model.impl.JournalArticleResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of journal article resources
	* @param end the upper bound of the range of journal article resources (not inclusive)
	* @return the range of journal article resources
	*/
	public static java.util.List<com.liferay.journal.model.JournalArticleResource> getJournalArticleResources(
		int start, int end) {
		return getService().getJournalArticleResources(start, end);
	}

	/**
	* Returns all the journal article resources matching the UUID and company.
	*
	* @param uuid the UUID of the journal article resources
	* @param companyId the primary key of the company
	* @return the matching journal article resources, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.journal.model.JournalArticleResource> getJournalArticleResourcesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService()
				   .getJournalArticleResourcesByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns a range of journal article resources matching the UUID and company.
	*
	* @param uuid the UUID of the journal article resources
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of journal article resources
	* @param end the upper bound of the range of journal article resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching journal article resources, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.journal.model.JournalArticleResource> getJournalArticleResourcesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.journal.model.JournalArticleResource> orderByComparator) {
		return getService()
				   .getJournalArticleResourcesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of journal article resources.
	*
	* @return the number of journal article resources
	*/
	public static int getJournalArticleResourcesCount() {
		return getService().getJournalArticleResourcesCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the journal article resource in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param journalArticleResource the journal article resource
	* @return the journal article resource that was updated
	*/
	public static com.liferay.journal.model.JournalArticleResource updateJournalArticleResource(
		com.liferay.journal.model.JournalArticleResource journalArticleResource) {
		return getService().updateJournalArticleResource(journalArticleResource);
	}

	public static JournalArticleResourceLocalService getService() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(JournalArticleResourceLocalService service) {
	}

	private static ServiceTracker<JournalArticleResourceLocalService, JournalArticleResourceLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(JournalArticleResourceLocalServiceUtil.class);

		_serviceTracker = new ServiceTracker<JournalArticleResourceLocalService, JournalArticleResourceLocalService>(bundle.getBundleContext(),
				JournalArticleResourceLocalService.class, null);

		_serviceTracker.open();
	}
}