/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.exception.LayoutTypeException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.service.impl.LayoutLocalServiceHelper;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import javax.portlet.PortletException;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.Is;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Moral
 */
@RunWith(Arquillian.class)
public class AddSimpleLayoutMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	@TestInfo("LPD-54782")
	public void testAddSimpleLayout() throws Exception {
		Layout layout = _layoutLocalService.createLayout(
			_layoutLocalServiceHelper.getUniquePlid());

		String name = RandomTestUtil.randomString();

		String friendlyURL =
			CharPool.SLASH +
				FriendlyURLNormalizerUtil.normalizeWithEncoding(name);

		boolean privateLayout = false;

		layout.setGroupId(_group.getGroupId());
		layout.setPrivateLayout(privateLayout);
		layout.setName(name);
		layout.setMasterLayoutPlid(0L);

		layout.setType(LayoutConstants.TYPE_PORTLET);

		_processAction(layout);

		layout = _layoutLocalService.getLayoutByFriendlyURL(
			_group.getGroupId(), privateLayout, friendlyURL);

		Assert.assertEquals(friendlyURL, layout.getFriendlyURL());
		Assert.assertEquals(name, layout.getName(LocaleUtil.US));
		Assert.assertEquals(privateLayout, layout.isPrivateLayout());
		Assert.assertEquals(LayoutConstants.TYPE_PORTLET, layout.getType());
	}

	@Test
	@TestInfo("LPD-54782")
	public void testAddSimpleLayoutNonexistentType() throws Exception {
		Layout layout = _layoutLocalService.createLayout(
			_layoutLocalServiceHelper.getUniquePlid());

		layout.setGroupId(TestPropsValues.getGroupId());
		layout.setPrivateLayout(false);
		layout.setParentLayoutId(LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);
		layout.setNameMap(RandomTestUtil.randomLocaleStringMap());
		layout.setType(RandomTestUtil.randomString());
		layout.setFriendlyURL(RandomTestUtil.randomString());
		layout.setMasterLayoutPlid(0L);

		try {
			_processAction(layout);
		}
		catch (PortletException portletException) {
			MatcherAssert.assertThat(
				portletException.getCause(),
				Is.is(CoreMatchers.instanceOf(LayoutTypeException.class)));
		}
	}

	private void _processAction(Layout layout) throws Exception {
		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			ContentLayoutTestUtil.getMockLiferayPortletActionRequest(
				_companyLocalService.getCompany(TestPropsValues.getCompanyId()),
				_group, layout);

		mockLiferayPortletActionRequest.addParameter(
			"groupId", String.valueOf(layout.getGroupId()));
		mockLiferayPortletActionRequest.addParameter(
			"liveGroupId", String.valueOf(layout.getGroupId()));
		mockLiferayPortletActionRequest.addParameter(
			"masterLayoutPlid", String.valueOf(layout.getMasterLayoutPlid()));
		mockLiferayPortletActionRequest.addParameter("name", layout.getName());
		mockLiferayPortletActionRequest.addParameter(
			"parentLayoutId", String.valueOf(layout.getParentLayoutId()));
		mockLiferayPortletActionRequest.addParameter(
			"privateLayout", String.valueOf(layout.isPrivateLayout()));
		mockLiferayPortletActionRequest.addParameter(
			"stagingGroupId", String.valueOf(0L));
		mockLiferayPortletActionRequest.addParameter("type", layout.getType());

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutLocalServiceHelper _layoutLocalServiceHelper;

	@Inject(filter = "mvc.command.name=/layout_admin/add_simple_layout")
	private MVCActionCommand _mvcActionCommand;

}