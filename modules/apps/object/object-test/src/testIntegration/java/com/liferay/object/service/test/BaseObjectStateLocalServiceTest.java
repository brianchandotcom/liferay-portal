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

import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectStateFlow;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectStateFlowLocalService;
import com.liferay.object.service.ObjectStateLocalService;
import com.liferay.object.service.ObjectStateTransitionLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.util.PropsUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * @author Feliphe Marinho
 */
public class BaseObjectStateLocalServiceTest {

	@BeforeClass
	public static void setUpClass() {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-152677", "true"
			).build());
	}

	@AfterClass
	public static void tearDownClass() {
		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"feature.flag.LPS-152677", "false"
			).build());
	}

	@Before
	public void setUp() throws Exception {
		listTypeDefinition =
			listTypeDefinitionLocalService.addListTypeDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(
					RandomTestUtil.randomString()));

		listTypeEntries = new ArrayList<>();
		listTypeEntryMap = new HashMap<>();

		for (String key : Arrays.asList("step1", "step2", "step3")) {
			ListTypeEntry listTypeEntry =
				listTypeEntryLocalService.addListTypeEntry(
					TestPropsValues.getUserId(),
					listTypeDefinition.getListTypeDefinitionId(), key,
					LocalizedMapUtil.getLocalizedMap(key));

			listTypeEntries.add(listTypeEntry);

			listTypeEntryMap.put(key, listTypeEntry);
		}

		objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Collections.emptyList());

		ObjectField objectField = objectFieldLocalService.addCustomObjectField(
			TestPropsValues.getUserId(),
			listTypeDefinition.getListTypeDefinitionId(),
			objectDefinition.getObjectDefinitionId(),
			ObjectFieldConstants.BUSINESS_TYPE_PICKLIST,
			ObjectFieldConstants.DB_TYPE_STRING, null, false, true, "",
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			StringUtil.randomId(), true, true, Collections.emptyList());

		objectStateFlow =
			objectStateFlowLocalService.getObjectFieldObjectStateFlow(
				objectField.getObjectFieldId());
	}

	@After
	public void tearDown() throws PortalException {
		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);
	}

	@DeleteAfterTestRun
	protected ListTypeDefinition listTypeDefinition;

	@Inject
	protected ListTypeDefinitionLocalService listTypeDefinitionLocalService;

	protected List<ListTypeEntry> listTypeEntries;

	@Inject
	protected ListTypeEntryLocalService listTypeEntryLocalService;

	protected Map<String, ListTypeEntry> listTypeEntryMap;
	protected ObjectDefinition objectDefinition;

	@Inject
	protected ObjectFieldLocalService objectFieldLocalService;

	protected ObjectStateFlow objectStateFlow;

	@Inject
	protected ObjectStateFlowLocalService objectStateFlowLocalService;

	@Inject
	protected ObjectStateLocalService objectStateLocalService;

	@Inject
	protected ObjectStateTransitionLocalService
		objectStateTransitionLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}