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

package com.liferay.analytics.message.storage.service.persistence;

import com.liferay.analytics.message.storage.model.AnalyticsAssociationChange;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the analytics association change service. This utility wraps <code>com.liferay.analytics.message.storage.service.persistence.impl.AnalyticsAssociationChangePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AnalyticsAssociationChangePersistence
 * @generated
 */
public class AnalyticsAssociationChangeUtil {

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
		AnalyticsAssociationChange analyticsAssociationChange) {

		getPersistence().clearCache(analyticsAssociationChange);
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
	public static Map<Serializable, AnalyticsAssociationChange>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AnalyticsAssociationChange> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AnalyticsAssociationChange> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AnalyticsAssociationChange> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AnalyticsAssociationChange> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AnalyticsAssociationChange update(
		AnalyticsAssociationChange analyticsAssociationChange) {

		return getPersistence().update(analyticsAssociationChange);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AnalyticsAssociationChange update(
		AnalyticsAssociationChange analyticsAssociationChange,
		ServiceContext serviceContext) {

		return getPersistence().update(
			analyticsAssociationChange, serviceContext);
	}

	/**
	 * Returns all the analytics association changes where companyId = &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @return the matching analytics association changes
	 */
	public static List<AnalyticsAssociationChange> findByC_A(
		long companyId, String associationClassName) {

		return getPersistence().findByC_A(companyId, associationClassName);
	}

	/**
	 * Returns a range of all the analytics association changes where companyId = &#63; and associationClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationChangeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param start the lower bound of the range of analytics association changes
	 * @param end the upper bound of the range of analytics association changes (not inclusive)
	 * @return the range of matching analytics association changes
	 */
	public static List<AnalyticsAssociationChange> findByC_A(
		long companyId, String associationClassName, int start, int end) {

		return getPersistence().findByC_A(
			companyId, associationClassName, start, end);
	}

	/**
	 * Returns an ordered range of all the analytics association changes where companyId = &#63; and associationClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationChangeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param start the lower bound of the range of analytics association changes
	 * @param end the upper bound of the range of analytics association changes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching analytics association changes
	 */
	public static List<AnalyticsAssociationChange> findByC_A(
		long companyId, String associationClassName, int start, int end,
		OrderByComparator<AnalyticsAssociationChange> orderByComparator) {

		return getPersistence().findByC_A(
			companyId, associationClassName, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the analytics association changes where companyId = &#63; and associationClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationChangeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param start the lower bound of the range of analytics association changes
	 * @param end the upper bound of the range of analytics association changes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching analytics association changes
	 */
	public static List<AnalyticsAssociationChange> findByC_A(
		long companyId, String associationClassName, int start, int end,
		OrderByComparator<AnalyticsAssociationChange> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_A(
			companyId, associationClassName, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first analytics association change in the ordered set where companyId = &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics association change
	 * @throws NoSuchAssociationChangeException if a matching analytics association change could not be found
	 */
	public static AnalyticsAssociationChange findByC_A_First(
			long companyId, String associationClassName,
			OrderByComparator<AnalyticsAssociationChange> orderByComparator)
		throws com.liferay.analytics.message.storage.exception.
			NoSuchAssociationChangeException {

		return getPersistence().findByC_A_First(
			companyId, associationClassName, orderByComparator);
	}

	/**
	 * Returns the first analytics association change in the ordered set where companyId = &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics association change, or <code>null</code> if a matching analytics association change could not be found
	 */
	public static AnalyticsAssociationChange fetchByC_A_First(
		long companyId, String associationClassName,
		OrderByComparator<AnalyticsAssociationChange> orderByComparator) {

		return getPersistence().fetchByC_A_First(
			companyId, associationClassName, orderByComparator);
	}

	/**
	 * Returns the last analytics association change in the ordered set where companyId = &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics association change
	 * @throws NoSuchAssociationChangeException if a matching analytics association change could not be found
	 */
	public static AnalyticsAssociationChange findByC_A_Last(
			long companyId, String associationClassName,
			OrderByComparator<AnalyticsAssociationChange> orderByComparator)
		throws com.liferay.analytics.message.storage.exception.
			NoSuchAssociationChangeException {

		return getPersistence().findByC_A_Last(
			companyId, associationClassName, orderByComparator);
	}

	/**
	 * Returns the last analytics association change in the ordered set where companyId = &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics association change, or <code>null</code> if a matching analytics association change could not be found
	 */
	public static AnalyticsAssociationChange fetchByC_A_Last(
		long companyId, String associationClassName,
		OrderByComparator<AnalyticsAssociationChange> orderByComparator) {

		return getPersistence().fetchByC_A_Last(
			companyId, associationClassName, orderByComparator);
	}

	/**
	 * Returns the analytics association changes before and after the current analytics association change in the ordered set where companyId = &#63; and associationClassName = &#63;.
	 *
	 * @param analyticsAssociationChangeId the primary key of the current analytics association change
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next analytics association change
	 * @throws NoSuchAssociationChangeException if a analytics association change with the primary key could not be found
	 */
	public static AnalyticsAssociationChange[] findByC_A_PrevAndNext(
			long analyticsAssociationChangeId, long companyId,
			String associationClassName,
			OrderByComparator<AnalyticsAssociationChange> orderByComparator)
		throws com.liferay.analytics.message.storage.exception.
			NoSuchAssociationChangeException {

		return getPersistence().findByC_A_PrevAndNext(
			analyticsAssociationChangeId, companyId, associationClassName,
			orderByComparator);
	}

	/**
	 * Removes all the analytics association changes where companyId = &#63; and associationClassName = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 */
	public static void removeByC_A(
		long companyId, String associationClassName) {

		getPersistence().removeByC_A(companyId, associationClassName);
	}

	/**
	 * Returns the number of analytics association changes where companyId = &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @return the number of matching analytics association changes
	 */
	public static int countByC_A(long companyId, String associationClassName) {
		return getPersistence().countByC_A(companyId, associationClassName);
	}

	/**
	 * Returns all the analytics association changes where companyId = &#63; and modifiedDate &gt; &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param associationClassName the association class name
	 * @return the matching analytics association changes
	 */
	public static List<AnalyticsAssociationChange> findByC_GtM_A(
		long companyId, Date modifiedDate, String associationClassName) {

		return getPersistence().findByC_GtM_A(
			companyId, modifiedDate, associationClassName);
	}

	/**
	 * Returns a range of all the analytics association changes where companyId = &#63; and modifiedDate &gt; &#63; and associationClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationChangeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param associationClassName the association class name
	 * @param start the lower bound of the range of analytics association changes
	 * @param end the upper bound of the range of analytics association changes (not inclusive)
	 * @return the range of matching analytics association changes
	 */
	public static List<AnalyticsAssociationChange> findByC_GtM_A(
		long companyId, Date modifiedDate, String associationClassName,
		int start, int end) {

		return getPersistence().findByC_GtM_A(
			companyId, modifiedDate, associationClassName, start, end);
	}

	/**
	 * Returns an ordered range of all the analytics association changes where companyId = &#63; and modifiedDate &gt; &#63; and associationClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationChangeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param associationClassName the association class name
	 * @param start the lower bound of the range of analytics association changes
	 * @param end the upper bound of the range of analytics association changes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching analytics association changes
	 */
	public static List<AnalyticsAssociationChange> findByC_GtM_A(
		long companyId, Date modifiedDate, String associationClassName,
		int start, int end,
		OrderByComparator<AnalyticsAssociationChange> orderByComparator) {

		return getPersistence().findByC_GtM_A(
			companyId, modifiedDate, associationClassName, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the analytics association changes where companyId = &#63; and modifiedDate &gt; &#63; and associationClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationChangeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param associationClassName the association class name
	 * @param start the lower bound of the range of analytics association changes
	 * @param end the upper bound of the range of analytics association changes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching analytics association changes
	 */
	public static List<AnalyticsAssociationChange> findByC_GtM_A(
		long companyId, Date modifiedDate, String associationClassName,
		int start, int end,
		OrderByComparator<AnalyticsAssociationChange> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_GtM_A(
			companyId, modifiedDate, associationClassName, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first analytics association change in the ordered set where companyId = &#63; and modifiedDate &gt; &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param associationClassName the association class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics association change
	 * @throws NoSuchAssociationChangeException if a matching analytics association change could not be found
	 */
	public static AnalyticsAssociationChange findByC_GtM_A_First(
			long companyId, Date modifiedDate, String associationClassName,
			OrderByComparator<AnalyticsAssociationChange> orderByComparator)
		throws com.liferay.analytics.message.storage.exception.
			NoSuchAssociationChangeException {

		return getPersistence().findByC_GtM_A_First(
			companyId, modifiedDate, associationClassName, orderByComparator);
	}

	/**
	 * Returns the first analytics association change in the ordered set where companyId = &#63; and modifiedDate &gt; &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param associationClassName the association class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics association change, or <code>null</code> if a matching analytics association change could not be found
	 */
	public static AnalyticsAssociationChange fetchByC_GtM_A_First(
		long companyId, Date modifiedDate, String associationClassName,
		OrderByComparator<AnalyticsAssociationChange> orderByComparator) {

		return getPersistence().fetchByC_GtM_A_First(
			companyId, modifiedDate, associationClassName, orderByComparator);
	}

	/**
	 * Returns the last analytics association change in the ordered set where companyId = &#63; and modifiedDate &gt; &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param associationClassName the association class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics association change
	 * @throws NoSuchAssociationChangeException if a matching analytics association change could not be found
	 */
	public static AnalyticsAssociationChange findByC_GtM_A_Last(
			long companyId, Date modifiedDate, String associationClassName,
			OrderByComparator<AnalyticsAssociationChange> orderByComparator)
		throws com.liferay.analytics.message.storage.exception.
			NoSuchAssociationChangeException {

		return getPersistence().findByC_GtM_A_Last(
			companyId, modifiedDate, associationClassName, orderByComparator);
	}

	/**
	 * Returns the last analytics association change in the ordered set where companyId = &#63; and modifiedDate &gt; &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param associationClassName the association class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics association change, or <code>null</code> if a matching analytics association change could not be found
	 */
	public static AnalyticsAssociationChange fetchByC_GtM_A_Last(
		long companyId, Date modifiedDate, String associationClassName,
		OrderByComparator<AnalyticsAssociationChange> orderByComparator) {

		return getPersistence().fetchByC_GtM_A_Last(
			companyId, modifiedDate, associationClassName, orderByComparator);
	}

	/**
	 * Returns the analytics association changes before and after the current analytics association change in the ordered set where companyId = &#63; and modifiedDate &gt; &#63; and associationClassName = &#63;.
	 *
	 * @param analyticsAssociationChangeId the primary key of the current analytics association change
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param associationClassName the association class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next analytics association change
	 * @throws NoSuchAssociationChangeException if a analytics association change with the primary key could not be found
	 */
	public static AnalyticsAssociationChange[] findByC_GtM_A_PrevAndNext(
			long analyticsAssociationChangeId, long companyId,
			Date modifiedDate, String associationClassName,
			OrderByComparator<AnalyticsAssociationChange> orderByComparator)
		throws com.liferay.analytics.message.storage.exception.
			NoSuchAssociationChangeException {

		return getPersistence().findByC_GtM_A_PrevAndNext(
			analyticsAssociationChangeId, companyId, modifiedDate,
			associationClassName, orderByComparator);
	}

	/**
	 * Removes all the analytics association changes where companyId = &#63; and modifiedDate &gt; &#63; and associationClassName = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param associationClassName the association class name
	 */
	public static void removeByC_GtM_A(
		long companyId, Date modifiedDate, String associationClassName) {

		getPersistence().removeByC_GtM_A(
			companyId, modifiedDate, associationClassName);
	}

	/**
	 * Returns the number of analytics association changes where companyId = &#63; and modifiedDate &gt; &#63; and associationClassName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param associationClassName the association class name
	 * @return the number of matching analytics association changes
	 */
	public static int countByC_GtM_A(
		long companyId, Date modifiedDate, String associationClassName) {

		return getPersistence().countByC_GtM_A(
			companyId, modifiedDate, associationClassName);
	}

	/**
	 * Returns all the analytics association changes where companyId = &#63; and associationClassName = &#63; and associationClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param associationClassPK the association class pk
	 * @return the matching analytics association changes
	 */
	public static List<AnalyticsAssociationChange> findByC_A_A(
		long companyId, String associationClassName, long associationClassPK) {

		return getPersistence().findByC_A_A(
			companyId, associationClassName, associationClassPK);
	}

	/**
	 * Returns a range of all the analytics association changes where companyId = &#63; and associationClassName = &#63; and associationClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationChangeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param associationClassPK the association class pk
	 * @param start the lower bound of the range of analytics association changes
	 * @param end the upper bound of the range of analytics association changes (not inclusive)
	 * @return the range of matching analytics association changes
	 */
	public static List<AnalyticsAssociationChange> findByC_A_A(
		long companyId, String associationClassName, long associationClassPK,
		int start, int end) {

		return getPersistence().findByC_A_A(
			companyId, associationClassName, associationClassPK, start, end);
	}

	/**
	 * Returns an ordered range of all the analytics association changes where companyId = &#63; and associationClassName = &#63; and associationClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationChangeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param associationClassPK the association class pk
	 * @param start the lower bound of the range of analytics association changes
	 * @param end the upper bound of the range of analytics association changes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching analytics association changes
	 */
	public static List<AnalyticsAssociationChange> findByC_A_A(
		long companyId, String associationClassName, long associationClassPK,
		int start, int end,
		OrderByComparator<AnalyticsAssociationChange> orderByComparator) {

		return getPersistence().findByC_A_A(
			companyId, associationClassName, associationClassPK, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the analytics association changes where companyId = &#63; and associationClassName = &#63; and associationClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationChangeModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param associationClassPK the association class pk
	 * @param start the lower bound of the range of analytics association changes
	 * @param end the upper bound of the range of analytics association changes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching analytics association changes
	 */
	public static List<AnalyticsAssociationChange> findByC_A_A(
		long companyId, String associationClassName, long associationClassPK,
		int start, int end,
		OrderByComparator<AnalyticsAssociationChange> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByC_A_A(
			companyId, associationClassName, associationClassPK, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first analytics association change in the ordered set where companyId = &#63; and associationClassName = &#63; and associationClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param associationClassPK the association class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics association change
	 * @throws NoSuchAssociationChangeException if a matching analytics association change could not be found
	 */
	public static AnalyticsAssociationChange findByC_A_A_First(
			long companyId, String associationClassName,
			long associationClassPK,
			OrderByComparator<AnalyticsAssociationChange> orderByComparator)
		throws com.liferay.analytics.message.storage.exception.
			NoSuchAssociationChangeException {

		return getPersistence().findByC_A_A_First(
			companyId, associationClassName, associationClassPK,
			orderByComparator);
	}

	/**
	 * Returns the first analytics association change in the ordered set where companyId = &#63; and associationClassName = &#63; and associationClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param associationClassPK the association class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching analytics association change, or <code>null</code> if a matching analytics association change could not be found
	 */
	public static AnalyticsAssociationChange fetchByC_A_A_First(
		long companyId, String associationClassName, long associationClassPK,
		OrderByComparator<AnalyticsAssociationChange> orderByComparator) {

		return getPersistence().fetchByC_A_A_First(
			companyId, associationClassName, associationClassPK,
			orderByComparator);
	}

	/**
	 * Returns the last analytics association change in the ordered set where companyId = &#63; and associationClassName = &#63; and associationClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param associationClassPK the association class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics association change
	 * @throws NoSuchAssociationChangeException if a matching analytics association change could not be found
	 */
	public static AnalyticsAssociationChange findByC_A_A_Last(
			long companyId, String associationClassName,
			long associationClassPK,
			OrderByComparator<AnalyticsAssociationChange> orderByComparator)
		throws com.liferay.analytics.message.storage.exception.
			NoSuchAssociationChangeException {

		return getPersistence().findByC_A_A_Last(
			companyId, associationClassName, associationClassPK,
			orderByComparator);
	}

	/**
	 * Returns the last analytics association change in the ordered set where companyId = &#63; and associationClassName = &#63; and associationClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param associationClassPK the association class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching analytics association change, or <code>null</code> if a matching analytics association change could not be found
	 */
	public static AnalyticsAssociationChange fetchByC_A_A_Last(
		long companyId, String associationClassName, long associationClassPK,
		OrderByComparator<AnalyticsAssociationChange> orderByComparator) {

		return getPersistence().fetchByC_A_A_Last(
			companyId, associationClassName, associationClassPK,
			orderByComparator);
	}

	/**
	 * Returns the analytics association changes before and after the current analytics association change in the ordered set where companyId = &#63; and associationClassName = &#63; and associationClassPK = &#63;.
	 *
	 * @param analyticsAssociationChangeId the primary key of the current analytics association change
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param associationClassPK the association class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next analytics association change
	 * @throws NoSuchAssociationChangeException if a analytics association change with the primary key could not be found
	 */
	public static AnalyticsAssociationChange[] findByC_A_A_PrevAndNext(
			long analyticsAssociationChangeId, long companyId,
			String associationClassName, long associationClassPK,
			OrderByComparator<AnalyticsAssociationChange> orderByComparator)
		throws com.liferay.analytics.message.storage.exception.
			NoSuchAssociationChangeException {

		return getPersistence().findByC_A_A_PrevAndNext(
			analyticsAssociationChangeId, companyId, associationClassName,
			associationClassPK, orderByComparator);
	}

	/**
	 * Removes all the analytics association changes where companyId = &#63; and associationClassName = &#63; and associationClassPK = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param associationClassPK the association class pk
	 */
	public static void removeByC_A_A(
		long companyId, String associationClassName, long associationClassPK) {

		getPersistence().removeByC_A_A(
			companyId, associationClassName, associationClassPK);
	}

	/**
	 * Returns the number of analytics association changes where companyId = &#63; and associationClassName = &#63; and associationClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param associationClassName the association class name
	 * @param associationClassPK the association class pk
	 * @return the number of matching analytics association changes
	 */
	public static int countByC_A_A(
		long companyId, String associationClassName, long associationClassPK) {

		return getPersistence().countByC_A_A(
			companyId, associationClassName, associationClassPK);
	}

	/**
	 * Caches the analytics association change in the entity cache if it is enabled.
	 *
	 * @param analyticsAssociationChange the analytics association change
	 */
	public static void cacheResult(
		AnalyticsAssociationChange analyticsAssociationChange) {

		getPersistence().cacheResult(analyticsAssociationChange);
	}

	/**
	 * Caches the analytics association changes in the entity cache if it is enabled.
	 *
	 * @param analyticsAssociationChanges the analytics association changes
	 */
	public static void cacheResult(
		List<AnalyticsAssociationChange> analyticsAssociationChanges) {

		getPersistence().cacheResult(analyticsAssociationChanges);
	}

	/**
	 * Creates a new analytics association change with the primary key. Does not add the analytics association change to the database.
	 *
	 * @param analyticsAssociationChangeId the primary key for the new analytics association change
	 * @return the new analytics association change
	 */
	public static AnalyticsAssociationChange create(
		long analyticsAssociationChangeId) {

		return getPersistence().create(analyticsAssociationChangeId);
	}

	/**
	 * Removes the analytics association change with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param analyticsAssociationChangeId the primary key of the analytics association change
	 * @return the analytics association change that was removed
	 * @throws NoSuchAssociationChangeException if a analytics association change with the primary key could not be found
	 */
	public static AnalyticsAssociationChange remove(
			long analyticsAssociationChangeId)
		throws com.liferay.analytics.message.storage.exception.
			NoSuchAssociationChangeException {

		return getPersistence().remove(analyticsAssociationChangeId);
	}

	public static AnalyticsAssociationChange updateImpl(
		AnalyticsAssociationChange analyticsAssociationChange) {

		return getPersistence().updateImpl(analyticsAssociationChange);
	}

	/**
	 * Returns the analytics association change with the primary key or throws a <code>NoSuchAssociationChangeException</code> if it could not be found.
	 *
	 * @param analyticsAssociationChangeId the primary key of the analytics association change
	 * @return the analytics association change
	 * @throws NoSuchAssociationChangeException if a analytics association change with the primary key could not be found
	 */
	public static AnalyticsAssociationChange findByPrimaryKey(
			long analyticsAssociationChangeId)
		throws com.liferay.analytics.message.storage.exception.
			NoSuchAssociationChangeException {

		return getPersistence().findByPrimaryKey(analyticsAssociationChangeId);
	}

	/**
	 * Returns the analytics association change with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param analyticsAssociationChangeId the primary key of the analytics association change
	 * @return the analytics association change, or <code>null</code> if a analytics association change with the primary key could not be found
	 */
	public static AnalyticsAssociationChange fetchByPrimaryKey(
		long analyticsAssociationChangeId) {

		return getPersistence().fetchByPrimaryKey(analyticsAssociationChangeId);
	}

	/**
	 * Returns all the analytics association changes.
	 *
	 * @return the analytics association changes
	 */
	public static List<AnalyticsAssociationChange> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the analytics association changes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationChangeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics association changes
	 * @param end the upper bound of the range of analytics association changes (not inclusive)
	 * @return the range of analytics association changes
	 */
	public static List<AnalyticsAssociationChange> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the analytics association changes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationChangeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics association changes
	 * @param end the upper bound of the range of analytics association changes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of analytics association changes
	 */
	public static List<AnalyticsAssociationChange> findAll(
		int start, int end,
		OrderByComparator<AnalyticsAssociationChange> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the analytics association changes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnalyticsAssociationChangeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics association changes
	 * @param end the upper bound of the range of analytics association changes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of analytics association changes
	 */
	public static List<AnalyticsAssociationChange> findAll(
		int start, int end,
		OrderByComparator<AnalyticsAssociationChange> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the analytics association changes from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of analytics association changes.
	 *
	 * @return the number of analytics association changes
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AnalyticsAssociationChangePersistence getPersistence() {
		return _persistence;
	}

	private static volatile AnalyticsAssociationChangePersistence _persistence;

}