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

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth2.provider.internal.test.TestAnnotatedApplication;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.BundleActivator;

/**
 * @author Carlos Sierra Andrés
 */
@Ignore
@RunWith(Arquillian.class)
public class IsolationAcrossCompaniesTest extends BaseClientTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAnnotated() throws Exception {
		WebTarget webTarget = getWebTarget("/annotated");

		String tokenString = getToken("oauthTestApplication", "host1.xyz");

		Invocation.Builder builder = authorize(
			webTarget.request(), tokenString);

		builder = builder.header("Host", "host1.xyz");

		Assert.assertEquals("everything.read", builder.get(String.class));

		builder = authorize(webTarget.request(), tokenString);

		builder = builder.header("Host", "host2.xyz");

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"portal_web.docroot.errors.code_jsp", LoggerTestUtil.WARN)) {

			Response response = builder.get();

			Assert.assertEquals(403, response.getStatus());
		}
	}

	@Test
	public void testNoScopes() throws Exception {
		WebTarget webTarget = getWebTarget("/no-scopes");

		String tokenString = getToken("oauthTestApplication", "host1.xyz");

		Invocation.Builder builder = authorize(
			webTarget.request(), tokenString);

		builder = builder.header("Host", "host1.xyz");

		Assert.assertEquals("everything.read", builder.get(String.class));

		builder = authorize(webTarget.request(), tokenString);

		builder = builder.header("Host", "host2.xyz");

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"portal_web.docroot.errors.code_jsp", LoggerTestUtil.WARN)) {

			Response response = builder.get();

			Assert.assertEquals(403, response.getStatus());
		}
	}

	public static class IsolationAccrossCompaniesTestPreparatorBundleActivator
		extends BaseTestPreparatorBundleActivator {

		@Override
		protected void prepareTest() throws Exception {
			registerJaxRsApplication(
				new TestAnnotatedApplication(), "annotated",
				HashMapDictionaryBuilder.<String, Object>put(
					"oauth2.scope.checker.type", "annotations"
				).build());

			registerJaxRsApplication(
				new TestAnnotatedApplication(), "no-scopes",
				HashMapDictionaryBuilder.<String, Object>put(
					"oauth2.scope.checker.type", "none"
				).build());

			Company company1 = createCompany("host1");

			createOAuth2Application(
				company1.getCompanyId(),
				UserTestUtil.getAdminUser(company1.getCompanyId()),
				"oauthTestApplication");

			Company company2 = createCompany("host2");

			createOAuth2Application(
				company2.getCompanyId(),
				UserTestUtil.getAdminUser(company2.getCompanyId()),
				"oauthTestApplication");
		}

	}

	@Override
	protected BundleActivator getBundleActivator() {
		return new IsolationAccrossCompaniesTestPreparatorBundleActivator();
	}

}