/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.storage.salesforce.internal.rest.manager.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.list.type.entry.util.ListTypeEntryUtil;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.field.builder.PicklistObjectFieldBuilder;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.test.util.BaseObjectEntryManagerImplTestCase;
import com.liferay.object.storage.salesforce.configuration.SalesforceConfiguration;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
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
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Arrays;
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
public class SalesforceObjectEntryManagerImplTest
	extends BaseObjectEntryManagerImplTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		adminUser = TestPropsValues.getUser();

		companyId = TestPropsValues.getCompanyId();

		_configurationProvider.saveCompanyConfiguration(
			SalesforceConfiguration.class, companyId,
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
			SalesforceConfiguration.class, companyId,
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
		super.setUp();

		listTypeDefinition =
			listTypeDefinitionLocalService.addListTypeDefinition(
				"Status", TestPropsValues.getUserId(),
				Collections.singletonMap(
					LocaleUtil.getDefault(), RandomTestUtil.randomString()),
				Arrays.asList(
					ListTypeEntryUtil.createListTypeEntry(
						"Completed", "completed",
						Collections.singletonMap(LocaleUtil.US, "Completed")),
					ListTypeEntryUtil.createListTypeEntry(
						"Not Completed", "notCompleted",
						Collections.singletonMap(
							LocaleUtil.US, "Not Completed")),
					ListTypeEntryUtil.createListTypeEntry(
						"Queued", "queued",
						Collections.singletonMap(LocaleUtil.US, "Queued")),
					ListTypeEntryUtil.createListTypeEntry(
						"Started", "started",
						Collections.singletonMap(LocaleUtil.US, "Started"))));

		_objectDefinition =
			objectDefinitionLocalService.addCustomObjectDefinition(
				adminUser.getUserId(), 0, false, false,
				LocalizedMapUtil.getLocalizedMap("Ticket"), "Ticket", null,
				null, LocalizedMapUtil.getLocalizedMap("Tickets"), true,
				ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_SALESFORCE,
				Collections.emptyList());

		ObjectFieldUtil.addCustomObjectField(
			new PicklistObjectFieldBuilder(
			).externalReferenceCode(
				"Status__c"
			).userId(
				adminUser.getUserId()
			).labelMap(
				LocalizedMapUtil.getLocalizedMap("Status")
			).listTypeDefinitionId(
				listTypeDefinition.getListTypeDefinitionId()
			).name(
				"customStatus"
			).objectDefinitionId(
				_objectDefinition.getObjectDefinitionId()
			).build());

		ObjectField objectField = ObjectFieldUtil.addCustomObjectField(
			new TextObjectFieldBuilder(
			).externalReferenceCode(
				"Title__c"
			).userId(
				adminUser.getUserId()
			).labelMap(
				LocalizedMapUtil.getLocalizedMap("Title")
			).name(
				"title"
			).objectDefinitionId(
				_objectDefinition.getObjectDefinitionId()
			).build());

		_objectDefinition.setTitleObjectFieldId(objectField.getObjectFieldId());

		_objectDefinition.setExternalReferenceCode("Ticket__c");

		_objectDefinition = objectDefinitionLocalService.updateObjectDefinition(
			_objectDefinition);

		_objectDefinition =
			objectDefinitionLocalService.publishCustomObjectDefinition(
				adminUser.getUserId(),
				_objectDefinition.getObjectDefinitionId());
	}

	@After
	public void tearDown() throws Exception {
		if (_objectDefinition != null) {
			objectDefinitionLocalService.deleteObjectDefinition(
				_objectDefinition.getObjectDefinitionId());
		}

		if (listTypeDefinition != null) {
			listTypeDefinitionLocalService.deleteListTypeDefinition(
				listTypeDefinition.getListTypeDefinitionId());
		}
	}

	@Test
	public void testAddObjectEntry() throws Exception {
		ObjectEntry objectEntry = _objectEntryManager.addObjectEntry(
			dtoConverterContext, _objectDefinition,
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
			TestPropsValues.getCompanyId(), dtoConverterContext,
			objectEntry.getExternalReferenceCode(), _objectDefinition,
			ObjectDefinitionConstants.SCOPE_COMPANY);
	}

	@Test
	public void testAddOrUpdateObjectEntry() throws Exception {
		ObjectEntry objectEntry = _objectEntryManager.addObjectEntry(
			dtoConverterContext, _objectDefinition,
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
			companyId, dtoConverterContext,
			objectEntry.getExternalReferenceCode(), _objectDefinition,
			objectEntry, ObjectDefinitionConstants.SCOPE_COMPANY);

		Assert.assertEquals(
			title, MapUtil.getString(objectEntry.getProperties(), "title"));

		_objectEntryManager.deleteObjectEntry(
			TestPropsValues.getCompanyId(), dtoConverterContext,
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
			dtoConverterContext, _objectDefinition,
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
			dtoConverterContext, _objectDefinition,
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
			dtoConverterContext, _objectDefinition,
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
			dtoConverterContext, _objectDefinition,
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

		testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				StringBundler.concat(
					buildEqualsExpressionFilterString("customStatus", "queued"),
					" and ", buildEqualsExpressionFilterString("title", title1))
			).build(),
			objectEntry1);

		testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				StringBundler.concat(
					_buildNotEqualsExpressionFilterString(
						"customStatus", "queued"),
					" and ",
					_buildNotEqualsExpressionFilterString("title", title1))
			).build(),
			objectEntry2, objectEntry3);

		testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				StringBundler.concat(
					buildEqualsExpressionFilterString("customStatus", "queued"),
					" or ", buildEqualsExpressionFilterString("title", title1))
			).build(),
			objectEntry1, objectEntry4);

		testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				StringBundler.concat(
					_buildNotEqualsExpressionFilterString(
						"customStatus", "queued"),
					" or ",
					_buildNotEqualsExpressionFilterString("title", title1))
			).build(),
			objectEntry2, objectEntry3, objectEntry4);

		// Equals/not equals expression

		testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				buildEqualsExpressionFilterString("customStatus", "queued")
			).build(),
			objectEntry1, objectEntry4);

		testGetObjectEntries(
			HashMapBuilder.put(
				"filter",
				_buildNotEqualsExpressionFilterString("customStatus", "queued")
			).build(),
			objectEntry2, objectEntry3);

		testGetObjectEntries(
			HashMapBuilder.put(
				"filter", buildEqualsExpressionFilterString("title", title1)
			).build(),
			objectEntry1);

		testGetObjectEntries(
			HashMapBuilder.put(
				"filter", _buildNotEqualsExpressionFilterString("title", title1)
			).build(),
			objectEntry2, objectEntry3, objectEntry4);

		for (ObjectEntry objectEntry :
				Arrays.asList(
					objectEntry1, objectEntry2, objectEntry3, objectEntry4)) {

			_objectEntryManager.deleteObjectEntry(
				companyId, dtoConverterContext,
				objectEntry.getExternalReferenceCode(), _objectDefinition,
				ObjectDefinitionConstants.SCOPE_COMPANY);
		}
	}

	@Test
	public void testGetObjectEntry() throws Exception {
		String title = RandomTestUtil.randomString();

		ObjectEntry objectEntry = _objectEntryManager.addObjectEntry(
			dtoConverterContext, _objectDefinition,
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						"title", title
					).build();
				}
			},
			ObjectDefinitionConstants.SCOPE_COMPANY);

		objectEntry = _objectEntryManager.getObjectEntry(
			companyId, dtoConverterContext,
			objectEntry.getExternalReferenceCode(), _objectDefinition,
			ObjectDefinitionConstants.SCOPE_COMPANY);

		Assert.assertEquals(
			title, MapUtil.getString(objectEntry.getProperties(), "title"));

		_objectEntryManager.deleteObjectEntry(
			TestPropsValues.getCompanyId(), dtoConverterContext,
			objectEntry.getExternalReferenceCode(), _objectDefinition,
			ObjectDefinitionConstants.SCOPE_COMPANY);
	}

	@Override
	protected Page<ObjectEntry> getObjectEntries(
			Map<String, String> context, Sort[] sorts)
		throws Exception {

		if (sorts == null) {
			sorts = new Sort[] {SortFactoryUtil.create("title", false)};
		}

		return _objectEntryManager.getObjectEntries(
			companyId, _objectDefinition, null, null, dtoConverterContext,
			context.get("filter"), Pagination.of(1, 3), context.get("search"),
			sorts);
	}

	private String _buildNotEqualsExpressionFilterString(
		String fieldName, Object value) {

		return StringBundler.concat(fieldName, " ne ", getValue(value));
	@Test
	public void testPartialUpdateObjectEntry() throws Exception {
		DTOConverterContext dtoConverterContext = _getDTOConverterContext();

		ObjectEntry objectEntry = _objectEntryManager.addObjectEntry(
			dtoConverterContext, _objectDefinition,
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						"title", RandomTestUtil.randomString()
					).build();
				}
			},
			ObjectDefinitionConstants.SCOPE_COMPANY);

		_objectEntryManager.partialUpdateObjectEntry(
			TestPropsValues.getCompanyId(), dtoConverterContext,
			objectEntry.getExternalReferenceCode(), _objectDefinition,
			new ObjectEntry() {
				{
					properties = HashMapBuilder.<String, Object>put(
						"title", "Able"
					).build();
				}
			},
			null);

		objectEntry = _objectEntryManager.getObjectEntry(
			TestPropsValues.getCompanyId(), dtoConverterContext,
			objectEntry.getExternalReferenceCode(), _objectDefinition,
			ObjectDefinitionConstants.SCOPE_COMPANY);

		Map<String, Object> properties = objectEntry.getProperties();

		Assert.assertEquals("Able", properties.get("title"));

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

	private ObjectDefinition _objectDefinition;

	@Inject(
		filter = "object.entry.manager.storage.type=" + ObjectDefinitionConstants.STORAGE_TYPE_SALESFORCE
	)
	private ObjectEntryManager _objectEntryManager;

}