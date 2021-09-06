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
		_companyId = TestPropsValues.getCompanyId();

		_user = TestPropsValues.getUser();

		UserTestUtil.setUser(TestPropsValues.getUser());
	}

	@Test
	public void testWorkflowPropagatedFromSiteTemplateOnPrivateSite()
		throws Exception {

		_assertWorkflowPropagatedFromSiteTemplate(true);
	}

	@Test
	public void testWorkflowPropagatedFromSiteTemplateOnPublicSite()
		throws Exception {

		_assertWorkflowPropagatedFromSiteTemplate(false);
	}

	private void _addWorkflowDefinitionLinkToSiteTemplate(long groupId)
		throws Exception {

		_workflowDefinitionLinkLocalService.addWorkflowDefinitionLink(
			_user.getUserId(), _companyId, groupId, null, 0, -1,
			RandomTestUtil.randomString(), 0);
	}

	private void _assertWorkflowPropagatedFromSiteTemplate(
			boolean layoutSetVisibilityPrivate)
		throws Exception {

		_layoutSetPrototype = LayoutTestUtil.addLayoutSetPrototype(
			RandomTestUtil.randomString());

		Group layoutSetPrototypeGroup =
			_groupLocalService.getLayoutSetPrototypeGroup(
				_companyId, _layoutSetPrototype.getLayoutSetPrototypeId());

		_addWorkflowDefinitionLinkToSiteTemplate(
			layoutSetPrototypeGroup.getGroupId());

		Group group = GroupTestUtil.addGroup();

		_updateGroupFromSiteTemplate(group, layoutSetVisibilityPrivate);

		Assert.assertEquals(
			1, _getWorkflowDefinitionLinkCount(group.getGroupId()));

		GroupTestUtil.deleteGroup(group);

		Assert.assertEquals(
			0, _getWorkflowDefinitionLinkCount(group.getGroupId()));
	}

	private int _getWorkflowDefinitionLinkCount(long groupId) {
		return _workflowDefinitionLinkLocalService.
			getWorkflowDefinitionLinksCount(_companyId, groupId, null);
	}

	private void _updateGroupFromSiteTemplate(
		Group group, boolean layoutSetVisibilityPrivate) {

		MockLiferayPortletActionRequest actionRequest =
			new MockLiferayPortletActionRequest();

		actionRequest.addParameter(
			"layoutSetPrototypeId",
			String.valueOf(_layoutSetPrototype.getLayoutSetPrototypeId()));
		actionRequest.addParameter(
			"layoutSetVisibilityPrivate",
			String.valueOf(layoutSetVisibilityPrivate));

		ReflectionTestUtil.invoke(
			_addGroupMVCActionCommandTest, "_updateGroupFromSiteTemplate",
			new Class<?>[] {ActionRequest.class, Group.class}, actionRequest,
			group);
	}

	@Inject(filter = "mvc.command.name=/site_admin/add_group")
	private MVCActionCommand _addGroupMVCActionCommandTest;

	private long _companyId;

	@Inject
	private GroupLocalService _groupLocalService;

	@DeleteAfterTestRun
	private LayoutSetPrototype _layoutSetPrototype;

	private User _user;

	@Inject
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

}