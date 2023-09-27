/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.editor.ckeditor.web.internal.editor.configuration;

import com.liferay.frontend.editor.embed.EditorEmbedProvider;
import com.liferay.frontend.editor.embed.constants.EditorEmbedProviderTypeConstants;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
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
import org.osgi.service.component.annotations.Reference;

/**
 * @author Chema Balsas
 */
@Component(
	property = {"editor.name=ckeditor", "editor.name=ckeditor_classic"},
	service = EditorConfigContributor.class
)
public class CKEditorEmbedConfigContributor
	extends BaseCKEditorConfigContributor {

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
				JSONArray urlSchemesJSONArray = _jsonFactory.createJSONArray();

				String[] urlSchemes = editorEmbedProvider.getURLSchemes();

				for (String urlScheme : urlSchemes) {
					urlSchemesJSONArray.put(urlScheme);
				}

				return urlSchemesJSONArray;
			}
		);
	}

	private JSONArray _getEditorEmbedProvidersJSONArray() {
		JSONArray jsonArray = _jsonFactory.createJSONArray();

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

	@Reference
	private JSONFactory _jsonFactory;

	private ServiceTrackerMap<String, List<EditorEmbedProvider>>
		_serviceTrackerMap;

}