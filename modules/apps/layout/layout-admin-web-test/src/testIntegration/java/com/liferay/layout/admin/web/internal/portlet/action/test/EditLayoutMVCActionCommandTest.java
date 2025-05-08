/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upload.test.util.UploadTestUtil;

import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletException;

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

	@Test(expected = PortletException.class)
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

		ReflectionTestUtil.setFieldValue(
			_mvcActionCommand, "_portal",
			ProxyUtil.newProxyInstance(
				Portal.class.getClassLoader(), new Class<?>[] {Portal.class},
				(proxy, method, args) -> {
					if (Objects.equals(
							method.getName(), "getUploadPortletRequest")) {

						LiferayPortletRequest liferayPortletRequest =
							_portal.getLiferayPortletRequest(
								mockLiferayPortletActionRequest);

						return UploadTestUtil.createUploadPortletRequest(
							_portal.getUploadServletRequest(
								liferayPortletRequest.getHttpServletRequest()),
							liferayPortletRequest,
							_portal.getPortletNamespace(
								liferayPortletRequest.getPortletName()));
					}

					return method.invoke(_portal, args);
				}));

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

	@Inject(filter = "mvc.command.name=/layout_admin/edit_layout")
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private Portal _portal;

}