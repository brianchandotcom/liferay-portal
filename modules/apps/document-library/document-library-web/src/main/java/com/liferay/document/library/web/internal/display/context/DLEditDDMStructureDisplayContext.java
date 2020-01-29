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

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González
 */
public class DLEditDDMStructureDisplayContext {

	public DLEditDDMStructureDisplayContext(
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;
	}

	public DDMStructure getDDMStructure() {
		if (_ddmStructure != null) {
			return _ddmStructure;
		}

		_ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(
			getDDMStructureId());

		return _ddmStructure;
	}

	public long getDDMStructureId() {
		if (_ddmStructureId != null) {
			return _ddmStructureId;
		}

		_ddmStructureId = ParamUtil.getLong(
			_httpServletRequest, "ddmStructureId");

		return _ddmStructureId;
	}

	public String getFields() throws PortalException {
		DDMStructure ddmStructure = getDDMStructure();

		if (ddmStructure == null) {
			return StringPool.BLANK;
		}

		JSONArray fieldsJSONArray = DDMUtil.getDDMFormFieldsJSONArray(
			ddmStructure.getLatestStructureVersion(), getScript());

		if (fieldsJSONArray != null) {
			return fieldsJSONArray.toString();
		}

		return StringPool.BLANK;
	}

	public long getParentDDMStructureId() {
		if (_parentDDMStructureId != null) {
			return _parentDDMStructureId;
		}

		long defaultParentDDMStructureId =
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID;

		DDMStructure ddmStructure = getDDMStructure();

		if (ddmStructure != null) {
			defaultParentDDMStructureId = ddmStructure.getParentStructureId();
		}

		_parentDDMStructureId = ParamUtil.getLong(
			_httpServletRequest, "parentDDMStructureId",
			defaultParentDDMStructureId);

		return _parentDDMStructureId;
	}

	public String getParentDDMStructureName() {
		if (_parentDDMStructureName != null) {
			return _parentDDMStructureName;
		}

		DDMStructure parentDDMStructure =
			DDMStructureLocalServiceUtil.fetchStructure(
				getParentDDMStructureId());

		if (parentDDMStructure != null) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			_parentDDMStructureName = parentDDMStructure.getName(
				themeDisplay.getLocale());
		}

		return _parentDDMStructureName;
	}

	public String getScript() throws PortalException {
		if (_script != null) {
			DDMStructure ddmStructure = getDDMStructure();

			_script = BeanParamUtil.getString(
				ddmStructure.getLatestStructureVersion(), _httpServletRequest,
				"definition");

			return _script;
		}

		_script = ParamUtil.getString(_httpServletRequest, "definition");

		return _script;
	}

	private DDMStructure _ddmStructure;
	private Long _ddmStructureId;
	private final HttpServletRequest _httpServletRequest;
	private Long _parentDDMStructureId;
	private String _parentDDMStructureName;
	private String _script;

}