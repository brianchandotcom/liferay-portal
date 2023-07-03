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

package com.liferay.object.web.internal.info.item.handler;

import com.liferay.info.exception.InfoFormException;
import com.liferay.info.exception.InfoFormValidationException;
import com.liferay.info.exception.NoSuchFormVariationException;
import com.liferay.info.field.InfoField;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.object.exception.ObjectEntryValuesException;
import com.liferay.object.exception.ObjectValidationRuleEngineException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;
import com.liferay.object.service.ObjectFieldSettingLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Eudaldo Alonso
 */
public class ObjectEntryInfoItemExceptionRequestHandler {

	public static void handleInfoFormException(
			Exception exception, long groupId,
			InfoItemFormProvider<?> infoItemFormProvider,
			ObjectDefinition objectDefinition)
		throws InfoFormException {

		if (exception instanceof ModelListenerException) {
			ModelListenerException modelListenerException =
				(ModelListenerException)exception;

			Throwable throwable = modelListenerException.getCause();

			if (throwable instanceof ObjectValidationRuleEngineException) {
				throw new InfoFormValidationException.CustomValidation(
					throwable.getLocalizedMessage());
			}

			throw new InfoFormException();
		}

		if (exception instanceof
				ObjectEntryValuesException.ExceedsIntegerSize) {

			ObjectEntryValuesException.ExceedsIntegerSize
				objectEntryValuesException =
					(ObjectEntryValuesException.ExceedsIntegerSize)exception;

			String infoFieldUniqueId = _getInfoFieldUniqueId(
				groupId, infoItemFormProvider, objectDefinition,
				objectEntryValuesException.getObjectFieldName());

			if (infoFieldUniqueId == null) {
				throw new InfoFormException();
			}

			throw new InfoFormValidationException.ExceedsMaxLength(
				infoFieldUniqueId, objectEntryValuesException.getMaxLength());
		}

		if (exception instanceof
				ObjectEntryValuesException.ExceedsLongMaxSize) {

			ObjectEntryValuesException.ExceedsLongMaxSize
				objectEntryValuesException =
					(ObjectEntryValuesException.ExceedsLongMaxSize)exception;

			String infoFieldUniqueId = _getInfoFieldUniqueId(
				groupId, infoItemFormProvider, objectDefinition,
				objectEntryValuesException.getObjectFieldName());

			if (infoFieldUniqueId == null) {
				throw new InfoFormException();
			}

			throw new InfoFormValidationException.ExceedsMaxValue(
				infoFieldUniqueId, objectEntryValuesException.getMaxValue());
		}

		if (exception instanceof
				ObjectEntryValuesException.ExceedsLongMinSize) {

			ObjectEntryValuesException.ExceedsLongMinSize
				objectEntryValuesException =
					(ObjectEntryValuesException.ExceedsLongMinSize)exception;

			String infoFieldUniqueId = _getInfoFieldUniqueId(
				groupId, infoItemFormProvider, objectDefinition,
				objectEntryValuesException.getObjectFieldName());

			if (infoFieldUniqueId == null) {
				throw new InfoFormException();
			}

			throw new InfoFormValidationException.ExceedsMinValue(
				infoFieldUniqueId, objectEntryValuesException.getMinValue());
		}

		if (exception instanceof ObjectEntryValuesException.ExceedsLongSize) {
			ObjectEntryValuesException.ExceedsLongSize
				objectEntryValuesException =
					(ObjectEntryValuesException.ExceedsLongSize)exception;

			String infoFieldUniqueId = _getInfoFieldUniqueId(
				groupId, infoItemFormProvider, objectDefinition,
				objectEntryValuesException.getObjectFieldName());

			if (infoFieldUniqueId == null) {
				throw new InfoFormException();
			}

			throw new InfoFormValidationException.ExceedsMaxLength(
				infoFieldUniqueId, objectEntryValuesException.getMaxLength());
		}

		if (exception instanceof
				ObjectEntryValuesException.ExceedsMaxFileSize) {

			ObjectEntryValuesException.ExceedsMaxFileSize
				objectEntryValuesException =
					(ObjectEntryValuesException.ExceedsMaxFileSize)exception;

			String infoFieldUniqueId = _getInfoFieldUniqueId(
				groupId, infoItemFormProvider, objectDefinition,
				objectEntryValuesException.getObjectFieldName());

			if (infoFieldUniqueId == null) {
				throw new InfoFormException();
			}

			throw new InfoFormValidationException.FileSize(
				infoFieldUniqueId,
				objectEntryValuesException.getMaxFileSize() + " MB");
		}

		if (exception instanceof
				ObjectEntryValuesException.ExceedsTextMaxLength) {

			ObjectEntryValuesException.ExceedsTextMaxLength
				objectEntryValuesException =
					(ObjectEntryValuesException.ExceedsTextMaxLength)exception;

			String infoFieldUniqueId = _getInfoFieldUniqueId(
				groupId, infoItemFormProvider, objectDefinition,
				objectEntryValuesException.getObjectFieldName());

			if (infoFieldUniqueId == null) {
				throw new InfoFormException();
			}

			throw new InfoFormValidationException.ExceedsMaxLength(
				infoFieldUniqueId, objectEntryValuesException.getMaxLength());
		}

		if (exception instanceof
				ObjectEntryValuesException.InvalidFileExtension) {

			ObjectEntryValuesException.InvalidFileExtension
				objectEntryValuesException =
					(ObjectEntryValuesException.InvalidFileExtension)exception;

			String infoFieldUniqueId = _getInfoFieldUniqueId(
				groupId, infoItemFormProvider, objectDefinition,
				objectEntryValuesException.getObjectFieldName());

			if (infoFieldUniqueId == null) {
				throw new InfoFormException();
			}

			throw new InfoFormValidationException.InvalidFileExtension(
				infoFieldUniqueId,
				_getAcceptedFileExtensions(
					objectDefinition.getObjectDefinitionId(),
					objectEntryValuesException.getObjectFieldName()));
		}

		if (exception instanceof ObjectEntryValuesException.ListTypeEntry) {
			ObjectEntryValuesException.ListTypeEntry
				objectEntryValuesException =
					(ObjectEntryValuesException.ListTypeEntry)exception;

			String infoFieldUniqueId = _getInfoFieldUniqueId(
				groupId, infoItemFormProvider, objectDefinition,
				objectEntryValuesException.getObjectFieldName());

			if (infoFieldUniqueId == null) {
				throw new InfoFormException();
			}

			throw new InfoFormValidationException.InvalidInfoFieldValue(
				infoFieldUniqueId);
		}

		if (exception instanceof ObjectEntryValuesException.Required) {
			ObjectEntryValuesException.Required objectEntryValuesException =
				(ObjectEntryValuesException.Required)exception;

			String infoFieldUniqueId = _getInfoFieldUniqueId(
				groupId, infoItemFormProvider, objectDefinition,
				objectEntryValuesException.getObjectFieldName());

			if (infoFieldUniqueId == null) {
				throw new InfoFormException();
			}

			throw new InfoFormValidationException.RequiredInfoField(
				infoFieldUniqueId);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(exception);
		}

		throw new InfoFormException();
	}

	private static String _getAcceptedFileExtensions(
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

	private static String _getInfoFieldUniqueId(
		long groupId, InfoItemFormProvider<?> infoItemFormProvider,
		ObjectDefinition objectDefinition, String objectFieldName) {

		try {
			InfoForm infoForm = infoItemFormProvider.getInfoForm(
				String.valueOf(objectDefinition.getObjectDefinitionId()),
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
		ObjectEntryInfoItemExceptionRequestHandler.class);

}