/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.service.persistence;

import com.liferay.commerce.tax.model.CommerceTaxCategoryMapping;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the commerce tax category mapping service. This utility wraps <code>com.liferay.commerce.tax.service.persistence.impl.CommerceTaxCategoryMappingPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceTaxCategoryMappingPersistence
 * @generated
 */
public class CommerceTaxCategoryMappingUtil {

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
		CommerceTaxCategoryMapping commerceTaxCategoryMapping) {

		getPersistence().clearCache(commerceTaxCategoryMapping);
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
	public static Map<Serializable, CommerceTaxCategoryMapping>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceTaxCategoryMapping> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceTaxCategoryMapping> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceTaxCategoryMapping> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceTaxCategoryMapping> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceTaxCategoryMapping update(
		CommerceTaxCategoryMapping commerceTaxCategoryMapping) {

		return getPersistence().update(commerceTaxCategoryMapping);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceTaxCategoryMapping update(
		CommerceTaxCategoryMapping commerceTaxCategoryMapping,
		ServiceContext serviceContext) {

		return getPersistence().update(
			commerceTaxCategoryMapping, serviceContext);
	}

	/**
	 * Returns all the commerce tax category mappings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce tax category mappings
	 */
	public static List<CommerceTaxCategoryMapping> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the commerce tax category mappings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @return the range of matching commerce tax category mappings
	 */
	public static List<CommerceTaxCategoryMapping> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce tax category mappings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tax category mappings
	 */
	public static List<CommerceTaxCategoryMapping> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceTaxCategoryMapping> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce tax category mappings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tax category mappings
	 */
	public static List<CommerceTaxCategoryMapping> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceTaxCategoryMapping> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce tax category mapping in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a matching commerce tax category mapping could not be found
	 */
	public static CommerceTaxCategoryMapping findByUuid_First(
			String uuid,
			OrderByComparator<CommerceTaxCategoryMapping> orderByComparator)
		throws com.liferay.commerce.tax.exception.
			NoSuchTaxCategoryMappingException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first commerce tax category mapping in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	public static CommerceTaxCategoryMapping fetchByUuid_First(
		String uuid,
		OrderByComparator<CommerceTaxCategoryMapping> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last commerce tax category mapping in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a matching commerce tax category mapping could not be found
	 */
	public static CommerceTaxCategoryMapping findByUuid_Last(
			String uuid,
			OrderByComparator<CommerceTaxCategoryMapping> orderByComparator)
		throws com.liferay.commerce.tax.exception.
			NoSuchTaxCategoryMappingException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last commerce tax category mapping in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	public static CommerceTaxCategoryMapping fetchByUuid_Last(
		String uuid,
		OrderByComparator<CommerceTaxCategoryMapping> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Removes all the commerce tax category mappings where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of commerce tax category mappings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce tax category mappings
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the commerce tax category mapping where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchTaxCategoryMappingException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a matching commerce tax category mapping could not be found
	 */
	public static CommerceTaxCategoryMapping findByUUID_G(
			String uuid, long groupId)
		throws com.liferay.commerce.tax.exception.
			NoSuchTaxCategoryMappingException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the commerce tax category mapping where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	public static CommerceTaxCategoryMapping fetchByUUID_G(
		String uuid, long groupId) {

		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the commerce tax category mapping where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	public static CommerceTaxCategoryMapping fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the commerce tax category mapping where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce tax category mapping that was removed
	 */
	public static CommerceTaxCategoryMapping removeByUUID_G(
			String uuid, long groupId)
		throws com.liferay.commerce.tax.exception.
			NoSuchTaxCategoryMappingException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of commerce tax category mappings where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce tax category mappings
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the commerce tax category mappings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce tax category mappings
	 */
	public static List<CommerceTaxCategoryMapping> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the commerce tax category mappings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @return the range of matching commerce tax category mappings
	 */
	public static List<CommerceTaxCategoryMapping> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce tax category mappings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tax category mappings
	 */
	public static List<CommerceTaxCategoryMapping> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceTaxCategoryMapping> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce tax category mappings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tax category mappings
	 */
	public static List<CommerceTaxCategoryMapping> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceTaxCategoryMapping> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce tax category mapping in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a matching commerce tax category mapping could not be found
	 */
	public static CommerceTaxCategoryMapping findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommerceTaxCategoryMapping> orderByComparator)
		throws com.liferay.commerce.tax.exception.
			NoSuchTaxCategoryMappingException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first commerce tax category mapping in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	public static CommerceTaxCategoryMapping fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommerceTaxCategoryMapping> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce tax category mapping in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a matching commerce tax category mapping could not be found
	 */
	public static CommerceTaxCategoryMapping findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommerceTaxCategoryMapping> orderByComparator)
		throws com.liferay.commerce.tax.exception.
			NoSuchTaxCategoryMappingException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last commerce tax category mapping in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	public static CommerceTaxCategoryMapping fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommerceTaxCategoryMapping> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the commerce tax category mappings where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of commerce tax category mappings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce tax category mappings
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns all the commerce tax category mappings where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @return the matching commerce tax category mappings
	 */
	public static List<CommerceTaxCategoryMapping> findByCommerceTaxMethodId(
		long commerceTaxMethodId) {

		return getPersistence().findByCommerceTaxMethodId(commerceTaxMethodId);
	}

	/**
	 * Returns a range of all the commerce tax category mappings where commerceTaxMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @return the range of matching commerce tax category mappings
	 */
	public static List<CommerceTaxCategoryMapping> findByCommerceTaxMethodId(
		long commerceTaxMethodId, int start, int end) {

		return getPersistence().findByCommerceTaxMethodId(
			commerceTaxMethodId, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce tax category mappings where commerceTaxMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tax category mappings
	 */
	public static List<CommerceTaxCategoryMapping> findByCommerceTaxMethodId(
		long commerceTaxMethodId, int start, int end,
		OrderByComparator<CommerceTaxCategoryMapping> orderByComparator) {

		return getPersistence().findByCommerceTaxMethodId(
			commerceTaxMethodId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce tax category mappings where commerceTaxMethodId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tax category mappings
	 */
	public static List<CommerceTaxCategoryMapping> findByCommerceTaxMethodId(
		long commerceTaxMethodId, int start, int end,
		OrderByComparator<CommerceTaxCategoryMapping> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByCommerceTaxMethodId(
			commerceTaxMethodId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce tax category mapping in the ordered set where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a matching commerce tax category mapping could not be found
	 */
	public static CommerceTaxCategoryMapping findByCommerceTaxMethodId_First(
			long commerceTaxMethodId,
			OrderByComparator<CommerceTaxCategoryMapping> orderByComparator)
		throws com.liferay.commerce.tax.exception.
			NoSuchTaxCategoryMappingException {

		return getPersistence().findByCommerceTaxMethodId_First(
			commerceTaxMethodId, orderByComparator);
	}

	/**
	 * Returns the first commerce tax category mapping in the ordered set where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	public static CommerceTaxCategoryMapping fetchByCommerceTaxMethodId_First(
		long commerceTaxMethodId,
		OrderByComparator<CommerceTaxCategoryMapping> orderByComparator) {

		return getPersistence().fetchByCommerceTaxMethodId_First(
			commerceTaxMethodId, orderByComparator);
	}

	/**
	 * Returns the last commerce tax category mapping in the ordered set where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a matching commerce tax category mapping could not be found
	 */
	public static CommerceTaxCategoryMapping findByCommerceTaxMethodId_Last(
			long commerceTaxMethodId,
			OrderByComparator<CommerceTaxCategoryMapping> orderByComparator)
		throws com.liferay.commerce.tax.exception.
			NoSuchTaxCategoryMappingException {

		return getPersistence().findByCommerceTaxMethodId_Last(
			commerceTaxMethodId, orderByComparator);
	}

	/**
	 * Returns the last commerce tax category mapping in the ordered set where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	public static CommerceTaxCategoryMapping fetchByCommerceTaxMethodId_Last(
		long commerceTaxMethodId,
		OrderByComparator<CommerceTaxCategoryMapping> orderByComparator) {

		return getPersistence().fetchByCommerceTaxMethodId_Last(
			commerceTaxMethodId, orderByComparator);
	}

	/**
	 * Removes all the commerce tax category mappings where commerceTaxMethodId = &#63; from the database.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 */
	public static void removeByCommerceTaxMethodId(long commerceTaxMethodId) {
		getPersistence().removeByCommerceTaxMethodId(commerceTaxMethodId);
	}

	/**
	 * Returns the number of commerce tax category mappings where commerceTaxMethodId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @return the number of matching commerce tax category mappings
	 */
	public static int countByCommerceTaxMethodId(long commerceTaxMethodId) {
		return getPersistence().countByCommerceTaxMethodId(commerceTaxMethodId);
	}

	/**
	 * Returns the commerce tax category mapping where commerceTaxMethodId = &#63; and CPTaxCategoryId = &#63; or throws a <code>NoSuchTaxCategoryMappingException</code> if it could not be found.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param CPTaxCategoryId the cp tax category ID
	 * @return the matching commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a matching commerce tax category mapping could not be found
	 */
	public static CommerceTaxCategoryMapping findByC_C(
			long commerceTaxMethodId, long CPTaxCategoryId)
		throws com.liferay.commerce.tax.exception.
			NoSuchTaxCategoryMappingException {

		return getPersistence().findByC_C(commerceTaxMethodId, CPTaxCategoryId);
	}

	/**
	 * Returns the commerce tax category mapping where commerceTaxMethodId = &#63; and CPTaxCategoryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param CPTaxCategoryId the cp tax category ID
	 * @return the matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	public static CommerceTaxCategoryMapping fetchByC_C(
		long commerceTaxMethodId, long CPTaxCategoryId) {

		return getPersistence().fetchByC_C(
			commerceTaxMethodId, CPTaxCategoryId);
	}

	/**
	 * Returns the commerce tax category mapping where commerceTaxMethodId = &#63; and CPTaxCategoryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	public static CommerceTaxCategoryMapping fetchByC_C(
		long commerceTaxMethodId, long CPTaxCategoryId,
		boolean useFinderCache) {

		return getPersistence().fetchByC_C(
			commerceTaxMethodId, CPTaxCategoryId, useFinderCache);
	}

	/**
	 * Removes the commerce tax category mapping where commerceTaxMethodId = &#63; and CPTaxCategoryId = &#63; from the database.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param CPTaxCategoryId the cp tax category ID
	 * @return the commerce tax category mapping that was removed
	 */
	public static CommerceTaxCategoryMapping removeByC_C(
			long commerceTaxMethodId, long CPTaxCategoryId)
		throws com.liferay.commerce.tax.exception.
			NoSuchTaxCategoryMappingException {

		return getPersistence().removeByC_C(
			commerceTaxMethodId, CPTaxCategoryId);
	}

	/**
	 * Returns the number of commerce tax category mappings where commerceTaxMethodId = &#63; and CPTaxCategoryId = &#63;.
	 *
	 * @param commerceTaxMethodId the commerce tax method ID
	 * @param CPTaxCategoryId the cp tax category ID
	 * @return the number of matching commerce tax category mappings
	 */
	public static int countByC_C(
		long commerceTaxMethodId, long CPTaxCategoryId) {

		return getPersistence().countByC_C(
			commerceTaxMethodId, CPTaxCategoryId);
	}

	/**
	 * Returns the commerce tax category mapping where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchTaxCategoryMappingException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a matching commerce tax category mapping could not be found
	 */
	public static CommerceTaxCategoryMapping findByERC_C(
			String externalReferenceCode, long companyId)
		throws com.liferay.commerce.tax.exception.
			NoSuchTaxCategoryMappingException {

		return getPersistence().findByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the commerce tax category mapping where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	public static CommerceTaxCategoryMapping fetchByERC_C(
		String externalReferenceCode, long companyId) {

		return getPersistence().fetchByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the commerce tax category mapping where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce tax category mapping, or <code>null</code> if a matching commerce tax category mapping could not be found
	 */
	public static CommerceTaxCategoryMapping fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

		return getPersistence().fetchByERC_C(
			externalReferenceCode, companyId, useFinderCache);
	}

	/**
	 * Removes the commerce tax category mapping where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the commerce tax category mapping that was removed
	 */
	public static CommerceTaxCategoryMapping removeByERC_C(
			String externalReferenceCode, long companyId)
		throws com.liferay.commerce.tax.exception.
			NoSuchTaxCategoryMappingException {

		return getPersistence().removeByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Returns the number of commerce tax category mappings where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching commerce tax category mappings
	 */
	public static int countByERC_C(
		String externalReferenceCode, long companyId) {

		return getPersistence().countByERC_C(externalReferenceCode, companyId);
	}

	/**
	 * Caches the commerce tax category mapping in the entity cache if it is enabled.
	 *
	 * @param commerceTaxCategoryMapping the commerce tax category mapping
	 */
	public static void cacheResult(
		CommerceTaxCategoryMapping commerceTaxCategoryMapping) {

		getPersistence().cacheResult(commerceTaxCategoryMapping);
	}

	/**
	 * Caches the commerce tax category mappings in the entity cache if it is enabled.
	 *
	 * @param commerceTaxCategoryMappings the commerce tax category mappings
	 */
	public static void cacheResult(
		List<CommerceTaxCategoryMapping> commerceTaxCategoryMappings) {

		getPersistence().cacheResult(commerceTaxCategoryMappings);
	}

	/**
	 * Creates a new commerce tax category mapping with the primary key. Does not add the commerce tax category mapping to the database.
	 *
	 * @param commerceTaxCategoryMappingId the primary key for the new commerce tax category mapping
	 * @return the new commerce tax category mapping
	 */
	public static CommerceTaxCategoryMapping create(
		long commerceTaxCategoryMappingId) {

		return getPersistence().create(commerceTaxCategoryMappingId);
	}

	/**
	 * Removes the commerce tax category mapping with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceTaxCategoryMappingId the primary key of the commerce tax category mapping
	 * @return the commerce tax category mapping that was removed
	 * @throws NoSuchTaxCategoryMappingException if a commerce tax category mapping with the primary key could not be found
	 */
	public static CommerceTaxCategoryMapping remove(
			long commerceTaxCategoryMappingId)
		throws com.liferay.commerce.tax.exception.
			NoSuchTaxCategoryMappingException {

		return getPersistence().remove(commerceTaxCategoryMappingId);
	}

	public static CommerceTaxCategoryMapping updateImpl(
		CommerceTaxCategoryMapping commerceTaxCategoryMapping) {

		return getPersistence().updateImpl(commerceTaxCategoryMapping);
	}

	/**
	 * Returns the commerce tax category mapping with the primary key or throws a <code>NoSuchTaxCategoryMappingException</code> if it could not be found.
	 *
	 * @param commerceTaxCategoryMappingId the primary key of the commerce tax category mapping
	 * @return the commerce tax category mapping
	 * @throws NoSuchTaxCategoryMappingException if a commerce tax category mapping with the primary key could not be found
	 */
	public static CommerceTaxCategoryMapping findByPrimaryKey(
			long commerceTaxCategoryMappingId)
		throws com.liferay.commerce.tax.exception.
			NoSuchTaxCategoryMappingException {

		return getPersistence().findByPrimaryKey(commerceTaxCategoryMappingId);
	}

	/**
	 * Returns the commerce tax category mapping with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceTaxCategoryMappingId the primary key of the commerce tax category mapping
	 * @return the commerce tax category mapping, or <code>null</code> if a commerce tax category mapping with the primary key could not be found
	 */
	public static CommerceTaxCategoryMapping fetchByPrimaryKey(
		long commerceTaxCategoryMappingId) {

		return getPersistence().fetchByPrimaryKey(commerceTaxCategoryMappingId);
	}

	/**
	 * Returns all the commerce tax category mappings.
	 *
	 * @return the commerce tax category mappings
	 */
	public static List<CommerceTaxCategoryMapping> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce tax category mappings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @return the range of commerce tax category mappings
	 */
	public static List<CommerceTaxCategoryMapping> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce tax category mappings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce tax category mappings
	 */
	public static List<CommerceTaxCategoryMapping> findAll(
		int start, int end,
		OrderByComparator<CommerceTaxCategoryMapping> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce tax category mappings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTaxCategoryMappingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tax category mappings
	 * @param end the upper bound of the range of commerce tax category mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce tax category mappings
	 */
	public static List<CommerceTaxCategoryMapping> findAll(
		int start, int end,
		OrderByComparator<CommerceTaxCategoryMapping> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce tax category mappings from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce tax category mappings.
	 *
	 * @return the number of commerce tax category mappings
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceTaxCategoryMappingPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		CommerceTaxCategoryMappingPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile CommerceTaxCategoryMappingPersistence _persistence;

}
// LIFERAY-SERVICE-BUILDER-HASH:878716744