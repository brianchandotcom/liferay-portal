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

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class DLFileEntryAdditionalMetadataSetsDisplayContext {

	public DLFileEntryAdditionalMetadataSetsDisplayContext(
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;
	}

	public long getDDMStructureId() {
		return BeanParamUtil.getLong(
			_getDDMStructure(), _httpServletRequest, "structureId");
	}

	public List<com.liferay.dynamic.data.mapping.kernel.DDMStructure>
		getDDMStructures() {

		DLFileEntryType fileEntryType = getDLFileEntryType();

		if (fileEntryType == null) {
			return Collections.emptyList();
		}

		DDMStructure ddmStructure = _getDDMStructure();

		if (ddmStructure == null) {
			return fileEntryType.getDDMStructures();
		}

		return ListUtil.filter(
			fileEntryType.getDDMStructures(),
			currentDDMStructure ->
				currentDDMStructure.getStructureId() !=
					ddmStructure.getStructureId());
	}

	public int getDDMStructuresCount() {
		List<com.liferay.dynamic.data.mapping.kernel.DDMStructure>
			ddmStructures = getDDMStructures();

		return ddmStructures.size();
	}

	public DLFileEntryType getDLFileEntryType() {
		return (DLFileEntryType)_httpServletRequest.getAttribute(
			WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY_TYPE);
	}

	private DDMStructure _getDDMStructure() {
		return (DDMStructure)_httpServletRequest.getAttribute(
			WebKeys.DOCUMENT_LIBRARY_DYNAMIC_DATA_MAPPING_STRUCTURE);
	}

	private final HttpServletRequest _httpServletRequest;

}