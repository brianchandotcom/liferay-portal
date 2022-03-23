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

package com.liferay.analytics.dxp.entity.internal.dispatch.executor;

import com.liferay.analytics.dxp.entity.internal.helper.UploadAnalyticsDXPEntityDispatchTaskExecutorHelper;
import com.liferay.dispatch.executor.BaseDispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutorOutput;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.IOException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
@Component(
	immediate = true,
	property = {
		"dispatch.task.executor.name=" + UploadRoleAnalyticsDXPEntityDispatchTaskExecutor.KEY,
		"dispatch.task.executor.type=" + UploadRoleAnalyticsDXPEntityDispatchTaskExecutor.KEY
	},
	service = DispatchTaskExecutor.class
)
public class UploadRoleAnalyticsDXPEntityDispatchTaskExecutor
	extends BaseDispatchTaskExecutor {

	public static final String KEY = "upload-role-analytics-dxp-entities";

	@Override
	public void doExecute(
			DispatchTrigger dispatchTrigger,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput)
		throws IOException, PortalException {

		_uploadAnalyticsDXPEntityDispatchTaskExecutorHelper.doExecute(
			"role-analytics-dxp-entities", dispatchTrigger,
			dispatchTaskExecutorOutput);
	}

	@Override
	public String getName() {
		return KEY;
	}

	@Reference
	private UploadAnalyticsDXPEntityDispatchTaskExecutorHelper
		_uploadAnalyticsDXPEntityDispatchTaskExecutorHelper;

}