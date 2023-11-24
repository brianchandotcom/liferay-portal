/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.headless.admin.taxonomy.client.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.admin.taxonomy.client.resource.v1_0.TaxonomyCategoryResource;
import com.liferay.list.type.entry.util.ListTypeEntryUtil;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectActionExecutorConstants;
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.field.setting.builder.ObjectFieldSettingBuilder;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.dto.v1_0.Folder;
import com.liferay.object.rest.dto.v1_0.Link;
import com.liferay.object.rest.resource.v1_0.ObjectEntryResource;
import com.liferay.object.rest.test.util.ObjectDefinitionTestUtil;
import com.liferay.object.rest.test.util.ObjectEntryTestUtil;
import com.liferay.object.rest.test.util.ObjectFieldTestUtil;
import com.liferay.object.rest.test.util.ObjectRelationshipTestUtil;
import com.liferay.object.rest.test.util.UserAccountTestUtil;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.object.system.SystemObjectDefinitionManagerRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.ModelPermissionsFactory;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.odata.filter.InvalidFilterException;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.fields.NestedFieldsContext;
import com.liferay.portal.vulcan.fields.NestedFieldsContextThreadLocal;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

import org.hamcrest.CoreMatchers;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Luis Miguel Barcos
 */
@FeatureFlags("LPS-164801")
@RunWith(Arquillian.class)
public class ObjectEntryResourceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_assetVocabulary = AssetVocabularyLocalServiceUtil.addVocabulary(
			UserLocalServiceUtil.getGuestUserId(TestPropsValues.getCompanyId()),
			TestPropsValues.getGroupId(), RandomTestUtil.randomString(),
			new ServiceContext());

		Bundle bundle = FrameworkUtil.getBundle(ObjectEntryResourceTest.class);

		_bundleContext = bundle.getBundleContext();

		_serviceRegistrations = Arrays.asList(
			_bundleContext.registerService(
				ModelListener.class, _testDLFileEntryModelListener, null),
			_bundleContext.registerService(
				ModelListener.class, _testObjectEntryModelListener, null));

		TaxonomyCategoryResource.Builder builder =
			TaxonomyCategoryResource.builder();

		_taxonomyCategoryResource = builder.authentication(
			"test@liferay.com", "test"
		).locale(
			LocaleUtil.getDefault()
		).build();
	}

	@AfterClass
	public static void tearDownClass() {
		_serviceRegistrations.forEach(ServiceRegistration::unregister);
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_listTypeDefinition =
			_listTypeDefinitionLocalService.addListTypeDefinition(
				null, TestPropsValues.getUserId(),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()),
				false, Collections.emptyList());

		_listTypeEntryLocalService.addListTypeEntry(
			null, TestPropsValues.getUserId(),
			_listTypeDefinition.getListTypeDefinitionId(), _LIST_TYPE_ENTRY_KEY,
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()));

		_objectDefinition1 = ObjectDefinitionTestUtil.publishObjectDefinition(
			Arrays.asList(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_1, false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT,
					ObjectFieldConstants.DB_TYPE_LONG, true, false, null,
					_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE,
					_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE,
					Arrays.asList(
						new ObjectFieldSettingBuilder(
						).name(
							ObjectFieldSettingConstants.
								NAME_ACCEPTED_FILE_EXTENSIONS
						).value(
							"txt"
						).build(),
						new ObjectFieldSettingBuilder(
						).name(
							ObjectFieldSettingConstants.NAME_FILE_SOURCE
						).value(
							ObjectFieldSettingConstants.VALUE_DOCS_AND_MEDIA
						).build(),
						new ObjectFieldSettingBuilder(
						).name(
							ObjectFieldSettingConstants.NAME_MAX_FILE_SIZE
						).value(
							String.valueOf(_MAX_FILE_SIZE_VALUE)
						).build()),
					false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT,
					ObjectFieldConstants.DB_TYPE_LONG, true, false, null,
					_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE,
					_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE,
					Arrays.asList(
						new ObjectFieldSettingBuilder(
						).name(
							ObjectFieldSettingConstants.
								NAME_ACCEPTED_FILE_EXTENSIONS
						).value(
							"txt"
						).build(),
						new ObjectFieldSettingBuilder(
						).name(
							ObjectFieldSettingConstants.NAME_FILE_SOURCE
						).value(
							ObjectFieldSettingConstants.VALUE_USER_COMPUTER
						).build(),
						new ObjectFieldSettingBuilder(
						).name(
							ObjectFieldSettingConstants.NAME_MAX_FILE_SIZE
						).value(
							String.valueOf(_MAX_FILE_SIZE_VALUE)
						).build()),
					false),
				ObjectFieldUtil.createObjectField(
					_listTypeDefinition.getListTypeDefinitionId(),
					ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST,
					null, ObjectFieldConstants.DB_TYPE_STRING, true, false,
					null, RandomTestUtil.randomString(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST, false, false)));

		_objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition1, _OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1);

		_objectDefinition2 = ObjectDefinitionTestUtil.publishObjectDefinition(
			Arrays.asList(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_2, false),
				ObjectFieldUtil.createObjectField(
					_listTypeDefinition.getListTypeDefinitionId(),
					ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST,
					null, ObjectFieldConstants.DB_TYPE_STRING, true, false,
					null, RandomTestUtil.randomString(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST, false, false)));

		_objectEntry2 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition2, _OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2);

		_objectDefinition3 = ObjectDefinitionTestUtil.publishObjectDefinition(
			Arrays.asList(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_3, false),
				ObjectFieldUtil.createObjectField(
					_listTypeDefinition.getListTypeDefinitionId(),
					ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST,
					null, ObjectFieldConstants.DB_TYPE_STRING, true, false,
					null, RandomTestUtil.randomString(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST, false, false)));

		_objectEntry3 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition3, _OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3);

		_objectDefinition4 = ObjectDefinitionTestUtil.publishObjectDefinition(
			Arrays.asList(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_4, false),
				ObjectFieldUtil.createObjectField(
					_listTypeDefinition.getListTypeDefinitionId(),
					ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST,
					null, ObjectFieldConstants.DB_TYPE_STRING, true, false,
					null, RandomTestUtil.randomString(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST, false, false)));

		_objectEntry4 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition4, _OBJECT_FIELD_NAME_4, _OBJECT_FIELD_VALUE_4);

		_siteScopedObjectDefinition1 =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
						RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_1,
						false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT,
						ObjectFieldConstants.DB_TYPE_LONG, true, false, null,
						_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE,
						_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE,
						Arrays.asList(
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.
									NAME_ACCEPTED_FILE_EXTENSIONS
							).value(
								"txt"
							).build(),
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.NAME_FILE_SOURCE
							).value(
								ObjectFieldSettingConstants.VALUE_DOCS_AND_MEDIA
							).build(),
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.NAME_MAX_FILE_SIZE
							).value(
								String.valueOf(_MAX_FILE_SIZE_VALUE)
							).build()),
						false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT,
						ObjectFieldConstants.DB_TYPE_LONG, true, false, null,
						_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE,
						_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE,
						Arrays.asList(
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.
									NAME_ACCEPTED_FILE_EXTENSIONS
							).value(
								"txt"
							).build(),
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.NAME_FILE_SOURCE
							).value(
								ObjectFieldSettingConstants.VALUE_USER_COMPUTER
							).build(),
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.NAME_MAX_FILE_SIZE
							).value(
								String.valueOf(_MAX_FILE_SIZE_VALUE)
							).build()),
						false)),
				ObjectDefinitionConstants.SCOPE_SITE);

		_siteScopedObjectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_siteScopedObjectDefinition1, _OBJECT_FIELD_NAME_1,
			_OBJECT_FIELD_VALUE_1);

		SystemObjectDefinitionManager systemObjectDefinitionManager =
			_systemObjectDefinitionManagerRegistry.
				getSystemObjectDefinitionManager("User");

		_userSystemObjectDefinition =
			_objectDefinitionLocalService.fetchSystemObjectDefinition(
				systemObjectDefinitionManager.getName());

		_userSystemObjectField = ObjectFieldTestUtil.addCustomObjectField(
			TestPropsValues.getUserId(),
			ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING, _userSystemObjectDefinition,
			_OBJECT_FIELD_NAME_2);

		_userAccountJSONObject = UserAccountTestUtil.addUserAccountJSONObject(
			systemObjectDefinitionManager,
			HashMapBuilder.<String, Serializable>put(
				_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)
			).build());
	}

	@After
	public void tearDown() throws Exception {
		if (_objectRelationship1 != null) {
			_objectRelationshipLocalService.deleteObjectRelationship(
				_objectRelationship1);
		}

		if (_objectRelationship2 != null) {
			_objectRelationshipLocalService.deleteObjectRelationship(
				_objectRelationship2);
		}

		if (_objectRelationship3 != null) {
			_objectRelationshipLocalService.deleteObjectRelationship(
				_objectRelationship3);
		}

		if (_objectRelationship4 != null) {
			_objectRelationshipLocalService.deleteObjectRelationship(
				_objectRelationship4);
		}

		if (_objectRelationship5 != null) {
			_objectRelationshipLocalService.deleteObjectRelationship(
				_objectRelationship5);
		}

		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinition1);
		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinition2);
		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinition3);
		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinition4);
		_objectDefinitionLocalService.deleteObjectDefinition(
			_siteScopedObjectDefinition1);

		_listTypeDefinitionLocalService.deleteListTypeDefinition(
			_listTypeDefinition);
	}

	@Test
	public void testFilterByComparisonOperatorsObjectEntriesByRelatesObjectEntriesFields()
		throws Exception {

		// Many to many relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s eq '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s ge '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s gt '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2 - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s le '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s lt '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2 + 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s ne '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2 - 1)),
			_objectDefinition1);

		// Many to many relationship, system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id eq '%s'", _objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id ge '%s'", _objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id gt '%s'", _objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId() - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id le '%s'", _objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id lt '%s'", _objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId() + 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id ne '%s'", _objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId() - 1)),
			_objectDefinition1);

		// Many to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s eq '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s ge '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s gt '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s le '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s lt '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 + 1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s ne '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition2);

		// Many to many relationship (other side), system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id eq '%s'", _objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId())),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id ge '%s'", _objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId())),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id gt '%s'", _objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id le '%s'", _objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId())),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id lt '%s'", _objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() + 1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id ne '%s'", _objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition2);

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship1);

		// One to many relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s eq '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s ge '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s gt '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2 - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s le '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s lt '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2 + 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s ne '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2 - 1)),
			_objectDefinition1);

		// One to many relationship, system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id eq '%s'", _objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id ge '%s'", _objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id gt '%s'", _objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId() - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id le '%s'", _objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id lt '%s'", _objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId() + 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id ne '%s'", _objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId() - 1)),
			_objectDefinition1);

		// One to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s eq '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s ge '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s gt '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s le '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s lt '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 + 1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s ne '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition2);

		// One to many relationship (other side), system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id eq '%s'", _objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId())),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id ge '%s'", _objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId())),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id gt '%s'", _objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id le '%s'", _objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId())),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id lt '%s'", _objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() + 1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id ne '%s'", _objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition2);
	}

	@Test
	public void testFilterByComparisonOperatorsObjectEntriesByRelatesObjectEntriesFieldsThroughMultipleRelationships()
		throws Exception {

		// Many to many relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition1, _objectDefinition2,
			_objectEntry1.getPrimaryKey(), _objectEntry2.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_objectRelationship2 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition2, _objectDefinition3,
			_objectEntry2.getPrimaryKey(), _objectEntry3.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s eq '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s ge '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s gt '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3 - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s le '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s lt '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3 + 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s ne '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3 - 1)),
			_objectDefinition1);

		// Many to many relationship, system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/id eq '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/id ge '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/id gt '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId() - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/id le '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/id lt '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId() + 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/id ne '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId() - 1)),
			_objectDefinition1);

		// Many to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s eq '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s ge '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s gt '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s le '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s lt '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 + 1)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s ne '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition3);

		// Many to many relationship (other side), system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/id eq '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId())),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/id ge '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId())),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/id gt '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/id le '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId())),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/id lt '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() + 1)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/id ne '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition3);

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship1);
		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship2);

		// One to many relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition1, _objectDefinition2,
			_objectEntry1.getPrimaryKey(), _objectEntry2.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_objectRelationship2 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition2, _objectDefinition3,
			_objectEntry2.getPrimaryKey(), _objectEntry3.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s eq '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s ge '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s gt '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3 - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s le '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s lt '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3 + 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s ne '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3 - 1)),
			_objectDefinition1);

		// One to many relationship, system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/id eq '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/id ge '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/id gt '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId() - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/id le '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/id lt '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId() + 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/id ne '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId() - 1)),
			_objectDefinition1);

		// One to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s eq '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s ge '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s gt '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s le '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s lt '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 + 1)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s ne '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition3);

		// One to many relationship (other side), system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/id eq '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId())),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/id ge '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId())),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/id gt '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/id le '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId())),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/id lt '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() + 1)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/id ne '%s'", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition3);
	}

	@Test
	public void testFilterByGroupingOperatorsObjectEntriesByRelatesObjectEntriesFields()
		throws Exception {

		// Many to many relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"(%s/%s le '%s') and (%s/%s gt '%s')",
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_2,
					_OBJECT_FIELD_VALUE_2, _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2 - 1)),
			_objectDefinition1);

		// Many to many relationship, system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"(%s/id le '%s') and (%s/id gt '%s')",
					_objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId(),
					_objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId() - 1)),
			_objectDefinition1);

		// Many to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"(%s/%s le '%s') and (%s/%s gt '%s')",
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition2);

		// Many to many relationship (other side), system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"(%s/id le '%s') and (%s/id gt '%s')",
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition2);

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship1);

		// One to many relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"(%s/%s le '%s') and (%s/%s gt '%s')",
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_2,
					_OBJECT_FIELD_VALUE_2, _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2 - 1)),
			_objectDefinition1);

		// One to many relationship, system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"(%s/id le '%s') and (%s/id gt '%s')",
					_objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId(),
					_objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId() - 1)),
			_objectDefinition1);

		// One to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"(%s/%s le '%s') and (%s/%s gt '%s')",
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition2);

		// One to many relationship (other side), system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"(%s/id le '%s') and (%s/id gt '%s')",
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition2);
	}

	@Test
	public void testFilterByGroupingOperatorsObjectEntriesByRelatesObjectEntriesFieldsThroughMultipleRelationships()
		throws Exception {

		// Many to many relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition1, _objectDefinition2,
			_objectEntry1.getPrimaryKey(), _objectEntry2.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_objectRelationship2 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition2, _objectDefinition3,
			_objectEntry2.getPrimaryKey(), _objectEntry3.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"(%s/%s/%s le '%s') and (%s/%s/%s gt '%s')",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3, _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3 - 1)),
			_objectDefinition1);

		// Many to many relationship, system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"(%s/%s/id le '%s') and (%s/%s/id gt '%s')",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId(),
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId() - 1)),
			_objectDefinition1);

		// Many to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"(%s/%s/%s le '%s') and (%s/%s/%s gt '%s')",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition3);

		// Many to many relationship (other side), system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"(%s/%s/id le '%s') and (%s/%s/id gt '%s')",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId(),
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition3);

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship1);
		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship2);

		// One to many relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition1, _objectDefinition2,
			_objectEntry1.getPrimaryKey(), _objectEntry2.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_objectRelationship2 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition2, _objectDefinition3,
			_objectEntry2.getPrimaryKey(), _objectEntry3.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"(%s/%s/%s le '%s') and (%s/%s/%s gt '%s')",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3, _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3 - 1)),
			_objectDefinition1);

		// One to many relationship, system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"(%s/%s/id le '%s') and (%s/%s/id gt '%s')",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId(),
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId() - 1)),
			_objectDefinition1);

		// One to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"(%s/%s/%s le '%s') and (%s/%s/%s gt '%s')",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition3);

		// One to many relationship (other side), system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"(%s/%s/id le '%s') and (%s/%s/id gt '%s')",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId(),
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition3);
	}

	@Test
	public void testFilterByLambdaOperatorsObjectEntriesByRelatesObjectEntriesFields()
		throws Exception {

		_objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition1,
			HashMapBuilder.<String, Serializable>put(
				_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1
			).put(
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST, _LIST_TYPE_ENTRY_KEY
			).build(),
			_TAG_1);
		_objectEntry2 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition2,
			HashMapBuilder.<String, Serializable>put(
				_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2
			).put(
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST, _LIST_TYPE_ENTRY_KEY
			).build(),
			_TAG_1);

		// Many to many relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition1, _objectDefinition2,
			_objectEntry1.getPrimaryKey(), _objectEntry2.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/any(k:contains(k,'%s'))",
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/any(k:k eq '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/any(k:k in ('%s', '%s'))",
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY, RandomTestUtil.randomString())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/any(k:startswith(k,'%s'))",
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY)),
			_objectDefinition1);

		// Many to many relationship, system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/keywords/any(k:contains(k,'%s'))",
					_objectRelationship1.getName(), _TAG_1.substring(1))),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/keywords/any(k:k eq '%s')",
					_objectRelationship1.getName(), _TAG_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/keywords/any(k:k in ('%s', '%s'))",
					_objectRelationship1.getName(), _TAG_1,
					RandomTestUtil.randomString())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/keywords/any(k:startswith(k,'%s'))",
					_objectRelationship1.getName(), _TAG_1.substring(0, 2))),
			_objectDefinition1);

		// Many to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s/any(k:contains(k,'%s'))",
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s/any(k:k eq '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s/any(k:k in ('%s', '%s'))",
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY, RandomTestUtil.randomString())),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s/any(k:startswith(k,'%s'))",
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY)),
			_objectDefinition2);

		// Many to many relationship (other side), system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/keywords/any(k:contains(k,'%s'))",
					_objectRelationship1.getName(), _TAG_1.substring(1))),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/keywords/any(k:k eq '%s')",
					_objectRelationship1.getName(), _TAG_1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/keywords/any(k:k in ('%s', '%s'))",
					_objectRelationship1.getName(), _TAG_1,
					RandomTestUtil.randomString())),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/keywords/any(k:startswith(k,'%s'))",
					_objectRelationship1.getName(), _TAG_1.substring(0, 2))),
			_objectDefinition2);

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship1);

		// One to many relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition1, _objectDefinition2,
			_objectEntry1.getPrimaryKey(), _objectEntry2.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/any(k:contains(k,'%s'))",
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/any(k:k eq '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/any(k:k in ('%s', '%s'))",
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY, RandomTestUtil.randomString())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/any(k:startswith(k,'%s'))",
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY)),
			_objectDefinition1);

		// One to many relationship, system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/keywords/any(k:contains(k,'%s'))",
					_objectRelationship1.getName(), _TAG_1.substring(1))),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/keywords/any(k:k eq '%s')",
					_objectRelationship1.getName(), _TAG_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/keywords/any(k:k in ('%s', '%s'))",
					_objectRelationship1.getName(), _TAG_1,
					RandomTestUtil.randomString())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/keywords/any(k:startswith(k,'%s'))",
					_objectRelationship1.getName(), _TAG_1.substring(0, 2))),
			_objectDefinition1);

		// One to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s/any(k:contains(k,'%s'))",
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s/any(k:k eq '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s/any(k:k in ('%s', '%s'))",
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY, RandomTestUtil.randomString())),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s/any(k:startswith(k,'%s'))",
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY)),
			_objectDefinition2);

		// One to many relationship (other side), system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/keywords/any(k:contains(k,'%s'))",
					_objectRelationship1.getName(), _TAG_1.substring(1))),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/keywords/any(k:k eq '%s')",
					_objectRelationship1.getName(), _TAG_1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/keywords/any(k:k in ('%s', '%s'))",
					_objectRelationship1.getName(), _TAG_1,
					RandomTestUtil.randomString())),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/keywords/any(k:startswith(k,'%s'))",
					_objectRelationship1.getName(), _TAG_1.substring(0, 2))),
			_objectDefinition2);
	}

	@Test
	public void testFilterByLambdaOperatorsObjectEntriesByRelatesObjectEntriesFieldsThroughMultipleRelationships()
		throws Exception {

		_objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition1,
			HashMapBuilder.<String, Serializable>put(
				_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1
			).put(
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST, _LIST_TYPE_ENTRY_KEY
			).build(),
			_TAG_1);

		_objectEntry2 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition2,
			HashMapBuilder.<String, Serializable>put(
				_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2
			).put(
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST, _LIST_TYPE_ENTRY_KEY
			).build(),
			_TAG_1);

		_objectEntry3 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition3,
			HashMapBuilder.<String, Serializable>put(
				_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3
			).put(
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST, _LIST_TYPE_ENTRY_KEY
			).build(),
			_TAG_1);

		// Many to many relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition1, _objectDefinition2,
			_objectEntry1.getPrimaryKey(), _objectEntry2.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_objectRelationship2 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition2, _objectDefinition3,
			_objectEntry2.getPrimaryKey(), _objectEntry3.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s/any(k:k eq '%s')", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY)),
			_objectDefinition1);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s/any(k:contains(k,'%s'))",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY)),
			_objectDefinition1);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s/any(k:startswith(k,'%s'))",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY)),
			_objectDefinition1);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s/any(k:k in ('%s', '%s'))",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY, RandomTestUtil.randomString())),
			_objectDefinition1);

		// Many to many relationship, system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/keywords/any(k:k eq '%s')",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(), _TAG_1)),
			_objectDefinition1);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/keywords/any(k:contains(k,'%s'))",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(), _TAG_1.substring(1))),
			_objectDefinition1);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/keywords/any(k:startswith(k,'%s'))",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(), _TAG_1.substring(0, 2))),
			_objectDefinition1);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/keywords/any(k:k in ('%s', '%s'))",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(), _TAG_1,
					RandomTestUtil.randomString())),
			_objectDefinition1);

		// Many to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s/any(k:k eq '%s')", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY)),
			_objectDefinition3);

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s/any(k:contains(k,'%s'))",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY)),
			_objectDefinition3);

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s/any(k:startswith(k,'%s'))",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY)),
			_objectDefinition3);

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s/any(k:k in ('%s', '%s'))",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY, RandomTestUtil.randomString())),
			_objectDefinition3);

		// Many to many relationship (other side), system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/keywords/any(k:k eq '%s')",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(), _TAG_1)),
			_objectDefinition3);

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/keywords/any(k:contains(k,'%s'))",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(), _TAG_1.substring(1))),
			_objectDefinition3);

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/keywords/any(k:startswith(k,'%s'))",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(), _TAG_1.substring(0, 2))),
			_objectDefinition3);

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/keywords/any(k:k in ('%s', '%s'))",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(), _TAG_1,
					RandomTestUtil.randomString())),
			_objectDefinition3);

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship1);
		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship2);

		// One to many relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition1, _objectDefinition2,
			_objectEntry1.getPrimaryKey(), _objectEntry2.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_objectRelationship2 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition2, _objectDefinition3,
			_objectEntry2.getPrimaryKey(), _objectEntry3.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s/any(k:k eq '%s')", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY)),
			_objectDefinition1);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s/any(k:contains(k,'%s'))",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY)),
			_objectDefinition1);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s/any(k:startswith(k,'%s'))",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY)),
			_objectDefinition1);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s/any(k:k in ('%s', '%s'))",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY, RandomTestUtil.randomString())),
			_objectDefinition1);

		// One to many relationship, system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/keywords/any(k:k eq '%s')",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(), _TAG_1)),
			_objectDefinition1);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/keywords/any(k:contains(k,'%s'))",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(), _TAG_1.substring(1))),
			_objectDefinition1);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/keywords/any(k:startswith(k,'%s'))",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(), _TAG_1.substring(0, 2))),
			_objectDefinition1);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/keywords/any(k:k in ('%s', '%s'))",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(), _TAG_1,
					RandomTestUtil.randomString())),
			_objectDefinition1);

		// One to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s/any(k:k eq '%s')", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY)),
			_objectDefinition3);

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s/any(k:contains(k,'%s'))",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY)),
			_objectDefinition3);

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s/any(k:startswith(k,'%s'))",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY)),
			_objectDefinition3);

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s/any(k:k in ('%s', '%s'))",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY, RandomTestUtil.randomString())),
			_objectDefinition3);

		// One to many relationship (other side), system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/keywords/any(k:k eq '%s')",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(), _TAG_1)),
			_objectDefinition3);

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/keywords/any(k:contains(k,'%s'))",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(), _TAG_1.substring(1))),
			_objectDefinition3);

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/keywords/any(k:startswith(k,'%s'))",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(), _TAG_1.substring(0, 2))),
			_objectDefinition3);

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/keywords/any(k:k in ('%s', '%s'))",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(), _TAG_1,
					RandomTestUtil.randomString())),
			_objectDefinition3);
	}

	@Test
	public void testFilterByListOperatorsObjectEntriesByRelatesObjectEntriesFields()
		throws Exception {

		// Many to many relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s in ('%s', '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
					RandomTestUtil.randomInt())),
			_objectDefinition1);

		// Many to many relationship, system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id in ('%s', '%s')", _objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId(),
					RandomTestUtil.randomInt())),
			_objectDefinition1);

		// Many to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s in ('%s', '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
					RandomTestUtil.randomInt())),
			_objectDefinition2);

		// Many to many relationship (other side), system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id in ('%s', '%s')", _objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId(),
					RandomTestUtil.randomInt())),
			_objectDefinition2);

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship1);

		// One to many relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s in ('%s', '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
					RandomTestUtil.randomInt())),
			_objectDefinition1);

		// One to many relationship, system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id in ('%s', '%s')", _objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId(),
					RandomTestUtil.randomInt())),
			_objectDefinition1);

		// One to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s in ('%s', '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
					RandomTestUtil.randomInt())),
			_objectDefinition2);

		// One to many relationship (other side), system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id in ('%s', '%s')", _objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId(),
					RandomTestUtil.randomInt())),
			_objectDefinition2);
	}

	@Test
	public void testFilterByLogicalOperatorsObjectEntriesByRelatesObjectEntriesFields()
		throws Exception {

		// Many to many relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s le '%s' and %s/%s gt '%s'",
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_2,
					_OBJECT_FIELD_VALUE_2, _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2 - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s le '%s' or %s/%s gt '%s'",
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_2,
					_OBJECT_FIELD_VALUE_2, _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2 - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"not (%s/%s ge '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2 + 1)),
			_objectDefinition1);

		// Many to many relationship, system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id le '%s' and %s/id gt '%s'",
					_objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId(),
					_objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId() - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id le '%s' or %s/id gt '%s'",
					_objectRelationship1.getName(), _OBJECT_FIELD_VALUE_2,
					_objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId() - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"not (%s/id ge '%s')", _objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId() + 1)),
			_objectDefinition1);

		// Many to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s le '%s' and %s/%s gt '%s'",
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s le '%s' or %s/%s gt '%s'",
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"not (%s/%s ge '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 + 1)),
			_objectDefinition2);

		// Many to many relationship (other side), system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id le '%s' and %s/id gt '%s'",
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id le '%s' or %s/id gt '%s'",
					_objectRelationship1.getName(), _OBJECT_FIELD_VALUE_1,
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"not (%s/id ge '%s')", _objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() + 1)),
			_objectDefinition2);

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship1);

		// One to many relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s le '%s' and %s/%s gt '%s'",
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_2,
					_OBJECT_FIELD_VALUE_2, _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2 - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s le '%s' or %s/%s gt '%s'",
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_2,
					_OBJECT_FIELD_VALUE_2, _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2 - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"not (%s/%s ge '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2 + 1)),
			_objectDefinition1);

		// One to many relationship, system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id le '%s' and %s/id gt '%s'",
					_objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId(),
					_objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId() - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/id le '%s' or %s/id gt '%s'",
					_objectRelationship1.getName(), _OBJECT_FIELD_VALUE_2,
					_objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId() - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"not (%s/id ge '%s')", _objectRelationship1.getName(),
					_objectEntry2.getObjectEntryId() + 1)),
			_objectDefinition1);

		// One to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s le '%s' and %s/%s gt '%s'",
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s le '%s' or %s/%s gt '%s'",
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"not (%s/%s ge '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 + 1)),
			_objectDefinition2);

		// One to many relationship (other side), system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id le '%s' and %s/id gt '%s'",
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/id le '%s' or %s/id gt '%s'",
					_objectRelationship1.getName(), _OBJECT_FIELD_VALUE_1,
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"not (%s/id ge '%s')", _objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() + 1)),
			_objectDefinition2);
	}

	@Test
	public void testFilterByLogicalOperatorsObjectEntriesByRelatesObjectEntriesFieldsThroughMultipleRelationships()
		throws Exception {

		// Many to many relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition1, _objectDefinition2,
			_objectEntry1.getPrimaryKey(), _objectEntry2.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_objectRelationship2 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition2, _objectDefinition3,
			_objectEntry2.getPrimaryKey(), _objectEntry3.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s le '%s' and %s/%s/%s gt '%s'",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3, _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3 - 1)),
			_objectDefinition1);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s le '%s' or %s/%s/%s gt '%s'",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3, _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3 - 1)),
			_objectDefinition1);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"not (%s/%s/%s ge '%s')", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3 + 1)),
			_objectDefinition1);

		// Many to many relationship, system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/id le '%s' and %s/%s/id gt '%s'",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId(),
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId() - 1)),
			_objectDefinition1);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/id le '%s' or %s/%s/id gt '%s'",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId(),
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId() - 1)),
			_objectDefinition1);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"not (%s/%s/id ge '%s')", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId() + 1)),
			_objectDefinition1);

		// Many to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s le '%s' and %s/%s/%s gt '%s'",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition3);

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s le '%s' or %s/%s/%s gt '%s'",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition3);

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"not (%s/%s/%s ge '%s')", _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 + 1)),
			_objectDefinition3);

		// Many to many relationship (other side), system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/id le '%s' and %s/%s/id gt '%s'",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId(),
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition3);

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/id le '%s' or %s/%s/id gt '%s'",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId(),
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition3);

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"not (%s/%s/id ge '%s')", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() + 1)),
			_objectDefinition3);

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship1);
		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship2);

		// One to many relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition1, _objectDefinition2,
			_objectEntry1.getPrimaryKey(), _objectEntry2.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_objectRelationship2 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition2, _objectDefinition3,
			_objectEntry2.getPrimaryKey(), _objectEntry3.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s le '%s' and %s/%s/%s gt '%s'",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3, _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3 - 1)),
			_objectDefinition1);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s le '%s' or %s/%s/%s gt '%s'",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3, _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3 - 1)),
			_objectDefinition1);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"not (%s/%s/%s ge '%s')", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					_OBJECT_FIELD_VALUE_3 + 1)),
			_objectDefinition1);

		// One to many relationship, system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/id le '%s' and %s/%s/id gt '%s'",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId(),
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId() - 1)),
			_objectDefinition1);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/id le '%s' or %s/%s/id gt '%s'",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId(),
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId() - 1)),
			_objectDefinition1);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"not (%s/%s/id ge '%s')", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectEntry3.getObjectEntryId() + 1)),
			_objectDefinition1);

		// One to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s le '%s' and %s/%s/%s gt '%s'",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition3);

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s le '%s' or %s/%s/%s gt '%s'",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition3);

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"not (%s/%s/%s ge '%s')", _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 + 1)),
			_objectDefinition3);

		// One to many relationship (other side), system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/id le '%s' and %s/%s/id gt '%s'",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId(),
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition3);

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/id le '%s' or %s/%s/id gt '%s'",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId(),
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() - 1)),
			_objectDefinition3);

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"not (%s/%s/id ge '%s')", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_objectEntry1.getObjectEntryId() + 1)),
			_objectDefinition3);
	}

	@Test
	public void testFilterByStringOperatorsObjectEntriesByRelatesObjectEntriesFields()
		throws Exception {

		String objectFieldValue1 = String.valueOf(_OBJECT_FIELD_VALUE_1);

		_objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition1, _OBJECT_FIELD_NAME_1, objectFieldValue1);

		String objectFieldValue2 = String.valueOf(_OBJECT_FIELD_VALUE_2);

		_objectEntry2 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition2, _OBJECT_FIELD_NAME_2, objectFieldValue2);

		// Many to many relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			String.format(
				"contains(%s/%s,'%s')", _objectRelationship1.getName(),
				_OBJECT_FIELD_NAME_2, objectFieldValue2.substring(1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			String.format(
				"startswith(%s/%s,'%s')", _objectRelationship1.getName(),
				_OBJECT_FIELD_NAME_2, objectFieldValue2.substring(0, 2)),
			_objectDefinition1);

		// Many to many relationship, system object field

		String objectEntry2ExternalReferenceCode =
			_objectEntry2.getExternalReferenceCode();

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			String.format(
				"contains(%s/externalReferenceCode,'%s')",
				_objectRelationship1.getName(),
				objectEntry2ExternalReferenceCode.substring(1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			String.format(
				"startswith(%s/externalReferenceCode,'%s')",
				_objectRelationship1.getName(),
				objectEntry2ExternalReferenceCode.substring(0, 2)),
			_objectDefinition1);

		// Many to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			String.format(
				"contains(%s/%s,'%s')", _objectRelationship1.getName(),
				_OBJECT_FIELD_NAME_1, objectFieldValue1.substring(1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			String.format(
				"startswith(%s/%s,'%s')", _objectRelationship1.getName(),
				_OBJECT_FIELD_NAME_1, objectFieldValue1.substring(0, 2)),
			_objectDefinition2);

		// Many to many relationship (other side), system object field

		String objectEntry1ExternalReferenceCode =
			_objectEntry1.getExternalReferenceCode();

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			String.format(
				"contains(%s/externalReferenceCode,'%s')",
				_objectRelationship1.getName(),
				objectEntry1ExternalReferenceCode.substring(1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			String.format(
				"startswith(%s/externalReferenceCode,'%s')",
				_objectRelationship1.getName(),
				objectEntry1ExternalReferenceCode.substring(0, 2)),
			_objectDefinition2);

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship1);

		// One to many relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			String.format(
				"contains(%s/%s,'%s')", _objectRelationship1.getName(),
				_OBJECT_FIELD_NAME_2, objectFieldValue2.substring(1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			String.format(
				"startswith(%s/%s,'%s')", _objectRelationship1.getName(),
				_OBJECT_FIELD_NAME_2, objectFieldValue2.substring(0, 2)),
			_objectDefinition1);

		// One to many relationship, system object field

		objectEntry2ExternalReferenceCode =
			_objectEntry2.getExternalReferenceCode();

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			String.format(
				"contains(%s/externalReferenceCode,'%s')",
				_objectRelationship1.getName(),
				objectEntry2ExternalReferenceCode.substring(1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			String.format(
				"startswith(%s/externalReferenceCode,'%s')",
				_objectRelationship1.getName(),
				objectEntry2ExternalReferenceCode.substring(0, 2)),
			_objectDefinition1);

		// One to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			String.format(
				"contains(%s/%s,'%s')", _objectRelationship1.getName(),
				_OBJECT_FIELD_NAME_1, objectFieldValue1.substring(1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			String.format(
				"startswith(%s/%s,'%s')", _objectRelationship1.getName(),
				_OBJECT_FIELD_NAME_1, objectFieldValue1.substring(0, 2)),
			_objectDefinition2);

		// One to many relationship (other side), system object field

		objectEntry1ExternalReferenceCode =
			_objectEntry1.getExternalReferenceCode();

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			String.format(
				"contains(%s/externalReferenceCode,'%s')",
				_objectRelationship1.getName(),
				objectEntry1ExternalReferenceCode.substring(1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			String.format(
				"startswith(%s/externalReferenceCode,'%s')",
				_objectRelationship1.getName(),
				objectEntry1ExternalReferenceCode.substring(0, 2)),
			_objectDefinition2);
	}

	@Test
	public void testFilterByStringOperatorsObjectEntriesByRelatesObjectEntriesFieldsThroughMultipleRelationships()
		throws Exception {

		String objectFieldValue1 = String.valueOf(_OBJECT_FIELD_VALUE_1);
		String objectFieldValue2 = String.valueOf(_OBJECT_FIELD_VALUE_2);
		String objectFieldValue3 = String.valueOf(_OBJECT_FIELD_VALUE_3);

		_objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition1, _OBJECT_FIELD_NAME_1, objectFieldValue1);

		_objectEntry2 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition2, _OBJECT_FIELD_NAME_2, objectFieldValue2);

		_objectEntry3 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition3, _OBJECT_FIELD_NAME_3, objectFieldValue3);

		// Many to many relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition1, _objectDefinition2,
			_objectEntry1.getPrimaryKey(), _objectEntry2.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_objectRelationship2 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition2, _objectDefinition3,
			_objectEntry2.getPrimaryKey(), _objectEntry3.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, objectFieldValue1,
			_escape(
				String.format(
					"contains(%s/%s/%s,'%s')", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					objectFieldValue3.substring(1))),
			_objectDefinition1);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, objectFieldValue1,
			_escape(
				String.format(
					"startswith(%s/%s/%s,'%s')", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					objectFieldValue3.substring(0, 2))),
			_objectDefinition1);

		// Many to many relationship, system object field

		String objectEntry3ExternalReferenceCode =
			_objectEntry3.getExternalReferenceCode();

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, objectFieldValue1,
			_escape(
				String.format(
					"contains(%s/%s/externalReferenceCode,'%s')",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					objectEntry3ExternalReferenceCode.substring(1))),
			_objectDefinition1);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, objectFieldValue1,
			_escape(
				String.format(
					"startswith(%s/%s/externalReferenceCode,'%s')",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					objectEntry3ExternalReferenceCode.substring(0, 2))),
			_objectDefinition1);

		// Many to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, objectFieldValue3,
			_escape(
				String.format(
					"contains(%s/%s/%s,'%s')", _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					objectFieldValue1.substring(1))),
			_objectDefinition3);

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, objectFieldValue3,
			_escape(
				String.format(
					"startswith(%s/%s/%s,'%s')", _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					objectFieldValue1.substring(0, 2))),
			_objectDefinition3);

		// Many to many relationship (other side), system object field

		String objectEntry1ExternalReferenceCode =
			_objectEntry1.getExternalReferenceCode();

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, objectFieldValue3,
			_escape(
				String.format(
					"contains(%s/%s/externalReferenceCode,'%s')",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					objectEntry1ExternalReferenceCode.substring(1))),
			_objectDefinition3);

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, objectFieldValue3,
			_escape(
				String.format(
					"startswith(%s/%s/externalReferenceCode,'%s')",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					objectEntry1ExternalReferenceCode.substring(0, 2))),
			_objectDefinition3);

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship1);
		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship2);

		// One to many relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition1, _objectDefinition2,
			_objectEntry1.getPrimaryKey(), _objectEntry2.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_objectRelationship2 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition2, _objectDefinition3,
			_objectEntry2.getPrimaryKey(), _objectEntry3.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, objectFieldValue1,
			_escape(
				String.format(
					"contains(%s/%s/%s,'%s')", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					objectFieldValue3.substring(1))),
			_objectDefinition1);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, objectFieldValue1,
			_escape(
				String.format(
					"startswith(%s/%s/%s,'%s')", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_3,
					objectFieldValue3.substring(0, 2))),
			_objectDefinition1);

		// One to many relationship, system object field

		objectEntry3ExternalReferenceCode =
			_objectEntry3.getExternalReferenceCode();

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, objectFieldValue1,
			_escape(
				String.format(
					"contains(%s/%s/externalReferenceCode,'%s')",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					objectEntry3ExternalReferenceCode.substring(1))),
			_objectDefinition1);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, objectFieldValue1,
			_escape(
				String.format(
					"startswith(%s/%s/externalReferenceCode,'%s')",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					objectEntry3ExternalReferenceCode.substring(0, 2))),
			_objectDefinition1);

		// One to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, objectFieldValue3,
			_escape(
				String.format(
					"contains(%s/%s/%s,'%s')", _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					objectFieldValue1.substring(1))),
			_objectDefinition3);

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, objectFieldValue3,
			_escape(
				String.format(
					"startswith(%s/%s/%s,'%s')", _objectRelationship2.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					objectFieldValue1.substring(0, 2))),
			_objectDefinition3);

		// One to many relationship (other side), system object field

		objectEntry1ExternalReferenceCode =
			_objectEntry1.getExternalReferenceCode();

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, objectFieldValue3,
			_escape(
				String.format(
					"contains(%s/%s/externalReferenceCode,'%s')",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					objectEntry1ExternalReferenceCode.substring(1))),
			_objectDefinition3);

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, objectFieldValue3,
			_escape(
				String.format(
					"startswith(%s/%s/externalReferenceCode,'%s')",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					objectEntry1ExternalReferenceCode.substring(0, 2))),
			_objectDefinition3);
	}

	@Test
	public void testFilterByUnknownField() throws Exception {
		String filterString = "unknownField eq 'value'";

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			_objectDefinition1.getRESTContextPath() + "?filter=" +
				_escape(filterString),
			Http.Method.GET);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"status", "BAD_REQUEST"
			).put(
				"title",
				"A property used in the filter criteria is not supported: " +
					filterString
			).put(
				"type", InvalidFilterException.class.getSimpleName()
			).toString(),
			jsonObject.toString(), JSONCompareMode.STRICT);
	}

	@Test
	public void testFilterObjectEntriesByRelatesSystemObjectEntriesFields()
		throws Exception {

		// Many to many relationship

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition1, _userSystemObjectDefinition,
			_objectEntry1.getPrimaryKey(), _userAccountJSONObject.getLong("id"),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_testFilterObjectEntriesByRelatesSystemObjectEntriesFields(
			_escape(
				String.format(
					"%s/%s eq '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2)),
			_objectDefinition1);

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship1);

		// One to many relationship

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition1, _userSystemObjectDefinition,
			_objectEntry1.getPrimaryKey(), _userAccountJSONObject.getLong("id"),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_testFilterObjectEntriesByRelatesSystemObjectEntriesFields(
			_escape(
				String.format(
					"%s/%s eq '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2)),
			_objectDefinition1);
	}

	@Test
	public void testGetNestedFieldDetailsInRelationshipsWithCustomObjectDefinition()
		throws Exception {

		// Many to many relationship

		_objectRelationship2 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition1, _objectDefinition2,
			_objectEntry1.getPrimaryKey(), _objectEntry2.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_testGetNestedFieldDetailsInRelationships(
			_objectRelationship2.getName(), null,
			_objectRelationship2.getName(), _objectDefinition1,
			new String[][] {
				{_OBJECT_FIELD_NAME_1, String.valueOf(_OBJECT_FIELD_VALUE_1)},
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)}
			},
			Type.MANY_TO_MANY);

		_testGetNestedFieldDetailsInRelationships(
			_objectRelationship2.getName(), 3, _objectRelationship2.getName(),
			_objectDefinition1,
			new String[][] {
				{_OBJECT_FIELD_NAME_1, String.valueOf(_OBJECT_FIELD_VALUE_1)},
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)},
				{_OBJECT_FIELD_NAME_1, String.valueOf(_OBJECT_FIELD_VALUE_1)},
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)}
			},
			Type.MANY_TO_MANY);

		_testGetNestedFieldDetailsInRelationships(
			_objectRelationship2.getName(), 5, _objectRelationship2.getName(),
			_objectDefinition1,
			new String[][] {
				{_OBJECT_FIELD_NAME_1, String.valueOf(_OBJECT_FIELD_VALUE_1)},
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)},
				{_OBJECT_FIELD_NAME_1, String.valueOf(_OBJECT_FIELD_VALUE_1)},
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)},
				{_OBJECT_FIELD_NAME_1, String.valueOf(_OBJECT_FIELD_VALUE_1)},
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)}
			},
			Type.MANY_TO_MANY);

		_testGetNestedFieldDetailsInRelationships(
			_objectRelationship2.getName(), 6, _objectRelationship2.getName(),
			_objectDefinition1,
			new String[][] {
				{_OBJECT_FIELD_NAME_1, String.valueOf(_OBJECT_FIELD_VALUE_1)},
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)},
				{_OBJECT_FIELD_NAME_1, String.valueOf(_OBJECT_FIELD_VALUE_1)},
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)},
				{_OBJECT_FIELD_NAME_1, String.valueOf(_OBJECT_FIELD_VALUE_1)},
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)}
			},
			Type.MANY_TO_MANY);

		// Many to one relationship

		_objectRelationship3 = _addObjectRelationshipAndRelateObjectEntries(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		String relationshipFieldName = String.format(
			"r_%s_%s", _objectRelationship3.getName(),
			_objectDefinition1.getPKObjectFieldName());

		String relationshipFieldNameNestedFieldName = StringUtil.removeLast(
			relationshipFieldName, "Id");

		_testGetNestedFieldDetailsInRelationships(
			relationshipFieldNameNestedFieldName, null, relationshipFieldName,
			_objectDefinition2,
			new String[][] {
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)},
				{_OBJECT_FIELD_NAME_1, String.valueOf(_OBJECT_FIELD_VALUE_1)}
			},
			Type.MANY_TO_ONE);

		String relatedObjectDefinitionName = StringUtil.removeFirst(
			StringUtil.removeLast(
				_objectDefinition1.getPKObjectFieldName(), "Id"),
			"c_");

		_testGetNestedFieldDetailsInRelationships(
			relationshipFieldNameNestedFieldName, null,
			StringUtil.removeLast(relationshipFieldName, "Id"),
			_objectDefinition2,
			new String[][] {
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)},
				{_OBJECT_FIELD_NAME_1, String.valueOf(_OBJECT_FIELD_VALUE_1)}
			},
			Type.MANY_TO_ONE);

		_testGetNestedFieldDetailsInRelationships(
			relationshipFieldNameNestedFieldName, null,
			relatedObjectDefinitionName, _objectDefinition2,
			new String[][] {
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)},
				{_OBJECT_FIELD_NAME_1, String.valueOf(_OBJECT_FIELD_VALUE_1)}
			},
			Type.MANY_TO_ONE);

		_testGetNestedFieldDetailsInRelationships(
			relationshipFieldNameNestedFieldName, null,
			RandomTestUtil.randomString() + relatedObjectDefinitionName,
			_objectDefinition2,
			new String[][] {
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)},
				{_OBJECT_FIELD_NAME_1, String.valueOf(_OBJECT_FIELD_VALUE_1)}
			},
			Type.MANY_TO_ONE);

		_testGetNestedFieldDetailsInRelationships(
			relationshipFieldNameNestedFieldName, null,
			_objectRelationship3.getName(), _objectDefinition2,
			new String[][] {
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)},
				{_OBJECT_FIELD_NAME_1, String.valueOf(_OBJECT_FIELD_VALUE_1)}
			},
			Type.MANY_TO_ONE);

		_testGetNestedFieldDetailsInRelationships(
			_objectRelationship3.getName(), null,
			_objectRelationship3.getName(), _objectDefinition2,
			new String[][] {
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)},
				{_OBJECT_FIELD_NAME_1, String.valueOf(_OBJECT_FIELD_VALUE_1)}
			},
			Type.MANY_TO_ONE);

		// One to many relationship

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition1, _objectDefinition2,
			_objectEntry1.getPrimaryKey(), _objectEntry2.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_testGetNestedFieldDetailsInRelationships(
			_objectRelationship1.getName(), null,
			_objectRelationship1.getName(), _objectDefinition1,
			new String[][] {
				{_OBJECT_FIELD_NAME_1, String.valueOf(_OBJECT_FIELD_VALUE_1)},
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)}
			},
			Type.ONE_TO_MANY);

		_testGetNestedFieldDetailsInRelationships(
			_objectRelationship1.getName(), 3, _objectRelationship1.getName(),
			_objectDefinition1,
			new String[][] {
				{_OBJECT_FIELD_NAME_1, String.valueOf(_OBJECT_FIELD_VALUE_1)},
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)},
				{_OBJECT_FIELD_NAME_1, String.valueOf(_OBJECT_FIELD_VALUE_1)},
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)}
			},
			Type.ONE_TO_MANY);

		_testGetNestedFieldDetailsInRelationships(
			_objectRelationship1.getName(), 5, _objectRelationship1.getName(),
			_objectDefinition1,
			new String[][] {
				{_OBJECT_FIELD_NAME_1, String.valueOf(_OBJECT_FIELD_VALUE_1)},
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)},
				{_OBJECT_FIELD_NAME_1, String.valueOf(_OBJECT_FIELD_VALUE_1)},
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)},
				{_OBJECT_FIELD_NAME_1, String.valueOf(_OBJECT_FIELD_VALUE_1)},
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)}
			},
			Type.ONE_TO_MANY);

		_testGetNestedFieldDetailsInRelationships(
			_objectRelationship1.getName(), 6, _objectRelationship1.getName(),
			_objectDefinition1,
			new String[][] {
				{_OBJECT_FIELD_NAME_1, String.valueOf(_OBJECT_FIELD_VALUE_1)},
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)},
				{_OBJECT_FIELD_NAME_1, String.valueOf(_OBJECT_FIELD_VALUE_1)},
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)},
				{_OBJECT_FIELD_NAME_1, String.valueOf(_OBJECT_FIELD_VALUE_1)},
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)}
			},
			Type.ONE_TO_MANY);
	}

	@Test
	public void testGetNestedFieldDetailsInRelationshipsWithSystemObjectDefinition()
		throws Exception {

		// TODO LPS-17875 and LPS-185883

		// With fields, many to many and one to many relationships

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition1, _userSystemObjectDefinition,
			_objectEntry1.getPrimaryKey(), _userAccountJSONObject.getLong("id"),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);
		_objectRelationship2 = _addObjectRelationshipAndRelateObjectEntries(
			_userSystemObjectDefinition, _objectDefinition2,
			_userAccountJSONObject.getLong("id"), _objectEntry2.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);
		_objectRelationship3 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition2, _objectDefinition3,
			_objectEntry2.getPrimaryKey(), _objectEntry3.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);
		_objectRelationship4 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition3, _userSystemObjectDefinition,
			_objectEntry3.getPrimaryKey(), _userAccountJSONObject.getLong("id"),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);
		_objectRelationship5 = _addObjectRelationshipAndRelateObjectEntries(
			_userSystemObjectDefinition, _objectDefinition4,
			_userAccountJSONObject.getLong("id"), _objectEntry4.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		int nestedFieldDepth = 5;

		String endpoint = StringBundler.concat(
			_objectDefinition1.getRESTContextPath(), "?fields=",
			_objectRelationship1.getName(), ".", _objectRelationship2.getName(),
			".", _objectRelationship3.getName(), ".",
			_objectRelationship4.getName(), ".", _objectRelationship5.getName(),
			".", _OBJECT_FIELD_NAME_4, "&nestedFields=",
			_objectRelationship1.getName(), ",", _objectRelationship2.getName(),
			",", _objectRelationship3.getName(), ",",
			_objectRelationship4.getName(), ",", _objectRelationship5.getName(),
			"&nestedFieldsDepth=", nestedFieldDepth);

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			null, endpoint, Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());

		_assertNestedFieldsFieldsInRelationships(
			0, nestedFieldDepth, itemsJSONArray.getJSONObject(0),
			new String[] {
				_objectRelationship1.getName(), _objectRelationship2.getName(),
				_objectRelationship3.getName(), _objectRelationship4.getName(),
				_objectRelationship5.getName()
			},
			new String[][] {
				{"", "", Boolean.TRUE.toString()},
				{"", "", Boolean.TRUE.toString()},
				{"", "", Boolean.TRUE.toString()},
				{"", "", Boolean.TRUE.toString()},
				{"", "", Boolean.TRUE.toString()},
				{
					_OBJECT_FIELD_NAME_4, String.valueOf(_OBJECT_FIELD_VALUE_4),
					Boolean.TRUE.toString()
				}
			},
			new Type[] {
				Type.ONE_TO_MANY, Type.MANY_TO_MANY, Type.ONE_TO_MANY,
				Type.MANY_TO_MANY, Type.ONE_TO_MANY
			});

		// Without fields, many to many relationship

		_testGetNestedFieldDetailsInRelationships(
			_objectRelationship4.getName(), null,
			_objectRelationship4.getName(), _objectDefinition3,
			new String[][] {
				{_OBJECT_FIELD_NAME_3, String.valueOf(_OBJECT_FIELD_VALUE_3)},
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)}
			},
			Type.MANY_TO_MANY);

		_testGetNestedFieldDetailsInRelationships(
			_objectRelationship4.getName(), 3, _objectRelationship4.getName(),
			_objectDefinition3,
			new String[][] {
				{_OBJECT_FIELD_NAME_3, String.valueOf(_OBJECT_FIELD_VALUE_3)},
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)},
				{_OBJECT_FIELD_NAME_3, String.valueOf(_OBJECT_FIELD_VALUE_3)},
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)}
			},
			Type.MANY_TO_MANY);

		_testGetNestedFieldDetailsInRelationships(
			_objectRelationship4.getName(), 5, _objectRelationship4.getName(),
			_objectDefinition3,
			new String[][] {
				{_OBJECT_FIELD_NAME_3, String.valueOf(_OBJECT_FIELD_VALUE_3)},
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)},
				{_OBJECT_FIELD_NAME_3, String.valueOf(_OBJECT_FIELD_VALUE_3)},
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)},
				{_OBJECT_FIELD_NAME_3, String.valueOf(_OBJECT_FIELD_VALUE_3)},
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)}
			},
			Type.MANY_TO_MANY);

		_testGetNestedFieldDetailsInRelationships(
			_objectRelationship4.getName(), 6, _objectRelationship4.getName(),
			_objectDefinition3,
			new String[][] {
				{_OBJECT_FIELD_NAME_3, String.valueOf(_OBJECT_FIELD_VALUE_3)},
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)},
				{_OBJECT_FIELD_NAME_3, String.valueOf(_OBJECT_FIELD_VALUE_3)},
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)},
				{_OBJECT_FIELD_NAME_3, String.valueOf(_OBJECT_FIELD_VALUE_3)},
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)}
			},
			Type.MANY_TO_MANY);

		// Without fields, one to many relationship

		_testGetNestedFieldDetailsInRelationships(
			_objectRelationship1.getName(), null,
			_objectRelationship1.getName(), _objectDefinition1,
			new String[][] {
				{_OBJECT_FIELD_NAME_1, String.valueOf(_OBJECT_FIELD_VALUE_1)},
				{_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)}
			},
			Type.ONE_TO_MANY);
	}

	@Test
	public void testGetObjectEntryFilteredByKeywords() throws Exception {
		_postObjectEntryWithKeywords("tag1");
		_postObjectEntryWithKeywords("tag1", "tag2");
		_postObjectEntryWithKeywords("tag1", "tag2", "tag3");

		_assertFilteredObjectEntries(3, "keywords/any(k:k eq 'tag1')");
		_assertFilteredObjectEntries(2, "keywords/any(k:k eq 'tag2')");
		_assertFilteredObjectEntries(1, "keywords/any(k:k eq 'tag3')");
		_assertFilteredObjectEntries(0, "keywords/any(k:k eq '1234')");

		_assertFilteredObjectEntries(2, "keywords/any(k:k ne 'tag1')");
		_assertFilteredObjectEntries(3, "keywords/any(k:k ne 'tag2')");
		_assertFilteredObjectEntries(3, "keywords/any(k:k ne 'tag3')");

		_assertFilteredObjectEntries(2, "keywords/any(k:k gt 'tag1')");
		_assertFilteredObjectEntries(1, "keywords/any(k:k gt 'tag2')");
		_assertFilteredObjectEntries(0, "keywords/any(k:k gt 'tag3')");

		_assertFilteredObjectEntries(3, "keywords/any(k:k ge 'tag1')");
		_assertFilteredObjectEntries(2, "keywords/any(k:k ge 'tag2')");
		_assertFilteredObjectEntries(1, "keywords/any(k:k ge 'tag3')");

		_assertFilteredObjectEntries(0, "keywords/any(k:k lt 'tag1')");
		_assertFilteredObjectEntries(3, "keywords/any(k:k lt 'tag2')");
		_assertFilteredObjectEntries(3, "keywords/any(k:k lt 'tag3')");

		_assertFilteredObjectEntries(3, "keywords/any(k:k le 'tag1')");
		_assertFilteredObjectEntries(3, "keywords/any(k:k le 'tag2')");
		_assertFilteredObjectEntries(3, "keywords/any(k:k le 'tag3')");

		_assertFilteredObjectEntries(3, "keywords/any(k:startswith(k,'t'))");
		_assertFilteredObjectEntries(3, "keywords/any(k:startswith(k,'ta'))");
		_assertFilteredObjectEntries(3, "keywords/any(k:startswith(k,'tag'))");
		_assertFilteredObjectEntries(3, "keywords/any(k:startswith(k,'tag1'))");
		_assertFilteredObjectEntries(2, "keywords/any(k:startswith(k,'tag2'))");
		_assertFilteredObjectEntries(1, "keywords/any(k:startswith(k,'tag3'))");
		_assertFilteredObjectEntries(0, "keywords/any(k:startswith(k,'1234'))");

		_assertFilteredObjectEntries(3, "keywords/any(k:contains(k,'tag'))");
		_assertFilteredObjectEntries(3, "keywords/any(k:contains(k,'ag1'))");
		_assertFilteredObjectEntries(2, "keywords/any(k:contains(k,'ag2'))");
		_assertFilteredObjectEntries(1, "keywords/any(k:contains(k,'ag3'))");
		_assertFilteredObjectEntries(0, "keywords/any(k:contains(k,'1234'))");

		_assertFilteredObjectEntries(3, "keywords/any(k:k in ('tag1','tag2'))");
		_assertFilteredObjectEntries(2, "keywords/any(k:k in ('tag2','tag3'))");
		_assertFilteredObjectEntries(0, "keywords/any(k:k in ('1234','5678'))");
	}

	@Test
	public void testGetObjectEntryFilteredByTaxonomyCategories()
		throws Exception {

		TaxonomyCategory taxonomyCategory1 = _addTaxonomyCategory();
		TaxonomyCategory taxonomyCategory2 = _addTaxonomyCategory();
		TaxonomyCategory taxonomyCategory3 = _addTaxonomyCategory();

		_postObjectEntryWithTaxonomyCategories();
		_postObjectEntryWithTaxonomyCategories(taxonomyCategory1);
		_postObjectEntryWithTaxonomyCategories(
			taxonomyCategory1, taxonomyCategory2);
		_postObjectEntryWithTaxonomyCategories(
			taxonomyCategory1, taxonomyCategory2, taxonomyCategory3);

		_assertFilteredObjectEntries(
			3,
			String.format(
				"taxonomyCategoryIds/any(k:k eq %s)",
				taxonomyCategory1.getId()));
		_assertFilteredObjectEntries(
			3,
			String.format(
				"taxonomyCategoryIds/any(k:k eq %s)",
				taxonomyCategory1.getId()));
		_assertFilteredObjectEntries(
			2,
			String.format(
				"taxonomyCategoryIds/any(k:k eq %s)",
				taxonomyCategory2.getId()));
		_assertFilteredObjectEntries(
			1,
			String.format(
				"taxonomyCategoryIds/any(k:k eq %s)",
				taxonomyCategory3.getId()));
		_assertFilteredObjectEntries(0, "taxonomyCategoryIds/any(k:k eq 1234)");

		_assertFilteredObjectEntries(
			2,
			String.format(
				"taxonomyCategoryIds/any(k:k ne %s)",
				taxonomyCategory1.getId()));
		_assertFilteredObjectEntries(
			3,
			String.format(
				"taxonomyCategoryIds/any(k:k ne %s)",
				taxonomyCategory2.getId()));
		_assertFilteredObjectEntries(
			3,
			String.format(
				"taxonomyCategoryIds/any(k:k ne %s)",
				taxonomyCategory3.getId()));

		_assertFilteredObjectEntries(
			2,
			String.format(
				"taxonomyCategoryIds/any(k:k gt %s)",
				taxonomyCategory1.getId()));
		_assertFilteredObjectEntries(
			1,
			String.format(
				"taxonomyCategoryIds/any(k:k gt %s)",
				taxonomyCategory2.getId()));
		_assertFilteredObjectEntries(
			0,
			String.format(
				"taxonomyCategoryIds/any(k:k gt %s)",
				taxonomyCategory3.getId()));

		_assertFilteredObjectEntries(
			3,
			String.format(
				"taxonomyCategoryIds/any(k:k ge %s)",
				taxonomyCategory1.getId()));
		_assertFilteredObjectEntries(
			2,
			String.format(
				"taxonomyCategoryIds/any(k:k ge %s)",
				taxonomyCategory2.getId()));
		_assertFilteredObjectEntries(
			1,
			String.format(
				"taxonomyCategoryIds/any(k:k ge %s)",
				taxonomyCategory3.getId()));

		_assertFilteredObjectEntries(
			0,
			String.format(
				"taxonomyCategoryIds/any(k:k lt %s)",
				taxonomyCategory1.getId()));
		_assertFilteredObjectEntries(
			3,
			String.format(
				"taxonomyCategoryIds/any(k:k lt %s)",
				taxonomyCategory2.getId()));
		_assertFilteredObjectEntries(
			3,
			String.format(
				"taxonomyCategoryIds/any(k:k lt %s)",
				taxonomyCategory3.getId()));

		_assertFilteredObjectEntries(
			3,
			String.format(
				"taxonomyCategoryIds/any(k:k le %s)",
				taxonomyCategory1.getId()));
		_assertFilteredObjectEntries(
			3,
			String.format(
				"taxonomyCategoryIds/any(k:k le %s)",
				taxonomyCategory2.getId()));
		_assertFilteredObjectEntries(
			3,
			String.format(
				"taxonomyCategoryIds/any(k:k le %s)",
				taxonomyCategory3.getId()));

		_assertFilteredObjectEntries(
			3,
			String.format(
				"taxonomyCategoryIds/any(k:k in (%s,%s))",
				taxonomyCategory1.getId(), taxonomyCategory2.getId()));
		_assertFilteredObjectEntries(
			2,
			String.format(
				"taxonomyCategoryIds/any(k:k in (%s,%s))",
				taxonomyCategory2.getId(), taxonomyCategory3.getId()));
		_assertFilteredObjectEntries(
			0, "taxonomyCategoryIds/any(k:k in (1234,5678))");
	}

	@Test
	public void testGetObjectEntryWithActions() throws Exception {
		ObjectAction objectAction = _objectActionLocalService.addObjectAction(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			_objectDefinition1.getObjectDefinitionId(), true, StringPool.BLANK,
			RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_GROOVY,
			ObjectActionTriggerConstants.KEY_STANDALONE,
			new UnicodeProperties(), false);

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, "value"
			).toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		JSONObject actionsJSONObject = jsonObject.getJSONObject("actions");

		JSONObject actionJSONObject = actionsJSONObject.getJSONObject(
			objectAction.getName());

		Assert.assertEquals(
			StringBundler.concat(
				"http://localhost:8080/o",
				_objectDefinition1.getRESTContextPath(),
				"/by-external-reference-code/",
				jsonObject.getString("externalReferenceCode"),
				"/object-actions/", objectAction.getName()),
			actionJSONObject.getString("href"));
		Assert.assertEquals("PUT", actionJSONObject.getString("method"));
	}

	@FeatureFlags("LPS-174455")
	@Test
	public void testGetObjectEntryWithAttachmentField() throws Exception {
		String content = RandomTestUtil.randomString();

		FileEntry fileEntry = _addTempFileEntry(
			content, _objectDefinition1, RandomTestUtil.randomString());

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, "value1"
			).put(
				_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE,
				fileEntry.getFileEntryId()
			).toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		JSONObject attachmentJSONObject = jsonObject.getJSONObject(
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE);

		Assert.assertNull(attachmentJSONObject.get("fileBase64"));

		content = RandomTestUtil.randomString();

		fileEntry = _addTempFileEntry(
			content, _objectDefinition1, RandomTestUtil.randomString());

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, "value2"
			).put(
				_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE,
				fileEntry.getFileEntryId()
			).toString(),
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), "?nestedFields=",
				_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE,
				".fileBase64"),
			Http.Method.POST);

		attachmentJSONObject = jsonObject.getJSONObject(
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE);

		Assert.assertEquals(
			Base64.encode(content.getBytes()),
			attachmentJSONObject.getString("fileBase64"));

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			_objectDefinition1.getRESTContextPath() +
				"/by-external-reference-code/" +
					jsonObject.getString("externalReferenceCode"),
			Http.Method.GET);

		attachmentJSONObject = jsonObject.getJSONObject(
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE);

		Assert.assertNull(attachmentJSONObject.get("fileBase64"));

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(),
				"/by-external-reference-code/",
				jsonObject.getString("externalReferenceCode"), "?nestedFields=",
				_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE,
				".fileBase64"),
			Http.Method.GET);

		attachmentJSONObject = jsonObject.getJSONObject(
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE);

		Assert.assertEquals(
			Base64.encode(content.getBytes()),
			attachmentJSONObject.getString("fileBase64"));
	}

	@Test
	public void testGetObjectEntryWithAuditEvents() throws Exception {
		ListTypeDefinition listTypeDefinition =
			_listTypeDefinitionLocalService.addListTypeDefinition(
				null, TestPropsValues.getUserId(),
				Collections.singletonMap(
					LocaleUtil.getDefault(), RandomTestUtil.randomString()),
				false,
				Arrays.asList(
					ListTypeEntryUtil.createListTypeEntry(
						"listTypeEntryKey1",
						Collections.singletonMap(
							LocaleUtil.US, "List Type Entry Key 1")),
					ListTypeEntryUtil.createListTypeEntry(
						"listTypeEntryKey2",
						Collections.singletonMap(
							LocaleUtil.US, "List Type Entry Key 2"))));

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				TestPropsValues.getUserId(), 0, false, false, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				true, ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_BOOLEAN,
						ObjectFieldConstants.DB_TYPE_BOOLEAN, true, false, null,
						"Author of Gospel", "authorOfGospel", false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
						"Email Address", "emailAddress", false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_DECIMAL,
						ObjectFieldConstants.DB_TYPE_DOUBLE, true, false, null,
						"Height", "height", false),
					ObjectFieldUtil.createObjectField(
						listTypeDefinition.getListTypeDefinitionId(),
						ObjectFieldConstants.BUSINESS_TYPE_PICKLIST, null,
						ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
						"List Type Entry Key", "listTypeEntryKey", false,
						false),
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT,
						ObjectFieldConstants.DB_TYPE_LONG, true, false, null,
						"Upload", "upload",
						Arrays.asList(
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.
									NAME_ACCEPTED_FILE_EXTENSIONS
							).value(
								"txt"
							).build(),
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.NAME_FILE_SOURCE
							).value(
								ObjectFieldSettingConstants.VALUE_USER_COMPUTER
							).build(),
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.NAME_MAX_FILE_SIZE
							).value(
								String.valueOf(_MAX_FILE_SIZE_VALUE)
							).build()),
						false)));

		objectDefinition.setEnableObjectEntryHistory(true);

		_objectDefinitionLocalService.updateObjectDefinition(objectDefinition);

		_objectDefinitionLocalService.publishCustomObjectDefinition(
			TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId());

		NestedFieldsContext originalNestedFieldsContext =
			NestedFieldsContextThreadLocal.getNestedFieldsContext();
		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();
		String originalName = PrincipalThreadLocal.getName();

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.portal.security.audit.router.configuration." +
						"PersistentAuditMessageProcessorConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"enabled", true
					).build())) {

			ObjectEntry serviceBuilderObjectEntry =
				_objectEntryLocalService.addObjectEntry(
					TestPropsValues.getUserId(), 0,
					objectDefinition.getObjectDefinitionId(),
					HashMapBuilder.<String, Serializable>put(
						"authorOfGospel", true
					).put(
						"emailAddress", "john@liferay.com"
					).put(
						"height", 110.1
					).put(
						"listTypeEntryKey", "listTypeEntryKey1"
					).put(
						"upload",
						() -> {
							FileEntry fileEntry = _addTempFileEntry(
								RandomTestUtil.randomString(), objectDefinition,
								"Old Testament");

							return fileEntry.getFileEntryId();
						}
					).build(),
					ServiceContextTestUtil.getServiceContext());

			long objectEntryId = serviceBuilderObjectEntry.getObjectEntryId();

			_objectEntryLocalService.updateObjectEntry(
				TestPropsValues.getUserId(), objectEntryId,
				HashMapBuilder.<String, Serializable>put(
					"authorOfGospel", false
				).put(
					"emailAddress", "peter@liferay.com"
				).put(
					"height", 120.2
				).put(
					"listTypeEntryKey", "listTypeEntryKey2"
				).put(
					"upload",
					() -> {
						FileEntry fileEntry = _addTempFileEntry(
							RandomTestUtil.randomString(), objectDefinition,
							"New Testament");

						return fileEntry.getFileEntryId();
					}
				).build(),
				ServiceContextTestUtil.getServiceContext());

			User user = UserTestUtil.addUser();

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));
			PrincipalThreadLocal.setName(user.getUserId());

			// Add permissions to get object entry with audit events

			_addModelResourcePermissions(
				new String[] {
					ActionKeys.VIEW, ObjectActionKeys.OBJECT_ENTRY_HISTORY
				},
				objectDefinition.getClassName(), objectEntryId,
				user.getUserId());

			// Get object entry with null context URI info

			ObjectEntryResource objectEntryResource = _getObjectEntryResource(
				objectDefinition, user);

			com.liferay.object.rest.dto.v1_0.ObjectEntry objectEntry =
				objectEntryResource.getObjectEntry(objectEntryId);

			Assert.assertNull(objectEntry.getAuditEvents());

			// Get object entry with no nested fields

			NestedFieldsContextThreadLocal.setNestedFieldsContext(null);

			objectEntry = objectEntryResource.getObjectEntry(objectEntryId);

			Assert.assertNull(objectEntry.getAuditEvents());

			// Get object entry with nested fields but without "auditEvents"

			NestedFieldsContextThreadLocal.setNestedFieldsContext(
				_getNestedFieldsContext(RandomTestUtil.randomString()));

			objectEntry = objectEntryResource.getObjectEntry(objectEntryId);

			Assert.assertNull(objectEntry.getAuditEvents());

			// Get object entry with "auditEvents" nested fields

			NestedFieldsContextThreadLocal.setNestedFieldsContext(
				_getNestedFieldsContext("auditEvents"));

			objectEntry = objectEntryResource.getObjectEntry(objectEntryId);

			JSONAssert.assertEquals(
				JSONUtil.putAll(
					JSONUtil.put(
						"auditFieldChanges",
						JSONUtil.putAll(
							JSONUtil.put(
								"name", "authorOfGospel"
							).put(
								"newValue", true
							),
							JSONUtil.put(
								"name", "emailAddress"
							).put(
								"newValue", "john@liferay.com"
							),
							JSONUtil.put(
								"name", "height"
							).put(
								"newValue", 110.1
							),
							JSONUtil.put(
								"name", "listTypeEntryKey"
							).put(
								"newValue",
								JSONUtil.put(
									"key", "listTypeEntryKey1"
								).put(
									"name", "List Type Entry Key 1"
								)
							),
							JSONUtil.put(
								"name", "upload"
							).put(
								"newValue",
								JSONUtil.put("title", "Old Testament")
							))
					).put(
						"eventType", "ADD"
					),
					JSONUtil.put(
						"auditFieldChanges",
						JSONUtil.putAll(
							JSONUtil.put(
								"name", "authorOfGospel"
							).put(
								"newValue", false
							).put(
								"oldValue", true
							),
							JSONUtil.put(
								"name", "emailAddress"
							).put(
								"newValue", "peter@liferay.com"
							).put(
								"oldValue", "john@liferay.com"
							),
							JSONUtil.put(
								"name", "height"
							).put(
								"newValue", 120.2
							).put(
								"oldValue", 110.1
							),
							JSONUtil.put(
								"name", "listTypeEntryKey"
							).put(
								"newValue",
								JSONUtil.put(
									"key", "listTypeEntryKey2"
								).put(
									"name", "List Type Entry Key 2"
								)
							).put(
								"oldValue",
								JSONUtil.put(
									"key", "listTypeEntryKey1"
								).put(
									"name", "List Type Entry Key 1"
								)
							),
							JSONUtil.put(
								"name", "upload"
							).put(
								"newValue",
								JSONUtil.put("title", "New Testament")
							).put(
								"oldValue",
								JSONUtil.put("title", "Old Testament")
							))
					).put(
						"eventType", "UPDATE"
					)
				).toString(),
				String.valueOf(
					JSONFactoryUtil.createJSONArray(
						JSONFactoryUtil.looseSerializeDeep(
							objectEntry.getAuditEvents()))),
				false);

			// Get object entry without object entry history permission

			_addModelResourcePermissions(
				new String[] {ActionKeys.VIEW}, objectDefinition.getClassName(),
				objectEntryId, user.getUserId());

			objectEntry = objectEntryResource.getObjectEntry(objectEntryId);

			Assert.assertNull(objectEntry.getAuditEvents());
		}
		finally {
			NestedFieldsContextThreadLocal.setNestedFieldsContext(
				originalNestedFieldsContext);
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
			PrincipalThreadLocal.setName(originalName);
		}

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);

		_listTypeDefinitionLocalService.deleteListTypeDefinition(
			listTypeDefinition);
	}

	@Test
	public void testGetObjectEntryWithKeywords() throws Exception {
		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, "value"
			).put(
				"keywords", JSONUtil.putAll("tag1", "tag2")
			).toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			_objectDefinition1.getRESTContextPath() + StringPool.SLASH +
				jsonObject.getString("id"),
			Http.Method.GET);

		JSONArray keywordsJSONArray = jsonObject.getJSONArray("keywords");

		Assert.assertEquals("tag1", keywordsJSONArray.get(0));
		Assert.assertEquals("tag2", keywordsJSONArray.get(1));

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"keywords", JSONUtil.putAll("tag1", "tag2", "tag3")
			).toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			_objectDefinition1.getRESTContextPath() + StringPool.SLASH +
				jsonObject.getString("id"),
			Http.Method.GET);

		keywordsJSONArray = jsonObject.getJSONArray("keywords");

		Assert.assertEquals("tag1", keywordsJSONArray.get(0));
		Assert.assertEquals("tag2", keywordsJSONArray.get(1));
		Assert.assertEquals("tag3", keywordsJSONArray.get(2));
	}

	@Test
	public void testGetObjectEntryWithTaxonomyCategories() throws Exception {
		TaxonomyCategory taxonomyCategory1 = _addTaxonomyCategory();
		TaxonomyCategory taxonomyCategory2 = _addTaxonomyCategory();

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, "value"
			).put(
				"taxonomyCategoryIds",
				JSONUtil.putAll(
					taxonomyCategory1.getId(), taxonomyCategory2.getId())
			).toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			_objectDefinition1.getRESTContextPath() + StringPool.SLASH +
				jsonObject.getString("id"),
			Http.Method.GET);

		Assert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"taxonomyCategoryId",
					Long.valueOf(taxonomyCategory1.getId())
				).put(
					"taxonomyCategoryName", taxonomyCategory1.getName()
				),
				JSONUtil.put(
					"taxonomyCategoryId",
					Long.valueOf(taxonomyCategory2.getId())
				).put(
					"taxonomyCategoryName", taxonomyCategory2.getName()
				)
			).toString(),
			jsonObject.getJSONArray(
				"taxonomyCategoryBriefs"
			).toString());
	}

	@Test
	public void testGetObjectEntryWithTaxonomyCategoriesAndEmbeddedTaxonomyCategory()
		throws Exception {

		TaxonomyCategory taxonomyCategory1 = _addTaxonomyCategory();
		TaxonomyCategory taxonomyCategory2 = _addTaxonomyCategory();

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, "value"
			).put(
				"taxonomyCategoryIds",
				JSONUtil.putAll(
					taxonomyCategory1.getId(), taxonomyCategory2.getId())
			).toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				jsonObject.getString("id"),
				"?nestedFields=embeddedTaxonomyCategory"),
			Http.Method.GET);

		Assert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"embeddedTaxonomyCategory",
					_toEmbeddedTaxonomyCategoryJSONObject(taxonomyCategory1)
				).put(
					"taxonomyCategoryId",
					Long.valueOf(taxonomyCategory1.getId())
				).put(
					"taxonomyCategoryName", taxonomyCategory1.getName()
				),
				JSONUtil.put(
					"embeddedTaxonomyCategory",
					_toEmbeddedTaxonomyCategoryJSONObject(taxonomyCategory2)
				).put(
					"taxonomyCategoryId",
					Long.valueOf(taxonomyCategory2.getId())
				).put(
					"taxonomyCategoryName", taxonomyCategory2.getName()
				)
			).toString(),
			jsonObject.getJSONArray(
				"taxonomyCategoryBriefs"
			).toString());
	}

	@Test
	public void testGetObjectRelationshipERCFieldNameInOneToManyRelationship()
		throws Exception {

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			null, _objectDefinition2.getRESTContextPath(), Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());

		JSONObject itemJSONObject = itemsJSONArray.getJSONObject(0);

		Assert.assertEquals(
			itemJSONObject.getString(_objectRelationship1.getName() + "ERC"),
			_objectEntry1.getExternalReferenceCode());
	}

	@Test
	public void testGetObjectRelationshipERCFieldNameInOneToManyRelationshipFromRelatedObjectEntry()
		throws Exception {

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), "?nestedFields=",
				_objectRelationship1.getName()),
			Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());

		JSONObject itemJSONObject = itemsJSONArray.getJSONObject(0);

		JSONArray relationshipJSONArray = itemJSONObject.getJSONArray(
			_objectRelationship1.getName());

		Assert.assertEquals(1, relationshipJSONArray.length());

		JSONObject relatedObjectEntryJSONObject =
			relationshipJSONArray.getJSONObject(0);

		Assert.assertEquals(
			relatedObjectEntryJSONObject.getString(
				_objectRelationship1.getName() + "ERC"),
			_objectEntry1.getExternalReferenceCode());
	}

	@Test
	public void testGetScopeScopeKeyObjectEntriesPage() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.vulcan.internal.jaxrs.exception.mapper." +
					"WebApplicationExceptionMapper",
				LoggerTestUtil.ERROR)) {

			JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
				null,
				_getEndpoint(
					RandomTestUtil.randomLong(), _siteScopedObjectDefinition1),
				Http.Method.GET);

			Assert.assertEquals("NOT_FOUND", jsonObject.getString("status"));
		}

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			_getEndpoint(
				TestPropsValues.getGroupId(), _siteScopedObjectDefinition1),
			Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());

		JSONObject itemJSONObject = itemsJSONArray.getJSONObject(0);

		Assert.assertEquals(
			itemJSONObject.getLong("id"),
			_siteScopedObjectEntry1.getObjectEntryId());
	}

	@Test
	public void testPatchObjectEntryWithKeywords() throws Exception {
		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, "value"
			).put(
				"keywords", JSONUtil.putAll("tag1", "tag2")
			).toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"keywords", JSONUtil.putAll("tag1", "tag2", "tag3")
			).toString(),
			_objectDefinition1.getRESTContextPath() + StringPool.SLASH +
				jsonObject.getString("id"),
			Http.Method.PATCH);

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			_objectDefinition1.getRESTContextPath() + StringPool.SLASH +
				jsonObject.getString("id"),
			Http.Method.GET);

		JSONArray keywordsJSONArray = jsonObject.getJSONArray("keywords");

		Assert.assertEquals("tag1", keywordsJSONArray.get(0));
		Assert.assertEquals("tag2", keywordsJSONArray.get(1));
		Assert.assertEquals("tag3", keywordsJSONArray.get(2));
	}

	@Test
	public void testPatchObjectEntryWithTaxonomyCategories() throws Exception {
		TaxonomyCategory taxonomyCategory1 = _addTaxonomyCategory();
		TaxonomyCategory taxonomyCategory2 = _addTaxonomyCategory();

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, "value"
			).put(
				"taxonomyCategoryIds",
				JSONUtil.putAll(
					taxonomyCategory1.getId(), taxonomyCategory2.getId())
			).toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		TaxonomyCategory taxonomyCategory3 = _addTaxonomyCategory();

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"taxonomyCategoryIds",
				JSONUtil.putAll(
					taxonomyCategory1.getId(), taxonomyCategory2.getId(),
					taxonomyCategory3.getId())
			).toString(),
			_objectDefinition1.getRESTContextPath() + StringPool.SLASH +
				jsonObject.getString("id"),
			Http.Method.PATCH);

		Assert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"taxonomyCategoryId",
					Long.valueOf(taxonomyCategory1.getId())
				).put(
					"taxonomyCategoryName", taxonomyCategory1.getName()
				),
				JSONUtil.put(
					"taxonomyCategoryId",
					Long.valueOf(taxonomyCategory2.getId())
				).put(
					"taxonomyCategoryName", taxonomyCategory2.getName()
				),
				JSONUtil.put(
					"taxonomyCategoryId",
					Long.valueOf(taxonomyCategory3.getId())
				).put(
					"taxonomyCategoryName", taxonomyCategory3.getName()
				)
			).toString(),
			jsonObject.getJSONArray(
				"taxonomyCategoryBriefs"
			).toString());
	}

	@FeatureFlags("LPS-174455")
	@Test
	public void testPatchPutCustomObjectEntryByExternalReferenceCodeWithAttachmentField()
		throws Exception {

		_testPatchPutCustomObjectEntryWithAttachmentField(
			Http.Method.PATCH, _objectDefinition1, true);
		_testPatchPutCustomObjectEntryWithAttachmentField(
			Http.Method.PUT, _objectDefinition1, true);
		_testPatchPutCustomObjectEntryWithAttachmentField(
			Http.Method.PATCH, _siteScopedObjectDefinition1, true);
		_testPatchPutCustomObjectEntryWithAttachmentField(
			Http.Method.PUT, _siteScopedObjectDefinition1, true);
	}

	@FeatureFlags("LPS-174455")
	@Test
	public void testPatchPutCustomObjectEntryWithAttachmentField()
		throws Exception {

		_testPatchPutCustomObjectEntryWithAttachmentField(
			Http.Method.PATCH, _objectDefinition1, false);
		_testPatchPutCustomObjectEntryWithAttachmentField(
			Http.Method.PUT, _objectDefinition1, false);
		_testPatchPutCustomObjectEntryWithAttachmentField(
			Http.Method.PATCH, _siteScopedObjectDefinition1, false);
		_testPatchPutCustomObjectEntryWithAttachmentField(
			Http.Method.PUT, _siteScopedObjectDefinition1, false);
	}

	@Test
	public void testPatchSiteScopedObject() throws Exception {
		String newObjectFieldValue = RandomTestUtil.randomString();

		JSONObject objectEntryJSONObject = JSONUtil.put(
			_OBJECT_FIELD_NAME_1, newObjectFieldValue);

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			objectEntryJSONObject.toString(),
			StringBundler.concat(
				_getEndpoint(
					TestPropsValues.getGroupId(), _siteScopedObjectDefinition1),
				"/by-external-reference-code/",
				_siteScopedObjectEntry1.getExternalReferenceCode()),
			Http.Method.PATCH);

		Assert.assertEquals(
			jsonObject.getString(_OBJECT_FIELD_NAME_1), newObjectFieldValue);
	}

	@FeatureFlags("LPS-174455")
	@Test
	public void testPostCustomObjectEntryWithAttachmentField()
		throws Exception {

		_testPostCustomObjectEntryWithAttachmentField(_objectDefinition1);
		_testPostCustomObjectEntryWithAttachmentField(
			_siteScopedObjectDefinition1);
	}

	@FeatureFlags("LPS-196724")
	@Test
	public void testPostCustomObjectEntryWithAutoIncrementField()
		throws Exception {

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				Collections.singletonList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_AUTO_INCREMENT,
						ObjectFieldConstants.DB_TYPE_STRING, "autoIncrement",
						Arrays.asList(
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.NAME_INITIAL_VALUE
							).value(
								"10"
							).build(),
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.NAME_SUFFIX
							).value(
								"-private"
							).build()))));

		String endpoint = _getEndpoint(
			TestPropsValues.getGroupId(), objectDefinition);

		HTTPTestUtil.invokeToJSONObject(
			JSONFactoryUtil.getNullJSON(), endpoint, Http.Method.POST);
		HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"autoIncrement", "100-private"
			).toString(),
			endpoint, Http.Method.POST);
		HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"autoIncrement", "90-private"
			).toString(),
			endpoint, Http.Method.POST);

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			null, endpoint, Http.Method.GET);

		_assertItem("0", jsonObject, "autoIncrement", "10-private");
		_assertItem("1", jsonObject, "autoIncrement", "100-private");
		_assertItem("2", jsonObject, "autoIncrement", "90-private");

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				endpoint, "?sort=", URLCodec.encodeURL("autoIncrement:asc")),
			Http.Method.GET);

		_assertItem("0", jsonObject, "autoIncrement", "10-private");
		_assertItem("1", jsonObject, "autoIncrement", "90-private");
		_assertItem("2", jsonObject, "autoIncrement", "100-private");

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				endpoint, "?sort=", URLCodec.encodeURL("autoIncrement:desc")),
			Http.Method.GET);

		_assertItem("0", jsonObject, "autoIncrement", "100-private");
		_assertItem("1", jsonObject, "autoIncrement", "90-private");
		_assertItem("2", jsonObject, "autoIncrement", "10-private");

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);
	}

	@Test
	public void testPostCustomObjectEntryWithEmptyNestedCustomObjectEntriesInOneToManyRelationship()
		throws Exception {

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		JSONObject objectEntryJSONObject = JSONUtil.put(
			_objectRelationship1.getName(), JSONFactoryUtil.createJSONArray());

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			objectEntryJSONObject.toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		Assert.assertEquals(
			0,
			jsonObject.getJSONObject(
				"status"
			).get(
				"code"
			));

		JSONArray jsonArray = jsonObject.getJSONArray(
			_objectRelationship1.getName());

		Assert.assertEquals(0, jsonArray.length());
	}

	@Test
	public void testPostCustomObjectEntryWithInvalidNestedCustomObjectEntries()
		throws Exception {

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.vulcan.internal.jaxrs.exception.mapper." +
					"WebApplicationExceptionMapper",
				LoggerTestUtil.WARN)) {

			_objectRelationship1 =
				ObjectRelationshipTestUtil.addObjectRelationship(
					_objectDefinition1, _objectDefinition2,
					TestPropsValues.getUserId(),
					ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

			_testPostCustomObjectEntryWithInvalidNestedCustomObjectEntriesInManyToManyRelationship(
				_objectDefinition1.getRESTContextPath(), _objectRelationship1);

			_objectRelationship1 =
				ObjectRelationshipTestUtil.addObjectRelationship(
					_objectDefinition1, _objectDefinition2,
					TestPropsValues.getUserId(),
					ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

			_testPostCustomObjectEntryWithInvalidNestedCustomObjectEntriesInManyToOneRelationship(
				_objectDefinition2.getRESTContextPath(), _objectRelationship1);

			_testPostCustomObjectEntryWithInvalidNestedCustomObjectEntriesInOneToManyRelationship(
				_objectDefinition1.getRESTContextPath(), _objectRelationship1);
		}
	}

	@Test
	public void testPostCustomObjectEntryWithNestedCustomObjectEntriesInManyToManyRelationship()
		throws Exception {

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		JSONObject objectEntryJSONObject = JSONUtil.put(
			_objectRelationship1.getName(),
			_createObjectEntriesJSONArray(
				new String[] {_ERC_VALUE_1, _ERC_VALUE_2}, _OBJECT_FIELD_NAME_2,
				new String[] {
					_NEW_OBJECT_FIELD_VALUE_1, _NEW_OBJECT_FIELD_VALUE_2
				}));

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			objectEntryJSONObject.toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		Assert.assertEquals(
			0,
			jsonObject.getJSONObject(
				"status"
			).get(
				"code"
			));

		JSONArray nestedObjectEntriesJSONArray = jsonObject.getJSONArray(
			_objectRelationship1.getName());

		Assert.assertEquals(2, nestedObjectEntriesJSONArray.length());

		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(0),
			_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_1);
		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(1),
			_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_2);

		String objectEntryId = jsonObject.getString("id");

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				objectEntryId, "?nestedFields=",
				_objectRelationship1.getName()),
			Http.Method.GET);

		nestedObjectEntriesJSONArray = jsonObject.getJSONArray(
			_objectRelationship1.getName());

		Assert.assertEquals(2, nestedObjectEntriesJSONArray.length());

		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(0),
			_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_1);
		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(1),
			_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_2);
	}

	@Test
	public void testPostCustomObjectEntryWithNestedCustomObjectEntriesInManyToOneRelationship()
		throws Exception {

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		JSONObject objectEntryJSONObject = JSONUtil.put(
			_objectRelationship1.getName(),
			JSONFactoryUtil.createJSONObject(
				JSONUtil.put(
					_OBJECT_FIELD_NAME_1, _NEW_OBJECT_FIELD_VALUE_1
				).put(
					"externalReferenceCode", _ERC_VALUE_1
				).toString()));

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			objectEntryJSONObject.toString(),
			_objectDefinition2.getRESTContextPath(), Http.Method.POST);

		Assert.assertEquals(
			0,
			jsonObject.getJSONObject(
				"status"
			).get(
				"code"
			));

		_assertObjectEntryField(
			jsonObject.getJSONObject(
				StringBundler.concat(
					"r_", _objectRelationship1.getName(), "_",
					StringUtil.replaceLast(
						_objectDefinition1.getPKObjectFieldName(), "Id", ""))),
			_OBJECT_FIELD_NAME_1, _NEW_OBJECT_FIELD_VALUE_1);

		String objectEntryId = jsonObject.getString("id");

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(), StringPool.SLASH,
				objectEntryId, "?nestedFields=",
				StringBundler.concat(
					"r_", _objectRelationship1.getName(), "_",
					StringUtil.removeLast(
						_objectDefinition1.getPKObjectFieldName(), "Id"))),
			Http.Method.GET);

		_assertObjectEntryField(
			jsonObject.getJSONObject(
				StringBundler.concat(
					"r_", _objectRelationship1.getName(), "_",
					StringUtil.removeLast(
						_objectDefinition1.getPKObjectFieldName(), "Id"))),
			_OBJECT_FIELD_NAME_1, _NEW_OBJECT_FIELD_VALUE_1);
	}

	@Test
	public void testPostCustomObjectEntryWithNestedCustomObjectEntriesInOneToManyRelationship()
		throws Exception {

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		JSONObject objectEntryJSONObject = JSONUtil.put(
			_objectRelationship1.getName(),
			_createObjectEntriesJSONArray(
				new String[] {_ERC_VALUE_1, _ERC_VALUE_2}, _OBJECT_FIELD_NAME_2,
				new String[] {
					_NEW_OBJECT_FIELD_VALUE_1, _NEW_OBJECT_FIELD_VALUE_2
				}));

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			objectEntryJSONObject.toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		Assert.assertEquals(
			0,
			jsonObject.getJSONObject(
				"status"
			).get(
				"code"
			));

		JSONArray nestedObjectEntriesJSONArray = jsonObject.getJSONArray(
			_objectRelationship1.getName());

		Assert.assertEquals(2, nestedObjectEntriesJSONArray.length());

		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(0),
			_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_1);
		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(1),
			_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_2);

		String objectEntryId = jsonObject.getString("id");

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				objectEntryId, "?nestedFields=",
				_objectRelationship1.getName()),
			Http.Method.GET);

		nestedObjectEntriesJSONArray = jsonObject.getJSONArray(
			_objectRelationship1.getName());

		Assert.assertEquals(2, nestedObjectEntriesJSONArray.length());

		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(0),
			_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_1);
		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(1),
			_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_2);
	}

	@Test
	public void testPostObjectEntryWithKeywordsAndTaxonomyCategoryIdsWhenCategorizationDisabled()
		throws Exception {

		// TODO LPS-173383 Add test coverage for invalid state transitions

		_objectDefinition1.setEnableCategorization(false);

		_objectDefinition1 =
			_objectDefinitionLocalService.updateObjectDefinition(
				_objectDefinition1);

		try {
			TaxonomyCategory taxonomyCategory = _addTaxonomyCategory();

			JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
				JSONUtil.put(
					_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
				).put(
					"keywords", JSONUtil.putAll("tag")
				).put(
					"taxonomyCategoryIds",
					JSONUtil.putAll(taxonomyCategory.getId())
				).toString(),
				_objectDefinition1.getRESTContextPath(), Http.Method.POST);

			Assert.assertFalse(jsonObject.has("keywords"));
			Assert.assertFalse(jsonObject.has("taxonomyCategoryBriefs"));

			AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
				_objectDefinition1.getClassName(), jsonObject.getInt("id"));

			List<AssetTag> assetEntryAssetTags =
				AssetTagLocalServiceUtil.getAssetEntryAssetTags(
					assetEntry.getEntryId());

			Assert.assertEquals(
				assetEntryAssetTags.toString(), 0, assetEntryAssetTags.size());

			List<AssetCategory> assetCategories =
				AssetCategoryLocalServiceUtil.getCategories(
					_objectDefinition1.getClassName(), jsonObject.getInt("id"));

			Assert.assertEquals(
				assetCategories.toString(), 0, assetCategories.size());
		}
		finally {
			_objectDefinition1.setEnableCategorization(true);

			_objectDefinition1 =
				_objectDefinitionLocalService.updateObjectDefinition(
					_objectDefinition1);
		}
	}

	@Test
	public void testPutByExternalReferenceCodeManyToManyRelationship()
		throws Exception {

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(),
				"/by-external-reference-code/",
				_objectEntry1.getExternalReferenceCode(), StringPool.SLASH,
				_objectRelationship1.getName(), StringPool.SLASH,
				_objectEntry2.getExternalReferenceCode()),
			Http.Method.PUT);

		Assert.assertEquals(
			_objectEntry2.getExternalReferenceCode(),
			jsonObject.getString("externalReferenceCode"));
		Assert.assertEquals(
			_OBJECT_FIELD_VALUE_2, jsonObject.getInt(_OBJECT_FIELD_NAME_2));

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(),
				"/by-external-reference-code/",
				_objectEntry2.getExternalReferenceCode(), StringPool.SLASH,
				_objectRelationship1.getName(), StringPool.SLASH,
				_objectEntry1.getExternalReferenceCode()),
			Http.Method.PUT);

		Assert.assertEquals(
			_objectEntry1.getExternalReferenceCode(),
			jsonObject.getString("externalReferenceCode"));
		Assert.assertEquals(
			_OBJECT_FIELD_VALUE_1, jsonObject.getInt(_OBJECT_FIELD_NAME_1));

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(),
				"/by-external-reference-code/",
				_objectEntry2.getExternalReferenceCode(), StringPool.SLASH,
				_objectRelationship1.getName(), StringPool.SLASH,
				RandomTestUtil.randomString()),
			Http.Method.PUT);

		Assert.assertThat(
			jsonObject.getString("title"),
			CoreMatchers.containsString("No ObjectEntry exists with the key"));
	}

	@Test
	public void testPutByExternalReferenceCodeManyToManyRelationshipWithSelf()
		throws Exception {

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition1, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, _NEW_OBJECT_FIELD_VALUE_1
			).put(
				_objectRelationship1.getName(),
				JSONUtil.putAll(
					JSONUtil.put(
						_OBJECT_FIELD_NAME_1, _NEW_OBJECT_FIELD_VALUE_2
					).put(
						"externalReferenceCode", _ERC_VALUE_2
					),
					JSONUtil.put(
						_OBJECT_FIELD_NAME_1, _NEW_OBJECT_FIELD_VALUE_3
					).put(
						"externalReferenceCode", _ERC_VALUE_3
					))
			).toString(),
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(),
				"/by-external-reference-code/", _ERC_VALUE_1),
			Http.Method.PUT);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"items",
				JSONUtil.putAll(
					JSONUtil.put(
						_OBJECT_FIELD_NAME_1, _NEW_OBJECT_FIELD_VALUE_2
					).put(
						"externalReferenceCode", _ERC_VALUE_2
					),
					JSONUtil.put(
						_OBJECT_FIELD_NAME_1, _NEW_OBJECT_FIELD_VALUE_3
					).put(
						"externalReferenceCode", _ERC_VALUE_3
					))
			).put(
				"lastPage", 1
			).put(
				"page", 1
			).put(
				"pageSize", 20
			).put(
				"totalCount", 2
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				null,
				StringBundler.concat(
					_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
					jsonObject.get("id"), StringPool.SLASH,
					_objectRelationship1.getName()),
				Http.Method.GET
			).toString(),
			JSONCompareMode.LENIENT);
	}

	@Test
	public void testPutByExternalReferenceCodeMultipleManyToManyRelationships()
		throws Exception {

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_objectRelationship2 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition3, _objectDefinition1, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		Assert.assertEquals(
			200,
			HTTPTestUtil.invokeToHttpCode(
				JSONUtil.put(
					_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
				).put(
					"externalReferenceCode", _ERC_VALUE_1
				).put(
					_objectRelationship1.getName(),
					_createObjectEntriesJSONArray(
						new String[] {RandomTestUtil.randomString()},
						_OBJECT_FIELD_NAME_2,
						new String[] {RandomTestUtil.randomString()})
				).put(
					_objectRelationship2.getName(),
					_createObjectEntriesJSONArray(
						new String[] {_ERC_VALUE_3},
						RandomTestUtil.randomString(),
						new String[] {RandomTestUtil.randomString()})
				).toString(),
				_objectDefinition1.getRESTContextPath(), Http.Method.POST));

		Assert.assertEquals(
			200,
			HTTPTestUtil.invokeToHttpCode(
				JSONUtil.put(
					_OBJECT_FIELD_NAME_1, _NEW_OBJECT_FIELD_VALUE_1
				).put(
					_objectRelationship1.getName(),
					_createObjectEntriesJSONArray(
						new String[] {_ERC_VALUE_2}, _OBJECT_FIELD_NAME_2,
						new String[] {_NEW_OBJECT_FIELD_VALUE_2})
				).toString(),
				StringBundler.concat(
					_objectDefinition1.getRESTContextPath(),
					"/by-external-reference-code/", _ERC_VALUE_1),
				Http.Method.PUT));

		JSONAssert.assertEquals(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, _NEW_OBJECT_FIELD_VALUE_1
			).put(
				_objectRelationship1.getName(),
				JSONUtil.putAll(
					JSONUtil.put("externalReferenceCode", _ERC_VALUE_2))
			).put(
				_objectRelationship2.getName(),
				JSONUtil.putAll(
					JSONUtil.put("externalReferenceCode", _ERC_VALUE_3))
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				null,
				StringBundler.concat(
					_objectDefinition1.getRESTContextPath(),
					"/by-external-reference-code/", _ERC_VALUE_1,
					"?nestedFields=", _objectRelationship1.getName(), ",",
					_objectRelationship2.getName()),
				Http.Method.GET
			).toString(),
			JSONCompareMode.LENIENT);
	}

	@Test
	public void testPutByExternalReferenceCodeMultipleOneToManyRelationships()
		throws Exception {

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_objectRelationship2 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition2, _objectDefinition3, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_2, RandomTestUtil.randomString()
			).put(
				"externalReferenceCode", _ERC_VALUE_2
			).put(
				_objectRelationship1.getName(),
				JSONUtil.put(
					_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
				).put(
					"externalReferenceCode", _ERC_VALUE_1
				)
			).put(
				_objectRelationship2.getName(),
				JSONUtil.putAll(
					JSONUtil.put(
						_OBJECT_FIELD_NAME_3, RandomTestUtil.randomString()
					).put(
						"externalReferenceCode", _ERC_VALUE_3
					),
					JSONUtil.put(
						_OBJECT_FIELD_NAME_3, RandomTestUtil.randomString()
					).put(
						"externalReferenceCode", RandomTestUtil.randomString()
					))
			).toString(),
			_objectDefinition2.getRESTContextPath(), Http.Method.POST);

		Assert.assertEquals(
			200,
			HTTPTestUtil.invokeToHttpCode(
				JSONUtil.put(
					_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_2
				).put(
					_objectRelationship2.getName(),
					_createObjectEntriesJSONArray(
						new String[] {_ERC_VALUE_3}, _OBJECT_FIELD_NAME_3,
						new String[] {_NEW_OBJECT_FIELD_VALUE_3})
				).toString(),
				StringBundler.concat(
					_objectDefinition2.getRESTContextPath(),
					"/by-external-reference-code/", _ERC_VALUE_2),
				Http.Method.PUT));

		String objectEntryId = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(),
				"/by-external-reference-code/", _ERC_VALUE_1),
			Http.Method.GET
		).get(
			"id"
		).toString();

		JSONAssert.assertEquals(
			JSONUtil.put(
				"items",
				JSONUtil.putAll(
					JSONUtil.put(
						_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_2
					).put(
						"externalReferenceCode", _ERC_VALUE_2
					))
			).put(
				"lastPage", 1
			).put(
				"page", 1
			).put(
				"pageSize", 20
			).put(
				"totalCount", 1
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				null,
				StringBundler.concat(
					_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
					objectEntryId, StringPool.SLASH,
					_objectRelationship1.getName()),
				Http.Method.GET
			).toString(),
			JSONCompareMode.LENIENT);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"items",
				JSONUtil.putAll(
					JSONUtil.put(
						_OBJECT_FIELD_NAME_3, _NEW_OBJECT_FIELD_VALUE_3
					).put(
						"externalReferenceCode", _ERC_VALUE_3
					))
			).put(
				"lastPage", 1
			).put(
				"page", 1
			).put(
				"pageSize", 20
			).put(
				"totalCount", 1
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				null,
				StringBundler.concat(
					_objectDefinition2.getRESTContextPath(), StringPool.SLASH,
					jsonObject.get("id"), StringPool.SLASH,
					_objectRelationship2.getName()),
				Http.Method.GET
			).toString(),
			JSONCompareMode.LENIENT);
	}

	@Test
	public void testPutByExternalReferenceCodeWithNonexistentValueOneToManyRelationship()
		throws Exception {

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		Assert.assertEquals(
			200,
			HTTPTestUtil.invokeToHttpCode(
				JSONUtil.put(
					_OBJECT_FIELD_NAME_1, _NEW_OBJECT_FIELD_VALUE_1
				).put(
					_objectRelationship1.getName(),
					_createObjectEntriesJSONArray(
						new String[] {_ERC_VALUE_2}, _OBJECT_FIELD_NAME_2,
						new String[] {_NEW_OBJECT_FIELD_VALUE_2})
				).toString(),
				StringBundler.concat(
					_objectDefinition1.getRESTContextPath(),
					"/by-external-reference-code/", _ERC_VALUE_1),
				Http.Method.PUT));

		JSONAssert.assertEquals(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, _NEW_OBJECT_FIELD_VALUE_1
			).put(
				_objectRelationship1.getName(),
				JSONUtil.putAll(
					JSONUtil.put("externalReferenceCode", _ERC_VALUE_2))
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				null,
				StringBundler.concat(
					_objectDefinition1.getRESTContextPath(),
					"/by-external-reference-code/", _ERC_VALUE_1,
					"?nestedFields=", _objectRelationship1.getName()),
				Http.Method.GET
			).toString(),
			JSONCompareMode.LENIENT);
	}

	@Test
	public void testPutCustomObjectEntryUnlinkNestedCustomObjectEntries()
		throws Exception {

		// Many to many

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_testPutCustomObjectEntryUnlinkNestedCustomObjectEntries(false);

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship1);

		// Many to one

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition2, _objectDefinition1, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_testPutCustomObjectEntryUnlinkNestedCustomObjectEntries(true);

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship1);

		// One to many

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_testPutCustomObjectEntryUnlinkNestedCustomObjectEntries(false);
	}

	@Test
	public void testPutCustomObjectEntryUnlinkNestedCustomObjectEntriesByExternalReferenceCode()
		throws Exception {

		// Many to many

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_testPutCustomObjectEntryUnlinkNestedCustomObjectEntriesByExternalReferenceCode(
			false);

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship1);

		// Many to one

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition2, _objectDefinition1, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_testPutCustomObjectEntryUnlinkNestedCustomObjectEntriesByExternalReferenceCode(
			true);

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship1);

		// One to many

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_testPutCustomObjectEntryUnlinkNestedCustomObjectEntriesByExternalReferenceCode(
			false);
	}

	@Test
	public void testPutCustomObjectEntryWithNestedCustomObjectEntriesByExternalReferenceCode()
		throws Exception {

		// Many to many

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_testPutCustomObjectEntryWithNestedCustomObjectEntriesByExternalReferenceCode(
			false);

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship1);

		// Many to one

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition2, _objectDefinition1, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_testPutCustomObjectEntryWithNestedCustomObjectEntriesByExternalReferenceCode(
			true);

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship1);

		// One to many

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_testPutCustomObjectEntryWithNestedCustomObjectEntriesByExternalReferenceCode(
			false);
	}

	@Test
	public void testPutCustomObjectEntryWithNestedCustomObjectEntriesInManyToManyRelationship()
		throws Exception {

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		JSONObject objectEntryJSONObject = JSONUtil.put(
			_objectRelationship1.getName(),
			_createObjectEntriesJSONArray(
				new String[] {_ERC_VALUE_1, _ERC_VALUE_2}, _OBJECT_FIELD_NAME_2,
				new String[] {
					RandomTestUtil.randomString(), RandomTestUtil.randomString()
				}));

		HTTPTestUtil.invokeToJSONObject(
			objectEntryJSONObject.toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		JSONObject newObjectEntryJSONObject = JSONUtil.put(
			_objectRelationship1.getName(),
			_createObjectEntriesJSONArray(
				new String[] {_ERC_VALUE_1}, _OBJECT_FIELD_NAME_2,
				new String[] {_NEW_OBJECT_FIELD_VALUE_1}));

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			newObjectEntryJSONObject.toString(),
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey()),
			Http.Method.PUT);

		Assert.assertEquals(
			0,
			jsonObject.getJSONObject(
				"status"
			).get(
				"code"
			));

		JSONArray nestedObjectEntriesJSONArray = jsonObject.getJSONArray(
			_objectRelationship1.getName());

		Assert.assertEquals(1, nestedObjectEntriesJSONArray.length());

		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(0),
			_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_1);

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey(), "?nestedFields=",
				_objectRelationship1.getName()),
			Http.Method.GET);

		nestedObjectEntriesJSONArray = jsonObject.getJSONArray(
			_objectRelationship1.getName());

		Assert.assertEquals(1, nestedObjectEntriesJSONArray.length());

		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(0),
			_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_1);
	}

	@Test
	public void testPutCustomObjectEntryWithNestedCustomObjectEntriesInManyToOneRelationship()
		throws Exception {

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		JSONObject objectEntryJSONObject = JSONUtil.put(
			_objectRelationship1.getName(),
			JSONFactoryUtil.createJSONObject(
				JSONUtil.put(
					_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
				).put(
					"externalReferenceCode", _ERC_VALUE_1
				).toString()));

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			objectEntryJSONObject.toString(),
			_objectDefinition2.getRESTContextPath(), Http.Method.POST);

		String objectEntryId = jsonObject.getString("id");

		JSONObject newObjectEntryJSONObject = JSONUtil.put(
			_objectRelationship1.getName(),
			JSONFactoryUtil.createJSONObject(
				JSONUtil.put(
					_OBJECT_FIELD_NAME_1, _NEW_OBJECT_FIELD_VALUE_1
				).put(
					"externalReferenceCode", _ERC_VALUE_1
				).toString()));

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			newObjectEntryJSONObject.toString(),
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(), StringPool.SLASH,
				objectEntryId),
			Http.Method.PUT);

		Assert.assertEquals(
			0,
			jsonObject.getJSONObject(
				"status"
			).get(
				"code"
			));

		_assertObjectEntryField(
			jsonObject.getJSONObject(
				StringBundler.concat(
					"r_", _objectRelationship1.getName(), "_",
					StringUtil.replaceLast(
						_objectDefinition1.getPKObjectFieldName(), "Id", ""))),
			_OBJECT_FIELD_NAME_1, _NEW_OBJECT_FIELD_VALUE_1);

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				_objectDefinition2.getRESTContextPath(), StringPool.SLASH,
				objectEntryId, "?nestedFields=",
				StringBundler.concat(
					"r_", _objectRelationship1.getName(), "_",
					StringUtil.removeLast(
						_objectDefinition1.getPKObjectFieldName(), "Id"))),
			Http.Method.GET);

		_assertObjectEntryField(
			jsonObject.getJSONObject(
				StringBundler.concat(
					"r_", _objectRelationship1.getName(), "_",
					StringUtil.removeLast(
						_objectDefinition1.getPKObjectFieldName(), "Id"))),
			_OBJECT_FIELD_NAME_1, _NEW_OBJECT_FIELD_VALUE_1);
	}

	@Test
	public void testPutCustomObjectEntryWithNestedCustomObjectEntriesInOneToManyRelationship()
		throws Exception {

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		JSONObject objectEntryJSONObject = JSONUtil.put(
			_objectRelationship1.getName(),
			_createObjectEntriesJSONArray(
				new String[] {_ERC_VALUE_1, _ERC_VALUE_2}, _OBJECT_FIELD_NAME_2,
				new String[] {
					RandomTestUtil.randomString(), RandomTestUtil.randomString()
				}));

		HTTPTestUtil.invokeToJSONObject(
			objectEntryJSONObject.toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		JSONObject newObjectEntryJSONObject = JSONUtil.put(
			_objectRelationship1.getName(),
			_createObjectEntriesJSONArray(
				new String[] {_ERC_VALUE_1}, _OBJECT_FIELD_NAME_2,
				new String[] {_NEW_OBJECT_FIELD_VALUE_1}));

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			newObjectEntryJSONObject.toString(),
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey()),
			Http.Method.PUT);

		Assert.assertEquals(
			0,
			jsonObject.getJSONObject(
				"status"
			).get(
				"code"
			));

		JSONArray nestedObjectEntriesJSONArray = jsonObject.getJSONArray(
			_objectRelationship1.getName());

		Assert.assertEquals(1, nestedObjectEntriesJSONArray.length());

		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(0),
			_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_1);

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				_objectEntry1.getPrimaryKey(), "?nestedFields=",
				_objectRelationship1.getName()),
			Http.Method.GET);

		nestedObjectEntriesJSONArray = jsonObject.getJSONArray(
			_objectRelationship1.getName());

		Assert.assertEquals(1, nestedObjectEntriesJSONArray.length());

		_assertObjectEntryField(
			(JSONObject)nestedObjectEntriesJSONArray.get(0),
			_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_1);
	}

	private void _addModelResourcePermissions(
			String[] actionIds, String className, long objectEntryId,
			long userId)
		throws Exception {

		_resourcePermissionLocalService.addModelResourcePermissions(
			TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
			userId, className, String.valueOf(objectEntryId),
			ModelPermissionsFactory.create(
				HashMapBuilder.put(
					RoleConstants.USER, actionIds
				).build(),
				className));
	}

	private ObjectRelationship _addObjectRelationshipAndRelateObjectEntries(
			ObjectDefinition objectDefinition1,
			ObjectDefinition objectDefinition2, long primaryKey1,
			long primaryKey2, String type)
		throws Exception {

		ObjectRelationship objectRelationship =
			ObjectRelationshipTestUtil.addObjectRelationship(
				objectDefinition1, objectDefinition2,
				TestPropsValues.getUserId(), type);

		ObjectRelationshipTestUtil.relateObjectEntries(
			primaryKey1, primaryKey2, objectRelationship,
			TestPropsValues.getUserId());

		return objectRelationship;
	}

	private ObjectRelationship _addObjectRelationshipAndRelateObjectEntries(
			String type)
		throws Exception {

		return _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition1, _objectDefinition2,
			_objectEntry1.getPrimaryKey(), _objectEntry2.getPrimaryKey(), type);
	}

	private TaxonomyCategory _addTaxonomyCategory() throws Exception {
		return _taxonomyCategoryResource.postTaxonomyVocabularyTaxonomyCategory(
			_assetVocabulary.getVocabularyId(),
			new TaxonomyCategory() {
				{
					dateCreated = RandomTestUtil.nextDate();
					dateModified = RandomTestUtil.nextDate();
					description = StringUtil.toLowerCase(
						RandomTestUtil.randomString());
					externalReferenceCode = StringUtil.toLowerCase(
						RandomTestUtil.randomString());
					id = StringUtil.toLowerCase(RandomTestUtil.randomString());
					name = StringUtil.toLowerCase(
						RandomTestUtil.randomString());
					numberOfTaxonomyCategories = RandomTestUtil.randomInt();
					siteId = TestPropsValues.getGroupId();
					taxonomyCategoryUsageCount = RandomTestUtil.randomInt();
					taxonomyVocabularyId = RandomTestUtil.randomLong();
				}
			});
	}

	private FileEntry _addTempFileEntry(
			String content, ObjectDefinition objectDefinition, String title)
		throws Exception {

		return TempFileEntryUtil.addTempFileEntry(
			TestPropsValues.getGroupId(), TestPropsValues.getUserId(),
			objectDefinition.getPortletId(),
			TempFileEntryUtil.getTempFileName(title + ".txt"),
			FileUtil.createTempFile(content.getBytes()),
			ContentTypes.TEXT_PLAIN);
	}

	private void _assertFilteredObjectEntries(
			int expectedObjectEntryCount, String filterString)
		throws Exception {

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			_objectDefinition1.getRESTContextPath() + "?filter=" +
				URLCodec.encodeURL(filterString),
			Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(expectedObjectEntryCount, itemsJSONArray.length());
	}

	private void _assertFilterString(
			String expectedObjectFieldName,
			Serializable expectedObjectFieldValue, String filterString,
			ObjectDefinition objectDefinition)
		throws Exception {

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			objectDefinition.getRESTContextPath() + "?filter=" + filterString,
			Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());

		JSONObject itemJSONObject = itemsJSONArray.getJSONObject(0);

		Assert.assertEquals(
			String.valueOf(expectedObjectFieldValue),
			String.valueOf(itemJSONObject.get(expectedObjectFieldName)));
	}

	private void _assertItem(
		String index, JSONObject jsonObject, String objectFieldName,
		String value) {

		Assert.assertEquals(
			value,
			JSONUtil.getValue(
				jsonObject, "JSONArray/items", "JSONObject/" + index,
				"Object/" + objectFieldName));
	}

	private void _assertJSONObjectWithAttachmentField(
			JSONObject jsonObject1, JSONObject jsonObject2,
			String objectFieldName)
		throws Exception {

		JSONAssert.assertEquals(
			jsonObject1.toString(), jsonObject2.toString(),
			JSONCompareMode.LENIENT);

		Object object = jsonObject1.get(objectFieldName);

		if (object != null) {
			JSONAssert.assertEquals(
				String.valueOf(object),
				String.valueOf(jsonObject2.get(objectFieldName)),
				JSONCompareMode.STRICT);
		}
	}

	private void _assertNestedFieldsFieldsInRelationships(
		int currentDepth, int depth, JSONObject jsonObject,
		String[] nestedFieldNames,
		String[][] objectFieldNamesAndObjectFieldValues, Type[] types) {

		if (objectFieldNamesAndObjectFieldValues[currentDepth][0] == null) {
			Assert.assertNull(jsonObject);
		}
		else {
			String notPresent;

			if (objectFieldNamesAndObjectFieldValues[currentDepth][1].equals(
					jsonObject.getString(
						objectFieldNamesAndObjectFieldValues[currentDepth]
							[0]))) {

				notPresent = "true";
			}
			else {
				notPresent = "false";
			}

			Assert.assertEquals(
				"Incorrect presence of field " +
					objectFieldNamesAndObjectFieldValues[currentDepth][0],
				objectFieldNamesAndObjectFieldValues[currentDepth][2],
				notPresent);
		}

		if ((currentDepth == depth) ||
			(currentDepth ==
				PropsValues.OBJECT_NESTED_FIELDS_MAX_QUERY_DEPTH)) {

			Assert.assertEquals(
				Arrays.toString(objectFieldNamesAndObjectFieldValues),
				currentDepth + 1, objectFieldNamesAndObjectFieldValues.length);

			return;
		}

		_assertNestedFieldsFieldsInRelationships(
			currentDepth + 1, depth,
			_getRelatedJSONObject(
				jsonObject, nestedFieldNames[currentDepth],
				types[currentDepth]),
			nestedFieldNames, objectFieldNamesAndObjectFieldValues, types);
	}

	private void _assertNestedFieldsInRelationships(
		int currentDepth, int depth, JSONObject jsonObject,
		String nestedFieldName, String[][] objectFieldNamesAndObjectFieldValues,
		Type type) {

		if (objectFieldNamesAndObjectFieldValues[currentDepth][0] == null) {
			Assert.assertNull(jsonObject);
		}
		else {
			Assert.assertEquals(
				objectFieldNamesAndObjectFieldValues[currentDepth][1],
				jsonObject.getString(
					objectFieldNamesAndObjectFieldValues[currentDepth][0]));
		}

		if ((currentDepth == depth) ||
			(currentDepth ==
				PropsValues.OBJECT_NESTED_FIELDS_MAX_QUERY_DEPTH)) {

			Assert.assertEquals(
				Arrays.toString(objectFieldNamesAndObjectFieldValues),
				currentDepth + 1, objectFieldNamesAndObjectFieldValues.length);
			Assert.assertNull(jsonObject.get(nestedFieldName));

			return;
		}

		_assertNestedFieldsInRelationships(
			currentDepth + 1, depth,
			_getRelatedJSONObject(jsonObject, nestedFieldName, type),
			nestedFieldName, objectFieldNamesAndObjectFieldValues,
			_getReverseType(type));
	}

	private void _assertObjectEntryField(
		JSONObject objectEntryJSONObject, String objectFieldName,
		String objectFieldValue) {

		int objectEntryId = objectEntryJSONObject.getInt("id");

		ObjectEntry objectEntry = _objectEntryLocalService.fetchObjectEntry(
			objectEntryId);

		Assert.assertEquals(
			"_assertObjectEntryField",
			MapUtil.getString(objectEntry.getValues(), objectFieldName),
			objectFieldValue);
	}

	private JSONArray _createObjectEntriesJSONArray(
			String[] externalReferenceCodeValues, String objectFieldName,
			String[] objectFieldValues)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (int i = 0; i < objectFieldValues.length; i++) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				JSONUtil.put(
					objectFieldName, objectFieldValues[i]
				).put(
					"externalReferenceCode", externalReferenceCodeValues[i]
				).toString());

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	private String _escape(String string) {
		return URLCodec.encodeURL(string);
	}

	private DLFolder _getDLFolder(ObjectDefinition objectDefinition)
		throws Exception {

		ObjectScopeProvider objectScopeProvider =
			_objectScopeProviderRegistry.getObjectScopeProvider(
				objectDefinition.getScope());

		long groupId = 0;

		if (objectScopeProvider.isGroupAware()) {
			groupId = TestPropsValues.getGroupId();
		}
		else {
			Company company = _companyLocalService.getCompany(
				objectDefinition.getCompanyId());

			groupId = company.getGroupId();
		}

		Repository repository = _portletFileRepository.getPortletRepository(
			groupId, objectDefinition.getPortletId());

		return _dlFolderLocalService.getFolder(
			repository.getGroupId(), repository.getDlFolderId(),
			String.valueOf(TestPropsValues.getUserId()));
	}

	private String _getEndpoint(
		long groupId, ObjectDefinition objectDefinition) {

		ObjectScopeProvider objectScopeProvider =
			_objectScopeProviderRegistry.getObjectScopeProvider(
				objectDefinition.getScope());

		if (objectScopeProvider.isGroupAware()) {
			return objectDefinition.getRESTContextPath() + "/scopes/" + groupId;
		}

		return objectDefinition.getRESTContextPath();
	}

	private JSONObject _getLinkJSONObject(
			DLFolder dlFolder, long fileEntryId, String fileName,
			ObjectDefinition objectDefinition)
		throws Exception {

		FileEntry fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);

		FileVersion fileVersion = fileEntry.getFileVersion();

		Date modifiedDate = fileVersion.getModifiedDate();

		ObjectEntry objectEntry = _objectEntryLocalService.getObjectEntry(
			_testObjectEntryModelListener.getLastObjectEntryId());

		Link link = new Link();

		link.setHref(
			StringBundler.concat(
				"/documents/", dlFolder.getRepositoryId(), "/",
				dlFolder.getFolderId(), "/", URLCodec.encodeURL(fileName), "/",
				fileEntry.getExternalReferenceCode(), "?version=",
				fileVersion.getVersion(), "&t=", modifiedDate.getTime(),
				"&download=true&objectDefinitionExternalReferenceCode=",
				objectDefinition.getExternalReferenceCode(),
				"&objectEntryExternalReferenceCode=",
				objectEntry.getExternalReferenceCode()));
		link.setLabel(fileName);

		return JSONFactoryUtil.createJSONObject(link.toString());
	}

	private NestedFieldsContext _getNestedFieldsContext(String nestedFields) {
		return new NestedFieldsContext(
			1, ListUtil.fromString(nestedFields, StringPool.COMMA), null, null,
			null, null);
	}

	private ObjectEntryResource _getObjectEntryResource(
			ObjectDefinition objectDefinition, User user)
		throws Exception {

		Bundle bundle = FrameworkUtil.getBundle(ObjectEntryResourceTest.class);

		try (ServiceTrackerMap<String, ObjectEntryResource> serviceTrackerMap =
				ServiceTrackerMapFactory.openSingleValueMap(
					bundle.getBundleContext(), ObjectEntryResource.class,
					"entity.class.name")) {

			ObjectEntryResource objectEntryResource =
				serviceTrackerMap.getService(
					StringBundler.concat(
						com.liferay.object.rest.dto.v1_0.ObjectEntry.class.
							getName(),
						StringPool.POUND, objectDefinition.getOSGiJaxRsName()));

			objectEntryResource.setContextAcceptLanguage(
				new AcceptLanguage() {

					@Override
					public List<Locale> getLocales() {
						return Arrays.asList(LocaleUtil.getDefault());
					}

					@Override
					public String getPreferredLanguageId() {
						return LocaleUtil.toLanguageId(LocaleUtil.getDefault());
					}

					@Override
					public Locale getPreferredLocale() {
						return LocaleUtil.getDefault();
					}

				});
			objectEntryResource.setContextCompany(
				_companyLocalService.getCompany(
					objectDefinition.getCompanyId()));
			objectEntryResource.setContextUser(user);

			Class<?> clazz = objectEntryResource.getClass();

			Method method = clazz.getMethod(
				"setObjectDefinition", ObjectDefinition.class);

			method.invoke(objectEntryResource, objectDefinition);

			return objectEntryResource;
		}
	}

	private JSONObject _getRelatedJSONObject(
		JSONObject jsonObject, String nestedFieldName, Type type) {

		if (type == Type.MANY_TO_ONE) {
			JSONObject nestedJSONObject = jsonObject.getJSONObject(
				nestedFieldName);

			Assert.assertNotNull(
				"Missing field " + nestedFieldName, nestedJSONObject);

			return jsonObject.getJSONObject(nestedFieldName);
		}

		JSONArray jsonArray = jsonObject.getJSONArray(nestedFieldName);

		Assert.assertNotNull("Missing field " + nestedFieldName, jsonArray);

		Assert.assertEquals(1, jsonArray.length());

		return jsonArray.getJSONObject(0);
	}

	private Type _getReverseType(Type type) {
		if (type == Type.MANY_TO_ONE) {
			return Type.ONE_TO_MANY;
		}
		else if (type == Type.ONE_TO_MANY) {
			return Type.MANY_TO_ONE;
		}

		return Type.MANY_TO_MANY;
	}

	private void _postObjectEntryWithKeywords(String... keywords)
		throws Exception {

		HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
			).put(
				"keywords", JSONUtil.putAll(keywords)
			).toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);
	}

	private void _postObjectEntryWithTaxonomyCategories(
			TaxonomyCategory... taxonomyCategories)
		throws Exception {

		HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
			).put(
				"taxonomyCategoryIds",
				TransformUtil.transform(
					taxonomyCategories, TaxonomyCategory::getId, String.class)
			).toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);
	}

	private void _testFilterObjectEntriesByRelatesSystemObjectEntriesFields(
			String filterString, ObjectDefinition objectDefinition)
		throws Exception {

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			objectDefinition.getRESTContextPath() + "?filter=" + filterString,
			Http.Method.GET);

		Assert.assertEquals(
			"Filtering is not supported for system objects",
			jsonObject.getString("title"));
		Assert.assertEquals("BAD_REQUEST", jsonObject.getString("status"));
	}

	private void _testGetNestedFieldDetailsInRelationships(
			String expectedFieldName, Integer nestedFieldDepth,
			String nestedFieldName, ObjectDefinition objectDefinition,
			String[][] objectFieldNamesAndObjectFieldValues, Type type)
		throws Exception {

		String endpoint = StringBundler.concat(
			objectDefinition.getRESTContextPath(), "?nestedFields=",
			nestedFieldName);

		if (nestedFieldDepth != null) {
			endpoint += "&nestedFieldsDepth=" + nestedFieldDepth;
		}

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			null, endpoint, Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, itemsJSONArray.length());

		JSONObject itemJSONObject = itemsJSONArray.getJSONObject(0);

		_assertNestedFieldsInRelationships(
			0, GetterUtil.getInteger(nestedFieldDepth, 1), itemJSONObject,
			expectedFieldName, objectFieldNamesAndObjectFieldValues, type);
	}

	private void _testPatchPutCustomObjectEntryWithAttachmentField(
			Http.Method httpMethod, ObjectDefinition objectDefinition,
			boolean useExternalReferenceCode)
		throws Exception {

		// File validation

		_testPatchPutCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				"status", "BAD_REQUEST"
			).put(
				"title",
				StringBundler.concat(
					"File ", fileEntry.getName(),
					" exceeds the maximum permitted size of ",
					_MAX_FILE_SIZE_VALUE, " MB")
			),
			_toFileEntry(
				Base64::encode,
				RandomTestUtil.randomString(
					(_MAX_FILE_SIZE_VALUE * 1024 * 1024) + 1),
				RandomTestUtil.randomString() + ".txt", null, null),
			httpMethod, null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE,
			useExternalReferenceCode);
		_testPatchPutCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				"status", "BAD_REQUEST"
			).put(
				"title", "File name is null"
			),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(), null, null,
				null),
			httpMethod, null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE,
			useExternalReferenceCode);
		_testPatchPutCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				"status", "BAD_REQUEST"
			).put(
				"title", "Invalid file extension for " + fileEntry.getName()
			),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".err", null, null),
			httpMethod, null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE,
			useExternalReferenceCode);
		_testPatchPutCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				"status", "BAD_REQUEST"
			).put(
				"title", "Unable to decode Base64 file"
			),
			_toFileEntry(
				String::new, RandomTestUtil.randomString(7),
				RandomTestUtil.randomString() + ".txt", null, null),
			httpMethod, null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE,
			useExternalReferenceCode);

		// File with a nonexistent name

		_testPatchPutCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE,
				JSONUtil.put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						_getDLFolder(objectDefinition),
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".txt", null, null),
			httpMethod, null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE,
			useExternalReferenceCode);
		_testPatchPutCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE,
				JSONUtil.put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						_getDLFolder(objectDefinition),
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".txt",
				RandomTestUtil.randomString(), RandomTestUtil.randomLong()),
			httpMethod, null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE,
			useExternalReferenceCode);

		// File with a nonexistent name and the Base64 content as a nested field

		_testPatchPutCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE,
				JSONUtil.put(
					"fileBase64", fileEntry.getFileBase64()
				).put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						_getDLFolder(objectDefinition),
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".txt", null, null),
			httpMethod, "fileBase64", objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE,
			useExternalReferenceCode);

		// File with an existing name

		com.liferay.object.rest.dto.v1_0.FileEntry testFileEntry = _toFileEntry(
			Base64::encode, RandomTestUtil.randomString(),
			RandomTestUtil.randomString() + ".txt", null, null);

		_testPatchPutCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE,
				JSONUtil.put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						_getDLFolder(objectDefinition),
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			testFileEntry, httpMethod, null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE,
			useExternalReferenceCode);
		_testPatchPutCustomObjectEntryWithAttachmentField(
			fileEntry -> {
				String newName = StringUtil.replace(
					fileEntry.getName(), ".txt", " (1).txt");

				return JSONUtil.put(
					_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE,
					JSONUtil.put(
						"id", _testDLFileEntryModelListener.getLastFileEntryId()
					).put(
						"link",
						_getLinkJSONObject(
							_getDLFolder(objectDefinition),
							_testDLFileEntryModelListener.getLastFileEntryId(),
							newName, objectDefinition)
					).put(
						"name", newName
					));
			},
			testFileEntry, httpMethod, null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE,
			useExternalReferenceCode);
	}

	private void _testPatchPutCustomObjectEntryWithAttachmentField(
			UnsafeFunction
				<com.liferay.object.rest.dto.v1_0.FileEntry, JSONObject,
				 Exception> expectedJSONObjectUnsafeFunction,
			com.liferay.object.rest.dto.v1_0.FileEntry fileEntry,
			Http.Method httpMethod, String nestedFields,
			ObjectDefinition objectDefinition, String objectFieldName,
			boolean useExternalReferenceCode)
		throws Exception {

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
			).put(
				objectFieldName,
				_toFileEntryJSONObject(
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString() + ".txt", objectFieldName)
			).toString(),
			_getEndpoint(TestPropsValues.getGroupId(), objectDefinition),
			Http.Method.POST);

		String endpoint =
			objectDefinition.getRESTContextPath() + "/" +
				jsonObject.getLong("id");

		if (useExternalReferenceCode) {
			endpoint =
				_getEndpoint(TestPropsValues.getGroupId(), objectDefinition) +
					"/by-external-reference-code/" +
						jsonObject.getString("externalReferenceCode");
		}

		if (nestedFields != null) {
			endpoint = StringBundler.concat(
				endpoint, "?nestedFields=", objectFieldName, ".", nestedFields);
		}

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
			).put(
				objectFieldName,
				JSONFactoryUtil.createJSONObject(fileEntry.toString())
			).put(
				"externalReferenceCode",
				jsonObject.getString("externalReferenceCode")
			).put(
				"id", jsonObject.getLong("id")
			).toString(),
			endpoint, httpMethod);

		_assertJSONObjectWithAttachmentField(
			expectedJSONObjectUnsafeFunction.apply(fileEntry), jsonObject,
			objectFieldName);
	}

	private void _testPostCustomObjectEntryWithAttachmentField(
			ObjectDefinition objectDefinition)
		throws Exception {

		// File validation

		_testPostCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				"status", "BAD_REQUEST"
			).put(
				"title",
				StringBundler.concat(
					"File ", fileEntry.getName(),
					" exceeds the maximum permitted size of ",
					_MAX_FILE_SIZE_VALUE, " MB")
			),
			_toFileEntry(
				Base64::encode,
				RandomTestUtil.randomString(
					(_MAX_FILE_SIZE_VALUE * 1024 * 1024) + 1),
				RandomTestUtil.randomString() + ".txt", null, null),
			null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE);
		_testPostCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				"status", "BAD_REQUEST"
			).put(
				"title", "File name is null"
			),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(), null, null,
				null),
			null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE);
		_testPostCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				"status", "BAD_REQUEST"
			).put(
				"title", "Invalid file extension for " + fileEntry.getName()
			),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".err", null, null),
			null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE);
		_testPostCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				"status", "BAD_REQUEST"
			).put(
				"title", "Unable to decode Base64 file"
			),
			_toFileEntry(
				String::new, RandomTestUtil.randomString(7),
				RandomTestUtil.randomString() + ".txt", null, null),
			null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE);

		// File with a nonexistent name

		_testPostCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE,
				JSONUtil.put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						_getDLFolder(objectDefinition),
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".txt", null, null),
			null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE);
		_testPostCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE,
				JSONUtil.put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						_getDLFolder(objectDefinition),
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".txt",
				RandomTestUtil.randomString(), RandomTestUtil.randomLong()),
			null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE);

		// File with a nonexistent name and the Base64 content as a nested field

		_testPostCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE,
				JSONUtil.put(
					"fileBase64", fileEntry.getFileBase64()
				).put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						_getDLFolder(objectDefinition),
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".txt", null, null),
			"fileBase64", objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE);

		// File with an existing name

		com.liferay.object.rest.dto.v1_0.FileEntry testFileEntry = _toFileEntry(
			Base64::encode, RandomTestUtil.randomString(),
			RandomTestUtil.randomString() + ".txt", null, null);

		_testPostCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE,
				JSONUtil.put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						_getDLFolder(objectDefinition),
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			testFileEntry, null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE);
		_testPostCustomObjectEntryWithAttachmentField(
			fileEntry -> {
				String newName = StringUtil.replace(
					fileEntry.getName(), ".txt", " (1).txt");

				return JSONUtil.put(
					_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE,
					JSONUtil.put(
						"id", _testDLFileEntryModelListener.getLastFileEntryId()
					).put(
						"link",
						_getLinkJSONObject(
							_getDLFolder(objectDefinition),
							_testDLFileEntryModelListener.getLastFileEntryId(),
							newName, objectDefinition)
					).put(
						"name", newName
					));
			},
			testFileEntry, null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE);
	}

	private void _testPostCustomObjectEntryWithAttachmentField(
			UnsafeFunction
				<com.liferay.object.rest.dto.v1_0.FileEntry, JSONObject,
				 Exception> expectedJSONObjectUnsafeFunction,
			com.liferay.object.rest.dto.v1_0.FileEntry fileEntry,
			String nestedFields, ObjectDefinition objectDefinition,
			String objectFieldName)
		throws Exception {

		String endpoint = _getEndpoint(
			TestPropsValues.getGroupId(), objectDefinition);

		if (nestedFields != null) {
			endpoint = StringBundler.concat(
				endpoint, "?nestedFields=", objectFieldName, ".", nestedFields);
		}

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
			).put(
				objectFieldName,
				JSONFactoryUtil.createJSONObject(fileEntry.toString())
			).toString(),
			endpoint, Http.Method.POST);

		_assertJSONObjectWithAttachmentField(
			expectedJSONObjectUnsafeFunction.apply(fileEntry), jsonObject,
			objectFieldName);
	}

	private void
			_testPostCustomObjectEntryWithInvalidNestedCustomObjectEntriesInManyToManyRelationship(
				String objectDefinitionRESTContextPath,
				ObjectRelationship objectRelationship)
		throws Exception {

		JSONObject objectEntryJSONObject = JSONUtil.put(
			objectRelationship.getName(),
			JSONFactoryUtil.createJSONObject(
				JSONUtil.put(
					_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_2
				).put(
					"externalReferenceCode", _ERC_VALUE_2
				).toString()));

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			objectEntryJSONObject.toString(), objectDefinitionRESTContextPath,
			Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
	}

	private void
			_testPostCustomObjectEntryWithInvalidNestedCustomObjectEntriesInManyToOneRelationship(
				String objectDefinitionRESTContextPath,
				ObjectRelationship objectRelationship)
		throws Exception {

		JSONObject objectEntryJSONObject = JSONUtil.put(
			objectRelationship.getName(),
			_createObjectEntriesJSONArray(
				new String[] {_ERC_VALUE_1, _ERC_VALUE_2}, _OBJECT_FIELD_NAME_1,
				new String[] {
					RandomTestUtil.randomString(), RandomTestUtil.randomString()
				}));

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			objectEntryJSONObject.toString(), objectDefinitionRESTContextPath,
			Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
	}

	private void
			_testPostCustomObjectEntryWithInvalidNestedCustomObjectEntriesInOneToManyRelationship(
				String objectDefinitionRESTContextPath,
				ObjectRelationship objectRelationship)
		throws Exception {

		JSONObject objectEntryJSONObject = JSONUtil.put(
			objectRelationship.getName(),
			JSONFactoryUtil.createJSONObject(
				JSONUtil.put(
					_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_2
				).put(
					"externalReferenceCode", _ERC_VALUE_2
				).toString()));

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			objectEntryJSONObject.toString(), objectDefinitionRESTContextPath,
			Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
	}

	private void _testPutCustomObjectEntryUnlinkNestedCustomObjectEntries(
			boolean manyToOne)
		throws Exception {

		JSONObject objectEntryJSONObject = JSONUtil.put(
			_objectRelationship1.getName(),
			() -> {
				if (manyToOne) {
					return JSONFactoryUtil.createJSONObject(
						JSONUtil.put(
							_OBJECT_FIELD_NAME_2, RandomTestUtil.randomString()
						).put(
							"externalReferenceCode", _ERC_VALUE_1
						).toString());
				}

				return _createObjectEntriesJSONArray(
					new String[] {_ERC_VALUE_1, _ERC_VALUE_2},
					_OBJECT_FIELD_NAME_2,
					new String[] {
						RandomTestUtil.randomString(),
						RandomTestUtil.randomString()
					});
			});

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			objectEntryJSONObject.toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		JSONObject newObjectEntryJSONObject = JSONUtil.put(
			_objectRelationship1.getName(),
			() -> {
				if (manyToOne) {
					return JSONFactoryUtil.createJSONObject();
				}

				return JSONFactoryUtil.createJSONArray();
			});

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			newObjectEntryJSONObject.toString(),
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				jsonObject.getString("id")),
			Http.Method.PUT);

		Assert.assertEquals(
			0,
			jsonObject.getJSONObject(
				"status"
			).get(
				"code"
			));

		if (manyToOne) {
			JSONObject systemObjectEntryJSONObject = jsonObject.getJSONObject(
				_objectRelationship1.getName());

			Assert.assertNull(systemObjectEntryJSONObject);
		}
		else {
			JSONArray nestedObjectEntriesJSONArray = jsonObject.getJSONArray(
				_objectRelationship1.getName());

			Assert.assertEquals(0, nestedObjectEntriesJSONArray.length());
		}
	}

	private void
			_testPutCustomObjectEntryUnlinkNestedCustomObjectEntriesByExternalReferenceCode(
				boolean manyToOne)
		throws Exception {

		JSONObject objectEntryJSONObject = JSONUtil.put(
			_objectRelationship1.getName(),
			() -> {
				if (manyToOne) {
					return JSONFactoryUtil.createJSONObject(
						JSONUtil.put(
							_OBJECT_FIELD_NAME_2, RandomTestUtil.randomString()
						).put(
							"externalReferenceCode", _ERC_VALUE_1
						).toString());
				}

				return _createObjectEntriesJSONArray(
					new String[] {_ERC_VALUE_1, _ERC_VALUE_2},
					_OBJECT_FIELD_NAME_2,
					new String[] {
						RandomTestUtil.randomString(),
						RandomTestUtil.randomString()
					});
			});

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			objectEntryJSONObject.toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		JSONObject newObjectEntryJSONObject = JSONUtil.put(
			_objectRelationship1.getName(),
			() -> {
				if (manyToOne) {
					return JSONFactoryUtil.createJSONObject();
				}

				return JSONFactoryUtil.createJSONArray();
			});

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			newObjectEntryJSONObject.toString(),
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(),
				"/by-external-reference-code/",
				jsonObject.getString("externalReferenceCode")),
			Http.Method.PUT);

		Assert.assertEquals(
			0,
			jsonObject.getJSONObject(
				"status"
			).get(
				"code"
			));

		if (manyToOne) {
			JSONObject systemObjectEntryJSONObject = jsonObject.getJSONObject(
				_objectRelationship1.getName());

			Assert.assertNull(systemObjectEntryJSONObject);
		}
		else {
			JSONArray nestedObjectEntriesJSONArray = jsonObject.getJSONArray(
				_objectRelationship1.getName());

			Assert.assertEquals(0, nestedObjectEntriesJSONArray.length());
		}
	}

	private void
			_testPutCustomObjectEntryWithNestedCustomObjectEntriesByExternalReferenceCode(
				boolean manyToOne)
		throws Exception {

		JSONObject objectEntryJSONObject = JSONUtil.put(
			_objectRelationship1.getName(),
			() -> {
				if (manyToOne) {
					return JSONFactoryUtil.createJSONObject(
						JSONUtil.put(
							_OBJECT_FIELD_NAME_2, RandomTestUtil.randomString()
						).put(
							"externalReferenceCode", _ERC_VALUE_1
						).toString());
				}

				return _createObjectEntriesJSONArray(
					new String[] {_ERC_VALUE_1, _ERC_VALUE_2},
					_OBJECT_FIELD_NAME_2,
					new String[] {
						RandomTestUtil.randomString(),
						RandomTestUtil.randomString()
					});
			});

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			objectEntryJSONObject.toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		JSONObject newObjectEntryJSONObject = JSONUtil.put(
			_objectRelationship1.getName(),
			() -> {
				if (manyToOne) {
					return JSONFactoryUtil.createJSONObject(
						JSONUtil.put(
							_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_1
						).put(
							"externalReferenceCode", _ERC_VALUE_1
						).toString());
				}

				return _createObjectEntriesJSONArray(
					new String[] {_ERC_VALUE_1}, _OBJECT_FIELD_NAME_2,
					new String[] {_NEW_OBJECT_FIELD_VALUE_1});
			});

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			newObjectEntryJSONObject.toString(),
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(),
				"/by-external-reference-code/",
				jsonObject.getString("externalReferenceCode")),
			Http.Method.PUT);

		Assert.assertEquals(
			0,
			jsonObject.getJSONObject(
				"status"
			).get(
				"code"
			));

		if (manyToOne) {
			_assertObjectEntryField(
				jsonObject.getJSONObject(
					StringBundler.concat(
						"r_", _objectRelationship1.getName(), "_",
						StringUtil.replaceLast(
							_objectDefinition2.getPKObjectFieldName(), "Id",
							""))),
				_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_1);
		}
		else {
			JSONArray nestedObjectEntriesJSONArray = jsonObject.getJSONArray(
				_objectRelationship1.getName());

			Assert.assertEquals(1, nestedObjectEntriesJSONArray.length());

			_assertObjectEntryField(
				(JSONObject)nestedObjectEntriesJSONArray.get(0),
				_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_1);
		}
	}

	private JSONObject _toEmbeddedTaxonomyCategoryJSONObject(
			TaxonomyCategory taxonomyCategory)
		throws Exception {

		TaxonomyCategory embeddedTaxonomyCategory = taxonomyCategory.clone();

		embeddedTaxonomyCategory.setActions(
			HashMapBuilder.<String, Map<String, String>>put(
				"add-category",
				HashMapBuilder.put(
					"href",
					StringBundler.concat(
						"http://localhost:8080/o/headless-admin-taxonomy/v1.0",
						"/taxonomy-categories/", taxonomyCategory.getId(),
						"/taxonomy-categories")
				).put(
					"method", "POST"
				).build()
			).put(
				"delete",
				HashMapBuilder.put(
					"href",
					StringBundler.concat(
						"http://localhost:8080/o/headless-admin-taxonomy/v1.0",
						"/taxonomy-categories/", taxonomyCategory.getId())
				).put(
					"method", "DELETE"
				).build()
			).put(
				"get",
				HashMapBuilder.put(
					"href",
					StringBundler.concat(
						"http://localhost:8080/o/headless-admin-taxonomy/v1.0",
						"/taxonomy-categories/", taxonomyCategory.getId())
				).put(
					"method", "GET"
				).build()
			).put(
				"replace",
				HashMapBuilder.put(
					"href",
					StringBundler.concat(
						"http://localhost:8080/o/headless-admin-taxonomy/v1.0",
						"/taxonomy-categories/", taxonomyCategory.getId())
				).put(
					"method", "PUT"
				).build()
			).put(
				"update",
				HashMapBuilder.put(
					"href",
					StringBundler.concat(
						"http://localhost:8080/o/headless-admin-taxonomy/v1.0",
						"/taxonomy-categories/", taxonomyCategory.getId())
				).put(
					"method", "PATCH"
				).build()
			).build());

		return JSONFactoryUtil.createJSONObject(
			embeddedTaxonomyCategory.toString());
	}

	private com.liferay.object.rest.dto.v1_0.FileEntry _toFileEntry(
		Function<byte[], String> encodeFunction, String fileContent,
		String fileName, String folderExternalReferenceCode,
		Long folderSiteId) {

		com.liferay.object.rest.dto.v1_0.FileEntry fileEntry =
			new com.liferay.object.rest.dto.v1_0.FileEntry();

		fileEntry.setFileBase64(encodeFunction.apply(fileContent.getBytes()));
		fileEntry.setName(fileName);

		if ((folderExternalReferenceCode != null) || (folderSiteId != null)) {
			Folder folder = new Folder();

			folder.setExternalReferenceCode(folderExternalReferenceCode);
			folder.setSiteId(folderSiteId);

			fileEntry.setFolder(folder);
		}

		return fileEntry;
	}

	private JSONObject _toFileEntryJSONObject(
			String fileContent, String fileName, String objectFieldName)
		throws Exception {

		com.liferay.object.rest.dto.v1_0.FileEntry fileEntry =
			new com.liferay.object.rest.dto.v1_0.FileEntry();

		fileEntry.setFileBase64(Base64.encode(fileContent.getBytes()));

		if (StringUtil.equals(
				objectFieldName,
				_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE)) {

			Folder folder = new Folder();

			folder.setSiteId(TestPropsValues.getGroupId());

			fileEntry.setFolder(folder);
		}

		fileEntry.setName(fileName);

		return JSONFactoryUtil.createJSONObject(fileEntry.toString());
	}

	private static final String _ERC_VALUE_1 = RandomTestUtil.randomString();

	private static final String _ERC_VALUE_2 = RandomTestUtil.randomString();

	private static final String _ERC_VALUE_3 = RandomTestUtil.randomString();

	private static final String _LIST_TYPE_ENTRY_KEY =
		"x" + RandomTestUtil.randomString();

	private static final int _MAX_FILE_SIZE_VALUE = 1;

	private static final String _NEW_OBJECT_FIELD_VALUE_1 =
		RandomTestUtil.randomString();

	private static final String _NEW_OBJECT_FIELD_VALUE_2 =
		RandomTestUtil.randomString();

	private static final String _NEW_OBJECT_FIELD_VALUE_3 =
		RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_1 =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_2 =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_3 =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_4 =
		"x" + RandomTestUtil.randomString();

	private static final String
		_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE =
			"x" + RandomTestUtil.randomString();

	private static final String
		_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE =
			"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_MULTISELECT_PICKLIST =
		"x" + RandomTestUtil.randomString();

	private static final int _OBJECT_FIELD_VALUE_1 = RandomTestUtil.randomInt();

	private static final int _OBJECT_FIELD_VALUE_2 = RandomTestUtil.randomInt();

	private static final int _OBJECT_FIELD_VALUE_3 = RandomTestUtil.randomInt();

	private static final int _OBJECT_FIELD_VALUE_4 = RandomTestUtil.randomInt();

	private static final String _TAG_1 = StringUtil.toLowerCase(
		RandomTestUtil.randomString());

	private static AssetVocabulary _assetVocabulary;
	private static BundleContext _bundleContext;
	private static List<ServiceRegistration<?>> _serviceRegistrations;
	private static TaxonomyCategoryResource _taxonomyCategoryResource;
	private static final TestDLFileEntryModelListener
		_testDLFileEntryModelListener = new TestDLFileEntryModelListener();
	private static final TestObjectEntryModelListener
		_testObjectEntryModelListener = new TestObjectEntryModelListener();

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private DLFolderLocalService _dlFolderLocalService;

	private Group _group;
	private ListTypeDefinition _listTypeDefinition;

	@Inject
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Inject
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Inject
	private ObjectActionLocalService _objectActionLocalService;

	private ObjectDefinition _objectDefinition1;
	private ObjectDefinition _objectDefinition2;
	private ObjectDefinition _objectDefinition3;
	private ObjectDefinition _objectDefinition4;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private ObjectEntry _objectEntry1;
	private ObjectEntry _objectEntry2;
	private ObjectEntry _objectEntry3;
	private ObjectEntry _objectEntry4;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	private ObjectRelationship _objectRelationship1;
	private ObjectRelationship _objectRelationship2;
	private ObjectRelationship _objectRelationship3;
	private ObjectRelationship _objectRelationship4;
	private ObjectRelationship _objectRelationship5;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Inject
	private ObjectScopeProviderRegistry _objectScopeProviderRegistry;

	@Inject
	private PortletFileRepository _portletFileRepository;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	private ObjectDefinition _siteScopedObjectDefinition1;
	private ObjectEntry _siteScopedObjectEntry1;

	@Inject
	private SystemObjectDefinitionManagerRegistry
		_systemObjectDefinitionManagerRegistry;

	private JSONObject _userAccountJSONObject;
	private ObjectDefinition _userSystemObjectDefinition;

	@DeleteAfterTestRun
	private ObjectField _userSystemObjectField;

	private static class TestDLFileEntryModelListener
		extends BaseModelListener<DLFileEntry> {

		public Long getLastFileEntryId() {
			return _fileEntryIds.get(_fileEntryIds.size() - 1);
		}

		@Override
		public void onAfterCreate(DLFileEntry dlFileEntry)
			throws ModelListenerException {

			_fileEntryIds.add(dlFileEntry.getFileEntryId());
		}

		private List<Long> _fileEntryIds = new ArrayList<>();

	}

	private static class TestObjectEntryModelListener
		extends BaseModelListener<ObjectEntry> {

		public Long getLastObjectEntryId() {
			return _objectEntryIds.get(_objectEntryIds.size() - 1);
		}

		@Override
		public void onAfterCreate(ObjectEntry objectEntry)
			throws ModelListenerException {

			_objectEntryIds.add(objectEntry.getObjectEntryId());
		}

		private List<Long> _objectEntryIds = new ArrayList<>();

	}

	private enum Type {

		MANY_TO_MANY, MANY_TO_ONE, ONE_TO_MANY

	}

}