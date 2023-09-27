/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.util.comparator;

import com.liferay.osb.faro.contacts.model.ContactsCardTemplate;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Shinn Lok
 */
public class ContactsCardTemplateNameComparator
	extends OrderByComparator<ContactsCardTemplate> {

	public static final String ORDER_BY_ASC =
		"OSBFaro_ContactsCardTemplate.name ASC";

	public static final String ORDER_BY_DESC =
		"OSBFaro_ContactsCardTemplate.name DESC";

	public static final String[] ORDER_BY_FIELDS = {"name"};

	public ContactsCardTemplateNameComparator() {
		this(true);
	}

	public ContactsCardTemplateNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		ContactsCardTemplate contactsCardTemplate1,
		ContactsCardTemplate contactsCardTemplate2) {

		String name1 = contactsCardTemplate1.getName();
		String name2 = contactsCardTemplate2.getName();

		if (_ascending) {
			return name1.compareTo(name2);
		}

		return name2.compareTo(name1);
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}

		return ORDER_BY_DESC;
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;

}