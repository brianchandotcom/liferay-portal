/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.dynamic.data.mapping.util;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.util.DDMFormFieldValueTransformer;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;

/**
 * @author Pavel Savinov
 */
public class ImageImportDDMFormFieldValueTransformer
	implements DDMFormFieldValueTransformer {

	public ImageImportDDMFormFieldValueTransformer(
		DLAppLocalService dlAppLocalService,
		PortletDataContext portletDataContext, StagedModel stagedModel) {

		_dlAppLocalService = dlAppLocalService;
		_portletDataContext = portletDataContext;
		_stagedModel = stagedModel;
	}

	@Override
	public String getFieldType() {
		return DDMFormFieldTypeConstants.IMAGE;
	}

	@Override
	public void transform(DDMFormFieldValue ddmFormFieldValue)
		throws PortalException {

		Value value = ddmFormFieldValue.getValue();

		for (Locale locale : value.getAvailableLocales()) {
			JSONObject jsonObject = null;

			try {
				jsonObject = JSONFactoryUtil.createJSONObject(
					value.getString(locale));
			}
			catch (JSONException jsonException) {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to parse JSON", jsonException);
				}

				continue;
			}

			FileEntry importedFileEntry = _fetchImportedFileEntry(
				_portletDataContext, jsonObject.getLong("fileEntryId"),
				jsonObject.getString("uuid"));

			if (importedFileEntry == null) {
				continue;
			}

			value.addString(
				locale,
				_toJSON(
					jsonObject.getString("alt"), importedFileEntry,
					jsonObject.getString("height"),
					jsonObject.getString("type"), jsonObject.getString("url"),
					jsonObject.getString("width")));
		}
	}

	private FileEntry _fetchImportedFileEntry(
			PortletDataContext portletDataContext, long oldClassPK, String uuid)
		throws PortalException {

		try {
			Map<Long, Long> fileEntryPKs =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					DLFileEntry.class);

			Long classPK = fileEntryPKs.get(oldClassPK);

			if (classPK == null) {
				if (Validator.isNotNull(uuid)) {
					return _dlAppLocalService.getFileEntryByUuidAndGroupId(
						uuid, portletDataContext.getScopeGroupId());
				}

				return null;
			}

			return _dlAppLocalService.getFileEntry(classPK);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to find file entry", portalException);
			}
		}

		return null;
	}

	private String _toJSON(
		String alt, FileEntry fileEntry, String height, String type, String url,
		String width) {

		return JSONUtil.put(
			"alt", alt
		).put(
			"description", alt
		).put(
			"fileEntryId", fileEntry.getFileEntryId()
		).put(
			"groupId", fileEntry.getGroupId()
		).put(
			"height", height
		).put(
			"name", fileEntry.getFileName()
		).put(
			"resourcePrimKey",
			() -> {
				JournalArticle article = (JournalArticle)_stagedModel;

				return article.getResourcePrimKey();
			}
		).put(
			"title", fileEntry.getTitle()
		).put(
			"type", type
		).put(
			"url", url
		).put(
			"uuid", fileEntry.getUuid()
		).put(
			"width", width
		).toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImageImportDDMFormFieldValueTransformer.class);

	private final DLAppLocalService _dlAppLocalService;
	private final PortletDataContext _portletDataContext;
	private final StagedModel _stagedModel;

}