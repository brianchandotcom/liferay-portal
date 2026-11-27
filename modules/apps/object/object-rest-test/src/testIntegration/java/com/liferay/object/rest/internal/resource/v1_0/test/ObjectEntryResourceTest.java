/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luis Miguel Barcos
 */
@FeatureFlags(featureFlags = @FeatureFlag(value = "LPS-164801"))
@RunWith(Arquillian.class)
public class ObjectEntryResourceTest extends BaseObjectEntryResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testGetObjectEntriesWithCreators() throws Exception {
		ObjectDefinition objectDefinition = getObjectDefinition1();

		User user1 = UserTestUtil.addUser();

		ObjectEntry objectEntry1 = _addObjectEntry(objectDefinition, user1);

		User user2 = UserTestUtil.addUser();

		ObjectEntry objectEntry2 = _addObjectEntry(objectDefinition, user2);

		ObjectEntry objectEntry3 = _addObjectEntry(objectDefinition, user1);

		User user3 = UserTestUtil.addUser();

		ObjectEntry objectEntry4 = _addObjectEntry(objectDefinition, user3);

		userLocalService.deleteUser(user3);

		JSONObject pageJSONObject = HTTPTestUtil.invokeToJSONObject(
			null, objectDefinition.getRESTContextPath(), Http.Method.GET);

		JSONArray itemsJSONArray = pageJSONObject.getJSONArray("items");

		Assert.assertEquals(
			itemsJSONArray.toString(), 4, itemsJSONArray.length());

		Map<Long, JSONObject> itemJSONObjects = new HashMap<>();

		for (int i = 0; i < itemsJSONArray.length(); i++) {
			JSONObject itemJSONObject = itemsJSONArray.getJSONObject(i);

			JSONObject jsonObject = getObjectEntryJSONObject(
				objectDefinition, itemJSONObject.getString("id"));

			Assert.assertEquals(
				String.valueOf(jsonObject.getJSONObject("creator")),
				String.valueOf(itemJSONObject.getJSONObject("creator")));

			itemJSONObjects.put(itemJSONObject.getLong("id"), itemJSONObject);
		}

		_assertCreator(
			itemJSONObjects.get(objectEntry1.getObjectEntryId()), user1);
		_assertCreator(
			itemJSONObjects.get(objectEntry2.getObjectEntryId()), user2);
		_assertCreator(
			itemJSONObjects.get(objectEntry3.getObjectEntryId()), user1);

		JSONObject itemJSONObject = itemJSONObjects.get(
			objectEntry4.getObjectEntryId());

		Assert.assertFalse(itemJSONObject.has("creator"));

		userLocalService.deleteUser(user1);
		userLocalService.deleteUser(user2);
	}

	@Test
	@TestInfo("LPD-53245")
	public void testPatchCustomObjectEntry() throws Exception {
		ObjectDefinition objectDefinition1 = getObjectDefinition1();

		assertPatchCustomObjectEntry(
			jsonObject ->
				objectDefinition1.getRESTContextPath() + "/" +
					jsonObject.getLong("id"),
			objectDefinition1, getObjectDefinition2(), testGroupId);

		ObjectDefinition siteScopedObjectDefinition1 =
			getSiteScopedObjectDefinition1();

		assertPatchCustomObjectEntry(
			jsonObject ->
				siteScopedObjectDefinition1.getRESTContextPath() + "/" +
					jsonObject.getLong("id"),
			siteScopedObjectDefinition1, getSiteScopedObjectDefinition2(),
			testGroupId);
	}

	@Test
	@TestInfo("LPD-53245")
	public void testPutCustomObjectEntry() throws Exception {
		ObjectDefinition objectDefinition1 = getObjectDefinition1();

		assertPutCustomObjectEntry(
			jsonObject ->
				objectDefinition1.getRESTContextPath() + "/" +
					jsonObject.getLong("id"),
			objectDefinition1, getObjectDefinition2(), testGroupId);

		ObjectDefinition siteScopedObjectDefinition1 =
			getSiteScopedObjectDefinition1();

		assertPutCustomObjectEntry(
			jsonObject ->
				siteScopedObjectDefinition1.getRESTContextPath() + "/" +
					jsonObject.getLong("id"),
			siteScopedObjectDefinition1, getSiteScopedObjectDefinition2(),
			testGroupId);
	}

	private ObjectEntry _addObjectEntry(
			ObjectDefinition objectDefinition, User user)
		throws Exception {

		return objectEntryLocalService.addObjectEntry(
			0, user.getUserId(), objectDefinition.getObjectDefinitionId(),
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
			null,
			HashMapBuilder.<String, Serializable>put(
				OBJECT_FIELD_NAME_1, RandomTestUtil.randomString()
			).build(),
			ServiceContextTestUtil.getServiceContext());
	}

	private void _assertCreator(JSONObject itemJSONObject, User user) {
		JSONObject creatorJSONObject = itemJSONObject.getJSONObject("creator");

		Assert.assertEquals(user.getUserId(), creatorJSONObject.getLong("id"));
		Assert.assertEquals(
			user.getFullName(), creatorJSONObject.getString("name"));
	}

}