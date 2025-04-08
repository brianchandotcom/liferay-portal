/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.change.tracking.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.conflict.ConflictInfo;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gislayne Vitorino
 */
@RunWith(Arquillian.class)
public class FragmentEntryVersionConstraintResolverTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testResolveConflict() throws Exception {
		Group group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				null, TestPropsValues.getUserId(), group.getGroupId(),
				RandomTestUtil.randomString(), StringPool.BLANK,
				serviceContext);

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				null, TestPropsValues.getUserId(), group.getGroupId(),
				fragmentCollection.getFragmentCollectionId(), null,
				RandomTestUtil.randomString(), StringPool.BLANK,
				"Fragment Entry HTML", StringPool.BLANK, false, null, null, 0,
				false, false, FragmentConstants.TYPE_COMPONENT, null,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		CTCollection ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), null);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection.getCtCollectionId())) {

			fragmentEntry.setName(RandomTestUtil.randomString());

			fragmentEntry = _fragmentEntryLocalService.updateFragmentEntry(
				fragmentEntry);
		}

		fragmentEntry.setName(RandomTestUtil.randomString());

		_fragmentEntryLocalService.updateFragmentEntry(fragmentEntry);

		Map<Long, List<ConflictInfo>> conflictsMap =
			_ctCollectionLocalService.checkConflicts(ctCollection);

		Assert.assertFalse(conflictsMap.isEmpty());
	}

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

}