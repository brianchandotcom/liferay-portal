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

package com.liferay.commerce.user.segment.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the commerce user segment criterion service. This utility wraps {@link com.liferay.commerce.user.segment.service.persistence.impl.CommerceUserSegmentCriterionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceUserSegmentCriterionPersistence
 * @see com.liferay.commerce.user.segment.service.persistence.impl.CommerceUserSegmentCriterionPersistenceImpl
 * @generated
 */
@ProviderType
public class CommerceUserSegmentCriterionUtil {
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
		CommerceUserSegmentCriterion commerceUserSegmentCriterion) {
		getPersistence().clearCache(commerceUserSegmentCriterion);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CommerceUserSegmentCriterion> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CommerceUserSegmentCriterion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CommerceUserSegmentCriterion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CommerceUserSegmentCriterion> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CommerceUserSegmentCriterion update(
		CommerceUserSegmentCriterion commerceUserSegmentCriterion) {
		return getPersistence().update(commerceUserSegmentCriterion);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CommerceUserSegmentCriterion update(
		CommerceUserSegmentCriterion commerceUserSegmentCriterion,
		ServiceContext serviceContext) {
		return getPersistence()
				   .update(commerceUserSegmentCriterion, serviceContext);
	}

	/**
	* Returns all the commerce user segment criterions where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @return the matching commerce user segment criterions
	*/
	public static List<CommerceUserSegmentCriterion> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId) {
		return getPersistence()
				   .findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
	}

	/**
	* Returns a range of all the commerce user segment criterions where commerceUserSegmentEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentCriterionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param start the lower bound of the range of commerce user segment criterions
	* @param end the upper bound of the range of commerce user segment criterions (not inclusive)
	* @return the range of matching commerce user segment criterions
	*/
	public static List<CommerceUserSegmentCriterion> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end) {
		return getPersistence()
				   .findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId,
			start, end);
	}

	/**
	* Returns an ordered range of all the commerce user segment criterions where commerceUserSegmentEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentCriterionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param start the lower bound of the range of commerce user segment criterions
	* @param end the upper bound of the range of commerce user segment criterions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce user segment criterions
	*/
	public static List<CommerceUserSegmentCriterion> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end,
		OrderByComparator<CommerceUserSegmentCriterion> orderByComparator) {
		return getPersistence()
				   .findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId,
			start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce user segment criterions where commerceUserSegmentEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentCriterionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param start the lower bound of the range of commerce user segment criterions
	* @param end the upper bound of the range of commerce user segment criterions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce user segment criterions
	*/
	public static List<CommerceUserSegmentCriterion> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end,
		OrderByComparator<CommerceUserSegmentCriterion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first commerce user segment criterion in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce user segment criterion
	* @throws NoSuchUserSegmentCriterionException if a matching commerce user segment criterion could not be found
	*/
	public static CommerceUserSegmentCriterion findByCommerceUserSegmentEntryId_First(
		long commerceUserSegmentEntryId,
		OrderByComparator<CommerceUserSegmentCriterion> orderByComparator)
		throws com.liferay.commerce.user.segment.exception.NoSuchUserSegmentCriterionException {
		return getPersistence()
				   .findByCommerceUserSegmentEntryId_First(commerceUserSegmentEntryId,
			orderByComparator);
	}

	/**
	* Returns the first commerce user segment criterion in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce user segment criterion, or <code>null</code> if a matching commerce user segment criterion could not be found
	*/
	public static CommerceUserSegmentCriterion fetchByCommerceUserSegmentEntryId_First(
		long commerceUserSegmentEntryId,
		OrderByComparator<CommerceUserSegmentCriterion> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceUserSegmentEntryId_First(commerceUserSegmentEntryId,
			orderByComparator);
	}

	/**
	* Returns the last commerce user segment criterion in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce user segment criterion
	* @throws NoSuchUserSegmentCriterionException if a matching commerce user segment criterion could not be found
	*/
	public static CommerceUserSegmentCriterion findByCommerceUserSegmentEntryId_Last(
		long commerceUserSegmentEntryId,
		OrderByComparator<CommerceUserSegmentCriterion> orderByComparator)
		throws com.liferay.commerce.user.segment.exception.NoSuchUserSegmentCriterionException {
		return getPersistence()
				   .findByCommerceUserSegmentEntryId_Last(commerceUserSegmentEntryId,
			orderByComparator);
	}

	/**
	* Returns the last commerce user segment criterion in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce user segment criterion, or <code>null</code> if a matching commerce user segment criterion could not be found
	*/
	public static CommerceUserSegmentCriterion fetchByCommerceUserSegmentEntryId_Last(
		long commerceUserSegmentEntryId,
		OrderByComparator<CommerceUserSegmentCriterion> orderByComparator) {
		return getPersistence()
				   .fetchByCommerceUserSegmentEntryId_Last(commerceUserSegmentEntryId,
			orderByComparator);
	}

	/**
	* Returns the commerce user segment criterions before and after the current commerce user segment criterion in the ordered set where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentCriterionId the primary key of the current commerce user segment criterion
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce user segment criterion
	* @throws NoSuchUserSegmentCriterionException if a commerce user segment criterion with the primary key could not be found
	*/
	public static CommerceUserSegmentCriterion[] findByCommerceUserSegmentEntryId_PrevAndNext(
		long commerceUserSegmentCriterionId, long commerceUserSegmentEntryId,
		OrderByComparator<CommerceUserSegmentCriterion> orderByComparator)
		throws com.liferay.commerce.user.segment.exception.NoSuchUserSegmentCriterionException {
		return getPersistence()
				   .findByCommerceUserSegmentEntryId_PrevAndNext(commerceUserSegmentCriterionId,
			commerceUserSegmentEntryId, orderByComparator);
	}

	/**
	* Removes all the commerce user segment criterions where commerceUserSegmentEntryId = &#63; from the database.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	*/
	public static void removeByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId) {
		getPersistence()
			.removeByCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
	}

	/**
	* Returns the number of commerce user segment criterions where commerceUserSegmentEntryId = &#63;.
	*
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @return the number of matching commerce user segment criterions
	*/
	public static int countByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId) {
		return getPersistence()
				   .countByCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
	}

	/**
	* Caches the commerce user segment criterion in the entity cache if it is enabled.
	*
	* @param commerceUserSegmentCriterion the commerce user segment criterion
	*/
	public static void cacheResult(
		CommerceUserSegmentCriterion commerceUserSegmentCriterion) {
		getPersistence().cacheResult(commerceUserSegmentCriterion);
	}

	/**
	* Caches the commerce user segment criterions in the entity cache if it is enabled.
	*
	* @param commerceUserSegmentCriterions the commerce user segment criterions
	*/
	public static void cacheResult(
		List<CommerceUserSegmentCriterion> commerceUserSegmentCriterions) {
		getPersistence().cacheResult(commerceUserSegmentCriterions);
	}

	/**
	* Creates a new commerce user segment criterion with the primary key. Does not add the commerce user segment criterion to the database.
	*
	* @param commerceUserSegmentCriterionId the primary key for the new commerce user segment criterion
	* @return the new commerce user segment criterion
	*/
	public static CommerceUserSegmentCriterion create(
		long commerceUserSegmentCriterionId) {
		return getPersistence().create(commerceUserSegmentCriterionId);
	}

	/**
	* Removes the commerce user segment criterion with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceUserSegmentCriterionId the primary key of the commerce user segment criterion
	* @return the commerce user segment criterion that was removed
	* @throws NoSuchUserSegmentCriterionException if a commerce user segment criterion with the primary key could not be found
	*/
	public static CommerceUserSegmentCriterion remove(
		long commerceUserSegmentCriterionId)
		throws com.liferay.commerce.user.segment.exception.NoSuchUserSegmentCriterionException {
		return getPersistence().remove(commerceUserSegmentCriterionId);
	}

	public static CommerceUserSegmentCriterion updateImpl(
		CommerceUserSegmentCriterion commerceUserSegmentCriterion) {
		return getPersistence().updateImpl(commerceUserSegmentCriterion);
	}

	/**
	* Returns the commerce user segment criterion with the primary key or throws a {@link NoSuchUserSegmentCriterionException} if it could not be found.
	*
	* @param commerceUserSegmentCriterionId the primary key of the commerce user segment criterion
	* @return the commerce user segment criterion
	* @throws NoSuchUserSegmentCriterionException if a commerce user segment criterion with the primary key could not be found
	*/
	public static CommerceUserSegmentCriterion findByPrimaryKey(
		long commerceUserSegmentCriterionId)
		throws com.liferay.commerce.user.segment.exception.NoSuchUserSegmentCriterionException {
		return getPersistence().findByPrimaryKey(commerceUserSegmentCriterionId);
	}

	/**
	* Returns the commerce user segment criterion with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceUserSegmentCriterionId the primary key of the commerce user segment criterion
	* @return the commerce user segment criterion, or <code>null</code> if a commerce user segment criterion with the primary key could not be found
	*/
	public static CommerceUserSegmentCriterion fetchByPrimaryKey(
		long commerceUserSegmentCriterionId) {
		return getPersistence().fetchByPrimaryKey(commerceUserSegmentCriterionId);
	}

	public static java.util.Map<java.io.Serializable, CommerceUserSegmentCriterion> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the commerce user segment criterions.
	*
	* @return the commerce user segment criterions
	*/
	public static List<CommerceUserSegmentCriterion> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the commerce user segment criterions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentCriterionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce user segment criterions
	* @param end the upper bound of the range of commerce user segment criterions (not inclusive)
	* @return the range of commerce user segment criterions
	*/
	public static List<CommerceUserSegmentCriterion> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the commerce user segment criterions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentCriterionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce user segment criterions
	* @param end the upper bound of the range of commerce user segment criterions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce user segment criterions
	*/
	public static List<CommerceUserSegmentCriterion> findAll(int start,
		int end,
		OrderByComparator<CommerceUserSegmentCriterion> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the commerce user segment criterions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceUserSegmentCriterionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce user segment criterions
	* @param end the upper bound of the range of commerce user segment criterions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce user segment criterions
	*/
	public static List<CommerceUserSegmentCriterion> findAll(int start,
		int end,
		OrderByComparator<CommerceUserSegmentCriterion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the commerce user segment criterions from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of commerce user segment criterions.
	*
	* @return the number of commerce user segment criterions
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CommerceUserSegmentCriterionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceUserSegmentCriterionPersistence, CommerceUserSegmentCriterionPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceUserSegmentCriterionPersistence.class);

		ServiceTracker<CommerceUserSegmentCriterionPersistence, CommerceUserSegmentCriterionPersistence> serviceTracker =
			new ServiceTracker<CommerceUserSegmentCriterionPersistence, CommerceUserSegmentCriterionPersistence>(bundle.getBundleContext(),
				CommerceUserSegmentCriterionPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}