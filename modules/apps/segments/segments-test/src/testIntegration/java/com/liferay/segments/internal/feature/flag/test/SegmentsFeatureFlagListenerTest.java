/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal.feature.flag.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.FeatureFlagTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.test.util.SegmentsTestUtil;

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
public class SegmentsFeatureFlagListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	@TestInfo("LPD-87246")
	public void testActivateSegmentsEntries() throws Exception {
		SegmentsEntry defaultSegmentsEntry = _addSegmentsEntry(
			false, SegmentsEntryConstants.SOURCE_DEFAULT);
		SegmentsEntry referredSegmentsEntry = _addSegmentsEntry(
			false, SegmentsEntryConstants.SOURCE_REFERRED);

		FeatureFlagTestUtil.invokeFeatureFlagListeners(
			TestPropsValues.getCompanyId(), true, "LPD-78863");

		Assert.assertTrue(
			_segmentsEntryLocalService.getSegmentsEntry(
				defaultSegmentsEntry.getSegmentsEntryId()
			).isActive());
		Assert.assertTrue(
			_segmentsEntryLocalService.getSegmentsEntry(
				referredSegmentsEntry.getSegmentsEntryId()
			).isActive());
	}

	@Test
	@TestInfo("LPD-87246")
	public void testAsahFaroBackendSegmentEntriesAreSkipped() throws Exception {
		SegmentsEntry asahFaroSegmentsEntry = _addSegmentsEntry(
			false, SegmentsEntryConstants.SOURCE_ASAH_FARO_BACKEND);

		FeatureFlagTestUtil.invokeFeatureFlagListeners(
			TestPropsValues.getCompanyId(), true, "LPD-78863");

		Assert.assertFalse(
			_segmentsEntryLocalService.getSegmentsEntry(
				asahFaroSegmentsEntry.getSegmentsEntryId()
			).isActive());
	}

	@Test
	@TestInfo("LPD-87246")
	public void testDeactivateSegmentsEntries() throws Exception {
		SegmentsEntry defaultSegmentsEntry = _addSegmentsEntry(
			true, SegmentsEntryConstants.SOURCE_DEFAULT);
		SegmentsEntry referredSegmentsEntry = _addSegmentsEntry(
			true, SegmentsEntryConstants.SOURCE_REFERRED);

		FeatureFlagTestUtil.invokeFeatureFlagListeners(
			TestPropsValues.getCompanyId(), false, "LPD-78863");

		Assert.assertFalse(
			_segmentsEntryLocalService.getSegmentsEntry(
				defaultSegmentsEntry.getSegmentsEntryId()
			).isActive());
		Assert.assertFalse(
			_segmentsEntryLocalService.getSegmentsEntry(
				referredSegmentsEntry.getSegmentsEntryId()
			).isActive());
	}

	@Test
	@TestInfo("LPD-87246")
	public void testDeactivateSegmentsExperiences() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		SegmentsEntry segmentsEntry = _addSegmentsEntry(
			true, SegmentsEntryConstants.SOURCE_DEFAULT);
		SegmentsEntry asahFaroSegmentsEntry = _addSegmentsEntry(
			true, SegmentsEntryConstants.SOURCE_ASAH_FARO_BACKEND);

		SegmentsExperience segmentsExperience =
			SegmentsTestUtil.addSegmentsExperience(
				_group.getGroupId(), segmentsEntry.getExternalReferenceCode(),
				_group.getExternalReferenceCode(), layout.getPlid());
		SegmentsExperience asahFaroSegmentsExperience =
			SegmentsTestUtil.addSegmentsExperience(
				_group.getGroupId(),
				asahFaroSegmentsEntry.getExternalReferenceCode(),
				_group.getExternalReferenceCode(), layout.getPlid());

		FeatureFlagTestUtil.invokeFeatureFlagListeners(
			TestPropsValues.getCompanyId(), false, "LPD-78863");

		Assert.assertFalse(
			_segmentsExperienceLocalService.getSegmentsExperience(
				segmentsExperience.getSegmentsExperienceId()
			).isActive());
		Assert.assertTrue(
			_segmentsExperienceLocalService.getSegmentsExperience(
				asahFaroSegmentsExperience.getSegmentsExperienceId()
			).isActive());
	}

	private SegmentsEntry _addSegmentsEntry(boolean active, String source)
		throws Exception {

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			CriteriaSerializer.serialize(new Criteria()), source,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		if (!active) {
			segmentsEntry.setActive(false);

			segmentsEntry = _segmentsEntryLocalService.updateSegmentsEntry(
				segmentsEntry);
		}

		return segmentsEntry;
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}