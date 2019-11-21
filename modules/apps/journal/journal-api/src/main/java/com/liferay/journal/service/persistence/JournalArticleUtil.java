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

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the journal article service. This utility wraps <code>com.liferay.journal.service.persistence.impl.JournalArticlePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticlePersistence
 * @generated
 */
public class JournalArticleUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(JournalArticle journalArticle) {
		getPersistence().clearCache(journalArticle);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, JournalArticle> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<JournalArticle> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<JournalArticle> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<JournalArticle> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static JournalArticle update(JournalArticle journalArticle) {
		return getPersistence().update(journalArticle);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static JournalArticle update(
		JournalArticle journalArticle, ServiceContext serviceContext) {

		return getPersistence().update(journalArticle, serviceContext);
	}

	/**
	 * Returns all the journal articles where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByResourcePrimKey(
		long resourcePrimKey) {

		return getPersistence().findByResourcePrimKey(resourcePrimKey);
	}

	/**
	 * Returns a range of all the journal articles where resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByResourcePrimKey(
		long resourcePrimKey, int start, int end) {

		return getPersistence().findByResourcePrimKey(
			resourcePrimKey, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByResourcePrimKey(
		long resourcePrimKey, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByResourcePrimKey(
			resourcePrimKey, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByResourcePrimKey(
		long resourcePrimKey, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByResourcePrimKey(
			resourcePrimKey, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByResourcePrimKey_First(
			long resourcePrimKey,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByResourcePrimKey_First(
			resourcePrimKey, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByResourcePrimKey_First(
		long resourcePrimKey,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByResourcePrimKey_First(
			resourcePrimKey, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByResourcePrimKey_Last(
			long resourcePrimKey,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByResourcePrimKey_Last(
			resourcePrimKey, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByResourcePrimKey_Last(
		long resourcePrimKey,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByResourcePrimKey_Last(
			resourcePrimKey, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByResourcePrimKey_PrevAndNext(
			long id, long resourcePrimKey,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByResourcePrimKey_PrevAndNext(
			id, resourcePrimKey, orderByComparator);
	}

	/**
	 * Removes all the journal articles where resourcePrimKey = &#63; from the database.
	 *
	 * @param resourcePrimKey the resource prim key
	 */
	public static void removeByResourcePrimKey(long resourcePrimKey) {
		getPersistence().removeByResourcePrimKey(resourcePrimKey);
	}

	/**
	 * Returns the number of journal articles where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @return the number of matching journal articles
	 */
	public static int countByResourcePrimKey(long resourcePrimKey) {
		return getPersistence().countByResourcePrimKey(resourcePrimKey);
	}

	/**
	 * Returns all the journal articles where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the journal articles where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByUuid_First(
			String uuid, OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByUuid_First(
		String uuid, OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByUuid_Last(
			String uuid, OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByUuid_Last(
		String uuid, OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where uuid = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByUuid_PrevAndNext(
			long id, String uuid,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByUuid_PrevAndNext(
			id, uuid, orderByComparator);
	}

	/**
	 * Removes all the journal articles where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of journal articles where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching journal articles
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the journal article where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchArticleException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByUUID_G(String uuid, long groupId)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the journal article where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the journal article where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the journal article where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the journal article that was removed
	 */
	public static JournalArticle removeByUUID_G(String uuid, long groupId)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of journal articles where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching journal articles
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the journal articles where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the journal articles where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByUuid_C_PrevAndNext(
			long id, String uuid, long companyId,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByUuid_C_PrevAndNext(
			id, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the journal articles where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of journal articles where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching journal articles
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the journal articles where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByGroupId_First(
			long groupId, OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByGroupId_First(
		long groupId, OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByGroupId_Last(
			long groupId, OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByGroupId_Last(
		long groupId, OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByGroupId_PrevAndNext(
			long id, long groupId,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByGroupId_PrevAndNext(
			id, groupId, orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByGroupId(long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByGroupId(
		long groupId, int start, int end) {

		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().filterFindByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] filterFindByGroupId_PrevAndNext(
			long id, long groupId,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().filterFindByGroupId_PrevAndNext(
			id, groupId, orderByComparator);
	}

	/**
	 * Removes all the journal articles where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of journal articles where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching journal articles
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching journal articles that the user has permission to view
	 */
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	 * Returns all the journal articles where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the journal articles where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByCompanyId_First(
			long companyId, OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByCompanyId_First(
		long companyId, OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByCompanyId_Last(
			long companyId, OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByCompanyId_Last(
		long companyId, OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where companyId = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByCompanyId_PrevAndNext(
			long id, long companyId,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByCompanyId_PrevAndNext(
			id, companyId, orderByComparator);
	}

	/**
	 * Removes all the journal articles where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of journal articles where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching journal articles
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns all the journal articles where DDMStructureKey = &#63;.
	 *
	 * @param DDMStructureKey the ddm structure key
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByDDMStructureKey(
		String DDMStructureKey) {

		return getPersistence().findByDDMStructureKey(DDMStructureKey);
	}

	/**
	 * Returns a range of all the journal articles where DDMStructureKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param DDMStructureKey the ddm structure key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByDDMStructureKey(
		String DDMStructureKey, int start, int end) {

		return getPersistence().findByDDMStructureKey(
			DDMStructureKey, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where DDMStructureKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param DDMStructureKey the ddm structure key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByDDMStructureKey(
		String DDMStructureKey, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByDDMStructureKey(
			DDMStructureKey, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where DDMStructureKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param DDMStructureKey the ddm structure key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByDDMStructureKey(
		String DDMStructureKey, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByDDMStructureKey(
			DDMStructureKey, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where DDMStructureKey = &#63;.
	 *
	 * @param DDMStructureKey the ddm structure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByDDMStructureKey_First(
			String DDMStructureKey,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByDDMStructureKey_First(
			DDMStructureKey, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where DDMStructureKey = &#63;.
	 *
	 * @param DDMStructureKey the ddm structure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByDDMStructureKey_First(
		String DDMStructureKey,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByDDMStructureKey_First(
			DDMStructureKey, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where DDMStructureKey = &#63;.
	 *
	 * @param DDMStructureKey the ddm structure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByDDMStructureKey_Last(
			String DDMStructureKey,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByDDMStructureKey_Last(
			DDMStructureKey, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where DDMStructureKey = &#63;.
	 *
	 * @param DDMStructureKey the ddm structure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByDDMStructureKey_Last(
		String DDMStructureKey,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByDDMStructureKey_Last(
			DDMStructureKey, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where DDMStructureKey = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param DDMStructureKey the ddm structure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByDDMStructureKey_PrevAndNext(
			long id, String DDMStructureKey,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByDDMStructureKey_PrevAndNext(
			id, DDMStructureKey, orderByComparator);
	}

	/**
	 * Returns all the journal articles where DDMStructureKey = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param DDMStructureKeies the ddm structure keies
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByDDMStructureKey(
		String[] DDMStructureKeies) {

		return getPersistence().findByDDMStructureKey(DDMStructureKeies);
	}

	/**
	 * Returns a range of all the journal articles where DDMStructureKey = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param DDMStructureKeies the ddm structure keies
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByDDMStructureKey(
		String[] DDMStructureKeies, int start, int end) {

		return getPersistence().findByDDMStructureKey(
			DDMStructureKeies, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where DDMStructureKey = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param DDMStructureKeies the ddm structure keies
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByDDMStructureKey(
		String[] DDMStructureKeies, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByDDMStructureKey(
			DDMStructureKeies, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where DDMStructureKey = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param DDMStructureKey the ddm structure key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByDDMStructureKey(
		String[] DDMStructureKeies, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByDDMStructureKey(
			DDMStructureKeies, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the journal articles where DDMStructureKey = &#63; from the database.
	 *
	 * @param DDMStructureKey the ddm structure key
	 */
	public static void removeByDDMStructureKey(String DDMStructureKey) {
		getPersistence().removeByDDMStructureKey(DDMStructureKey);
	}

	/**
	 * Returns the number of journal articles where DDMStructureKey = &#63;.
	 *
	 * @param DDMStructureKey the ddm structure key
	 * @return the number of matching journal articles
	 */
	public static int countByDDMStructureKey(String DDMStructureKey) {
		return getPersistence().countByDDMStructureKey(DDMStructureKey);
	}

	/**
	 * Returns the number of journal articles where DDMStructureKey = any &#63;.
	 *
	 * @param DDMStructureKeies the ddm structure keies
	 * @return the number of matching journal articles
	 */
	public static int countByDDMStructureKey(String[] DDMStructureKeies) {
		return getPersistence().countByDDMStructureKey(DDMStructureKeies);
	}

	/**
	 * Returns all the journal articles where DDMTemplateKey = &#63;.
	 *
	 * @param DDMTemplateKey the ddm template key
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByDDMTemplateKey(
		String DDMTemplateKey) {

		return getPersistence().findByDDMTemplateKey(DDMTemplateKey);
	}

	/**
	 * Returns a range of all the journal articles where DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param DDMTemplateKey the ddm template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByDDMTemplateKey(
		String DDMTemplateKey, int start, int end) {

		return getPersistence().findByDDMTemplateKey(
			DDMTemplateKey, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param DDMTemplateKey the ddm template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByDDMTemplateKey(
		String DDMTemplateKey, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByDDMTemplateKey(
			DDMTemplateKey, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param DDMTemplateKey the ddm template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByDDMTemplateKey(
		String DDMTemplateKey, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByDDMTemplateKey(
			DDMTemplateKey, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where DDMTemplateKey = &#63;.
	 *
	 * @param DDMTemplateKey the ddm template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByDDMTemplateKey_First(
			String DDMTemplateKey,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByDDMTemplateKey_First(
			DDMTemplateKey, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where DDMTemplateKey = &#63;.
	 *
	 * @param DDMTemplateKey the ddm template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByDDMTemplateKey_First(
		String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByDDMTemplateKey_First(
			DDMTemplateKey, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where DDMTemplateKey = &#63;.
	 *
	 * @param DDMTemplateKey the ddm template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByDDMTemplateKey_Last(
			String DDMTemplateKey,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByDDMTemplateKey_Last(
			DDMTemplateKey, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where DDMTemplateKey = &#63;.
	 *
	 * @param DDMTemplateKey the ddm template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByDDMTemplateKey_Last(
		String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByDDMTemplateKey_Last(
			DDMTemplateKey, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where DDMTemplateKey = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param DDMTemplateKey the ddm template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByDDMTemplateKey_PrevAndNext(
			long id, String DDMTemplateKey,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByDDMTemplateKey_PrevAndNext(
			id, DDMTemplateKey, orderByComparator);
	}

	/**
	 * Removes all the journal articles where DDMTemplateKey = &#63; from the database.
	 *
	 * @param DDMTemplateKey the ddm template key
	 */
	public static void removeByDDMTemplateKey(String DDMTemplateKey) {
		getPersistence().removeByDDMTemplateKey(DDMTemplateKey);
	}

	/**
	 * Returns the number of journal articles where DDMTemplateKey = &#63;.
	 *
	 * @param DDMTemplateKey the ddm template key
	 * @return the number of matching journal articles
	 */
	public static int countByDDMTemplateKey(String DDMTemplateKey) {
		return getPersistence().countByDDMTemplateKey(DDMTemplateKey);
	}

	/**
	 * Returns all the journal articles where layoutUuid = &#63;.
	 *
	 * @param layoutUuid the layout uuid
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByLayoutUuid(String layoutUuid) {
		return getPersistence().findByLayoutUuid(layoutUuid);
	}

	/**
	 * Returns a range of all the journal articles where layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByLayoutUuid(
		String layoutUuid, int start, int end) {

		return getPersistence().findByLayoutUuid(layoutUuid, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByLayoutUuid(
		String layoutUuid, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByLayoutUuid(
			layoutUuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByLayoutUuid(
		String layoutUuid, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByLayoutUuid(
			layoutUuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where layoutUuid = &#63;.
	 *
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByLayoutUuid_First(
			String layoutUuid,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByLayoutUuid_First(
			layoutUuid, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where layoutUuid = &#63;.
	 *
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByLayoutUuid_First(
		String layoutUuid,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByLayoutUuid_First(
			layoutUuid, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where layoutUuid = &#63;.
	 *
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByLayoutUuid_Last(
			String layoutUuid,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByLayoutUuid_Last(
			layoutUuid, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where layoutUuid = &#63;.
	 *
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByLayoutUuid_Last(
		String layoutUuid,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByLayoutUuid_Last(
			layoutUuid, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where layoutUuid = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByLayoutUuid_PrevAndNext(
			long id, String layoutUuid,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByLayoutUuid_PrevAndNext(
			id, layoutUuid, orderByComparator);
	}

	/**
	 * Removes all the journal articles where layoutUuid = &#63; from the database.
	 *
	 * @param layoutUuid the layout uuid
	 */
	public static void removeByLayoutUuid(String layoutUuid) {
		getPersistence().removeByLayoutUuid(layoutUuid);
	}

	/**
	 * Returns the number of journal articles where layoutUuid = &#63;.
	 *
	 * @param layoutUuid the layout uuid
	 * @return the number of matching journal articles
	 */
	public static int countByLayoutUuid(String layoutUuid) {
		return getPersistence().countByLayoutUuid(layoutUuid);
	}

	/**
	 * Returns all the journal articles where smallImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findBySmallImageId(long smallImageId) {
		return getPersistence().findBySmallImageId(smallImageId);
	}

	/**
	 * Returns a range of all the journal articles where smallImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param smallImageId the small image ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findBySmallImageId(
		long smallImageId, int start, int end) {

		return getPersistence().findBySmallImageId(smallImageId, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where smallImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param smallImageId the small image ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findBySmallImageId(
		long smallImageId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findBySmallImageId(
			smallImageId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where smallImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param smallImageId the small image ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findBySmallImageId(
		long smallImageId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findBySmallImageId(
			smallImageId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where smallImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findBySmallImageId_First(
			long smallImageId,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findBySmallImageId_First(
			smallImageId, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where smallImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchBySmallImageId_First(
		long smallImageId,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchBySmallImageId_First(
			smallImageId, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where smallImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findBySmallImageId_Last(
			long smallImageId,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findBySmallImageId_Last(
			smallImageId, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where smallImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchBySmallImageId_Last(
		long smallImageId,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchBySmallImageId_Last(
			smallImageId, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where smallImageId = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param smallImageId the small image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findBySmallImageId_PrevAndNext(
			long id, long smallImageId,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findBySmallImageId_PrevAndNext(
			id, smallImageId, orderByComparator);
	}

	/**
	 * Removes all the journal articles where smallImageId = &#63; from the database.
	 *
	 * @param smallImageId the small image ID
	 */
	public static void removeBySmallImageId(long smallImageId) {
		getPersistence().removeBySmallImageId(smallImageId);
	}

	/**
	 * Returns the number of journal articles where smallImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @return the number of matching journal articles
	 */
	public static int countBySmallImageId(long smallImageId) {
		return getPersistence().countBySmallImageId(smallImageId);
	}

	/**
	 * Returns all the journal articles where resourcePrimKey = &#63; and indexable = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByR_I(
		long resourcePrimKey, boolean indexable) {

		return getPersistence().findByR_I(resourcePrimKey, indexable);
	}

	/**
	 * Returns a range of all the journal articles where resourcePrimKey = &#63; and indexable = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByR_I(
		long resourcePrimKey, boolean indexable, int start, int end) {

		return getPersistence().findByR_I(
			resourcePrimKey, indexable, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and indexable = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByR_I(
		long resourcePrimKey, boolean indexable, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByR_I(
			resourcePrimKey, indexable, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and indexable = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByR_I(
		long resourcePrimKey, boolean indexable, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByR_I(
			resourcePrimKey, indexable, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByR_I_First(
			long resourcePrimKey, boolean indexable,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByR_I_First(
			resourcePrimKey, indexable, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByR_I_First(
		long resourcePrimKey, boolean indexable,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByR_I_First(
			resourcePrimKey, indexable, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByR_I_Last(
			long resourcePrimKey, boolean indexable,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByR_I_Last(
			resourcePrimKey, indexable, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByR_I_Last(
		long resourcePrimKey, boolean indexable,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByR_I_Last(
			resourcePrimKey, indexable, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByR_I_PrevAndNext(
			long id, long resourcePrimKey, boolean indexable,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByR_I_PrevAndNext(
			id, resourcePrimKey, indexable, orderByComparator);
	}

	/**
	 * Removes all the journal articles where resourcePrimKey = &#63; and indexable = &#63; from the database.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 */
	public static void removeByR_I(long resourcePrimKey, boolean indexable) {
		getPersistence().removeByR_I(resourcePrimKey, indexable);
	}

	/**
	 * Returns the number of journal articles where resourcePrimKey = &#63; and indexable = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @return the number of matching journal articles
	 */
	public static int countByR_I(long resourcePrimKey, boolean indexable) {
		return getPersistence().countByR_I(resourcePrimKey, indexable);
	}

	/**
	 * Returns all the journal articles where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByR_ST(
		long resourcePrimKey, int status) {

		return getPersistence().findByR_ST(resourcePrimKey, status);
	}

	/**
	 * Returns a range of all the journal articles where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByR_ST(
		long resourcePrimKey, int status, int start, int end) {

		return getPersistence().findByR_ST(resourcePrimKey, status, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByR_ST(
		long resourcePrimKey, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByR_ST(
			resourcePrimKey, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByR_ST(
		long resourcePrimKey, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByR_ST(
			resourcePrimKey, status, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByR_ST_First(
			long resourcePrimKey, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByR_ST_First(
			resourcePrimKey, status, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByR_ST_First(
		long resourcePrimKey, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByR_ST_First(
			resourcePrimKey, status, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByR_ST_Last(
			long resourcePrimKey, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByR_ST_Last(
			resourcePrimKey, status, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByR_ST_Last(
		long resourcePrimKey, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByR_ST_Last(
			resourcePrimKey, status, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByR_ST_PrevAndNext(
			long id, long resourcePrimKey, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByR_ST_PrevAndNext(
			id, resourcePrimKey, status, orderByComparator);
	}

	/**
	 * Returns all the journal articles where resourcePrimKey = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param statuses the statuses
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByR_ST(
		long resourcePrimKey, int[] statuses) {

		return getPersistence().findByR_ST(resourcePrimKey, statuses);
	}

	/**
	 * Returns a range of all the journal articles where resourcePrimKey = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByR_ST(
		long resourcePrimKey, int[] statuses, int start, int end) {

		return getPersistence().findByR_ST(
			resourcePrimKey, statuses, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByR_ST(
		long resourcePrimKey, int[] statuses, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByR_ST(
			resourcePrimKey, statuses, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByR_ST(
		long resourcePrimKey, int[] statuses, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByR_ST(
			resourcePrimKey, statuses, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Removes all the journal articles where resourcePrimKey = &#63; and status = &#63; from the database.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 */
	public static void removeByR_ST(long resourcePrimKey, int status) {
		getPersistence().removeByR_ST(resourcePrimKey, status);
	}

	/**
	 * Returns the number of journal articles where resourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	public static int countByR_ST(long resourcePrimKey, int status) {
		return getPersistence().countByR_ST(resourcePrimKey, status);
	}

	/**
	 * Returns the number of journal articles where resourcePrimKey = &#63; and status = any &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param statuses the statuses
	 * @return the number of matching journal articles
	 */
	public static int countByR_ST(long resourcePrimKey, int[] statuses) {
		return getPersistence().countByR_ST(resourcePrimKey, statuses);
	}

	/**
	 * Returns all the journal articles where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByG_U(long groupId, long userId) {
		return getPersistence().findByG_U(groupId, userId);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByG_U(
		long groupId, long userId, int start, int end) {

		return getPersistence().findByG_U(groupId, userId, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_U(
		long groupId, long userId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByG_U(
			groupId, userId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_U(
		long groupId, long userId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_U(
			groupId, userId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_U_First(
			long groupId, long userId,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_U_First(
			groupId, userId, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_U_First(
		long groupId, long userId,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_U_First(
			groupId, userId, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_U_Last(
			long groupId, long userId,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_U_Last(
			groupId, userId, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_U_Last(
		long groupId, long userId,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_U_Last(
			groupId, userId, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByG_U_PrevAndNext(
			long id, long groupId, long userId,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_U_PrevAndNext(
			id, groupId, userId, orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_U(
		long groupId, long userId) {

		return getPersistence().filterFindByG_U(groupId, userId);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_U(
		long groupId, long userId, int start, int end) {

		return getPersistence().filterFindByG_U(groupId, userId, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_U(
		long groupId, long userId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().filterFindByG_U(
			groupId, userId, start, end, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] filterFindByG_U_PrevAndNext(
			long id, long groupId, long userId,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().filterFindByG_U_PrevAndNext(
			id, groupId, userId, orderByComparator);
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 */
	public static void removeByG_U(long groupId, long userId) {
		getPersistence().removeByG_U(groupId, userId);
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching journal articles
	 */
	public static int countByG_U(long groupId, long userId) {
		return getPersistence().countByG_U(groupId, userId);
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching journal articles that the user has permission to view
	 */
	public static int filterCountByG_U(long groupId, long userId) {
		return getPersistence().filterCountByG_U(groupId, userId);
	}

	/**
	 * Returns all the journal articles where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByG_F(long groupId, long folderId) {
		return getPersistence().findByG_F(groupId, folderId);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByG_F(
		long groupId, long folderId, int start, int end) {

		return getPersistence().findByG_F(groupId, folderId, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_F(
		long groupId, long folderId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByG_F(
			groupId, folderId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_F(
		long groupId, long folderId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_F(
			groupId, folderId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_F_First(
			long groupId, long folderId,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_F_First(
			groupId, folderId, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_F_First(
		long groupId, long folderId,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_F_First(
			groupId, folderId, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_F_Last(
			long groupId, long folderId,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_F_Last(
			groupId, folderId, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_F_Last(
		long groupId, long folderId,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_F_Last(
			groupId, folderId, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByG_F_PrevAndNext(
			long id, long groupId, long folderId,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_F_PrevAndNext(
			id, groupId, folderId, orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_F(
		long groupId, long folderId) {

		return getPersistence().filterFindByG_F(groupId, folderId);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_F(
		long groupId, long folderId, int start, int end) {

		return getPersistence().filterFindByG_F(groupId, folderId, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_F(
		long groupId, long folderId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().filterFindByG_F(
			groupId, folderId, start, end, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] filterFindByG_F_PrevAndNext(
			long id, long groupId, long folderId,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().filterFindByG_F_PrevAndNext(
			id, groupId, folderId, orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @return the matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_F(
		long groupId, long[] folderIds) {

		return getPersistence().filterFindByG_F(groupId, folderIds);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_F(
		long groupId, long[] folderIds, int start, int end) {

		return getPersistence().filterFindByG_F(groupId, folderIds, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_F(
		long groupId, long[] folderIds, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().filterFindByG_F(
			groupId, folderIds, start, end, orderByComparator);
	}

	/**
	 * Returns all the journal articles where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByG_F(
		long groupId, long[] folderIds) {

		return getPersistence().findByG_F(groupId, folderIds);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByG_F(
		long groupId, long[] folderIds, int start, int end) {

		return getPersistence().findByG_F(groupId, folderIds, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_F(
		long groupId, long[] folderIds, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByG_F(
			groupId, folderIds, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and folderId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_F(
		long groupId, long[] folderIds, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_F(
			groupId, folderIds, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and folderId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 */
	public static void removeByG_F(long groupId, long folderId) {
		getPersistence().removeByG_F(groupId, folderId);
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the number of matching journal articles
	 */
	public static int countByG_F(long groupId, long folderId) {
		return getPersistence().countByG_F(groupId, folderId);
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @return the number of matching journal articles
	 */
	public static int countByG_F(long groupId, long[] folderIds) {
		return getPersistence().countByG_F(groupId, folderIds);
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the number of matching journal articles that the user has permission to view
	 */
	public static int filterCountByG_F(long groupId, long folderId) {
		return getPersistence().filterCountByG_F(groupId, folderId);
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @return the number of matching journal articles that the user has permission to view
	 */
	public static int filterCountByG_F(long groupId, long[] folderIds) {
		return getPersistence().filterCountByG_F(groupId, folderIds);
	}

	/**
	 * Returns all the journal articles where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByG_A(
		long groupId, String articleId) {

		return getPersistence().findByG_A(groupId, articleId);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByG_A(
		long groupId, String articleId, int start, int end) {

		return getPersistence().findByG_A(groupId, articleId, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_A(
		long groupId, String articleId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByG_A(
			groupId, articleId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_A(
		long groupId, String articleId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_A(
			groupId, articleId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_A_First(
			long groupId, String articleId,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_A_First(
			groupId, articleId, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_A_First(
		long groupId, String articleId,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_A_First(
			groupId, articleId, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_A_Last(
			long groupId, String articleId,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_A_Last(
			groupId, articleId, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_A_Last(
		long groupId, String articleId,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_A_Last(
			groupId, articleId, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByG_A_PrevAndNext(
			long id, long groupId, String articleId,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_A_PrevAndNext(
			id, groupId, articleId, orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @return the matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_A(
		long groupId, String articleId) {

		return getPersistence().filterFindByG_A(groupId, articleId);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_A(
		long groupId, String articleId, int start, int end) {

		return getPersistence().filterFindByG_A(groupId, articleId, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_A(
		long groupId, String articleId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().filterFindByG_A(
			groupId, articleId, start, end, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and articleId = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] filterFindByG_A_PrevAndNext(
			long id, long groupId, String articleId,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().filterFindByG_A_PrevAndNext(
			id, groupId, articleId, orderByComparator);
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and articleId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 */
	public static void removeByG_A(long groupId, String articleId) {
		getPersistence().removeByG_A(groupId, articleId);
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @return the number of matching journal articles
	 */
	public static int countByG_A(long groupId, String articleId) {
		return getPersistence().countByG_A(groupId, articleId);
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @return the number of matching journal articles that the user has permission to view
	 */
	public static int filterCountByG_A(long groupId, String articleId) {
		return getPersistence().filterCountByG_A(groupId, articleId);
	}

	/**
	 * Returns all the journal articles where groupId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByG_UT(
		long groupId, String urlTitle) {

		return getPersistence().findByG_UT(groupId, urlTitle);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and urlTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByG_UT(
		long groupId, String urlTitle, int start, int end) {

		return getPersistence().findByG_UT(groupId, urlTitle, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and urlTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_UT(
		long groupId, String urlTitle, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByG_UT(
			groupId, urlTitle, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and urlTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_UT(
		long groupId, String urlTitle, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_UT(
			groupId, urlTitle, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_UT_First(
			long groupId, String urlTitle,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_UT_First(
			groupId, urlTitle, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_UT_First(
		long groupId, String urlTitle,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_UT_First(
			groupId, urlTitle, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_UT_Last(
			long groupId, String urlTitle,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_UT_Last(
			groupId, urlTitle, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_UT_Last(
		long groupId, String urlTitle,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_UT_Last(
			groupId, urlTitle, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and urlTitle = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByG_UT_PrevAndNext(
			long id, long groupId, String urlTitle,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_UT_PrevAndNext(
			id, groupId, urlTitle, orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @return the matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_UT(
		long groupId, String urlTitle) {

		return getPersistence().filterFindByG_UT(groupId, urlTitle);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and urlTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_UT(
		long groupId, String urlTitle, int start, int end) {

		return getPersistence().filterFindByG_UT(groupId, urlTitle, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and urlTitle = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_UT(
		long groupId, String urlTitle, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().filterFindByG_UT(
			groupId, urlTitle, start, end, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and urlTitle = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] filterFindByG_UT_PrevAndNext(
			long id, long groupId, String urlTitle,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().filterFindByG_UT_PrevAndNext(
			id, groupId, urlTitle, orderByComparator);
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and urlTitle = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 */
	public static void removeByG_UT(long groupId, String urlTitle) {
		getPersistence().removeByG_UT(groupId, urlTitle);
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @return the number of matching journal articles
	 */
	public static int countByG_UT(long groupId, String urlTitle) {
		return getPersistence().countByG_UT(groupId, urlTitle);
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and urlTitle = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @return the number of matching journal articles that the user has permission to view
	 */
	public static int filterCountByG_UT(long groupId, String urlTitle) {
		return getPersistence().filterCountByG_UT(groupId, urlTitle);
	}

	/**
	 * Returns all the journal articles where groupId = &#63; and DDMStructureKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMStructureKey the ddm structure key
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByG_DDMSK(
		long groupId, String DDMStructureKey) {

		return getPersistence().findByG_DDMSK(groupId, DDMStructureKey);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and DDMStructureKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param DDMStructureKey the ddm structure key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByG_DDMSK(
		long groupId, String DDMStructureKey, int start, int end) {

		return getPersistence().findByG_DDMSK(
			groupId, DDMStructureKey, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and DDMStructureKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param DDMStructureKey the ddm structure key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_DDMSK(
		long groupId, String DDMStructureKey, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByG_DDMSK(
			groupId, DDMStructureKey, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and DDMStructureKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param DDMStructureKey the ddm structure key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_DDMSK(
		long groupId, String DDMStructureKey, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_DDMSK(
			groupId, DDMStructureKey, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and DDMStructureKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMStructureKey the ddm structure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_DDMSK_First(
			long groupId, String DDMStructureKey,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_DDMSK_First(
			groupId, DDMStructureKey, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and DDMStructureKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMStructureKey the ddm structure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_DDMSK_First(
		long groupId, String DDMStructureKey,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_DDMSK_First(
			groupId, DDMStructureKey, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and DDMStructureKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMStructureKey the ddm structure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_DDMSK_Last(
			long groupId, String DDMStructureKey,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_DDMSK_Last(
			groupId, DDMStructureKey, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and DDMStructureKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMStructureKey the ddm structure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_DDMSK_Last(
		long groupId, String DDMStructureKey,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_DDMSK_Last(
			groupId, DDMStructureKey, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and DDMStructureKey = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param DDMStructureKey the ddm structure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByG_DDMSK_PrevAndNext(
			long id, long groupId, String DDMStructureKey,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_DDMSK_PrevAndNext(
			id, groupId, DDMStructureKey, orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and DDMStructureKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMStructureKey the ddm structure key
	 * @return the matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_DDMSK(
		long groupId, String DDMStructureKey) {

		return getPersistence().filterFindByG_DDMSK(groupId, DDMStructureKey);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and DDMStructureKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param DDMStructureKey the ddm structure key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_DDMSK(
		long groupId, String DDMStructureKey, int start, int end) {

		return getPersistence().filterFindByG_DDMSK(
			groupId, DDMStructureKey, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and DDMStructureKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param DDMStructureKey the ddm structure key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_DDMSK(
		long groupId, String DDMStructureKey, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().filterFindByG_DDMSK(
			groupId, DDMStructureKey, start, end, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and DDMStructureKey = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param DDMStructureKey the ddm structure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] filterFindByG_DDMSK_PrevAndNext(
			long id, long groupId, String DDMStructureKey,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().filterFindByG_DDMSK_PrevAndNext(
			id, groupId, DDMStructureKey, orderByComparator);
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and DDMStructureKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param DDMStructureKey the ddm structure key
	 */
	public static void removeByG_DDMSK(long groupId, String DDMStructureKey) {
		getPersistence().removeByG_DDMSK(groupId, DDMStructureKey);
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and DDMStructureKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMStructureKey the ddm structure key
	 * @return the number of matching journal articles
	 */
	public static int countByG_DDMSK(long groupId, String DDMStructureKey) {
		return getPersistence().countByG_DDMSK(groupId, DDMStructureKey);
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and DDMStructureKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMStructureKey the ddm structure key
	 * @return the number of matching journal articles that the user has permission to view
	 */
	public static int filterCountByG_DDMSK(
		long groupId, String DDMStructureKey) {

		return getPersistence().filterCountByG_DDMSK(groupId, DDMStructureKey);
	}

	/**
	 * Returns all the journal articles where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the ddm template key
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByG_DDMTK(
		long groupId, String DDMTemplateKey) {

		return getPersistence().findByG_DDMTK(groupId, DDMTemplateKey);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the ddm template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByG_DDMTK(
		long groupId, String DDMTemplateKey, int start, int end) {

		return getPersistence().findByG_DDMTK(
			groupId, DDMTemplateKey, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the ddm template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_DDMTK(
		long groupId, String DDMTemplateKey, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByG_DDMTK(
			groupId, DDMTemplateKey, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the ddm template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_DDMTK(
		long groupId, String DDMTemplateKey, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_DDMTK(
			groupId, DDMTemplateKey, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the ddm template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_DDMTK_First(
			long groupId, String DDMTemplateKey,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_DDMTK_First(
			groupId, DDMTemplateKey, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the ddm template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_DDMTK_First(
		long groupId, String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_DDMTK_First(
			groupId, DDMTemplateKey, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the ddm template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_DDMTK_Last(
			long groupId, String DDMTemplateKey,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_DDMTK_Last(
			groupId, DDMTemplateKey, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the ddm template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_DDMTK_Last(
		long groupId, String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_DDMTK_Last(
			groupId, DDMTemplateKey, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param DDMTemplateKey the ddm template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByG_DDMTK_PrevAndNext(
			long id, long groupId, String DDMTemplateKey,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_DDMTK_PrevAndNext(
			id, groupId, DDMTemplateKey, orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the ddm template key
	 * @return the matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_DDMTK(
		long groupId, String DDMTemplateKey) {

		return getPersistence().filterFindByG_DDMTK(groupId, DDMTemplateKey);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the ddm template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_DDMTK(
		long groupId, String DDMTemplateKey, int start, int end) {

		return getPersistence().filterFindByG_DDMTK(
			groupId, DDMTemplateKey, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the ddm template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_DDMTK(
		long groupId, String DDMTemplateKey, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().filterFindByG_DDMTK(
			groupId, DDMTemplateKey, start, end, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param DDMTemplateKey the ddm template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] filterFindByG_DDMTK_PrevAndNext(
			long id, long groupId, String DDMTemplateKey,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().filterFindByG_DDMTK_PrevAndNext(
			id, groupId, DDMTemplateKey, orderByComparator);
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and DDMTemplateKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the ddm template key
	 */
	public static void removeByG_DDMTK(long groupId, String DDMTemplateKey) {
		getPersistence().removeByG_DDMTK(groupId, DDMTemplateKey);
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the ddm template key
	 * @return the number of matching journal articles
	 */
	public static int countByG_DDMTK(long groupId, String DDMTemplateKey) {
		return getPersistence().countByG_DDMTK(groupId, DDMTemplateKey);
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param DDMTemplateKey the ddm template key
	 * @return the number of matching journal articles that the user has permission to view
	 */
	public static int filterCountByG_DDMTK(
		long groupId, String DDMTemplateKey) {

		return getPersistence().filterCountByG_DDMTK(groupId, DDMTemplateKey);
	}

	/**
	 * Returns all the journal articles where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByG_L(
		long groupId, String layoutUuid) {

		return getPersistence().findByG_L(groupId, layoutUuid);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByG_L(
		long groupId, String layoutUuid, int start, int end) {

		return getPersistence().findByG_L(groupId, layoutUuid, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_L(
		long groupId, String layoutUuid, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByG_L(
			groupId, layoutUuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_L(
		long groupId, String layoutUuid, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_L(
			groupId, layoutUuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_L_First(
			long groupId, String layoutUuid,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_L_First(
			groupId, layoutUuid, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_L_First(
		long groupId, String layoutUuid,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_L_First(
			groupId, layoutUuid, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_L_Last(
			long groupId, String layoutUuid,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_L_Last(
			groupId, layoutUuid, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_L_Last(
		long groupId, String layoutUuid,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_L_Last(
			groupId, layoutUuid, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByG_L_PrevAndNext(
			long id, long groupId, String layoutUuid,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_L_PrevAndNext(
			id, groupId, layoutUuid, orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @return the matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_L(
		long groupId, String layoutUuid) {

		return getPersistence().filterFindByG_L(groupId, layoutUuid);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_L(
		long groupId, String layoutUuid, int start, int end) {

		return getPersistence().filterFindByG_L(
			groupId, layoutUuid, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_L(
		long groupId, String layoutUuid, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().filterFindByG_L(
			groupId, layoutUuid, start, end, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] filterFindByG_L_PrevAndNext(
			long id, long groupId, String layoutUuid,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().filterFindByG_L_PrevAndNext(
			id, groupId, layoutUuid, orderByComparator);
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and layoutUuid = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 */
	public static void removeByG_L(long groupId, String layoutUuid) {
		getPersistence().removeByG_L(groupId, layoutUuid);
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @return the number of matching journal articles
	 */
	public static int countByG_L(long groupId, String layoutUuid) {
		return getPersistence().countByG_L(groupId, layoutUuid);
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @return the number of matching journal articles that the user has permission to view
	 */
	public static int filterCountByG_L(long groupId, String layoutUuid) {
		return getPersistence().filterCountByG_L(groupId, layoutUuid);
	}

	/**
	 * Returns all the journal articles where groupId = &#63; and layoutUuid &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByG_NotL(
		long groupId, String layoutUuid) {

		return getPersistence().findByG_NotL(groupId, layoutUuid);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and layoutUuid &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByG_NotL(
		long groupId, String layoutUuid, int start, int end) {

		return getPersistence().findByG_NotL(groupId, layoutUuid, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and layoutUuid &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_NotL(
		long groupId, String layoutUuid, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByG_NotL(
			groupId, layoutUuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and layoutUuid &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_NotL(
		long groupId, String layoutUuid, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_NotL(
			groupId, layoutUuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and layoutUuid &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_NotL_First(
			long groupId, String layoutUuid,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_NotL_First(
			groupId, layoutUuid, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and layoutUuid &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_NotL_First(
		long groupId, String layoutUuid,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_NotL_First(
			groupId, layoutUuid, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and layoutUuid &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_NotL_Last(
			long groupId, String layoutUuid,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_NotL_Last(
			groupId, layoutUuid, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and layoutUuid &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_NotL_Last(
		long groupId, String layoutUuid,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_NotL_Last(
			groupId, layoutUuid, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and layoutUuid &ne; &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByG_NotL_PrevAndNext(
			long id, long groupId, String layoutUuid,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_NotL_PrevAndNext(
			id, groupId, layoutUuid, orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and layoutUuid &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @return the matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_NotL(
		long groupId, String layoutUuid) {

		return getPersistence().filterFindByG_NotL(groupId, layoutUuid);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and layoutUuid &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_NotL(
		long groupId, String layoutUuid, int start, int end) {

		return getPersistence().filterFindByG_NotL(
			groupId, layoutUuid, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and layoutUuid &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_NotL(
		long groupId, String layoutUuid, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().filterFindByG_NotL(
			groupId, layoutUuid, start, end, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and layoutUuid &ne; &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] filterFindByG_NotL_PrevAndNext(
			long id, long groupId, String layoutUuid,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().filterFindByG_NotL_PrevAndNext(
			id, groupId, layoutUuid, orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and layoutUuid &ne; all &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuids the layout uuids
	 * @return the matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_NotL(
		long groupId, String[] layoutUuids) {

		return getPersistence().filterFindByG_NotL(groupId, layoutUuids);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and layoutUuid &ne; all &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutUuids the layout uuids
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_NotL(
		long groupId, String[] layoutUuids, int start, int end) {

		return getPersistence().filterFindByG_NotL(
			groupId, layoutUuids, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permission to view where groupId = &#63; and layoutUuid &ne; all &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutUuids the layout uuids
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_NotL(
		long groupId, String[] layoutUuids, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().filterFindByG_NotL(
			groupId, layoutUuids, start, end, orderByComparator);
	}

	/**
	 * Returns all the journal articles where groupId = &#63; and layoutUuid &ne; all &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutUuids the layout uuids
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByG_NotL(
		long groupId, String[] layoutUuids) {

		return getPersistence().findByG_NotL(groupId, layoutUuids);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and layoutUuid &ne; all &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutUuids the layout uuids
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByG_NotL(
		long groupId, String[] layoutUuids, int start, int end) {

		return getPersistence().findByG_NotL(groupId, layoutUuids, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and layoutUuid &ne; all &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutUuids the layout uuids
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_NotL(
		long groupId, String[] layoutUuids, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByG_NotL(
			groupId, layoutUuids, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and layoutUuid &ne; &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_NotL(
		long groupId, String[] layoutUuids, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_NotL(
			groupId, layoutUuids, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and layoutUuid &ne; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 */
	public static void removeByG_NotL(long groupId, String layoutUuid) {
		getPersistence().removeByG_NotL(groupId, layoutUuid);
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and layoutUuid &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @return the number of matching journal articles
	 */
	public static int countByG_NotL(long groupId, String layoutUuid) {
		return getPersistence().countByG_NotL(groupId, layoutUuid);
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and layoutUuid &ne; all &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuids the layout uuids
	 * @return the number of matching journal articles
	 */
	public static int countByG_NotL(long groupId, String[] layoutUuids) {
		return getPersistence().countByG_NotL(groupId, layoutUuids);
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and layoutUuid &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuid the layout uuid
	 * @return the number of matching journal articles that the user has permission to view
	 */
	public static int filterCountByG_NotL(long groupId, String layoutUuid) {
		return getPersistence().filterCountByG_NotL(groupId, layoutUuid);
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and layoutUuid &ne; all &#63;.
	 *
	 * @param groupId the group ID
	 * @param layoutUuids the layout uuids
	 * @return the number of matching journal articles that the user has permission to view
	 */
	public static int filterCountByG_NotL(long groupId, String[] layoutUuids) {
		return getPersistence().filterCountByG_NotL(groupId, layoutUuids);
	}

	/**
	 * Returns all the journal articles where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByG_ST(long groupId, int status) {
		return getPersistence().findByG_ST(groupId, status);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByG_ST(
		long groupId, int status, int start, int end) {

		return getPersistence().findByG_ST(groupId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_ST(
		long groupId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByG_ST(
			groupId, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_ST(
		long groupId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_ST(
			groupId, status, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_ST_First(
			long groupId, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_ST_First(
			groupId, status, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_ST_First(
		long groupId, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_ST_First(
			groupId, status, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_ST_Last(
			long groupId, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_ST_Last(
			groupId, status, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_ST_Last(
		long groupId, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_ST_Last(
			groupId, status, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByG_ST_PrevAndNext(
			long id, long groupId, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_ST_PrevAndNext(
			id, groupId, status, orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_ST(
		long groupId, int status) {

		return getPersistence().filterFindByG_ST(groupId, status);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_ST(
		long groupId, int status, int start, int end) {

		return getPersistence().filterFindByG_ST(groupId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_ST(
		long groupId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().filterFindByG_ST(
			groupId, status, start, end, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] filterFindByG_ST_PrevAndNext(
			long id, long groupId, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().filterFindByG_ST_PrevAndNext(
			id, groupId, status, orderByComparator);
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 */
	public static void removeByG_ST(long groupId, int status) {
		getPersistence().removeByG_ST(groupId, status);
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	public static int countByG_ST(long groupId, int status) {
		return getPersistence().countByG_ST(groupId, status);
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching journal articles that the user has permission to view
	 */
	public static int filterCountByG_ST(long groupId, int status) {
		return getPersistence().filterCountByG_ST(groupId, status);
	}

	/**
	 * Returns all the journal articles where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByC_V(
		long companyId, double version) {

		return getPersistence().findByC_V(companyId, version);
	}

	/**
	 * Returns a range of all the journal articles where companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByC_V(
		long companyId, double version, int start, int end) {

		return getPersistence().findByC_V(companyId, version, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByC_V(
		long companyId, double version, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByC_V(
			companyId, version, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByC_V(
		long companyId, double version, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_V(
			companyId, version, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByC_V_First(
			long companyId, double version,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByC_V_First(
			companyId, version, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByC_V_First(
		long companyId, double version,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByC_V_First(
			companyId, version, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByC_V_Last(
			long companyId, double version,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByC_V_Last(
			companyId, version, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByC_V_Last(
		long companyId, double version,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByC_V_Last(
			companyId, version, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where companyId = &#63; and version = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByC_V_PrevAndNext(
			long id, long companyId, double version,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByC_V_PrevAndNext(
			id, companyId, version, orderByComparator);
	}

	/**
	 * Removes all the journal articles where companyId = &#63; and version = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 */
	public static void removeByC_V(long companyId, double version) {
		getPersistence().removeByC_V(companyId, version);
	}

	/**
	 * Returns the number of journal articles where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @return the number of matching journal articles
	 */
	public static int countByC_V(long companyId, double version) {
		return getPersistence().countByC_V(companyId, version);
	}

	/**
	 * Returns all the journal articles where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByC_ST(long companyId, int status) {
		return getPersistence().findByC_ST(companyId, status);
	}

	/**
	 * Returns a range of all the journal articles where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByC_ST(
		long companyId, int status, int start, int end) {

		return getPersistence().findByC_ST(companyId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByC_ST(
		long companyId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByC_ST(
			companyId, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByC_ST(
		long companyId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_ST(
			companyId, status, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByC_ST_First(
			long companyId, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByC_ST_First(
			companyId, status, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByC_ST_First(
		long companyId, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByC_ST_First(
			companyId, status, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByC_ST_Last(
			long companyId, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByC_ST_Last(
			companyId, status, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByC_ST_Last(
		long companyId, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByC_ST_Last(
			companyId, status, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByC_ST_PrevAndNext(
			long id, long companyId, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByC_ST_PrevAndNext(
			id, companyId, status, orderByComparator);
	}

	/**
	 * Removes all the journal articles where companyId = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 */
	public static void removeByC_ST(long companyId, int status) {
		getPersistence().removeByC_ST(companyId, status);
	}

	/**
	 * Returns the number of journal articles where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	public static int countByC_ST(long companyId, int status) {
		return getPersistence().countByC_ST(companyId, status);
	}

	/**
	 * Returns all the journal articles where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByC_NotST(
		long companyId, int status) {

		return getPersistence().findByC_NotST(companyId, status);
	}

	/**
	 * Returns a range of all the journal articles where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByC_NotST(
		long companyId, int status, int start, int end) {

		return getPersistence().findByC_NotST(companyId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByC_NotST(
		long companyId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByC_NotST(
			companyId, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByC_NotST(
		long companyId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_NotST(
			companyId, status, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByC_NotST_First(
			long companyId, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByC_NotST_First(
			companyId, status, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByC_NotST_First(
		long companyId, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByC_NotST_First(
			companyId, status, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByC_NotST_Last(
			long companyId, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByC_NotST_Last(
			companyId, status, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByC_NotST_Last(
		long companyId, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByC_NotST_Last(
			companyId, status, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByC_NotST_PrevAndNext(
			long id, long companyId, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByC_NotST_PrevAndNext(
			id, companyId, status, orderByComparator);
	}

	/**
	 * Removes all the journal articles where companyId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 */
	public static void removeByC_NotST(long companyId, int status) {
		getPersistence().removeByC_NotST(companyId, status);
	}

	/**
	 * Returns the number of journal articles where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	public static int countByC_NotST(long companyId, int status) {
		return getPersistence().countByC_NotST(companyId, status);
	}

	/**
	 * Returns all the journal articles where classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByC_DDMTK(
		long classNameId, String DDMTemplateKey) {

		return getPersistence().findByC_DDMTK(classNameId, DDMTemplateKey);
	}

	/**
	 * Returns a range of all the journal articles where classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByC_DDMTK(
		long classNameId, String DDMTemplateKey, int start, int end) {

		return getPersistence().findByC_DDMTK(
			classNameId, DDMTemplateKey, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByC_DDMTK(
		long classNameId, String DDMTemplateKey, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByC_DDMTK(
			classNameId, DDMTemplateKey, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByC_DDMTK(
		long classNameId, String DDMTemplateKey, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_DDMTK(
			classNameId, DDMTemplateKey, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByC_DDMTK_First(
			long classNameId, String DDMTemplateKey,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByC_DDMTK_First(
			classNameId, DDMTemplateKey, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByC_DDMTK_First(
		long classNameId, String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByC_DDMTK_First(
			classNameId, DDMTemplateKey, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByC_DDMTK_Last(
			long classNameId, String DDMTemplateKey,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByC_DDMTK_Last(
			classNameId, DDMTemplateKey, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByC_DDMTK_Last(
		long classNameId, String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByC_DDMTK_Last(
			classNameId, DDMTemplateKey, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByC_DDMTK_PrevAndNext(
			long id, long classNameId, String DDMTemplateKey,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByC_DDMTK_PrevAndNext(
			id, classNameId, DDMTemplateKey, orderByComparator);
	}

	/**
	 * Removes all the journal articles where classNameId = &#63; and DDMTemplateKey = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 */
	public static void removeByC_DDMTK(
		long classNameId, String DDMTemplateKey) {

		getPersistence().removeByC_DDMTK(classNameId, DDMTemplateKey);
	}

	/**
	 * Returns the number of journal articles where classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @return the number of matching journal articles
	 */
	public static int countByC_DDMTK(long classNameId, String DDMTemplateKey) {
		return getPersistence().countByC_DDMTK(classNameId, DDMTemplateKey);
	}

	/**
	 * Returns all the journal articles where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByLtD_S(
		Date displayDate, int status) {

		return getPersistence().findByLtD_S(displayDate, status);
	}

	/**
	 * Returns a range of all the journal articles where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByLtD_S(
		Date displayDate, int status, int start, int end) {

		return getPersistence().findByLtD_S(displayDate, status, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByLtD_S(
			displayDate, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByLtD_S(
			displayDate, status, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByLtD_S_First(
			Date displayDate, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByLtD_S_First(
			displayDate, status, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByLtD_S_First(
		Date displayDate, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByLtD_S_First(
			displayDate, status, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByLtD_S_Last(
			Date displayDate, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByLtD_S_Last(
			displayDate, status, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByLtD_S_Last(
		Date displayDate, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByLtD_S_Last(
			displayDate, status, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByLtD_S_PrevAndNext(
			long id, Date displayDate, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByLtD_S_PrevAndNext(
			id, displayDate, status, orderByComparator);
	}

	/**
	 * Removes all the journal articles where displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 */
	public static void removeByLtD_S(Date displayDate, int status) {
		getPersistence().removeByLtD_S(displayDate, status);
	}

	/**
	 * Returns the number of journal articles where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	public static int countByLtD_S(Date displayDate, int status) {
		return getPersistence().countByLtD_S(displayDate, status);
	}

	/**
	 * Returns all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param status the status
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByR_I_S(
		long resourcePrimKey, boolean indexable, int status) {

		return getPersistence().findByR_I_S(resourcePrimKey, indexable, status);
	}

	/**
	 * Returns a range of all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByR_I_S(
		long resourcePrimKey, boolean indexable, int status, int start,
		int end) {

		return getPersistence().findByR_I_S(
			resourcePrimKey, indexable, status, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByR_I_S(
		long resourcePrimKey, boolean indexable, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByR_I_S(
			resourcePrimKey, indexable, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByR_I_S(
		long resourcePrimKey, boolean indexable, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByR_I_S(
			resourcePrimKey, indexable, status, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByR_I_S_First(
			long resourcePrimKey, boolean indexable, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByR_I_S_First(
			resourcePrimKey, indexable, status, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByR_I_S_First(
		long resourcePrimKey, boolean indexable, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByR_I_S_First(
			resourcePrimKey, indexable, status, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByR_I_S_Last(
			long resourcePrimKey, boolean indexable, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByR_I_S_Last(
			resourcePrimKey, indexable, status, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByR_I_S_Last(
		long resourcePrimKey, boolean indexable, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByR_I_S_Last(
			resourcePrimKey, indexable, status, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByR_I_S_PrevAndNext(
			long id, long resourcePrimKey, boolean indexable, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByR_I_S_PrevAndNext(
			id, resourcePrimKey, indexable, status, orderByComparator);
	}

	/**
	 * Returns all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param statuses the statuses
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByR_I_S(
		long resourcePrimKey, boolean indexable, int[] statuses) {

		return getPersistence().findByR_I_S(
			resourcePrimKey, indexable, statuses);
	}

	/**
	 * Returns a range of all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByR_I_S(
		long resourcePrimKey, boolean indexable, int[] statuses, int start,
		int end) {

		return getPersistence().findByR_I_S(
			resourcePrimKey, indexable, statuses, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByR_I_S(
		long resourcePrimKey, boolean indexable, int[] statuses, int start,
		int end, OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByR_I_S(
			resourcePrimKey, indexable, statuses, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByR_I_S(
		long resourcePrimKey, boolean indexable, int[] statuses, int start,
		int end, OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByR_I_S(
			resourcePrimKey, indexable, statuses, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Removes all the journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = &#63; from the database.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param status the status
	 */
	public static void removeByR_I_S(
		long resourcePrimKey, boolean indexable, int status) {

		getPersistence().removeByR_I_S(resourcePrimKey, indexable, status);
	}

	/**
	 * Returns the number of journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	public static int countByR_I_S(
		long resourcePrimKey, boolean indexable, int status) {

		return getPersistence().countByR_I_S(
			resourcePrimKey, indexable, status);
	}

	/**
	 * Returns the number of journal articles where resourcePrimKey = &#63; and indexable = &#63; and status = any &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param indexable the indexable
	 * @param statuses the statuses
	 * @return the number of matching journal articles
	 */
	public static int countByR_I_S(
		long resourcePrimKey, boolean indexable, int[] statuses) {

		return getPersistence().countByR_I_S(
			resourcePrimKey, indexable, statuses);
	}

	/**
	 * Returns all the journal articles where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByG_U_C(
		long groupId, long userId, long classNameId) {

		return getPersistence().findByG_U_C(groupId, userId, classNameId);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByG_U_C(
		long groupId, long userId, long classNameId, int start, int end) {

		return getPersistence().findByG_U_C(
			groupId, userId, classNameId, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_U_C(
		long groupId, long userId, long classNameId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByG_U_C(
			groupId, userId, classNameId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_U_C(
		long groupId, long userId, long classNameId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_U_C(
			groupId, userId, classNameId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_U_C_First(
			long groupId, long userId, long classNameId,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_U_C_First(
			groupId, userId, classNameId, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_U_C_First(
		long groupId, long userId, long classNameId,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_U_C_First(
			groupId, userId, classNameId, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_U_C_Last(
			long groupId, long userId, long classNameId,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_U_C_Last(
			groupId, userId, classNameId, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_U_C_Last(
		long groupId, long userId, long classNameId,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_U_C_Last(
			groupId, userId, classNameId, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByG_U_C_PrevAndNext(
			long id, long groupId, long userId, long classNameId,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_U_C_PrevAndNext(
			id, groupId, userId, classNameId, orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @return the matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_U_C(
		long groupId, long userId, long classNameId) {

		return getPersistence().filterFindByG_U_C(groupId, userId, classNameId);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_U_C(
		long groupId, long userId, long classNameId, int start, int end) {

		return getPersistence().filterFindByG_U_C(
			groupId, userId, classNameId, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_U_C(
		long groupId, long userId, long classNameId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().filterFindByG_U_C(
			groupId, userId, classNameId, start, end, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] filterFindByG_U_C_PrevAndNext(
			long id, long groupId, long userId, long classNameId,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().filterFindByG_U_C_PrevAndNext(
			id, groupId, userId, classNameId, orderByComparator);
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and userId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 */
	public static void removeByG_U_C(
		long groupId, long userId, long classNameId) {

		getPersistence().removeByG_U_C(groupId, userId, classNameId);
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @return the number of matching journal articles
	 */
	public static int countByG_U_C(
		long groupId, long userId, long classNameId) {

		return getPersistence().countByG_U_C(groupId, userId, classNameId);
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and userId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @return the number of matching journal articles that the user has permission to view
	 */
	public static int filterCountByG_U_C(
		long groupId, long userId, long classNameId) {

		return getPersistence().filterCountByG_U_C(
			groupId, userId, classNameId);
	}

	/**
	 * Returns all the journal articles where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByG_F_ST(
		long groupId, long folderId, int status) {

		return getPersistence().findByG_F_ST(groupId, folderId, status);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByG_F_ST(
		long groupId, long folderId, int status, int start, int end) {

		return getPersistence().findByG_F_ST(
			groupId, folderId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_F_ST(
		long groupId, long folderId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByG_F_ST(
			groupId, folderId, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_F_ST(
		long groupId, long folderId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_F_ST(
			groupId, folderId, status, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_F_ST_First(
			long groupId, long folderId, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_F_ST_First(
			groupId, folderId, status, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_F_ST_First(
		long groupId, long folderId, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_F_ST_First(
			groupId, folderId, status, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_F_ST_Last(
			long groupId, long folderId, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_F_ST_Last(
			groupId, folderId, status, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_F_ST_Last(
		long groupId, long folderId, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_F_ST_Last(
			groupId, folderId, status, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByG_F_ST_PrevAndNext(
			long id, long groupId, long folderId, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_F_ST_PrevAndNext(
			id, groupId, folderId, status, orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @return the matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_F_ST(
		long groupId, long folderId, int status) {

		return getPersistence().filterFindByG_F_ST(groupId, folderId, status);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_F_ST(
		long groupId, long folderId, int status, int start, int end) {

		return getPersistence().filterFindByG_F_ST(
			groupId, folderId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_F_ST(
		long groupId, long folderId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().filterFindByG_F_ST(
			groupId, folderId, status, start, end, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] filterFindByG_F_ST_PrevAndNext(
			long id, long groupId, long folderId, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().filterFindByG_F_ST_PrevAndNext(
			id, groupId, folderId, status, orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and status = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param statuses the statuses
	 * @return the matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_F_ST(
		long groupId, long folderId, int[] statuses) {

		return getPersistence().filterFindByG_F_ST(groupId, folderId, statuses);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_F_ST(
		long groupId, long folderId, int[] statuses, int start, int end) {

		return getPersistence().filterFindByG_F_ST(
			groupId, folderId, statuses, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_F_ST(
		long groupId, long folderId, int[] statuses, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().filterFindByG_F_ST(
			groupId, folderId, statuses, start, end, orderByComparator);
	}

	/**
	 * Returns all the journal articles where groupId = &#63; and folderId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param statuses the statuses
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByG_F_ST(
		long groupId, long folderId, int[] statuses) {

		return getPersistence().findByG_F_ST(groupId, folderId, statuses);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and folderId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByG_F_ST(
		long groupId, long folderId, int[] statuses, int start, int end) {

		return getPersistence().findByG_F_ST(
			groupId, folderId, statuses, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and folderId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_F_ST(
		long groupId, long folderId, int[] statuses, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByG_F_ST(
			groupId, folderId, statuses, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and folderId = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_F_ST(
		long groupId, long folderId, int[] statuses, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_F_ST(
			groupId, folderId, statuses, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and folderId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 */
	public static void removeByG_F_ST(long groupId, long folderId, int status) {
		getPersistence().removeByG_F_ST(groupId, folderId, status);
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	public static int countByG_F_ST(long groupId, long folderId, int status) {
		return getPersistence().countByG_F_ST(groupId, folderId, status);
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and folderId = &#63; and status = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param statuses the statuses
	 * @return the number of matching journal articles
	 */
	public static int countByG_F_ST(
		long groupId, long folderId, int[] statuses) {

		return getPersistence().countByG_F_ST(groupId, folderId, statuses);
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @return the number of matching journal articles that the user has permission to view
	 */
	public static int filterCountByG_F_ST(
		long groupId, long folderId, int status) {

		return getPersistence().filterCountByG_F_ST(groupId, folderId, status);
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and folderId = &#63; and status = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param statuses the statuses
	 * @return the number of matching journal articles that the user has permission to view
	 */
	public static int filterCountByG_F_ST(
		long groupId, long folderId, int[] statuses) {

		return getPersistence().filterCountByG_F_ST(
			groupId, folderId, statuses);
	}

	/**
	 * Returns all the journal articles where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByG_C_C(
		long groupId, long classNameId, long classPK) {

		return getPersistence().findByG_C_C(groupId, classNameId, classPK);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end) {

		return getPersistence().findByG_C_C(
			groupId, classNameId, classPK, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByG_C_C(
			groupId, classNameId, classPK, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_C_C(
			groupId, classNameId, classPK, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_C_C_First(
			long groupId, long classNameId, long classPK,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_C_C_First(
			groupId, classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_C_C_First(
		long groupId, long classNameId, long classPK,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_C_C_First(
			groupId, classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_C_C_Last(
			long groupId, long classNameId, long classPK,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_C_C_Last(
			groupId, classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_C_C_Last(
		long groupId, long classNameId, long classPK,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_C_C_Last(
			groupId, classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByG_C_C_PrevAndNext(
			long id, long groupId, long classNameId, long classPK,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_C_C_PrevAndNext(
			id, groupId, classNameId, classPK, orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_C_C(
		long groupId, long classNameId, long classPK) {

		return getPersistence().filterFindByG_C_C(
			groupId, classNameId, classPK);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end) {

		return getPersistence().filterFindByG_C_C(
			groupId, classNameId, classPK, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().filterFindByG_C_C(
			groupId, classNameId, classPK, start, end, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] filterFindByG_C_C_PrevAndNext(
			long id, long groupId, long classNameId, long classPK,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().filterFindByG_C_C_PrevAndNext(
			id, groupId, classNameId, classPK, orderByComparator);
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public static void removeByG_C_C(
		long groupId, long classNameId, long classPK) {

		getPersistence().removeByG_C_C(groupId, classNameId, classPK);
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching journal articles
	 */
	public static int countByG_C_C(
		long groupId, long classNameId, long classPK) {

		return getPersistence().countByG_C_C(groupId, classNameId, classPK);
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching journal articles that the user has permission to view
	 */
	public static int filterCountByG_C_C(
		long groupId, long classNameId, long classPK) {

		return getPersistence().filterCountByG_C_C(
			groupId, classNameId, classPK);
	}

	/**
	 * Returns the journal article where groupId = &#63; and classNameId = &#63; and DDMStructureKey = &#63; or throws a <code>NoSuchArticleException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMStructureKey the ddm structure key
	 * @return the matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_C_DDMSK(
			long groupId, long classNameId, String DDMStructureKey)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_C_DDMSK(
			groupId, classNameId, DDMStructureKey);
	}

	/**
	 * Returns the journal article where groupId = &#63; and classNameId = &#63; and DDMStructureKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMStructureKey the ddm structure key
	 * @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_C_DDMSK(
		long groupId, long classNameId, String DDMStructureKey) {

		return getPersistence().fetchByG_C_DDMSK(
			groupId, classNameId, DDMStructureKey);
	}

	/**
	 * Returns the journal article where groupId = &#63; and classNameId = &#63; and DDMStructureKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMStructureKey the ddm structure key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_C_DDMSK(
		long groupId, long classNameId, String DDMStructureKey,
		boolean useFinderCache) {

		return getPersistence().fetchByG_C_DDMSK(
			groupId, classNameId, DDMStructureKey, useFinderCache);
	}

	/**
	 * Removes the journal article where groupId = &#63; and classNameId = &#63; and DDMStructureKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMStructureKey the ddm structure key
	 * @return the journal article that was removed
	 */
	public static JournalArticle removeByG_C_DDMSK(
			long groupId, long classNameId, String DDMStructureKey)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().removeByG_C_DDMSK(
			groupId, classNameId, DDMStructureKey);
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and classNameId = &#63; and DDMStructureKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMStructureKey the ddm structure key
	 * @return the number of matching journal articles
	 */
	public static int countByG_C_DDMSK(
		long groupId, long classNameId, String DDMStructureKey) {

		return getPersistence().countByG_C_DDMSK(
			groupId, classNameId, DDMStructureKey);
	}

	/**
	 * Returns all the journal articles where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByG_C_DDMTK(
		long groupId, long classNameId, String DDMTemplateKey) {

		return getPersistence().findByG_C_DDMTK(
			groupId, classNameId, DDMTemplateKey);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByG_C_DDMTK(
		long groupId, long classNameId, String DDMTemplateKey, int start,
		int end) {

		return getPersistence().findByG_C_DDMTK(
			groupId, classNameId, DDMTemplateKey, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_C_DDMTK(
		long groupId, long classNameId, String DDMTemplateKey, int start,
		int end, OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByG_C_DDMTK(
			groupId, classNameId, DDMTemplateKey, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_C_DDMTK(
		long groupId, long classNameId, String DDMTemplateKey, int start,
		int end, OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_C_DDMTK(
			groupId, classNameId, DDMTemplateKey, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_C_DDMTK_First(
			long groupId, long classNameId, String DDMTemplateKey,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_C_DDMTK_First(
			groupId, classNameId, DDMTemplateKey, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_C_DDMTK_First(
		long groupId, long classNameId, String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_C_DDMTK_First(
			groupId, classNameId, DDMTemplateKey, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_C_DDMTK_Last(
			long groupId, long classNameId, String DDMTemplateKey,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_C_DDMTK_Last(
			groupId, classNameId, DDMTemplateKey, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_C_DDMTK_Last(
		long groupId, long classNameId, String DDMTemplateKey,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_C_DDMTK_Last(
			groupId, classNameId, DDMTemplateKey, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByG_C_DDMTK_PrevAndNext(
			long id, long groupId, long classNameId, String DDMTemplateKey,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_C_DDMTK_PrevAndNext(
			id, groupId, classNameId, DDMTemplateKey, orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @return the matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_C_DDMTK(
		long groupId, long classNameId, String DDMTemplateKey) {

		return getPersistence().filterFindByG_C_DDMTK(
			groupId, classNameId, DDMTemplateKey);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_C_DDMTK(
		long groupId, long classNameId, String DDMTemplateKey, int start,
		int end) {

		return getPersistence().filterFindByG_C_DDMTK(
			groupId, classNameId, DDMTemplateKey, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_C_DDMTK(
		long groupId, long classNameId, String DDMTemplateKey, int start,
		int end, OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().filterFindByG_C_DDMTK(
			groupId, classNameId, DDMTemplateKey, start, end,
			orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] filterFindByG_C_DDMTK_PrevAndNext(
			long id, long groupId, long classNameId, String DDMTemplateKey,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().filterFindByG_C_DDMTK_PrevAndNext(
			id, groupId, classNameId, DDMTemplateKey, orderByComparator);
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 */
	public static void removeByG_C_DDMTK(
		long groupId, long classNameId, String DDMTemplateKey) {

		getPersistence().removeByG_C_DDMTK(
			groupId, classNameId, DDMTemplateKey);
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @return the number of matching journal articles
	 */
	public static int countByG_C_DDMTK(
		long groupId, long classNameId, String DDMTemplateKey) {

		return getPersistence().countByG_C_DDMTK(
			groupId, classNameId, DDMTemplateKey);
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and DDMTemplateKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param DDMTemplateKey the ddm template key
	 * @return the number of matching journal articles that the user has permission to view
	 */
	public static int filterCountByG_C_DDMTK(
		long groupId, long classNameId, String DDMTemplateKey) {

		return getPersistence().filterCountByG_C_DDMTK(
			groupId, classNameId, DDMTemplateKey);
	}

	/**
	 * Returns all the journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByG_C_L(
		long groupId, long classNameId, String layoutUuid) {

		return getPersistence().findByG_C_L(groupId, classNameId, layoutUuid);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByG_C_L(
		long groupId, long classNameId, String layoutUuid, int start, int end) {

		return getPersistence().findByG_C_L(
			groupId, classNameId, layoutUuid, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_C_L(
		long groupId, long classNameId, String layoutUuid, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByG_C_L(
			groupId, classNameId, layoutUuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_C_L(
		long groupId, long classNameId, String layoutUuid, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_C_L(
			groupId, classNameId, layoutUuid, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_C_L_First(
			long groupId, long classNameId, String layoutUuid,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_C_L_First(
			groupId, classNameId, layoutUuid, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_C_L_First(
		long groupId, long classNameId, String layoutUuid,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_C_L_First(
			groupId, classNameId, layoutUuid, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_C_L_Last(
			long groupId, long classNameId, String layoutUuid,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_C_L_Last(
			groupId, classNameId, layoutUuid, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_C_L_Last(
		long groupId, long classNameId, String layoutUuid,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_C_L_Last(
			groupId, classNameId, layoutUuid, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByG_C_L_PrevAndNext(
			long id, long groupId, long classNameId, String layoutUuid,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_C_L_PrevAndNext(
			id, groupId, classNameId, layoutUuid, orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @return the matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_C_L(
		long groupId, long classNameId, String layoutUuid) {

		return getPersistence().filterFindByG_C_L(
			groupId, classNameId, layoutUuid);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_C_L(
		long groupId, long classNameId, String layoutUuid, int start, int end) {

		return getPersistence().filterFindByG_C_L(
			groupId, classNameId, layoutUuid, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_C_L(
		long groupId, long classNameId, String layoutUuid, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().filterFindByG_C_L(
			groupId, classNameId, layoutUuid, start, end, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] filterFindByG_C_L_PrevAndNext(
			long id, long groupId, long classNameId, String layoutUuid,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().filterFindByG_C_L_PrevAndNext(
			id, groupId, classNameId, layoutUuid, orderByComparator);
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 */
	public static void removeByG_C_L(
		long groupId, long classNameId, String layoutUuid) {

		getPersistence().removeByG_C_L(groupId, classNameId, layoutUuid);
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @return the number of matching journal articles
	 */
	public static int countByG_C_L(
		long groupId, long classNameId, String layoutUuid) {

		return getPersistence().countByG_C_L(groupId, classNameId, layoutUuid);
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and classNameId = &#63; and layoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param layoutUuid the layout uuid
	 * @return the number of matching journal articles that the user has permission to view
	 */
	public static int filterCountByG_C_L(
		long groupId, long classNameId, String layoutUuid) {

		return getPersistence().filterCountByG_C_L(
			groupId, classNameId, layoutUuid);
	}

	/**
	 * Returns the journal article where groupId = &#63; and articleId = &#63; and version = &#63; or throws a <code>NoSuchArticleException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param version the version
	 * @return the matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_A_V(
			long groupId, String articleId, double version)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_A_V(groupId, articleId, version);
	}

	/**
	 * Returns the journal article where groupId = &#63; and articleId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param version the version
	 * @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_A_V(
		long groupId, String articleId, double version) {

		return getPersistence().fetchByG_A_V(groupId, articleId, version);
	}

	/**
	 * Returns the journal article where groupId = &#63; and articleId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_A_V(
		long groupId, String articleId, double version,
		boolean useFinderCache) {

		return getPersistence().fetchByG_A_V(
			groupId, articleId, version, useFinderCache);
	}

	/**
	 * Removes the journal article where groupId = &#63; and articleId = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param version the version
	 * @return the journal article that was removed
	 */
	public static JournalArticle removeByG_A_V(
			long groupId, String articleId, double version)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().removeByG_A_V(groupId, articleId, version);
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and articleId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param version the version
	 * @return the number of matching journal articles
	 */
	public static int countByG_A_V(
		long groupId, String articleId, double version) {

		return getPersistence().countByG_A_V(groupId, articleId, version);
	}

	/**
	 * Returns all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByG_A_ST(
		long groupId, String articleId, int status) {

		return getPersistence().findByG_A_ST(groupId, articleId, status);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByG_A_ST(
		long groupId, String articleId, int status, int start, int end) {

		return getPersistence().findByG_A_ST(
			groupId, articleId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_A_ST(
		long groupId, String articleId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByG_A_ST(
			groupId, articleId, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_A_ST(
		long groupId, String articleId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_A_ST(
			groupId, articleId, status, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_A_ST_First(
			long groupId, String articleId, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_A_ST_First(
			groupId, articleId, status, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_A_ST_First(
		long groupId, String articleId, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_A_ST_First(
			groupId, articleId, status, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_A_ST_Last(
			long groupId, String articleId, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_A_ST_Last(
			groupId, articleId, status, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_A_ST_Last(
		long groupId, String articleId, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_A_ST_Last(
			groupId, articleId, status, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByG_A_ST_PrevAndNext(
			long id, long groupId, String articleId, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_A_ST_PrevAndNext(
			id, groupId, articleId, status, orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @return the matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_A_ST(
		long groupId, String articleId, int status) {

		return getPersistence().filterFindByG_A_ST(groupId, articleId, status);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_A_ST(
		long groupId, String articleId, int status, int start, int end) {

		return getPersistence().filterFindByG_A_ST(
			groupId, articleId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_A_ST(
		long groupId, String articleId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().filterFindByG_A_ST(
			groupId, articleId, status, start, end, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] filterFindByG_A_ST_PrevAndNext(
			long id, long groupId, String articleId, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().filterFindByG_A_ST_PrevAndNext(
			id, groupId, articleId, status, orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param statuses the statuses
	 * @return the matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_A_ST(
		long groupId, String articleId, int[] statuses) {

		return getPersistence().filterFindByG_A_ST(
			groupId, articleId, statuses);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_A_ST(
		long groupId, String articleId, int[] statuses, int start, int end) {

		return getPersistence().filterFindByG_A_ST(
			groupId, articleId, statuses, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_A_ST(
		long groupId, String articleId, int[] statuses, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().filterFindByG_A_ST(
			groupId, articleId, statuses, start, end, orderByComparator);
	}

	/**
	 * Returns all the journal articles where groupId = &#63; and articleId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param statuses the statuses
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByG_A_ST(
		long groupId, String articleId, int[] statuses) {

		return getPersistence().findByG_A_ST(groupId, articleId, statuses);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and articleId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByG_A_ST(
		long groupId, String articleId, int[] statuses, int start, int end) {

		return getPersistence().findByG_A_ST(
			groupId, articleId, statuses, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and articleId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_A_ST(
		long groupId, String articleId, int[] statuses, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByG_A_ST(
			groupId, articleId, statuses, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_A_ST(
		long groupId, String articleId, int[] statuses, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_A_ST(
			groupId, articleId, statuses, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and articleId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 */
	public static void removeByG_A_ST(
		long groupId, String articleId, int status) {

		getPersistence().removeByG_A_ST(groupId, articleId, status);
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	public static int countByG_A_ST(
		long groupId, String articleId, int status) {

		return getPersistence().countByG_A_ST(groupId, articleId, status);
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and articleId = &#63; and status = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param statuses the statuses
	 * @return the number of matching journal articles
	 */
	public static int countByG_A_ST(
		long groupId, String articleId, int[] statuses) {

		return getPersistence().countByG_A_ST(groupId, articleId, statuses);
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @return the number of matching journal articles that the user has permission to view
	 */
	public static int filterCountByG_A_ST(
		long groupId, String articleId, int status) {

		return getPersistence().filterCountByG_A_ST(groupId, articleId, status);
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param statuses the statuses
	 * @return the number of matching journal articles that the user has permission to view
	 */
	public static int filterCountByG_A_ST(
		long groupId, String articleId, int[] statuses) {

		return getPersistence().filterCountByG_A_ST(
			groupId, articleId, statuses);
	}

	/**
	 * Returns all the journal articles where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByG_A_NotST(
		long groupId, String articleId, int status) {

		return getPersistence().findByG_A_NotST(groupId, articleId, status);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByG_A_NotST(
		long groupId, String articleId, int status, int start, int end) {

		return getPersistence().findByG_A_NotST(
			groupId, articleId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_A_NotST(
		long groupId, String articleId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByG_A_NotST(
			groupId, articleId, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_A_NotST(
		long groupId, String articleId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_A_NotST(
			groupId, articleId, status, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_A_NotST_First(
			long groupId, String articleId, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_A_NotST_First(
			groupId, articleId, status, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_A_NotST_First(
		long groupId, String articleId, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_A_NotST_First(
			groupId, articleId, status, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_A_NotST_Last(
			long groupId, String articleId, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_A_NotST_Last(
			groupId, articleId, status, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_A_NotST_Last(
		long groupId, String articleId, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_A_NotST_Last(
			groupId, articleId, status, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByG_A_NotST_PrevAndNext(
			long id, long groupId, String articleId, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_A_NotST_PrevAndNext(
			id, groupId, articleId, status, orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @return the matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_A_NotST(
		long groupId, String articleId, int status) {

		return getPersistence().filterFindByG_A_NotST(
			groupId, articleId, status);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_A_NotST(
		long groupId, String articleId, int status, int start, int end) {

		return getPersistence().filterFindByG_A_NotST(
			groupId, articleId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_A_NotST(
		long groupId, String articleId, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().filterFindByG_A_NotST(
			groupId, articleId, status, start, end, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] filterFindByG_A_NotST_PrevAndNext(
			long id, long groupId, String articleId, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().filterFindByG_A_NotST_PrevAndNext(
			id, groupId, articleId, status, orderByComparator);
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and articleId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 */
	public static void removeByG_A_NotST(
		long groupId, String articleId, int status) {

		getPersistence().removeByG_A_NotST(groupId, articleId, status);
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	public static int countByG_A_NotST(
		long groupId, String articleId, int status) {

		return getPersistence().countByG_A_NotST(groupId, articleId, status);
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and articleId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param status the status
	 * @return the number of matching journal articles that the user has permission to view
	 */
	public static int filterCountByG_A_NotST(
		long groupId, String articleId, int status) {

		return getPersistence().filterCountByG_A_NotST(
			groupId, articleId, status);
	}

	/**
	 * Returns all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByG_UT_ST(
		long groupId, String urlTitle, int status) {

		return getPersistence().findByG_UT_ST(groupId, urlTitle, status);
	}

	/**
	 * Returns a range of all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByG_UT_ST(
		long groupId, String urlTitle, int status, int start, int end) {

		return getPersistence().findByG_UT_ST(
			groupId, urlTitle, status, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_UT_ST(
		long groupId, String urlTitle, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByG_UT_ST(
			groupId, urlTitle, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByG_UT_ST(
		long groupId, String urlTitle, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_UT_ST(
			groupId, urlTitle, status, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_UT_ST_First(
			long groupId, String urlTitle, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_UT_ST_First(
			groupId, urlTitle, status, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_UT_ST_First(
		long groupId, String urlTitle, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_UT_ST_First(
			groupId, urlTitle, status, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByG_UT_ST_Last(
			long groupId, String urlTitle, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_UT_ST_Last(
			groupId, urlTitle, status, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByG_UT_ST_Last(
		long groupId, String urlTitle, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByG_UT_ST_Last(
			groupId, urlTitle, status, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByG_UT_ST_PrevAndNext(
			long id, long groupId, String urlTitle, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByG_UT_ST_PrevAndNext(
			id, groupId, urlTitle, status, orderByComparator);
	}

	/**
	 * Returns all the journal articles that the user has permission to view where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @return the matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_UT_ST(
		long groupId, String urlTitle, int status) {

		return getPersistence().filterFindByG_UT_ST(groupId, urlTitle, status);
	}

	/**
	 * Returns a range of all the journal articles that the user has permission to view where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_UT_ST(
		long groupId, String urlTitle, int status, int start, int end) {

		return getPersistence().filterFindByG_UT_ST(
			groupId, urlTitle, status, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles that the user has permissions to view where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles that the user has permission to view
	 */
	public static List<JournalArticle> filterFindByG_UT_ST(
		long groupId, String urlTitle, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().filterFindByG_UT_ST(
			groupId, urlTitle, status, start, end, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set of journal articles that the user has permission to view where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] filterFindByG_UT_ST_PrevAndNext(
			long id, long groupId, String urlTitle, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().filterFindByG_UT_ST_PrevAndNext(
			id, groupId, urlTitle, status, orderByComparator);
	}

	/**
	 * Removes all the journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 */
	public static void removeByG_UT_ST(
		long groupId, String urlTitle, int status) {

		getPersistence().removeByG_UT_ST(groupId, urlTitle, status);
	}

	/**
	 * Returns the number of journal articles where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	public static int countByG_UT_ST(
		long groupId, String urlTitle, int status) {

		return getPersistence().countByG_UT_ST(groupId, urlTitle, status);
	}

	/**
	 * Returns the number of journal articles that the user has permission to view where groupId = &#63; and urlTitle = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param status the status
	 * @return the number of matching journal articles that the user has permission to view
	 */
	public static int filterCountByG_UT_ST(
		long groupId, String urlTitle, int status) {

		return getPersistence().filterCountByG_UT_ST(groupId, urlTitle, status);
	}

	/**
	 * Returns all the journal articles where companyId = &#63; and version = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param status the status
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByC_V_ST(
		long companyId, double version, int status) {

		return getPersistence().findByC_V_ST(companyId, version, status);
	}

	/**
	 * Returns a range of all the journal articles where companyId = &#63; and version = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByC_V_ST(
		long companyId, double version, int status, int start, int end) {

		return getPersistence().findByC_V_ST(
			companyId, version, status, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where companyId = &#63; and version = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByC_V_ST(
		long companyId, double version, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByC_V_ST(
			companyId, version, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where companyId = &#63; and version = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByC_V_ST(
		long companyId, double version, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_V_ST(
			companyId, version, status, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where companyId = &#63; and version = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByC_V_ST_First(
			long companyId, double version, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByC_V_ST_First(
			companyId, version, status, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where companyId = &#63; and version = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByC_V_ST_First(
		long companyId, double version, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByC_V_ST_First(
			companyId, version, status, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where companyId = &#63; and version = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByC_V_ST_Last(
			long companyId, double version, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByC_V_ST_Last(
			companyId, version, status, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where companyId = &#63; and version = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByC_V_ST_Last(
		long companyId, double version, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByC_V_ST_Last(
			companyId, version, status, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where companyId = &#63; and version = &#63; and status = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param companyId the company ID
	 * @param version the version
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByC_V_ST_PrevAndNext(
			long id, long companyId, double version, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByC_V_ST_PrevAndNext(
			id, companyId, version, status, orderByComparator);
	}

	/**
	 * Removes all the journal articles where companyId = &#63; and version = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param status the status
	 */
	public static void removeByC_V_ST(
		long companyId, double version, int status) {

		getPersistence().removeByC_V_ST(companyId, version, status);
	}

	/**
	 * Returns the number of journal articles where companyId = &#63; and version = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	public static int countByC_V_ST(
		long companyId, double version, int status) {

		return getPersistence().countByC_V_ST(companyId, version, status);
	}

	/**
	 * Returns all the journal articles where classNameId = &#63; and expirationDate = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the matching journal articles
	 */
	public static List<JournalArticle> findByC_ED_ST(
		long classNameId, Date expirationDate, int status) {

		return getPersistence().findByC_ED_ST(
			classNameId, expirationDate, status);
	}

	/**
	 * Returns a range of all the journal articles where classNameId = &#63; and expirationDate = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of matching journal articles
	 */
	public static List<JournalArticle> findByC_ED_ST(
		long classNameId, Date expirationDate, int status, int start, int end) {

		return getPersistence().findByC_ED_ST(
			classNameId, expirationDate, status, start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles where classNameId = &#63; and expirationDate = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByC_ED_ST(
		long classNameId, Date expirationDate, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findByC_ED_ST(
			classNameId, expirationDate, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles where classNameId = &#63; and expirationDate = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching journal articles
	 */
	public static List<JournalArticle> findByC_ED_ST(
		long classNameId, Date expirationDate, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_ED_ST(
			classNameId, expirationDate, status, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first journal article in the ordered set where classNameId = &#63; and expirationDate = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByC_ED_ST_First(
			long classNameId, Date expirationDate, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByC_ED_ST_First(
			classNameId, expirationDate, status, orderByComparator);
	}

	/**
	 * Returns the first journal article in the ordered set where classNameId = &#63; and expirationDate = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByC_ED_ST_First(
		long classNameId, Date expirationDate, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByC_ED_ST_First(
			classNameId, expirationDate, status, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where classNameId = &#63; and expirationDate = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article
	 * @throws NoSuchArticleException if a matching journal article could not be found
	 */
	public static JournalArticle findByC_ED_ST_Last(
			long classNameId, Date expirationDate, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByC_ED_ST_Last(
			classNameId, expirationDate, status, orderByComparator);
	}

	/**
	 * Returns the last journal article in the ordered set where classNameId = &#63; and expirationDate = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article, or <code>null</code> if a matching journal article could not be found
	 */
	public static JournalArticle fetchByC_ED_ST_Last(
		long classNameId, Date expirationDate, int status,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().fetchByC_ED_ST_Last(
			classNameId, expirationDate, status, orderByComparator);
	}

	/**
	 * Returns the journal articles before and after the current journal article in the ordered set where classNameId = &#63; and expirationDate = &#63; and status = &#63;.
	 *
	 * @param id the primary key of the current journal article
	 * @param classNameId the class name ID
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle[] findByC_ED_ST_PrevAndNext(
			long id, long classNameId, Date expirationDate, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByC_ED_ST_PrevAndNext(
			id, classNameId, expirationDate, status, orderByComparator);
	}

	/**
	 * Removes all the journal articles where classNameId = &#63; and expirationDate = &#63; and status = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param expirationDate the expiration date
	 * @param status the status
	 */
	public static void removeByC_ED_ST(
		long classNameId, Date expirationDate, int status) {

		getPersistence().removeByC_ED_ST(classNameId, expirationDate, status);
	}

	/**
	 * Returns the number of journal articles where classNameId = &#63; and expirationDate = &#63; and status = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the number of matching journal articles
	 */
	public static int countByC_ED_ST(
		long classNameId, Date expirationDate, int status) {

		return getPersistence().countByC_ED_ST(
			classNameId, expirationDate, status);
	}

	/**
	 * Caches the journal article in the entity cache if it is enabled.
	 *
	 * @param journalArticle the journal article
	 */
	public static void cacheResult(JournalArticle journalArticle) {
		getPersistence().cacheResult(journalArticle);
	}

	/**
	 * Caches the journal articles in the entity cache if it is enabled.
	 *
	 * @param journalArticles the journal articles
	 */
	public static void cacheResult(List<JournalArticle> journalArticles) {
		getPersistence().cacheResult(journalArticles);
	}

	/**
	 * Creates a new journal article with the primary key. Does not add the journal article to the database.
	 *
	 * @param id the primary key for the new journal article
	 * @return the new journal article
	 */
	public static JournalArticle create(long id) {
		return getPersistence().create(id);
	}

	/**
	 * Removes the journal article with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param id the primary key of the journal article
	 * @return the journal article that was removed
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle remove(long id)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().remove(id);
	}

	public static JournalArticle updateImpl(JournalArticle journalArticle) {
		return getPersistence().updateImpl(journalArticle);
	}

	/**
	 * Returns the journal article with the primary key or throws a <code>NoSuchArticleException</code> if it could not be found.
	 *
	 * @param id the primary key of the journal article
	 * @return the journal article
	 * @throws NoSuchArticleException if a journal article with the primary key could not be found
	 */
	public static JournalArticle findByPrimaryKey(long id)
		throws com.liferay.journal.exception.NoSuchArticleException {

		return getPersistence().findByPrimaryKey(id);
	}

	/**
	 * Returns the journal article with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param id the primary key of the journal article
	 * @return the journal article, or <code>null</code> if a journal article with the primary key could not be found
	 */
	public static JournalArticle fetchByPrimaryKey(long id) {
		return getPersistence().fetchByPrimaryKey(id);
	}

	/**
	 * Returns all the journal articles.
	 *
	 * @return the journal articles
	 */
	public static List<JournalArticle> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the journal articles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @return the range of journal articles
	 */
	public static List<JournalArticle> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the journal articles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of journal articles
	 */
	public static List<JournalArticle> findAll(
		int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the journal articles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>JournalArticleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal articles
	 * @param end the upper bound of the range of journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of journal articles
	 */
	public static List<JournalArticle> findAll(
		int start, int end, OrderByComparator<JournalArticle> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the journal articles from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of journal articles.
	 *
	 * @return the number of journal articles
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static JournalArticlePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<JournalArticlePersistence, JournalArticlePersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			JournalArticlePersistence.class);

		ServiceTracker<JournalArticlePersistence, JournalArticlePersistence>
			serviceTracker =
				new ServiceTracker
					<JournalArticlePersistence, JournalArticlePersistence>(
						bundle.getBundleContext(),
						JournalArticlePersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}