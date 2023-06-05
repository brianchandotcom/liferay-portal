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

package com.liferay.object.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.io.Closeable;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mateus Santana
 */
@Ignore
@RunWith(Arquillian.class)
public class ObjectEntryPerformanceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		Class<?> clazz = ObjectEntryPerformanceTest.class;

		Properties properties = PropertiesUtil.load(
			clazz.getResourceAsStream(
				"dependencies/object-entry-performance.properties"),
			"UTF-8");

		_logFilePath = Paths.get(properties.getProperty("log.file"));

		Files.deleteIfExists(_logFilePath);

		_objectDefinition = ObjectDefinitionTestUtil.addObjectDefinition(
			false, _objectDefinitionLocalService,
			Collections.singletonList(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, "Performance",
					"performance")));

		_objectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				_objectDefinition.getObjectDefinitionId());

		_addObjectEntries(
			GetterUtil.getInteger(
				properties.getProperty("object.entries.count")));
	}

	@After
	public void tearDown() throws Exception {
		_deleteObjectEntries();
	}

	@Test
	public void testGetObjectEntries() throws Exception {
		try (Closeable closeable = _startTimer()) {
			_objectEntries = _objectEntryLocalService.getObjectEntries(
				0, _objectDefinition.getObjectDefinitionId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);
		}
	}

	private void _addObjectEntries(Integer numberOfEntries) throws Exception {
		ObjectEntryManager objectEntryManager =
			_objectEntryManagerRegistry.getObjectEntryManager(
				_objectDefinition.getStorageType());

		DTOConverterContext dtoConverterContext =
			new DefaultDTOConverterContext(
				false, Collections.emptyMap(), _dtoConverterRegistry, null,
				LocaleUtil.getDefault(), null, TestPropsValues.getUser());

		for (int counter = 0; counter < numberOfEntries; counter++) {
			objectEntryManager.addObjectEntry(
				dtoConverterContext, _objectDefinition,
				new ObjectEntry() {
					{
						properties = HashMapBuilder.<String, Object>put(
							"performance", RandomTestUtil.randomString()
						).build();
					}
				},
				ObjectDefinitionConstants.SCOPE_COMPANY);
		}
	}

	private void _deleteObjectEntries() throws Exception {
		for (com.liferay.object.model.ObjectEntry objectEntry :
				_objectEntries) {

			_objectEntryLocalService.deleteObjectEntry(objectEntry);
		}
	}

	private Closeable _startTimer() {
		Thread thread = Thread.currentThread();

		StackTraceElement stackTraceElement = thread.getStackTrace()[2];

		String invokerName = StringBundler.concat(
			stackTraceElement.getClassName(), StringPool.POUND,
			stackTraceElement.getMethodName());

		long startTime = System.currentTimeMillis();

		return () -> Files.write(
			_logFilePath,
			Collections.singletonList(
				StringBundler.concat(
					invokerName, " used ",
					System.currentTimeMillis() - startTime, "ms")),
			StandardOpenOption.APPEND, StandardOpenOption.CREATE,
			StandardOpenOption.WRITE);
	}

	private static Path _logFilePath;

	@Inject
	private DTOConverterRegistry _dtoConverterRegistry;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private List<com.liferay.object.model.ObjectEntry> _objectEntries;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectEntryManagerRegistry _objectEntryManagerRegistry;

}