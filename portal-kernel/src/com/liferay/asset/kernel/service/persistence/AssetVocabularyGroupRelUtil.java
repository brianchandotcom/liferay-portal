/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.service.persistence;

import com.liferay.asset.kernel.model.AssetVocabularyGroupRel;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the asset vocabulary group rel service. This utility wraps <code>com.liferay.portlet.asset.service.persistence.impl.AssetVocabularyGroupRelPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetVocabularyGroupRelPersistence
 * @generated
 */
public class AssetVocabularyGroupRelUtil {

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
	public static void clearCache(
		AssetVocabularyGroupRel assetVocabularyGroupRel) {

		getPersistence().clearCache(assetVocabularyGroupRel);
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
	public static Map<Serializable, AssetVocabularyGroupRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AssetVocabularyGroupRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetVocabularyGroupRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AssetVocabularyGroupRel> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AssetVocabularyGroupRel> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AssetVocabularyGroupRel update(
		AssetVocabularyGroupRel assetVocabularyGroupRel) {

		return getPersistence().update(assetVocabularyGroupRel);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AssetVocabularyGroupRel update(
		AssetVocabularyGroupRel assetVocabularyGroupRel,
		ServiceContext serviceContext) {

		return getPersistence().update(assetVocabularyGroupRel, serviceContext);
	}

	/**
	 * Returns all the asset vocabulary group rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching asset vocabulary group rels
	 */
	public static List<AssetVocabularyGroupRel> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the asset vocabulary group rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset vocabulary group rels
	 * @param end the upper bound of the range of asset vocabulary group rels (not inclusive)
	 * @return the range of matching asset vocabulary group rels
	 */
	public static List<AssetVocabularyGroupRel> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the asset vocabulary group rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset vocabulary group rels
	 * @param end the upper bound of the range of asset vocabulary group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset vocabulary group rels
	 */
	public static List<AssetVocabularyGroupRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetVocabularyGroupRel> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset vocabulary group rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of asset vocabulary group rels
	 * @param end the upper bound of the range of asset vocabulary group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset vocabulary group rels
	 */
	public static List<AssetVocabularyGroupRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<AssetVocabularyGroupRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset vocabulary group rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary group rel
	 * @throws NoSuchVocabularyGroupRelException if a matching asset vocabulary group rel could not be found
	 */
	public static AssetVocabularyGroupRel findByUuid_First(
			String uuid,
			OrderByComparator<AssetVocabularyGroupRel> orderByComparator)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyGroupRelException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first asset vocabulary group rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary group rel, or <code>null</code> if a matching asset vocabulary group rel could not be found
	 */
	public static AssetVocabularyGroupRel fetchByUuid_First(
		String uuid,
		OrderByComparator<AssetVocabularyGroupRel> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last asset vocabulary group rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary group rel
	 * @throws NoSuchVocabularyGroupRelException if a matching asset vocabulary group rel could not be found
	 */
	public static AssetVocabularyGroupRel findByUuid_Last(
			String uuid,
			OrderByComparator<AssetVocabularyGroupRel> orderByComparator)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyGroupRelException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last asset vocabulary group rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary group rel, or <code>null</code> if a matching asset vocabulary group rel could not be found
	 */
	public static AssetVocabularyGroupRel fetchByUuid_Last(
		String uuid,
		OrderByComparator<AssetVocabularyGroupRel> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the asset vocabulary group rels before and after the current asset vocabulary group rel in the ordered set where uuid = &#63;.
	 *
	 * @param assetVocabularyGroupRelId the primary key of the current asset vocabulary group rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset vocabulary group rel
	 * @throws NoSuchVocabularyGroupRelException if a asset vocabulary group rel with the primary key could not be found
	 */
	public static AssetVocabularyGroupRel[] findByUuid_PrevAndNext(
			long assetVocabularyGroupRelId, String uuid,
			OrderByComparator<AssetVocabularyGroupRel> orderByComparator)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyGroupRelException {

		return getPersistence().findByUuid_PrevAndNext(
			assetVocabularyGroupRelId, uuid, orderByComparator);
	}

	/**
	 * Removes all the asset vocabulary group rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of asset vocabulary group rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching asset vocabulary group rels
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the asset vocabulary group rel where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchVocabularyGroupRelException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset vocabulary group rel
	 * @throws NoSuchVocabularyGroupRelException if a matching asset vocabulary group rel could not be found
	 */
	public static AssetVocabularyGroupRel findByUUID_G(
			String uuid, long groupId)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyGroupRelException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the asset vocabulary group rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching asset vocabulary group rel, or <code>null</code> if a matching asset vocabulary group rel could not be found
	 */
	public static AssetVocabularyGroupRel fetchByUUID_G(
		String uuid, long groupId) {

		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the asset vocabulary group rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset vocabulary group rel, or <code>null</code> if a matching asset vocabulary group rel could not be found
	 */
	public static AssetVocabularyGroupRel fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the asset vocabulary group rel where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the asset vocabulary group rel that was removed
	 */
	public static AssetVocabularyGroupRel removeByUUID_G(
			String uuid, long groupId)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyGroupRelException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of asset vocabulary group rels where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching asset vocabulary group rels
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the asset vocabulary group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching asset vocabulary group rels
	 */
	public static List<AssetVocabularyGroupRel> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the asset vocabulary group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset vocabulary group rels
	 * @param end the upper bound of the range of asset vocabulary group rels (not inclusive)
	 * @return the range of matching asset vocabulary group rels
	 */
	public static List<AssetVocabularyGroupRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the asset vocabulary group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset vocabulary group rels
	 * @param end the upper bound of the range of asset vocabulary group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset vocabulary group rels
	 */
	public static List<AssetVocabularyGroupRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetVocabularyGroupRel> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset vocabulary group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset vocabulary group rels
	 * @param end the upper bound of the range of asset vocabulary group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset vocabulary group rels
	 */
	public static List<AssetVocabularyGroupRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<AssetVocabularyGroupRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset vocabulary group rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary group rel
	 * @throws NoSuchVocabularyGroupRelException if a matching asset vocabulary group rel could not be found
	 */
	public static AssetVocabularyGroupRel findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<AssetVocabularyGroupRel> orderByComparator)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyGroupRelException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first asset vocabulary group rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary group rel, or <code>null</code> if a matching asset vocabulary group rel could not be found
	 */
	public static AssetVocabularyGroupRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<AssetVocabularyGroupRel> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last asset vocabulary group rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary group rel
	 * @throws NoSuchVocabularyGroupRelException if a matching asset vocabulary group rel could not be found
	 */
	public static AssetVocabularyGroupRel findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<AssetVocabularyGroupRel> orderByComparator)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyGroupRelException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last asset vocabulary group rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary group rel, or <code>null</code> if a matching asset vocabulary group rel could not be found
	 */
	public static AssetVocabularyGroupRel fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<AssetVocabularyGroupRel> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the asset vocabulary group rels before and after the current asset vocabulary group rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param assetVocabularyGroupRelId the primary key of the current asset vocabulary group rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset vocabulary group rel
	 * @throws NoSuchVocabularyGroupRelException if a asset vocabulary group rel with the primary key could not be found
	 */
	public static AssetVocabularyGroupRel[] findByUuid_C_PrevAndNext(
			long assetVocabularyGroupRelId, String uuid, long companyId,
			OrderByComparator<AssetVocabularyGroupRel> orderByComparator)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyGroupRelException {

		return getPersistence().findByUuid_C_PrevAndNext(
			assetVocabularyGroupRelId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the asset vocabulary group rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of asset vocabulary group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching asset vocabulary group rels
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the asset vocabulary group rels where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching asset vocabulary group rels
	 */
	public static List<AssetVocabularyGroupRel> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the asset vocabulary group rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset vocabulary group rels
	 * @param end the upper bound of the range of asset vocabulary group rels (not inclusive)
	 * @return the range of matching asset vocabulary group rels
	 */
	public static List<AssetVocabularyGroupRel> findByGroupId(
		long groupId, int start, int end) {

		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the asset vocabulary group rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset vocabulary group rels
	 * @param end the upper bound of the range of asset vocabulary group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset vocabulary group rels
	 */
	public static List<AssetVocabularyGroupRel> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AssetVocabularyGroupRel> orderByComparator) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset vocabulary group rels where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of asset vocabulary group rels
	 * @param end the upper bound of the range of asset vocabulary group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset vocabulary group rels
	 */
	public static List<AssetVocabularyGroupRel> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<AssetVocabularyGroupRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByGroupId(
			groupId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset vocabulary group rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary group rel
	 * @throws NoSuchVocabularyGroupRelException if a matching asset vocabulary group rel could not be found
	 */
	public static AssetVocabularyGroupRel findByGroupId_First(
			long groupId,
			OrderByComparator<AssetVocabularyGroupRel> orderByComparator)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyGroupRelException {

		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	 * Returns the first asset vocabulary group rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary group rel, or <code>null</code> if a matching asset vocabulary group rel could not be found
	 */
	public static AssetVocabularyGroupRel fetchByGroupId_First(
		long groupId,
		OrderByComparator<AssetVocabularyGroupRel> orderByComparator) {

		return getPersistence().fetchByGroupId_First(
			groupId, orderByComparator);
	}

	/**
	 * Returns the last asset vocabulary group rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary group rel
	 * @throws NoSuchVocabularyGroupRelException if a matching asset vocabulary group rel could not be found
	 */
	public static AssetVocabularyGroupRel findByGroupId_Last(
			long groupId,
			OrderByComparator<AssetVocabularyGroupRel> orderByComparator)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyGroupRelException {

		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the last asset vocabulary group rel in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary group rel, or <code>null</code> if a matching asset vocabulary group rel could not be found
	 */
	public static AssetVocabularyGroupRel fetchByGroupId_Last(
		long groupId,
		OrderByComparator<AssetVocabularyGroupRel> orderByComparator) {

		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	 * Returns the asset vocabulary group rels before and after the current asset vocabulary group rel in the ordered set where groupId = &#63;.
	 *
	 * @param assetVocabularyGroupRelId the primary key of the current asset vocabulary group rel
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset vocabulary group rel
	 * @throws NoSuchVocabularyGroupRelException if a asset vocabulary group rel with the primary key could not be found
	 */
	public static AssetVocabularyGroupRel[] findByGroupId_PrevAndNext(
			long assetVocabularyGroupRelId, long groupId,
			OrderByComparator<AssetVocabularyGroupRel> orderByComparator)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyGroupRelException {

		return getPersistence().findByGroupId_PrevAndNext(
			assetVocabularyGroupRelId, groupId, orderByComparator);
	}

	/**
	 * Removes all the asset vocabulary group rels where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	 * Returns the number of asset vocabulary group rels where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching asset vocabulary group rels
	 */
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	 * Returns all the asset vocabulary group rels where vocabularyId = &#63;.
	 *
	 * @param vocabularyId the vocabulary ID
	 * @return the matching asset vocabulary group rels
	 */
	public static List<AssetVocabularyGroupRel> findByVocabularyId(
		long vocabularyId) {

		return getPersistence().findByVocabularyId(vocabularyId);
	}

	/**
	 * Returns a range of all the asset vocabulary group rels where vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset vocabulary group rels
	 * @param end the upper bound of the range of asset vocabulary group rels (not inclusive)
	 * @return the range of matching asset vocabulary group rels
	 */
	public static List<AssetVocabularyGroupRel> findByVocabularyId(
		long vocabularyId, int start, int end) {

		return getPersistence().findByVocabularyId(vocabularyId, start, end);
	}

	/**
	 * Returns an ordered range of all the asset vocabulary group rels where vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset vocabulary group rels
	 * @param end the upper bound of the range of asset vocabulary group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset vocabulary group rels
	 */
	public static List<AssetVocabularyGroupRel> findByVocabularyId(
		long vocabularyId, int start, int end,
		OrderByComparator<AssetVocabularyGroupRel> orderByComparator) {

		return getPersistence().findByVocabularyId(
			vocabularyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset vocabulary group rels where vocabularyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param vocabularyId the vocabulary ID
	 * @param start the lower bound of the range of asset vocabulary group rels
	 * @param end the upper bound of the range of asset vocabulary group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching asset vocabulary group rels
	 */
	public static List<AssetVocabularyGroupRel> findByVocabularyId(
		long vocabularyId, int start, int end,
		OrderByComparator<AssetVocabularyGroupRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByVocabularyId(
			vocabularyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first asset vocabulary group rel in the ordered set where vocabularyId = &#63;.
	 *
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary group rel
	 * @throws NoSuchVocabularyGroupRelException if a matching asset vocabulary group rel could not be found
	 */
	public static AssetVocabularyGroupRel findByVocabularyId_First(
			long vocabularyId,
			OrderByComparator<AssetVocabularyGroupRel> orderByComparator)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyGroupRelException {

		return getPersistence().findByVocabularyId_First(
			vocabularyId, orderByComparator);
	}

	/**
	 * Returns the first asset vocabulary group rel in the ordered set where vocabularyId = &#63;.
	 *
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset vocabulary group rel, or <code>null</code> if a matching asset vocabulary group rel could not be found
	 */
	public static AssetVocabularyGroupRel fetchByVocabularyId_First(
		long vocabularyId,
		OrderByComparator<AssetVocabularyGroupRel> orderByComparator) {

		return getPersistence().fetchByVocabularyId_First(
			vocabularyId, orderByComparator);
	}

	/**
	 * Returns the last asset vocabulary group rel in the ordered set where vocabularyId = &#63;.
	 *
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary group rel
	 * @throws NoSuchVocabularyGroupRelException if a matching asset vocabulary group rel could not be found
	 */
	public static AssetVocabularyGroupRel findByVocabularyId_Last(
			long vocabularyId,
			OrderByComparator<AssetVocabularyGroupRel> orderByComparator)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyGroupRelException {

		return getPersistence().findByVocabularyId_Last(
			vocabularyId, orderByComparator);
	}

	/**
	 * Returns the last asset vocabulary group rel in the ordered set where vocabularyId = &#63;.
	 *
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset vocabulary group rel, or <code>null</code> if a matching asset vocabulary group rel could not be found
	 */
	public static AssetVocabularyGroupRel fetchByVocabularyId_Last(
		long vocabularyId,
		OrderByComparator<AssetVocabularyGroupRel> orderByComparator) {

		return getPersistence().fetchByVocabularyId_Last(
			vocabularyId, orderByComparator);
	}

	/**
	 * Returns the asset vocabulary group rels before and after the current asset vocabulary group rel in the ordered set where vocabularyId = &#63;.
	 *
	 * @param assetVocabularyGroupRelId the primary key of the current asset vocabulary group rel
	 * @param vocabularyId the vocabulary ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset vocabulary group rel
	 * @throws NoSuchVocabularyGroupRelException if a asset vocabulary group rel with the primary key could not be found
	 */
	public static AssetVocabularyGroupRel[] findByVocabularyId_PrevAndNext(
			long assetVocabularyGroupRelId, long vocabularyId,
			OrderByComparator<AssetVocabularyGroupRel> orderByComparator)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyGroupRelException {

		return getPersistence().findByVocabularyId_PrevAndNext(
			assetVocabularyGroupRelId, vocabularyId, orderByComparator);
	}

	/**
	 * Removes all the asset vocabulary group rels where vocabularyId = &#63; from the database.
	 *
	 * @param vocabularyId the vocabulary ID
	 */
	public static void removeByVocabularyId(long vocabularyId) {
		getPersistence().removeByVocabularyId(vocabularyId);
	}

	/**
	 * Returns the number of asset vocabulary group rels where vocabularyId = &#63;.
	 *
	 * @param vocabularyId the vocabulary ID
	 * @return the number of matching asset vocabulary group rels
	 */
	public static int countByVocabularyId(long vocabularyId) {
		return getPersistence().countByVocabularyId(vocabularyId);
	}

	/**
	 * Returns the asset vocabulary group rel where groupId = &#63; and vocabularyId = &#63; or throws a <code>NoSuchVocabularyGroupRelException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @return the matching asset vocabulary group rel
	 * @throws NoSuchVocabularyGroupRelException if a matching asset vocabulary group rel could not be found
	 */
	public static AssetVocabularyGroupRel findByG_V(
			long groupId, long vocabularyId)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyGroupRelException {

		return getPersistence().findByG_V(groupId, vocabularyId);
	}

	/**
	 * Returns the asset vocabulary group rel where groupId = &#63; and vocabularyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @return the matching asset vocabulary group rel, or <code>null</code> if a matching asset vocabulary group rel could not be found
	 */
	public static AssetVocabularyGroupRel fetchByG_V(
		long groupId, long vocabularyId) {

		return getPersistence().fetchByG_V(groupId, vocabularyId);
	}

	/**
	 * Returns the asset vocabulary group rel where groupId = &#63; and vocabularyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching asset vocabulary group rel, or <code>null</code> if a matching asset vocabulary group rel could not be found
	 */
	public static AssetVocabularyGroupRel fetchByG_V(
		long groupId, long vocabularyId, boolean useFinderCache) {

		return getPersistence().fetchByG_V(
			groupId, vocabularyId, useFinderCache);
	}

	/**
	 * Removes the asset vocabulary group rel where groupId = &#63; and vocabularyId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @return the asset vocabulary group rel that was removed
	 */
	public static AssetVocabularyGroupRel removeByG_V(
			long groupId, long vocabularyId)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyGroupRelException {

		return getPersistence().removeByG_V(groupId, vocabularyId);
	}

	/**
	 * Returns the number of asset vocabulary group rels where groupId = &#63; and vocabularyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param vocabularyId the vocabulary ID
	 * @return the number of matching asset vocabulary group rels
	 */
	public static int countByG_V(long groupId, long vocabularyId) {
		return getPersistence().countByG_V(groupId, vocabularyId);
	}

	/**
	 * Caches the asset vocabulary group rel in the entity cache if it is enabled.
	 *
	 * @param assetVocabularyGroupRel the asset vocabulary group rel
	 */
	public static void cacheResult(
		AssetVocabularyGroupRel assetVocabularyGroupRel) {

		getPersistence().cacheResult(assetVocabularyGroupRel);
	}

	/**
	 * Caches the asset vocabulary group rels in the entity cache if it is enabled.
	 *
	 * @param assetVocabularyGroupRels the asset vocabulary group rels
	 */
	public static void cacheResult(
		List<AssetVocabularyGroupRel> assetVocabularyGroupRels) {

		getPersistence().cacheResult(assetVocabularyGroupRels);
	}

	/**
	 * Creates a new asset vocabulary group rel with the primary key. Does not add the asset vocabulary group rel to the database.
	 *
	 * @param assetVocabularyGroupRelId the primary key for the new asset vocabulary group rel
	 * @return the new asset vocabulary group rel
	 */
	public static AssetVocabularyGroupRel create(
		long assetVocabularyGroupRelId) {

		return getPersistence().create(assetVocabularyGroupRelId);
	}

	/**
	 * Removes the asset vocabulary group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetVocabularyGroupRelId the primary key of the asset vocabulary group rel
	 * @return the asset vocabulary group rel that was removed
	 * @throws NoSuchVocabularyGroupRelException if a asset vocabulary group rel with the primary key could not be found
	 */
	public static AssetVocabularyGroupRel remove(long assetVocabularyGroupRelId)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyGroupRelException {

		return getPersistence().remove(assetVocabularyGroupRelId);
	}

	public static AssetVocabularyGroupRel updateImpl(
		AssetVocabularyGroupRel assetVocabularyGroupRel) {

		return getPersistence().updateImpl(assetVocabularyGroupRel);
	}

	/**
	 * Returns the asset vocabulary group rel with the primary key or throws a <code>NoSuchVocabularyGroupRelException</code> if it could not be found.
	 *
	 * @param assetVocabularyGroupRelId the primary key of the asset vocabulary group rel
	 * @return the asset vocabulary group rel
	 * @throws NoSuchVocabularyGroupRelException if a asset vocabulary group rel with the primary key could not be found
	 */
	public static AssetVocabularyGroupRel findByPrimaryKey(
			long assetVocabularyGroupRelId)
		throws com.liferay.asset.kernel.exception.
			NoSuchVocabularyGroupRelException {

		return getPersistence().findByPrimaryKey(assetVocabularyGroupRelId);
	}

	/**
	 * Returns the asset vocabulary group rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param assetVocabularyGroupRelId the primary key of the asset vocabulary group rel
	 * @return the asset vocabulary group rel, or <code>null</code> if a asset vocabulary group rel with the primary key could not be found
	 */
	public static AssetVocabularyGroupRel fetchByPrimaryKey(
		long assetVocabularyGroupRelId) {

		return getPersistence().fetchByPrimaryKey(assetVocabularyGroupRelId);
	}

	/**
	 * Returns all the asset vocabulary group rels.
	 *
	 * @return the asset vocabulary group rels
	 */
	public static List<AssetVocabularyGroupRel> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the asset vocabulary group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset vocabulary group rels
	 * @param end the upper bound of the range of asset vocabulary group rels (not inclusive)
	 * @return the range of asset vocabulary group rels
	 */
	public static List<AssetVocabularyGroupRel> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the asset vocabulary group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset vocabulary group rels
	 * @param end the upper bound of the range of asset vocabulary group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset vocabulary group rels
	 */
	public static List<AssetVocabularyGroupRel> findAll(
		int start, int end,
		OrderByComparator<AssetVocabularyGroupRel> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the asset vocabulary group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AssetVocabularyGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset vocabulary group rels
	 * @param end the upper bound of the range of asset vocabulary group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of asset vocabulary group rels
	 */
	public static List<AssetVocabularyGroupRel> findAll(
		int start, int end,
		OrderByComparator<AssetVocabularyGroupRel> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the asset vocabulary group rels from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of asset vocabulary group rels.
	 *
	 * @return the number of asset vocabulary group rels
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AssetVocabularyGroupRelPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		AssetVocabularyGroupRelPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile AssetVocabularyGroupRelPersistence _persistence;

}