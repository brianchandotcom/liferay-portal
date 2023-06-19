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

import com.liferay.object.exception.ObjectEntryValuesException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.listener.RelevantObjectEntryModelListener;
import com.liferay.object.rest.petra.sql.dsl.expression.FilterPredicateFactory;
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

		if (_isModified(originalObjectEntry, objectEntry)) {
			_validate(objectEntry);
		}
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
		ObjectDefinition apiEndpointObjectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				objectEntry.getObjectDefinitionId());

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

			Predicate predicate = _filterPredicateFactory.create(
				filterString,
				apiEndpointObjectDefinition.getObjectDefinitionId());

			List<Map<String, Serializable>> valuesList =
				_objectEntryLocalService.getValuesList(
					objectEntry.getGroupId(), objectEntry.getCompanyId(),
					objectEntry.getUserId(),
					apiEndpointObjectDefinition.getObjectDefinitionId(),
					predicate, null, -1, -1, null);

			if (!valuesList.isEmpty()) {
				throw new ObjectEntryValuesException.InvalidObjectField(
					"There is an API endpoint with the same HTTP method and " +
						"path",
					"there-is-an-api-endpoint-with-the-same-http-method-and-" +
						"path",
					null);
			}

			if ((long)values.get(
					"r_apiApplicationToAPIEndpoints_c_apiApplicationId") == 0) {

				throw new ObjectEntryValuesException.InvalidObjectField(
					"An API endpoint must be related to an API application",
					"an-api-endpoint-must-be-related-to-an-api-application",
					null);
			}
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private static final Pattern _pathPattern = Pattern.compile(
		"[a-zA-Z0-9-/]{1,255}");

	@Reference
	private FilterPredicateFactory _filterPredicateFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private UserLocalService _userLocalService;

}