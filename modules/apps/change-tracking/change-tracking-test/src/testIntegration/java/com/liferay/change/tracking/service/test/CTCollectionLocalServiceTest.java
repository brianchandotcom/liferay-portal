/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.change.tracking.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTProcessLocalService;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class CTCollectionLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		long ctCollectionId = _counterLocalService.increment(
			CTCollection.class.getName());

		_ctCollection = _ctCollectionLocalService.createCTCollection(
			ctCollectionId);

		_ctCollection.setUserId(TestPropsValues.getUserId());
		_ctCollection.setName(String.valueOf(ctCollectionId));
		_ctCollection.setStatus(WorkflowConstants.STATUS_DRAFT);

		_ctCollection = _ctCollectionLocalService.updateCTCollection(
			_ctCollection);

		_layoutClassNameId = _classNameLocalService.getClassNameId(
			Layout.class);

		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testUndoCTCollection() throws Exception {
		Layout addedLayout = null;

		Layout deletedLayout = LayoutTestUtil.addLayout(_group);

		Layout modifiedLayout = LayoutTestUtil.addLayout(_group);

		String originalFriendlyURL = modifiedLayout.getFriendlyURL();

		String newFriendlyURL = "/testModifyLayout";

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection.getCtCollectionId())) {

			addedLayout = LayoutTestUtil.addLayout(_group);

			_layoutLocalService.deleteLayout(deletedLayout);

			modifiedLayout.setFriendlyURL(newFriendlyURL);

			modifiedLayout = _layoutLocalService.updateLayout(modifiedLayout);
		}

		_ctProcessLocalService.addCTProcess(
			_ctCollection.getUserId(), _ctCollection.getCtCollectionId());

		Assert.assertEquals(
			addedLayout,
			_layoutLocalService.fetchLayout(addedLayout.getPlid()));

		Assert.assertNull(
			_layoutLocalService.fetchLayout(deletedLayout.getPlid()));

		modifiedLayout = _layoutLocalService.fetchLayout(
			modifiedLayout.getPlid());

		Assert.assertEquals(newFriendlyURL, modifiedLayout.getFriendlyURL());

		_ctCollection2 = _ctCollectionLocalService.undoCTCollection(
			_ctCollection.getCtCollectionId(), _ctCollection.getUserId(),
			_ctCollection.getName() + " (undo)", StringPool.BLANK);

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_ctCollection2.getCtCollectionId())) {

			Assert.assertNull(
				_layoutLocalService.fetchLayout(addedLayout.getPlid()));

			Assert.assertEquals(
				deletedLayout,
				_layoutLocalService.fetchLayout(deletedLayout.getPlid()));

			modifiedLayout = _layoutLocalService.fetchLayout(
				modifiedLayout.getPlid());

			Assert.assertEquals(
				originalFriendlyURL, modifiedLayout.getFriendlyURL());
		}

		_ctProcessLocalService.addCTProcess(
			_ctCollection2.getUserId(), _ctCollection2.getCtCollectionId());

		Assert.assertNull(
			_layoutLocalService.fetchLayout(addedLayout.getPlid()));

		Assert.assertEquals(
			deletedLayout,
			_layoutLocalService.fetchLayout(deletedLayout.getPlid()));

		modifiedLayout = _layoutLocalService.fetchLayout(
			modifiedLayout.getPlid());

		Assert.assertEquals(
			originalFriendlyURL, modifiedLayout.getFriendlyURL());
	}

	@Inject
	private static ClassNameLocalService _classNameLocalService;

	@Inject
	private static CounterLocalService _counterLocalService;

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private static CTProcessLocalService _ctProcessLocalService;

	private static long _layoutClassNameId;

	@Inject
	private static LayoutLocalService _layoutLocalService;

	@DeleteAfterTestRun
	private CTCollection _ctCollection;

	@DeleteAfterTestRun
	private CTCollection _ctCollection2;

	@DeleteAfterTestRun
	private Group _group;

}