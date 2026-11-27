/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.web.internal.portlet.action;

import com.liferay.document.library.kernel.exception.FileEntryExpiredException;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.dynamic.data.mapping.constants.DDMFormConstants;
import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.ResourceRequest;
import jakarta.portlet.ResourceResponse;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
@Component(
	property = {
		"jakarta.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM,
		"mvc.command.name=/dynamic_data_mapping_form/download_file_entry"
	},
	service = MVCResourceCommand.class
)
public class DownloadFileEntryMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		DDMFormInstanceRecord ddmFormInstanceRecord =
			_ddmFormInstanceRecordService.getFormInstanceRecord(
				ParamUtil.getLong(resourceRequest, "ddmFormInstanceRecordId"));

		String ddmFormFieldName = ParamUtil.getString(
			resourceRequest, "ddmFormFieldName");
		long fileEntryId = ParamUtil.getLong(resourceRequest, "fileEntryId");

		if (!_hasFileEntryId(
				ddmFormFieldName, ddmFormInstanceRecord.getDDMFormValues(),
				fileEntryId)) {

			throw new NoSuchFileEntryException(
				"No file entry exists with file entry ID " + fileEntryId);
		}

		FileEntry fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);

		if (!_isDDMFormInstanceRecordFileEntry(
				ddmFormFieldName, ddmFormInstanceRecord, fileEntry)) {

			ThemeDisplay themeDisplay =
				(ThemeDisplay)resourceRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			PermissionChecker permissionChecker =
				themeDisplay.getPermissionChecker();

			_fileEntryModelResourcePermission.check(
				permissionChecker, fileEntry, ActionKeys.DOWNLOAD);

			FileVersion fileVersion = fileEntry.getFileVersion();

			if (fileVersion.isExpired() &&
				!permissionChecker.isContentReviewer(
					permissionChecker.getCompanyId(),
					fileVersion.getGroupId()) &&
				(fileVersion.getUserId() != permissionChecker.getUserId())) {

				throw new FileEntryExpiredException(
					StringBundler.concat(
						"The file entry ", fileEntry.getFileEntryId(),
						" is expired and the user does not have review ",
						"permission"));
			}
		}

		PortletResponseUtil.sendFile(
			resourceRequest, resourceResponse, fileEntry.getFileName(),
			fileEntry.getContentStream(),
			MimeTypesUtil.getContentType(fileEntry.getFileName()));
	}

	private boolean _hasFileEntryId(
			String ddmFormFieldName, DDMFormValues ddmFormValues,
			long fileEntryId)
		throws Exception {

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap(true);

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			ddmFormFieldName);

		if (ddmFormFieldValues == null) {
			return false;
		}

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			if (!Objects.equals(
					ddmFormFieldValue.getType(),
					DDMFormFieldTypeConstants.DOCUMENT_LIBRARY)) {

				continue;
			}

			Value value = ddmFormFieldValue.getValue();

			if (value == null) {
				continue;
			}

			JSONObject valueJSONObject = _jsonFactory.createJSONObject(
				value.getString(value.getDefaultLocale()));

			if (valueJSONObject.isNull("fileEntryId")) {
				continue;
			}

			if (Objects.equals(
					valueJSONObject.getLong("fileEntryId"), fileEntryId)) {

				return true;
			}
		}

		return false;
	}

	private boolean _isDDMFormInstanceRecordFileEntry(
			String ddmFormFieldName,
			DDMFormInstanceRecord ddmFormInstanceRecord, FileEntry fileEntry)
		throws Exception {

		Repository repository = _portletFileRepository.fetchPortletRepository(
			ddmFormInstanceRecord.getGroupId(), DDMFormConstants.SERVICE_NAME);

		if ((repository == null) ||
			(fileEntry.getRepositoryId() != repository.getRepositoryId()) ||
			!(fileEntry.getModel() instanceof DLFileEntry)) {

			return false;
		}

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		if (Objects.equals(
				dlFileEntry.getClassName(),
				DDMFormInstanceRecord.class.getName()) &&
			(dlFileEntry.getClassPK() ==
				ddmFormInstanceRecord.getFormInstanceRecordId())) {

			return true;
		}

		for (DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion :
				_ddmFormInstanceRecordVersionLocalService.
					getFormInstanceRecordVersions(
						ddmFormInstanceRecord.getFormInstanceRecordId(),
						QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			if ((ddmFormInstanceRecordVersion.getUserId() ==
					fileEntry.getUserId()) &&
				_hasFileEntryId(
					ddmFormFieldName,
					ddmFormInstanceRecordVersion.getDDMFormValues(),
					fileEntry.getFileEntryId())) {

				return true;
			}
		}

		return false;
	}

	@Reference
	private DDMFormInstanceRecordService _ddmFormInstanceRecordService;

	@Reference
	private DDMFormInstanceRecordVersionLocalService
		_ddmFormInstanceRecordVersionLocalService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.FileEntry)"
	)
	private ModelResourcePermission<FileEntry>
		_fileEntryModelResourcePermission;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private PortletFileRepository _portletFileRepository;

}