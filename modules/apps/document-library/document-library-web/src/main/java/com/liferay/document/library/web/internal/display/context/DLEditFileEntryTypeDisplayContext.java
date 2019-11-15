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

package com.liferay.document.library.web.internal.display.context;

import com.liferay.document.library.web.internal.dynamic.data.mapping.util.DLDDMDisplay;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.dynamic.data.mapping.util.DDMDisplay;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Optional;

/**
 * @author Cristina González
 */
public class DLEditFileEntryTypeDisplayContext {

	public DLEditFileEntryTypeDisplayContext(
		DDM ddm, DDMStructureLocalService ddmStructureLocalService,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_ddm = ddm;
		_ddmStructureLocalService = ddmStructureLocalService;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
	}

	public String getAvailableFields() {
		DDMDisplay ddmDisplay = new DLDDMDisplay();

		return ddmDisplay.getAvailableFields();
	}

	public DDMStructure getDDMStructure() {
		return (DDMStructure)_liferayPortletRequest.getAttribute(
			WebKeys.DOCUMENT_LIBRARY_DYNAMIC_DATA_MAPPING_STRUCTURE);
	}

	public String getFieldsJSONArrayString() {
		DDMStructure ddmStructure = getDDMStructure();

		long ddmStructureId = BeanParamUtil.getLong(
			ddmStructure, _liferayPortletRequest, "structureId");

		String script = BeanParamUtil.getString(
			ddmStructure, _liferayPortletRequest, "definition");

		return Optional.ofNullable(
			_ddm.getDDMFormFieldsJSONArray(
				_ddmStructureLocalService.fetchDDMStructure(ddmStructureId),
				script)
		).map(
			JSONArray::toString
		).orElseGet(
			() -> StringPool.BLANK
		);
	}

	private final DDM _ddm;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;

}