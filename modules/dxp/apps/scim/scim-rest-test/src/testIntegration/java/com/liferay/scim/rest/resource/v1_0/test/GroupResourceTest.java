/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.scim.rest.client.dto.v1_0.Group;
import com.liferay.scim.rest.client.dto.v1_0.Meta;
import com.liferay.scim.rest.client.dto.v1_0.MultiValuedAttribute;
import com.liferay.scim.rest.client.dto.v1_0.Name;
import com.liferay.scim.rest.client.dto.v1_0.User;
import com.liferay.scim.rest.client.http.HttpInvoker;
import com.liferay.scim.rest.client.resource.v1_0.UserResource;
import com.liferay.scim.rest.resource.v1_0.test.util.ScimTestUtil;

import java.util.Arrays;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Olivér Kecskeméty
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class GroupResourceTest extends BaseGroupResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseUserResourceTestCase.setUpClass();

		UserResource.Builder builder = UserResource.builder();

		_userResource = builder.authentication(
			"test@liferay.com", PropsValues.DEFAULT_ADMIN_PASSWORD
		).locale(
			LocaleUtil.getDefault()
		).build();
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_pid = ConfigurationTestUtil.createFactoryConfiguration(
			"com.liferay.scim.rest.internal.configuration." +
				"ScimClientOAuth2ApplicationConfiguration",
			HashMapDictionaryBuilder.<String, Object>put(
				"companyId", TestPropsValues.getCompanyId()
			).put(
				"matcherField", "email"
			).put(
				"oAuth2ApplicationName", "scim-client-test"
			).put(
				"userId", TestPropsValues.getUserId()
			).build());
	}

	@After
	@Override
	public void tearDown() throws Exception {
		ConfigurationTestUtil.deleteConfiguration(_pid);
	}

	@Override
	@Test
	public void testDeleteV2Group() throws Exception {
		Group group = testDeleteV2Group_addGroup();

		assertHttpResponseStatusCode(
			204, groupResource.deleteV2GroupHttpResponse(group.getId()));

		assertHttpResponseStatusCode(
			404, groupResource.getV2GroupByIdHttpResponse(group.getId()));

		Assert.assertNull(
			_userGroupLocalService.fetchUserGroupByExternalReferenceCode(
				group.getExternalId(), TestPropsValues.getCompanyId()));

		// Delete an existing group with no SCIM client ID

		UserGroup userGroup = _userGroupLocalService.addUserGroup(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			RandomTestUtil.randomString(), null, new ServiceContext());

		assertHttpResponseStatusCode(
			404,
			groupResource.deleteV2GroupHttpResponse(
				String.valueOf(userGroup.getUserGroupId())));

		// Delete an existing group provided by another SCIM client

		ScimTestUtil.saveSCIMClientId(
			UserGroup.class.getName(), userGroup.getUserGroupId(),
			userGroup.getCompanyId());

		assertHttpResponseStatusCode(
			409,
			groupResource.deleteV2GroupHttpResponse(
				String.valueOf(userGroup.getUserGroupId())));

		ConfigurationTestUtil.deleteConfiguration(_pid);

		assertHttpResponseStatusCode(
			404, groupResource.deleteV2GroupHttpResponse("12345"));
	}

	@Override
	@Test
	public void testGetV2GroupById() throws Exception {
		assertHttpResponseStatusCode(
			404, groupResource.getV2GroupByIdHttpResponse("12345"));

		Group group = testDeleteV2Group_addGroup();

		HttpInvoker.HttpResponse httpResponse =
			groupResource.getV2GroupByIdHttpResponse(group.getId());

		assertHttpResponseStatusCode(200, httpResponse);
		assertValid(Group.toDTO(httpResponse.getContent()));

		ConfigurationTestUtil.deleteConfiguration(_pid);

		assertHttpResponseStatusCode(
			404, groupResource.getV2GroupByIdHttpResponse(group.getId()));
	}

	@Override
	@Test
	public void testGetV2Groups() throws Exception {
		_userGroupLocalService.addUserGroup(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			RandomTestUtil.randomString(), null, new ServiceContext());

		_assertListResponse(groupResource.getV2Groups(5, 0), 0, 0);

		Group group1 = testDeleteV2Group_addGroup();
		Group group2 = testDeleteV2Group_addGroup();

		_assertListResponse(
			groupResource.getV2Groups(5, 0), 2, 2, group1, group2);

		Group group3 = testDeleteV2Group_addGroup();

		_assertListResponse(groupResource.getV2Groups(5, 3), 3, 1, group3);

		ConfigurationTestUtil.deleteConfiguration(_pid);

		assertHttpResponseStatusCode(
			404, groupResource.getV2GroupsHttpResponse(5, 0));
	}

	@Override
	@Test
	public void testPostV2Group() throws Exception {
		Group postGroup1 = randomGroup();

		User user = _addUser();

		postGroup1.setMembers(
			new MultiValuedAttribute[] {
				new MultiValuedAttribute() {
					{
						Meta meta = user.getMeta();

						$ref = meta.getLocation();

						value = user.getId();
					}
				}
			});

		groupResource.postV2Group(postGroup1);

		UserGroup userGroup1 =
			_userGroupLocalService.getUserGroupByExternalReferenceCode(
				postGroup1.getExternalId(), TestPropsValues.getCompanyId());

		assertEquals(
			postGroup1, _getGroup(String.valueOf(userGroup1.getUserGroupId())));

		// Provision an existing group with no SCIM client ID set

		Group postGroup2 = randomGroup();

		UserGroup userGroup2 = _userGroupLocalService.addUserGroup(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			postGroup2.getDisplayName(), null, new ServiceContext());

		postGroup2.setExternalId(userGroup2.getExternalReferenceCode());

		postGroup2.setMembers(
			new MultiValuedAttribute[] {
				new MultiValuedAttribute() {
					{
						Meta meta = user.getMeta();

						$ref = meta.getLocation();

						value = user.getId();
					}
				}
			});

		HttpInvoker.HttpResponse httpResponse =
			groupResource.postV2GroupHttpResponse(postGroup2);

		postGroup2 = Group.toDTO(httpResponse.getContent());

		Assert.assertEquals(
			userGroup2.getUserGroupId(),
			GetterUtil.getInteger(postGroup2.getId()));

		Assert.assertEquals(
			postGroup2.getExternalId(), userGroup2.getExternalReferenceCode());

		ConfigurationTestUtil.deleteConfiguration(_pid);

		assertHttpResponseStatusCode(
			404, groupResource.postV2GroupHttpResponse(randomGroup()));
	}

	@Ignore
	@Override
	@Test
	public void testPostV2GroupSearch() throws Exception {
	}

	@Override
	@Test
	public void testPutV2Group() throws Exception {
		Group group = testDeleteV2Group_addGroup();

		User user1 = _addUser();
		User user2 = _addUser();

		group.setMembers(
			new MultiValuedAttribute[] {
				new MultiValuedAttribute() {
					{
						Meta meta = user1.getMeta();

						$ref = meta.getLocation();

						value = user1.getId();
					}
				},
				new MultiValuedAttribute() {
					{
						Meta meta = user2.getMeta();

						$ref = meta.getLocation();

						value = user2.getId();
					}
				}
			});

		HttpInvoker.HttpResponse httpResponse =
			groupResource.putV2GroupHttpResponse(group.getId(), group);

		assertEquals(group, Group.toDTO(httpResponse.getContent()));

		group.setMembers(new MultiValuedAttribute[0]);

		httpResponse = groupResource.putV2GroupHttpResponse(
			group.getId(), group);

		assertEquals(group, Group.toDTO(httpResponse.getContent()));

		ConfigurationTestUtil.deleteConfiguration(_pid);

		assertHttpResponseStatusCode(
			404, groupResource.putV2GroupHttpResponse("12345", randomGroup()));
	}

	@Override
	protected void assertEquals(Group group1, Group group2) {
		super.assertEquals(group1, group2);

		Assert.assertEquals(
			ArrayUtil.getLength(group1.getMembers()),
			ArrayUtil.getLength(group2.getMembers()));

		if (ArrayUtil.isEmpty(group1.getMembers())) {
			return;
		}

		String[] group1MemberValues = TransformUtil.transform(
			group1.getMembers(), member -> member.getValue(), String.class);

		for (MultiValuedAttribute memberMultiValuedAttribute2 :
				group2.getMembers()) {

			Assert.assertTrue(
				ArrayUtil.contains(
					group1MemberValues, memberMultiValuedAttribute2.getValue(),
					false));
		}
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"displayName", "externalId"};
	}

	@Override
	protected Group testDeleteV2Group_addGroup() throws Exception {
		Group group = randomGroup();

		HttpInvoker.HttpResponse httpResponse =
			groupResource.postV2GroupHttpResponse(group);

		Assert.assertEquals(2, httpResponse.getStatusCode() / 100);

		JSONObject groupJSONObject = _jsonFactory.createJSONObject(
			httpResponse.getContent());

		group.setId(groupJSONObject.getString("id"));

		return group;
	}

	private User _addUser() throws Exception {
		String emailPrefix = StringUtil.toLowerCase(
			RandomTestUtil.randomString());

		HttpInvoker.HttpResponse httpResponse =
			_userResource.postV2UserHttpResponse(
				new User() {
					{
						active = true;
						displayName = StringUtil.toLowerCase(
							RandomTestUtil.randomString());
						emails = new MultiValuedAttribute[] {
							new MultiValuedAttribute() {
								{
									primary = true;
									type = "default";
									value = emailPrefix + "@liferay.com";
								}
							}
						};
						externalId = StringUtil.toLowerCase(
							RandomTestUtil.randomString());
						locale = StringUtil.toLowerCase(
							RandomTestUtil.randomString());
						name = new Name() {
							{
								familyName = RandomTestUtil.randomString();
								givenName = RandomTestUtil.randomString();
								middleName = RandomTestUtil.randomString();
							}
						};
						password = StringUtil.toLowerCase(
							RandomTestUtil.randomString());
						preferredLanguage = StringUtil.toLowerCase(
							RandomTestUtil.randomString());
						profileUrl = StringUtil.toLowerCase(
							RandomTestUtil.randomString());
						schemas = new String[] {
							"urn:ietf:params:scim:schemas:core:2.0:User"
						};
						timezone = StringUtil.toLowerCase(
							RandomTestUtil.randomString());
						title = StringUtil.toLowerCase(
							RandomTestUtil.randomString());
						userName = StringUtil.toLowerCase(
							RandomTestUtil.randomString());
						userType = StringUtil.toLowerCase(
							RandomTestUtil.randomString());
					}
				});

		return User.toDTO(httpResponse.getContent());
	}

	private void _assertListResponse(
			Object response, long expectedTotalResults,
			long expectedItemsPerPage, Group... expectedGroups)
		throws Exception {

		JSONObject listResponseJSONObject = _jsonFactory.createJSONObject(
			response.toString());

		JSONArray schemasJSONArray = listResponseJSONObject.getJSONArray(
			"schemas");

		Assert.assertEquals(
			"urn:ietf:params:scim:api:messages:2.0:ListResponse",
			schemasJSONArray.get(0));

		Assert.assertEquals(
			expectedTotalResults,
			listResponseJSONObject.getLong("totalResults"));
		Assert.assertEquals(
			expectedItemsPerPage,
			listResponseJSONObject.getLong("itemsPerPage"));

		if (ArrayUtil.isEmpty(expectedGroups)) {
			Assert.assertFalse(listResponseJSONObject.has("Resources"));

			return;
		}

		JSONArray resourcesJSONArray = listResponseJSONObject.getJSONArray(
			"Resources");

		Assert.assertEquals(expectedGroups.length, resourcesJSONArray.length());

		for (int i = 0; i < resourcesJSONArray.length(); i++) {
			JSONObject groupJSONObject = resourcesJSONArray.getJSONObject(i);

			assertContains(
				Group.toDTO(groupJSONObject.toString()),
				Arrays.asList(expectedGroups));
		}
	}

	private Group _getGroup(String userId) throws Exception {
		Object groupObject = groupResource.getV2GroupById(userId);

		return Group.toDTO(groupObject.toString());
	}

	private static String _pid;
	private static UserResource _userResource;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private UserGroupLocalService _userGroupLocalService;

}