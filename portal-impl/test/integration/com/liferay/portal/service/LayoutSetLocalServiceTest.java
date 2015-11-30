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

package com.liferay.portal.service;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.sites.util.SitesUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
* @author Jonathan McCann
*/
public class LayoutSetLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Test
	public void testDeleteOrganizationSite() throws Exception {
		User user = TestPropsValues.getUser();

		_organization = OrganizationLocalServiceUtil.addOrganization(
			user.getUserId(),
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			RandomTestUtil.randomString(), true);

		Group group = _organization.getGroup();

		List<LayoutSetPrototype> layoutSetPrototypes =
			LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypes(
				PortalUtil.getDefaultCompanyId());

		LayoutSetPrototype layoutSetPrototype = layoutSetPrototypes.get(0);

		SitesUtil.updateLayoutSetPrototypesLinks(
			group, layoutSetPrototype.getLayoutSetPrototypeId(),
			layoutSetPrototype.getLayoutSetPrototypeId(), true, true);

		group = _organization.getGroup();

		Assert.assertTrue(group.isSite());

		GroupLocalServiceUtil.deleteGroup(group);

		LayoutSet privateLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			_organization.getGroupId(), true);

		Assert.assertEquals(
			StringPool.BLANK, privateLayoutSet.getLayoutSetPrototypeUuid());
		Assert.assertFalse(privateLayoutSet.getLayoutSetPrototypeLinkEnabled());
		Assert.assertEquals(0, _organization.getPrivateLayoutsPageCount());

		LayoutSet publicLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			_organization.getGroupId(), false);

		Assert.assertEquals(
			StringPool.BLANK, publicLayoutSet.getLayoutSetPrototypeUuid());
		Assert.assertFalse(publicLayoutSet.getLayoutSetPrototypeLinkEnabled());
		Assert.assertEquals(0, _organization.getPublicLayoutsPageCount());
	}

	@DeleteAfterTestRun
	private Organization _organization;

}