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

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;

import com.liferay.user.associated.data.aggregator.DynamicQueryUADAggregator;
import com.liferay.user.associated.data.aggregator.UADAggregator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.Serializable;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(immediate = true, property =  {
	"model.class.name=" + ContactsUADConstants.CLASS_NAME_ENTRY}, service = UADAggregator.class)
public class EntryUADAggregator extends DynamicQueryUADAggregator<Entry> {
	@Override
	public String getApplicationName() {
		return ContactsUADConstants.APPLICATION_NAME;
	}

	@Override
	public Entry get(Serializable primaryKey) throws PortalException {
		return _entryLocalService.getEntry(Long.valueOf(primaryKey.toString()));
	}

	@Override
	protected long doCount(DynamicQuery dynamicQuery) {
		return _entryLocalService.dynamicQueryCount(dynamicQuery);
	}

	@Override
	protected DynamicQuery doGetDynamicQuery() {
		return _entryLocalService.dynamicQuery();
	}

	@Override
	protected List<Entry> doGetRange(DynamicQuery dynamicQuery, int start,
		int end) {
		return _entryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@Override
	protected String[] doGetUserIdFieldNames() {
		return ContactsUADConstants.USER_ID_FIELD_NAMES_ENTRY;
	}

	@Reference
	private EntryLocalService _entryLocalService;
}