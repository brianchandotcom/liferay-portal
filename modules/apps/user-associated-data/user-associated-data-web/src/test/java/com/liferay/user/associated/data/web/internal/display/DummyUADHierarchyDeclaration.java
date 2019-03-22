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