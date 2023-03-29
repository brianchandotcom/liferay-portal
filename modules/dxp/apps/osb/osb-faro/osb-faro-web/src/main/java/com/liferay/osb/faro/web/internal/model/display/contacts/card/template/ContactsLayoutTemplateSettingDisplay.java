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

package com.liferay.osb.faro.web.internal.model.display.contacts.card.template;

/**
 * @author Matthew Kong
 */
public class ContactsLayoutTemplateSettingDisplay {

	public ContactsLayoutTemplateSettingDisplay() {
	}

	public ContactsLayoutTemplateSettingDisplay(
		long contactsCardTemplateId, int size) {

		_contactsCardTemplateId = contactsCardTemplateId;
		_size = size;
	}

	public long getContactsCardTemplateId() {
		return _contactsCardTemplateId;
	}

	public int getSize() {
		return _size;
	}

	private long _contactsCardTemplateId;
	private int _size;

}