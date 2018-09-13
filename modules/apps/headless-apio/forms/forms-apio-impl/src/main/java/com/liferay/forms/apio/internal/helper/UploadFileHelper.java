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

package com.liferay.forms.apio.internal.helper;

import com.google.gson.Gson;

import com.liferay.apio.architect.functional.Try;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.forms.apio.internal.model.FileEntryValue;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 * @author Paulo Cruz
 */
@Component(immediate = true, service = UploadFileHelper.class)
public class UploadFileHelper {

	public void linkFiles(
		List<DDMFormField> ddmFormFields,
		List<DDMFormFieldValue> ddmFormFieldValues) {

		Stream<DDMFormField> ddmFormFieldsStream = ddmFormFields.stream();

		ddmFormFieldsStream.filter(
			formField -> formField.getType(
			).equals(
				"document_library"
			)
		).map(
			field -> _findField(field, ddmFormFieldValues)
		).forEach(
			optional -> optional.ifPresent(this::_setFileEntryAsFormFieldValue)
		);
	}

	private Long _extractFileEntryId(DDMFormFieldValue ddmFormFieldValue) {
		return Try.fromFallible(
			() -> ddmFormFieldValue.getValue()
		).map(
			Value::getValues
		).map(
			Map::values
		).map(
			Collection::stream
		).mapOptional(
			Stream::findFirst
		).map(
			fileEntryUrl -> fileEntryUrl.substring(
				fileEntryUrl.lastIndexOf("/") + 1)
		).map(
			Long::valueOf
		).orElse(
			null
		);
	}

	private Optional<DDMFormFieldValue> _findField(
		DDMFormField formField, List<DDMFormFieldValue> ddmFormFieldValues) {

		Stream<DDMFormFieldValue> ddmFormFieldValuesStream =
			ddmFormFieldValues.stream();

		return ddmFormFieldValuesStream.filter(
			value -> value.getName(
			).equals(
				formField.getName()
			)
		).findFirst();
	}

	private void _setFileEntryAsFormFieldValue(
		DDMFormFieldValue ddmFormFieldValue) {

		Gson gson = new Gson();

		Try.fromFallible(
			() -> _extractFileEntryId(ddmFormFieldValue)
		).map(
			_dlAppService::getFileEntry
		).map(
			fileEntry -> new FileEntryValue(
				fileEntry.getFileEntryId(), fileEntry.getGroupId(),
				fileEntry.getTitle(), fileEntry.getMimeType(),
				fileEntry.getUuid(), fileEntry.getVersion())
		).map(
			gson::toJson
		).map(
			UnlocalizedValue::new
		).ifSuccess(
			ddmFormFieldValue::setValue
		);
	}

	@Reference
	private DLAppService _dlAppService;

}