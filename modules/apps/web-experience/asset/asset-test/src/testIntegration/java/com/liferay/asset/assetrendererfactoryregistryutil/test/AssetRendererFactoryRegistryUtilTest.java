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

package com.liferay.asset.assetrendererfactoryregistryutil.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortalImpl;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Peter Fellwock
 */
@RunWith(Arquillian.class)
public class AssetRendererFactoryRegistryUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetAssetRendererFactories() {
		String className = TestAssetRendererFactory.class.getName();

		List<AssetRendererFactory<?>> assetRendererFactories =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactories(1);

		for (AssetRendererFactory<?> assetRendererFactory :
				assetRendererFactories) {

			Class<?> clazz = assetRendererFactory.getClass();

			if (className.equals(clazz.getName())) {
				Assert.assertTrue(true);

				return;
			}
		}

		Assert.fail();
	}

	@Test
	public void testGetAssetRendererFactoryByClassName() {
		String className = TestAssetRendererFactory.class.getName();

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		Class<?> clazz = assetRendererFactory.getClass();

		Assert.assertEquals(className, clazz.getName());
	}

	@Test
	public void testGetAssetRendererFactoryByClassNameId() {
		PortalImpl portalImpl = new PortalImpl();

		long classNameId = portalImpl.getClassNameId(
			TestAssetRendererFactory.class);

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(classNameId);

		Class<?> clazz = assetRendererFactory.getClass();

		Assert.assertEquals(
			TestAssetRendererFactory.class.getName(), clazz.getName());
	}

	@Test
	public void testGetAssetRendererFactoryByType() {
		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByType(
				TestAssetRendererFactory.class.getName());

		Class<?> clazz = assetRendererFactory.getClass();

		Assert.assertEquals(
			TestAssetRendererFactory.class.getName(), clazz.getName());
	}

	@Test
	public void testGetClassNameIds1() {
		long[] classNameIds = AssetRendererFactoryRegistryUtil.getClassNameIds(
			1);

		for (long classNameId : classNameIds) {
			if (classNameId == 1234567890) {
				Assert.assertTrue(true);

				return;
			}
		}

		Assert.fail();
	}

	@Test
	public void testGetClassNameIds2() {
		long[] classNameIds = AssetRendererFactoryRegistryUtil.getClassNameIds(
			1, true);

		for (long classNameId : classNameIds) {
			if (classNameId == 1234567890) {
				Assert.assertTrue(true);

				return;
			}
		}

		Assert.fail();
	}

	@Test
	public void testGetClassNameIds3() {
		long[] classNameIds = AssetRendererFactoryRegistryUtil.getClassNameIds(
			1, false);

		for (long classNameId : classNameIds) {
			if (classNameId == 1234567890) {
				Assert.assertTrue(true);

				return;
			}
		}

		Assert.fail();
	}

}