/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.renderer.categorization.inputs.impl.test;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.layout.provider.LayoutStructureProvider;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Portal;
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
			ContentLayoutTestUtil.getMockHttpServletRequest(
				companyLocalService.getCompany(TestPropsValues.getCompanyId()),
				group, layout);

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
			ContentLayoutTestUtil.getMockHttpServletRequest(
				companyLocalService.getCompany(TestPropsValues.getCompanyId()),
				group, layout);

		FragmentRenderer fragmentRenderer = getFragmentRenderer();

		fragmentRenderer.render(
			new DefaultFragmentRendererContext(fragmentEntryLink),
			mockHttpServletRequest, new MockHttpServletResponse());

		assertRender(0, mockHttpServletRequest);
	}

	protected FragmentEntryLink addFragmentEntryLinkToForm() throws Exception {
		Layout draftLayout = layout.fetchDraftLayout();
		long segmentsExperienceId =
			segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid());

		JSONObject jsonObject = ContentLayoutTestUtil.addFormToLayout(
			false,
			String.valueOf(
				portal.getClassNameId(objectDefinition.getClassName())),
			"0", draftLayout, layoutStructureProvider, segmentsExperienceId);

		FragmentRenderer fragmentRenderer = getFragmentRenderer();

		return ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
			StringPool.BLANK, StringPool.BLANK,
			fragmentRenderer.getConfiguration(
				new DefaultFragmentRendererContext(null)),
			0, StringPool.BLANK, StringPool.BLANK, draftLayout, getRenderKey(),
			fragmentRenderer.getType(), jsonObject.getString("addedItemId"), 0,
			segmentsExperienceId);
	}

	@Inject
	protected CompanyLocalService companyLocalService;

	protected FragmentEntryLink fragmentEntryLink;

	@DeleteAfterTestRun
	protected Group group;

	@Inject
	protected InfoItemServiceRegistry infoItemServiceRegistry;

	protected Layout layout;

	@Inject
	protected LayoutStructureProvider layoutStructureProvider;

	@DeleteAfterTestRun
	protected ObjectDefinition objectDefinition;

	@Inject
	protected Portal portal;

	@Inject
	protected SegmentsExperienceLocalService segmentsExperienceLocalService;

}