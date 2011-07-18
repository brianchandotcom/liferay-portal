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

package com.liferay.portal.mobile.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.mobile.model.DeviceProfileAction;
import com.liferay.portal.mobile.service.DeviceProfileActionLocalServiceUtil;

import java.util.Collection;
import java.util.Collections;

/**
 * The extended model implementation for the DeviceProfileRule service. Represents a row in the &quot;DeviceProfileRule&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portal.mobile.model.DeviceProfileRule} interface.
 * </p>
 *
 * @author Edward C. Han
 */
public class DeviceProfileRuleImpl extends DeviceProfileRuleBaseImpl {
	public DeviceProfileRuleImpl() {
	}

	public UnicodeProperties getRuleTypeSettingsProperties() {
		if (_ruleTypeSettingsProperties == null) {
			_ruleTypeSettingsProperties = new UnicodeProperties(true);

			_ruleTypeSettingsProperties.fastLoad(getRuleTypeSettings());
		}

		return _ruleTypeSettingsProperties;
	}

	public void setRuleTypeSettings(String ruleTypeSettings) {
		super.setRuleTypeSettings(ruleTypeSettings);

		_ruleTypeSettingsProperties = null;
	}

	public void setRuleTypeSettingsProperties(
		UnicodeProperties ruleTypeSettingsProperties) {

		_ruleTypeSettingsProperties = ruleTypeSettingsProperties;

		super.setRuleTypeSettings(_ruleTypeSettingsProperties.toString());
	}

	public Collection<DeviceProfileAction> getDeviceProfileActions()
		throws PortalException, SystemException {

		if (getDeviceProfileId() > 0) {
			return DeviceProfileActionLocalServiceUtil.getDeviceProfileActions(
				getDeviceProfileRuleId());
		}
		else {
			return Collections.emptyList();
		}
	}

	private UnicodeProperties _ruleTypeSettingsProperties = null;
}