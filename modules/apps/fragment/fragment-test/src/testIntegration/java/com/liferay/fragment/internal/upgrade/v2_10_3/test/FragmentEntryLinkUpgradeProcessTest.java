/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.upgrade.v2_10_3.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class FragmentEntryLinkUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testUpgradeProcess() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		Layout draftLayout = layout.fetchDraftLayout();

		Assert.assertNotNull(draftLayout);

		long segmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				draftLayout.getPlid());

		FragmentEntryLink draftLayoutFragmentEntryLink =
			ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
				"{}", draftLayout, segmentsExperienceId);

		Assert.assertEquals(
			0, draftLayoutFragmentEntryLink.getOriginalFragmentEntryLinkId());

		ContentLayoutTestUtil.publishLayout(draftLayout, layout);

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinksByPlid(
				layout.getGroupId(), layout.getPlid());

		Assert.assertEquals(
			fragmentEntryLinks.toString(), 1, fragmentEntryLinks.size());

		FragmentEntryLink publishedLayoutFragmentEntryLink =
			fragmentEntryLinks.get(0);

		Assert.assertEquals(
			draftLayoutFragmentEntryLink.getFragmentEntryLinkId(),
			publishedLayoutFragmentEntryLink.getOriginalFragmentEntryLinkId());

		draftLayoutFragmentEntryLink.setOriginalFragmentEntryLinkId(
			RandomTestUtil.randomLong());

		draftLayoutFragmentEntryLink =
			_fragmentEntryLinkLocalService.updateFragmentEntryLink(
				draftLayoutFragmentEntryLink);

		Assert.assertTrue(
			draftLayoutFragmentEntryLink.getOriginalFragmentEntryLinkId() > 0);

		_runUpgrade();

		draftLayoutFragmentEntryLink =
			_fragmentEntryLinkLocalService.getFragmentEntryLink(
				draftLayoutFragmentEntryLink.getFragmentEntryLinkId());

		Assert.assertEquals(
			0, draftLayoutFragmentEntryLink.getOriginalFragmentEntryLinkId());

		publishedLayoutFragmentEntryLink =
			_fragmentEntryLinkLocalService.getFragmentEntryLink(
				publishedLayoutFragmentEntryLink.getFragmentEntryLinkId());

		Assert.assertEquals(
			draftLayoutFragmentEntryLink.getFragmentEntryLinkId(),
			publishedLayoutFragmentEntryLink.getOriginalFragmentEntryLinkId());
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess[] upgradeProcesses = UpgradeTestUtil.getUpgradeSteps(
			_upgradeStepRegistrator, new Version(2, 10, 3));

		for (UpgradeProcess upgradeProcess : upgradeProcesses) {
			upgradeProcess.upgrade();
		}

		_multiVMPool.clear();
	}

	@Inject(
		filter = "(&(component.name=com.liferay.fragment.internal.upgrade.registry.FragmentServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}