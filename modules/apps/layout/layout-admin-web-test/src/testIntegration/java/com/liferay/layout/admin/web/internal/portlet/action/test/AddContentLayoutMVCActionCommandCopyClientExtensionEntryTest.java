/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.model.ClientExtensionEntryRel;
import com.liferay.client.extension.service.ClientExtensionEntryLocalService;
import com.liferay.client.extension.service.ClientExtensionEntryRelLocalService;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
@Sync
public class AddContentLayoutMVCActionCommandCopyClientExtensionEntryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(), 0,
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.BASIC, 0,
				WorkflowConstants.STATUS_APPROVED,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Layout layout = _layoutLocalService.fetchLayout(
			_layoutPageTemplateEntry.getPlid());

		_globalCSSClientExtensionEntryRel = _addClientExtensionEntryRel(
			layout, ClientExtensionEntryConstants.TYPE_GLOBAL_CSS);
		_globaJSClientExtensionEntryRel = _addClientExtensionEntryRel(
			layout, ClientExtensionEntryConstants.TYPE_GLOBAL_JS);
		_themeCSSClientExtensionEntryRel = _addClientExtensionEntryRel(
			layout, ClientExtensionEntryConstants.TYPE_THEME_CSS);

		_layoutLocalService.updateLayout(layout);
	}

	@Test
	@TestInfo({"LPS-153653", "LPS-153655", "LPS-153656"})
	public void testAddContentLayoutCopyClientExtensionEntries()
		throws Exception {

		_mvcActionCommand.processAction(
			_getMockLiferayPortletActionRequest(),
			new MockLiferayPortletActionResponse());

		Layout layout = _layoutLocalService.fetchFirstLayout(
			_group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		_assertClientExtensionEntryRel(
			_globalCSSClientExtensionEntryRel.getCETExternalReferenceCode(),
			layout, ClientExtensionEntryConstants.TYPE_GLOBAL_CSS);
		_assertClientExtensionEntryRel(
			_globaJSClientExtensionEntryRel.getCETExternalReferenceCode(),
			layout, ClientExtensionEntryConstants.TYPE_GLOBAL_JS);
		_assertClientExtensionEntryRel(
			_themeCSSClientExtensionEntryRel.getCETExternalReferenceCode(),
			layout, ClientExtensionEntryConstants.TYPE_THEME_CSS);
	}

	private ClientExtensionEntryRel _addClientExtensionEntryRel(
			Layout layout, String type)
		throws Exception {

		ClientExtensionEntry clientExtensionEntry =
			_clientExtensionEntryLocalService.addClientExtensionEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				StringPool.BLANK,
				Collections.singletonMap(
					LocaleUtil.getDefault(), RandomTestUtil.randomString()),
				StringPool.BLANK, StringPool.BLANK, type,
				UnicodePropertiesBuilder.create(
					true
				).put(
					"url", "http://www.example.com"
				).buildString());

		_clientExtensionEntries.add(clientExtensionEntry);

		return _clientExtensionEntryRelLocalService.addClientExtensionEntryRel(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_portal.getClassNameId(Layout.class), layout.getPlid(),
			clientExtensionEntry.getExternalReferenceCode(), type,
			StringPool.BLANK,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	private void _assertClientExtensionEntryRel(
		String expectedCETExternalReferenceCode, Layout layout, String type) {

		List<ClientExtensionEntryRel> globalCSSClientExtensionEntryRels =
			_clientExtensionEntryRelLocalService.getClientExtensionEntryRels(
				_portal.getClassNameId(Layout.class), layout.getPlid(), type);

		Assert.assertEquals(
			globalCSSClientExtensionEntryRels.toString(), 1,
			globalCSSClientExtensionEntryRels.size());

		ClientExtensionEntryRel clientExtensionEntryRel =
			globalCSSClientExtensionEntryRels.get(0);

		Assert.assertEquals(
			expectedCETExternalReferenceCode,
			clientExtensionEntryRel.getCETExternalReferenceCode());
	}

	private MockLiferayPortletActionRequest
			_getMockLiferayPortletActionRequest()
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());
		mockLiferayPortletActionRequest.setParameter(
			"groupId", String.valueOf(_group.getGroupId()));
		mockLiferayPortletActionRequest.setParameter(
			"layoutPageTemplateEntryId",
			String.valueOf(
				_layoutPageTemplateEntry.getLayoutPageTemplateEntryId()));
		mockLiferayPortletActionRequest.setParameter(
			"name", RandomTestUtil.randomString());
		mockLiferayPortletActionRequest.setParameter(
			"parentLayoutId",
			String.valueOf(LayoutConstants.DEFAULT_PARENT_LAYOUT_ID));
		mockLiferayPortletActionRequest.setParameter(
			"privateLayout", String.valueOf(Boolean.FALSE));

		return mockLiferayPortletActionRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(_group.getCompanyId()));
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	@DeleteAfterTestRun
	private List<ClientExtensionEntry> _clientExtensionEntries =
		new ArrayList<>();

	@Inject
	private ClientExtensionEntryLocalService _clientExtensionEntryLocalService;

	@Inject
	private ClientExtensionEntryRelLocalService
		_clientExtensionEntryRelLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	private ClientExtensionEntryRel _globaJSClientExtensionEntryRel;
	private ClientExtensionEntryRel _globalCSSClientExtensionEntryRel;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	private LayoutPageTemplateEntry _layoutPageTemplateEntry;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject(filter = "mvc.command.name=/layout_admin/add_content_layout")
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private Portal _portal;

	private ClientExtensionEntryRel _themeCSSClientExtensionEntryRel;

}