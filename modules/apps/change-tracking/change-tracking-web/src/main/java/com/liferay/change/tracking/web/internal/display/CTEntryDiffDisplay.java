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
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Samuel Trong Tran
 */
public class CTEntryDiffDisplay {

	public static final String TYPE_AFTER = "after";

	public static final String TYPE_BEFORE = "before";

	public CTEntryDiffDisplay(
		CTCollection ctCollection,
		CTDisplayRendererRegistry ctDisplayRendererRegistry, CTEntry ctEntry,
		CTEntryLocalService ctEntryLocalService,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Language language,
		Locale locale) {

		_ctCollection = ctCollection;
		_ctDisplayRendererRegistry = ctDisplayRendererRegistry;
		_ctEntry = ctEntry;
		_ctEntryLocalService = ctEntryLocalService;
		_httpServletRequest = httpServletRequest;
		_httpServletResponse = httpServletResponse;
		_language = language;
		_locale = locale;
	}

	public String getLeftTitle() throws PortalException {
		String title = null;

		if (_ctCollection.getStatus() == WorkflowConstants.STATUS_APPROVED) {
			title = _ctDisplayRendererRegistry.getTitle(
				_ctEntry.getCtCollectionId(), _ctEntry, _locale);
		}
		else {
			title = _ctDisplayRendererRegistry.getTitle(
				CTConstants.CT_COLLECTION_ID_PRODUCTION, _ctEntry, _locale);
		}

		if (isChangeType(CTConstants.CT_CHANGE_TYPE_DELETION)) {
			return StringBundler.concat(
				_language.get(_httpServletRequest, "production"), " : ", title,
				" (", _language.get(_httpServletRequest, "deleted"), ")");
		}

		return StringBundler.concat(
			_language.get(_httpServletRequest, "production"), " : ", title);
	}

	public String getRightTitle() throws PortalException {
		String title = _ctDisplayRendererRegistry.getTitle(
			_ctDisplayRendererRegistry.getCtCollectionId(
				_ctCollection, _ctEntry),
			_ctEntry, _locale);

		if (isChangeType(CTConstants.CT_CHANGE_TYPE_ADDITION)) {
			return StringBundler.concat(
				_ctCollection.getName(), " : ", title, " (",
				_language.get(_httpServletRequest, "new"), ")");
		}

		return StringBundler.concat(_ctCollection.getName(), " : ", title);
	}

	public boolean isChangeType(int changeType) {
		if (_ctEntry.getChangeType() == changeType) {
			return true;
		}

		return false;
	}

	public void renderLeftCTRow() throws Exception {
		if (_ctCollection.getStatus() == WorkflowConstants.STATUS_APPROVED) {
			_ctDisplayRendererRegistry.renderCTEntry(
				_httpServletRequest, _httpServletResponse,
				_ctEntry.getCtCollectionId(), _ctEntry, TYPE_BEFORE);
		}
		else {
			_ctDisplayRendererRegistry.renderCTEntry(
				_httpServletRequest, _httpServletResponse,
				CTConstants.CT_COLLECTION_ID_PRODUCTION, _ctEntry, TYPE_BEFORE);
		}
	}

	public void renderRightCTRow() throws Exception {
		if (_ctCollection.getStatus() == WorkflowConstants.STATUS_APPROVED) {
			long ctCollectionId = _ctEntryLocalService.getCTRowCTCollectionId(
				_ctEntry);

			_ctDisplayRendererRegistry.renderCTEntry(
				_httpServletRequest, _httpServletResponse, ctCollectionId,
				_ctEntry, TYPE_AFTER);
		}
		else {
			_ctDisplayRendererRegistry.renderCTEntry(
				_httpServletRequest, _httpServletResponse,
				_ctCollection.getCtCollectionId(), _ctEntry, TYPE_AFTER);
		}
	}

	private final CTCollection _ctCollection;
	private final CTDisplayRendererRegistry _ctDisplayRendererRegistry;
	private final CTEntry _ctEntry;
	private final CTEntryLocalService _ctEntryLocalService;
	private final HttpServletRequest _httpServletRequest;
	private final HttpServletResponse _httpServletResponse;
	private final Language _language;
	private final Locale _locale;

}