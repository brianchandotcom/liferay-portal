/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upload.test.util.UploadTestUtil;

import java.util.Map;
import java.util.Objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mikel Lorza
 */
@RunWith(Arquillian.class)
@Sync
public class EditLayoutMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() {
		if (_originalPortal != null) {
			ReflectionTestUtil.setFieldValue(
				_mvcActionCommand, "_portal", _originalPortal);
		}
	}

	@Test
	public void testUpdateLinkToLayout() throws Exception {
		Layout linkToLayout = LayoutTestUtil.addTypeContentLayout(_group);

		Layout layout = LayoutTestUtil.addTypeLinkToLayoutLayout(
			_group.getGroupId(), linkToLayout.getLayoutId());

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_getMockLiferayPortletActionRequest(
				HashMapBuilder.put(
					Constants.CMD, Constants.UPDATE
				).put(
					"groupId", String.valueOf(layout.getGroupId())
				).put(
					"linkToLayoutUuid", linkToLayout.getUuid()
				).put(
					"nameMapAsXML_en_US", layout.getName(LocaleUtil.US)
				).put(
					"selPlid", String.valueOf(layout.getPlid())
				).put(
					"type", layout.getType()
				).build());

		_setUpUploadPortletRequest(mockLiferayPortletActionRequest);

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());

		Layout actualLayout = _layoutLocalService.getLayout(layout.getPlid());

		Assert.assertEquals(
			String.valueOf(linkToLayout.getExternalReferenceCode()),
			actualLayout.getTypeSettingsProperty(
				"linkToLayoutExternalReferenceCode"));
		Assert.assertEquals(
			String.valueOf(linkToLayout.getLayoutId()),
			actualLayout.getTypeSettingsProperty("linkToLayoutId"));

		UnicodeProperties typeSettingsUnicodeProperties =
			layout.getTypeSettingsProperties();

		typeSettingsUnicodeProperties.put(
			"linkToLayoutExternalReferenceCode", null);
		typeSettingsUnicodeProperties.put("linkToLayoutId", null);

		_layoutLocalService.updateTypeSettings(
			_group.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			typeSettingsUnicodeProperties.toString());

		actualLayout = _layoutLocalService.getLayout(layout.getPlid());

		Assert.assertNull(
			actualLayout.getTypeSettingsProperty(
				"linkToLayoutExternalReferenceCode"));
		Assert.assertNull(
			actualLayout.getTypeSettingsProperty("linkToLayoutId"));

		mockLiferayPortletActionRequest = _getMockLiferayPortletActionRequest(
			HashMapBuilder.put(
				Constants.CMD, Constants.UPDATE
			).put(
				"groupId", String.valueOf(layout.getGroupId())
			).put(
				"linkToLayoutExternalReferenceCode",
				linkToLayout.getExternalReferenceCode()
			).put(
				"nameMapAsXML_en_US", layout.getName(LocaleUtil.US)
			).put(
				"selPlid", String.valueOf(layout.getPlid())
			).put(
				"type", layout.getType()
			).build());

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());

		actualLayout = _layoutLocalService.getLayout(layout.getPlid());

		Assert.assertEquals(
			String.valueOf(linkToLayout.getExternalReferenceCode()),
			actualLayout.getTypeSettingsProperty(
				"linkToLayoutExternalReferenceCode"));
		Assert.assertNull(
			actualLayout.getTypeSettingsProperty("linkToLayoutId"));
	}

	private MockLiferayPortletActionRequest _getMockLiferayPortletActionRequest(
			Map<String, String> parameters)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		for (Map.Entry<String, String> entry : parameters.entrySet()) {
			mockLiferayPortletActionRequest.addParameter(
				entry.getKey(), new String[] {entry.getValue()});
		}

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.fetchCompany(TestPropsValues.getCompanyId()));

		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockLiferayPortletActionRequest;
	}

	private void _setUpUploadPortletRequest(
		MockLiferayPortletActionRequest mockLiferayPortletActionRequest) {

		_originalPortal = (Portal)ReflectionTestUtil.getAndSetFieldValue(
			_mvcActionCommand, "_portal",
			ProxyUtil.newProxyInstance(
				EditLayoutMVCActionCommandTest.class.getClassLoader(),
				new Class<?>[] {Portal.class},
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
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject(filter = "mvc.command.name=/layout_admin/edit_layout")
	private MVCActionCommand _mvcActionCommand;

	private Portal _originalPortal;

	@Inject
	private Portal _portal;

}