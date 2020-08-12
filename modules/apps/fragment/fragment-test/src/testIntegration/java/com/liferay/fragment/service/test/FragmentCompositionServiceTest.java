/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.fragment.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.service.FragmentCompositionService;
import com.liferay.fragment.service.persistence.FragmentCompositionPersistence;
import com.liferay.fragment.util.FragmentTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.transaction.Propagation;
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
 * @author Binh Tran
 */
@RunWith(Arquillian.class)
public class FragmentCompositionServiceTest {

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

		_fragmentCollectionAlter = FragmentTestUtil.addFragmentCollection(
			_group.getGroupId());
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

		final FragmentComposition fragmentComposition =
			_fragmentCompositionService.addFragmentComposition(
				_group.getGroupId(),
				_fragmentCollection.getFragmentCollectionId(),
				fragmentCompositionKey, name, description, data,
				previewFileEntryId, status, serviceContext);

		_fragmentCompositionService.updateFragmentComposition(
			fragmentComposition.getFragmentCompositionId(),
			_fragmentCollectionAlter.getFragmentCollectionId(),
			fragmentComposition.getName(), fragmentComposition.getDescription(),
			fragmentComposition.getData(),
			fragmentComposition.getPreviewFileEntryId(),
			fragmentComposition.getStatus());

		final FragmentComposition fragmentCompositionByPK =
			_fragmentCompositionPersistence.fetchByPrimaryKey(
				fragmentComposition.getFragmentCompositionId());

		Assert.assertEquals(
			fragmentCompositionByPK.getFragmentCollectionId(),
			_fragmentCollectionAlter.getFragmentCollectionId());

	}

	private FragmentCollection _fragmentCollection;

	private FragmentCollection _fragmentCollectionAlter;

	@Inject
	private FragmentCompositionPersistence _fragmentCompositionPersistence;

	@Inject
	private FragmentCompositionService _fragmentCompositionService;

	@DeleteAfterTestRun
	private Group _group;

}