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

package com.liferay.contacts.uad.entity;

import com.liferay.contacts.model.Entry;
import com.liferay.contacts.uad.constants.ContactsUADConstants;

import com.liferay.user.associated.data.entity.BaseUADEntity;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class EntryUADEntity extends BaseUADEntity {
	public EntryUADEntity(long userId, String uadEntityId, Entry entry) {
		super(userId, uadEntityId, ContactsUADConstants.CLASS_NAME_ENTRY);

		_entry = entry;
	}

	public Entry getEntry() {
		return _entry;
	}

	private final Entry _entry;
}