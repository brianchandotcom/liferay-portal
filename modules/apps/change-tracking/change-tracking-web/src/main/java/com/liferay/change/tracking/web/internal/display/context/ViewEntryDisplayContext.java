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
import com.liferay.change.tracking.web.internal.display.BasePersistenceRegistry;
import com.liferay.change.tracking.web.internal.display.CTDisplayRendererRegistry;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Samuel Trong Tran
 */
public class ViewEntryDisplayContext {

	public ViewEntryDisplayContext(
		BasePersistenceRegistry basePersistenceRegistry,
		CTDisplayRendererRegistry ctDisplayRendererRegistry) {

		_basePersistenceRegistry = basePersistenceRegistry;
		_ctDisplayRendererRegistry = ctDisplayRendererRegistry;
	}

	public <T extends BaseModel<T>> void renderEntry(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		long ctCollectionId = ParamUtil.getLong(
			httpServletRequest, "ctCollectionId",
			CTConstants.CT_COLLECTION_ID_PRODUCTION);
		long modelClassNameId = ParamUtil.getLong(
			httpServletRequest, "modelClassNameId");
		long modelClassPK = ParamUtil.getLong(
			httpServletRequest, "modelClassPK");

		T model = _ctDisplayRendererRegistry.fetchCTModel(
			ctCollectionId, modelClassNameId, modelClassPK);

		if (model == null) {
			model = _basePersistenceRegistry.fetchBaseModel(
				modelClassNameId, modelClassPK);
		}

		_ctDisplayRendererRegistry.renderCTEntry(
			httpServletRequest, httpServletResponse, ctCollectionId, model,
			modelClassNameId);
	}

	private final BasePersistenceRegistry _basePersistenceRegistry;
	private final CTDisplayRendererRegistry _ctDisplayRendererRegistry;

}