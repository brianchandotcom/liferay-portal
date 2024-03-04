/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.internal.upgrade.v3_4_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

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
public class LayoutFriendlyURLEntryUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_privateLayoutClassNameId = _classNameLocalService.getClassNameId(
			_resourceActions.getCompositeModelName(
				Layout.class.getName(), Boolean.TRUE.toString()));

		_publicLayoutClassNameId = _classNameLocalService.getClassNameId(
			_resourceActions.getCompositeModelName(
				Layout.class.getName(), Boolean.FALSE.toString()));
	}

	@Test
	public void testUpgradeProcess() throws Exception {
		Layout layout1 = LayoutTestUtil.addTypeContentLayout(_group);

		Layout draftLayout1 = layout1.fetchDraftLayout();

		_assertFriendlyURLEntries(draftLayout1, layout1);

		Layout layout2 = LayoutTestUtil.addTypeContentLayout(_group);

		Layout draftLayout2 = layout2.fetchDraftLayout();

		_deleteFriendlyURLEntries(draftLayout2, layout2);

		_runUpgrade();

		_assertFriendlyURLEntries(draftLayout1, draftLayout2, layout1, layout2);
	}

	private void _assertFriendlyURLEntries(Layout... layouts) throws Exception {
		for (Layout layout : layouts) {
			_assertFriendlyURLEntry(layout, _getFriendlyURLEntry(layout));
		}
	}

	private void _assertFriendlyURLEntry(
			Layout layout, FriendlyURLEntry friendlyURLEntry)
		throws Exception {

		Assert.assertEquals(
			layout.getAvailableLanguageIds(),
			friendlyURLEntry.getAvailableLanguageIds());

		for (String languageId : layout.getAvailableLanguageIds()) {
			FriendlyURLEntryLocalization friendlyURLEntryLocalization =
				_friendlyURLEntryLocalService.getFriendlyURLEntryLocalization(
					friendlyURLEntry.getFriendlyURLEntryId(), languageId);

			Assert.assertNotNull(friendlyURLEntryLocalization);

			Assert.assertEquals(
				layout.getFriendlyURL(LocaleUtil.fromLanguageId(languageId)),
				friendlyURLEntryLocalization.getUrlTitle());
		}
	}

	private void _deleteFriendlyURLEntries(Layout... layouts) throws Exception {
		for (Layout layout : layouts) {
			_deleteFriendlyURLEntry(layout);
		}
	}

	private void _deleteFriendlyURLEntry(Layout layout) throws Exception {
		FriendlyURLEntry friendlyURLEntry = _getFriendlyURLEntry(layout);

		_assertFriendlyURLEntry(layout, friendlyURLEntry);

		_friendlyURLEntryLocalService.deleteFriendlyURLEntry(friendlyURLEntry);

		List<FriendlyURLEntry> friendlyURLEntries =
			_friendlyURLEntryLocalService.getFriendlyURLEntries(
				layout.getGroupId(), friendlyURLEntry.getClassNameId(),
				layout.getPlid());

		Assert.assertEquals(
			friendlyURLEntries.toString(), 0, friendlyURLEntries.size());
	}

	private FriendlyURLEntry _getFriendlyURLEntry(Layout layout)
		throws Exception {

		long classNameId = _publicLayoutClassNameId;

		if (layout.isPrivateLayout()) {
			classNameId = _privateLayoutClassNameId;
		}

		List<FriendlyURLEntry> friendlyURLEntries =
			_friendlyURLEntryLocalService.getFriendlyURLEntries(
				layout.getGroupId(), classNameId, layout.getPlid());

		Assert.assertEquals(
			friendlyURLEntries.toString(), 1, friendlyURLEntries.size());

		return friendlyURLEntries.get(0);
	}

	private void _runUpgrade() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.WARN)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME);

			upgradeProcess.upgrade();

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 0, logEntries.size());

			_multiVMPool.clear();
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.friendly.url.internal.upgrade.v3_4_1." +
			"LayoutFriendlyURLEntryUpgradeProcess";

	@Inject(
		filter = "(&(component.name=com.liferay.friendly.url.internal.upgrade.registry.FriendlyURLServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private MultiVMPool _multiVMPool;

	private long _privateLayoutClassNameId;
	private long _publicLayoutClassNameId;

	@Inject
	private ResourceActions _resourceActions;

}