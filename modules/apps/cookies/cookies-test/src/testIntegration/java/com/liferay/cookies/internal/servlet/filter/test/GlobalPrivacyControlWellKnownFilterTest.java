/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.internal.servlet.filter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import jakarta.servlet.http.HttpServletResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Christian Borges de Moura
 */
@RunWith(Arquillian.class)
public class GlobalPrivacyControlWellKnownFilterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testProcessFilter() throws Exception {
		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		String urlString = StringBundler.concat(
			Http.HTTP_WITH_SLASH, company.getVirtualHostname(), ":",
			PortalUtil.getPortalServerPort(false), "/.well-known/gpc.json");

		HttpResponse<String> getHttpResponse = _send(urlString, "GET");

		Assert.assertEquals(
			HttpServletResponse.SC_OK, getHttpResponse.statusCode());
		Assert.assertNotEquals(StringPool.BLANK, getHttpResponse.body());

		HttpResponse<String> headHttpResponse = _send(urlString, "HEAD");

		Assert.assertEquals(
			HttpServletResponse.SC_OK, headHttpResponse.statusCode());
		Assert.assertEquals(StringPool.BLANK, headHttpResponse.body());

		HttpHeaders getHttpHeaders = getHttpResponse.headers();

		_assertHeader(
			getHttpHeaders.firstValue(
				"Cache-Control"
			).get(),
			"Cache-Control", headHttpResponse.headers());
		_assertHeader(
			getHttpHeaders.firstValue(
				"Content-Type"
			).get(),
			"Content-Type", headHttpResponse.headers());

		HttpResponse<String> postHttpResponse = _send(urlString, "POST");

		Assert.assertEquals(
			HttpServletResponse.SC_METHOD_NOT_ALLOWED,
			postHttpResponse.statusCode());

		_assertHeader("GET, HEAD", "Allow", postHttpResponse.headers());
	}

	private void _assertHeader(
		String expectedHeaderValue, String headerName,
		HttpHeaders httpHeaders) {

		Map<String, List<String>> map = httpHeaders.map();

		List<String> headerValues = map.get(headerName);

		Assert.assertEquals(expectedHeaderValue, headerValues.get(0));
	}

	private HttpResponse<String> _send(String urlString, String method)
		throws Exception {

		return _httpClient.send(
			HttpRequest.newBuilder(
			).uri(
				URI.create(urlString)
			).method(
				method, HttpRequest.BodyPublishers.noBody()
			).build(),
			HttpResponse.BodyHandlers.ofString());
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	private final HttpClient _httpClient = HttpClient.newBuilder(
	).followRedirects(
		HttpClient.Redirect.NEVER
	).build();

}