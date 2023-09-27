/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.exception.FragmentCompositionNameException;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.service.FragmentCompositionLocalService;
import com.liferay.fragment.util.FragmentTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pavel Savinov
 */
@RunWith(Arquillian.class)
public class FragmentCompositionLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.fragment.service"));

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_fragmentCollection = FragmentTestUtil.addFragmentCollection(
			_group.getGroupId());

		_updatedFragmentCollection = FragmentTestUtil.addFragmentCollection(
			_group.getGroupId());
	}

	@Test(expected = FragmentCompositionNameException.class)
	public void testFragmentCompositionNameRequired() throws Exception {
		_fragmentCompositionLocalService.addFragmentComposition(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_fragmentCollection.getFragmentCollectionId(),
			StringUtil.randomId(), StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, 0, WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext());
	}

	@Test
	public void testUpdateFragmentCollectionId() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		String fragmentCompositionKey = RandomTestUtil.randomString();
		String name = RandomTestUtil.randomString();
		String description = RandomTestUtil.randomString();
		String data = RandomTestUtil.randomString();
		long previewFileEntryId = RandomTestUtil.randomLong();
		int status = WorkflowConstants.STATUS_APPROVED;

		FragmentComposition fragmentComposition =
			_fragmentCompositionLocalService.addFragmentComposition(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				fragmentCompositionKey, name, description, data,
				previewFileEntryId, status, serviceContext);

		_fragmentCompositionLocalService.updateFragmentComposition(
			fragmentComposition.getUserId(),
			fragmentComposition.getFragmentCompositionId(),
			_updatedFragmentCollection.getFragmentCollectionId(),
			fragmentComposition.getName(), fragmentComposition.getDescription(),
			fragmentComposition.getData(),
			fragmentComposition.getPreviewFileEntryId(),
			fragmentComposition.getStatus());

		FragmentComposition fragmentCompositionByPrimaryKey =
			_fragmentCompositionLocalService.fetchFragmentComposition(
				fragmentComposition.getFragmentCompositionId());

		Assert.assertEquals(
			_updatedFragmentCollection.getFragmentCollectionId(),
			fragmentCompositionByPrimaryKey.getFragmentCollectionId());
	}

	private FragmentCollection _fragmentCollection;

	@Inject
	private FragmentCompositionLocalService _fragmentCompositionLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private FragmentCollection _updatedFragmentCollection;

}