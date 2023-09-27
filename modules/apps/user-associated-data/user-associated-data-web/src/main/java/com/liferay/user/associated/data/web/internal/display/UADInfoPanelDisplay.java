/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.associated.data.web.internal.display;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.web.internal.util.SafeDisplayValueUtil;
import com.liferay.user.associated.data.web.internal.util.UADLanguageUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Samuel Trong Tran
 */
public class UADInfoPanelDisplay {

	public void addUADEntities(List<UADEntity<Object>> uadEntities) {
		_uadEntities.addAll(uadEntities);
	}

	public UADEntity<Object> getFirstUADEntity() {
		if (!_uadEntities.isEmpty()) {
			return _uadEntities.get(0);
		}

		return null;
	}

	public String getSubtitle(Locale locale) {
		if (_uadEntities.isEmpty()) {
			if (_uadDisplay != null) {
				return UADLanguageUtil.getApplicationName(_uadDisplay, locale);
			}

			return null;
		}
		else if (_uadEntities.size() == 1) {
			return _uadDisplay.getTypeName(locale);
		}

		return LanguageUtil.format(
			locale, "x-items-are-selected", getUADEntitiesCount());
	}

	public String getTitle(Locale locale) {
		if (_uadEntities.isEmpty()) {
			if (!_hierarchyView || !_topLevelView) {
				return _uadDisplay.getTypeName(locale);
			}

			return null;
		}
		else if (_uadEntities.size() == 1) {
			UADEntity<Object> uadEntity = getFirstUADEntity();

			Map<String, Object> displayValues = _uadDisplay.getFieldValues(
				uadEntity.getEntity(), _uadDisplay.getDisplayFieldNames(),
				locale);

			return SafeDisplayValueUtil.get(
				displayValues.get(_uadDisplay.getDisplayFieldNames()[0]));
		}

		if (!_hierarchyView) {
			return _uadDisplay.getTypeName(locale);
		}

		return null;
	}

	public UADDisplay<Object> getUADDisplay() {
		return _uadDisplay;
	}

	public int getUADEntitiesCount() {
		return _uadEntities.size();
	}

	public void setHierarchyView(boolean hierarchyView) {
		_hierarchyView = hierarchyView;
	}

	public void setTopLevelView(boolean topLevelView) {
		_topLevelView = topLevelView;
	}

	public void setUADDisplay(UADDisplay<Object> uadDisplay) {
		_uadDisplay = uadDisplay;
	}

	private boolean _hierarchyView;
	private boolean _topLevelView = true;
	private UADDisplay<Object> _uadDisplay;
	private final List<UADEntity<Object>> _uadEntities = new ArrayList<>();

}