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

import com.liferay.mobile.device.rules.model.MDRRule;
import com.liferay.mobile.device.rules.service.MDRRuleLocalServiceUtil;

import java.util.Collections;
import java.util.List;

/**
 * @author Edward C. Han
 */
public class MDRRuleGroupImpl extends MDRRuleGroupBaseImpl {

	@Override
	public List<MDRRule> getRules() {
		if (getRuleGroupId() > 0) {
			return MDRRuleLocalServiceUtil.getRules(getRuleGroupId());
		}

		return Collections.emptyList();
	}

}