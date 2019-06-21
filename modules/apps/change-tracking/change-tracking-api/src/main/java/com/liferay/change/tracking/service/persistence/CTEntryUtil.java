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

import org.osgi.annotation.versioning.ProviderType;
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
@ProviderType
public class CTEntryUtil {

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ct entries
	 */
	public static List<CTEntry> findByCTCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTEntry> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByCTCollectionId(
			ctCollectionId, start, end, orderByComparator, retrieveFromCache);
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
	 * Returns all the ct entries where modelClassNameId = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @return the matching ct entries
	 */
	public static List<CTEntry> findByModelClassNameId(long modelClassNameId) {
		return getPersistence().findByModelClassNameId(modelClassNameId);
	}

	/**
	 * Returns a range of all the ct entries where modelClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param modelClassNameId the model class name ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of matching ct entries
	 */
	public static List<CTEntry> findByModelClassNameId(
		long modelClassNameId, int start, int end) {

		return getPersistence().findByModelClassNameId(
			modelClassNameId, start, end);
	}

	/**
	 * Returns an ordered range of all the ct entries where modelClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param modelClassNameId the model class name ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entries
	 */
	public static List<CTEntry> findByModelClassNameId(
		long modelClassNameId, int start, int end,
		OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().findByModelClassNameId(
			modelClassNameId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct entries where modelClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param modelClassNameId the model class name ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ct entries
	 */
	public static List<CTEntry> findByModelClassNameId(
		long modelClassNameId, int start, int end,
		OrderByComparator<CTEntry> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByModelClassNameId(
			modelClassNameId, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first ct entry in the ordered set where modelClassNameId = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	public static CTEntry findByModelClassNameId_First(
			long modelClassNameId, OrderByComparator<CTEntry> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {

		return getPersistence().findByModelClassNameId_First(
			modelClassNameId, orderByComparator);
	}

	/**
	 * Returns the first ct entry in the ordered set where modelClassNameId = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public static CTEntry fetchByModelClassNameId_First(
		long modelClassNameId, OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().fetchByModelClassNameId_First(
			modelClassNameId, orderByComparator);
	}

	/**
	 * Returns the last ct entry in the ordered set where modelClassNameId = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	public static CTEntry findByModelClassNameId_Last(
			long modelClassNameId, OrderByComparator<CTEntry> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {

		return getPersistence().findByModelClassNameId_Last(
			modelClassNameId, orderByComparator);
	}

	/**
	 * Returns the last ct entry in the ordered set where modelClassNameId = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public static CTEntry fetchByModelClassNameId_Last(
		long modelClassNameId, OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().fetchByModelClassNameId_Last(
			modelClassNameId, orderByComparator);
	}

	/**
	 * Returns the ct entries before and after the current ct entry in the ordered set where modelClassNameId = &#63;.
	 *
	 * @param ctEntryId the primary key of the current ct entry
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	public static CTEntry[] findByModelClassNameId_PrevAndNext(
			long ctEntryId, long modelClassNameId,
			OrderByComparator<CTEntry> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {

		return getPersistence().findByModelClassNameId_PrevAndNext(
			ctEntryId, modelClassNameId, orderByComparator);
	}

	/**
	 * Removes all the ct entries where modelClassNameId = &#63; from the database.
	 *
	 * @param modelClassNameId the model class name ID
	 */
	public static void removeByModelClassNameId(long modelClassNameId) {
		getPersistence().removeByModelClassNameId(modelClassNameId);
	}

	/**
	 * Returns the number of ct entries where modelClassNameId = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @return the number of matching ct entries
	 */
	public static int countByModelClassNameId(long modelClassNameId) {
		return getPersistence().countByModelClassNameId(modelClassNameId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ct entries
	 */
	public static List<CTEntry> findByC_MCNI(
		long ctCollectionId, long modelClassNameId, int start, int end,
		OrderByComparator<CTEntry> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByC_MCNI(
			ctCollectionId, modelClassNameId, start, end, orderByComparator,
			retrieveFromCache);
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
	 * Returns all the ct entries where ctCollectionId = &#63; and modelResourcePrimKey = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @return the matching ct entries
	 */
	public static List<CTEntry> findByC_MRPK(
		long ctCollectionId, long modelResourcePrimKey) {

		return getPersistence().findByC_MRPK(
			ctCollectionId, modelResourcePrimKey);
	}

	/**
	 * Returns a range of all the ct entries where ctCollectionId = &#63; and modelResourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of matching ct entries
	 */
	public static List<CTEntry> findByC_MRPK(
		long ctCollectionId, long modelResourcePrimKey, int start, int end) {

		return getPersistence().findByC_MRPK(
			ctCollectionId, modelResourcePrimKey, start, end);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId = &#63; and modelResourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entries
	 */
	public static List<CTEntry> findByC_MRPK(
		long ctCollectionId, long modelResourcePrimKey, int start, int end,
		OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().findByC_MRPK(
			ctCollectionId, modelResourcePrimKey, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId = &#63; and modelResourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ct entries
	 */
	public static List<CTEntry> findByC_MRPK(
		long ctCollectionId, long modelResourcePrimKey, int start, int end,
		OrderByComparator<CTEntry> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByC_MRPK(
			ctCollectionId, modelResourcePrimKey, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first ct entry in the ordered set where ctCollectionId = &#63; and modelResourcePrimKey = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	public static CTEntry findByC_MRPK_First(
			long ctCollectionId, long modelResourcePrimKey,
			OrderByComparator<CTEntry> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {

		return getPersistence().findByC_MRPK_First(
			ctCollectionId, modelResourcePrimKey, orderByComparator);
	}

	/**
	 * Returns the first ct entry in the ordered set where ctCollectionId = &#63; and modelResourcePrimKey = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public static CTEntry fetchByC_MRPK_First(
		long ctCollectionId, long modelResourcePrimKey,
		OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().fetchByC_MRPK_First(
			ctCollectionId, modelResourcePrimKey, orderByComparator);
	}

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId = &#63; and modelResourcePrimKey = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	public static CTEntry findByC_MRPK_Last(
			long ctCollectionId, long modelResourcePrimKey,
			OrderByComparator<CTEntry> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {

		return getPersistence().findByC_MRPK_Last(
			ctCollectionId, modelResourcePrimKey, orderByComparator);
	}

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId = &#63; and modelResourcePrimKey = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public static CTEntry fetchByC_MRPK_Last(
		long ctCollectionId, long modelResourcePrimKey,
		OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().fetchByC_MRPK_Last(
			ctCollectionId, modelResourcePrimKey, orderByComparator);
	}

	/**
	 * Returns the ct entries before and after the current ct entry in the ordered set where ctCollectionId = &#63; and modelResourcePrimKey = &#63;.
	 *
	 * @param ctEntryId the primary key of the current ct entry
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	public static CTEntry[] findByC_MRPK_PrevAndNext(
			long ctEntryId, long ctCollectionId, long modelResourcePrimKey,
			OrderByComparator<CTEntry> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {

		return getPersistence().findByC_MRPK_PrevAndNext(
			ctEntryId, ctCollectionId, modelResourcePrimKey, orderByComparator);
	}

	/**
	 * Removes all the ct entries where ctCollectionId = &#63; and modelResourcePrimKey = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 */
	public static void removeByC_MRPK(
		long ctCollectionId, long modelResourcePrimKey) {

		getPersistence().removeByC_MRPK(ctCollectionId, modelResourcePrimKey);
	}

	/**
	 * Returns the number of ct entries where ctCollectionId = &#63; and modelResourcePrimKey = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @return the number of matching ct entries
	 */
	public static int countByC_MRPK(
		long ctCollectionId, long modelResourcePrimKey) {

		return getPersistence().countByC_MRPK(
			ctCollectionId, modelResourcePrimKey);
	}

	/**
	 * Returns all the ct entries where ctCollectionId = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param status the status
	 * @return the matching ct entries
	 */
	public static List<CTEntry> findByC_S(long ctCollectionId, int status) {
		return getPersistence().findByC_S(ctCollectionId, status);
	}

	/**
	 * Returns a range of all the ct entries where ctCollectionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param status the status
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of matching ct entries
	 */
	public static List<CTEntry> findByC_S(
		long ctCollectionId, int status, int start, int end) {

		return getPersistence().findByC_S(ctCollectionId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param status the status
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entries
	 */
	public static List<CTEntry> findByC_S(
		long ctCollectionId, int status, int start, int end,
		OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().findByC_S(
			ctCollectionId, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param status the status
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ct entries
	 */
	public static List<CTEntry> findByC_S(
		long ctCollectionId, int status, int start, int end,
		OrderByComparator<CTEntry> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByC_S(
			ctCollectionId, status, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first ct entry in the ordered set where ctCollectionId = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	public static CTEntry findByC_S_First(
			long ctCollectionId, int status,
			OrderByComparator<CTEntry> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {

		return getPersistence().findByC_S_First(
			ctCollectionId, status, orderByComparator);
	}

	/**
	 * Returns the first ct entry in the ordered set where ctCollectionId = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public static CTEntry fetchByC_S_First(
		long ctCollectionId, int status,
		OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().fetchByC_S_First(
			ctCollectionId, status, orderByComparator);
	}

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	public static CTEntry findByC_S_Last(
			long ctCollectionId, int status,
			OrderByComparator<CTEntry> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {

		return getPersistence().findByC_S_Last(
			ctCollectionId, status, orderByComparator);
	}

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public static CTEntry fetchByC_S_Last(
		long ctCollectionId, int status,
		OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().fetchByC_S_Last(
			ctCollectionId, status, orderByComparator);
	}

	/**
	 * Returns the ct entries before and after the current ct entry in the ordered set where ctCollectionId = &#63; and status = &#63;.
	 *
	 * @param ctEntryId the primary key of the current ct entry
	 * @param ctCollectionId the ct collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	public static CTEntry[] findByC_S_PrevAndNext(
			long ctEntryId, long ctCollectionId, int status,
			OrderByComparator<CTEntry> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {

		return getPersistence().findByC_S_PrevAndNext(
			ctEntryId, ctCollectionId, status, orderByComparator);
	}

	/**
	 * Removes all the ct entries where ctCollectionId = &#63; and status = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param status the status
	 */
	public static void removeByC_S(long ctCollectionId, int status) {
		getPersistence().removeByC_S(ctCollectionId, status);
	}

	/**
	 * Returns the number of ct entries where ctCollectionId = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param status the status
	 * @return the number of matching ct entries
	 */
	public static int countByC_S(long ctCollectionId, int status) {
		return getPersistence().countByC_S(ctCollectionId, status);
	}

	/**
	 * Returns all the ct entries where modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the matching ct entries
	 */
	public static List<CTEntry> findByMCNI_MCPK(
		long modelClassNameId, long modelClassPK) {

		return getPersistence().findByMCNI_MCPK(modelClassNameId, modelClassPK);
	}

	/**
	 * Returns a range of all the ct entries where modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of matching ct entries
	 */
	public static List<CTEntry> findByMCNI_MCPK(
		long modelClassNameId, long modelClassPK, int start, int end) {

		return getPersistence().findByMCNI_MCPK(
			modelClassNameId, modelClassPK, start, end);
	}

	/**
	 * Returns an ordered range of all the ct entries where modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entries
	 */
	public static List<CTEntry> findByMCNI_MCPK(
		long modelClassNameId, long modelClassPK, int start, int end,
		OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().findByMCNI_MCPK(
			modelClassNameId, modelClassPK, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct entries where modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ct entries
	 */
	public static List<CTEntry> findByMCNI_MCPK(
		long modelClassNameId, long modelClassPK, int start, int end,
		OrderByComparator<CTEntry> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByMCNI_MCPK(
			modelClassNameId, modelClassPK, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first ct entry in the ordered set where modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	public static CTEntry findByMCNI_MCPK_First(
			long modelClassNameId, long modelClassPK,
			OrderByComparator<CTEntry> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {

		return getPersistence().findByMCNI_MCPK_First(
			modelClassNameId, modelClassPK, orderByComparator);
	}

	/**
	 * Returns the first ct entry in the ordered set where modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public static CTEntry fetchByMCNI_MCPK_First(
		long modelClassNameId, long modelClassPK,
		OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().fetchByMCNI_MCPK_First(
			modelClassNameId, modelClassPK, orderByComparator);
	}

	/**
	 * Returns the last ct entry in the ordered set where modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	public static CTEntry findByMCNI_MCPK_Last(
			long modelClassNameId, long modelClassPK,
			OrderByComparator<CTEntry> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {

		return getPersistence().findByMCNI_MCPK_Last(
			modelClassNameId, modelClassPK, orderByComparator);
	}

	/**
	 * Returns the last ct entry in the ordered set where modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public static CTEntry fetchByMCNI_MCPK_Last(
		long modelClassNameId, long modelClassPK,
		OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().fetchByMCNI_MCPK_Last(
			modelClassNameId, modelClassPK, orderByComparator);
	}

	/**
	 * Returns the ct entries before and after the current ct entry in the ordered set where modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param ctEntryId the primary key of the current ct entry
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	public static CTEntry[] findByMCNI_MCPK_PrevAndNext(
			long ctEntryId, long modelClassNameId, long modelClassPK,
			OrderByComparator<CTEntry> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {

		return getPersistence().findByMCNI_MCPK_PrevAndNext(
			ctEntryId, modelClassNameId, modelClassPK, orderByComparator);
	}

	/**
	 * Removes all the ct entries where modelClassNameId = &#63; and modelClassPK = &#63; from the database.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 */
	public static void removeByMCNI_MCPK(
		long modelClassNameId, long modelClassPK) {

		getPersistence().removeByMCNI_MCPK(modelClassNameId, modelClassPK);
	}

	/**
	 * Returns the number of ct entries where modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the number of matching ct entries
	 */
	public static int countByMCNI_MCPK(
		long modelClassNameId, long modelClassPK) {

		return getPersistence().countByMCNI_MCPK(
			modelClassNameId, modelClassPK);
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
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public static CTEntry fetchByC_MCNI_MCPK(
		long ctCollectionId, long modelClassNameId, long modelClassPK,
		boolean retrieveFromCache) {

		return getPersistence().fetchByC_MCNI_MCPK(
			ctCollectionId, modelClassNameId, modelClassPK, retrieveFromCache);
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
	 * Returns all the ct entries where ctCollectionId = &#63; and modelClassNameId = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param status the status
	 * @return the matching ct entries
	 */
	public static List<CTEntry> findByC_MCNI_S(
		long ctCollectionId, long modelClassNameId, int status) {

		return getPersistence().findByC_MCNI_S(
			ctCollectionId, modelClassNameId, status);
	}

	/**
	 * Returns a range of all the ct entries where ctCollectionId = &#63; and modelClassNameId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param status the status
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of matching ct entries
	 */
	public static List<CTEntry> findByC_MCNI_S(
		long ctCollectionId, long modelClassNameId, int status, int start,
		int end) {

		return getPersistence().findByC_MCNI_S(
			ctCollectionId, modelClassNameId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId = &#63; and modelClassNameId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param status the status
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entries
	 */
	public static List<CTEntry> findByC_MCNI_S(
		long ctCollectionId, long modelClassNameId, int status, int start,
		int end, OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().findByC_MCNI_S(
			ctCollectionId, modelClassNameId, status, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId = &#63; and modelClassNameId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param status the status
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ct entries
	 */
	public static List<CTEntry> findByC_MCNI_S(
		long ctCollectionId, long modelClassNameId, int status, int start,
		int end, OrderByComparator<CTEntry> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByC_MCNI_S(
			ctCollectionId, modelClassNameId, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first ct entry in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	public static CTEntry findByC_MCNI_S_First(
			long ctCollectionId, long modelClassNameId, int status,
			OrderByComparator<CTEntry> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {

		return getPersistence().findByC_MCNI_S_First(
			ctCollectionId, modelClassNameId, status, orderByComparator);
	}

	/**
	 * Returns the first ct entry in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public static CTEntry fetchByC_MCNI_S_First(
		long ctCollectionId, long modelClassNameId, int status,
		OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().fetchByC_MCNI_S_First(
			ctCollectionId, modelClassNameId, status, orderByComparator);
	}

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	public static CTEntry findByC_MCNI_S_Last(
			long ctCollectionId, long modelClassNameId, int status,
			OrderByComparator<CTEntry> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {

		return getPersistence().findByC_MCNI_S_Last(
			ctCollectionId, modelClassNameId, status, orderByComparator);
	}

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public static CTEntry fetchByC_MCNI_S_Last(
		long ctCollectionId, long modelClassNameId, int status,
		OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().fetchByC_MCNI_S_Last(
			ctCollectionId, modelClassNameId, status, orderByComparator);
	}

	/**
	 * Returns the ct entries before and after the current ct entry in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63; and status = &#63;.
	 *
	 * @param ctEntryId the primary key of the current ct entry
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	public static CTEntry[] findByC_MCNI_S_PrevAndNext(
			long ctEntryId, long ctCollectionId, long modelClassNameId,
			int status, OrderByComparator<CTEntry> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {

		return getPersistence().findByC_MCNI_S_PrevAndNext(
			ctEntryId, ctCollectionId, modelClassNameId, status,
			orderByComparator);
	}

	/**
	 * Removes all the ct entries where ctCollectionId = &#63; and modelClassNameId = &#63; and status = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param status the status
	 */
	public static void removeByC_MCNI_S(
		long ctCollectionId, long modelClassNameId, int status) {

		getPersistence().removeByC_MCNI_S(
			ctCollectionId, modelClassNameId, status);
	}

	/**
	 * Returns the number of ct entries where ctCollectionId = &#63; and modelClassNameId = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param status the status
	 * @return the number of matching ct entries
	 */
	public static int countByC_MCNI_S(
		long ctCollectionId, long modelClassNameId, int status) {

		return getPersistence().countByC_MCNI_S(
			ctCollectionId, modelClassNameId, status);
	}

	/**
	 * Returns all the ct entries where ctCollectionId = &#63; and modelResourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param status the status
	 * @return the matching ct entries
	 */
	public static List<CTEntry> findByC_MRPK_S(
		long ctCollectionId, long modelResourcePrimKey, int status) {

		return getPersistence().findByC_MRPK_S(
			ctCollectionId, modelResourcePrimKey, status);
	}

	/**
	 * Returns a range of all the ct entries where ctCollectionId = &#63; and modelResourcePrimKey = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param status the status
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of matching ct entries
	 */
	public static List<CTEntry> findByC_MRPK_S(
		long ctCollectionId, long modelResourcePrimKey, int status, int start,
		int end) {

		return getPersistence().findByC_MRPK_S(
			ctCollectionId, modelResourcePrimKey, status, start, end);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId = &#63; and modelResourcePrimKey = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param status the status
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entries
	 */
	public static List<CTEntry> findByC_MRPK_S(
		long ctCollectionId, long modelResourcePrimKey, int status, int start,
		int end, OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().findByC_MRPK_S(
			ctCollectionId, modelResourcePrimKey, status, start, end,
			orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId = &#63; and modelResourcePrimKey = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param status the status
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ct entries
	 */
	public static List<CTEntry> findByC_MRPK_S(
		long ctCollectionId, long modelResourcePrimKey, int status, int start,
		int end, OrderByComparator<CTEntry> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByC_MRPK_S(
			ctCollectionId, modelResourcePrimKey, status, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first ct entry in the ordered set where ctCollectionId = &#63; and modelResourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	public static CTEntry findByC_MRPK_S_First(
			long ctCollectionId, long modelResourcePrimKey, int status,
			OrderByComparator<CTEntry> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {

		return getPersistence().findByC_MRPK_S_First(
			ctCollectionId, modelResourcePrimKey, status, orderByComparator);
	}

	/**
	 * Returns the first ct entry in the ordered set where ctCollectionId = &#63; and modelResourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public static CTEntry fetchByC_MRPK_S_First(
		long ctCollectionId, long modelResourcePrimKey, int status,
		OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().fetchByC_MRPK_S_First(
			ctCollectionId, modelResourcePrimKey, status, orderByComparator);
	}

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId = &#63; and modelResourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	public static CTEntry findByC_MRPK_S_Last(
			long ctCollectionId, long modelResourcePrimKey, int status,
			OrderByComparator<CTEntry> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {

		return getPersistence().findByC_MRPK_S_Last(
			ctCollectionId, modelResourcePrimKey, status, orderByComparator);
	}

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId = &#63; and modelResourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	public static CTEntry fetchByC_MRPK_S_Last(
		long ctCollectionId, long modelResourcePrimKey, int status,
		OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().fetchByC_MRPK_S_Last(
			ctCollectionId, modelResourcePrimKey, status, orderByComparator);
	}

	/**
	 * Returns the ct entries before and after the current ct entry in the ordered set where ctCollectionId = &#63; and modelResourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param ctEntryId the primary key of the current ct entry
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	public static CTEntry[] findByC_MRPK_S_PrevAndNext(
			long ctEntryId, long ctCollectionId, long modelResourcePrimKey,
			int status, OrderByComparator<CTEntry> orderByComparator)
		throws com.liferay.change.tracking.exception.NoSuchEntryException {

		return getPersistence().findByC_MRPK_S_PrevAndNext(
			ctEntryId, ctCollectionId, modelResourcePrimKey, status,
			orderByComparator);
	}

	/**
	 * Removes all the ct entries where ctCollectionId = &#63; and modelResourcePrimKey = &#63; and status = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param status the status
	 */
	public static void removeByC_MRPK_S(
		long ctCollectionId, long modelResourcePrimKey, int status) {

		getPersistence().removeByC_MRPK_S(
			ctCollectionId, modelResourcePrimKey, status);
	}

	/**
	 * Returns the number of ct entries where ctCollectionId = &#63; and modelResourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param status the status
	 * @return the number of matching ct entries
	 */
	public static int countByC_MRPK_S(
		long ctCollectionId, long modelResourcePrimKey, int status) {

		return getPersistence().countByC_MRPK_S(
			ctCollectionId, modelResourcePrimKey, status);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of ct entries
	 */
	public static List<CTEntry> findAll(
		int start, int end, OrderByComparator<CTEntry> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, retrieveFromCache);
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

	/**
	 * Returns the primaryKeys of ct entry aggregates associated with the ct entry.
	 *
	 * @param pk the primary key of the ct entry
	 * @return long[] of the primaryKeys of ct entry aggregates associated with the ct entry
	 */
	public static long[] getCTEntryAggregatePrimaryKeys(long pk) {
		return getPersistence().getCTEntryAggregatePrimaryKeys(pk);
	}

	/**
	 * Returns all the ct entry associated with the ct entry aggregate.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @return the ct entries associated with the ct entry aggregate
	 */
	public static List<CTEntry> getCTEntryAggregateCTEntries(long pk) {
		return getPersistence().getCTEntryAggregateCTEntries(pk);
	}

	/**
	 * Returns all the ct entry associated with the ct entry aggregate.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @return the range of ct entries associated with the ct entry aggregate
	 */
	public static List<CTEntry> getCTEntryAggregateCTEntries(
		long pk, int start, int end) {

		return getPersistence().getCTEntryAggregateCTEntries(pk, start, end);
	}

	/**
	 * Returns all the ct entry associated with the ct entry aggregate.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct entries associated with the ct entry aggregate
	 */
	public static List<CTEntry> getCTEntryAggregateCTEntries(
		long pk, int start, int end,
		OrderByComparator<CTEntry> orderByComparator) {

		return getPersistence().getCTEntryAggregateCTEntries(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of ct entry aggregates associated with the ct entry.
	 *
	 * @param pk the primary key of the ct entry
	 * @return the number of ct entry aggregates associated with the ct entry
	 */
	public static int getCTEntryAggregatesSize(long pk) {
		return getPersistence().getCTEntryAggregatesSize(pk);
	}

	/**
	 * Returns <code>true</code> if the ct entry aggregate is associated with the ct entry.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregatePK the primary key of the ct entry aggregate
	 * @return <code>true</code> if the ct entry aggregate is associated with the ct entry; <code>false</code> otherwise
	 */
	public static boolean containsCTEntryAggregate(
		long pk, long ctEntryAggregatePK) {

		return getPersistence().containsCTEntryAggregate(
			pk, ctEntryAggregatePK);
	}

	/**
	 * Returns <code>true</code> if the ct entry has any ct entry aggregates associated with it.
	 *
	 * @param pk the primary key of the ct entry to check for associations with ct entry aggregates
	 * @return <code>true</code> if the ct entry has any ct entry aggregates associated with it; <code>false</code> otherwise
	 */
	public static boolean containsCTEntryAggregates(long pk) {
		return getPersistence().containsCTEntryAggregates(pk);
	}

	/**
	 * Adds an association between the ct entry and the ct entry aggregate. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregatePK the primary key of the ct entry aggregate
	 */
	public static void addCTEntryAggregate(long pk, long ctEntryAggregatePK) {
		getPersistence().addCTEntryAggregate(pk, ctEntryAggregatePK);
	}

	/**
	 * Adds an association between the ct entry and the ct entry aggregate. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregate the ct entry aggregate
	 */
	public static void addCTEntryAggregate(
		long pk,
		com.liferay.change.tracking.model.CTEntryAggregate ctEntryAggregate) {

		getPersistence().addCTEntryAggregate(pk, ctEntryAggregate);
	}

	/**
	 * Adds an association between the ct entry and the ct entry aggregates. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregatePKs the primary keys of the ct entry aggregates
	 */
	public static void addCTEntryAggregates(
		long pk, long[] ctEntryAggregatePKs) {

		getPersistence().addCTEntryAggregates(pk, ctEntryAggregatePKs);
	}

	/**
	 * Adds an association between the ct entry and the ct entry aggregates. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregates the ct entry aggregates
	 */
	public static void addCTEntryAggregates(
		long pk,
		List<com.liferay.change.tracking.model.CTEntryAggregate>
			ctEntryAggregates) {

		getPersistence().addCTEntryAggregates(pk, ctEntryAggregates);
	}

	/**
	 * Clears all associations between the ct entry and its ct entry aggregates. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry to clear the associated ct entry aggregates from
	 */
	public static void clearCTEntryAggregates(long pk) {
		getPersistence().clearCTEntryAggregates(pk);
	}

	/**
	 * Removes the association between the ct entry and the ct entry aggregate. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregatePK the primary key of the ct entry aggregate
	 */
	public static void removeCTEntryAggregate(
		long pk, long ctEntryAggregatePK) {

		getPersistence().removeCTEntryAggregate(pk, ctEntryAggregatePK);
	}

	/**
	 * Removes the association between the ct entry and the ct entry aggregate. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregate the ct entry aggregate
	 */
	public static void removeCTEntryAggregate(
		long pk,
		com.liferay.change.tracking.model.CTEntryAggregate ctEntryAggregate) {

		getPersistence().removeCTEntryAggregate(pk, ctEntryAggregate);
	}

	/**
	 * Removes the association between the ct entry and the ct entry aggregates. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregatePKs the primary keys of the ct entry aggregates
	 */
	public static void removeCTEntryAggregates(
		long pk, long[] ctEntryAggregatePKs) {

		getPersistence().removeCTEntryAggregates(pk, ctEntryAggregatePKs);
	}

	/**
	 * Removes the association between the ct entry and the ct entry aggregates. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregates the ct entry aggregates
	 */
	public static void removeCTEntryAggregates(
		long pk,
		List<com.liferay.change.tracking.model.CTEntryAggregate>
			ctEntryAggregates) {

		getPersistence().removeCTEntryAggregates(pk, ctEntryAggregates);
	}

	/**
	 * Sets the ct entry aggregates associated with the ct entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregatePKs the primary keys of the ct entry aggregates to be associated with the ct entry
	 */
	public static void setCTEntryAggregates(
		long pk, long[] ctEntryAggregatePKs) {

		getPersistence().setCTEntryAggregates(pk, ctEntryAggregatePKs);
	}

	/**
	 * Sets the ct entry aggregates associated with the ct entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregates the ct entry aggregates to be associated with the ct entry
	 */
	public static void setCTEntryAggregates(
		long pk,
		List<com.liferay.change.tracking.model.CTEntryAggregate>
			ctEntryAggregates) {

		getPersistence().setCTEntryAggregates(pk, ctEntryAggregates);
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