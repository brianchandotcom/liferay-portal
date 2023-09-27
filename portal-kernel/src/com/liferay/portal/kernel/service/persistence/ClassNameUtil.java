/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence utility for the class name service. This utility wraps <code>com.liferay.portal.service.persistence.impl.ClassNamePersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ClassNamePersistence
 * @generated
 */
public class ClassNameUtil {

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
	public static void clearCache(ClassName className) {
		getPersistence().clearCache(className);
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
	public static Map<Serializable, ClassName> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ClassName> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ClassName> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ClassName> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ClassName> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ClassName update(ClassName className) {
		return getPersistence().update(className);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ClassName update(
		ClassName className, ServiceContext serviceContext) {

		return getPersistence().update(className, serviceContext);
	}

	/**
	 * Returns the class name where value = &#63; or throws a <code>NoSuchClassNameException</code> if it could not be found.
	 *
	 * @param value the value
	 * @return the matching class name
	 * @throws NoSuchClassNameException if a matching class name could not be found
	 */
	public static ClassName findByValue(String value)
		throws com.liferay.portal.kernel.exception.NoSuchClassNameException {

		return getPersistence().findByValue(value);
	}

	/**
	 * Returns the class name where value = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param value the value
	 * @return the matching class name, or <code>null</code> if a matching class name could not be found
	 */
	public static ClassName fetchByValue(String value) {
		return getPersistence().fetchByValue(value);
	}

	/**
	 * Returns the class name where value = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param value the value
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching class name, or <code>null</code> if a matching class name could not be found
	 */
	public static ClassName fetchByValue(String value, boolean useFinderCache) {
		return getPersistence().fetchByValue(value, useFinderCache);
	}

	/**
	 * Removes the class name where value = &#63; from the database.
	 *
	 * @param value the value
	 * @return the class name that was removed
	 */
	public static ClassName removeByValue(String value)
		throws com.liferay.portal.kernel.exception.NoSuchClassNameException {

		return getPersistence().removeByValue(value);
	}

	/**
	 * Returns the number of class names where value = &#63;.
	 *
	 * @param value the value
	 * @return the number of matching class names
	 */
	public static int countByValue(String value) {
		return getPersistence().countByValue(value);
	}

	/**
	 * Caches the class name in the entity cache if it is enabled.
	 *
	 * @param className the class name
	 */
	public static void cacheResult(ClassName className) {
		getPersistence().cacheResult(className);
	}

	/**
	 * Caches the class names in the entity cache if it is enabled.
	 *
	 * @param classNames the class names
	 */
	public static void cacheResult(List<ClassName> classNames) {
		getPersistence().cacheResult(classNames);
	}

	/**
	 * Creates a new class name with the primary key. Does not add the class name to the database.
	 *
	 * @param classNameId the primary key for the new class name
	 * @return the new class name
	 */
	public static ClassName create(long classNameId) {
		return getPersistence().create(classNameId);
	}

	/**
	 * Removes the class name with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param classNameId the primary key of the class name
	 * @return the class name that was removed
	 * @throws NoSuchClassNameException if a class name with the primary key could not be found
	 */
	public static ClassName remove(long classNameId)
		throws com.liferay.portal.kernel.exception.NoSuchClassNameException {

		return getPersistence().remove(classNameId);
	}

	public static ClassName updateImpl(ClassName className) {
		return getPersistence().updateImpl(className);
	}

	/**
	 * Returns the class name with the primary key or throws a <code>NoSuchClassNameException</code> if it could not be found.
	 *
	 * @param classNameId the primary key of the class name
	 * @return the class name
	 * @throws NoSuchClassNameException if a class name with the primary key could not be found
	 */
	public static ClassName findByPrimaryKey(long classNameId)
		throws com.liferay.portal.kernel.exception.NoSuchClassNameException {

		return getPersistence().findByPrimaryKey(classNameId);
	}

	/**
	 * Returns the class name with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param classNameId the primary key of the class name
	 * @return the class name, or <code>null</code> if a class name with the primary key could not be found
	 */
	public static ClassName fetchByPrimaryKey(long classNameId) {
		return getPersistence().fetchByPrimaryKey(classNameId);
	}

	/**
	 * Returns all the class names.
	 *
	 * @return the class names
	 */
	public static List<ClassName> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the class names.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClassNameModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of class names
	 * @param end the upper bound of the range of class names (not inclusive)
	 * @return the range of class names
	 */
	public static List<ClassName> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the class names.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClassNameModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of class names
	 * @param end the upper bound of the range of class names (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of class names
	 */
	public static List<ClassName> findAll(
		int start, int end, OrderByComparator<ClassName> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the class names.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ClassNameModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of class names
	 * @param end the upper bound of the range of class names (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of class names
	 */
	public static List<ClassName> findAll(
		int start, int end, OrderByComparator<ClassName> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the class names from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of class names.
	 *
	 * @return the number of class names
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ClassNamePersistence getPersistence() {
		return _persistence;
	}

	public static void setPersistence(ClassNamePersistence persistence) {
		_persistence = persistence;
	}

	private static volatile ClassNamePersistence _persistence;

}