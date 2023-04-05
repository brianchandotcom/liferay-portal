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

package com.liferay.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.layout.set.prototype.helper.LayoutSetPrototypeHelper;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.sites.kernel.util.SitesUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Vendel Toreki
 */
@RunWith(Arquillian.class)
public class LayoutSetPrototypeFriendlyURLConflictDetectionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext());

		UserTestUtil.setUser(TestPropsValues.getUser());

		_group = GroupTestUtil.addGroup();

		_layoutSetPrototype = LayoutTestUtil.addLayoutSetPrototype(
			RandomTestUtil.randomString());

		_layoutSetPrototypeGroup = _layoutSetPrototype.getGroup();

		setLinkEnabled(true);
	}

	@Test
	public void testLayoutSetPrototypeLayoutMultipleFriendlyURLConflictDetectionFromPrototype()
		throws Exception {

		setLinkEnabled(true);

		String[] names = RandomTestUtil.randomStrings(3);

		// Add conflicting Layouts to site

		for (String name : names) {
			LayoutTestUtil.addTypePortletLayout(
				_group.getGroupId(), name, false);
		}

		// Add non-conflicting Layouts to site

		for (int i = 0; i < names.length; ++i) {
			LayoutTestUtil.addTypePortletLayout(
				_group.getGroupId(), RandomTestUtil.randomString(5), false);
		}

		// Add conflicting Layouts to LayoutSetPrototype

		List<Layout> prototypeLayouts = new ArrayList<>(names.length);

		for (String name : names) {
			Layout prototypeLayout = LayoutTestUtil.addTypePortletLayout(
				_layoutSetPrototypeGroup.getGroupId(), name, true);

			prototypeLayouts.add(prototypeLayout);
		}

		// Add non-conflicting Layouts to LayoutSetPrototype

		for (int i = 0; i < names.length; ++i) {
			LayoutTestUtil.addTypePortletLayout(
				_layoutSetPrototypeGroup.getGroupId(),
				RandomTestUtil.randomString(5), true);
		}

		List<Long> duplicatedFriendlyURLPlids =
			_layoutSetPrototypeHelper.getDuplicatedFriendlyURLPlids(
				_layoutSetPrototype);

		Assert.assertEquals(
			duplicatedFriendlyURLPlids.toString(), names.length,
			duplicatedFriendlyURLPlids.size());

		for (Layout prototypeLayout : prototypeLayouts) {
			Assert.assertTrue(
				duplicatedFriendlyURLPlids.contains(prototypeLayout.getPlid()));
		}
	}

	@Test
	public void testLayoutSetPrototypeLayoutMultipleFriendlyURLConflictDetectionFromSite()
		throws Exception {

		setLinkEnabled(true);

		String[] names = RandomTestUtil.randomStrings(3);

		List<Layout> siteLayouts = new ArrayList<>(names.length);

		// Add conflicting Layouts to site

		for (String name : names) {
			Layout siteLayout = LayoutTestUtil.addTypePortletLayout(
				_group.getGroupId(), name, false);

			siteLayouts.add(siteLayout);
		}

		// Add non-conflicting Layouts to site

		for (int i = 0; i < names.length; ++i) {
			LayoutTestUtil.addTypePortletLayout(
				_group.getGroupId(), RandomTestUtil.randomString(5), false);
		}

		// Add conflicting Layouts to LayoutSetPrototype

		for (String name : names) {
			LayoutTestUtil.addTypePortletLayout(
				_layoutSetPrototypeGroup.getGroupId(), name, true);
		}

		// Add non-conflicting Layouts to LayoutSetPrototype

		for (int i = 0; i < names.length; ++i) {
			LayoutTestUtil.addTypePortletLayout(
				_layoutSetPrototypeGroup.getGroupId(),
				RandomTestUtil.randomString(5), true);
		}

		List<Long> duplicatedFriendlyURLPlids =
			_layoutSetPrototypeHelper.getDuplicatedFriendlyURLPlids(
				_group.getPublicLayoutSet());

		Assert.assertEquals(
			duplicatedFriendlyURLPlids.toString(), names.length,
			duplicatedFriendlyURLPlids.size());

		for (Layout siteLayout : siteLayouts) {
			Assert.assertTrue(
				duplicatedFriendlyURLPlids.contains(siteLayout.getPlid()));
		}
	}

	protected void setLinkEnabled(boolean linkEnabled) throws Exception {
		MergeLayoutPrototypesThreadLocal.clearMergeComplete();

		SitesUtil.updateLayoutSetPrototypesLinks(
			_group, _layoutSetPrototype.getLayoutSetPrototypeId(), 0,
			linkEnabled, false);

		Thread.sleep(2000);
	}

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private LayoutSetPrototype _layoutSetPrototype;

	@DeleteAfterTestRun
	private Group _layoutSetPrototypeGroup;

	@Inject
	private LayoutSetPrototypeHelper _layoutSetPrototypeHelper;

}