/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.change.tracking.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.configuration.CTSettingsConfiguration;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTCollectionServiceUtil;
import com.liferay.fragment.constants.FragmentEntryVersionConstants;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.test.util.FragmentEntryVersionTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Georgel Pop
 */
@RunWith(Arquillian.class)
public class FragmentEntryVersionCleanUpCTCollectionTest {

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
	public void testCleanUpDoesNotBreakCTCollectionPublication()
		throws Exception {

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						CTSettingsConfiguration.class.getName(),
						HashMapDictionaryBuilder.<String, Object>put(
							"enabled", true
						).build())) {

			CTCollection ctCollection =
				_ctCollectionLocalService.addCTCollection(
					null, TestPropsValues.getCompanyId(),
					TestPropsValues.getUserId(), 0,
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString());

			FragmentEntry fragmentEntry = null;

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
						ctCollection.getCtCollectionId())) {

				fragmentEntry = FragmentEntryVersionTestUtil.addFragmentEntry(
					_group.getGroupId());

				for (int i = 0;
					 i <
						 (FragmentEntryVersionConstants.
							 FRAGMENT_ENTRY_VERSIONS_COUNT_MAX + 5);
					 i++) {

					_fragmentEntryLocalService.updateFragmentEntry(
						TestPropsValues.getUserId(),
						fragmentEntry.getFragmentEntryId(),
						fragmentEntry.getFragmentCollectionId(),
						fragmentEntry.getName(), StringPool.BLANK,
						RandomTestUtil.randomString(), StringPool.BLANK, false,
						StringPool.BLANK, StringPool.BLANK, 0, false,
						StringPool.BLANK, WorkflowConstants.STATUS_APPROVED);
				}
			}

			int versionsInCTCollectionBeforeCleanUp =
				FragmentEntryVersionTestUtil.countVersions(
					ctCollection.getCtCollectionId(), fragmentEntry);

			_fragmentEntryLocalService.cleanUpFragmentEntryVersions(
				TestPropsValues.getCompanyId());

			int versionsInCTCollectionAfterCleanUp =
				FragmentEntryVersionTestUtil.countVersions(
					ctCollection.getCtCollectionId(), fragmentEntry);

			Assert.assertTrue(
				versionsInCTCollectionBeforeCleanUp >
					FragmentEntryVersionConstants.
						FRAGMENT_ENTRY_VERSIONS_COUNT_MAX);

			Assert.assertEquals(
				FragmentEntryVersionConstants.FRAGMENT_ENTRY_VERSIONS_COUNT_MAX,
				versionsInCTCollectionAfterCleanUp);

			try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
					"com.liferay.portal.background.task.internal.messaging." +
						"BackgroundTaskMessageListener",
					LoggerTestUtil.ERROR)) {

				CTCollectionServiceUtil.publishCTCollection(
					TestPropsValues.getUserId(),
					ctCollection.getCtCollectionId());

				List<LogEntry> logEntries = logCapture.getLogEntries();

				Assert.assertEquals(
					logEntries.toString(), 0, logEntries.size());
			}
		}
	}

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

}