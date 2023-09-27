/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.uad.exporter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.portal.kernel.model.RepositoryEntry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.uad.test.RepositoryEntryUADTestHelper;

import com.liferay.user.associated.data.exporter.UADExporter;
import com.liferay.user.associated.data.test.util.BaseUADExporterTestCase;

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
public class RepositoryEntryUADExporterTest extends BaseUADExporterTestCase<RepositoryEntry> {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		_repositoryEntryUADTestHelper.cleanUpDependencies(_repositoryEntries);
	}

	@Override
	protected RepositoryEntry addBaseModel(long userId)
		throws Exception {
		RepositoryEntry repositoryEntry = _repositoryEntryUADTestHelper.addRepositoryEntry(userId);

		_repositoryEntries.add(repositoryEntry);

		return repositoryEntry;
	}

	@Override
	protected String getPrimaryKeyName() {
		return "repositoryEntryId";
	}

	@Override
	protected UADExporter getUADExporter() {
		return _uadExporter;
	}

	@DeleteAfterTestRun
	private final List<RepositoryEntry> _repositoryEntries = new ArrayList<RepositoryEntry>();
	@Inject
	private RepositoryEntryUADTestHelper _repositoryEntryUADTestHelper;
	@Inject(filter = "component.name=*.RepositoryEntryUADExporter")
	private UADExporter _uadExporter;
}