/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.renderer.categorization.inputs.impl.test;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.structure.FormStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Eudaldo Alonso
 */
public abstract class BaseInputFragmentRendererTestCase {

	public abstract ObjectEntry addObjectEntry() throws Exception;

	public abstract void assertRender(
		int expectedResult, HttpServletRequest httpServletRequest);

	public abstract FragmentRenderer getFragmentRenderer();

	public abstract String getRenderKey();

	@Before
	public void setUp() throws Exception {
		group = GroupTestUtil.addGroup();

		layout = LayoutTestUtil.addTypeContentLayout(group);

		objectDefinition = ObjectDefinitionTestUtil.publishObjectDefinition(
			Collections.singletonList(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, "First Name",
					"firstName")),
			ObjectDefinitionConstants.SCOPE_SITE);

		fragmentEntryLink = addFragmentEntryLinkToForm();

		ContentLayoutTestUtil.publishLayout(layout.fetchDraftLayout(), layout);
	}

	@Test
	public void testRenderWithInfoItemDetails() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			getMockHttpServletRequest();

		InfoItemDetailsProvider<ObjectEntry> infoItemDetailsProvider =
			infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemDetailsProvider.class, objectDefinition.getClassName());

		mockHttpServletRequest.setAttribute(
			InfoDisplayWebKeys.INFO_ITEM_DETAILS,
			infoItemDetailsProvider.getInfoItemDetails(addObjectEntry()));

		FragmentRenderer fragmentRenderer = getFragmentRenderer();

		fragmentRenderer.render(
			new DefaultFragmentRendererContext(fragmentEntryLink),
			mockHttpServletRequest, new MockHttpServletResponse());

		assertRender(1, mockHttpServletRequest);
	}

	@Test
	public void testRenderWithoutInfoItemDetails() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			getMockHttpServletRequest();

		FragmentRenderer fragmentRenderer = getFragmentRenderer();

		fragmentRenderer.render(
			new DefaultFragmentRendererContext(fragmentEntryLink),
			mockHttpServletRequest, new MockHttpServletResponse());

		assertRender(0, mockHttpServletRequest);
	}

	protected FragmentEntryLink addFragmentEntryLinkToForm() throws Exception {
		Layout draftLayout = layout.fetchDraftLayout();

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					draftLayout.getGroupId(), draftLayout.getPlid());

		long defaultSegmentsExperienceId =
			segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid());

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(defaultSegmentsExperienceId));

		FormStyledLayoutStructureItem formStyledLayoutStructureItem =
			(FormStyledLayoutStructureItem)
				layoutStructure.addFormStyledLayoutStructureItem(
					layoutStructure.getMainItemId(), 0);

		formStyledLayoutStructureItem.setClassNameId(
			portal.getClassNameId(objectDefinition.getClassName()));

		FragmentRenderer fragmentRenderer = getFragmentRenderer();

		FragmentEntryLink fragmentEntryLink =
			fragmentEntryLinkLocalService.addFragmentEntryLink(
				null, TestPropsValues.getUserId(), draftLayout.getGroupId(), 0,
				0, defaultSegmentsExperienceId, draftLayout.getPlid(),
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
				fragmentRenderer.getConfiguration(
					new DefaultFragmentRendererContext(null)),
				StringPool.BLANK, StringPool.BLANK, 0, getRenderKey(),
				fragmentRenderer.getType(),
				ServiceContextTestUtil.getServiceContext(
					draftLayout.getGroupId(), TestPropsValues.getUserId()));

		layoutStructure.addFragmentStyledLayoutStructureItem(
			fragmentEntryLink.getFragmentEntryLinkId(),
			formStyledLayoutStructureItem.getItemId(), 0);

		layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructureData(
				draftLayout.getGroupId(), draftLayout.getPlid(),
				defaultSegmentsExperienceId, layoutStructure.toString());

		return fragmentEntryLink;
	}

	protected MockHttpServletRequest getMockHttpServletRequest()
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLanguageId(LanguageUtil.getLanguageId(LocaleUtil.US));
		themeDisplay.setLayout(layout);
		themeDisplay.setScopeGroupId(group.getGroupId());
		themeDisplay.setSiteGroupId(group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockHttpServletRequest;
	}

	@Inject
	protected CompanyLocalService companyLocalService;

	protected FragmentEntryLink fragmentEntryLink;

	@Inject
	protected FragmentEntryLinkLocalService fragmentEntryLinkLocalService;

	@DeleteAfterTestRun
	protected Group group;

	@Inject
	protected InfoItemServiceRegistry infoItemServiceRegistry;

	protected Layout layout;

	@Inject
	protected LayoutPageTemplateStructureLocalService
		layoutPageTemplateStructureLocalService;

	@DeleteAfterTestRun
	protected ObjectDefinition objectDefinition;

	@Inject
	protected Portal portal;

	@Inject
	protected SegmentsExperienceLocalService segmentsExperienceLocalService;

}