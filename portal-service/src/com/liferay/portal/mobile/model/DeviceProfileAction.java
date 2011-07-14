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
 * The extended model interface for the DeviceProfileAction service. Represents a row in the &quot;DeviceProfileAction&quot; database table, with each column mapped to a property of this class.
 *
 * @author Edward C. Han
 * @see DeviceProfileActionModel
 * @see com.liferay.portal.mobile.model.impl.DeviceProfileActionImpl
 * @see com.liferay.portal.mobile.model.impl.DeviceProfileActionModelImpl
 * @generated
 */
public interface DeviceProfileAction extends DeviceProfileActionModel,
	SaveableModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.mobile.model.impl.DeviceProfileActionImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties();

	public void setTypeSettings(java.lang.String typeSettings);

	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties);
}