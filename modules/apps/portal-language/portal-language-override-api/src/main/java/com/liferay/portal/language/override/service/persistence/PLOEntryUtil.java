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

package com.liferay.portal.language.override.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.language.override.model.PLOEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the plo entry service. This utility wraps <code>com.liferay.portal.language.override.service.persistence.impl.PLOEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PLOEntryPersistence
 * @generated
 */
public class PLOEntryUtil {

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
	public static void clearCache(PLOEntry ploEntry) {
		getPersistence().clearCache(ploEntry);
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
	public static Map<Serializable, PLOEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<PLOEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<PLOEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<PLOEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<PLOEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static PLOEntry update(PLOEntry ploEntry) {
		return getPersistence().update(ploEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static PLOEntry update(
		PLOEntry ploEntry, ServiceContext serviceContext) {

		return getPersistence().update(ploEntry, serviceContext);
	}

	/**
	 * Returns all the plo entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching plo entries
	 */
	public static List<PLOEntry> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	 * Returns a range of all the plo entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @return the range of matching plo entries
	 */
	public static List<PLOEntry> findByCompanyId(
		long companyId, int start, int end) {

		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the plo entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching plo entries
	 */
	public static List<PLOEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<PLOEntry> orderByComparator) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the plo entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching plo entries
	 */
	public static List<PLOEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<PLOEntry> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByCompanyId(
			companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first plo entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching plo entry
	 * @throws NoSuchPLOEntryException if a matching plo entry could not be found
	 */
	public static PLOEntry findByCompanyId_First(
			long companyId, OrderByComparator<PLOEntry> orderByComparator)
		throws com.liferay.portal.language.override.exception.
			NoSuchPLOEntryException {

		return getPersistence().findByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the first plo entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching plo entry, or <code>null</code> if a matching plo entry could not be found
	 */
	public static PLOEntry fetchByCompanyId_First(
		long companyId, OrderByComparator<PLOEntry> orderByComparator) {

		return getPersistence().fetchByCompanyId_First(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last plo entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching plo entry
	 * @throws NoSuchPLOEntryException if a matching plo entry could not be found
	 */
	public static PLOEntry findByCompanyId_Last(
			long companyId, OrderByComparator<PLOEntry> orderByComparator)
		throws com.liferay.portal.language.override.exception.
			NoSuchPLOEntryException {

		return getPersistence().findByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the last plo entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching plo entry, or <code>null</code> if a matching plo entry could not be found
	 */
	public static PLOEntry fetchByCompanyId_Last(
		long companyId, OrderByComparator<PLOEntry> orderByComparator) {

		return getPersistence().fetchByCompanyId_Last(
			companyId, orderByComparator);
	}

	/**
	 * Returns the plo entries before and after the current plo entry in the ordered set where companyId = &#63;.
	 *
	 * @param ploEntryId the primary key of the current plo entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next plo entry
	 * @throws NoSuchPLOEntryException if a plo entry with the primary key could not be found
	 */
	public static PLOEntry[] findByCompanyId_PrevAndNext(
			long ploEntryId, long companyId,
			OrderByComparator<PLOEntry> orderByComparator)
		throws com.liferay.portal.language.override.exception.
			NoSuchPLOEntryException {

		return getPersistence().findByCompanyId_PrevAndNext(
			ploEntryId, companyId, orderByComparator);
	}

	/**
	 * Removes all the plo entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	 * Returns the number of plo entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching plo entries
	 */
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	 * Returns all the plo entries where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the matching plo entries
	 */
	public static List<PLOEntry> findByC_K(long companyId, String key) {
		return getPersistence().findByC_K(companyId, key);
	}

	/**
	 * Returns a range of all the plo entries where companyId = &#63; and key = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @return the range of matching plo entries
	 */
	public static List<PLOEntry> findByC_K(
		long companyId, String key, int start, int end) {

		return getPersistence().findByC_K(companyId, key, start, end);
	}

	/**
	 * Returns an ordered range of all the plo entries where companyId = &#63; and key = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching plo entries
	 */
	public static List<PLOEntry> findByC_K(
		long companyId, String key, int start, int end,
		OrderByComparator<PLOEntry> orderByComparator) {

		return getPersistence().findByC_K(
			companyId, key, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the plo entries where companyId = &#63; and key = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching plo entries
	 */
	public static List<PLOEntry> findByC_K(
		long companyId, String key, int start, int end,
		OrderByComparator<PLOEntry> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByC_K(
			companyId, key, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first plo entry in the ordered set where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching plo entry
	 * @throws NoSuchPLOEntryException if a matching plo entry could not be found
	 */
	public static PLOEntry findByC_K_First(
			long companyId, String key,
			OrderByComparator<PLOEntry> orderByComparator)
		throws com.liferay.portal.language.override.exception.
			NoSuchPLOEntryException {

		return getPersistence().findByC_K_First(
			companyId, key, orderByComparator);
	}

	/**
	 * Returns the first plo entry in the ordered set where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching plo entry, or <code>null</code> if a matching plo entry could not be found
	 */
	public static PLOEntry fetchByC_K_First(
		long companyId, String key,
		OrderByComparator<PLOEntry> orderByComparator) {

		return getPersistence().fetchByC_K_First(
			companyId, key, orderByComparator);
	}

	/**
	 * Returns the last plo entry in the ordered set where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching plo entry
	 * @throws NoSuchPLOEntryException if a matching plo entry could not be found
	 */
	public static PLOEntry findByC_K_Last(
			long companyId, String key,
			OrderByComparator<PLOEntry> orderByComparator)
		throws com.liferay.portal.language.override.exception.
			NoSuchPLOEntryException {

		return getPersistence().findByC_K_Last(
			companyId, key, orderByComparator);
	}

	/**
	 * Returns the last plo entry in the ordered set where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching plo entry, or <code>null</code> if a matching plo entry could not be found
	 */
	public static PLOEntry fetchByC_K_Last(
		long companyId, String key,
		OrderByComparator<PLOEntry> orderByComparator) {

		return getPersistence().fetchByC_K_Last(
			companyId, key, orderByComparator);
	}

	/**
	 * Returns the plo entries before and after the current plo entry in the ordered set where companyId = &#63; and key = &#63;.
	 *
	 * @param ploEntryId the primary key of the current plo entry
	 * @param companyId the company ID
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next plo entry
	 * @throws NoSuchPLOEntryException if a plo entry with the primary key could not be found
	 */
	public static PLOEntry[] findByC_K_PrevAndNext(
			long ploEntryId, long companyId, String key,
			OrderByComparator<PLOEntry> orderByComparator)
		throws com.liferay.portal.language.override.exception.
			NoSuchPLOEntryException {

		return getPersistence().findByC_K_PrevAndNext(
			ploEntryId, companyId, key, orderByComparator);
	}

	/**
	 * Removes all the plo entries where companyId = &#63; and key = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 */
	public static void removeByC_K(long companyId, String key) {
		getPersistence().removeByC_K(companyId, key);
	}

	/**
	 * Returns the number of plo entries where companyId = &#63; and key = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @return the number of matching plo entries
	 */
	public static int countByC_K(long companyId, String key) {
		return getPersistence().countByC_K(companyId, key);
	}

	/**
	 * Returns all the plo entries where companyId = &#63; and languageId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @return the matching plo entries
	 */
	public static List<PLOEntry> findByC_L(long companyId, String languageId) {
		return getPersistence().findByC_L(companyId, languageId);
	}

	/**
	 * Returns a range of all the plo entries where companyId = &#63; and languageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @return the range of matching plo entries
	 */
	public static List<PLOEntry> findByC_L(
		long companyId, String languageId, int start, int end) {

		return getPersistence().findByC_L(companyId, languageId, start, end);
	}

	/**
	 * Returns an ordered range of all the plo entries where companyId = &#63; and languageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching plo entries
	 */
	public static List<PLOEntry> findByC_L(
		long companyId, String languageId, int start, int end,
		OrderByComparator<PLOEntry> orderByComparator) {

		return getPersistence().findByC_L(
			companyId, languageId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the plo entries where companyId = &#63; and languageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching plo entries
	 */
	public static List<PLOEntry> findByC_L(
		long companyId, String languageId, int start, int end,
		OrderByComparator<PLOEntry> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByC_L(
			companyId, languageId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first plo entry in the ordered set where companyId = &#63; and languageId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching plo entry
	 * @throws NoSuchPLOEntryException if a matching plo entry could not be found
	 */
	public static PLOEntry findByC_L_First(
			long companyId, String languageId,
			OrderByComparator<PLOEntry> orderByComparator)
		throws com.liferay.portal.language.override.exception.
			NoSuchPLOEntryException {

		return getPersistence().findByC_L_First(
			companyId, languageId, orderByComparator);
	}

	/**
	 * Returns the first plo entry in the ordered set where companyId = &#63; and languageId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching plo entry, or <code>null</code> if a matching plo entry could not be found
	 */
	public static PLOEntry fetchByC_L_First(
		long companyId, String languageId,
		OrderByComparator<PLOEntry> orderByComparator) {

		return getPersistence().fetchByC_L_First(
			companyId, languageId, orderByComparator);
	}

	/**
	 * Returns the last plo entry in the ordered set where companyId = &#63; and languageId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching plo entry
	 * @throws NoSuchPLOEntryException if a matching plo entry could not be found
	 */
	public static PLOEntry findByC_L_Last(
			long companyId, String languageId,
			OrderByComparator<PLOEntry> orderByComparator)
		throws com.liferay.portal.language.override.exception.
			NoSuchPLOEntryException {

		return getPersistence().findByC_L_Last(
			companyId, languageId, orderByComparator);
	}

	/**
	 * Returns the last plo entry in the ordered set where companyId = &#63; and languageId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching plo entry, or <code>null</code> if a matching plo entry could not be found
	 */
	public static PLOEntry fetchByC_L_Last(
		long companyId, String languageId,
		OrderByComparator<PLOEntry> orderByComparator) {

		return getPersistence().fetchByC_L_Last(
			companyId, languageId, orderByComparator);
	}

	/**
	 * Returns the plo entries before and after the current plo entry in the ordered set where companyId = &#63; and languageId = &#63;.
	 *
	 * @param ploEntryId the primary key of the current plo entry
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next plo entry
	 * @throws NoSuchPLOEntryException if a plo entry with the primary key could not be found
	 */
	public static PLOEntry[] findByC_L_PrevAndNext(
			long ploEntryId, long companyId, String languageId,
			OrderByComparator<PLOEntry> orderByComparator)
		throws com.liferay.portal.language.override.exception.
			NoSuchPLOEntryException {

		return getPersistence().findByC_L_PrevAndNext(
			ploEntryId, companyId, languageId, orderByComparator);
	}

	/**
	 * Removes all the plo entries where companyId = &#63; and languageId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 */
	public static void removeByC_L(long companyId, String languageId) {
		getPersistence().removeByC_L(companyId, languageId);
	}

	/**
	 * Returns the number of plo entries where companyId = &#63; and languageId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param languageId the language ID
	 * @return the number of matching plo entries
	 */
	public static int countByC_L(long companyId, String languageId) {
		return getPersistence().countByC_L(companyId, languageId);
	}

	/**
	 * Returns the plo entry where companyId = &#63; and key = &#63; and languageId = &#63; or throws a <code>NoSuchPLOEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param languageId the language ID
	 * @return the matching plo entry
	 * @throws NoSuchPLOEntryException if a matching plo entry could not be found
	 */
	public static PLOEntry findByC_K_L(
			long companyId, String key, String languageId)
		throws com.liferay.portal.language.override.exception.
			NoSuchPLOEntryException {

		return getPersistence().findByC_K_L(companyId, key, languageId);
	}

	/**
	 * Returns the plo entry where companyId = &#63; and key = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param languageId the language ID
	 * @return the matching plo entry, or <code>null</code> if a matching plo entry could not be found
	 */
	public static PLOEntry fetchByC_K_L(
		long companyId, String key, String languageId) {

		return getPersistence().fetchByC_K_L(companyId, key, languageId);
	}

	/**
	 * Returns the plo entry where companyId = &#63; and key = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param languageId the language ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching plo entry, or <code>null</code> if a matching plo entry could not be found
	 */
	public static PLOEntry fetchByC_K_L(
		long companyId, String key, String languageId, boolean useFinderCache) {

		return getPersistence().fetchByC_K_L(
			companyId, key, languageId, useFinderCache);
	}

	/**
	 * Removes the plo entry where companyId = &#63; and key = &#63; and languageId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param languageId the language ID
	 * @return the plo entry that was removed
	 */
	public static PLOEntry removeByC_K_L(
			long companyId, String key, String languageId)
		throws com.liferay.portal.language.override.exception.
			NoSuchPLOEntryException {

		return getPersistence().removeByC_K_L(companyId, key, languageId);
	}

	/**
	 * Returns the number of plo entries where companyId = &#63; and key = &#63; and languageId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param key the key
	 * @param languageId the language ID
	 * @return the number of matching plo entries
	 */
	public static int countByC_K_L(
		long companyId, String key, String languageId) {

		return getPersistence().countByC_K_L(companyId, key, languageId);
	}

	/**
	 * Caches the plo entry in the entity cache if it is enabled.
	 *
	 * @param ploEntry the plo entry
	 */
	public static void cacheResult(PLOEntry ploEntry) {
		getPersistence().cacheResult(ploEntry);
	}

	/**
	 * Caches the plo entries in the entity cache if it is enabled.
	 *
	 * @param ploEntries the plo entries
	 */
	public static void cacheResult(List<PLOEntry> ploEntries) {
		getPersistence().cacheResult(ploEntries);
	}

	/**
	 * Creates a new plo entry with the primary key. Does not add the plo entry to the database.
	 *
	 * @param ploEntryId the primary key for the new plo entry
	 * @return the new plo entry
	 */
	public static PLOEntry create(long ploEntryId) {
		return getPersistence().create(ploEntryId);
	}

	/**
	 * Removes the plo entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ploEntryId the primary key of the plo entry
	 * @return the plo entry that was removed
	 * @throws NoSuchPLOEntryException if a plo entry with the primary key could not be found
	 */
	public static PLOEntry remove(long ploEntryId)
		throws com.liferay.portal.language.override.exception.
			NoSuchPLOEntryException {

		return getPersistence().remove(ploEntryId);
	}

	public static PLOEntry updateImpl(PLOEntry ploEntry) {
		return getPersistence().updateImpl(ploEntry);
	}

	/**
	 * Returns the plo entry with the primary key or throws a <code>NoSuchPLOEntryException</code> if it could not be found.
	 *
	 * @param ploEntryId the primary key of the plo entry
	 * @return the plo entry
	 * @throws NoSuchPLOEntryException if a plo entry with the primary key could not be found
	 */
	public static PLOEntry findByPrimaryKey(long ploEntryId)
		throws com.liferay.portal.language.override.exception.
			NoSuchPLOEntryException {

		return getPersistence().findByPrimaryKey(ploEntryId);
	}

	/**
	 * Returns the plo entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ploEntryId the primary key of the plo entry
	 * @return the plo entry, or <code>null</code> if a plo entry with the primary key could not be found
	 */
	public static PLOEntry fetchByPrimaryKey(long ploEntryId) {
		return getPersistence().fetchByPrimaryKey(ploEntryId);
	}

	/**
	 * Returns all the plo entries.
	 *
	 * @return the plo entries
	 */
	public static List<PLOEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the plo entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @return the range of plo entries
	 */
	public static List<PLOEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the plo entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of plo entries
	 */
	public static List<PLOEntry> findAll(
		int start, int end, OrderByComparator<PLOEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the plo entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PLOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of plo entries
	 * @param end the upper bound of the range of plo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of plo entries
	 */
	public static List<PLOEntry> findAll(
		int start, int end, OrderByComparator<PLOEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the plo entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of plo entries.
	 *
	 * @return the number of plo entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static PLOEntryPersistence getPersistence() {
		return _persistence;
	}

	private static volatile PLOEntryPersistence _persistence;

}