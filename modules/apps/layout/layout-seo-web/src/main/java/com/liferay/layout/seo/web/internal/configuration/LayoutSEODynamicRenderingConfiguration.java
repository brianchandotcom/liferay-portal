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

package com.liferay.layout.seo.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Jamie Sammons
 */
@ExtendedObjectClassDefinition(category = "pages")
@Meta.OCD(
	description = "layout-seo-dynamic-rendering-configuration-description",
	id = "com.liferay.layout.seo.web.internal.configuration.LayoutSEODynamicRenderingConfiguration",
	localization = "content/Language",
	name = "layout-seo-dynamic-rendering-configuration-name"
)
public interface LayoutSEODynamicRenderingConfiguration {

	@Meta.AD(
		deflt = "false",
		name = "layout-seo-dynamic-rendering-configuration-enable-service",
		required = false
	)
	public boolean enabled();

	@Meta.AD(
		deflt = "",
		name = "layout-seo-dynamic-rendering-configuration-service-url",
		required = false
	)
	public String serviceURL();

	@Meta.AD(
		deflt = "",
		name = "layout-seo-dynamic-rendering-configuration-included-path",
		required = false
	)
	public String[] includedPaths();

	@Meta.AD(
		deflt = ".ai,.avi,.css,.dat,.dmg,.doc,.doc,.eot,.exe,.flv,.gif,.ico,.iso,.jpeg,.jpg,.js,.less,.m4a,.m4v,.mov,.mp3,.mp4,.mpeg,.mpg,.pdf,.png,.ppt,.psd,.rar,.rss,.svg,.swf,.tif,.torrent,.ttf,.txt,.wav,.wmv,.woff,.xls,.xml,.zip",
		name = "layout-seo-dynamic-rendering-configuration-ignored-extensions",
		required = false
	)
	public String[] ignoredExtensions();

}