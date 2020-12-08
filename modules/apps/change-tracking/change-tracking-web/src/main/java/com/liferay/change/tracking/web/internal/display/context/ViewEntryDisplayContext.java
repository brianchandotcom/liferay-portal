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
import com.liferay.change.tracking.web.internal.display.CTDisplayRendererRegistry;
import com.liferay.portal.change.tracking.sql.CTSQLModeThreadLocal;
import com.liferay.portal.kernel.model.BaseModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Samuel Trong Tran
 */
public class ViewEntryDisplayContext<T extends BaseModel<T>> {

	public ViewEntryDisplayContext(
		T baseModel, CTDisplayRendererRegistry ctDisplayRendererRegistry,
		long modelClassNameId) {

		_baseModel = baseModel;
		_ctDisplayRendererRegistry = ctDisplayRendererRegistry;
		_modelClassNameId = modelClassNameId;
	}

	public void renderEntry(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		_ctDisplayRendererRegistry.renderCTEntry(
			httpServletRequest, httpServletResponse,
			CTConstants.CT_COLLECTION_ID_PRODUCTION,
			CTSQLModeThreadLocal.CTSQLMode.DEFAULT, 0, _baseModel,
			_modelClassNameId, null);
	}

	private final T _baseModel;
	private final CTDisplayRendererRegistry _ctDisplayRendererRegistry;
	private final long _modelClassNameId;

}