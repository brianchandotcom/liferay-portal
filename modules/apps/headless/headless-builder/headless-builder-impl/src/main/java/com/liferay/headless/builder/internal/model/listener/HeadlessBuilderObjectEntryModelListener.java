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

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.headless.builder.application.provider.APIApplicationProvider;
import com.liferay.headless.builder.application.publisher.APIApplicationPublisher;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio Jiménez del Coso
 */
@Component(service = ModelListener.class)
public class HeadlessBuilderObjectEntryModelListener
	extends BaseModelListener<ObjectEntry> {

	@Override
	public void onAfterCreate(ObjectEntry objectEntry)
		throws ModelListenerException {

		_schedulePublication(objectEntry);
	}

	@Override
	public void onAfterUpdate(
			ObjectEntry originalObjectEntry, ObjectEntry objectEntry)
		throws ModelListenerException {

		_schedulePublication(objectEntry);
	}

	private long _getAPIApplicationId(ObjectEntry objectEntry) {
		if (_isObjectDefinition("L_API_APPLICATION", objectEntry)) {
			return objectEntry.getObjectEntryId();
		}
		else if (_isObjectDefinition("L_API_SCHEMA", objectEntry)) {
			Map<String, Serializable> values = objectEntry.getValues();

			return (long)values.get(
				"r_apiApplicationToAPISchemas_c_apiApplicationId");
		}
		else if (_isObjectDefinition("L_API_ENDPOINT", objectEntry)) {
			Map<String, Serializable> values = objectEntry.getValues();

			return (long)values.get(
				"r_apiApplicationToAPIEndpoints_c_apiApplicationId");
		}

		throw new UnsupportedOperationException("Invalid object definition");
	}

	private boolean _isObjectDefinition(
		String objectDefinitionExternalReferenceCode, ObjectEntry objectEntry) {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				objectEntry.getObjectDefinitionId());

		return Objects.equals(
			objectDefinition.getExternalReferenceCode(),
			objectDefinitionExternalReferenceCode);
	}

	private void _schedulePublication(ObjectEntry objectEntry) {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				objectEntry.getObjectDefinitionId());

		if (StringUtil.startsWith(
				objectDefinition.getExternalReferenceCode(), "L_API_")) {

			long apiApplicationId = _getAPIApplicationId(objectEntry);

			_pendingAPIApplications.add(apiApplicationId);

			TransactionCommitCallbackUtil.registerCallback(
				() -> {
					if (_pendingAPIApplications.contains(apiApplicationId)) {
						ObjectEntry apiApplicationObjectEntry =
							_objectEntryLocalService.getObjectEntry(
								apiApplicationId);

						Map<String, Serializable> values =
							apiApplicationObjectEntry.getValues();

						APIApplication apiApplication =
							_apiApplicationProvider.fetchAPIApplication(
								(String)values.get("baseURL"),
								apiApplicationObjectEntry.getCompanyId());

						if (StringUtil.equals(
								(String)values.get("applicationStatus"),
								"published")) {

							_apiApplicationPublisher.unpublish(apiApplication);
							_apiApplicationPublisher.publish(apiApplication);
						}
						else {
							_apiApplicationPublisher.unpublish(apiApplication);
						}

						_pendingAPIApplications.remove(apiApplicationId);
					}

					return null;
				});
		}
	}

	@Reference
	private APIApplicationProvider _apiApplicationProvider;

	@Reference
	private APIApplicationPublisher _apiApplicationPublisher;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	private final Set<Long> _pendingAPIApplications = new HashSet<>();

}