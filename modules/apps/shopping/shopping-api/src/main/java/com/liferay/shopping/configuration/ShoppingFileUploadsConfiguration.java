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
	name = "shopping-file-uploads-configuration-name"
)
public interface ShoppingFileUploadsConfiguration {

	@Meta.AD(
		deflt = ".gif,.jpeg,.jpg,.png", name = "allowed-file-extensions",
		required = false
	)
	public String[] imageExtensions();

	@Meta.AD(
		deflt = "307200", name = "maximum-file-size-large-image",
		required = false
	)
	public long largeImageMaxSize();

	@Meta.AD(
		deflt = "153600", name = "maximum-file-size-medium-image",
		required = false
	)
	public long mediumImageMaxSize();

	@Meta.AD(
		deflt = "51200", name = "maximum-file-size-small-image",
		required = false
	)
	public long smallImageMaxSize();

}