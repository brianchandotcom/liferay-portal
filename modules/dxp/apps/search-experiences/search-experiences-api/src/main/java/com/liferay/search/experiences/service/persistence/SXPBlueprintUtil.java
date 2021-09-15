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

package com.liferay.search.experiences.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.search.experiences.model.SXPBlueprint;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the sxp blueprint service. This utility wraps <code>com.liferay.search.experiences.service.persistence.impl.SXPBlueprintPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SXPBlueprintPersistence
 * @generated
 */
public class SXPBlueprintUtil {

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
	public static void clearCache(SXPBlueprint sxpBlueprint) {
		getPersistence().clearCache(sxpBlueprint);
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
	public static Map<Serializable, SXPBlueprint> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<SXPBlueprint> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SXPBlueprint> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SXPBlueprint> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SXPBlueprint> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static SXPBlueprint update(SXPBlueprint sxpBlueprint) {
		return getPersistence().update(sxpBlueprint);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static SXPBlueprint update(
		SXPBlueprint sxpBlueprint, ServiceContext serviceContext) {

		return getPersistence().update(sxpBlueprint, serviceContext);
	}

	/**
	 * Returns all the sxp blueprints where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching sxp blueprints
	 */
	public static List<SXPBlueprint> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the sxp blueprints where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @return the range of matching sxp blueprints
	 */
	public static List<SXPBlueprint> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the sxp blueprints where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sxp blueprints
	 */
	public static List<SXPBlueprint> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SXPBlueprint> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the sxp blueprints where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sxp blueprints
	 */
	public static List<SXPBlueprint> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<SXPBlueprint> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first sxp blueprint in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a matching sxp blueprint could not be found
	 */
	public static SXPBlueprint findByUuid_First(
			String uuid, OrderByComparator<SXPBlueprint> orderByComparator)
		throws com.liferay.search.experiences.exception.
			NoSuchSXPBlueprintException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first sxp blueprint in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp blueprint, or <code>null</code> if a matching sxp blueprint could not be found
	 */
	public static SXPBlueprint fetchByUuid_First(
		String uuid, OrderByComparator<SXPBlueprint> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last sxp blueprint in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a matching sxp blueprint could not be found
	 */
	public static SXPBlueprint findByUuid_Last(
			String uuid, OrderByComparator<SXPBlueprint> orderByComparator)
		throws com.liferay.search.experiences.exception.
			NoSuchSXPBlueprintException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last sxp blueprint in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp blueprint, or <code>null</code> if a matching sxp blueprint could not be found
	 */
	public static SXPBlueprint fetchByUuid_Last(
		String uuid, OrderByComparator<SXPBlueprint> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the sxp blueprints before and after the current sxp blueprint in the ordered set where uuid = &#63;.
	 *
	 * @param sxpBlueprintId the primary key of the current sxp blueprint
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a sxp blueprint with the primary key could not be found
	 */
	public static SXPBlueprint[] findByUuid_PrevAndNext(
			long sxpBlueprintId, String uuid,
			OrderByComparator<SXPBlueprint> orderByComparator)
		throws com.liferay.search.experiences.exception.
			NoSuchSXPBlueprintException {

		return getPersistence().findByUuid_PrevAndNext(
			sxpBlueprintId, uuid, orderByComparator);
	}

	/**
	 * Removes all the sxp blueprints where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of sxp blueprints where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching sxp blueprints
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the sxp blueprint where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchSXPBlueprintException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a matching sxp blueprint could not be found
	 */
	public static SXPBlueprint findByUUID_G(String uuid, long groupId)
		throws com.liferay.search.experiences.exception.
			NoSuchSXPBlueprintException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the sxp blueprint where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching sxp blueprint, or <code>null</code> if a matching sxp blueprint could not be found
	 */
	public static SXPBlueprint fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the sxp blueprint where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching sxp blueprint, or <code>null</code> if a matching sxp blueprint could not be found
	 */
	public static SXPBlueprint fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the sxp blueprint where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the sxp blueprint that was removed
	 */
	public static SXPBlueprint removeByUUID_G(String uuid, long groupId)
		throws com.liferay.search.experiences.exception.
			NoSuchSXPBlueprintException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of sxp blueprints where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching sxp blueprints
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the sxp blueprints where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching sxp blueprints
	 */
	public static List<SXPBlueprint> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the sxp blueprints where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @return the range of matching sxp blueprints
	 */
	public static List<SXPBlueprint> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the sxp blueprints where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sxp blueprints
	 */
	public static List<SXPBlueprint> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SXPBlueprint> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the sxp blueprints where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sxp blueprints
	 */
	public static List<SXPBlueprint> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<SXPBlueprint> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first sxp blueprint in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a matching sxp blueprint could not be found
	 */
	public static SXPBlueprint findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<SXPBlueprint> orderByComparator)
		throws com.liferay.search.experiences.exception.
			NoSuchSXPBlueprintException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first sxp blueprint in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp blueprint, or <code>null</code> if a matching sxp blueprint could not be found
	 */
	public static SXPBlueprint fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<SXPBlueprint> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last sxp blueprint in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a matching sxp blueprint could not be found
	 */
	public static SXPBlueprint findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<SXPBlueprint> orderByComparator)
		throws com.liferay.search.experiences.exception.
			NoSuchSXPBlueprintException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last sxp blueprint in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp blueprint, or <code>null</code> if a matching sxp blueprint could not be found
	 */
	public static SXPBlueprint fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<SXPBlueprint> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the sxp blueprints before and after the current sxp blueprint in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param sxpBlueprintId the primary key of the current sxp blueprint
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a sxp blueprint with the primary key could not be found
	 */
	public static SXPBlueprint[] findByUuid_C_PrevAndNext(
			long sxpBlueprintId, String uuid, long companyId,
			OrderByComparator<SXPBlueprint> orderByComparator)
		throws com.liferay.search.experiences.exception.
			NoSuchSXPBlueprintException {

		return getPersistence().findByUuid_C_PrevAndNext(
			sxpBlueprintId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the sxp blueprints where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of sxp blueprints where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching sxp blueprints
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the sxp blueprints where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching sxp blueprints
	 */
	public static List<SXPBlueprint> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the sxp blueprints where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @return the range of matching sxp blueprints
	 */
	public static List<SXPBlueprint> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the sxp blueprints where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sxp blueprints
	 */
	public static List<SXPBlueprint> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SXPBlueprint> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the sxp blueprints where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sxp blueprints
	 */
	public static List<SXPBlueprint> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<SXPBlueprint> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first sxp blueprint in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a matching sxp blueprint could not be found
	 */
	public static SXPBlueprint findByGroupId_First(
			long groupId, OrderByComparator<SXPBlueprint> orderByComparator)
		throws com.liferay.search.experiences.exception.
			NoSuchSXPBlueprintException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first sxp blueprint in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp blueprint, or <code>null</code> if a matching sxp blueprint could not be found
	 */
	public static SXPBlueprint fetchByGroupId_First(
		long groupId, OrderByComparator<SXPBlueprint> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last sxp blueprint in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a matching sxp blueprint could not be found
	 */
	public static SXPBlueprint findByGroupId_Last(
			long groupId, OrderByComparator<SXPBlueprint> orderByComparator)
		throws com.liferay.search.experiences.exception.
			NoSuchSXPBlueprintException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last sxp blueprint in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp blueprint, or <code>null</code> if a matching sxp blueprint could not be found
	 */
	public static SXPBlueprint fetchByGroupId_Last(
		long groupId, OrderByComparator<SXPBlueprint> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the sxp blueprints before and after the current sxp blueprint in the ordered set where groupId = &#63;.
	 *
	 * @param sxpBlueprintId the primary key of the current sxp blueprint
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a sxp blueprint with the primary key could not be found
	 */
	public static SXPBlueprint[] findByGroupId_PrevAndNext(
			long sxpBlueprintId, long groupId,
			OrderByComparator<SXPBlueprint> orderByComparator)
		throws com.liferay.search.experiences.exception.
			NoSuchSXPBlueprintException {

		return getPersistence().findByGroupId_PrevAndNext(
			sxpBlueprintId, groupId, orderByComparator);
	}

	/**
	 * Removes all the sxp blueprints where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of sxp blueprints where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching sxp blueprints
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns all the sxp blueprints where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching sxp blueprints
	 */
	public static List<SXPBlueprint> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the sxp blueprints where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @return the range of matching sxp blueprints
	 */
	public static List<SXPBlueprint> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the sxp blueprints where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sxp blueprints
	 */
	public static List<SXPBlueprint> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<SXPBlueprint> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the sxp blueprints where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sxp blueprints
	 */
	public static List<SXPBlueprint> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<SXPBlueprint> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first sxp blueprint in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a matching sxp blueprint could not be found
	 */
	public static SXPBlueprint findByCompanyId_First(
			long companyId, OrderByComparator<SXPBlueprint> orderByComparator)
		throws com.liferay.search.experiences.exception.
			NoSuchSXPBlueprintException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first sxp blueprint in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp blueprint, or <code>null</code> if a matching sxp blueprint could not be found
	 */
	public static SXPBlueprint fetchByCompanyId_First(
		long companyId, OrderByComparator<SXPBlueprint> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last sxp blueprint in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a matching sxp blueprint could not be found
	 */
	public static SXPBlueprint findByCompanyId_Last(
			long companyId, OrderByComparator<SXPBlueprint> orderByComparator)
		throws com.liferay.search.experiences.exception.
			NoSuchSXPBlueprintException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last sxp blueprint in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp blueprint, or <code>null</code> if a matching sxp blueprint could not be found
	 */
	public static SXPBlueprint fetchByCompanyId_Last(
		long companyId, OrderByComparator<SXPBlueprint> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the sxp blueprints before and after the current sxp blueprint in the ordered set where companyId = &#63;.
	 *
	 * @param sxpBlueprintId the primary key of the current sxp blueprint
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a sxp blueprint with the primary key could not be found
	 */
	public static SXPBlueprint[] findByCompanyId_PrevAndNext(
			long sxpBlueprintId, long companyId,
			OrderByComparator<SXPBlueprint> orderByComparator)
		throws com.liferay.search.experiences.exception.
			NoSuchSXPBlueprintException {

		return getPersistence().findByCompanyId_PrevAndNext(
			sxpBlueprintId, companyId, orderByComparator);
	}

	/**
	 * Removes all the sxp blueprints where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of sxp blueprints where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching sxp blueprints
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns all the sxp blueprints where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching sxp blueprints
	 */
	public static List<SXPBlueprint> findByG_S(long groupId, int status) {
		return getPersistence().findByG_S(groupId, status);
	}

	/**
	 * Returns a range of all the sxp blueprints where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @return the range of matching sxp blueprints
	 */
	public static List<SXPBlueprint> findByG_S(
		long groupId, int status, int start, int end) {

		return getPersistence().findByG_S(groupId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the sxp blueprints where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sxp blueprints
	 */
	public static List<SXPBlueprint> findByG_S(
		long groupId, int status, int start, int end,
		OrderByComparator<SXPBlueprint> orderByComparator) {

		return getPersistence().findByG_S(
			groupId, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the sxp blueprints where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sxp blueprints
	 */
	public static List<SXPBlueprint> findByG_S(
		long groupId, int status, int start, int end,
		OrderByComparator<SXPBlueprint> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_S(
			groupId, status, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first sxp blueprint in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a matching sxp blueprint could not be found
	 */
	public static SXPBlueprint findByG_S_First(
			long groupId, int status,
			OrderByComparator<SXPBlueprint> orderByComparator)
		throws com.liferay.search.experiences.exception.
			NoSuchSXPBlueprintException {

		return getPersistence().findByG_S_First(
			groupId, status, orderByComparator);
	}

	/**
	 * Returns the first sxp blueprint in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp blueprint, or <code>null</code> if a matching sxp blueprint could not be found
	 */
	public static SXPBlueprint fetchByG_S_First(
		long groupId, int status,
		OrderByComparator<SXPBlueprint> orderByComparator) {

		return getPersistence().fetchByG_S_First(
			groupId, status, orderByComparator);
	}

	/**
	 * Returns the last sxp blueprint in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a matching sxp blueprint could not be found
	 */
	public static SXPBlueprint findByG_S_Last(
			long groupId, int status,
			OrderByComparator<SXPBlueprint> orderByComparator)
		throws com.liferay.search.experiences.exception.
			NoSuchSXPBlueprintException {

		return getPersistence().findByG_S_Last(
			groupId, status, orderByComparator);
	}

	/**
	 * Returns the last sxp blueprint in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp blueprint, or <code>null</code> if a matching sxp blueprint could not be found
	 */
	public static SXPBlueprint fetchByG_S_Last(
		long groupId, int status,
		OrderByComparator<SXPBlueprint> orderByComparator) {

		return getPersistence().fetchByG_S_Last(
			groupId, status, orderByComparator);
	}

	/**
	 * Returns the sxp blueprints before and after the current sxp blueprint in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param sxpBlueprintId the primary key of the current sxp blueprint
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a sxp blueprint with the primary key could not be found
	 */
	public static SXPBlueprint[] findByG_S_PrevAndNext(
			long sxpBlueprintId, long groupId, int status,
			OrderByComparator<SXPBlueprint> orderByComparator)
		throws com.liferay.search.experiences.exception.
			NoSuchSXPBlueprintException {

		return getPersistence().findByG_S_PrevAndNext(
			sxpBlueprintId, groupId, status, orderByComparator);
	}

	/**
	 * Removes all the sxp blueprints where groupId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 */
	public static void removeByG_S(long groupId, int status) {
		getPersistence().removeByG_S(groupId, status);
	}

	/**
	 * Returns the number of sxp blueprints where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching sxp blueprints
	 */
	public static int countByG_S(long groupId, int status) {
		return getPersistence().countByG_S(groupId, status);
	}

	/**
	 * Returns all the sxp blueprints where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching sxp blueprints
	 */
	public static List<SXPBlueprint> findByG_S_T(long groupId, int status) {
		return getPersistence().findByG_S_T(groupId, status);
	}

	/**
	 * Returns a range of all the sxp blueprints where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @return the range of matching sxp blueprints
	 */
	public static List<SXPBlueprint> findByG_S_T(
		long groupId, int status, int start, int end) {

		return getPersistence().findByG_S_T(groupId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the sxp blueprints where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sxp blueprints
	 */
	public static List<SXPBlueprint> findByG_S_T(
		long groupId, int status, int start, int end,
		OrderByComparator<SXPBlueprint> orderByComparator) {

		return getPersistence().findByG_S_T(
			groupId, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the sxp blueprints where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sxp blueprints
	 */
	public static List<SXPBlueprint> findByG_S_T(
		long groupId, int status, int start, int end,
		OrderByComparator<SXPBlueprint> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByG_S_T(
			groupId, status, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first sxp blueprint in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a matching sxp blueprint could not be found
	 */
	public static SXPBlueprint findByG_S_T_First(
			long groupId, int status,
			OrderByComparator<SXPBlueprint> orderByComparator)
		throws com.liferay.search.experiences.exception.
			NoSuchSXPBlueprintException {

		return getPersistence().findByG_S_T_First(
			groupId, status, orderByComparator);
	}

	/**
	 * Returns the first sxp blueprint in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sxp blueprint, or <code>null</code> if a matching sxp blueprint could not be found
	 */
	public static SXPBlueprint fetchByG_S_T_First(
		long groupId, int status,
		OrderByComparator<SXPBlueprint> orderByComparator) {

		return getPersistence().fetchByG_S_T_First(
			groupId, status, orderByComparator);
	}

	/**
	 * Returns the last sxp blueprint in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a matching sxp blueprint could not be found
	 */
	public static SXPBlueprint findByG_S_T_Last(
			long groupId, int status,
			OrderByComparator<SXPBlueprint> orderByComparator)
		throws com.liferay.search.experiences.exception.
			NoSuchSXPBlueprintException {

		return getPersistence().findByG_S_T_Last(
			groupId, status, orderByComparator);
	}

	/**
	 * Returns the last sxp blueprint in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sxp blueprint, or <code>null</code> if a matching sxp blueprint could not be found
	 */
	public static SXPBlueprint fetchByG_S_T_Last(
		long groupId, int status,
		OrderByComparator<SXPBlueprint> orderByComparator) {

		return getPersistence().fetchByG_S_T_Last(
			groupId, status, orderByComparator);
	}

	/**
	 * Returns the sxp blueprints before and after the current sxp blueprint in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param sxpBlueprintId the primary key of the current sxp blueprint
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a sxp blueprint with the primary key could not be found
	 */
	public static SXPBlueprint[] findByG_S_T_PrevAndNext(
			long sxpBlueprintId, long groupId, int status,
			OrderByComparator<SXPBlueprint> orderByComparator)
		throws com.liferay.search.experiences.exception.
			NoSuchSXPBlueprintException {

		return getPersistence().findByG_S_T_PrevAndNext(
			sxpBlueprintId, groupId, status, orderByComparator);
	}

	/**
	 * Removes all the sxp blueprints where groupId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 */
	public static void removeByG_S_T(long groupId, int status) {
		getPersistence().removeByG_S_T(groupId, status);
	}

	/**
	 * Returns the number of sxp blueprints where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching sxp blueprints
	 */
	public static int countByG_S_T(long groupId, int status) {
		return getPersistence().countByG_S_T(groupId, status);
	}

	/**
	 * Caches the sxp blueprint in the entity cache if it is enabled.
	 *
	 * @param sxpBlueprint the sxp blueprint
	 */
	public static void cacheResult(SXPBlueprint sxpBlueprint) {
		getPersistence().cacheResult(sxpBlueprint);
	}

	/**
	 * Caches the sxp blueprints in the entity cache if it is enabled.
	 *
	 * @param sxpBlueprints the sxp blueprints
	 */
	public static void cacheResult(List<SXPBlueprint> sxpBlueprints) {
		getPersistence().cacheResult(sxpBlueprints);
	}

	/**
	 * Creates a new sxp blueprint with the primary key. Does not add the sxp blueprint to the database.
	 *
	 * @param sxpBlueprintId the primary key for the new sxp blueprint
	 * @return the new sxp blueprint
	 */
	public static SXPBlueprint create(long sxpBlueprintId) {
		return getPersistence().create(sxpBlueprintId);
	}

	/**
	 * Removes the sxp blueprint with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param sxpBlueprintId the primary key of the sxp blueprint
	 * @return the sxp blueprint that was removed
	 * @throws NoSuchSXPBlueprintException if a sxp blueprint with the primary key could not be found
	 */
	public static SXPBlueprint remove(long sxpBlueprintId)
		throws com.liferay.search.experiences.exception.
			NoSuchSXPBlueprintException {

		return getPersistence().remove(sxpBlueprintId);
	}

	public static SXPBlueprint updateImpl(SXPBlueprint sxpBlueprint) {
		return getPersistence().updateImpl(sxpBlueprint);
	}

	/**
	 * Returns the sxp blueprint with the primary key or throws a <code>NoSuchSXPBlueprintException</code> if it could not be found.
	 *
	 * @param sxpBlueprintId the primary key of the sxp blueprint
	 * @return the sxp blueprint
	 * @throws NoSuchSXPBlueprintException if a sxp blueprint with the primary key could not be found
	 */
	public static SXPBlueprint findByPrimaryKey(long sxpBlueprintId)
		throws com.liferay.search.experiences.exception.
			NoSuchSXPBlueprintException {

		return getPersistence().findByPrimaryKey(sxpBlueprintId);
	}

	/**
	 * Returns the sxp blueprint with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param sxpBlueprintId the primary key of the sxp blueprint
	 * @return the sxp blueprint, or <code>null</code> if a sxp blueprint with the primary key could not be found
	 */
	public static SXPBlueprint fetchByPrimaryKey(long sxpBlueprintId) {
		return getPersistence().fetchByPrimaryKey(sxpBlueprintId);
	}

	/**
	 * Returns all the sxp blueprints.
	 *
	 * @return the sxp blueprints
	 */
	public static List<SXPBlueprint> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the sxp blueprints.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @return the range of sxp blueprints
	 */
	public static List<SXPBlueprint> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the sxp blueprints.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of sxp blueprints
	 */
	public static List<SXPBlueprint> findAll(
		int start, int end, OrderByComparator<SXPBlueprint> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the sxp blueprints.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SXPBlueprintModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sxp blueprints
	 * @param end the upper bound of the range of sxp blueprints (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of sxp blueprints
	 */
	public static List<SXPBlueprint> findAll(
		int start, int end, OrderByComparator<SXPBlueprint> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the sxp blueprints from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of sxp blueprints.
	 *
	 * @return the number of sxp blueprints
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static SXPBlueprintPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<SXPBlueprintPersistence, SXPBlueprintPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SXPBlueprintPersistence.class);

		ServiceTracker<SXPBlueprintPersistence, SXPBlueprintPersistence>
			serviceTracker =
				new ServiceTracker
					<SXPBlueprintPersistence, SXPBlueprintPersistence>(
						bundle.getBundleContext(),
						SXPBlueprintPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}