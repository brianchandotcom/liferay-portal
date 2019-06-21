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

import com.liferay.change.tracking.model.CTEntryAggregate;
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
 * The persistence utility for the ct entry aggregate service. This utility wraps <code>com.liferay.change.tracking.service.persistence.impl.CTEntryAggregatePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTEntryAggregatePersistence
 * @generated
 */
@ProviderType
public class CTEntryAggregateUtil {

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
	public static void clearCache(CTEntryAggregate ctEntryAggregate) {
		getPersistence().clearCache(ctEntryAggregate);
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
	public static Map<Serializable, CTEntryAggregate> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CTEntryAggregate> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CTEntryAggregate> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CTEntryAggregate> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CTEntryAggregate> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CTEntryAggregate update(CTEntryAggregate ctEntryAggregate) {
		return getPersistence().update(ctEntryAggregate);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CTEntryAggregate update(
		CTEntryAggregate ctEntryAggregate, ServiceContext serviceContext) {

		return getPersistence().update(ctEntryAggregate, serviceContext);
	}

	/**
	 * Returns all the ct entry aggregates where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching ct entry aggregates
	 */
	public static List<CTEntryAggregate> findByCTCollectionId(
		long ctCollectionId) {

		return getPersistence().findByCTCollectionId(ctCollectionId);
	}

	/**
	 * Returns a range of all the ct entry aggregates where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @return the range of matching ct entry aggregates
	 */
	public static List<CTEntryAggregate> findByCTCollectionId(
		long ctCollectionId, int start, int end) {

		return getPersistence().findByCTCollectionId(
			ctCollectionId, start, end);
	}

	/**
	 * Returns an ordered range of all the ct entry aggregates where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entry aggregates
	 */
	public static List<CTEntryAggregate> findByCTCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTEntryAggregate> orderByComparator) {

		return getPersistence().findByCTCollectionId(
			ctCollectionId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct entry aggregates where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ct entry aggregates
	 */
	public static List<CTEntryAggregate> findByCTCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTEntryAggregate> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByCTCollectionId(
			ctCollectionId, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first ct entry aggregate in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry aggregate
	 * @throws NoSuchEntryAggregateException if a matching ct entry aggregate could not be found
	 */
	public static CTEntryAggregate findByCTCollectionId_First(
			long ctCollectionId,
			OrderByComparator<CTEntryAggregate> orderByComparator)
		throws com.liferay.change.tracking.exception.
			NoSuchEntryAggregateException {

		return getPersistence().findByCTCollectionId_First(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the first ct entry aggregate in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry aggregate, or <code>null</code> if a matching ct entry aggregate could not be found
	 */
	public static CTEntryAggregate fetchByCTCollectionId_First(
		long ctCollectionId,
		OrderByComparator<CTEntryAggregate> orderByComparator) {

		return getPersistence().fetchByCTCollectionId_First(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the last ct entry aggregate in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry aggregate
	 * @throws NoSuchEntryAggregateException if a matching ct entry aggregate could not be found
	 */
	public static CTEntryAggregate findByCTCollectionId_Last(
			long ctCollectionId,
			OrderByComparator<CTEntryAggregate> orderByComparator)
		throws com.liferay.change.tracking.exception.
			NoSuchEntryAggregateException {

		return getPersistence().findByCTCollectionId_Last(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the last ct entry aggregate in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry aggregate, or <code>null</code> if a matching ct entry aggregate could not be found
	 */
	public static CTEntryAggregate fetchByCTCollectionId_Last(
		long ctCollectionId,
		OrderByComparator<CTEntryAggregate> orderByComparator) {

		return getPersistence().fetchByCTCollectionId_Last(
			ctCollectionId, orderByComparator);
	}

	/**
	 * Returns the ct entry aggregates before and after the current ct entry aggregate in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctEntryAggregateId the primary key of the current ct entry aggregate
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry aggregate
	 * @throws NoSuchEntryAggregateException if a ct entry aggregate with the primary key could not be found
	 */
	public static CTEntryAggregate[] findByCTCollectionId_PrevAndNext(
			long ctEntryAggregateId, long ctCollectionId,
			OrderByComparator<CTEntryAggregate> orderByComparator)
		throws com.liferay.change.tracking.exception.
			NoSuchEntryAggregateException {

		return getPersistence().findByCTCollectionId_PrevAndNext(
			ctEntryAggregateId, ctCollectionId, orderByComparator);
	}

	/**
	 * Removes all the ct entry aggregates where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 */
	public static void removeByCTCollectionId(long ctCollectionId) {
		getPersistence().removeByCTCollectionId(ctCollectionId);
	}

	/**
	 * Returns the number of ct entry aggregates where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching ct entry aggregates
	 */
	public static int countByCTCollectionId(long ctCollectionId) {
		return getPersistence().countByCTCollectionId(ctCollectionId);
	}

	/**
	 * Returns all the ct entry aggregates where ownerCTEntryId = &#63;.
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @return the matching ct entry aggregates
	 */
	public static List<CTEntryAggregate> findByOwnerCTEntryId(
		long ownerCTEntryId) {

		return getPersistence().findByOwnerCTEntryId(ownerCTEntryId);
	}

	/**
	 * Returns a range of all the ct entry aggregates where ownerCTEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @return the range of matching ct entry aggregates
	 */
	public static List<CTEntryAggregate> findByOwnerCTEntryId(
		long ownerCTEntryId, int start, int end) {

		return getPersistence().findByOwnerCTEntryId(
			ownerCTEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the ct entry aggregates where ownerCTEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entry aggregates
	 */
	public static List<CTEntryAggregate> findByOwnerCTEntryId(
		long ownerCTEntryId, int start, int end,
		OrderByComparator<CTEntryAggregate> orderByComparator) {

		return getPersistence().findByOwnerCTEntryId(
			ownerCTEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct entry aggregates where ownerCTEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ct entry aggregates
	 */
	public static List<CTEntryAggregate> findByOwnerCTEntryId(
		long ownerCTEntryId, int start, int end,
		OrderByComparator<CTEntryAggregate> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByOwnerCTEntryId(
			ownerCTEntryId, start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Returns the first ct entry aggregate in the ordered set where ownerCTEntryId = &#63;.
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry aggregate
	 * @throws NoSuchEntryAggregateException if a matching ct entry aggregate could not be found
	 */
	public static CTEntryAggregate findByOwnerCTEntryId_First(
			long ownerCTEntryId,
			OrderByComparator<CTEntryAggregate> orderByComparator)
		throws com.liferay.change.tracking.exception.
			NoSuchEntryAggregateException {

		return getPersistence().findByOwnerCTEntryId_First(
			ownerCTEntryId, orderByComparator);
	}

	/**
	 * Returns the first ct entry aggregate in the ordered set where ownerCTEntryId = &#63;.
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry aggregate, or <code>null</code> if a matching ct entry aggregate could not be found
	 */
	public static CTEntryAggregate fetchByOwnerCTEntryId_First(
		long ownerCTEntryId,
		OrderByComparator<CTEntryAggregate> orderByComparator) {

		return getPersistence().fetchByOwnerCTEntryId_First(
			ownerCTEntryId, orderByComparator);
	}

	/**
	 * Returns the last ct entry aggregate in the ordered set where ownerCTEntryId = &#63;.
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry aggregate
	 * @throws NoSuchEntryAggregateException if a matching ct entry aggregate could not be found
	 */
	public static CTEntryAggregate findByOwnerCTEntryId_Last(
			long ownerCTEntryId,
			OrderByComparator<CTEntryAggregate> orderByComparator)
		throws com.liferay.change.tracking.exception.
			NoSuchEntryAggregateException {

		return getPersistence().findByOwnerCTEntryId_Last(
			ownerCTEntryId, orderByComparator);
	}

	/**
	 * Returns the last ct entry aggregate in the ordered set where ownerCTEntryId = &#63;.
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry aggregate, or <code>null</code> if a matching ct entry aggregate could not be found
	 */
	public static CTEntryAggregate fetchByOwnerCTEntryId_Last(
		long ownerCTEntryId,
		OrderByComparator<CTEntryAggregate> orderByComparator) {

		return getPersistence().fetchByOwnerCTEntryId_Last(
			ownerCTEntryId, orderByComparator);
	}

	/**
	 * Returns the ct entry aggregates before and after the current ct entry aggregate in the ordered set where ownerCTEntryId = &#63;.
	 *
	 * @param ctEntryAggregateId the primary key of the current ct entry aggregate
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry aggregate
	 * @throws NoSuchEntryAggregateException if a ct entry aggregate with the primary key could not be found
	 */
	public static CTEntryAggregate[] findByOwnerCTEntryId_PrevAndNext(
			long ctEntryAggregateId, long ownerCTEntryId,
			OrderByComparator<CTEntryAggregate> orderByComparator)
		throws com.liferay.change.tracking.exception.
			NoSuchEntryAggregateException {

		return getPersistence().findByOwnerCTEntryId_PrevAndNext(
			ctEntryAggregateId, ownerCTEntryId, orderByComparator);
	}

	/**
	 * Removes all the ct entry aggregates where ownerCTEntryId = &#63; from the database.
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 */
	public static void removeByOwnerCTEntryId(long ownerCTEntryId) {
		getPersistence().removeByOwnerCTEntryId(ownerCTEntryId);
	}

	/**
	 * Returns the number of ct entry aggregates where ownerCTEntryId = &#63;.
	 *
	 * @param ownerCTEntryId the owner ct entry ID
	 * @return the number of matching ct entry aggregates
	 */
	public static int countByOwnerCTEntryId(long ownerCTEntryId) {
		return getPersistence().countByOwnerCTEntryId(ownerCTEntryId);
	}

	/**
	 * Returns all the ct entry aggregates where ctCollectionId = &#63; and ownerCTEntryId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param ownerCTEntryId the owner ct entry ID
	 * @return the matching ct entry aggregates
	 */
	public static List<CTEntryAggregate> findByCTCID_OCEID(
		long ctCollectionId, long ownerCTEntryId) {

		return getPersistence().findByCTCID_OCEID(
			ctCollectionId, ownerCTEntryId);
	}

	/**
	 * Returns a range of all the ct entry aggregates where ctCollectionId = &#63; and ownerCTEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @return the range of matching ct entry aggregates
	 */
	public static List<CTEntryAggregate> findByCTCID_OCEID(
		long ctCollectionId, long ownerCTEntryId, int start, int end) {

		return getPersistence().findByCTCID_OCEID(
			ctCollectionId, ownerCTEntryId, start, end);
	}

	/**
	 * Returns an ordered range of all the ct entry aggregates where ctCollectionId = &#63; and ownerCTEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entry aggregates
	 */
	public static List<CTEntryAggregate> findByCTCID_OCEID(
		long ctCollectionId, long ownerCTEntryId, int start, int end,
		OrderByComparator<CTEntryAggregate> orderByComparator) {

		return getPersistence().findByCTCID_OCEID(
			ctCollectionId, ownerCTEntryId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct entry aggregates where ctCollectionId = &#63; and ownerCTEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ct entry aggregates
	 */
	public static List<CTEntryAggregate> findByCTCID_OCEID(
		long ctCollectionId, long ownerCTEntryId, int start, int end,
		OrderByComparator<CTEntryAggregate> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findByCTCID_OCEID(
			ctCollectionId, ownerCTEntryId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	 * Returns the first ct entry aggregate in the ordered set where ctCollectionId = &#63; and ownerCTEntryId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry aggregate
	 * @throws NoSuchEntryAggregateException if a matching ct entry aggregate could not be found
	 */
	public static CTEntryAggregate findByCTCID_OCEID_First(
			long ctCollectionId, long ownerCTEntryId,
			OrderByComparator<CTEntryAggregate> orderByComparator)
		throws com.liferay.change.tracking.exception.
			NoSuchEntryAggregateException {

		return getPersistence().findByCTCID_OCEID_First(
			ctCollectionId, ownerCTEntryId, orderByComparator);
	}

	/**
	 * Returns the first ct entry aggregate in the ordered set where ctCollectionId = &#63; and ownerCTEntryId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry aggregate, or <code>null</code> if a matching ct entry aggregate could not be found
	 */
	public static CTEntryAggregate fetchByCTCID_OCEID_First(
		long ctCollectionId, long ownerCTEntryId,
		OrderByComparator<CTEntryAggregate> orderByComparator) {

		return getPersistence().fetchByCTCID_OCEID_First(
			ctCollectionId, ownerCTEntryId, orderByComparator);
	}

	/**
	 * Returns the last ct entry aggregate in the ordered set where ctCollectionId = &#63; and ownerCTEntryId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry aggregate
	 * @throws NoSuchEntryAggregateException if a matching ct entry aggregate could not be found
	 */
	public static CTEntryAggregate findByCTCID_OCEID_Last(
			long ctCollectionId, long ownerCTEntryId,
			OrderByComparator<CTEntryAggregate> orderByComparator)
		throws com.liferay.change.tracking.exception.
			NoSuchEntryAggregateException {

		return getPersistence().findByCTCID_OCEID_Last(
			ctCollectionId, ownerCTEntryId, orderByComparator);
	}

	/**
	 * Returns the last ct entry aggregate in the ordered set where ctCollectionId = &#63; and ownerCTEntryId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry aggregate, or <code>null</code> if a matching ct entry aggregate could not be found
	 */
	public static CTEntryAggregate fetchByCTCID_OCEID_Last(
		long ctCollectionId, long ownerCTEntryId,
		OrderByComparator<CTEntryAggregate> orderByComparator) {

		return getPersistence().fetchByCTCID_OCEID_Last(
			ctCollectionId, ownerCTEntryId, orderByComparator);
	}

	/**
	 * Returns the ct entry aggregates before and after the current ct entry aggregate in the ordered set where ctCollectionId = &#63; and ownerCTEntryId = &#63;.
	 *
	 * @param ctEntryAggregateId the primary key of the current ct entry aggregate
	 * @param ctCollectionId the ct collection ID
	 * @param ownerCTEntryId the owner ct entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry aggregate
	 * @throws NoSuchEntryAggregateException if a ct entry aggregate with the primary key could not be found
	 */
	public static CTEntryAggregate[] findByCTCID_OCEID_PrevAndNext(
			long ctEntryAggregateId, long ctCollectionId, long ownerCTEntryId,
			OrderByComparator<CTEntryAggregate> orderByComparator)
		throws com.liferay.change.tracking.exception.
			NoSuchEntryAggregateException {

		return getPersistence().findByCTCID_OCEID_PrevAndNext(
			ctEntryAggregateId, ctCollectionId, ownerCTEntryId,
			orderByComparator);
	}

	/**
	 * Removes all the ct entry aggregates where ctCollectionId = &#63; and ownerCTEntryId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param ownerCTEntryId the owner ct entry ID
	 */
	public static void removeByCTCID_OCEID(
		long ctCollectionId, long ownerCTEntryId) {

		getPersistence().removeByCTCID_OCEID(ctCollectionId, ownerCTEntryId);
	}

	/**
	 * Returns the number of ct entry aggregates where ctCollectionId = &#63; and ownerCTEntryId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param ownerCTEntryId the owner ct entry ID
	 * @return the number of matching ct entry aggregates
	 */
	public static int countByCTCID_OCEID(
		long ctCollectionId, long ownerCTEntryId) {

		return getPersistence().countByCTCID_OCEID(
			ctCollectionId, ownerCTEntryId);
	}

	/**
	 * Caches the ct entry aggregate in the entity cache if it is enabled.
	 *
	 * @param ctEntryAggregate the ct entry aggregate
	 */
	public static void cacheResult(CTEntryAggregate ctEntryAggregate) {
		getPersistence().cacheResult(ctEntryAggregate);
	}

	/**
	 * Caches the ct entry aggregates in the entity cache if it is enabled.
	 *
	 * @param ctEntryAggregates the ct entry aggregates
	 */
	public static void cacheResult(List<CTEntryAggregate> ctEntryAggregates) {
		getPersistence().cacheResult(ctEntryAggregates);
	}

	/**
	 * Creates a new ct entry aggregate with the primary key. Does not add the ct entry aggregate to the database.
	 *
	 * @param ctEntryAggregateId the primary key for the new ct entry aggregate
	 * @return the new ct entry aggregate
	 */
	public static CTEntryAggregate create(long ctEntryAggregateId) {
		return getPersistence().create(ctEntryAggregateId);
	}

	/**
	 * Removes the ct entry aggregate with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctEntryAggregateId the primary key of the ct entry aggregate
	 * @return the ct entry aggregate that was removed
	 * @throws NoSuchEntryAggregateException if a ct entry aggregate with the primary key could not be found
	 */
	public static CTEntryAggregate remove(long ctEntryAggregateId)
		throws com.liferay.change.tracking.exception.
			NoSuchEntryAggregateException {

		return getPersistence().remove(ctEntryAggregateId);
	}

	public static CTEntryAggregate updateImpl(
		CTEntryAggregate ctEntryAggregate) {

		return getPersistence().updateImpl(ctEntryAggregate);
	}

	/**
	 * Returns the ct entry aggregate with the primary key or throws a <code>NoSuchEntryAggregateException</code> if it could not be found.
	 *
	 * @param ctEntryAggregateId the primary key of the ct entry aggregate
	 * @return the ct entry aggregate
	 * @throws NoSuchEntryAggregateException if a ct entry aggregate with the primary key could not be found
	 */
	public static CTEntryAggregate findByPrimaryKey(long ctEntryAggregateId)
		throws com.liferay.change.tracking.exception.
			NoSuchEntryAggregateException {

		return getPersistence().findByPrimaryKey(ctEntryAggregateId);
	}

	/**
	 * Returns the ct entry aggregate with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctEntryAggregateId the primary key of the ct entry aggregate
	 * @return the ct entry aggregate, or <code>null</code> if a ct entry aggregate with the primary key could not be found
	 */
	public static CTEntryAggregate fetchByPrimaryKey(long ctEntryAggregateId) {
		return getPersistence().fetchByPrimaryKey(ctEntryAggregateId);
	}

	/**
	 * Returns all the ct entry aggregates.
	 *
	 * @return the ct entry aggregates
	 */
	public static List<CTEntryAggregate> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the ct entry aggregates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @return the range of ct entry aggregates
	 */
	public static List<CTEntryAggregate> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the ct entry aggregates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct entry aggregates
	 */
	public static List<CTEntryAggregate> findAll(
		int start, int end,
		OrderByComparator<CTEntryAggregate> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the ct entry aggregates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of ct entry aggregates
	 */
	public static List<CTEntryAggregate> findAll(
		int start, int end,
		OrderByComparator<CTEntryAggregate> orderByComparator,
		boolean retrieveFromCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	 * Removes all the ct entry aggregates from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of ct entry aggregates.
	 *
	 * @return the number of ct entry aggregates
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	/**
	 * Returns the primaryKeys of ct entries associated with the ct entry aggregate.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @return long[] of the primaryKeys of ct entries associated with the ct entry aggregate
	 */
	public static long[] getCTEntryPrimaryKeys(long pk) {
		return getPersistence().getCTEntryPrimaryKeys(pk);
	}

	/**
	 * Returns all the ct entry aggregate associated with the ct entry.
	 *
	 * @param pk the primary key of the ct entry
	 * @return the ct entry aggregates associated with the ct entry
	 */
	public static List<CTEntryAggregate> getCTEntryCTEntryAggregates(long pk) {
		return getPersistence().getCTEntryCTEntryAggregates(pk);
	}

	/**
	 * Returns all the ct entry aggregate associated with the ct entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the ct entry
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of ct entry aggregates associated with the ct entry
	 */
	public static List<CTEntryAggregate> getCTEntryCTEntryAggregates(
		long pk, int start, int end) {

		return getPersistence().getCTEntryCTEntryAggregates(pk, start, end);
	}

	/**
	 * Returns all the ct entry aggregate associated with the ct entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryAggregateModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the ct entry
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct entry aggregates associated with the ct entry
	 */
	public static List<CTEntryAggregate> getCTEntryCTEntryAggregates(
		long pk, int start, int end,
		OrderByComparator<CTEntryAggregate> orderByComparator) {

		return getPersistence().getCTEntryCTEntryAggregates(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of ct entries associated with the ct entry aggregate.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @return the number of ct entries associated with the ct entry aggregate
	 */
	public static int getCTEntriesSize(long pk) {
		return getPersistence().getCTEntriesSize(pk);
	}

	/**
	 * Returns <code>true</code> if the ct entry is associated with the ct entry aggregate.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctEntryPK the primary key of the ct entry
	 * @return <code>true</code> if the ct entry is associated with the ct entry aggregate; <code>false</code> otherwise
	 */
	public static boolean containsCTEntry(long pk, long ctEntryPK) {
		return getPersistence().containsCTEntry(pk, ctEntryPK);
	}

	/**
	 * Returns <code>true</code> if the ct entry aggregate has any ct entries associated with it.
	 *
	 * @param pk the primary key of the ct entry aggregate to check for associations with ct entries
	 * @return <code>true</code> if the ct entry aggregate has any ct entries associated with it; <code>false</code> otherwise
	 */
	public static boolean containsCTEntries(long pk) {
		return getPersistence().containsCTEntries(pk);
	}

	/**
	 * Adds an association between the ct entry aggregate and the ct entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctEntryPK the primary key of the ct entry
	 */
	public static void addCTEntry(long pk, long ctEntryPK) {
		getPersistence().addCTEntry(pk, ctEntryPK);
	}

	/**
	 * Adds an association between the ct entry aggregate and the ct entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctEntry the ct entry
	 */
	public static void addCTEntry(
		long pk, com.liferay.change.tracking.model.CTEntry ctEntry) {

		getPersistence().addCTEntry(pk, ctEntry);
	}

	/**
	 * Adds an association between the ct entry aggregate and the ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctEntryPKs the primary keys of the ct entries
	 */
	public static void addCTEntries(long pk, long[] ctEntryPKs) {
		getPersistence().addCTEntries(pk, ctEntryPKs);
	}

	/**
	 * Adds an association between the ct entry aggregate and the ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctEntries the ct entries
	 */
	public static void addCTEntries(
		long pk, List<com.liferay.change.tracking.model.CTEntry> ctEntries) {

		getPersistence().addCTEntries(pk, ctEntries);
	}

	/**
	 * Clears all associations between the ct entry aggregate and its ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate to clear the associated ct entries from
	 */
	public static void clearCTEntries(long pk) {
		getPersistence().clearCTEntries(pk);
	}

	/**
	 * Removes the association between the ct entry aggregate and the ct entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctEntryPK the primary key of the ct entry
	 */
	public static void removeCTEntry(long pk, long ctEntryPK) {
		getPersistence().removeCTEntry(pk, ctEntryPK);
	}

	/**
	 * Removes the association between the ct entry aggregate and the ct entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctEntry the ct entry
	 */
	public static void removeCTEntry(
		long pk, com.liferay.change.tracking.model.CTEntry ctEntry) {

		getPersistence().removeCTEntry(pk, ctEntry);
	}

	/**
	 * Removes the association between the ct entry aggregate and the ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctEntryPKs the primary keys of the ct entries
	 */
	public static void removeCTEntries(long pk, long[] ctEntryPKs) {
		getPersistence().removeCTEntries(pk, ctEntryPKs);
	}

	/**
	 * Removes the association between the ct entry aggregate and the ct entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctEntries the ct entries
	 */
	public static void removeCTEntries(
		long pk, List<com.liferay.change.tracking.model.CTEntry> ctEntries) {

		getPersistence().removeCTEntries(pk, ctEntries);
	}

	/**
	 * Sets the ct entries associated with the ct entry aggregate, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctEntryPKs the primary keys of the ct entries to be associated with the ct entry aggregate
	 */
	public static void setCTEntries(long pk, long[] ctEntryPKs) {
		getPersistence().setCTEntries(pk, ctEntryPKs);
	}

	/**
	 * Sets the ct entries associated with the ct entry aggregate, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param ctEntries the ct entries to be associated with the ct entry aggregate
	 */
	public static void setCTEntries(
		long pk, List<com.liferay.change.tracking.model.CTEntry> ctEntries) {

		getPersistence().setCTEntries(pk, ctEntries);
	}

	public static CTEntryAggregatePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CTEntryAggregatePersistence, CTEntryAggregatePersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CTEntryAggregatePersistence.class);

		ServiceTracker<CTEntryAggregatePersistence, CTEntryAggregatePersistence>
			serviceTracker =
				new ServiceTracker
					<CTEntryAggregatePersistence, CTEntryAggregatePersistence>(
						bundle.getBundleContext(),
						CTEntryAggregatePersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}