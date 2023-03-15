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

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.web.internal.model.display.contacts.card.template.ContactsCardTemplateDisplay;
import com.liferay.osb.faro.web.internal.model.display.main.FaroEntityDisplay;

import java.util.Map;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class ContactsCardDisplay {

	public ContactsCardDisplay() {
	}

	public ContactsCardDisplay(
		FaroEntityDisplay faroEntityDisplay,
		ContactsCardTemplateDisplay contactsCardTemplateDisplay,
		Map<String, Object> contactsCardData) {

		_faroEntityDisplay = faroEntityDisplay;
		_contactsCardTemplateDisplay = contactsCardTemplateDisplay;
		_contactsCardData = contactsCardData;
	}

	private Map<String, Object> _contactsCardData;
	private ContactsCardTemplateDisplay _contactsCardTemplateDisplay;
	private FaroEntityDisplay _faroEntityDisplay;

}