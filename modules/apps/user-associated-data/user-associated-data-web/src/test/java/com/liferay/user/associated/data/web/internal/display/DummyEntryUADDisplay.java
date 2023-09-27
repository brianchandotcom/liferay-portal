/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.associated.data.web.internal.display;

/**
 * @author Drew Brokke
 */
public class DummyEntryUADDisplay extends BaseDummyUADDisplay<DummyEntry> {

	public DummyEntryUADDisplay(DummyService<DummyEntry> dummyEntryService) {
		_dummyEntryService = dummyEntryService;
	}

	@Override
	public Class<DummyEntry> getTypeClass() {
		return DummyEntry.class;
	}

	@Override
	public boolean isInTrash(DummyEntry dummyEntry) {
		return false;
	}

	@Override
	protected DummyService<DummyEntry> getDummyService() {
		return _dummyEntryService;
	}

	private final DummyService<DummyEntry> _dummyEntryService;

}