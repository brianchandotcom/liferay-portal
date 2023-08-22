/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.admin.rest.resource.v1_0.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectField;
import com.liferay.object.admin.rest.client.dto.v1_0.Status;
import com.liferay.object.admin.rest.client.pagination.Page;
import com.liferay.object.admin.rest.client.pagination.Pagination;
import com.liferay.object.admin.rest.client.problem.Problem;
import com.liferay.object.admin.rest.client.serdes.v1_0.ObjectDefinitionSerDes;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFolderConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.exception.NoSuchObjectDefinitionException;
import com.liferay.object.model.ObjectFolder;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFolderLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@FeatureFlags(
	{
		"LPS-148856", "LPS-167253", "LPS-170122", "LPS-172017", "LPS-181663",
		"LPS-187142"
	}
)
@RunWith(Arquillian.class)
public class ObjectDefinitionResourceTest
	extends BaseObjectDefinitionResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_objectFolder1 = _objectFolderLocalService.addObjectFolder(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString());

		_objectFolder2 = _objectFolderLocalService.addObjectFolder(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString());
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		if (_objectDefinition != null) {
			try {
				_objectDefinitionLocalService.deleteObjectDefinition(
					_objectDefinition.getId());
			}
			catch (NoSuchObjectDefinitionException
						noSuchObjectDefinitionException) {

				if (_log.isDebugEnabled()) {
					_log.debug(noSuchObjectDefinitionException);
				}
			}
		}
	}

	@Override
	@Test
	public void testGetObjectDefinition() throws Exception {
		super.testGetObjectDefinition();

		ObjectDefinition objectDefinition =
			testGetObjectDefinitionsPage_addObjectDefinition(
				randomObjectDefinition());

		String objectDefinitionPluralName = StringUtil.lowerCaseFirstLetter(
			TextFormatter.formatPlural(objectDefinition.getName()));

		Assert.assertEquals(
			"/o/c/" + objectDefinitionPluralName,
			objectDefinition.getRestContextPath());
	}

	@Override
	@Test
	public void testGetObjectDefinitionsPageWithSortString() throws Exception {
		ObjectDefinition objectDefinition1 = randomObjectDefinition();

		objectDefinition1.setName("A" + objectDefinition1.getName());

		objectDefinition1 = testGetObjectDefinitionsPage_addObjectDefinition(
			objectDefinition1);

		ObjectDefinition objectDefinition2 = randomObjectDefinition();

		objectDefinition2.setName("B" + objectDefinition2.getName());

		objectDefinition2 = testGetObjectDefinitionsPage_addObjectDefinition(
			objectDefinition2);

		Page<ObjectDefinition> ascPage =
			objectDefinitionResource.getObjectDefinitionsPage(
				null, null, null, null, "name:asc");

		List<ObjectDefinition> objectDefinitions =
			(List<ObjectDefinition>)ascPage.getItems();

		assertEquals(
			Arrays.asList(objectDefinition1, objectDefinition2),
			objectDefinitions.subList(2, 4));

		Page<ObjectDefinition> descPage =
			objectDefinitionResource.getObjectDefinitionsPage(
				null, null, null, null, "name:desc");

		objectDefinitions = (List<ObjectDefinition>)descPage.getItems();

		assertEquals(
			Arrays.asList(objectDefinition2, objectDefinition1),
			objectDefinitions.subList(
				objectDefinitions.size() - 4, objectDefinitions.size() - 2));

		_objectDefinitionLocalService.deleteObjectDefinition(
			objectDefinition1.getId());
		_objectDefinitionLocalService.deleteObjectDefinition(
			objectDefinition2.getId());
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetObjectDefinitionByExternalReferenceCodeNotFound() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetObjectDefinitionNotFound() {
	}

	@Override
	@Test
	public void testGraphQLGetObjectDefinitionsPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"objectDefinitions",
			HashMapBuilder.<String, Object>put(
				"page", 1
			).put(
				"pageSize",
				() -> {
					int objectDefinitionsCount =
						_objectDefinitionLocalService.getObjectDefinitionsCount(
							TestPropsValues.getCompanyId());

					return objectDefinitionsCount + 10;
				}
			).build(),
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject objectDefinitionsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/objectDefinitions");

		long totalCount = objectDefinitionsJSONObject.getLong("totalCount");

		ObjectDefinition objectDefinition1 =
			testGraphQLGetObjectDefinitionsPage_addObjectDefinition();
		ObjectDefinition objectDefinition2 =
			testGraphQLGetObjectDefinitionsPage_addObjectDefinition();

		objectDefinitionsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/objectDefinitions");

		Assert.assertEquals(
			totalCount + 2, objectDefinitionsJSONObject.getLong("totalCount"));

		assertContains(
			objectDefinition1,
			Arrays.asList(
				ObjectDefinitionSerDes.toDTOs(
					objectDefinitionsJSONObject.getString("items"))));
		assertContains(
			objectDefinition2,
			Arrays.asList(
				ObjectDefinitionSerDes.toDTOs(
					objectDefinitionsJSONObject.getString("items"))));
	}

	@Override
	@Test
	public void testPostObjectDefinition() throws Exception {
		super.testPostObjectDefinition();

		ObjectDefinition randomObjectDefinition = randomObjectDefinition();

		Status status = new Status() {
			{
				code = WorkflowConstants.STATUS_APPROVED;
				label = WorkflowConstants.getStatusLabel(
					WorkflowConstants.STATUS_APPROVED);
				label_i18n = _language.get(
					LanguageResources.getResourceBundle(
						LocaleUtil.getDefault()),
					WorkflowConstants.getStatusLabel(
						WorkflowConstants.STATUS_APPROVED));
			}
		};

		randomObjectDefinition.setStatus(status);

		ObjectDefinition postObjectDefinition =
			testPostObjectDefinition_addObjectDefinition(
				randomObjectDefinition);

		assertEquals(postObjectDefinition, randomObjectDefinition);
		assertValid(postObjectDefinition);
	}

	@Override
	@Test
	public void testPutObjectDefinition() throws Exception {
		super.testPutObjectDefinition();

		// Account entry restricted

		ObjectDefinition randomObjectDefinition = randomObjectDefinition();

		randomObjectDefinition.setSystem(false);

		ObjectDefinition postObjectDefinition =
			objectDefinitionResource.postObjectDefinition(
				randomObjectDefinition);

		com.liferay.object.model.ObjectDefinition
			serviceBuilderAccountEntryObjectDefinition =
				_objectDefinitionLocalService.fetchSystemObjectDefinition(
					AccountEntry.class.getSimpleName());

		_objectDefinitionLocalService.enableAccountEntryRestricted(
			_objectRelationshipLocalService.addObjectRelationship(
				TestPropsValues.getUserId(),
				serviceBuilderAccountEntryObjectDefinition.
					getObjectDefinitionId(),
				postObjectDefinition.getId(), 0,
				ObjectRelationshipConstants.DELETION_TYPE_DISASSOCIATE,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"a" + RandomTestUtil.randomString(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY));

		postObjectDefinition = objectDefinitionResource.getObjectDefinition(
			postObjectDefinition.getId());

		Assert.assertTrue(postObjectDefinition.getAccountEntryRestricted());

		String accountEntryRestrictedObjectFieldName =
			postObjectDefinition.getAccountEntryRestrictedObjectFieldName();

		ObjectDefinition accountEntryObjectDefinition =
			objectDefinitionResource.getObjectDefinition(
				serviceBuilderAccountEntryObjectDefinition.
					getObjectDefinitionId());

		accountEntryObjectDefinition.setExternalReferenceCode(
			RandomTestUtil.randomString());

		objectDefinitionResource.putObjectDefinition(
			accountEntryObjectDefinition.getId(), accountEntryObjectDefinition);

		postObjectDefinition = objectDefinitionResource.getObjectDefinition(
			postObjectDefinition.getId());

		Assert.assertTrue(postObjectDefinition.getAccountEntryRestricted());
		Assert.assertEquals(
			accountEntryRestrictedObjectFieldName,
			postObjectDefinition.getAccountEntryRestrictedObjectFieldName());

		_objectDefinitionLocalService.deleteObjectDefinition(
			postObjectDefinition.getId());

		// Storage type

		postObjectDefinition = testPutObjectDefinition_addObjectDefinition();

		randomObjectDefinition = randomObjectDefinition();

		randomObjectDefinition.setStorageType(
			ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT);

		try {
			objectDefinitionResource.putObjectDefinition(
				postObjectDefinition.getId(), randomObjectDefinition);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
		}

		_objectDefinitionLocalService.deleteObjectDefinition(
			postObjectDefinition.getId());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name", "status"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"dateCreated", "dateModified", "label", "userId"};
	}

	@Override
	protected ObjectDefinition randomObjectDefinition() throws Exception {
		ObjectDefinition objectDefinition = super.randomObjectDefinition();

		objectDefinition.setAccountEntryRestricted(false);
		objectDefinition.setAccountEntryRestrictedObjectFieldName("");
		objectDefinition.setActive(false);
		objectDefinition.setLabel(
			Collections.singletonMap(
				"en_US", "O" + objectDefinition.getName()));
		objectDefinition.setEnableLocalization(true);
		objectDefinition.setModifiable(true);
		objectDefinition.setName("O" + objectDefinition.getName());
		objectDefinition.setObjectFolderExternalReferenceCode(
			ObjectFolderConstants.EXTERNAL_REFERENCE_CODE_UNCATEGORIZED);
		objectDefinition.setPluralLabel(
			Collections.singletonMap(
				"en_US", "O" + objectDefinition.getName()));
		objectDefinition.setObjectFields(
			new ObjectField[] {
				new ObjectField() {
					{
						businessType = BusinessType.TEXT;
						DBType = ObjectField.DBType.create("String");
						indexed = false;
						indexedAsKeyword = false;
						label = Collections.singletonMap("en_US", "Column");
						localized = !objectDefinition.getSystem();
						name = StringUtil.randomId();
						readOnly = ReadOnly.FALSE;
						required = false;
						system = false;
					}
				}
			});
		objectDefinition.setScope(ObjectDefinitionConstants.SCOPE_COMPANY);
		objectDefinition.setStatus(
			new Status() {
				{
					code = WorkflowConstants.STATUS_DRAFT;
					label = WorkflowConstants.getStatusLabel(
						WorkflowConstants.STATUS_DRAFT);
					label_i18n = _language.get(
						LanguageResources.getResourceBundle(
							LocaleUtil.getDefault()),
						WorkflowConstants.getStatusLabel(
							WorkflowConstants.STATUS_DRAFT));
				}
			});
		objectDefinition.setSystem(false);

		if (!FeatureFlagManagerUtil.isEnabled("LPS-135430")) {
			objectDefinition.setStorageType(StringPool.BLANK);
		}

		return objectDefinition;
	}

	@Override
	protected ObjectDefinition testDeleteObjectDefinition_addObjectDefinition()
		throws Exception {

		return _addObjectDefinition(randomObjectDefinition());
	}

	@Override
	protected ObjectDefinition testGetObjectDefinition_addObjectDefinition()
		throws Exception {

		return _addObjectDefinition(randomObjectDefinition());
	}

	@Override
	protected ObjectDefinition
			testGetObjectDefinitionByExternalReferenceCode_addObjectDefinition()
		throws Exception {

		return _addObjectDefinition(randomObjectDefinition());
	}

	@Override
	protected ObjectDefinition testGetObjectDefinitionsPage_addObjectDefinition(
			ObjectDefinition objectDefinition)
		throws Exception {

		return _addObjectDefinition(objectDefinition);
	}

	@Override
	protected void testGetObjectDefinitionsPageWithFilter(
			String operator, EntityField.Type type)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		ObjectDefinition objectDefinition1 = randomObjectDefinition();

		objectDefinition1.setObjectFolderExternalReferenceCode(
			_objectFolder1.getExternalReferenceCode());

		objectDefinition1 = testGetObjectDefinitionsPage_addObjectDefinition(
			objectDefinition1);

		ObjectDefinition objectDefinition2 = randomObjectDefinition();

		objectDefinition2.setObjectFolderExternalReferenceCode(
			_objectFolder2.getExternalReferenceCode());

		testGetObjectDefinitionsPage_addObjectDefinition(objectDefinition2);

		for (EntityField entityField : entityFields) {
			Page<ObjectDefinition> page =
				objectDefinitionResource.getObjectDefinitionsPage(
					null, null,
					getFilterString(entityField, operator, objectDefinition1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(objectDefinition1),
				(List<ObjectDefinition>)page.getItems());
		}
	}

	@Override
	protected ObjectDefinition testGraphQLObjectDefinition_addObjectDefinition()
		throws Exception {

		return _addObjectDefinition(randomObjectDefinition());
	}

	@Override
	protected ObjectDefinition testPatchObjectDefinition_addObjectDefinition()
		throws Exception {

		return _addObjectDefinition(randomObjectDefinition());
	}

	@Override
	protected ObjectDefinition testPostObjectDefinition_addObjectDefinition(
			ObjectDefinition objectDefinition)
		throws Exception {

		return _addObjectDefinition(objectDefinition);
	}

	@Override
	protected ObjectDefinition
			testPostObjectDefinitionPublish_addObjectDefinition(
				ObjectDefinition objectDefinition)
		throws Exception {

		return _addObjectDefinition(objectDefinition);
	}

	@Override
	protected ObjectDefinition testPutObjectDefinition_addObjectDefinition()
		throws Exception {

		return _addObjectDefinition(randomObjectDefinition());
	}

	@Override
	protected ObjectDefinition
			testPutObjectDefinitionByExternalReferenceCode_addObjectDefinition()
		throws Exception {

		return _addObjectDefinition(randomObjectDefinition());
	}

	private ObjectDefinition _addObjectDefinition(
			ObjectDefinition objectDefinition)
		throws Exception {

		_objectDefinition = objectDefinitionResource.postObjectDefinition(
			objectDefinition);

		return _objectDefinition;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectDefinitionResourceTest.class);

	@Inject
	private Language _language;

	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private ObjectFolder _objectFolder1;
	private ObjectFolder _objectFolder2;

	@Inject
	private ObjectFolderLocalService _objectFolderLocalService;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}