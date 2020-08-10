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

package com.liferay.commerce.product.model.impl;

import com.liferay.commerce.product.service.CommerceChannelLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.impl.GroupImpl;

/**
 * @author Alec Sloan
 * @author Alessio Antonio Rendina
 */
public class CommerceChannelImpl extends CommerceChannelBaseImpl {

	public CommerceChannelImpl() {
	}

	@Override
	public Group getGroup() {
		if (getCommerceChannelId() > 0) {
			try {
				return CommerceChannelLocalServiceUtil.getCommerceChannelGroup(
					getCommerceChannelId());
			}
			catch (Exception e) {
				_log.error("Unable to get commerce channel group", e);
			}
		}

		return new GroupImpl();
	}

	@Override
	public long getGroupId() {
		Group group = getGroup();

		return group.getGroupId();
	}

	@Override
	public UnicodeProperties getTypeSettingsProperties() {
		if (_typeSettingsProperties == null) {
			_typeSettingsProperties = new UnicodeProperties(true);

			_typeSettingsProperties.fastLoad(getTypeSettings());
		}

		return _typeSettingsProperties;
	}

	@Override
	public void setTypeSettings(String typeSettings) {
		super.setTypeSettings(typeSettings);

		_typeSettingsProperties = null;
	}

	@Override
	public void setTypeSettingsProperties(
		UnicodeProperties typeSettingsProperties) {

		_typeSettingsProperties = typeSettingsProperties;

		if (_typeSettingsProperties == null) {
			_typeSettingsProperties = new UnicodeProperties();
		}

		super.setTypeSettings(_typeSettingsProperties.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceChannelImpl.class);

	private UnicodeProperties _typeSettingsProperties;

}