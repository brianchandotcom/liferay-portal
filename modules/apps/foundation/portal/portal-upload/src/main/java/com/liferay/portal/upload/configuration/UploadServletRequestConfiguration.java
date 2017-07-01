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

package com.liferay.portal.upload.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Pei-Jung Lan
 */
@ExtendedObjectClassDefinition(category = "foundation")
@Meta.OCD(
	id = "com.liferay.portal.upload.configuration.UploadServletRequestConfiguration",
	localization = "content/Language",
	name = "upload-servlet-request-configuration-name"
)
public interface UploadServletRequestConfiguration {

	@Meta.AD(
		deflt = "104857600", description = "max-size-help",
		name = "overall-maximum-upload-request-size", required = false
	)
	public long maxSize();

	@Meta.AD(
		description = "temp-dir-help", name = "temporary-storage-directory",
		required = false
	)
	public String tempDir();

}