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
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.util.HashSet;
import java.util.Map;
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

				_unPublishAPIApplication(
					_objectEntryLocalService.getObjectEntry(
						objectEntry.getObjectEntryId()));
			}
			else {
				_schedulePublication(objectEntry);
			}
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private Long _getAPIApplicationId(ObjectEntry objectEntry) {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				objectEntry.getObjectDefinitionId());

		String externalReferenceCode =
			objectDefinition.getExternalReferenceCode();

		if (StringUtil.equals(externalReferenceCode, "L_API_APPLICATION")) {
			return objectEntry.getObjectEntryId();
		}
		else if (StringUtil.equals(externalReferenceCode, "L_API_SCHEMA")) {
			Map<String, Serializable> values = objectEntry.getValues();

			return (long)values.get(
				"r_apiApplicationToAPISchemas_c_apiApplicationId");
		}
		else if (StringUtil.equals(externalReferenceCode, "L_API_ENDPOINT")) {
			Map<String, Serializable> values = objectEntry.getValues();

			return (long)values.get(
				"r_apiApplicationToAPIEndpoints_c_apiApplicationId");
		}
		else if (StringUtil.equals(externalReferenceCode, "L_API_FILTER")) {
			Map<String, Serializable> apiFilterObjectEntryValues =
				objectEntry.getValues();

			ObjectEntry apiEndpointObjectEntry =
				_objectEntryLocalService.fetchObjectEntry(
					(long)apiFilterObjectEntryValues.get(
						"r_apiEndpointToAPIFilters_c_apiEndpointId"));

			Map<String, Serializable> apiEndpointObjectEntryValues =
				apiEndpointObjectEntry.getValues();

			return (long)apiEndpointObjectEntryValues.get(
				"r_apiApplicationToAPIEndpoints_c_apiApplicationId");
		}
		else if (StringUtil.equals(externalReferenceCode, "L_API_SORT")) {
			Map<String, Serializable> apiSortObjectEntryValues =
				objectEntry.getValues();

			ObjectEntry apiEndpointObjectEntry =
				_objectEntryLocalService.fetchObjectEntry(
					(long)apiSortObjectEntryValues.get(
						"r_apiEndpointToAPISorts_c_apiEndpointId"));

			Map<String, Serializable> apiEndpointObjectEntryValues =
				apiEndpointObjectEntry.getValues();

			return (long)apiEndpointObjectEntryValues.get(
				"r_apiApplicationToAPIEndpoints_c_apiApplicationId");
		}

		if (_log.isDebugEnabled()) {
			_log.debug("No API Application ID is available");
		}

		return null;
	}

	private synchronized void _schedulePublication(ObjectEntry objectEntry) {
		Long apiApplicationId = _getAPIApplicationId(objectEntry);

		if (apiApplicationId == null) {
			return;
		}

		_pendingAPIApplications.add(apiApplicationId);

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				if (_pendingAPIApplications.remove(apiApplicationId)) {
					ObjectEntry apiApplicationObjectEntry =
						_objectEntryLocalService.getObjectEntry(
							apiApplicationId);

					Map<String, Serializable> values =
						apiApplicationObjectEntry.getValues();

					APIApplication apiApplication =
						_apiApplicationProvider.fetchAPIApplication(
							(String)values.get("baseURL"),
							apiApplicationObjectEntry.getCompanyId());

					_apiApplicationPublisher.unpublish(apiApplication);

					if (StringUtil.equals(
							(String)values.get("applicationStatus"),
							"published")) {

						_apiApplicationPublisher.publish(apiApplication);
					}
				}

				return null;
			});
	}

	private void _unPublishAPIApplication(ObjectEntry apiApplicationObjectEntry)
		throws Exception {

		Map<String, Serializable> apiApplicationObjectEntryValues =
			apiApplicationObjectEntry.getValues();

		APIApplication apiApplication =
			_apiApplicationProvider.fetchAPIApplication(
				(String)apiApplicationObjectEntryValues.get("baseURL"),
				apiApplicationObjectEntry.getCompanyId());

		_apiApplicationPublisher.unpublish(apiApplication);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		HeadlessBuilderObjectEntryModelListener.class);

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