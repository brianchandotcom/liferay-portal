/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.associated.data.web.internal.display;

import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.display.UADHierarchyDeclaration;

import java.util.Locale;

/**
 * @author Drew Brokke
 */
public class DummyUADHierarchyDeclaration implements UADHierarchyDeclaration {

	public DummyUADHierarchyDeclaration(
		DummyEntryUADDisplay dummyEntryUADDisplay,
		DummyContainerUADDisplay dummyContainerUADDisplay) {

		_dummyEntryUADDisplay = dummyEntryUADDisplay;
		_dummyContainerUADDisplay = dummyContainerUADDisplay;
	}

	@Override
	public UADDisplay<?>[] getContainerUADDisplays() {
		return new UADDisplay[] {_dummyContainerUADDisplay};
	}

	@Override
	public String getEntitiesTypeLabel(Locale locale) {
		return null;
	}

	@Override
	public String[] getExtraColumnNames() {
		return new String[] {"uuid"};
	}

	@Override
	public UADDisplay<?>[] getNoncontainerUADDisplays() {
		return new UADDisplay[] {_dummyEntryUADDisplay};
	}

	private final DummyContainerUADDisplay _dummyContainerUADDisplay;
	private final DummyEntryUADDisplay _dummyEntryUADDisplay;

}