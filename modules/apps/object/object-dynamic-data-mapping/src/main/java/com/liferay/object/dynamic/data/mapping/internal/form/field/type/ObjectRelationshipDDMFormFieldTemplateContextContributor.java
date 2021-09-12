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
import com.liferay.object.rest.context.RESTContextPathResolver;
import com.liferay.object.rest.context.RESTContextPathResolverRegistry;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;

import java.util.HashMap;
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

		long objectDefinitionId = GetterUtil.getLong(
			ddmFormField.getProperty("objectDefinitionId"));

		try {
			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.getObjectDefinition(
					objectDefinitionId);

			ObjectScopeProvider objectScopeProvider =
				_objectScopeProviderRegistry.getObjectScopeProvider(
					objectDefinition.getScope());

			String apiUrl = _portal.getPortalURL(
				ddmFormFieldRenderingContext.getHttpServletRequest());

			RESTContextPathResolver restContextPathResolver =
				_restContextPathResolverRegistry.getRESTContextPathResolver(
					objectDefinition.getClassName());

			String restContextPath = restContextPathResolver.getRESTContextPath(
				objectScopeProvider.getGroupId(
					ddmFormFieldRenderingContext.getHttpServletRequest()));

			apiUrl = apiUrl + restContextPath;

			return HashMapBuilder.<String, Object>put(
				"apiUrl", apiUrl
			).put(
				"inputName", ddmFormField.getName()
			).put(
				"itemsKey", "id"
			).put(
				"itemsLabel", "id"
			).build();
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		return new HashMap<>();
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