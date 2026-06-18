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
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Collections;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Víctor Galán
 */
@RunWith(Arquillian.class)
public class
	PublishLayoutPageTemplateStructureRelElementVariationsMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testProcessAction() throws Exception {
		Group group = _groupLocalService.getGroup(TestPropsValues.getGroupId());

		Layout layout = LayoutTestUtil.addTypeContentLayout(group);

		Layout draftLayout = layout.fetchDraftLayout();

		long plid = draftLayout.getPlid();

		LayoutPageTemplateStructureRelElementVariation
			layoutPageTemplateStructureRelElementVariation =
				_layoutPageTemplateStructureRelElementVariationLocalService.
					addLayoutPageTemplateStructureRelElementVariation(
						null, TestPropsValues.getUserId(), group.getGroupId(),
						RandomTestUtil.randomString(),
						Collections.singletonMap(
							LocaleUtil.US, RandomTestUtil.randomString()),
						Collections.singletonMap(
							LocaleUtil.US, RandomTestUtil.randomString()),
						Collections.singletonMap(
							LocaleUtil.US, RandomTestUtil.randomString()),
						RandomTestUtil.randomString(), plid,
						RandomTestUtil.randomString(),
						RandomTestUtil.randomString(),
						ServiceContextTestUtil.getServiceContext(
							group.getGroupId()));

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			ContentLayoutTestUtil.getMockLiferayPortletActionRequest(
				_companyLocalService.getCompany(TestPropsValues.getCompanyId()),
				group, draftLayout);

		String audienceEntryERC = RandomTestUtil.randomString();
		String externalReferenceCode = RandomTestUtil.randomString();
		String hide = RandomTestUtil.randomString();
		String html = RandomTestUtil.randomString();
		String js = RandomTestUtil.randomString();
		String name = RandomTestUtil.randomString();
		String segmentsExperienceERC = RandomTestUtil.randomString();
		String targetElement = RandomTestUtil.randomString();

		JSONArray elementVariationsJSONArray = JSONUtil.putAll(
			JSONUtil.put(
				"audienceEntryERC", audienceEntryERC
			).put(
				"externalReferenceCode", externalReferenceCode
			).put(
				"hideMap",
				JSONUtil.put(LocaleUtil.toLanguageId(LocaleUtil.US), hide)
			).put(
				"htmlMap",
				JSONUtil.put(LocaleUtil.toLanguageId(LocaleUtil.US), html)
			).put(
				"jsMap",
				JSONUtil.put(LocaleUtil.toLanguageId(LocaleUtil.US), js)
			).put(
				"name", name
			).put(
				"segmentsExperienceERC", segmentsExperienceERC
			).put(
				"targetElement", targetElement
			));

		mockLiferayPortletActionRequest.setParameter(
			"elementVariations", elementVariationsJSONArray.toString());

		mockLiferayPortletActionRequest.setParameter(
			"plid", String.valueOf(plid));

		_mvcActionCommand.processAction(
			mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());

		Assert.assertNull(
			_layoutPageTemplateStructureRelElementVariationLocalService.
				fetchLayoutPageTemplateStructureRelElementVariation(
					layoutPageTemplateStructureRelElementVariation.
						getLayoutPageTemplateStructureRelElementVariationId()));

		layoutPageTemplateStructureRelElementVariation =
			_layoutPageTemplateStructureRelElementVariationLocalService.
				fetchLayoutPageTemplateStructureRelElementVariationByExternalReferenceCode(
					externalReferenceCode, group.getGroupId());

		Assert.assertEquals(
			audienceEntryERC,
			layoutPageTemplateStructureRelElementVariation.
				getAudienceEntryERC());
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
			plid, layoutPageTemplateStructureRelElementVariation.getPlid());
		Assert.assertEquals(
			segmentsExperienceERC,
			layoutPageTemplateStructureRelElementVariation.
				getSegmentsExperienceERC());
		Assert.assertEquals(
			targetElement,
			layoutPageTemplateStructureRelElementVariation.getTargetElement());
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private LayoutPageTemplateStructureRelElementVariationLocalService
		_layoutPageTemplateStructureRelElementVariationLocalService;

	@Inject(
		filter = "mvc.command.name=/layout_content_page_editor/publish_layout_page_template_structure_rel_element_variations"
	)
	private MVCActionCommand _mvcActionCommand;

}