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

package com.liferay.user.associated.data.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.web.internal.registry.UADRegistry;

import javax.portlet.ActionRequest;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
public abstract class BaseUADMVCActionCommand extends BaseMVCActionCommand {

	protected Object getEntity(
			ActionRequest actionRequest, String uadRegistryKey)
		throws Exception {

		UADEntityAggregator uadEntityAggregator =
			uadRegistry.getUADEntityAggregator(uadRegistryKey);

		String entityId = ParamUtil.getString(actionRequest, "entityId");

		return uadEntityAggregator.getEntity(entityId);
	}

	@Reference
	protected UADRegistry uadRegistry;

}