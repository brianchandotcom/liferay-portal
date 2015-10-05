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

import com.liferay.portlet.softwarecatalog.model.SCProductScreenshot;

import java.util.List;

/**
 * The persistence utility for the s c product screenshot service. This utility wraps {@link com.liferay.portlet.softwarecatalog.service.persistence.impl.SCProductScreenshotPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SCProductScreenshotPersistence
 * @see com.liferay.portlet.softwarecatalog.service.persistence.impl.SCProductScreenshotPersistenceImpl
 * @generated
 */
@ProviderType
public class SCProductScreenshotUtil {
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
	public static void clearCache(SCProductScreenshot scProductScreenshot) {
		getPersistence().clearCache(scProductScreenshot);
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
	public static List<SCProductScreenshot> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SCProductScreenshot> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SCProductScreenshot> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<SCProductScreenshot> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static SCProductScreenshot update(
		SCProductScreenshot scProductScreenshot) {
		return getPersistence().update(scProductScreenshot);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static SCProductScreenshot update(
		SCProductScreenshot scProductScreenshot, ServiceContext serviceContext) {
		return getPersistence().update(scProductScreenshot, serviceContext);
	}

	/**
	* Returns all the s c product screenshots where productEntryId = &#63;.
	*
	* @param productEntryId the product entry ID
	* @return the matching s c product screenshots
	*/
	public static List<SCProductScreenshot> findByProductEntryId(
		long productEntryId) {
		return getPersistence().findByProductEntryId(productEntryId);
	}

	/**
	* Returns a range of all the s c product screenshots where productEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductScreenshotModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param productEntryId the product entry ID
	* @param start the lower bound of the range of s c product screenshots
	* @param end the upper bound of the range of s c product screenshots (not inclusive)
	* @return the range of matching s c product screenshots
	*/
	public static List<SCProductScreenshot> findByProductEntryId(
		long productEntryId, int start, int end) {
		return getPersistence().findByProductEntryId(productEntryId, start, end);
	}

	/**
	* Returns an ordered range of all the s c product screenshots where productEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductScreenshotModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param productEntryId the product entry ID
	* @param start the lower bound of the range of s c product screenshots
	* @param end the upper bound of the range of s c product screenshots (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c product screenshots
	*/
	public static List<SCProductScreenshot> findByProductEntryId(
		long productEntryId, int start, int end,
		OrderByComparator<SCProductScreenshot> orderByComparator) {
		return getPersistence()
				   .findByProductEntryId(productEntryId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the s c product screenshots where productEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductScreenshotModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param productEntryId the product entry ID
	* @param start the lower bound of the range of s c product screenshots
	* @param end the upper bound of the range of s c product screenshots (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to use the finder cache
	* @return the ordered range of matching s c product screenshots
	*/
	public static List<SCProductScreenshot> findByProductEntryId(
		long productEntryId, int start, int end,
		OrderByComparator<SCProductScreenshot> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByProductEntryId(productEntryId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first s c product screenshot in the ordered set where productEntryId = &#63;.
	*
	* @param productEntryId the product entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c product screenshot
	* @throws NoSuchProductScreenshotException if a matching s c product screenshot could not be found
	*/
	public static SCProductScreenshot findByProductEntryId_First(
		long productEntryId,
		OrderByComparator<SCProductScreenshot> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException {
		return getPersistence()
				   .findByProductEntryId_First(productEntryId, orderByComparator);
	}

	/**
	* Returns the first s c product screenshot in the ordered set where productEntryId = &#63;.
	*
	* @param productEntryId the product entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c product screenshot, or <code>null</code> if a matching s c product screenshot could not be found
	*/
	public static SCProductScreenshot fetchByProductEntryId_First(
		long productEntryId,
		OrderByComparator<SCProductScreenshot> orderByComparator) {
		return getPersistence()
				   .fetchByProductEntryId_First(productEntryId,
			orderByComparator);
	}

	/**
	* Returns the last s c product screenshot in the ordered set where productEntryId = &#63;.
	*
	* @param productEntryId the product entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c product screenshot
	* @throws NoSuchProductScreenshotException if a matching s c product screenshot could not be found
	*/
	public static SCProductScreenshot findByProductEntryId_Last(
		long productEntryId,
		OrderByComparator<SCProductScreenshot> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException {
		return getPersistence()
				   .findByProductEntryId_Last(productEntryId, orderByComparator);
	}

	/**
	* Returns the last s c product screenshot in the ordered set where productEntryId = &#63;.
	*
	* @param productEntryId the product entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c product screenshot, or <code>null</code> if a matching s c product screenshot could not be found
	*/
	public static SCProductScreenshot fetchByProductEntryId_Last(
		long productEntryId,
		OrderByComparator<SCProductScreenshot> orderByComparator) {
		return getPersistence()
				   .fetchByProductEntryId_Last(productEntryId, orderByComparator);
	}

	/**
	* Returns the s c product screenshots before and after the current s c product screenshot in the ordered set where productEntryId = &#63;.
	*
	* @param productScreenshotId the primary key of the current s c product screenshot
	* @param productEntryId the product entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c product screenshot
	* @throws NoSuchProductScreenshotException if a s c product screenshot with the primary key could not be found
	*/
	public static SCProductScreenshot[] findByProductEntryId_PrevAndNext(
		long productScreenshotId, long productEntryId,
		OrderByComparator<SCProductScreenshot> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException {
		return getPersistence()
				   .findByProductEntryId_PrevAndNext(productScreenshotId,
			productEntryId, orderByComparator);
	}

	/**
	* Removes all the s c product screenshots where productEntryId = &#63; from the database.
	*
	* @param productEntryId the product entry ID
	*/
	public static void removeByProductEntryId(long productEntryId) {
		getPersistence().removeByProductEntryId(productEntryId);
	}

	/**
	* Returns the number of s c product screenshots where productEntryId = &#63;.
	*
	* @param productEntryId the product entry ID
	* @return the number of matching s c product screenshots
	*/
	public static int countByProductEntryId(long productEntryId) {
		return getPersistence().countByProductEntryId(productEntryId);
	}

	/**
	* Returns the s c product screenshot where thumbnailId = &#63; or throws a {@link NoSuchProductScreenshotException} if it could not be found.
	*
	* @param thumbnailId the thumbnail ID
	* @return the matching s c product screenshot
	* @throws NoSuchProductScreenshotException if a matching s c product screenshot could not be found
	*/
	public static SCProductScreenshot findByThumbnailId(long thumbnailId)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException {
		return getPersistence().findByThumbnailId(thumbnailId);
	}

	/**
	* Returns the s c product screenshot where thumbnailId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param thumbnailId the thumbnail ID
	* @return the matching s c product screenshot, or <code>null</code> if a matching s c product screenshot could not be found
	*/
	public static SCProductScreenshot fetchByThumbnailId(long thumbnailId) {
		return getPersistence().fetchByThumbnailId(thumbnailId);
	}

	/**
	* Returns the s c product screenshot where thumbnailId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param thumbnailId the thumbnail ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching s c product screenshot, or <code>null</code> if a matching s c product screenshot could not be found
	*/
	public static SCProductScreenshot fetchByThumbnailId(long thumbnailId,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByThumbnailId(thumbnailId, retrieveFromCache);
	}

	/**
	* Removes the s c product screenshot where thumbnailId = &#63; from the database.
	*
	* @param thumbnailId the thumbnail ID
	* @return the s c product screenshot that was removed
	*/
	public static SCProductScreenshot removeByThumbnailId(long thumbnailId)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException {
		return getPersistence().removeByThumbnailId(thumbnailId);
	}

	/**
	* Returns the number of s c product screenshots where thumbnailId = &#63;.
	*
	* @param thumbnailId the thumbnail ID
	* @return the number of matching s c product screenshots
	*/
	public static int countByThumbnailId(long thumbnailId) {
		return getPersistence().countByThumbnailId(thumbnailId);
	}

	/**
	* Returns the s c product screenshot where fullImageId = &#63; or throws a {@link NoSuchProductScreenshotException} if it could not be found.
	*
	* @param fullImageId the full image ID
	* @return the matching s c product screenshot
	* @throws NoSuchProductScreenshotException if a matching s c product screenshot could not be found
	*/
	public static SCProductScreenshot findByFullImageId(long fullImageId)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException {
		return getPersistence().findByFullImageId(fullImageId);
	}

	/**
	* Returns the s c product screenshot where fullImageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param fullImageId the full image ID
	* @return the matching s c product screenshot, or <code>null</code> if a matching s c product screenshot could not be found
	*/
	public static SCProductScreenshot fetchByFullImageId(long fullImageId) {
		return getPersistence().fetchByFullImageId(fullImageId);
	}

	/**
	* Returns the s c product screenshot where fullImageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param fullImageId the full image ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching s c product screenshot, or <code>null</code> if a matching s c product screenshot could not be found
	*/
	public static SCProductScreenshot fetchByFullImageId(long fullImageId,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByFullImageId(fullImageId, retrieveFromCache);
	}

	/**
	* Removes the s c product screenshot where fullImageId = &#63; from the database.
	*
	* @param fullImageId the full image ID
	* @return the s c product screenshot that was removed
	*/
	public static SCProductScreenshot removeByFullImageId(long fullImageId)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException {
		return getPersistence().removeByFullImageId(fullImageId);
	}

	/**
	* Returns the number of s c product screenshots where fullImageId = &#63;.
	*
	* @param fullImageId the full image ID
	* @return the number of matching s c product screenshots
	*/
	public static int countByFullImageId(long fullImageId) {
		return getPersistence().countByFullImageId(fullImageId);
	}

	/**
	* Returns the s c product screenshot where productEntryId = &#63; and priority = &#63; or throws a {@link NoSuchProductScreenshotException} if it could not be found.
	*
	* @param productEntryId the product entry ID
	* @param priority the priority
	* @return the matching s c product screenshot
	* @throws NoSuchProductScreenshotException if a matching s c product screenshot could not be found
	*/
	public static SCProductScreenshot findByP_P(long productEntryId,
		int priority)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException {
		return getPersistence().findByP_P(productEntryId, priority);
	}

	/**
	* Returns the s c product screenshot where productEntryId = &#63; and priority = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param productEntryId the product entry ID
	* @param priority the priority
	* @return the matching s c product screenshot, or <code>null</code> if a matching s c product screenshot could not be found
	*/
	public static SCProductScreenshot fetchByP_P(long productEntryId,
		int priority) {
		return getPersistence().fetchByP_P(productEntryId, priority);
	}

	/**
	* Returns the s c product screenshot where productEntryId = &#63; and priority = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param productEntryId the product entry ID
	* @param priority the priority
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching s c product screenshot, or <code>null</code> if a matching s c product screenshot could not be found
	*/
	public static SCProductScreenshot fetchByP_P(long productEntryId,
		int priority, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByP_P(productEntryId, priority, retrieveFromCache);
	}

	/**
	* Removes the s c product screenshot where productEntryId = &#63; and priority = &#63; from the database.
	*
	* @param productEntryId the product entry ID
	* @param priority the priority
	* @return the s c product screenshot that was removed
	*/
	public static SCProductScreenshot removeByP_P(long productEntryId,
		int priority)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException {
		return getPersistence().removeByP_P(productEntryId, priority);
	}

	/**
	* Returns the number of s c product screenshots where productEntryId = &#63; and priority = &#63;.
	*
	* @param productEntryId the product entry ID
	* @param priority the priority
	* @return the number of matching s c product screenshots
	*/
	public static int countByP_P(long productEntryId, int priority) {
		return getPersistence().countByP_P(productEntryId, priority);
	}

	/**
	* Caches the s c product screenshot in the entity cache if it is enabled.
	*
	* @param scProductScreenshot the s c product screenshot
	*/
	public static void cacheResult(SCProductScreenshot scProductScreenshot) {
		getPersistence().cacheResult(scProductScreenshot);
	}

	/**
	* Caches the s c product screenshots in the entity cache if it is enabled.
	*
	* @param scProductScreenshots the s c product screenshots
	*/
	public static void cacheResult(
		List<SCProductScreenshot> scProductScreenshots) {
		getPersistence().cacheResult(scProductScreenshots);
	}

	/**
	* Creates a new s c product screenshot with the primary key. Does not add the s c product screenshot to the database.
	*
	* @param productScreenshotId the primary key for the new s c product screenshot
	* @return the new s c product screenshot
	*/
	public static SCProductScreenshot create(long productScreenshotId) {
		return getPersistence().create(productScreenshotId);
	}

	/**
	* Removes the s c product screenshot with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param productScreenshotId the primary key of the s c product screenshot
	* @return the s c product screenshot that was removed
	* @throws NoSuchProductScreenshotException if a s c product screenshot with the primary key could not be found
	*/
	public static SCProductScreenshot remove(long productScreenshotId)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException {
		return getPersistence().remove(productScreenshotId);
	}

	public static SCProductScreenshot updateImpl(
		SCProductScreenshot scProductScreenshot) {
		return getPersistence().updateImpl(scProductScreenshot);
	}

	/**
	* Returns the s c product screenshot with the primary key or throws a {@link NoSuchProductScreenshotException} if it could not be found.
	*
	* @param productScreenshotId the primary key of the s c product screenshot
	* @return the s c product screenshot
	* @throws NoSuchProductScreenshotException if a s c product screenshot with the primary key could not be found
	*/
	public static SCProductScreenshot findByPrimaryKey(long productScreenshotId)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException {
		return getPersistence().findByPrimaryKey(productScreenshotId);
	}

	/**
	* Returns the s c product screenshot with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param productScreenshotId the primary key of the s c product screenshot
	* @return the s c product screenshot, or <code>null</code> if a s c product screenshot with the primary key could not be found
	*/
	public static SCProductScreenshot fetchByPrimaryKey(
		long productScreenshotId) {
		return getPersistence().fetchByPrimaryKey(productScreenshotId);
	}

	public static java.util.Map<java.io.Serializable, SCProductScreenshot> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the s c product screenshots.
	*
	* @return the s c product screenshots
	*/
	public static List<SCProductScreenshot> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the s c product screenshots.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductScreenshotModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s c product screenshots
	* @param end the upper bound of the range of s c product screenshots (not inclusive)
	* @return the range of s c product screenshots
	*/
	public static List<SCProductScreenshot> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the s c product screenshots.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductScreenshotModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s c product screenshots
	* @param end the upper bound of the range of s c product screenshots (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of s c product screenshots
	*/
	public static List<SCProductScreenshot> findAll(int start, int end,
		OrderByComparator<SCProductScreenshot> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the s c product screenshots.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SCProductScreenshotModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s c product screenshots
	* @param end the upper bound of the range of s c product screenshots (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to use the finder cache
	* @return the ordered range of s c product screenshots
	*/
	public static List<SCProductScreenshot> findAll(int start, int end,
		OrderByComparator<SCProductScreenshot> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the s c product screenshots from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of s c product screenshots.
	*
	* @return the number of s c product screenshots
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static SCProductScreenshotPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SCProductScreenshotPersistence)PortalBeanLocatorUtil.locate(SCProductScreenshotPersistence.class.getName());

			ReferenceRegistry.registerReference(SCProductScreenshotUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(SCProductScreenshotPersistence persistence) {
	}

	private static SCProductScreenshotPersistence _persistence;
}