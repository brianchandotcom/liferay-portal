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

package com.liferay.info.display.contributor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayField;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Yang Cao
 */
@RunWith(Arquillian.class)
public class InfoDisplayContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@After
	public void tearDown() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	@Test
	public void testRegisterInfoDisplayContributorAsInfoItemFormProvider() {
		_registerInfoDisplayContributor();

		InfoItemFormProvider<TestInfoDisplayContributor> infoItemFormProvider =
			(InfoItemFormProvider<TestInfoDisplayContributor>)
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemFormProvider.class,
					TestInfoDisplayContributor.class.getName());

		Assert.assertNotNull(infoItemFormProvider);
	}

	private void _registerInfoDisplayContributor() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceRegistration = registry.registerService(
			(Class<InfoDisplayContributor<?>>)
				(Class<?>)InfoDisplayContributor.class,
			new TestInfoDisplayContributor());
	}

	@Inject
	private InfoItemServiceTracker _infoItemServiceTracker;

	private ServiceRegistration<InfoDisplayContributor<?>> _serviceRegistration;

	private static class TestInfoDisplayContributor
		implements InfoDisplayContributor<Object> {

		public TestInfoDisplayContributor() {
		}

		@Override
		public String getClassName() {
			return TestInfoDisplayContributor.class.getName();
		}

		@Override
		public Set<InfoDisplayField> getInfoDisplayFields(
			long classTypeId, Locale locale) {

			return null;
		}

		@Override
		public Map<String, Object> getInfoDisplayFieldsValues(
			Object object, Locale locale) {

			return null;
		}

		@Override
		public InfoDisplayObjectProvider<Object> getInfoDisplayObjectProvider(
			long classPK) {

			return null;
		}

		@Override
		public InfoDisplayObjectProvider<Object> getInfoDisplayObjectProvider(
			long groupId, String urlTitle) {

			return null;
		}

		@Override
		public String getInfoURLSeparator() {
			return RandomTestUtil.randomString();
		}

	}

}