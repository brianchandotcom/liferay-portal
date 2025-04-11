/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.editor.ckeditor.web.internal.editor.configuration;

import com.liferay.frontend.editor.ckeditor.web.internal.configuration.CKEditor5Configuration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Marko Cikos
 */
@Component(
	configurationPid = "com.liferay.frontend.editor.ckeditor.web.internal.configuration.CKEditor5Configuration",
	property = "editor.name=ckeditor5_classic",
	service = EditorConfigContributor.class
)
public class CKEditor5ClassicEditorConfigContributor
	extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		String namespace = GetterUtil.getString(
			inputEditorTaglibAttributes.get(
				"liferay-ui:input-editor:namespace"));
		String name = GetterUtil.getString(
			inputEditorTaglibAttributes.get("liferay-ui:input-editor:name"));

		jsonObject.put(
			"itemSelectorEventName", namespace + name + "selectItem"
		).put(
			"licenseKey", _ckEditor5Configuration.licenseKey()
		).put(
			"preset", "advanced"
		);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_ckEditor5Configuration = ConfigurableUtil.createConfigurable(
			CKEditor5Configuration.class, properties);
	}

	private volatile CKEditor5Configuration _ckEditor5Configuration;

}