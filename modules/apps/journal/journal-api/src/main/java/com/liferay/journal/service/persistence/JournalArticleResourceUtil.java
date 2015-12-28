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

import com.liferay.journal.model.JournalArticleResource;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the journal article resource service. This utility wraps {@link com.liferay.journal.service.persistence.impl.JournalArticleResourcePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticleResourcePersistence
 * @see com.liferay.journal.service.persistence.impl.JournalArticleResourcePersistenceImpl
 * @generated
 */
@ProviderType
public class JournalArticleResourceUtil {
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
	public static void clearCache(JournalArticleResource journalArticleResource) {
		getPersistence().clearCache(journalArticleResource);
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
	public static List<JournalArticleResource> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<JournalArticleResource> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<JournalArticleResource> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<JournalArticleResource> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static JournalArticleResource update(
		JournalArticleResource journalArticleResource) {
		return getPersistence().update(journalArticleResource);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static JournalArticleResource update(
		JournalArticleResource journalArticleResource,
		ServiceContext serviceContext) {
		return getPersistence().update(journalArticleResource, serviceContext);
	}

	/**
	* Returns all the journal article resources where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching journal article resources
	*/
	public static List<JournalArticleResource> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the journal article resources where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of journal article resources
	* @param end the upper bound of the range of journal article resources (not inclusive)
	* @return the range of matching journal article resources
	*/
	public static List<JournalArticleResource> findByUuid(
		java.lang.String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the journal article resources where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of journal article resources
	* @param end the upper bound of the range of journal article resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal article resources
	*/
	public static List<JournalArticleResource> findByUuid(
		java.lang.String uuid, int start, int end,
		OrderByComparator<JournalArticleResource> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the journal article resources where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of journal article resources
	* @param end the upper bound of the range of journal article resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal article resources
	*/
	public static List<JournalArticleResource> findByUuid(
		java.lang.String uuid, int start, int end,
		OrderByComparator<JournalArticleResource> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first journal article resource in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article resource
	* @throws com.liferay.journal.NoSuchArticleResourceException if a matching journal article resource could not be found
	*/
	public static JournalArticleResource findByUuid_First(
		java.lang.String uuid,
		OrderByComparator<JournalArticleResource> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleResourceException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first journal article resource in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	*/
	public static JournalArticleResource fetchByUuid_First(
		java.lang.String uuid,
		OrderByComparator<JournalArticleResource> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last journal article resource in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article resource
	* @throws com.liferay.journal.NoSuchArticleResourceException if a matching journal article resource could not be found
	*/
	public static JournalArticleResource findByUuid_Last(
		java.lang.String uuid,
		OrderByComparator<JournalArticleResource> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleResourceException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last journal article resource in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	*/
	public static JournalArticleResource fetchByUuid_Last(
		java.lang.String uuid,
		OrderByComparator<JournalArticleResource> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the journal article resources before and after the current journal article resource in the ordered set where uuid = &#63;.
	*
	* @param resourcePrimKey the primary key of the current journal article resource
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article resource
	* @throws com.liferay.journal.NoSuchArticleResourceException if a journal article resource with the primary key could not be found
	*/
	public static JournalArticleResource[] findByUuid_PrevAndNext(
		long resourcePrimKey, java.lang.String uuid,
		OrderByComparator<JournalArticleResource> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleResourceException {
		return getPersistence()
				   .findByUuid_PrevAndNext(resourcePrimKey, uuid,
			orderByComparator);
	}

	/**
	* Removes all the journal article resources where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of journal article resources where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching journal article resources
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the journal article resource where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.journal.NoSuchArticleResourceException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching journal article resource
	* @throws com.liferay.journal.NoSuchArticleResourceException if a matching journal article resource could not be found
	*/
	public static JournalArticleResource findByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.journal.exception.NoSuchArticleResourceException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the journal article resource where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	*/
	public static JournalArticleResource fetchByUUID_G(java.lang.String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the journal article resource where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	*/
	public static JournalArticleResource fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the journal article resource where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the journal article resource that was removed
	*/
	public static JournalArticleResource removeByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.journal.exception.NoSuchArticleResourceException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of journal article resources where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching journal article resources
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the journal article resources where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching journal article resources
	*/
	public static List<JournalArticleResource> findByUuid_C(
		java.lang.String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the journal article resources where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of journal article resources
	* @param end the upper bound of the range of journal article resources (not inclusive)
	* @return the range of matching journal article resources
	*/
	public static List<JournalArticleResource> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the journal article resources where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of journal article resources
	* @param end the upper bound of the range of journal article resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal article resources
	*/
	public static List<JournalArticleResource> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<JournalArticleResource> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the journal article resources where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of journal article resources
	* @param end the upper bound of the range of journal article resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal article resources
	*/
	public static List<JournalArticleResource> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<JournalArticleResource> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first journal article resource in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article resource
	* @throws com.liferay.journal.NoSuchArticleResourceException if a matching journal article resource could not be found
	*/
	public static JournalArticleResource findByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<JournalArticleResource> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleResourceException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first journal article resource in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	*/
	public static JournalArticleResource fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<JournalArticleResource> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last journal article resource in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article resource
	* @throws com.liferay.journal.NoSuchArticleResourceException if a matching journal article resource could not be found
	*/
	public static JournalArticleResource findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<JournalArticleResource> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleResourceException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last journal article resource in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	*/
	public static JournalArticleResource fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<JournalArticleResource> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the journal article resources before and after the current journal article resource in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param resourcePrimKey the primary key of the current journal article resource
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article resource
	* @throws com.liferay.journal.NoSuchArticleResourceException if a journal article resource with the primary key could not be found
	*/
	public static JournalArticleResource[] findByUuid_C_PrevAndNext(
		long resourcePrimKey, java.lang.String uuid, long companyId,
		OrderByComparator<JournalArticleResource> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleResourceException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(resourcePrimKey, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the journal article resources where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of journal article resources where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching journal article resources
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the journal article resources where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching journal article resources
	*/
	public static List<JournalArticleResource> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the journal article resources where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal article resources
	* @param end the upper bound of the range of journal article resources (not inclusive)
	* @return the range of matching journal article resources
	*/
	public static List<JournalArticleResource> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the journal article resources where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal article resources
	* @param end the upper bound of the range of journal article resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal article resources
	*/
	public static List<JournalArticleResource> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<JournalArticleResource> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the journal article resources where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal article resources
	* @param end the upper bound of the range of journal article resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal article resources
	*/
	public static List<JournalArticleResource> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<JournalArticleResource> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first journal article resource in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article resource
	* @throws com.liferay.journal.NoSuchArticleResourceException if a matching journal article resource could not be found
	*/
	public static JournalArticleResource findByGroupId_First(long groupId,
		OrderByComparator<JournalArticleResource> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleResourceException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first journal article resource in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	*/
	public static JournalArticleResource fetchByGroupId_First(long groupId,
		OrderByComparator<JournalArticleResource> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last journal article resource in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article resource
	* @throws com.liferay.journal.NoSuchArticleResourceException if a matching journal article resource could not be found
	*/
	public static JournalArticleResource findByGroupId_Last(long groupId,
		OrderByComparator<JournalArticleResource> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleResourceException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last journal article resource in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	*/
	public static JournalArticleResource fetchByGroupId_Last(long groupId,
		OrderByComparator<JournalArticleResource> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the journal article resources before and after the current journal article resource in the ordered set where groupId = &#63;.
	*
	* @param resourcePrimKey the primary key of the current journal article resource
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal article resource
	* @throws com.liferay.journal.NoSuchArticleResourceException if a journal article resource with the primary key could not be found
	*/
	public static JournalArticleResource[] findByGroupId_PrevAndNext(
		long resourcePrimKey, long groupId,
		OrderByComparator<JournalArticleResource> orderByComparator)
		throws com.liferay.journal.exception.NoSuchArticleResourceException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(resourcePrimKey, groupId,
			orderByComparator);
	}

	/**
	* Removes all the journal article resources where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of journal article resources where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching journal article resources
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the journal article resource where groupId = &#63; and articleId = &#63; or throws a {@link com.liferay.journal.NoSuchArticleResourceException} if it could not be found.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @return the matching journal article resource
	* @throws com.liferay.journal.NoSuchArticleResourceException if a matching journal article resource could not be found
	*/
	public static JournalArticleResource findByG_A(long groupId,
		java.lang.String articleId)
		throws com.liferay.journal.exception.NoSuchArticleResourceException {
		return getPersistence().findByG_A(groupId, articleId);
	}

	/**
	* Returns the journal article resource where groupId = &#63; and articleId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @return the matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	*/
	public static JournalArticleResource fetchByG_A(long groupId,
		java.lang.String articleId) {
		return getPersistence().fetchByG_A(groupId, articleId);
	}

	/**
	* Returns the journal article resource where groupId = &#63; and articleId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching journal article resource, or <code>null</code> if a matching journal article resource could not be found
	*/
	public static JournalArticleResource fetchByG_A(long groupId,
		java.lang.String articleId, boolean retrieveFromCache) {
		return getPersistence().fetchByG_A(groupId, articleId, retrieveFromCache);
	}

	/**
	* Removes the journal article resource where groupId = &#63; and articleId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @return the journal article resource that was removed
	*/
	public static JournalArticleResource removeByG_A(long groupId,
		java.lang.String articleId)
		throws com.liferay.journal.exception.NoSuchArticleResourceException {
		return getPersistence().removeByG_A(groupId, articleId);
	}

	/**
	* Returns the number of journal article resources where groupId = &#63; and articleId = &#63;.
	*
	* @param groupId the group ID
	* @param articleId the article ID
	* @return the number of matching journal article resources
	*/
	public static int countByG_A(long groupId, java.lang.String articleId) {
		return getPersistence().countByG_A(groupId, articleId);
	}

	/**
	* Caches the journal article resource in the entity cache if it is enabled.
	*
	* @param journalArticleResource the journal article resource
	*/
	public static void cacheResult(
		JournalArticleResource journalArticleResource) {
		getPersistence().cacheResult(journalArticleResource);
	}

	/**
	* Caches the journal article resources in the entity cache if it is enabled.
	*
	* @param journalArticleResources the journal article resources
	*/
	public static void cacheResult(
		List<JournalArticleResource> journalArticleResources) {
		getPersistence().cacheResult(journalArticleResources);
	}

	/**
	* Creates a new journal article resource with the primary key. Does not add the journal article resource to the database.
	*
	* @param resourcePrimKey the primary key for the new journal article resource
	* @return the new journal article resource
	*/
	public static JournalArticleResource create(long resourcePrimKey) {
		return getPersistence().create(resourcePrimKey);
	}

	/**
	* Removes the journal article resource with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param resourcePrimKey the primary key of the journal article resource
	* @return the journal article resource that was removed
	* @throws com.liferay.journal.NoSuchArticleResourceException if a journal article resource with the primary key could not be found
	*/
	public static JournalArticleResource remove(long resourcePrimKey)
		throws com.liferay.journal.exception.NoSuchArticleResourceException {
		return getPersistence().remove(resourcePrimKey);
	}

	public static JournalArticleResource updateImpl(
		JournalArticleResource journalArticleResource) {
		return getPersistence().updateImpl(journalArticleResource);
	}

	/**
	* Returns the journal article resource with the primary key or throws a {@link com.liferay.journal.NoSuchArticleResourceException} if it could not be found.
	*
	* @param resourcePrimKey the primary key of the journal article resource
	* @return the journal article resource
	* @throws com.liferay.journal.NoSuchArticleResourceException if a journal article resource with the primary key could not be found
	*/
	public static JournalArticleResource findByPrimaryKey(long resourcePrimKey)
		throws com.liferay.journal.exception.NoSuchArticleResourceException {
		return getPersistence().findByPrimaryKey(resourcePrimKey);
	}

	/**
	* Returns the journal article resource with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param resourcePrimKey the primary key of the journal article resource
	* @return the journal article resource, or <code>null</code> if a journal article resource with the primary key could not be found
	*/
	public static JournalArticleResource fetchByPrimaryKey(long resourcePrimKey) {
		return getPersistence().fetchByPrimaryKey(resourcePrimKey);
	}

	public static java.util.Map<java.io.Serializable, JournalArticleResource> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the journal article resources.
	*
	* @return the journal article resources
	*/
	public static List<JournalArticleResource> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the journal article resources.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of journal article resources
	* @param end the upper bound of the range of journal article resources (not inclusive)
	* @return the range of journal article resources
	*/
	public static List<JournalArticleResource> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the journal article resources.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of journal article resources
	* @param end the upper bound of the range of journal article resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of journal article resources
	*/
	public static List<JournalArticleResource> findAll(int start, int end,
		OrderByComparator<JournalArticleResource> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the journal article resources.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleResourceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of journal article resources
	* @param end the upper bound of the range of journal article resources (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of journal article resources
	*/
	public static List<JournalArticleResource> findAll(int start, int end,
		OrderByComparator<JournalArticleResource> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the journal article resources from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of journal article resources.
	*
	* @return the number of journal article resources
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static JournalArticleResourcePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<JournalArticleResourcePersistence, JournalArticleResourcePersistence> _serviceTracker =
		ServiceTrackerFactory.open(JournalArticleResourcePersistence.class);
}