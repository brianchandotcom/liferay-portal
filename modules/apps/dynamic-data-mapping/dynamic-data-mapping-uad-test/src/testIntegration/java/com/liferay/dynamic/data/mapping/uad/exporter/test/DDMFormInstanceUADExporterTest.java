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
			DDMFormInstanceRecordTestUtil.addDDMFormInstanceRecord(
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

	@Inject(filter = "component.name=*.DDMFormInstanceUADExporter")
	private UADExporter _uadExporter;

}