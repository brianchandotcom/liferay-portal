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
		HttpServletResponse httpServletResponse, String changeType,
		CTCollection ctCollection, CTDisplayRegistry ctDisplayRegistry,
		CTEntry ctEntry, Language language, String name) {

		_httpServletRequest = httpServletRequest;
		_httpServletResponse = httpServletResponse;
		_changeType = changeType;
		_ctCollection = ctCollection;
		_ctDisplayRegistry = ctDisplayRegistry;
		_ctEntry = ctEntry;
		_language = language;
		_name = name;
	}

	public String getLeftTitle() {
		String title = StringBundler.concat(
			_language.get(_httpServletRequest, "production"), " : ", _name);

		if (_changeType.equals("added")) {
			title = StringBundler.concat(
				title, " (", _language.get(_httpServletRequest, "new"), ")");
		}

		return title;
	}

	public String getRightTitle() {
		String title = StringBundler.concat(
			_ctCollection.getName(), " : ", _name);

		if (_changeType.equals("deleted")) {
			title = StringBundler.concat(
				title, " (", _language.get(_httpServletRequest, "deleted"),
				")");
		}

		return title;
	}

	public void renderLeftView() throws Exception {
		if (!_changeType.equals("added")) {
			long ctCollectionId = 0;

			if (_changeType.equals("deleted")) {
				ctCollectionId = _ctCollection.getCtCollectionId();
			}

			_ctDisplayRegistry.renderCTEntry(
				_httpServletRequest, _httpServletResponse, _ctEntry,
				ctCollectionId);
		}
	}

	public void renderRightView() throws Exception {
		if (!_changeType.equals("deleted")) {
			_ctDisplayRegistry.renderCTEntry(
				_httpServletRequest, _httpServletResponse, _ctEntry,
				_ctCollection.getCtCollectionId());
		}
	}

	private final String _changeType;
	private final CTCollection _ctCollection;
	private final CTDisplayRegistry _ctDisplayRegistry;
	private final CTEntry _ctEntry;
	private final HttpServletRequest _httpServletRequest;
	private final HttpServletResponse _httpServletResponse;
	private final Language _language;
	private final String _name;

}