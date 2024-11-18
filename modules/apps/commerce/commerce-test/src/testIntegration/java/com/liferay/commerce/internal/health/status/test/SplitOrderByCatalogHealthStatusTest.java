/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.health.status.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.constants.CommerceHealthStatusConstants;
import com.liferay.commerce.health.status.CommerceHealthStatus;
import com.liferay.commerce.health.status.CommerceHealthStatusRegistry;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import javax.servlet.http.HttpServletRequest;

import org.frutilla.FrutillaRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Crescenzo Rega
 */
@RunWith(Arquillian.class)
public class SplitOrderByCatalogHealthStatusTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testFixIssue() throws Exception {
		LocaleThreadLocal.setSiteDefaultLocale(
			LocaleUtil.fromLanguageId("fi_FI"));

		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		httpServletRequest.setAttribute(
			WebKeys.COMPANY_ID, PortalUtil.getDefaultCompanyId());
		httpServletRequest.setAttribute(
			WebKeys.CURRENT_URL, "http://localhost:8080");
		httpServletRequest.setAttribute(
			WebKeys.USER, UserTestUtil.addOmniadminUser());

		CommerceHealthStatus splitOrderByCatalogHealthStatus =
			_commerceHealthStatusRegistry.getCommerceHealthStatus(
				CommerceHealthStatusConstants.
					SPLIT_COMMERCE_ORDER_BY_CATALOG_HEALTH_STATUS_KEY);

		splitOrderByCatalogHealthStatus.fixIssue(httpServletRequest);

		ObjectDefinition commerceOrderObjectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinitionByClassName(
				PortalUtil.getDefaultCompanyId(),
				CommerceOrder.class.getName());

		Assert.assertTrue(
			ListUtil.exists(
				_objectActionLocalService.getObjectActions(
					commerceOrderObjectDefinition.getObjectDefinitionId()),
				objectAction -> StringUtil.equals(
					objectAction.getName(), "SplitOrderByCatalog")));
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	@Inject
	private CommerceHealthStatusRegistry _commerceHealthStatusRegistry;

	@Inject
	private ObjectActionLocalService _objectActionLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}