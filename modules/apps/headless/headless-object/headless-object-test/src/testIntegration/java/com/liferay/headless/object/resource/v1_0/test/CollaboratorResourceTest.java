/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.object.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.object.client.dto.v1_0.Collaborator;
import com.liferay.headless.object.client.pagination.Page;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.service.SharingEntryLocalService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 */
@FeatureFlags("LPD-17564")
@RunWith(Arquillian.class)
public class CollaboratorResourceTest extends BaseCollaboratorResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_objectEntryFolder = _addObjectEntryFolder();
	}

	@Override
	@Test
	public void testGraphQLGetObjectEntryFolderCollaboratorByTypeCollaboratorTypeCollaboratorNotFound()
		throws Exception {

		Long irrelevantObjectEntryFolderId = RandomTestUtil.randomLong();
		Long irrelevantCollaboratorId = RandomTestUtil.randomLong();

		// No namespace

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"objectEntryFolderCollaboratorByTypeCollaborator" +
							"TypeCollaborator",
						HashMapBuilder.<String, Object>put(
							"collaboratorId", irrelevantCollaboratorId
						).put(
							"collaboratorType", "\"User\""
						).put(
							"objectEntryFolderId", irrelevantObjectEntryFolderId
						).build(),
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));

		// Using the namespace headlessObject_v1_0

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"headlessObject_v1_0",
						new GraphQLField(
							"objectEntryFolderCollaboratorByType" +
								"CollaboratorTypeCollaborator",
							HashMapBuilder.<String, Object>put(
								"collaboratorId", irrelevantCollaboratorId
							).put(
								"collaboratorType", "\"User\""
							).put(
								"objectEntryFolderId",
								irrelevantObjectEntryFolderId
							).build(),
							getGraphQLFields()))),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Override
	@Test
	public void testGraphQLGetScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorByTypeCollaboratorTypeCollaboratorNotFound()
		throws Exception {

		String irrelevantScopeKey = "\"" + RandomTestUtil.randomString() + "\"";
		String irrelevantExternalReferenceCode =
			"\"" + RandomTestUtil.randomString() + "\"";
		Long irrelevantCollaboratorId = RandomTestUtil.randomLong();

		// No namespace

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"scopeScopeKeyObjectEntryFolderByExternalReference" +
							"CodeCollaboratorByTypeCollaboratorType" +
								"Collaborator",
						HashMapBuilder.<String, Object>put(
							"collaboratorId", irrelevantCollaboratorId
						).put(
							"collaboratorType", "\"User\""
						).put(
							"externalReferenceCode",
							irrelevantExternalReferenceCode
						).put(
							"scopeKey", irrelevantScopeKey
						).build(),
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));

		// Using the namespace headlessObject_v1_0

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"headlessObject_v1_0",
						new GraphQLField(
							"scopeScopeKeyObjectEntryFolderByExternal" +
								"ReferenceCodeCollaboratorByTypeCollaborator" +
									"TypeCollaborator",
							HashMapBuilder.<String, Object>put(
								"collaboratorId", irrelevantCollaboratorId
							).put(
								"collaboratorType", "\"User\""
							).put(
								"externalReferenceCode",
								irrelevantExternalReferenceCode
							).put(
								"scopeKey", irrelevantScopeKey
							).build(),
							getGraphQLFields()))),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Override
	@Test
	public void testPostObjectEntryFolderCollaboratorsPage() throws Exception {
		ObjectEntryFolder objectEntryFolder = _addObjectEntryFolder();

		_addUserCollaborator(objectEntryFolder);

		Collaborator[] collaborators = {
			_getUserCollaborator(), _getUserGroupCollaborator(),
			_getUserGroupCollaborator()
		};

		Page<Collaborator> collaboratorsPage =
			collaboratorResource.postObjectEntryFolderCollaboratorsPage(
				objectEntryFolder.getObjectEntryFolderId(), collaborators);

		_assertCollaborators(
			(List<Collaborator>)collaboratorsPage.getItems(), collaborators);
	}

	@Override
	@Test
	public void testPostScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorsPage()
		throws Exception {

		ObjectEntryFolder objectEntryFolder = _addObjectEntryFolder();

		_addUserCollaborator(objectEntryFolder);

		Collaborator[] collaborators = {
			_getUserCollaborator(), _getUserGroupCollaborator(),
			_getUserGroupCollaborator()
		};

		Page<Collaborator> collaboratorsPage =
			collaboratorResource.
				postScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorsPage(
					testGroup.getGroupKey(),
					objectEntryFolder.getExternalReferenceCode(),
					collaborators);

		_assertCollaborators(
			(List<Collaborator>)collaboratorsPage.getItems(), collaborators);
	}

	@Override
	@Test
	public void testPutScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorByTypeCollaboratorTypeCollaborator()
		throws Exception {

		Collaborator postCollaborator =
			testPutScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorByTypeCollaboratorTypeCollaborator_addCollaborator();

		Collaborator randomCollaborator = randomCollaborator();

		Collaborator putCollaborator =
			collaboratorResource.
				putScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorByTypeCollaboratorTypeCollaborator(
					testPutScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorByTypeCollaboratorTypeCollaborator_getScopeKey(),
					testPutScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorByTypeCollaboratorTypeCollaborator_getExternalReferenceCode(
						postCollaborator),
					testPutScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorByTypeCollaboratorTypeCollaborator_getCollaboratorType(),
					postCollaborator.getId(), randomCollaborator);

		assertEquals(randomCollaborator, putCollaborator);
		assertValid(putCollaborator);

		Collaborator getCollaborator =
			collaboratorResource.
				getScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorByTypeCollaboratorTypeCollaborator(
					testPutScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorByTypeCollaboratorTypeCollaborator_getScopeKey(),
					testPutScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorByTypeCollaboratorTypeCollaborator_getExternalReferenceCode(
						postCollaborator),
					testPutScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorByTypeCollaboratorTypeCollaborator_getCollaboratorType(),
					putCollaborator.getId());

		assertEquals(randomCollaborator, getCollaborator);
		assertValid(getCollaborator);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"actionIds", "dateExpired", "share", "type"};
	}

	@Override
	protected Collaborator randomCollaborator() throws Exception {
		return new Collaborator() {
			{
				actionIds = new String[] {
					SharingEntryAction.VIEW.getActionId()
				};
				dateExpired = _randomDatePlusAYear();
				portrait = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				share = RandomTestUtil.randomBoolean();
				type = Type.USER;
			}
		};
	}

	@Override
	protected Collaborator
			testDeleteObjectEntryFolderCollaboratorByTypeCollaboratorTypeCollaborator_addCollaborator()
		throws Exception {

		return _addUserCollaborator(_objectEntryFolder);
	}

	@Override
	protected String
			testDeleteObjectEntryFolderCollaboratorByTypeCollaboratorTypeCollaborator_getCollaboratorType()
		throws Exception {

		return Collaborator.Type.USER.getValue();
	}

	@Override
	protected Long
			testDeleteObjectEntryFolderCollaboratorByTypeCollaboratorTypeCollaborator_getObjectEntryFolderId()
		throws Exception {

		return _objectEntryFolder.getObjectEntryFolderId();
	}

	@Override
	protected Collaborator
			testDeleteScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorByTypeCollaboratorTypeCollaborator_addCollaborator()
		throws Exception {

		return _addUserCollaborator(_objectEntryFolder);
	}

	@Override
	protected String
			testDeleteScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorByTypeCollaboratorTypeCollaborator_getCollaboratorType()
		throws Exception {

		return Collaborator.Type.USER.getValue();
	}

	@Override
	protected String
			testDeleteScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorByTypeCollaboratorTypeCollaborator_getExternalReferenceCode(
				Collaborator collaborator)
		throws Exception {

		return _objectEntryFolder.getExternalReferenceCode();
	}

	@Override
	protected String
			testDeleteScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorByTypeCollaboratorTypeCollaborator_getScopeKey()
		throws Exception {

		return testGroup.getGroupKey();
	}

	@Override
	protected Collaborator
			testGetObjectEntryFolderCollaboratorByTypeCollaboratorTypeCollaborator_addCollaborator()
		throws Exception {

		return _addUserGroupCollaborator(_objectEntryFolder);
	}

	@Override
	protected String
			testGetObjectEntryFolderCollaboratorByTypeCollaboratorTypeCollaborator_getCollaboratorType()
		throws Exception {

		return Collaborator.Type.USER_GROUP.getValue();
	}

	@Override
	protected Long
			testGetObjectEntryFolderCollaboratorByTypeCollaboratorTypeCollaborator_getObjectEntryFolderId()
		throws Exception {

		return _objectEntryFolder.getObjectEntryFolderId();
	}

	@Override
	protected Collaborator
			testGetObjectEntryFolderCollaboratorsPage_addCollaborator(
				Long objectEntryFolderId, Collaborator collaborator)
		throws Exception {

		return _addUserGroupCollaborator(
			_objectEntryFolderLocalService.getObjectEntryFolder(
				objectEntryFolderId));
	}

	@Override
	protected Long
			testGetObjectEntryFolderCollaboratorsPage_getObjectEntryFolderId()
		throws Exception {

		return _objectEntryFolder.getObjectEntryFolderId();
	}

	@Override
	protected Collaborator
			testGetScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorByTypeCollaboratorTypeCollaborator_addCollaborator()
		throws Exception {

		return _addUserCollaborator(_objectEntryFolder);
	}

	@Override
	protected String
			testGetScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorByTypeCollaboratorTypeCollaborator_getCollaboratorType()
		throws Exception {

		return Collaborator.Type.USER.getValue();
	}

	@Override
	protected String
			testGetScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorByTypeCollaboratorTypeCollaborator_getExternalReferenceCode(
				Collaborator collaborator)
		throws Exception {

		return _objectEntryFolder.getExternalReferenceCode();
	}

	@Override
	protected String
			testGetScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorByTypeCollaboratorTypeCollaborator_getScopeKey()
		throws Exception {

		return testGroup.getGroupKey();
	}

	@Override
	protected Collaborator
			testGetScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorsPage_addCollaborator(
				String scopeKey, String externalReferenceCode,
				Collaborator collaborator)
		throws Exception {

		return _addUserCollaborator(
			_objectEntryFolderLocalService.
				getObjectEntryFolderByExternalReferenceCode(
					externalReferenceCode, testGroup.getGroupId(),
					testCompany.getCompanyId()));
	}

	@Override
	protected String
			testGetScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorsPage_getExternalReferenceCode()
		throws Exception {

		ObjectEntryFolder objectEntryFolder = _addObjectEntryFolder();

		return objectEntryFolder.getExternalReferenceCode();
	}

	@Override
	protected String
			testGetScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorsPage_getScopeKey()
		throws Exception {

		return testGroup.getGroupKey();
	}

	@Override
	protected Collaborator testGraphQLCollaborator_addCollaborator()
		throws Exception {

		return _addUserCollaborator(_objectEntryFolder);
	}

	@Override
	protected String
			testGraphQLGetObjectEntryFolderCollaboratorByTypeCollaboratorTypeCollaborator_getCollaboratorType()
		throws Exception {

		return Collaborator.Type.USER.getValue();
	}

	@Override
	protected Long
			testGraphQLGetObjectEntryFolderCollaboratorByTypeCollaboratorTypeCollaborator_getObjectEntryFolderId()
		throws Exception {

		return _objectEntryFolder.getObjectEntryFolderId();
	}

	@Override
	protected String
			testGraphQLGetScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorByTypeCollaboratorTypeCollaborator_getCollaboratorType()
		throws Exception {

		return Collaborator.Type.USER.getValue();
	}

	@Override
	protected String
			testGraphQLGetScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorByTypeCollaboratorTypeCollaborator_getExternalReferenceCode(
				Collaborator collaborator)
		throws Exception {

		return _objectEntryFolder.getExternalReferenceCode();
	}

	@Override
	protected String
			testGraphQLGetScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorByTypeCollaboratorTypeCollaborator_getScopeKey()
		throws Exception {

		return testGroup.getGroupKey();
	}

	@Override
	protected Collaborator
			testPutObjectEntryFolderCollaboratorByTypeCollaboratorTypeCollaborator_addCollaborator()
		throws Exception {

		return _addUserCollaborator(_objectEntryFolder);
	}

	@Override
	protected String
			testPutObjectEntryFolderCollaboratorByTypeCollaboratorTypeCollaborator_getCollaboratorType()
		throws Exception {

		return Collaborator.Type.USER.getValue();
	}

	@Override
	protected Long
			testPutObjectEntryFolderCollaboratorByTypeCollaboratorTypeCollaborator_getObjectEntryFolderId()
		throws Exception {

		return _objectEntryFolder.getObjectEntryFolderId();
	}

	@Override
	protected Collaborator
			testPutScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorByTypeCollaboratorTypeCollaborator_addCollaborator()
		throws Exception {

		return _addUserCollaborator(_objectEntryFolder);
	}

	@Override
	protected String
			testPutScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorByTypeCollaboratorTypeCollaborator_getCollaboratorType()
		throws Exception {

		return Collaborator.Type.USER.getValue();
	}

	@Override
	protected String
			testPutScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorByTypeCollaboratorTypeCollaborator_getExternalReferenceCode(
				Collaborator collaborator)
		throws Exception {

		return _objectEntryFolder.getExternalReferenceCode();
	}

	@Override
	protected String
			testPutScopeScopeKeyObjectEntryFolderByExternalReferenceCodeCollaboratorByTypeCollaboratorTypeCollaborator_getScopeKey()
		throws Exception {

		return testGroup.getGroupKey();
	}

	private ObjectEntryFolder _addObjectEntryFolder() throws Exception {
		return _objectEntryFolderLocalService.addObjectEntryFolder(
			null, TestPropsValues.getUserId(), testGroup.getGroupId(),
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
			HashMapBuilder.put(
				LocaleUtil.ENGLISH, RandomTestUtil.randomString()
			).build(),
			RandomTestUtil.randomString(), new ServiceContext());
	}

	private Collaborator _addUserCollaborator(
			ObjectEntryFolder objectEntryFolder)
		throws Exception {

		User user = _getUser();

		return _toCollaborator(
			_sharingEntryLocalService.addSharingEntry(
				null, TestPropsValues.getUserId(), 0, user.getUserId(),
				_classNameLocalService.getClassNameId(
					ObjectEntryFolder.class.getName()),
				objectEntryFolder.getObjectEntryFolderId(),
				objectEntryFolder.getGroupId(), true,
				Arrays.asList(SharingEntryAction.VIEW), _randomDatePlusAYear(),
				ServiceContextTestUtil.getServiceContext(
					testGroup.getGroupId(), TestPropsValues.getUserId())));
	}

	private Collaborator _addUserGroupCollaborator(
			ObjectEntryFolder objectEntryFolder)
		throws Exception {

		UserGroup userGroup = _getUserGroup();

		return _toCollaborator(
			_sharingEntryLocalService.addSharingEntry(
				null, TestPropsValues.getUserId(), userGroup.getUserGroupId(),
				0,
				_classNameLocalService.getClassNameId(
					ObjectEntryFolder.class.getName()),
				objectEntryFolder.getObjectEntryFolderId(),
				testGroup.getGroupId(), true,
				Arrays.asList(SharingEntryAction.VIEW), _randomDatePlusAYear(),
				ServiceContextTestUtil.getServiceContext(
					testGroup.getGroupId(), TestPropsValues.getUserId())));
	}

	private void _assertCollaborators(
		List<Collaborator> actualCollaborators,
		Collaborator[] expectedCollaborators) {

		Assert.assertEquals(
			actualCollaborators.toString(), expectedCollaborators.length,
			actualCollaborators.size());

		for (Collaborator expectedCollaborator : expectedCollaborators) {
			assertContains(expectedCollaborator, actualCollaborators);
		}
	}

	private User _getUser() throws Exception {
		User user = UserTestUtil.addUser();

		_users.add(user);

		return user;
	}

	private Collaborator _getUserCollaborator() throws Exception {
		User user = _getUser();

		return new Collaborator() {
			{
				actionIds = new String[] {
					SharingEntryAction.VIEW.getActionId()
				};
				dateExpired = _randomDatePlusAYear();
				externalReferenceCode = user.getExternalReferenceCode();
				id = user.getUserId();
				name = user.getFullName();
				share = true;
				type = Type.USER;
			}
		};
	}

	private UserGroup _getUserGroup() throws Exception {
		UserGroup userGroup = UserGroupTestUtil.addUserGroup();

		_userGroups.add(userGroup);

		return userGroup;
	}

	private Collaborator _getUserGroupCollaborator() throws Exception {
		UserGroup userGroup = _getUserGroup();

		return new Collaborator() {
			{
				actionIds = new String[] {
					SharingEntryAction.VIEW.getActionId()
				};
				dateExpired = _randomDatePlusAYear();
				externalReferenceCode = userGroup.getExternalReferenceCode();
				id = userGroup.getUserGroupId();
				name = userGroup.getName();
				share = true;
				type = Type.USER_GROUP;
			}
		};
	}

	private Date _randomDatePlusAYear() {
		Calendar calendar = CalendarFactoryUtil.getCalendar();

		calendar.add(Calendar.YEAR, 1);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);

		return calendar.getTime();
	}

	private Collaborator _toCollaborator(SharingEntry sharingEntry)
		throws Exception {

		if (sharingEntry.getToUserId() > 0) {
			User user = _userLocalService.getUser(sharingEntry.getToUserId());

			return new Collaborator() {
				{
					actionIds = TransformUtil.transformToArray(
						SharingEntryAction.getSharingEntryActions(
							sharingEntry.getActionIds()),
						SharingEntryAction::getActionId, String.class);
					dateExpired = sharingEntry.getExpirationDate();
					externalReferenceCode = user.getExternalReferenceCode();
					id = user.getUserId();
					name = user.getFullName();
					share = sharingEntry.isShareable();
					type = Type.USER;
				}
			};
		}

		UserGroup userGroup = _userGroupLocalService.getUserGroup(
			sharingEntry.getToUserGroupId());

		return new Collaborator() {
			{
				actionIds = TransformUtil.transformToArray(
					SharingEntryAction.getSharingEntryActions(
						sharingEntry.getActionIds()),
					SharingEntryAction::getActionId, String.class);
				dateExpired = sharingEntry.getExpirationDate();
				externalReferenceCode = userGroup.getExternalReferenceCode();
				id = userGroup.getUserGroupId();
				name = userGroup.getName();
				share = sharingEntry.isShareable();
				type = Type.USER_GROUP;
			}
		};
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	private ObjectEntryFolder _objectEntryFolder;

	@Inject
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

	@Inject
	private SharingEntryLocalService _sharingEntryLocalService;

	@Inject
	private UserGroupLocalService _userGroupLocalService;

	@DeleteAfterTestRun
	private List<UserGroup> _userGroups = new ArrayList<>();

	@Inject
	private UserLocalService _userLocalService;

	@DeleteAfterTestRun
	private List<User> _users = new ArrayList<>();

}