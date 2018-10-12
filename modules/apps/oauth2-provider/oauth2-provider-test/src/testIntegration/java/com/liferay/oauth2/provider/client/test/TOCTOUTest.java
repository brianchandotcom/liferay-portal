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

package com.liferay.oauth2.provider.client.test;

import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.model.OAuth2ApplicationScopeAliases;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.oauth2.provider.service.OAuth2ApplicationScopeAliasesLocalService;
import com.liferay.oauth2.provider.test.internal.TestRunnablePostHandlingApplication;
import com.liferay.oauth2.provider.test.internal.activator.BaseTestPreparatorBundleActivator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Collections;
import java.util.Dictionary;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.ServiceReference;

/**
 * @author Stian Sigvartsen
 */
@RunAsClient
@RunWith(Arquillian.class)
public class TOCTOUTest extends BaseClientTestCase {

	@Deployment
	public static Archive<?> getDeployment() throws Exception {
		return BaseClientTestCase.getArchive(
			SecurityTestPreparatorBundleActivator.class);
	}

	/**
	 * OAUTH2-101 / OAUTH2-102
	 */
	@Test
	public void testPreventTOCTOUWithNewScopes() {

		// Test plan
		// ------------

		// [1] Get a token (implicitly for "everything.read") and check success
		//     for pre-installed JAX-RS app 1

		// [2] Install JAX-RS app 2

		// [3] Fail to use the token from [1] on JAX-RS app 2
		//     (admin & end-user TOCTOU protection when API grows)

		// [4] Try again with a fresh narrowed down token for "everything.read".
		//     Should still fail (admin TOCTOU protection when narrowing down)

		// [5] Re-save the OAuth2 app scope assignment

		// [6] Fail to use the token from [4] on JAX-RS app 2
		//     (end-user TOCTOU protection when OAuth2 app scope assignment
		//     grows)

		// [7] Try again with a fresh token (implicitly for "everything.read").
		//     Should succeed

		// Test begins
		// -----------

		// [1]

		WebTarget webTarget1 = getWebTarget("/annotated");

		String token = getToken(
			"oauthTestApplicationCode", null,
			getAuthorizationCodeBiFunction("test@liferay.com", "test", null),
			this::parseTokenString);

		Invocation.Builder webTarget1InvocationBuilder = authorize(
			webTarget1.request(), token);

		Assert.assertEquals(
			"everything.read", webTarget1InvocationBuilder.get(String.class));

		// [2]

		webTarget1InvocationBuilder.post(null, String.class);

		// [3]

		WebTarget webTarget2 = getWebTarget("/annotated2");

		Invocation.Builder webTarget2InvocationBuilder = authorize(
			webTarget2.request(), token);

		try {
			webTarget2InvocationBuilder.get(String.class);
			Assert.fail(
				"Expected request GET /annotated2 to fail through admin & " +
					"end-user TOCTOU protection");
		}
		catch (ClientErrorException cee) {
			Assert.assertEquals(403, cee.getResponse().getStatus());
		}

		// [4]

		token = getToken(
			"oauthTestApplicationCode", null,
			getAuthorizationCodeBiFunction(
				"test@liferay.com", "test", null, "everything.read"),
			this::parseTokenString);

		webTarget2InvocationBuilder = authorize(webTarget2.request(), token);

		try {
			webTarget2InvocationBuilder.get(String.class);
			Assert.fail(
				"Expected request GET /annotated2 to fail through admin " +
					"TOCTOU protection");
		}
		catch (ClientErrorException cee) {
			Assert.assertEquals(403, cee.getResponse().getStatus());
		}

		// [5]

		webTarget2InvocationBuilder.post(null, String.class);

		// [6]

		try {
			webTarget2InvocationBuilder.get(String.class);
			Assert.fail(
				"Expected request GET /annotated2 to fail through end-user " +
					"TOCTOU protection");
		}
		catch (ClientErrorException cee) {
			Assert.assertEquals(403, cee.getResponse().getStatus());
		}

		// [7]

		token = getToken(
			"oauthTestApplicationCode", null,
			getAuthorizationCodeBiFunction("test@liferay.com", "test", null),
			this::parseTokenString);

		webTarget2InvocationBuilder = authorize(webTarget2.request(), token);

		Assert.assertEquals(
			"everything.read", webTarget2InvocationBuilder.get(String.class));
	}

	public static class SecurityTestPreparatorBundleActivator
		extends BaseTestPreparatorBundleActivator {

		public OAuth2Application updateOAuth2ApplicationScopeAliases(
				OAuth2Application oAuth2Application)
			throws PortalException {

			ServiceReference<OAuth2ApplicationLocalService>
				oA2ApplicationLSServiceReference =
					bundleContext.getServiceReference(
						OAuth2ApplicationLocalService.class);

			OAuth2ApplicationLocalService oAuth2ApplicationLS =
				bundleContext.getService(oA2ApplicationLSServiceReference);

			ServiceReference<OAuth2ApplicationScopeAliasesLocalService>
				oA2AScopeAliasesLSServiceReference =
					bundleContext.getServiceReference(
						OAuth2ApplicationScopeAliasesLocalService.class);

			OAuth2ApplicationScopeAliasesLocalService oA2AScopeAliasesLS =
				bundleContext.getService(oA2AScopeAliasesLSServiceReference);

			try {
				OAuth2ApplicationScopeAliases oAuth2ApplicationScopeAliases =
					oA2AScopeAliasesLS.getOAuth2ApplicationScopeAliases(
						oAuth2Application.getOAuth2ApplicationScopeAliasesId());

				oAuth2Application = oAuth2ApplicationLS.updateScopeAliases(
					oAuth2Application.getUserId(),
					oAuth2Application.getUserName(),
					oAuth2Application.getOAuth2ApplicationId(),
					oAuth2ApplicationScopeAliases.getScopeAliasesList());

				return oAuth2Application;
			}
			finally {
				bundleContext.ungetService(oA2ApplicationLSServiceReference);

				bundleContext.ungetService(oA2AScopeAliasesLSServiceReference);
			}
		}

		@Override
		protected void prepareTest() throws Exception {
			long defaultCompanyId = PortalUtil.getDefaultCompanyId();

			User user = UserTestUtil.getAdminUser(defaultCompanyId);

			OAuth2Application oAuth2Application = createOAuth2Application(
				defaultCompanyId, user, "oauthTestApplicationCode",
				Collections.singletonList(GrantType.AUTHORIZATION_CODE),
				Collections.singletonList("everything.read"));

			Application application = new TestRunnablePostHandlingApplication(
				() -> {
					try {
						updateOAuth2ApplicationScopeAliases(oAuth2Application);
					}
					catch (PortalException pe) {
						throw new RuntimeException(pe);
					}
				});

			Dictionary<String, Object> applicationProperties =
				new HashMapDictionary<>();

			applicationProperties.put(
				"oauth2.scopechecker.type", "annotations");

			registerJaxRsApplication(
				new TestRunnablePostHandlingApplication(
					() -> {
						registerJaxRsApplication(
							application, "annotated2", applicationProperties);
					}),
				"annotated", applicationProperties);

			updateOAuth2ApplicationScopeAliases(oAuth2Application);
		}

	}

}