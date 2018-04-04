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

package com.liferay.contacts.uad.display.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.contacts.model.Entry;
import com.liferay.contacts.uad.constants.ContactsUADConstants;
import com.liferay.contacts.uad.test.EntryUADEntityTestHelper;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import com.liferay.user.associated.data.aggregator.UADAggregator;
import com.liferay.user.associated.data.display.UADEntityDisplay;
import com.liferay.user.associated.data.test.util.BaseUADEntityDisplayTestCase;

import org.junit.After;
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
public class EntryUADEntityDisplayTest extends BaseUADEntityDisplayTestCase {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new LiferayIntegrationTestRule();

	@Override
	protected BaseModel<?> addBaseModel(long userId) throws Exception {
		Entry entry = _entryUADEntityTestHelper.addEntry(userId);

		_entries.add(entry);

		return entry;
	}

	@Override
	protected String getApplicationName() {
		return ContactsUADConstants.APPLICATION_NAME;
	}

	@Override
	protected UADAggregator getUADAggregator() {
		return _uadAggregator;
	}

	@Override
	protected UADEntityDisplay getUADEntityDisplay() {
		return _uadEntityDisplay;
	}

	@Override
	protected String getTypeDescription() {
		return "";
	}

	@After
	public void tearDown() throws Exception {
		_entryUADEntityTestHelper.cleanUpDependencies(_entries);
	}

	@DeleteAfterTestRun
	private final List<Entry> _entries = new ArrayList<Entry>();
	@Inject
	private EntryUADEntityTestHelper _entryUADEntityTestHelper;
	@Inject(filter = "model.class.name=" +
	ContactsUADConstants.CLASS_NAME_ENTRY)
	private UADAggregator _uadAggregator;
	@Inject(filter = "model.class.name=" +
	ContactsUADConstants.CLASS_NAME_ENTRY)
	private UADEntityDisplay _uadEntityDisplay;
}