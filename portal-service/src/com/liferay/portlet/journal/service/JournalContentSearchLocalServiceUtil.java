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
 * Provides the local service utility for JournalContentSearch. This utility wraps
 * {@link com.liferay.portlet.journal.service.impl.JournalContentSearchLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see JournalContentSearchLocalService
 * @see com.liferay.portlet.journal.service.base.JournalContentSearchLocalServiceBaseImpl
 * @see com.liferay.portlet.journal.service.impl.JournalContentSearchLocalServiceImpl
 * @generated
 */
@ProviderType
public class JournalContentSearchLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.journal.service.impl.JournalContentSearchLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the journal content search to the database. Also notifies the appropriate model listeners.
	*
	* @param journalContentSearch the journal content search
	* @return the journal content search that was added
	*/
	public static com.liferay.portlet.journal.model.JournalContentSearch addJournalContentSearch(
		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch) {
		return getService().addJournalContentSearch(journalContentSearch);
	}

	public static void checkContentSearches(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().checkContentSearches(companyId);
	}

	/**
	* Creates a new journal content search with the primary key. Does not add the journal content search to the database.
	*
	* @param contentSearchId the primary key for the new journal content search
	* @return the new journal content search
	*/
	public static com.liferay.portlet.journal.model.JournalContentSearch createJournalContentSearch(
		long contentSearchId) {
		return getService().createJournalContentSearch(contentSearchId);
	}

	public static void deleteArticleContentSearch(long groupId, long layoutId,
		java.lang.String portletId) {
		getService().deleteArticleContentSearch(groupId, layoutId, portletId);
	}

	public static void deleteArticleContentSearch(long groupId, long layoutId,
		java.lang.String portletId, java.lang.String articleId) {
		getService()
			.deleteArticleContentSearch(groupId, layoutId, portletId, articleId);
	}

	public static void deleteArticleContentSearches(long groupId,
		java.lang.String articleId) {
		getService().deleteArticleContentSearches(groupId, articleId);
	}

	/**
	* Deletes the journal content search with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param contentSearchId the primary key of the journal content search
	* @return the journal content search that was removed
	* @throws PortalException if a journal content search with the primary key could not be found
	*/
	public static com.liferay.portlet.journal.model.JournalContentSearch deleteJournalContentSearch(
		long contentSearchId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteJournalContentSearch(contentSearchId);
	}

	/**
	* Deletes the journal content search from the database. Also notifies the appropriate model listeners.
	*
	* @param journalContentSearch the journal content search
	* @return the journal content search that was removed
	*/
	public static com.liferay.portlet.journal.model.JournalContentSearch deleteJournalContentSearch(
		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch) {
		return getService().deleteJournalContentSearch(journalContentSearch);
	}

	public static void deleteLayoutContentSearches(long groupId, long layoutId) {
		getService().deleteLayoutContentSearches(groupId, layoutId);
	}

	public static void deleteOwnerContentSearches(long groupId) {
		getService().deleteOwnerContentSearches(groupId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.portlet.journal.model.JournalContentSearch fetchJournalContentSearch(
		long contentSearchId) {
		return getService().fetchJournalContentSearch(contentSearchId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> getArticleContentSearches() {
		return getService().getArticleContentSearches();
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> getArticleContentSearches(
		java.lang.String articleId) {
		return getService().getArticleContentSearches(articleId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> getArticleContentSearches(
		long groupId, java.lang.String articleId) {
		return getService().getArticleContentSearches(groupId, articleId);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Returns the journal content search with the primary key.
	*
	* @param contentSearchId the primary key of the journal content search
	* @return the journal content search
	* @throws PortalException if a journal content search with the primary key could not be found
	*/
	public static com.liferay.portlet.journal.model.JournalContentSearch getJournalContentSearch(
		long contentSearchId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getJournalContentSearch(contentSearchId);
	}

	/**
	* Returns a range of all the journal content searchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @return the range of journal content searchs
	*/
	public static java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> getJournalContentSearchs(
		int start, int end) {
		return getService().getJournalContentSearchs(start, end);
	}

	/**
	* Returns the number of journal content searchs.
	*
	* @return the number of journal content searchs
	*/
	public static int getJournalContentSearchsCount() {
		return getService().getJournalContentSearchsCount();
	}

	public static java.util.List<java.lang.Long> getLayoutIds(long groupId,
		java.lang.String articleId) {
		return getService().getLayoutIds(groupId, articleId);
	}

	public static int getLayoutIdsCount(java.lang.String articleId) {
		return getService().getLayoutIdsCount(articleId);
	}

	public static int getLayoutIdsCount(long groupId, java.lang.String articleId) {
		return getService().getLayoutIdsCount(groupId, articleId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> getPortletContentSearches(
		java.lang.String portletId) {
		return getService().getPortletContentSearches(portletId);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch updateContentSearch(
		long groupId, long layoutId, java.lang.String portletId,
		java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateContentSearch(groupId, layoutId, portletId, articleId);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch updateContentSearch(
		long groupId, long layoutId, java.lang.String portletId,
		java.lang.String articleId, boolean purge)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateContentSearch(groupId, layoutId, portletId,
			articleId, purge);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> updateContentSearch(
		long groupId, long layoutId, java.lang.String portletId,
		java.lang.String[] articleIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateContentSearch(groupId, layoutId, portletId, articleIds);
	}

	/**
	* Updates the journal content search in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param journalContentSearch the journal content search
	* @return the journal content search that was updated
	*/
	public static com.liferay.portlet.journal.model.JournalContentSearch updateJournalContentSearch(
		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch) {
		return getService().updateJournalContentSearch(journalContentSearch);
	}

	public static JournalContentSearchLocalService getService() {
		if (_service == null) {
			_service = (JournalContentSearchLocalService)PortalBeanLocatorUtil.locate(JournalContentSearchLocalService.class.getName());

			ReferenceRegistry.registerReference(JournalContentSearchLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(JournalContentSearchLocalService service) {
	}

	private static JournalContentSearchLocalService _service;
}