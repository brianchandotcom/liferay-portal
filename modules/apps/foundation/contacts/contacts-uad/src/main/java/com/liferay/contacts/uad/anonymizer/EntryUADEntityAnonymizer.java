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
import com.liferay.contacts.uad.entity.EntryUADEntity;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.anonymizer.BaseUADEntityAnonymizer;
import com.liferay.user.associated.data.anonymizer.UADEntityAnonymizer;
import com.liferay.user.associated.data.entity.UADEntity;
import com.liferay.user.associated.data.exception.UADEntityException;
import com.liferay.user.associated.data.util.UADAnonymizerHelper;
import com.liferay.user.associated.data.util.UADDynamicQueryHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Arrays;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(immediate = true, property =  {
	"model.class.name=" + ContactsUADConstants.CLASS_NAME_ENTRY}, service = UADEntityAnonymizer.class)
public class EntryUADEntityAnonymizer extends BaseUADEntityAnonymizer {
	@Override
	public void autoAnonymize(UADEntity uadEntity) throws PortalException {
		Entry entry = _getEntry(uadEntity);

		_autoAnonymize(entry, uadEntity.getUserId());
	}

	@Override
	public void autoAnonymizeAll(final long userId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery = _getActionableDynamicQuery(userId);

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<Entry>() {
				@Override
				public void performAction(Entry entry)
					throws PortalException {
					_autoAnonymize(entry, userId);
				}
			});

		actionableDynamicQuery.performActions();
	}

	@Override
	public void delete(UADEntity uadEntity) throws PortalException {
		_entryLocalService.deleteEntry(_getEntry(uadEntity));
	}

	@Override
	public void deleteAll(long userId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery = _getActionableDynamicQuery(userId);

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<Entry>() {
				@Override
				public void performAction(Entry entry)
					throws PortalException {
					_entryLocalService.deleteEntry(entry);
				}
			});

		actionableDynamicQuery.performActions();
	}

	@Override
	protected UADEntityAggregator getUADEntityAggregator() {
		return _uadEntityAggregator;
	}

	@Override
	public List<String> getUADEntityNonanonymizableFieldNames() {
		return Arrays.asList("fullName", "emailAddress", "comments");
	}

	private void _autoAnonymize(Entry entry, long userId)
		throws PortalException {
		User anonymousUser = _uadAnonymizerHelper.getAnonymousUser();

		if (entry.getUserId() == userId) {
			entry.setUserId(anonymousUser.getUserId());
			entry.setUserName(anonymousUser.getFullName());
		}

		_entryLocalService.updateEntry(entry);
	}

	private ActionableDynamicQuery _getActionableDynamicQuery(long userId) {
		return _uadDynamicQueryHelper.addActionableDynamicQueryCriteria(_entryLocalService.getActionableDynamicQuery(),
			ContactsUADConstants.USER_ID_FIELD_NAMES_ENTRY, userId);
	}

	private Entry _getEntry(UADEntity uadEntity) throws PortalException {
		_validate(uadEntity);

		EntryUADEntity entryUADEntity = (EntryUADEntity)uadEntity;

		return entryUADEntity.getEntry();
	}

	private void _validate(UADEntity uadEntity) throws PortalException {
		if (!(uadEntity instanceof EntryUADEntity)) {
			throw new UADEntityException();
		}
	}

	@Reference
	private EntryLocalService _entryLocalService;
	@Reference
	private UADAnonymizerHelper _uadAnonymizerHelper;
	@Reference
	private UADDynamicQueryHelper _uadDynamicQueryHelper;
	@Reference(target = "(model.class.name=" +
	ContactsUADConstants.CLASS_NAME_ENTRY + ")")
	private UADEntityAggregator _uadEntityAggregator;
}