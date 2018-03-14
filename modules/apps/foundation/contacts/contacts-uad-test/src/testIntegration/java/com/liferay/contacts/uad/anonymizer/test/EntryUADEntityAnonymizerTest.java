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

package com.liferay.contacts.uad.anonymizer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.contacts.model.Entry;
import com.liferay.contacts.service.EntryLocalService;
import com.liferay.contacts.uad.constants.ContactsUADConstants;
import com.liferay.contacts.uad.test.EntryUADEntityTestHelper;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.anonymizer.UADEntityAnonymizer;
import com.liferay.user.associated.data.test.util.BaseUADEntityAnonymizerTestCase;

import org.junit.ClassRule;
import org.junit.Rule;

import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@RunWith(Arquillian.class)
public class EntryUADEntityAnonymizerTest
	extends BaseUADEntityAnonymizerTestCase {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new LiferayIntegrationTestRule();

	@Override
	protected BaseModel<?> addBaseModel(long userId) throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected BaseModel<?> addBaseModel(long userId, boolean deleteAfterTestRun)
		throws Exception {
		Entry entry = _entryUADEntityTestHelper.addEntry(userId);

		if (deleteAfterTestRun) {
			_entries.add(entry);
		}

		return entry;
	}

	@Override
	protected UADEntityAggregator getUADEntityAggregator() {
		return _uadEntityAggregator;
	}

	@Override
	protected UADEntityAnonymizer getUADEntityAnonymizer() {
		return _uadEntityAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {
		Entry entry = _entryLocalService.getEntry(baseModelPK);

		String userName = entry.getUserName();

		if ((entry.getUserId() != user.getUserId()) &&
				!userName.equals(user.getFullName())) {
			return true;
		}

		return false;
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		if (_entryLocalService.fetchEntry(baseModelPK) == null) {
			return true;
		}

		return false;
	}

	@DeleteAfterTestRun
	private final List<Entry> _entries = new ArrayList<Entry>();
	@Inject
	private EntryLocalService _entryLocalService;
	@Inject
	private EntryUADEntityTestHelper _entryUADEntityTestHelper;
	@Inject(filter = "model.class.name=" +
	ContactsUADConstants.CLASS_NAME_ENTRY)
	private UADEntityAggregator _uadEntityAggregator;
	@Inject(filter = "model.class.name=" +
	ContactsUADConstants.CLASS_NAME_ENTRY)
	private UADEntityAnonymizer _uadEntityAnonymizer;
}