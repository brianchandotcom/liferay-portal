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

package com.liferay.batch.planner.rest.internal.vulcan.batch.engine;

import com.liferay.batch.planner.rest.internal.vulcan.yaml.openapi.OpenAPIYAMLProvider;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.openapi.v1_0.ObjectEntryOpenAPIResource;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.vulcan.batch.engine.Field;
import com.liferay.portal.vulcan.util.OpenAPIUtil;
import com.liferay.portal.vulcan.yaml.openapi.OpenAPIYAML;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matija Petanjek
 */
@Component(service = FieldProvider.class)
public class FieldProvider {

	public List<Field> filter(
		List<Field> fields, Field.AccessType ignoredAccessType) {

		return ListUtil.filter(
			fields,
			dtoEntityField -> {
				if (dtoEntityField.getAccessType() == ignoredAccessType) {
					return false;
				}

				String name = dtoEntityField.getName();

				if (name.equals("actions") || name.startsWith("x-")) {
					return false;
				}

				return true;
			});
	}

	public List<Field> getFields(
			long companyId, String objectDefinitionName, UriInfo uriInfo)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				companyId, objectDefinitionName);

		Map<String, Field> fields = _objectEntryOpenAPIResource.getFields(
			objectDefinition.getObjectDefinitionId(), uriInfo);

		return new ArrayList<>(fields.values());
	}

	public List<Field> getFields(String internalClassName) throws Exception {
		OpenAPIYAML openAPIYAML = _openAPIYAMLProvider.getOpenAPIYAML(
			internalClassName);

		Map<String, Field> dtoEntityFields = OpenAPIUtil.getDTOEntityFields(
			internalClassName.substring(
				internalClassName.lastIndexOf(StringPool.PERIOD) + 1),
			openAPIYAML);

		return new ArrayList<>(dtoEntityFields.values());
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryOpenAPIResource _objectEntryOpenAPIResource;

	@Reference
	private OpenAPIYAMLProvider _openAPIYAMLProvider;

}