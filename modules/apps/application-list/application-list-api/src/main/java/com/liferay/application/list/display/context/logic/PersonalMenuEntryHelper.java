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

	public List<PersonalMenuEntry> getAllPersonalMenuEntries() {
		return _personalMenuEntries;
	}

	public List<PersonalMenuEntry> getBasePersonalMenuEntries() {
		List<PersonalMenuEntry> basePersonalMenuEntries = new ArrayList<>();

		for (PersonalMenuEntry personalMenuEntry :
				getAllPersonalMenuEntries()) {

			if (personalMenuEntry instanceof BasePersonalMenuEntry) {
				basePersonalMenuEntries.add(personalMenuEntry);
			}
		}

		return basePersonalMenuEntries;
	}

	public boolean hasEntry(String portletId) {
		for (PersonalMenuEntry personalMenuEntry :
				getBasePersonalMenuEntries()) {

			BasePersonalMenuEntry basePersonalMenuEntry =
				(BasePersonalMenuEntry)personalMenuEntry;

			if (portletId.equals(basePersonalMenuEntry.getPortletId())) {
				return true;
			}
		}

		return false;
	}

	private final List<PersonalMenuEntry> _personalMenuEntries;

}