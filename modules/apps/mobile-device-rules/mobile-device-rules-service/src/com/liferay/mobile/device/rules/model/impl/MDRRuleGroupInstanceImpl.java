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

package com.liferay.mobile.device.rules.model.impl;

import com.liferay.mobile.device.rules.model.MDRAction;
import com.liferay.mobile.device.rules.model.MDRRuleGroup;
import com.liferay.mobile.device.rules.service.MDRActionLocalServiceUtil;
import com.liferay.mobile.device.rules.service.MDRRuleGroupLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Edward C. Han
 */
public class MDRRuleGroupInstanceImpl extends MDRRuleGroupInstanceBaseImpl {

	@Override
	public List<MDRAction> getActions() {
		return MDRActionLocalServiceUtil.getActions(getRuleGroupInstanceId());
	}

	@Override
	public MDRRuleGroup getRuleGroup() throws PortalException {
		return MDRRuleGroupLocalServiceUtil.getRuleGroup(getRuleGroupId());
	}

}