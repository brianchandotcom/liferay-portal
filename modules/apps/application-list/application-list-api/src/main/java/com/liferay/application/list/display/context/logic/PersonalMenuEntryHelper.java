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