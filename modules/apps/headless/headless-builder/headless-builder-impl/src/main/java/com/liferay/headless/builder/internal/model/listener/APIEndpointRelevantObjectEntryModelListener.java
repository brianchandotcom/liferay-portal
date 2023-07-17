/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.model.listener;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.exception.ObjectEntryValuesException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.listener.RelevantObjectEntryModelListener;
import com.liferay.object.rest.filter.factory.FilterFactory;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import java.io.Serializable;

import java.util.Arrays;
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
@Component(service = RelevantObjectEntryModelListener.class)
public class APIEndpointRelevantObjectEntryModelListener
	extends BaseModelListener<ObjectEntry>
	implements RelevantObjectEntryModelListener {

	@Override
	public String getObjectDefinitionExternalReferenceCode() {
		return "L_API_ENDPOINT";
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

		if (_isModified(originalObjectEntry, objectEntry)) {
			_validate(objectEntry);
		}
	}

	private boolean _isAPIApplication(long apiApplicationId) throws Exception {
		if (apiApplicationId == 0) {
			return false;
		}

		ObjectEntry objectEntry = _objectEntryLocalService.fetchObjectEntry(
			apiApplicationId);

		if (objectEntry == null) {
			return false;
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectEntry.getObjectDefinitionId());

		if (!Objects.equals(
				objectDefinition.getExternalReferenceCode(),
				"L_API_APPLICATION")) {

			return false;
		}

		return true;
	}

	private boolean _isAPISchema(long apiSchemaId) throws Exception {
		ObjectEntry objectEntry = _objectEntryLocalService.fetchObjectEntry(
			apiSchemaId);

		if (objectEntry == null) {
			return false;
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectEntry.getObjectDefinitionId());

		if (!Objects.equals(
				objectDefinition.getExternalReferenceCode(), "L_API_SCHEMA")) {

			return false;
		}

		return true;
	}

	private boolean _isModified(
		ObjectEntry originalObjectEntry, ObjectEntry objectEntry) {

		Map<String, Serializable> originalValues =
			originalObjectEntry.getValues();
		Map<String, Serializable> values = objectEntry.getValues();

		if (Objects.equals(
				values.get("httpMethod"), originalValues.get("httpMethod")) &&
			Objects.equals(values.get("path"), originalValues.get("path")) &&
			Objects.equals(
				originalValues.get(
					"r_apiApplicationToAPIEndpoints_c_apiApplicationId"),
				values.get(
					"r_apiApplicationToAPIEndpoints_c_apiApplicationId"))) {

			return false;
		}

		return true;
	}

	private void _validate(ObjectEntry objectEntry) {
		try {
			Map<String, Serializable> values = objectEntry.getValues();

			Matcher matcher = _pathPattern.matcher((String)values.get("path"));

			if (!matcher.matches()) {
				User user = _userLocalService.getUser(objectEntry.getUserId());

				ObjectField objectField =
					_objectFieldLocalService.getObjectField(
						objectEntry.getObjectDefinitionId(), "path");

				throw new ObjectEntryValuesException.InvalidObjectField(
					String.format(
						"%s can have a maximum of 255 alphanumeric characters",
						objectField.getLabel(user.getLocale())),
					"x-can-have-a-maximum-of-255-alphanumeric-characters",
					Arrays.asList(objectField.getLabel(user.getLocale())));
			}

			String filterString = StringBundler.concat(
				"id ne '", objectEntry.getObjectEntryId(),
				"' and httpMethod eq '", values.get("httpMethod"),
				"' and path eq '", values.get("path"),
				"' and r_apiApplicationToAPIEndpoints_c_apiApplicationId eq '",
				values.get("r_apiApplicationToAPIEndpoints_c_apiApplicationId"),
				"'");
			ObjectDefinition apiEndpointObjectDefinition =
				_objectDefinitionLocalService.getObjectDefinition(
					objectEntry.getObjectDefinitionId());

			Predicate predicate = _filterFactory.create(
				filterString,
				apiEndpointObjectDefinition.getObjectDefinitionId());

			List<Map<String, Serializable>> valuesList =
				_objectEntryLocalService.getValuesList(
					objectEntry.getGroupId(), objectEntry.getCompanyId(),
					objectEntry.getUserId(),
					objectEntry.getObjectDefinitionId(), predicate, null, -1,
					-1, null);

			if (!valuesList.isEmpty()) {
				throw new ObjectEntryValuesException.InvalidObjectField(
					"There is an API endpoint with the same HTTP method and " +
						"path",
					"there-is-an-api-endpoint-with-the-same-http-method-and-" +
						"path",
					null);
			}

			if (!_isAPIApplication(
					(long)values.get(
						"r_apiApplicationToAPIEndpoints_c_apiApplicationId"))) {

				throw new ObjectEntryValuesException.InvalidObjectField(
					"An API endpoint must be related to an API application",
					"an-api-endpoint-must-be-related-to-an-api-application",
					null);
			}

			long requestAPISchemaId = (long)values.get(
				"r_requestAPISchemaToAPIEndpoints_c_apiSchemaId");

			if ((requestAPISchemaId != 0) &&
				!_isAPISchema(requestAPISchemaId)) {

				throw new ObjectEntryValuesException.InvalidObjectField(
					"An API endpoint must be related to an API schema",
					"an-api-endpoint-must-be-related-to-an-api-schema", null);
			}

			long responseAPISchemaId = (long)values.get(
				"r_responseAPISchemaToAPIEndpoints_c_apiSchemaId");

			if ((responseAPISchemaId != 0) &&
				!_isAPISchema(responseAPISchemaId)) {

				throw new ObjectEntryValuesException.InvalidObjectField(
					"An API endpoint must be related to an API schema",
					"an-api-endpoint-must-be-related-to-an-api-schema", null);
			}
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private static final Pattern _pathPattern = Pattern.compile(
		"^[/][a-zA-Z0-9][a-zA-Z0-9-/]{1,255}");

	@Reference(
		target = "(filter.factory.key=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT + ")"
	)
	private FilterFactory<Predicate> _filterFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private UserLocalService _userLocalService;

}