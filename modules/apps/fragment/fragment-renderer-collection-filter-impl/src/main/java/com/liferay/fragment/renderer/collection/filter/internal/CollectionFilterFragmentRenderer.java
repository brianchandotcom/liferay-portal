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

import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.renderer.collection.filter.internal.configuration.FFFragmentRendererCollectionFilterConfiguration;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.PrintWriter;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

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
			"content.Language", getClass());

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				StringUtil.read(
					getClass(),
					"/META-INF/resources/fragment/renderer/collection/filter" +
						"/configuration.json"));

			return _fragmentEntryConfigurationParser.translateConfiguration(
				jsonObject, resourceBundle);
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
		return _ffFragmentRendererCollectionFilterConfiguration.enabled();
	}

	@Override
	public void render(
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			PrintWriter printWriter = httpServletResponse.getWriter();

			printWriter.print("COLLECTION FILTER");

			printWriter.flush();
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Modified
	protected void activate(Map<String, Object> properties) {
		_ffFragmentRendererCollectionFilterConfiguration =
			ConfigurableUtil.createConfigurable(
				FFFragmentRendererCollectionFilterConfiguration.class,
				properties);
	}

	private volatile FFFragmentRendererCollectionFilterConfiguration
		_ffFragmentRendererCollectionFilterConfiguration;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;
}