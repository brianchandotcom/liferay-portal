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

package com.liferay.portal.tools.service.builder.test.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.DefaultScopedEntry;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the default scoped entry service. This utility wraps <code>com.liferay.portal.tools.service.builder.test.service.persistence.impl.DefaultScopedEntryPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DefaultScopedEntryPersistence
 * @generated
 */
public class DefaultScopedEntryUtil {

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
	public static void clearCache(DefaultScopedEntry defaultScopedEntry) {
		getPersistence().clearCache(defaultScopedEntry);
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
	public static Map<Serializable, DefaultScopedEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<DefaultScopedEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DefaultScopedEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DefaultScopedEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<DefaultScopedEntry> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static DefaultScopedEntry update(
		DefaultScopedEntry defaultScopedEntry) {

		return getPersistence().update(defaultScopedEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static DefaultScopedEntry update(
		DefaultScopedEntry defaultScopedEntry, ServiceContext serviceContext) {

		return getPersistence().update(defaultScopedEntry, serviceContext);
	}

	/**
	 * Returns the default scoped entry where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchDefaultScopedEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching default scoped entry
	 * @throws NoSuchDefaultScopedEntryException if a matching default scoped entry could not be found
	 */
	public static DefaultScopedEntry findByC_ERC(
			long companyId, String externalReferenceCode)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchDefaultScopedEntryException {

		return getPersistence().findByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the default scoped entry where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching default scoped entry, or <code>null</code> if a matching default scoped entry could not be found
	 */
	public static DefaultScopedEntry fetchByC_ERC(
		long companyId, String externalReferenceCode) {

		return getPersistence().fetchByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the default scoped entry where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching default scoped entry, or <code>null</code> if a matching default scoped entry could not be found
	 */
	public static DefaultScopedEntry fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache) {

		return getPersistence().fetchByC_ERC(
			companyId, externalReferenceCode, useFinderCache);
	}

	/**
	 * Removes the default scoped entry where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the default scoped entry that was removed
	 */
	public static DefaultScopedEntry removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchDefaultScopedEntryException {

		return getPersistence().removeByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Returns the number of default scoped entries where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching default scoped entries
	 */
	public static int countByC_ERC(
		long companyId, String externalReferenceCode) {

		return getPersistence().countByC_ERC(companyId, externalReferenceCode);
	}

	/**
	 * Caches the default scoped entry in the entity cache if it is enabled.
	 *
	 * @param defaultScopedEntry the default scoped entry
	 */
	public static void cacheResult(DefaultScopedEntry defaultScopedEntry) {
		getPersistence().cacheResult(defaultScopedEntry);
	}

	/**
	 * Caches the default scoped entries in the entity cache if it is enabled.
	 *
	 * @param defaultScopedEntries the default scoped entries
	 */
	public static void cacheResult(
		List<DefaultScopedEntry> defaultScopedEntries) {

		getPersistence().cacheResult(defaultScopedEntries);
	}

	/**
	 * Creates a new default scoped entry with the primary key. Does not add the default scoped entry to the database.
	 *
	 * @param DefaultScopedEntryId the primary key for the new default scoped entry
	 * @return the new default scoped entry
	 */
	public static DefaultScopedEntry create(long DefaultScopedEntryId) {
		return getPersistence().create(DefaultScopedEntryId);
	}

	/**
	 * Removes the default scoped entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param DefaultScopedEntryId the primary key of the default scoped entry
	 * @return the default scoped entry that was removed
	 * @throws NoSuchDefaultScopedEntryException if a default scoped entry with the primary key could not be found
	 */
	public static DefaultScopedEntry remove(long DefaultScopedEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchDefaultScopedEntryException {

		return getPersistence().remove(DefaultScopedEntryId);
	}

	public static DefaultScopedEntry updateImpl(
		DefaultScopedEntry defaultScopedEntry) {

		return getPersistence().updateImpl(defaultScopedEntry);
	}

	/**
	 * Returns the default scoped entry with the primary key or throws a <code>NoSuchDefaultScopedEntryException</code> if it could not be found.
	 *
	 * @param DefaultScopedEntryId the primary key of the default scoped entry
	 * @return the default scoped entry
	 * @throws NoSuchDefaultScopedEntryException if a default scoped entry with the primary key could not be found
	 */
	public static DefaultScopedEntry findByPrimaryKey(long DefaultScopedEntryId)
		throws com.liferay.portal.tools.service.builder.test.exception.
			NoSuchDefaultScopedEntryException {

		return getPersistence().findByPrimaryKey(DefaultScopedEntryId);
	}

	/**
	 * Returns the default scoped entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param DefaultScopedEntryId the primary key of the default scoped entry
	 * @return the default scoped entry, or <code>null</code> if a default scoped entry with the primary key could not be found
	 */
	public static DefaultScopedEntry fetchByPrimaryKey(
		long DefaultScopedEntryId) {

		return getPersistence().fetchByPrimaryKey(DefaultScopedEntryId);
	}

	/**
	 * Returns all the default scoped entries.
	 *
	 * @return the default scoped entries
	 */
	public static List<DefaultScopedEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the default scoped entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DefaultScopedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of default scoped entries
	 * @param end the upper bound of the range of default scoped entries (not inclusive)
	 * @return the range of default scoped entries
	 */
	public static List<DefaultScopedEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the default scoped entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DefaultScopedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of default scoped entries
	 * @param end the upper bound of the range of default scoped entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of default scoped entries
	 */
	public static List<DefaultScopedEntry> findAll(
		int start, int end,
		OrderByComparator<DefaultScopedEntry> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the default scoped entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DefaultScopedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of default scoped entries
	 * @param end the upper bound of the range of default scoped entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of default scoped entries
	 */
	public static List<DefaultScopedEntry> findAll(
		int start, int end,
		OrderByComparator<DefaultScopedEntry> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the default scoped entries from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of default scoped entries.
	 *
	 * @return the number of default scoped entries
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static DefaultScopedEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DefaultScopedEntryPersistence, DefaultScopedEntryPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			DefaultScopedEntryPersistence.class);

		ServiceTracker
			<DefaultScopedEntryPersistence, DefaultScopedEntryPersistence>
				serviceTracker =
					new ServiceTracker
						<DefaultScopedEntryPersistence,
						 DefaultScopedEntryPersistence>(
							 bundle.getBundleContext(),
							 DefaultScopedEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}