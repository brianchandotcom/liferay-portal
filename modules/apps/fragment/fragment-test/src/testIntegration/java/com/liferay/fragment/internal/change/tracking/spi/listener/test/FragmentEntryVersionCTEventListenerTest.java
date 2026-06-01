/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.change.tracking.spi.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTCollectionService;
import com.liferay.fragment.internal.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.persistence.FragmentEntryVersionUtil;
import com.liferay.fragment.test.util.FragmentEntryVersionTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
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
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class FragmentEntryVersionCTEventListenerTest {

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
	public void testOnAfterPublish() throws Throwable {
		FragmentEntry fragmentEntry1 = _addFragmentEntry();

		List<Integer> versions =
			FragmentEntryVersionTestUtil.getFragmentEntryVersions(
				fragmentEntry1);

		int oldestVersion = versions.get(0);

		Assert.assertTrue(oldestVersion > 0);

		FragmentEntry fragmentEntry2 = _addFragmentEntry();

		CTCollection ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), null);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection.getCtCollectionId())) {

			FragmentEntryVersionTestUtil.updateFragmentEntry(fragmentEntry1);
			FragmentEntryVersionTestUtil.updateFragmentEntry(fragmentEntry2);
		}

		_ctCollectionService.publishCTCollection(
			TestPropsValues.getUserId(), ctCollection.getCtCollectionId());

		FragmentEntryVersionUtil.clearCache();

		Assert.assertEquals(
			FragmentConstants.FRAGMENT_ENTRY_VERSIONS_COUNT_MAX,
			FragmentEntryVersionTestUtil.countFragmentEntryVersions(
				CTConstants.CT_COLLECTION_ID_PRODUCTION, fragmentEntry1));

		versions = FragmentEntryVersionTestUtil.getFragmentEntryVersions(
			fragmentEntry1);

		Assert.assertFalse(
			versions.toString(), versions.contains(oldestVersion));

		Assert.assertEquals(
			FragmentConstants.FRAGMENT_ENTRY_VERSIONS_COUNT_MAX,
			FragmentEntryVersionTestUtil.countFragmentEntryVersions(
				CTConstants.CT_COLLECTION_ID_PRODUCTION, fragmentEntry2));
	}

	private FragmentEntry _addFragmentEntry() throws Exception {
		FragmentEntry fragmentEntry =
			FragmentEntryVersionTestUtil.addFragmentEntry(_group.getGroupId());

		FragmentEntryVersionTestUtil.insertFragmentEntryVersions(
			FragmentConstants.FRAGMENT_ENTRY_VERSIONS_COUNT_MAX,
			CTConstants.CT_COLLECTION_ID_PRODUCTION, fragmentEntry);

		Assert.assertEquals(
			FragmentConstants.FRAGMENT_ENTRY_VERSIONS_COUNT_MAX + 1,
			FragmentEntryVersionTestUtil.countFragmentEntryVersions(
				CTConstants.CT_COLLECTION_ID_PRODUCTION, fragmentEntry));

		return fragmentEntry;
	}

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private CTCollectionService _ctCollectionService;

	@DeleteAfterTestRun
	private Group _group;

}