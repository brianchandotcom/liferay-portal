/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.blueprints.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.search.tuning.blueprints.model.Element;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the element service. This utility wraps <code>com.liferay.portal.search.tuning.blueprints.service.persistence.impl.ElementPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ElementPersistence
 * @generated
 */
public class ElementUtil {

	/*
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
	public static void clearCache(Element element) {
		getPersistence().clearCache(element);
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
	public static Map<Serializable, Element> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Element> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Element> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Element> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<Element> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static Element update(Element element) {
		return getPersistence().update(element);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static Element update(
		Element element, ServiceContext serviceContext) {

		return getPersistence().update(element, serviceContext);
	}

	/**
	 * Returns all the elements where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching elements
	 */
	public static List<Element> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the elements where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @return the range of matching elements
	 */
	public static List<Element> findByUuid(String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the elements where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching elements
	 */
	public static List<Element> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<Element> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the elements where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching elements
	 */
	public static List<Element> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<Element> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first element in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public static Element findByUuid_First(
			String uuid, OrderByComparator<Element> orderByComparator)
		throws com.liferay.portal.search.tuning.blueprints.exception.
			NoSuchElementException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first element in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching element, or <code>null</code> if a matching element could not be found
	 */
	public static Element fetchByUuid_First(
		String uuid, OrderByComparator<Element> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last element in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public static Element findByUuid_Last(
			String uuid, OrderByComparator<Element> orderByComparator)
		throws com.liferay.portal.search.tuning.blueprints.exception.
			NoSuchElementException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last element in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching element, or <code>null</code> if a matching element could not be found
	 */
	public static Element fetchByUuid_Last(
		String uuid, OrderByComparator<Element> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the elements before and after the current element in the ordered set where uuid = &#63;.
	 *
	 * @param elementId the primary key of the current element
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next element
	 * @throws NoSuchElementException if a element with the primary key could not be found
	 */
	public static Element[] findByUuid_PrevAndNext(
			long elementId, String uuid,
			OrderByComparator<Element> orderByComparator)
		throws com.liferay.portal.search.tuning.blueprints.exception.
			NoSuchElementException {

		return getPersistence().findByUuid_PrevAndNext(
			elementId, uuid, orderByComparator);
	}

	/**
	 * Removes all the elements where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of elements where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching elements
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the element where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchElementException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public static Element findByUUID_G(String uuid, long groupId)
		throws com.liferay.portal.search.tuning.blueprints.exception.
			NoSuchElementException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the element where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching element, or <code>null</code> if a matching element could not be found
	 */
	public static Element fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the element where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching element, or <code>null</code> if a matching element could not be found
	 */
	public static Element fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the element where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the element that was removed
	 */
	public static Element removeByUUID_G(String uuid, long groupId)
		throws com.liferay.portal.search.tuning.blueprints.exception.
			NoSuchElementException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of elements where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching elements
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the elements where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching elements
	 */
	public static List<Element> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the elements where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @return the range of matching elements
	 */
	public static List<Element> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the elements where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching elements
	 */
	public static List<Element> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<Element> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the elements where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching elements
	 */
	public static List<Element> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<Element> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first element in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public static Element findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<Element> orderByComparator)
		throws com.liferay.portal.search.tuning.blueprints.exception.
			NoSuchElementException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first element in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching element, or <code>null</code> if a matching element could not be found
	 */
	public static Element fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<Element> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last element in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public static Element findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<Element> orderByComparator)
		throws com.liferay.portal.search.tuning.blueprints.exception.
			NoSuchElementException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last element in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching element, or <code>null</code> if a matching element could not be found
	 */
	public static Element fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<Element> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the elements before and after the current element in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param elementId the primary key of the current element
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next element
	 * @throws NoSuchElementException if a element with the primary key could not be found
	 */
	public static Element[] findByUuid_C_PrevAndNext(
			long elementId, String uuid, long companyId,
			OrderByComparator<Element> orderByComparator)
		throws com.liferay.portal.search.tuning.blueprints.exception.
			NoSuchElementException {

		return getPersistence().findByUuid_C_PrevAndNext(
			elementId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the elements where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of elements where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching elements
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the elements where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching elements
	 */
	public static List<Element> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the elements where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @return the range of matching elements
	 */
	public static List<Element> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the elements where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching elements
	 */
	public static List<Element> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<Element> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the elements where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching elements
	 */
	public static List<Element> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<Element> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first element in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public static Element findByGroupId_First(
			long groupId, OrderByComparator<Element> orderByComparator)
		throws com.liferay.portal.search.tuning.blueprints.exception.
			NoSuchElementException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first element in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching element, or <code>null</code> if a matching element could not be found
	 */
	public static Element fetchByGroupId_First(
		long groupId, OrderByComparator<Element> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last element in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public static Element findByGroupId_Last(
			long groupId, OrderByComparator<Element> orderByComparator)
		throws com.liferay.portal.search.tuning.blueprints.exception.
			NoSuchElementException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last element in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching element, or <code>null</code> if a matching element could not be found
	 */
	public static Element fetchByGroupId_Last(
		long groupId, OrderByComparator<Element> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the elements before and after the current element in the ordered set where groupId = &#63;.
	 *
	 * @param elementId the primary key of the current element
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next element
	 * @throws NoSuchElementException if a element with the primary key could not be found
	 */
	public static Element[] findByGroupId_PrevAndNext(
			long elementId, long groupId,
			OrderByComparator<Element> orderByComparator)
		throws com.liferay.portal.search.tuning.blueprints.exception.
			NoSuchElementException {

		return getPersistence().findByGroupId_PrevAndNext(
			elementId, groupId, orderByComparator);
	}

	/**
	 * Returns all the elements that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching elements that the user has permission to view
	 */
	public static List<Element> filterFindByGroupId(long groupId) {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	 * Returns a range of all the elements that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @return the range of matching elements that the user has permission to view
	 */
	public static List<Element> filterFindByGroupId(
		long groupId, int start, int end) {

		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the elements that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching elements that the user has permission to view
	 */
	public static List<Element> filterFindByGroupId(
		long groupId, int start, int end,
		OrderByComparator<Element> orderByComparator) {

		return getPersistence().filterFindByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns the elements before and after the current element in the ordered set of elements that the user has permission to view where groupId = &#63;.
	 *
	 * @param elementId the primary key of the current element
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next element
	 * @throws NoSuchElementException if a element with the primary key could not be found
	 */
	public static Element[] filterFindByGroupId_PrevAndNext(
			long elementId, long groupId,
			OrderByComparator<Element> orderByComparator)
		throws com.liferay.portal.search.tuning.blueprints.exception.
			NoSuchElementException {

		return getPersistence().filterFindByGroupId_PrevAndNext(
			elementId, groupId, orderByComparator);
	}

	/**
	 * Removes all the elements where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of elements where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching elements
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns the number of elements that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching elements that the user has permission to view
	 */
	public static int filterCountByGroupId(long groupId) {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	 * Returns all the elements where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching elements
	 */
	public static List<Element> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the elements where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @return the range of matching elements
	 */
	public static List<Element> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the elements where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching elements
	 */
	public static List<Element> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<Element> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the elements where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching elements
	 */
	public static List<Element> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<Element> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first element in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public static Element findByCompanyId_First(
			long companyId, OrderByComparator<Element> orderByComparator)
		throws com.liferay.portal.search.tuning.blueprints.exception.
			NoSuchElementException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first element in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching element, or <code>null</code> if a matching element could not be found
	 */
	public static Element fetchByCompanyId_First(
		long companyId, OrderByComparator<Element> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last element in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public static Element findByCompanyId_Last(
			long companyId, OrderByComparator<Element> orderByComparator)
		throws com.liferay.portal.search.tuning.blueprints.exception.
			NoSuchElementException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last element in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching element, or <code>null</code> if a matching element could not be found
	 */
	public static Element fetchByCompanyId_Last(
		long companyId, OrderByComparator<Element> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the elements before and after the current element in the ordered set where companyId = &#63;.
	 *
	 * @param elementId the primary key of the current element
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next element
	 * @throws NoSuchElementException if a element with the primary key could not be found
	 */
	public static Element[] findByCompanyId_PrevAndNext(
			long elementId, long companyId,
			OrderByComparator<Element> orderByComparator)
		throws com.liferay.portal.search.tuning.blueprints.exception.
			NoSuchElementException {

		return getPersistence().findByCompanyId_PrevAndNext(
			elementId, companyId, orderByComparator);
	}

	/**
	 * Removes all the elements where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of elements where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching elements
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns all the elements where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching elements
	 */
	public static List<Element> findByG_S(long groupId, int status) {
		return getPersistence().findByG_S(groupId, status);
	}

	/**
	 * Returns a range of all the elements where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @return the range of matching elements
	 */
	public static List<Element> findByG_S(
		long groupId, int status, int start, int end) {

		return getPersistence().findByG_S(groupId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the elements where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching elements
	 */
	public static List<Element> findByG_S(
		long groupId, int status, int start, int end,
		OrderByComparator<Element> orderByComparator) {

		return getPersistence().findByG_S(
			groupId, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the elements where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching elements
	 */
	public static List<Element> findByG_S(
		long groupId, int status, int start, int end,
		OrderByComparator<Element> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByG_S(
			groupId, status, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first element in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public static Element findByG_S_First(
			long groupId, int status,
			OrderByComparator<Element> orderByComparator)
		throws com.liferay.portal.search.tuning.blueprints.exception.
			NoSuchElementException {

		return getPersistence().findByG_S_First(
			groupId, status, orderByComparator);
	}

	/**
	 * Returns the first element in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching element, or <code>null</code> if a matching element could not be found
	 */
	public static Element fetchByG_S_First(
		long groupId, int status,
		OrderByComparator<Element> orderByComparator) {

		return getPersistence().fetchByG_S_First(
			groupId, status, orderByComparator);
	}

	/**
	 * Returns the last element in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public static Element findByG_S_Last(
			long groupId, int status,
			OrderByComparator<Element> orderByComparator)
		throws com.liferay.portal.search.tuning.blueprints.exception.
			NoSuchElementException {

		return getPersistence().findByG_S_Last(
			groupId, status, orderByComparator);
	}

	/**
	 * Returns the last element in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching element, or <code>null</code> if a matching element could not be found
	 */
	public static Element fetchByG_S_Last(
		long groupId, int status,
		OrderByComparator<Element> orderByComparator) {

		return getPersistence().fetchByG_S_Last(
			groupId, status, orderByComparator);
	}

	/**
	 * Returns the elements before and after the current element in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param elementId the primary key of the current element
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next element
	 * @throws NoSuchElementException if a element with the primary key could not be found
	 */
	public static Element[] findByG_S_PrevAndNext(
			long elementId, long groupId, int status,
			OrderByComparator<Element> orderByComparator)
		throws com.liferay.portal.search.tuning.blueprints.exception.
			NoSuchElementException {

		return getPersistence().findByG_S_PrevAndNext(
			elementId, groupId, status, orderByComparator);
	}

	/**
	 * Returns all the elements that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching elements that the user has permission to view
	 */
	public static List<Element> filterFindByG_S(long groupId, int status) {
		return getPersistence().filterFindByG_S(groupId, status);
	}

	/**
	 * Returns a range of all the elements that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @return the range of matching elements that the user has permission to view
	 */
	public static List<Element> filterFindByG_S(
		long groupId, int status, int start, int end) {

		return getPersistence().filterFindByG_S(groupId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the elements that the user has permissions to view where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching elements that the user has permission to view
	 */
	public static List<Element> filterFindByG_S(
		long groupId, int status, int start, int end,
		OrderByComparator<Element> orderByComparator) {

		return getPersistence().filterFindByG_S(
			groupId, status, start, end, orderByComparator);
	}

	/**
	 * Returns the elements before and after the current element in the ordered set of elements that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param elementId the primary key of the current element
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next element
	 * @throws NoSuchElementException if a element with the primary key could not be found
	 */
	public static Element[] filterFindByG_S_PrevAndNext(
			long elementId, long groupId, int status,
			OrderByComparator<Element> orderByComparator)
		throws com.liferay.portal.search.tuning.blueprints.exception.
			NoSuchElementException {

		return getPersistence().filterFindByG_S_PrevAndNext(
			elementId, groupId, status, orderByComparator);
	}

	/**
	 * Removes all the elements where groupId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 */
	public static void removeByG_S(long groupId, int status) {
		getPersistence().removeByG_S(groupId, status);
	}

	/**
	 * Returns the number of elements where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching elements
	 */
	public static int countByG_S(long groupId, int status) {
		return getPersistence().countByG_S(groupId, status);
	}

	/**
	 * Returns the number of elements that the user has permission to view where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching elements that the user has permission to view
	 */
	public static int filterCountByG_S(long groupId, int status) {
		return getPersistence().filterCountByG_S(groupId, status);
	}

	/**
	 * Returns all the elements where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching elements
	 */
	public static List<Element> findByG_T(long groupId, int type) {
		return getPersistence().findByG_T(groupId, type);
	}

	/**
	 * Returns a range of all the elements where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @return the range of matching elements
	 */
	public static List<Element> findByG_T(
		long groupId, int type, int start, int end) {

		return getPersistence().findByG_T(groupId, type, start, end);
	}

	/**
	 * Returns an ordered range of all the elements where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching elements
	 */
	public static List<Element> findByG_T(
		long groupId, int type, int start, int end,
		OrderByComparator<Element> orderByComparator) {

		return getPersistence().findByG_T(
			groupId, type, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the elements where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching elements
	 */
	public static List<Element> findByG_T(
		long groupId, int type, int start, int end,
		OrderByComparator<Element> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByG_T(
			groupId, type, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first element in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public static Element findByG_T_First(
			long groupId, int type,
			OrderByComparator<Element> orderByComparator)
		throws com.liferay.portal.search.tuning.blueprints.exception.
			NoSuchElementException {

		return getPersistence().findByG_T_First(
			groupId, type, orderByComparator);
	}

	/**
	 * Returns the first element in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching element, or <code>null</code> if a matching element could not be found
	 */
	public static Element fetchByG_T_First(
		long groupId, int type, OrderByComparator<Element> orderByComparator) {

		return getPersistence().fetchByG_T_First(
			groupId, type, orderByComparator);
	}

	/**
	 * Returns the last element in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public static Element findByG_T_Last(
			long groupId, int type,
			OrderByComparator<Element> orderByComparator)
		throws com.liferay.portal.search.tuning.blueprints.exception.
			NoSuchElementException {

		return getPersistence().findByG_T_Last(
			groupId, type, orderByComparator);
	}

	/**
	 * Returns the last element in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching element, or <code>null</code> if a matching element could not be found
	 */
	public static Element fetchByG_T_Last(
		long groupId, int type, OrderByComparator<Element> orderByComparator) {

		return getPersistence().fetchByG_T_Last(
			groupId, type, orderByComparator);
	}

	/**
	 * Returns the elements before and after the current element in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param elementId the primary key of the current element
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next element
	 * @throws NoSuchElementException if a element with the primary key could not be found
	 */
	public static Element[] findByG_T_PrevAndNext(
			long elementId, long groupId, int type,
			OrderByComparator<Element> orderByComparator)
		throws com.liferay.portal.search.tuning.blueprints.exception.
			NoSuchElementException {

		return getPersistence().findByG_T_PrevAndNext(
			elementId, groupId, type, orderByComparator);
	}

	/**
	 * Returns all the elements that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching elements that the user has permission to view
	 */
	public static List<Element> filterFindByG_T(long groupId, int type) {
		return getPersistence().filterFindByG_T(groupId, type);
	}

	/**
	 * Returns a range of all the elements that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @return the range of matching elements that the user has permission to view
	 */
	public static List<Element> filterFindByG_T(
		long groupId, int type, int start, int end) {

		return getPersistence().filterFindByG_T(groupId, type, start, end);
	}

	/**
	 * Returns an ordered range of all the elements that the user has permissions to view where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching elements that the user has permission to view
	 */
	public static List<Element> filterFindByG_T(
		long groupId, int type, int start, int end,
		OrderByComparator<Element> orderByComparator) {

		return getPersistence().filterFindByG_T(
			groupId, type, start, end, orderByComparator);
	}

	/**
	 * Returns the elements before and after the current element in the ordered set of elements that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param elementId the primary key of the current element
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next element
	 * @throws NoSuchElementException if a element with the primary key could not be found
	 */
	public static Element[] filterFindByG_T_PrevAndNext(
			long elementId, long groupId, int type,
			OrderByComparator<Element> orderByComparator)
		throws com.liferay.portal.search.tuning.blueprints.exception.
			NoSuchElementException {

		return getPersistence().filterFindByG_T_PrevAndNext(
			elementId, groupId, type, orderByComparator);
	}

	/**
	 * Removes all the elements where groupId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 */
	public static void removeByG_T(long groupId, int type) {
		getPersistence().removeByG_T(groupId, type);
	}

	/**
	 * Returns the number of elements where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching elements
	 */
	public static int countByG_T(long groupId, int type) {
		return getPersistence().countByG_T(groupId, type);
	}

	/**
	 * Returns the number of elements that the user has permission to view where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching elements that the user has permission to view
	 */
	public static int filterCountByG_T(long groupId, int type) {
		return getPersistence().filterCountByG_T(groupId, type);
	}

	/**
	 * Returns all the elements where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching elements
	 */
	public static List<Element> findByC_T(long companyId, int type) {
		return getPersistence().findByC_T(companyId, type);
	}

	/**
	 * Returns a range of all the elements where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @return the range of matching elements
	 */
	public static List<Element> findByC_T(
		long companyId, int type, int start, int end) {

		return getPersistence().findByC_T(companyId, type, start, end);
	}

	/**
	 * Returns an ordered range of all the elements where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching elements
	 */
	public static List<Element> findByC_T(
		long companyId, int type, int start, int end,
		OrderByComparator<Element> orderByComparator) {

		return getPersistence().findByC_T(
			companyId, type, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the elements where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching elements
	 */
	public static List<Element> findByC_T(
		long companyId, int type, int start, int end,
		OrderByComparator<Element> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByC_T(
			companyId, type, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first element in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public static Element findByC_T_First(
			long companyId, int type,
			OrderByComparator<Element> orderByComparator)
		throws com.liferay.portal.search.tuning.blueprints.exception.
			NoSuchElementException {

		return getPersistence().findByC_T_First(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the first element in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching element, or <code>null</code> if a matching element could not be found
	 */
	public static Element fetchByC_T_First(
		long companyId, int type,
		OrderByComparator<Element> orderByComparator) {

		return getPersistence().fetchByC_T_First(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the last element in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public static Element findByC_T_Last(
			long companyId, int type,
			OrderByComparator<Element> orderByComparator)
		throws com.liferay.portal.search.tuning.blueprints.exception.
			NoSuchElementException {

		return getPersistence().findByC_T_Last(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the last element in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching element, or <code>null</code> if a matching element could not be found
	 */
	public static Element fetchByC_T_Last(
		long companyId, int type,
		OrderByComparator<Element> orderByComparator) {

		return getPersistence().fetchByC_T_Last(
			companyId, type, orderByComparator);
	}

	/**
	 * Returns the elements before and after the current element in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param elementId the primary key of the current element
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next element
	 * @throws NoSuchElementException if a element with the primary key could not be found
	 */
	public static Element[] findByC_T_PrevAndNext(
			long elementId, long companyId, int type,
			OrderByComparator<Element> orderByComparator)
		throws com.liferay.portal.search.tuning.blueprints.exception.
			NoSuchElementException {

		return getPersistence().findByC_T_PrevAndNext(
			elementId, companyId, type, orderByComparator);
	}

	/**
	 * Removes all the elements where companyId = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	public static void removeByC_T(long companyId, int type) {
		getPersistence().removeByC_T(companyId, type);
	}

	/**
	 * Returns the number of elements where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching elements
	 */
	public static int countByC_T(long companyId, int type) {
		return getPersistence().countByC_T(companyId, type);
	}

	/**
	 * Returns all the elements where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @return the matching elements
	 */
	public static List<Element> findByG_S_T(
		long groupId, int status, int type) {

		return getPersistence().findByG_S_T(groupId, status, type);
	}

	/**
	 * Returns a range of all the elements where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @return the range of matching elements
	 */
	public static List<Element> findByG_S_T(
		long groupId, int status, int type, int start, int end) {

		return getPersistence().findByG_S_T(groupId, status, type, start, end);
	}

	/**
	 * Returns an ordered range of all the elements where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching elements
	 */
	public static List<Element> findByG_S_T(
		long groupId, int status, int type, int start, int end,
		OrderByComparator<Element> orderByComparator) {

		return getPersistence().findByG_S_T(
			groupId, status, type, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the elements where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching elements
	 */
	public static List<Element> findByG_S_T(
		long groupId, int status, int type, int start, int end,
		OrderByComparator<Element> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByG_S_T(
			groupId, status, type, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first element in the ordered set where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public static Element findByG_S_T_First(
			long groupId, int status, int type,
			OrderByComparator<Element> orderByComparator)
		throws com.liferay.portal.search.tuning.blueprints.exception.
			NoSuchElementException {

		return getPersistence().findByG_S_T_First(
			groupId, status, type, orderByComparator);
	}

	/**
	 * Returns the first element in the ordered set where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching element, or <code>null</code> if a matching element could not be found
	 */
	public static Element fetchByG_S_T_First(
		long groupId, int status, int type,
		OrderByComparator<Element> orderByComparator) {

		return getPersistence().fetchByG_S_T_First(
			groupId, status, type, orderByComparator);
	}

	/**
	 * Returns the last element in the ordered set where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching element
	 * @throws NoSuchElementException if a matching element could not be found
	 */
	public static Element findByG_S_T_Last(
			long groupId, int status, int type,
			OrderByComparator<Element> orderByComparator)
		throws com.liferay.portal.search.tuning.blueprints.exception.
			NoSuchElementException {

		return getPersistence().findByG_S_T_Last(
			groupId, status, type, orderByComparator);
	}

	/**
	 * Returns the last element in the ordered set where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching element, or <code>null</code> if a matching element could not be found
	 */
	public static Element fetchByG_S_T_Last(
		long groupId, int status, int type,
		OrderByComparator<Element> orderByComparator) {

		return getPersistence().fetchByG_S_T_Last(
			groupId, status, type, orderByComparator);
	}

	/**
	 * Returns the elements before and after the current element in the ordered set where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param elementId the primary key of the current element
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next element
	 * @throws NoSuchElementException if a element with the primary key could not be found
	 */
	public static Element[] findByG_S_T_PrevAndNext(
			long elementId, long groupId, int status, int type,
			OrderByComparator<Element> orderByComparator)
		throws com.liferay.portal.search.tuning.blueprints.exception.
			NoSuchElementException {

		return getPersistence().findByG_S_T_PrevAndNext(
			elementId, groupId, status, type, orderByComparator);
	}

	/**
	 * Returns all the elements that the user has permission to view where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @return the matching elements that the user has permission to view
	 */
	public static List<Element> filterFindByG_S_T(
		long groupId, int status, int type) {

		return getPersistence().filterFindByG_S_T(groupId, status, type);
	}

	/**
	 * Returns a range of all the elements that the user has permission to view where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @return the range of matching elements that the user has permission to view
	 */
	public static List<Element> filterFindByG_S_T(
		long groupId, int status, int type, int start, int end) {

		return getPersistence().filterFindByG_S_T(
			groupId, status, type, start, end);
	}

	/**
	 * Returns an ordered range of all the elements that the user has permissions to view where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching elements that the user has permission to view
	 */
	public static List<Element> filterFindByG_S_T(
		long groupId, int status, int type, int start, int end,
		OrderByComparator<Element> orderByComparator) {

		return getPersistence().filterFindByG_S_T(
			groupId, status, type, start, end, orderByComparator);
	}

	/**
	 * Returns the elements before and after the current element in the ordered set of elements that the user has permission to view where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param elementId the primary key of the current element
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next element
	 * @throws NoSuchElementException if a element with the primary key could not be found
	 */
	public static Element[] filterFindByG_S_T_PrevAndNext(
			long elementId, long groupId, int status, int type,
			OrderByComparator<Element> orderByComparator)
		throws com.liferay.portal.search.tuning.blueprints.exception.
			NoSuchElementException {

		return getPersistence().filterFindByG_S_T_PrevAndNext(
			elementId, groupId, status, type, orderByComparator);
	}

	/**
	 * Removes all the elements where groupId = &#63; and status = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 */
	public static void removeByG_S_T(long groupId, int status, int type) {
		getPersistence().removeByG_S_T(groupId, status, type);
	}

	/**
	 * Returns the number of elements where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @return the number of matching elements
	 */
	public static int countByG_S_T(long groupId, int status, int type) {
		return getPersistence().countByG_S_T(groupId, status, type);
	}

	/**
	 * Returns the number of elements that the user has permission to view where groupId = &#63; and status = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param type the type
	 * @return the number of matching elements that the user has permission to view
	 */
	public static int filterCountByG_S_T(long groupId, int status, int type) {
		return getPersistence().filterCountByG_S_T(groupId, status, type);
	}

	/**
	 * Caches the element in the entity cache if it is enabled.
	 *
	 * @param element the element
	 */
	public static void cacheResult(Element element) {
		getPersistence().cacheResult(element);
	}

	/**
	 * Caches the elements in the entity cache if it is enabled.
	 *
	 * @param elements the elements
	 */
	public static void cacheResult(List<Element> elements) {
		getPersistence().cacheResult(elements);
	}

	/**
	 * Creates a new element with the primary key. Does not add the element to the database.
	 *
	 * @param elementId the primary key for the new element
	 * @return the new element
	 */
	public static Element create(long elementId) {
		return getPersistence().create(elementId);
	}

	/**
	 * Removes the element with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param elementId the primary key of the element
	 * @return the element that was removed
	 * @throws NoSuchElementException if a element with the primary key could not be found
	 */
	public static Element remove(long elementId)
		throws com.liferay.portal.search.tuning.blueprints.exception.
			NoSuchElementException {

		return getPersistence().remove(elementId);
	}

	public static Element updateImpl(Element element) {
		return getPersistence().updateImpl(element);
	}

	/**
	 * Returns the element with the primary key or throws a <code>NoSuchElementException</code> if it could not be found.
	 *
	 * @param elementId the primary key of the element
	 * @return the element
	 * @throws NoSuchElementException if a element with the primary key could not be found
	 */
	public static Element findByPrimaryKey(long elementId)
		throws com.liferay.portal.search.tuning.blueprints.exception.
			NoSuchElementException {

		return getPersistence().findByPrimaryKey(elementId);
	}

	/**
	 * Returns the element with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param elementId the primary key of the element
	 * @return the element, or <code>null</code> if a element with the primary key could not be found
	 */
	public static Element fetchByPrimaryKey(long elementId) {
		return getPersistence().fetchByPrimaryKey(elementId);
	}

	/**
	 * Returns all the elements.
	 *
	 * @return the elements
	 */
	public static List<Element> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the elements.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @return the range of elements
	 */
	public static List<Element> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the elements.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of elements
	 */
	public static List<Element> findAll(
		int start, int end, OrderByComparator<Element> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the elements.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ElementModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of elements
	 */
	public static List<Element> findAll(
		int start, int end, OrderByComparator<Element> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the elements from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of elements.
	 *
	 * @return the number of elements
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ElementPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ElementPersistence, ElementPersistence>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ElementPersistence.class);

		ServiceTracker<ElementPersistence, ElementPersistence> serviceTracker =
			new ServiceTracker<ElementPersistence, ElementPersistence>(
				bundle.getBundleContext(), ElementPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}