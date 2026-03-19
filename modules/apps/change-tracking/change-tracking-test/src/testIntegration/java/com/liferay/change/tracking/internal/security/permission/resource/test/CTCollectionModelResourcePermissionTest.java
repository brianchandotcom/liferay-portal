/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.security.permission.resource.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.on.demand.user.ticket.generator.CTOnDemandUserTicketGenerator;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.portal.kernel.exception.NoSuchResourcePermissionException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class CTCollectionModelResourcePermissionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testCheck() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		CTCollection ctCollection = _ctCollectionLocalService.addCTCollection(
			null, company.getCompanyId(), company.getUserId(), 0,
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		ctCollection.setShareable(true);

		ctCollection = _ctCollectionLocalService.updateCTCollection(
			ctCollection);

		_ctOnDemandUserTicketGenerator.generate(
			ctCollection.getCtCollectionId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser());

		_testCheck(permissionChecker, ctCollection, ActionKeys.DELETE);
		_testCheck(permissionChecker, ctCollection, ActionKeys.UPDATE);
		_testCheck(permissionChecker, ctCollection, ActionKeys.VIEW);
	}

	private void _testCheck(
			PermissionChecker permissionChecker, CTCollection ctCollection,
			String action)
		throws Exception {

		try {
			_ctCollectionModelResourcePermission.check(
				permissionChecker, ctCollection, action);

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertTrue(
				exception.getCause() instanceof
					NoSuchResourcePermissionException);
		}
	}

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	@Inject(
		filter = "model.class.name=com.liferay.change.tracking.model.CTCollection"
	)
	private ModelResourcePermission<CTCollection>
		_ctCollectionModelResourcePermission;

	@Inject
	private CTOnDemandUserTicketGenerator _ctOnDemandUserTicketGenerator;

}