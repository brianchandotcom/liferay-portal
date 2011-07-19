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

package com.liferay.portlet.imagegallery.model;

import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the IGImage service. Represents a row in the &quot;IGImage&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see IGImageModel
 * @see com.liferay.portlet.imagegallery.model.impl.IGImageImpl
 * @see com.liferay.portlet.imagegallery.model.impl.IGImageModelImpl
 * @generated
 */
public interface IGImage extends IGImageModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.imagegallery.model.impl.IGImageImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public com.liferay.portlet.imagegallery.model.IGFolder getFolder();

	public int getImageSize();

	public java.lang.String getImageType();

	public java.lang.String getNameWithExtension();

	public void setImageType(java.lang.String imageType);
}