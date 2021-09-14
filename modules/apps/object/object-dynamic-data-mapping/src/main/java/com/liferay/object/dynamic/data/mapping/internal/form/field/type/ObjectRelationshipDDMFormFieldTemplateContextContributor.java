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

package com.liferay.object.dynamic.data.mapping.internal.form.field.type;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.context.path.RESTContextPathResolver;
import com.liferay.object.rest.context.path.RESTContextPathResolverRegistry;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=object-relationship",
	service = {
		DDMFormFieldTemplateContextContributor.class,
		ObjectRelationshipDDMFormFieldTemplateContextContributor.class
	}
)
public class ObjectRelationshipDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		try {
			return HashMapBuilder.<String, Object>put(
				"apiUrl", _getAPIURL(ddmFormField, ddmFormFieldRenderingContext)
			).put(
				"initialLabel", ddmFormFieldRenderingContext.getValue()
			).put(
				"initialValue", ddmFormFieldRenderingContext.getValue()
			).put(
				"inputName", ddmFormField.getName()
			).put(
				"itemsKey", "id"
			).put(
				"itemsLabel", "id"
			).put(
				"value", ddmFormFieldRenderingContext.getValue()
			).build();
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		return Collections.<String, Object>emptyMap();
	}

	protected String getValue(String valueString) {
		try {
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray(valueString);

			return GetterUtil.getString(jsonArray.get(0));
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException, jsonException);
			}
		}

		return valueString;
	}

	private String _getAPIURL(
			DDMFormField ddmFormField,
			DDMFormFieldRenderingContext ddmFormFieldRenderingContext)
		throws PortalException {

		String apiUrl = GetterUtil.getString(
			ddmFormField.getProperty("apiUrl"));

		if (Validator.isNotNull(apiUrl)) {
			return apiUrl;
		}

		String apiURL = _portal.getPortalURL(
			ddmFormFieldRenderingContext.getHttpServletRequest());

		long objectDefinitionId = GetterUtil.getLong(
			getValue(
				GetterUtil.getString(
					ddmFormField.getProperty("objectDefinitionId"))));

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectDefinitionId);

		RESTContextPathResolver restContextPathResolver =
			_restContextPathResolverRegistry.getRESTContextPathResolver(
				objectDefinition.getClassName());

		ObjectScopeProvider objectScopeProvider =
			_objectScopeProviderRegistry.getObjectScopeProvider(
				objectDefinition.getScope());

		String restContextPath = restContextPathResolver.getRESTContextPath(
			objectScopeProvider.getGroupId(
				ddmFormFieldRenderingContext.getHttpServletRequest()));

		return apiURL + restContextPath;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectRelationshipDDMFormFieldTemplateContextContributor.class);

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectScopeProviderRegistry _objectScopeProviderRegistry;

	@Reference
	private Portal _portal;

	@Reference
	private RESTContextPathResolverRegistry _restContextPathResolverRegistry;

}