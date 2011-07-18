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

package com.liferay.portal.mobile.model;

import com.liferay.portal.model.SaveableModel;

/**
 * The extended model interface for the DeviceProfileRule service. Represents a row in the &quot;DeviceProfileRule&quot; database table, with each column mapped to a property of this class.
 *
 * @author Edward C. Han
 * @see DeviceProfileRuleModel
 * @see com.liferay.portal.mobile.model.impl.DeviceProfileRuleImpl
 * @see com.liferay.portal.mobile.model.impl.DeviceProfileRuleModelImpl
 * @generated
 */
public interface DeviceProfileRule extends DeviceProfileRuleModel, SaveableModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.mobile.model.impl.DeviceProfileRuleImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public com.liferay.portal.kernel.util.UnicodeProperties getRuleTypeSettingsProperties();

	public void setRuleTypeSettings(java.lang.String ruleTypeSettings);

	public void setRuleTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties ruleTypeSettingsProperties);

	public java.util.Collection<com.liferay.portal.mobile.model.DeviceProfileAction> getDeviceProfileActions()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;
}