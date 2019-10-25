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

package com.liferay.change.tracking.service.persistence;

import com.liferay.change.tracking.model.CTEntry;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the ct entry service. This utility wraps <code>com.liferay.change.tracking.service.persistence.impl.CTEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTEntryPersistence
 * @generated
 */
public class CTEntryUtil {

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
	public static void clearCache(CTEntry ctEntry) {
		getPersistence().clearCache(ctEntry);
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
	public static Map<Serializable, CTEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CTEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CTEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CTEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CTEntry update(CTEntry ctEntry) {
		return getPersistence().update(ctEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CTEntry update(
		CTEntry ctEntry, ServiceContext serviceContext) {

		return getPersistence().update(ctEntry, serviceContext);
	}

	/**
	 * Returns all the ct entries where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching ct entries
	 */
	public static List<CTEntry> findByCTCollectionId(long ctCollectionId) {
		return getPersistence().findByCTCollectionId(ctCollectionId);
	}

	/**
	 * Returns a range of all the ct entries where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of matching ct entries
	 */
	public static List<CTEntry> findByCTCollectionId(
		long ctCollectionId, int start, int end) {

		return getPersistence().findByCTCollectionId(
			ctCollectionId, start, end);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entries
	 */
	public static List<CTEntry> findByCTCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().findByCTCollectionId(
			ctCollectionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct entries
	 */
	public static List<CTEntry> findByCTCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTEntry> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByCTCollectionId(
			ctCollectionId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first ct entry in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	public static CTEntry findByCTCollectionId_First(
			long ctCollectionId, OrderByComparator<CTEntry> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {

		return getPersistence().findByCTCollectionId_First(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the first ct entry in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public static CTEntry fetchByCTCollectionId_First(
		long ctCollectionId, OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().fetchByCTCollectionId_First(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	public static CTEntry findByCTCollectionId_Last(
			long ctCollectionId, OrderByComparator<CTEntry> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {

		return getPersistence().findByCTCollectionId_Last(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public static CTEntry fetchByCTCollectionId_Last(
		long ctCollectionId, OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().fetchByCTCollectionId_Last(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the ct entries before and after the current ct entry in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctEntryId the primary key of the current ct entry
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	public static CTEntry[] findByCTCollectionId_PrevAndNext(
			long ctEntryId, long ctCollectionId,
			OrderByComparator<CTEntry> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {

		return getPersistence().findByCTCollectionId_PrevAndNext(
			ctEntryId, ctCollectionId, orderByComparator);
	}

	/**
	 * Removes all the ct entries where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 */
	public static void removeByCTCollectionId(long ctCollectionId) {
		getPersistence().removeByCTCollectionId(ctCollectionId);
	}

	/**
	 * Returns the number of ct entries where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching ct entries
	 */
	public static int countByCTCollectionId(long ctCollectionId) {
		return getPersistence().countByCTCollectionId(ctCollectionId);
	}

	/**
	 * Returns all the ct entries where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @return the matching ct entries
	 */
	public static List<CTEntry> findByC_MCNI(
		long ctCollectionId, long modelClassNameId) {

		return getPersistence().findByC_MCNI(ctCollectionId, modelClassNameId);
	}

	/**
	 * Returns a range of all the ct entries where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of matching ct entries
	 */
	public static List<CTEntry> findByC_MCNI(
		long ctCollectionId, long modelClassNameId, int start, int end) {

		return getPersistence().findByC_MCNI(
			ctCollectionId, modelClassNameId, start, end);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entries
	 */
	public static List<CTEntry> findByC_MCNI(
		long ctCollectionId, long modelClassNameId, int start, int end,
		OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().findByC_MCNI(
			ctCollectionId, modelClassNameId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct entries
	 */
	public static List<CTEntry> findByC_MCNI(
		long ctCollectionId, long modelClassNameId, int start, int end,
		OrderByComparator<CTEntry> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByC_MCNI(
			ctCollectionId, modelClassNameId, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first ct entry in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	public static CTEntry findByC_MCNI_First(
			long ctCollectionId, long modelClassNameId,
			OrderByComparator<CTEntry> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {

		return getPersistence().findByC_MCNI_First(
			ctCollectionId, modelClassNameId, orderByComparator);
	}

	/**
	 * Returns the first ct entry in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public static CTEntry fetchByC_MCNI_First(
		long ctCollectionId, long modelClassNameId,
		OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().fetchByC_MCNI_First(
			ctCollectionId, modelClassNameId, orderByComparator);
	}

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	public static CTEntry findByC_MCNI_Last(
			long ctCollectionId, long modelClassNameId,
			OrderByComparator<CTEntry> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {

		return getPersistence().findByC_MCNI_Last(
			ctCollectionId, modelClassNameId, orderByComparator);
	}

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public static CTEntry fetchByC_MCNI_Last(
		long ctCollectionId, long modelClassNameId,
		OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().fetchByC_MCNI_Last(
			ctCollectionId, modelClassNameId, orderByComparator);
	}

	/**
	 * Returns the ct entries before and after the current ct entry in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * @param ctEntryId the primary key of the current ct entry
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	public static CTEntry[] findByC_MCNI_PrevAndNext(
			long ctEntryId, long ctCollectionId, long modelClassNameId,
			OrderByComparator<CTEntry> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {

		return getPersistence().findByC_MCNI_PrevAndNext(
			ctEntryId, ctCollectionId, modelClassNameId, orderByComparator);
	}

	/**
	 * Removes all the ct entries where ctCollectionId = &#63; and modelClassNameId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 */
	public static void removeByC_MCNI(
		long ctCollectionId, long modelClassNameId) {

		getPersistence().removeByC_MCNI(ctCollectionId, modelClassNameId);
	}

	/**
	 * Returns the number of ct entries where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @return the number of matching ct entries
	 */
	public static int countByC_MCNI(
		long ctCollectionId, long modelClassNameId) {

		return getPersistence().countByC_MCNI(ctCollectionId, modelClassNameId);
	}

	/**
	 * Returns all the ct entries where ctCollectionId &ne; &#63; and modelClassPK = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassPK the model class pk
	 * @return the matching ct entries
	 */
	public static List<CTEntry> findByNotC_MCPK(
		long ctCollectionId, long modelClassPK) {

		return getPersistence().findByNotC_MCPK(ctCollectionId, modelClassPK);
	}

	/**
	 * Returns a range of all the ct entries where ctCollectionId &ne; &#63; and modelClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassPK the model class pk
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of matching ct entries
	 */
	public static List<CTEntry> findByNotC_MCPK(
		long ctCollectionId, long modelClassPK, int start, int end) {

		return getPersistence().findByNotC_MCPK(
			ctCollectionId, modelClassPK, start, end);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId &ne; &#63; and modelClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassPK the model class pk
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entries
	 */
	public static List<CTEntry> findByNotC_MCPK(
		long ctCollectionId, long modelClassPK, int start, int end,
		OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().findByNotC_MCPK(
			ctCollectionId, modelClassPK, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId &ne; &#63; and modelClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassPK the model class pk
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct entries
	 */
	public static List<CTEntry> findByNotC_MCPK(
		long ctCollectionId, long modelClassPK, int start, int end,
		OrderByComparator<CTEntry> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByNotC_MCPK(
			ctCollectionId, modelClassPK, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Returns the first ct entry in the ordered set where ctCollectionId &ne; &#63; and modelClassPK = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassPK the model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	public static CTEntry findByNotC_MCPK_First(
			long ctCollectionId, long modelClassPK,
			OrderByComparator<CTEntry> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {

		return getPersistence().findByNotC_MCPK_First(
			ctCollectionId, modelClassPK, orderByComparator);
	}

	/**
	 * Returns the first ct entry in the ordered set where ctCollectionId &ne; &#63; and modelClassPK = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassPK the model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public static CTEntry fetchByNotC_MCPK_First(
		long ctCollectionId, long modelClassPK,
		OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().fetchByNotC_MCPK_First(
			ctCollectionId, modelClassPK, orderByComparator);
	}

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId &ne; &#63; and modelClassPK = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassPK the model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	public static CTEntry findByNotC_MCPK_Last(
			long ctCollectionId, long modelClassPK,
			OrderByComparator<CTEntry> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {

		return getPersistence().findByNotC_MCPK_Last(
			ctCollectionId, modelClassPK, orderByComparator);
	}

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId &ne; &#63; and modelClassPK = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassPK the model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public static CTEntry fetchByNotC_MCPK_Last(
		long ctCollectionId, long modelClassPK,
		OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().fetchByNotC_MCPK_Last(
			ctCollectionId, modelClassPK, orderByComparator);
	}

	/**
	 * Returns the ct entries before and after the current ct entry in the ordered set where ctCollectionId &ne; &#63; and modelClassPK = &#63;.
	 *
	 * @param ctEntryId the primary key of the current ct entry
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassPK the model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	public static CTEntry[] findByNotC_MCPK_PrevAndNext(
			long ctEntryId, long ctCollectionId, long modelClassPK,
			OrderByComparator<CTEntry> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {

		return getPersistence().findByNotC_MCPK_PrevAndNext(
			ctEntryId, ctCollectionId, modelClassPK, orderByComparator);
	}

	/**
	 * Returns all the ct entries where ctCollectionId &ne; &#63; and modelClassPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassPKs the model class pks
	 * @return the matching ct entries
	 */
	public static List<CTEntry> findByNotC_MCPK(
		long ctCollectionId, long[] modelClassPKs) {

		return getPersistence().findByNotC_MCPK(ctCollectionId, modelClassPKs);
	}

	/**
	 * Returns a range of all the ct entries where ctCollectionId &ne; &#63; and modelClassPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassPKs the model class pks
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of matching ct entries
	 */
	public static List<CTEntry> findByNotC_MCPK(
		long ctCollectionId, long[] modelClassPKs, int start, int end) {

		return getPersistence().findByNotC_MCPK(
			ctCollectionId, modelClassPKs, start, end);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId &ne; &#63; and modelClassPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassPKs the model class pks
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entries
	 */
	public static List<CTEntry> findByNotC_MCPK(
		long ctCollectionId, long[] modelClassPKs, int start, int end,
		OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().findByNotC_MCPK(
			ctCollectionId, modelClassPKs, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId &ne; &#63; and modelClassPK = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassPK the model class pk
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct entries
	 */
	public static List<CTEntry> findByNotC_MCPK(
		long ctCollectionId, long[] modelClassPKs, int start, int end,
		OrderByComparator<CTEntry> orderByComparator, boolean useFinderCache) {

		return getPersistence().findByNotC_MCPK(
			ctCollectionId, modelClassPKs, start, end, orderByComparator,
			useFinderCache);
	}

	/**
	 * Removes all the ct entries where ctCollectionId &ne; &#63; and modelClassPK = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassPK the model class pk
	 */
	public static void removeByNotC_MCPK(
		long ctCollectionId, long modelClassPK) {

		getPersistence().removeByNotC_MCPK(ctCollectionId, modelClassPK);
	}

	/**
	 * Returns the number of ct entries where ctCollectionId &ne; &#63; and modelClassPK = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassPK the model class pk
	 * @return the number of matching ct entries
	 */
	public static int countByNotC_MCPK(long ctCollectionId, long modelClassPK) {
		return getPersistence().countByNotC_MCPK(ctCollectionId, modelClassPK);
	}

	/**
	 * Returns the number of ct entries where ctCollectionId &ne; &#63; and modelClassPK = any &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassPKs the model class pks
	 * @return the number of matching ct entries
	 */
	public static int countByNotC_MCPK(
		long ctCollectionId, long[] modelClassPKs) {

		return getPersistence().countByNotC_MCPK(ctCollectionId, modelClassPKs);
	}

	/**
	 * Returns the ct entry where ctCollectionId = &#63; and modelClassNameId = &#63; and modelClassPK = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	public static CTEntry findByC_MCNI_MCPK(
			long ctCollectionId, long modelClassNameId, long modelClassPK)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {

		return getPersistence().findByC_MCNI_MCPK(
			ctCollectionId, modelClassNameId, modelClassPK);
	}

	/**
	 * Returns the ct entry where ctCollectionId = &#63; and modelClassNameId = &#63; and modelClassPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public static CTEntry fetchByC_MCNI_MCPK(
		long ctCollectionId, long modelClassNameId, long modelClassPK) {

		return getPersistence().fetchByC_MCNI_MCPK(
			ctCollectionId, modelClassNameId, modelClassPK);
	}

	/**
	 * Returns the ct entry where ctCollectionId = &#63; and modelClassNameId = &#63; and modelClassPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public static CTEntry fetchByC_MCNI_MCPK(
		long ctCollectionId, long modelClassNameId, long modelClassPK,
		boolean useFinderCache) {

		return getPersistence().fetchByC_MCNI_MCPK(
			ctCollectionId, modelClassNameId, modelClassPK, useFinderCache);
	}

	/**
	 * Removes the ct entry where ctCollectionId = &#63; and modelClassNameId = &#63; and modelClassPK = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the ct entry that was removed
	 */
	public static CTEntry removeByC_MCNI_MCPK(
			long ctCollectionId, long modelClassNameId, long modelClassPK)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {

		return getPersistence().removeByC_MCNI_MCPK(
			ctCollectionId, modelClassNameId, modelClassPK);
	}

	/**
	 * Returns the number of ct entries where ctCollectionId = &#63; and modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the number of matching ct entries
	 */
	public static int countByC_MCNI_MCPK(
		long ctCollectionId, long modelClassNameId, long modelClassPK) {

		return getPersistence().countByC_MCNI_MCPK(
			ctCollectionId, modelClassNameId, modelClassPK);
	}

	/**
	 * Caches the ct entry in the entity cache if it is enabled.
	 *
	 * @param ctEntry the ct entry
	 */
	public static void cacheResult(CTEntry ctEntry) {
		getPersistence().cacheResult(ctEntry);
	}

	/**
	 * Caches the ct entries in the entity cache if it is enabled.
	 *
	 * @param ctEntries the ct entries
	 */
	public static void cacheResult(List<CTEntry> ctEntries) {
		getPersistence().cacheResult(ctEntries);
	}

	/**
	 * Creates a new ct entry with the primary key. Does not add the ct entry to the database.
	 *
	 * @param ctEntryId the primary key for the new ct entry
	 * @return the new ct entry
	 */
	public static CTEntry create(long ctEntryId) {
		return getPersistence().create(ctEntryId);
	}

	/**
	 * Removes the ct entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctEntryId the primary key of the ct entry
	 * @return the ct entry that was removed
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	public static CTEntry remove(long ctEntryId)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {

		return getPersistence().remove(ctEntryId);
	}

	public static CTEntry updateImpl(CTEntry ctEntry) {
		return getPersistence().updateImpl(ctEntry);
	}

	/**
	 * Returns the ct entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param ctEntryId the primary key of the ct entry
	 * @return the ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	public static CTEntry findByPrimaryKey(long ctEntryId)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {

		return getPersistence().findByPrimaryKey(ctEntryId);
	}

	/**
	 * Returns the ct entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctEntryId the primary key of the ct entry
	 * @return the ct entry, or <code>null</code> if a ct entry with the primary key could not be found
	 */
	public static CTEntry fetchByPrimaryKey(long ctEntryId) {
		return getPersistence().fetchByPrimaryKey(ctEntryId);
	}

	/**
	 * Returns all the ct entries.
	 *
	 * @return the ct entries
	 */
	public static List<CTEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the ct entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of ct entries
	 */
	public static List<CTEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the ct entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct entries
	 */
	public static List<CTEntry> findAll(
		int start, int end, OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ct entries
	 */
	public static List<CTEntry> findAll(
		int start, int end, OrderByComparator<CTEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the ct entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of ct entries.
	 *
	 * @return the number of ct entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CTEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CTEntryPersistence, CTEntryPersistence>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CTEntryPersistence.class);

		ServiceTracker<CTEntryPersistence, CTEntryPersistence> serviceTracker =
			new ServiceTracker<CTEntryPersistence, CTEntryPersistence>(
				bundle.getBundleContext(), CTEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}