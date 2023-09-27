/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.dynamic.data.mapping.util;

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.util.DDMFormFieldValueTransformer;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;

import java.util.Locale;

/**
 * @author Pavel Savinov
 */
public class ImageExportDDMFormFieldValueTransformer
	implements DDMFormFieldValueTransformer {

	public ImageExportDDMFormFieldValueTransformer(
		DLAppLocalService dlAppLocalService, boolean exportReferencedContent,
		PortletDataContext portletDataContext, StagedModel stagedModel) {

		_dlAppLocalService = dlAppLocalService;
		_exportReferencedContent = exportReferencedContent;
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
			String valueString = value.getString(locale);

			JSONObject jsonObject = null;

			try {
				jsonObject = JSONFactoryUtil.createJSONObject(valueString);
			}
			catch (JSONException jsonException) {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to parse JSON", jsonException);
				}

				continue;
			}

			long groupId = GetterUtil.getLong(jsonObject.get("groupId"));
			String uuid = jsonObject.getString("uuid");

			if ((groupId == 0) || Validator.isNull(uuid)) {
				continue;
			}

			try {
				FileEntry fileEntry =
					_dlAppLocalService.getFileEntryByUuidAndGroupId(
						uuid, groupId);

				if (fileEntry.isInTrash()) {
					continue;
				}

				if (_exportReferencedContent) {
					StagedModelDataHandlerUtil.exportReferenceStagedModel(
						_portletDataContext, _stagedModel, fileEntry,
						_portletDataContext.REFERENCE_TYPE_DEPENDENCY);

					continue;
				}

				Element entityElement =
					_portletDataContext.getExportDataElement(_stagedModel);

				_portletDataContext.addReferenceElement(
					_stagedModel, entityElement, fileEntry,
					PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
			}
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImageExportDDMFormFieldValueTransformer.class);

	private final DLAppLocalService _dlAppLocalService;
	private final boolean _exportReferencedContent;
	private final PortletDataContext _portletDataContext;
	private final StagedModel _stagedModel;

}