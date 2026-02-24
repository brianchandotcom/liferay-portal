/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.dsr.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.dsr.client.dto.v1_0.InvitedMember;
import com.liferay.headless.dsr.client.dto.v1_0.UserAccount;
import com.liferay.headless.dsr.client.resource.v1_0.UserAccountResource;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.site.dsr.site.initializer.test.util.DSRTestUtil;

import java.io.Serializable;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@FeatureFlag("LPD-66359")
@RunWith(Arquillian.class)
public class InvitedMemberResourceTest
	extends BaseInvitedMemberResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		DSRTestUtil.getOrAddGroup(InvitedMemberResourceTest.class);

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_DSR_ROOM", TestPropsValues.getCompanyId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		_objectEntry = _objectEntryLocalService.addObjectEntry(
			0, TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId(), 0, null,
			HashMapBuilder.<String, Serializable>put(
				"name", RandomTestUtil.randomString()
			).build(),
			serviceContext);

		User user = UserTestUtil.getAdminUser(objectDefinition.getCompanyId());

		_userAccountResource = UserAccountResource.builder(
		).authentication(
			user.getEmailAddress(), PropsValues.DEFAULT_ADMIN_PASSWORD
		).build();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"emailAddress"};
	}

	@Override
	protected InvitedMember randomInvitedMember() throws Exception {
		return new InvitedMember() {
			{
				emailAddress = RandomTestUtil.randomString() + "@liferay.com";
			}
		};
	}

	@Override
	protected InvitedMember testDeleteRoomInvitedMember_addInvitedMember()
		throws Exception {

		return testGetRoomInvitedMembersPage_addInvitedMember(
			_objectEntry.getObjectEntryId(), randomInvitedMember());
	}

	@Override
	protected Long testDeleteRoomInvitedMember_getRoomId() throws Exception {
		return _objectEntry.getObjectEntryId();
	}

	@Override
	protected InvitedMember testGetRoomInvitedMembersPage_addInvitedMember(
			Long roomId, InvitedMember invitedMember)
		throws Exception {

		UserAccount userAccount = _userAccountResource.postRoomUserAccount(
			_objectEntry.getObjectEntryId(),
			new UserAccount() {
				{
					setEmailAddress(invitedMember::getEmailAddress);
				}
			});

		invitedMember.setId(userAccount.getId());

		return invitedMember;
	}

	@Override
	protected Long testGetRoomInvitedMembersPage_getRoomId() throws Exception {
		return _objectEntry.getObjectEntryId();
	}

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private ObjectEntry _objectEntry;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	private UserAccountResource _userAccountResource;

}