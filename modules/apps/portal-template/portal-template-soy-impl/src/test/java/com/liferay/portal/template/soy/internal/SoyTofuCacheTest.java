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

import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.template.soy.SoyTemplateResource;

import java.util.Arrays;

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
		SoyTemplateResource soyTemplateResource =
			_soyTestHelper.getSoyTemplateResource(
				Arrays.asList("simple.soy", "context.soy"));

		_soyTofuCacheHandler.add(
			soyTemplateResource.getTemplateId(),
			_soyTestHelper.getSoyFileSet(
				soyTemplateResource.getTemplateResources()),
			ProxyFactory.newDummyInstance(SoyTofu.class));

		Assert.assertNotNull(
			_soyTofuCacheHandler.get(soyTemplateResource.getTemplateId()));
	}

	@Test
	public void testCacheMiss() throws Exception {
		SoyTemplateResource soyTemplateResource =
			_soyTestHelper.getSoyTemplateResource(
				Arrays.asList("simple.soy", "context.soy"));

		_soyTofuCacheHandler.add(
			soyTemplateResource.getTemplateId(),
			_soyTestHelper.getSoyFileSet(
				soyTemplateResource.getTemplateResources()),
			ProxyFactory.newDummyInstance(SoyTofu.class));

		soyTemplateResource = _soyTestHelper.getSoyTemplateResource(
			Arrays.asList("context.soy"));

		Assert.assertNull(
			_soyTofuCacheHandler.get(soyTemplateResource.getTemplateId()));
	}

	@Test
	public void testRemoveAny() throws Exception {
		SoyTemplateResource cachedSoyTemplateResource =
			_soyTestHelper.getSoyTemplateResource(
				Arrays.asList(
					"simple.soy", "context.soy", "multi-context.soy"));

		_soyTofuCacheHandler.add(
			cachedSoyTemplateResource.getTemplateId(),
			_soyTestHelper.getSoyFileSet(
				cachedSoyTemplateResource.getTemplateResources()),
			ProxyFactory.newDummyInstance(SoyTofu.class));

		SoyTemplateResource soyTemplateResource =
			_soyTestHelper.getSoyTemplateResource(Arrays.asList("context.soy"));

		_soyTofuCacheHandler.removeIfAny(
			soyTemplateResource.getTemplateResources());

		Assert.assertNull(
			_soyTofuCacheHandler.get(soyTemplateResource.getTemplateId()));
	}

	@Test
	public void testRemoveAnyWithMultipleEntries() throws Exception {
		SoyTemplateResource cachedSoyTemplateResourceA =
			_soyTestHelper.getSoyTemplateResource(Arrays.asList("simple.soy"));

		_soyTofuCacheHandler.add(
			cachedSoyTemplateResourceA.getTemplateId(),
			_soyTestHelper.getSoyFileSet(
				cachedSoyTemplateResourceA.getTemplateResources()),
			ProxyFactory.newDummyInstance(SoyTofu.class));

		Assert.assertNotNull(
			_soyTofuCacheHandler.get(
				cachedSoyTemplateResourceA.getTemplateId()));

		SoyTemplateResource cachedSoyTemplateResourceB =
			_soyTestHelper.getSoyTemplateResource(
				Arrays.asList("context.soy", "multi-context.soy"));

		_soyTofuCacheHandler.add(
			cachedSoyTemplateResourceB.getTemplateId(),
			_soyTestHelper.getSoyFileSet(
				cachedSoyTemplateResourceA.getTemplateResources()),
			ProxyFactory.newDummyInstance(SoyTofu.class));

		SoyTemplateResource soyTemplateResource =
			_soyTestHelper.getSoyTemplateResource(Arrays.asList("context.soy"));

		_soyTofuCacheHandler.removeIfAny(
			soyTemplateResource.getTemplateResources());

		Assert.assertNull(
			_soyTofuCacheHandler.get(
				cachedSoyTemplateResourceB.getTemplateId()));
		Assert.assertNotNull(
			_soyTofuCacheHandler.get(
				cachedSoyTemplateResourceA.getTemplateId()));
	}

	private final SoyTestHelper _soyTestHelper = new SoyTestHelper();
	private SoyTofuCacheHandler _soyTofuCacheHandler;

}