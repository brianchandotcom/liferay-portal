/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.frontend.js.audiences.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.audiences.model.AudiencesEntry;
import com.liferay.audiences.service.AudiencesEntryLocalService;
import com.liferay.frontend.js.audiences.ElementVariations;
import com.liferay.frontend.js.audiences.ElementVariationsProvider;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelElementVariationLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Víctor Galán
 */
@RunWith(Arquillian.class)
public class ElementVariationsProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@FeatureFlags(featureFlags = @FeatureFlag(value = "LPD-93951"))
	@Test
	public void testGetElementVariations() throws Exception {
		AudiencesEntry audiencesEntry =
			_audiencesEntryLocalService.addAudiencesEntry(
				RandomTestUtil.randomString(),
				"{\"conjunction\": \"AND\", \"rules\": []}",
				RandomTestUtil.randomString(),
				ServiceContextTestUtil.getServiceContext(
					TestPropsValues.getGroupId()));

		String jsBody = "console.log();";
		String targetElement = RandomTestUtil.randomString();

		Group group = _groupLocalService.getGroup(TestPropsValues.getGroupId());

		Layout layout = LayoutTestUtil.addTypeContentLayout(group);

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperience(
				layout.getPlid());

		String segmentsExperienceERC =
			segmentsExperience.getExternalReferenceCode();

		Map<Locale, String> htmlMap = new HashMap<>();

		htmlMap.put(LocaleUtil.SPAIN, null);
		htmlMap.put(LocaleUtil.US, RandomTestUtil.randomString());

		_layoutPageTemplateStructureRelElementVariationLocalService.
			addOrUpdateLayoutPageTemplateStructureRelElementVariation(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				group.getGroupId(),
				new String[] {audiencesEntry.getExternalReferenceCode()},
				HashMapBuilder.put(
					LocaleUtil.SPAIN, ""
				).put(
					LocaleUtil.US, "true"
				).build(),
				htmlMap,
				HashMapBuilder.put(
					LocaleUtil.SPAIN, ""
				).put(
					LocaleUtil.US, jsBody
				).build(),
				RandomTestUtil.randomString(), layout.getPlid(),
				segmentsExperienceERC, targetElement,
				ServiceContextTestUtil.getServiceContext(
					group, TestPropsValues.getUserId()));

		ElementVariations elementVariations =
			_elementVariationsProvider.getElementVariations(layout.getPlid());

		Assert.assertNotNull(elementVariations);

		String content = elementVariations.getContent();

		String audienceEntryERCsJSON = JSONUtil.putAll(
			audiencesEntry.getExternalReferenceCode()
		).toString();

		Assert.assertTrue(
			content,
			content.contains("\"audienceEntryERCs\":" + audienceEntryERCsJSON));

		String hideJSON = JSONUtil.put(
			"en_US", "true"
		).toString();

		Assert.assertTrue(content, content.contains("\"hide\":" + hideJSON));

		Assert.assertTrue(
			content,
			content.contains(
				"\"en_US\": function (element) {\n" + jsBody + "\n}"));
		Assert.assertTrue(
			content,
			content.contains("\"targetElement\":\"" + targetElement + "\""));
	}

	@Inject
	private AudiencesEntryLocalService _audiencesEntryLocalService;

	@Inject
	private ElementVariationsProvider _elementVariationsProvider;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private LayoutPageTemplateStructureRelElementVariationLocalService
		_layoutPageTemplateStructureRelElementVariationLocalService;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}