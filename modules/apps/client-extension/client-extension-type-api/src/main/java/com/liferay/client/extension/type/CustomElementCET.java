/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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