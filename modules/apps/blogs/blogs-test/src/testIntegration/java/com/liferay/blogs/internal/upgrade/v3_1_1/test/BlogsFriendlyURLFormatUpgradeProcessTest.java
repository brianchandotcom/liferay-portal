/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.internal.upgrade.v3_1_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.friendly.url.test.util.BaseFriendlyURLFormatUpgradeProcessTestCase;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Joao Victor Alves
 */
@RunWith(Arquillian.class)
public class BlogsFriendlyURLFormatUpgradeProcessTest
	extends BaseFriendlyURLFormatUpgradeProcessTestCase<BlogsEntry> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetFriendlyURLWithoutTrailingSlash() throws Exception {
		_addBlogsEntry("test/");

		_runUpgrade();

		_blogsEntry = _blogsEntryLocalService.fetchBlogsEntry(modelId);

		Assert.assertEquals("test", _blogsEntry.getUrlTitle());
	}

	@Test
	public void testGetFriendlyURLWithUniqueURL() throws Exception {
		_addBlogsEntry("test");
		_addBlogsEntry("test/");

		_runUpgrade();

		_blogsEntry = _blogsEntryLocalService.fetchBlogsEntry(modelId);

		Assert.assertEquals("test-1", _blogsEntry.getUrlTitle());
	}

	private void _addBlogsEntry(String urlTitle) {
		createFriendlyURLEntryLocalization(urlTitle, BlogsEntry.class);

		_blogsEntry = _blogsEntryLocalService.createBlogsEntry(modelId);

		_blogsEntry.setContent("test");

		_blogsEntry.setGroupId(group.getGroupId());

		_blogsEntry.setUrlTitle(urlTitle);

		_blogsEntryLocalService.addBlogsEntry(_blogsEntry);
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

	@Inject(
		filter = "(&(component.name=com.liferay.blogs.internal.upgrade.registry.BlogsServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	private BlogsEntry _blogsEntry;

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Inject
	private MultiVMPool _multiVMPool;

}