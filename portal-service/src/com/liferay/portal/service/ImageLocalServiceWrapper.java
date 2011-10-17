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

package com.liferay.portal.service;

/**
 * <p>
 * This class is a wrapper for {@link ImageLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ImageLocalService
 * @generated
 */
public class ImageLocalServiceWrapper implements ImageLocalService,
	ServiceWrapper<ImageLocalService> {
	public ImageLocalServiceWrapper(ImageLocalService imageLocalService) {
		_imageLocalService = imageLocalService;
	}

	/**
	* Adds the image to the database. Also notifies the appropriate model listeners.
	*
	* @param image the image
	* @return the image that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Image addImage(
		com.liferay.portal.model.Image image)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.addImage(image);
	}

	/**
	* Creates a new image with the primary key. Does not add the image to the database.
	*
	* @param imageId the primary key for the new image
	* @return the new image
	*/
	public com.liferay.portal.model.Image createImage(long imageId) {
		return _imageLocalService.createImage(imageId);
	}

	/**
	* Deletes the image with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param imageId the primary key of the image
	* @throws PortalException if a image with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteImage(long imageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_imageLocalService.deleteImage(imageId);
	}

	/**
	* Deletes the image from the database. Also notifies the appropriate model listeners.
	*
	* @param image the image
	* @throws SystemException if a system exception occurred
	*/
	public void deleteImage(com.liferay.portal.model.Image image)
		throws com.liferay.portal.kernel.exception.SystemException {
		_imageLocalService.deleteImage(image);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the image with the primary key.
	*
	* @param imageId the primary key of the image
	* @return the image
	* @throws PortalException if a image with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Image getImage(long imageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.getImage(imageId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the images.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of images
	* @param end the upper bound of the range of images (not inclusive)
	* @return the range of images
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Image> getImages(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.getImages(start, end);
	}

	/**
	* Returns the number of images.
	*
	* @return the number of images
	* @throws SystemException if a system exception occurred
	*/
	public int getImagesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.getImagesCount();
	}

	/**
	* Updates the image in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param image the image
	* @return the image that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Image updateImage(
		com.liferay.portal.model.Image image)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.updateImage(image);
	}

	/**
	* Updates the image in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param image the image
	* @param merge whether to merge the image with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the image that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Image updateImage(
		com.liferay.portal.model.Image image, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.updateImage(image, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _imageLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_imageLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portal.model.Image getCompanyLogo(long imageId) {
		return _imageLocalService.getCompanyLogo(imageId);
	}

	public com.liferay.portal.model.Image getDefaultCompanyLogo() {
		return _imageLocalService.getDefaultCompanyLogo();
	}

	public com.liferay.portal.model.Image getDefaultOrganizationLogo() {
		return _imageLocalService.getDefaultOrganizationLogo();
	}

	public com.liferay.portal.model.Image getDefaultSpacer() {
		return _imageLocalService.getDefaultSpacer();
	}

	public com.liferay.portal.model.Image getDefaultUserFemalePortrait() {
		return _imageLocalService.getDefaultUserFemalePortrait();
	}

	public com.liferay.portal.model.Image getDefaultUserMalePortrait() {
		return _imageLocalService.getDefaultUserMalePortrait();
	}

	public com.liferay.portal.model.Image getImage(byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.getImage(bytes);
	}

	public com.liferay.portal.model.Image getImage(java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.getImage(file);
	}

	public com.liferay.portal.model.Image getImage(java.io.InputStream is)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.getImage(is);
	}

	public com.liferay.portal.model.Image getImageOrDefault(long imageId) {
		return _imageLocalService.getImageOrDefault(imageId);
	}

	public java.util.List<com.liferay.portal.model.Image> getImages()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.getImages();
	}

	public java.util.List<com.liferay.portal.model.Image> getImagesBySize(
		int size) throws com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.getImagesBySize(size);
	}

	public boolean isNullOrDefaultSpacer(byte[] bytes) {
		return _imageLocalService.isNullOrDefaultSpacer(bytes);
	}

	public com.liferay.portal.model.Image updateImage(long imageId, byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.updateImage(imageId, bytes);
	}

	public com.liferay.portal.model.Image updateImage(long imageId,
		java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.updateImage(imageId, file);
	}

	public com.liferay.portal.model.Image updateImage(long imageId,
		java.io.InputStream is)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.updateImage(imageId, is);
	}

	public com.liferay.portal.model.Image updateImage(long imageId,
		byte[] bytes, java.lang.String type, int height, int width, int size)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _imageLocalService.updateImage(imageId, bytes, type, height,
			width, size);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public ImageLocalService getWrappedImageLocalService() {
		return _imageLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedImageLocalService(ImageLocalService imageLocalService) {
		_imageLocalService = imageLocalService;
	}

	public ImageLocalService getWrappedService() {
		return _imageLocalService;
	}

	public void setWrappedService(ImageLocalService imageLocalService) {
		_imageLocalService = imageLocalService;
	}

	private ImageLocalService _imageLocalService;
}