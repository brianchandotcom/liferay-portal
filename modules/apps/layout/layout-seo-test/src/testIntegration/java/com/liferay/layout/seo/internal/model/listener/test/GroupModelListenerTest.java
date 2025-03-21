/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.seo.service.LayoutSEOSiteLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jonathan McCann
 */
@RunWith(Arquillian.class)
public class GroupModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testDeleteGroup() throws Exception {
		Group group = GroupTestUtil.addGroup();

		_layoutSEOSiteLocalService.updateLayoutSEOSite(
			TestPropsValues.getUserId(), group.getGroupId(), false,
			Collections.emptyMap(), RandomTestUtil.randomLong(),
			ServiceContextTestUtil.getServiceContext());

		Assert.assertNotNull(
			_layoutSEOSiteLocalService.fetchLayoutSEOSiteByGroupId(
				group.getGroupId()));

		_groupLocalService.deleteGroup(group);

		Assert.assertNull(
			_layoutSEOSiteLocalService.fetchLayoutSEOSiteByGroupId(
				group.getGroupId()));
	}

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private LayoutSEOSiteLocalService _layoutSEOSiteLocalService;

}