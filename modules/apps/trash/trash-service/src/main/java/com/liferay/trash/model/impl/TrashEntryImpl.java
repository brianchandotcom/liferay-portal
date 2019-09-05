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

package com.liferay.trash.model.impl;

import com.liferay.portal.kernel.util.UnicodeProperties;
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
		if (_typeSettingsProperties == null) {
			return super.getTypeSettings();
		}

		return _typeSettingsProperties.toString();
	}

	@Override
	public UnicodeProperties getTypeSettingsProperties() {
		if (_typeSettingsProperties == null) {
			_typeSettingsProperties = new UnicodeProperties(true);

			_typeSettingsProperties.fastLoad(super.getTypeSettings());
		}

		return _typeSettingsProperties;
	}

	@Override
	public String getTypeSettingsProperty(String key) {
		UnicodeProperties typeSettingsProperties = getTypeSettingsProperties();

		return typeSettingsProperties.getProperty(key);
	}

	@Override
	public String getTypeSettingsProperty(String key, String defaultValue) {
		UnicodeProperties typeSettingsProperties = getTypeSettingsProperties();

		return typeSettingsProperties.getProperty(key, defaultValue);
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
		_typeSettingsProperties = null;

		super.setTypeSettings(typeSettings);
	}

	@Override
	public void setTypeSettingsProperties(
		UnicodeProperties typeSettingsProperties) {

		_typeSettingsProperties = typeSettingsProperties;

		super.setTypeSettings(_typeSettingsProperties.toString());
	}

	private TrashEntry _rootEntry;
	private UnicodeProperties _typeSettingsProperties;

}