/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.planner.rest.internal.vulcan.batch.engine;

import com.liferay.batch.planner.rest.internal.vulcan.yaml.openapi.OpenAPIYAMLProvider;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.openapi.v1_0.ObjectEntryOpenAPIResource;
import com.liferay.object.rest.openapi.v1_0.ObjectEntryOpenAPIResourceProvider;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.batch.engine.Field;
import com.liferay.portal.vulcan.util.OpenAPIUtil;
import com.liferay.portal.vulcan.yaml.openapi.OpenAPIYAML;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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

				if (name.startsWith("x-")) {
					return false;
				}

				return true;
			});
	}

	public List<Field> getFields(
			long companyId, String internalClassNameKey, UriInfo uriInfo)
		throws Exception {

		int index = internalClassNameKey.indexOf(StringPool.POUND);

		if (index < 0) {
			OpenAPIYAML openAPIYAML = _openAPIYAMLProvider.getOpenAPIYAML(
				companyId, internalClassNameKey);

			return ListUtil.fromMapValues(
				OpenAPIUtil.getDTOEntityFields(
					StringUtil.extractLast(
						internalClassNameKey, StringPool.PERIOD),
					openAPIYAML));
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				companyId, internalClassNameKey.substring(index + 1));

		ObjectEntryOpenAPIResource objectEntryOpenAPIResource =
			_objectEntryOpenAPIResourceProvider.getObjectEntryOpenAPIResource(
				objectDefinition);

		Map<String, Field> fields = objectEntryOpenAPIResource.getFields(
			uriInfo);

		return TransformUtil.transform(
			fields.values(),
			field -> {
				if ((Objects.equals(field.getType(), "array") ||
					 Objects.equals(field.getType(), "object")) &&
					!Validator.isBlank(field.getRef())) {

					return null;
				}

				return field;
			});
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryOpenAPIResourceProvider
		_objectEntryOpenAPIResourceProvider;

	@Reference
	private OpenAPIYAMLProvider _openAPIYAMLProvider;

}