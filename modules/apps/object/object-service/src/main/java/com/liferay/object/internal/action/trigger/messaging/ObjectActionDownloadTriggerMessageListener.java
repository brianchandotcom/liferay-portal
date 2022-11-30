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

package com.liferay.object.internal.action.trigger.messaging;

import com.liferay.object.action.engine.ObjectActionEngine;
import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.util.Collections;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leov
 */
@Component(
	immediate = true,
	property = "destination.name=" + DestinationNames.OBJECT_ENTRY_ATTACHMENT_DOWNLOAD,
	service = MessageListener.class
)
public class ObjectActionDownloadTriggerMessageListener
	extends BaseMessageListener {

	@Activate
	protected void activate(BundleContext bundleContext) {
		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_SYNCHRONOUS,
				DestinationNames.OBJECT_ENTRY_ATTACHMENT_DOWNLOAD);

		Destination destination = _destinationFactory.createDestination(
			destinationConfiguration);

		_serviceRegistration = bundleContext.registerService(
			Destination.class, destination,
			MapUtil.singletonDictionary(
				"destination.name", destination.getName()));
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	@Override
	protected void doReceive(Message message) {
		long companyId = message.getLong("companyId");
		long userId = message.getLong("userId");

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					message.getString("objectDefinition"),
					message.getLong("companyId"));

		_objectActionEngine.executeObjectActions(
			objectDefinition.getClassName(), companyId,
			ObjectActionTriggerConstants.KEY_ON_AFTER_ATTACHMENT_DOWNLOAD,
			_getPayloadJSONObject(
				ObjectActionTriggerConstants.KEY_ON_AFTER_ATTACHMENT_DOWNLOAD,
				null,
				_objectEntryLocalService.fetchObjectEntry(
					message.getString("objectEntry"),
					objectDefinition.getObjectDefinitionId()),
				userId),
			userId);
	}

	private String _getObjectDefinitionShortName(long objectDefinitionId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectDefinitionId);

		return objectDefinition.getShortName();
	}

	private JSONObject _getPayloadJSONObject(
		String objectActionTriggerKey, ObjectEntry originalObjectEntry,
		ObjectEntry objectEntry, long userId) {

		try {
			String objectDefinitionShortName = _getObjectDefinitionShortName(
				objectEntry.getObjectDefinitionId());
			User user = _userLocalService.getUser(userId);

			return JSONUtil.put(
				"classPK", objectEntry.getObjectEntryId()
			).put(
				"objectActionTriggerKey", objectActionTriggerKey
			).put(
				"objectEntry",
				HashMapBuilder.putAll(
					objectEntry.getModelAttributes()
				).put(
					"creator", user.getFullName()
				).put(
					"id", objectEntry.getObjectEntryId()
				).put(
					"values", objectEntry.getValues()
				).build()
			).put(
				"objectEntryDTO" + objectDefinitionShortName,
				_toDTO(objectEntry, user)
			).put(
				"originalObjectEntry",
				() -> {
					if (originalObjectEntry == null) {
						return null;
					}

					return HashMapBuilder.putAll(
						originalObjectEntry.getModelAttributes()
					).put(
						"values", originalObjectEntry.getValues()
					).build();
				}
			).put(
				"originalObjectEntryDTO" + objectDefinitionShortName,
				() -> {
					if (originalObjectEntry == null) {
						return null;
					}

					return _toDTO(originalObjectEntry, user);
				}
			);
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return null;
	}

	private Map<String, Object> _toDTO(ObjectEntry objectEntry, User user)
		throws PortalException {

		DTOConverter<ObjectEntry, ?> dtoConverter =
			(DTOConverter<ObjectEntry, ?>)_dtoConverterRegistry.getDTOConverter(
				ObjectEntry.class.getName());

		if (dtoConverter == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No DTO converter found for " +
						ObjectEntry.class.getName());
			}

			return objectEntry.getModelAttributes();
		}

		DefaultDTOConverterContext defaultDTOConverterContext =
			new DefaultDTOConverterContext(
				false, Collections.emptyMap(), _dtoConverterRegistry, null,
				user.getLocale(), null, user);

		try {
			JSONObject jsonObject = _jsonFactory.createJSONObject(
				_jsonFactory.looseSerializeDeep(
					dtoConverter.toDTO(
						defaultDTOConverterContext, objectEntry)));

			return jsonObject.toMap();
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return objectEntry.getModelAttributes();
	}

	// THIS IS DUPLICATED CODE

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectActionDownloadTriggerMessageListener.class);

	@Reference
	private DestinationFactory _destinationFactory;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectActionEngine _objectActionEngine;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	private ServiceRegistration<Destination> _serviceRegistration;

	@Reference
	private UserLocalService _userLocalService;

}