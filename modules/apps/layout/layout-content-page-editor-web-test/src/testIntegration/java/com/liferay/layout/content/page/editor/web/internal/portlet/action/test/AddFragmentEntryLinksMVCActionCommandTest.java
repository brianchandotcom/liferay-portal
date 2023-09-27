/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentCompositionLocalService;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.serializer.LayoutStructureItemJSONSerializer;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.List;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class AddFragmentEntryLinksMVCActionCommandTest {

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
			_group.getGroupId(), TestPropsValues.getUserId());

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(WebKeys.LAYOUT, _layout);

		ThemeDisplay themeDisplay = ContentLayoutTestUtil.getThemeDisplay(
			_company, _group, _layout);

		themeDisplay.setRequest(mockHttpServletRequest);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		_serviceContext.setRequest(mockHttpServletRequest);

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testAddDropZoneFragmentEntryLinks() throws Exception {
		int numberOfFragmentEntryLinks = RandomTestUtil.randomInt(1, 3);

		_testAddFragmentEntryLinks(
			"<div><lfr-drop-zone></lfr-drop-zone></div>",
			numberOfFragmentEntryLinks);
	}

	@Test
	public void testAddFragmentEntryLinks() throws Exception {
		int numberOfFragmentEntryLinks = RandomTestUtil.randomInt(1, 3);

		_testAddFragmentEntryLinks(
			RandomTestUtil.randomString(), numberOfFragmentEntryLinks);
	}

	private FragmentComposition _addFragmentComposition(
			String html, int numberOfFragmentEntryLinks)
		throws Exception {

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				TestPropsValues.getUserId(), _group.getGroupId(),
				StringUtil.randomString(), StringPool.BLANK, _serviceContext);

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				StringPool.BLANK, html, StringPool.BLANK, false,
				StringPool.BLANK, null, 0, FragmentConstants.TYPE_COMPONENT,
				null, WorkflowConstants.STATUS_APPROVED, _serviceContext);

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		long defaultSegmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid());

		LayoutStructure layoutStructure = new LayoutStructure();

		LayoutStructureItem rootLayoutStructureItem =
			layoutStructure.addRootLayoutStructureItem();

		LayoutStructureItem containerStyledLayoutStructureItem =
			layoutStructure.addContainerStyledLayoutStructureItem(
				rootLayoutStructureItem.getItemId(), 0);

		for (int i = 0; i < numberOfFragmentEntryLinks; i++) {
			FragmentEntryLink fragmentEntryLink =
				_fragmentEntryLinkLocalService.addFragmentEntryLink(
					TestPropsValues.getUserId(), _group.getGroupId(), 0,
					fragmentEntry.getFragmentEntryId(),
					defaultSegmentsExperienceId, layout.getPlid(),
					fragmentEntry.getCss(), fragmentEntry.getHtml(),
					fragmentEntry.getJs(), fragmentEntry.getConfiguration(),
					"{}", StringPool.BLANK, 0, null, fragmentEntry.getType(),
					_serviceContext);

			layoutStructure.addFragmentStyledLayoutStructureItem(
				fragmentEntryLink.getFragmentEntryLinkId(),
				containerStyledLayoutStructureItem.getItemId(), -1);
		}

		_layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructureData(
				_group.getGroupId(), layout.getPlid(),
				defaultSegmentsExperienceId, layoutStructure.toString());

		String layoutStructureItemJSON =
			_layoutStructureItemJSONSerializer.toJSONString(
				layout, containerStyledLayoutStructureItem.getItemId(), false,
				false, defaultSegmentsExperienceId);

		return _fragmentCompositionLocalService.addFragmentComposition(
			TestPropsValues.getUserId(), _group.getGroupId(),
			fragmentCollection.getFragmentCollectionId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), layoutStructureItemJSON, 0,
			WorkflowConstants.STATUS_APPROVED, _serviceContext);
	}

	private void _assertFragmentEntryLinksContent(
		JSONObject fragmentEntryLinksJSONObject,
		MockLiferayPortletActionRequest mockLiferayPortletActionRequest,
		MockLiferayPortletActionResponse mockLiferayPortletActionResponse) {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			mockLiferayPortletActionRequest);
		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(mockLiferayPortletActionResponse);
		Locale locale = LocaleUtil.getMostRelevantLocale();

		for (String fragmentEntryLinkId :
				fragmentEntryLinksJSONObject.keySet()) {

			FragmentEntryLink fragmentEntryLink =
				_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
					Long.valueOf(fragmentEntryLinkId));

			Assert.assertNotNull(fragmentEntryLink);

			DefaultFragmentRendererContext defaultFragmentRendererContext =
				new DefaultFragmentRendererContext(fragmentEntryLink);

			defaultFragmentRendererContext.setLocale(locale);
			defaultFragmentRendererContext.setMode(
				FragmentEntryLinkConstants.EDIT);

			JSONObject fragmentEntryLinkJSONObject =
				fragmentEntryLinksJSONObject.getJSONObject(fragmentEntryLinkId);

			Assert.assertEquals(
				_removeUniqueId(
					_fragmentRendererController.render(
						defaultFragmentRendererContext, httpServletRequest,
						httpServletResponse)),
				_removeUniqueId(
					fragmentEntryLinkJSONObject.getString("content")));
		}
	}

	private void _assertLayoutData(JSONObject jsonObject) {
		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					_group.getGroupId(), _layout.getPlid());

		LayoutStructure updatedLayoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getDefaultSegmentsExperienceData());

		JSONObject updatedLayoutDataJSONObject =
			updatedLayoutStructure.toJSONObject();

		JSONObject layoutDataJSONObject = jsonObject.getJSONObject(
			"layoutData");

		Assert.assertEquals(
			updatedLayoutDataJSONObject.toString(),
			layoutDataJSONObject.toString());
	}

	private MockLiferayPortletActionRequest _getMockLiferayPortletActionRequest(
			String fragmentEntryKey)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			ContentLayoutTestUtil.getMockLiferayPortletActionRequest(
				_company, _group, _layout);

		mockLiferayPortletActionRequest.addParameter(
			"fragmentEntryKey", fragmentEntryKey);

		mockLiferayPortletActionRequest.addParameter(
			"groupId", String.valueOf(_group.getGroupId()));

		long defaultSegmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				_layout.getPlid());

		mockLiferayPortletActionRequest.addParameter(
			"segmentsExperienceId",
			String.valueOf(defaultSegmentsExperienceId));

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					_layout.getGroupId(), _layout.getPlid());

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(defaultSegmentsExperienceId));

		mockLiferayPortletActionRequest.addParameter(
			"parentItemId", layoutStructure.getMainItemId());

		ThemeDisplay themeDisplay =
			(ThemeDisplay)mockLiferayPortletActionRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			mockLiferayPortletActionRequest);

		themeDisplay.setRequest(httpServletRequest);

		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		return mockLiferayPortletActionRequest;
	}

	private String _removeUniqueId(String content) {
		Document document = Jsoup.parseBodyFragment(content);

		Element bodyElement = document.body();

		Element element = bodyElement.firstElementSibling();

		element.attr("id", StringPool.BLANK);

		return element.outerHtml();
	}

	private void _testAddFragmentEntryLinks(
			String html, int numberOfFragmentEntryLinks)
		throws Exception {

		FragmentComposition fragmentComposition = _addFragmentComposition(
			html, numberOfFragmentEntryLinks);

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_getMockLiferayPortletActionRequest(
				fragmentComposition.getFragmentCompositionKey());

		MockLiferayPortletActionResponse mockLiferayPortletActionResponse =
			new MockLiferayPortletActionResponse();

		List<FragmentEntryLink> originalFragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinksByPlid(
				_group.getGroupId(), _layout.getPlid());

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcActionCommand, "_processAddFragmentEntryLinks",
			new Class<?>[] {ActionRequest.class, ActionResponse.class},
			mockLiferayPortletActionRequest, mockLiferayPortletActionResponse);

		List<FragmentEntryLink> actualFragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinksByPlid(
				_group.getGroupId(), _layout.getPlid());

		Assert.assertEquals(
			actualFragmentEntryLinks.toString(),
			originalFragmentEntryLinks.size() + numberOfFragmentEntryLinks,
			actualFragmentEntryLinks.size());

		JSONObject fragmentEntryLinksJSONObject = jsonObject.getJSONObject(
			"fragmentEntryLinks");

		Assert.assertEquals(
			fragmentEntryLinksJSONObject.toString(), numberOfFragmentEntryLinks,
			fragmentEntryLinksJSONObject.length());

		_assertFragmentEntryLinksContent(
			fragmentEntryLinksJSONObject, mockLiferayPortletActionRequest,
			mockLiferayPortletActionResponse);

		_assertLayoutData(jsonObject);
	}

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Inject
	private FragmentCompositionLocalService _fragmentCompositionLocalService;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Inject
	private FragmentRendererController _fragmentRendererController;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject
	private LayoutStructureItemJSONSerializer
		_layoutStructureItemJSONSerializer;

	@Inject(
		filter = "mvc.command.name=/layout_content_page_editor/add_fragment_entry_links"
	)
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private Portal _portal;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	private ServiceContext _serviceContext;

}