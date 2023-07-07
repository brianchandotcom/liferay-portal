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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio Jiménez del Coso
 */
@Component(service = ModelListener.class)
public class APIApplicationPublisherObjectEntryModelListener
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

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				objectEntry.getObjectDefinitionId());

		if (StringUtil.equals(
				objectDefinition.getExternalReferenceCode(),
				"L_API_APPLICATION")) {

			Map<String, Serializable> originalValues =
				originalObjectEntry.getValues();
			Map<String, Serializable> values = objectEntry.getValues();

			if (!Objects.equals(
					originalValues.get("baseURL"), values.get("baseURL"))) {

				_apiApplicationPublisher.unpublish(
					(String)originalValues.get("baseURL"),
					objectEntry.getCompanyId());
			}
		}

		_schedulePublication(objectEntry);
	}

	@Override
	public void onBeforeRemove(ObjectEntry objectEntry)
		throws ModelListenerException {

		try {
			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinition(
					objectEntry.getObjectDefinitionId());

			if (StringUtil.equals(
					objectDefinition.getExternalReferenceCode(),
					"L_API_APPLICATION")) {

				Map<String, Serializable> values = objectEntry.getValues();

				_apiApplicationPublisher.unpublish(
					(String)values.get("baseURL"), objectEntry.getCompanyId());
			}
			else {
				_schedulePublication(objectEntry);
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}
	}

	private long _getAPIApplicationId(ObjectEntry objectEntry) {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				objectEntry.getObjectDefinitionId());

		String externalReferenceCode =
			objectDefinition.getExternalReferenceCode();

		if (StringUtil.equals(externalReferenceCode, "L_API_APPLICATION")) {
			return objectEntry.getObjectEntryId();
		}
		else if (StringUtil.equals(externalReferenceCode, "L_API_ENDPOINT")) {
			Map<String, Serializable> values = objectEntry.getValues();

			return GetterUtil.getLong(
				values.get(
					"r_apiApplicationToAPIEndpoints_c_apiApplicationId"));
		}
		else if (StringUtil.equals(externalReferenceCode, "L_API_FILTER")) {
			Map<String, Serializable> apiFilterObjectEntryValues =
				objectEntry.getValues();

			ObjectEntry apiEndpointObjectEntry =
				_objectEntryLocalService.fetchObjectEntry(
					GetterUtil.getLong(
						apiFilterObjectEntryValues.get(
							"r_apiEndpointToAPIFilters_c_apiEndpointId")));

			Map<String, Serializable> apiEndpointObjectEntryValues =
				apiEndpointObjectEntry.getValues();

			return GetterUtil.getLong(
				apiEndpointObjectEntryValues.get(
					"r_apiApplicationToAPIEndpoints_c_apiApplicationId"));
		}
		else if (StringUtil.equals(externalReferenceCode, "L_API_SCHEMA")) {
			Map<String, Serializable> values = objectEntry.getValues();

			return GetterUtil.getLong(
				values.get("r_apiApplicationToAPISchemas_c_apiApplicationId"));
		}
		else if (StringUtil.equals(externalReferenceCode, "L_API_SORT")) {
			Map<String, Serializable> apiSortObjectEntryValues =
				objectEntry.getValues();

			ObjectEntry apiEndpointObjectEntry =
				_objectEntryLocalService.fetchObjectEntry(
					GetterUtil.getLong(
						apiSortObjectEntryValues.get(
							"r_apiEndpointToAPISorts_c_apiEndpointId")));

			Map<String, Serializable> apiEndpointObjectEntryValues =
				apiEndpointObjectEntry.getValues();

			return GetterUtil.getLong(
				apiEndpointObjectEntryValues.get(
					"r_apiApplicationToAPIEndpoints_c_apiApplicationId"));
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"No API Application ID exists for object entry " + objectEntry);
		}

		return 0;
	}

	private void _schedulePublication(ObjectEntry objectEntry) {
		long apiApplicationId = _getAPIApplicationId(objectEntry);

		if (apiApplicationId == 0) {
			return;
		}

		_pendingAPIApplications.add(apiApplicationId);

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				if (_pendingAPIApplications.remove(apiApplicationId)) {
					ObjectEntry apiApplicationObjectEntry =
						_objectEntryLocalService.fetchObjectEntry(
							apiApplicationId);

					if (apiApplicationObjectEntry == null) {
						return null;
					}

					Map<String, Serializable> values =
						apiApplicationObjectEntry.getValues();

					if (StringUtil.equals(
							(String)values.get("applicationStatus"),
							"unpublished")) {

						_apiApplicationPublisher.unpublish(
							(String)values.get("baseURL"),
							apiApplicationObjectEntry.getCompanyId());
					}
					else {
						APIApplication apiApplication =
							_apiApplicationProvider.fetchAPIApplication(
								(String)values.get("baseURL"),
								apiApplicationObjectEntry.getCompanyId());

						if (apiApplication == null) {
							return null;
						}

						_apiApplicationPublisher.publish(apiApplication);
					}
				}

				return null;
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		APIApplicationPublisherObjectEntryModelListener.class);

	@Reference
	private APIApplicationProvider _apiApplicationProvider;

	@Reference
	private APIApplicationPublisher _apiApplicationPublisher;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	private final Set<Long> _pendingAPIApplications =
		new CopyOnWriteArraySet<>();

}