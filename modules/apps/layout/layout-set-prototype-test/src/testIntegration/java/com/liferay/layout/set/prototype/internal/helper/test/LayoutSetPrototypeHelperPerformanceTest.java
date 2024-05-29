/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.set.prototype.internal.helper.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.layout.set.prototype.helper.LayoutSetPrototypeHelper;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.test.performance.PerformanceTimer;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.sites.kernel.util.Sites;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class LayoutSetPrototypeHelperPerformanceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_layoutSetPrototype = LayoutTestUtil.addLayoutSetPrototype(
			RandomTestUtil.randomString());

		for (int i = 0; i < _NUMBER_GROUPS; i++) {
			Group group = GroupTestUtil.addGroup();

			setLinkEnabled(group);

			_groups.add(group);
		}
	}

	@Test
	public void testGetDuplicatedFriendlyURLLayoutsWithLayoutSetPrototypeLayout()
		throws Exception {

		List<Layout> layouts = new ArrayList<>();

		for (Group group : _groups) {
			layouts.add(
				LayoutTestUtil.addTypePortletLayout(
					group.getGroupId(), "test", false));
		}

		Layout layoutSetPrototypeLayout = LayoutTestUtil.addTypePortletLayout(
			_layoutSetPrototype.getGroupId(), "test", true);

		_entityCache.clearCache();
		_multiVMPool.clear();

		List<Layout> conflictLayouts = null;

		try (PerformanceTimer performanceTimer = new PerformanceTimer(1000)) {
			conflictLayouts =
				_layoutSetPrototypeHelper.getDuplicatedFriendlyURLLayouts(
					layoutSetPrototypeLayout);
		}

		Assert.assertEquals(
			conflictLayouts.toString(), layouts.size(), conflictLayouts.size());
		Assert.assertArrayEquals(
			TransformUtil.transformToLongArray(
				layouts, layout -> layout.getPlid()),
			TransformUtil.transformToLongArray(
				conflictLayouts, layout -> layout.getPlid()));
	}

	protected void setLinkEnabled(Group group) throws Exception {
		MergeLayoutPrototypesThreadLocal.clearMergeComplete();

		_sites.updateLayoutSetPrototypesLinks(
			group, _layoutSetPrototype.getLayoutSetPrototypeId(), 0, true,
			false);
	}

	private static final int _NUMBER_GROUPS = 5;

	@Inject
	private EntityCache _entityCache;

	@DeleteAfterTestRun
	private List<Group> _groups = new ArrayList<>();

	@DeleteAfterTestRun
	private LayoutSetPrototype _layoutSetPrototype;

	@Inject
	private LayoutSetPrototypeHelper _layoutSetPrototypeHelper;

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject
	private Sites _sites;

}