/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.application.list.display.context.logic;

import com.liferay.product.navigation.personal.menu.BasePersonalMenuEntry;
import com.liferay.product.navigation.personal.menu.PersonalMenuEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Samuel Trong Tran
 */
public class PersonalMenuEntryHelper {

	public PersonalMenuEntryHelper(
		List<PersonalMenuEntry> personalMenuEntries) {

		_personalMenuEntries = personalMenuEntries;
	}

	public List<BasePersonalMenuEntry> getBasePersonalMenuEntries() {
		List<BasePersonalMenuEntry> basePersonalMenuEntries = new ArrayList<>();

		for (PersonalMenuEntry personalMenuEntry : getPersonalMenuEntries()) {
			if (personalMenuEntry instanceof BasePersonalMenuEntry) {
				basePersonalMenuEntries.add(
					(BasePersonalMenuEntry)personalMenuEntry);
			}
		}

		return basePersonalMenuEntries;
	}

	public List<PersonalMenuEntry> getPersonalMenuEntries() {
		return _personalMenuEntries;
	}

	public boolean hasPersonalMenuEntry(String portletId) {
		for (BasePersonalMenuEntry basePersonalMenuEntry :
				getBasePersonalMenuEntries()) {

			if (portletId.equals(basePersonalMenuEntry.getPortletId())) {
				return true;
			}
		}

		return false;
	}

	private final List<PersonalMenuEntry> _personalMenuEntries;

}