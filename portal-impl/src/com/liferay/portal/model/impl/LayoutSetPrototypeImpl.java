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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.IOException;

/**
 * @author Brian Wing Shun Chan
 * @author Ryan Park
 */
public class LayoutSetPrototypeImpl extends LayoutSetPrototypeBaseImpl {

	@Override
	public Group getGroup() throws PortalException {
		return GroupLocalServiceUtil.getLayoutSetPrototypeGroup(
			getCompanyId(), getLayoutSetPrototypeId());
	}

	@Override
	public long getGroupId() throws PortalException {
		Group group = getGroup();

		return group.getGroupId();
	}

	@Override
	public LayoutSet getLayoutSet() throws PortalException {
		return LayoutSetLocalServiceUtil.getLayoutSet(
			getGroup().getGroupId(), true);
	}

	@Override
	public UnicodeProperties getSettingsProperties() {
		if (_settingsUnicodeProperties == null) {
			_settingsUnicodeProperties = new UnicodeProperties(true);

			try {
				_settingsUnicodeProperties.load(super.getSettings());
			}
			catch (IOException ioException) {
				_log.error(ioException, ioException);
			}
		}

		return _settingsUnicodeProperties;
	}

	@Override
	public String getSettingsProperty(String key) {
		UnicodeProperties settingsUnicodeProperties = getSettingsProperties();

		return settingsUnicodeProperties.getProperty(key);
	}

	@Override
	public boolean hasSetModifiedDate() {
		return true;
	}

	@Override
	public void setSettings(String settings) {
		_settingsUnicodeProperties = null;

		super.setSettings(settings);
	}

	@Override
	public void setSettingsProperties(
		UnicodeProperties settingsUnicodeProperties) {

		_settingsUnicodeProperties = settingsUnicodeProperties;

		super.setSettings(settingsUnicodeProperties.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSetPrototypeImpl.class);

	private UnicodeProperties _settingsUnicodeProperties;

}