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

package com.liferay.journal.internal.exportimport.content.processor;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
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
		String content, DLAppService dlAppService,
		boolean exportReferencedContent, PortletDataContext portletDataContext,
		StagedModel stagedModel) {

		_dlAppService = dlAppService;
		_exportReferencedContent = exportReferencedContent;
		_portletDataContext = portletDataContext;
		_stagedModel = stagedModel;
	}

	@Override
	public String getFieldType() {
		return DDMFormFieldType.IMAGE;
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
			catch (JSONException jsone) {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to parse JSON", jsone);
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
					_dlAppService.getFileEntryByUuidAndGroupId(uuid, groupId);

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
			catch (PortalException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(pe, pe);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImageExportDDMFormFieldValueTransformer.class);

	private final DLAppService _dlAppService;
	private final boolean _exportReferencedContent;
	private final PortletDataContext _portletDataContext;
	private final StagedModel _stagedModel;

}