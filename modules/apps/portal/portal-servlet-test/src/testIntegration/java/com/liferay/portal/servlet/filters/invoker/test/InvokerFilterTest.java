/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.servlet.filters.invoker.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.servlet.filters.BasePortalFilter;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Eric Yan
 */
@RunWith(Arquillian.class)
public class InvokerFilterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testOSGIComponentFilterInitParams() {
		BasePortalFilter testBasePortalFilter = new BasePortalFilter() {
		};

		String urlRegexIgnorePattern = "/test-regex-ignore-pattern";
		String urlRegexPattern = "/test-regex-pattern";

		Bundle bundle = FrameworkUtil.getBundle(InvokerFilterTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceRegistration<Filter> serviceRegistration =
			bundleContext.registerService(
				Filter.class, testBasePortalFilter,
				HashMapDictionaryBuilder.put(
					"init-param.url-regex-ignore-pattern", urlRegexIgnorePattern
				).put(
					"init-param.url-regex-pattern", urlRegexPattern
				).put(
					"servlet-context-name", StringPool.BLANK
				).put(
					"servlet-filter-name", "Invoker Filter - Test Filter"
				).build());

		try {
			FilterConfig filterConfig = testBasePortalFilter.getFilterConfig();

			Assert.assertEquals(
				urlRegexIgnorePattern,
				filterConfig.getInitParameter("url-regex-ignore-pattern"));
			Assert.assertEquals(
				urlRegexPattern,
				filterConfig.getInitParameter("url-regex-pattern"));
		}
		finally {
			serviceRegistration.unregister();
		}
	}

}