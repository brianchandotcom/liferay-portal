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

package com.liferay.object.system.model.listener;

import com.liferay.object.action.engine.ObjectActionEngine;
import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author Brian Wing Shun Chan
 */
public class SystemObjectDefinitionMetadataModelListener<T extends BaseModel<T>>
	extends BaseModelListener<T> {

	public SystemObjectDefinitionMetadataModelListener(
		DTOConverterRegistry dtoConverterRegistry, JSONFactory jsonFactory,
		Class<?> modelClass, ObjectActionEngine objectActionEngine,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectEntryLocalService objectEntryLocalService,
		UserLocalService userLocalService) {

		_dtoConverterRegistry = dtoConverterRegistry;
		_jsonFactory = jsonFactory;
		_modelClass = modelClass;
		_objectActionEngine = objectActionEngine;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectEntryLocalService = objectEntryLocalService;
		_userLocalService = userLocalService;
	}

	@Override
	public Class<?> getModelClass() {
		return _modelClass;
	}

	@Override
	public void onAfterCreate(T baseModel) throws ModelListenerException {
		_executeObjectActions(
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD, null,
			(T)baseModel.clone());
	}

	@Override
	public void onAfterRemove(T baseModel) throws ModelListenerException {
		_executeObjectActions(
			ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE, null, baseModel);
	}

	@Override
	public void onAfterUpdate(T originalBaseModel, T baseModel)
		throws ModelListenerException {

		_executeObjectActions(
			ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE, originalBaseModel,
			(T)baseModel.clone());
	}

	@Override
	public void onBeforeRemove(T baseModel) throws ModelListenerException {
		try {
			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinitionByClassName(
					_getCompanyId(baseModel), _modelClass.getName());

			if (objectDefinition == null) {
				return;
			}

			_objectEntryLocalService.deleteRelatedObjectEntries(
				0, objectDefinition.getObjectDefinitionId(),
				GetterUtil.getLong(baseModel.getPrimaryKeyObj()));
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	private void _executeObjectActions(
			String objectActionTriggerKey, T originalBaseModel, T baseModel)
		throws ModelListenerException {

		try {
			long userId = PrincipalThreadLocal.getUserId();

			if (userId == 0) {
				userId = _getUserId(baseModel);
			}

			_objectActionEngine.executeObjectActions(
				_modelClass.getName(), _getCompanyId(baseModel),
				objectActionTriggerKey,
				_getPayloadJSONObject(
					objectActionTriggerKey, originalBaseModel, baseModel,
					userId),
				userId);
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	private long _getCompanyId(T baseModel) {
		Map<String, Function<Object, Object>> functions =
			(Map<String, Function<Object, Object>>)
				(Map<String, ?>)baseModel.getAttributeGetterFunctions();

		Function<Object, Object> function = functions.get("companyId");

		if (function == null) {
			throw new IllegalArgumentException(
				"Base model does not have a company ID column");
		}

		return (Long)function.apply(baseModel);
	}

	private Optional<DTOConverter<T, ?>> _getDTOConverter(T baseModel) {
		return Optional.ofNullable(
			(DTOConverter<T, ?>)_dtoConverterRegistry.getDTOConverter(
				baseModel.getModelClassName()));
	}

	private String _getExternalModel(T baseModel, long userId) {
		User user = _userLocalService.fetchUser(userId);

		if (user == null) {
			return baseModel.toString();
		}

		Optional<DTOConverter<T, ?>> dtoConverterOptional = _getDTOConverter(
			baseModel);

		if (!dtoConverterOptional.isPresent()) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No DTOConverter found for " +
						baseModel.getModelClassName());
			}

			return baseModel.toString();
		}

		DefaultDTOConverterContext defaultDTOConverterContext =
			new DefaultDTOConverterContext(
				false, Collections.emptyMap(), _dtoConverterRegistry, null,
				user.getLocale(), null, user);

		DTOConverter<T, ?> dtoConverter = dtoConverterOptional.get();

		try {
			Object externalModel = dtoConverter.toDTO(
				defaultDTOConverterContext, baseModel);

			return _jsonFactory.looseSerializeDeep(externalModel);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return baseModel.toString();
	}

	private JSONObject _getPayloadJSONObject(
			String objectActionTriggerKey, T originalBaseModel, T baseModel,
			long userId)
		throws PortalException {

		String dtoConverterType = _getDTOConverter(
			baseModel
		).map(
			DTOConverter::getContentType
		).orElse(
			_modelClass.getSimpleName()
		);

		return JSONUtil.put(
			"modelDTO" + StringUtil.upperCaseFirstLetter(dtoConverterType),
			_jsonFactory.createJSONObject(
				_jsonFactory.serialize(_getExternalModel(baseModel, userId)))
		).put(
			"objectActionTriggerKey", objectActionTriggerKey
		).put(
			"originalDTO" + StringUtil.upperCaseFirstLetter(dtoConverterType),
			() -> {
				if (originalBaseModel != null) {
					return _jsonFactory.createJSONObject(
						_jsonFactory.serialize(
							_getExternalModel(originalBaseModel, userId)));
				}

				return null;
			}
		).put(
			"model" + _modelClass.getSimpleName(),
			() -> {
				if (GetterUtil.getBoolean(
						PropsUtil.get(
							PropsKeys.WEBHOOK_EXPOSE_INTERNAL_MODEL))) {

					return _jsonFactory.createJSONObject(baseModel.toString());
				}

				return null;
			}
		).put(
			"original" + _modelClass.getSimpleName(),
			() -> {
				if (GetterUtil.getBoolean(
						PropsUtil.get(
							PropsKeys.WEBHOOK_EXPOSE_INTERNAL_MODEL)) &&
					(originalBaseModel != null)) {

					return _jsonFactory.createJSONObject(
						originalBaseModel.toString());
				}

				return null;
			}
		);
	}

	private long _getUserId(T baseModel) {
		Map<String, Function<Object, Object>> functions =
			(Map<String, Function<Object, Object>>)
				(Map<String, ?>)baseModel.getAttributeGetterFunctions();

		Function<Object, Object> function = functions.get("userId");

		if (function == null) {
			throw new IllegalArgumentException(
				"Base model does not have a user ID column");
		}

		return (Long)function.apply(baseModel);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SystemObjectDefinitionMetadataModelListener.class);

	private final DTOConverterRegistry _dtoConverterRegistry;
	private final JSONFactory _jsonFactory;
	private final Class<?> _modelClass;
	private final ObjectActionEngine _objectActionEngine;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final UserLocalService _userLocalService;

}