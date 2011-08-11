/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.mobiledevicerules.service;

/**
 * <p>
 * This class is a wrapper for {@link MDRRuleService}.
 * </p>
 *
 * @author    Edward C. Han
 * @see       MDRRuleService
 * @generated
 */
public class MDRRuleServiceWrapper implements MDRRuleService {
	public MDRRuleServiceWrapper(MDRRuleService mdrRuleService) {
		_mdrRuleService = mdrRuleService;
	}

	public MDRRuleService getWrappedMDRRuleService() {
		return _mdrRuleService;
	}

	public void setWrappedMDRRuleService(MDRRuleService mdrRuleService) {
		_mdrRuleService = mdrRuleService;
	}

	private MDRRuleService _mdrRuleService;
}