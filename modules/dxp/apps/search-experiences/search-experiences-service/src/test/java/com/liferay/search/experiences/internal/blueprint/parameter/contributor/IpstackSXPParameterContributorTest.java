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

package com.liferay.search.experiences.internal.blueprint.parameter.contributor;

import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.webcache.WebCachePool;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.internal.configuration.IpstackConfiguration;

import java.beans.ExceptionListener;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Petteri Karttunen
 * @author Wade Cao
 */
public class IpstackSXPParameterContributorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		ConfigurationProvider configurationProvider = Mockito.mock(
			ConfigurationProvider.class);

		_ipstackSXPParameterContributor = new IpstackSXPParameterContributor(
			configurationProvider);

		ReflectionTestUtil.setFieldValue(
			_ipstackSXPParameterContributor, "_configurationProvider",
			configurationProvider);

		Mockito.when(
			configurationProvider.getCompanyConfiguration(
				Mockito.any(), Mockito.anyLong())
		).thenReturn(
			_ipstackConfiguration
		);
	}

	@Test
	public void testContributorWithBlankIPAddress() throws Exception {
		_setUpIPStackConfiguration(true);
		_setUpSearchContext("");

		_ipstackSXPParameterContributor.contribute(
			Mockito.mock(ExceptionListener.class), _searchContext, null,
			_sxpParameters);

		Mockito.verify(
			_ipstackConfiguration, Mockito.times(1)
		).enabled();

		Mockito.verify(
			_sxpParameters, Mockito.never()
		).add(
			Mockito.any()
		);
	}

	@Test
	public void testContributorWithIPAddressException() throws Exception {
		_setUpIPStackConfiguration(true);
		_setUpSearchContext("127.0.0.1");

		RuntimeException runtimeException = new RuntimeException();

		_ipstackSXPParameterContributor.contribute(
			runtimeException::addSuppressed, _searchContext, null,
			_sxpParameters);

		Throwable[] throwables = runtimeException.getSuppressed();

		Assert.assertEquals(Arrays.toString(throwables), 1, throwables.length);

		Mockito.verify(
			_searchContext, Mockito.times(1)
		).getAttribute(
			Mockito.anyString()
		);
		Mockito.verify(
			_sxpParameters, Mockito.never()
		).add(
			Mockito.any()
		);
	}

	@Test
	public void testContributorWithIpstackConfigurationDisabled()
		throws Exception {

		_setUpIPStackConfiguration(false);

		_ipstackSXPParameterContributor.contribute(
			Mockito.mock(ExceptionListener.class), _searchContext, null,
			_sxpParameters);

		Mockito.verify(
			_searchContext, Mockito.never()
		).getAttribute(
			Mockito.anyString()
		);
	}

	@Test
	public void testContributorWithValidIPAddress() throws Exception {
		_setUpIPStackConfiguration(true);
		_setUpSearchContext("www.liferay.com");

		WebCachePool webCachePool = Mockito.mock(WebCachePool.class);

		WebCachePoolUtil webCachePoolUtil = new WebCachePoolUtil();

		webCachePoolUtil.setWebCachePool(webCachePool);

		Mockito.when(
			webCachePool.get(Mockito.anyString(), Mockito.any())
		).thenReturn(
			JSONUtil.put("city", "Diamond Bar")
		);

		Set<SXPParameter> sxpParameters = new HashSet<>();

		_ipstackSXPParameterContributor.contribute(
			Mockito.mock(ExceptionListener.class), _searchContext, null,
			sxpParameters);

		Assert.assertEquals(
			Arrays.toString(sxpParameters.toArray()), 10, sxpParameters.size());
	}

	private void _setUpIPStackConfiguration(boolean enabled) {
		Mockito.when(
			_ipstackConfiguration.enabled()
		).thenReturn(
			enabled
		);
	}

	private void _setUpSearchContext(String value) {
		Mockito.when(
			_searchContext.getAttribute(Mockito.anyString())
		).thenReturn(
			value
		);
	}

	private final IpstackConfiguration _ipstackConfiguration = Mockito.mock(
		IpstackConfiguration.class);
	private IpstackSXPParameterContributor _ipstackSXPParameterContributor;
	private final SearchContext _searchContext = Mockito.mock(
		SearchContext.class);
	private final Set<SXPParameter> _sxpParameters = Mockito.mock(Set.class);

}