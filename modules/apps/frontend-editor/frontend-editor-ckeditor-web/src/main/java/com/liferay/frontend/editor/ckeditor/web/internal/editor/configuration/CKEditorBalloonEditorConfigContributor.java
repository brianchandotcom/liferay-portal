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

package com.liferay.frontend.editor.ckeditor.web.internal.editor.configuration;

import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Ambrín Chaudhary
 */
@Component(
	property = "editor.name=ballooneditor",
	service = EditorConfigContributor.class
)
public class CKEditorBalloonEditorConfigContributor
	extends BaseCKEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		super.populateConfigJSONObject(
			jsonObject, inputEditorTaglibAttributes, themeDisplay,
			requestBackedPortletURLFactory);

		String extraPlugins = jsonObject.getString("extraPlugins");

		if (Validator.isNotNull(extraPlugins)) {
			extraPlugins += ",stylescombo";
		}
		else {
			extraPlugins = "stylescombo";
		}

		jsonObject.put(
			"extraPlugins", extraPlugins
		).put(
			"stylesSet", getStyleFormatsJSONArray(themeDisplay.getLocale())
		).put(
			"toolbarImage", "JustifyLeft,JustifyCenter,JustifyRight,Link,Unlink"
		).put(
			"toolbarText", getToolbarText()
		).put(
			"toolbarVideo", "JustifyLeft,JustifyCenter,JustifyRight"
		);
	}

	protected JSONObject getStyleFormatJSONObject(
		String styleFormatName, String element) {

		return JSONUtil.put(
			"element", element
		).put(
			"name", styleFormatName
		);
	}

	protected JSONArray getStyleFormatsJSONArray(Locale locale) {
		ResourceBundle resourceBundle = null;

		try {
			resourceBundle = _resourceBundleLoader.loadResourceBundle(locale);
		}
		catch (MissingResourceException missingResourceException) {
			if (_log.isDebugEnabled()) {
				_log.debug(missingResourceException, missingResourceException);
			}

			resourceBundle = ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE;
		}

		return JSONUtil.putAll(
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "normal"), "p"),
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "1"), "h1"),
			getStyleFormatJSONObject(
				LanguageUtil.format(resourceBundle, "heading-x", "2"), "h2"),
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "preformatted-text"), "pre"),
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "cited-work"), "cite"),
			getStyleFormatJSONObject(
				LanguageUtil.get(resourceBundle, "computer-code"), "code"));
	}

	protected String getToolbarText() {
		return "Styles,Bold,Italic,Underline,BulletedList,NumberedList,Link," +
			"JustifyLeft,JustifyCenter,JustifyRight,JustifyBlock,RemoveFormat";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CKEditorBalloonEditorConfigContributor.class);

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(bundle.symbolic.name=com.liferay.frontend.editor.lang)"
	)
	private volatile ResourceBundleLoader _resourceBundleLoader;

}