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

package com.liferay.portal.kernel.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.model.WebDAVProps;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;

import java.util.List;

/**
 * The persistence utility for the web dav props service. This utility wraps {@link com.liferay.portal.service.persistence.impl.WebDAVPropsPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WebDAVPropsPersistence
 * @see com.liferay.portal.service.persistence.impl.WebDAVPropsPersistenceImpl
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
	 * @see BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(WebDAVProps webDAVProps) {
		getPersistence().clearCache(webDAVProps);
	}

	/**
	 * @see BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<WebDAVProps> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<WebDAVProps> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<WebDAVProps> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<WebDAVProps> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static WebDAVProps update(WebDAVProps webDAVProps) {
		return getPersistence().update(webDAVProps);
	}

	/**
	 * @see BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static WebDAVProps update(WebDAVProps webDAVProps,
		ServiceContext serviceContext) {
		return getPersistence().update(webDAVProps, serviceContext);
	}

	/**
	* Returns the web dav props where classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchWebDAVPropsException} if it could not be found.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching web dav props
	* @throws NoSuchWebDAVPropsException if a matching web dav props could not be found
	*/
	public static WebDAVProps findByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.NoSuchWebDAVPropsException {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	* Returns the web dav props where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching web dav props, or <code>null</code> if a matching web dav props could not be found
	*/
	public static WebDAVProps fetchByC_C(long classNameId, long classPK) {
		return getPersistence().fetchByC_C(classNameId, classPK);
	}

	/**
	* Returns the web dav props where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching web dav props, or <code>null</code> if a matching web dav props could not be found
	*/
	public static WebDAVProps fetchByC_C(long classNameId, long classPK,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_C(classNameId, classPK, retrieveFromCache);
	}

	/**
	* Removes the web dav props where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the web dav props that was removed
	*/
	public static WebDAVProps removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.NoSuchWebDAVPropsException {
		return getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	* Returns the number of web dav propses where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching web dav propses
	*/
	public static int countByC_C(long classNameId, long classPK) {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	* Caches the web dav props in the entity cache if it is enabled.
	*
	* @param webDAVProps the web dav props
	*/
	public static void cacheResult(WebDAVProps webDAVProps) {
		getPersistence().cacheResult(webDAVProps);
	}

	/**
	* Caches the web dav propses in the entity cache if it is enabled.
	*
	* @param webDAVPropses the web dav propses
	*/
	public static void cacheResult(List<WebDAVProps> webDAVPropses) {
		getPersistence().cacheResult(webDAVPropses);
	}

	/**
	* Creates a new web dav props with the primary key. Does not add the web dav props to the database.
	*
	* @param webDavPropsId the primary key for the new web dav props
	* @return the new web dav props
	*/
	public static WebDAVProps create(long webDavPropsId) {
		return getPersistence().create(webDavPropsId);
	}

	/**
	* Removes the web dav props with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param webDavPropsId the primary key of the web dav props
	* @return the web dav props that was removed
	* @throws NoSuchWebDAVPropsException if a web dav props with the primary key could not be found
	*/
	public static WebDAVProps remove(long webDavPropsId)
		throws com.liferay.portal.kernel.exception.NoSuchWebDAVPropsException {
		return getPersistence().remove(webDavPropsId);
	}

	public static WebDAVProps updateImpl(WebDAVProps webDAVProps) {
		return getPersistence().updateImpl(webDAVProps);
	}

	/**
	* Returns the web dav props with the primary key or throws a {@link NoSuchWebDAVPropsException} if it could not be found.
	*
	* @param webDavPropsId the primary key of the web dav props
	* @return the web dav props
	* @throws NoSuchWebDAVPropsException if a web dav props with the primary key could not be found
	*/
	public static WebDAVProps findByPrimaryKey(long webDavPropsId)
		throws com.liferay.portal.kernel.exception.NoSuchWebDAVPropsException {
		return getPersistence().findByPrimaryKey(webDavPropsId);
	}

	/**
	* Returns the web dav props with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param webDavPropsId the primary key of the web dav props
	* @return the web dav props, or <code>null</code> if a web dav props with the primary key could not be found
	*/
	public static WebDAVProps fetchByPrimaryKey(long webDavPropsId) {
		return getPersistence().fetchByPrimaryKey(webDavPropsId);
	}

	public static java.util.Map<java.io.Serializable, WebDAVProps> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the web dav propses.
	*
	* @return the web dav propses
	*/
	public static List<WebDAVProps> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the web dav propses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WebDAVPropsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of web dav propses
	* @param end the upper bound of the range of web dav propses (not inclusive)
	* @return the range of web dav propses
	*/
	public static List<WebDAVProps> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the web dav propses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WebDAVPropsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of web dav propses
	* @param end the upper bound of the range of web dav propses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of web dav propses
	*/
	public static List<WebDAVProps> findAll(int start, int end,
		OrderByComparator<WebDAVProps> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the web dav propses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WebDAVPropsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of web dav propses
	* @param end the upper bound of the range of web dav propses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of web dav propses
	*/
	public static List<WebDAVProps> findAll(int start, int end,
		OrderByComparator<WebDAVProps> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the web dav propses from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of web dav propses.
	*
	* @return the number of web dav propses
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

	private static WebDAVPropsPersistence _persistence;
}