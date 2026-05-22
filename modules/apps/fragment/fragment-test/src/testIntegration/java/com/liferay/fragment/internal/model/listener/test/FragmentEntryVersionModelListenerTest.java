/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.fragment.internal.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryVersion;
import com.liferay.fragment.service.persistence.FragmentEntryVersionPersistence;
import com.liferay.fragment.test.util.FragmentEntryVersionTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.Date;
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
public class FragmentEntryVersionModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_date = new Date(System.currentTimeMillis() + 3600000L);
		_modifiedDateCounter = System.currentTimeMillis() + 3600000L;
		_versionCounter = 1000;
	}

	@Test
	public void testOnAfterCreate() throws Throwable {
		_testOnAfterCreateDeletesOldVersions();
		_testOnAfterCreateSkipsCleanup();
	}

	private void _addFragmentEntryVersions(
			int count, FragmentEntry fragmentEntry)
		throws Throwable {

		for (int i = 0; i < count; i++) {
			FragmentEntryVersion newVersion =
				_fragmentEntryVersionPersistence.create(
					_counterLocalService.increment(
						FragmentEntryVersion.class.getName()));

			Date modifiedDate = new Date(_modifiedDateCounter);

			_modifiedDateCounter += 1000L;

			newVersion.setVersion(_versionCounter++);
			newVersion.setFragmentEntryId(fragmentEntry.getFragmentEntryId());
			newVersion.setGroupId(fragmentEntry.getGroupId());
			newVersion.setCompanyId(fragmentEntry.getCompanyId());
			newVersion.setUserId(fragmentEntry.getUserId());
			newVersion.setUserName(fragmentEntry.getUserName());
			newVersion.setCreateDate(_date);
			newVersion.setModifiedDate(modifiedDate);
			newVersion.setFragmentCollectionId(
				fragmentEntry.getFragmentCollectionId());
			newVersion.setFragmentEntryKey(fragmentEntry.getFragmentEntryKey());
			newVersion.setName(RandomTestUtil.randomString());
			newVersion.setStatus(WorkflowConstants.STATUS_APPROVED);

			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> _fragmentEntryVersionPersistence.update(newVersion));
		}
	}

	private void _testOnAfterCreateDeletesOldVersions() throws Throwable {
		FragmentEntry fragmentEntry =
			FragmentEntryVersionTestUtil.addFragmentEntry(_group.getGroupId());

		_addFragmentEntryVersions(20, fragmentEntry);

		List<Integer> expectedVersions = new ArrayList<>(
			FragmentConstants.MAX_FRAGMENT_ENTRY_VERSION_COUNT);

		for (int version =
				_versionCounter -
					FragmentConstants.MAX_FRAGMENT_ENTRY_VERSION_COUNT;
			 version < _versionCounter; version++) {

			expectedVersions.add(version);
		}

		Assert.assertEquals(
			expectedVersions,
			FragmentEntryVersionTestUtil.getFragmentEntryVersions(
				fragmentEntry));
	}

	private void _testOnAfterCreateSkipsCleanup() throws Throwable {
		int versionsToGenerate = 5;

		FragmentEntry fragmentEntry =
			FragmentEntryVersionTestUtil.addFragmentEntry(_group.getGroupId());

		int initialCount =
			FragmentEntryVersionTestUtil.countFragmentEntryVersions(
				fragmentEntry);

		_addFragmentEntryVersions(versionsToGenerate, fragmentEntry);

		Assert.assertEquals(
			versionsToGenerate + initialCount,
			FragmentEntryVersionTestUtil.countFragmentEntryVersions(
				fragmentEntry));
	}

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Inject
	private CounterLocalService _counterLocalService;

	private Date _date;

	@Inject
	private FragmentEntryVersionPersistence _fragmentEntryVersionPersistence;

	@DeleteAfterTestRun
	private Group _group;

	private long _modifiedDateCounter;
	private int _versionCounter;

}