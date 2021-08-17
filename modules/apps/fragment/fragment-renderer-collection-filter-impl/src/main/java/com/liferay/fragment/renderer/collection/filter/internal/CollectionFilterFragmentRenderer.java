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

package com.liferay.fragment.renderer.collection.filter.internal;

import com.liferay.fragment.collection.filter.FragmentCollectionFilter;
import com.liferay.fragment.collection.filter.FragmentCollectionFilterTracker;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.renderer.collection.filter.internal.configuration.FFFragmentRendererCollectionFilterConfiguration;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(
	configurationPid = "com.liferay.fragment.renderer.collection.filter.internal.configuration.FFFragmentRendererCollectionFilterConfiguration",
	service = FragmentRenderer.class
)
public class CollectionFilterFragmentRenderer implements FragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "content-display";
	}

	@Override
	public String getConfiguration(
		FragmentRendererContext fragmentRendererContext) {

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", fragmentRendererContext.getLocale(),
			getClass());

		try {
			String json = StringUtil.read(
				getClass(),
				"/com/liferay/fragment/renderer/collection/filter/internal" +
					"/dependencies/configuration.json");

			JSONObject configurationJSONObject =
				JSONFactoryUtil.createJSONObject(json);

			String filterPlaceholder = _getFilterPlaceholder(
				json, fragmentRendererContext, resourceBundle);

			JSONObject filterTypeOptionsJSONObject =
				_filterTypeOptionsJSONObject(configurationJSONObject);

			if ((filterPlaceholder != null) &&
				(filterTypeOptionsJSONObject != null)) {

				filterTypeOptionsJSONObject.put(
					"placeholder", filterPlaceholder);
			}

			return _fragmentEntryConfigurationParser.translateConfiguration(
				configurationJSONObject, resourceBundle);
		}
		catch (JSONException jsonException) {
			return StringPool.BLANK;
		}
	}

	@Override
	public String getIcon() {
		return "filter";
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getClass());

		return LanguageUtil.get(resourceBundle, "collection-filter");
	}

	@Override
	public boolean isSelectable(HttpServletRequest httpServletRequest) {
		if (!_ffFragmentRendererCollectionFilterConfiguration.enabled()) {
			return false;
		}

		return true;
	}

	@Override
	public void render(
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			httpServletRequest.setAttribute(
				FragmentCollectionFilter.class.getName(),
				_fragmentCollectionFilterTracker.getFragmentCollectionFilter(
					"category"));

			httpServletRequest.setAttribute(
				FragmentRendererContext.class.getName(),
				fragmentRendererContext);

			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher("/page.jsp");

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			_log.error(
				"Unable to render collection filter fragment", exception);
		}
	}

	@Modified
	protected void activate(Map<String, Object> properties) {
		_ffFragmentRendererCollectionFilterConfiguration =
			ConfigurableUtil.createConfigurable(
				FFFragmentRendererCollectionFilterConfiguration.class,
				properties);
	}

	private JSONObject _filterTypeOptionsJSONObject(
		JSONObject configurationJSONObject) {

		JSONArray fieldSetsJSONArray = configurationJSONObject.getJSONArray(
			"fieldSets");

		if (fieldSetsJSONArray == null) {
			return null;
		}

		JSONObject fieldSetsJSONObject = fieldSetsJSONArray.getJSONObject(0);

		JSONArray fieldsJSONArray = fieldSetsJSONObject.getJSONArray("fields");

		if (fieldsJSONArray == null) {
			return null;
		}

		for (int j = 0; j < fieldsJSONArray.length(); j++) {
			JSONObject fieldJSONObject = fieldsJSONArray.getJSONObject(j);

			if (Objects.equals(fieldJSONObject.getString("name"), "label") &&
				fieldJSONObject.has("typeOptions")) {

				return fieldJSONObject.getJSONObject("typeOptions");
			}
		}

		return null;
	}

	private String _getFilterPlaceholder(
		String configuration, FragmentRendererContext fragmentRendererContext,
		ResourceBundle resourceBundle) {

		FragmentEntryLink fragmentEntryLink =
			fragmentRendererContext.getFragmentEntryLink();

		if (fragmentEntryLink == null) {
			return null;
		}

		String source = GetterUtil.getString(
			_fragmentEntryConfigurationParser.getFieldValue(
				configuration, fragmentEntryLink.getEditableValues(),
				resourceBundle.getLocale(), "source"));

		if (Validator.isNull(source) || !JSONUtil.isValid(source)) {
			return null;
		}

		try {
			JSONObject sourceJSONObject = JSONFactoryUtil.createJSONObject(
				source);

			return sourceJSONObject.getString("title");
		}
		catch (JSONException jsonException) {
			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CollectionFilterFragmentRenderer.class);

	private volatile FFFragmentRendererCollectionFilterConfiguration
		_ffFragmentRendererCollectionFilterConfiguration;

	@Reference
	private FragmentCollectionFilterTracker _fragmentCollectionFilterTracker;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.fragment.renderer.collection.filter.impl)"
	)
	private ServletContext _servletContext;

}