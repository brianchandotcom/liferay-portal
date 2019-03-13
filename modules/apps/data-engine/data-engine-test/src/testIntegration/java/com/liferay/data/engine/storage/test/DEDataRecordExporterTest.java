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

package com.liferay.data.engine.storage.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.model.DEDataDefinitionField;
import com.liferay.data.engine.model.DEDataRecord;
import com.liferay.data.engine.model.DEDataRecordCollection;
import com.liferay.data.engine.service.DEDataDefinitionRequestBuilder;
import com.liferay.data.engine.service.DEDataDefinitionSaveRequest;
import com.liferay.data.engine.service.DEDataDefinitionSaveResponse;
import com.liferay.data.engine.service.DEDataDefinitionService;
import com.liferay.data.engine.service.DEDataRecordCollectionService;
import com.liferay.data.engine.service.test.DEDataEngineTestUtil;
import com.liferay.data.engine.storage.DEDataRecordExporter;
import com.liferay.data.engine.storage.DEDataRecordExporterApplyRequest;
import com.liferay.data.engine.storage.DEDataRecordExporterApplyResponse;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.IOException;
import java.io.InputStream;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Leonardo Barros
 */
@Ignore
@RunWith(Arquillian.class)
public class DEDataRecordExporterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_user = UserTestUtil.addOmniAdminUser();

		setUpPermissionThreadLocal();
	}

	@Test
	public void testApply() throws Exception {
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			DEDataDefinition deDataDefinition = new DEDataDefinition();

			Map<String, String> field1Labels = new HashMap() {
				{
					put("en_US", "Field 1");
				}
			};

			DEDataDefinitionField deDataDefinitionField1 =
				new DEDataDefinitionField("field1", "text");

			deDataDefinitionField1.addLabels(field1Labels);

			Map<String, String> field2Labels = new HashMap() {
				{
					put("en_US", "Field 2");
				}
			};

			DEDataDefinitionField deDataDefinitionField2 =
				new DEDataDefinitionField("field2", "numeric");

			deDataDefinitionField2.addLabels(field2Labels);

			deDataDefinition.addName(LocaleUtil.US, "Data Definition Test");
			deDataDefinition.setDEDataDefinitionFields(
				Arrays.asList(deDataDefinitionField1, deDataDefinitionField2));
			deDataDefinition.setStorageType("json");

			DEDataDefinitionSaveRequest deDataDefinitionSaveRequest =
				DEDataDefinitionRequestBuilder.saveBuilder(
					deDataDefinition
				).inGroup(
					_group.getGroupId()
				).onBehalfOf(
					_user.getUserId()
				).build();

			DEDataDefinitionSaveResponse deDataDefinitionSaveResponse =
				_deDataDefinitionService.execute(deDataDefinitionSaveRequest);

			deDataDefinition =
				deDataDefinitionSaveResponse.getDEDataDefinition();

			DEDataRecordCollection deDataRecordCollection =
				DEDataEngineTestUtil.insertDEDataRecordCollection(
					_user, _group, deDataDefinition,
					_deDataRecordCollectionService);

			DEDataRecord deDataRecord1 = new DEDataRecord();

			Map<String, Object> values1 = new HashMap() {
				{
					put("field1", "Text 1");
					put("field2", 1);
				}
			};

			deDataRecord1.setValues(values1);

			deDataRecord1.setDEDataRecordCollection(deDataRecordCollection);

			deDataRecord1 = DEDataEngineTestUtil.insertDEDataRecord(
				_user, _group, deDataRecord1, _deDataRecordCollectionService);

			DEDataRecord deDataRecord2 = new DEDataRecord();

			Map<String, Object> values2 = new HashMap() {
				{
					put("field1", "Text 2");
					put("field2", 2);
				}
			};

			deDataRecord2.setValues(values2);

			deDataRecord2.setDEDataRecordCollection(deDataRecordCollection);

			deDataRecord2 = DEDataEngineTestUtil.insertDEDataRecord(
				_user, _group, deDataRecord2, _deDataRecordCollectionService);

			DEDataRecordExporterApplyRequest deDataRecordExporterApplyRequest =
				DEDataRecordExporterApplyRequest.Builder.newBuilder(
					Arrays.asList(deDataRecord1, deDataRecord2)
				).exportTo(
					"json"
				).build();

			DEDataRecordExporterApplyResponse
				deDataRecordExporterApplyResponse = _deDataRecordExporter.apply(
					deDataRecordExporterApplyRequest);

			JSONAssert.assertEquals(
				read("data-record-exporter.json"),
				deDataRecordExporterApplyResponse.getContent(), false);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	protected String read(String fileName) throws IOException {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(fileName);

		return StringUtil.read(inputStream);
	}

	protected void setUpPermissionThreadLocal() throws Exception {
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
	}

	@Inject(type = DEDataDefinitionService.class)
	private DEDataDefinitionService _deDataDefinitionService;

	@Inject(type = DEDataRecordCollectionService.class)
	private DEDataRecordCollectionService _deDataRecordCollectionService;

	@Inject(
		filter = "de.data.record.exporter.format=json",
		type = DEDataRecordExporter.class
	)
	private DEDataRecordExporter _deDataRecordExporter;

	@DeleteAfterTestRun
	private Group _group;

	private PermissionChecker _originalPermissionChecker;

	@DeleteAfterTestRun
	private User _user;

}