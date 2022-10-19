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

package com.liferay.frontend.editor.alloyeditor.web.internal.editor.configuration;

import com.liferay.frontend.editor.embed.EditorEmbedProvider;
import com.liferay.frontend.editor.embed.constants.EditorEmbedProviderTypeConstants;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Chema Balsas
 */
@Component(
	property = "editor.name=alloyeditor",
	service = EditorConfigContributor.class
)
public class AlloyEditorEmbedConfigContributor
	extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		jsonObject.put("embedProviders", _getEditorEmbedProvidersJSONArray());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, EditorEmbedProvider.class, null,
			(serviceReference, emitter) -> {
				String type = (String)serviceReference.getProperty("type");

				if (Validator.isNull(type)) {
					type = EditorEmbedProviderTypeConstants.UNKNOWN;
				}

				emitter.emit(type);
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private JSONObject _getEditorEmbedProviderJSONObject(
		String editorEmbedProviderType,
		EditorEmbedProvider editorEmbedProvider) {

		return JSONUtil.put(
			"id", editorEmbedProvider.getId()
		).put(
			"tpl", editorEmbedProvider.getTpl()
		).put(
			"type", editorEmbedProviderType
		).put(
			"urlSchemes",
			() -> {
				JSONArray urlSchemesJSONArray =
					JSONFactoryUtil.createJSONArray();

				String[] urlSchemes = editorEmbedProvider.getURLSchemes();

				for (String urlScheme : urlSchemes) {
					urlSchemesJSONArray.put(urlScheme);
				}

				return urlSchemesJSONArray;
			}
		);
	}

	private JSONArray _getEditorEmbedProvidersJSONArray() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		Set<String> editorEmbedProviderTypes = _serviceTrackerMap.keySet();

		editorEmbedProviderTypes.forEach(
			editorEmbedProviderType -> {
				List<EditorEmbedProvider> editorEmbedProviders =
					_serviceTrackerMap.getService(editorEmbedProviderType);

				editorEmbedProviders.forEach(
					editorEmbedProvider -> jsonArray.put(
						_getEditorEmbedProviderJSONObject(
							editorEmbedProviderType, editorEmbedProvider)));
			});

		return jsonArray;
	}

	private ServiceTrackerMap<String, List<EditorEmbedProvider>>
		_serviceTrackerMap;

}