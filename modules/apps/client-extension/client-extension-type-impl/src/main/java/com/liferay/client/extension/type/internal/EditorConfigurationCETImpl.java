/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.internal;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.type.EditorConfigurationCET;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;

import java.util.Properties;
import java.util.Set;

import javax.portlet.PortletRequest;

/**
 * @author Daniel Sanz
 */
public class EditorConfigurationCETImpl
	extends BaseCETImpl implements EditorConfigurationCET {

	public EditorConfigurationCETImpl(
		ClientExtensionEntry clientExtensionEntry) {

		super(clientExtensionEntry);
	}

	public EditorConfigurationCETImpl(PortletRequest portletRequest) {
		this(
			StringPool.BLANK,
			UnicodePropertiesBuilder.create(
				true
			).put(
				"editorConfigKeys",
				StringUtil.merge(
					ParamUtil.getStringValues(
						portletRequest, "editorConfigKeys"),
					StringPool.NEW_LINE)
			).put(
				"editorNames",
				StringUtil.merge(
					ParamUtil.getStringValues(portletRequest, "editorNames"),
					StringPool.NEW_LINE)
			).put(
				"portletNames",
				StringUtil.merge(
					ParamUtil.getStringValues(portletRequest, "portletNames"),
					StringPool.NEW_LINE)
			).put(
				"url", ParamUtil.getString(portletRequest, "url")
			).build());
	}

	public EditorConfigurationCETImpl(
		String baseURL, long companyId, String description,
		String externalReferenceCode, String name, Properties properties,
		String sourceCodeURL, UnicodeProperties typeSettingsUnicodeProperties) {

		super(
			baseURL, companyId, description, externalReferenceCode, name,
			properties, sourceCodeURL, typeSettingsUnicodeProperties);
	}

	public EditorConfigurationCETImpl(
		String baseURL, UnicodeProperties typeSettingsUnicodeProperties) {

		super(baseURL, typeSettingsUnicodeProperties);
	}

	@Override
	public String getEditJSP() {
		return "/admin/edit_editor_configuration.jsp";
	}

	@Override
	public String getEditorConfigKeys() {
		return getString("editorConfigKeys");
	}

	@Override
	public String getEditorNames() {
		return getString("editorNames");
	}

	@Override
	public String getPortletNames() {
		return getString("portletNames");
	}

	@Override
	public String getType() {
		return ClientExtensionEntryConstants.TYPE_EDITOR_CONFIGURATION;
	}

	@Override
	public String getURL() {
		return getString("url");
	}

	@Override
	public boolean hasProperties() {
		return true;
	}

	@Override
	protected boolean isURLCETPropertyName(String name) {
		return _urlCETPropertyNames.contains(name);
	}

	private static final Set<String> _urlCETPropertyNames =
		getURLCETPropertyNames(EditorConfigurationCET.class);

}