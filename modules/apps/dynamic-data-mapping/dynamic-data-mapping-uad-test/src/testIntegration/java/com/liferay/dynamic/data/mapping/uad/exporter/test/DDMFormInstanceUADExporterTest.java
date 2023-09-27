/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.uad.exporter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.test.util.DDMFormInstanceRecordTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.exporter.UADExporter;
import com.liferay.user.associated.data.test.util.BaseUADExporterTestCase;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marcos Martins
 */
@RunWith(Arquillian.class)
public class DDMFormInstanceUADExporterTest
	extends BaseUADExporterTestCase<DDMFormInstance> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_group = GroupTestUtil.addGroup();
	}

	@Override
	@Test
	public void testExport() throws Exception {
		DDMFormInstance ddmFormInstance = addBaseModel(user.getUserId());

		Document document = getExportDocument(ddmFormInstance);

		assertColumnValue(
			document, getPrimaryKeyName(),
			String.valueOf(ddmFormInstance.getPrimaryKeyObj()));
	}

	@Override
	protected DDMFormInstance addBaseModel(long userId) throws Exception {
		DDMFormInstanceRecord ddmFormInstanceRecord =
			DDMFormInstanceRecordTestUtil.addDDMFormInstanceRecordWithoutValues(
				_group, userId);

		return ddmFormInstanceRecord.getFormInstance();
	}

	@Override
	protected String getPrimaryKeyName() {
		return "formInstanceId";
	}

	@Override
	protected UADExporter<DDMFormInstance> getUADExporter() {
		return _uadExporter;
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject(
		filter = "component.name=com.liferay.dynamic.data.mapping.uad.exporter.DDMFormInstanceUADExporter"
	)
	private UADExporter<DDMFormInstance> _uadExporter;

}