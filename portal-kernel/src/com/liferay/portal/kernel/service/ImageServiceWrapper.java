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

package com.liferay.portal.kernel.service;

/**
 * Provides a wrapper for {@link ImageService}.
 *
 * @author Brian Wing Shun Chan
 * @see ImageService
 * @generated
 */
public class ImageServiceWrapper
	implements ImageService, ServiceWrapper<ImageService> {

	public ImageServiceWrapper(ImageService imageService) {
		_imageService = imageService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ImageServiceUtil} to access the image remote service. Add custom service methods to <code>com.liferay.portal.service.impl.ImageServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.portal.kernel.model.Image getImage(long imageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _imageService.getImage(imageId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _imageService.getOSGiServiceIdentifier();
	}

	@Override
	public ImageService getWrappedService() {
		return _imageService;
	}

	@Override
	public void setWrappedService(ImageService imageService) {
		_imageService = imageService;
	}

	private ImageService _imageService;

}