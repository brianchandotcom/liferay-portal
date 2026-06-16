/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.serializer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.frontend.data.set.serializer.FDSSerializer;
import com.liferay.frontend.data.set.test.util.FrontendDataSetTestUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.service.SharingEntryLocalService;

import jakarta.servlet.http.HttpServletRequest;

import java.io.Serializable;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Juanjo Fernandez
 */
@FeatureFlags(
	featureFlags = {@FeatureFlag("LPD-34594"), @FeatureFlag("LPS-164563")}
)
@RunWith(Arquillian.class)
public class SystemFDSSerializerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		FrontendDataSetTestUtil.initialize(SystemFDSSerializerTest.class);

		_objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_DATA_SET_SNAPSHOT", TestPropsValues.getCompanyId());

		Assert.assertNotNull(_objectDefinition);

		_fdsName = RandomTestUtil.randomString();
		_label = RandomTestUtil.randomString();

		_objectEntry = _addSnapshotObjectEntry(_fdsName, _label);

		_userGroup = UserGroupTestUtil.addUserGroup();

		_memberUser = UserTestUtil.addUser();
		_otherUser = UserTestUtil.addUser();

		_userLocalService.addUserGroupUser(
			_userGroup.getUserGroupId(), _memberUser.getUserId());

		_sharingEntry = _sharingEntryLocalService.addSharingEntry(
			null, TestPropsValues.getUserId(), 0, _userGroup.getUserGroupId(),
			0,
			_classNameLocalService.getClassNameId(
				_objectDefinition.getClassName()),
			_objectEntry.getObjectEntryId(), 0, true,
			Arrays.asList(SharingEntryAction.VIEW), null,
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));
	}

	@Test
	public void testSerializeSnapshotsSharedWithUserGroupForOtherUser()
		throws Exception {

		JSONArray jsonArray = _fdsSerializer.serializeSnapshots(
			_fdsName, _getHttpServletRequest(_otherUser.getUserId()));

		Assert.assertNull(
			"The snapshot shared with the user group is visible to a user " +
				"who does not belong to that group",
			_findSharedItemJSONObject(
				jsonArray, _objectEntry.getObjectEntryId()));
	}

	@Test
	public void testSerializeSnapshotsSharedWithUserGroupMember()
		throws Exception {

		JSONArray jsonArray = _fdsSerializer.serializeSnapshots(
			_fdsName, _getHttpServletRequest(_memberUser.getUserId()));

		JSONObject itemJSONObject = _findSharedItemJSONObject(
			jsonArray, _objectEntry.getObjectEntryId());

		Assert.assertEquals(_label, itemJSONObject.getString("label"));
	}

	private ObjectEntry _addSnapshotObjectEntry(String fdsName, String label)
		throws Exception {

		return _objectEntryLocalService.addObjectEntry(
			0, TestPropsValues.getUserId(),
			_objectDefinition.getObjectDefinitionId(), 0, null,
			HashMapBuilder.<String, Serializable>put(
				"fdsName", fdsName
			).put(
				"label", label
			).put(
				"viewConfig", "{}"
			).build(),
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));
	}

	private JSONObject _findSharedItemJSONObject(
		JSONArray jsonArray, long objectEntryId) {

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject groupJSONObject = jsonArray.getJSONObject(i);

			if (!groupJSONObject.getBoolean("headerVisible")) {
				continue;
			}

			JSONArray itemsJSONArray = groupJSONObject.getJSONArray("items");

			for (int j = 0; j < itemsJSONArray.length(); j++) {
				JSONObject itemJSONObject = itemsJSONArray.getJSONObject(j);

				if (itemJSONObject.getLong("id") == objectEntryId) {
					return itemJSONObject;
				}
			}
		}

		return null;
	}

	private HttpServletRequest _getHttpServletRequest(long userId)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.COMPANY_ID, TestPropsValues.getCompanyId());
		mockHttpServletRequest.setAttribute(WebKeys.LOCALE, LocaleUtil.US);
		mockHttpServletRequest.setAttribute(WebKeys.USER_ID, userId);

		return mockHttpServletRequest;
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	private String _fdsName;

	@Inject(filter = "frontend.data.set.serializer.type=system")
	private FDSSerializer _fdsSerializer;

	private String _label;

	@DeleteAfterTestRun
	private User _memberUser;

	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@DeleteAfterTestRun
	private ObjectEntry _objectEntry;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@DeleteAfterTestRun
	private User _otherUser;

	@DeleteAfterTestRun
	private SharingEntry _sharingEntry;

	@Inject
	private SharingEntryLocalService _sharingEntryLocalService;

	@DeleteAfterTestRun
	private UserGroup _userGroup;

	@Inject
	private UserLocalService _userLocalService;

}