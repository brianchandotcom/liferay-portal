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

package com.liferay.document.library.uad.exporter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.uad.test.DLFileShortcutUADTestHelper;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import com.liferay.user.associated.data.exporter.UADExporter;
import com.liferay.user.associated.data.test.util.BaseUADExporterTestCase;
import com.liferay.user.associated.data.test.util.WhenHasStatusByUserIdField;

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
public class DLFileShortcutUADExporterTest extends BaseUADExporterTestCase<DLFileShortcut>
	implements WhenHasStatusByUserIdField<DLFileShortcut> {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new LiferayIntegrationTestRule();

	@Override
	public DLFileShortcut addBaseModelWithStatusByUserId(long userId,
		long statusByUserId) throws Exception {
		DLFileShortcut dlFileShortcut = _dlFileShortcutUADTestHelper.addDLFileShortcutWithStatusByUserId(userId,
				statusByUserId);

		_dlFileShortcuts.add(dlFileShortcut);

		return dlFileShortcut;
	}

	@After
	public void tearDown() throws Exception {
		_dlFileShortcutUADTestHelper.cleanUpDependencies(_dlFileShortcuts);
	}

	@Override
	protected DLFileShortcut addBaseModel(long userId)
		throws Exception {
		DLFileShortcut dlFileShortcut = _dlFileShortcutUADTestHelper.addDLFileShortcut(userId);

		_dlFileShortcuts.add(dlFileShortcut);

		return dlFileShortcut;
	}

	@Override
	protected String getPrimaryKeyName() {
		return "fileShortcutId";
	}

	@Override
	protected UADExporter getUADExporter() {
		return _uadExporter;
	}

	@DeleteAfterTestRun
	private final List<DLFileShortcut> _dlFileShortcuts = new ArrayList<DLFileShortcut>();
	@Inject
	private DLFileShortcutUADTestHelper _dlFileShortcutUADTestHelper;
	@Inject(filter = "component.name=*.DLFileShortcutUADExporter")
	private UADExporter _uadExporter;
}