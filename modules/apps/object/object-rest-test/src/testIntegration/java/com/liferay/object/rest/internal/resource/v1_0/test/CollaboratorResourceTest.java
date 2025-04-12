/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.service.SharingEntryLocalService;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
 * @author Mikel Lorza
 */
@FeatureFlags("LPD-17564")
@RunWith(Arquillian.class)
public class CollaboratorResourceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_objectDefinition = _getObjectDefinition();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_objectDefinitionLocalService.deleteObjectDefinition(_objectDefinition);
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		_users.clear();
		_userGroups.clear();
	}

	@Test
	public void testGetObjectEntryCollaboratorsPage() throws Exception {
		ObjectEntry objectEntry = _addObjectEntry();

		JSONArray jsonArray = JSONUtil.putAll(
			_getUserCollaboratorJSONObject(),
			_getUserGroupCollaboratorJSONObject(),
			_getUserCollaboratorJSONObject());

		HTTPTestUtil.invokeToJSONObject(
			jsonArray.toString(),
			StringBundler.concat(
				_objectDefinition.getRESTContextPath(), StringPool.SLASH,
				objectEntry.getObjectEntryId(), _COLLABORATORS),
			Http.Method.POST);

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				_objectDefinition.getRESTContextPath(), StringPool.SLASH,
				objectEntry.getObjectEntryId(), _COLLABORATORS),
			Http.Method.GET);

		_assertEquals(jsonArray, jsonObject.getJSONArray("items"));
	}

	@Test
	public void testGetScopeScopeKeyByExternalReferenceCodeCollaboratorsPage()
		throws Exception {

		ObjectEntry objectEntry = _addObjectEntry();

		JSONArray jsonArray = JSONUtil.putAll(
			_getUserCollaboratorJSONObject(),
			_getUserGroupCollaboratorJSONObject());

		HTTPTestUtil.invokeToJSONObject(
			jsonArray.toString(),
			StringBundler.concat(
				_objectDefinition.getRESTContextPath(), StringPool.SLASH,
				objectEntry.getObjectEntryId(), _COLLABORATORS),
			Http.Method.POST);

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				_objectDefinition.getRESTContextPath(), _SCOPES,
				_group.getGroupId(), _BY_EXTERNAL_REFERENCE_CODE,
				objectEntry.getExternalReferenceCode(), _COLLABORATORS),
			Http.Method.GET);

		_assertEquals(jsonArray, jsonObject.getJSONArray("items"));
	}

	@Test
	public void testPostObjectEntryCollaboratorsPage() throws Exception {
		ObjectEntry objectEntry = _addObjectEntry();

		JSONArray jsonArray = JSONUtil.putAll(
			_getUserCollaboratorJSONObject(),
			_getUserGroupCollaboratorJSONObject(),
			_getUserCollaboratorJSONObject());

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			jsonArray.toString(),
			StringBundler.concat(
				_objectDefinition.getRESTContextPath(), StringPool.SLASH,
				objectEntry.getObjectEntryId(), _COLLABORATORS),
			Http.Method.POST);

		_assertEquals(jsonArray, jsonObject.getJSONArray("items"));
	}

	@Test
	public void testPostScopeScopeKeyByExternalReferenceCodeCollaboratorsPage()
		throws Exception {

		ObjectEntry objectEntry = _addObjectEntry();

		JSONArray jsonArray = JSONUtil.putAll(
			_getUserCollaboratorJSONObject(),
			_getUserGroupCollaboratorJSONObject(),
			_getUserCollaboratorJSONObject(),
			_getUserGroupCollaboratorJSONObject());

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			jsonArray.toString(),
			StringBundler.concat(
				_objectDefinition.getRESTContextPath(), _SCOPES,
				_group.getGroupId(), _BY_EXTERNAL_REFERENCE_CODE,
				objectEntry.getExternalReferenceCode(), _COLLABORATORS),
			Http.Method.POST);

		_assertEquals(jsonArray, jsonObject.getJSONArray("items"));
	}

	private static ObjectDefinition _getObjectDefinition() throws Exception {
		return ObjectDefinitionTestUtil.publishObjectDefinition(
			true, ObjectDefinitionTestUtil.getRandomName(),
			Collections.singletonList(
				new TextObjectFieldBuilder(
				).labelMap(
					LocalizedMapUtil.getLocalizedMap(
						RandomTestUtil.randomString())
				).indexed(
					true
				).indexedAsKeyword(
					true
				).name(
					"title"
				).localized(
					false
				).build()),
			ObjectDefinitionConstants.SCOPE_SITE, TestPropsValues.getUserId());
	}

	private ObjectEntry _addObjectEntry() throws Exception {
		return _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_objectDefinition.getObjectDefinitionId(), 0, null,
			HashMapBuilder.<String, Serializable>put(
				"title", RandomTestUtil.randomString()
			).build(),
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));
	}

	private void _assertContains(
		JSONArray actualJSONArray, JSONObject expectedJSONObject) {

		boolean contains = false;

		for (int i = 0; i < actualJSONArray.length(); i++) {
			JSONObject actualJSONObject = actualJSONArray.getJSONObject(i);

			if (_equals(actualJSONObject, expectedJSONObject)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			actualJSONArray + " does not contain " + expectedJSONObject,
			contains);
	}

	private void _assertEquals(
		JSONArray actualJSONArray, JSONArray expectedJSONArray) {

		Assert.assertEquals(
			expectedJSONArray.length(), actualJSONArray.length());

		for (int i = 0; i < expectedJSONArray.length(); i++) {
			_assertContains(
				actualJSONArray, expectedJSONArray.getJSONObject(i));
		}
	}

	private boolean _equals(JSONObject jsonObject1, JSONObject jsonObject2) {
		for (String assertFieldName : _ASSERT_FIELD_NAMES) {
			if (Objects.equals(assertFieldName, "actionIds")) {
				if (!JSONUtil.equals(
						jsonObject1.getJSONArray("actionIds"),
						jsonObject2.getJSONArray("actionIds"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals(assertFieldName, "externalReferenceCode")) {
				if (!StringUtil.equals(
						jsonObject1.getString("externalReferenceCode"),
						jsonObject2.getString("externalReferenceCode"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals(assertFieldName, "name")) {
				if (!StringUtil.equals(
						jsonObject1.getString("name"),
						jsonObject2.getString("name"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals(assertFieldName, "share")) {
				if (!jsonObject1.getBoolean("share") == jsonObject2.getBoolean(
						"share")) {

					return false;
				}

				continue;
			}

			if (Objects.equals(assertFieldName, "type") &&
				!StringUtil.equals(
					jsonObject1.getString("type"),
					jsonObject2.getString("type"))) {

				return false;
			}
		}

		return true;
	}

	private User _getUser() throws Exception {
		User user = UserTestUtil.addUser();

		_users.add(user);

		return user;
	}

	private JSONObject _getUserCollaboratorJSONObject() throws Exception {
		User user = _getUser();

		return JSONUtil.put(
			"actionIds", JSONUtil.put(SharingEntryAction.VIEW.getActionId())
		).put(
			"externalReferenceCode", user.getExternalReferenceCode()
		).put(
			"name", user.getFullName()
		).put(
			"share", true
		).put(
			"type", "User"
		);
	}

	private UserGroup _getUserGroup() throws Exception {
		UserGroup userGroup = UserGroupTestUtil.addUserGroup();

		_userGroups.add(userGroup);

		return userGroup;
	}

	private JSONObject _getUserGroupCollaboratorJSONObject() throws Exception {
		UserGroup userGroup = _getUserGroup();

		return JSONUtil.put(
			"actionIds", JSONUtil.put(SharingEntryAction.VIEW.getActionId())
		).put(
			"externalReferenceCode", userGroup.getExternalReferenceCode()
		).put(
			"name", userGroup.getName()
		).put(
			"share", true
		).put(
			"type", "UserGroup"
		);
	}

	private static final String[] _ASSERT_FIELD_NAMES = {
		"actionIds", "externalReferenceCode", "name", "share", "type"
	};

	private static final String _BY_EXTERNAL_REFERENCE_CODE =
		"/by-external-reference-code/";

	private static final String _COLLABORATORS = "/collaborators";

	private static final String _SCOPES = "/scopes/";

	private static ObjectDefinition _objectDefinition;

	@Inject
	private static ObjectDefinitionLocalService _objectDefinitionLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private SharingEntryLocalService _sharingEntryLocalService;

	@DeleteAfterTestRun
	private List<UserGroup> _userGroups = new ArrayList<>();

	@DeleteAfterTestRun
	private List<User> _users = new ArrayList<>();

}