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

package com.liferay.portal.kernel.image;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Image;

import java.io.InputStream;

/**
 * @author Jorge Ferrer
 */
public interface Hook {

	public void deleteImage(Image image)
		throws PortalException, SystemException;

	public byte[] getImageAsBytes(Image image)
		throws PortalException, SystemException;

	public InputStream getImageAsStream(Image image)
		throws PortalException, SystemException;

	public void updateImage(Image image, String type, byte[] bytes)
		throws PortalException, SystemException;

}