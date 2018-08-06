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

package com.liferay.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.LayoutFriendlyURLComposite;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.portlet.FriendlyURLResolverRegistryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portlet.bundle.friendlyurlresolverregistryutil.TestFriendlyURLResolver;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Raymond Augé
 */
public class FriendlyURLResolverRegistryUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new SyntheticBundleRule("bundle.friendlyurlresolverregistryutil"));

	@Test
	public void testGetFriendlyURLResolver() throws Exception {
		Collection<FriendlyURLResolver> collection =
			FriendlyURLResolverRegistryUtil.
				getFriendlyURLResolversAsCollection();

		Assert.assertFalse(collection.toString(), collection.isEmpty());

		FriendlyURLResolver friendlyURLResolver =
			FriendlyURLResolverRegistryUtil.getFriendlyURLResolver(
				TestFriendlyURLResolver.SEPARATOR);

		Assert.assertNotNull(friendlyURLResolver);

		Class<?> clazz = friendlyURLResolver.getClass();

		Assert.assertEquals(
			TestFriendlyURLResolver.class.getName(), clazz.getName());
	}

	@Test
	public void testOverride() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<>();

		properties.put("service.ranking", 25);

		FriendlyURLResolver friendlyURLResolver1 =
			new OverrideFriendlyURLResolver();

		ServiceRegistration<FriendlyURLResolver> serviceRegistration1 =
			registry.registerService(
				FriendlyURLResolver.class, friendlyURLResolver1, properties);

		ServiceRegistration<FriendlyURLResolver> serviceRegistration2 = null;

		try {
			Collection<FriendlyURLResolver> collection =
				FriendlyURLResolverRegistryUtil.
					getFriendlyURLResolversAsCollection();

			Assert.assertFalse(collection.toString(), collection.isEmpty());

			FriendlyURLResolver friendlyURLResolver =
				FriendlyURLResolverRegistryUtil.getFriendlyURLResolver(
					TestFriendlyURLResolver.SEPARATOR);

			Assert.assertNotNull(friendlyURLResolver);

			Assert.assertEquals(friendlyURLResolver1, friendlyURLResolver);

			FriendlyURLResolver friendlyURLResolver2 =
				new OverrideFriendlyURLResolver();

			properties = new HashMap<>();

			properties.put("service.ranking", 12);

			serviceRegistration2 = registry.registerService(
				FriendlyURLResolver.class, friendlyURLResolver2, properties);

			friendlyURLResolver =
				FriendlyURLResolverRegistryUtil.getFriendlyURLResolver(
					TestFriendlyURLResolver.SEPARATOR);

			Assert.assertNotNull(friendlyURLResolver);

			Assert.assertEquals(
				"Should still be 1 since it is higher rankged.",
				friendlyURLResolver1, friendlyURLResolver);
		}
		finally {
			serviceRegistration1.unregister();

			if (serviceRegistration2 != null) {
				serviceRegistration2.unregister();
			}

			FriendlyURLResolver friendlyURLResolver =
				FriendlyURLResolverRegistryUtil.getFriendlyURLResolver(
					TestFriendlyURLResolver.SEPARATOR);

			Assert.assertNotNull(friendlyURLResolver);

			Class<?> clazz = friendlyURLResolver.getClass();

			Assert.assertEquals(
				TestFriendlyURLResolver.class.getName(), clazz.getName());
		}
	}

	private class OverrideFriendlyURLResolver implements FriendlyURLResolver {

		@Override
		public String getActualURL(
				long companyId, long groupId, boolean privateLayout,
				String mainPath, String friendlyURL,
				Map<String, String[]> params,
				Map<String, Object> requestContext)
			throws PortalException {

			return null;
		}

		@Override
		public LayoutFriendlyURLComposite getLayoutFriendlyURLComposite(
				long companyId, long groupId, boolean privateLayout,
				String friendlyURL, Map<String, String[]> params,
				Map<String, Object> requestContext)
			throws PortalException {

			return null;
		}

		@Override
		public String getURLSeparator() {
			return TestFriendlyURLResolver.SEPARATOR;
		}

	}

}