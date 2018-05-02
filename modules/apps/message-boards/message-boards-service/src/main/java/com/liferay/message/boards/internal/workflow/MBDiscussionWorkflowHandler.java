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

package com.liferay.message.boards.internal.workflow;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandler;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jorge Ferrer
 */
@Component(
	property = "model.class.name=com.liferay.message.boards.model.MBDiscussion",
	service = WorkflowHandler.class
)
public class MBDiscussionWorkflowHandler extends MBMessageWorkflowHandler {

	@Override
	@SuppressWarnings("rawtypes")
	public AssetRendererFactory getAssetRendererFactory() {
		return AssetRendererFactoryRegistryUtil.
			getAssetRendererFactoryByClassName(getClassName());
	}

	@Override
	public String getClassName() {
		return MBDiscussion.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		return ResourceActionsUtil.getModelResource(locale, getClassName());
	}

}