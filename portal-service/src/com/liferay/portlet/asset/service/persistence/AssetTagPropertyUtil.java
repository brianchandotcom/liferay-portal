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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.asset.model.AssetTagProperty;

import java.util.List;

/**
 * The persistence utility for the asset tag property service. This utility wraps {@link AssetTagPropertyPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagPropertyPersistence
 * @see AssetTagPropertyPersistenceImpl
 * @generated
 */
public class AssetTagPropertyUtil {
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
	public static void clearCache(AssetTagProperty assetTagProperty) {
		getPersistence().clearCache(assetTagProperty);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AssetTagProperty> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetTagProperty> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AssetTagProperty> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static AssetTagProperty update(AssetTagProperty assetTagProperty)
		throws SystemException {
		return getPersistence().update(assetTagProperty);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static AssetTagProperty update(AssetTagProperty assetTagProperty,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(assetTagProperty, serviceContext);
	}

	/**
	* Returns all the asset tag properties where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching asset tag properties
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the asset tag properties where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of asset tag properties
	* @param end the upper bound of the range of asset tag properties (not inclusive)
	* @return the range of matching asset tag properties
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the asset tag properties where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of asset tag properties
	* @param end the upper bound of the range of asset tag properties (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset tag properties
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first asset tag property in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset tag property
	* @throws com.liferay.portlet.asset.NoSuchTagPropertyException if a matching asset tag property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first asset tag property in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset tag property, or <code>null</code> if a matching asset tag property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last asset tag property in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset tag property
	* @throws com.liferay.portlet.asset.NoSuchTagPropertyException if a matching asset tag property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last asset tag property in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset tag property, or <code>null</code> if a matching asset tag property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the asset tag properties before and after the current asset tag property in the ordered set where companyId = &#63;.
	*
	* @param tagPropertyId the primary key of the current asset tag property
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset tag property
	* @throws com.liferay.portlet.asset.NoSuchTagPropertyException if a asset tag property with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty[] findByCompanyId_PrevAndNext(
		long tagPropertyId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(tagPropertyId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the asset tag properties where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of asset tag properties where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching asset tag properties
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns all the asset tag properties where tagId = &#63;.
	*
	* @param tagId the tag ID
	* @return the matching asset tag properties
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByTagId(
		long tagId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByTagId(tagId);
	}

	/**
	* Returns a range of all the asset tag properties where tagId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tagId the tag ID
	* @param start the lower bound of the range of asset tag properties
	* @param end the upper bound of the range of asset tag properties (not inclusive)
	* @return the range of matching asset tag properties
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByTagId(
		long tagId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByTagId(tagId, start, end);
	}

	/**
	* Returns an ordered range of all the asset tag properties where tagId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param tagId the tag ID
	* @param start the lower bound of the range of asset tag properties
	* @param end the upper bound of the range of asset tag properties (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset tag properties
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByTagId(
		long tagId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByTagId(tagId, start, end, orderByComparator);
	}

	/**
	* Returns the first asset tag property in the ordered set where tagId = &#63;.
	*
	* @param tagId the tag ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset tag property
	* @throws com.liferay.portlet.asset.NoSuchTagPropertyException if a matching asset tag property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty findByTagId_First(
		long tagId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence().findByTagId_First(tagId, orderByComparator);
	}

	/**
	* Returns the first asset tag property in the ordered set where tagId = &#63;.
	*
	* @param tagId the tag ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset tag property, or <code>null</code> if a matching asset tag property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty fetchByTagId_First(
		long tagId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByTagId_First(tagId, orderByComparator);
	}

	/**
	* Returns the last asset tag property in the ordered set where tagId = &#63;.
	*
	* @param tagId the tag ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset tag property
	* @throws com.liferay.portlet.asset.NoSuchTagPropertyException if a matching asset tag property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty findByTagId_Last(
		long tagId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence().findByTagId_Last(tagId, orderByComparator);
	}

	/**
	* Returns the last asset tag property in the ordered set where tagId = &#63;.
	*
	* @param tagId the tag ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset tag property, or <code>null</code> if a matching asset tag property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty fetchByTagId_Last(
		long tagId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByTagId_Last(tagId, orderByComparator);
	}

	/**
	* Returns the asset tag properties before and after the current asset tag property in the ordered set where tagId = &#63;.
	*
	* @param tagPropertyId the primary key of the current asset tag property
	* @param tagId the tag ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset tag property
	* @throws com.liferay.portlet.asset.NoSuchTagPropertyException if a asset tag property with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty[] findByTagId_PrevAndNext(
		long tagPropertyId, long tagId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence()
				   .findByTagId_PrevAndNext(tagPropertyId, tagId,
			orderByComparator);
	}

	/**
	* Removes all the asset tag properties where tagId = &#63; from the database.
	*
	* @param tagId the tag ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByTagId(long tagId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByTagId(tagId);
	}

	/**
	* Returns the number of asset tag properties where tagId = &#63;.
	*
	* @param tagId the tag ID
	* @return the number of matching asset tag properties
	* @throws SystemException if a system exception occurred
	*/
	public static int countByTagId(long tagId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByTagId(tagId);
	}

	/**
	* Returns all the asset tag properties where companyId = &#63; and key = &#63;.
	*
	* @param companyId the company ID
	* @param key the key
	* @return the matching asset tag properties
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByC_K(
		long companyId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_K(companyId, key);
	}

	/**
	* Returns a range of all the asset tag properties where companyId = &#63; and key = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param key the key
	* @param start the lower bound of the range of asset tag properties
	* @param end the upper bound of the range of asset tag properties (not inclusive)
	* @return the range of matching asset tag properties
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByC_K(
		long companyId, java.lang.String key, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_K(companyId, key, start, end);
	}

	/**
	* Returns an ordered range of all the asset tag properties where companyId = &#63; and key = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param key the key
	* @param start the lower bound of the range of asset tag properties
	* @param end the upper bound of the range of asset tag properties (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset tag properties
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findByC_K(
		long companyId, java.lang.String key, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_K(companyId, key, start, end, orderByComparator);
	}

	/**
	* Returns the first asset tag property in the ordered set where companyId = &#63; and key = &#63;.
	*
	* @param companyId the company ID
	* @param key the key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset tag property
	* @throws com.liferay.portlet.asset.NoSuchTagPropertyException if a matching asset tag property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty findByC_K_First(
		long companyId, java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence()
				   .findByC_K_First(companyId, key, orderByComparator);
	}

	/**
	* Returns the first asset tag property in the ordered set where companyId = &#63; and key = &#63;.
	*
	* @param companyId the company ID
	* @param key the key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset tag property, or <code>null</code> if a matching asset tag property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty fetchByC_K_First(
		long companyId, java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_K_First(companyId, key, orderByComparator);
	}

	/**
	* Returns the last asset tag property in the ordered set where companyId = &#63; and key = &#63;.
	*
	* @param companyId the company ID
	* @param key the key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset tag property
	* @throws com.liferay.portlet.asset.NoSuchTagPropertyException if a matching asset tag property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty findByC_K_Last(
		long companyId, java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence().findByC_K_Last(companyId, key, orderByComparator);
	}

	/**
	* Returns the last asset tag property in the ordered set where companyId = &#63; and key = &#63;.
	*
	* @param companyId the company ID
	* @param key the key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset tag property, or <code>null</code> if a matching asset tag property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty fetchByC_K_Last(
		long companyId, java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_K_Last(companyId, key, orderByComparator);
	}

	/**
	* Returns the asset tag properties before and after the current asset tag property in the ordered set where companyId = &#63; and key = &#63;.
	*
	* @param tagPropertyId the primary key of the current asset tag property
	* @param companyId the company ID
	* @param key the key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset tag property
	* @throws com.liferay.portlet.asset.NoSuchTagPropertyException if a asset tag property with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty[] findByC_K_PrevAndNext(
		long tagPropertyId, long companyId, java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence()
				   .findByC_K_PrevAndNext(tagPropertyId, companyId, key,
			orderByComparator);
	}

	/**
	* Removes all the asset tag properties where companyId = &#63; and key = &#63; from the database.
	*
	* @param companyId the company ID
	* @param key the key
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_K(long companyId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_K(companyId, key);
	}

	/**
	* Returns the number of asset tag properties where companyId = &#63; and key = &#63;.
	*
	* @param companyId the company ID
	* @param key the key
	* @return the number of matching asset tag properties
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_K(long companyId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_K(companyId, key);
	}

	/**
	* Returns the asset tag property where tagId = &#63; and key = &#63; or throws a {@link com.liferay.portlet.asset.NoSuchTagPropertyException} if it could not be found.
	*
	* @param tagId the tag ID
	* @param key the key
	* @return the matching asset tag property
	* @throws com.liferay.portlet.asset.NoSuchTagPropertyException if a matching asset tag property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty findByT_K(
		long tagId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence().findByT_K(tagId, key);
	}

	/**
	* Returns the asset tag property where tagId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param tagId the tag ID
	* @param key the key
	* @return the matching asset tag property, or <code>null</code> if a matching asset tag property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty fetchByT_K(
		long tagId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByT_K(tagId, key);
	}

	/**
	* Returns the asset tag property where tagId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param tagId the tag ID
	* @param key the key
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching asset tag property, or <code>null</code> if a matching asset tag property could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty fetchByT_K(
		long tagId, java.lang.String key, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByT_K(tagId, key, retrieveFromCache);
	}

	/**
	* Removes the asset tag property where tagId = &#63; and key = &#63; from the database.
	*
	* @param tagId the tag ID
	* @param key the key
	* @return the asset tag property that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty removeByT_K(
		long tagId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence().removeByT_K(tagId, key);
	}

	/**
	* Returns the number of asset tag properties where tagId = &#63; and key = &#63;.
	*
	* @param tagId the tag ID
	* @param key the key
	* @return the number of matching asset tag properties
	* @throws SystemException if a system exception occurred
	*/
	public static int countByT_K(long tagId, java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByT_K(tagId, key);
	}

	/**
	* Caches the asset tag property in the entity cache if it is enabled.
	*
	* @param assetTagProperty the asset tag property
	*/
	public static void cacheResult(
		com.liferay.portlet.asset.model.AssetTagProperty assetTagProperty) {
		getPersistence().cacheResult(assetTagProperty);
	}

	/**
	* Caches the asset tag properties in the entity cache if it is enabled.
	*
	* @param assetTagProperties the asset tag properties
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> assetTagProperties) {
		getPersistence().cacheResult(assetTagProperties);
	}

	/**
	* Creates a new asset tag property with the primary key. Does not add the asset tag property to the database.
	*
	* @param tagPropertyId the primary key for the new asset tag property
	* @return the new asset tag property
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty create(
		long tagPropertyId) {
		return getPersistence().create(tagPropertyId);
	}

	/**
	* Removes the asset tag property with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param tagPropertyId the primary key of the asset tag property
	* @return the asset tag property that was removed
	* @throws com.liferay.portlet.asset.NoSuchTagPropertyException if a asset tag property with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty remove(
		long tagPropertyId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence().remove(tagPropertyId);
	}

	public static com.liferay.portlet.asset.model.AssetTagProperty updateImpl(
		com.liferay.portlet.asset.model.AssetTagProperty assetTagProperty)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(assetTagProperty);
	}

	/**
	* Returns the asset tag property with the primary key or throws a {@link com.liferay.portlet.asset.NoSuchTagPropertyException} if it could not be found.
	*
	* @param tagPropertyId the primary key of the asset tag property
	* @return the asset tag property
	* @throws com.liferay.portlet.asset.NoSuchTagPropertyException if a asset tag property with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty findByPrimaryKey(
		long tagPropertyId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchTagPropertyException {
		return getPersistence().findByPrimaryKey(tagPropertyId);
	}

	/**
	* Returns the asset tag property with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param tagPropertyId the primary key of the asset tag property
	* @return the asset tag property, or <code>null</code> if a asset tag property with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetTagProperty fetchByPrimaryKey(
		long tagPropertyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(tagPropertyId);
	}

	/**
	* Returns all the asset tag properties.
	*
	* @return the asset tag properties
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the asset tag properties.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset tag properties
	* @param end the upper bound of the range of asset tag properties (not inclusive)
	* @return the range of asset tag properties
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the asset tag properties.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagPropertyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset tag properties
	* @param end the upper bound of the range of asset tag properties (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset tag properties
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTagProperty> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the asset tag properties from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of asset tag properties.
	*
	* @return the number of asset tag properties
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static AssetTagPropertyPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (AssetTagPropertyPersistence)PortalBeanLocatorUtil.locate(AssetTagPropertyPersistence.class.getName());

			ReferenceRegistry.registerReference(AssetTagPropertyUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(AssetTagPropertyPersistence persistence) {
	}

	private static AssetTagPropertyPersistence _persistence;
}