/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.thread.local.BatchEngineThreadLocal;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectDefinitionSettingConstants;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.rest.filter.factory.FilterFactory;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectDefinitionSettingLocalService;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.FeatureFlagTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.cms.site.initializer.test.util.CMSDefaultValueTestUtil;
import com.liferay.site.cms.site.initializer.util.CMSDefaultValueUtil;

import java.io.Serializable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@FeatureFlag("LPD-112761")
@RunWith(Arquillian.class)
public class CMSDefaultValueObjectEntryLocalServiceWrapperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		CMSDefaultValueTestUtil.processBatchEngineUnits(
			CMSDefaultValueObjectEntryLocalServiceWrapperTest.class);

		_listTypeDefinition =
			_listTypeDefinitionLocalService.addListTypeDefinition(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				false);

		_listTypeEntryLocalService.addListTypeEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			_listTypeDefinition.getListTypeDefinitionId(), "a",
			Collections.singletonMap(LocaleUtil.US, "A"), false);

		_objectDefinition = ObjectDefinitionTestUtil.publishObjectDefinition(
			"A" + RandomTestUtil.randomString(),
			ListUtil.fromArray(
				ObjectFieldUtil.createObjectField(
					_listTypeDefinition.getListTypeDefinitionId(),
					ObjectFieldConstants.BUSINESS_TYPE_PICKLIST, null,
					ObjectFieldConstants.DB_TYPE_STRING, false, false, null,
					"category", "category", false, false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, "code"),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_INTEGER,
					ObjectFieldConstants.DB_TYPE_INTEGER, "count"),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_BOOLEAN,
					ObjectFieldConstants.DB_TYPE_BOOLEAN, "flag"),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_DATE,
					ObjectFieldConstants.DB_TYPE_DATE, "publishDate"),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, "text")),
			ObjectDefinitionConstants.SCOPE_DEPOT, TestPropsValues.getUserId());

		_objectDefinitionSettingLocalService.addObjectDefinitionSetting(
			_objectDefinition.getUserId(),
			_objectDefinition.getObjectDefinitionId(),
			ObjectDefinitionSettingConstants.NAME_ACCEPT_ALL_GROUPS,
			StringPool.TRUE);

		_depotEntry = _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			DepotConstants.TYPE_SPACE,
			ServiceContextTestUtil.getServiceContext());

		_group = _depotEntry.getGroup();

		ObjectEntryFolder rootObjectEntryFolder =
			_objectEntryFolderLocalService.
				getObjectEntryFolderByExternalReferenceCode(
					ObjectEntryFolderConstants.EXTERNAL_REFERENCE_CODE_CONTENTS,
					_group.getGroupId(), _group.getCompanyId());

		_parentObjectEntryFolder = _addObjectEntryFolder(
			_group, rootObjectEntryFolder.getObjectEntryFolderId());

		_objectEntryFolder = _addObjectEntryFolder(
			_group, _parentObjectEntryFolder.getObjectEntryFolderId());

		CMSDefaultValueUtil.addOrUpdateObjectEntry(
			TestPropsValues.getUserId(),
			JSONUtil.putAll(
				_createJSONObject(
					"category", "Picklist", JSONUtil.put("key", "a")
				).put(
					"listTypeDefinitionERC",
					_listTypeDefinition.getExternalReferenceCode()
				),
				_createJSONObject(
					"code", "Text", RandomTestUtil.randomString(300)),
				_createJSONObject(
					"content", "RichText",
					JSONUtil.put(
						"en_US", "Space content"
					).put(
						"it_IT", RandomTestUtil.randomString()
					)),
				_createJSONObject("count", "Integer", 1),
				_createJSONObject("flag", "Boolean", true),
				_createJSONObject("publishDate", "Date", "2026-12-01"),
				_createJSONObject("text", "Text", "Space text")),
			_depotEntry, _filterFactory);

		CMSDefaultValueUtil.addOrUpdateObjectEntry(
			TestPropsValues.getUserId(),
			JSONUtil.putAll(
				_createJSONObject("count", "Integer", 2),
				_createJSONObject("flag", "Boolean", null)),
			_filterFactory, _parentObjectEntryFolder);
	}

	@Test
	public void testAddObjectEntry() throws Exception {
		_assertValues(
			"a", StringPool.BLANK, 2, true, "2026-12-01", "Space text",
			_addObjectEntryValues(new HashMap<>()));
		_assertValues(
			"a", StringPool.BLANK, 5, true, "2026-12-01", StringPool.BLANK,
			_addObjectEntryValues(
				HashMapBuilder.<String, Serializable>put(
					"count", 5
				).put(
					"text", StringPool.BLANK
				).build()));

		Map<String, Serializable> values = new HashMap<>();

		values.put("count", null);

		_assertValues(
			"a", StringPool.BLANK, 2, true, "2026-12-01", "Space text",
			_addObjectEntryValues(values));

		try (SafeCloseable safeCloseable =
				BatchEngineThreadLocal.setBatchImportInProcessWithSafeCloseable(
					true)) {

			values = _addObjectEntryValues(new HashMap<>());
		}

		_assertValues(
			StringPool.BLANK, StringPool.BLANK, 0, false, null,
			StringPool.BLANK, values);

		try (SafeCloseable safeCloseable =
				FeatureFlagTestUtil.setFeatureFlagsWithSafeCloseable(
					false, "LPD-112761")) {

			values = _addObjectEntryValues(new HashMap<>());
		}

		_assertValues(
			StringPool.BLANK, StringPool.BLANK, 0, false, null,
			StringPool.BLANK, values);

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_CMS_BASIC_WEB_CONTENT", _group.getCompanyId());

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			_group.getGroupId(), TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId(),
			_objectEntryFolder.getObjectEntryFolderId(),
			LocaleUtil.toLanguageId(LocaleUtil.US),
			HashMapBuilder.<String, Serializable>put(
				"title_i18n",
				HashMapBuilder.put(
					"en_US", "Title"
				).build()
			).build(),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		values = objectEntry.getValues();

		Assert.assertEquals(
			"Space content", MapUtil.getString(values, "content"));
		Assert.assertEquals("Title", MapUtil.getString(values, "title"));
	}

	private ObjectEntryFolder _addObjectEntryFolder(
			Group group, long parentObjectEntryFolderId)
		throws Exception {

		return _objectEntryFolderLocalService.addObjectEntryFolder(
			RandomTestUtil.randomString(), group.getGroupId(),
			group.getCreatorUserId(), parentObjectEntryFolderId,
			StringPool.BLANK,
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext());
	}

	private Map<String, Serializable> _addObjectEntryValues(
			Map<String, Serializable> values)
		throws Exception {

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			_group.getGroupId(), TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(),
			_objectEntryFolder.getObjectEntryFolderId(),
			LocaleUtil.toLanguageId(LocaleUtil.US), values,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		return objectEntry.getValues();
	}

	private void _assertValues(
		String category, String code, int count, boolean flag,
		String publishDate, String text, Map<String, Serializable> values) {

		Assert.assertEquals(category, MapUtil.getString(values, "category"));
		Assert.assertEquals(code, MapUtil.getString(values, "code"));
		Assert.assertEquals(count, MapUtil.getInteger(values, "count"));
		Assert.assertEquals(flag, MapUtil.getBoolean(values, "flag"));

		Date date = (Date)values.get("publishDate");

		if (publishDate == null) {
			Assert.assertNull(date);
		}
		else {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			Assert.assertEquals(publishDate, dateFormat.format(date));
		}

		Assert.assertEquals(text, MapUtil.getString(values, "text"));
	}

	private JSONObject _createJSONObject(
		String name, String type, Object value) {

		return JSONUtil.put(
			"name", name
		).put(
			"type", type
		).put(
			"value", value
		);
	}

	private DepotEntry _depotEntry;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject(
		filter = "filter.factory.key=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT
	)
	private FilterFactory<Predicate> _filterFactory;

	private Group _group;
	private ListTypeDefinition _listTypeDefinition;

	@Inject
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Inject
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectDefinitionSettingLocalService
		_objectDefinitionSettingLocalService;

	private ObjectEntryFolder _objectEntryFolder;

	@Inject
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	private ObjectEntryFolder _parentObjectEntryFolder;

}