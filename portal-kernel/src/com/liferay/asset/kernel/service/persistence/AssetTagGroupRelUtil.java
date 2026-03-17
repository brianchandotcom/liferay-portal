/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.service.persistence;

import com.liferay.asset.kernel.model.AssetTagGroupRel;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the asset tag group rel service. This utility wraps <code>com.liferay.portlet.asset.service.persistence.impl.AssetTagGroupRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagGroupRelPersistence
 * @generated
 */
public class AssetTagGroupRelUtil {

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
	public static void clearCache(AssetTagGroupRel assetTagGroupRel) {
		getPersistence().clearCache(assetTagGroupRel);
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
	public static Map<Serializable, AssetTagGroupRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AssetTagGroupRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetTagGroupRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AssetTagGroupRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AssetTagGroupRel> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AssetTagGroupRel update(AssetTagGroupRel assetTagGroupRel) {
		return getPersistence().update(assetTagGroupRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AssetTagGroupRel update(
		AssetTagGroupRel assetTagGroupRel, ServiceContext serviceContext) {

		return getPersistence().update(assetTagGroupRel, serviceContext);
	}

	/**
	 * Returns all the asset tag group rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset tag group rels
	 */
	public static List<AssetTagGroupRel> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the asset tag group rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @return the range of matching asset tag group rels
	 */
	public static List<AssetTagGroupRel> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the asset tag group rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset tag group rels
	 */
	public static List<AssetTagGroupRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetTagGroupRel> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset tag group rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset tag group rels
	 */
	public static List<AssetTagGroupRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetTagGroupRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset tag group rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag group rel
	 * @throws NoSuchTagGroupRelException if a matching asset tag group rel could not be found
	 */
	public static AssetTagGroupRel findByUuid_First(
			String uuid, OrderByComparator<AssetTagGroupRel> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchTagGroupRelException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first asset tag group rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	public static AssetTagGroupRel fetchByUuid_First(
		String uuid, OrderByComparator<AssetTagGroupRel> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last asset tag group rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag group rel
	 * @throws NoSuchTagGroupRelException if a matching asset tag group rel could not be found
	 */
	public static AssetTagGroupRel findByUuid_Last(
			String uuid, OrderByComparator<AssetTagGroupRel> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchTagGroupRelException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last asset tag group rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	public static AssetTagGroupRel fetchByUuid_Last(
		String uuid, OrderByComparator<AssetTagGroupRel> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Removes all the asset tag group rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of asset tag group rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset tag group rels
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the asset tag group rel where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchTagGroupRelException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset tag group rel
	 * @throws NoSuchTagGroupRelException if a matching asset tag group rel could not be found
	 */
	public static AssetTagGroupRel findByUUID_G(String uuid, long groupId)
		throws com.liferay.asset.kernel.exception.NoSuchTagGroupRelException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the asset tag group rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	public static AssetTagGroupRel fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the asset tag group rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	public static AssetTagGroupRel fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the asset tag group rel where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the asset tag group rel that was removed
	 */
	public static AssetTagGroupRel removeByUUID_G(String uuid, long groupId)
		throws com.liferay.asset.kernel.exception.NoSuchTagGroupRelException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of asset tag group rels where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching asset tag group rels
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the asset tag group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching asset tag group rels
	 */
	public static List<AssetTagGroupRel> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the asset tag group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @return the range of matching asset tag group rels
	 */
	public static List<AssetTagGroupRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the asset tag group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset tag group rels
	 */
	public static List<AssetTagGroupRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetTagGroupRel> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset tag group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset tag group rels
	 */
	public static List<AssetTagGroupRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetTagGroupRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset tag group rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag group rel
	 * @throws NoSuchTagGroupRelException if a matching asset tag group rel could not be found
	 */
	public static AssetTagGroupRel findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<AssetTagGroupRel> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchTagGroupRelException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first asset tag group rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	public static AssetTagGroupRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<AssetTagGroupRel> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last asset tag group rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag group rel
	 * @throws NoSuchTagGroupRelException if a matching asset tag group rel could not be found
	 */
	public static AssetTagGroupRel findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<AssetTagGroupRel> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchTagGroupRelException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last asset tag group rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	public static AssetTagGroupRel fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<AssetTagGroupRel> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the asset tag group rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of asset tag group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching asset tag group rels
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the asset tag group rels where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching asset tag group rels
	 */
	public static List<AssetTagGroupRel> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the asset tag group rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @return the range of matching asset tag group rels
	 */
	public static List<AssetTagGroupRel> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the asset tag group rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset tag group rels
	 */
	public static List<AssetTagGroupRel> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AssetTagGroupRel> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset tag group rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset tag group rels
	 */
	public static List<AssetTagGroupRel> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AssetTagGroupRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset tag group rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag group rel
	 * @throws NoSuchTagGroupRelException if a matching asset tag group rel could not be found
	 */
	public static AssetTagGroupRel findByGroupId_First(
			long groupId, OrderByComparator<AssetTagGroupRel> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchTagGroupRelException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first asset tag group rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	public static AssetTagGroupRel fetchByGroupId_First(
		long groupId, OrderByComparator<AssetTagGroupRel> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last asset tag group rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag group rel
	 * @throws NoSuchTagGroupRelException if a matching asset tag group rel could not be found
	 */
	public static AssetTagGroupRel findByGroupId_Last(
			long groupId, OrderByComparator<AssetTagGroupRel> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchTagGroupRelException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last asset tag group rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	public static AssetTagGroupRel fetchByGroupId_Last(
		long groupId, OrderByComparator<AssetTagGroupRel> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Removes all the asset tag group rels where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of asset tag group rels where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching asset tag group rels
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns all the asset tag group rels where tagId = &#63;.
	 *
	 * @param tagId the tag ID
	 * @return the matching asset tag group rels
	 */
	public static List<AssetTagGroupRel> findByTagId(long tagId) {
		return getPersistence().findByTagId(tagId);
	}

	/**
	 * Returns a range of all the asset tag group rels where tagId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param tagId the tag ID
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @return the range of matching asset tag group rels
	 */
	public static List<AssetTagGroupRel> findByTagId(
		long tagId, int start, int end) {

		return getPersistence().findByTagId(tagId, start, end);
	}

	/**
	 * Returns an ordered range of all the asset tag group rels where tagId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param tagId the tag ID
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset tag group rels
	 */
	public static List<AssetTagGroupRel> findByTagId(
		long tagId, int start, int end,
		OrderByComparator<AssetTagGroupRel> orderByComparator) {

		return getPersistence().findByTagId(
			tagId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset tag group rels where tagId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param tagId the tag ID
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset tag group rels
	 */
	public static List<AssetTagGroupRel> findByTagId(
		long tagId, int start, int end,
		OrderByComparator<AssetTagGroupRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByTagId(
			tagId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset tag group rel in the ordered set where tagId = &#63;.
	 *
	 * @param tagId the tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag group rel
	 * @throws NoSuchTagGroupRelException if a matching asset tag group rel could not be found
	 */
	public static AssetTagGroupRel findByTagId_First(
			long tagId, OrderByComparator<AssetTagGroupRel> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchTagGroupRelException {

		return getPersistence().findByTagId_First(tagId, orderByComparator);
	}

	/**
	 * Returns the first asset tag group rel in the ordered set where tagId = &#63;.
	 *
	 * @param tagId the tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	public static AssetTagGroupRel fetchByTagId_First(
		long tagId, OrderByComparator<AssetTagGroupRel> orderByComparator) {

		return getPersistence().fetchByTagId_First(tagId, orderByComparator);
	}

	/**
	 * Returns the last asset tag group rel in the ordered set where tagId = &#63;.
	 *
	 * @param tagId the tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag group rel
	 * @throws NoSuchTagGroupRelException if a matching asset tag group rel could not be found
	 */
	public static AssetTagGroupRel findByTagId_Last(
			long tagId, OrderByComparator<AssetTagGroupRel> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchTagGroupRelException {

		return getPersistence().findByTagId_Last(tagId, orderByComparator);
	}

	/**
	 * Returns the last asset tag group rel in the ordered set where tagId = &#63;.
	 *
	 * @param tagId the tag ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	public static AssetTagGroupRel fetchByTagId_Last(
		long tagId, OrderByComparator<AssetTagGroupRel> orderByComparator) {

		return getPersistence().fetchByTagId_Last(tagId, orderByComparator);
	}

	/**
	 * Removes all the asset tag group rels where tagId = &#63; from the database.
	 *
	 * @param tagId the tag ID
	 */
	public static void removeByTagId(long tagId) {
		getPersistence().removeByTagId(tagId);
	}

	/**
	 * Returns the number of asset tag group rels where tagId = &#63;.
	 *
	 * @param tagId the tag ID
	 * @return the number of matching asset tag group rels
	 */
	public static int countByTagId(long tagId) {
		return getPersistence().countByTagId(tagId);
	}

	/**
	 * Returns the asset tag group rel where groupId = &#63; and tagId = &#63; or throws a <code>NoSuchTagGroupRelException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param tagId the tag ID
	 * @return the matching asset tag group rel
	 * @throws NoSuchTagGroupRelException if a matching asset tag group rel could not be found
	 */
	public static AssetTagGroupRel findByG_T(long groupId, long tagId)
		throws com.liferay.asset.kernel.exception.NoSuchTagGroupRelException {

		return getPersistence().findByG_T(groupId, tagId);
	}

	/**
	 * Returns the asset tag group rel where groupId = &#63; and tagId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param tagId the tag ID
	 * @return the matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	public static AssetTagGroupRel fetchByG_T(long groupId, long tagId) {
		return getPersistence().fetchByG_T(groupId, tagId);
	}

	/**
	 * Returns the asset tag group rel where groupId = &#63; and tagId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param tagId the tag ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset tag group rel, or <code>null</code> if a matching asset tag group rel could not be found
	 */
	public static AssetTagGroupRel fetchByG_T(
		long groupId, long tagId, boolean useFinderCache) {

		return getPersistence().fetchByG_T(groupId, tagId, useFinderCache);
	}

	/**
	 * Removes the asset tag group rel where groupId = &#63; and tagId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param tagId the tag ID
	 * @return the asset tag group rel that was removed
	 */
	public static AssetTagGroupRel removeByG_T(long groupId, long tagId)
		throws com.liferay.asset.kernel.exception.NoSuchTagGroupRelException {

		return getPersistence().removeByG_T(groupId, tagId);
	}

	/**
	 * Returns the number of asset tag group rels where groupId = &#63; and tagId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param tagId the tag ID
	 * @return the number of matching asset tag group rels
	 */
	public static int countByG_T(long groupId, long tagId) {
		return getPersistence().countByG_T(groupId, tagId);
	}

	/**
	 * Caches the asset tag group rel in the entity cache if it is enabled.
	 *
	 * @param assetTagGroupRel the asset tag group rel
	 */
	public static void cacheResult(AssetTagGroupRel assetTagGroupRel) {
		getPersistence().cacheResult(assetTagGroupRel);
	}

	/**
	 * Caches the asset tag group rels in the entity cache if it is enabled.
	 *
	 * @param assetTagGroupRels the asset tag group rels
	 */
	public static void cacheResult(List<AssetTagGroupRel> assetTagGroupRels) {
		getPersistence().cacheResult(assetTagGroupRels);
	}

	/**
	 * Creates a new asset tag group rel with the primary key. Does not add the asset tag group rel to the database.
	 *
	 * @param assetTagGroupRelId the primary key for the new asset tag group rel
	 * @return the new asset tag group rel
	 */
	public static AssetTagGroupRel create(long assetTagGroupRelId) {
		return getPersistence().create(assetTagGroupRelId);
	}

	/**
	 * Removes the asset tag group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetTagGroupRelId the primary key of the asset tag group rel
	 * @return the asset tag group rel that was removed
	 * @throws NoSuchTagGroupRelException if a asset tag group rel with the primary key could not be found
	 */
	public static AssetTagGroupRel remove(long assetTagGroupRelId)
		throws com.liferay.asset.kernel.exception.NoSuchTagGroupRelException {

		return getPersistence().remove(assetTagGroupRelId);
	}

	public static AssetTagGroupRel updateImpl(
		AssetTagGroupRel assetTagGroupRel) {

		return getPersistence().updateImpl(assetTagGroupRel);
	}

	/**
	 * Returns the asset tag group rel with the primary key or throws a <code>NoSuchTagGroupRelException</code> if it could not be found.
	 *
	 * @param assetTagGroupRelId the primary key of the asset tag group rel
	 * @return the asset tag group rel
	 * @throws NoSuchTagGroupRelException if a asset tag group rel with the primary key could not be found
	 */
	public static AssetTagGroupRel findByPrimaryKey(long assetTagGroupRelId)
		throws com.liferay.asset.kernel.exception.NoSuchTagGroupRelException {

		return getPersistence().findByPrimaryKey(assetTagGroupRelId);
	}

	/**
	 * Returns the asset tag group rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetTagGroupRelId the primary key of the asset tag group rel
	 * @return the asset tag group rel, or <code>null</code> if a asset tag group rel with the primary key could not be found
	 */
	public static AssetTagGroupRel fetchByPrimaryKey(long assetTagGroupRelId) {
		return getPersistence().fetchByPrimaryKey(assetTagGroupRelId);
	}

	/**
	 * Returns all the asset tag group rels.
	 *
	 * @return the asset tag group rels
	 */
	public static List<AssetTagGroupRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the asset tag group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @return the range of asset tag group rels
	 */
	public static List<AssetTagGroupRel> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the asset tag group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset tag group rels
	 */
	public static List<AssetTagGroupRel> findAll(
		int start, int end,
		OrderByComparator<AssetTagGroupRel> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset tag group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetTagGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset tag group rels
	 * @param end the upper bound of the range of asset tag group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of asset tag group rels
	 */
	public static List<AssetTagGroupRel> findAll(
		int start, int end,
		OrderByComparator<AssetTagGroupRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the asset tag group rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of asset tag group rels.
	 *
	 * @return the number of asset tag group rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AssetTagGroupRelPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(AssetTagGroupRelPersistence persistence) {
		_persistence = persistence;
	}

	private static volatile AssetTagGroupRelPersistence _persistence;

}
// LIFERAY-SERVICE-BUILDER-HASH:1735986008