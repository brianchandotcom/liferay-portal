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

package com.liferay.portlet.mobiledevicerules.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portlet.mobiledevicerules.model.MDRAction;
import com.liferay.portlet.mobiledevicerules.service.MDRActionLocalServiceUtil;

import java.util.Collections;
import java.util.List;

/**
 * @author Edward C. Han
 */
public class MDRRuleImpl extends MDRRuleBaseImpl {

	public MDRRuleImpl() {
	}

	public UnicodeProperties getTypeSettingsProperties() {
		if (_typeSettingsProperties == null) {
			_typeSettingsProperties = new UnicodeProperties(true);

			_typeSettingsProperties.fastLoad(getTypeSettings());
		}

		return _typeSettingsProperties;
	}

	public void setTypeSettings(String typeSettings) {
		super.setTypeSettings(typeSettings);

		_typeSettingsProperties = null;
	}

	public void setTypeSettingsProperties(
			UnicodeProperties typeSettingsProperties) {

		_typeSettingsProperties = typeSettingsProperties;

		super.setTypeSettings(_typeSettingsProperties.toString());
	}

	public List<MDRAction> getActions()
		throws PortalException, SystemException {

		if (getRuleId() > 0) {
			return MDRActionLocalServiceUtil.getActions(getRuleId());
		}
		else {
			return Collections.emptyList();
		}
	}

	private UnicodeProperties _typeSettingsProperties = null;

}