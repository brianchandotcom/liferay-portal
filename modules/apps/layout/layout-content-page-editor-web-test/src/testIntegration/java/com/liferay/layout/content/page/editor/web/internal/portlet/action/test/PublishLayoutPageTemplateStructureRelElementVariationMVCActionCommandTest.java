/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRelElementVariation;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelElementVariationLocalService;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Víctor Galán
 */
@RunWith(Arquillian.class)
public class PublishLayoutPageTemplateStructureRelElementVariationMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testPublishLayoutPageTemplateStructureRelElementVariation()
		throws Exception {

		Group group = _groupLocalService.getGroup(
			TestPropsValues.getGroupId());

		Layout layout = LayoutTestUtil.addTypeContentLayout(group);

		Layout draftLayout = layout.fetchDraftLayout();

		String hide = RandomTestUtil.randomString();
		String html = RandomTestUtil.randomString();
		String js = RandomTestUtil.randomString();
		String name = RandomTestUtil.randomString();
		String targetElement = RandomTestUtil.randomString();

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			ContentLayoutTestUtil.getMockLiferayPortletActionRequest(
				_companyLocalService.getCompany(
					TestPropsValues.getCompanyId()),
				group, draftLayout);

		mockLiferayPortletActionRequest.setParameter(
			LocalizationUtil.getLocalizedName(
				"hide", LocaleUtil.toLanguageId(LocaleUtil.US)),
			hide);
		mockLiferayPortletActionRequest.setParameter(
			LocalizationUtil.getLocalizedName(
				"html", LocaleUtil.toLanguageId(LocaleUtil.US)),
			html);
		mockLiferayPortletActionRequest.setParameter(
			LocalizationUtil.getLocalizedName(
				"js", LocaleUtil.toLanguageId(LocaleUtil.US)),
			js);
		mockLiferayPortletActionRequest.setParameter("name", name);
		mockLiferayPortletActionRequest.setParameter(
			"targetElement", targetElement);

		MockLiferayPortletActionResponse mockLiferayPortletActionResponse =
			new MockLiferayPortletActionResponse();

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest, mockLiferayPortletActionResponse);

		MockHttpServletResponse mockHttpServletResponse =
			(MockHttpServletResponse)
				mockLiferayPortletActionResponse.getHttpServletResponse();

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			mockHttpServletResponse.getContentAsString());

		LayoutPageTemplateStructureRelElementVariation
			layoutPageTemplateStructureRelElementVariation =
				_layoutPageTemplateStructureRelElementVariationLocalService.
					getLayoutPageTemplateStructureRelElementVariation(
						jsonObject.getLong(
							"layoutPageTemplateStructureRelElementVariationId"));

		Assert.assertEquals(
			hide,
			layoutPageTemplateStructureRelElementVariation.getHide(
				LocaleUtil.US));
		Assert.assertEquals(
			html,
			layoutPageTemplateStructureRelElementVariation.getHtml(
				LocaleUtil.US));
		Assert.assertEquals(
			js,
			layoutPageTemplateStructureRelElementVariation.getJs(
				LocaleUtil.US));
		Assert.assertEquals(
			name, layoutPageTemplateStructureRelElementVariation.getName());
		Assert.assertEquals(
			draftLayout.getPlid(),
			layoutPageTemplateStructureRelElementVariation.getPlid());
		Assert.assertEquals(
			targetElement,
			layoutPageTemplateStructureRelElementVariation.getTargetElement());
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private LayoutPageTemplateStructureRelElementVariationLocalService
		_layoutPageTemplateStructureRelElementVariationLocalService;

	@Inject(
		filter = "mvc.command.name=/layout_content_page_editor/publish_layout_page_template_structure_rel_element_variation"
	)
	private MVCActionCommand _mvcActionCommand;

}
