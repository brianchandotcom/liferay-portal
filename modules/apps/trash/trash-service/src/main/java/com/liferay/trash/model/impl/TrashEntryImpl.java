/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.trash.model.impl;

import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.trash.model.TrashEntry;

/**
 * @author Zsolt Berentey
 */
public class TrashEntryImpl extends TrashEntryBaseImpl {

	@Override
	public TrashEntry getRootEntry() {
		return _rootEntry;
	}

	@Override
	public String getTypeSettings() {
		if (_typeSettingsUnicodeProperties == null) {
			return super.getTypeSettings();
		}

		return _typeSettingsUnicodeProperties.toString();
	}

	@Override
	public UnicodeProperties getTypeSettingsProperties() {
		if (_typeSettingsUnicodeProperties == null) {
			_typeSettingsUnicodeProperties = UnicodePropertiesBuilder.create(
				true
			).fastLoad(
				super.getTypeSettings()
			).build();
		}

		return _typeSettingsUnicodeProperties;
	}

	@Override
	public String getTypeSettingsProperty(String key) {
		UnicodeProperties typeSettingsUnicodeProperties =
			getTypeSettingsProperties();

		return typeSettingsUnicodeProperties.getProperty(key);
	}

	@Override
	public String getTypeSettingsProperty(String key, String defaultValue) {
		UnicodeProperties typeSettingsUnicodeProperties =
			getTypeSettingsProperties();

		return typeSettingsUnicodeProperties.getProperty(key, defaultValue);
	}

	@Override
	public boolean isTrashEntry(Class<?> clazz, long classPK) {
		if (clazz == null) {
			return false;
		}

		return isTrashEntry(clazz.getName(), classPK);
	}

	@Override
	public boolean isTrashEntry(String className, long classPK) {
		if (className.equals(getClassName()) && (classPK == getClassPK())) {
			return true;
		}

		return false;
	}

	@Override
	public void setRootEntry(TrashEntry rootEntry) {
		_rootEntry = rootEntry;
	}

	@Override
	public void setTypeSettings(String typeSettings) {
		_typeSettingsUnicodeProperties = null;

		super.setTypeSettings(typeSettings);
	}

	@Override
	public void setTypeSettingsProperties(
		UnicodeProperties typeSettingsUnicodeProperties) {

		_typeSettingsUnicodeProperties = typeSettingsUnicodeProperties;

		super.setTypeSettings(_typeSettingsUnicodeProperties.toString());
	}

	private TrashEntry _rootEntry;
	private UnicodeProperties _typeSettingsUnicodeProperties;

}