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

package com.liferay.batch.engine.web.internal.portlet.action;

import com.liferay.batch.engine.constants.BatchEnginePortletKeys;
import com.liferay.batch.engine.service.BatchEngineExportTaskService;
import com.liferay.batch.engine.web.internal.display.context.BatchEngineExportTaskDisplayContext;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matija Petanjek
 */
@Component(
	property = {
		"javax.portlet.name=" + BatchEnginePortletKeys.BATCH,
		"mvc.command.name=/batch_engine/view_batch_engine_export_tasks"
	},
	service = MVCRenderCommand.class
)
public class ViewBatchEngineExportTasksMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		BatchEngineExportTaskDisplayContext
			batchEngineExportTaskDisplayContext =
				new BatchEngineExportTaskDisplayContext(
					_batchEngineExportTaskService, renderRequest,
					renderResponse);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			batchEngineExportTaskDisplayContext);

		return "/view_batch_engine_export_tasks.jsp";
	}

	@Reference
	private BatchEngineExportTaskService _batchEngineExportTaskService;

}