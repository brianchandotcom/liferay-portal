/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.manager.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.list.type.entry.util.ListTypeEntryUtil;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.builder.PicklistObjectFieldBuilder;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.storage.salesforce.configuration.SalesforceConfiguration;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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

		_listTypeDefinition =
			_listTypeDefinitionLocalService.addListTypeDefinition(
				"Status", TestPropsValues.getUserId(),
				Collections.singletonMap(
					LocaleUtil.getDefault(), RandomTestUtil.randomString()),
				Arrays.asList(
					ListTypeEntryUtil.createListTypeEntry(
						"Queued", "queued",
						Collections.singletonMap(LocaleUtil.US, "Queued")),
					ListTypeEntryUtil.createListTypeEntry(
						"Started", "started",
						Collections.singletonMap(LocaleUtil.US, "Started")),
					ListTypeEntryUtil.createListTypeEntry(
						"Not Completed", "notCompleted",
						Collections.singletonMap(
							LocaleUtil.US, "Not Completed")),
					ListTypeEntryUtil.createListTypeEntry(
						"Completed", "completed",
						Collections.singletonMap(LocaleUtil.US, "Completed"))));

		_objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				_user.getUserId(), 0, false, false,
				LocalizedMapUtil.getLocalizedMap("Ticket"), "Ticket", null,
				null, LocalizedMapUtil.getLocalizedMap("Tickets"), true,
				ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_SALESFORCE,
				Collections.emptyList());

		ObjectField objectField = ObjectFieldUtil.addCustomObjectField(
			new PicklistObjectFieldBuilder(
			).userId(
				_user.getUserId()
			).labelMap(
				LocalizedMapUtil.getLocalizedMap("Status")
			).listTypeDefinitionId(
				_listTypeDefinition.getListTypeDefinitionId()
			).name(
				"customStatus"
			).objectDefinitionId(
				_objectDefinition.getObjectDefinitionId()
			).build());

		_objectFieldLocalService.updateCustomObjectField(
			"Status__c", objectField.getObjectFieldId(),
			objectField.getListTypeDefinitionId(),
			objectField.getBusinessType(), objectField.getDBType(), false,
			false, null, objectField.getLabelMap(), false,
			objectField.getName(), ObjectFieldConstants.READ_ONLY_FALSE, null,
			false, false, objectField.getObjectFieldSettings());

		objectField = ObjectFieldUtil.addCustomObjectField(
			new TextObjectFieldBuilder(
			).userId(
				_user.getUserId()
			).labelMap(
				LocalizedMapUtil.getLocalizedMap("Title")
			).name(
				"title"
			).objectDefinitionId(
				_objectDefinition.getObjectDefinitionId()
			).build());

		_objectFieldLocalService.updateCustomObjectField(
			"Title__c", objectField.getObjectFieldId(), 0,
			objectField.getBusinessType(), objectField.getDBType(), false,
			false, null, objectField.getLabelMap(), false,
			objectField.getName(), ObjectFieldConstants.READ_ONLY_FALSE, null,
			false, false, objectField.getObjectFieldSettings());

		_objectDefinition.setTitleObjectFieldId(objectField.getObjectFieldId());

		_objectDefinition.setExternalReferenceCode("Ticket__c");

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

		if (_listTypeDefinition != null) {
			_listTypeDefinitionLocalService.deleteListTypeDefinition(
				_listTypeDefinition.getListTypeDefinitionId());
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
	public void testGetObjectEntries() throws Exception {
		String title1 = "a" + RandomTestUtil.randomString();
		String title2 = "b" + RandomTestUtil.randomString();
		String title3 = "c" + RandomTestUtil.randomString();
		String title4 = "d" + RandomTestUtil.randomString();

		ObjectEntry objectEntry1 = _objectEntryManager.addObjectEntry(
			_getDTOConverterContext(), _objectDefinition,
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						"customStatus", "queued"
					).put(
						"title", title1
					).build();
				}
			},
			ObjectDefinitionConstants.SCOPE_COMPANY);
		ObjectEntry objectEntry2 = _objectEntryManager.addObjectEntry(
			_getDTOConverterContext(), _objectDefinition,
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						"customStatus", "started"
					).put(
						"title", title2
					).build();
				}
			},
			ObjectDefinitionConstants.SCOPE_COMPANY);
		ObjectEntry objectEntry3 = _objectEntryManager.addObjectEntry(
			_getDTOConverterContext(), _objectDefinition,
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						"customStatus", "completed"
					).put(
						"title", title3
					).build();
				}
			},
			ObjectDefinitionConstants.SCOPE_COMPANY);
		ObjectEntry objectEntry4 = _objectEntryManager.addObjectEntry(
			_getDTOConverterContext(), _objectDefinition,
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						"customStatus", "queued"
					).put(
						"title", title4
					).build();
				}
			},
			ObjectDefinitionConstants.SCOPE_COMPANY);

		// And/or with equals/not equals expression

		_testGetObjectEntries(
			StringBundler.concat(
				_buildEqualsExpressionFilterString("customStatus", "queued"),
				" and ", _buildEqualsExpressionFilterString("title", title1)),
			objectEntry1);

		_testGetObjectEntries(
			StringBundler.concat(
				_buildNotEqualsExpressionFilterString("customStatus", "queued"),
				" and ",
				_buildNotEqualsExpressionFilterString("title", title1)),
			objectEntry2, objectEntry3);

		_testGetObjectEntries(
			StringBundler.concat(
				_buildEqualsExpressionFilterString("customStatus", "queued"),
				" or ", _buildEqualsExpressionFilterString("title", title1)),
			objectEntry1, objectEntry4);

		_testGetObjectEntries(
			StringBundler.concat(
				_buildNotEqualsExpressionFilterString("customStatus", "queued"),
				" or ", _buildNotEqualsExpressionFilterString("title", title1)),
			objectEntry2, objectEntry3, objectEntry4);

		// Equals/not equals expression

		_testGetObjectEntries(
			_buildEqualsExpressionFilterString("customStatus", "queued"),
			objectEntry1, objectEntry4);

		_testGetObjectEntries(
			_buildNotEqualsExpressionFilterString("customStatus", "queued"),
			objectEntry2, objectEntry3);

		_testGetObjectEntries(
			_buildEqualsExpressionFilterString("title", title1), objectEntry1);

		_testGetObjectEntries(
			_buildNotEqualsExpressionFilterString("title", title1),
			objectEntry2, objectEntry3, objectEntry4);

		for (ObjectEntry objectEntry :
				Arrays.asList(
					objectEntry1, objectEntry2, objectEntry3, objectEntry4)) {

			_objectEntryManager.deleteObjectEntry(
				TestPropsValues.getCompanyId(), _getDTOConverterContext(),
				objectEntry.getExternalReferenceCode(), _objectDefinition,
				ObjectDefinitionConstants.SCOPE_COMPANY);
		}
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

	private void _assertEquals(
		List<ObjectEntry> actualObjectEntries,
		List<ObjectEntry> expectedObjectEntries) {

		Assert.assertEquals(
			actualObjectEntries.toString(), expectedObjectEntries.size(),
			actualObjectEntries.size());

		for (int i = 0; i < expectedObjectEntries.size(); i++) {
			_assertEquals(
				actualObjectEntries.get(i), expectedObjectEntries.get(i));
		}
	}

	private void _assertEquals(
		ObjectEntry actualObjectEntry, ObjectEntry expectedObjectEntry) {

		Map<String, Object> actualObjectEntryProperties =
			actualObjectEntry.getProperties();

		Map<String, Object> expectedObjectEntryProperties =
			expectedObjectEntry.getProperties();

		for (Map.Entry<String, Object> expectedEntry :
				expectedObjectEntryProperties.entrySet()) {

			Assert.assertEquals(
				expectedEntry.getKey(), expectedEntry.getValue(),
				actualObjectEntryProperties.get(expectedEntry.getKey()));
		}
	}

	private String _buildEqualsExpressionFilterString(
		String fieldName, Object value) {

		return StringBundler.concat(fieldName, " eq ", _getValue(value));
	}

	private String _buildNotEqualsExpressionFilterString(
		String fieldName, Object value) {

		return StringBundler.concat(fieldName, " ne ", _getValue(value));
	}

	private DTOConverterContext _getDTOConverterContext() throws Exception {
		return new DefaultDTOConverterContext(
			false, Collections.emptyMap(), _dtoConverterRegistry, null,
			LocaleUtil.getDefault(), null, _user);
	}

	private Object _getValue(Object value) {
		if (value instanceof String) {
			return StringUtil.quote(String.valueOf(value));
		}

		return value;
	}

	private void _testGetObjectEntries(
			String filterExpression, ObjectEntry... expectedObjectEntries)
		throws Exception {

		Page<ObjectEntry> page = _objectEntryManager.getObjectEntries(
			TestPropsValues.getCompanyId(), _objectDefinition, null, null,
			_getDTOConverterContext(), filterExpression, Pagination.of(1, 3),
			null, new Sort[] {SortFactoryUtil.create("title", false)});

		_assertEquals(
			(List<ObjectEntry>)page.getItems(),
			ListUtil.fromArray(expectedObjectEntries));
	}

	@Inject
	private static ConfigurationProvider _configurationProvider;

	@Inject
	private DTOConverterRegistry _dtoConverterRegistry;

	private ListTypeDefinition _listTypeDefinition;

	@Inject
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

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