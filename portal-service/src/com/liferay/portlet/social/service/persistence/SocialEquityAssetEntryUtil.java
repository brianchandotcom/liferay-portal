/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.social.model.SocialEquityAssetEntry;

import java.util.List;

/**
 * The persistence utility for the social equity asset entry service. This utility wraps {@link SocialEquityAssetEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SocialEquityAssetEntryPersistence
 * @see SocialEquityAssetEntryPersistenceImpl
 * @generated
 */
public class SocialEquityAssetEntryUtil {
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
	public static void clearCache(SocialEquityAssetEntry socialEquityAssetEntry) {
		getPersistence().clearCache(socialEquityAssetEntry);
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
	public static List<SocialEquityAssetEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SocialEquityAssetEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SocialEquityAssetEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static SocialEquityAssetEntry remove(
		SocialEquityAssetEntry socialEquityAssetEntry)
		throws SystemException {
		return getPersistence().remove(socialEquityAssetEntry);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static SocialEquityAssetEntry update(
		SocialEquityAssetEntry socialEquityAssetEntry, boolean merge)
		throws SystemException {
		return getPersistence().update(socialEquityAssetEntry, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static SocialEquityAssetEntry update(
		SocialEquityAssetEntry socialEquityAssetEntry, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence()
				   .update(socialEquityAssetEntry, merge, serviceContext);
	}

	/**
	* Caches the social equity asset entry in the entity cache if it is enabled.
	*
	* @param socialEquityAssetEntry the social equity asset entry
	*/
	public static void cacheResult(
		com.liferay.portlet.social.model.SocialEquityAssetEntry socialEquityAssetEntry) {
		getPersistence().cacheResult(socialEquityAssetEntry);
	}

	/**
	* Caches the social equity asset entries in the entity cache if it is enabled.
	*
	* @param socialEquityAssetEntries the social equity asset entries
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.social.model.SocialEquityAssetEntry> socialEquityAssetEntries) {
		getPersistence().cacheResult(socialEquityAssetEntries);
	}

	/**
	* Creates a new social equity asset entry with the primary key. Does not add the social equity asset entry to the database.
	*
	* @param equityAssetEntryId the primary key for the new social equity asset entry
	* @return the new social equity asset entry
	*/
	public static com.liferay.portlet.social.model.SocialEquityAssetEntry create(
		long equityAssetEntryId) {
		return getPersistence().create(equityAssetEntryId);
	}

	/**
	* Removes the social equity asset entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param equityAssetEntryId the primary key of the social equity asset entry
	* @return the social equity asset entry that was removed
	* @throws com.liferay.portlet.social.NoSuchEquityAssetEntryException if a social equity asset entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityAssetEntry remove(
		long equityAssetEntryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityAssetEntryException {
		return getPersistence().remove(equityAssetEntryId);
	}

	public static com.liferay.portlet.social.model.SocialEquityAssetEntry updateImpl(
		com.liferay.portlet.social.model.SocialEquityAssetEntry socialEquityAssetEntry,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(socialEquityAssetEntry, merge);
	}

	/**
	* Returns the social equity asset entry with the primary key or throws a {@link com.liferay.portlet.social.NoSuchEquityAssetEntryException} if it could not be found.
	*
	* @param equityAssetEntryId the primary key of the social equity asset entry
	* @return the social equity asset entry
	* @throws com.liferay.portlet.social.NoSuchEquityAssetEntryException if a social equity asset entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityAssetEntry findByPrimaryKey(
		long equityAssetEntryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityAssetEntryException {
		return getPersistence().findByPrimaryKey(equityAssetEntryId);
	}

	/**
	* Returns the social equity asset entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param equityAssetEntryId the primary key of the social equity asset entry
	* @return the social equity asset entry, or <code>null</code> if a social equity asset entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityAssetEntry fetchByPrimaryKey(
		long equityAssetEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(equityAssetEntryId);
	}

	/**
	* Returns the social equity asset entry where assetEntryId = &#63; or throws a {@link com.liferay.portlet.social.NoSuchEquityAssetEntryException} if it could not be found.
	*
	* @param assetEntryId the asset entry ID
	* @return the matching social equity asset entry
	* @throws com.liferay.portlet.social.NoSuchEquityAssetEntryException if a matching social equity asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityAssetEntry findByAssetEntryId(
		long assetEntryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityAssetEntryException {
		return getPersistence().findByAssetEntryId(assetEntryId);
	}

	/**
	* Returns the social equity asset entry where assetEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param assetEntryId the asset entry ID
	* @return the matching social equity asset entry, or <code>null</code> if a matching social equity asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityAssetEntry fetchByAssetEntryId(
		long assetEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByAssetEntryId(assetEntryId);
	}

	/**
	* Returns the social equity asset entry where assetEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param assetEntryId the asset entry ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching social equity asset entry, or <code>null</code> if a matching social equity asset entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.social.model.SocialEquityAssetEntry fetchByAssetEntryId(
		long assetEntryId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByAssetEntryId(assetEntryId, retrieveFromCache);
	}

	/**
	* Returns all the social equity asset entries.
	*
	* @return the social equity asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityAssetEntry> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the social equity asset entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social equity asset entries
	* @param end the upper bound of the range of social equity asset entries (not inclusive)
	* @return the range of social equity asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityAssetEntry> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the social equity asset entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social equity asset entries
	* @param end the upper bound of the range of social equity asset entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of social equity asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.social.model.SocialEquityAssetEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes the social equity asset entry where assetEntryId = &#63; from the database.
	*
	* @param assetEntryId the asset entry ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByAssetEntryId(long assetEntryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.social.NoSuchEquityAssetEntryException {
		getPersistence().removeByAssetEntryId(assetEntryId);
	}

	/**
	* Removes all the social equity asset entries from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of social equity asset entries where assetEntryId = &#63;.
	*
	* @param assetEntryId the asset entry ID
	* @return the number of matching social equity asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByAssetEntryId(long assetEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByAssetEntryId(assetEntryId);
	}

	/**
	* Returns the number of social equity asset entries.
	*
	* @return the number of social equity asset entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static SocialEquityAssetEntryPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SocialEquityAssetEntryPersistence)PortalBeanLocatorUtil.locate(SocialEquityAssetEntryPersistence.class.getName());

			ReferenceRegistry.registerReference(SocialEquityAssetEntryUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(SocialEquityAssetEntryPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(SocialEquityAssetEntryUtil.class,
			"_persistence");
	}

	private static SocialEquityAssetEntryPersistence _persistence;
}