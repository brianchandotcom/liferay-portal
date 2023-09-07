/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.model.listener;

import com.liferay.headless.builder.internal.helper.ObjectEntryHelper;
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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

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

		if (!_equals(
				originalObjectEntry.getValues(), objectEntry.getValues(),
				"httpMethod", "path",
				"r_apiApplicationToAPIEndpoints_c_apiApplicationId",
				"r_requestAPISchemaToAPIEndpoints_c_apiSchemaId",
				"r_responseAPISchemaToAPIEndpoints_c_apiSchemaId")) {

			_validate(objectEntry);
		}
	}

	private boolean _equals(
		Map<String, Serializable> map1, Map<String, Serializable> map2,
		String... keys) {

		for (String key : keys) {
			if (!Objects.equals(map1.get(key), map2.get(key))) {
				return false;
			}
		}

		return true;
	}

	private void _validate(ObjectEntry objectEntry) {
		try {
			Map<String, Serializable> values = objectEntry.getValues();

			String pathString = (String)values.get("path");

			if (StringUtil.equals(
					(String)values.get("retrieveType"), "singleElement")) {

				_validateSingleElementPath(
					objectEntry, (String)values.get("pathParameter"),
					pathString);
			}
			else {
				Matcher matcher = _pathPattern.matcher(pathString);

				if (!matcher.matches()) {
					User user = _userLocalService.getUser(
						objectEntry.getUserId());

					ObjectField objectField =
						_objectFieldLocalService.getObjectField(
							objectEntry.getObjectDefinitionId(), "path");

					String message = null;
					String messageKey = null;

					if (pathString.startsWith(StringPool.FORWARD_SLASH)) {
						message =
							"%s can have a maximum of 255 alphanumeric " +
								"characters";
						messageKey =
							"x-can-have-a-maximum-of-255-alphanumeric-" +
								"characters";
					}
					else {
						message = "%s must start with the \"/\" character";
						messageKey = "x-must-start-with-the-x-character";
					}

					String label = objectField.getLabel(user.getLocale());

					throw new ObjectEntryValuesException.InvalidObjectField(
						Arrays.asList(label, "\"/\""),
						String.format(message, label), messageKey);
				}
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
				filterString, apiEndpointObjectDefinition);

			List<Map<String, Serializable>> valuesList =
				_objectEntryLocalService.getValuesList(
					objectEntry.getGroupId(), objectEntry.getCompanyId(),
					objectEntry.getUserId(),
					objectEntry.getObjectDefinitionId(), predicate, null, -1,
					-1, null);

			if (!valuesList.isEmpty()) {
				throw new ObjectEntryValuesException.InvalidObjectField(
					null,
					"There is an API endpoint with the same HTTP method and " +
						"path",
					"there-is-an-api-endpoint-with-the-same-http-method-and-" +
						"path");
			}

			long apiApplicationId = (long)values.get(
				"r_apiApplicationToAPIEndpoints_c_apiApplicationId");

			if (!_objectEntryHelper.isValidObjectEntry(
					apiApplicationId, "L_API_APPLICATION")) {

				throw new ObjectEntryValuesException.InvalidObjectField(
					null,
					"An API endpoint must be related to an API application",
					"an-api-endpoint-must-be-related-to-an-api-application");
			}

			long requestAPISchemaId = (long)values.get(
				"r_requestAPISchemaToAPIEndpoints_c_apiSchemaId");

			if (requestAPISchemaId != 0) {
				_validateAPISchema(apiApplicationId, requestAPISchemaId);
			}

			long responseAPISchemaId = (long)values.get(
				"r_responseAPISchemaToAPIEndpoints_c_apiSchemaId");

			if (responseAPISchemaId != 0) {
				_validateAPISchema(apiApplicationId, responseAPISchemaId);
			}
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private void _validateAPISchema(long apiApplicationId, long apiSchemaId)
		throws Exception {

		ObjectEntry objectEntry = _objectEntryLocalService.fetchObjectEntry(
			apiSchemaId);

		if (objectEntry == null) {
			throw new ObjectEntryValuesException.InvalidObjectField(
				null, "An API endpoint must be related to an API schema",
				"an-api-endpoint-must-be-related-to-an-api-schema");
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectEntry.getObjectDefinitionId());

		if (!Objects.equals(
				objectDefinition.getExternalReferenceCode(), "L_API_SCHEMA")) {

			throw new ObjectEntryValuesException.InvalidObjectField(
				null, "An API endpoint must be related to an API schema",
				"an-api-endpoint-must-be-related-to-an-api-schema");
		}

		Map<String, Serializable> apiSchemaValues = objectEntry.getValues();

		if (!Objects.equals(
				apiApplicationId,
				apiSchemaValues.get(
					"r_apiApplicationToAPISchemas_c_apiApplicationId"))) {

			throw new ObjectEntryValuesException.InvalidObjectField(
				null,
				"The API endpoint and the API schema must be related to the " +
					"same API Application",
				"the-api-endpoint-and-the-api-schema-must-be-related-to-the-" +
					"same-api-application");
		}
	}

	private void _validateSingleElementPath(
			ObjectEntry objectEntry, String pathParameterString,
			String pathString)
		throws Exception {

		if (Validator.isNull(pathParameterString)) {
			throw new ObjectEntryValuesException.InvalidObjectField(
				null,
				"Path parameter cannot be null in a single element endpoint",
				"path-parameter-cannot-be-null-in-a-single-element-endpoint");
		}

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectEntry.getObjectDefinitionId(), "path");

		User user = _userLocalService.getUser(objectEntry.getUserId());

		if (!pathString.startsWith(StringPool.FORWARD_SLASH)) {
			throw new ObjectEntryValuesException.InvalidObjectField(
				Arrays.asList(objectField.getLabel(user.getLocale()), "\"/\""),
				"%s must start with the \"/\" character",
				"x-must-start-with-the-x-character");
		}

		String pathInParameterString = StringUtil.extractLast(
			pathString, StringPool.FORWARD_SLASH);

		Matcher individualMatcher = _individualPathPattern.matcher(pathString);

		if (!individualMatcher.matches()) {
			throw new ObjectEntryValuesException.InvalidObjectField(
				Arrays.asList(objectField.getLabel(user.getLocale())),
				"%s can have a maximum of 255 alphanumeric characters",
				"x-can-have-a-maximum-of-255-alphanumeric-characters");
		}

		Matcher curlyBraceMatcher = _curlyBracePattern.matcher(
			pathInParameterString);

		if (!curlyBraceMatcher.matches()) {
			throw new ObjectEntryValuesException.InvalidObjectField(
				Arrays.asList(objectField.getLabel(user.getLocale())),
				"%s must contain a path parameter between curly braces",
				"x-must-contain-a-path-parameter-between-curly-braces");
		}
	}

	private static final Pattern _curlyBracePattern = Pattern.compile(
		"^\\{[a-zA-Z0-9]+\\}$");
	private static final Pattern _individualPathPattern = Pattern.compile(
		"/[a-zA-Z0-9][a-zA-Z0-9-/-{\\-}]{1,253}");
	private static final Pattern _pathPattern = Pattern.compile(
		"/[a-zA-Z0-9][a-zA-Z0-9-/]{1,253}");

	@Reference(
		target = "(filter.factory.key=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT + ")"
	)
	private FilterFactory<Predicate> _filterFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryHelper _objectEntryHelper;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private UserLocalService _userLocalService;

}