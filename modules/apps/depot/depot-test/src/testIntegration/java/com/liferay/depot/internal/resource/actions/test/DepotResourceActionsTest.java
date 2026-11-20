/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.internal.resource.actions.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.design.library.constants.DesignLibraryAdminPortletKeys;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Moral
 */
@RunWith(Arquillian.class)
public class DepotResourceActionsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	@TestInfo("LPD-116830")
	public void testGetPortletModelResourcesWithDesignLibraryAdminPortlet() {
		List<String> modelResources = _resourceActions.getPortletModelResources(
			DesignLibraryAdminPortletKeys.DESIGN_LIBRARY_ADMIN);

		Assert.assertTrue(
			modelResources.contains(DepotConstants.RESOURCE_NAME));
		Assert.assertTrue(modelResources.contains(DepotEntry.class.getName()));
	}

	@Test
	@TestInfo("LPD-116830")
	public void testGetPortletRootModelResourceWithDesignLibraryAdminPortlet() {
		Assert.assertEquals(
			DepotConstants.RESOURCE_NAME,
			_resourceActions.getPortletRootModelResource(
				DesignLibraryAdminPortletKeys.DESIGN_LIBRARY_ADMIN));
	}

	@Inject
	private ResourceActions _resourceActions;

}