/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.internal.upgrade.v3_1_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.friendly.url.service.persistence.FriendlyURLEntryLocalizationPersistence;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
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

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Joao Victor Alves
 */
@RunWith(Arquillian.class)
public class BlogsFriendlyURLFormatUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@AfterClass
	public static void tearDownClass() throws PortalException {
		for (Long friendlyURLEntryId : _friendlyURLEntryIds) {
			_friendlyURLEntryLocalService.deleteFriendlyURLLocalizationEntry(
				friendlyURLEntryId,
				LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()));
		}
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetFriendlyURLWithoutTrailingSlash() throws Exception {
		_addBlogsEntry("test/");

		_runUpgrade();

		_blogsEntry = _blogsEntryLocalService.fetchBlogsEntry(_blogsEntryId);

		Assert.assertEquals("test", _blogsEntry.getUrlTitle());
	}

	@Test
	public void testGetFriendlyURLWithUniqueURL() throws Exception {
		_addBlogsEntry("test");
		_addBlogsEntry("test/");

		_runUpgrade();

		_blogsEntry = _blogsEntryLocalService.fetchBlogsEntry(_blogsEntryId);

		Assert.assertEquals("test-1", _blogsEntry.getUrlTitle());
	}

	private void _addBlogsEntry(String urlTitle) {
		_blogsEntryId = RandomTestUtil.randomLong();

		_friendlyURLEntryIds.add(_blogsEntryId);

		_blogsEntry = _blogsEntryLocalService.createBlogsEntry(_blogsEntryId);

		_blogsEntry.setContent("test");

		_blogsEntry.setGroupId(_group.getGroupId());

		_blogsEntry.setUrlTitle(urlTitle);

		_blogsEntryLocalService.addBlogsEntry(_blogsEntry);

		_createFriendlyURLEntryLocalization(urlTitle);
	}

	private void _createFriendlyURLEntryLocalization(String urlTitle) {
		long friendlyURLEntryId = RandomTestUtil.randomLong();

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.createFriendlyURLEntry(
				friendlyURLEntryId);

		friendlyURLEntry.setDefaultLanguageId(
			LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()));

		_friendlyURLEntryLocalService.addFriendlyURLEntry(friendlyURLEntry);

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			_friendlyURLEntryLocalizationPersistence.create(
				RandomTestUtil.randomInt());

		friendlyURLEntryLocalization.setFriendlyURLEntryId(friendlyURLEntryId);
		friendlyURLEntryLocalization.setLanguageId(
			LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()));
		friendlyURLEntryLocalization.setUrlTitle(urlTitle);
		friendlyURLEntryLocalization.setGroupId(_group.getGroupId());
		friendlyURLEntryLocalization.setClassNameId(
			_classNameLocalService.getClassNameId(BlogsEntry.class));
		friendlyURLEntryLocalization.setClassPK(_blogsEntryId);

		_friendlyURLEntryLocalService.updateFriendlyURLLocalization(
			friendlyURLEntryLocalization);
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
		"com.liferay.blogs.internal.upgrade.v3_1_1." +
			"BlogsFriendlyURLFormatUpgradeProcess";

	private static final List<Long> _friendlyURLEntryIds = new ArrayList<>();

	@Inject
	private static FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@DeleteAfterTestRun
	private static Group _group;

	@Inject(
		filter = "(&(component.name=com.liferay.blogs.internal.upgrade.registry.BlogsServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	private BlogsEntry _blogsEntry;
	private long _blogsEntryId;

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private FriendlyURLEntryLocalizationPersistence
		_friendlyURLEntryLocalizationPersistence;

	@Inject
	private MultiVMPool _multiVMPool;

}