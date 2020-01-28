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

package com.liferay.change.tracking.web.internal.display;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.Language;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Samuel Trong Tran
 */
public class CTEntryDiffDisplay {

	public CTEntryDiffDisplay(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, CTCollection ctCollection,
		CTDisplayRendererRegistry ctDisplayRendererRegistry, CTEntry ctEntry,
		Language language, String name) {

		_httpServletRequest = httpServletRequest;
		_httpServletResponse = httpServletResponse;
		_ctCollection = ctCollection;
		_ctDisplayRendererRegistry = ctDisplayRendererRegistry;
		_ctEntry = ctEntry;
		_language = language;
		_name = name;
	}

	public String getCTCollectionTitle() {
		if (_ctEntry.getChangeType() != CTConstants.CT_CHANGE_TYPE_ADDITION) {
			return StringBundler.concat(_ctCollection.getName(), " : ", _name);
		}

		return StringBundler.concat(
			_ctCollection.getName(), " : ", _name, " (",
			_language.get(_httpServletRequest, "new"), ")");
	}

	public String getProductionTitle() {
		if (_ctEntry.getChangeType() != CTConstants.CT_CHANGE_TYPE_DELETION) {
			return StringBundler.concat(
				_language.get(_httpServletRequest, "production"), " : ", _name);
		}

		return StringBundler.concat(
			_language.get(_httpServletRequest, "production"), " : ", _name,
			" (", _language.get(_httpServletRequest, "deleted"), ")");
	}

	public boolean isChangeType(int changeType) {
		if (_ctEntry.getChangeType() == changeType) {
			return true;
		}

		return false;
	}

	public void renderCTCollectionCTEntry() throws Exception {
		_ctDisplayRendererRegistry.renderCTEntry(
			_httpServletRequest, _httpServletResponse, _ctEntry,
			_ctCollection.getCtCollectionId());
	}

	public void renderProductionCTEntry() throws Exception {
		_ctDisplayRendererRegistry.renderCTEntry(
			_httpServletRequest, _httpServletResponse, _ctEntry,
			CTConstants.CT_COLLECTION_ID_PRODUCTION);
	}

	private final CTCollection _ctCollection;
	private final CTDisplayRendererRegistry _ctDisplayRendererRegistry;
	private final CTEntry _ctEntry;
	private final HttpServletRequest _httpServletRequest;
	private final HttpServletResponse _httpServletResponse;
	private final Language _language;
	private final String _name;

}