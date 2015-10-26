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

package com.liferay.portlet.softwarecatalog.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.softwarecatalog.model.SCProductVersion;

import java.util.List;

/**
 * The persistence utility for the s c product version service. This utility wraps {@link com.liferay.portlet.softwarecatalog.service.persistence.impl.SCProductVersionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SCProductVersionPersistence
 * @see com.liferay.portlet.softwarecatalog.service.persistence.impl.SCProductVersionPersistenceImpl
 * @generated
 */
@ProviderType
public class SCProductVersionUtil {
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
	public static void clearCache(SCProductVersion scProductVersion) {
		getPersistence().clearCache(scProductVersion);
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
	public static List<SCProductVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SCProductVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SCProductVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SCProductVersion> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static SCProductVersion update(SCProductVersion scProductVersion) {
		return getPersistence().update(scProductVersion);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static SCProductVersion update(SCProductVersion scProductVersion,
		ServiceContext serviceContext) {
		return getPersistence().update(scProductVersion, serviceContext);
	}

	/**
	* Returns all the s c product versions where productEntryId = &#63;.
	*
	* @param productEntryId the product entry ID
	* @return the matching s c product versions
	*/
	public static List<SCProductVersion> findByProductEntryId(
		long productEntryId) {
		return getPersistence().findByProductEntryId(productEntryId);
	}

	/**
	* Returns a range of all the s c product versions where productEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param productEntryId the product entry ID
	* @param start the lower bound of the range of s c product versions
	* @param end the upper bound of the range of s c product versions (not inclusive)
	* @return the range of matching s c product versions
	*/
	public static List<SCProductVersion> findByProductEntryId(
		long productEntryId, int start, int end) {
		return getPersistence().findByProductEntryId(productEntryId, start, end);
	}

	/**
	* Returns an ordered range of all the s c product versions where productEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param productEntryId the product entry ID
	* @param start the lower bound of the range of s c product versions
	* @param end the upper bound of the range of s c product versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c product versions
	*/
	public static List<SCProductVersion> findByProductEntryId(
		long productEntryId, int start, int end,
		OrderByComparator<SCProductVersion> orderByComparator) {
		return getPersistence()
				   .findByProductEntryId(productEntryId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the s c product versions where productEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param productEntryId the product entry ID
	* @param start the lower bound of the range of s c product versions
	* @param end the upper bound of the range of s c product versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching s c product versions
	*/
	public static List<SCProductVersion> findByProductEntryId(
		long productEntryId, int start, int end,
		OrderByComparator<SCProductVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByProductEntryId(productEntryId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first s c product version in the ordered set where productEntryId = &#63;.
	*
	* @param productEntryId the product entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c product version
	* @throws NoSuchProductVersionException if a matching s c product version could not be found
	*/
	public static SCProductVersion findByProductEntryId_First(
		long productEntryId,
		OrderByComparator<SCProductVersion> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductVersionException {
		return getPersistence()
				   .findByProductEntryId_First(productEntryId, orderByComparator);
	}

	/**
	* Returns the first s c product version in the ordered set where productEntryId = &#63;.
	*
	* @param productEntryId the product entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c product version, or <code>null</code> if a matching s c product version could not be found
	*/
	public static SCProductVersion fetchByProductEntryId_First(
		long productEntryId,
		OrderByComparator<SCProductVersion> orderByComparator) {
		return getPersistence()
				   .fetchByProductEntryId_First(productEntryId,
			orderByComparator);
	}

	/**
	* Returns the last s c product version in the ordered set where productEntryId = &#63;.
	*
	* @param productEntryId the product entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c product version
	* @throws NoSuchProductVersionException if a matching s c product version could not be found
	*/
	public static SCProductVersion findByProductEntryId_Last(
		long productEntryId,
		OrderByComparator<SCProductVersion> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductVersionException {
		return getPersistence()
				   .findByProductEntryId_Last(productEntryId, orderByComparator);
	}

	/**
	* Returns the last s c product version in the ordered set where productEntryId = &#63;.
	*
	* @param productEntryId the product entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c product version, or <code>null</code> if a matching s c product version could not be found
	*/
	public static SCProductVersion fetchByProductEntryId_Last(
		long productEntryId,
		OrderByComparator<SCProductVersion> orderByComparator) {
		return getPersistence()
				   .fetchByProductEntryId_Last(productEntryId, orderByComparator);
	}

	/**
	* Returns the s c product versions before and after the current s c product version in the ordered set where productEntryId = &#63;.
	*
	* @param productVersionId the primary key of the current s c product version
	* @param productEntryId the product entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c product version
	* @throws NoSuchProductVersionException if a s c product version with the primary key could not be found
	*/
	public static SCProductVersion[] findByProductEntryId_PrevAndNext(
		long productVersionId, long productEntryId,
		OrderByComparator<SCProductVersion> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductVersionException {
		return getPersistence()
				   .findByProductEntryId_PrevAndNext(productVersionId,
			productEntryId, orderByComparator);
	}

	/**
	* Removes all the s c product versions where productEntryId = &#63; from the database.
	*
	* @param productEntryId the product entry ID
	*/
	public static void removeByProductEntryId(long productEntryId) {
		getPersistence().removeByProductEntryId(productEntryId);
	}

	/**
	* Returns the number of s c product versions where productEntryId = &#63;.
	*
	* @param productEntryId the product entry ID
	* @return the number of matching s c product versions
	*/
	public static int countByProductEntryId(long productEntryId) {
		return getPersistence().countByProductEntryId(productEntryId);
	}

	/**
	* Returns the s c product version where directDownloadURL = &#63; or throws a {@link NoSuchProductVersionException} if it could not be found.
	*
	* @param directDownloadURL the direct download u r l
	* @return the matching s c product version
	* @throws NoSuchProductVersionException if a matching s c product version could not be found
	*/
	public static SCProductVersion findByDirectDownloadURL(
		java.lang.String directDownloadURL)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductVersionException {
		return getPersistence().findByDirectDownloadURL(directDownloadURL);
	}

	/**
	* Returns the s c product version where directDownloadURL = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param directDownloadURL the direct download u r l
	* @return the matching s c product version, or <code>null</code> if a matching s c product version could not be found
	*/
	public static SCProductVersion fetchByDirectDownloadURL(
		java.lang.String directDownloadURL) {
		return getPersistence().fetchByDirectDownloadURL(directDownloadURL);
	}

	/**
	* Returns the s c product version where directDownloadURL = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param directDownloadURL the direct download u r l
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching s c product version, or <code>null</code> if a matching s c product version could not be found
	*/
	public static SCProductVersion fetchByDirectDownloadURL(
		java.lang.String directDownloadURL, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByDirectDownloadURL(directDownloadURL,
			retrieveFromCache);
	}

	/**
	* Removes the s c product version where directDownloadURL = &#63; from the database.
	*
	* @param directDownloadURL the direct download u r l
	* @return the s c product version that was removed
	*/
	public static SCProductVersion removeByDirectDownloadURL(
		java.lang.String directDownloadURL)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductVersionException {
		return getPersistence().removeByDirectDownloadURL(directDownloadURL);
	}

	/**
	* Returns the number of s c product versions where directDownloadURL = &#63;.
	*
	* @param directDownloadURL the direct download u r l
	* @return the number of matching s c product versions
	*/
	public static int countByDirectDownloadURL(
		java.lang.String directDownloadURL) {
		return getPersistence().countByDirectDownloadURL(directDownloadURL);
	}

	/**
	* Caches the s c product version in the entity cache if it is enabled.
	*
	* @param scProductVersion the s c product version
	*/
	public static void cacheResult(SCProductVersion scProductVersion) {
		getPersistence().cacheResult(scProductVersion);
	}

	/**
	* Caches the s c product versions in the entity cache if it is enabled.
	*
	* @param scProductVersions the s c product versions
	*/
	public static void cacheResult(List<SCProductVersion> scProductVersions) {
		getPersistence().cacheResult(scProductVersions);
	}

	/**
	* Creates a new s c product version with the primary key. Does not add the s c product version to the database.
	*
	* @param productVersionId the primary key for the new s c product version
	* @return the new s c product version
	*/
	public static SCProductVersion create(long productVersionId) {
		return getPersistence().create(productVersionId);
	}

	/**
	* Removes the s c product version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param productVersionId the primary key of the s c product version
	* @return the s c product version that was removed
	* @throws NoSuchProductVersionException if a s c product version with the primary key could not be found
	*/
	public static SCProductVersion remove(long productVersionId)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductVersionException {
		return getPersistence().remove(productVersionId);
	}

	public static SCProductVersion updateImpl(SCProductVersion scProductVersion) {
		return getPersistence().updateImpl(scProductVersion);
	}

	/**
	* Returns the s c product version with the primary key or throws a {@link NoSuchProductVersionException} if it could not be found.
	*
	* @param productVersionId the primary key of the s c product version
	* @return the s c product version
	* @throws NoSuchProductVersionException if a s c product version with the primary key could not be found
	*/
	public static SCProductVersion findByPrimaryKey(long productVersionId)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductVersionException {
		return getPersistence().findByPrimaryKey(productVersionId);
	}

	/**
	* Returns the s c product version with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param productVersionId the primary key of the s c product version
	* @return the s c product version, or <code>null</code> if a s c product version with the primary key could not be found
	*/
	public static SCProductVersion fetchByPrimaryKey(long productVersionId) {
		return getPersistence().fetchByPrimaryKey(productVersionId);
	}

	public static java.util.Map<java.io.Serializable, SCProductVersion> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the s c product versions.
	*
	* @return the s c product versions
	*/
	public static List<SCProductVersion> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the s c product versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s c product versions
	* @param end the upper bound of the range of s c product versions (not inclusive)
	* @return the range of s c product versions
	*/
	public static List<SCProductVersion> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the s c product versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s c product versions
	* @param end the upper bound of the range of s c product versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of s c product versions
	*/
	public static List<SCProductVersion> findAll(int start, int end,
		OrderByComparator<SCProductVersion> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the s c product versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s c product versions
	* @param end the upper bound of the range of s c product versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of s c product versions
	*/
	public static List<SCProductVersion> findAll(int start, int end,
		OrderByComparator<SCProductVersion> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the s c product versions from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of s c product versions.
	*
	* @return the number of s c product versions
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	/**
	* Returns the primaryKeys of s c framework versions associated with the s c product version.
	*
	* @param pk the primary key of the s c product version
	* @return long[] of the primaryKeys of s c framework versions associated with the s c product version
	*/
	public static long[] getSCFrameworkVersionPrimaryKeys(long pk) {
		return getPersistence().getSCFrameworkVersionPrimaryKeys(pk);
	}

	/**
	* Returns all the s c framework versions associated with the s c product version.
	*
	* @param pk the primary key of the s c product version
	* @return the s c framework versions associated with the s c product version
	*/
	public static List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		long pk) {
		return getPersistence().getSCFrameworkVersions(pk);
	}

	/**
	* Returns a range of all the s c framework versions associated with the s c product version.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the s c product version
	* @param start the lower bound of the range of s c product versions
	* @param end the upper bound of the range of s c product versions (not inclusive)
	* @return the range of s c framework versions associated with the s c product version
	*/
	public static List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		long pk, int start, int end) {
		return getPersistence().getSCFrameworkVersions(pk, start, end);
	}

	/**
	* Returns an ordered range of all the s c framework versions associated with the s c product version.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the s c product version
	* @param start the lower bound of the range of s c product versions
	* @param end the upper bound of the range of s c product versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of s c framework versions associated with the s c product version
	*/
	public static List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getSCFrameworkVersions(
		long pk, int start, int end,
		OrderByComparator<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> orderByComparator) {
		return getPersistence()
				   .getSCFrameworkVersions(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of s c framework versions associated with the s c product version.
	*
	* @param pk the primary key of the s c product version
	* @return the number of s c framework versions associated with the s c product version
	*/
	public static int getSCFrameworkVersionsSize(long pk) {
		return getPersistence().getSCFrameworkVersionsSize(pk);
	}

	/**
	* Returns <code>true</code> if the s c framework version is associated with the s c product version.
	*
	* @param pk the primary key of the s c product version
	* @param scFrameworkVersionPK the primary key of the s c framework version
	* @return <code>true</code> if the s c framework version is associated with the s c product version; <code>false</code> otherwise
	*/
	public static boolean containsSCFrameworkVersion(long pk,
		long scFrameworkVersionPK) {
		return getPersistence()
				   .containsSCFrameworkVersion(pk, scFrameworkVersionPK);
	}

	/**
	* Returns <code>true</code> if the s c product version has any s c framework versions associated with it.
	*
	* @param pk the primary key of the s c product version to check for associations with s c framework versions
	* @return <code>true</code> if the s c product version has any s c framework versions associated with it; <code>false</code> otherwise
	*/
	public static boolean containsSCFrameworkVersions(long pk) {
		return getPersistence().containsSCFrameworkVersions(pk);
	}

	/**
	* Adds an association between the s c product version and the s c framework version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product version
	* @param scFrameworkVersionPK the primary key of the s c framework version
	*/
	public static void addSCFrameworkVersion(long pk, long scFrameworkVersionPK) {
		getPersistence().addSCFrameworkVersion(pk, scFrameworkVersionPK);
	}

	/**
	* Adds an association between the s c product version and the s c framework version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product version
	* @param scFrameworkVersion the s c framework version
	*/
	public static void addSCFrameworkVersion(long pk,
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion) {
		getPersistence().addSCFrameworkVersion(pk, scFrameworkVersion);
	}

	/**
	* Adds an association between the s c product version and the s c framework versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product version
	* @param scFrameworkVersionPKs the primary keys of the s c framework versions
	*/
	public static void addSCFrameworkVersions(long pk,
		long[] scFrameworkVersionPKs) {
		getPersistence().addSCFrameworkVersions(pk, scFrameworkVersionPKs);
	}

	/**
	* Adds an association between the s c product version and the s c framework versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product version
	* @param scFrameworkVersions the s c framework versions
	*/
	public static void addSCFrameworkVersions(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions) {
		getPersistence().addSCFrameworkVersions(pk, scFrameworkVersions);
	}

	/**
	* Clears all associations between the s c product version and its s c framework versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product version to clear the associated s c framework versions from
	*/
	public static void clearSCFrameworkVersions(long pk) {
		getPersistence().clearSCFrameworkVersions(pk);
	}

	/**
	* Removes the association between the s c product version and the s c framework version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product version
	* @param scFrameworkVersionPK the primary key of the s c framework version
	*/
	public static void removeSCFrameworkVersion(long pk,
		long scFrameworkVersionPK) {
		getPersistence().removeSCFrameworkVersion(pk, scFrameworkVersionPK);
	}

	/**
	* Removes the association between the s c product version and the s c framework version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product version
	* @param scFrameworkVersion the s c framework version
	*/
	public static void removeSCFrameworkVersion(long pk,
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion) {
		getPersistence().removeSCFrameworkVersion(pk, scFrameworkVersion);
	}

	/**
	* Removes the association between the s c product version and the s c framework versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product version
	* @param scFrameworkVersionPKs the primary keys of the s c framework versions
	*/
	public static void removeSCFrameworkVersions(long pk,
		long[] scFrameworkVersionPKs) {
		getPersistence().removeSCFrameworkVersions(pk, scFrameworkVersionPKs);
	}

	/**
	* Removes the association between the s c product version and the s c framework versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product version
	* @param scFrameworkVersions the s c framework versions
	*/
	public static void removeSCFrameworkVersions(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions) {
		getPersistence().removeSCFrameworkVersions(pk, scFrameworkVersions);
	}

	/**
	* Sets the s c framework versions associated with the s c product version, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product version
	* @param scFrameworkVersionPKs the primary keys of the s c framework versions to be associated with the s c product version
	*/
	public static void setSCFrameworkVersions(long pk,
		long[] scFrameworkVersionPKs) {
		getPersistence().setSCFrameworkVersions(pk, scFrameworkVersionPKs);
	}

	/**
	* Sets the s c framework versions associated with the s c product version, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c product version
	* @param scFrameworkVersions the s c framework versions to be associated with the s c product version
	*/
	public static void setSCFrameworkVersions(long pk,
		List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions) {
		getPersistence().setSCFrameworkVersions(pk, scFrameworkVersions);
	}

	public static SCProductVersionPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SCProductVersionPersistence)PortalBeanLocatorUtil.locate(SCProductVersionPersistence.class.getName());

			ReferenceRegistry.registerReference(SCProductVersionUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(SCProductVersionPersistence persistence) {
	}

	private static SCProductVersionPersistence _persistence;
}