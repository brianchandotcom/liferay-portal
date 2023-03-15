/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.web.internal.controller.main;

import com.liferay.osb.faro.provisioning.client.constants.ProductConstants;
import com.liferay.osb.faro.provisioning.client.model.OSBAccountEntry;
import com.liferay.osb.faro.provisioning.client.model.OSBOfferingEntry;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Marcos Martins
 */
public class ProjectControllerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testCreateOSBAccountEntry() throws Exception {
		OSBAccountEntry osbAccountEntry =
			_projectController.createOSBAccountEntry(false);

		List<OSBOfferingEntry> offeringEntries =
			osbAccountEntry.getOfferingEntries();

		Assert.assertEquals(
			offeringEntries.toString(), 1, offeringEntries.size());

		OSBOfferingEntry osbOfferingEntry = offeringEntries.get(0);

		Assert.assertEquals(
			ProductConstants.ENTERPRISE_PRODUCT_ENTRY_ID,
			osbOfferingEntry.getProductEntryId());
		Assert.assertEquals(1, osbOfferingEntry.getQuantity());
		Assert.assertNotNull(osbOfferingEntry.getStartDate());
	}

	private final ProjectController _projectController =
		new ProjectController();

}