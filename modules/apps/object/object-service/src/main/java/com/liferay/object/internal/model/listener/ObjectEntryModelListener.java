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

package com.liferay.object.internal.model.listener;

import com.liferay.object.action.engine.ObjectActionEngine;
import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectValidationRuleLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
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
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = ModelListener.class)
public class ObjectEntryModelListener extends BaseModelListener<ObjectEntry> {

	@Override
	public void onAfterCreate(ObjectEntry objectEntry)
		throws ModelListenerException {

		_executeObjectActions(
			ObjectActionTriggerConstants.KEY_ON_AFTER_ADD, null, objectEntry);
	}

	@Override
	public void onAfterRemove(ObjectEntry objectEntry)
		throws ModelListenerException {

		_executeObjectActions(
			ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE, null,
			objectEntry);
	}

	@Override
	public void onAfterUpdate(
			ObjectEntry originalObjectEntry, ObjectEntry objectEntry)
		throws ModelListenerException {

		_executeObjectActions(
			ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE,
			originalObjectEntry, objectEntry);
	}

	@Override
	public void onBeforeCreate(ObjectEntry objectEntry)
		throws ModelListenerException {

		try {
			_objectValidationRuleLocalService.validate(
				PrincipalThreadLocal.getUserId(),
				objectEntry.getObjectDefinitionId(), null, objectEntry);
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	@Override
	public void onBeforeUpdate(
			ObjectEntry originalObjectEntry, ObjectEntry objectEntry)
		throws ModelListenerException {

		try {
			_objectValidationRuleLocalService.validate(
				PrincipalThreadLocal.getUserId(),
				objectEntry.getObjectDefinitionId(), originalObjectEntry,
				objectEntry);
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	private void _executeObjectActions(
			String objectActionTriggerKey, ObjectEntry originalObjectEntry,
			ObjectEntry objectEntry)
		throws ModelListenerException {

		try {
			long userId = PrincipalThreadLocal.getUserId();

			if (userId == 0) {
				userId = objectEntry.getUserId();
			}

			_objectActionEngine.executeObjectActions(
				objectEntry.getModelClassName(), objectEntry.getCompanyId(),
				objectActionTriggerKey,
				_getPayloadJSONObject(
					objectActionTriggerKey, originalObjectEntry, objectEntry,
					userId),
				userId);
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	private String _getExternalModel(ObjectEntry objectEntry, User user)
		throws PortalException {

		DTOConverter<ObjectEntry, ?> objectEntryDTOConverter =
			(DTOConverter<ObjectEntry, ?>)_dtoConverterRegistry.getDTOConverter(
				ObjectEntry.class.getName());

		String objectDefinitionLabel = _getObjectDefinitionLabel(
			objectEntry.getObjectDefinitionId(), user.getLocale());

		if (objectEntryDTOConverter == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("No DTOConverter found for " + objectDefinitionLabel);
			}

			return objectEntry.toString();
		}

		DefaultDTOConverterContext defaultDTOConverterContext =
			new DefaultDTOConverterContext(
				false, Collections.emptyMap(), _dtoConverterRegistry, null,
				user.getLocale(), null, user);

		try {
			Object externalModel = objectEntryDTOConverter.toDTO(
				defaultDTOConverterContext, objectEntry);

			return _jsonFactory.looseSerializeDeep(externalModel);
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return objectEntry.toString();
	}

	private String _getObjectDefinitionLabel(
			long objectDefinitionId, Locale locale)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectDefinitionId);

		return objectDefinition.getLabel(locale);
	}

	private JSONObject _getPayloadJSONObject(
			String objectActionTriggerKey, ObjectEntry originalObjectEntry,
			ObjectEntry objectEntry, long userId)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		String objectDefinitionLabel = _getObjectDefinitionLabel(
			objectEntry.getObjectDefinitionId(), user.getLocale());

		return JSONUtil.put(
			"objectEntryDTO" +
				StringUtil.upperCaseFirstLetter(objectDefinitionLabel),
			_jsonFactory.createJSONObject(_getExternalModel(objectEntry, user))
		).put(
			"objectActionTriggerKey", objectActionTriggerKey
		).put(
			"objectEntry",
			() -> {
				if (GetterUtil.getBoolean(
						PropsUtil.get(
							PropsKeys.WEBHOOK_EXPOSE_INTERNAL_MODEL))) {

					return _jsonFactory.createJSONObject(
						objectEntry.toString()
					).put(
						"values", objectEntry.getValues()
					);
				}

				return null;
			}
		).put(
			"originalObjectEntry",
			() -> {
				if (GetterUtil.getBoolean(
						PropsUtil.get(
							PropsKeys.WEBHOOK_EXPOSE_INTERNAL_MODEL)) &&
					(originalObjectEntry != null)) {

					return _jsonFactory.createJSONObject(
						originalObjectEntry.toString()
					).put(
						"values", originalObjectEntry.getValues()
					);
				}

				return null;
			}
		).put(
			"originalObjectEntryDTO" +
				StringUtil.upperCaseFirstLetter(objectDefinitionLabel),
			() -> {
				if (originalObjectEntry != null) {
					return _jsonFactory.createJSONObject(
						_getExternalModel(originalObjectEntry, user));
				}

				return null;
			}
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryModelListener.class);

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectActionEngine _objectActionEngine;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectValidationRuleLocalService _objectValidationRuleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}