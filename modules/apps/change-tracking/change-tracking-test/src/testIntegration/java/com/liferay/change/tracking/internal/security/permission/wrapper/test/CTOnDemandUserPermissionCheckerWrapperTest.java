/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.security.permission.wrapper.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.on.demand.user.ticket.generator.CTOnDemandUserTicketGenerator;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.permission.LayoutPermission;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class CTOnDemandUserPermissionCheckerWrapperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testHasPermission() throws Exception {
		CTCollection ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), null);

		ctCollection.setShareable(true);

		ctCollection = _ctCollectionLocalService.updateCTCollection(
			ctCollection);

		Ticket ticket = _ctOnDemandUserTicketGenerator.generate(
			ctCollection.getCtCollectionId());

		PermissionChecker permissionChecker = _permissionCheckerFactory.create(
			_userLocalService.getUser(Long.valueOf(ticket.getExtraInfo())));

		Layout layout = LayoutTestUtil.addTypeContentLayout(
			GroupTestUtil.addGroup(), true, false);

		Assert.assertFalse(
			_layoutPermission.contains(
				permissionChecker, layout, ActionKeys.DELETE));
		Assert.assertFalse(
			_layoutPermission.contains(
				permissionChecker, layout, ActionKeys.UPDATE));
		Assert.assertFalse(
			_layoutPermission.contains(
				permissionChecker, layout, ActionKeys.VIEW));

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection.getCtCollectionId())) {

			layout = _layoutLocalService.updatePriority(
				layout, RandomTestUtil.randomInt());
		}

		Assert.assertFalse(
			_layoutPermission.contains(
				permissionChecker, layout, ActionKeys.DELETE));
		Assert.assertFalse(
			_layoutPermission.contains(
				permissionChecker, layout, ActionKeys.UPDATE));
		Assert.assertTrue(
			_layoutPermission.contains(
				permissionChecker, layout, ActionKeys.VIEW));

		_layoutLocalService.deleteLayout(layout);
	}

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private CTOnDemandUserTicketGenerator _ctOnDemandUserTicketGenerator;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPermission _layoutPermission;

	@Inject
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Inject
	private UserLocalService _userLocalService;

}