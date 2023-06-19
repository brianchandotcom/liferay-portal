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

package com.liferay.headless.builder.internal.model.listener;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.listener.ObjectDefinitionObjectEntryModelListener;
import com.liferay.object.rest.petra.sql.dsl.expression.FilterPredicateFactory;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio Jiménez del Coso
 */
@Component(service = ObjectDefinitionObjectEntryModelListener.class)
public class APIEndpointObjectEntryModelListener
	extends BaseModelListener<ObjectEntry>
	implements ObjectDefinitionObjectEntryModelListener {

	@Override
	public String getObjectDefinitionExternalReferenceCode() {
		return "MSOD_API_ENDPOINT";
	}

	@Override
	public void onBeforeCreate(ObjectEntry objectEntry)
		throws ModelListenerException {

		_validate(objectEntry);
	}

	@Override
	public void onBeforeUpdate(
			ObjectEntry originalObjectEntry, ObjectEntry objectEntry)
		throws ModelListenerException {

		if (_validateNewAPIEndpointValues(originalObjectEntry, objectEntry)) {
			_validate(objectEntry);
		}
	}

	private void _validate(ObjectEntry objectEntry) {
		ObjectDefinition apiEndpointObjectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				objectEntry.getObjectDefinitionId());

		try {
			Map<String, Serializable> objectEntryValues =
				objectEntry.getValues();

			if (!_validateEndpointPath((String)objectEntryValues.get("path"))) {
				throw new IllegalArgumentException(
					"Path can have a maximum of 255 alphanumeric characters");
			}

			String filterString = StringBundler.concat(
				"id ne '", objectEntry.getObjectEntryId(),
				"' and httpMethod eq '", objectEntryValues.get("httpMethod"),
				"' and path eq '", objectEntryValues.get("path"),
				"' and r_apiApplicationToAPIEndpoints_c_apiApplicationId eq '",
				objectEntryValues.get(
					"r_apiApplicationToAPIEndpoints_c_apiApplicationId"),
				"'");

			Predicate predicate = _filterPredicateFactory.create(
				filterString,
				apiEndpointObjectDefinition.getObjectDefinitionId());

			List<Map<String, Serializable>> definedAPIEndpointList =
				_objectEntryLocalService.getValuesList(
					objectEntry.getGroupId(), objectEntry.getCompanyId(),
					objectEntry.getUserId(),
					apiEndpointObjectDefinition.getObjectDefinitionId(),
					predicate, null, -1, -1, null);

			if (!definedAPIEndpointList.isEmpty()) {
				throw new IllegalArgumentException(
					"There is an endpoint with the same http method and path " +
						"combination");
			}

			if ((long)objectEntryValues.get(
					"r_apiApplicationToAPIEndpoints_c_apiApplicationId") == 0) {

				throw new IllegalArgumentException(
					"An endpoint must be related to an application");
			}
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private boolean _validateEndpointPath(String path) {
		Matcher matcher = _baseEndpointPathPattern.matcher(path);

		return matcher.matches();
	}

	private boolean _validateNewAPIEndpointValues(
		ObjectEntry originalObjectEntry, ObjectEntry objectEntry) {

		Map<String, Serializable> objectEntryValues = objectEntry.getValues();
		Map<String, Serializable> originalObjectEntryValues =
			originalObjectEntry.getValues();

		if (Objects.equals(
				objectEntryValues.get("httpMethod"),
				originalObjectEntryValues.get("httpMethod")) &&
			Objects.equals(
				objectEntryValues.get("path"),
				originalObjectEntryValues.get("path")) &&
			Objects.equals(
				objectEntryValues.get(
					"r_apiApplicationToAPIEndpoints_c_apiApplicationId"),
				originalObjectEntryValues.get(
					"r_apiApplicationToAPIEndpoints_c_apiApplicationId"))) {

			return false;
		}

		return true;
	}

	private static final Pattern _baseEndpointPathPattern = Pattern.compile(
		"[a-zA-Z0-9-/]{1,255}");

	@Reference
	private FilterPredicateFactory _filterPredicateFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}