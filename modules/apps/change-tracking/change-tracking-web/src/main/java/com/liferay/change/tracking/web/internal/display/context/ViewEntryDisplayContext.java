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
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.web.internal.display.CTDisplayRendererRegistry;
import com.liferay.change.tracking.web.internal.display.CTEntryDiffDisplay;
import com.liferay.portal.change.tracking.sql.CTSQLModeThreadLocal;
import com.liferay.portal.kernel.model.BaseModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Samuel Trong Tran
 */
public class ViewEntryDisplayContext {

	public ViewEntryDisplayContext(
		CTDisplayRendererRegistry ctDisplayRendererRegistry, CTEntry ctEntry,
		long modelClassNameId, long modelClassPK) {

		_ctDisplayRendererRegistry = ctDisplayRendererRegistry;
		_ctEntry = ctEntry;
		_modelClassNameId = modelClassNameId;
		_modelClassPK = modelClassPK;
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

		T model = _ctDisplayRendererRegistry.fetchCTModel(
			_modelClassNameId, _modelClassPK);

		_ctDisplayRendererRegistry.renderCTEntry(
			httpServletRequest, httpServletResponse,
			CTConstants.CT_COLLECTION_ID_PRODUCTION,
			CTSQLModeThreadLocal.CTSQLMode.DEFAULT, 0, model, _modelClassNameId,
			null);
	}

	private final CTDisplayRendererRegistry _ctDisplayRendererRegistry;
	private final CTEntry _ctEntry;
	private final long _modelClassNameId;
	private final long _modelClassPK;

}