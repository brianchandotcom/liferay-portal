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
import com.liferay.portal.model.WebDAVProps;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the web d a v props service. This utility wraps {@link WebDAVPropsPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WebDAVPropsPersistence
 * @see WebDAVPropsPersistenceImpl
 * @generated
 */
@ProviderType
public class WebDAVPropsUtil {
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
	public static void clearCache(WebDAVProps webDAVProps) {
		getPersistence().clearCache(webDAVProps);
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
	public static List<WebDAVProps> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<WebDAVProps> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<WebDAVProps> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static WebDAVProps update(WebDAVProps webDAVProps) {
		return getPersistence().update(webDAVProps);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static WebDAVProps update(WebDAVProps webDAVProps,
		ServiceContext serviceContext) {
		return getPersistence().update(webDAVProps, serviceContext);
	}

	/**
	* Returns the web d a v props where classNameId = &#63; and classPK = &#63; or throws a {@link com.liferay.portal.NoSuchWebDAVPropsException} if it could not be found.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching web d a v props
	* @throws com.liferay.portal.NoSuchWebDAVPropsException if a matching web d a v props could not be found
	*/
	public static com.liferay.portal.model.WebDAVProps findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.NoSuchWebDAVPropsException {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	* Returns the web d a v props where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching web d a v props, or <code>null</code> if a matching web d a v props could not be found
	*/
	public static com.liferay.portal.model.WebDAVProps fetchByC_C(
		long classNameId, long classPK) {
		return getPersistence().fetchByC_C(classNameId, classPK);
	}

	/**
	* Returns the web d a v props where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching web d a v props, or <code>null</code> if a matching web d a v props could not be found
	*/
	public static com.liferay.portal.model.WebDAVProps fetchByC_C(
		long classNameId, long classPK, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_C(classNameId, classPK, retrieveFromCache);
	}

	/**
	* Removes the web d a v props where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the web d a v props that was removed
	*/
	public static com.liferay.portal.model.WebDAVProps removeByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.NoSuchWebDAVPropsException {
		return getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	* Returns the number of web d a v propses where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching web d a v propses
	*/
	public static int countByC_C(long classNameId, long classPK) {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	* Caches the web d a v props in the entity cache if it is enabled.
	*
	* @param webDAVProps the web d a v props
	*/
	public static void cacheResult(
		com.liferay.portal.model.WebDAVProps webDAVProps) {
		getPersistence().cacheResult(webDAVProps);
	}

	/**
	* Caches the web d a v propses in the entity cache if it is enabled.
	*
	* @param webDAVPropses the web d a v propses
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.model.WebDAVProps> webDAVPropses) {
		getPersistence().cacheResult(webDAVPropses);
	}

	/**
	* Creates a new web d a v props with the primary key. Does not add the web d a v props to the database.
	*
	* @param webDavPropsId the primary key for the new web d a v props
	* @return the new web d a v props
	*/
	public static com.liferay.portal.model.WebDAVProps create(
		long webDavPropsId) {
		return getPersistence().create(webDavPropsId);
	}

	/**
	* Removes the web d a v props with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param webDavPropsId the primary key of the web d a v props
	* @return the web d a v props that was removed
	* @throws com.liferay.portal.NoSuchWebDAVPropsException if a web d a v props with the primary key could not be found
	*/
	public static com.liferay.portal.model.WebDAVProps remove(
		long webDavPropsId)
		throws com.liferay.portal.NoSuchWebDAVPropsException {
		return getPersistence().remove(webDavPropsId);
	}

	public static com.liferay.portal.model.WebDAVProps updateImpl(
		com.liferay.portal.model.WebDAVProps webDAVProps) {
		return getPersistence().updateImpl(webDAVProps);
	}

	/**
	* Returns the web d a v props with the primary key or throws a {@link com.liferay.portal.NoSuchWebDAVPropsException} if it could not be found.
	*
	* @param webDavPropsId the primary key of the web d a v props
	* @return the web d a v props
	* @throws com.liferay.portal.NoSuchWebDAVPropsException if a web d a v props with the primary key could not be found
	*/
	public static com.liferay.portal.model.WebDAVProps findByPrimaryKey(
		long webDavPropsId)
		throws com.liferay.portal.NoSuchWebDAVPropsException {
		return getPersistence().findByPrimaryKey(webDavPropsId);
	}

	/**
	* Returns the web d a v props with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param webDavPropsId the primary key of the web d a v props
	* @return the web d a v props, or <code>null</code> if a web d a v props with the primary key could not be found
	*/
	public static com.liferay.portal.model.WebDAVProps fetchByPrimaryKey(
		long webDavPropsId) {
		return getPersistence().fetchByPrimaryKey(webDavPropsId);
	}

	public static java.util.Map<java.io.Serializable, com.liferay.portal.model.WebDAVProps> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the web d a v propses.
	*
	* @return the web d a v propses
	*/
	public static java.util.List<com.liferay.portal.model.WebDAVProps> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the web d a v propses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.WebDAVPropsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of web d a v propses
	* @param end the upper bound of the range of web d a v propses (not inclusive)
	* @return the range of web d a v propses
	*/
	public static java.util.List<com.liferay.portal.model.WebDAVProps> findAll(
		int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the web d a v propses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.WebDAVPropsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of web d a v propses
	* @param end the upper bound of the range of web d a v propses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of web d a v propses
	*/
	public static java.util.List<com.liferay.portal.model.WebDAVProps> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the web d a v propses from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of web d a v propses.
	*
	* @return the number of web d a v propses
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static WebDAVPropsPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (WebDAVPropsPersistence)PortalBeanLocatorUtil.locate(WebDAVPropsPersistence.class.getName());

			ReferenceRegistry.registerReference(WebDAVPropsUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(WebDAVPropsPersistence persistence) {
	}

	private static WebDAVPropsPersistence _persistence;
}