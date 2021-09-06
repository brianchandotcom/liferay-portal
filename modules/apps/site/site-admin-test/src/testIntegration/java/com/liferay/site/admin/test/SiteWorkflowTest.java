/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.site.admin.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import javax.portlet.ActionRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author To To Trinh
 */
@RunWith(Arquillian.class)
public class SiteWorkflowTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_user = TestPropsValues.getUser();

		UserTestUtil.setUser(TestPropsValues.getUser());

		_companyId = TestPropsValues.getCompanyId();
	}

	@Test
	public void testWorkflowPropagatedFromSiteTemplateOnPrivateSite()
		throws Exception {

		_layoutSetPrototype = LayoutTestUtil.addLayoutSetPrototype(
			RandomTestUtil.randomString());

		Group layoutSetPrototypeGroup =
			_groupLocalService.getLayoutSetPrototypeGroup(
				_companyId, _layoutSetPrototype.getLayoutSetPrototypeId());

		_addWorkflowDefinitionLinkToSiteTemplate(
			layoutSetPrototypeGroup.getGroupId());

		Group privateGroup = GroupTestUtil.addGroup();

		_updateGroupFromSiteTemplate(
			privateGroup, String.valueOf(Boolean.TRUE));

		Assert.assertEquals(
			1, _getWorkflowDefinitionLinkCount(privateGroup.getGroupId()));

		GroupTestUtil.deleteGroup(privateGroup);

		Assert.assertEquals(
			0, _getWorkflowDefinitionLinkCount(privateGroup.getGroupId()));
	}

	@Test
	public void testWorkflowPropagatedFromSiteTemplateOnPublicSite()
		throws Exception {

		_layoutSetPrototype = LayoutTestUtil.addLayoutSetPrototype(
			RandomTestUtil.randomString());

		Group layoutSetPrototypeGroup =
			_groupLocalService.getLayoutSetPrototypeGroup(
				_companyId, _layoutSetPrototype.getLayoutSetPrototypeId());

		_addWorkflowDefinitionLinkToSiteTemplate(
			layoutSetPrototypeGroup.getGroupId());

		Group publicGroup = GroupTestUtil.addGroup();

		_updateGroupFromSiteTemplate(
			publicGroup, String.valueOf(Boolean.FALSE));

		Assert.assertEquals(
			1, _getWorkflowDefinitionLinkCount(publicGroup.getGroupId()));

		GroupTestUtil.deleteGroup(publicGroup);

		Assert.assertEquals(
			0, _getWorkflowDefinitionLinkCount(publicGroup.getGroupId()));
	}

	private void _addWorkflowDefinitionLinkToSiteTemplate(long groupId)
		throws Exception {

		_workflowDefinitionLinkLocalService.addWorkflowDefinitionLink(
			_user.getUserId(), _companyId, groupId, null, 0, -1,
			RandomTestUtil.randomString(), 0);
	}

	private int _getWorkflowDefinitionLinkCount(long groupId) {
		return _workflowDefinitionLinkLocalService.
			getWorkflowDefinitionLinksCount(_companyId, groupId, null);
	}

	private void _updateGroupFromSiteTemplate(
		Group group, String layoutSetVisibilityPrivate) {

		MockLiferayPortletActionRequest actionRequest =
			new MockLiferayPortletActionRequest();

		actionRequest.addParameter(
			"layoutSetPrototypeId",
			String.valueOf(_layoutSetPrototype.getLayoutSetPrototypeId()));
		actionRequest.addParameter(
			"layoutSetVisibilityPrivate", layoutSetVisibilityPrivate);

		ReflectionTestUtil.invoke(
			_addGroupMVCActionCommandTest, "_updateGroupFromSiteTemplate",
			new Class<?>[] {ActionRequest.class, Group.class}, actionRequest,
			group);
	}

	private static long _companyId;
	private static User _user;

	@Inject(filter = "mvc.command.name=/site_admin/add_group")
	private MVCActionCommand _addGroupMVCActionCommandTest;

	@Inject
	private GroupLocalService _groupLocalService;

	@DeleteAfterTestRun
	private LayoutSetPrototype _layoutSetPrototype;

	@Inject
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

}