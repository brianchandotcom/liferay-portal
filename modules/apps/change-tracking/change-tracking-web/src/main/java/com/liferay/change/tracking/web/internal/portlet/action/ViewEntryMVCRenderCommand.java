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

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.web.internal.constants.CTPortletKeys;
import com.liferay.change.tracking.web.internal.constants.CTWebKeys;
import com.liferay.change.tracking.web.internal.display.CTDisplayRendererRegistry;
import com.liferay.change.tracking.web.internal.display.context.ViewEntryDisplayContext;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CTPortletKeys.PUBLICATIONS,
		"mvc.command.name=/publications/view_entry"
	},
	service = MVCRenderCommand.class
)
public class ViewEntryMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		long ctEntryId = ParamUtil.getLong(renderRequest, "ctEntryId");
		long modelClassNameId = ParamUtil.getLong(
			renderRequest, "modelClassNameId");
		long modelClassPK = ParamUtil.getLong(renderRequest, "modelClassPK");

		renderRequest.setAttribute(
			CTWebKeys.VIEW_ENTRY_DISPLAY_CONTEXT,
			_createViewEntryDisplayContext(
				ctEntryId, modelClassNameId, modelClassPK));

		return "/publications/view_entry.jsp";
	}

	private <T extends BaseModel<T>> ViewEntryDisplayContext<T>
		_createViewEntryDisplayContext(
			long ctEntryId, long modelClassNameId, long modelClassPK) {

		CTEntry ctEntry = _ctEntryLocalService.fetchCTEntry(ctEntryId);

		T baseModel = _ctDisplayRendererRegistry.fetchCTModel(
			modelClassNameId, modelClassPK);

		return new ViewEntryDisplayContext<>(
			baseModel, _ctCollectionLocalService, _ctDisplayRendererRegistry,
			ctEntry, _language, modelClassNameId);
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTDisplayRendererRegistry _ctDisplayRendererRegistry;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private Language _language;

}