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

import com.liferay.portlet.asset.model.AssetLink;

import java.util.List;

/**
 * The persistence utility for the asset link service. This utility wraps {@link AssetLinkPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetLinkPersistence
 * @see AssetLinkPersistenceImpl
 * @generated
 */
public class AssetLinkUtil {
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
	public static void clearCache(AssetLink assetLink) {
		getPersistence().clearCache(assetLink);
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
	public static List<AssetLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AssetLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static AssetLink update(AssetLink assetLink)
		throws SystemException {
		return getPersistence().update(assetLink);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static AssetLink update(AssetLink assetLink,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(assetLink, serviceContext);
	}

	/**
	* Returns all the asset links where entryId1 = &#63;.
	*
	* @param entryId1 the entry id1
	* @return the matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE1(
		long entryId1)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByE1(entryId1);
	}

	/**
	* Returns a range of all the asset links where entryId1 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId1 the entry id1
	* @param start the lower bound of the range of asset links
	* @param end the upper bound of the range of asset links (not inclusive)
	* @return the range of matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE1(
		long entryId1, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByE1(entryId1, start, end);
	}

	/**
	* Returns an ordered range of all the asset links where entryId1 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId1 the entry id1
	* @param start the lower bound of the range of asset links
	* @param end the upper bound of the range of asset links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE1(
		long entryId1, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByE1(entryId1, start, end, orderByComparator);
	}

	/**
	* Returns the first asset link in the ordered set where entryId1 = &#63;.
	*
	* @param entryId1 the entry id1
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetLink findByE1_First(
		long entryId1,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence().findByE1_First(entryId1, orderByComparator);
	}

	/**
	* Returns the first asset link in the ordered set where entryId1 = &#63;.
	*
	* @param entryId1 the entry id1
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset link, or <code>null</code> if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetLink fetchByE1_First(
		long entryId1,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByE1_First(entryId1, orderByComparator);
	}

	/**
	* Returns the last asset link in the ordered set where entryId1 = &#63;.
	*
	* @param entryId1 the entry id1
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetLink findByE1_Last(
		long entryId1,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence().findByE1_Last(entryId1, orderByComparator);
	}

	/**
	* Returns the last asset link in the ordered set where entryId1 = &#63;.
	*
	* @param entryId1 the entry id1
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset link, or <code>null</code> if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetLink fetchByE1_Last(
		long entryId1,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByE1_Last(entryId1, orderByComparator);
	}

	/**
	* Returns the asset links before and after the current asset link in the ordered set where entryId1 = &#63;.
	*
	* @param linkId the primary key of the current asset link
	* @param entryId1 the entry id1
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a asset link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetLink[] findByE1_PrevAndNext(
		long linkId, long entryId1,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence()
				   .findByE1_PrevAndNext(linkId, entryId1, orderByComparator);
	}

	/**
	* Removes all the asset links where entryId1 = &#63; from the database.
	*
	* @param entryId1 the entry id1
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByE1(long entryId1)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByE1(entryId1);
	}

	/**
	* Returns the number of asset links where entryId1 = &#63;.
	*
	* @param entryId1 the entry id1
	* @return the number of matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public static int countByE1(long entryId1)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByE1(entryId1);
	}

	/**
	* Returns all the asset links where entryId2 = &#63;.
	*
	* @param entryId2 the entry id2
	* @return the matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE2(
		long entryId2)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByE2(entryId2);
	}

	/**
	* Returns a range of all the asset links where entryId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId2 the entry id2
	* @param start the lower bound of the range of asset links
	* @param end the upper bound of the range of asset links (not inclusive)
	* @return the range of matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE2(
		long entryId2, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByE2(entryId2, start, end);
	}

	/**
	* Returns an ordered range of all the asset links where entryId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId2 the entry id2
	* @param start the lower bound of the range of asset links
	* @param end the upper bound of the range of asset links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE2(
		long entryId2, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByE2(entryId2, start, end, orderByComparator);
	}

	/**
	* Returns the first asset link in the ordered set where entryId2 = &#63;.
	*
	* @param entryId2 the entry id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetLink findByE2_First(
		long entryId2,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence().findByE2_First(entryId2, orderByComparator);
	}

	/**
	* Returns the first asset link in the ordered set where entryId2 = &#63;.
	*
	* @param entryId2 the entry id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset link, or <code>null</code> if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetLink fetchByE2_First(
		long entryId2,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByE2_First(entryId2, orderByComparator);
	}

	/**
	* Returns the last asset link in the ordered set where entryId2 = &#63;.
	*
	* @param entryId2 the entry id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetLink findByE2_Last(
		long entryId2,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence().findByE2_Last(entryId2, orderByComparator);
	}

	/**
	* Returns the last asset link in the ordered set where entryId2 = &#63;.
	*
	* @param entryId2 the entry id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset link, or <code>null</code> if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetLink fetchByE2_Last(
		long entryId2,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByE2_Last(entryId2, orderByComparator);
	}

	/**
	* Returns the asset links before and after the current asset link in the ordered set where entryId2 = &#63;.
	*
	* @param linkId the primary key of the current asset link
	* @param entryId2 the entry id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a asset link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetLink[] findByE2_PrevAndNext(
		long linkId, long entryId2,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence()
				   .findByE2_PrevAndNext(linkId, entryId2, orderByComparator);
	}

	/**
	* Removes all the asset links where entryId2 = &#63; from the database.
	*
	* @param entryId2 the entry id2
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByE2(long entryId2)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByE2(entryId2);
	}

	/**
	* Returns the number of asset links where entryId2 = &#63;.
	*
	* @param entryId2 the entry id2
	* @return the number of matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public static int countByE2(long entryId2)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByE2(entryId2);
	}

	/**
	* Returns all the asset links where entryId1 = &#63; and entryId2 = &#63;.
	*
	* @param entryId1 the entry id1
	* @param entryId2 the entry id2
	* @return the matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE_E(
		long entryId1, long entryId2)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByE_E(entryId1, entryId2);
	}

	/**
	* Returns a range of all the asset links where entryId1 = &#63; and entryId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId1 the entry id1
	* @param entryId2 the entry id2
	* @param start the lower bound of the range of asset links
	* @param end the upper bound of the range of asset links (not inclusive)
	* @return the range of matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE_E(
		long entryId1, long entryId2, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByE_E(entryId1, entryId2, start, end);
	}

	/**
	* Returns an ordered range of all the asset links where entryId1 = &#63; and entryId2 = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId1 the entry id1
	* @param entryId2 the entry id2
	* @param start the lower bound of the range of asset links
	* @param end the upper bound of the range of asset links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE_E(
		long entryId1, long entryId2, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByE_E(entryId1, entryId2, start, end, orderByComparator);
	}

	/**
	* Returns the first asset link in the ordered set where entryId1 = &#63; and entryId2 = &#63;.
	*
	* @param entryId1 the entry id1
	* @param entryId2 the entry id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetLink findByE_E_First(
		long entryId1, long entryId2,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence()
				   .findByE_E_First(entryId1, entryId2, orderByComparator);
	}

	/**
	* Returns the first asset link in the ordered set where entryId1 = &#63; and entryId2 = &#63;.
	*
	* @param entryId1 the entry id1
	* @param entryId2 the entry id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset link, or <code>null</code> if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetLink fetchByE_E_First(
		long entryId1, long entryId2,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByE_E_First(entryId1, entryId2, orderByComparator);
	}

	/**
	* Returns the last asset link in the ordered set where entryId1 = &#63; and entryId2 = &#63;.
	*
	* @param entryId1 the entry id1
	* @param entryId2 the entry id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetLink findByE_E_Last(
		long entryId1, long entryId2,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence()
				   .findByE_E_Last(entryId1, entryId2, orderByComparator);
	}

	/**
	* Returns the last asset link in the ordered set where entryId1 = &#63; and entryId2 = &#63;.
	*
	* @param entryId1 the entry id1
	* @param entryId2 the entry id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset link, or <code>null</code> if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetLink fetchByE_E_Last(
		long entryId1, long entryId2,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByE_E_Last(entryId1, entryId2, orderByComparator);
	}

	/**
	* Returns the asset links before and after the current asset link in the ordered set where entryId1 = &#63; and entryId2 = &#63;.
	*
	* @param linkId the primary key of the current asset link
	* @param entryId1 the entry id1
	* @param entryId2 the entry id2
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a asset link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetLink[] findByE_E_PrevAndNext(
		long linkId, long entryId1, long entryId2,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence()
				   .findByE_E_PrevAndNext(linkId, entryId1, entryId2,
			orderByComparator);
	}

	/**
	* Removes all the asset links where entryId1 = &#63; and entryId2 = &#63; from the database.
	*
	* @param entryId1 the entry id1
	* @param entryId2 the entry id2
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByE_E(long entryId1, long entryId2)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByE_E(entryId1, entryId2);
	}

	/**
	* Returns the number of asset links where entryId1 = &#63; and entryId2 = &#63;.
	*
	* @param entryId1 the entry id1
	* @param entryId2 the entry id2
	* @return the number of matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public static int countByE_E(long entryId1, long entryId2)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByE_E(entryId1, entryId2);
	}

	/**
	* Returns all the asset links where entryId1 = &#63; and type = &#63;.
	*
	* @param entryId1 the entry id1
	* @param type the type
	* @return the matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE1_T(
		long entryId1, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByE1_T(entryId1, type);
	}

	/**
	* Returns a range of all the asset links where entryId1 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId1 the entry id1
	* @param type the type
	* @param start the lower bound of the range of asset links
	* @param end the upper bound of the range of asset links (not inclusive)
	* @return the range of matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE1_T(
		long entryId1, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByE1_T(entryId1, type, start, end);
	}

	/**
	* Returns an ordered range of all the asset links where entryId1 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId1 the entry id1
	* @param type the type
	* @param start the lower bound of the range of asset links
	* @param end the upper bound of the range of asset links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE1_T(
		long entryId1, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByE1_T(entryId1, type, start, end, orderByComparator);
	}

	/**
	* Returns the first asset link in the ordered set where entryId1 = &#63; and type = &#63;.
	*
	* @param entryId1 the entry id1
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetLink findByE1_T_First(
		long entryId1, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence()
				   .findByE1_T_First(entryId1, type, orderByComparator);
	}

	/**
	* Returns the first asset link in the ordered set where entryId1 = &#63; and type = &#63;.
	*
	* @param entryId1 the entry id1
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset link, or <code>null</code> if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetLink fetchByE1_T_First(
		long entryId1, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByE1_T_First(entryId1, type, orderByComparator);
	}

	/**
	* Returns the last asset link in the ordered set where entryId1 = &#63; and type = &#63;.
	*
	* @param entryId1 the entry id1
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetLink findByE1_T_Last(
		long entryId1, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence()
				   .findByE1_T_Last(entryId1, type, orderByComparator);
	}

	/**
	* Returns the last asset link in the ordered set where entryId1 = &#63; and type = &#63;.
	*
	* @param entryId1 the entry id1
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset link, or <code>null</code> if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetLink fetchByE1_T_Last(
		long entryId1, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByE1_T_Last(entryId1, type, orderByComparator);
	}

	/**
	* Returns the asset links before and after the current asset link in the ordered set where entryId1 = &#63; and type = &#63;.
	*
	* @param linkId the primary key of the current asset link
	* @param entryId1 the entry id1
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a asset link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetLink[] findByE1_T_PrevAndNext(
		long linkId, long entryId1, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence()
				   .findByE1_T_PrevAndNext(linkId, entryId1, type,
			orderByComparator);
	}

	/**
	* Removes all the asset links where entryId1 = &#63; and type = &#63; from the database.
	*
	* @param entryId1 the entry id1
	* @param type the type
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByE1_T(long entryId1, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByE1_T(entryId1, type);
	}

	/**
	* Returns the number of asset links where entryId1 = &#63; and type = &#63;.
	*
	* @param entryId1 the entry id1
	* @param type the type
	* @return the number of matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public static int countByE1_T(long entryId1, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByE1_T(entryId1, type);
	}

	/**
	* Returns all the asset links where entryId2 = &#63; and type = &#63;.
	*
	* @param entryId2 the entry id2
	* @param type the type
	* @return the matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE2_T(
		long entryId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByE2_T(entryId2, type);
	}

	/**
	* Returns a range of all the asset links where entryId2 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId2 the entry id2
	* @param type the type
	* @param start the lower bound of the range of asset links
	* @param end the upper bound of the range of asset links (not inclusive)
	* @return the range of matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE2_T(
		long entryId2, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByE2_T(entryId2, type, start, end);
	}

	/**
	* Returns an ordered range of all the asset links where entryId2 = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId2 the entry id2
	* @param type the type
	* @param start the lower bound of the range of asset links
	* @param end the upper bound of the range of asset links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findByE2_T(
		long entryId2, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByE2_T(entryId2, type, start, end, orderByComparator);
	}

	/**
	* Returns the first asset link in the ordered set where entryId2 = &#63; and type = &#63;.
	*
	* @param entryId2 the entry id2
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetLink findByE2_T_First(
		long entryId2, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence()
				   .findByE2_T_First(entryId2, type, orderByComparator);
	}

	/**
	* Returns the first asset link in the ordered set where entryId2 = &#63; and type = &#63;.
	*
	* @param entryId2 the entry id2
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset link, or <code>null</code> if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetLink fetchByE2_T_First(
		long entryId2, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByE2_T_First(entryId2, type, orderByComparator);
	}

	/**
	* Returns the last asset link in the ordered set where entryId2 = &#63; and type = &#63;.
	*
	* @param entryId2 the entry id2
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetLink findByE2_T_Last(
		long entryId2, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence()
				   .findByE2_T_Last(entryId2, type, orderByComparator);
	}

	/**
	* Returns the last asset link in the ordered set where entryId2 = &#63; and type = &#63;.
	*
	* @param entryId2 the entry id2
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset link, or <code>null</code> if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetLink fetchByE2_T_Last(
		long entryId2, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByE2_T_Last(entryId2, type, orderByComparator);
	}

	/**
	* Returns the asset links before and after the current asset link in the ordered set where entryId2 = &#63; and type = &#63;.
	*
	* @param linkId the primary key of the current asset link
	* @param entryId2 the entry id2
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a asset link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetLink[] findByE2_T_PrevAndNext(
		long linkId, long entryId2, int type,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence()
				   .findByE2_T_PrevAndNext(linkId, entryId2, type,
			orderByComparator);
	}

	/**
	* Removes all the asset links where entryId2 = &#63; and type = &#63; from the database.
	*
	* @param entryId2 the entry id2
	* @param type the type
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByE2_T(long entryId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByE2_T(entryId2, type);
	}

	/**
	* Returns the number of asset links where entryId2 = &#63; and type = &#63;.
	*
	* @param entryId2 the entry id2
	* @param type the type
	* @return the number of matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public static int countByE2_T(long entryId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByE2_T(entryId2, type);
	}

	/**
	* Returns the asset link where entryId1 = &#63; and entryId2 = &#63; and type = &#63; or throws a {@link com.liferay.portlet.asset.NoSuchLinkException} if it could not be found.
	*
	* @param entryId1 the entry id1
	* @param entryId2 the entry id2
	* @param type the type
	* @return the matching asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetLink findByE_E_T(
		long entryId1, long entryId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence().findByE_E_T(entryId1, entryId2, type);
	}

	/**
	* Returns the asset link where entryId1 = &#63; and entryId2 = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param entryId1 the entry id1
	* @param entryId2 the entry id2
	* @param type the type
	* @return the matching asset link, or <code>null</code> if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetLink fetchByE_E_T(
		long entryId1, long entryId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByE_E_T(entryId1, entryId2, type);
	}

	/**
	* Returns the asset link where entryId1 = &#63; and entryId2 = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param entryId1 the entry id1
	* @param entryId2 the entry id2
	* @param type the type
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching asset link, or <code>null</code> if a matching asset link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetLink fetchByE_E_T(
		long entryId1, long entryId2, int type, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByE_E_T(entryId1, entryId2, type, retrieveFromCache);
	}

	/**
	* Removes the asset link where entryId1 = &#63; and entryId2 = &#63; and type = &#63; from the database.
	*
	* @param entryId1 the entry id1
	* @param entryId2 the entry id2
	* @param type the type
	* @return the asset link that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetLink removeByE_E_T(
		long entryId1, long entryId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence().removeByE_E_T(entryId1, entryId2, type);
	}

	/**
	* Returns the number of asset links where entryId1 = &#63; and entryId2 = &#63; and type = &#63;.
	*
	* @param entryId1 the entry id1
	* @param entryId2 the entry id2
	* @param type the type
	* @return the number of matching asset links
	* @throws SystemException if a system exception occurred
	*/
	public static int countByE_E_T(long entryId1, long entryId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByE_E_T(entryId1, entryId2, type);
	}

	/**
	* Caches the asset link in the entity cache if it is enabled.
	*
	* @param assetLink the asset link
	*/
	public static void cacheResult(
		com.liferay.portlet.asset.model.AssetLink assetLink) {
		getPersistence().cacheResult(assetLink);
	}

	/**
	* Caches the asset links in the entity cache if it is enabled.
	*
	* @param assetLinks the asset links
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.asset.model.AssetLink> assetLinks) {
		getPersistence().cacheResult(assetLinks);
	}

	/**
	* Creates a new asset link with the primary key. Does not add the asset link to the database.
	*
	* @param linkId the primary key for the new asset link
	* @return the new asset link
	*/
	public static com.liferay.portlet.asset.model.AssetLink create(long linkId) {
		return getPersistence().create(linkId);
	}

	/**
	* Removes the asset link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param linkId the primary key of the asset link
	* @return the asset link that was removed
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a asset link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetLink remove(long linkId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence().remove(linkId);
	}

	public static com.liferay.portlet.asset.model.AssetLink updateImpl(
		com.liferay.portlet.asset.model.AssetLink assetLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(assetLink);
	}

	/**
	* Returns the asset link with the primary key or throws a {@link com.liferay.portlet.asset.NoSuchLinkException} if it could not be found.
	*
	* @param linkId the primary key of the asset link
	* @return the asset link
	* @throws com.liferay.portlet.asset.NoSuchLinkException if a asset link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetLink findByPrimaryKey(
		long linkId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.asset.NoSuchLinkException {
		return getPersistence().findByPrimaryKey(linkId);
	}

	/**
	* Returns the asset link with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param linkId the primary key of the asset link
	* @return the asset link, or <code>null</code> if a asset link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetLink fetchByPrimaryKey(
		long linkId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(linkId);
	}

	/**
	* Returns all the asset links.
	*
	* @return the asset links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the asset links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset links
	* @param end the upper bound of the range of asset links (not inclusive)
	* @return the range of asset links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the asset links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset links
	* @param end the upper bound of the range of asset links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetLink> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the asset links from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of asset links.
	*
	* @return the number of asset links
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static AssetLinkPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (AssetLinkPersistence)PortalBeanLocatorUtil.locate(AssetLinkPersistence.class.getName());

			ReferenceRegistry.registerReference(AssetLinkUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(AssetLinkPersistence persistence) {
	}

	private static AssetLinkPersistence _persistence;
}