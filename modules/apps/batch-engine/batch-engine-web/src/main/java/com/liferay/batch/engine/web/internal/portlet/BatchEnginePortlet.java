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

package com.liferay.batch.engine.web.internal.portlet;

import com.liferay.batch.engine.constants.BatchEnginePortletKeys;
import com.liferay.batch.engine.service.BatchEngineImportTaskService;
import com.liferay.batch.engine.web.internal.display.context.BatchEngineImportTaskDisplayContext;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matija Petanjek
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Batch Jobs",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view_batch_engine_import_tasks.jsp",
		"javax.portlet.name=" + BatchEnginePortletKeys.BATCH,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,power-user,user"
	},
	service = Portlet.class
)
public class BatchEnginePortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		BatchEngineImportTaskDisplayContext
			batchEngineImportTaskDisplayContext =
				new BatchEngineImportTaskDisplayContext(
					_batchEngineImportTaskService, renderRequest,
					renderResponse);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			batchEngineImportTaskDisplayContext);

		super.render(renderRequest, renderResponse);
	}

	@Reference
	private BatchEngineImportTaskService _batchEngineImportTaskService;

}