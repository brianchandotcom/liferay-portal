/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.mobiledevicerules.model.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.mobiledevicerules.model.MDRRule;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleLocalServiceUtil;

import java.util.Collections;
import java.util.List;

/**
 * @author Edward C. Han
 */
public class MDRRuleGroupImpl extends MDRRuleGroupBaseImpl {

	public MDRRuleGroupImpl() {
	}

	@Override
	public List<MDRRule> getRules() throws SystemException {
		if (getRuleGroupId() > 0) {
			return MDRRuleLocalServiceUtil.getRules(getRuleGroupId());
		}
		else {
			return Collections.emptyList();
		}
	}

}