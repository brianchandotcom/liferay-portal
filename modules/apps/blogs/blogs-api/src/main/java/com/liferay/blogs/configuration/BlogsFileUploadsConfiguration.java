/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Roberto Díaz
 */
@ExtendedObjectClassDefinition(category = "blogs")
@Meta.OCD(
	id = "com.liferay.blogs.configuration.BlogsFileUploadsConfiguration",
	localization = "content/Language",
	name = "blogs-file-uploads-configuration-name"
)
public interface BlogsFileUploadsConfiguration {

	@Meta.AD(
		deflt = ".gif,.jpeg,.jpg,.png",
		description = "blogs-allowed-image-file-extensions-description",
		name = "blogs-allowed-image-file-extensions", required = false
	)
	public String[] imageExtensions();

	@Meta.AD(
		deflt = "5242880",
		description = "blogs-image-maximum-file-size-description",
		name = "blogs-image-maximum-file-size", required = false
	)
	public long imageMaxSize();

}