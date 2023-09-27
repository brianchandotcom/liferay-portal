/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.system.model.listener;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.object.action.engine.ObjectActionEngine;
import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.entry.util.ObjectEntryThreadLocal;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectValidationRuleLocalService;
import com.liferay.object.system.JaxRsApplicationDescriptor;
import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
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
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.extension.EntityExtensionThreadLocal;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Brian Wing Shun Chan
 */
public class SystemObjectDefinitionManagerModelListener<T extends BaseModel<T>>
	extends BaseModelListener<T> {

	public SystemObjectDefinitionManagerModelListener(
		DDMExpressionFactory ddmExpressionFactory,
		DTOConverterRegistry dtoConverterRegistry, JSONFactory jsonFactory,
		Class<T> modelClass, ObjectActionEngine objectActionEngine,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectValidationRuleLocalService objectValidationRuleLocalService,
		SystemObjectDefinitionManager systemObjectDefinitionManager,
		UserLocalService userLocalService) {

		_ddmExpressionFactory = ddmExpressionFactory;
		_dtoConverterRegistry = dtoConverterRegistry;
		_jsonFactory = jsonFactory;
		_modelClass = modelClass;
		_objectActionEngine = objectActionEngine;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectEntryLocalService = objectEntryLocalService;
		_objectFieldLocalService = objectFieldLocalService;
		_objectValidationRuleLocalService = objectValidationRuleLocalService;
		_systemObjectDefinitionManager = systemObjectDefinitionManager;
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
			ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE, baseModel,
			baseModel);
	}

	@Override
	public void onAfterUpdate(T originalBaseModel, T baseModel)
		throws ModelListenerException {

		_executeObjectActions(
			ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE, originalBaseModel,
			(T)baseModel.clone());
	}

	@Override
	public void onBeforeCreate(T model) throws ModelListenerException {
		_validateSystemObject(null, model);
	}

	@Override
	public void onBeforeRemove(T baseModel) throws ModelListenerException {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setProductionModeWithSafeCloseable()) {

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinitionByClassName(
					_getCompanyId(baseModel), _modelClass.getName());

			if (objectDefinition == null) {
				return;
			}

			EntityExtensionThreadLocal.setExtendedProperties(
				HashMapBuilder.putAll(
					_objectEntryLocalService.
						getExtensionDynamicObjectDefinitionTableValues(
							objectDefinition,
							GetterUtil.getLong(baseModel.getPrimaryKeyObj()))
				).putAll(
					EntityExtensionThreadLocal.getExtendedProperties()
				).build());

			_objectEntryLocalService.
				deleteExtensionDynamicObjectDefinitionTableValues(
					objectDefinition,
					GetterUtil.getLong(baseModel.getPrimaryKeyObj()));
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	@Override
	public void onBeforeUpdate(T originalModel, T model)
		throws ModelListenerException {

		_validateSystemObject(originalModel, model);
	}

	private void _executeObjectActions(
			String objectActionTriggerKey, T originalBaseModel, T baseModel)
		throws ModelListenerException {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setProductionModeWithSafeCloseable()) {

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinitionByClassName(
					_getCompanyId(baseModel), _modelClass.getName());

			if (objectDefinition == null) {
				return;
			}

			long userId = PrincipalThreadLocal.getUserId();

			if (userId == 0) {
				userId = _getUserId(baseModel);
			}

			_objectActionEngine.executeObjectActions(
				_modelClass.getName(), _getCompanyId(baseModel),
				objectActionTriggerKey,
				_getPayloadJSONObject(
					objectActionTriggerKey, objectDefinition, originalBaseModel,
					baseModel, userId),
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

	private DTOConverter<T, ?> _getDTOConverter() {
		JaxRsApplicationDescriptor jaxRsApplicationDescriptor =
			_systemObjectDefinitionManager.getJaxRsApplicationDescriptor();

		return (DTOConverter<T, ?>)_dtoConverterRegistry.getDTOConverter(
			jaxRsApplicationDescriptor.getApplicationName(),
			_modelClass.getName(), jaxRsApplicationDescriptor.getVersion());
	}

	private String _getDTOConverterType() {
		DTOConverter<T, ?> dtoConverter = _getDTOConverter();

		if (dtoConverter == null) {
			return _modelClass.getSimpleName();
		}

		return dtoConverter.getContentType();
	}

	private JSONObject _getPayloadJSONObject(
			String objectActionTriggerKey, ObjectDefinition objectDefinition,
			T originalBaseModel, T baseModel, long userId)
		throws PortalException {

		String dtoConverterType = _getDTOConverterType();

		return JSONUtil.put(
			"classPK", baseModel.getPrimaryKeyObj()
		).put(
			"extendedProperties",
			HashMapBuilder.<String, Object>putAll(
				_objectEntryLocalService.
					getExtensionDynamicObjectDefinitionTableValues(
						objectDefinition,
						GetterUtil.getLong(baseModel.getPrimaryKeyObj()))
			).putAll(
				EntityExtensionThreadLocal.getExtendedProperties()
			).build()
		).put(
			"model" + _modelClass.getSimpleName(),
			baseModel.getModelAttributes()
		).put(
			"modelDTO" + dtoConverterType, _toDTO(baseModel, userId)
		).put(
			"objectActionTriggerKey", objectActionTriggerKey
		).put(
			"original" + _modelClass.getSimpleName(),
			() -> {
				if (originalBaseModel == null) {
					return null;
				}

				return originalBaseModel.getModelAttributes();
			}
		).put(
			"originalDTO" + dtoConverterType,
			() -> {
				if (originalBaseModel == null) {
					return null;
				}

				return _toDTO(originalBaseModel, userId);
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

	private Map<String, Object> _toDTO(T baseModel, long userId) {
		DTOConverter<T, ?> dtoConverter = _getDTOConverter();

		Map<String, Object> modelAttributes = baseModel.getModelAttributes();

		if (dtoConverter == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No DTO converter found for " + _modelClass.getName());
			}

			return modelAttributes;
		}

		User user = _userLocalService.fetchUser(userId);

		if (user == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No user found with user ID " + userId);
			}

			return modelAttributes;
		}

		DefaultDTOConverterContext defaultDTOConverterContext =
			new DefaultDTOConverterContext(
				false, Collections.emptyMap(), _dtoConverterRegistry,
				baseModel.getPrimaryKeyObj(), user.getLocale(), null, user);

		try {
			Object object = dtoConverter.toDTO(defaultDTOConverterContext);

			if (object == null) {
				return modelAttributes;
			}

			JSONObject jsonObject = _jsonFactory.createJSONObject(
				_jsonFactory.looseSerializeDeep(object));

			return jsonObject.put(
				"createDate", modelAttributes.get("createDate")
			).put(
				"modifiedDate", modelAttributes.get("modifiedDate")
			).put(
				"status", modelAttributes.get("status")
			).put(
				"userName", user.getFullName()
			).put(
				"uuid", modelAttributes.get("uuid")
			).toMap();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return baseModel.getModelAttributes();
	}

	private void _validateReadOnlyObjectFields(
			T originalModel, T model, ObjectDefinition objectDefinition)
		throws PortalException {

		if (EntityExtensionThreadLocal.getExtendedProperties() == null) {
			return;
		}

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(
				objectDefinition.getObjectDefinitionId());
		Map<String, Object> extendedProperties = new HashMap<>(
			EntityExtensionThreadLocal.getExtendedProperties());

		if (originalModel == null) {
			ObjectFieldUtil.validateReadOnlyObjectFields(
				_ddmExpressionFactory, new HashMap<>(), objectFields,
				extendedProperties);

			ObjectEntryThreadLocal.setSkipReadOnlyObjectFieldsValidation(true);

			return;
		}

		ObjectFieldUtil.validateReadOnlyObjectFields(
			_ddmExpressionFactory,
			HashMapBuilder.putAll(
				originalModel.getModelAttributes()
			).putAll(
				_objectEntryLocalService.
					getExtensionDynamicObjectDefinitionTableValues(
						objectDefinition,
						GetterUtil.getLong(model.getPrimaryKeyObj()))
			).build(),
			objectFields, extendedProperties);

		ObjectEntryThreadLocal.setSkipReadOnlyObjectFieldsValidation(true);
	}

	private void _validateSystemObject(T originalModel, T model)
		throws ModelListenerException {

		try {
			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinitionByClassName(
					_getCompanyId(model), _modelClass.getName());

			if (objectDefinition == null) {
				return;
			}

			long userId = PrincipalThreadLocal.getUserId();

			if (userId == 0) {
				userId = _getUserId(model);
			}

			_validateReadOnlyObjectFields(
				originalModel, model, objectDefinition);

			int count =
				_objectValidationRuleLocalService.getObjectValidationRulesCount(
					objectDefinition.getObjectDefinitionId(), true);

			if (count > 0) {
				_objectValidationRuleLocalService.validate(
					model, objectDefinition.getObjectDefinitionId(),
					_getPayloadJSONObject(
						null, objectDefinition, originalModel, model, userId),
					userId);
			}
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SystemObjectDefinitionManagerModelListener.class);

	private final DDMExpressionFactory _ddmExpressionFactory;
	private final DTOConverterRegistry _dtoConverterRegistry;
	private final JSONFactory _jsonFactory;
	private final Class<?> _modelClass;
	private final ObjectActionEngine _objectActionEngine;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectValidationRuleLocalService
		_objectValidationRuleLocalService;
	private final SystemObjectDefinitionManager _systemObjectDefinitionManager;
	private final UserLocalService _userLocalService;

}