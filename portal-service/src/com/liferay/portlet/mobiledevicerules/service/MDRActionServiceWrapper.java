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
 * This class is a wrapper for {@link MDRActionService}.
 * </p>
 *
 * @author    Edward C. Han
 * @see       MDRActionService
 * @generated
 */
public class MDRActionServiceWrapper implements MDRActionService {
	public MDRActionServiceWrapper(MDRActionService mdrActionService) {
		_mdrActionService = mdrActionService;
	}

	public MDRActionService getWrappedMDRActionService() {
		return _mdrActionService;
	}

	public void setWrappedMDRActionService(MDRActionService mdrActionService) {
		_mdrActionService = mdrActionService;
	}

	private MDRActionService _mdrActionService;
}