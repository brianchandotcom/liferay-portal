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

package com.liferay.object.storage.salesforce.internal.rest.manager.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.storage.salesforce.configuration.SalesforceConfiguration;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Collections;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Guilherme Camacho
 */
@FeatureFlags("LPS-135430")
@RunWith(Arquillian.class)
public class SalesforceObjectEntryManagerImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_configurationProvider.saveCompanyConfiguration(
			SalesforceConfiguration.class, TestPropsValues.getCompanyId(),
			HashMapDictionaryBuilder.<String, Object>put(
				"consumerKey",
				TestPropsUtil.get("object.storage.salesforce.consumer.key")
			).put(
				"consumerSecret",
				TestPropsUtil.get("object.storage.salesforce.consumer.secret")
			).put(
				"loginURL",
				TestPropsUtil.get("object.storage.salesforce.login.url")
			).put(
				"password",
				TestPropsUtil.get("object.storage.salesforce.password")
			).put(
				"username",
				TestPropsUtil.get("object.storage.salesforce.username")
			).build());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_configurationProvider.saveCompanyConfiguration(
			SalesforceConfiguration.class, TestPropsValues.getCompanyId(),
			HashMapDictionaryBuilder.<String, Object>put(
				"consumerKey", ""
			).put(
				"consumerSecret", ""
			).put(
				"loginURL", ""
			).put(
				"password", ""
			).put(
				"username", ""
			).build());
	}

	@Before
	public void setUp() throws Exception {
		_user = TestPropsValues.getUser();

		_objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				_user.getUserId(), false, false,
				LocalizedMapUtil.getLocalizedMap("Ticket"), "Ticket", null,
				null, LocalizedMapUtil.getLocalizedMap("Tickets"),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_SALESFORCE,
				Collections.emptyList());

		ObjectField objectField = _objectFieldLocalService.addCustomObjectField(
			null, _user.getUserId(), 0,
			_objectDefinition.getObjectDefinitionId(),
			ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING, false, false, null,
			LocalizedMapUtil.getLocalizedMap("Title"), false, "title", false,
			false, Collections.emptyList());

		_objectFieldLocalService.updateCustomObjectField(
			"Title__c", objectField.getObjectFieldId(), 0,
			objectField.getBusinessType(), objectField.getDBType(), false,
			false, null, objectField.getLabelMap(), false,
			objectField.getName(), false, false,
			objectField.getObjectFieldSettings());

		_objectDefinition.setExternalReferenceCode("Ticket__c");
		_objectDefinition.setTitleObjectFieldId(objectField.getObjectFieldId());

		_objectDefinition =
			_objectDefinitionLocalService.updateObjectDefinition(
				_objectDefinition);

		_objectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				_user.getUserId(), _objectDefinition.getObjectDefinitionId());
	}

	@After
	public void tearDown() throws Exception {
		if (_objectDefinition != null) {
			_objectDefinitionLocalService.deleteObjectDefinition(
				_objectDefinition.getObjectDefinitionId());
		}
	}

	@Test
	public void testAddObjectEntry() throws Exception {
		ObjectEntry objectEntry = _objectEntryManager.addObjectEntry(
			_getDTOConverterContext(), _objectDefinition,
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						"title", RandomTestUtil.randomString()
					).build();
				}
			},
			ObjectDefinitionConstants.SCOPE_COMPANY);

		Assert.assertNotNull(objectEntry.getExternalReferenceCode());

		_objectEntryManager.deleteObjectEntry(
			TestPropsValues.getCompanyId(), _getDTOConverterContext(),
			objectEntry.getExternalReferenceCode(), _objectDefinition,
			ObjectDefinitionConstants.SCOPE_COMPANY);
	}

	@Test
	public void testAddOrUpdateObjectEntry() throws Exception {
		DTOConverterContext dtoConverterContext = _getDTOConverterContext();

		ObjectEntry objectEntry = _objectEntryManager.addObjectEntry(
			_getDTOConverterContext(), _objectDefinition,
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						"title", RandomTestUtil.randomString()
					).build();
				}
			},
			ObjectDefinitionConstants.SCOPE_COMPANY);

		Map<String, Object> properties = objectEntry.getProperties();

		String title = RandomTestUtil.randomString();

		properties.put("title", title);

		objectEntry = _objectEntryManager.updateObjectEntry(
			TestPropsValues.getCompanyId(), dtoConverterContext,
			objectEntry.getExternalReferenceCode(), _objectDefinition,
			objectEntry, ObjectDefinitionConstants.SCOPE_COMPANY);

		Assert.assertEquals(
			title, MapUtil.getString(objectEntry.getProperties(), "title"));

		_objectEntryManager.deleteObjectEntry(
			TestPropsValues.getCompanyId(), _getDTOConverterContext(),
			objectEntry.getExternalReferenceCode(), _objectDefinition,
			ObjectDefinitionConstants.SCOPE_COMPANY);
	}

	@Test
	public void testGetObjectEntry() throws Exception {
		DTOConverterContext dtoConverterContext = _getDTOConverterContext();

		String title = RandomTestUtil.randomString();

		ObjectEntry objectEntry = _objectEntryManager.addObjectEntry(
			_getDTOConverterContext(), _objectDefinition,
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						"title", title
					).build();
				}
			},
			ObjectDefinitionConstants.SCOPE_COMPANY);

		objectEntry = _objectEntryManager.getObjectEntry(
			TestPropsValues.getCompanyId(), dtoConverterContext,
			objectEntry.getExternalReferenceCode(), _objectDefinition,
			ObjectDefinitionConstants.SCOPE_COMPANY);

		Assert.assertEquals(
			title, MapUtil.getString(objectEntry.getProperties(), "title"));

		_objectEntryManager.deleteObjectEntry(
			TestPropsValues.getCompanyId(), _getDTOConverterContext(),
			objectEntry.getExternalReferenceCode(), _objectDefinition,
			ObjectDefinitionConstants.SCOPE_COMPANY);
	}

	private DTOConverterContext _getDTOConverterContext() throws Exception {
		return new DefaultDTOConverterContext(
			false, Collections.emptyMap(), _dtoConverterRegistry, null,
			LocaleUtil.getDefault(), null, _user);
	}

	@Inject
	private static ConfigurationProvider _configurationProvider;

	@Inject
	private DTOConverterRegistry _dtoConverterRegistry;

	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject(
		filter = "object.entry.manager.storage.type=" + ObjectDefinitionConstants.STORAGE_TYPE_SALESFORCE
	)
	private ObjectEntryManager _objectEntryManager;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	private User _user;

}