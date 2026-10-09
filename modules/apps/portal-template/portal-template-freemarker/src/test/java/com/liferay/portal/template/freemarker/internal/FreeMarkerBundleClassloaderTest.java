/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.template.freemarker.internal;

import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class FreeMarkerBundleClassloaderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testLoadClass() throws Exception {
		List<String> alphaClassNames = new ArrayList<>();
		List<String> betaClassNames = new ArrayList<>();

		FreeMarkerBundleClassloader freeMarkerBundleClassloader =
			new FreeMarkerBundleClassloader(
				SetUtil.fromArray(
					_createClassLoader("com.liferay.alpha.", alphaClassNames),
					_createClassLoader("com.liferay.beta.", betaClassNames)));

		freeMarkerBundleClassloader.loadClass("com.liferay.alpha.Alpha1");
		freeMarkerBundleClassloader.loadClass("com.liferay.beta.Beta1");

		alphaClassNames.clear();
		betaClassNames.clear();

		freeMarkerBundleClassloader.loadClass("com.liferay.alpha.Alpha2");
		freeMarkerBundleClassloader.loadClass("com.liferay.beta.Beta2");

		Assert.assertEquals(
			Collections.singletonList("com.liferay.alpha.Alpha2"),
			alphaClassNames);
		Assert.assertEquals(
			Collections.singletonList("com.liferay.beta.Beta2"),
			betaClassNames);
	}

	private ClassLoader _createClassLoader(
		String packageName, List<String> classNames) {

		return new ClassLoader() {

			@Override
			public Class<?> loadClass(String name)
				throws ClassNotFoundException {

				classNames.add(name);

				if (name.startsWith(packageName)) {
					return Object.class;
				}

				throw new ClassNotFoundException(name);
			}

		};
	}

}