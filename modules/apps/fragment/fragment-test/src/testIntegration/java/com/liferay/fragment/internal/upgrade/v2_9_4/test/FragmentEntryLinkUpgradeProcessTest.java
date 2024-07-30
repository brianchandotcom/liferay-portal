/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.upgrade.v2_9_4.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.test.util.BaseCTUpgradeProcessTestCase;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
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
public class FragmentEntryLinkUpgradeProcessTest
	extends BaseCTUpgradeProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addTypeContentLayout(_group);

		_segmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				_layout.getPlid());
	}

	@Test
	public void testUpgrade() throws Exception {
		FragmentEntryLink draftLayoutFragmentEntryLink =
			ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
				JSONUtil.put(
					FragmentEntryProcessorConstants.
						KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR,
					JSONUtil.put(
						RandomTestUtil.randomString(),
						RandomTestUtil.randomString())
				).toString(),
				_layout.fetchDraftLayout(), _segmentsExperienceId);
		FragmentEntryLink draftLayoutPortletFragmentEntryLink =
			ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
				JSONUtil.put(
					"portletId", RandomTestUtil.randomString()
				).toString(),
				_layout.fetchDraftLayout(), _segmentsExperienceId);

		ContentLayoutTestUtil.publishLayout(
			_layout.fetchDraftLayout(), _layout);

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinksByPlid(
				_layout.getGroupId(), _layout.getPlid());

		Assert.assertEquals(
			fragmentEntryLinks.toString(), 2, fragmentEntryLinks.size());

		FragmentEntryLink publishedLayoutFragmentEntryLink =
			_fragmentEntryLinkLocalService.getFragmentEntryLink(
				_group.getGroupId(),
				draftLayoutFragmentEntryLink.getFragmentEntryLinkId(),
				_layout.getPlid());

		FragmentEntryLink publishedLayoutPortletFragmentEntryLink =
			_fragmentEntryLinkLocalService.getFragmentEntryLink(
				_group.getGroupId(),
				draftLayoutPortletFragmentEntryLink.getFragmentEntryLinkId(),
				_layout.getPlid());

		_updateFragmentEntryLinksType(
			RandomTestUtil.randomInt(),
			draftLayoutFragmentEntryLink.getFragmentEntryLinkId(),
			draftLayoutPortletFragmentEntryLink.getFragmentEntryLinkId(),
			publishedLayoutFragmentEntryLink.getFragmentEntryLinkId(),
			publishedLayoutPortletFragmentEntryLink.getFragmentEntryLinkId());

		runUpgrade();

		_assertFragmentEntryLinksType(
			FragmentConstants.TYPE_COMPONENT,
			draftLayoutFragmentEntryLink.getFragmentEntryLinkId(),
			publishedLayoutFragmentEntryLink.getFragmentEntryLinkId());
		_assertFragmentEntryLinksType(
			FragmentConstants.TYPE_PORTLET,
			draftLayoutPortletFragmentEntryLink.getFragmentEntryLinkId(),
			publishedLayoutPortletFragmentEntryLink.getFragmentEntryLinkId());
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		return ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
			"{}", _layout.fetchDraftLayout(), _segmentsExperienceId);
	}

	@Override
	protected CTService<?> getCTService() {
		return _fragmentEntryLinkLocalService;
	}

	@Override
	protected void runUpgrade() throws Exception {
		UpgradeProcess[] upgradeProcesses = UpgradeTestUtil.getUpgradeSteps(
			_upgradeStepRegistrator, new Version(2, 9, 4));

		for (UpgradeProcess upgradeProcess : upgradeProcesses) {
			upgradeProcess.upgrade();
		}

		_entityCache.clearCache();
		_multiVMPool.clear();
	}

	@Override
	protected CTModel<?> updateCTModel(CTModel<?> ctModel) throws Exception {
		FragmentEntryLink fragmentEntryLink = (FragmentEntryLink)ctModel;

		return _fragmentEntryLinkLocalService.updateFragmentEntryLink(
			TestPropsValues.getUserId(),
			fragmentEntryLink.getFragmentEntryLinkId(),
			JSONUtil.put(
				FragmentEntryProcessorConstants.
					KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR,
				JSONUtil.put(
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString())
			).toString());
	}

	private void _assertFragmentEntryLinksType(
			int expectedType, long... fragmentEntryLinkIds)
		throws Exception {

		for (long fragmentEntryLinkId : fragmentEntryLinkIds) {
			FragmentEntryLink fragmentEntryLink =
				_fragmentEntryLinkLocalService.getFragmentEntryLink(
					fragmentEntryLinkId);

			Assert.assertEquals(expectedType, fragmentEntryLink.getType());
		}
	}

	private void _updateFragmentEntryLinksType(
			int type, long... fragmentEntryLinkIds)
		throws Exception {

		for (long fragmentEntryLinkId : fragmentEntryLinkIds) {
			FragmentEntryLink fragmentEntryLink =
				_fragmentEntryLinkLocalService.getFragmentEntryLink(
					fragmentEntryLinkId);

			fragmentEntryLink.setType(type);

			_fragmentEntryLinkLocalService.updateFragmentEntryLink(
				fragmentEntryLink);
		}

		_assertFragmentEntryLinksType(type, fragmentEntryLinkIds);
	}

	@Inject(
		filter = "(&(component.name=com.liferay.fragment.internal.upgrade.registry.FragmentServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private EntityCache _entityCache;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private MultiVMPool _multiVMPool;

	private long _segmentsExperienceId;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}