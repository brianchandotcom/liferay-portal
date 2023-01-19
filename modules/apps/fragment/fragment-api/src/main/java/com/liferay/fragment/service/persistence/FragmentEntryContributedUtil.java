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

package com.liferay.fragment.service.persistence;

import com.liferay.fragment.model.FragmentEntryContributed;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the fragment entry contributed service. This utility wraps <code>com.liferay.fragment.service.persistence.impl.FragmentEntryContributedPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryContributedPersistence
 * @generated
 */
public class FragmentEntryContributedUtil {

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
		FragmentEntryContributed fragmentEntryContributed) {

		getPersistence().clearCache(fragmentEntryContributed);
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
	public static Map<Serializable, FragmentEntryContributed>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<FragmentEntryContributed> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<FragmentEntryContributed> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<FragmentEntryContributed> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<FragmentEntryContributed> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static FragmentEntryContributed update(
		FragmentEntryContributed fragmentEntryContributed) {

		return getPersistence().update(fragmentEntryContributed);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static FragmentEntryContributed update(
		FragmentEntryContributed fragmentEntryContributed,
		ServiceContext serviceContext) {

		return getPersistence().update(
			fragmentEntryContributed, serviceContext);
	}

	/**
	 * Returns the fragment entry contributed where fragmentEntryKey = &#63; or throws a <code>NoSuchEntryContributedException</code> if it could not be found.
	 *
	 * @param fragmentEntryKey the fragment entry key
	 * @return the matching fragment entry contributed
	 * @throws NoSuchEntryContributedException if a matching fragment entry contributed could not be found
	 */
	public static FragmentEntryContributed findByFragmentEntryKey(
			String fragmentEntryKey)
		throws com.liferay.fragment.exception.NoSuchEntryContributedException {

		return getPersistence().findByFragmentEntryKey(fragmentEntryKey);
	}

	/**
	 * Returns the fragment entry contributed where fragmentEntryKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param fragmentEntryKey the fragment entry key
	 * @return the matching fragment entry contributed, or <code>null</code> if a matching fragment entry contributed could not be found
	 */
	public static FragmentEntryContributed fetchByFragmentEntryKey(
		String fragmentEntryKey) {

		return getPersistence().fetchByFragmentEntryKey(fragmentEntryKey);
	}

	/**
	 * Returns the fragment entry contributed where fragmentEntryKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param fragmentEntryKey the fragment entry key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching fragment entry contributed, or <code>null</code> if a matching fragment entry contributed could not be found
	 */
	public static FragmentEntryContributed fetchByFragmentEntryKey(
		String fragmentEntryKey, boolean useFinderCache) {

		return getPersistence().fetchByFragmentEntryKey(
			fragmentEntryKey, useFinderCache);
	}

	/**
	 * Removes the fragment entry contributed where fragmentEntryKey = &#63; from the database.
	 *
	 * @param fragmentEntryKey the fragment entry key
	 * @return the fragment entry contributed that was removed
	 */
	public static FragmentEntryContributed removeByFragmentEntryKey(
			String fragmentEntryKey)
		throws com.liferay.fragment.exception.NoSuchEntryContributedException {

		return getPersistence().removeByFragmentEntryKey(fragmentEntryKey);
	}

	/**
	 * Returns the number of fragment entry contributeds where fragmentEntryKey = &#63;.
	 *
	 * @param fragmentEntryKey the fragment entry key
	 * @return the number of matching fragment entry contributeds
	 */
	public static int countByFragmentEntryKey(String fragmentEntryKey) {
		return getPersistence().countByFragmentEntryKey(fragmentEntryKey);
	}

	/**
	 * Caches the fragment entry contributed in the entity cache if it is enabled.
	 *
	 * @param fragmentEntryContributed the fragment entry contributed
	 */
	public static void cacheResult(
		FragmentEntryContributed fragmentEntryContributed) {

		getPersistence().cacheResult(fragmentEntryContributed);
	}

	/**
	 * Caches the fragment entry contributeds in the entity cache if it is enabled.
	 *
	 * @param fragmentEntryContributeds the fragment entry contributeds
	 */
	public static void cacheResult(
		List<FragmentEntryContributed> fragmentEntryContributeds) {

		getPersistence().cacheResult(fragmentEntryContributeds);
	}

	/**
	 * Creates a new fragment entry contributed with the primary key. Does not add the fragment entry contributed to the database.
	 *
	 * @param fragmentEntryContributedId the primary key for the new fragment entry contributed
	 * @return the new fragment entry contributed
	 */
	public static FragmentEntryContributed create(
		long fragmentEntryContributedId) {

		return getPersistence().create(fragmentEntryContributedId);
	}

	/**
	 * Removes the fragment entry contributed with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentEntryContributedId the primary key of the fragment entry contributed
	 * @return the fragment entry contributed that was removed
	 * @throws NoSuchEntryContributedException if a fragment entry contributed with the primary key could not be found
	 */
	public static FragmentEntryContributed remove(
			long fragmentEntryContributedId)
		throws com.liferay.fragment.exception.NoSuchEntryContributedException {

		return getPersistence().remove(fragmentEntryContributedId);
	}

	public static FragmentEntryContributed updateImpl(
		FragmentEntryContributed fragmentEntryContributed) {

		return getPersistence().updateImpl(fragmentEntryContributed);
	}

	/**
	 * Returns the fragment entry contributed with the primary key or throws a <code>NoSuchEntryContributedException</code> if it could not be found.
	 *
	 * @param fragmentEntryContributedId the primary key of the fragment entry contributed
	 * @return the fragment entry contributed
	 * @throws NoSuchEntryContributedException if a fragment entry contributed with the primary key could not be found
	 */
	public static FragmentEntryContributed findByPrimaryKey(
			long fragmentEntryContributedId)
		throws com.liferay.fragment.exception.NoSuchEntryContributedException {

		return getPersistence().findByPrimaryKey(fragmentEntryContributedId);
	}

	/**
	 * Returns the fragment entry contributed with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fragmentEntryContributedId the primary key of the fragment entry contributed
	 * @return the fragment entry contributed, or <code>null</code> if a fragment entry contributed with the primary key could not be found
	 */
	public static FragmentEntryContributed fetchByPrimaryKey(
		long fragmentEntryContributedId) {

		return getPersistence().fetchByPrimaryKey(fragmentEntryContributedId);
	}

	/**
	 * Returns all the fragment entry contributeds.
	 *
	 * @return the fragment entry contributeds
	 */
	public static List<FragmentEntryContributed> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the fragment entry contributeds.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryContributedModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entry contributeds
	 * @param end the upper bound of the range of fragment entry contributeds (not inclusive)
	 * @return the range of fragment entry contributeds
	 */
	public static List<FragmentEntryContributed> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the fragment entry contributeds.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryContributedModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entry contributeds
	 * @param end the upper bound of the range of fragment entry contributeds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of fragment entry contributeds
	 */
	public static List<FragmentEntryContributed> findAll(
		int start, int end,
		OrderByComparator<FragmentEntryContributed> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the fragment entry contributeds.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryContributedModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entry contributeds
	 * @param end the upper bound of the range of fragment entry contributeds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of fragment entry contributeds
	 */
	public static List<FragmentEntryContributed> findAll(
		int start, int end,
		OrderByComparator<FragmentEntryContributed> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the fragment entry contributeds from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of fragment entry contributeds.
	 *
	 * @return the number of fragment entry contributeds
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static FragmentEntryContributedPersistence getPersistence() {
		return _persistence;
	}

	private static volatile FragmentEntryContributedPersistence _persistence;

}