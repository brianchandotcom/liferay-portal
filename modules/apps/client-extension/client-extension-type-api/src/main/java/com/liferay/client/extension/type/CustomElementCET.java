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

package com.liferay.client.extension.type;

import com.liferay.client.extension.type.annotation.CETProperty;
import com.liferay.client.extension.type.annotation.CETType;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 */
@CETType(description = "This is a description.", name = "customElement")
@ProviderType
public interface CustomElementCET extends CET {

	@CETProperty(
		defaultValue = "", label = "css-urls", name = "cssURLs",
		type = CETProperty.Type.URLList
	)
	public String getCSSURLs();

	@CETProperty(
		defaultValue = "", name = "friendlyURLMapping",
		type = CETProperty.Type.String
	)
	public String getFriendlyURLMapping();

	@CETProperty(
		defaultValue = "", name = "htmlElementName",
		type = CETProperty.Type.String
	)
	public String getHTMLElementName();

	@CETProperty(
		defaultValue = "", name = "portletCategoryName",
		type = CETProperty.Type.String
	)
	public String getPortletCategoryName();

	@CETProperty(
		defaultValue = "", name = "urls", type = CETProperty.Type.URLList
	)
	public String getURLs();

	@CETProperty(
		defaultValue = "false", name = "instanceable",
		type = CETProperty.Type.Boolean
	)
	public boolean isInstanceable();

	@CETProperty(
		defaultValue = "false", name = "useESM", type = CETProperty.Type.Boolean
	)
	public boolean isUseESM();

}