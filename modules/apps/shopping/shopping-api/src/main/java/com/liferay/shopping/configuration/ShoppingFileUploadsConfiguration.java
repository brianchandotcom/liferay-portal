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

package com.liferay.shopping.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Drew Brokke
 */
@ExtendedObjectClassDefinition(category = "other")
@Meta.OCD(
	id = "com.liferay.shopping.configuration.ShoppingFileUploadsConfiguration",
	localization = "content/Language",
	name = "shopping.file.uploads.configuration.name"
)
public interface ShoppingFileUploadsConfiguration {

		@Meta.AD(deflt = "", required = false)
		public String[] shoppingImageExtensions();

		@Meta.AD(deflt = "0", required = false)
		public long shoppingImageLargeMaxSize();

		@Meta.AD(deflt = "0", required = false)
		public long shoppingImageMediumMaxSize();

		@Meta.AD(deflt = "0", required = false)
		public long shoppingImageSmallMaxSize();

}