/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type;

import com.liferay.client.extension.type.annotation.CETProperty;
import com.liferay.client.extension.type.annotation.CETType;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Daniel Sanz
 */
@CETType(
	description = "This is a description.", name = "editorConfigContributor"
)
@ProviderType
public interface EditorConfigContributorCET extends CET {

	@CETProperty(
		defaultValue = "", label = "editor-config-keys",
		name = "editorConfigKeys", type = CETProperty.Type.StringList
	)
	public String getEditorConfigKeys();

	@CETProperty(
		defaultValue = "", label = "editor-names", name = "editorNames",
		type = CETProperty.Type.StringList
	)
	public String getEditorNames();

	@CETProperty(
		defaultValue = "", label = "portlet-names", name = "portletNames",
		type = CETProperty.Type.StringList
	)
	public String getPortletNames();

	@CETProperty(
		defaultValue = "", label = "url", name = "url",
		type = CETProperty.Type.URL
	)
	public String getURL();

}