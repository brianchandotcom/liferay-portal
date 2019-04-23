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

package com.liferay.headless.form.internal.helper;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.headless.form.dto.v1_0.FormDocument;
import com.liferay.headless.form.dto.v1_0.MediaForm;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;

import java.io.IOException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.BadRequestException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 * @author Paulo Cruz
 * @author Victor Oliveira
 */
@Component(immediate = true, service = UploadFileHelper.class)
public class UploadFileHelper {

	public FileEntry getFileEntry(String value) throws PortalException {
		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(value);

			return _dlAppService.getFileEntry(
				jsonObject.getLong("fileEntryId"));
		}
		catch (JSONException jsone) {
			_log.error(jsone.getMessage());

			throw new BadRequestException(jsone);
		}
	}

	public void linkFiles(
		List<DDMFormField> ddmFormFields,
		List<DDMFormFieldValue> ddmFormFieldValues) {

		Stream<DDMFormField> ddmFormFieldsStream = ddmFormFields.stream();

		ddmFormFieldsStream.filter(
			formField -> Objects.equals(formField.getType(), "document_library")
		).map(
			field -> {
				Stream<DDMFormFieldValue> ddmFormFieldValuesStream =
					ddmFormFieldValues.stream();

				return ddmFormFieldValuesStream.filter(
					value -> Objects.equals(field.getName(), value.getName())
				).collect(
					Collectors.toList()
				);
			}
		).forEach(
			values -> {
				try {
					for (DDMFormFieldValue ddmFormFieldValue : values) {
						FileEntry fileEntry = _dlAppService.getFileEntry(
							_getFileEntryId(ddmFormFieldValue));

						JSONObject jsonObject =
							JSONFactoryUtil.createJSONObject();

						jsonObject.put(
							"fileEntryId", fileEntry.getFileEntryId()
						).put(
							"groupId", fileEntry.getGroupId()
						).put(
							"title", fileEntry.getTitle()
						).put(
							"type", fileEntry.getMimeType()
						).put(
							"uuid", fileEntry.getUuid()
						).put(
							"version", fileEntry.getVersion()
						);

						DDMFormField ddmFormField =
							ddmFormFieldValue.getDDMFormField();

						String jsonValue = jsonObject.toString();

						Value value = new UnlocalizedValue(jsonValue);

						if (ddmFormField.isLocalizable()) {
							value = new LocalizedValue();

							value.addString(
								value.getDefaultLocale(), jsonValue);
						}

						ddmFormFieldValue.setValue(value);
					}
				}
				catch (Exception e) {
					_log.error(e.getMessage());

					throw new BadRequestException(e);
				}
			}
		);
	}

	public FormDocument toDocument(FileEntry fileEntry) throws Exception {
		FileVersion fileVersion = fileEntry.getFileVersion();

		return new FormDocument() {
			{
				contentUrl = _dlurlHelper.getPreviewURL(
					fileEntry, fileVersion, null, "");
				encodingFormat = fileEntry.getMimeType();
				fileEntryId = fileEntry.getFileEntryId();
				fileExtension = fileEntry.getExtension();
				siteId = fileEntry.getGroupId();
				sizeInBytes = fileEntry.getSize();
				title = fileEntry.getTitle();
			}
		};
	}

	public FileEntry uploadFile(
			DDMFormInstance ddmFormInstance, MultipartBody multipartBody)
		throws IOException, PortalException {

		MediaForm mediaForm = multipartBody.getValueAsInstance(
			"mediaForm", MediaForm.class);

		long folderId = Optional.ofNullable(
			mediaForm.getFolderId()
		).orElse(
			0L
		);

		BinaryFile binaryFile = multipartBody.getBinaryFile("file");

		return _dlAppService.addFileEntry(
			ddmFormInstance.getGroupId(), folderId, binaryFile.getFileName(),
			binaryFile.getContentType(), mediaForm.getTitle(),
			mediaForm.getDescription(), null, binaryFile.getInputStream(),
			binaryFile.getSize(), new ServiceContext());
	}

	private Long _getFileEntryId(DDMFormFieldValue ddmFormFieldValue)
		throws JSONException {

		Value value = ddmFormFieldValue.getValue();

		String valueString = value.getString(
			LocaleThreadLocal.getDefaultLocale());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(valueString);

		return jsonObject.getLong("fileEntryId");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UploadFileHelper.class);

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLURLHelper _dlurlHelper;

}