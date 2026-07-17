/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.page.template.exception.DuplicateLayoutPageTemplateStructureRelElementVariationAudienceEntryRelException;
import com.liferay.layout.page.template.exception.LayoutPageTemplateStructureRelElementVariationTargetElementException;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRelElementVariation;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelElementVariationLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Víctor Galán
 */
@RunWith(Arquillian.class)
public class LayoutPageTemplateStructureRelElementVariationLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, TestPropsValues.getUserId());
	}

	@Test
	public void testAddOrUpdateLayoutPageTemplateStructureRelElementVariation()
		throws Exception {

		_testAddOrUpdateLayoutPageTemplateStructureRelElementVariationWithDuplicateAudienceEntryERCForTargetElement();
		_testAddOrUpdateLayoutPageTemplateStructureRelElementVariationWithDuplicateAudienceEntryERCs();
		_testAddOrUpdateLayoutPageTemplateStructureRelElementVariationWithSameAudienceEntryERCForDifferentSegmentsExperience();
		_testAddOrUpdateLayoutPageTemplateStructureRelElementVariationWithSameAudienceEntryERCForDifferentTargetElement();
	}

	private void _addOrUpdateLayoutPageTemplateStructureRelElementVariation(
			String externalReferenceCode, long plid,
			String segmentsExperienceERC, String targetElement,
			String... audienceEntryERCs)
		throws Exception {

		_layoutPageTemplateStructureRelElementVariationLocalService.
			addOrUpdateLayoutPageTemplateStructureRelElementVariation(
				externalReferenceCode, TestPropsValues.getUserId(),
				_group.getGroupId(), RandomTestUtil.randomBoolean(),
				RandomTestUtil.randomString(),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()),
				Collections.singletonMap(
					LocaleUtil.US, RandomTestUtil.randomString()),
				RandomTestUtil.randomString(), plid, segmentsExperienceERC,
				targetElement, audienceEntryERCs, _serviceContext);
	}

	private void _testAddOrUpdateLayoutPageTemplateStructureRelElementVariationWithDuplicateAudienceEntryERCForTargetElement()
		throws Exception {

		String audienceEntryERC = RandomTestUtil.randomString();

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		long plid = layout.getPlid();

		String segmentsExperienceERC = RandomTestUtil.randomString();
		String targetElement = RandomTestUtil.randomString();

		_addOrUpdateLayoutPageTemplateStructureRelElementVariation(
			RandomTestUtil.randomString(), plid, segmentsExperienceERC,
			targetElement, audienceEntryERC);

		Assert.assertThrows(
			LayoutPageTemplateStructureRelElementVariationTargetElementException.class,
			() -> _addOrUpdateLayoutPageTemplateStructureRelElementVariation(
				RandomTestUtil.randomString(), plid, segmentsExperienceERC,
				targetElement, audienceEntryERC));
	}

	private void _testAddOrUpdateLayoutPageTemplateStructureRelElementVariationWithDuplicateAudienceEntryERCs()
		throws Exception {

		String audienceEntryERC = RandomTestUtil.randomString();

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		Assert.assertThrows(
			DuplicateLayoutPageTemplateStructureRelElementVariationAudienceEntryRelException.class,
			() -> _addOrUpdateLayoutPageTemplateStructureRelElementVariation(
				RandomTestUtil.randomString(), layout.getPlid(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				audienceEntryERC, audienceEntryERC));
	}

	private void _testAddOrUpdateLayoutPageTemplateStructureRelElementVariationWithSameAudienceEntryERCForDifferentSegmentsExperience()
		throws Exception {

		String audienceEntryERC = RandomTestUtil.randomString();

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		long plid = layout.getPlid();

		String targetElement = RandomTestUtil.randomString();

		_addOrUpdateLayoutPageTemplateStructureRelElementVariation(
			RandomTestUtil.randomString(), plid, RandomTestUtil.randomString(),
			targetElement, audienceEntryERC);
		_addOrUpdateLayoutPageTemplateStructureRelElementVariation(
			RandomTestUtil.randomString(), plid, RandomTestUtil.randomString(),
			targetElement, audienceEntryERC);

		List<LayoutPageTemplateStructureRelElementVariation>
			layoutPageTemplateStructureRelElementVariations =
				_layoutPageTemplateStructureRelElementVariationLocalService.
					getLayoutPageTemplateStructureRelElementVariations(plid);

		Assert.assertEquals(
			layoutPageTemplateStructureRelElementVariations.toString(), 2,
			layoutPageTemplateStructureRelElementVariations.size());
	}

	private void _testAddOrUpdateLayoutPageTemplateStructureRelElementVariationWithSameAudienceEntryERCForDifferentTargetElement()
		throws Exception {

		String audienceEntryERC = RandomTestUtil.randomString();

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		long plid = layout.getPlid();

		String segmentsExperienceERC = RandomTestUtil.randomString();

		_addOrUpdateLayoutPageTemplateStructureRelElementVariation(
			RandomTestUtil.randomString(), plid, segmentsExperienceERC,
			RandomTestUtil.randomString(), audienceEntryERC);
		_addOrUpdateLayoutPageTemplateStructureRelElementVariation(
			RandomTestUtil.randomString(), plid, segmentsExperienceERC,
			RandomTestUtil.randomString(), audienceEntryERC);

		List<LayoutPageTemplateStructureRelElementVariation>
			layoutPageTemplateStructureRelElementVariations =
				_layoutPageTemplateStructureRelElementVariationLocalService.
					getLayoutPageTemplateStructureRelElementVariations(
						plid, segmentsExperienceERC);

		Assert.assertEquals(
			layoutPageTemplateStructureRelElementVariations.toString(), 2,
			layoutPageTemplateStructureRelElementVariations.size());
	}

	private Group _group;

	@Inject
	private LayoutPageTemplateStructureRelElementVariationLocalService
		_layoutPageTemplateStructureRelElementVariationLocalService;

	private ServiceContext _serviceContext;

}