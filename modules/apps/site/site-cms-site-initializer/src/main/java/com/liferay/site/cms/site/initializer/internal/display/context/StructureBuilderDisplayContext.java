/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.object.admin.rest.dto.v1_0.ObjectAction;
import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class StructureBuilderDisplayContext {

	public StructureBuilderDisplayContext(
		HttpServletRequest httpServletRequest, JSONFactory jsonFactory,
		ObjectDefinitionResource.Factory objectDefinitionResourceFactory) {

		_httpServletRequest = httpServletRequest;
		_jsonFactory = jsonFactory;
		_objectDefinitionResourceFactory = objectDefinitionResourceFactory;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Map<String, Object> getProps() {
		return HashMapBuilder.<String, Object>put(
			"config",
			JSONUtil.put(
				"objectFolderExternalReferenceCode",
				ParamUtil.getString(
					_httpServletRequest, "objectFolderExternalReferenceCode"))
		).put(
			"state",
			JSONUtil.put("objectDefinition", _getObjectDefinitionJSONObject())
		).build();
	}

	private JSONObject _getObjectDefinitionJSONObject() {
		long objectDefinitionId = ParamUtil.getLong(
			_httpServletRequest, "objectDefinitionId");

		if (objectDefinitionId <= 0) {
			return null;
		}

		ObjectDefinitionResource.Builder builder =
			_objectDefinitionResourceFactory.create();

		ObjectDefinitionResource objectDefinitionResource = builder.user(
			_themeDisplay.getUser()
		).build();

		try {
			ObjectDefinition objectDefinition =
				objectDefinitionResource.getObjectDefinition(
					objectDefinitionId);

			for (ObjectAction objectAction :
					objectDefinition.getObjectActions()) {

				Map<String, Object> parameters =
					(Map<String, Object>)objectAction.getParameters();

				Object object = parameters.get("predefinedValues");

				if (object == null) {
					continue;
				}

				parameters.put(
					"predefinedValues",
					ListUtil.toList(
						(ArrayList<LinkedHashMap>)object,
						_jsonFactory::createJSONObject));
			}

			return _jsonFactory.createJSONObject(objectDefinition.toString());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StructureBuilderDisplayContext.class);

	private final HttpServletRequest _httpServletRequest;
	private final JSONFactory _jsonFactory;
	private final ObjectDefinitionResource.Factory
		_objectDefinitionResourceFactory;
	private final ThemeDisplay _themeDisplay;

}