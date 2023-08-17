/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.service.persistence;

import com.liferay.commerce.inventory.model.CommerceInventoryAudit;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the commerce inventory audit service. This utility wraps <code>com.liferay.commerce.inventory.service.persistence.impl.CommerceInventoryAuditPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryAuditPersistence
 * @generated
 */
public class CommerceInventoryAuditUtil {

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
		CommerceInventoryAudit commerceInventoryAudit) {

		getPersistence().clearCache(commerceInventoryAudit);
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
	public static Map<Serializable, CommerceInventoryAudit> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceInventoryAudit> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceInventoryAudit> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceInventoryAudit> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceInventoryAudit> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceInventoryAudit update(
		CommerceInventoryAudit commerceInventoryAudit) {

		return getPersistence().update(commerceInventoryAudit);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceInventoryAudit update(
		CommerceInventoryAudit commerceInventoryAudit,
		ServiceContext serviceContext) {

		return getPersistence().update(commerceInventoryAudit, serviceContext);
	}

	/**
	 * Returns all the commerce inventory audits where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @return the matching commerce inventory audits
	 */
	public static List<CommerceInventoryAudit> findByLtCreateDate(
		Date createDate) {

		return getPersistence().findByLtCreateDate(createDate);
	}

	/**
	 * Returns a range of all the commerce inventory audits where createDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param createDate the create date
	 * @param start the lower bound of the range of commerce inventory audits
	 * @param end the upper bound of the range of commerce inventory audits (not inclusive)
	 * @return the range of matching commerce inventory audits
	 */
	public static List<CommerceInventoryAudit> findByLtCreateDate(
		Date createDate, int start, int end) {

		return getPersistence().findByLtCreateDate(createDate, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce inventory audits where createDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param createDate the create date
	 * @param start the lower bound of the range of commerce inventory audits
	 * @param end the upper bound of the range of commerce inventory audits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory audits
	 */
	public static List<CommerceInventoryAudit> findByLtCreateDate(
		Date createDate, int start, int end,
		OrderByComparator<CommerceInventoryAudit> orderByComparator) {

		return getPersistence().findByLtCreateDate(
			createDate, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce inventory audits where createDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param createDate the create date
	 * @param start the lower bound of the range of commerce inventory audits
	 * @param end the upper bound of the range of commerce inventory audits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory audits
	 */
	public static List<CommerceInventoryAudit> findByLtCreateDate(
		Date createDate, int start, int end,
		OrderByComparator<CommerceInventoryAudit> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByLtCreateDate(
			createDate, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first commerce inventory audit in the ordered set where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory audit
	 * @throws NoSuchInventoryAuditException if a matching commerce inventory audit could not be found
	 */
	public static CommerceInventoryAudit findByLtCreateDate_First(
			Date createDate,
			OrderByComparator<CommerceInventoryAudit> orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryAuditException {

		return getPersistence().findByLtCreateDate_First(
			createDate, orderByComparator);
	}

	/**
	 * Returns the first commerce inventory audit in the ordered set where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory audit, or <code>null</code> if a matching commerce inventory audit could not be found
	 */
	public static CommerceInventoryAudit fetchByLtCreateDate_First(
		Date createDate,
		OrderByComparator<CommerceInventoryAudit> orderByComparator) {

		return getPersistence().fetchByLtCreateDate_First(
			createDate, orderByComparator);
	}

	/**
	 * Returns the last commerce inventory audit in the ordered set where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory audit
	 * @throws NoSuchInventoryAuditException if a matching commerce inventory audit could not be found
	 */
	public static CommerceInventoryAudit findByLtCreateDate_Last(
			Date createDate,
			OrderByComparator<CommerceInventoryAudit> orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryAuditException {

		return getPersistence().findByLtCreateDate_Last(
			createDate, orderByComparator);
	}

	/**
	 * Returns the last commerce inventory audit in the ordered set where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory audit, or <code>null</code> if a matching commerce inventory audit could not be found
	 */
	public static CommerceInventoryAudit fetchByLtCreateDate_Last(
		Date createDate,
		OrderByComparator<CommerceInventoryAudit> orderByComparator) {

		return getPersistence().fetchByLtCreateDate_Last(
			createDate, orderByComparator);
	}

	/**
	 * Returns the commerce inventory audits before and after the current commerce inventory audit in the ordered set where createDate &lt; &#63;.
	 *
	 * @param commerceInventoryAuditId the primary key of the current commerce inventory audit
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory audit
	 * @throws NoSuchInventoryAuditException if a commerce inventory audit with the primary key could not be found
	 */
	public static CommerceInventoryAudit[] findByLtCreateDate_PrevAndNext(
			long commerceInventoryAuditId, Date createDate,
			OrderByComparator<CommerceInventoryAudit> orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryAuditException {

		return getPersistence().findByLtCreateDate_PrevAndNext(
			commerceInventoryAuditId, createDate, orderByComparator);
	}

	/**
	 * Removes all the commerce inventory audits where createDate &lt; &#63; from the database.
	 *
	 * @param createDate the create date
	 */
	public static void removeByLtCreateDate(Date createDate) {
		getPersistence().removeByLtCreateDate(createDate);
	}

	/**
	 * Returns the number of commerce inventory audits where createDate &lt; &#63;.
	 *
	 * @param createDate the create date
	 * @return the number of matching commerce inventory audits
	 */
	public static int countByLtCreateDate(Date createDate) {
		return getPersistence().countByLtCreateDate(createDate);
	}

	/**
	 * Returns all the commerce inventory audits where companyId = &#63; and sku = &#63; and unitOfMeasureKey = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param unitOfMeasureKey the unit of measure key
	 * @return the matching commerce inventory audits
	 */
	public static List<CommerceInventoryAudit> findByC_S_U(
		long companyId, String sku, String unitOfMeasureKey) {

		return getPersistence().findByC_S_U(companyId, sku, unitOfMeasureKey);
	}

	/**
	 * Returns a range of all the commerce inventory audits where companyId = &#63; and sku = &#63; and unitOfMeasureKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param unitOfMeasureKey the unit of measure key
	 * @param start the lower bound of the range of commerce inventory audits
	 * @param end the upper bound of the range of commerce inventory audits (not inclusive)
	 * @return the range of matching commerce inventory audits
	 */
	public static List<CommerceInventoryAudit> findByC_S_U(
		long companyId, String sku, String unitOfMeasureKey, int start,
		int end) {

		return getPersistence().findByC_S_U(
			companyId, sku, unitOfMeasureKey, start, end);
	}

	/**
	 * Returns an ordered range of all the commerce inventory audits where companyId = &#63; and sku = &#63; and unitOfMeasureKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param unitOfMeasureKey the unit of measure key
	 * @param start the lower bound of the range of commerce inventory audits
	 * @param end the upper bound of the range of commerce inventory audits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce inventory audits
	 */
	public static List<CommerceInventoryAudit> findByC_S_U(
		long companyId, String sku, String unitOfMeasureKey, int start, int end,
		OrderByComparator<CommerceInventoryAudit> orderByComparator) {

		return getPersistence().findByC_S_U(
			companyId, sku, unitOfMeasureKey, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce inventory audits where companyId = &#63; and sku = &#63; and unitOfMeasureKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param unitOfMeasureKey the unit of measure key
	 * @param start the lower bound of the range of commerce inventory audits
	 * @param end the upper bound of the range of commerce inventory audits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce inventory audits
	 */
	public static List<CommerceInventoryAudit> findByC_S_U(
		long companyId, String sku, String unitOfMeasureKey, int start, int end,
		OrderByComparator<CommerceInventoryAudit> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_S_U(
			companyId, sku, unitOfMeasureKey, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first commerce inventory audit in the ordered set where companyId = &#63; and sku = &#63; and unitOfMeasureKey = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param unitOfMeasureKey the unit of measure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory audit
	 * @throws NoSuchInventoryAuditException if a matching commerce inventory audit could not be found
	 */
	public static CommerceInventoryAudit findByC_S_U_First(
			long companyId, String sku, String unitOfMeasureKey,
			OrderByComparator<CommerceInventoryAudit> orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryAuditException {

		return getPersistence().findByC_S_U_First(
			companyId, sku, unitOfMeasureKey, orderByComparator);
	}

	/**
	 * Returns the first commerce inventory audit in the ordered set where companyId = &#63; and sku = &#63; and unitOfMeasureKey = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param unitOfMeasureKey the unit of measure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce inventory audit, or <code>null</code> if a matching commerce inventory audit could not be found
	 */
	public static CommerceInventoryAudit fetchByC_S_U_First(
		long companyId, String sku, String unitOfMeasureKey,
		OrderByComparator<CommerceInventoryAudit> orderByComparator) {

		return getPersistence().fetchByC_S_U_First(
			companyId, sku, unitOfMeasureKey, orderByComparator);
	}

	/**
	 * Returns the last commerce inventory audit in the ordered set where companyId = &#63; and sku = &#63; and unitOfMeasureKey = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param unitOfMeasureKey the unit of measure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory audit
	 * @throws NoSuchInventoryAuditException if a matching commerce inventory audit could not be found
	 */
	public static CommerceInventoryAudit findByC_S_U_Last(
			long companyId, String sku, String unitOfMeasureKey,
			OrderByComparator<CommerceInventoryAudit> orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryAuditException {

		return getPersistence().findByC_S_U_Last(
			companyId, sku, unitOfMeasureKey, orderByComparator);
	}

	/**
	 * Returns the last commerce inventory audit in the ordered set where companyId = &#63; and sku = &#63; and unitOfMeasureKey = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param unitOfMeasureKey the unit of measure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce inventory audit, or <code>null</code> if a matching commerce inventory audit could not be found
	 */
	public static CommerceInventoryAudit fetchByC_S_U_Last(
		long companyId, String sku, String unitOfMeasureKey,
		OrderByComparator<CommerceInventoryAudit> orderByComparator) {

		return getPersistence().fetchByC_S_U_Last(
			companyId, sku, unitOfMeasureKey, orderByComparator);
	}

	/**
	 * Returns the commerce inventory audits before and after the current commerce inventory audit in the ordered set where companyId = &#63; and sku = &#63; and unitOfMeasureKey = &#63;.
	 *
	 * @param commerceInventoryAuditId the primary key of the current commerce inventory audit
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param unitOfMeasureKey the unit of measure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce inventory audit
	 * @throws NoSuchInventoryAuditException if a commerce inventory audit with the primary key could not be found
	 */
	public static CommerceInventoryAudit[] findByC_S_U_PrevAndNext(
			long commerceInventoryAuditId, long companyId, String sku,
			String unitOfMeasureKey,
			OrderByComparator<CommerceInventoryAudit> orderByComparator)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryAuditException {

		return getPersistence().findByC_S_U_PrevAndNext(
			commerceInventoryAuditId, companyId, sku, unitOfMeasureKey,
			orderByComparator);
	}

	/**
	 * Removes all the commerce inventory audits where companyId = &#63; and sku = &#63; and unitOfMeasureKey = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param unitOfMeasureKey the unit of measure key
	 */
	public static void removeByC_S_U(
		long companyId, String sku, String unitOfMeasureKey) {

		getPersistence().removeByC_S_U(companyId, sku, unitOfMeasureKey);
	}

	/**
	 * Returns the number of commerce inventory audits where companyId = &#63; and sku = &#63; and unitOfMeasureKey = &#63;.
	 *
	 * @param companyId the company ID
	 * @param sku the sku
	 * @param unitOfMeasureKey the unit of measure key
	 * @return the number of matching commerce inventory audits
	 */
	public static int countByC_S_U(
		long companyId, String sku, String unitOfMeasureKey) {

		return getPersistence().countByC_S_U(companyId, sku, unitOfMeasureKey);
	}

	/**
	 * Caches the commerce inventory audit in the entity cache if it is enabled.
	 *
	 * @param commerceInventoryAudit the commerce inventory audit
	 */
	public static void cacheResult(
		CommerceInventoryAudit commerceInventoryAudit) {

		getPersistence().cacheResult(commerceInventoryAudit);
	}

	/**
	 * Caches the commerce inventory audits in the entity cache if it is enabled.
	 *
	 * @param commerceInventoryAudits the commerce inventory audits
	 */
	public static void cacheResult(
		List<CommerceInventoryAudit> commerceInventoryAudits) {

		getPersistence().cacheResult(commerceInventoryAudits);
	}

	/**
	 * Creates a new commerce inventory audit with the primary key. Does not add the commerce inventory audit to the database.
	 *
	 * @param commerceInventoryAuditId the primary key for the new commerce inventory audit
	 * @return the new commerce inventory audit
	 */
	public static CommerceInventoryAudit create(long commerceInventoryAuditId) {
		return getPersistence().create(commerceInventoryAuditId);
	}

	/**
	 * Removes the commerce inventory audit with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceInventoryAuditId the primary key of the commerce inventory audit
	 * @return the commerce inventory audit that was removed
	 * @throws NoSuchInventoryAuditException if a commerce inventory audit with the primary key could not be found
	 */
	public static CommerceInventoryAudit remove(long commerceInventoryAuditId)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryAuditException {

		return getPersistence().remove(commerceInventoryAuditId);
	}

	public static CommerceInventoryAudit updateImpl(
		CommerceInventoryAudit commerceInventoryAudit) {

		return getPersistence().updateImpl(commerceInventoryAudit);
	}

	/**
	 * Returns the commerce inventory audit with the primary key or throws a <code>NoSuchInventoryAuditException</code> if it could not be found.
	 *
	 * @param commerceInventoryAuditId the primary key of the commerce inventory audit
	 * @return the commerce inventory audit
	 * @throws NoSuchInventoryAuditException if a commerce inventory audit with the primary key could not be found
	 */
	public static CommerceInventoryAudit findByPrimaryKey(
			long commerceInventoryAuditId)
		throws com.liferay.commerce.inventory.exception.
			NoSuchInventoryAuditException {

		return getPersistence().findByPrimaryKey(commerceInventoryAuditId);
	}

	/**
	 * Returns the commerce inventory audit with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceInventoryAuditId the primary key of the commerce inventory audit
	 * @return the commerce inventory audit, or <code>null</code> if a commerce inventory audit with the primary key could not be found
	 */
	public static CommerceInventoryAudit fetchByPrimaryKey(
		long commerceInventoryAuditId) {

		return getPersistence().fetchByPrimaryKey(commerceInventoryAuditId);
	}

	/**
	 * Returns all the commerce inventory audits.
	 *
	 * @return the commerce inventory audits
	 */
	public static List<CommerceInventoryAudit> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the commerce inventory audits.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory audits
	 * @param end the upper bound of the range of commerce inventory audits (not inclusive)
	 * @return the range of commerce inventory audits
	 */
	public static List<CommerceInventoryAudit> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the commerce inventory audits.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory audits
	 * @param end the upper bound of the range of commerce inventory audits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce inventory audits
	 */
	public static List<CommerceInventoryAudit> findAll(
		int start, int end,
		OrderByComparator<CommerceInventoryAudit> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the commerce inventory audits.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceInventoryAuditModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce inventory audits
	 * @param end the upper bound of the range of commerce inventory audits (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce inventory audits
	 */
	public static List<CommerceInventoryAudit> findAll(
		int start, int end,
		OrderByComparator<CommerceInventoryAudit> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the commerce inventory audits from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of commerce inventory audits.
	 *
	 * @return the number of commerce inventory audits
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CommerceInventoryAuditPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		CommerceInventoryAuditPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile CommerceInventoryAuditPersistence _persistence;

}