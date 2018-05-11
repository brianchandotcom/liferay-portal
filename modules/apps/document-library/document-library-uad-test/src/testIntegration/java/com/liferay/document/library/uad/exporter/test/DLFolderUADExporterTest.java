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

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.uad.test.DLFolderUADTestHelper;

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
public class DLFolderUADExporterTest extends BaseUADExporterTestCase<DLFolder>
	implements WhenHasStatusByUserIdField<DLFolder> {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new LiferayIntegrationTestRule();

	@Override
	public DLFolder addBaseModelWithStatusByUserId(long userId,
		long statusByUserId) throws Exception {
		DLFolder dlFolder = _dlFolderUADTestHelper.addDLFolderWithStatusByUserId(userId,
				statusByUserId);

		_dlFolders.add(dlFolder);

		return dlFolder;
	}

	@After
	public void tearDown() throws Exception {
		_dlFolderUADTestHelper.cleanUpDependencies(_dlFolders);
	}

	@Override
	protected DLFolder addBaseModel(long userId) throws Exception {
		DLFolder dlFolder = _dlFolderUADTestHelper.addDLFolder(userId);

		_dlFolders.add(dlFolder);

		return dlFolder;
	}

	@Override
	protected String getPrimaryKeyName() {
		return "folderId";
	}

	@Override
	protected UADExporter getUADExporter() {
		return _uadExporter;
	}

	@DeleteAfterTestRun
	private final List<DLFolder> _dlFolders = new ArrayList<DLFolder>();
	@Inject
	private DLFolderUADTestHelper _dlFolderUADTestHelper;
	@Inject(filter = "component.name=*.DLFolderUADExporter")
	private UADExporter _uadExporter;
}