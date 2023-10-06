/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.model.PermissionPropagation;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the permission propagation service. This utility wraps <code>com.liferay.portal.service.persistence.impl.PermissionPropagationPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PermissionPropagationPersistence
 * @generated
 */
public class PermissionPropagationUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(PermissionPropagation permissionPropagation) {
		getPersistence().clearCache(permissionPropagation);
	}

	/**
	 * @see BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, PermissionPropagation> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<PermissionPropagation> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<PermissionPropagation> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<PermissionPropagation> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<PermissionPropagation> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static PermissionPropagation update(
		PermissionPropagation permissionPropagation) {

		return getPersistence().update(permissionPropagation);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static PermissionPropagation update(
		PermissionPropagation permissionPropagation,
		ServiceContext serviceContext) {

		return getPersistence().update(permissionPropagation, serviceContext);
	}

	/**
	 * Caches the permission propagation in the entity cache if it is enabled.
	 *
	 * @param permissionPropagation the permission propagation
	 */
	public static void cacheResult(
		PermissionPropagation permissionPropagation) {

		getPersistence().cacheResult(permissionPropagation);
	}

	/**
	 * Caches the permission propagations in the entity cache if it is enabled.
	 *
	 * @param permissionPropagations the permission propagations
	 */
	public static void cacheResult(
		List<PermissionPropagation> permissionPropagations) {

		getPersistence().cacheResult(permissionPropagations);
	}

	/**
	 * Creates a new permission propagation with the primary key. Does not add the permission propagation to the database.
	 *
	 * @param permissionPropagationId the primary key for the new permission propagation
	 * @return the new permission propagation
	 */
	public static PermissionPropagation create(long permissionPropagationId) {
		return getPersistence().create(permissionPropagationId);
	}

	/**
	 * Removes the permission propagation with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param permissionPropagationId the primary key of the permission propagation
	 * @return the permission propagation that was removed
	 * @throws NoSuchPermissionPropagationException if a permission propagation with the primary key could not be found
	 */
	public static PermissionPropagation remove(long permissionPropagationId)
		throws com.liferay.portal.kernel.exception.
			NoSuchPermissionPropagationException {

		return getPersistence().remove(permissionPropagationId);
	}

	public static PermissionPropagation updateImpl(
		PermissionPropagation permissionPropagation) {

		return getPersistence().updateImpl(permissionPropagation);
	}

	/**
	 * Returns the permission propagation with the primary key or throws a <code>NoSuchPermissionPropagationException</code> if it could not be found.
	 *
	 * @param permissionPropagationId the primary key of the permission propagation
	 * @return the permission propagation
	 * @throws NoSuchPermissionPropagationException if a permission propagation with the primary key could not be found
	 */
	public static PermissionPropagation findByPrimaryKey(
			long permissionPropagationId)
		throws com.liferay.portal.kernel.exception.
			NoSuchPermissionPropagationException {

		return getPersistence().findByPrimaryKey(permissionPropagationId);
	}

	/**
	 * Returns the permission propagation with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param permissionPropagationId the primary key of the permission propagation
	 * @return the permission propagation, or <code>null</code> if a permission propagation with the primary key could not be found
	 */
	public static PermissionPropagation fetchByPrimaryKey(
		long permissionPropagationId) {

		return getPersistence().fetchByPrimaryKey(permissionPropagationId);
	}

	/**
	 * Returns all the permission propagations.
	 *
	 * @return the permission propagations
	 */
	public static List<PermissionPropagation> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the permission propagations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PermissionPropagationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of permission propagations
	 * @param end the upper bound of the range of permission propagations (not inclusive)
	 * @return the range of permission propagations
	 */
	public static List<PermissionPropagation> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the permission propagations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PermissionPropagationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of permission propagations
	 * @param end the upper bound of the range of permission propagations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of permission propagations
	 */
	public static List<PermissionPropagation> findAll(
		int start, int end,
		OrderByComparator<PermissionPropagation> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the permission propagations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PermissionPropagationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of permission propagations
	 * @param end the upper bound of the range of permission propagations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of permission propagations
	 */
	public static List<PermissionPropagation> findAll(
		int start, int end,
		OrderByComparator<PermissionPropagation> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the permission propagations from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of permission propagations.
	 *
	 * @return the number of permission propagations
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static PermissionPropagationPersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(
		PermissionPropagationPersistence persistence) {

		_persistence = persistence;
	}

	private static volatile PermissionPropagationPersistence _persistence;

}