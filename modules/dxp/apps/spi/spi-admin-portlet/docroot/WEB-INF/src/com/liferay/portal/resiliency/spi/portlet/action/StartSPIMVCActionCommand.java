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

package com.liferay.portal.resiliency.spi.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.resiliency.spi.service.SPIDefinitionServiceUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

/**
 * @author Michael C. Han
 */
public class StartSPIMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long spiDefinitionId = ParamUtil.getLong(
			actionRequest, "spiDefinitionId");

		SPIDefinitionServiceUtil.startSPIinBackground(spiDefinitionId);
	}

}