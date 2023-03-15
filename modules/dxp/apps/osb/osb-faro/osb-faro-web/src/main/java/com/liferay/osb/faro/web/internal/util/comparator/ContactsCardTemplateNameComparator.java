/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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