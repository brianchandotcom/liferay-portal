/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.sites.kernel.util.Sites;

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

	/**
	 * Returns the number of failed merge attempts for the layout set prototype
	 * since its last reset or update.
	 *
	 * @return the number of failed merge attempts for the layout set prototype
	 */
	@Override
	public int getMergeFailCount() throws PortalException {
		if (getLayoutSetPrototypeId() == 0) {
			return 0;
		}

		LayoutSet layoutSetPrototypeLayoutSet = getLayoutSet();

		UnicodeProperties layoutSetPrototypeSettingsUnicodeProperties =
			layoutSetPrototypeLayoutSet.getSettingsProperties();

		return GetterUtil.getInteger(
			layoutSetPrototypeSettingsUnicodeProperties.getProperty(
				Sites.MERGE_FAIL_COUNT));
	}

	@Override
	public UnicodeProperties getSettingsProperties() {
		if (_settingsUnicodeProperties == null) {
			_settingsUnicodeProperties = new UnicodeProperties(true);

			try {
				_settingsUnicodeProperties.load(super.getSettings());
			}
			catch (IOException ioException) {
				_log.error(ioException);
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