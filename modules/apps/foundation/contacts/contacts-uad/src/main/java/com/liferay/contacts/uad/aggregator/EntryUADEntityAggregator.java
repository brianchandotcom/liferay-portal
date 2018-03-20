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

package com.liferay.contacts.uad.aggregator;

import com.liferay.contacts.model.Entry;
import com.liferay.contacts.service.EntryLocalService;
import com.liferay.contacts.uad.constants.ContactsUADConstants;
import com.liferay.contacts.uad.entity.EntryUADEntity;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;

import com.liferay.user.associated.data.aggregator.BaseUADEntityAggregator;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.entity.UADEntity;
import com.liferay.user.associated.data.util.UADDynamicQueryHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(immediate = true, property =  {
	"model.class.name=" + ContactsUADConstants.CLASS_NAME_ENTRY}, service = UADEntityAggregator.class)
public class EntryUADEntityAggregator extends BaseUADEntityAggregator {
	@Override
	public int count(long userId) {
		return (int)_entryLocalService.dynamicQueryCount(_getDynamicQuery(
				userId));
	}

	@Override
	public List<UADEntity> getUADEntities(long userId, int start, int end) {
		List<Entry> entries = _entryLocalService.dynamicQuery(_getDynamicQuery(
					userId), start, end);

		List<UADEntity> uadEntities = new ArrayList<UADEntity>(entries.size());

		for (Entry entry : entries) {
			uadEntities.add(new EntryUADEntity(userId,
					_getUADEntityId(userId, entry), entry));
		}

		return uadEntities;
	}

	@Override
	public UADEntity getUADEntity(String uadEntityId) throws PortalException {
		Entry entry = _entryLocalService.getEntry(_getEntryId(uadEntityId));

		return new EntryUADEntity(_getUserId(uadEntityId), uadEntityId, entry);
	}

	@Override
	public String getUADEntitySetName() {
		return ContactsUADConstants.UAD_ENTITY_SET_NAME;
	}

	private DynamicQuery _getDynamicQuery(long userId) {
		return _uadDynamicQueryHelper.addDynamicQueryCriteria(_entryLocalService.dynamicQuery(),
			ContactsUADConstants.USER_ID_FIELD_NAMES_ENTRY, userId);
	}

	private long _getEntryId(String uadEntityId) {
		String[] uadEntityIdParts = uadEntityId.split("#");

		return Long.parseLong(uadEntityIdParts[0]);
	}

	private String _getUADEntityId(long userId, Entry entry) {
		return String.valueOf(entry.getEntryId()) + "#" +
		String.valueOf(userId);
	}

	private long _getUserId(String uadEntityId) {
		String[] uadEntityIdParts = uadEntityId.split("#");

		return Long.parseLong(uadEntityIdParts[1]);
	}

	@Reference
	private EntryLocalService _entryLocalService;
	@Reference
	private UADDynamicQueryHelper _uadDynamicQueryHelper;
}