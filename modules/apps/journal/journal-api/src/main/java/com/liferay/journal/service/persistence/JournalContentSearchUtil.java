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

package com.liferay.journal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.journal.model.JournalContentSearch;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the journal content search service. This utility wraps {@link com.liferay.journal.service.persistence.impl.JournalContentSearchPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalContentSearchPersistence
 * @see com.liferay.journal.service.persistence.impl.JournalContentSearchPersistenceImpl
 * @generated
 */
@ProviderType
public class JournalContentSearchUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(JournalContentSearch journalContentSearch) {
		getPersistence().clearCache(journalContentSearch);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<JournalContentSearch> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<JournalContentSearch> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<JournalContentSearch> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static JournalContentSearch update(
		JournalContentSearch journalContentSearch) {
		return getPersistence().update(journalContentSearch);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static JournalContentSearch update(
		JournalContentSearch journalContentSearch, ServiceContext serviceContext) {
		return getPersistence().update(journalContentSearch, serviceContext);
	}

	/**
	* Returns all the journal content searchs where portletId = &#63;.
	*
	* @param portletId the portlet ID
	* @return the matching journal content searchs
	*/
	public static List<JournalContentSearch> findByPortletId(
		java.lang.String portletId) {
		return getPersistence().findByPortletId(portletId);
	}

	/**
	* Returns a range of all the journal content searchs where portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param portletId the portlet ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @return the range of matching journal content searchs
	*/
	public static List<JournalContentSearch> findByPortletId(
		java.lang.String portletId, int start, int end) {
		return getPersistence().findByPortletId(portletId, start, end);
	}

	/**
	* Returns an ordered range of all the journal content searchs where portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param portletId the portlet ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal content searchs
	*/
	public static List<JournalContentSearch> findByPortletId(
		java.lang.String portletId, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator) {
		return getPersistence()
				   .findByPortletId(portletId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the journal content searchs where portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param portletId the portlet ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal content searchs
	*/
	public static List<JournalContentSearch> findByPortletId(
		java.lang.String portletId, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByPortletId(portletId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first journal content search in the ordered set where portletId = &#63;.
	*
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search
	* @throws com.liferay.journal.NoSuchContentSearchException if a matching journal content search could not be found
	*/
	public static JournalContentSearch findByPortletId_First(
		java.lang.String portletId,
		OrderByComparator<JournalContentSearch> orderByComparator)
		throws com.liferay.journal.exception.NoSuchContentSearchException {
		return getPersistence()
				   .findByPortletId_First(portletId, orderByComparator);
	}

	/**
	* Returns the first journal content search in the ordered set where portletId = &#63;.
	*
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	*/
	public static JournalContentSearch fetchByPortletId_First(
		java.lang.String portletId,
		OrderByComparator<JournalContentSearch> orderByComparator) {
		return getPersistence()
				   .fetchByPortletId_First(portletId, orderByComparator);
	}

	/**
	* Returns the last journal content search in the ordered set where portletId = &#63;.
	*
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search
	* @throws com.liferay.journal.NoSuchContentSearchException if a matching journal content search could not be found
	*/
	public static JournalContentSearch findByPortletId_Last(
		java.lang.String portletId,
		OrderByComparator<JournalContentSearch> orderByComparator)
		throws com.liferay.journal.exception.NoSuchContentSearchException {
		return getPersistence()
				   .findByPortletId_Last(portletId, orderByComparator);
	}

	/**
	* Returns the last journal content search in the ordered set where portletId = &#63;.
	*
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search, or <code>null</code> if a matching journal content search could not be found
	*/
	public static JournalContentSearch fetchByPortletId_Last(
		java.lang.String portletId,
		OrderByComparator<JournalContentSearch> orderByComparator) {
		return getPersistence()
				   .fetchByPortletId_Last(portletId, orderByComparator);
	}

	/**
	* Returns the journal content searchs before and after the current journal content search in the ordered set where portletId = &#63;.
	*
	* @param contentSearchId the primary key of the current journal content search
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal content search
	* @throws com.liferay.journal.NoSuchContentSearchException if a journal content search with the primary key could not be found
	*/
	public static JournalContentSearch[] findByPortletId_PrevAndNext(
		long contentSearchId, java.lang.String portletId,
		OrderByComparator<JournalContentSearch> orderByComparator)
		throws com.liferay.journal.exception.NoSuchContentSearchException {
		return getPersistence()
				   .findByPortletId_PrevAndNext(contentSearchId, portletId,
			orderByComparator);
	}

	/**
	* Removes all the journal content searchs where portletId = &#63; from the database.
	*
	* @param portletId the portlet ID
	*/
	public static void removeByPortletId(java.lang.String portletId) {
		getPersistence().removeByPortletId(portletId);
	}

	/**
	* Returns the number of journal content searchs where portletId = &#63;.
	*
	* @param portletId the portlet ID
	* @return the number of matching journal content searchs
	*/
	public static int countByPortletId(java.lang.String portletId) {
		return getPersistence().countByPortletId(portletId);
	}

	/**
	* Returns all the journal content searchs where articleId = &#63;.
	*
	* @param articleId the article ID
	* @return the matching journal content searchs
	*/
	public static List<JournalContentSearch> findByArticleId(
		java.lang.String articleId) {
		return getPersistence().findByArticleId(articleId);
	}

	/**
	* Returns a range of all the journal content searchs where articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param articleId the article ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @return the range of matching journal content searchs
	*/
	public static List<JournalContentSearch> findByArticleId(
		java.lang.String articleId, int start, int end) {
		return getPersistence().findByArticleId(articleId, start, end);
	}

	/**
	* Returns an ordered range of all the journal content searchs where articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param articleId the article ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal content searchs
	*/
	public static List<JournalContentSearch> findByArticleId(
		java.lang.String articleId, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator) {
		return getPersistence()
				   .findByArticleId(articleId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the journal content searchs where articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param articleId the article ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal content searchs
	*/
	public static List<JournalContentSearch> findByArticleId(
		java.lang.String articleId, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByArticleId(articleId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first journal content search in the ordered set where articleId = &#63;.
	*
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search
	* @throws com.liferay.journal.NoSuchContentSearchException if a matching journal content search could not be found
	*/
	public static JournalContentSearch findByArticleId_First(
		java.lang.String articleId,
		OrderByComparator<JournalContentSearch> orderByComparator)
		throws com.liferay.journal.exception.NoSuchContentSearchException {
		return getPersistence()
				   .findByArticleId_First(articleId, orderByComparator);
	}

	/**
	* Returns the first journal content search in the ordered set where articleId = &#63;.
	*
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	*/
	public static JournalContentSearch fetchByArticleId_First(
		java.lang.String articleId,
		OrderByComparator<JournalContentSearch> orderByComparator) {
		return getPersistence()
				   .fetchByArticleId_First(articleId, orderByComparator);
	}

	/**
	* Returns the last journal content search in the ordered set where articleId = &#63;.
	*
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search
	* @throws com.liferay.journal.NoSuchContentSearchException if a matching journal content search could not be found
	*/
	public static JournalContentSearch findByArticleId_Last(
		java.lang.String articleId,
		OrderByComparator<JournalContentSearch> orderByComparator)
		throws com.liferay.journal.exception.NoSuchContentSearchException {
		return getPersistence()
				   .findByArticleId_Last(articleId, orderByComparator);
	}

	/**
	* Returns the last journal content search in the ordered set where articleId = &#63;.
	*
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search, or <code>null</code> if a matching journal content search could not be found
	*/
	public static JournalContentSearch fetchByArticleId_Last(
		java.lang.String articleId,
		OrderByComparator<JournalContentSearch> orderByComparator) {
		return getPersistence()
				   .fetchByArticleId_Last(articleId, orderByComparator);
	}

	/**
	* Returns the journal content searchs before and after the current journal content search in the ordered set where articleId = &#63;.
	*
	* @param contentSearchId the primary key of the current journal content search
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal content search
	* @throws com.liferay.journal.NoSuchContentSearchException if a journal content search with the primary key could not be found
	*/
	public static JournalContentSearch[] findByArticleId_PrevAndNext(
		long contentSearchId, java.lang.String articleId,
		OrderByComparator<JournalContentSearch> orderByComparator)
		throws com.liferay.journal.exception.NoSuchContentSearchException {
		return getPersistence()
				   .findByArticleId_PrevAndNext(contentSearchId, articleId,
			orderByComparator);
	}

	/**
	* Removes all the journal content searchs where articleId = &#63; from the database.
	*
	* @param articleId the article ID
	*/
	public static void removeByArticleId(java.lang.String articleId) {
		getPersistence().removeByArticleId(articleId);
	}

	/**
	* Returns the number of journal content searchs where articleId = &#63;.
	*
	* @param articleId the article ID
	* @return the number of matching journal content searchs
	*/
	public static int countByArticleId(java.lang.String articleId) {
		return getPersistence().countByArticleId(articleId);
	}

	/**
	* Returns all the journal content searchs where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @return the matching journal content searchs
	*/
	public static List<JournalContentSearch> findByG_P(long groupId,
		boolean privateLayout) {
		return getPersistence().findByG_P(groupId, privateLayout);
	}

	/**
	* Returns a range of all the journal content searchs where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @return the range of matching journal content searchs
	*/
	public static List<JournalContentSearch> findByG_P(long groupId,
		boolean privateLayout, int start, int end) {
		return getPersistence().findByG_P(groupId, privateLayout, start, end);
	}

	/**
	* Returns an ordered range of all the journal content searchs where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal content searchs
	*/
	public static List<JournalContentSearch> findByG_P(long groupId,
		boolean privateLayout, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator) {
		return getPersistence()
				   .findByG_P(groupId, privateLayout, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the journal content searchs where groupId = &#63; and privateLayout = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal content searchs
	*/
	public static List<JournalContentSearch> findByG_P(long groupId,
		boolean privateLayout, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_P(groupId, privateLayout, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search
	* @throws com.liferay.journal.NoSuchContentSearchException if a matching journal content search could not be found
	*/
	public static JournalContentSearch findByG_P_First(long groupId,
		boolean privateLayout,
		OrderByComparator<JournalContentSearch> orderByComparator)
		throws com.liferay.journal.exception.NoSuchContentSearchException {
		return getPersistence()
				   .findByG_P_First(groupId, privateLayout, orderByComparator);
	}

	/**
	* Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	*/
	public static JournalContentSearch fetchByG_P_First(long groupId,
		boolean privateLayout,
		OrderByComparator<JournalContentSearch> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_First(groupId, privateLayout, orderByComparator);
	}

	/**
	* Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search
	* @throws com.liferay.journal.NoSuchContentSearchException if a matching journal content search could not be found
	*/
	public static JournalContentSearch findByG_P_Last(long groupId,
		boolean privateLayout,
		OrderByComparator<JournalContentSearch> orderByComparator)
		throws com.liferay.journal.exception.NoSuchContentSearchException {
		return getPersistence()
				   .findByG_P_Last(groupId, privateLayout, orderByComparator);
	}

	/**
	* Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search, or <code>null</code> if a matching journal content search could not be found
	*/
	public static JournalContentSearch fetchByG_P_Last(long groupId,
		boolean privateLayout,
		OrderByComparator<JournalContentSearch> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_Last(groupId, privateLayout, orderByComparator);
	}

	/**
	* Returns the journal content searchs before and after the current journal content search in the ordered set where groupId = &#63; and privateLayout = &#63;.
	*
	* @param contentSearchId the primary key of the current journal content search
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal content search
	* @throws com.liferay.journal.NoSuchContentSearchException if a journal content search with the primary key could not be found
	*/
	public static JournalContentSearch[] findByG_P_PrevAndNext(
		long contentSearchId, long groupId, boolean privateLayout,
		OrderByComparator<JournalContentSearch> orderByComparator)
		throws com.liferay.journal.exception.NoSuchContentSearchException {
		return getPersistence()
				   .findByG_P_PrevAndNext(contentSearchId, groupId,
			privateLayout, orderByComparator);
	}

	/**
	* Removes all the journal content searchs where groupId = &#63; and privateLayout = &#63; from the database.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	*/
	public static void removeByG_P(long groupId, boolean privateLayout) {
		getPersistence().removeByG_P(groupId, privateLayout);
	}

	/**
	* Returns the number of journal content searchs where groupId = &#63; and privateLayout = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @return the number of matching journal content searchs
	*/
	public static int countByG_P(long groupId, boolean privateLayout) {
		return getPersistence().countByG_P(groupId, privateLayout);
	}

	/**
	* Returns all the journal content searchs where groupId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @return the matching journal content searchs
	*/
	public static List<JournalContentSearch> findByG_A(long groupId,
		java.lang.String articleId) {
		return getPersistence().findByG_A(groupId, articleId);
	}

	/**
	* Returns a range of all the journal content searchs where groupId = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @return the range of matching journal content searchs
	*/
	public static List<JournalContentSearch> findByG_A(long groupId,
		java.lang.String articleId, int start, int end) {
		return getPersistence().findByG_A(groupId, articleId, start, end);
	}

	/**
	* Returns an ordered range of all the journal content searchs where groupId = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal content searchs
	*/
	public static List<JournalContentSearch> findByG_A(long groupId,
		java.lang.String articleId, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator) {
		return getPersistence()
				   .findByG_A(groupId, articleId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the journal content searchs where groupId = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal content searchs
	*/
	public static List<JournalContentSearch> findByG_A(long groupId,
		java.lang.String articleId, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_A(groupId, articleId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first journal content search in the ordered set where groupId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search
	* @throws com.liferay.journal.NoSuchContentSearchException if a matching journal content search could not be found
	*/
	public static JournalContentSearch findByG_A_First(long groupId,
		java.lang.String articleId,
		OrderByComparator<JournalContentSearch> orderByComparator)
		throws com.liferay.journal.exception.NoSuchContentSearchException {
		return getPersistence()
				   .findByG_A_First(groupId, articleId, orderByComparator);
	}

	/**
	* Returns the first journal content search in the ordered set where groupId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	*/
	public static JournalContentSearch fetchByG_A_First(long groupId,
		java.lang.String articleId,
		OrderByComparator<JournalContentSearch> orderByComparator) {
		return getPersistence()
				   .fetchByG_A_First(groupId, articleId, orderByComparator);
	}

	/**
	* Returns the last journal content search in the ordered set where groupId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search
	* @throws com.liferay.journal.NoSuchContentSearchException if a matching journal content search could not be found
	*/
	public static JournalContentSearch findByG_A_Last(long groupId,
		java.lang.String articleId,
		OrderByComparator<JournalContentSearch> orderByComparator)
		throws com.liferay.journal.exception.NoSuchContentSearchException {
		return getPersistence()
				   .findByG_A_Last(groupId, articleId, orderByComparator);
	}

	/**
	* Returns the last journal content search in the ordered set where groupId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search, or <code>null</code> if a matching journal content search could not be found
	*/
	public static JournalContentSearch fetchByG_A_Last(long groupId,
		java.lang.String articleId,
		OrderByComparator<JournalContentSearch> orderByComparator) {
		return getPersistence()
				   .fetchByG_A_Last(groupId, articleId, orderByComparator);
	}

	/**
	* Returns the journal content searchs before and after the current journal content search in the ordered set where groupId = &#63; and articleId = &#63;.
	*
	* @param contentSearchId the primary key of the current journal content search
	* @param groupId the group ID
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal content search
	* @throws com.liferay.journal.NoSuchContentSearchException if a journal content search with the primary key could not be found
	*/
	public static JournalContentSearch[] findByG_A_PrevAndNext(
		long contentSearchId, long groupId, java.lang.String articleId,
		OrderByComparator<JournalContentSearch> orderByComparator)
		throws com.liferay.journal.exception.NoSuchContentSearchException {
		return getPersistence()
				   .findByG_A_PrevAndNext(contentSearchId, groupId, articleId,
			orderByComparator);
	}

	/**
	* Removes all the journal content searchs where groupId = &#63; and articleId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	*/
	public static void removeByG_A(long groupId, java.lang.String articleId) {
		getPersistence().removeByG_A(groupId, articleId);
	}

	/**
	* Returns the number of journal content searchs where groupId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @return the number of matching journal content searchs
	*/
	public static int countByG_A(long groupId, java.lang.String articleId) {
		return getPersistence().countByG_A(groupId, articleId);
	}

	/**
	* Returns all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @return the matching journal content searchs
	*/
	public static List<JournalContentSearch> findByG_P_L(long groupId,
		boolean privateLayout, long layoutId) {
		return getPersistence().findByG_P_L(groupId, privateLayout, layoutId);
	}

	/**
	* Returns a range of all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @return the range of matching journal content searchs
	*/
	public static List<JournalContentSearch> findByG_P_L(long groupId,
		boolean privateLayout, long layoutId, int start, int end) {
		return getPersistence()
				   .findByG_P_L(groupId, privateLayout, layoutId, start, end);
	}

	/**
	* Returns an ordered range of all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal content searchs
	*/
	public static List<JournalContentSearch> findByG_P_L(long groupId,
		boolean privateLayout, long layoutId, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator) {
		return getPersistence()
				   .findByG_P_L(groupId, privateLayout, layoutId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal content searchs
	*/
	public static List<JournalContentSearch> findByG_P_L(long groupId,
		boolean privateLayout, long layoutId, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_P_L(groupId, privateLayout, layoutId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search
	* @throws com.liferay.journal.NoSuchContentSearchException if a matching journal content search could not be found
	*/
	public static JournalContentSearch findByG_P_L_First(long groupId,
		boolean privateLayout, long layoutId,
		OrderByComparator<JournalContentSearch> orderByComparator)
		throws com.liferay.journal.exception.NoSuchContentSearchException {
		return getPersistence()
				   .findByG_P_L_First(groupId, privateLayout, layoutId,
			orderByComparator);
	}

	/**
	* Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	*/
	public static JournalContentSearch fetchByG_P_L_First(long groupId,
		boolean privateLayout, long layoutId,
		OrderByComparator<JournalContentSearch> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_L_First(groupId, privateLayout, layoutId,
			orderByComparator);
	}

	/**
	* Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search
	* @throws com.liferay.journal.NoSuchContentSearchException if a matching journal content search could not be found
	*/
	public static JournalContentSearch findByG_P_L_Last(long groupId,
		boolean privateLayout, long layoutId,
		OrderByComparator<JournalContentSearch> orderByComparator)
		throws com.liferay.journal.exception.NoSuchContentSearchException {
		return getPersistence()
				   .findByG_P_L_Last(groupId, privateLayout, layoutId,
			orderByComparator);
	}

	/**
	* Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search, or <code>null</code> if a matching journal content search could not be found
	*/
	public static JournalContentSearch fetchByG_P_L_Last(long groupId,
		boolean privateLayout, long layoutId,
		OrderByComparator<JournalContentSearch> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_L_Last(groupId, privateLayout, layoutId,
			orderByComparator);
	}

	/**
	* Returns the journal content searchs before and after the current journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	*
	* @param contentSearchId the primary key of the current journal content search
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal content search
	* @throws com.liferay.journal.NoSuchContentSearchException if a journal content search with the primary key could not be found
	*/
	public static JournalContentSearch[] findByG_P_L_PrevAndNext(
		long contentSearchId, long groupId, boolean privateLayout,
		long layoutId, OrderByComparator<JournalContentSearch> orderByComparator)
		throws com.liferay.journal.exception.NoSuchContentSearchException {
		return getPersistence()
				   .findByG_P_L_PrevAndNext(contentSearchId, groupId,
			privateLayout, layoutId, orderByComparator);
	}

	/**
	* Removes all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	*/
	public static void removeByG_P_L(long groupId, boolean privateLayout,
		long layoutId) {
		getPersistence().removeByG_P_L(groupId, privateLayout, layoutId);
	}

	/**
	* Returns the number of journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @return the number of matching journal content searchs
	*/
	public static int countByG_P_L(long groupId, boolean privateLayout,
		long layoutId) {
		return getPersistence().countByG_P_L(groupId, privateLayout, layoutId);
	}

	/**
	* Returns all the journal content searchs where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param articleId the article ID
	* @return the matching journal content searchs
	*/
	public static List<JournalContentSearch> findByG_P_A(long groupId,
		boolean privateLayout, java.lang.String articleId) {
		return getPersistence().findByG_P_A(groupId, privateLayout, articleId);
	}

	/**
	* Returns a range of all the journal content searchs where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param articleId the article ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @return the range of matching journal content searchs
	*/
	public static List<JournalContentSearch> findByG_P_A(long groupId,
		boolean privateLayout, java.lang.String articleId, int start, int end) {
		return getPersistence()
				   .findByG_P_A(groupId, privateLayout, articleId, start, end);
	}

	/**
	* Returns an ordered range of all the journal content searchs where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param articleId the article ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal content searchs
	*/
	public static List<JournalContentSearch> findByG_P_A(long groupId,
		boolean privateLayout, java.lang.String articleId, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator) {
		return getPersistence()
				   .findByG_P_A(groupId, privateLayout, articleId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the journal content searchs where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param articleId the article ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal content searchs
	*/
	public static List<JournalContentSearch> findByG_P_A(long groupId,
		boolean privateLayout, java.lang.String articleId, int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_P_A(groupId, privateLayout, articleId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search
	* @throws com.liferay.journal.NoSuchContentSearchException if a matching journal content search could not be found
	*/
	public static JournalContentSearch findByG_P_A_First(long groupId,
		boolean privateLayout, java.lang.String articleId,
		OrderByComparator<JournalContentSearch> orderByComparator)
		throws com.liferay.journal.exception.NoSuchContentSearchException {
		return getPersistence()
				   .findByG_P_A_First(groupId, privateLayout, articleId,
			orderByComparator);
	}

	/**
	* Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	*/
	public static JournalContentSearch fetchByG_P_A_First(long groupId,
		boolean privateLayout, java.lang.String articleId,
		OrderByComparator<JournalContentSearch> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_A_First(groupId, privateLayout, articleId,
			orderByComparator);
	}

	/**
	* Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search
	* @throws com.liferay.journal.NoSuchContentSearchException if a matching journal content search could not be found
	*/
	public static JournalContentSearch findByG_P_A_Last(long groupId,
		boolean privateLayout, java.lang.String articleId,
		OrderByComparator<JournalContentSearch> orderByComparator)
		throws com.liferay.journal.exception.NoSuchContentSearchException {
		return getPersistence()
				   .findByG_P_A_Last(groupId, privateLayout, articleId,
			orderByComparator);
	}

	/**
	* Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search, or <code>null</code> if a matching journal content search could not be found
	*/
	public static JournalContentSearch fetchByG_P_A_Last(long groupId,
		boolean privateLayout, java.lang.String articleId,
		OrderByComparator<JournalContentSearch> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_A_Last(groupId, privateLayout, articleId,
			orderByComparator);
	}

	/**
	* Returns the journal content searchs before and after the current journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	*
	* @param contentSearchId the primary key of the current journal content search
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal content search
	* @throws com.liferay.journal.NoSuchContentSearchException if a journal content search with the primary key could not be found
	*/
	public static JournalContentSearch[] findByG_P_A_PrevAndNext(
		long contentSearchId, long groupId, boolean privateLayout,
		java.lang.String articleId,
		OrderByComparator<JournalContentSearch> orderByComparator)
		throws com.liferay.journal.exception.NoSuchContentSearchException {
		return getPersistence()
				   .findByG_P_A_PrevAndNext(contentSearchId, groupId,
			privateLayout, articleId, orderByComparator);
	}

	/**
	* Removes all the journal content searchs where groupId = &#63; and privateLayout = &#63; and articleId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param articleId the article ID
	*/
	public static void removeByG_P_A(long groupId, boolean privateLayout,
		java.lang.String articleId) {
		getPersistence().removeByG_P_A(groupId, privateLayout, articleId);
	}

	/**
	* Returns the number of journal content searchs where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param articleId the article ID
	* @return the number of matching journal content searchs
	*/
	public static int countByG_P_A(long groupId, boolean privateLayout,
		java.lang.String articleId) {
		return getPersistence().countByG_P_A(groupId, privateLayout, articleId);
	}

	/**
	* Returns all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @return the matching journal content searchs
	*/
	public static List<JournalContentSearch> findByG_P_L_P(long groupId,
		boolean privateLayout, long layoutId, java.lang.String portletId) {
		return getPersistence()
				   .findByG_P_L_P(groupId, privateLayout, layoutId, portletId);
	}

	/**
	* Returns a range of all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @return the range of matching journal content searchs
	*/
	public static List<JournalContentSearch> findByG_P_L_P(long groupId,
		boolean privateLayout, long layoutId, java.lang.String portletId,
		int start, int end) {
		return getPersistence()
				   .findByG_P_L_P(groupId, privateLayout, layoutId, portletId,
			start, end);
	}

	/**
	* Returns an ordered range of all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal content searchs
	*/
	public static List<JournalContentSearch> findByG_P_L_P(long groupId,
		boolean privateLayout, long layoutId, java.lang.String portletId,
		int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator) {
		return getPersistence()
				   .findByG_P_L_P(groupId, privateLayout, layoutId, portletId,
			start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal content searchs
	*/
	public static List<JournalContentSearch> findByG_P_L_P(long groupId,
		boolean privateLayout, long layoutId, java.lang.String portletId,
		int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByG_P_L_P(groupId, privateLayout, layoutId, portletId,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search
	* @throws com.liferay.journal.NoSuchContentSearchException if a matching journal content search could not be found
	*/
	public static JournalContentSearch findByG_P_L_P_First(long groupId,
		boolean privateLayout, long layoutId, java.lang.String portletId,
		OrderByComparator<JournalContentSearch> orderByComparator)
		throws com.liferay.journal.exception.NoSuchContentSearchException {
		return getPersistence()
				   .findByG_P_L_P_First(groupId, privateLayout, layoutId,
			portletId, orderByComparator);
	}

	/**
	* Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	*/
	public static JournalContentSearch fetchByG_P_L_P_First(long groupId,
		boolean privateLayout, long layoutId, java.lang.String portletId,
		OrderByComparator<JournalContentSearch> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_L_P_First(groupId, privateLayout, layoutId,
			portletId, orderByComparator);
	}

	/**
	* Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search
	* @throws com.liferay.journal.NoSuchContentSearchException if a matching journal content search could not be found
	*/
	public static JournalContentSearch findByG_P_L_P_Last(long groupId,
		boolean privateLayout, long layoutId, java.lang.String portletId,
		OrderByComparator<JournalContentSearch> orderByComparator)
		throws com.liferay.journal.exception.NoSuchContentSearchException {
		return getPersistence()
				   .findByG_P_L_P_Last(groupId, privateLayout, layoutId,
			portletId, orderByComparator);
	}

	/**
	* Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal content search, or <code>null</code> if a matching journal content search could not be found
	*/
	public static JournalContentSearch fetchByG_P_L_P_Last(long groupId,
		boolean privateLayout, long layoutId, java.lang.String portletId,
		OrderByComparator<JournalContentSearch> orderByComparator) {
		return getPersistence()
				   .fetchByG_P_L_P_Last(groupId, privateLayout, layoutId,
			portletId, orderByComparator);
	}

	/**
	* Returns the journal content searchs before and after the current journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	*
	* @param contentSearchId the primary key of the current journal content search
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal content search
	* @throws com.liferay.journal.NoSuchContentSearchException if a journal content search with the primary key could not be found
	*/
	public static JournalContentSearch[] findByG_P_L_P_PrevAndNext(
		long contentSearchId, long groupId, boolean privateLayout,
		long layoutId, java.lang.String portletId,
		OrderByComparator<JournalContentSearch> orderByComparator)
		throws com.liferay.journal.exception.NoSuchContentSearchException {
		return getPersistence()
				   .findByG_P_L_P_PrevAndNext(contentSearchId, groupId,
			privateLayout, layoutId, portletId, orderByComparator);
	}

	/**
	* Removes all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	*/
	public static void removeByG_P_L_P(long groupId, boolean privateLayout,
		long layoutId, java.lang.String portletId) {
		getPersistence()
			.removeByG_P_L_P(groupId, privateLayout, layoutId, portletId);
	}

	/**
	* Returns the number of journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @return the number of matching journal content searchs
	*/
	public static int countByG_P_L_P(long groupId, boolean privateLayout,
		long layoutId, java.lang.String portletId) {
		return getPersistence()
				   .countByG_P_L_P(groupId, privateLayout, layoutId, portletId);
	}

	/**
	* Returns the journal content search where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; and articleId = &#63; or throws a {@link com.liferay.journal.NoSuchContentSearchException} if it could not be found.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param articleId the article ID
	* @return the matching journal content search
	* @throws com.liferay.journal.NoSuchContentSearchException if a matching journal content search could not be found
	*/
	public static JournalContentSearch findByG_P_L_P_A(long groupId,
		boolean privateLayout, long layoutId, java.lang.String portletId,
		java.lang.String articleId)
		throws com.liferay.journal.exception.NoSuchContentSearchException {
		return getPersistence()
				   .findByG_P_L_P_A(groupId, privateLayout, layoutId,
			portletId, articleId);
	}

	/**
	* Returns the journal content search where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; and articleId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param articleId the article ID
	* @return the matching journal content search, or <code>null</code> if a matching journal content search could not be found
	*/
	public static JournalContentSearch fetchByG_P_L_P_A(long groupId,
		boolean privateLayout, long layoutId, java.lang.String portletId,
		java.lang.String articleId) {
		return getPersistence()
				   .fetchByG_P_L_P_A(groupId, privateLayout, layoutId,
			portletId, articleId);
	}

	/**
	* Returns the journal content search where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; and articleId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param articleId the article ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching journal content search, or <code>null</code> if a matching journal content search could not be found
	*/
	public static JournalContentSearch fetchByG_P_L_P_A(long groupId,
		boolean privateLayout, long layoutId, java.lang.String portletId,
		java.lang.String articleId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByG_P_L_P_A(groupId, privateLayout, layoutId,
			portletId, articleId, retrieveFromCache);
	}

	/**
	* Removes the journal content search where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; and articleId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param articleId the article ID
	* @return the journal content search that was removed
	*/
	public static JournalContentSearch removeByG_P_L_P_A(long groupId,
		boolean privateLayout, long layoutId, java.lang.String portletId,
		java.lang.String articleId)
		throws com.liferay.journal.exception.NoSuchContentSearchException {
		return getPersistence()
				   .removeByG_P_L_P_A(groupId, privateLayout, layoutId,
			portletId, articleId);
	}

	/**
	* Returns the number of journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param privateLayout the private layout
	* @param layoutId the layout ID
	* @param portletId the portlet ID
	* @param articleId the article ID
	* @return the number of matching journal content searchs
	*/
	public static int countByG_P_L_P_A(long groupId, boolean privateLayout,
		long layoutId, java.lang.String portletId, java.lang.String articleId) {
		return getPersistence()
				   .countByG_P_L_P_A(groupId, privateLayout, layoutId,
			portletId, articleId);
	}

	/**
	* Caches the journal content search in the entity cache if it is enabled.
	*
	* @param journalContentSearch the journal content search
	*/
	public static void cacheResult(JournalContentSearch journalContentSearch) {
		getPersistence().cacheResult(journalContentSearch);
	}

	/**
	* Caches the journal content searchs in the entity cache if it is enabled.
	*
	* @param journalContentSearchs the journal content searchs
	*/
	public static void cacheResult(
		List<JournalContentSearch> journalContentSearchs) {
		getPersistence().cacheResult(journalContentSearchs);
	}

	/**
	* Creates a new journal content search with the primary key. Does not add the journal content search to the database.
	*
	* @param contentSearchId the primary key for the new journal content search
	* @return the new journal content search
	*/
	public static JournalContentSearch create(long contentSearchId) {
		return getPersistence().create(contentSearchId);
	}

	/**
	* Removes the journal content search with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param contentSearchId the primary key of the journal content search
	* @return the journal content search that was removed
	* @throws com.liferay.journal.NoSuchContentSearchException if a journal content search with the primary key could not be found
	*/
	public static JournalContentSearch remove(long contentSearchId)
		throws com.liferay.journal.exception.NoSuchContentSearchException {
		return getPersistence().remove(contentSearchId);
	}

	public static JournalContentSearch updateImpl(
		JournalContentSearch journalContentSearch) {
		return getPersistence().updateImpl(journalContentSearch);
	}

	/**
	* Returns the journal content search with the primary key or throws a {@link com.liferay.journal.NoSuchContentSearchException} if it could not be found.
	*
	* @param contentSearchId the primary key of the journal content search
	* @return the journal content search
	* @throws com.liferay.journal.NoSuchContentSearchException if a journal content search with the primary key could not be found
	*/
	public static JournalContentSearch findByPrimaryKey(long contentSearchId)
		throws com.liferay.journal.exception.NoSuchContentSearchException {
		return getPersistence().findByPrimaryKey(contentSearchId);
	}

	/**
	* Returns the journal content search with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param contentSearchId the primary key of the journal content search
	* @return the journal content search, or <code>null</code> if a journal content search with the primary key could not be found
	*/
	public static JournalContentSearch fetchByPrimaryKey(long contentSearchId) {
		return getPersistence().fetchByPrimaryKey(contentSearchId);
	}

	public static java.util.Map<java.io.Serializable, JournalContentSearch> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the journal content searchs.
	*
	* @return the journal content searchs
	*/
	public static List<JournalContentSearch> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the journal content searchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @return the range of journal content searchs
	*/
	public static List<JournalContentSearch> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the journal content searchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of journal content searchs
	*/
	public static List<JournalContentSearch> findAll(int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the journal content searchs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalContentSearchModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of journal content searchs
	* @param end the upper bound of the range of journal content searchs (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of journal content searchs
	*/
	public static List<JournalContentSearch> findAll(int start, int end,
		OrderByComparator<JournalContentSearch> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the journal content searchs from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of journal content searchs.
	*
	* @return the number of journal content searchs
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static JournalContentSearchPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<JournalContentSearchPersistence, JournalContentSearchPersistence> _serviceTracker =
		ServiceTrackerFactory.open(JournalContentSearchPersistence.class);
}