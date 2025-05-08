/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.exception.LayoutTypeException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upload.test.util.UploadTestUtil;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Javier Moral
 */
@RunWith(Arquillian.class)
public class EditLayoutMVCActionCommandTest {

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
	public void testEditLayout() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		Date modifiedDate = layout.getModifiedDate();

		Map<Locale, String> nameMap = RandomTestUtil.randomLocaleStringMap();

		layout.setNameMap(nameMap);

		layout.setType(LayoutConstants.TYPE_PORTLET);

		_processAction(layout);

		layout = _layoutLocalService.getLayout(layout.getPlid());

		Assert.assertTrue(
			layout.getModifiedDate(
			).after(
				modifiedDate
			));
		Assert.assertEquals(
			nameMap.get(LocaleUtil.US), layout.getName(LocaleUtil.US));
		Assert.assertEquals(LayoutConstants.TYPE_PORTLET, layout.getType());
	}

	@Test(expected = LayoutTypeException.class)
	@TestInfo("LPD-54782")
	public void testEditLayoutNonexistentType() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		layout.setType(RandomTestUtil.randomString());

		_processAction(layout);
	}

	private MockLiferayPortletActionRequest _getMockLiferayPortletActionRequest(
			Layout layout)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			ContentLayoutTestUtil.getMockLiferayPortletActionRequest(
				_companyLocalService.getCompany(TestPropsValues.getCompanyId()),
				_group, layout);

		mockLiferayPortletActionRequest.addParameter(
			"groupId", String.valueOf(layout.getGroupId()));
		mockLiferayPortletActionRequest.addParameter(
			"hidden", String.valueOf(RandomTestUtil.randomBoolean()));
		mockLiferayPortletActionRequest.addParameter(
			"nameMapAsXML", layout.getName(LocaleUtil.US));
		mockLiferayPortletActionRequest.addParameter(
			"nameMapAsXML_en_US", layout.getName(LocaleUtil.US));
		mockLiferayPortletActionRequest.addParameter(
			"friendlyURL", layout.getFriendlyURL());
		mockLiferayPortletActionRequest.addParameter(
			"selPlid", String.valueOf(layout.getPlid()));
		mockLiferayPortletActionRequest.addParameter("type", layout.getType());

		return mockLiferayPortletActionRequest;
	}

	private void _processAction(Layout layout) throws Exception {
		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_getMockLiferayPortletActionRequest(layout);

		ReflectionTestUtil.invoke(
			_mvcActionCommand, "_editLayout",
			new Class<?>[] {
				ActionRequest.class, HttpServletResponse.class,
				UploadPortletRequest.class
			},
			mockLiferayPortletActionRequest, new MockHttpServletResponse(),
			UploadTestUtil.createUploadPortletRequest(
				_portal.getUploadServletRequest(
					mockLiferayPortletActionRequest.getHttpServletRequest()),
				mockLiferayPortletActionRequest,
				_portal.getPortletNamespace(
					mockLiferayPortletActionRequest.getPortletName())));
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject(filter = "mvc.command.name=/layout_admin/edit_layout")
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private Portal _portal;

}