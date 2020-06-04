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
import com.liferay.petra.lang.SafeClosable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.change.tracking.sql.CTSQLModeThreadLocal;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Samuel Trong Tran
 */
public class CTEntryDiffDisplay {

	public CTEntryDiffDisplay(
		CTCollection ctCollection,
		CTDisplayRendererRegistry ctDisplayRendererRegistry, CTEntry ctEntry,
		CTEntryLocalService ctEntryLocalService,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Language language,
		String name) {

		_ctCollection = ctCollection;
		_ctDisplayRendererRegistry = ctDisplayRendererRegistry;
		_ctEntry = ctEntry;
		_ctEntryLocalService = ctEntryLocalService;
		_httpServletRequest = httpServletRequest;
		_httpServletResponse = httpServletResponse;
		_language = language;
		_name = name;
	}

	public String getLeftTitle() {
		if (isChangeType(CTConstants.CT_CHANGE_TYPE_DELETION)) {
			return StringBundler.concat(
				_language.get(_httpServletRequest, "production"), " : ", _name,
				" (", _language.get(_httpServletRequest, "deleted"), ")");
		}

		return StringBundler.concat(
			_language.get(_httpServletRequest, "production"), " : ", _name);
	}

	public String getRightTitle() {
		if (isChangeType(CTConstants.CT_CHANGE_TYPE_ADDITION)) {
			return StringBundler.concat(
				_ctCollection.getName(), " : ", _name, " (",
				_language.get(_httpServletRequest, "new"), ")");
		}

		return StringBundler.concat(_ctCollection.getName(), " : ", _name);
	}

	public boolean isChangeType(int changeType) {
		if (_ctEntry.getChangeType() == changeType) {
			return true;
		}

		return false;
	}

	public void renderLeftCTRow() throws Exception {
		if (_ctCollection.getStatus() == WorkflowConstants.STATUS_APPROVED) {
			try (SafeClosable safeClosable = CTSQLModeThreadLocal.setCTSQLMode(
					_getCTSQLMode(_ctEntry.getCtCollectionId()))) {

				_ctDisplayRendererRegistry.renderCTEntry(
					_httpServletRequest, _httpServletResponse,
					_ctEntry.getCtCollectionId(), _ctEntry);
			}
		}
		else {
			_ctDisplayRendererRegistry.renderCTEntry(
				_httpServletRequest, _httpServletResponse,
				CTConstants.CT_COLLECTION_ID_PRODUCTION, _ctEntry);
		}
	}

	public void renderRightCTRow() throws Exception {
		if (_ctCollection.getStatus() == WorkflowConstants.STATUS_APPROVED) {
			long ctCollectionId = _ctEntryLocalService.getCTRowCTCollectionId(
				_ctEntry);

			try (SafeClosable safeClosable = CTSQLModeThreadLocal.setCTSQLMode(
					_getCTSQLMode(ctCollectionId))) {

				_ctDisplayRendererRegistry.renderCTEntry(
					_httpServletRequest, _httpServletResponse, ctCollectionId,
					_ctEntry);
			}
		}
		else {
			_ctDisplayRendererRegistry.renderCTEntry(
				_httpServletRequest, _httpServletResponse,
				_ctCollection.getCtCollectionId(), _ctEntry);
		}
	}

	private CTSQLModeThreadLocal.CTSQLMode _getCTSQLMode(long ctCollectionId) {
		if (ctCollectionId == CTConstants.CT_COLLECTION_ID_PRODUCTION) {
			return CTSQLModeThreadLocal.CTSQLMode.DEFAULT;
		}

		CTEntry ctEntry = _ctEntry;

		if (ctCollectionId != _ctEntry.getCtCollectionId()) {
			ctEntry = _ctEntryLocalService.fetchCTEntry(
				ctCollectionId, _ctEntry.getModelClassNameId(),
				_ctEntry.getModelClassPK());

			if (ctEntry == null) {
				return CTSQLModeThreadLocal.CTSQLMode.DEFAULT;
			}
		}

		if (ctEntry.getChangeType() == CTConstants.CT_CHANGE_TYPE_DELETION) {
			return CTSQLModeThreadLocal.CTSQLMode.CT_ONLY;
		}

		return CTSQLModeThreadLocal.CTSQLMode.DEFAULT;
	}

	private final CTCollection _ctCollection;
	private final CTDisplayRendererRegistry _ctDisplayRendererRegistry;
	private final CTEntry _ctEntry;
	private final CTEntryLocalService _ctEntryLocalService;
	private final HttpServletRequest _httpServletRequest;
	private final HttpServletResponse _httpServletResponse;
	private final Language _language;
	private final String _name;

}