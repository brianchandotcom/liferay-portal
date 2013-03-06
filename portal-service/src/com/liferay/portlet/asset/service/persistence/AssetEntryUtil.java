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

import com.liferay.portlet.asset.model.AssetEntry;

import java.util.List;

/**
 * The persistence utility for the asset entry service. This utility wraps {@link AssetEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryPersistence
 * @see AssetEntryPersistenceImpl
 * @generated
 */
public class AssetEntryUtil {
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
	public static void clearCache(AssetEntry assetEntry) {
		getPersistence().clearCache(assetEntry);
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
	public static List<AssetEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AssetEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static AssetEntry update(AssetEntry assetEntry)
		throws SystemException {
		return getPersistence().update(assetEntry);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static AssetEntry update(AssetEntry assetEntry,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(assetEntry, serviceContext);
	}

	/**
	* Returns all the asset entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the asset entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the asset entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first asset entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first asset entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last asset entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last asset entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the asset entries before and after the current asset entry in the ordered set where companyId = &#63;.
	*
	* @param entryId the primary key of the current asset entry
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a asset entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry[] findByCompanyId_PrevAndNext(
		long entryId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(entryId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the asset entries where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of asset entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns all the asset entries where visible = &#63;.
	*
	* @param visible the visible
	* @return the matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByVisible(
		boolean visible)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByVisible(visible);
	}

	/**
	* Returns a range of all the asset entries where visible = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param visible the visible
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByVisible(
		boolean visible, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByVisible(visible, start, end);
	}

	/**
	* Returns an ordered range of all the asset entries where visible = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param visible the visible
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByVisible(
		boolean visible, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByVisible(visible, start, end, orderByComparator);
	}

	/**
	* Returns the first asset entry in the ordered set where visible = &#63;.
	*
	* @param visible the visible
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry findByVisible_First(
		boolean visible,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence().findByVisible_First(visible, orderByComparator);
	}

	/**
	* Returns the first asset entry in the ordered set where visible = &#63;.
	*
	* @param visible the visible
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry fetchByVisible_First(
		boolean visible,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByVisible_First(visible, orderByComparator);
	}

	/**
	* Returns the last asset entry in the ordered set where visible = &#63;.
	*
	* @param visible the visible
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry findByVisible_Last(
		boolean visible,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence().findByVisible_Last(visible, orderByComparator);
	}

	/**
	* Returns the last asset entry in the ordered set where visible = &#63;.
	*
	* @param visible the visible
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry fetchByVisible_Last(
		boolean visible,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByVisible_Last(visible, orderByComparator);
	}

	/**
	* Returns the asset entries before and after the current asset entry in the ordered set where visible = &#63;.
	*
	* @param entryId the primary key of the current asset entry
	* @param visible the visible
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a asset entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry[] findByVisible_PrevAndNext(
		long entryId, boolean visible,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence()
				   .findByVisible_PrevAndNext(entryId, visible,
			orderByComparator);
	}

	/**
	* Removes all the asset entries where visible = &#63; from the database.
	*
	* @param visible the visible
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByVisible(boolean visible)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByVisible(visible);
	}

	/**
	* Returns the number of asset entries where visible = &#63;.
	*
	* @param visible the visible
	* @return the number of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByVisible(boolean visible)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByVisible(visible);
	}

	/**
	* Returns all the asset entries where publishDate = &#63;.
	*
	* @param publishDate the publish date
	* @return the matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByPublishDate(
		java.util.Date publishDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPublishDate(publishDate);
	}

	/**
	* Returns a range of all the asset entries where publishDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param publishDate the publish date
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByPublishDate(
		java.util.Date publishDate, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPublishDate(publishDate, start, end);
	}

	/**
	* Returns an ordered range of all the asset entries where publishDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param publishDate the publish date
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByPublishDate(
		java.util.Date publishDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByPublishDate(publishDate, start, end, orderByComparator);
	}

	/**
	* Returns the first asset entry in the ordered set where publishDate = &#63;.
	*
	* @param publishDate the publish date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry findByPublishDate_First(
		java.util.Date publishDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence()
				   .findByPublishDate_First(publishDate, orderByComparator);
	}

	/**
	* Returns the first asset entry in the ordered set where publishDate = &#63;.
	*
	* @param publishDate the publish date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry fetchByPublishDate_First(
		java.util.Date publishDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByPublishDate_First(publishDate, orderByComparator);
	}

	/**
	* Returns the last asset entry in the ordered set where publishDate = &#63;.
	*
	* @param publishDate the publish date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry findByPublishDate_Last(
		java.util.Date publishDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence()
				   .findByPublishDate_Last(publishDate, orderByComparator);
	}

	/**
	* Returns the last asset entry in the ordered set where publishDate = &#63;.
	*
	* @param publishDate the publish date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry fetchByPublishDate_Last(
		java.util.Date publishDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByPublishDate_Last(publishDate, orderByComparator);
	}

	/**
	* Returns the asset entries before and after the current asset entry in the ordered set where publishDate = &#63;.
	*
	* @param entryId the primary key of the current asset entry
	* @param publishDate the publish date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a asset entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry[] findByPublishDate_PrevAndNext(
		long entryId, java.util.Date publishDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence()
				   .findByPublishDate_PrevAndNext(entryId, publishDate,
			orderByComparator);
	}

	/**
	* Removes all the asset entries where publishDate = &#63; from the database.
	*
	* @param publishDate the publish date
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByPublishDate(java.util.Date publishDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByPublishDate(publishDate);
	}

	/**
	* Returns the number of asset entries where publishDate = &#63;.
	*
	* @param publishDate the publish date
	* @return the number of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByPublishDate(java.util.Date publishDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByPublishDate(publishDate);
	}

	/**
	* Returns all the asset entries where expirationDate = &#63;.
	*
	* @param expirationDate the expiration date
	* @return the matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByExpirationDate(
		java.util.Date expirationDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByExpirationDate(expirationDate);
	}

	/**
	* Returns a range of all the asset entries where expirationDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param expirationDate the expiration date
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByExpirationDate(
		java.util.Date expirationDate, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByExpirationDate(expirationDate, start, end);
	}

	/**
	* Returns an ordered range of all the asset entries where expirationDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param expirationDate the expiration date
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByExpirationDate(
		java.util.Date expirationDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByExpirationDate(expirationDate, start, end,
			orderByComparator);
	}

	/**
	* Returns the first asset entry in the ordered set where expirationDate = &#63;.
	*
	* @param expirationDate the expiration date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry findByExpirationDate_First(
		java.util.Date expirationDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence()
				   .findByExpirationDate_First(expirationDate, orderByComparator);
	}

	/**
	* Returns the first asset entry in the ordered set where expirationDate = &#63;.
	*
	* @param expirationDate the expiration date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry fetchByExpirationDate_First(
		java.util.Date expirationDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByExpirationDate_First(expirationDate,
			orderByComparator);
	}

	/**
	* Returns the last asset entry in the ordered set where expirationDate = &#63;.
	*
	* @param expirationDate the expiration date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry findByExpirationDate_Last(
		java.util.Date expirationDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence()
				   .findByExpirationDate_Last(expirationDate, orderByComparator);
	}

	/**
	* Returns the last asset entry in the ordered set where expirationDate = &#63;.
	*
	* @param expirationDate the expiration date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry fetchByExpirationDate_Last(
		java.util.Date expirationDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByExpirationDate_Last(expirationDate, orderByComparator);
	}

	/**
	* Returns the asset entries before and after the current asset entry in the ordered set where expirationDate = &#63;.
	*
	* @param entryId the primary key of the current asset entry
	* @param expirationDate the expiration date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a asset entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry[] findByExpirationDate_PrevAndNext(
		long entryId, java.util.Date expirationDate,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence()
				   .findByExpirationDate_PrevAndNext(entryId, expirationDate,
			orderByComparator);
	}

	/**
	* Removes all the asset entries where expirationDate = &#63; from the database.
	*
	* @param expirationDate the expiration date
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByExpirationDate(java.util.Date expirationDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByExpirationDate(expirationDate);
	}

	/**
	* Returns the number of asset entries where expirationDate = &#63;.
	*
	* @param expirationDate the expiration date
	* @return the number of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByExpirationDate(java.util.Date expirationDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByExpirationDate(expirationDate);
	}

	/**
	* Returns all the asset entries where layoutUuid = &#63;.
	*
	* @param layoutUuid the layout uuid
	* @return the matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByLayoutUuid(
		java.lang.String layoutUuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByLayoutUuid(layoutUuid);
	}

	/**
	* Returns a range of all the asset entries where layoutUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutUuid the layout uuid
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByLayoutUuid(
		java.lang.String layoutUuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByLayoutUuid(layoutUuid, start, end);
	}

	/**
	* Returns an ordered range of all the asset entries where layoutUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param layoutUuid the layout uuid
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> findByLayoutUuid(
		java.lang.String layoutUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByLayoutUuid(layoutUuid, start, end, orderByComparator);
	}

	/**
	* Returns the first asset entry in the ordered set where layoutUuid = &#63;.
	*
	* @param layoutUuid the layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry findByLayoutUuid_First(
		java.lang.String layoutUuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence()
				   .findByLayoutUuid_First(layoutUuid, orderByComparator);
	}

	/**
	* Returns the first asset entry in the ordered set where layoutUuid = &#63;.
	*
	* @param layoutUuid the layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry fetchByLayoutUuid_First(
		java.lang.String layoutUuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByLayoutUuid_First(layoutUuid, orderByComparator);
	}

	/**
	* Returns the last asset entry in the ordered set where layoutUuid = &#63;.
	*
	* @param layoutUuid the layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry findByLayoutUuid_Last(
		java.lang.String layoutUuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence()
				   .findByLayoutUuid_Last(layoutUuid, orderByComparator);
	}

	/**
	* Returns the last asset entry in the ordered set where layoutUuid = &#63;.
	*
	* @param layoutUuid the layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry fetchByLayoutUuid_Last(
		java.lang.String layoutUuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByLayoutUuid_Last(layoutUuid, orderByComparator);
	}

	/**
	* Returns the asset entries before and after the current asset entry in the ordered set where layoutUuid = &#63;.
	*
	* @param entryId the primary key of the current asset entry
	* @param layoutUuid the layout uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a asset entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry[] findByLayoutUuid_PrevAndNext(
		long entryId, java.lang.String layoutUuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence()
				   .findByLayoutUuid_PrevAndNext(entryId, layoutUuid,
			orderByComparator);
	}

	/**
	* Removes all the asset entries where layoutUuid = &#63; from the database.
	*
	* @param layoutUuid the layout uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByLayoutUuid(java.lang.String layoutUuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByLayoutUuid(layoutUuid);
	}

	/**
	* Returns the number of asset entries where layoutUuid = &#63;.
	*
	* @param layoutUuid the layout uuid
	* @return the number of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByLayoutUuid(java.lang.String layoutUuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByLayoutUuid(layoutUuid);
	}

	/**
	* Returns the asset entry where groupId = &#63; and classUuid = &#63; or throws a {@link com.liferay.portlet.asset.NoSuchEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classUuid the class uuid
	* @return the matching asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry findByG_CU(
		long groupId, java.lang.String classUuid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence().findByG_CU(groupId, classUuid);
	}

	/**
	* Returns the asset entry where groupId = &#63; and classUuid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classUuid the class uuid
	* @return the matching asset entry, or <code>null</code> if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry fetchByG_CU(
		long groupId, java.lang.String classUuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByG_CU(groupId, classUuid);
	}

	/**
	* Returns the asset entry where groupId = &#63; and classUuid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classUuid the class uuid
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching asset entry, or <code>null</code> if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry fetchByG_CU(
		long groupId, java.lang.String classUuid, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_CU(groupId, classUuid, retrieveFromCache);
	}

	/**
	* Removes the asset entry where groupId = &#63; and classUuid = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classUuid the class uuid
	* @return the asset entry that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry removeByG_CU(
		long groupId, java.lang.String classUuid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence().removeByG_CU(groupId, classUuid);
	}

	/**
	* Returns the number of asset entries where groupId = &#63; and classUuid = &#63;.
	*
	* @param groupId the group ID
	* @param classUuid the class uuid
	* @return the number of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_CU(long groupId, java.lang.String classUuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_CU(groupId, classUuid);
	}

	/**
	* Returns the asset entry where classNameId = &#63; and classPK = &#63; or throws a {@link com.liferay.portlet.asset.NoSuchEntryException} if it could not be found.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	* Returns the asset entry where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching asset entry, or <code>null</code> if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry fetchByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_C(classNameId, classPK);
	}

	/**
	* Returns the asset entry where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching asset entry, or <code>null</code> if a matching asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry fetchByC_C(
		long classNameId, long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_C(classNameId, classPK, retrieveFromCache);
	}

	/**
	* Removes the asset entry where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the asset entry that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry removeByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	* Returns the number of asset entries where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	* Caches the asset entry in the entity cache if it is enabled.
	*
	* @param assetEntry the asset entry
	*/
	public static void cacheResult(
		com.liferay.portlet.asset.model.AssetEntry assetEntry) {
		getPersistence().cacheResult(assetEntry);
	}

	/**
	* Caches the asset entries in the entity cache if it is enabled.
	*
	* @param assetEntries the asset entries
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.asset.model.AssetEntry> assetEntries) {
		getPersistence().cacheResult(assetEntries);
	}

	/**
	* Creates a new asset entry with the primary key. Does not add the asset entry to the database.
	*
	* @param entryId the primary key for the new asset entry
	* @return the new asset entry
	*/
	public static com.liferay.portlet.asset.model.AssetEntry create(
		long entryId) {
		return getPersistence().create(entryId);
	}

	/**
	* Removes the asset entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the asset entry
	* @return the asset entry that was removed
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a asset entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry remove(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence().remove(entryId);
	}

	public static com.liferay.portlet.asset.model.AssetEntry updateImpl(
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(assetEntry);
	}

	/**
	* Returns the asset entry with the primary key or throws a {@link com.liferay.portlet.asset.NoSuchEntryException} if it could not be found.
	*
	* @param entryId the primary key of the asset entry
	* @return the asset entry
	* @throws com.liferay.portlet.asset.NoSuchEntryException if a asset entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry findByPrimaryKey(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchEntryException {
		return getPersistence().findByPrimaryKey(entryId);
	}

	/**
	* Returns the asset entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param entryId the primary key of the asset entry
	* @return the asset entry, or <code>null</code> if a asset entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetEntry fetchByPrimaryKey(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(entryId);
	}

	/**
	* Returns all the asset entries.
	*
	* @return the asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the asset entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the asset entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the asset entries from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of asset entries.
	*
	* @return the number of asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	/**
	* Returns all the asset categories associated with the asset entry.
	*
	* @param pk the primary key of the asset entry
	* @return the asset categories associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getAssetCategories(pk);
	}

	/**
	* Returns a range of all the asset categories associated with the asset entry.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the asset entry
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of asset categories associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getAssetCategories(pk, start, end);
	}

	/**
	* Returns an ordered range of all the asset categories associated with the asset entry.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the asset entry
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset categories associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .getAssetCategories(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of asset categories associated with the asset entry.
	*
	* @param pk the primary key of the asset entry
	* @return the number of asset categories associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public static int getAssetCategoriesSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getAssetCategoriesSize(pk);
	}

	/**
	* Returns <code>true</code> if the asset category is associated with the asset entry.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategoryPK the primary key of the asset category
	* @return <code>true</code> if the asset category is associated with the asset entry; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public static boolean containsAssetCategory(long pk, long assetCategoryPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsAssetCategory(pk, assetCategoryPK);
	}

	/**
	* Returns <code>true</code> if the asset entry has any asset categories associated with it.
	*
	* @param pk the primary key of the asset entry to check for associations with asset categories
	* @return <code>true</code> if the asset entry has any asset categories associated with it; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public static boolean containsAssetCategories(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsAssetCategories(pk);
	}

	/**
	* Adds an association between the asset entry and the asset category. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategoryPK the primary key of the asset category
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetCategory(long pk, long assetCategoryPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addAssetCategory(pk, assetCategoryPK);
	}

	/**
	* Adds an association between the asset entry and the asset category. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategory the asset category
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetCategory(long pk,
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addAssetCategory(pk, assetCategory);
	}

	/**
	* Adds an association between the asset entry and the asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategoryPKs the primary keys of the asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetCategories(long pk, long[] assetCategoryPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addAssetCategories(pk, assetCategoryPKs);
	}

	/**
	* Adds an association between the asset entry and the asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategories the asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetCategories(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> assetCategories)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addAssetCategories(pk, assetCategories);
	}

	/**
	* Clears all associations between the asset entry and its asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry to clear the associated asset categories from
	* @throws SystemException if a system exception occurred
	*/
	public static void clearAssetCategories(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().clearAssetCategories(pk);
	}

	/**
	* Removes the association between the asset entry and the asset category. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategoryPK the primary key of the asset category
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAssetCategory(long pk, long assetCategoryPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAssetCategory(pk, assetCategoryPK);
	}

	/**
	* Removes the association between the asset entry and the asset category. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategory the asset category
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAssetCategory(long pk,
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAssetCategory(pk, assetCategory);
	}

	/**
	* Removes the association between the asset entry and the asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategoryPKs the primary keys of the asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAssetCategories(long pk, long[] assetCategoryPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAssetCategories(pk, assetCategoryPKs);
	}

	/**
	* Removes the association between the asset entry and the asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategories the asset categories
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAssetCategories(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> assetCategories)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAssetCategories(pk, assetCategories);
	}

	/**
	* Sets the asset categories associated with the asset entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategoryPKs the primary keys of the asset categories to be associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public static void setAssetCategories(long pk, long[] assetCategoryPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setAssetCategories(pk, assetCategoryPKs);
	}

	/**
	* Sets the asset categories associated with the asset entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetCategories the asset categories to be associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public static void setAssetCategories(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetCategory> assetCategories)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setAssetCategories(pk, assetCategories);
	}

	/**
	* Returns all the asset tags associated with the asset entry.
	*
	* @param pk the primary key of the asset entry
	* @return the asset tags associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		long pk) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getAssetTags(pk);
	}

	/**
	* Returns a range of all the asset tags associated with the asset entry.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the asset entry
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @return the range of asset tags associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getAssetTags(pk, start, end);
	}

	/**
	* Returns an ordered range of all the asset tags associated with the asset entry.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the asset entry
	* @param start the lower bound of the range of asset entries
	* @param end the upper bound of the range of asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset tags associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getAssetTags(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of asset tags associated with the asset entry.
	*
	* @param pk the primary key of the asset entry
	* @return the number of asset tags associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public static int getAssetTagsSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getAssetTagsSize(pk);
	}

	/**
	* Returns <code>true</code> if the asset tag is associated with the asset entry.
	*
	* @param pk the primary key of the asset entry
	* @param assetTagPK the primary key of the asset tag
	* @return <code>true</code> if the asset tag is associated with the asset entry; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public static boolean containsAssetTag(long pk, long assetTagPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsAssetTag(pk, assetTagPK);
	}

	/**
	* Returns <code>true</code> if the asset entry has any asset tags associated with it.
	*
	* @param pk the primary key of the asset entry to check for associations with asset tags
	* @return <code>true</code> if the asset entry has any asset tags associated with it; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public static boolean containsAssetTags(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsAssetTags(pk);
	}

	/**
	* Adds an association between the asset entry and the asset tag. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTagPK the primary key of the asset tag
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetTag(long pk, long assetTagPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addAssetTag(pk, assetTagPK);
	}

	/**
	* Adds an association between the asset entry and the asset tag. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTag the asset tag
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetTag(long pk,
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addAssetTag(pk, assetTag);
	}

	/**
	* Adds an association between the asset entry and the asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTagPKs the primary keys of the asset tags
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetTags(long pk, long[] assetTagPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addAssetTags(pk, assetTagPKs);
	}

	/**
	* Adds an association between the asset entry and the asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTags the asset tags
	* @throws SystemException if a system exception occurred
	*/
	public static void addAssetTags(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetTag> assetTags)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addAssetTags(pk, assetTags);
	}

	/**
	* Clears all associations between the asset entry and its asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry to clear the associated asset tags from
	* @throws SystemException if a system exception occurred
	*/
	public static void clearAssetTags(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().clearAssetTags(pk);
	}

	/**
	* Removes the association between the asset entry and the asset tag. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTagPK the primary key of the asset tag
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAssetTag(long pk, long assetTagPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAssetTag(pk, assetTagPK);
	}

	/**
	* Removes the association between the asset entry and the asset tag. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTag the asset tag
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAssetTag(long pk,
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAssetTag(pk, assetTag);
	}

	/**
	* Removes the association between the asset entry and the asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTagPKs the primary keys of the asset tags
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAssetTags(long pk, long[] assetTagPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAssetTags(pk, assetTagPKs);
	}

	/**
	* Removes the association between the asset entry and the asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTags the asset tags
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAssetTags(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetTag> assetTags)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAssetTags(pk, assetTags);
	}

	/**
	* Sets the asset tags associated with the asset entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTagPKs the primary keys of the asset tags to be associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public static void setAssetTags(long pk, long[] assetTagPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setAssetTags(pk, assetTagPKs);
	}

	/**
	* Sets the asset tags associated with the asset entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the asset entry
	* @param assetTags the asset tags to be associated with the asset entry
	* @throws SystemException if a system exception occurred
	*/
	public static void setAssetTags(long pk,
		java.util.List<com.liferay.portlet.asset.model.AssetTag> assetTags)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setAssetTags(pk, assetTags);
	}

	public static AssetEntryPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (AssetEntryPersistence)PortalBeanLocatorUtil.locate(AssetEntryPersistence.class.getName());

			ReferenceRegistry.registerReference(AssetEntryUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(AssetEntryPersistence persistence) {
	}

	private static AssetEntryPersistence _persistence;
}