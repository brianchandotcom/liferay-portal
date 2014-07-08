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

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the class name service. This utility wraps {@link ClassNamePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ClassNamePersistence
 * @see ClassNamePersistenceImpl
 * @generated
 */
@ProviderType
public class ClassNameUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(ClassName className) {
		getPersistence().clearCache(className);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ClassName> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ClassName> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ClassName> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static ClassName update(ClassName className) {
		return getPersistence().update(className);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static ClassName update(ClassName className,
		ServiceContext serviceContext) {
		return getPersistence().update(className, serviceContext);
	}

	/**
	* Returns the class name where value = &#63; or throws a {@link com.liferay.portal.NoSuchClassNameException} if it could not be found.
	*
	* @param value the value
	* @return the matching class name
	* @throws com.liferay.portal.NoSuchClassNameException if a matching class name could not be found
	*/
	public static com.liferay.portal.model.ClassName findByValue(
		java.lang.String value)
		throws com.liferay.portal.NoSuchClassNameException {
		return getPersistence().findByValue(value);
	}

	/**
	* Returns the class name where value = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param value the value
	* @return the matching class name, or <code>null</code> if a matching class name could not be found
	*/
	public static com.liferay.portal.model.ClassName fetchByValue(
		java.lang.String value) {
		return getPersistence().fetchByValue(value);
	}

	/**
	* Returns the class name where value = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param value the value
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching class name, or <code>null</code> if a matching class name could not be found
	*/
	public static com.liferay.portal.model.ClassName fetchByValue(
		java.lang.String value, boolean retrieveFromCache) {
		return getPersistence().fetchByValue(value, retrieveFromCache);
	}

	/**
	* Removes the class name where value = &#63; from the database.
	*
	* @param value the value
	* @return the class name that was removed
	*/
	public static com.liferay.portal.model.ClassName removeByValue(
		java.lang.String value)
		throws com.liferay.portal.NoSuchClassNameException {
		return getPersistence().removeByValue(value);
	}

	/**
	* Returns the number of class names where value = &#63;.
	*
	* @param value the value
	* @return the number of matching class names
	*/
	public static int countByValue(java.lang.String value) {
		return getPersistence().countByValue(value);
	}

	/**
	* Caches the class name in the entity cache if it is enabled.
	*
	* @param className the class name
	*/
	public static void cacheResult(com.liferay.portal.model.ClassName className) {
		getPersistence().cacheResult(className);
	}

	/**
	* Caches the class names in the entity cache if it is enabled.
	*
	* @param classNames the class names
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.model.ClassName> classNames) {
		getPersistence().cacheResult(classNames);
	}

	/**
	* Creates a new class name with the primary key. Does not add the class name to the database.
	*
	* @param classNameId the primary key for the new class name
	* @return the new class name
	*/
	public static com.liferay.portal.model.ClassName create(long classNameId) {
		return getPersistence().create(classNameId);
	}

	/**
	* Removes the class name with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param classNameId the primary key of the class name
	* @return the class name that was removed
	* @throws com.liferay.portal.NoSuchClassNameException if a class name with the primary key could not be found
	*/
	public static com.liferay.portal.model.ClassName remove(long classNameId)
		throws com.liferay.portal.NoSuchClassNameException {
		return getPersistence().remove(classNameId);
	}

	public static com.liferay.portal.model.ClassName updateImpl(
		com.liferay.portal.model.ClassName className) {
		return getPersistence().updateImpl(className);
	}

	/**
	* Returns the class name with the primary key or throws a {@link com.liferay.portal.NoSuchClassNameException} if it could not be found.
	*
	* @param classNameId the primary key of the class name
	* @return the class name
	* @throws com.liferay.portal.NoSuchClassNameException if a class name with the primary key could not be found
	*/
	public static com.liferay.portal.model.ClassName findByPrimaryKey(
		long classNameId) throws com.liferay.portal.NoSuchClassNameException {
		return getPersistence().findByPrimaryKey(classNameId);
	}

	/**
	* Returns the class name with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param classNameId the primary key of the class name
	* @return the class name, or <code>null</code> if a class name with the primary key could not be found
	*/
	public static com.liferay.portal.model.ClassName fetchByPrimaryKey(
		long classNameId) {
		return getPersistence().fetchByPrimaryKey(classNameId);
	}

	public static java.util.Map<java.io.Serializable, com.liferay.portal.model.ClassName> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the class names.
	*
	* @return the class names
	*/
	public static java.util.List<com.liferay.portal.model.ClassName> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the class names.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ClassNameModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of class names
	* @param end the upper bound of the range of class names (not inclusive)
	* @return the range of class names
	*/
	public static java.util.List<com.liferay.portal.model.ClassName> findAll(
		int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the class names.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ClassNameModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of class names
	* @param end the upper bound of the range of class names (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of class names
	*/
	public static java.util.List<com.liferay.portal.model.ClassName> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
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
		if (_persistence == null) {
			_persistence = (ClassNamePersistence)PortalBeanLocatorUtil.locate(ClassNamePersistence.class.getName());

			ReferenceRegistry.registerReference(ClassNameUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(ClassNamePersistence persistence) {
	}

	private static ClassNamePersistence _persistence;
}