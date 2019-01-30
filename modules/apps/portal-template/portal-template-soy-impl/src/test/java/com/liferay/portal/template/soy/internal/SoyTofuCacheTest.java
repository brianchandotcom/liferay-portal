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

package com.liferay.portal.template.soy.internal;

import com.google.template.soy.tofu.SoyTofu;

import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.util.ProxyFactory;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Bruno Basto
 */
public class SoyTofuCacheTest {

	@Before
	public void setUp() throws Exception {
		_soyTestHelper.setUp();

		_soyTofuCacheHandler = new SoyTofuCacheHandler(
			_soyTestHelper.mockPortalCache());
	}

	@After
	public void tearDown() {
		_soyTestHelper.tearDown();
	}

	@Test
	public void testCacheHit() throws Exception {
		List<TemplateResource> templateResources =
			_soyTestHelper.getTemplateResources(
				Arrays.asList("simple.soy", "context.soy"));

		_soyTofuCacheHandler.add(
			templateResources, _soyTestHelper.getSoyFileSet(templateResources),
			ProxyFactory.newDummyInstance(SoyTofu.class));

		Assert.assertNotNull(_soyTofuCacheHandler.get(templateResources));
	}

	@Test
	public void testCacheMiss() throws Exception {
		List<TemplateResource> templateResources =
			_soyTestHelper.getTemplateResources(
				Arrays.asList("simple.soy", "context.soy"));

		_soyTofuCacheHandler.add(
			templateResources, _soyTestHelper.getSoyFileSet(templateResources),
			ProxyFactory.newDummyInstance(SoyTofu.class));

		Assert.assertNull(
			_soyTofuCacheHandler.get(
				_soyTestHelper.getTemplateResources(
					Arrays.asList("context.soy"))));
	}

	@Test
	public void testRemoveAny() throws Exception {
		List<TemplateResource> cachedTemplateResources =
			_soyTestHelper.getTemplateResources(
				Arrays.asList(
					"simple.soy", "context.soy", "multi-context.soy"));

		_soyTofuCacheHandler.add(
			cachedTemplateResources,
			_soyTestHelper.getSoyFileSet(cachedTemplateResources),
			ProxyFactory.newDummyInstance(SoyTofu.class));

		List<TemplateResource> templateResources =
			_soyTestHelper.getTemplateResources(Arrays.asList("context.soy"));

		_soyTofuCacheHandler.removeIfAny(templateResources);

		Assert.assertNull(_soyTofuCacheHandler.get(templateResources));
	}

	@Test
	public void testRemoveAnyWithMultipleEntries() throws Exception {
		List<TemplateResource> cachedTemplateResourcesA =
			_soyTestHelper.getTemplateResources(Arrays.asList("simple.soy"));

		_soyTofuCacheHandler.add(
			cachedTemplateResourcesA,
			_soyTestHelper.getSoyFileSet(cachedTemplateResourcesA),
			ProxyFactory.newDummyInstance(SoyTofu.class));

		Assert.assertNotNull(
			_soyTofuCacheHandler.get(cachedTemplateResourcesA));

		List<TemplateResource> cachedTemplateResourcesB =
			_soyTestHelper.getTemplateResources(
				Arrays.asList("context.soy", "multi-context.soy"));

		_soyTofuCacheHandler.add(
			cachedTemplateResourcesB,
			_soyTestHelper.getSoyFileSet(cachedTemplateResourcesA),
			ProxyFactory.newDummyInstance(SoyTofu.class));

		_soyTofuCacheHandler.removeIfAny(
			_soyTestHelper.getTemplateResources(Arrays.asList("context.soy")));

		Assert.assertNull(_soyTofuCacheHandler.get(cachedTemplateResourcesB));
		Assert.assertNotNull(
			_soyTofuCacheHandler.get(cachedTemplateResourcesA));
	}

	private final SoyTestHelper _soyTestHelper = new SoyTestHelper();
	private SoyTofuCacheHandler _soyTofuCacheHandler;

}