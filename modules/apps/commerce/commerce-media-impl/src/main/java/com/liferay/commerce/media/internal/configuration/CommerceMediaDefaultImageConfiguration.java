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

package com.liferay.commerce.media.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.commerce.media.constants.CommerceMediaConstants;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Alec Sloan
 */
@ExtendedObjectClassDefinition(
	category = "catalog", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	description = "commerce-default-images-configuration-description",
	id = CommerceMediaConstants.SERVICE_NAME, localization = "content/Language",
	name = "commerce-default-images-configuration-name"
)
public interface CommerceMediaDefaultImageConfiguration {

	@Meta.AD(deflt = "0", name = "default-catalog-image", required = false)
	public long defaultFileEntryId();

}