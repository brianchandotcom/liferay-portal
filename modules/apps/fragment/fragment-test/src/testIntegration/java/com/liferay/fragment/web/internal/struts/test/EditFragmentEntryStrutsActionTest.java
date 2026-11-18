/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.struts.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.test.util.FragmentEntryTestUtil;
import com.liferay.fragment.test.util.FragmentTestUtil;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import jakarta.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Sandro Chinea
 */
@RunWith(Arquillian.class)
public class EditFragmentEntryStrutsActionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	@TestInfo("LPD-111343")
	public void testExecute() throws Exception {
		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection.getFragmentCollectionId());

		MockHttpServletResponse mockHttpServletResponse = _execute(
			fragmentEntry.getExternalReferenceCode(),
			_group.getExternalReferenceCode(), TestPropsValues.getUser());

		Assert.assertEquals(
			StringBundler.concat(
				"/group", _group.getFriendlyURL(),
				"/~/control_panel/manage/-/fragments/fragment_entry",
				"/by_external_reference_code/",
				fragmentEntry.getExternalReferenceCode(), "/edit"),
			HttpComponentsUtil.getPath(
				mockHttpServletResponse.getRedirectedUrl()));
	}

	@Test
	@TestInfo("LPD-111343")
	public void testExecuteWithNonexistentSiteExternalReferenceCode()
		throws Exception {

		MockHttpServletResponse mockHttpServletResponse = _execute(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			TestPropsValues.getUser());

		Assert.assertEquals(
			HttpServletResponse.SC_NOT_FOUND,
			mockHttpServletResponse.getStatus());
	}

	@Test
	@TestInfo("LPD-111343")
	public void testExecuteWithoutPermission() throws Exception {
		_user = UserTestUtil.addUser();

		MockHttpServletResponse mockHttpServletResponse = _execute(
			RandomTestUtil.randomString(), _group.getExternalReferenceCode(),
			_user);

		Assert.assertEquals(
			HttpServletResponse.SC_FORBIDDEN,
			mockHttpServletResponse.getStatus());
		Assert.assertNull(mockHttpServletResponse.getRedirectedUrl());
	}

	private MockHttpServletResponse _execute(
			String externalReferenceCode, String siteExternalReferenceCode,
			User user)
		throws Exception {

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		MockHttpServletRequest mockHttpServletRequest =
			ContentLayoutTestUtil.getMockHttpServletRequest(
				_companyLocalService.getCompany(_group.getCompanyId()), _group,
				layout);

		mockHttpServletRequest.setParameter(
			"externalReferenceCode", externalReferenceCode);
		mockHttpServletRequest.setParameter(
			"siteExternalReferenceCode", siteExternalReferenceCode);
		mockHttpServletRequest.setRequestURI(layout.getFriendlyURL());

		ThemeDisplay themeDisplay =
			(ThemeDisplay)mockHttpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_editFragmentEntryStrutsAction.execute(
			mockHttpServletRequest, mockHttpServletResponse);

		return mockHttpServletResponse;
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject(filter = "path=/portal/fragment/edit_fragment_entry")
	private StrutsAction _editFragmentEntryStrutsAction;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _user;

}