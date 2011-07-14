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

import com.liferay.portal.kernel.util.UnicodeProperties;

/**
 * The model implementation for the DeviceProfileAction service. Represents a row in the &quot;DeviceProfileAction&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portal.mobile.model.DeviceProfileAction} interface.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
public class DeviceProfileActionImpl extends DeviceProfileActionBaseImpl {
	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. All methods that expect a device profile action model instance should use the {@link DeviceProfileAction} interface instead.
	 */
	public DeviceProfileActionImpl() {
	}

	public UnicodeProperties getTypeSettingsProperties() {
		if (_typeSettingsProperties == null) {
			_typeSettingsProperties = new UnicodeProperties(true);

			_typeSettingsProperties.fastLoad(super.getTypeSettings());
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

	private UnicodeProperties _typeSettingsProperties = null;
}