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
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.document.library.test.util.DLTestUtil;
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
import com.liferay.object.constants.ObjectFieldValidationConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.field.builder.LongTextObjectFieldBuilder;
import com.liferay.object.field.builder.RichTextObjectFieldBuilder;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
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
import com.liferay.object.rest.test.util.ObjectEntryTestUtil;
import com.liferay.object.rest.test.util.ObjectFieldTestUtil;
import com.liferay.object.rest.test.util.ObjectRelationshipTestUtil;
import com.liferay.object.rest.test.util.UserAccountTestUtil;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.object.system.SystemObjectDefinitionManagerRegistry;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.function.UnsafeTriConsumer;
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
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
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
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.ModelPermissionsFactory;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
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
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
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
import com.liferay.portlet.documentlibrary.constants.DLConstants;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.math.BigDecimal;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Feature;

import org.hamcrest.CoreMatchers;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.ComparisonFailure;
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
			"test@liferay.com", PropsValues.DEFAULT_ADMIN_PASSWORD
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
			_listTypeDefinition.getListTypeDefinitionId(),
			_LIST_TYPE_ENTRY_KEY_1,
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()));
		_listTypeEntryLocalService.addListTypeEntry(
			null, TestPropsValues.getUserId(),
			_listTypeDefinition.getListTypeDefinitionId(),
			_LIST_TYPE_ENTRY_KEY_2,
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()));
		_listTypeEntryLocalService.addListTypeEntry(
			null, TestPropsValues.getUserId(),
			_listTypeDefinition.getListTypeDefinitionId(),
			_LIST_TYPE_ENTRY_KEY_3,
			Collections.singletonMap(
				LocaleUtil.US, RandomTestUtil.randomString()));

		String objectDefinitionName = ObjectDefinitionTestUtil.getRandomName();

		_objectDefinition1 = ObjectDefinitionTestUtil.publishObjectDefinition(
			objectDefinitionName,
			Arrays.asList(
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
					_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1,
					_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1,
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
					ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT,
					ObjectFieldConstants.DB_TYPE_LONG, true, false, null,
					_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_2,
					_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_2,
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
						).build(),
						new ObjectFieldSettingBuilder(
						).name(
							ObjectFieldSettingConstants.
								NAME_SHOW_FILES_IN_DOCS_AND_MEDIA
						).value(
							Boolean.TRUE.toString()
						).build(),
						new ObjectFieldSettingBuilder(
						).name(
							ObjectFieldSettingConstants.
								NAME_STORAGE_DL_FOLDER_PATH
						).value(
							StringPool.SLASH + objectDefinitionName
						).build()),
					false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_BOOLEAN,
					ObjectFieldConstants.DB_TYPE_BOOLEAN, true, false, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_BOOLEAN,
					false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_DATE,
					ObjectFieldConstants.DB_TYPE_DATE,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_DATE,
					false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_DATE_TIME,
					ObjectFieldConstants.DB_TYPE_DATE_TIME, true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_DATE_TIME,
					Collections.singletonList(
						new ObjectFieldSettingBuilder(
						).name(
							ObjectFieldSettingConstants.NAME_TIME_STORAGE
						).value(
							ObjectFieldSettingConstants.
								VALUE_USE_INPUT_AS_ENTERED
						).build()),
					false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_DECIMAL,
					ObjectFieldConstants.DB_TYPE_DOUBLE, true, false, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_DECIMAL,
					false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_INTEGER,
					ObjectFieldConstants.DB_TYPE_INTEGER, true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_INTEGER,
					false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER,
					ObjectFieldConstants.DB_TYPE_LONG, true, false, null,
					RandomTestUtil.randomString(),
					_OBJECT_FIELD_NAME_LONG_INTEGER, false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_LONG_TEXT,
					ObjectFieldConstants.DB_TYPE_CLOB, false, false, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_LONG_TEXT,
					false),
				ObjectFieldUtil.createObjectField(
					_listTypeDefinition.getListTypeDefinitionId(),
					ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST,
					null, ObjectFieldConstants.DB_TYPE_STRING, true, false,
					null, RandomTestUtil.randomString(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST, false, false),
				ObjectFieldUtil.createObjectField(
					_listTypeDefinition.getListTypeDefinitionId(),
					ObjectFieldConstants.BUSINESS_TYPE_PICKLIST, null,
					ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_PICKLIST,
					false, false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_PRECISION_DECIMAL,
					ObjectFieldConstants.DB_TYPE_BIG_DECIMAL, true, false, null,
					RandomTestUtil.randomString(),
					_OBJECT_FIELD_NAME_PRECISION_DECIMAL, false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_TEXT,
					false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_1,
					false)),
			ObjectDefinitionConstants.SCOPE_COMPANY);

		_objectDefinition2 = ObjectDefinitionTestUtil.publishObjectDefinition(
			ObjectDefinitionTestUtil.getRandomName(),
			Arrays.asList(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_BOOLEAN,
					ObjectFieldConstants.DB_TYPE_BOOLEAN, true, false, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_BOOLEAN,
					false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_DATE,
					ObjectFieldConstants.DB_TYPE_DATE,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_DATE,
					false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_DATE_TIME,
					ObjectFieldConstants.DB_TYPE_DATE_TIME, true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_DATE_TIME,
					Collections.singletonList(
						new ObjectFieldSettingBuilder(
						).name(
							ObjectFieldSettingConstants.NAME_TIME_STORAGE
						).value(
							ObjectFieldSettingConstants.
								VALUE_USE_INPUT_AS_ENTERED
						).build()),
					false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_DECIMAL,
					ObjectFieldConstants.DB_TYPE_DOUBLE, true, false, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_DECIMAL,
					false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_INTEGER,
					ObjectFieldConstants.DB_TYPE_INTEGER, true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_INTEGER,
					false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER,
					ObjectFieldConstants.DB_TYPE_LONG, true, false, null,
					RandomTestUtil.randomString(),
					_OBJECT_FIELD_NAME_LONG_INTEGER, false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_LONG_TEXT,
					ObjectFieldConstants.DB_TYPE_CLOB, false, false, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_LONG_TEXT,
					false),
				ObjectFieldUtil.createObjectField(
					_listTypeDefinition.getListTypeDefinitionId(),
					ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST,
					null, ObjectFieldConstants.DB_TYPE_STRING, true, false,
					null, RandomTestUtil.randomString(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST, false, false),
				ObjectFieldUtil.createObjectField(
					_listTypeDefinition.getListTypeDefinitionId(),
					ObjectFieldConstants.BUSINESS_TYPE_PICKLIST, null,
					ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_PICKLIST,
					false, false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_PRECISION_DECIMAL,
					ObjectFieldConstants.DB_TYPE_BIG_DECIMAL, true, false, null,
					RandomTestUtil.randomString(),
					_OBJECT_FIELD_NAME_PRECISION_DECIMAL, false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_TEXT,
					false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_2,
					false)),
			ObjectDefinitionConstants.SCOPE_COMPANY);
		_objectDefinition3 = ObjectDefinitionTestUtil.publishObjectDefinition(
			ObjectDefinitionTestUtil.getRandomName(),
			Arrays.asList(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_BOOLEAN,
					ObjectFieldConstants.DB_TYPE_BOOLEAN, true, false, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_BOOLEAN,
					false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_DATE,
					ObjectFieldConstants.DB_TYPE_DATE,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_DATE,
					false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_DATE_TIME,
					ObjectFieldConstants.DB_TYPE_DATE_TIME, true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_DATE_TIME,
					Collections.singletonList(
						new ObjectFieldSettingBuilder(
						).name(
							ObjectFieldSettingConstants.NAME_TIME_STORAGE
						).value(
							ObjectFieldSettingConstants.
								VALUE_USE_INPUT_AS_ENTERED
						).build()),
					false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_DECIMAL,
					ObjectFieldConstants.DB_TYPE_DOUBLE, true, false, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_DECIMAL,
					false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_INTEGER,
					ObjectFieldConstants.DB_TYPE_INTEGER, true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_INTEGER,
					false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER,
					ObjectFieldConstants.DB_TYPE_LONG, true, false, null,
					RandomTestUtil.randomString(),
					_OBJECT_FIELD_NAME_LONG_INTEGER, false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_LONG_TEXT,
					ObjectFieldConstants.DB_TYPE_CLOB, false, false, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_LONG_TEXT,
					false),
				ObjectFieldUtil.createObjectField(
					_listTypeDefinition.getListTypeDefinitionId(),
					ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST,
					null, ObjectFieldConstants.DB_TYPE_STRING, true, false,
					null, RandomTestUtil.randomString(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST, false, false),
				ObjectFieldUtil.createObjectField(
					_listTypeDefinition.getListTypeDefinitionId(),
					ObjectFieldConstants.BUSINESS_TYPE_PICKLIST, null,
					ObjectFieldConstants.DB_TYPE_STRING, true, false, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_PICKLIST,
					false, false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_PRECISION_DECIMAL,
					ObjectFieldConstants.DB_TYPE_BIG_DECIMAL, true, false, null,
					RandomTestUtil.randomString(),
					_OBJECT_FIELD_NAME_PRECISION_DECIMAL, false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_TEXT,
					false),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_3,
					false)),
			ObjectDefinitionConstants.SCOPE_COMPANY);

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

		_objectDefinition5 = ObjectDefinitionTestUtil.publishObjectDefinition(
			true, ObjectDefinitionTestUtil.getRandomName(),
			Arrays.asList(
				new TextObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).localized(
					true
				).name(
					_OBJECT_FIELD_NAME_TEXT
				).build(),
				new LongTextObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).localized(
					true
				).name(
					_OBJECT_FIELD_NAME_LONG_TEXT
				).build(),
				new RichTextObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).localized(
					true
				).name(
					_OBJECT_FIELD_NAME_RICH_TEXT
				).build()),
			ObjectDefinitionConstants.SCOPE_COMPANY,
			TestPropsValues.getUserId());

		_objectEntry5 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition5,
			HashMapBuilder.<String, Serializable>put(
				_OBJECT_FIELD_NAME_LONG_TEXT, "name2_text_english"
			).put(
				_OBJECT_FIELD_NAME_LONG_TEXT + "_i18n",
				HashMapBuilder.<String, Serializable>put(
					"en_US", "longTextEng"
				).put(
					"es_ES", "longTextEsp"
				).build()
			).put(
				_OBJECT_FIELD_NAME_RICH_TEXT, "<p>c</p>\\n"
			).put(
				_OBJECT_FIELD_NAME_RICH_TEXT + "_i18n",
				HashMapBuilder.<String, Serializable>put(
					"en_US", "<p>richTextEng</p>"
				).put(
					"es_ES", "<p>richTextEsp</p>"
				).build()
			).put(
				_OBJECT_FIELD_NAME_TEXT, "name1_text_english"
			).put(
				_OBJECT_FIELD_NAME_TEXT + "_i18n",
				HashMapBuilder.<String, Serializable>put(
					"en_US", "textEng"
				).put(
					"es_ES", "textEsp"
				).build()
			).build());

		_objectDefinition6 = ObjectDefinitionTestUtil.publishObjectDefinition(
			Collections.singletonList(
				ObjectFieldUtil.createObjectField(
					"Text", "String", true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_TEXT,
					false)));

		objectDefinitionName = ObjectDefinitionTestUtil.getRandomName();

		_siteScopedObjectDefinition1 =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				objectDefinitionName,
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
						_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1,
						_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1,
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
						ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT,
						ObjectFieldConstants.DB_TYPE_LONG, true, false, null,
						_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_2,
						_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_2,
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
							).build(),
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.
									NAME_SHOW_FILES_IN_DOCS_AND_MEDIA
							).value(
								Boolean.TRUE.toString()
							).build(),
							new ObjectFieldSettingBuilder(
							).name(
								ObjectFieldSettingConstants.
									NAME_STORAGE_DL_FOLDER_PATH
							).value(
								StringPool.SLASH + objectDefinitionName
							).build()),
						false)),
				ObjectDefinitionConstants.SCOPE_SITE);

		_siteScopedObjectDefinition2 =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
						RandomTestUtil.randomString(), _OBJECT_FIELD_NAME_2,
						false)),
				ObjectDefinitionConstants.SCOPE_SITE);

		_systemObjectDefinitionManager =
			_systemObjectDefinitionManagerRegistry.
				getSystemObjectDefinitionManager("User");

		_userSystemObjectDefinition =
			_objectDefinitionLocalService.fetchSystemObjectDefinition(
				_systemObjectDefinitionManager.getName());

		_userSystemObjectField = ObjectFieldTestUtil.addCustomObjectField(
			TestPropsValues.getUserId(),
			ObjectFieldConstants.BUSINESS_TYPE_TEXT,
			ObjectFieldConstants.DB_TYPE_STRING, _userSystemObjectDefinition,
			_OBJECT_FIELD_NAME_2);
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

		if (_objectRelationship6 != null) {
			_objectRelationshipLocalService.deleteObjectRelationship(
				_objectRelationship6);
		}

		if (_objectRelationship7 != null) {
			_objectRelationshipLocalService.deleteObjectRelationship(
				_objectRelationship7);
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
			_objectDefinition5);
		_objectDefinitionLocalService.deleteObjectDefinition(
			_objectDefinition6);
		_objectDefinitionLocalService.deleteObjectDefinition(
			_siteScopedObjectDefinition1);
		_objectDefinitionLocalService.deleteObjectDefinition(
			_siteScopedObjectDefinition2);

		_listTypeDefinitionLocalService.deleteListTypeDefinition(
			_listTypeDefinition);
	}

	@Test
	public void testFilterByComparisonOperatorsObjectEntriesByRelatedObjectEntriesFields()
		throws Exception {

		// Many to many relationship, custom object field

		_objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition1, _OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1);
		_objectEntry2 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition2, _OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2);

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

		// Many to many self relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition1, _objectDefinition1,
			_objectEntry1.getPrimaryKey(), _objectEntry1.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s eq '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s eq '%s'", _objectRelationship1.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s ge '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s ge '%s'", _objectRelationship1.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s gt '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s gt '%s'", _objectRelationship1.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s le '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s le '%s'", _objectRelationship1.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s lt '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 + 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s lt '%s'", _objectRelationship1.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 + 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s ne '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s ne '%s'", _objectRelationship1.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition1);

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
					StringUtil.removeFirst(
						_objectDefinition1.getPKObjectFieldName(), "c_"),
					_objectEntry1.getPrimaryKey())),
			_objectDefinition1);
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

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship1);

		// One to many self relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition1, _objectDefinition1,
			_objectEntry1.getPrimaryKey(), _objectEntry1.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s eq '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s eq '%s'", _objectRelationship1.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s ge '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s ge '%s'", _objectRelationship1.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s gt '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s gt '%s'", _objectRelationship1.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s le '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s le '%s'", _objectRelationship1.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s lt '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 + 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s lt '%s'", _objectRelationship1.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 + 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s ne '%s'", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s ne '%s'", _objectRelationship1.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition1);
	}

	@Test
	public void testFilterByComparisonOperatorsObjectEntriesByRelatedObjectEntriesFieldsThroughMultipleRelationships()
		throws Exception {

		// Many to many relationship, custom object field

		_objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition1, _OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1);
		_objectEntry2 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition2, _OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2);
		_objectEntry3 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition3, _OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3);
		_objectEntry4 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition4, _OBJECT_FIELD_NAME_4, _OBJECT_FIELD_VALUE_4);

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

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship1);
		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship2);

		// One to many relationships with two paths to the same destination

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition1, _objectDefinition2,
			_objectEntry1.getPrimaryKey(), _objectEntry2.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_objectRelationship2 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition2, _objectDefinition3,
			_objectEntry2.getPrimaryKey(), _objectEntry3.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_objectRelationship3 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition3, _objectDefinition4,
			_objectEntry3.getPrimaryKey(), _objectEntry4.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_objectRelationship4 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition2, _objectDefinition3,
			_objectEntry2.getPrimaryKey(), _objectEntry3.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s/%s eq '%s'", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_objectRelationship3.getName(), _OBJECT_FIELD_NAME_4,
					_OBJECT_FIELD_VALUE_4)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s/%s eq '%s'", _objectRelationship1.getName(),
					_objectRelationship4.getName(),
					_objectRelationship3.getName(), _OBJECT_FIELD_NAME_4,
					_OBJECT_FIELD_VALUE_4)),
			_objectDefinition1);
	}

	@Test
	public void testFilterByGroupingOperatorsObjectEntriesByRelatedObjectEntriesFields()
		throws Exception {

		// Many to many relationship, custom object field

		_objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition1, _OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1);
		_objectEntry2 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition2, _OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2);

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

		// Many to many self relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition1, _objectDefinition1,
			_objectEntry1.getPrimaryKey(), _objectEntry1.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"(%s/%s le '%s') and (%s/%s gt '%s')",
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"(%s/%s/%s le '%s') and (%s/%s/%s gt '%s')",
					_objectRelationship1.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, _objectRelationship1.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition1);

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

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship1);

		// One to many self relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition1, _objectDefinition1,
			_objectEntry1.getPrimaryKey(), _objectEntry1.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"(%s/%s le '%s') and (%s/%s gt '%s')",
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"(%s/%s/%s le '%s') and (%s/%s/%s gt '%s')",
					_objectRelationship1.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, _objectRelationship1.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition1);
	}

	@Test
	public void testFilterByGroupingOperatorsObjectEntriesByRelatedObjectEntriesFieldsThroughMultipleRelationships()
		throws Exception {

		// Many to many relationship, custom object field

		_objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition1, _OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1);
		_objectEntry2 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition2, _OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2);
		_objectEntry3 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition3, _OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3);

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
	public void testFilterByLambdaOperatorsObjectEntriesByRelatedObjectEntriesFields()
		throws Exception {

		_objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition1,
			HashMapBuilder.<String, Serializable>put(
				_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1
			).put(
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				(Serializable)Arrays.asList(
					_LIST_TYPE_ENTRY_KEY_1, _LIST_TYPE_ENTRY_KEY_2)
			).build(),
			_TAG_1);
		_objectEntry2 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition2,
			HashMapBuilder.<String, Serializable>put(
				_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2
			).put(
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				(Serializable)Arrays.asList(
					_LIST_TYPE_ENTRY_KEY_1, _LIST_TYPE_ENTRY_KEY_2)
			).build(),
			_TAG_2);

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
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/any(k:k eq '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/any(k:k in ('%s', '%s'))",
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1, RandomTestUtil.randomString())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/any(k:k ne '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/any(k:startswith(k,'%s'))",
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition1);

		// Many to many relationship, system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/keywords/any(k:contains(k,'%s'))",
					_objectRelationship1.getName(), _TAG_2.substring(1))),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/keywords/any(k:k eq '%s')",
					_objectRelationship1.getName(), _TAG_2)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/keywords/any(k:k in ('%s', '%s'))",
					_objectRelationship1.getName(), _TAG_2,
					RandomTestUtil.randomString())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/keywords/any(k:k ne '%s')",
					_objectRelationship1.getName(), _TAG_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/keywords/any(k:startswith(k,'%s'))",
					_objectRelationship1.getName(), _TAG_2.substring(0, 2))),
			_objectDefinition1);

		// Many to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s/any(k:contains(k,'%s'))",
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s/any(k:k eq '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s/any(k:k in ('%s', '%s'))",
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1, RandomTestUtil.randomString())),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s/any(k:k ne '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s/any(k:startswith(k,'%s'))",
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
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
					"%s/keywords/any(k:k ne '%s')",
					_objectRelationship1.getName(), _TAG_2)),
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

		// Many to many self relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition1, _objectDefinition1,
			_objectEntry1.getPrimaryKey(), _objectEntry1.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/any(k:contains(k,'%s'))",
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s/any(k:contains(k,'%s'))",
					_objectRelationship1.getName(),
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/any(k:k eq '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s/any(k:k eq '%s')", _objectRelationship1.getName(),
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/any(k:k in ('%s', '%s'))",
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1, RandomTestUtil.randomString())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s/any(k:k in ('%s', '%s'))",
					_objectRelationship1.getName(),
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1, RandomTestUtil.randomString())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/any(k:k ne '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s/any(k:k ne '%s')", _objectRelationship1.getName(),
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/any(k:startswith(k,'%s'))",
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s/any(k:startswith(k,'%s'))",
					_objectRelationship1.getName(),
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition1);

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
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/any(k:k eq '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/any(k:k in ('%s', '%s'))",
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1, RandomTestUtil.randomString())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/any(k:k ne '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/any(k:startswith(k,'%s'))",
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition1);

		// One to many relationship, system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/keywords/any(k:contains(k,'%s'))",
					_objectRelationship1.getName(), _TAG_2.substring(1))),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/keywords/any(k:k eq '%s')",
					_objectRelationship1.getName(), _TAG_2)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/keywords/any(k:k in ('%s', '%s'))",
					_objectRelationship1.getName(), _TAG_2,
					RandomTestUtil.randomString())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/keywords/any(k:k ne '%s')",
					_objectRelationship1.getName(), _TAG_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/keywords/any(k:startswith(k,'%s'))",
					_objectRelationship1.getName(), _TAG_2.substring(0, 2))),
			_objectDefinition1);

		// One to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s/any(k:contains(k,'%s'))",
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s/any(k:k eq '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s/any(k:k in ('%s', '%s'))",
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1, RandomTestUtil.randomString())),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s/any(k:k ne '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition2);
		_assertFilterString(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2,
			_escape(
				String.format(
					"%s/%s/any(k:startswith(k,'%s'))",
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
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
					"%s/keywords/any(k:k ne '%s')",
					_objectRelationship1.getName(), _TAG_2)),
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

		// One to many self relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition1, _objectDefinition1,
			_objectEntry1.getPrimaryKey(), _objectEntry1.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/any(k:contains(k,'%s'))",
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s/any(k:contains(k,'%s'))",
					_objectRelationship1.getName(),
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/any(k:k eq '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s/any(k:k eq '%s')", _objectRelationship1.getName(),
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/any(k:k in ('%s', '%s'))",
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1, RandomTestUtil.randomString())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s/any(k:k in ('%s', '%s'))",
					_objectRelationship1.getName(),
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1, RandomTestUtil.randomString())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/any(k:k ne '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s/any(k:k ne '%s')", _objectRelationship1.getName(),
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/any(k:startswith(k,'%s'))",
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s/any(k:startswith(k,'%s'))",
					_objectRelationship1.getName(),
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition1);
	}

	@Test
	public void testFilterByLambdaOperatorsObjectEntriesByRelatedObjectEntriesFieldsThroughMultipleRelationships()
		throws Exception {

		_objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition1,
			HashMapBuilder.<String, Serializable>put(
				_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1
			).put(
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST, _LIST_TYPE_ENTRY_KEY_1
			).build(),
			_TAG_1);
		_objectEntry2 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition2,
			HashMapBuilder.<String, Serializable>put(
				_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2
			).put(
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST, _LIST_TYPE_ENTRY_KEY_2
			).build(),
			_TAG_2);
		_objectEntry3 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition3,
			HashMapBuilder.<String, Serializable>put(
				_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3
			).put(
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST, _LIST_TYPE_ENTRY_KEY_3
			).build(),
			_TAG_3);

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
					"%s/%s/%s/any(k:contains(k,'%s'))",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_3)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s/any(k:k eq '%s')", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_3)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s/any(k:k in ('%s', '%s'))",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_3, RandomTestUtil.randomString())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s/any(k:k ne '%s')", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_2)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s/any(k:startswith(k,'%s'))",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_3)),
			_objectDefinition1);

		// Many to many relationship, system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/keywords/any(k:contains(k,'%s'))",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(), _TAG_3.substring(1))),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/keywords/any(k:k eq '%s')",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(), _TAG_3)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/keywords/any(k:k in ('%s', '%s'))",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(), _TAG_3,
					RandomTestUtil.randomString())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/keywords/any(k:k ne '%s')",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(), _TAG_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/keywords/any(k:startswith(k,'%s'))",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(), _TAG_3.substring(0, 2))),
			_objectDefinition1);

		// Many to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s/any(k:contains(k,'%s'))",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s/any(k:k eq '%s')", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s/any(k:k in ('%s', '%s'))",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1, RandomTestUtil.randomString())),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s/any(k:k ne '%s')", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_3)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s/any(k:startswith(k,'%s'))",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition3);

		// Many to many relationship (other side), system object field

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
					"%s/%s/keywords/any(k:k eq '%s')",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(), _TAG_1)),
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
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/keywords/any(k:k ne '%s')",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(), _TAG_3)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/keywords/any(k:startswith(k,'%s'))",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(), _TAG_1.substring(0, 2))),
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
					"%s/%s/%s/any(k:contains(k,'%s'))",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_3)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s/any(k:k eq '%s')", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_3)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s/any(k:k in ('%s', '%s'))",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_3, RandomTestUtil.randomString())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s/any(k:k ne '%s')", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s/any(k:startswith(k,'%s'))",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_3)),
			_objectDefinition1);

		// One to many relationship, system object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/keywords/any(k:contains(k,'%s'))",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(), _TAG_3.substring(1))),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/keywords/any(k:k eq '%s')",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(), _TAG_3)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/keywords/any(k:k in ('%s', '%s'))",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(), _TAG_3,
					RandomTestUtil.randomString())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/keywords/any(k:k ne '%s')",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(), _TAG_1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/keywords/any(k:startswith(k,'%s'))",
					_objectRelationship1.getName(),
					_objectRelationship2.getName(), _TAG_3.substring(0, 2))),
			_objectDefinition1);

		// One to many relationship (other side), custom object field

		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s/any(k:contains(k,'%s'))",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s/any(k:k eq '%s')", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s/any(k:k in ('%s', '%s'))",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1, RandomTestUtil.randomString())),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s/any(k:k ne '%s')", _objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_3)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/%s/any(k:startswith(k,'%s'))",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
					_LIST_TYPE_ENTRY_KEY_1)),
			_objectDefinition3);

		// One to many relationship (other side), system object field

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
					"%s/%s/keywords/any(k:k eq '%s')",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(), _TAG_1)),
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
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/keywords/any(k:k ne '%s')",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(), _TAG_3)),
			_objectDefinition3);
		_assertFilterString(
			_OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3,
			_escape(
				String.format(
					"%s/%s/keywords/any(k:startswith(k,'%s'))",
					_objectRelationship2.getName(),
					_objectRelationship1.getName(), _TAG_1.substring(0, 2))),
			_objectDefinition3);
	}

	@Test
	public void testFilterByListOperatorsObjectEntriesByRelatedObjectEntriesFields()
		throws Exception {

		// Many to many relationship, custom object field

		_objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition1, _OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1);
		_objectEntry2 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition2, _OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2);

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

		// Many to many self relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition1, _objectDefinition1,
			_objectEntry1.getPrimaryKey(), _objectEntry1.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s in ('%s', '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
					RandomTestUtil.randomInt())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s in ('%s', '%s')", _objectRelationship1.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, RandomTestUtil.randomInt())),
			_objectDefinition1);

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

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship1);

		// One to many self relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition1, _objectDefinition1,
			_objectEntry1.getPrimaryKey(), _objectEntry1.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s in ('%s', '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
					RandomTestUtil.randomInt())),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s in ('%s', '%s')", _objectRelationship1.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, RandomTestUtil.randomInt())),
			_objectDefinition1);
	}

	@Test
	public void testFilterByLogicalOperatorsObjectEntriesByRelatedObjectEntriesFields()
		throws Exception {

		// Many to many relationship, custom object field

		_objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition1, _OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1);
		_objectEntry2 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition2, _OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2);

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

		// Many to many self relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition1, _objectDefinition1,
			_objectEntry1.getPrimaryKey(), _objectEntry1.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s le '%s' and %s/%s gt '%s'",
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s le '%s' and %s/%s/%s gt '%s'",
					_objectRelationship1.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, _objectRelationship1.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s le '%s' or %s/%s gt '%s'",
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s le '%s' or %s/%s/%s gt '%s'",
					_objectRelationship1.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, _objectRelationship1.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"not (%s/%s ge '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 + 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"not (%s/%s/%s ge '%s')", _objectRelationship1.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 + 1)),
			_objectDefinition1);

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

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship1);

		// One to many self relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition1, _objectDefinition1,
			_objectEntry1.getPrimaryKey(), _objectEntry1.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s le '%s' and %s/%s gt '%s'",
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s le '%s' and %s/%s/%s gt '%s'",
					_objectRelationship1.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, _objectRelationship1.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s le '%s' or %s/%s gt '%s'",
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"%s/%s/%s le '%s' or %s/%s/%s gt '%s'",
					_objectRelationship1.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1, _objectRelationship1.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 - 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"not (%s/%s ge '%s')", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1 + 1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			_escape(
				String.format(
					"not (%s/%s/%s ge '%s')", _objectRelationship1.getName(),
					_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
					_OBJECT_FIELD_VALUE_1 + 1)),
			_objectDefinition1);
	}

	@Test
	public void testFilterByLogicalOperatorsObjectEntriesByRelatedObjectEntriesFieldsThroughMultipleRelationships()
		throws Exception {

		// Many to many relationship, custom object field

		_objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition1, _OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1);
		_objectEntry2 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition2, _OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2);
		_objectEntry3 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition3, _OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3);

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
	public void testFilterByStringOperatorsObjectEntriesByRelatedObjectEntriesFields()
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

		// Many to many self relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition1, _objectDefinition1,
			_objectEntry1.getPrimaryKey(), _objectEntry1.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			String.format(
				"contains(%s/%s,'%s')", _objectRelationship1.getName(),
				_OBJECT_FIELD_NAME_1, objectFieldValue1.substring(1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			String.format(
				"contains(%s/%s/%s,'%s')", _objectRelationship1.getName(),
				_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
				objectFieldValue1.substring(1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			String.format(
				"startswith(%s/%s,'%s')", _objectRelationship1.getName(),
				_OBJECT_FIELD_NAME_1, objectFieldValue1.substring(0, 2)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			String.format(
				"startswith(%s/%s/%s,'%s')", _objectRelationship1.getName(),
				_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
				objectFieldValue1.substring(0, 2)),
			_objectDefinition1);

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

		_objectRelationshipLocalService.deleteObjectRelationship(
			_objectRelationship1);

		// One to many self relationship, custom object field

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition1, _objectDefinition1,
			_objectEntry1.getPrimaryKey(), _objectEntry1.getPrimaryKey(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			String.format(
				"contains(%s/%s,'%s')", _objectRelationship1.getName(),
				_OBJECT_FIELD_NAME_1, objectFieldValue1.substring(1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			String.format(
				"contains(%s/%s/%s,'%s')", _objectRelationship1.getName(),
				_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
				objectFieldValue1.substring(1)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			String.format(
				"startswith(%s/%s,'%s')", _objectRelationship1.getName(),
				_OBJECT_FIELD_NAME_1, objectFieldValue1.substring(0, 2)),
			_objectDefinition1);
		_assertFilterString(
			_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1,
			String.format(
				"startswith(%s/%s/%s,'%s')", _objectRelationship1.getName(),
				_objectRelationship1.getName(), _OBJECT_FIELD_NAME_1,
				objectFieldValue1.substring(0, 2)),
			_objectDefinition1);
	}

	@Test
	public void testFilterByStringOperatorsObjectEntriesByRelatedObjectEntriesFieldsThroughMultipleRelationships()
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
	public void testFilterByUnknownObjectField() throws Exception {
		String filterString =
			"x" + RandomTestUtil.randomString() + " eq 'value'";

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
	public void testFilterObjectEntriesByRelatedSystemObjectEntriesFields()
		throws Exception {

		// Many to many relationship

		_objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition1, _OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1);

		_userAccountJSONObject = UserAccountTestUtil.addUserAccountJSONObject(
			_systemObjectDefinitionManager,
			HashMapBuilder.<String, Serializable>put(
				_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)
			).build());

		_objectRelationship1 = _addObjectRelationshipAndRelateObjectEntries(
			_objectDefinition1, _userSystemObjectDefinition,
			_objectEntry1.getPrimaryKey(), _userAccountJSONObject.getLong("id"),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_testFilterObjectEntriesByRelatedSystemObjectEntriesFields(
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

		_testFilterObjectEntriesByRelatedSystemObjectEntriesFields(
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

		_objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition1, _OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1);
		_objectEntry2 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition2, _OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2);

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
		_testGetNestedFieldDetailsInRelationships(
			relationshipFieldNameNestedFieldName, null,
			StringUtil.removeLast(relationshipFieldName, "Id"),
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

		_objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition1, _OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1);
		_objectEntry2 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition2, _OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2);
		_objectEntry3 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition3, _OBJECT_FIELD_NAME_3, _OBJECT_FIELD_VALUE_3);
		_objectEntry4 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition4, _OBJECT_FIELD_NAME_4, _OBJECT_FIELD_VALUE_4);

		_userAccountJSONObject = UserAccountTestUtil.addUserAccountJSONObject(
			_systemObjectDefinitionManager,
			HashMapBuilder.<String, Serializable>put(
				_OBJECT_FIELD_NAME_2, String.valueOf(_OBJECT_FIELD_VALUE_2)
			).build());

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
	public void testGetObjectEntriesWithPagination() throws Exception {
		ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition6, _OBJECT_FIELD_NAME_TEXT,
			_NEW_OBJECT_FIELD_VALUE_1);
		ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition6, _OBJECT_FIELD_NAME_TEXT,
			_NEW_OBJECT_FIELD_VALUE_1);
		ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition6, _OBJECT_FIELD_NAME_TEXT,
			_NEW_OBJECT_FIELD_VALUE_1);
		ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition6, _OBJECT_FIELD_NAME_TEXT,
			_NEW_OBJECT_FIELD_VALUE_1);
		ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition6, _OBJECT_FIELD_NAME_TEXT,
			_NEW_OBJECT_FIELD_VALUE_1);
		ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition6, _OBJECT_FIELD_NAME_TEXT,
			_NEW_OBJECT_FIELD_VALUE_1);
		ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition6, _OBJECT_FIELD_NAME_TEXT,
			_NEW_OBJECT_FIELD_VALUE_1);

		_assertPagination(7, _objectDefinition6);

		ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition6, _OBJECT_FIELD_NAME_TEXT,
			_NEW_OBJECT_FIELD_VALUE_1);
		ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition6, _OBJECT_FIELD_NAME_TEXT,
			_NEW_OBJECT_FIELD_VALUE_1);

		_assertPagination(9, _objectDefinition6);

		ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition6, _OBJECT_FIELD_NAME_TEXT,
			_NEW_OBJECT_FIELD_VALUE_1);
		ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition6, _OBJECT_FIELD_NAME_TEXT,
			_NEW_OBJECT_FIELD_VALUE_1);

		_assertPagination(11, _objectDefinition6);
	}

	@Test
	public void testGetObjectEntryByExternalReferenceCodeWithSlash()
		throws Exception {

		HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
			).put(
				"externalReferenceCode", "a"
			).toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		Assert.assertEquals(
			200,
			HTTPTestUtil.invokeToHttpCode(
				null,
				_objectDefinition1.getRESTContextPath() +
					"/by-external-reference-code/a/",
				Http.Method.GET));

		HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
			).put(
				"externalReferenceCode", "a/"
			).toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		Assert.assertEquals(
			200,
			HTTPTestUtil.invokeToHttpCode(
				null,
				_objectDefinition1.getRESTContextPath() +
					"/by-external-reference-code/a%252F",
				Http.Method.GET));
		Assert.assertEquals(
			200,
			HTTPTestUtil.invokeToHttpCode(
				null,
				_objectDefinition1.getRESTContextPath() +
					"/by-external-reference-code/a%252F/",
				Http.Method.GET));

		HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
			).put(
				"externalReferenceCode", "/a"
			).toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		Assert.assertEquals(
			200,
			HTTPTestUtil.invokeToHttpCode(
				null,
				_objectDefinition1.getRESTContextPath() +
					"/by-external-reference-code/%252Fa",
				Http.Method.GET));

		HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
			).put(
				"externalReferenceCode", "a/b"
			).toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.vulcan.internal.jaxrs.exception.mapper." +
					"WebApplicationExceptionMapper",
				LoggerTestUtil.ERROR)) {

			Assert.assertEquals(
				405,
				HTTPTestUtil.invokeToHttpCode(
					null,
					_objectDefinition1.getRESTContextPath() +
						"/by-external-reference-code/a/b",
					Http.Method.GET));
		}

		Assert.assertEquals(
			200,
			HTTPTestUtil.invokeToHttpCode(
				null,
				_objectDefinition1.getRESTContextPath() +
					"/by-external-reference-code/a%252Fb",
				Http.Method.GET));
		Assert.assertEquals(
			200,
			HTTPTestUtil.invokeToHttpCode(
				null,
				_objectDefinition1.getRESTContextPath() +
					"/by-external-reference-code/a%252Fb/",
				Http.Method.GET));
		Assert.assertEquals(
			400,
			HTTPTestUtil.invokeToHttpCode(
				null,
				_objectDefinition1.getRESTContextPath() +
					"/by-external-reference-code/a%2Fb",
				Http.Method.GET));

		HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
			).put(
				"externalReferenceCode", "a//b"
			).toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		Assert.assertEquals(
			200,
			HTTPTestUtil.invokeToHttpCode(
				null,
				_objectDefinition1.getRESTContextPath() +
					"/by-external-reference-code/a%252F%252Fb",
				Http.Method.GET));
	}

	@Test
	public void testGetObjectEntryFilteredByKeywords() throws Exception {
		_postObjectEntryWithKeywords("tag1");
		_postObjectEntryWithKeywords("TAG1");
		_postObjectEntryWithKeywords("tag1", "tag2");
		_postObjectEntryWithKeywords("tag1", "tag2", "tag3");

		_assertFilteredObjectEntries(4, "keywords/any(k:k eq 'tag1')");
		_assertFilteredObjectEntries(4, "keywords/any(k:k eq 'TAG1')");
		_assertFilteredObjectEntries(2, "keywords/any(k:k eq 'tag2')");
		_assertFilteredObjectEntries(1, "keywords/any(k:k eq 'tag3')");
		_assertFilteredObjectEntries(0, "keywords/any(k:k eq '1234')");

		_assertFilteredObjectEntries(2, "keywords/any(k:k ne 'tag1')");
		_assertFilteredObjectEntries(2, "keywords/any(k:k ne 'TAG1')");
		_assertFilteredObjectEntries(4, "keywords/any(k:k ne 'tag2')");
		_assertFilteredObjectEntries(4, "keywords/any(k:k ne 'tag3')");

		_assertFilteredObjectEntries(2, "keywords/any(k:k gt 'tag1')");
		_assertFilteredObjectEntries(2, "keywords/any(k:k gt 'TAG1')");
		_assertFilteredObjectEntries(1, "keywords/any(k:k gt 'tag2')");
		_assertFilteredObjectEntries(0, "keywords/any(k:k gt 'tag3')");

		_assertFilteredObjectEntries(4, "keywords/any(k:k ge 'tag1')");
		_assertFilteredObjectEntries(4, "keywords/any(k:k ge 'TAG1')");
		_assertFilteredObjectEntries(2, "keywords/any(k:k ge 'tag2')");
		_assertFilteredObjectEntries(1, "keywords/any(k:k ge 'tag3')");

		_assertFilteredObjectEntries(0, "keywords/any(k:k lt 'tag1')");
		_assertFilteredObjectEntries(0, "keywords/any(k:k lt 'TAG1')");
		_assertFilteredObjectEntries(4, "keywords/any(k:k lt 'tag2')");
		_assertFilteredObjectEntries(4, "keywords/any(k:k lt 'tag3')");

		_assertFilteredObjectEntries(4, "keywords/any(k:k le 'tag1')");
		_assertFilteredObjectEntries(4, "keywords/any(k:k le 'TAG1')");
		_assertFilteredObjectEntries(4, "keywords/any(k:k le 'tag2')");
		_assertFilteredObjectEntries(4, "keywords/any(k:k le 'tag3')");

		_assertFilteredObjectEntries(4, "keywords/any(k:startswith(k,'t'))");
		_assertFilteredObjectEntries(4, "keywords/any(k:startswith(k,'ta'))");
		_assertFilteredObjectEntries(4, "keywords/any(k:startswith(k,'tag'))");
		_assertFilteredObjectEntries(4, "keywords/any(k:startswith(k,'tag1'))");
		_assertFilteredObjectEntries(4, "keywords/any(k:startswith(k,'TAG1'))");
		_assertFilteredObjectEntries(2, "keywords/any(k:startswith(k,'tag2'))");
		_assertFilteredObjectEntries(1, "keywords/any(k:startswith(k,'tag3'))");
		_assertFilteredObjectEntries(0, "keywords/any(k:startswith(k,'1234'))");

		_assertFilteredObjectEntries(4, "keywords/any(k:contains(k,'tag'))");
		_assertFilteredObjectEntries(4, "keywords/any(k:contains(k,'ag1'))");
		_assertFilteredObjectEntries(4, "keywords/any(k:contains(k,'AG1'))");
		_assertFilteredObjectEntries(2, "keywords/any(k:contains(k,'ag2'))");
		_assertFilteredObjectEntries(1, "keywords/any(k:contains(k,'ag3'))");
		_assertFilteredObjectEntries(0, "keywords/any(k:contains(k,'1234'))");

		_assertFilteredObjectEntries(4, "keywords/any(k:k in ('tag1','tag2'))");
		_assertFilteredObjectEntries(4, "keywords/any(k:k in ('TAG1','tag2'))");
		_assertFilteredObjectEntries(2, "keywords/any(k:k in ('tag2','tag3'))");
		_assertFilteredObjectEntries(0, "keywords/any(k:k in ('1234','5678'))");
	}

	@Test
	public void testGetObjectEntryFilteredByMultiselectPicklistObjectField()
		throws Exception {

		ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition1,
			HashMapBuilder.<String, Serializable>put(
				_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
			).put(
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST, _LIST_TYPE_ENTRY_KEY_1
			).build(),
			_TAG_1);
		ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition1,
			HashMapBuilder.<String, Serializable>put(
				_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
			).put(
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				(Serializable)Arrays.asList(
					_LIST_TYPE_ENTRY_KEY_1, _LIST_TYPE_ENTRY_KEY_2)
			).build(),
			_TAG_1);
		ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition1,
			HashMapBuilder.<String, Serializable>put(
				_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
			).put(
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				(Serializable)Arrays.asList(
					_LIST_TYPE_ENTRY_KEY_1, _LIST_TYPE_ENTRY_KEY_2,
					_LIST_TYPE_ENTRY_KEY_3)
			).build(),
			_TAG_1);

		_assertFilteredObjectEntries(
			3,
			String.format(
				"%s/any(k:contains(k,'%s'))",
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				_LIST_TYPE_ENTRY_KEY_1.substring(1)));
		_assertFilteredObjectEntries(
			2,
			String.format(
				"%s/any(k:contains(k,'%s'))",
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				_LIST_TYPE_ENTRY_KEY_2.substring(1)));
		_assertFilteredObjectEntries(
			1,
			String.format(
				"%s/any(k:contains(k,'%s'))",
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				_LIST_TYPE_ENTRY_KEY_3.substring(1)));
		_assertFilteredObjectEntries(
			0,
			String.format(
				"%s/any(k:contains(k,'%s'))",
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				RandomTestUtil.randomString()));
		_assertFilteredObjectEntries(
			3,
			String.format(
				"%s/any(k:k eq '%s')", _OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				_LIST_TYPE_ENTRY_KEY_1));
		_assertFilteredObjectEntries(
			2,
			String.format(
				"%s/any(k:k eq '%s')", _OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				_LIST_TYPE_ENTRY_KEY_2));
		_assertFilteredObjectEntries(
			1,
			String.format(
				"%s/any(k:k eq '%s')", _OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				_LIST_TYPE_ENTRY_KEY_3));
		_assertFilteredObjectEntries(
			0,
			String.format(
				"%s/any(k:k eq '%s')", _OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				RandomTestUtil.randomString()));
		_assertFilteredObjectEntries(
			0,
			String.format(
				"%s/any(k:k eq '%s')", _OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				_LIST_TYPE_ENTRY_KEY_1.substring(1)));
		_assertFilteredObjectEntries(
			0,
			String.format(
				"%s/any(k:k eq '%s')", _OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				_LIST_TYPE_ENTRY_KEY_2.substring(1)));
		_assertFilteredObjectEntries(
			0,
			String.format(
				"%s/any(k:k eq '%s')", _OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				_LIST_TYPE_ENTRY_KEY_3.substring(1)));
		_assertFilteredObjectEntries(
			3,
			String.format(
				"%s/any(k:k in ('%s','%s'))",
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST, _LIST_TYPE_ENTRY_KEY_1,
				_LIST_TYPE_ENTRY_KEY_2));
		_assertFilteredObjectEntries(
			2,
			String.format(
				"%s/any(k:k in ('%s','%s'))",
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST, _LIST_TYPE_ENTRY_KEY_2,
				_LIST_TYPE_ENTRY_KEY_3));
		_assertFilteredObjectEntries(
			0,
			String.format(
				"%s/any(k:k in ('%s','%s'))",
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				RandomTestUtil.randomString(), RandomTestUtil.randomString()));
		_assertFilteredObjectEntries(
			2,
			String.format(
				"%s/any(k:k ne '%s')", _OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				_LIST_TYPE_ENTRY_KEY_1));
		_assertFilteredObjectEntries(
			3,
			String.format(
				"%s/any(k:k ne '%s')", _OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				_LIST_TYPE_ENTRY_KEY_2));
		_assertFilteredObjectEntries(
			3,
			String.format(
				"%s/any(k:k ne '%s')", _OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				_LIST_TYPE_ENTRY_KEY_3));
		_assertFilteredObjectEntries(
			3,
			String.format(
				"%s/any(k:k ne '%s')", _OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				RandomTestUtil.randomString()));
		_assertFilteredObjectEntries(
			3,
			String.format(
				"%s/any(k:startswith(k,'%s'))",
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				_LIST_TYPE_ENTRY_KEY_1.substring(0, 2)));
		_assertFilteredObjectEntries(
			2,
			String.format(
				"%s/any(k:startswith(k,'%s'))",
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				_LIST_TYPE_ENTRY_KEY_2.substring(0, 2)));
		_assertFilteredObjectEntries(
			1,
			String.format(
				"%s/any(k:startswith(k,'%s'))",
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				_LIST_TYPE_ENTRY_KEY_3.substring(0, 2)));
		_assertFilteredObjectEntries(
			3,
			String.format(
				"%s/any(k:startswith(k,'%s'))",
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				_LIST_TYPE_ENTRY_KEY_1));
		_assertFilteredObjectEntries(
			2,
			String.format(
				"%s/any(k:startswith(k,'%s'))",
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				_LIST_TYPE_ENTRY_KEY_2));
		_assertFilteredObjectEntries(
			1,
			String.format(
				"%s/any(k:startswith(k,'%s'))",
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				_LIST_TYPE_ENTRY_KEY_3));
		_assertFilteredObjectEntries(
			0,
			String.format(
				"%s/any(k:startswith(k,'%s'))",
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				RandomTestUtil.randomString()));
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
	public void testGetObjectEntryPermissionsPage() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		_addResourcePermission(
			ObjectActionKeys.ADD_OBJECT_ENTRY, _objectDefinition1, role);

		User user = _addUser("test1", "test1");

		_roleLocalService.addUserRole(user.getUserId(), role.getRoleId());

		HTTPTestUtil.customize(
		).withCredentials(
			"test1@liferay.com", "test1"
		).apply(
			() -> {
				String externalReferenceCode = RandomTestUtil.randomString();

				JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
					JSONUtil.put(
						"externalReferenceCode", externalReferenceCode
					).toString(),
					_getEndpoint(
						TestPropsValues.getGroupId(), _objectDefinition1),
					Http.Method.POST);

				jsonObject = HTTPTestUtil.invokeToJSONObject(
					JSONUtil.putAll(
						JSONUtil.put(
							"actionIds", JSONUtil.putAll()
						).put(
							"roleName", role.getName()
						)
					).toString(),
					StringBundler.concat(
						_getEndpoint(
							TestPropsValues.getGroupId(), _objectDefinition1),
						"/", jsonObject.getString("id"), "/permissions"),
					Http.Method.PUT);

				Assert.assertNotEquals(
					jsonObject.getString("title"), "FORBIDDEN",
					jsonObject.getString("status"));
			}
		);
	}

	@Test
	public void testGetObjectEntryUnsafeSuppliers() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(ObjectEntryResourceTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		AtomicBoolean booleanObjectFieldComputed = new AtomicBoolean();
		AtomicBoolean dateObjectFieldComputed = new AtomicBoolean();

		ServiceRegistration<Feature> serviceRegistration =
			bundleContext.registerService(
				Feature.class,
				featureContext -> {
					featureContext.register(
						(ContainerResponseFilter)
							(containerRequestContext,
							 containerResponseContext) -> {

								com.liferay.object.rest.dto.v1_0.ObjectEntry
									objectEntry =
										(com.liferay.object.rest.dto.v1_0.
											ObjectEntry)
												containerResponseContext.
													getEntity();

								Map<String, Object> properties =
									objectEntry.getProperties();

								UnsafeSupplier<Object, Exception>
									unsafeSupplier1 = () -> {
										booleanObjectFieldComputed.set(true);

										return RandomTestUtil.randomBoolean();
									};

								properties.put(
									_OBJECT_FIELD_NAME_BOOLEAN,
									unsafeSupplier1);

								UnsafeSupplier<Object, Exception>
									unsafeSupplier2 = () -> {
										dateObjectFieldComputed.set(true);

										return _dateFormat.format(
											RandomTestUtil.nextDate());
									};

								properties.put(
									_OBJECT_FIELD_NAME_DATE, unsafeSupplier2);
							});

					return false;
				},
				HashMapDictionaryBuilder.<String, Object>put(
					"osgi.jaxrs.application.select",
					"(osgi.jaxrs.extension.select=\\(osgi.jaxrs.name=Liferay." +
						"Vulcan\\))"
				).put(
					"osgi.jaxrs.extension", true
				).put(
					"osgi.jaxrs.name", "Liferay.Vulcan)"
				).build());

		try {
			_objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
				_objectDefinition1,
				HashMapBuilder.<String, Serializable>put(
					_OBJECT_FIELD_NAME_BOOLEAN, RandomTestUtil.randomBoolean()
				).put(
					_OBJECT_FIELD_NAME_DATE,
					_dateFormat.format(RandomTestUtil.nextDate())
				).build(),
				_TAG_1);

			HTTPTestUtil.invokeToJSONObject(
				null,
				StringBundler.concat(
					_objectDefinition1.getRESTContextPath(),
					"/by-external-reference-code/",
					_objectEntry1.getExternalReferenceCode(), "?fields=",
					_OBJECT_FIELD_NAME_BOOLEAN),
				Http.Method.GET);

			Assert.assertTrue(
				_OBJECT_FIELD_NAME_BOOLEAN + " should have been computed",
				booleanObjectFieldComputed.getAndSet(false));
			Assert.assertFalse(
				_OBJECT_FIELD_NAME_DATE + " should not have been computed",
				dateObjectFieldComputed.getAndSet(false));

			HTTPTestUtil.invokeToJSONObject(
				null,
				_objectDefinition1.getRESTContextPath() +
					"/by-external-reference-code/" +
						_objectEntry1.getExternalReferenceCode(),
				Http.Method.GET);

			Assert.assertTrue(
				_OBJECT_FIELD_NAME_BOOLEAN + " should have been computed",
				booleanObjectFieldComputed.getAndSet(false));
			Assert.assertTrue(
				_OBJECT_FIELD_NAME_DATE + " should have been computed",
				dateObjectFieldComputed.getAndSet(false));
		}
		finally {
			serviceRegistration.unregister();
		}
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
			ObjectActionExecutorConstants.KEY_WEBHOOK,
			ObjectActionTriggerConstants.KEY_STANDALONE,
			UnicodePropertiesBuilder.put(
				"secret", "standalone"
			).put(
				"url", "https://standalone.com"
			).build(),
			false);

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
	public void testGetObjectEntryWithAttachmentObjectField() throws Exception {
		String content = RandomTestUtil.randomString();

		FileEntry fileEntry = _addTempFileEntry(
			content, _objectDefinition1, RandomTestUtil.randomString());

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, "value1"
			).put(
				_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1,
				fileEntry.getFileEntryId()
			).toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		JSONObject attachmentJSONObject = jsonObject.getJSONObject(
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1);

		Assert.assertNull(attachmentJSONObject.get("fileBase64"));

		content = RandomTestUtil.randomString();

		fileEntry = _addTempFileEntry(
			content, _objectDefinition1, RandomTestUtil.randomString());

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, "value2"
			).put(
				_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1,
				fileEntry.getFileEntryId()
			).toString(),
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), "?nestedFields=",
				_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1,
				".fileBase64"),
			Http.Method.POST);

		attachmentJSONObject = jsonObject.getJSONObject(
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1);

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
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1);

		Assert.assertNull(attachmentJSONObject.get("fileBase64"));

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(),
				"/by-external-reference-code/",
				jsonObject.getString("externalReferenceCode"), "?nestedFields=",
				_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1,
				".fileBase64"),
			Http.Method.GET);

		attachmentJSONObject = jsonObject.getJSONObject(
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1);

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
				TestPropsValues.getUserId(), 0, false, true, false, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionTestUtil.getRandomName(), null, null,
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
					).put(
						"flushInterval", 1
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
	public void testGetObjectEntryWithLocalizedObjectField() throws Exception {

		// "Accept-Language" header

		JSONAssert.assertEquals(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_LONG_TEXT, "longTextEsp"
			).put(
				_OBJECT_FIELD_NAME_RICH_TEXT, "<p>richTextEsp</p>"
			).put(
				_OBJECT_FIELD_NAME_RICH_TEXT + "RawText", "richTextEsp"
			).put(
				_OBJECT_FIELD_NAME_TEXT, "textEsp"
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				null,
				_objectDefinition5.getRESTContextPath() + StringPool.SLASH +
					_objectEntry5.getObjectEntryId(),
				HashMapBuilder.put(
					"Accept-Language", "es-ES"
				).build(),
				Http.Method.GET
			).toString(),
			JSONCompareMode.LENIENT);

		// Empty "Accept-Language" header

		JSONAssert.assertEquals(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_LONG_TEXT, "longTextEng"
			).put(
				_OBJECT_FIELD_NAME_RICH_TEXT, "<p>richTextEng</p>"
			).put(
				_OBJECT_FIELD_NAME_RICH_TEXT + "RawText", "richTextEng"
			).put(
				_OBJECT_FIELD_NAME_TEXT, "textEng"
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				null,
				_objectDefinition5.getRESTContextPath() + StringPool.SLASH +
					_objectEntry5.getObjectEntryId(),
				HashMapBuilder.put(
					"Accept-Language", ""
				).build(),
				Http.Method.GET
			).toString(),
			JSONCompareMode.LENIENT);

		// Nonexistent "Accept-Language" header

		JSONAssert.assertEquals(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_LONG_TEXT, ""
			).put(
				_OBJECT_FIELD_NAME_RICH_TEXT, ""
			).put(
				_OBJECT_FIELD_NAME_RICH_TEXT + "RawText", ""
			).put(
				_OBJECT_FIELD_NAME_TEXT, ""
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				null,
				_objectDefinition5.getRESTContextPath() + StringPool.SLASH +
					_objectEntry5.getObjectEntryId(),
				HashMapBuilder.put(
					"Accept-Language", "de-DE"
				).build(),
				Http.Method.GET
			).toString(),
			JSONCompareMode.LENIENT);

		// Without "Accept-Language" header

		JSONAssert.assertEquals(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_LONG_TEXT, "longTextEng"
			).put(
				_OBJECT_FIELD_NAME_RICH_TEXT, "<p>richTextEng</p>"
			).put(
				_OBJECT_FIELD_NAME_RICH_TEXT + "RawText", "richTextEng"
			).put(
				_OBJECT_FIELD_NAME_TEXT, "textEng"
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				null,
				_objectDefinition5.getRESTContextPath() + StringPool.SLASH +
					_objectEntry5.getObjectEntryId(),
				Http.Method.GET
			).toString(),
			JSONCompareMode.LENIENT);
	}

	@Test
	public void testGetObjectEntryWithObjectActions() throws Exception {
		_testGetObjectEntryWithObjectActions(
			_addObjectAction(_objectDefinition1), _objectDefinition1,
			(actionJSONObject, jsonObject, objectAction) -> Assert.assertEquals(
				StringBundler.concat(
					"http://localhost:8080/o",
					_objectDefinition1.getRESTContextPath(),
					"/by-external-reference-code/",
					jsonObject.getString("externalReferenceCode"),
					"/object-actions/", objectAction.getName()),
				actionJSONObject.getString("href")));

		_testGetObjectEntryWithObjectActions(
			_addObjectAction(_siteScopedObjectDefinition1),
			_siteScopedObjectDefinition1,
			(actionJSONObject, jsonObject, objectAction) -> Assert.assertEquals(
				StringBundler.concat(
					"http://localhost:8080/o",
					_siteScopedObjectDefinition1.getRESTContextPath(),
					"/scopes/", TestPropsValues.getGroupId(),
					"/by-external-reference-code/",
					jsonObject.getString("externalReferenceCode"),
					"/object-actions/", objectAction.getName()),
				actionJSONObject.getString("href")));
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

		_objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition1, _OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1);

		_objectEntry2 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition2, _OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2);

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

		_objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition1, _OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1);

		_objectEntry2 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition2, _OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2);

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

		_siteScopedObjectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_siteScopedObjectDefinition1, _OBJECT_FIELD_NAME_1,
			_OBJECT_FIELD_VALUE_1);

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
	public void testPatchObjectEntryWithRequiredObjectFields()
		throws Exception {

		ObjectField objectField1 = _objectFieldLocalService.getObjectField(
			_objectDefinition1.getObjectDefinitionId(), _OBJECT_FIELD_NAME_1);

		_objectFieldLocalService.updateRequired(
			objectField1.getObjectFieldId(), true);

		ObjectField objectField2 = _objectFieldLocalService.getObjectField(
			_objectDefinition1.getObjectDefinitionId(),
			_OBJECT_FIELD_NAME_TEXT);

		_objectFieldLocalService.updateRequired(
			objectField2.getObjectFieldId(), true);

		String objectFieldValue1 = RandomTestUtil.randomString();

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, objectFieldValue1
			).put(
				_OBJECT_FIELD_NAME_TEXT, RandomTestUtil.randomString()
			).toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		String objectFieldValue2 = RandomTestUtil.randomString();

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_TEXT, objectFieldValue2
			).toString(),
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				jsonObject.get("id")),
			Http.Method.PATCH);

		Assert.assertEquals(
			objectFieldValue1, jsonObject.getString(_OBJECT_FIELD_NAME_1));
		Assert.assertEquals(
			objectFieldValue2, jsonObject.getString(_OBJECT_FIELD_NAME_TEXT));
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
	public void testPatchPutCustomObjectEntryByExternalReferenceCodeWithAttachmentObjectField()
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

	@Test
	public void testPatchPutCustomObjectEntryExternalReferenceCode()
		throws Exception {

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
			).put(
				"externalReferenceCode", _ERC_VALUE_1
			).toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);

		_testPatchPutCustomObjectEntryExternalReferenceCode(
			Http.Method.PATCH, jsonObject.getLong("id"));
		_testPatchPutCustomObjectEntryExternalReferenceCode(
			Http.Method.PUT, jsonObject.getLong("id"));
	}

	@FeatureFlags("LPS-174455")
	@Test
	public void testPatchPutCustomObjectEntryWithAttachmentObjectField()
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
	public void testPatchPutCustomObjectEntryWithDuplicateExternalReferenceCode()
		throws Exception {

		_testPatchPutCustomObjectEntryWithDuplicateExternalReferenceCode(
			Http.Method.PATCH, _objectDefinition1,
			_siteScopedObjectDefinition1);
		_testPatchPutCustomObjectEntryWithDuplicateExternalReferenceCode(
			Http.Method.PUT, _objectDefinition2, _siteScopedObjectDefinition2);
	}

	@Test
	public void testPatchSiteScopedObject() throws Exception {
		String newObjectFieldValue = RandomTestUtil.randomString();

		JSONObject objectEntryJSONObject = JSONUtil.put(
			_OBJECT_FIELD_NAME_1, newObjectFieldValue);

		_siteScopedObjectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_siteScopedObjectDefinition1, _OBJECT_FIELD_NAME_1,
			_OBJECT_FIELD_VALUE_1);

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
	public void testPostCustomObjectEntryWithAttachmentObjectField()
		throws Exception {

		_testPostCustomObjectEntryWithAttachmentField(_objectDefinition1);
		_testPostCustomObjectEntryWithAttachmentField(
			_siteScopedObjectDefinition1);
	}

	@Test
	public void testPostCustomObjectEntryWithAutoIncrementObjectField()
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
			null,
			StringBundler.concat(
				endpoint, "?sort=", URLCodec.encodeURL("id:asc")),
			Http.Method.GET);

		_assertItem(0, jsonObject, "autoIncrement", "10-private");
		_assertItem(1, jsonObject, "autoIncrement", "100-private");
		_assertItem(2, jsonObject, "autoIncrement", "90-private");

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				endpoint, "?sort=", URLCodec.encodeURL("autoIncrement:asc")),
			Http.Method.GET);

		_assertItem(0, jsonObject, "autoIncrement", "10-private");
		_assertItem(1, jsonObject, "autoIncrement", "90-private");
		_assertItem(2, jsonObject, "autoIncrement", "100-private");

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				endpoint, "?sort=", URLCodec.encodeURL("autoIncrement:desc")),
			Http.Method.GET);

		_assertItem(0, jsonObject, "autoIncrement", "100-private");
		_assertItem(1, jsonObject, "autoIncrement", "90-private");
		_assertItem(2, jsonObject, "autoIncrement", "10-private");

		_objectDefinitionLocalService.deleteObjectDefinition(objectDefinition);
	}

	@Test
	public void testPostCustomObjectEntryWithDuplicateExternalReferenceCode()
		throws Exception {

		Assert.assertEquals(
			200,
			HTTPTestUtil.invokeToHttpCode(
				JSONUtil.put(
					_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
				).put(
					"externalReferenceCode", _ERC_VALUE_1
				).toString(),
				_objectDefinition1.getRESTContextPath(), Http.Method.POST));

		Assert.assertEquals(
			400,
			HTTPTestUtil.invokeToHttpCode(
				JSONUtil.put(
					_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
				).put(
					"externalReferenceCode", _ERC_VALUE_1
				).toString(),
				_objectDefinition1.getRESTContextPath(), Http.Method.POST));

		Assert.assertEquals(
			200,
			HTTPTestUtil.invokeToHttpCode(
				JSONUtil.put(
					_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
				).put(
					"externalReferenceCode", _ERC_VALUE_1
				).toString(),
				_getEndpoint(
					TestPropsValues.getGroupId(), _siteScopedObjectDefinition1),
				Http.Method.POST));

		Assert.assertEquals(
			400,
			HTTPTestUtil.invokeToHttpCode(
				JSONUtil.put(
					_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
				).put(
					"externalReferenceCode", _ERC_VALUE_1
				).toString(),
				_getEndpoint(
					TestPropsValues.getGroupId(), _siteScopedObjectDefinition1),
				Http.Method.POST));
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

		// Company scope

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

		_assertEquals(nestedObjectEntriesJSONArray);

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

		_assertEquals(nestedObjectEntriesJSONArray);

		// Site scope

		_objectRelationship6 = ObjectRelationshipTestUtil.addObjectRelationship(
			_siteScopedObjectDefinition1, _siteScopedObjectDefinition2,
			TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		objectEntryJSONObject = JSONUtil.put(
			_objectRelationship6.getName(),
			_createObjectEntriesJSONArray(
				new String[] {_ERC_VALUE_1, _ERC_VALUE_2}, _OBJECT_FIELD_NAME_2,
				new String[] {
					_NEW_OBJECT_FIELD_VALUE_1, _NEW_OBJECT_FIELD_VALUE_2
				}));

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			objectEntryJSONObject.toString(),
			_getEndpoint(
				TestPropsValues.getGroupId(), _siteScopedObjectDefinition1),
			Http.Method.POST);

		Assert.assertEquals(
			0,
			jsonObject.getJSONObject(
				"status"
			).get(
				"code"
			));

		nestedObjectEntriesJSONArray = jsonObject.getJSONArray(
			_objectRelationship6.getName());

		Assert.assertEquals(2, nestedObjectEntriesJSONArray.length());

		_assertEquals(nestedObjectEntriesJSONArray);

		objectEntryId = jsonObject.getString("id");

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				_siteScopedObjectDefinition1.getRESTContextPath(),
				StringPool.SLASH, objectEntryId, "?nestedFields=",
				_objectRelationship6.getName()),
			Http.Method.GET);

		nestedObjectEntriesJSONArray = jsonObject.getJSONArray(
			_objectRelationship6.getName());

		Assert.assertEquals(2, nestedObjectEntriesJSONArray.length());

		_assertEquals(nestedObjectEntriesJSONArray);
	}

	@Test
	public void testPostCustomObjectEntryWithNestedCustomObjectEntriesInManyToOneRelationship()
		throws Exception {

		// Company scope

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

		// Site scope

		_objectRelationship7 = ObjectRelationshipTestUtil.addObjectRelationship(
			_siteScopedObjectDefinition1, _siteScopedObjectDefinition2,
			TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		objectEntryJSONObject = JSONUtil.put(
			_objectRelationship7.getName(),
			JSONFactoryUtil.createJSONObject(
				JSONUtil.put(
					_OBJECT_FIELD_NAME_1, _NEW_OBJECT_FIELD_VALUE_1
				).put(
					"externalReferenceCode", _ERC_VALUE_1
				).toString()));

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			objectEntryJSONObject.toString(),
			_getEndpoint(
				TestPropsValues.getGroupId(), _siteScopedObjectDefinition2),
			Http.Method.POST);

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
					"r_", _objectRelationship7.getName(), "_",
					StringUtil.replaceLast(
						_siteScopedObjectDefinition1.getPKObjectFieldName(),
						"Id", ""))),
			_OBJECT_FIELD_NAME_1, _NEW_OBJECT_FIELD_VALUE_1);

		objectEntryId = jsonObject.getString("id");

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				_siteScopedObjectDefinition2.getRESTContextPath(),
				StringPool.SLASH, objectEntryId, "?nestedFields=",
				StringBundler.concat(
					"r_", _objectRelationship7.getName(), "_",
					StringUtil.removeLast(
						_siteScopedObjectDefinition1.getPKObjectFieldName(),
						"Id"))),
			Http.Method.GET);

		_assertObjectEntryField(
			jsonObject.getJSONObject(
				StringBundler.concat(
					"r_", _objectRelationship7.getName(), "_",
					StringUtil.removeLast(
						_siteScopedObjectDefinition1.getPKObjectFieldName(),
						"Id"))),
			_OBJECT_FIELD_NAME_1, _NEW_OBJECT_FIELD_VALUE_1);
	}

	@Test
	public void testPostCustomObjectEntryWithNestedCustomObjectEntriesInOneToManyRelationship()
		throws Exception {

		// Company scope

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

		_assertEquals(nestedObjectEntriesJSONArray);

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

		_assertEquals(nestedObjectEntriesJSONArray);

		// Site scope

		_objectRelationship7 = ObjectRelationshipTestUtil.addObjectRelationship(
			_siteScopedObjectDefinition1, _siteScopedObjectDefinition2,
			TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		objectEntryJSONObject = JSONUtil.put(
			_objectRelationship7.getName(),
			_createObjectEntriesJSONArray(
				new String[] {_ERC_VALUE_1, _ERC_VALUE_2}, _OBJECT_FIELD_NAME_2,
				new String[] {
					_NEW_OBJECT_FIELD_VALUE_1, _NEW_OBJECT_FIELD_VALUE_2
				}));

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			objectEntryJSONObject.toString(),
			_getEndpoint(
				TestPropsValues.getGroupId(), _siteScopedObjectDefinition1),
			Http.Method.POST);

		Assert.assertEquals(
			0,
			jsonObject.getJSONObject(
				"status"
			).get(
				"code"
			));

		nestedObjectEntriesJSONArray = jsonObject.getJSONArray(
			_objectRelationship7.getName());

		Assert.assertEquals(2, nestedObjectEntriesJSONArray.length());

		_assertEquals(nestedObjectEntriesJSONArray);

		objectEntryId = jsonObject.getString("id");

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				_siteScopedObjectDefinition1.getRESTContextPath(),
				StringPool.SLASH, objectEntryId, "?nestedFields=",
				_objectRelationship7.getName()),
			Http.Method.GET);

		nestedObjectEntriesJSONArray = jsonObject.getJSONArray(
			_objectRelationship7.getName());

		Assert.assertEquals(2, nestedObjectEntriesJSONArray.length());

		_assertEquals(nestedObjectEntriesJSONArray);
	}

	@Test
	public void testPostCustomObjectEntryWithNonexistentRelatedCustomObjectEntryId()
		throws Exception {

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		JSONObject objectEntryJSONObject = JSONUtil.put(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2
		).put(
			StringBundler.concat(
				"r_", _objectRelationship1.getName(), "_",
				_objectDefinition1.getPKObjectFieldName()),
			StringPool.BLANK
		);

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

		long randomObjectEntryId = RandomTestUtil.randomLong();

		objectEntryJSONObject = JSONUtil.put(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2
		).put(
			StringBundler.concat(
				"r_", _objectRelationship1.getName(), "_",
				_objectDefinition1.getPKObjectFieldName()),
			randomObjectEntryId
		);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"status", "NOT_FOUND"
			).put(
				"title",
				"No ObjectEntry exists with the primary key " +
					randomObjectEntryId
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				objectEntryJSONObject.toString(),
				_objectDefinition2.getRESTContextPath(), Http.Method.POST
			).toString(),
			JSONCompareMode.STRICT);

		ObjectRelationship objectRelationship =
			ObjectRelationshipTestUtil.addObjectRelationship(
				_userSystemObjectDefinition, _objectDefinition2,
				TestPropsValues.getUserId(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		objectEntryJSONObject = JSONUtil.put(
			_OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2
		).put(
			StringBundler.concat(
				"r_", objectRelationship.getName(), "_",
				_userSystemObjectDefinition.getPKObjectFieldName()),
			randomObjectEntryId
		);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"status", "NOT_FOUND"
			).put(
				"title",
				"No User exists with the primary key " + randomObjectEntryId
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				objectEntryJSONObject.toString(),
				_objectDefinition2.getRESTContextPath(), Http.Method.POST
			).toString(),
			JSONCompareMode.STRICT);
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
	public void testPostSiteScopedObjectDefinitionInUserGroup()
		throws Exception {

		UserGroup userGroup = UserGroupTestUtil.addUserGroup();

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			_getEndpoint(userGroup.getGroupId(), _siteScopedObjectDefinition1),
			Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(0, itemsJSONArray.length());

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1
			).toString(),
			_getEndpoint(userGroup.getGroupId(), _siteScopedObjectDefinition1),
			Http.Method.POST);

		Assert.assertEquals(
			_OBJECT_FIELD_VALUE_1, jsonObject.getInt(_OBJECT_FIELD_NAME_1));
	}

	@Test
	public void testPutByExternalReferenceCodeCurrentExternalReferenceCodeObjectRelationshipNameRelatedExternalReferenceCodeWithSlash()
		throws Exception {

		_objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition1, _OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1);

		_objectEntry2 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition2, _OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2);

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
			).put(
				"externalReferenceCode", "a"
			).toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);
		HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
			).put(
				"externalReferenceCode", "a/b"
			).toString(),
			_objectDefinition1.getRESTContextPath(), Http.Method.POST);
		HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
			).put(
				"externalReferenceCode", "c"
			).toString(),
			_objectDefinition2.getRESTContextPath(), Http.Method.POST);
		HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
			).put(
				"externalReferenceCode", "c/d"
			).toString(),
			_objectDefinition2.getRESTContextPath(), Http.Method.POST);

		// Slash on the left side

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.vulcan.internal.jaxrs.exception.mapper." +
					"WebApplicationExceptionMapper",
				LoggerTestUtil.ERROR)) {

			Assert.assertEquals(
				404,
				HTTPTestUtil.invokeToHttpCode(
					null,
					StringBundler.concat(
						_objectDefinition1.getRESTContextPath(),
						"/by-external-reference-code/a/b/",
						_objectRelationship1.getName(), "/c"),
					Http.Method.PUT));
		}

		Assert.assertEquals(
			200,
			HTTPTestUtil.invokeToHttpCode(
				null,
				StringBundler.concat(
					_objectDefinition1.getRESTContextPath(),
					"/by-external-reference-code/a%252Fb/",
					_objectRelationship1.getName(), "/c"),
				Http.Method.PUT));

		// Slash on the right side

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.vulcan.internal.jaxrs.exception.mapper." +
					"WebApplicationExceptionMapper",
				LoggerTestUtil.ERROR)) {

			Assert.assertEquals(
				404,
				HTTPTestUtil.invokeToHttpCode(
					null,
					StringBundler.concat(
						_objectDefinition1.getRESTContextPath(),
						"/by-external-reference-code/a/",
						_objectRelationship1.getName(), "/c/d"),
					Http.Method.PUT));
		}

		Assert.assertEquals(
			200,
			HTTPTestUtil.invokeToHttpCode(
				null,
				StringBundler.concat(
					_objectDefinition1.getRESTContextPath(),
					"/by-external-reference-code/a/",
					_objectRelationship1.getName(), "/c%252Fd"),
				Http.Method.PUT));

		// Slash on the both sides

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.vulcan.internal.jaxrs.exception.mapper." +
					"WebApplicationExceptionMapper",
				LoggerTestUtil.ERROR)) {

			Assert.assertEquals(
				404,
				HTTPTestUtil.invokeToHttpCode(
					null,
					StringBundler.concat(
						_objectDefinition1.getRESTContextPath(),
						"/by-external-reference-code/a/b/",
						_objectRelationship1.getName(), "/c/d"),
					Http.Method.PUT));
		}

		Assert.assertEquals(
			200,
			HTTPTestUtil.invokeToHttpCode(
				null,
				StringBundler.concat(
					_objectDefinition1.getRESTContextPath(),
					"/by-external-reference-code/a%252Fb/",
					_objectRelationship1.getName(), "/c%252Fd"),
				Http.Method.PUT));

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.vulcan.internal.jaxrs.exception.mapper." +
					"WebApplicationExceptionMapper",
				LoggerTestUtil.ERROR)) {

			Assert.assertEquals(
				404,
				HTTPTestUtil.invokeToHttpCode(
					null,
					StringBundler.concat(
						_objectDefinition1.getRESTContextPath(),
						"/by-external-reference-code/a%252Fb/",
						_objectRelationship1.getName(), "/c/d"),
					Http.Method.PUT));
			Assert.assertEquals(
				404,
				HTTPTestUtil.invokeToHttpCode(
					null,
					StringBundler.concat(
						_objectDefinition1.getRESTContextPath(),
						"/by-external-reference-code/a/b/",
						_objectRelationship1.getName(), "/c%252Fd"),
					Http.Method.PUT));
		}
	}

	@Test
	public void testPutByExternalReferenceCodeManyToManyRelationship()
		throws Exception {

		_objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition1, _OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1);

		_objectEntry2 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition2, _OBJECT_FIELD_NAME_2, _OBJECT_FIELD_VALUE_2);

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
		_objectRelationship2 = ObjectRelationshipTestUtil.addObjectRelationship(
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

		_objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition1, _OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1);

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

		_objectEntry1 = ObjectEntryTestUtil.addObjectEntry(
			_objectDefinition1, _OBJECT_FIELD_NAME_1, _OBJECT_FIELD_VALUE_1);

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

	@Test
	public void testSortByCustomObjectField() throws Exception {
		String endpoint = _getEndpoint(
			TestPropsValues.getGroupId(), _objectDefinition1);

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition2, _objectDefinition1, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			_objectRelationship1.getObjectFieldId2());

		BigDecimal randomBigDecimal = new BigDecimal(
			RandomTestUtil.randomDouble());
		Date randomDate1 = RandomTestUtil.nextDate();
		Date randomDate2 = RandomTestUtil.nextDate();
		float randomFloat1 = RandomTestUtil.randomFloat();
		int randomInt = RandomTestUtil.randomInt();
		long randomLong = RandomTestUtil.randomLong(
			ObjectFieldValidationConstants.BUSINESS_TYPE_LONG_VALUE_MIN,
			ObjectFieldValidationConstants.BUSINESS_TYPE_LONG_VALUE_MAX);
		String randomString1 = RandomTestUtil.randomString();
		String randomString2 = RandomTestUtil.randomString();

		JSONObject jsonObject1 = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_BOOLEAN, false
			).put(
				_OBJECT_FIELD_NAME_DATE, _dateFormat.format(randomDate1)
			).put(
				_OBJECT_FIELD_NAME_DATE_TIME,
				_dateTimeDateFormat.format(randomDate2)
			).put(
				_OBJECT_FIELD_NAME_DECIMAL, randomFloat1
			).put(
				_OBJECT_FIELD_NAME_INTEGER, randomInt
			).put(
				_OBJECT_FIELD_NAME_LONG_INTEGER, randomLong
			).put(
				_OBJECT_FIELD_NAME_LONG_TEXT, "a" + randomString1
			).put(
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				JSONUtil.putAll(_LIST_TYPE_ENTRY_KEY_1, _LIST_TYPE_ENTRY_KEY_2)
			).put(
				_OBJECT_FIELD_NAME_PICKLIST, _LIST_TYPE_ENTRY_KEY_1
			).put(
				_OBJECT_FIELD_NAME_PRECISION_DECIMAL, randomBigDecimal
			).put(
				_OBJECT_FIELD_NAME_TEXT, "a" + randomString2
			).put(
				objectField.getName(),
				() -> {
					ObjectEntry relatedObjectEntry =
						ObjectEntryTestUtil.addObjectEntry(
							_objectDefinition2, _OBJECT_FIELD_NAME_2,
							_OBJECT_FIELD_VALUE_2);

					return relatedObjectEntry.getObjectEntryId();
				}
			).toString(),
			endpoint, Http.Method.POST);

		JSONObject jsonObject2 = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_BOOLEAN, true
			).put(
				_OBJECT_FIELD_NAME_DATE,
				() -> _dateFormat.format(
					new Date(randomDate1.getTime() + (24 * 3600 * 1000)))
			).put(
				_OBJECT_FIELD_NAME_DATE_TIME,
				_dateTimeDateFormat.format(
					new Date(
						randomDate2.getTime() +
							(RandomTestUtil.randomInt(1, 60) * 1000)))
			).put(
				_OBJECT_FIELD_NAME_DECIMAL, randomFloat1 + 1
			).put(
				_OBJECT_FIELD_NAME_INTEGER, randomInt + 1
			).put(
				_OBJECT_FIELD_NAME_LONG_INTEGER, randomLong + 1
			).put(
				_OBJECT_FIELD_NAME_LONG_TEXT, "b" + randomString1
			).put(
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				JSONUtil.putAll(_LIST_TYPE_ENTRY_KEY_2, _LIST_TYPE_ENTRY_KEY_3)
			).put(
				_OBJECT_FIELD_NAME_PICKLIST, _LIST_TYPE_ENTRY_KEY_2
			).put(
				_OBJECT_FIELD_NAME_PRECISION_DECIMAL,
				randomBigDecimal.add(BigDecimal.ONE)
			).put(
				_OBJECT_FIELD_NAME_TEXT, "b" + randomString2
			).put(
				objectField.getName(),
				() -> {
					ObjectEntry relatedObjectEntry =
						ObjectEntryTestUtil.addObjectEntry(
							_objectDefinition2, _OBJECT_FIELD_NAME_2,
							_OBJECT_FIELD_VALUE_2);

					return relatedObjectEntry.getObjectEntryId();
				}
			).toString(),
			endpoint, Http.Method.POST);

		try {
			_testSortByCustomObjectField(
				endpoint, jsonObject1, jsonObject2, _OBJECT_FIELD_NAME_BOOLEAN);
			_testSortByCustomObjectField(
				endpoint, jsonObject1, jsonObject2, _OBJECT_FIELD_NAME_DATE);
			_testSortByCustomObjectField(
				endpoint, jsonObject1, jsonObject2,
				_OBJECT_FIELD_NAME_DATE_TIME);
			_testSortByCustomObjectField(
				endpoint, jsonObject1, jsonObject2, _OBJECT_FIELD_NAME_DECIMAL);
			_testSortByCustomObjectField(
				endpoint, jsonObject1, jsonObject2, _OBJECT_FIELD_NAME_INTEGER);
			_testSortByCustomObjectField(
				endpoint, jsonObject1, jsonObject2,
				_OBJECT_FIELD_NAME_LONG_INTEGER);
			_testSortByCustomObjectField(
				endpoint, jsonObject1, jsonObject2,
				_OBJECT_FIELD_NAME_LONG_TEXT);
			_testSortByCustomObjectField(
				endpoint, jsonObject1, jsonObject2,
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST);
			_testSortByCustomObjectField(
				endpoint, jsonObject1, jsonObject2,
				_OBJECT_FIELD_NAME_PICKLIST);
			_testSortByCustomObjectField(
				endpoint, jsonObject1, jsonObject2,
				_OBJECT_FIELD_NAME_PRECISION_DECIMAL);
			_testSortByCustomObjectField(
				endpoint, jsonObject1, jsonObject2, _OBJECT_FIELD_NAME_TEXT);

			// Many to one relationship fields

			String objectFieldName = objectField.getName();

			_testSortByCustomObjectField(
				endpoint, jsonObject1, jsonObject2, objectFieldName);

			_testSortByFieldName(
				endpoint, jsonObject1, jsonObject2,
				StringUtil.replaceLast(objectField.getName(), "Id", "ERC"));
			_testSortByFieldName(
				endpoint, jsonObject1, jsonObject2,
				StringUtil.extractLast(
					objectField.getName(), StringPool.UNDERLINE));

			// Sort by several fields

			_testSortByCustomObjectField(
				endpoint, jsonObject1, jsonObject2, _OBJECT_FIELD_NAME_BOOLEAN,
				_OBJECT_FIELD_NAME_DATE, _OBJECT_FIELD_NAME_DATE_TIME,
				_OBJECT_FIELD_NAME_DECIMAL, _OBJECT_FIELD_NAME_INTEGER,
				_OBJECT_FIELD_NAME_LONG_INTEGER, _OBJECT_FIELD_NAME_LONG_TEXT,
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				_OBJECT_FIELD_NAME_PICKLIST,
				_OBJECT_FIELD_NAME_PRECISION_DECIMAL, _OBJECT_FIELD_NAME_TEXT,
				objectFieldName);
		}
		finally {
			if (jsonObject1 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					jsonObject1.getLong("id"));
			}

			if (jsonObject2 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					jsonObject2.getLong("id"));
			}
		}
	}

	@Test
	public void testSortByManyToOneAndOneToManyRelationshipsCustomObjectFields()
		throws Exception {

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition2, _objectDefinition1, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);
		_objectRelationship2 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition2, _objectDefinition3, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		String endpoint1 = _getEndpoint(
			TestPropsValues.getGroupId(), _objectDefinition1);
		String endpoint2 = _getEndpoint(
			TestPropsValues.getGroupId(), _objectDefinition2);
		String endpoint3 = _getEndpoint(
			TestPropsValues.getGroupId(), _objectDefinition3);

		BigDecimal randomBigDecimal = new BigDecimal(
			RandomTestUtil.randomDouble());
		Date randomDate1 = RandomTestUtil.nextDate();
		Date randomDate2 = RandomTestUtil.nextDate();
		float randomFloat1 = RandomTestUtil.randomFloat();
		int randomInt = RandomTestUtil.randomInt();
		long randomLong = RandomTestUtil.randomLong(
			ObjectFieldValidationConstants.BUSINESS_TYPE_LONG_VALUE_MIN,
			ObjectFieldValidationConstants.BUSINESS_TYPE_LONG_VALUE_MAX);
		String randomString1 = RandomTestUtil.randomString();
		String randomString2 = RandomTestUtil.randomString();

		JSONObject depth1JSONObject1 = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_BOOLEAN, false
			).put(
				_OBJECT_FIELD_NAME_DATE, _dateFormat.format(randomDate1)
			).put(
				_OBJECT_FIELD_NAME_DATE_TIME,
				_dateTimeDateFormat.format(randomDate2)
			).put(
				_OBJECT_FIELD_NAME_DECIMAL, randomFloat1
			).put(
				_OBJECT_FIELD_NAME_INTEGER, randomInt
			).put(
				_OBJECT_FIELD_NAME_LONG_INTEGER, randomLong
			).put(
				_OBJECT_FIELD_NAME_LONG_TEXT, "a" + randomString1
			).put(
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				JSONUtil.putAll(_LIST_TYPE_ENTRY_KEY_1, _LIST_TYPE_ENTRY_KEY_2)
			).put(
				_OBJECT_FIELD_NAME_PICKLIST, _LIST_TYPE_ENTRY_KEY_1
			).put(
				_OBJECT_FIELD_NAME_PRECISION_DECIMAL, randomBigDecimal
			).put(
				_OBJECT_FIELD_NAME_TEXT, "a" + randomString2
			).toString(),
			endpoint2, Http.Method.POST);

		JSONObject depth1JSONObject2 = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_BOOLEAN, true
			).put(
				_OBJECT_FIELD_NAME_DATE,
				() -> _dateFormat.format(
					new Date(randomDate1.getTime() + (2 * 24 * 3600 * 1000)))
			).put(
				_OBJECT_FIELD_NAME_DATE_TIME,
				_dateTimeDateFormat.format(
					new Date(randomDate2.getTime() + 2000))
			).put(
				_OBJECT_FIELD_NAME_DECIMAL, randomFloat1 + 2
			).put(
				_OBJECT_FIELD_NAME_INTEGER, randomInt + 2
			).put(
				_OBJECT_FIELD_NAME_LONG_INTEGER, randomLong + 2
			).put(
				_OBJECT_FIELD_NAME_LONG_TEXT, "c" + randomString1
			).put(
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				JSONUtil.putAll(_LIST_TYPE_ENTRY_KEY_2, _LIST_TYPE_ENTRY_KEY_3)
			).put(
				_OBJECT_FIELD_NAME_PICKLIST, _LIST_TYPE_ENTRY_KEY_2
			).put(
				_OBJECT_FIELD_NAME_PRECISION_DECIMAL,
				randomBigDecimal.add(new BigDecimal(2))
			).put(
				_OBJECT_FIELD_NAME_TEXT, "c" + randomString2
			).toString(),
			endpoint2, Http.Method.POST);

		JSONObject depth2JSONObject1 = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_BOOLEAN, false
			).put(
				_OBJECT_FIELD_NAME_DATE, _dateFormat.format(randomDate1)
			).put(
				_OBJECT_FIELD_NAME_DATE_TIME,
				_dateTimeDateFormat.format(randomDate2)
			).put(
				_OBJECT_FIELD_NAME_DECIMAL, randomFloat1
			).put(
				_OBJECT_FIELD_NAME_INTEGER, randomInt
			).put(
				_OBJECT_FIELD_NAME_LONG_INTEGER, randomLong
			).put(
				_OBJECT_FIELD_NAME_LONG_TEXT, "a" + randomString1
			).put(
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				JSONUtil.putAll(_LIST_TYPE_ENTRY_KEY_1, _LIST_TYPE_ENTRY_KEY_2)
			).put(
				_OBJECT_FIELD_NAME_PICKLIST, _LIST_TYPE_ENTRY_KEY_1
			).put(
				_OBJECT_FIELD_NAME_PRECISION_DECIMAL, randomBigDecimal
			).put(
				_OBJECT_FIELD_NAME_TEXT, "a" + randomString2
			).toString(),
			endpoint3, Http.Method.POST);

		JSONObject depth2JSONObject2 = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_BOOLEAN, false
			).put(
				_OBJECT_FIELD_NAME_DATE,
				() -> _dateFormat.format(
					new Date(randomDate1.getTime() + (2 * 24 * 3600 * 1000)))
			).put(
				_OBJECT_FIELD_NAME_DATE_TIME,
				_dateTimeDateFormat.format(
					new Date(randomDate2.getTime() + 2000))
			).put(
				_OBJECT_FIELD_NAME_DECIMAL, randomFloat1 + 2
			).put(
				_OBJECT_FIELD_NAME_INTEGER, randomInt + 2
			).put(
				_OBJECT_FIELD_NAME_LONG_INTEGER, randomLong + 2
			).put(
				_OBJECT_FIELD_NAME_LONG_TEXT, "c" + randomString1
			).put(
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				JSONUtil.putAll(_LIST_TYPE_ENTRY_KEY_1, _LIST_TYPE_ENTRY_KEY_2)
			).put(
				_OBJECT_FIELD_NAME_PICKLIST, _LIST_TYPE_ENTRY_KEY_2
			).put(
				_OBJECT_FIELD_NAME_PRECISION_DECIMAL,
				randomBigDecimal.add(new BigDecimal(2))
			).put(
				_OBJECT_FIELD_NAME_TEXT, "c" + randomString2
			).toString(),
			endpoint3, Http.Method.POST);

		JSONObject depth2JSONObject3 = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_BOOLEAN, true
			).put(
				_OBJECT_FIELD_NAME_DATE,
				() -> _dateFormat.format(
					new Date(randomDate1.getTime() + (24 * 3600 * 1000)))
			).put(
				_OBJECT_FIELD_NAME_DATE_TIME,
				_dateTimeDateFormat.format(
					new Date(randomDate2.getTime() + 1000))
			).put(
				_OBJECT_FIELD_NAME_DECIMAL, randomFloat1 + 1
			).put(
				_OBJECT_FIELD_NAME_INTEGER, randomInt + 1
			).put(
				_OBJECT_FIELD_NAME_LONG_INTEGER, randomLong + 1
			).put(
				_OBJECT_FIELD_NAME_LONG_TEXT, "b" + randomString1
			).put(
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				JSONUtil.putAll(_LIST_TYPE_ENTRY_KEY_2, _LIST_TYPE_ENTRY_KEY_3)
			).put(
				_OBJECT_FIELD_NAME_PICKLIST, _LIST_TYPE_ENTRY_KEY_2
			).put(
				_OBJECT_FIELD_NAME_PRECISION_DECIMAL,
				randomBigDecimal.add(BigDecimal.ONE)
			).put(
				_OBJECT_FIELD_NAME_TEXT, "b" + randomString2
			).toString(),
			endpoint3, Http.Method.POST);

		JSONObject depth2JSONObject4 = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_BOOLEAN, true
			).put(
				_OBJECT_FIELD_NAME_DATE,
				() -> _dateFormat.format(
					new Date(randomDate1.getTime() + (3 * 24 * 3600 * 1000)))
			).put(
				_OBJECT_FIELD_NAME_DATE_TIME,
				_dateTimeDateFormat.format(
					new Date(randomDate2.getTime() + 3000))
			).put(
				_OBJECT_FIELD_NAME_DECIMAL, randomFloat1 + 3
			).put(
				_OBJECT_FIELD_NAME_INTEGER, randomInt + 3
			).put(
				_OBJECT_FIELD_NAME_LONG_INTEGER, randomLong + 3
			).put(
				_OBJECT_FIELD_NAME_LONG_TEXT, "d" + randomString1
			).put(
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				JSONUtil.putAll(_LIST_TYPE_ENTRY_KEY_2, _LIST_TYPE_ENTRY_KEY_3)
			).put(
				_OBJECT_FIELD_NAME_PICKLIST, _LIST_TYPE_ENTRY_KEY_3
			).put(
				_OBJECT_FIELD_NAME_PRECISION_DECIMAL,
				randomBigDecimal.add(new BigDecimal(3))
			).put(
				_OBJECT_FIELD_NAME_TEXT, "d" + randomString2
			).toString(),
			endpoint3, Http.Method.POST);

		JSONObject jsonObject1 = HTTPTestUtil.invokeToJSONObject(
			JSONFactoryUtil.getNullJSON(
			).toString(),
			endpoint1, Http.Method.POST);

		JSONObject jsonObject2 = HTTPTestUtil.invokeToJSONObject(
			JSONFactoryUtil.getNullJSON(
			).toString(),
			endpoint1, Http.Method.POST);

		JSONObject jsonObject3 = HTTPTestUtil.invokeToJSONObject(
			JSONFactoryUtil.getNullJSON(
			).toString(),
			endpoint1, Http.Method.POST);

		JSONObject jsonObject4 = HTTPTestUtil.invokeToJSONObject(
			JSONFactoryUtil.getNullJSON(
			).toString(),
			endpoint1, Http.Method.POST);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint2, depth1JSONObject1.getLong("id"),
				_objectRelationship1.getName(), jsonObject1.getLong("id")),
			Http.Method.PUT);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint2, depth1JSONObject1.getLong("id"),
				_objectRelationship1.getName(), jsonObject2.getLong("id")),
			Http.Method.PUT);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint2, depth1JSONObject2.getLong("id"),
				_objectRelationship1.getName(), jsonObject3.getLong("id")),
			Http.Method.PUT);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint2, depth1JSONObject2.getLong("id"),
				_objectRelationship1.getName(), jsonObject4.getLong("id")),
			Http.Method.PUT);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint2, depth1JSONObject1.getLong("id"),
				_objectRelationship2.getName(),
				depth2JSONObject1.getLong("id")),
			Http.Method.PUT);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint2, depth1JSONObject1.getLong("id"),
				_objectRelationship2.getName(),
				depth2JSONObject2.getLong("id")),
			Http.Method.PUT);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint3, depth1JSONObject2.getLong("id"),
				_objectRelationship2.getName(),
				depth2JSONObject3.getLong("id")),
			Http.Method.PUT);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint3, depth1JSONObject2.getLong("id"),
				_objectRelationship2.getName(),
				depth2JSONObject4.getLong("id")),
			Http.Method.PUT);

		try {
			_testSortByManyToOneAndOneToManyRelationshipsCustomObjectFields(
				endpoint1, endpoint3, jsonObject3, jsonObject4, jsonObject1,
				jsonObject2, depth2JSONObject1, depth2JSONObject2,
				depth2JSONObject3, depth2JSONObject4,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_BOOLEAN));
			_testSortByManyToOneAndOneToManyRelationshipsCustomObjectFields(
				endpoint1, endpoint3, jsonObject3, jsonObject4, jsonObject1,
				jsonObject2, depth2JSONObject1, depth2JSONObject2,
				depth2JSONObject3, depth2JSONObject4,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_DATE));
			_testSortByManyToOneAndOneToManyRelationshipsCustomObjectFields(
				endpoint1, endpoint3, jsonObject3, jsonObject4, jsonObject1,
				jsonObject2, depth2JSONObject1, depth2JSONObject2,
				depth2JSONObject3, depth2JSONObject4,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_DATE_TIME));
			_testSortByManyToOneAndOneToManyRelationshipsCustomObjectFields(
				endpoint1, endpoint3, jsonObject3, jsonObject4, jsonObject1,
				jsonObject2, depth2JSONObject1, depth2JSONObject2,
				depth2JSONObject3, depth2JSONObject4,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_DECIMAL));
			_testSortByManyToOneAndOneToManyRelationshipsCustomObjectFields(
				endpoint1, endpoint3, jsonObject3, jsonObject4, jsonObject1,
				jsonObject2, depth2JSONObject1, depth2JSONObject2,
				depth2JSONObject3, depth2JSONObject4,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_INTEGER));
			_testSortByManyToOneAndOneToManyRelationshipsCustomObjectFields(
				endpoint1, endpoint3, jsonObject3, jsonObject4, jsonObject1,
				jsonObject2, depth2JSONObject1, depth2JSONObject2,
				depth2JSONObject3, depth2JSONObject4,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_LONG_INTEGER));
			_testSortByManyToOneAndOneToManyRelationshipsCustomObjectFields(
				endpoint1, endpoint3, jsonObject3, jsonObject4, jsonObject1,
				jsonObject2, depth2JSONObject1, depth2JSONObject2,
				depth2JSONObject3, depth2JSONObject4,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_LONG_TEXT));
			_testSortByManyToOneAndOneToManyRelationshipsCustomObjectFields(
				endpoint1, endpoint3, jsonObject3, jsonObject4, jsonObject1,
				jsonObject2, depth2JSONObject1, depth2JSONObject2,
				depth2JSONObject3, depth2JSONObject4,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST));
			_testSortByManyToOneAndOneToManyRelationshipsCustomObjectFields(
				endpoint1, endpoint3, jsonObject3, jsonObject4, jsonObject1,
				jsonObject2, depth2JSONObject1, depth2JSONObject2,
				depth2JSONObject3, depth2JSONObject4,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_PICKLIST));
			_testSortByManyToOneAndOneToManyRelationshipsCustomObjectFields(
				endpoint1, endpoint3, jsonObject3, jsonObject4, jsonObject1,
				jsonObject2, depth2JSONObject1, depth2JSONObject2,
				depth2JSONObject3, depth2JSONObject4,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_PRECISION_DECIMAL));
			_testSortByManyToOneAndOneToManyRelationshipsCustomObjectFields(
				endpoint1, endpoint3, jsonObject3, jsonObject4, jsonObject1,
				jsonObject2, depth2JSONObject1, depth2JSONObject2,
				depth2JSONObject3, depth2JSONObject4,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_TEXT));

			// Sort by several fields

			_testSortByManyToOneAndOneToManyRelationshipsCustomObjectFields(
				endpoint1, endpoint3, jsonObject3, jsonObject4, jsonObject1,
				jsonObject2, depth2JSONObject1, depth2JSONObject2,
				depth2JSONObject3, depth2JSONObject4,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_BOOLEAN),
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_DATE),
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_DATE_TIME),
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_DECIMAL),
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_INTEGER),
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_LONG_INTEGER),
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_LONG_TEXT),
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST),
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_PICKLIST),
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_PRECISION_DECIMAL),
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_TEXT));
		}
		finally {
			if (jsonObject1 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					jsonObject1.getLong("id"));
			}

			if (jsonObject2 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					jsonObject2.getLong("id"));
			}

			if (jsonObject3 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					jsonObject3.getLong("id"));
			}

			if (jsonObject4 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					jsonObject4.getLong("id"));
			}

			if (depth2JSONObject1 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					depth2JSONObject1.getLong("id"));
			}

			if (depth2JSONObject2 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					depth2JSONObject2.getLong("id"));
			}

			if (depth2JSONObject3 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					depth2JSONObject3.getLong("id"));
			}

			if (depth2JSONObject4 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					depth2JSONObject4.getLong("id"));
			}

			if (depth1JSONObject1 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					depth1JSONObject1.getLong("id"));
			}

			if (depth1JSONObject2 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					depth1JSONObject2.getLong("id"));
			}
		}
	}

	@Test
	public void testSortByManyToOneRelationshipCustomObjectFields()
		throws Exception {

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition2, _objectDefinition1, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);
		_objectRelationship2 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition3, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		String endpoint1 = _getEndpoint(
			TestPropsValues.getGroupId(), _objectDefinition1);
		String endpoint2 = _getEndpoint(
			TestPropsValues.getGroupId(), _objectDefinition2);
		String endpoint3 = _getEndpoint(
			TestPropsValues.getGroupId(), _objectDefinition3);

		BigDecimal randomBigDecimal = new BigDecimal(
			RandomTestUtil.randomDouble());
		Date randomDate1 = RandomTestUtil.nextDate();
		Date randomDate2 = RandomTestUtil.nextDate();
		float randomFloat1 = RandomTestUtil.randomFloat();
		int randomInt = RandomTestUtil.randomInt();
		long randomLong = RandomTestUtil.randomLong(
			ObjectFieldValidationConstants.BUSINESS_TYPE_LONG_VALUE_MIN,
			ObjectFieldValidationConstants.BUSINESS_TYPE_LONG_VALUE_MAX);
		String randomString1 = RandomTestUtil.randomString();
		String randomString2 = RandomTestUtil.randomString();

		JSONObject depth1JSONObject1 = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_BOOLEAN, false
			).put(
				_OBJECT_FIELD_NAME_DATE, _dateFormat.format(randomDate1)
			).put(
				_OBJECT_FIELD_NAME_DATE_TIME,
				_dateTimeDateFormat.format(randomDate2)
			).put(
				_OBJECT_FIELD_NAME_DECIMAL, randomFloat1
			).put(
				_OBJECT_FIELD_NAME_INTEGER, randomInt
			).put(
				_OBJECT_FIELD_NAME_LONG_INTEGER, randomLong
			).put(
				_OBJECT_FIELD_NAME_LONG_TEXT, "a" + randomString1
			).put(
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				JSONUtil.putAll(_LIST_TYPE_ENTRY_KEY_1, _LIST_TYPE_ENTRY_KEY_2)
			).put(
				_OBJECT_FIELD_NAME_PICKLIST, _LIST_TYPE_ENTRY_KEY_1
			).put(
				_OBJECT_FIELD_NAME_PRECISION_DECIMAL, randomBigDecimal
			).put(
				_OBJECT_FIELD_NAME_TEXT, "a" + randomString2
			).toString(),
			endpoint2, Http.Method.POST);

		JSONObject depth1JSONObject2 = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_BOOLEAN, true
			).put(
				_OBJECT_FIELD_NAME_DATE,
				() -> _dateFormat.format(
					new Date(randomDate1.getTime() + (2 * 24 * 3600 * 1000)))
			).put(
				_OBJECT_FIELD_NAME_DATE_TIME,
				_dateTimeDateFormat.format(
					new Date(randomDate2.getTime() + 2000))
			).put(
				_OBJECT_FIELD_NAME_DECIMAL, randomFloat1 + 2
			).put(
				_OBJECT_FIELD_NAME_INTEGER, randomInt + 2
			).put(
				_OBJECT_FIELD_NAME_LONG_INTEGER, randomLong + 2
			).put(
				_OBJECT_FIELD_NAME_LONG_TEXT, "c" + randomString1
			).put(
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				JSONUtil.putAll(_LIST_TYPE_ENTRY_KEY_2, _LIST_TYPE_ENTRY_KEY_3)
			).put(
				_OBJECT_FIELD_NAME_PICKLIST, _LIST_TYPE_ENTRY_KEY_2
			).put(
				_OBJECT_FIELD_NAME_PRECISION_DECIMAL,
				randomBigDecimal.add(new BigDecimal(2))
			).put(
				_OBJECT_FIELD_NAME_TEXT, "c" + randomString2
			).toString(),
			endpoint2, Http.Method.POST);

		JSONObject depth2JSONObject1 = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_BOOLEAN, false
			).put(
				_OBJECT_FIELD_NAME_DATE, _dateFormat.format(randomDate1)
			).put(
				_OBJECT_FIELD_NAME_DATE_TIME,
				_dateTimeDateFormat.format(randomDate2)
			).put(
				_OBJECT_FIELD_NAME_DECIMAL, randomFloat1
			).put(
				_OBJECT_FIELD_NAME_INTEGER, randomInt
			).put(
				_OBJECT_FIELD_NAME_LONG_INTEGER, randomLong
			).put(
				_OBJECT_FIELD_NAME_LONG_TEXT, "a" + randomString1
			).put(
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				JSONUtil.putAll(_LIST_TYPE_ENTRY_KEY_1, _LIST_TYPE_ENTRY_KEY_2)
			).put(
				_OBJECT_FIELD_NAME_PICKLIST, _LIST_TYPE_ENTRY_KEY_1
			).put(
				_OBJECT_FIELD_NAME_PRECISION_DECIMAL, randomBigDecimal
			).put(
				_OBJECT_FIELD_NAME_TEXT, "a" + randomString2
			).toString(),
			endpoint3, Http.Method.POST);

		JSONObject depth2JSONObject2 = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_BOOLEAN, true
			).put(
				_OBJECT_FIELD_NAME_DATE,
				() -> _dateFormat.format(
					new Date(randomDate1.getTime() + (2 * 24 * 3600 * 1000)))
			).put(
				_OBJECT_FIELD_NAME_DATE_TIME,
				_dateTimeDateFormat.format(
					new Date(randomDate2.getTime() + 2000))
			).put(
				_OBJECT_FIELD_NAME_DECIMAL, randomFloat1 + 2
			).put(
				_OBJECT_FIELD_NAME_INTEGER, randomInt + 2
			).put(
				_OBJECT_FIELD_NAME_LONG_INTEGER, randomLong + 2
			).put(
				_OBJECT_FIELD_NAME_LONG_TEXT, "c" + randomString1
			).put(
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				JSONUtil.putAll(_LIST_TYPE_ENTRY_KEY_2, _LIST_TYPE_ENTRY_KEY_3)
			).put(
				_OBJECT_FIELD_NAME_PICKLIST, _LIST_TYPE_ENTRY_KEY_2
			).put(
				_OBJECT_FIELD_NAME_PRECISION_DECIMAL,
				randomBigDecimal.add(new BigDecimal(2))
			).put(
				_OBJECT_FIELD_NAME_TEXT, "c" + randomString2
			).toString(),
			endpoint3, Http.Method.POST);

		JSONObject jsonObject1 = HTTPTestUtil.invokeToJSONObject(
			JSONFactoryUtil.getNullJSON(
			).toString(),
			endpoint1, Http.Method.POST);

		JSONObject jsonObject2 = HTTPTestUtil.invokeToJSONObject(
			JSONFactoryUtil.getNullJSON(
			).toString(),
			endpoint1, Http.Method.POST);

		JSONObject jsonObject3 = HTTPTestUtil.invokeToJSONObject(
			JSONFactoryUtil.getNullJSON(
			).toString(),
			endpoint1, Http.Method.POST);

		JSONObject jsonObject4 = HTTPTestUtil.invokeToJSONObject(
			JSONFactoryUtil.getNullJSON(
			).toString(),
			endpoint1, Http.Method.POST);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint2, depth1JSONObject1.getLong("id"),
				_objectRelationship1.getName(), jsonObject1.getLong("id")),
			Http.Method.PUT);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint2, depth1JSONObject1.getLong("id"),
				_objectRelationship1.getName(), jsonObject2.getLong("id")),
			Http.Method.PUT);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint2, depth1JSONObject2.getLong("id"),
				_objectRelationship1.getName(), jsonObject3.getLong("id")),
			Http.Method.PUT);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint2, depth1JSONObject2.getLong("id"),
				_objectRelationship1.getName(), jsonObject4.getLong("id")),
			Http.Method.PUT);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint3, depth2JSONObject1.getLong("id"),
				_objectRelationship2.getName(),
				depth1JSONObject1.getLong("id")),
			Http.Method.PUT);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint3, depth2JSONObject2.getLong("id"),
				_objectRelationship2.getName(),
				depth1JSONObject2.getLong("id")),
			Http.Method.PUT);

		try {

			// Depth 1

			_testSortByManyToOneRelationshipCustomObjectFields(
				endpoint1, endpoint2, jsonObject1, jsonObject2, jsonObject3,
				jsonObject4, depth1JSONObject1, depth1JSONObject2,
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_BOOLEAN));

			_testSortByManyToOneRelationshipCustomObjectFields(
				endpoint1, endpoint2, jsonObject1, jsonObject2, jsonObject3,
				jsonObject4, depth1JSONObject1, depth1JSONObject2,
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_DATE));
			_testSortByManyToOneRelationshipCustomObjectFields(
				endpoint1, endpoint2, jsonObject1, jsonObject2, jsonObject3,
				jsonObject4, depth1JSONObject1, depth1JSONObject2,
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_DATE_TIME));
			_testSortByManyToOneRelationshipCustomObjectFields(
				endpoint1, endpoint2, jsonObject1, jsonObject2, jsonObject3,
				jsonObject4, depth1JSONObject1, depth1JSONObject2,
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_DECIMAL));
			_testSortByManyToOneRelationshipCustomObjectFields(
				endpoint1, endpoint2, jsonObject1, jsonObject2, jsonObject3,
				jsonObject4, depth1JSONObject1, depth1JSONObject2,
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_INTEGER));
			_testSortByManyToOneRelationshipCustomObjectFields(
				endpoint1, endpoint2, jsonObject1, jsonObject2, jsonObject3,
				jsonObject4, depth1JSONObject1, depth1JSONObject2,
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_LONG_INTEGER));
			_testSortByManyToOneRelationshipCustomObjectFields(
				endpoint1, endpoint2, jsonObject1, jsonObject2, jsonObject3,
				jsonObject4, depth1JSONObject1, depth1JSONObject2,
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_LONG_TEXT));
			_testSortByManyToOneRelationshipCustomObjectFields(
				endpoint1, endpoint2, jsonObject1, jsonObject2, jsonObject3,
				jsonObject4, depth1JSONObject1, depth1JSONObject2,
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST));
			_testSortByManyToOneRelationshipCustomObjectFields(
				endpoint1, endpoint2, jsonObject1, jsonObject2, jsonObject3,
				jsonObject4, depth1JSONObject1, depth1JSONObject2,
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_PICKLIST));
			_testSortByManyToOneRelationshipCustomObjectFields(
				endpoint1, endpoint2, jsonObject1, jsonObject2, jsonObject3,
				jsonObject4, depth1JSONObject1, depth1JSONObject2,
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_PRECISION_DECIMAL));
			_testSortByManyToOneRelationshipCustomObjectFields(
				endpoint1, endpoint2, jsonObject1, jsonObject2, jsonObject3,
				jsonObject4, depth1JSONObject1, depth1JSONObject2,
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_TEXT));

			// Depth 2

			_testSortByManyToOneRelationshipCustomObjectFields(
				endpoint1, endpoint3, jsonObject1, jsonObject2, jsonObject3,
				jsonObject4, depth2JSONObject1, depth2JSONObject2,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_BOOLEAN));

			_testSortByManyToOneRelationshipCustomObjectFields(
				endpoint1, endpoint3, jsonObject1, jsonObject2, jsonObject3,
				jsonObject4, depth2JSONObject1, depth2JSONObject2,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_DATE));
			_testSortByManyToOneRelationshipCustomObjectFields(
				endpoint1, endpoint3, jsonObject1, jsonObject2, jsonObject3,
				jsonObject4, depth2JSONObject1, depth2JSONObject2,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_DATE_TIME));
			_testSortByManyToOneRelationshipCustomObjectFields(
				endpoint1, endpoint3, jsonObject1, jsonObject2, jsonObject3,
				jsonObject4, depth2JSONObject1, depth2JSONObject2,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_DECIMAL));
			_testSortByManyToOneRelationshipCustomObjectFields(
				endpoint1, endpoint3, jsonObject1, jsonObject2, jsonObject3,
				jsonObject4, depth2JSONObject1, depth2JSONObject2,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_INTEGER));
			_testSortByManyToOneRelationshipCustomObjectFields(
				endpoint1, endpoint3, jsonObject1, jsonObject2, jsonObject3,
				jsonObject4, depth2JSONObject1, depth2JSONObject2,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_LONG_INTEGER));
			_testSortByManyToOneRelationshipCustomObjectFields(
				endpoint1, endpoint3, jsonObject1, jsonObject2, jsonObject3,
				jsonObject4, depth2JSONObject1, depth2JSONObject2,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_LONG_TEXT));
			_testSortByManyToOneRelationshipCustomObjectFields(
				endpoint1, endpoint3, jsonObject1, jsonObject2, jsonObject3,
				jsonObject4, depth2JSONObject1, depth2JSONObject2,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST));
			_testSortByManyToOneRelationshipCustomObjectFields(
				endpoint1, endpoint3, jsonObject1, jsonObject2, jsonObject3,
				jsonObject4, depth2JSONObject1, depth2JSONObject2,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_PICKLIST));
			_testSortByManyToOneRelationshipCustomObjectFields(
				endpoint1, endpoint3, jsonObject1, jsonObject2, jsonObject3,
				jsonObject4, depth2JSONObject1, depth2JSONObject2,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_PRECISION_DECIMAL));
			_testSortByManyToOneRelationshipCustomObjectFields(
				endpoint1, endpoint3, jsonObject1, jsonObject2, jsonObject3,
				jsonObject4, depth2JSONObject1, depth2JSONObject2,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_TEXT));

			// Sort by several fields

			_testSortByManyToOneRelationshipCustomObjectFields(
				endpoint1, endpoint2, jsonObject1, jsonObject2, jsonObject3,
				jsonObject4, depth1JSONObject1, depth1JSONObject2,
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_BOOLEAN),
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_DATE),
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_DATE_TIME),
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_DECIMAL),
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_INTEGER),
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_LONG_INTEGER),
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_LONG_TEXT),
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST),
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_PICKLIST),
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_PRECISION_DECIMAL),
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_TEXT));

			_testSortByManyToOneRelationshipCustomObjectFields(
				endpoint1, endpoint3, jsonObject1, jsonObject2, jsonObject3,
				jsonObject4, depth2JSONObject1, depth2JSONObject2,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_BOOLEAN),
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_DATE),
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_DATE_TIME),
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_DECIMAL),
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_INTEGER),
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_LONG_INTEGER),
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_LONG_TEXT),
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST),
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_PICKLIST),
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_PRECISION_DECIMAL),
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_TEXT));
		}
		finally {
			if (jsonObject1 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					jsonObject1.getLong("id"));
			}

			if (jsonObject2 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					jsonObject2.getLong("id"));
			}

			if (jsonObject3 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					jsonObject3.getLong("id"));
			}

			if (jsonObject4 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					jsonObject4.getLong("id"));
			}

			if (depth1JSONObject1 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					depth1JSONObject1.getLong("id"));
			}

			if (depth1JSONObject2 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					depth1JSONObject2.getLong("id"));
			}

			if (depth2JSONObject1 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					depth2JSONObject1.getLong("id"));
			}

			if (depth2JSONObject2 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					depth2JSONObject2.getLong("id"));
			}
		}
	}

	@Test
	public void testSortByManyToOneRelationshipSystemObjectFields()
		throws Exception {

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition2, _objectDefinition1, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);
		_objectRelationship2 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition3, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		_addResourcePermission(
			ObjectActionKeys.ADD_OBJECT_ENTRY, _objectDefinition2, role);
		_addResourcePermission(
			ObjectActionKeys.ADD_OBJECT_ENTRY, _objectDefinition3, role);

		User user1 = _addUser("test1", "test1");
		User user2 = _addUser("test2", "test2");
		User user3 = _addUser("test3", "test3");

		_roleLocalService.addUserRole(user1.getUserId(), role.getRoleId());
		_roleLocalService.addUserRole(user2.getUserId(), role.getRoleId());
		_roleLocalService.addUserRole(user3.getUserId(), role.getRoleId());

		_objectDefinition1.setEnableObjectEntryDraft(true);
		_objectDefinition2.setEnableObjectEntryDraft(true);
		_objectDefinition3.setEnableObjectEntryDraft(true);

		_objectDefinition1 =
			_objectDefinitionLocalService.updateObjectDefinition(
				_objectDefinition1);
		_objectDefinition2 =
			_objectDefinitionLocalService.updateObjectDefinition(
				_objectDefinition2);
		_objectDefinition3 =
			_objectDefinitionLocalService.updateObjectDefinition(
				_objectDefinition3);

		String endpoint1 = _getEndpoint(
			TestPropsValues.getGroupId(), _objectDefinition1);
		String endpoint2 = _getEndpoint(
			TestPropsValues.getGroupId(), _objectDefinition2);
		String endpoint3 = _getEndpoint(
			TestPropsValues.getGroupId(), _objectDefinition3);

		JSONObject[] manyToOneDepth1JSONObjects = new JSONObject[2];

		HTTPTestUtil.customize(
		).withCredentials(
			"test3@liferay.com", "test3"
		).apply(
			() ->
				manyToOneDepth1JSONObjects[0] = HTTPTestUtil.invokeToJSONObject(
					JSONUtil.put(
						"externalReferenceCode", "ERC2_1"
					).put(
						"status",
						JSONUtil.put("code", WorkflowConstants.STATUS_DRAFT)
					).toString(),
					endpoint2, Http.Method.POST)
		);

		HTTPTestUtil.customize(
		).withCredentials(
			"test2@liferay.com", "test2"
		).apply(
			() ->
				manyToOneDepth1JSONObjects[1] = HTTPTestUtil.invokeToJSONObject(
					JSONUtil.put(
						"externalReferenceCode", "ERC1_1"
					).put(
						"status",
						JSONUtil.put("code", WorkflowConstants.STATUS_DRAFT)
					).toString(),
					endpoint2, Http.Method.POST)
		);

		JSONObject[] manyToOneDepth2JSONObjects = new JSONObject[2];

		HTTPTestUtil.customize(
		).withCredentials(
			"test2@liferay.com", "test2"
		).apply(
			() ->
				manyToOneDepth2JSONObjects[0] = HTTPTestUtil.invokeToJSONObject(
					JSONUtil.put(
						"externalReferenceCode", "ERC2_2"
					).put(
						"status",
						JSONUtil.put("code", WorkflowConstants.STATUS_DRAFT)
					).toString(),
					endpoint3, Http.Method.POST)
		);

		HTTPTestUtil.customize(
		).withCredentials(
			"test1@liferay.com", "test1"
		).apply(
			() ->
				manyToOneDepth2JSONObjects[1] = HTTPTestUtil.invokeToJSONObject(
					JSONUtil.put(
						"externalReferenceCode", "ERC1_2"
					).put(
						"status",
						JSONUtil.put("code", WorkflowConstants.STATUS_DRAFT)
					).toString(),
					endpoint3, Http.Method.POST)
		);

		JSONObject jsonObject1 = HTTPTestUtil.invokeToJSONObject(
			JSONFactoryUtil.getNullJSON(
			).toString(),
			endpoint1, Http.Method.POST);

		JSONObject jsonObject2 = HTTPTestUtil.invokeToJSONObject(
			JSONFactoryUtil.getNullJSON(
			).toString(),
			endpoint1, Http.Method.POST);

		JSONObject jsonObject3 = HTTPTestUtil.invokeToJSONObject(
			JSONFactoryUtil.getNullJSON(
			).toString(),
			endpoint1, Http.Method.POST);

		JSONObject jsonObject4 = HTTPTestUtil.invokeToJSONObject(
			JSONFactoryUtil.getNullJSON(
			).toString(),
			endpoint1, Http.Method.POST);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint2,
				manyToOneDepth1JSONObjects[0].getLong("id"),
				_objectRelationship1.getName(), jsonObject1.getLong("id")),
			Http.Method.PUT);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint2,
				manyToOneDepth1JSONObjects[0].getLong("id"),
				_objectRelationship1.getName(), jsonObject2.getLong("id")),
			Http.Method.PUT);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint2,
				manyToOneDepth1JSONObjects[1].getLong("id"),
				_objectRelationship1.getName(), jsonObject3.getLong("id")),
			Http.Method.PUT);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint2,
				manyToOneDepth1JSONObjects[1].getLong("id"),
				_objectRelationship1.getName(), jsonObject4.getLong("id")),
			Http.Method.PUT);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint3,
				manyToOneDepth2JSONObjects[0].getLong("id"),
				_objectRelationship2.getName(),
				manyToOneDepth1JSONObjects[0].getLong("id")),
			Http.Method.PUT);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint3,
				manyToOneDepth2JSONObjects[1].getLong("id"),
				_objectRelationship2.getName(),
				manyToOneDepth1JSONObjects[1].getLong("id")),
			Http.Method.PUT);

		HTTPTestUtil.customize(
		).withCredentials(
			"test3@liferay.com", "test3"
		).apply(
			() ->
				manyToOneDepth1JSONObjects[0] = HTTPTestUtil.invokeToJSONObject(
					JSONUtil.put(
						_OBJECT_FIELD_NAME_2, RandomTestUtil.randomString()
					).toString(),
					endpoint2 + "/by-external-reference-code/ERC2_1",
					Http.Method.PATCH)
		);

		HTTPTestUtil.customize(
		).withCredentials(
			"test2@liferay.com", "test2"
		).apply(
			() ->
				manyToOneDepth1JSONObjects[1] = HTTPTestUtil.invokeToJSONObject(
					JSONUtil.put(
						_OBJECT_FIELD_NAME_2, RandomTestUtil.randomString()
					).toString(),
					endpoint2 + "/by-external-reference-code/ERC1_1",
					Http.Method.PATCH)
		);

		HTTPTestUtil.customize(
		).withCredentials(
			"test2@liferay.com", "test2"
		).apply(
			() ->
				manyToOneDepth2JSONObjects[0] = HTTPTestUtil.invokeToJSONObject(
					JSONUtil.put(
						_OBJECT_FIELD_NAME_3, RandomTestUtil.randomString()
					).toString(),
					endpoint3 + "/by-external-reference-code/ERC2_2",
					Http.Method.PATCH)
		);

		HTTPTestUtil.customize(
		).withCredentials(
			"test1@liferay.com", "test1"
		).apply(
			() ->
				manyToOneDepth2JSONObjects[1] = HTTPTestUtil.invokeToJSONObject(
					JSONUtil.put(
						_OBJECT_FIELD_NAME_3, RandomTestUtil.randomString()
					).toString(),
					endpoint3 + "/by-external-reference-code/ERC1_2",
					Http.Method.PATCH)
		);

		try {

			// Depth 1

			_testSortByFieldName(
				endpoint1, jsonObject3, jsonObject4, jsonObject1, jsonObject2,
				String.format("%s/creator", _objectRelationship1.getName()));
			_testSortByFieldName(
				endpoint1, jsonObject3, jsonObject4, jsonObject1, jsonObject2,
				String.format("%s/creatorId", _objectRelationship1.getName()));
			_testSortByFieldName(
				endpoint1, jsonObject1, jsonObject2, jsonObject3, jsonObject4,
				String.format(
					"%s/dateCreated", _objectRelationship1.getName()));
			_testSortByFieldName(
				endpoint1, jsonObject1, jsonObject2, jsonObject3, jsonObject4,
				String.format(
					"%s/dateModified", _objectRelationship1.getName()));
			_testSortByFieldName(
				endpoint1, jsonObject3, jsonObject4, jsonObject1, jsonObject2,
				String.format(
					"%s/externalReferenceCode",
					_objectRelationship1.getName()));
			_testSortByFieldName(
				endpoint1, jsonObject1, jsonObject2, jsonObject3, jsonObject4,
				String.format("%s/id", _objectRelationship1.getName()));

			// Depth 2

			_testSortByFieldName(
				endpoint1, jsonObject3, jsonObject4, jsonObject1, jsonObject2,
				String.format(
					"%s/%s/creator", _objectRelationship1.getName(),
					_objectRelationship2.getName()));
			_testSortByFieldName(
				endpoint1, jsonObject3, jsonObject4, jsonObject1, jsonObject2,
				String.format(
					"%s/%s/creatorId", _objectRelationship1.getName(),
					_objectRelationship2.getName()));
			_testSortByFieldName(
				endpoint1, jsonObject1, jsonObject2, jsonObject3, jsonObject4,
				String.format(
					"%s/%s/dateCreated", _objectRelationship1.getName(),
					_objectRelationship2.getName()));
			_testSortByFieldName(
				endpoint1, jsonObject1, jsonObject2, jsonObject3, jsonObject4,
				String.format(
					"%s/%s/dateModified", _objectRelationship1.getName(),
					_objectRelationship2.getName()));
			_testSortByFieldName(
				endpoint1, jsonObject3, jsonObject4, jsonObject1, jsonObject2,
				String.format(
					"%s/%s/externalReferenceCode",
					_objectRelationship1.getName(),
					_objectRelationship2.getName()));
			_testSortByFieldName(
				endpoint1, jsonObject1, jsonObject2, jsonObject3, jsonObject4,
				String.format(
					"%s/%s/id", _objectRelationship1.getName(),
					_objectRelationship2.getName()));
			_testSortByFieldName(
				endpoint1, jsonObject3, jsonObject4, jsonObject1, jsonObject2,
				String.format("%s/userId", _objectRelationship1.getName()));

			// Sort by several fields

			_testSortByFieldName(
				endpoint1, jsonObject1, jsonObject2, jsonObject3, jsonObject4,
				String.format("%s/dateCreated", _objectRelationship1.getName()),
				String.format("%s/id", _objectRelationship1.getName()),
				String.format(
					"%s/%s/dateCreated", _objectRelationship1.getName(),
					_objectRelationship2.getName()),
				String.format(
					"%s/%s/id", _objectRelationship1.getName(),
					_objectRelationship2.getName()));
			_testSortByFieldName(
				endpoint1, jsonObject3, jsonObject4, jsonObject1, jsonObject2,
				String.format("%s/creator", _objectRelationship1.getName()),
				String.format("%s/creatorId", _objectRelationship1.getName()),
				String.format(
					"%s/dateModified", _objectRelationship1.getName()),
				String.format(
					"%s/externalReferenceCode", _objectRelationship1.getName()),
				String.format("%s/userId", _objectRelationship1.getName()),
				String.format(
					"%s/%s/creator", _objectRelationship1.getName(),
					_objectRelationship2.getName()),
				String.format(
					"%s/%s/creatorId", _objectRelationship1.getName(),
					_objectRelationship2.getName()),
				String.format(
					"%s/%s/dateModified", _objectRelationship1.getName(),
					_objectRelationship2.getName()),
				String.format(
					"%s/%s/externalReferenceCode",
					_objectRelationship1.getName(),
					_objectRelationship2.getName()),
				String.format(
					"%s/%s/userId", _objectRelationship1.getName(),
					_objectRelationship2.getName()));

			// TODO LPD-20530

			_assertFailure(
				ComparisonFailure.class,
				() -> _testSortByFieldName(
					endpoint1, jsonObject1, jsonObject2, jsonObject3,
					jsonObject4,
					String.format(
						"%s/status", _objectRelationship1.getName())));
			_assertFailure(
				ComparisonFailure.class,
				() -> _testSortByFieldName(
					endpoint1, jsonObject1, jsonObject2, jsonObject3,
					jsonObject4,
					String.format(
						"%s/%s/status", _objectRelationship1.getName(),
						_objectRelationship2.getName())));
		}
		finally {
			if (jsonObject1 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					jsonObject1.getLong("id"));
			}

			if (jsonObject2 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					jsonObject2.getLong("id"));
			}

			if (jsonObject3 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					jsonObject3.getLong("id"));
			}

			if (jsonObject4 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					jsonObject4.getLong("id"));
			}

			for (JSONObject jsonObject :
					ArrayUtil.append(
						manyToOneDepth1JSONObjects,
						manyToOneDepth2JSONObjects)) {

				if (jsonObject == null) {
					continue;
				}

				_objectEntryLocalService.deleteObjectEntry(
					jsonObject.getLong("id"));
			}
		}
	}

	@Test
	public void testSortByOneToManyRelationshipCustomObjectFields()
		throws Exception {

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);
		_objectRelationship2 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition2, _objectDefinition3, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		String endpoint1 = _getEndpoint(
			TestPropsValues.getGroupId(), _objectDefinition1);
		String endpoint2 = _getEndpoint(
			TestPropsValues.getGroupId(), _objectDefinition2);
		String endpoint3 = _getEndpoint(
			TestPropsValues.getGroupId(), _objectDefinition3);

		BigDecimal randomBigDecimal = new BigDecimal(
			RandomTestUtil.randomDouble());
		Date randomDate1 = RandomTestUtil.nextDate();
		Date randomDate2 = RandomTestUtil.nextDate();
		float randomFloat1 = RandomTestUtil.randomFloat();
		int randomInt = RandomTestUtil.randomInt();
		long randomLong = RandomTestUtil.randomLong(
			ObjectFieldValidationConstants.BUSINESS_TYPE_LONG_VALUE_MIN,
			ObjectFieldValidationConstants.BUSINESS_TYPE_LONG_VALUE_MAX);
		String randomString1 = RandomTestUtil.randomString();
		String randomString2 = RandomTestUtil.randomString();

		JSONObject depth1JSONObject1 = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_BOOLEAN, false
			).put(
				_OBJECT_FIELD_NAME_DATE, _dateFormat.format(randomDate1)
			).put(
				_OBJECT_FIELD_NAME_DATE_TIME,
				_dateTimeDateFormat.format(randomDate2)
			).put(
				_OBJECT_FIELD_NAME_DECIMAL, randomFloat1
			).put(
				_OBJECT_FIELD_NAME_INTEGER, randomInt
			).put(
				_OBJECT_FIELD_NAME_LONG_INTEGER, randomLong
			).put(
				_OBJECT_FIELD_NAME_LONG_TEXT, "a" + randomString1
			).put(
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				JSONUtil.putAll(_LIST_TYPE_ENTRY_KEY_1, _LIST_TYPE_ENTRY_KEY_2)
			).put(
				_OBJECT_FIELD_NAME_PICKLIST, _LIST_TYPE_ENTRY_KEY_1
			).put(
				_OBJECT_FIELD_NAME_PRECISION_DECIMAL, randomBigDecimal
			).put(
				_OBJECT_FIELD_NAME_TEXT, "a" + randomString2
			).toString(),
			endpoint2, Http.Method.POST);

		JSONObject depth1JSONObject2 = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_BOOLEAN, false
			).put(
				_OBJECT_FIELD_NAME_DATE,
				() -> _dateFormat.format(
					new Date(randomDate1.getTime() + (2 * 24 * 3600 * 1000)))
			).put(
				_OBJECT_FIELD_NAME_DATE_TIME,
				_dateTimeDateFormat.format(
					new Date(randomDate2.getTime() + 2000))
			).put(
				_OBJECT_FIELD_NAME_DECIMAL, randomFloat1 + 2
			).put(
				_OBJECT_FIELD_NAME_INTEGER, randomInt + 2
			).put(
				_OBJECT_FIELD_NAME_LONG_INTEGER, randomLong + 2
			).put(
				_OBJECT_FIELD_NAME_LONG_TEXT, "c" + randomString1
			).put(
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				JSONUtil.putAll(_LIST_TYPE_ENTRY_KEY_1, _LIST_TYPE_ENTRY_KEY_2)
			).put(
				_OBJECT_FIELD_NAME_PICKLIST, _LIST_TYPE_ENTRY_KEY_2
			).put(
				_OBJECT_FIELD_NAME_PRECISION_DECIMAL,
				randomBigDecimal.add(new BigDecimal(2))
			).put(
				_OBJECT_FIELD_NAME_TEXT, "c" + randomString2
			).toString(),
			endpoint2, Http.Method.POST);

		JSONObject depth1JSONObject3 = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_BOOLEAN, true
			).put(
				_OBJECT_FIELD_NAME_DATE,
				() -> _dateFormat.format(
					new Date(randomDate1.getTime() + (24 * 3600 * 1000)))
			).put(
				_OBJECT_FIELD_NAME_DATE_TIME,
				_dateTimeDateFormat.format(
					new Date(randomDate2.getTime() + 1000))
			).put(
				_OBJECT_FIELD_NAME_DECIMAL, randomFloat1 + 1
			).put(
				_OBJECT_FIELD_NAME_INTEGER, randomInt + 1
			).put(
				_OBJECT_FIELD_NAME_LONG_INTEGER, randomLong + 1
			).put(
				_OBJECT_FIELD_NAME_LONG_TEXT, "b" + randomString1
			).put(
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				JSONUtil.putAll(_LIST_TYPE_ENTRY_KEY_2, _LIST_TYPE_ENTRY_KEY_3)
			).put(
				_OBJECT_FIELD_NAME_PICKLIST, _LIST_TYPE_ENTRY_KEY_2
			).put(
				_OBJECT_FIELD_NAME_PRECISION_DECIMAL,
				randomBigDecimal.add(BigDecimal.ONE)
			).put(
				_OBJECT_FIELD_NAME_TEXT, "b" + randomString2
			).toString(),
			endpoint2, Http.Method.POST);

		JSONObject depth1JSONObject4 = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_BOOLEAN, true
			).put(
				_OBJECT_FIELD_NAME_DATE,
				() -> _dateFormat.format(
					new Date(randomDate1.getTime() + (3 * 24 * 3600 * 1000)))
			).put(
				_OBJECT_FIELD_NAME_DATE_TIME,
				_dateTimeDateFormat.format(
					new Date(randomDate2.getTime() + 3000))
			).put(
				_OBJECT_FIELD_NAME_DECIMAL, randomFloat1 + 3
			).put(
				_OBJECT_FIELD_NAME_INTEGER, randomInt + 3
			).put(
				_OBJECT_FIELD_NAME_LONG_INTEGER, randomLong + 3
			).put(
				_OBJECT_FIELD_NAME_LONG_TEXT, "d" + randomString1
			).put(
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				JSONUtil.putAll(_LIST_TYPE_ENTRY_KEY_2, _LIST_TYPE_ENTRY_KEY_3)
			).put(
				_OBJECT_FIELD_NAME_PICKLIST, _LIST_TYPE_ENTRY_KEY_3
			).put(
				_OBJECT_FIELD_NAME_PRECISION_DECIMAL,
				randomBigDecimal.add(new BigDecimal(3))
			).put(
				_OBJECT_FIELD_NAME_TEXT, "d" + randomString2
			).toString(),
			endpoint2, Http.Method.POST);

		JSONObject depth2JSONObject1 = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_BOOLEAN, false
			).put(
				_OBJECT_FIELD_NAME_DATE, _dateFormat.format(randomDate1)
			).put(
				_OBJECT_FIELD_NAME_DATE_TIME,
				_dateTimeDateFormat.format(randomDate2)
			).put(
				_OBJECT_FIELD_NAME_DECIMAL, randomFloat1
			).put(
				_OBJECT_FIELD_NAME_INTEGER, randomInt
			).put(
				_OBJECT_FIELD_NAME_LONG_INTEGER, randomLong
			).put(
				_OBJECT_FIELD_NAME_LONG_TEXT, "a" + randomString1
			).put(
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				JSONUtil.putAll(_LIST_TYPE_ENTRY_KEY_1, _LIST_TYPE_ENTRY_KEY_2)
			).put(
				_OBJECT_FIELD_NAME_PICKLIST, _LIST_TYPE_ENTRY_KEY_1
			).put(
				_OBJECT_FIELD_NAME_PRECISION_DECIMAL, randomBigDecimal
			).put(
				_OBJECT_FIELD_NAME_TEXT, "a" + randomString2
			).toString(),
			endpoint3, Http.Method.POST);

		JSONObject depth2JSONObject2 = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_BOOLEAN, false
			).put(
				_OBJECT_FIELD_NAME_DATE,
				() -> _dateFormat.format(
					new Date(randomDate1.getTime() + (2 * 24 * 3600 * 1000)))
			).put(
				_OBJECT_FIELD_NAME_DATE_TIME,
				_dateTimeDateFormat.format(
					new Date(randomDate2.getTime() + 2000))
			).put(
				_OBJECT_FIELD_NAME_DECIMAL, randomFloat1 + 2
			).put(
				_OBJECT_FIELD_NAME_INTEGER, randomInt + 2
			).put(
				_OBJECT_FIELD_NAME_LONG_INTEGER, randomLong + 2
			).put(
				_OBJECT_FIELD_NAME_LONG_TEXT, "c" + randomString1
			).put(
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				JSONUtil.putAll(_LIST_TYPE_ENTRY_KEY_1, _LIST_TYPE_ENTRY_KEY_2)
			).put(
				_OBJECT_FIELD_NAME_PICKLIST, _LIST_TYPE_ENTRY_KEY_2
			).put(
				_OBJECT_FIELD_NAME_PRECISION_DECIMAL,
				randomBigDecimal.add(new BigDecimal(2))
			).put(
				_OBJECT_FIELD_NAME_TEXT, "c" + randomString2
			).toString(),
			endpoint3, Http.Method.POST);

		JSONObject depth2JSONObject3 = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_BOOLEAN, true
			).put(
				_OBJECT_FIELD_NAME_DATE,
				() -> _dateFormat.format(
					new Date(randomDate1.getTime() + (24 * 3600 * 1000)))
			).put(
				_OBJECT_FIELD_NAME_DATE_TIME,
				_dateTimeDateFormat.format(
					new Date(randomDate2.getTime() + 1000))
			).put(
				_OBJECT_FIELD_NAME_DECIMAL, randomFloat1 + 1
			).put(
				_OBJECT_FIELD_NAME_INTEGER, randomInt + 1
			).put(
				_OBJECT_FIELD_NAME_LONG_INTEGER, randomLong + 1
			).put(
				_OBJECT_FIELD_NAME_LONG_TEXT, "b" + randomString1
			).put(
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				JSONUtil.putAll(_LIST_TYPE_ENTRY_KEY_2, _LIST_TYPE_ENTRY_KEY_3)
			).put(
				_OBJECT_FIELD_NAME_PICKLIST, _LIST_TYPE_ENTRY_KEY_2
			).put(
				_OBJECT_FIELD_NAME_PRECISION_DECIMAL,
				randomBigDecimal.add(BigDecimal.ONE)
			).put(
				_OBJECT_FIELD_NAME_TEXT, "b" + randomString2
			).toString(),
			endpoint3, Http.Method.POST);

		JSONObject depth2JSONObject4 = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_BOOLEAN, true
			).put(
				_OBJECT_FIELD_NAME_DATE,
				() -> _dateFormat.format(
					new Date(randomDate1.getTime() + (3 * 24 * 3600 * 1000)))
			).put(
				_OBJECT_FIELD_NAME_DATE_TIME,
				_dateTimeDateFormat.format(
					new Date(randomDate2.getTime() + 3000))
			).put(
				_OBJECT_FIELD_NAME_DECIMAL, randomFloat1 + 3
			).put(
				_OBJECT_FIELD_NAME_INTEGER, randomInt + 3
			).put(
				_OBJECT_FIELD_NAME_LONG_INTEGER, randomLong + 3
			).put(
				_OBJECT_FIELD_NAME_LONG_TEXT, "d" + randomString1
			).put(
				_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST,
				JSONUtil.putAll(_LIST_TYPE_ENTRY_KEY_2, _LIST_TYPE_ENTRY_KEY_3)
			).put(
				_OBJECT_FIELD_NAME_PICKLIST, _LIST_TYPE_ENTRY_KEY_3
			).put(
				_OBJECT_FIELD_NAME_PRECISION_DECIMAL,
				randomBigDecimal.add(new BigDecimal(3))
			).put(
				_OBJECT_FIELD_NAME_TEXT, "d" + randomString2
			).toString(),
			endpoint3, Http.Method.POST);

		JSONObject jsonObject1 = HTTPTestUtil.invokeToJSONObject(
			JSONFactoryUtil.getNullJSON(
			).toString(),
			endpoint1, Http.Method.POST);

		JSONObject jsonObject2 = HTTPTestUtil.invokeToJSONObject(
			JSONFactoryUtil.getNullJSON(
			).toString(),
			endpoint1, Http.Method.POST);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint1, jsonObject1.getLong("id"),
				_objectRelationship1.getName(),
				depth1JSONObject1.getLong("id")),
			Http.Method.PUT);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint1, jsonObject1.getLong("id"),
				_objectRelationship1.getName(),
				depth1JSONObject2.getLong("id")),
			Http.Method.PUT);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint1, jsonObject2.getLong("id"),
				_objectRelationship1.getName(),
				depth1JSONObject3.getLong("id")),
			Http.Method.PUT);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint1, jsonObject2.getLong("id"),
				_objectRelationship1.getName(),
				depth1JSONObject4.getLong("id")),
			Http.Method.PUT);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint2, depth1JSONObject1.getLong("id"),
				_objectRelationship2.getName(),
				depth2JSONObject1.getLong("id")),
			Http.Method.PUT);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint2, depth1JSONObject2.getLong("id"),
				_objectRelationship2.getName(),
				depth2JSONObject2.getLong("id")),
			Http.Method.PUT);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint2, depth1JSONObject3.getLong("id"),
				_objectRelationship2.getName(),
				depth2JSONObject3.getLong("id")),
			Http.Method.PUT);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint2, depth1JSONObject4.getLong("id"),
				_objectRelationship2.getName(),
				depth2JSONObject4.getLong("id")),
			Http.Method.PUT);

		try {

			// Depth 1

			_testSortByOneToManyRelationshipCustomObjectFields(
				endpoint1, endpoint2, jsonObject1, jsonObject2,
				depth1JSONObject1, depth1JSONObject2, depth1JSONObject3,
				depth1JSONObject4,
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_BOOLEAN));
			_testSortByOneToManyRelationshipCustomObjectFields(
				endpoint1, endpoint2, jsonObject1, jsonObject2,
				depth1JSONObject1, depth1JSONObject2, depth1JSONObject3,
				depth1JSONObject4,
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_DATE));
			_testSortByOneToManyRelationshipCustomObjectFields(
				endpoint1, endpoint2, jsonObject1, jsonObject2,
				depth1JSONObject1, depth1JSONObject2, depth1JSONObject3,
				depth1JSONObject4,
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_DATE_TIME));
			_testSortByOneToManyRelationshipCustomObjectFields(
				endpoint1, endpoint2, jsonObject1, jsonObject2,
				depth1JSONObject1, depth1JSONObject2, depth1JSONObject3,
				depth1JSONObject4,
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_DECIMAL));
			_testSortByOneToManyRelationshipCustomObjectFields(
				endpoint1, endpoint2, jsonObject1, jsonObject2,
				depth1JSONObject1, depth1JSONObject2, depth1JSONObject3,
				depth1JSONObject4,
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_INTEGER));
			_testSortByOneToManyRelationshipCustomObjectFields(
				endpoint1, endpoint2, jsonObject1, jsonObject2,
				depth1JSONObject1, depth1JSONObject2, depth1JSONObject3,
				depth1JSONObject4,
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_LONG_INTEGER));
			_testSortByOneToManyRelationshipCustomObjectFields(
				endpoint1, endpoint2, jsonObject1, jsonObject2,
				depth1JSONObject1, depth1JSONObject2, depth1JSONObject3,
				depth1JSONObject4,
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_LONG_TEXT));
			_testSortByOneToManyRelationshipCustomObjectFields(
				endpoint1, endpoint2, jsonObject1, jsonObject2,
				depth1JSONObject1, depth1JSONObject2, depth1JSONObject3,
				depth1JSONObject4,
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST));
			_testSortByOneToManyRelationshipCustomObjectFields(
				endpoint1, endpoint2, jsonObject1, jsonObject2,
				depth1JSONObject1, depth1JSONObject2, depth1JSONObject3,
				depth1JSONObject4,
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_PICKLIST));
			_testSortByOneToManyRelationshipCustomObjectFields(
				endpoint1, endpoint2, jsonObject1, jsonObject2,
				depth1JSONObject1, depth1JSONObject2, depth1JSONObject3,
				depth1JSONObject4,
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_PRECISION_DECIMAL));
			_testSortByOneToManyRelationshipCustomObjectFields(
				endpoint1, endpoint2, jsonObject1, jsonObject2,
				depth1JSONObject1, depth1JSONObject2, depth1JSONObject3,
				depth1JSONObject4,
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_TEXT));

			// Depth 2

			_testSortByOneToManyRelationshipCustomObjectFields(
				endpoint1, endpoint3, jsonObject1, jsonObject2,
				depth2JSONObject1, depth2JSONObject2, depth2JSONObject3,
				depth2JSONObject4,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_BOOLEAN));
			_testSortByOneToManyRelationshipCustomObjectFields(
				endpoint1, endpoint3, jsonObject1, jsonObject2,
				depth2JSONObject1, depth2JSONObject2, depth2JSONObject3,
				depth2JSONObject4,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_DATE));
			_testSortByOneToManyRelationshipCustomObjectFields(
				endpoint1, endpoint3, jsonObject1, jsonObject2,
				depth2JSONObject1, depth2JSONObject2, depth2JSONObject3,
				depth2JSONObject4,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_DATE_TIME));
			_testSortByOneToManyRelationshipCustomObjectFields(
				endpoint1, endpoint3, jsonObject1, jsonObject2,
				depth2JSONObject1, depth2JSONObject2, depth2JSONObject3,
				depth2JSONObject4,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_DECIMAL));
			_testSortByOneToManyRelationshipCustomObjectFields(
				endpoint1, endpoint3, jsonObject1, jsonObject2,
				depth2JSONObject1, depth2JSONObject2, depth2JSONObject3,
				depth2JSONObject4,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_INTEGER));
			_testSortByOneToManyRelationshipCustomObjectFields(
				endpoint1, endpoint3, jsonObject1, jsonObject2,
				depth2JSONObject1, depth2JSONObject2, depth2JSONObject3,
				depth2JSONObject4,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_LONG_INTEGER));
			_testSortByOneToManyRelationshipCustomObjectFields(
				endpoint1, endpoint3, jsonObject1, jsonObject2,
				depth2JSONObject1, depth2JSONObject2, depth2JSONObject3,
				depth2JSONObject4,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_LONG_TEXT));
			_testSortByOneToManyRelationshipCustomObjectFields(
				endpoint1, endpoint3, jsonObject1, jsonObject2,
				depth2JSONObject1, depth2JSONObject2, depth2JSONObject3,
				depth2JSONObject4,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST));
			_testSortByOneToManyRelationshipCustomObjectFields(
				endpoint1, endpoint3, jsonObject1, jsonObject2,
				depth2JSONObject1, depth2JSONObject2, depth2JSONObject3,
				depth2JSONObject4,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_PICKLIST));
			_testSortByOneToManyRelationshipCustomObjectFields(
				endpoint1, endpoint3, jsonObject1, jsonObject2,
				depth2JSONObject1, depth2JSONObject2, depth2JSONObject3,
				depth2JSONObject4,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_PRECISION_DECIMAL));
			_testSortByOneToManyRelationshipCustomObjectFields(
				endpoint1, endpoint3, jsonObject1, jsonObject2,
				depth2JSONObject1, depth2JSONObject2, depth2JSONObject3,
				depth2JSONObject4,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_TEXT));

			// Sort by several fields

			_testSortByOneToManyRelationshipCustomObjectFields(
				endpoint1, endpoint2, jsonObject1, jsonObject2,
				depth1JSONObject1, depth1JSONObject2, depth1JSONObject3,
				depth1JSONObject4,
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_BOOLEAN),
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_DATE),
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_DATE_TIME),
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_DECIMAL),
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_INTEGER),
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_LONG_INTEGER),
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_LONG_TEXT),
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST),
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_PICKLIST),
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_PRECISION_DECIMAL),
				String.format(
					"%s/%s", _objectRelationship1.getName(),
					_OBJECT_FIELD_NAME_TEXT));

			_testSortByOneToManyRelationshipCustomObjectFields(
				endpoint1, endpoint3, jsonObject1, jsonObject2,
				depth2JSONObject1, depth2JSONObject2, depth2JSONObject3,
				depth2JSONObject4,
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_BOOLEAN),
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_DATE),
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_DATE_TIME),
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_DECIMAL),
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_INTEGER),
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_LONG_INTEGER),
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_LONG_TEXT),
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_MULTISELECT_PICKLIST),
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_PICKLIST),
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(),
					_OBJECT_FIELD_NAME_PRECISION_DECIMAL),
				String.format(
					"%s/%s/%s", _objectRelationship1.getName(),
					_objectRelationship2.getName(), _OBJECT_FIELD_NAME_TEXT));
		}
		finally {
			if (depth2JSONObject1 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					depth2JSONObject1.getLong("id"));
			}

			if (depth2JSONObject2 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					depth2JSONObject2.getLong("id"));
			}

			if (depth2JSONObject3 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					depth2JSONObject3.getLong("id"));
			}

			if (depth2JSONObject4 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					depth2JSONObject4.getLong("id"));
			}

			if (depth1JSONObject1 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					depth1JSONObject1.getLong("id"));
			}

			if (depth1JSONObject2 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					depth1JSONObject2.getLong("id"));
			}

			if (depth1JSONObject3 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					depth1JSONObject3.getLong("id"));
			}

			if (depth1JSONObject4 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					depth1JSONObject4.getLong("id"));
			}

			if (jsonObject1 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					jsonObject1.getLong("id"));
			}

			if (jsonObject2 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					jsonObject2.getLong("id"));
			}
		}
	}

	@Test
	public void testSortByOneToManyRelationshipSystemObjectFields()
		throws Exception {

		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);
		_objectRelationship2 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition2, _objectDefinition3, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		_addResourcePermission(
			ObjectActionKeys.ADD_OBJECT_ENTRY, _objectDefinition2, role);
		_addResourcePermission(
			ObjectActionKeys.ADD_OBJECT_ENTRY, _objectDefinition3, role);

		User user1 = _addUser("test1", "test1");
		User user2 = _addUser("test2", "test2");
		User user3 = _addUser("test3", "test3");

		_roleLocalService.addUserRole(user1.getUserId(), role.getRoleId());
		_roleLocalService.addUserRole(user2.getUserId(), role.getRoleId());
		_roleLocalService.addUserRole(user3.getUserId(), role.getRoleId());

		_objectDefinition1.setEnableObjectEntryDraft(true);
		_objectDefinition2.setEnableObjectEntryDraft(true);
		_objectDefinition3.setEnableObjectEntryDraft(true);

		_objectDefinition1 =
			_objectDefinitionLocalService.updateObjectDefinition(
				_objectDefinition1);
		_objectDefinition2 =
			_objectDefinitionLocalService.updateObjectDefinition(
				_objectDefinition2);
		_objectDefinition3 =
			_objectDefinitionLocalService.updateObjectDefinition(
				_objectDefinition3);

		String endpoint1 = _getEndpoint(
			TestPropsValues.getGroupId(), _objectDefinition1);
		String endpoint2 = _getEndpoint(
			TestPropsValues.getGroupId(), _objectDefinition2);
		String endpoint3 = _getEndpoint(
			TestPropsValues.getGroupId(), _objectDefinition3);

		JSONObject[] oneToManyDepth1JSONObjects = new JSONObject[4];

		HTTPTestUtil.customize(
		).withCredentials(
			"test3@liferay.com", "test3"
		).apply(
			() ->
				oneToManyDepth1JSONObjects[0] = HTTPTestUtil.invokeToJSONObject(
					JSONUtil.put(
						"externalReferenceCode", "ERC4"
					).put(
						"status",
						JSONUtil.put("code", WorkflowConstants.STATUS_DRAFT)
					).toString(),
					endpoint2, Http.Method.POST)
		);

		HTTPTestUtil.customize(
		).withCredentials(
			"test2@liferay.com", "test2"
		).apply(
			() ->
				oneToManyDepth1JSONObjects[1] = HTTPTestUtil.invokeToJSONObject(
					JSONUtil.put(
						"externalReferenceCode", "ERC2"
					).put(
						"status",
						JSONUtil.put("code", WorkflowConstants.STATUS_DRAFT)
					).toString(),
					endpoint2, Http.Method.POST)
		);

		HTTPTestUtil.customize(
		).withCredentials(
			"test2@liferay.com", "test2"
		).apply(
			() ->
				oneToManyDepth1JSONObjects[2] = HTTPTestUtil.invokeToJSONObject(
					JSONUtil.put(
						"externalReferenceCode", "ERC3"
					).toString(),
					endpoint2, Http.Method.POST)
		);

		HTTPTestUtil.customize(
		).withCredentials(
			"test1@liferay.com", "test1"
		).apply(
			() ->
				oneToManyDepth1JSONObjects[3] = HTTPTestUtil.invokeToJSONObject(
					JSONUtil.put(
						"externalReferenceCode", "ERC1"
					).toString(),
					endpoint2, Http.Method.POST)
		);

		JSONObject[] oneToManyDepth2JSONObjects = new JSONObject[4];

		HTTPTestUtil.customize(
		).withCredentials(
			"test3@liferay.com", "test3"
		).apply(
			() ->
				oneToManyDepth2JSONObjects[0] = HTTPTestUtil.invokeToJSONObject(
					JSONUtil.put(
						"externalReferenceCode", "ERC4_2"
					).put(
						"status",
						JSONUtil.put("code", WorkflowConstants.STATUS_DRAFT)
					).toString(),
					endpoint3, Http.Method.POST)
		);

		HTTPTestUtil.customize(
		).withCredentials(
			"test2@liferay.com", "test2"
		).apply(
			() ->
				oneToManyDepth2JSONObjects[1] = HTTPTestUtil.invokeToJSONObject(
					JSONUtil.put(
						"externalReferenceCode", "ERC2_2"
					).put(
						"status",
						JSONUtil.put("code", WorkflowConstants.STATUS_DRAFT)
					).toString(),
					endpoint3, Http.Method.POST)
		);

		HTTPTestUtil.customize(
		).withCredentials(
			"test2@liferay.com", "test2"
		).apply(
			() ->
				oneToManyDepth2JSONObjects[2] = HTTPTestUtil.invokeToJSONObject(
					JSONUtil.put(
						"externalReferenceCode", "ERC3_2"
					).toString(),
					endpoint3, Http.Method.POST)
		);

		HTTPTestUtil.customize(
		).withCredentials(
			"test1@liferay.com", "test1"
		).apply(
			() ->
				oneToManyDepth2JSONObjects[3] = HTTPTestUtil.invokeToJSONObject(
					JSONUtil.put(
						"externalReferenceCode", "ERC1_2"
					).toString(),
					endpoint3, Http.Method.POST)
		);

		JSONObject jsonObject1 = HTTPTestUtil.invokeToJSONObject(
			JSONFactoryUtil.getNullJSON(
			).toString(),
			endpoint1, Http.Method.POST);

		JSONObject jsonObject2 = HTTPTestUtil.invokeToJSONObject(
			JSONFactoryUtil.getNullJSON(
			).toString(),
			endpoint1, Http.Method.POST);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint1, jsonObject1.getLong("id"),
				_objectRelationship1.getName(),
				oneToManyDepth1JSONObjects[0].getLong("id")),
			Http.Method.PUT);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint1, jsonObject1.getLong("id"),
				_objectRelationship1.getName(),
				oneToManyDepth1JSONObjects[1].getLong("id")),
			Http.Method.PUT);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint1, jsonObject2.getLong("id"),
				_objectRelationship1.getName(),
				oneToManyDepth1JSONObjects[2].getLong("id")),
			Http.Method.PUT);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint1, jsonObject2.getLong("id"),
				_objectRelationship1.getName(),
				oneToManyDepth1JSONObjects[3].getLong("id")),
			Http.Method.PUT);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint2,
				oneToManyDepth1JSONObjects[0].getLong("id"),
				_objectRelationship2.getName(),
				oneToManyDepth2JSONObjects[0].getLong("id")),
			Http.Method.PUT);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint2,
				oneToManyDepth1JSONObjects[1].getLong("id"),
				_objectRelationship2.getName(),
				oneToManyDepth2JSONObjects[1].getLong("id")),
			Http.Method.PUT);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint2,
				oneToManyDepth1JSONObjects[2].getLong("id"),
				_objectRelationship2.getName(),
				oneToManyDepth2JSONObjects[2].getLong("id")),
			Http.Method.PUT);

		HTTPTestUtil.invokeToJSONObject(
			null,
			String.format(
				"%s/%d/%s/%d", endpoint2,
				oneToManyDepth1JSONObjects[3].getLong("id"),
				_objectRelationship2.getName(),
				oneToManyDepth2JSONObjects[3].getLong("id")),
			Http.Method.PUT);

		HTTPTestUtil.customize(
		).withCredentials(
			"test3@liferay.com", "test3"
		).apply(
			() ->
				oneToManyDepth1JSONObjects[0] = HTTPTestUtil.invokeToJSONObject(
					JSONUtil.put(
						_OBJECT_FIELD_NAME_2, RandomTestUtil.randomString()
					).toString(),
					endpoint2 + "/by-external-reference-code/ERC4",
					Http.Method.PATCH)
		);

		HTTPTestUtil.customize(
		).withCredentials(
			"test2@liferay.com", "test2"
		).apply(
			() ->
				oneToManyDepth1JSONObjects[1] = HTTPTestUtil.invokeToJSONObject(
					JSONUtil.put(
						_OBJECT_FIELD_NAME_2, RandomTestUtil.randomString()
					).toString(),
					endpoint2 + "/by-external-reference-code/ERC2",
					Http.Method.PATCH)
		);

		HTTPTestUtil.customize(
		).withCredentials(
			"test3@liferay.com", "test3"
		).apply(
			() ->
				oneToManyDepth2JSONObjects[0] = HTTPTestUtil.invokeToJSONObject(
					JSONUtil.put(
						_OBJECT_FIELD_NAME_3, RandomTestUtil.randomString()
					).toString(),
					endpoint3 + "/by-external-reference-code/ERC4_2",
					Http.Method.PATCH)
		);

		HTTPTestUtil.customize(
		).withCredentials(
			"test2@liferay.com", "test2"
		).apply(
			() ->
				oneToManyDepth2JSONObjects[1] = HTTPTestUtil.invokeToJSONObject(
					JSONUtil.put(
						_OBJECT_FIELD_NAME_3, RandomTestUtil.randomString()
					).toString(),
					endpoint3 + "/by-external-reference-code/ERC2_2",
					Http.Method.PATCH)
		);

		try {

			// Depth 1

			_testSortByFieldName(
				endpoint1, jsonObject2, jsonObject1,
				String.format("%s/creator", _objectRelationship1.getName()));
			_testSortByFieldName(
				endpoint1, jsonObject2, jsonObject1,
				String.format("%s/creatorId", _objectRelationship1.getName()));
			_testSortByFieldName(
				endpoint1, jsonObject1, jsonObject2,
				String.format(
					"%s/dateCreated", _objectRelationship1.getName()));
			_testSortByFieldName(
				endpoint1, jsonObject2, jsonObject1,
				String.format(
					"%s/dateModified", _objectRelationship1.getName()));
			_testSortByFieldName(
				endpoint1, jsonObject2, jsonObject1,
				String.format(
					"%s/externalReferenceCode",
					_objectRelationship1.getName()));
			_testSortByFieldName(
				endpoint1, jsonObject1, jsonObject2,
				String.format("%s/id", _objectRelationship1.getName()));

			// Depth 2

			_testSortByFieldName(
				endpoint1, jsonObject2, jsonObject1,
				String.format(
					"%s/%s/creator", _objectRelationship1.getName(),
					_objectRelationship2.getName()));
			_testSortByFieldName(
				endpoint1, jsonObject2, jsonObject1,
				String.format(
					"%s/%s/creatorId", _objectRelationship1.getName(),
					_objectRelationship2.getName()));
			_testSortByFieldName(
				endpoint1, jsonObject1, jsonObject2,
				String.format(
					"%s/%s/dateCreated", _objectRelationship1.getName(),
					_objectRelationship2.getName()));
			_testSortByFieldName(
				endpoint1, jsonObject2, jsonObject1,
				String.format(
					"%s/%s/dateModified", _objectRelationship1.getName(),
					_objectRelationship2.getName()));
			_testSortByFieldName(
				endpoint1, jsonObject2, jsonObject1,
				String.format(
					"%s/%s/externalReferenceCode",
					_objectRelationship1.getName(),
					_objectRelationship2.getName()));
			_testSortByFieldName(
				endpoint1, jsonObject1, jsonObject2,
				String.format(
					"%s/%s/id", _objectRelationship1.getName(),
					_objectRelationship2.getName()));
			_testSortByFieldName(
				endpoint1, jsonObject2, jsonObject1,
				String.format("%s/userId", _objectRelationship1.getName()));

			// Sort by several fields

			_testSortByFieldName(
				endpoint1, jsonObject1, jsonObject2,
				String.format("%s/dateCreated", _objectRelationship1.getName()),
				String.format("%s/id", _objectRelationship1.getName()),
				String.format(
					"%s/%s/dateCreated", _objectRelationship1.getName(),
					_objectRelationship2.getName()),
				String.format(
					"%s/%s/id", _objectRelationship1.getName(),
					_objectRelationship2.getName()));
			_testSortByFieldName(
				endpoint1, jsonObject2, jsonObject1,
				String.format("%s/creator", _objectRelationship1.getName()),
				String.format("%s/creatorId", _objectRelationship1.getName()),
				String.format(
					"%s/dateModified", _objectRelationship1.getName()),
				String.format(
					"%s/externalReferenceCode", _objectRelationship1.getName()),
				String.format("%s/userId", _objectRelationship1.getName()),
				String.format(
					"%s/%s/creator", _objectRelationship1.getName(),
					_objectRelationship2.getName()),
				String.format(
					"%s/%s/creatorId", _objectRelationship1.getName(),
					_objectRelationship2.getName()),
				String.format(
					"%s/%s/dateModified", _objectRelationship1.getName(),
					_objectRelationship2.getName()),
				String.format(
					"%s/%s/externalReferenceCode",
					_objectRelationship1.getName(),
					_objectRelationship2.getName()),
				String.format(
					"%s/%s/userId", _objectRelationship1.getName(),
					_objectRelationship2.getName()));

			// TODO LPD-20530

			_assertFailure(
				ComparisonFailure.class,
				() -> _testSortByFieldName(
					endpoint1, jsonObject2, jsonObject1,
					String.format(
						"%s/status", _objectRelationship1.getName())));
			_assertFailure(
				ComparisonFailure.class,
				() -> _testSortByFieldName(
					endpoint1, jsonObject2, jsonObject1,
					String.format(
						"%s/%s/status", _objectRelationship1.getName(),
						_objectRelationship2.getName())));
		}
		finally {
			for (JSONObject jsonObject :
					ArrayUtil.append(
						oneToManyDepth2JSONObjects,
						oneToManyDepth1JSONObjects)) {

				if (jsonObject == null) {
					continue;
				}

				_objectEntryLocalService.deleteObjectEntry(
					jsonObject.getLong("id"));
			}

			if (jsonObject1 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					jsonObject1.getLong("id"));
			}

			if (jsonObject2 != null) {
				_objectEntryLocalService.deleteObjectEntry(
					jsonObject2.getLong("id"));
			}
		}
	}

	@Test
	public void testSortByRelationshipObjectFields() throws Exception {
		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		String endpoint = _getEndpoint(
			TestPropsValues.getGroupId(), _objectDefinition1);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.vulcan.internal.jaxrs.exception.mapper." +
					"WebApplicationExceptionMapper",
				LoggerTestUtil.ERROR)) {

			JSONAssert.assertEquals(
				JSONUtil.put(
					"status", "BAD_REQUEST"
				).put(
					"title",
					"Unable to sort by a many to many related object field"
				).toString(),
				HTTPTestUtil.invokeToString(
					null,
					StringBundler.concat(
						endpoint, "?sort=",
						URLCodec.encodeURL(
							String.format(
								"%s/%s:asc", _objectRelationship1.getName(),
								_OBJECT_FIELD_NAME_TEXT))),
					Http.Method.GET),
				JSONCompareMode.STRICT);
		}
	}

	@Test
	public void testSortBySystemObjectField() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		_addResourcePermission(
			ObjectActionKeys.ADD_OBJECT_ENTRY, _objectDefinition1, role);

		User user1 = _addUser("test1", "test1");
		User user2 = _addUser("test2", "test2");

		_roleLocalService.addUserRole(user1.getUserId(), role.getRoleId());
		_roleLocalService.addUserRole(user2.getUserId(), role.getRoleId());

		_objectDefinition1.setEnableObjectEntryDraft(true);

		_objectDefinition1 =
			_objectDefinitionLocalService.updateObjectDefinition(
				_objectDefinition1);

		String endpoint = _getEndpoint(
			TestPropsValues.getGroupId(), _objectDefinition1);

		JSONObject[] jsonObjects = new JSONObject[2];

		HTTPTestUtil.customize(
		).withCredentials(
			"test2@liferay.com", "test2"
		).apply(
			() ->
				jsonObjects[0] = HTTPTestUtil.invokeToJSONObject(
					JSONUtil.put(
						"externalReferenceCode", "ERC2"
					).put(
						"status",
						JSONUtil.put("code", WorkflowConstants.STATUS_DRAFT)
					).toString(),
					endpoint, Http.Method.POST)
		);

		HTTPTestUtil.customize(
		).withCredentials(
			"test1@liferay.com", "test1"
		).apply(
			() ->
				jsonObjects[1] = HTTPTestUtil.invokeToJSONObject(
					JSONUtil.put(
						"externalReferenceCode", "ERC1"
					).toString(),
					endpoint, Http.Method.POST)
		);

		HTTPTestUtil.customize(
		).withCredentials(
			"test1@liferay.com", "test1"
		).apply(
			() ->
				jsonObjects[1] = HTTPTestUtil.invokeToJSONObject(
					JSONUtil.put(
						_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
					).toString(),
					endpoint + "/by-external-reference-code/ERC1",
					Http.Method.PATCH)
		);

		HTTPTestUtil.customize(
		).withCredentials(
			"test2@liferay.com", "test2"
		).apply(
			() ->
				jsonObjects[0] = HTTPTestUtil.invokeToJSONObject(
					JSONUtil.put(
						_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
					).toString(),
					endpoint + "/by-external-reference-code/ERC2",
					Http.Method.PATCH)
		);

		try {
			_testSortByFieldName(
				endpoint, jsonObjects[1], jsonObjects[0], "creator");
			_testSortByFieldName(
				endpoint, jsonObjects[1], jsonObjects[0], "creatorId");
			_testSortByFieldName(
				endpoint, jsonObjects[0], jsonObjects[1], "dateCreated");
			_testSortByFieldName(
				endpoint, jsonObjects[1], jsonObjects[0], "dateModified");
			_testSortByFieldName(
				endpoint, jsonObjects[1], jsonObjects[0],
				"externalReferenceCode");
			_testSortByFieldName(
				endpoint, jsonObjects[0], jsonObjects[1], "id");
			_testSortByFieldName(
				endpoint, jsonObjects[1], jsonObjects[0], "status");
			_testSortByFieldName(
				endpoint, jsonObjects[1], jsonObjects[0], "userId");

			// Sort by several fields

			_testSortByFieldName(
				endpoint, jsonObjects[0], jsonObjects[1], "dateCreated", "id");
			_testSortByFieldName(
				endpoint, jsonObjects[1], jsonObjects[0], "creator",
				"dateModified", "externalReferenceCode");
		}
		finally {
			for (JSONObject jsonObject : jsonObjects) {
				if (jsonObject == null) {
					continue;
				}

				_objectEntryLocalService.deleteObjectEntry(
					jsonObject.getLong("id"));
			}
		}
	}

	@Test
	public void testSortByUnsupportedObjectFields() throws Exception {
		_objectRelationship1 = ObjectRelationshipTestUtil.addObjectRelationship(
			_objectDefinition1, _objectDefinition2, TestPropsValues.getUserId(),
			ObjectRelationshipConstants.TYPE_MANY_TO_MANY);

		_testSortByUnsupportedObjectField(
			"Unable to sort by a many to many related object field",
			_objectDefinition1,
			String.format(
				"%s/%s:asc", _objectRelationship1.getName(),
				_OBJECT_FIELD_NAME_TEXT));

		_testSortByUnsupportedObjectField(
			"Unable to sort by property: objectDefinitionId",
			_objectDefinition1, "objectDefinitionId");
		_testSortByUnsupportedObjectField(
			"Unable to sort by property: siteId", _siteScopedObjectDefinition1,
			"siteId");
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

	private ObjectAction _addObjectAction(ObjectDefinition objectDefinition)
		throws Exception {

		return _objectActionLocalService.addObjectAction(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId(), true, StringPool.BLANK,
			RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_WEBHOOK,
			ObjectActionTriggerConstants.KEY_STANDALONE,
			UnicodePropertiesBuilder.put(
				"secret", "standalone"
			).put(
				"url", "https://standalone.com"
			).build(),
			false);
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

	private void _addResourcePermission(
			String actionId, ObjectDefinition objectDefinition, Role role)
		throws Exception {

		_resourcePermissionLocalService.addResourcePermission(
			TestPropsValues.getCompanyId(), objectDefinition.getResourceName(),
			ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()), role.getRoleId(),
			actionId);
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

	private User _addUser(String userName, String userPassword)
		throws Exception {

		String upperCaseFirstLetterUserName = StringUtil.upperCaseFirstLetter(
			userName);

		User user = UserTestUtil.addUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			userPassword, userName + "@liferay.com", userName,
			LocaleUtil.getDefault(), upperCaseFirstLetterUserName,
			upperCaseFirstLetterUserName, null,
			ServiceContextTestUtil.getServiceContext());

		user.setEmailAddressVerified(true);

		return UserLocalServiceUtil.updateUser(user);
	}

	private void _assertEquals(JSONArray nestedObjectEntriesJSONArray)
		throws Exception {

		JSONAssert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_1
				).put(
					"externalReferenceCode", _ERC_VALUE_1
				),
				JSONUtil.put(
					_OBJECT_FIELD_NAME_2, _NEW_OBJECT_FIELD_VALUE_2
				).put(
					"externalReferenceCode", _ERC_VALUE_2
				)
			).toString(),
			nestedObjectEntriesJSONArray.toString(), JSONCompareMode.LENIENT);
	}

	private void _assertFailure(
		Class<?> clazz, UnsafeRunnable<Exception> unsafeRunnable) {

		try {
			unsafeRunnable.run();

			Assert.fail();
		}
		catch (Throwable throwable) {
			Class<?> throwableClass = throwable.getClass();

			Assert.assertTrue(
				throwableClass.getName() + " is not an instance of " +
					clazz.getName(),
				clazz.isInstance(throwable));
		}
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

		Assert.assertNull(jsonObject.get("title"));
		Assert.assertNull(jsonObject.get("status"));

		Assert.assertEquals(1, itemsJSONArray.length());

		JSONObject itemJSONObject = itemsJSONArray.getJSONObject(0);

		Assert.assertEquals(
			String.valueOf(expectedObjectFieldValue),
			String.valueOf(itemJSONObject.get(expectedObjectFieldName)));
	}

	private void _assertItem(
		int index, JSONObject jsonObject, String objectFieldName,
		Object value) {

		Assert.assertEquals(
			String.valueOf(value),
			JSONUtil.getValueAsString(
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

	private void _assertPagination(
			int expectedSize, ObjectDefinition objectDefinition)
		throws Exception {

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			null, objectDefinition.getRESTContextPath() + "?page=1&pageSize=5",
			Http.Method.GET);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(5, itemsJSONArray.length());

		Assert.assertEquals(1, jsonObject.getLong("page"));
		Assert.assertEquals(5, jsonObject.getLong("pageSize"));
		Assert.assertEquals(expectedSize, jsonObject.getLong("totalCount"));

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			null, objectDefinition.getRESTContextPath() + "?pageSize=-1",
			Http.Method.GET);

		itemsJSONArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(expectedSize, itemsJSONArray.length());

		Assert.assertEquals(expectedSize, jsonObject.getLong("totalCount"));
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

	private DLFolder _getDLFolder(
			ObjectDefinition objectDefinition, boolean showInDocsAndMedia)
		throws Exception {

		long groupId = 0;

		ObjectScopeProvider objectScopeProvider =
			_objectScopeProviderRegistry.getObjectScopeProvider(
				objectDefinition.getScope());

		if (objectScopeProvider.isGroupAware()) {
			groupId = TestPropsValues.getGroupId();
		}
		else {
			Company company = _companyLocalService.getCompany(
				objectDefinition.getCompanyId());

			groupId = company.getGroupId();
		}

		if (showInDocsAndMedia) {
			return _dlFolderLocalService.fetchFolder(
				groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				objectDefinition.getShortName());
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
			DLFolder dlFolder, long fileEntryId, String fileName, Folder folder,
			ObjectDefinition objectDefinition)
		throws Exception {

		Link link = new Link();

		FileEntry fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);

		FileVersion fileVersion = fileEntry.getFileVersion();

		long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		long repositoryId = 0;

		if (dlFolder == null) {
			if (folder == null) {
				if (StringUtil.equals(
						objectDefinition.getScope(),
						ObjectDefinitionConstants.SCOPE_SITE)) {

					repositoryId = TestPropsValues.getGroupId();
				}
				else {
					Company company = _companyLocalService.getCompany(
						objectDefinition.getCompanyId());

					repositoryId = company.getGroupId();
				}
			}
			else {
				repositoryId = folder.getSiteId();
			}
		}
		else {
			folderId = dlFolder.getFolderId();
			repositoryId = dlFolder.getRepositoryId();
		}

		Date modifiedDate = fileVersion.getModifiedDate();

		ObjectEntry objectEntry = _objectEntryLocalService.getObjectEntry(
			_testObjectEntryModelListener.getLastObjectEntryId());

		link.setHref(
			StringBundler.concat(
				"/documents/", repositoryId, "/", folderId, "/",
				URLCodec.encodeURL(fileName), "/",
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
						StringPool.POUND,
						StringUtil.toLowerCase(objectDefinition.getName())));

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

	private void _testFilterObjectEntriesByRelatedSystemObjectEntriesFields(
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

	private void _testGetObjectEntryWithObjectActions(
			ObjectAction objectAction, ObjectDefinition objectDefinition,
			UnsafeTriConsumer<JSONObject, JSONObject, ObjectAction, Exception>
				unsafeTriConsumer)
		throws Exception {

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				_OBJECT_FIELD_NAME_1, "value"
			).toString(),
			_getEndpoint(TestPropsValues.getGroupId(), objectDefinition),
			Http.Method.POST);

		JSONObject actionsJSONObject = jsonObject.getJSONObject("actions");

		JSONObject actionJSONObject = actionsJSONObject.getJSONObject(
			objectAction.getName());

		Assert.assertEquals("PUT", actionJSONObject.getString("method"));

		unsafeTriConsumer.accept(actionJSONObject, jsonObject, objectAction);
	}

	private void _testPatchPutCustomObjectEntryExternalReferenceCode(
			Http.Method httpMethod, long objectEntryId)
		throws Exception {

		_testPatchPutCustomObjectEntryExternalReferenceCode(
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(), StringPool.SLASH,
				objectEntryId),
			_ERC_VALUE_2, httpMethod);
		_testPatchPutCustomObjectEntryExternalReferenceCode(
			StringBundler.concat(
				_objectDefinition1.getRESTContextPath(),
				"/by-external-reference-code/", _ERC_VALUE_2),
			_ERC_VALUE_3, httpMethod);
	}

	private void _testPatchPutCustomObjectEntryExternalReferenceCode(
			String endpoint, String externalReferenceCode,
			Http.Method httpMethod)
		throws Exception {

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"externalReferenceCode", externalReferenceCode
			).toString(),
			endpoint, httpMethod);

		Assert.assertEquals(
			externalReferenceCode,
			jsonObject.getString("externalReferenceCode"));
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
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1,
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
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1,
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
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1,
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
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1,
			useExternalReferenceCode);

		String userName = RandomTestUtil.randomString();
		String userPassword = RandomTestUtil.randomString();

		HTTPTestUtil.customize(
		).withCredentials(
			userName + "@liferay.com", userPassword
		).apply(
			() -> {
				DLFolder dlFolder = DLTestUtil.addDLFolder(
					TestPropsValues.getGroupId());

				Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

				User user = _addUser(userName, userPassword);

				_roleLocalService.addUserRole(
					user.getUserId(), role.getRoleId());

				_addResourcePermission(
					ObjectActionKeys.ADD_OBJECT_ENTRY, objectDefinition, role);

				_resourcePermissionLocalService.setResourcePermissions(
					TestPropsValues.getCompanyId(), DLConstants.RESOURCE_NAME,
					ResourceConstants.SCOPE_GROUP,
					String.valueOf(TestPropsValues.getGroupId()),
					role.getRoleId(), new String[] {ActionKeys.ADD_DOCUMENT});

				com.liferay.object.rest.dto.v1_0.FileEntry testFileEntry =
					_toFileEntry(
						Base64::encode, RandomTestUtil.randomString(),
						RandomTestUtil.randomString() + ".txt",
						dlFolder.getExternalReferenceCode(),
						dlFolder.getGroupId());

				_testPatchPutCustomObjectEntryWithAttachmentField(
					fileEntry -> JSONUtil.put(
						"status", "FORBIDDEN"
					).put(
						"title",
						StringBundler.concat(
							"User ", user.getUserId(),
							" must have ADD_DOCUMENT permission for com.",
							"liferay.portal.kernel.repository.model.Folder ",
							dlFolder.getFolderId())
					),
					testFileEntry, httpMethod, null, objectDefinition,
					_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE,
					useExternalReferenceCode);

				_resourcePermissionLocalService.setResourcePermissions(
					TestPropsValues.getCompanyId(),
					DLFolderConstants.getClassName(),
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(dlFolder.getFolderId()), role.getRoleId(),
					new String[] {ActionKeys.ADD_DOCUMENT});

				_testPatchPutCustomObjectEntryWithAttachmentField(
					fileEntry -> JSONUtil.put(
						_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE,
						JSONUtil.put(
							"id",
							_testDLFileEntryModelListener.getLastFileEntryId()
						).put(
							"link",
							_getLinkJSONObject(
								dlFolder,
								_testDLFileEntryModelListener.
									getLastFileEntryId(),
								fileEntry.getName(), fileEntry.getFolder(),
								objectDefinition)
						).put(
							"name", fileEntry.getName()
						)),
					testFileEntry, httpMethod, null, objectDefinition,
					_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE,
					useExternalReferenceCode);
			}
		);

		String randomExternalReferenceCode = RandomTestUtil.randomString();
		long randomGroupId = RandomTestUtil.randomLong();

		_testPatchPutCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				"status", "NOT_FOUND"
			).put(
				"title",
				StringBundler.concat(
					"No DLFolder exists with the key {externalReferenceCode=",
					randomExternalReferenceCode, ", groupId=", randomGroupId,
					"}")
			),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".txt",
				randomExternalReferenceCode, randomGroupId),
			httpMethod, null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE,
			useExternalReferenceCode);

		// File with a nonexistent name (documents and media source)

		DLFolder dlFolder1 = DLTestUtil.addDLFolder(
			TestPropsValues.getGroupId());

		_testPatchPutCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE,
				JSONUtil.put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						dlFolder1,
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), fileEntry.getFolder(),
						objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".txt",
				dlFolder1.getExternalReferenceCode(), dlFolder1.getGroupId()),
			httpMethod, null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE,
			useExternalReferenceCode);

		DLFolder dlFolder2 = DLTestUtil.addDLFolder(_group.getGroupId());

		_testPatchPutCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE,
				JSONUtil.put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						dlFolder2,
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), fileEntry.getFolder(),
						objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".txt",
				dlFolder2.getExternalReferenceCode(), dlFolder2.getGroupId()),
			httpMethod, null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE,
			useExternalReferenceCode);

		_testPatchPutCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE,
				JSONUtil.put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						null,
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), fileEntry.getFolder(),
						objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".txt", null,
				TestPropsValues.getGroupId()),
			httpMethod, null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE,
			useExternalReferenceCode);
		_testPatchPutCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE,
				JSONUtil.put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						null,
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), fileEntry.getFolder(),
						objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".txt", null,
				_group.getGroupId()),
			httpMethod, null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE,
			useExternalReferenceCode);
		_testPatchPutCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE,
				JSONUtil.put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						null,
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), fileEntry.getFolder(),
						objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".txt", null, null),
			httpMethod, null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE,
			useExternalReferenceCode);

		// File with a nonexistent name (user computer source)

		_testPatchPutCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1,
				JSONUtil.put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						_getDLFolder(objectDefinition, false),
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), fileEntry.getFolder(),
						objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".txt", null, null),
			httpMethod, null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1,
			useExternalReferenceCode);
		_testPatchPutCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1,
				JSONUtil.put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						_getDLFolder(objectDefinition, false),
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), fileEntry.getFolder(),
						objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".txt",
				RandomTestUtil.randomString(), RandomTestUtil.randomLong()),
			httpMethod, null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1,
			useExternalReferenceCode);
		_testPatchPutCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_2,
				JSONUtil.put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						_getDLFolder(objectDefinition, true),
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), fileEntry.getFolder(),
						objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".txt", null, null),
			httpMethod, null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_2,
			useExternalReferenceCode);
		_testPatchPutCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_2,
				JSONUtil.put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						_getDLFolder(objectDefinition, true),
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), fileEntry.getFolder(),
						objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".txt",
				RandomTestUtil.randomString(), RandomTestUtil.randomLong()),
			httpMethod, null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_2,
			useExternalReferenceCode);

		// File with a nonexistent name and nested fields

		_testPatchPutCustomObjectEntryWithAttachmentField(
			fileEntry -> {
				Folder folder = new Folder() {
					{
						externalReferenceCode =
							dlFolder1.getExternalReferenceCode();
						siteId = dlFolder1.getGroupId();
					}
				};

				return JSONUtil.put(
					_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE,
					JSONUtil.put(
						"fileBase64", fileEntry.getFileBase64()
					).put(
						"folder",
						JSONFactoryUtil.createJSONObject(folder.toString())
					).put(
						"id", _testDLFileEntryModelListener.getLastFileEntryId()
					).put(
						"link",
						_getLinkJSONObject(
							dlFolder1,
							_testDLFileEntryModelListener.getLastFileEntryId(),
							fileEntry.getName(), fileEntry.getFolder(),
							objectDefinition)
					).put(
						"name", fileEntry.getName()
					));
			},
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".txt",
				dlFolder1.getExternalReferenceCode(), dlFolder1.getGroupId()),
			httpMethod, "fileBase64,folder", objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE,
			useExternalReferenceCode);
		_testPatchPutCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1,
				JSONUtil.put(
					"fileBase64", fileEntry.getFileBase64()
				).put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						_getDLFolder(objectDefinition, false),
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), fileEntry.getFolder(),
						objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".txt", null, null),
			httpMethod, "fileBase64,folder", objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1,
			useExternalReferenceCode);
		_testPatchPutCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_2,
				JSONUtil.put(
					"fileBase64", fileEntry.getFileBase64()
				).put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						_getDLFolder(objectDefinition, true),
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), fileEntry.getFolder(),
						objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".txt", null, null),
			httpMethod, "fileBase64,folder", objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_2,
			useExternalReferenceCode);

		// File with an existing name

		com.liferay.object.rest.dto.v1_0.FileEntry testFileEntry = _toFileEntry(
			Base64::encode, RandomTestUtil.randomString(),
			RandomTestUtil.randomString() + ".txt", null, null);

		_testPatchPutCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1,
				JSONUtil.put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						_getDLFolder(objectDefinition, false),
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), fileEntry.getFolder(),
						objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			testFileEntry, httpMethod, null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1,
			useExternalReferenceCode);
		_testPatchPutCustomObjectEntryWithAttachmentField(
			fileEntry -> {
				String newName = StringUtil.replace(
					fileEntry.getName(), ".txt", " (1).txt");

				return JSONUtil.put(
					_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1,
					JSONUtil.put(
						"id", _testDLFileEntryModelListener.getLastFileEntryId()
					).put(
						"link",
						_getLinkJSONObject(
							_getDLFolder(objectDefinition, false),
							_testDLFileEntryModelListener.getLastFileEntryId(),
							newName, fileEntry.getFolder(), objectDefinition)
					).put(
						"name", newName
					));
			},
			testFileEntry, httpMethod, null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1,
			useExternalReferenceCode);

		testFileEntry = _toFileEntry(
			Base64::encode, RandomTestUtil.randomString(),
			RandomTestUtil.randomString() + ".txt", null, null);

		_testPatchPutCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_2,
				JSONUtil.put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						_getDLFolder(objectDefinition, true),
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), fileEntry.getFolder(),
						objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			testFileEntry, httpMethod, null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_2,
			useExternalReferenceCode);
		_testPatchPutCustomObjectEntryWithAttachmentField(
			fileEntry -> {
				String newName = StringUtil.replace(
					fileEntry.getName(), ".txt", " (1).txt");

				return JSONUtil.put(
					_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_2,
					JSONUtil.put(
						"id", _testDLFileEntryModelListener.getLastFileEntryId()
					).put(
						"link",
						_getLinkJSONObject(
							_getDLFolder(objectDefinition, true),
							_testDLFileEntryModelListener.getLastFileEntryId(),
							newName, fileEntry.getFolder(), objectDefinition)
					).put(
						"name", newName
					));
			},
			testFileEntry, httpMethod, null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_2,
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
				endpoint, "?nestedFields=",
				StringUtil.merge(
					TransformUtil.transform(
						StringUtil.split(nestedFields),
						nestedField -> objectFieldName + "." + nestedField,
						String.class)));
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

	private void
			_testPatchPutCustomObjectEntryWithDuplicateExternalReferenceCode(
				Http.Method httpMethod, ObjectDefinition objectDefinition,
				ObjectDefinition siteScopedObjectDefinition)
		throws Exception {

		_testPatchPutCustomObjectEntryWithDuplicateExternalReferenceCode(
			objectDefinition.getRESTContextPath(),
			objectDefinition.getRESTContextPath() +
				"/by-external-reference-code/",
			httpMethod);

		String endpoint = _getEndpoint(
			TestPropsValues.getGroupId(), siteScopedObjectDefinition);

		_testPatchPutCustomObjectEntryWithDuplicateExternalReferenceCode(
			endpoint, endpoint + "/by-external-reference-code/", httpMethod);
	}

	private void
			_testPatchPutCustomObjectEntryWithDuplicateExternalReferenceCode(
				String endpoint1, String endpoint2, Http.Method httpMethod)
		throws Exception {

		String externalReferenceCode1 = RandomTestUtil.randomString();
		String externalReferenceCode2 = RandomTestUtil.randomString();

		Assert.assertEquals(
			200,
			HTTPTestUtil.invokeToHttpCode(
				JSONUtil.put(
					_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
				).put(
					"externalReferenceCode", externalReferenceCode1
				).toString(),
				endpoint1, Http.Method.POST));
		Assert.assertEquals(
			200,
			HTTPTestUtil.invokeToHttpCode(
				JSONUtil.put(
					_OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
				).put(
					"externalReferenceCode", externalReferenceCode2
				).toString(),
				endpoint1, Http.Method.POST));
		Assert.assertEquals(
			400,
			HTTPTestUtil.invokeToHttpCode(
				JSONUtil.put(
					"externalReferenceCode", externalReferenceCode2
				).toString(),
				endpoint2 + externalReferenceCode1, httpMethod));
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
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1);
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
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1);
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
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1);
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
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1);

		String userName = RandomTestUtil.randomString();
		String userPassword = RandomTestUtil.randomString();

		HTTPTestUtil.customize(
		).withCredentials(
			userName + "@liferay.com", userPassword
		).apply(
			() -> {
				DLFolder dlFolder = DLTestUtil.addDLFolder(
					TestPropsValues.getGroupId());

				Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

				User user = _addUser(userName, userPassword);

				_roleLocalService.addUserRole(
					user.getUserId(), role.getRoleId());

				_addResourcePermission(
					ObjectActionKeys.ADD_OBJECT_ENTRY, objectDefinition, role);

				_testPostCustomObjectEntryWithAttachmentField(
					fileEntry -> JSONUtil.put(
						"status", "FORBIDDEN"
					).put(
						"title",
						StringBundler.concat(
							"User ", user.getUserId(),
							" must have ADD_DOCUMENT permission for com.",
							"liferay.portal.kernel.repository.model.Folder ",
							dlFolder.getFolderId())
					),
					_toFileEntry(
						Base64::encode, RandomTestUtil.randomString(),
						RandomTestUtil.randomString() + ".txt",
						dlFolder.getExternalReferenceCode(),
						dlFolder.getGroupId()),
					null, objectDefinition,
					_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE);

				_resourcePermissionLocalService.setResourcePermissions(
					TestPropsValues.getCompanyId(),
					DLFolderConstants.getClassName(),
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(dlFolder.getFolderId()), role.getRoleId(),
					new String[] {ActionKeys.ADD_DOCUMENT});

				_testPostCustomObjectEntryWithAttachmentField(
					fileEntry -> JSONUtil.put(
						_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE,
						JSONUtil.put(
							"id",
							_testDLFileEntryModelListener.getLastFileEntryId()
						).put(
							"link",
							_getLinkJSONObject(
								dlFolder,
								_testDLFileEntryModelListener.
									getLastFileEntryId(),
								fileEntry.getName(), fileEntry.getFolder(),
								objectDefinition)
						).put(
							"name", fileEntry.getName()
						)),
					_toFileEntry(
						Base64::encode, RandomTestUtil.randomString(),
						RandomTestUtil.randomString() + ".txt",
						dlFolder.getExternalReferenceCode(),
						dlFolder.getGroupId()),
					null, objectDefinition,
					_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE);
			}
		);

		String randomExternalReferenceCode = RandomTestUtil.randomString();
		long randomGroupId = RandomTestUtil.randomLong();

		_testPostCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				"status", "NOT_FOUND"
			).put(
				"title",
				StringBundler.concat(
					"No DLFolder exists with the key {externalReferenceCode=",
					randomExternalReferenceCode, ", groupId=", randomGroupId,
					"}")
			),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".txt",
				randomExternalReferenceCode, randomGroupId),
			null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE);

		// File with a nonexistent name (documents and media source)

		DLFolder dlFolder1 = DLTestUtil.addDLFolder(
			TestPropsValues.getGroupId());

		_testPostCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE,
				JSONUtil.put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						dlFolder1,
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), fileEntry.getFolder(),
						objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".txt",
				dlFolder1.getExternalReferenceCode(), dlFolder1.getGroupId()),
			null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE);

		DLFolder dlFolder2 = DLTestUtil.addDLFolder(_group.getGroupId());

		_testPostCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE,
				JSONUtil.put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						dlFolder2,
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), fileEntry.getFolder(),
						objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".txt",
				dlFolder2.getExternalReferenceCode(), dlFolder2.getGroupId()),
			null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE);

		_testPostCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE,
				JSONUtil.put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						null,
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), fileEntry.getFolder(),
						objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".txt", null,
				TestPropsValues.getGroupId()),
			null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE);
		_testPostCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE,
				JSONUtil.put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						null,
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), fileEntry.getFolder(),
						objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".txt", null,
				_group.getGroupId()),
			null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE);
		_testPostCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE,
				JSONUtil.put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						null,
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), fileEntry.getFolder(),
						objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".txt", null, null),
			null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE);

		// File with a nonexistent name (user computer source)

		_testPostCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1,
				JSONUtil.put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						_getDLFolder(objectDefinition, false),
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), fileEntry.getFolder(),
						objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".txt", null, null),
			null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1);
		_testPostCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1,
				JSONUtil.put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						_getDLFolder(objectDefinition, false),
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), fileEntry.getFolder(),
						objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".txt",
				RandomTestUtil.randomString(), RandomTestUtil.randomLong()),
			null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1);
		_testPostCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_2,
				JSONUtil.put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						_getDLFolder(objectDefinition, true),
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), fileEntry.getFolder(),
						objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".txt", null, null),
			null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_2);
		_testPostCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_2,
				JSONUtil.put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						_getDLFolder(objectDefinition, true),
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), fileEntry.getFolder(),
						objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".txt",
				RandomTestUtil.randomString(), RandomTestUtil.randomLong()),
			null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_2);

		// File with a nonexistent name and nested fields

		_testPostCustomObjectEntryWithAttachmentField(
			fileEntry -> {
				Folder folder = new Folder() {
					{
						externalReferenceCode =
							dlFolder1.getExternalReferenceCode();
						siteId = dlFolder1.getGroupId();
					}
				};

				return JSONUtil.put(
					_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE,
					JSONUtil.put(
						"fileBase64", fileEntry.getFileBase64()
					).put(
						"folder",
						JSONFactoryUtil.createJSONObject(folder.toString())
					).put(
						"id", _testDLFileEntryModelListener.getLastFileEntryId()
					).put(
						"link",
						_getLinkJSONObject(
							dlFolder1,
							_testDLFileEntryModelListener.getLastFileEntryId(),
							fileEntry.getName(), fileEntry.getFolder(),
							objectDefinition)
					).put(
						"name", fileEntry.getName()
					));
			},
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".txt",
				dlFolder1.getExternalReferenceCode(), dlFolder1.getGroupId()),
			"fileBase64,folder", objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_DOCS_AND_MEDIA_SOURCE);
		_testPostCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1,
				JSONUtil.put(
					"fileBase64", fileEntry.getFileBase64()
				).put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						_getDLFolder(objectDefinition, false),
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), fileEntry.getFolder(),
						objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".txt", null, null),
			"fileBase64,folder", objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1);
		_testPostCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_2,
				JSONUtil.put(
					"fileBase64", fileEntry.getFileBase64()
				).put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						_getDLFolder(objectDefinition, true),
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), fileEntry.getFolder(),
						objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			_toFileEntry(
				Base64::encode, RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".txt", null, null),
			"fileBase64,folder", objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_2);

		// File with an existing name

		com.liferay.object.rest.dto.v1_0.FileEntry testFileEntry = _toFileEntry(
			Base64::encode, RandomTestUtil.randomString(),
			RandomTestUtil.randomString() + ".txt", null, null);

		_testPostCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1,
				JSONUtil.put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						_getDLFolder(objectDefinition, false),
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), fileEntry.getFolder(),
						objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			testFileEntry, null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1);
		_testPostCustomObjectEntryWithAttachmentField(
			fileEntry -> {
				String newName = StringUtil.replace(
					fileEntry.getName(), ".txt", " (1).txt");

				return JSONUtil.put(
					_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1,
					JSONUtil.put(
						"id", _testDLFileEntryModelListener.getLastFileEntryId()
					).put(
						"link",
						_getLinkJSONObject(
							_getDLFolder(objectDefinition, false),
							_testDLFileEntryModelListener.getLastFileEntryId(),
							newName, fileEntry.getFolder(), objectDefinition)
					).put(
						"name", newName
					));
			},
			testFileEntry, null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1);

		testFileEntry = _toFileEntry(
			Base64::encode, RandomTestUtil.randomString(),
			RandomTestUtil.randomString() + ".txt", null, null);

		_testPostCustomObjectEntryWithAttachmentField(
			fileEntry -> JSONUtil.put(
				_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_2,
				JSONUtil.put(
					"id", _testDLFileEntryModelListener.getLastFileEntryId()
				).put(
					"link",
					_getLinkJSONObject(
						_getDLFolder(objectDefinition, true),
						_testDLFileEntryModelListener.getLastFileEntryId(),
						fileEntry.getName(), fileEntry.getFolder(),
						objectDefinition)
				).put(
					"name", fileEntry.getName()
				)),
			testFileEntry, null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_2);
		_testPostCustomObjectEntryWithAttachmentField(
			fileEntry -> {
				String newName = StringUtil.replace(
					fileEntry.getName(), ".txt", " (1).txt");

				return JSONUtil.put(
					_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_2,
					JSONUtil.put(
						"id", _testDLFileEntryModelListener.getLastFileEntryId()
					).put(
						"link",
						_getLinkJSONObject(
							_getDLFolder(objectDefinition, true),
							_testDLFileEntryModelListener.getLastFileEntryId(),
							newName, fileEntry.getFolder(), objectDefinition)
					).put(
						"name", newName
					));
			},
			testFileEntry, null, objectDefinition,
			_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_2);
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
				endpoint, "?nestedFields=",
				StringUtil.merge(
					TransformUtil.transform(
						StringUtil.split(nestedFields),
						nestedField -> objectFieldName + "." + nestedField,
						String.class)));
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

		JSONObject newObjectEntryJSONObject;

		if (manyToOne) {
			newObjectEntryJSONObject = JSONUtil.put(
				_objectRelationship1.getName(),
				JSONFactoryUtil.createJSONObject()
			).put(
				StringBundler.concat(
					"r_", _objectRelationship2.getName(), "_",
					_objectDefinition2.getPKObjectFieldName()),
				0
			);
		}
		else {
			newObjectEntryJSONObject = JSONUtil.put(
				_objectRelationship1.getName(),
				JSONFactoryUtil.createJSONArray());
		}

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
			JSONObject nestedObjectEntryJSONObject1 = jsonObject.getJSONObject(
				_objectRelationship1.getName());

			Assert.assertNull(nestedObjectEntryJSONObject1);

			JSONObject nestedObjectEntryJSONObject2 = jsonObject.getJSONObject(
				_objectRelationship2.getName());

			Assert.assertNull(nestedObjectEntryJSONObject2);

			JSONAssert.assertEquals(
				JSONUtil.put(
					StringBundler.concat(
						"r_", _objectRelationship1.getName(), "_",
						_objectDefinition2.getPKObjectFieldName()),
					0
				).put(
					StringBundler.concat(
						"r_", _objectRelationship1.getName(), "_",
						StringUtil.replaceLast(
							_objectDefinition2.getPKObjectFieldName(), "Id",
							"ERC")),
					""
				).put(
					StringBundler.concat(
						"r_", _objectRelationship2.getName(), "_",
						_objectDefinition2.getPKObjectFieldName()),
					0
				).put(
					StringBundler.concat(
						"r_", _objectRelationship2.getName(), "_",
						StringUtil.replaceLast(
							_objectDefinition2.getPKObjectFieldName(), "Id",
							"ERC")),
					""
				).toString(),
				jsonObject.toString(), JSONCompareMode.LENIENT);
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

	private void _testSortByCustomObjectField(
			String endpoint, JSONObject expectedJSONObject1,
			JSONObject expectedJSONObject2, String... fieldNames)
		throws Exception {

		_testSortByFieldName(
			endpoint, expectedJSONObject1, expectedJSONObject2, fieldNames);

		JSONObject valuesJSONObject1 = JSONFactoryUtil.createJSONObject();
		JSONObject valuesJSONObject2 = JSONFactoryUtil.createJSONObject();

		for (String fieldName : fieldNames) {
			valuesJSONObject1.put(
				fieldName, expectedJSONObject1.get(fieldName));
			valuesJSONObject2.put(
				fieldName, expectedJSONObject2.get(fieldName));
		}

		try {
			JSONObject jsonObject1 = HTTPTestUtil.invokeToJSONObject(
				valuesJSONObject2.toString(),
				endpoint + "/" + expectedJSONObject1.getLong("id"),
				Http.Method.PATCH);

			JSONObject jsonObject2 = HTTPTestUtil.invokeToJSONObject(
				valuesJSONObject1.toString(),
				endpoint + "/" + expectedJSONObject2.getLong("id"),
				Http.Method.PATCH);

			_testSortByFieldName(
				endpoint, jsonObject2, jsonObject1, fieldNames);
		}
		finally {
			HTTPTestUtil.invokeToJSONObject(
				valuesJSONObject1.toString(),
				endpoint + "/" + expectedJSONObject1.getLong("id"),
				Http.Method.PATCH);

			HTTPTestUtil.invokeToJSONObject(
				valuesJSONObject2.toString(),
				endpoint + "/" + expectedJSONObject2.getLong("id"),
				Http.Method.PATCH);
		}
	}

	private void _testSortByFieldName(
			String endpoint, JSONObject expectedJSONObject1,
			JSONObject expectedJSONObject2, JSONObject expectedJSONObject3,
			JSONObject expectedJSONObject4, String... fieldNames)
		throws Exception {

		JSONObject pageJSONObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				endpoint, "?sort=",
				URLCodec.encodeURL(
					StringUtil.merge(fieldNames, ":asc,") + ":asc")),
			Http.Method.GET);

		_assertItem(0, pageJSONObject, "id", expectedJSONObject1.getLong("id"));
		_assertItem(1, pageJSONObject, "id", expectedJSONObject2.getLong("id"));
		_assertItem(2, pageJSONObject, "id", expectedJSONObject3.getLong("id"));
		_assertItem(3, pageJSONObject, "id", expectedJSONObject4.getLong("id"));

		pageJSONObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				endpoint, "?sort=",
				URLCodec.encodeURL(
					StringUtil.merge(fieldNames, ":desc,") + ":desc")),
			Http.Method.GET);

		_assertItem(0, pageJSONObject, "id", expectedJSONObject3.getLong("id"));
		_assertItem(1, pageJSONObject, "id", expectedJSONObject4.getLong("id"));
		_assertItem(2, pageJSONObject, "id", expectedJSONObject1.getLong("id"));
		_assertItem(3, pageJSONObject, "id", expectedJSONObject2.getLong("id"));
	}

	private void _testSortByFieldName(
			String endpoint, JSONObject expectedJSONObject1,
			JSONObject expectedJSONObject2, String... fieldNames)
		throws Exception {

		JSONObject pageJSONObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				endpoint, "?sort=",
				URLCodec.encodeURL(
					StringUtil.merge(fieldNames, ":asc,") + ":asc")),
			Http.Method.GET);

		_assertItem(0, pageJSONObject, "id", expectedJSONObject1.getLong("id"));
		_assertItem(1, pageJSONObject, "id", expectedJSONObject2.getLong("id"));

		pageJSONObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				endpoint, "?sort=",
				URLCodec.encodeURL(
					StringUtil.merge(fieldNames, ":desc,") + ":desc")),
			Http.Method.GET);

		_assertItem(0, pageJSONObject, "id", expectedJSONObject2.getLong("id"));
		_assertItem(1, pageJSONObject, "id", expectedJSONObject1.getLong("id"));
	}

	private void
			_testSortByManyToOneAndOneToManyRelationshipsCustomObjectFields(
				String endpoint1, String endpoint2,
				JSONObject expectedJSONObject1, JSONObject expectedJSONObject2,
				JSONObject expectedJSONObject3, JSONObject expectedJSONObject4,
				JSONObject relatedJSONObject1, JSONObject relatedJSONObject2,
				JSONObject relatedJSONObject3, JSONObject relatedJSONObject4,
				String... fieldNames)
		throws Exception {

		_testSortByFieldName(
			endpoint1, expectedJSONObject1, expectedJSONObject2,
			expectedJSONObject3, expectedJSONObject4, fieldNames);

		JSONObject valuesJSONObject1 = JSONFactoryUtil.createJSONObject();
		JSONObject valuesJSONObject2 = JSONFactoryUtil.createJSONObject();
		JSONObject valuesJSONObject3 = JSONFactoryUtil.createJSONObject();
		JSONObject valuesJSONObject4 = JSONFactoryUtil.createJSONObject();

		for (String fieldName : fieldNames) {
			String objectFieldName = StringUtil.extractLast(fieldName, "/");

			valuesJSONObject1.put(
				objectFieldName, relatedJSONObject1.get(objectFieldName));
			valuesJSONObject2.put(
				objectFieldName, relatedJSONObject2.get(objectFieldName));
			valuesJSONObject3.put(
				objectFieldName, relatedJSONObject3.get(objectFieldName));
			valuesJSONObject4.put(
				objectFieldName, relatedJSONObject4.get(objectFieldName));
		}

		try {
			HTTPTestUtil.invokeToJSONObject(
				valuesJSONObject3.toString(),
				endpoint2 + "/" + relatedJSONObject1.getLong("id"),
				Http.Method.PATCH);

			HTTPTestUtil.invokeToJSONObject(
				valuesJSONObject4.toString(),
				endpoint2 + "/" + relatedJSONObject2.getLong("id"),
				Http.Method.PATCH);

			HTTPTestUtil.invokeToJSONObject(
				valuesJSONObject1.toString(),
				endpoint2 + "/" + relatedJSONObject3.getLong("id"),
				Http.Method.PATCH);

			HTTPTestUtil.invokeToJSONObject(
				valuesJSONObject2.toString(),
				endpoint2 + "/" + relatedJSONObject4.getLong("id"),
				Http.Method.PATCH);

			_testSortByFieldName(
				endpoint1, expectedJSONObject1, expectedJSONObject2,
				expectedJSONObject3, expectedJSONObject4, fieldNames);
		}
		finally {
			HTTPTestUtil.invokeToJSONObject(
				valuesJSONObject1.toString(),
				endpoint2 + "/" + relatedJSONObject1.getLong("id"),
				Http.Method.PATCH);

			HTTPTestUtil.invokeToJSONObject(
				valuesJSONObject2.toString(),
				endpoint2 + "/" + relatedJSONObject2.getLong("id"),
				Http.Method.PATCH);

			HTTPTestUtil.invokeToJSONObject(
				valuesJSONObject3.toString(),
				endpoint2 + "/" + relatedJSONObject3.getLong("id"),
				Http.Method.PATCH);

			HTTPTestUtil.invokeToJSONObject(
				valuesJSONObject4.toString(),
				endpoint2 + "/" + relatedJSONObject4.getLong("id"),
				Http.Method.PATCH);
		}
	}

	private void _testSortByManyToOneRelationshipCustomObjectFields(
			String endpoint1, String endpoint2, JSONObject expectedJSONObject1,
			JSONObject expectedJSONObject2, JSONObject expectedJSONObject3,
			JSONObject expectedJSONObject4, JSONObject relatedJSONObject1,
			JSONObject relatedJSONObject2, String... fieldNames)
		throws Exception {

		_testSortByFieldName(
			endpoint1, expectedJSONObject1, expectedJSONObject2,
			expectedJSONObject3, expectedJSONObject4, fieldNames);

		JSONObject valuesJSONObject1 = JSONFactoryUtil.createJSONObject();
		JSONObject valuesJSONObject2 = JSONFactoryUtil.createJSONObject();

		for (String fieldName : fieldNames) {
			String objectFieldName = StringUtil.extractLast(fieldName, "/");

			valuesJSONObject1.put(
				objectFieldName, relatedJSONObject1.get(objectFieldName));
			valuesJSONObject2.put(
				objectFieldName, relatedJSONObject2.get(objectFieldName));
		}

		try {
			HTTPTestUtil.invokeToJSONObject(
				valuesJSONObject2.toString(),
				endpoint2 + "/" + relatedJSONObject1.getLong("id"),
				Http.Method.PATCH);

			HTTPTestUtil.invokeToJSONObject(
				valuesJSONObject1.toString(),
				endpoint2 + "/" + relatedJSONObject2.getLong("id"),
				Http.Method.PATCH);

			_testSortByFieldName(
				endpoint1, expectedJSONObject3, expectedJSONObject4,
				expectedJSONObject1, expectedJSONObject2, fieldNames);
		}
		finally {
			HTTPTestUtil.invokeToJSONObject(
				valuesJSONObject1.toString(),
				endpoint2 + "/" + relatedJSONObject1.getLong("id"),
				Http.Method.PATCH);

			HTTPTestUtil.invokeToJSONObject(
				valuesJSONObject2.toString(),
				endpoint2 + "/" + relatedJSONObject2.getLong("id"),
				Http.Method.PATCH);
		}
	}

	private void _testSortByOneToManyRelationshipCustomObjectFields(
			String endpoint1, String endpoint2, JSONObject expectedJSONObject1,
			JSONObject expectedJSONObject2, JSONObject relatedJSONObject1,
			JSONObject relatedJSONObject2, JSONObject relatedJSONObject3,
			JSONObject relatedJSONObject4, String... fieldNames)
		throws Exception {

		_testSortByFieldName(
			endpoint1, expectedJSONObject1, expectedJSONObject2, fieldNames);

		JSONObject valuesJSONObject1 = JSONFactoryUtil.createJSONObject();
		JSONObject valuesJSONObject2 = JSONFactoryUtil.createJSONObject();
		JSONObject valuesJSONObject3 = JSONFactoryUtil.createJSONObject();
		JSONObject valuesJSONObject4 = JSONFactoryUtil.createJSONObject();

		for (String fieldName : fieldNames) {
			String objectFieldName = StringUtil.extractLast(fieldName, "/");

			valuesJSONObject1.put(
				objectFieldName, relatedJSONObject1.get(objectFieldName));
			valuesJSONObject2.put(
				objectFieldName, relatedJSONObject2.get(objectFieldName));
			valuesJSONObject3.put(
				objectFieldName, relatedJSONObject3.get(objectFieldName));
			valuesJSONObject4.put(
				objectFieldName, relatedJSONObject4.get(objectFieldName));
		}

		try {
			HTTPTestUtil.invokeToJSONObject(
				valuesJSONObject3.toString(),
				endpoint2 + "/" + relatedJSONObject1.getLong("id"),
				Http.Method.PATCH);

			HTTPTestUtil.invokeToJSONObject(
				valuesJSONObject4.toString(),
				endpoint2 + "/" + relatedJSONObject2.getLong("id"),
				Http.Method.PATCH);

			HTTPTestUtil.invokeToJSONObject(
				valuesJSONObject1.toString(),
				endpoint2 + "/" + relatedJSONObject3.getLong("id"),
				Http.Method.PATCH);

			HTTPTestUtil.invokeToJSONObject(
				valuesJSONObject2.toString(),
				endpoint2 + "/" + relatedJSONObject4.getLong("id"),
				Http.Method.PATCH);

			_testSortByFieldName(
				endpoint1, expectedJSONObject2, expectedJSONObject1,
				fieldNames);
		}
		finally {
			HTTPTestUtil.invokeToJSONObject(
				valuesJSONObject1.toString(),
				endpoint2 + "/" + relatedJSONObject1.getLong("id"),
				Http.Method.PATCH);

			HTTPTestUtil.invokeToJSONObject(
				valuesJSONObject2.toString(),
				endpoint2 + "/" + relatedJSONObject2.getLong("id"),
				Http.Method.PATCH);

			HTTPTestUtil.invokeToJSONObject(
				valuesJSONObject3.toString(),
				endpoint2 + "/" + relatedJSONObject3.getLong("id"),
				Http.Method.PATCH);

			HTTPTestUtil.invokeToJSONObject(
				valuesJSONObject4.toString(),
				endpoint2 + "/" + relatedJSONObject4.getLong("id"),
				Http.Method.PATCH);
		}
	}

	private void _testSortByUnsupportedObjectField(
			String expectedTitle, ObjectDefinition objectDefinition,
			String sortString)
		throws Exception {

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.vulcan.internal.jaxrs.exception.mapper." +
					"WebApplicationExceptionMapper",
				LoggerTestUtil.ERROR)) {

			String endpoint = _getEndpoint(
				TestPropsValues.getGroupId(), objectDefinition);

			JSONAssert.assertEquals(
				JSONUtil.put(
					"status", "BAD_REQUEST"
				).put(
					"title", expectedTitle
				).toString(),
				HTTPTestUtil.invokeToString(
					null, endpoint + "?sort=" + URLCodec.encodeURL(sortString),
					Http.Method.GET),
				JSONCompareMode.STRICT);
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

	private static final String _LIST_TYPE_ENTRY_KEY_1 =
		"a" + RandomTestUtil.randomString();

	private static final String _LIST_TYPE_ENTRY_KEY_2 =
		"b" + RandomTestUtil.randomString();

	private static final String _LIST_TYPE_ENTRY_KEY_3 =
		"c" + RandomTestUtil.randomString();

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
		_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_1 =
			"x" + RandomTestUtil.randomString();

	private static final String
		_OBJECT_FIELD_NAME_ATTACHMENT_USER_COMPUTER_SOURCE_2 =
			"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_BOOLEAN =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_DATE =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_DATE_TIME =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_DECIMAL =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_INTEGER =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_LONG_INTEGER =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_LONG_TEXT =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_MULTISELECT_PICKLIST =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_PICKLIST =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_PRECISION_DECIMAL =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_RICH_TEXT =
		"x" + RandomTestUtil.randomString();

	private static final String _OBJECT_FIELD_NAME_TEXT =
		"x" + RandomTestUtil.randomString();

	private static final int _OBJECT_FIELD_VALUE_1 = RandomTestUtil.randomInt();

	private static final int _OBJECT_FIELD_VALUE_2 = RandomTestUtil.randomInt();

	private static final int _OBJECT_FIELD_VALUE_3 = RandomTestUtil.randomInt();

	private static final int _OBJECT_FIELD_VALUE_4 = RandomTestUtil.randomInt();

	private static final String _TAG_1 = StringUtil.toLowerCase(
		RandomTestUtil.randomString());

	private static final String _TAG_2 = StringUtil.toLowerCase(
		RandomTestUtil.randomString());

	private static final String _TAG_3 = StringUtil.toLowerCase(
		RandomTestUtil.randomString());

	private static AssetVocabulary _assetVocabulary;
	private static BundleContext _bundleContext;
	private static final DateFormat _dateFormat =
		DateFormatFactoryUtil.getSimpleDateFormat("yyyy-MM-dd");
	private static final DateFormat _dateTimeDateFormat =
		DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
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
	private ObjectDefinition _objectDefinition5;
	private ObjectDefinition _objectDefinition6;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private ObjectEntry _objectEntry1;
	private ObjectEntry _objectEntry2;
	private ObjectEntry _objectEntry3;
	private ObjectEntry _objectEntry4;
	private ObjectEntry _objectEntry5;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	private ObjectRelationship _objectRelationship1;
	private ObjectRelationship _objectRelationship2;
	private ObjectRelationship _objectRelationship3;
	private ObjectRelationship _objectRelationship4;
	private ObjectRelationship _objectRelationship5;
	private ObjectRelationship _objectRelationship6;
	private ObjectRelationship _objectRelationship7;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Inject
	private ObjectScopeProviderRegistry _objectScopeProviderRegistry;

	@Inject
	private PortletFileRepository _portletFileRepository;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	private ObjectDefinition _siteScopedObjectDefinition1;
	private ObjectDefinition _siteScopedObjectDefinition2;
	private ObjectEntry _siteScopedObjectEntry1;
	private SystemObjectDefinitionManager _systemObjectDefinitionManager;

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