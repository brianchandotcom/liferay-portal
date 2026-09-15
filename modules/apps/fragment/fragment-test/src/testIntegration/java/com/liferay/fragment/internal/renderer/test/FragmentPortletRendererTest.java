/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.renderer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Magdalena Jedraszak
 */
@RunWith(Arquillian.class)
public class FragmentPortletRendererTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group.getCompanyId());

		_layout = LayoutTestUtil.addTypeContentLayout(_group);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testRenderPortletFragmentEntryLinkFromMasterLayout()
		throws Exception {

		LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(), 0, null,
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT, 0,
				WorkflowConstants.STATUS_APPROVED, _serviceContext);

		Layout layout = _layoutLocalService.updateLayout(
			_group.getGroupId(), _layout.isPrivateLayout(),
			_layout.getLayoutId(), _layout.getParentLayoutId(),
			_layout.getNameMap(), _layout.getTitleMap(),
			_layout.getDescriptionMap(), _layout.getKeywordsMap(),
			_layout.getRobotsMap(), _layout.getType(), _layout.isHidden(),
			_layout.getFriendlyURLMap(), false, null, null, null, null, null,
			masterLayoutPageTemplateEntry.getExternalReferenceCode(),
			_serviceContext);

		_render(
			_addPortletFragmentEntryLink(
				_layoutLocalService.getLayout(
					masterLayoutPageTemplateEntry.getPlid())),
			layout);

		Assert.assertNotNull(_fetchSharedPortletPreferences());
	}

	@Test
	public void testRenderPortletFragmentEntryLinkFromOtherLayout()
		throws Exception {

		_render(
			_addPortletFragmentEntryLink(_layout),
			LayoutTestUtil.addTypeContentLayout(_group));

		Assert.assertNull(_fetchSharedPortletPreferences());
	}

	private FragmentEntryLink _addPortletFragmentEntryLink(Layout layout)
		throws Exception {

		return _fragmentEntryLinkLocalService.addFragmentEntryLink(
			null, TestPropsValues.getUserId(), _group.getGroupId(), null, null,
			null,
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid()),
			layout.getPlid(), StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, StringPool.BLANK,
			JSONUtil.put(
				"instanceId", StringPool.BLANK
			).put(
				"portletId", BlogsPortletKeys.BLOGS
			).toString(),
			StringUtil.randomId(), 0, null, FragmentConstants.TYPE_PORTLET,
			_serviceContext);
	}

	private PortletPreferences _fetchSharedPortletPreferences() {
		return _portletPreferencesLocalService.fetchPortletPreferences(
			_group.getGroupId(), PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
			PortletKeys.PREFS_PLID_SHARED, BlogsPortletKeys.BLOGS);
	}

	private void _render(FragmentEntryLink fragmentEntryLink, Layout layout)
		throws Exception {

		_fragmentRendererController.render(
			new DefaultFragmentRendererContext(fragmentEntryLink),
			ContentLayoutTestUtil.getMockHttpServletRequest(
				_company, _group, layout),
			new MockHttpServletResponse());
	}

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentRendererController _fragmentRendererController;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	private ServiceContext _serviceContext;

}