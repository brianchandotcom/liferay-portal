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

import com.liferay.portal.model.Image;

/**
 * The persistence interface for the image service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ImagePersistenceImpl
 * @see ImageUtil
 * @generated
 */
@ProviderType
public interface ImagePersistence extends BasePersistence<Image> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ImageUtil} to access the image persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the images where size &lt; &#63;.
	*
	* @param size the size
	* @return the matching images
	*/
	public java.util.List<com.liferay.portal.model.Image> findByLtSize(int size);

	/**
	* Returns a range of all the images where size &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param size the size
	* @param start the lower bound of the range of images
	* @param end the upper bound of the range of images (not inclusive)
	* @return the range of matching images
	*/
	public java.util.List<com.liferay.portal.model.Image> findByLtSize(
		int size, int start, int end);

	/**
	* Returns an ordered range of all the images where size &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param size the size
	* @param start the lower bound of the range of images
	* @param end the upper bound of the range of images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching images
	*/
	public java.util.List<com.liferay.portal.model.Image> findByLtSize(
		int size, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first image in the ordered set where size &lt; &#63;.
	*
	* @param size the size
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching image
	* @throws com.liferay.portal.NoSuchImageException if a matching image could not be found
	*/
	public com.liferay.portal.model.Image findByLtSize_First(int size,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchImageException;

	/**
	* Returns the first image in the ordered set where size &lt; &#63;.
	*
	* @param size the size
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching image, or <code>null</code> if a matching image could not be found
	*/
	public com.liferay.portal.model.Image fetchByLtSize_First(int size,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last image in the ordered set where size &lt; &#63;.
	*
	* @param size the size
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching image
	* @throws com.liferay.portal.NoSuchImageException if a matching image could not be found
	*/
	public com.liferay.portal.model.Image findByLtSize_Last(int size,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchImageException;

	/**
	* Returns the last image in the ordered set where size &lt; &#63;.
	*
	* @param size the size
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching image, or <code>null</code> if a matching image could not be found
	*/
	public com.liferay.portal.model.Image fetchByLtSize_Last(int size,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the images before and after the current image in the ordered set where size &lt; &#63;.
	*
	* @param imageId the primary key of the current image
	* @param size the size
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next image
	* @throws com.liferay.portal.NoSuchImageException if a image with the primary key could not be found
	*/
	public com.liferay.portal.model.Image[] findByLtSize_PrevAndNext(
		long imageId, int size,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchImageException;

	/**
	* Removes all the images where size &lt; &#63; from the database.
	*
	* @param size the size
	*/
	public void removeByLtSize(int size);

	/**
	* Returns the number of images where size &lt; &#63;.
	*
	* @param size the size
	* @return the number of matching images
	*/
	public int countByLtSize(int size);

	/**
	* Caches the image in the entity cache if it is enabled.
	*
	* @param image the image
	*/
	public void cacheResult(com.liferay.portal.model.Image image);

	/**
	* Caches the images in the entity cache if it is enabled.
	*
	* @param images the images
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.Image> images);

	/**
	* Creates a new image with the primary key. Does not add the image to the database.
	*
	* @param imageId the primary key for the new image
	* @return the new image
	*/
	public com.liferay.portal.model.Image create(long imageId);

	/**
	* Removes the image with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param imageId the primary key of the image
	* @return the image that was removed
	* @throws com.liferay.portal.NoSuchImageException if a image with the primary key could not be found
	*/
	public com.liferay.portal.model.Image remove(long imageId)
		throws com.liferay.portal.NoSuchImageException;

	public com.liferay.portal.model.Image updateImpl(
		com.liferay.portal.model.Image image);

	/**
	* Returns the image with the primary key or throws a {@link com.liferay.portal.NoSuchImageException} if it could not be found.
	*
	* @param imageId the primary key of the image
	* @return the image
	* @throws com.liferay.portal.NoSuchImageException if a image with the primary key could not be found
	*/
	public com.liferay.portal.model.Image findByPrimaryKey(long imageId)
		throws com.liferay.portal.NoSuchImageException;

	/**
	* Returns the image with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param imageId the primary key of the image
	* @return the image, or <code>null</code> if a image with the primary key could not be found
	*/
	public com.liferay.portal.model.Image fetchByPrimaryKey(long imageId);

	@Override
	public java.util.Map<java.io.Serializable, com.liferay.portal.model.Image> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the images.
	*
	* @return the images
	*/
	public java.util.List<com.liferay.portal.model.Image> findAll();

	/**
	* Returns a range of all the images.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of images
	* @param end the upper bound of the range of images (not inclusive)
	* @return the range of images
	*/
	public java.util.List<com.liferay.portal.model.Image> findAll(int start,
		int end);

	/**
	* Returns an ordered range of all the images.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of images
	* @param end the upper bound of the range of images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of images
	*/
	public java.util.List<com.liferay.portal.model.Image> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Removes all the images from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of images.
	*
	* @return the number of images
	*/
	public int countAll();
}