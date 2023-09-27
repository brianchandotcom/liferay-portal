/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.opener.uad.exporter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.opener.model.DLOpenerFileEntryReference;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.exporter.UADExporter;
import com.liferay.user.associated.data.test.util.BaseUADExporterTestCase;

import java.util.ArrayList;
import java.util.List;

import org.junit.AssumptionViolatedException;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class DLOpenerFileEntryReferenceUADExporterTest
	extends BaseUADExporterTestCase<DLOpenerFileEntryReference> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected DLOpenerFileEntryReference addBaseModel(long userId)
		throws Exception {

		throw new AssumptionViolatedException("");
	}

	@Override
	protected String getPrimaryKeyName() {
		return "dlOpenerFileEntryReferenceId";
	}

	@Override
	protected UADExporter<DLOpenerFileEntryReference> getUADExporter() {
		return _uadExporter;
	}

	@DeleteAfterTestRun
	private final List<DLOpenerFileEntryReference>
		_dlOpenerFileEntryReferences = new ArrayList<>();

	@Inject(
		filter = "component.name=com.liferay.document.library.opener.uad.exporter.DLOpenerFileEntryReferenceUADExporter"
	)
	private UADExporter<DLOpenerFileEntryReference> _uadExporter;

}