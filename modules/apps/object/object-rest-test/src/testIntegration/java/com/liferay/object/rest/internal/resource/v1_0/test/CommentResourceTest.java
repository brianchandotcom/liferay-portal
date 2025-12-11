/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.test.util.ObjectEntryTestUtil;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Arrays;

import org.junit.After;
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
@FeatureFlag("LPD-69419")
@RunWith(Arquillian.class)
public class CommentResourceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_testGroupId = TestPropsValues.getGroupId();
	}

	@Before
	public void setUp() throws Exception {
		_objectDefinition = ObjectDefinitionTestUtil.publishObjectDefinition(
			ObjectDefinitionTestUtil.getRandomName(),
			Arrays.asList(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
					RandomTestUtil.randomString(), _OBJECT_FIELD_NAME, false)),
			ObjectDefinitionConstants.SCOPE_COMPANY);

		_siteScopedObjectDefinition =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				ObjectDefinitionTestUtil.getRandomName(),
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						ObjectFieldConstants.BUSINESS_TYPE_TEXT,
						ObjectFieldConstants.DB_TYPE_STRING, true, true, null,
						RandomTestUtil.randomString(), _OBJECT_FIELD_NAME,
						false)),
				ObjectDefinitionConstants.SCOPE_SITE);
	}

	@After
	public void tearDown() throws Exception {
		_objectDefinitionLocalService.deleteObjectDefinition(_objectDefinition);
		_objectDefinitionLocalService.deleteObjectDefinition(
			_siteScopedObjectDefinition);
	}

	@FeatureFlag("LPD-69419")
	@Test
	public void testDeleteByExternalReferenceCodeComment() throws Exception {

		// Company scope

		_testDeleteByExternalReferenceCodeComment(0L, _objectDefinition);

		// Site scope

		_testDeleteByExternalReferenceCodeComment(
			_testGroupId, _siteScopedObjectDefinition);
	}

	@FeatureFlag("LPD-69419")
	@Test
	public void testGetByExternalReferenceCodeComment() throws Exception {

		// Company scope

		_testGetByExternalReferenceCodeComment(0L, _objectDefinition);

		// Site scope

		_testGetByExternalReferenceCodeComment(
			_testGroupId, _siteScopedObjectDefinition);
	}

	@FeatureFlag("LPD-69419")
	@Test
	public void testGetByExternalReferenceCodeCommentChildCommentsPage()
		throws Exception {

		// Company scope

		_testGetByExternalReferenceCodeCommentChildCommentsPage(
			0L, _objectDefinition);

		// Site scope

		_testGetByExternalReferenceCodeCommentChildCommentsPage(
			_testGroupId, _siteScopedObjectDefinition);
	}

	@FeatureFlag("LPD-69419")
	@Test
	public void testGetByExternalReferenceCodeCommentsPage() throws Exception {

		// Company scope

		_testGetByExternalReferenceCodeCommentsPage(0L, _objectDefinition);

		// Site scope

		_testGetByExternalReferenceCodeCommentsPage(
			_testGroupId, _siteScopedObjectDefinition);
	}

	@FeatureFlag("LPD-69419")
	@Test
	public void testPostByExternalReferenceCodeComment() throws Exception {

		// Company scope

		_testPostByExternalReferenceCodeComment(0L, _objectDefinition);

		// Site scope

		_testPostByExternalReferenceCodeComment(
			_testGroupId, _siteScopedObjectDefinition);
	}

	@FeatureFlag("LPD-69419")
	@Test
	public void testPostByExternalReferenceCodeCommentReplyComment()
		throws Exception {

		// Company scope

		_testPostByExternalReferenceCodeCommentReplyComment(
			0L, _objectDefinition);

		// Site scope

		_testPostByExternalReferenceCodeCommentReplyComment(
			_testGroupId, _siteScopedObjectDefinition);
	}

	@FeatureFlag("LPD-69419")
	@Test
	public void testPutByExternalReferenceCodeComment() throws Exception {

		// Company scope

		_testPutByExternalReferenceCodeComment(0L, _objectDefinition);

		// Site scope

		_testPutByExternalReferenceCodeComment(
			_testGroupId, _siteScopedObjectDefinition);
	}

	private void _assertComment(
			String endpoint, String text, String externalReferenceCode,
			JSONObject jsonObject1, JSONObject jsonObject2)
		throws Exception {

		// Aggregation

		JSONObject jsonObject3 = HTTPTestUtil.invokeToJSONObject(
			null, endpoint + "?aggregationTerms=creatorId", Http.Method.GET);

		JSONArray jsonArray = jsonObject3.getJSONArray("facets");

		Assert.assertEquals(1, jsonArray.length());

		jsonObject3 = jsonArray.getJSONObject(0);

		Assert.assertEquals(
			"creatorId", jsonObject3.getString("facetCriteria"));

		jsonArray = jsonObject3.getJSONArray("facetValues");

		jsonObject3 = jsonArray.getJSONObject(0);

		Assert.assertEquals(2, jsonObject3.getInt("numberOfOccurrences"));

		JSONObject jsonObject4 = jsonObject1.getJSONObject("creator");

		Assert.assertEquals(
			jsonObject4.getInt("id"), jsonObject3.getInt("term"));

		// Filter

		String filter = URLCodec.encodeURL(
			"dateCreated eq " + jsonObject1.getString("dateCreated"));

		jsonObject3 = HTTPTestUtil.invokeToJSONObject(
			null, endpoint + "?filter=" + filter, Http.Method.GET);

		jsonArray = jsonObject3.getJSONArray("items");

		Assert.assertEquals(1, jsonArray.length());

		jsonObject3 = jsonArray.getJSONObject(0);

		Assert.assertEquals(
			"<p>" + text + "</p>", jsonObject3.getString("text"));
		Assert.assertEquals(
			jsonObject1.getString("dateCreated"),
			jsonObject3.getString("dateCreated"));

		// Search

		jsonObject3 = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				endpoint, "?search=", URLCodec.encodeURL(text)),
			Http.Method.GET);

		jsonArray = jsonObject3.getJSONArray("items");

		Assert.assertEquals(1, jsonArray.length());

		jsonObject3 = jsonArray.getJSONObject(0);

		Assert.assertEquals(
			externalReferenceCode,
			jsonObject3.getString("externalReferenceCode"));

		// Sort

		jsonObject3 = HTTPTestUtil.invokeToJSONObject(
			null, endpoint + "?sort=dateCreated:desc", Http.Method.GET);

		jsonArray = jsonObject3.getJSONArray("items");

		jsonObject3 = jsonArray.getJSONObject(0);

		jsonObject4 = jsonArray.getJSONObject(1);

		Assert.assertEquals(
			jsonObject1.getString("externalReferenceCode"),
			jsonObject3.getString("externalReferenceCode"));

		Assert.assertEquals(
			jsonObject2.getString("externalReferenceCode"),
			jsonObject4.getString("externalReferenceCode"));
	}

	private ObjectDefinition _enableComments(ObjectDefinition objectDefinition)
		throws Exception {

		objectDefinition.setEnableComments(true);

		return _objectDefinitionLocalService.updateObjectDefinition(
			objectDefinition);
	}

	private String _getEndpoint(
		ObjectDefinition objectDefinition, Object scopeKey) {

		ObjectScopeProvider objectScopeProvider =
			_objectScopeProviderRegistry.getObjectScopeProvider(
				objectDefinition.getScope());

		if (objectScopeProvider.isGroupAware()) {
			return objectDefinition.getRESTContextPath() + "/scopes/" +
				scopeKey;
		}

		return objectDefinition.getRESTContextPath();
	}

	private JSONObject _postComment(
			String endpoint, String externalReferenceCode, String text)
		throws Exception {

		return HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"externalReferenceCode", externalReferenceCode
			).put(
				"text", text
			).toString(),
			endpoint, Http.Method.POST);
	}

	private String _prepareCommentsEndpoint(
			long scopeKey, ObjectDefinition objectDefinition)
		throws Exception {

		_enableComments(objectDefinition);

		ObjectEntry objectEntry = ObjectEntryTestUtil.addObjectEntry(
			objectDefinition, _OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE);

		return StringBundler.concat(
			_getEndpoint(objectDefinition, scopeKey),
			"/by-external-reference-code/",
			objectEntry.getExternalReferenceCode(), "/comments");
	}

	private void _testDeleteByExternalReferenceCodeComment(
			long groupId, ObjectDefinition objectDefinition)
		throws Exception {

		_enableComments(objectDefinition);

		ObjectEntry objectEntry = ObjectEntryTestUtil.addObjectEntry(
			objectDefinition, _OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE);

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"externalReferenceCode", RandomTestUtil.randomString()
			).put(
				"text", RandomTestUtil.randomString()
			).toString(),
			StringBundler.concat(
				_getEndpoint(objectDefinition, groupId),
				"/by-external-reference-code/",
				objectEntry.getExternalReferenceCode(), "/comments"),
			Http.Method.POST);

		Assert.assertEquals(
			204,
			HTTPTestUtil.invokeToHttpCode(
				null,
				StringBundler.concat(
					_getEndpoint(objectDefinition, groupId),
					"/by-external-reference-code/",
					objectEntry.getExternalReferenceCode(), "/comments/",
					jsonObject.getString("externalReferenceCode")),
				Http.Method.DELETE));
	}

	private void _testGetByExternalReferenceCodeComment(
			long groupId, ObjectDefinition objectDefinition)
		throws Exception {

		String endpoint = _prepareCommentsEndpoint(groupId, objectDefinition);
		String externalReferenceCode = RandomTestUtil.randomString();

		JSONObject jsonObject1 = _postComment(
			endpoint, externalReferenceCode, RandomTestUtil.randomString());

		JSONObject jsonObject2 = HTTPTestUtil.invokeToJSONObject(
			null,
			StringBundler.concat(
				endpoint, StringPool.SLASH, externalReferenceCode),
			Http.Method.GET);

		Assert.assertEquals(
			jsonObject1.getString("externalReferenceCode"),
			jsonObject2.getString("externalReferenceCode"));

		Assert.assertEquals(
			jsonObject1.getString("text"), jsonObject2.getString("text"));
	}

	private void _testGetByExternalReferenceCodeCommentChildCommentsPage(
			long groupId, ObjectDefinition objectDefinition)
		throws Exception {

		String endpoint = _prepareCommentsEndpoint(groupId, objectDefinition);
		String externalReferenceCode = RandomTestUtil.randomString();

		_postComment(
			endpoint, externalReferenceCode, RandomTestUtil.randomString());

		endpoint = StringBundler.concat(
			endpoint, StringPool.SLASH, externalReferenceCode,
			"/child-comments");

		JSONObject jsonObject1 = _postComment(
			endpoint, RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		externalReferenceCode = RandomTestUtil.randomString();
		String text = RandomTestUtil.randomString();

		JSONObject jsonObject2 = _postComment(
			endpoint, externalReferenceCode, text);

		_assertComment(
			endpoint, text, externalReferenceCode, jsonObject2, jsonObject1);
	}

	private void _testGetByExternalReferenceCodeCommentsPage(
			long groupId, ObjectDefinition objectDefinition)
		throws Exception {

		String endpoint = _prepareCommentsEndpoint(groupId, objectDefinition);

		JSONObject jsonObject1 = _postComment(
			endpoint, RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		String externalReferenceCode = RandomTestUtil.randomString();
		String text = RandomTestUtil.randomString();

		JSONObject jsonObject2 = _postComment(
			endpoint, externalReferenceCode, text);

		_assertComment(
			endpoint, text, externalReferenceCode, jsonObject2, jsonObject1);
	}

	private void _testPostByExternalReferenceCodeComment(
			long groupId, ObjectDefinition objectDefinition)
		throws Exception {

		ObjectEntry objectEntry = ObjectEntryTestUtil.addObjectEntry(
			objectDefinition, _OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE);

		String externalReferenceCode = RandomTestUtil.randomString();
		String text = RandomTestUtil.randomString();

		JSONObject bodyJSONObject = JSONUtil.put(
			"externalReferenceCode", externalReferenceCode
		).put(
			"text", text
		);

		String endpoint = StringBundler.concat(
			_getEndpoint(objectDefinition, groupId),
			"/by-external-reference-code/",
			objectEntry.getExternalReferenceCode(), "/comments");

		Assert.assertEquals(
			400,
			HTTPTestUtil.invokeToHttpCode(
				bodyJSONObject.toString(), endpoint, Http.Method.POST));

		_enableComments(objectDefinition);

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			bodyJSONObject.toString(), endpoint, Http.Method.POST);

		Assert.assertEquals(
			externalReferenceCode, jsonObject.get("externalReferenceCode"));
		Assert.assertEquals("<p>" + text + "</p>", jsonObject.get("text"));
	}

	private void _testPostByExternalReferenceCodeCommentReplyComment(
			long groupId, ObjectDefinition objectDefinition)
		throws Exception {

		_enableComments(objectDefinition);

		ObjectEntry objectEntry = ObjectEntryTestUtil.addObjectEntry(
			objectDefinition, _OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE);

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"externalReferenceCode", RandomTestUtil.randomString()
			).put(
				"text", RandomTestUtil.randomString()
			).toString(),
			StringBundler.concat(
				_getEndpoint(objectDefinition, groupId),
				"/by-external-reference-code/",
				objectEntry.getExternalReferenceCode(), "/comments"),
			Http.Method.POST);

		long parentCommentId = jsonObject.getLong("id");

		String externalReferenceCode = RandomTestUtil.randomString();
		String text = RandomTestUtil.randomString();

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"externalReferenceCode", externalReferenceCode
			).put(
				"text", text
			).toString(),
			StringBundler.concat(
				_getEndpoint(objectDefinition, groupId),
				"/by-external-reference-code/",
				objectEntry.getExternalReferenceCode(), "/comments/",
				jsonObject.getString("externalReferenceCode"),
				"/child-comments"),
			Http.Method.POST);

		Assert.assertEquals(
			externalReferenceCode, jsonObject.get("externalReferenceCode"));
		Assert.assertEquals("<p>" + text + "</p>", jsonObject.get("text"));
		Assert.assertEquals(
			parentCommentId, jsonObject.getLong("parentCommentId"));
	}

	private void _testPutByExternalReferenceCodeComment(
			long groupId, ObjectDefinition objectDefinition)
		throws Exception {

		_enableComments(objectDefinition);

		ObjectEntry objectEntry = ObjectEntryTestUtil.addObjectEntry(
			objectDefinition, _OBJECT_FIELD_NAME, _OBJECT_FIELD_VALUE);

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"externalReferenceCode", RandomTestUtil.randomString()
			).put(
				"text", RandomTestUtil.randomString()
			).toString(),
			StringBundler.concat(
				_getEndpoint(objectDefinition, groupId),
				"/by-external-reference-code/",
				objectEntry.getExternalReferenceCode(), "/comments"),
			Http.Method.POST);

		String text = RandomTestUtil.randomString();

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"text", text
			).toString(),
			StringBundler.concat(
				_getEndpoint(objectDefinition, groupId),
				"/by-external-reference-code/",
				objectEntry.getExternalReferenceCode(), "/comments/",
				jsonObject.getString("externalReferenceCode")),
			Http.Method.PUT);

		Assert.assertEquals("<p>" + text + "</p>", jsonObject.get("text"));
	}

	private static final String _OBJECT_FIELD_NAME =
		"x" + RandomTestUtil.randomString();

	private static final int _OBJECT_FIELD_VALUE = RandomTestUtil.randomInt();

	private static long _testGroupId;

	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectScopeProviderRegistry _objectScopeProviderRegistry;

	private ObjectDefinition _siteScopedObjectDefinition;

}