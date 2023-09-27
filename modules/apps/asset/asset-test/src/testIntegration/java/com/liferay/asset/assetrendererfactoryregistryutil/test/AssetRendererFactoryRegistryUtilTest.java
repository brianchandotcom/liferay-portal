/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.assetrendererfactoryregistryutil.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.test.util.asset.renderer.factory.TestAssetRendererFactory;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.ListUtil;
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

		List<String> targetClassNames = ListUtil.filter(
			TransformUtil.transform(
				AssetRendererFactoryRegistryUtil.getAssetRendererFactories(1),
				AssetRendererFactory::getClassName),
			className::equals);

		Assert.assertEquals(
			targetClassNames.toString(), 1, targetClassNames.size());
	}

	@Test
	public void testGetAssetRendererFactoryByClassName() {
		String className = TestAssetRendererFactory.class.getName();

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		Assert.assertEquals(className, assetRendererFactory.getClassName());
	}

	@Test
	public void testGetAssetRendererFactoryByClassNameId() {
		PortalImpl portalImpl = new PortalImpl();

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(
					portalImpl.getClassNameId(TestAssetRendererFactory.class));

		Assert.assertEquals(
			TestAssetRendererFactory.class.getName(),
			assetRendererFactory.getClassName());
	}

	@Test
	public void testGetAssetRendererFactoryByType() {
		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByType(
				TestAssetRendererFactory.class.getName());

		Assert.assertEquals(
			TestAssetRendererFactory.class.getName(),
			assetRendererFactory.getClassName());
	}

	@Test
	public void testGetClassNameIds1() {
		List<Long> classNameIdsList = ListUtil.fromArray(
			AssetRendererFactoryRegistryUtil.getClassNameIds(1));

		Assert.assertTrue(
			classNameIdsList.toString(),
			classNameIdsList.contains(Long.valueOf(1234567890)));
	}

	@Test
	public void testGetClassNameIds2() {
		List<Long> classNameIdsList = ListUtil.fromArray(
			AssetRendererFactoryRegistryUtil.getClassNameIds(1, true));

		Assert.assertTrue(
			classNameIdsList.toString(),
			classNameIdsList.contains(Long.valueOf(1234567890)));
	}

	@Test
	public void testGetClassNameIds3() {
		List<Long> classNameIdsList = ListUtil.fromArray(
			AssetRendererFactoryRegistryUtil.getClassNameIds(1, false));

		Assert.assertTrue(
			classNameIdsList.toString(),
			classNameIdsList.contains(Long.valueOf(1234567890)));
	}

}