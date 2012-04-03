/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.model.WebDAVProps;

/**
 * The persistence interface for the web d a v props service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WebDAVPropsPersistenceImpl
 * @see WebDAVPropsUtil
 * @generated
 */
public interface WebDAVPropsPersistence extends BasePersistence<WebDAVProps> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link WebDAVPropsUtil} to access the web d a v props persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the web d a v props in the entity cache if it is enabled.
	*
	* @param webDAVProps the web d a v props
	*/
	public void cacheResult(com.liferay.portal.model.WebDAVProps webDAVProps);

	/**
	* Caches the web d a v propses in the entity cache if it is enabled.
	*
	* @param webDAVPropses the web d a v propses
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.WebDAVProps> webDAVPropses);

	/**
	* Creates a new web d a v props with the primary key. Does not add the web d a v props to the database.
	*
	* @param webDavPropsId the primary key for the new web d a v props
	* @return the new web d a v props
	*/
	public com.liferay.portal.model.WebDAVProps create(long webDavPropsId);

	/**
	* Removes the web d a v props with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param webDavPropsId the primary key of the web d a v props
	* @return the web d a v props that was removed
	* @throws com.liferay.portal.NoSuchWebDAVPropsException if a web d a v props with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.WebDAVProps remove(long webDavPropsId)
		throws com.liferay.portal.NoSuchWebDAVPropsException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.WebDAVProps updateImpl(
		com.liferay.portal.model.WebDAVProps webDAVProps, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the web d a v props with the primary key or throws a {@link com.liferay.portal.NoSuchWebDAVPropsException} if it could not be found.
	*
	* @param webDavPropsId the primary key of the web d a v props
	* @return the web d a v props
	* @throws com.liferay.portal.NoSuchWebDAVPropsException if a web d a v props with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.WebDAVProps findByPrimaryKey(
		long webDavPropsId)
		throws com.liferay.portal.NoSuchWebDAVPropsException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the web d a v props with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param webDavPropsId the primary key of the web d a v props
	* @return the web d a v props, or <code>null</code> if a web d a v props with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.WebDAVProps fetchByPrimaryKey(
		long webDavPropsId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the web d a v props where classNameId = &#63; and classPK = &#63; or throws a {@link com.liferay.portal.NoSuchWebDAVPropsException} if it could not be found.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching web d a v props
	* @throws com.liferay.portal.NoSuchWebDAVPropsException if a matching web d a v props could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.WebDAVProps findByC_C(long classNameId,
		long classPK)
		throws com.liferay.portal.NoSuchWebDAVPropsException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the web d a v props where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching web d a v props, or <code>null</code> if a matching web d a v props could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.WebDAVProps fetchByC_C(long classNameId,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the web d a v props where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching web d a v props, or <code>null</code> if a matching web d a v props could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.WebDAVProps fetchByC_C(long classNameId,
		long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the web d a v propses.
	*
	* @return the web d a v propses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.WebDAVProps> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the web d a v propses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of web d a v propses
	* @param end the upper bound of the range of web d a v propses (not inclusive)
	* @return the range of web d a v propses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.WebDAVProps> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the web d a v propses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of web d a v propses
	* @param end the upper bound of the range of web d a v propses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of web d a v propses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.WebDAVProps> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the web d a v props where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the web d a v props that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.WebDAVProps removeByC_C(long classNameId,
		long classPK)
		throws com.liferay.portal.NoSuchWebDAVPropsException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the web d a v propses from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of web d a v propses where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching web d a v propses
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of web d a v propses.
	*
	* @return the number of web d a v propses
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}