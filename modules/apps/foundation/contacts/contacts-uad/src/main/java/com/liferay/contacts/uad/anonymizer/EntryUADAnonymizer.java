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

package com.liferay.contacts.uad.anonymizer;

import com.liferay.contacts.model.Entry;
import com.liferay.contacts.service.EntryLocalService;
import com.liferay.contacts.uad.constants.ContactsUADConstants;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import com.liferay.user.associated.data.anonymizer.DynamicQueryUADAnonymizer;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.util.UADAnonymizerHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Arrays;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(immediate = true, property =  {
	"model.class.name=" + ContactsUADConstants.CLASS_NAME_ENTRY}, service = UADAnonymizer.class)
public class EntryUADAnonymizer extends DynamicQueryUADAnonymizer<Entry> {
	@Override
	public void autoAnonymize(Entry entry, long userId)
		throws PortalException {
		User anonymousUser = _uadAnonymizerHelper.getAnonymousUser();

		if (entry.getUserId() == userId) {
			entry.setUserId(anonymousUser.getUserId());
			entry.setUserName(anonymousUser.getFullName());
		}

		_entryLocalService.updateEntry(entry);
	}

	@Override
	public void delete(Entry entry) throws PortalException {
		_entryLocalService.deleteEntry(entry);
	}

	@Override
	public List<String> getNonanonymizableFieldNames() {
		return Arrays.asList("fullName", "emailAddress", "comments");
	}

	@Override
	protected ActionableDynamicQuery doGetActionableDynamicQuery() {
		return _entryLocalService.getActionableDynamicQuery();
	}

	@Override
	protected String[] doGetUserIdFieldNames() {
		return ContactsUADConstants.USER_ID_FIELD_NAMES_ENTRY;
	}

	@Reference
	private EntryLocalService _entryLocalService;
	@Reference
	private UADAnonymizerHelper _uadAnonymizerHelper;
}