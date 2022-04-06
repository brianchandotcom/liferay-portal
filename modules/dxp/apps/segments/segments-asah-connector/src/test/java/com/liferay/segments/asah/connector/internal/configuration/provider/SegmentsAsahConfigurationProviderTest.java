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

package com.liferay.segments.asah.connector.internal.configuration.provider;

import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.segments.asah.connector.internal.configuration.SegmentsAsahCompanyConfiguration;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Cristina González
 */
public class SegmentsAsahConfigurationProviderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_segmentsAsahConfigurationProvider.activate(Collections.emptyMap());

		_configurationAdmin = Mockito.mock(ConfigurationAdmin.class);

		ReflectionTestUtil.setFieldValue(
			_segmentsAsahConfigurationProvider, "_configurationAdmin",
			_configurationAdmin);

		_configurationProvider = Mockito.mock(ConfigurationProvider.class);

		ReflectionTestUtil.setFieldValue(
			_segmentsAsahConfigurationProvider, "_configurationProvider",
			_configurationProvider);
	}

	@Test
	public void testGetAnonymousUserSegmentsCacheExpirationTimeWithCompanyConfiguration()
		throws Exception {

		Mockito.when(
			_configurationAdmin.listConfigurations(Mockito.anyString())
		).thenReturn(
			new Configuration[0]
		);

		long companyId = RandomTestUtil.randomLong();

		Mockito.when(
			_configurationProvider.getCompanyConfiguration(
				SegmentsAsahCompanyConfiguration.class, companyId)
		).thenReturn(
			new SegmentsAsahCompanyConfiguration() {

				@Override
				public int anonymousUserSegmentsCacheExpirationTime() {
					return 100;
				}

				@Override
				public int interestTermsCacheExpirationTime() {
					return 0;
				}

			}
		);

		Assert.assertEquals(
			100L,
			_segmentsAsahConfigurationProvider.
				getAnonymousUserSegmentsCacheExpirationTime(companyId));
	}

	@Test
	public void testGetAnonymousUserSegmentsCacheExpirationTimeWithoutCompanyConfiguration()
		throws Exception {

		Mockito.when(
			_configurationAdmin.listConfigurations(Mockito.anyString())
		).thenReturn(
			null
		);

		Assert.assertEquals(
			86400L,
			_segmentsAsahConfigurationProvider.
				getAnonymousUserSegmentsCacheExpirationTime(
					-RandomTestUtil.randomLong()));
	}

	@Test
	public void testGetInterestTermsCacheExpirationTimeWithCompanyConfiguration()
		throws Exception {

		Mockito.when(
			_configurationAdmin.listConfigurations(Mockito.anyString())
		).thenReturn(
			new Configuration[0]
		);

		long companyId = RandomTestUtil.randomLong();

		Mockito.when(
			_configurationProvider.getCompanyConfiguration(
				SegmentsAsahCompanyConfiguration.class, companyId)
		).thenReturn(
			new SegmentsAsahCompanyConfiguration() {

				@Override
				public int anonymousUserSegmentsCacheExpirationTime() {
					return 0;
				}

				@Override
				public int interestTermsCacheExpirationTime() {
					return 100;
				}

			}
		);

		Assert.assertEquals(
			100L,
			_segmentsAsahConfigurationProvider.
				getInterestTermsCacheExpirationTime(companyId));
	}

	@Test
	public void testGetInterestTermsCacheExpirationTimeWithoutCompanyConfiguration()
		throws Exception {

		Mockito.when(
			_configurationAdmin.listConfigurations(Mockito.anyString())
		).thenReturn(
			null
		);

		Assert.assertEquals(
			86400L,
			_segmentsAsahConfigurationProvider.
				getInterestTermsCacheExpirationTime(
					-RandomTestUtil.randomLong()));
	}

	private ConfigurationAdmin _configurationAdmin;
	private ConfigurationProvider _configurationProvider;
	private final SegmentsAsahConfigurationProvider
		_segmentsAsahConfigurationProvider =
			new SegmentsAsahConfigurationProvider();

}