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

package com.liferay.change.tracking.web.internal.display.context;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.web.internal.display.CTDisplayRendererRegistry;
import com.liferay.change.tracking.web.internal.display.CTEntryDiffDisplay;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.change.tracking.sql.CTSQLModeThreadLocal;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Samuel Trong Tran
 */
public class ViewEntryDisplayContext {

	public ViewEntryDisplayContext(
		CTCollectionLocalService ctCollectionLocalService,
		CTDisplayRendererRegistry ctDisplayRendererRegistry, CTEntry ctEntry,
		Language language, long modelClassNameId, long modelClassPK) {

		_ctCollectionLocalService = ctCollectionLocalService;
		_ctDisplayRendererRegistry = ctDisplayRendererRegistry;
		_ctEntry = ctEntry;
		_language = language;
		_model = _getModel(ctEntry, modelClassNameId, modelClassPK);
		_modelClassNameId = modelClassNameId;
	}

	public String getDividerTitle(ResourceBundle resourceBundle) {
		if (_ctEntry == null) {
			return _language.get(resourceBundle.getLocale(), "production");
		}

		CTCollection ctCollection = _ctCollectionLocalService.fetchCTCollection(
			_ctEntry.getCtCollectionId());

		if (ctCollection == null) {
			return _language.get(resourceBundle, "publication");
		}

		return StringBundler.concat(
			_language.get(resourceBundle, "publication"), " : ",
			ctCollection.getName());
	}

	public String getEntryDescription(HttpServletRequest httpServletRequest) {
		if (_ctEntry == null) {
			return null;
		}

		return _ctDisplayRendererRegistry.getEntryDescription(
			httpServletRequest, _ctEntry);
	}

	public <T extends BaseModel<T>> String getEntryTitle(Locale locale) {
		if (_ctEntry != null) {
			return _ctDisplayRendererRegistry.getTitle(
				_ctEntry.getCtCollectionId(), _ctEntry, locale);
		}

		return _ctDisplayRendererRegistry.getTitle(
			CTConstants.CT_COLLECTION_ID_PRODUCTION,
			CTSQLModeThreadLocal.CTSQLMode.DEFAULT, locale, (T)_model,
			_modelClassNameId);
	}

	public long getUserId() {
		if (_ctEntry == null) {
			return 0;
		}

		return _ctEntry.getUserId();
	}

	public <T extends BaseModel<T>> void renderEntry(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		if (_ctEntry != null) {
			_ctDisplayRendererRegistry.renderCTEntry(
				httpServletRequest, httpServletResponse,
				_ctEntry.getCtCollectionId(), _ctEntry,
				CTEntryDiffDisplay.TYPE_AFTER);

			return;
		}

		_ctDisplayRendererRegistry.renderCTEntry(
			httpServletRequest, httpServletResponse,
			CTConstants.CT_COLLECTION_ID_PRODUCTION,
			CTSQLModeThreadLocal.CTSQLMode.DEFAULT, 0, (T)_model,
			_modelClassNameId, null);
	}

	private <T extends BaseModel<T>> T _getModel(
		CTEntry ctEntry, long modelClassNameId, long modelClassPK) {

		if (ctEntry != null) {
			return null;
		}

		return _ctDisplayRendererRegistry.fetchCTModel(
			modelClassNameId, modelClassPK);
	}

	private final CTCollectionLocalService _ctCollectionLocalService;
	private final CTDisplayRendererRegistry _ctDisplayRendererRegistry;
	private final CTEntry _ctEntry;
	private final Language _language;
	private final BaseModel<?> _model;
	private final long _modelClassNameId;

}