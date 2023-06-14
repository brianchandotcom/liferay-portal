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

import com.liferay.headless.builder.internal.constants.HeadlessBuilderConstants;
import com.liferay.headless.builder.internal.exception.InvalidBaseURLException;
import com.liferay.headless.builder.internal.validator.HeadlessBuilderValidator;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio Jiménez del Coso
 */
@Component(service = ModelListener.class)
public class APIApplicationModelListener
	extends BaseModelListener<ObjectEntry> {

	@Override
	public void onBeforeCreate(ObjectEntry objectEntry)
		throws ModelListenerException {

		_validate(objectEntry);
	}

	@Override
	public void onBeforeUpdate(
			ObjectEntry originalObjectEntry, ObjectEntry objectEntry)
		throws ModelListenerException {

		_validate(objectEntry);
	}

	private void _validate(ObjectEntry objectEntry) {
		try {
			Map<String, Serializable> objectEntryValues =
				objectEntry.getValues();

			if (_validObjectDefinition(objectEntry) &&
				!HeadlessBuilderValidator.validatePath(
					(String)objectEntryValues.get(
						HeadlessBuilderConstants.
							API_APPLICATION_BASE_URL_FIELD_NAME))) {

				throw new InvalidBaseURLException(
					"Base URL should not have blank spaces and special " +
						"characters with a maximum of 255 characters");
			}
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private boolean _validObjectDefinition(ObjectEntry objectEntry) {
		ObjectDefinition apiApplicationObjectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				objectEntry.getObjectDefinitionId());

		if (Objects.equals(
				apiApplicationObjectDefinition.getExternalReferenceCode(),
				HeadlessBuilderConstants.API_APPLICATION_ERC)) {

			return true;
		}

		return false;
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}