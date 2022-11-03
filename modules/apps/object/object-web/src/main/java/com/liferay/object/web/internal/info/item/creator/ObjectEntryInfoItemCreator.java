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

package com.liferay.object.web.internal.info.item.creator;

import com.liferay.info.exception.InfoFormException;
import com.liferay.info.exception.InfoFormValidationException;
import com.liferay.info.exception.NoSuchFormVariationException;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.creator.InfoItemCreator;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.exception.ObjectEntryValuesException;
import com.liferay.object.exception.ObjectValidationRuleEngineException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;
import com.liferay.object.service.ObjectFieldSettingLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.util.GroupUtil;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Rubén Pulido
 */
public class ObjectEntryInfoItemCreator
	implements InfoItemCreator<ObjectEntry> {

	public ObjectEntryInfoItemCreator(
		GroupLocalService groupLocalService,
		InfoItemFormProvider<ObjectEntry> infoItemFormProvider,
		ObjectDefinition objectDefinition,
		ObjectEntryService objectEntryService,
		ObjectScopeProviderRegistry objectScopeProviderRegistry) {

		_groupLocalService = groupLocalService;
		_infoItemFormProvider = infoItemFormProvider;
		_objectDefinition = objectDefinition;
		_objectEntryService = objectEntryService;
		_objectScopeProviderRegistry = objectScopeProviderRegistry;
	}

	@Override
	public ObjectEntry createFromInfoItemFieldValues(
			long groupId, InfoItemFieldValues infoItemFieldValues)
		throws InfoFormException {

		try {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			Map<String, Serializable> values = new HashMap<>();

			for (InfoFieldValue<Object> infoFieldValue :
					infoItemFieldValues.getInfoFieldValues()) {

				InfoField<?> infoField = infoFieldValue.getInfoField();

				values.put(
					infoField.getName(),
					(Serializable)infoFieldValue.getValue());
			}

			return _objectEntryService.addObjectEntry(
				_getGroupId(_objectDefinition, String.valueOf(groupId)),
				_objectDefinition.getObjectDefinitionId(), values,
				serviceContext);
		}
		catch (ModelListenerException modelListenerException) {
			Throwable throwable = modelListenerException.getCause();

			if (throwable instanceof ObjectValidationRuleEngineException) {
				throw new InfoFormValidationException.CustomValidation(
					throwable.getLocalizedMessage());
			}

			throw new InfoFormException();
		}
		catch (ObjectEntryValuesException.ExceedsIntegerSize
					objectEntryValuesException) {

			String infoFieldUniqueId = _getInfoFieldUniqueId(
				groupId, objectEntryValuesException.getObjectFieldName());

			if (infoFieldUniqueId == null) {
				throw new InfoFormException();
			}

			throw new InfoFormValidationException.ExceedsMaxLength(
				infoFieldUniqueId, objectEntryValuesException.getMaxLength());
		}
		catch (ObjectEntryValuesException.ExceedsLongMaxSize
					objectEntryValuesException) {

			String infoFieldUniqueId = _getInfoFieldUniqueId(
				groupId, objectEntryValuesException.getObjectFieldName());

			if (infoFieldUniqueId == null) {
				throw new InfoFormException();
			}

			throw new InfoFormValidationException.ExceedsMaxValue(
				infoFieldUniqueId, objectEntryValuesException.getMaxValue());
		}
		catch (ObjectEntryValuesException.ExceedsLongMinSize
					objectEntryValuesException) {

			String infoFieldUniqueId = _getInfoFieldUniqueId(
				groupId, objectEntryValuesException.getObjectFieldName());

			if (infoFieldUniqueId == null) {
				throw new InfoFormException();
			}

			throw new InfoFormValidationException.ExceedsMinValue(
				infoFieldUniqueId, objectEntryValuesException.getMinValue());
		}
		catch (ObjectEntryValuesException.ExceedsLongSize
					objectEntryValuesException) {

			String infoFieldUniqueId = _getInfoFieldUniqueId(
				groupId, objectEntryValuesException.getObjectFieldName());

			if (infoFieldUniqueId == null) {
				throw new InfoFormException();
			}

			throw new InfoFormValidationException.ExceedsMaxLength(
				infoFieldUniqueId, objectEntryValuesException.getMaxLength());
		}
		catch (ObjectEntryValuesException.ExceedsMaxFileSize
					objectEntryValuesException) {

			String infoFieldUniqueId = _getInfoFieldUniqueId(
				groupId, objectEntryValuesException.getObjectFieldName());

			if (infoFieldUniqueId == null) {
				throw new InfoFormException();
			}

			throw new InfoFormValidationException.FileSize(
				infoFieldUniqueId,
				objectEntryValuesException.getMaxFileSize() + " MB");
		}
		catch (ObjectEntryValuesException.ExceedsTextMaxLength
					objectEntryValuesException) {

			String infoFieldUniqueId = _getInfoFieldUniqueId(
				groupId, objectEntryValuesException.getObjectFieldName());

			if (infoFieldUniqueId == null) {
				throw new InfoFormException();
			}

			throw new InfoFormValidationException.ExceedsMaxLength(
				infoFieldUniqueId, objectEntryValuesException.getMaxLength());
		}
		catch (ObjectEntryValuesException.InvalidFileExtension
					objectEntryValuesException) {

			String infoFieldUniqueId = _getInfoFieldUniqueId(
				groupId, objectEntryValuesException.getObjectFieldName());

			if (infoFieldUniqueId == null) {
				throw new InfoFormException();
			}

			throw new InfoFormValidationException.InvalidFileExtension(
				infoFieldUniqueId,
				_getAcceptedFileExtensions(
					_objectDefinition.getObjectDefinitionId(),
					objectEntryValuesException.getObjectFieldName()));
		}
		catch (ObjectEntryValuesException.ListTypeEntry
					objectEntryValuesException) {

			String infoFieldUniqueId = _getInfoFieldUniqueId(
				groupId, objectEntryValuesException.getObjectFieldName());

			if (infoFieldUniqueId == null) {
				throw new InfoFormException();
			}

			throw new InfoFormValidationException.InvalidInfoFieldValue(
				infoFieldUniqueId);
		}
		catch (ObjectEntryValuesException.Required objectEntryValuesException) {
			String infoFieldUniqueId = _getInfoFieldUniqueId(
				groupId, objectEntryValuesException.getObjectFieldName());

			if (infoFieldUniqueId == null) {
				throw new InfoFormException();
			}

			throw new InfoFormValidationException.RequiredInfoField(
				infoFieldUniqueId);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			throw new InfoFormException();
		}
	}

	private String _getAcceptedFileExtensions(
		long objectDefinitionId, String objectFieldName) {

		ObjectField objectField = ObjectFieldLocalServiceUtil.fetchObjectField(
			objectDefinitionId, objectFieldName);

		ObjectFieldSetting objectFieldSetting =
			ObjectFieldSettingLocalServiceUtil.fetchObjectFieldSetting(
				objectField.getObjectFieldId(), "acceptedFileExtensions");

		if (objectFieldSetting == null) {
			return StringPool.BLANK;
		}

		return objectFieldSetting.getValue();
	}

	private long _getGroupId(
		ObjectDefinition objectDefinition, String siteKey) {

		ObjectScopeProvider objectScopeProvider =
			_objectScopeProviderRegistry.getObjectScopeProvider(
				objectDefinition.getScope());

		if (!objectScopeProvider.isGroupAware()) {
			return 0;
		}

		long groupId = 0;

		if (Objects.equals(
				ObjectDefinitionConstants.SCOPE_SITE,
				objectDefinition.getScope())) {

			groupId = GetterUtil.getLong(
				GroupUtil.getGroupId(
					objectDefinition.getCompanyId(), siteKey,
					_groupLocalService));
		}

		return groupId;
	}

	private String _getInfoFieldUniqueId(long groupId, String objectFieldName) {
		try {
			InfoForm infoForm = _infoItemFormProvider.getInfoForm(
				String.valueOf(_objectDefinition.getObjectDefinitionId()),
				groupId);

			InfoField<?> infoField = infoForm.getInfoField(objectFieldName);

			if (infoField != null) {
				return infoField.getUniqueId();
			}
		}
		catch (NoSuchFormVariationException noSuchFormVariationException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchFormVariationException);
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryInfoItemCreator.class);

	private final GroupLocalService _groupLocalService;
	private final InfoItemFormProvider<ObjectEntry> _infoItemFormProvider;
	private final ObjectDefinition _objectDefinition;
	private final ObjectEntryService _objectEntryService;
	private final ObjectScopeProviderRegistry _objectScopeProviderRegistry;

}