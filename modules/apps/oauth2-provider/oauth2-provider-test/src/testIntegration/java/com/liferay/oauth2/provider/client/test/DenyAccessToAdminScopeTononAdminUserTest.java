/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.client.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ScopeGrantLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.BundleActivator;

/**
 * @author Jorge García Jiménez
 */
@RunWith(Arquillian.class)
public class DenyAccessToAdminScopeTononAdminUserTest
	extends BaseClientTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void test() {
		WebTarget webTarget = getWebTarget();

		webTarget = webTarget.path(
			"o/headless-admin-workflow/v1.0/workflow-definitions");

		String tokenString = getToken(
			"oauthTestApplicationAdmin", null,
			this::getClientCredentialsResponse, this::parseTokenString);

		Invocation.Builder invocationBuilder = authorize(
			webTarget.request(), tokenString);

		Response response = invocationBuilder.get();

		Assert.assertEquals(200, response.getStatus());

		tokenString = getToken("oauthTestApplicationNonAdmin");

		invocationBuilder = authorize(webTarget.request(), tokenString);

		response = invocationBuilder.get();

		Assert.assertEquals(400, response.getStatus());
	}

	public static class AdminScopeTestPreparatorBundleActivator
		extends BaseTestPreparatorBundleActivator {

		@Override
		protected void prepareTest() throws Exception {
			long companyId = PortalUtil.getDefaultCompanyId();

			OAuth2Application oauth2AdminApp = createOAuth2Application(
				companyId, UserTestUtil.getAdminUser(companyId),
				"oauthTestApplicationAdmin",
				Collections.singletonList(
					"Liferay.Headless.Admin.Workflow.everything"));

			_oAuth2ScopeGrantLocalService.createOAuth2ScopeGrant(
				oauth2AdminApp.getCompanyId(),
				oauth2AdminApp.getOAuth2ApplicationScopeAliasesId(),
				"Liferay.Headless.Admin.Workflow",
				"com.liferay.headless.admin.workflow.impl", "GET",
				Collections.singletonList(
					"Liferay.Headless.Admin.Workflow.everything"));

			OAuth2Application oauth2RegularUserApp = createOAuth2Application(
				companyId, UserTestUtil.addUser(),
				"oauthTestApplicationNonAdmin",
				Collections.singletonList(
					"Liferay.Headless.Admin.Workflow.everything"));

			_oAuth2ScopeGrantLocalService.createOAuth2ScopeGrant(
				oauth2RegularUserApp.getCompanyId(),
				oauth2RegularUserApp.getOAuth2ApplicationScopeAliasesId(),
				"Liferay.Headless.Admin.Workflow",
				"com.liferay.headless.admin.workflow.impl", "GET",
				Collections.singletonList(
					"Liferay.Headless.Admin.Workflow.everything"));
		}

	}

	@Override
	protected BundleActivator getBundleActivator() {
		return new AdminScopeTestPreparatorBundleActivator();
	}

	@Inject
	private static OAuth2ScopeGrantLocalService _oAuth2ScopeGrantLocalService;

}