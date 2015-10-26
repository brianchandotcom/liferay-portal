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

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.softwarecatalog.model.SCProductScreenshot;

/**
 * The persistence interface for the s c product screenshot service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.softwarecatalog.service.persistence.impl.SCProductScreenshotPersistenceImpl
 * @see SCProductScreenshotUtil
 * @generated
 */
@ProviderType
public interface SCProductScreenshotPersistence extends BasePersistence<SCProductScreenshot> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SCProductScreenshotUtil} to access the s c product screenshot persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the s c product screenshots where productEntryId = &#63;.
	*
	* @param productEntryId the product entry ID
	* @return the matching s c product screenshots
	*/
	public java.util.List<SCProductScreenshot> findByProductEntryId(
		long productEntryId);

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
	public java.util.List<SCProductScreenshot> findByProductEntryId(
		long productEntryId, int start, int end);

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
	public java.util.List<SCProductScreenshot> findByProductEntryId(
		long productEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductScreenshot> orderByComparator);

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
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching s c product screenshots
	*/
	public java.util.List<SCProductScreenshot> findByProductEntryId(
		long productEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductScreenshot> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first s c product screenshot in the ordered set where productEntryId = &#63;.
	*
	* @param productEntryId the product entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c product screenshot
	* @throws NoSuchProductScreenshotException if a matching s c product screenshot could not be found
	*/
	public SCProductScreenshot findByProductEntryId_First(long productEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductScreenshot> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException;

	/**
	* Returns the first s c product screenshot in the ordered set where productEntryId = &#63;.
	*
	* @param productEntryId the product entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c product screenshot, or <code>null</code> if a matching s c product screenshot could not be found
	*/
	public SCProductScreenshot fetchByProductEntryId_First(
		long productEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductScreenshot> orderByComparator);

	/**
	* Returns the last s c product screenshot in the ordered set where productEntryId = &#63;.
	*
	* @param productEntryId the product entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c product screenshot
	* @throws NoSuchProductScreenshotException if a matching s c product screenshot could not be found
	*/
	public SCProductScreenshot findByProductEntryId_Last(long productEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductScreenshot> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException;

	/**
	* Returns the last s c product screenshot in the ordered set where productEntryId = &#63;.
	*
	* @param productEntryId the product entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c product screenshot, or <code>null</code> if a matching s c product screenshot could not be found
	*/
	public SCProductScreenshot fetchByProductEntryId_Last(long productEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductScreenshot> orderByComparator);

	/**
	* Returns the s c product screenshots before and after the current s c product screenshot in the ordered set where productEntryId = &#63;.
	*
	* @param productScreenshotId the primary key of the current s c product screenshot
	* @param productEntryId the product entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c product screenshot
	* @throws NoSuchProductScreenshotException if a s c product screenshot with the primary key could not be found
	*/
	public SCProductScreenshot[] findByProductEntryId_PrevAndNext(
		long productScreenshotId, long productEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductScreenshot> orderByComparator)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException;

	/**
	* Removes all the s c product screenshots where productEntryId = &#63; from the database.
	*
	* @param productEntryId the product entry ID
	*/
	public void removeByProductEntryId(long productEntryId);

	/**
	* Returns the number of s c product screenshots where productEntryId = &#63;.
	*
	* @param productEntryId the product entry ID
	* @return the number of matching s c product screenshots
	*/
	public int countByProductEntryId(long productEntryId);

	/**
	* Returns the s c product screenshot where thumbnailId = &#63; or throws a {@link NoSuchProductScreenshotException} if it could not be found.
	*
	* @param thumbnailId the thumbnail ID
	* @return the matching s c product screenshot
	* @throws NoSuchProductScreenshotException if a matching s c product screenshot could not be found
	*/
	public SCProductScreenshot findByThumbnailId(long thumbnailId)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException;

	/**
	* Returns the s c product screenshot where thumbnailId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param thumbnailId the thumbnail ID
	* @return the matching s c product screenshot, or <code>null</code> if a matching s c product screenshot could not be found
	*/
	public SCProductScreenshot fetchByThumbnailId(long thumbnailId);

	/**
	* Returns the s c product screenshot where thumbnailId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param thumbnailId the thumbnail ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching s c product screenshot, or <code>null</code> if a matching s c product screenshot could not be found
	*/
	public SCProductScreenshot fetchByThumbnailId(long thumbnailId,
		boolean retrieveFromCache);

	/**
	* Removes the s c product screenshot where thumbnailId = &#63; from the database.
	*
	* @param thumbnailId the thumbnail ID
	* @return the s c product screenshot that was removed
	*/
	public SCProductScreenshot removeByThumbnailId(long thumbnailId)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException;

	/**
	* Returns the number of s c product screenshots where thumbnailId = &#63;.
	*
	* @param thumbnailId the thumbnail ID
	* @return the number of matching s c product screenshots
	*/
	public int countByThumbnailId(long thumbnailId);

	/**
	* Returns the s c product screenshot where fullImageId = &#63; or throws a {@link NoSuchProductScreenshotException} if it could not be found.
	*
	* @param fullImageId the full image ID
	* @return the matching s c product screenshot
	* @throws NoSuchProductScreenshotException if a matching s c product screenshot could not be found
	*/
	public SCProductScreenshot findByFullImageId(long fullImageId)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException;

	/**
	* Returns the s c product screenshot where fullImageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param fullImageId the full image ID
	* @return the matching s c product screenshot, or <code>null</code> if a matching s c product screenshot could not be found
	*/
	public SCProductScreenshot fetchByFullImageId(long fullImageId);

	/**
	* Returns the s c product screenshot where fullImageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param fullImageId the full image ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching s c product screenshot, or <code>null</code> if a matching s c product screenshot could not be found
	*/
	public SCProductScreenshot fetchByFullImageId(long fullImageId,
		boolean retrieveFromCache);

	/**
	* Removes the s c product screenshot where fullImageId = &#63; from the database.
	*
	* @param fullImageId the full image ID
	* @return the s c product screenshot that was removed
	*/
	public SCProductScreenshot removeByFullImageId(long fullImageId)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException;

	/**
	* Returns the number of s c product screenshots where fullImageId = &#63;.
	*
	* @param fullImageId the full image ID
	* @return the number of matching s c product screenshots
	*/
	public int countByFullImageId(long fullImageId);

	/**
	* Returns the s c product screenshot where productEntryId = &#63; and priority = &#63; or throws a {@link NoSuchProductScreenshotException} if it could not be found.
	*
	* @param productEntryId the product entry ID
	* @param priority the priority
	* @return the matching s c product screenshot
	* @throws NoSuchProductScreenshotException if a matching s c product screenshot could not be found
	*/
	public SCProductScreenshot findByP_P(long productEntryId, int priority)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException;

	/**
	* Returns the s c product screenshot where productEntryId = &#63; and priority = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param productEntryId the product entry ID
	* @param priority the priority
	* @return the matching s c product screenshot, or <code>null</code> if a matching s c product screenshot could not be found
	*/
	public SCProductScreenshot fetchByP_P(long productEntryId, int priority);

	/**
	* Returns the s c product screenshot where productEntryId = &#63; and priority = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param productEntryId the product entry ID
	* @param priority the priority
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching s c product screenshot, or <code>null</code> if a matching s c product screenshot could not be found
	*/
	public SCProductScreenshot fetchByP_P(long productEntryId, int priority,
		boolean retrieveFromCache);

	/**
	* Removes the s c product screenshot where productEntryId = &#63; and priority = &#63; from the database.
	*
	* @param productEntryId the product entry ID
	* @param priority the priority
	* @return the s c product screenshot that was removed
	*/
	public SCProductScreenshot removeByP_P(long productEntryId, int priority)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException;

	/**
	* Returns the number of s c product screenshots where productEntryId = &#63; and priority = &#63;.
	*
	* @param productEntryId the product entry ID
	* @param priority the priority
	* @return the number of matching s c product screenshots
	*/
	public int countByP_P(long productEntryId, int priority);

	/**
	* Caches the s c product screenshot in the entity cache if it is enabled.
	*
	* @param scProductScreenshot the s c product screenshot
	*/
	public void cacheResult(SCProductScreenshot scProductScreenshot);

	/**
	* Caches the s c product screenshots in the entity cache if it is enabled.
	*
	* @param scProductScreenshots the s c product screenshots
	*/
	public void cacheResult(
		java.util.List<SCProductScreenshot> scProductScreenshots);

	/**
	* Creates a new s c product screenshot with the primary key. Does not add the s c product screenshot to the database.
	*
	* @param productScreenshotId the primary key for the new s c product screenshot
	* @return the new s c product screenshot
	*/
	public SCProductScreenshot create(long productScreenshotId);

	/**
	* Removes the s c product screenshot with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param productScreenshotId the primary key of the s c product screenshot
	* @return the s c product screenshot that was removed
	* @throws NoSuchProductScreenshotException if a s c product screenshot with the primary key could not be found
	*/
	public SCProductScreenshot remove(long productScreenshotId)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException;

	public SCProductScreenshot updateImpl(
		SCProductScreenshot scProductScreenshot);

	/**
	* Returns the s c product screenshot with the primary key or throws a {@link NoSuchProductScreenshotException} if it could not be found.
	*
	* @param productScreenshotId the primary key of the s c product screenshot
	* @return the s c product screenshot
	* @throws NoSuchProductScreenshotException if a s c product screenshot with the primary key could not be found
	*/
	public SCProductScreenshot findByPrimaryKey(long productScreenshotId)
		throws com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException;

	/**
	* Returns the s c product screenshot with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param productScreenshotId the primary key of the s c product screenshot
	* @return the s c product screenshot, or <code>null</code> if a s c product screenshot with the primary key could not be found
	*/
	public SCProductScreenshot fetchByPrimaryKey(long productScreenshotId);

	@Override
	public java.util.Map<java.io.Serializable, SCProductScreenshot> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the s c product screenshots.
	*
	* @return the s c product screenshots
	*/
	public java.util.List<SCProductScreenshot> findAll();

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
	public java.util.List<SCProductScreenshot> findAll(int start, int end);

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
	public java.util.List<SCProductScreenshot> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductScreenshot> orderByComparator);

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
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of s c product screenshots
	*/
	public java.util.List<SCProductScreenshot> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SCProductScreenshot> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the s c product screenshots from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of s c product screenshots.
	*
	* @return the number of s c product screenshots
	*/
	public int countAll();
}