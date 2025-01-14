/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.web.internal.type.deployer;

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.client.extension.type.JSImportMapsEntryCET;
import com.liferay.frontend.js.importmaps.extender.JSImportMapsContributor;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Objects;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.framework.BundleContext;

/**
 * @author Iván Zaera Avellón
 */
public class CETDeployerImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testDeployJSImportMapsEntryCET() {
		CETDeployerImpl cetDeployerImpl = new CETDeployerImpl();

		BundleContext bundleContext = Mockito.spy(BundleContext.class);

		cetDeployerImpl.activate(bundleContext);

		ReflectionTestUtil.setFieldValue(
			cetDeployerImpl, "_jsonFactory", new JSONFactoryImpl());

		JSImportMapsEntryCET jsImportMapsEntryCET = Mockito.mock(
			JSImportMapsEntryCET.class);

		long companyId = RandomTestUtil.randomLong();

		Mockito.when(
			jsImportMapsEntryCET.getCompanyId()
		).thenReturn(
			companyId
		);

		Mockito.when(
			jsImportMapsEntryCET.getType()
		).thenReturn(
			ClientExtensionEntryConstants.TYPE_JS_IMPORT_MAPS_ENTRY
		);

		cetDeployerImpl.deploy(jsImportMapsEntryCET);

		Mockito.verify(
			bundleContext
		).registerService(
			Mockito.eq(JSImportMapsContributor.class),
			Mockito.isA(JSImportMapsContributor.class),
			Mockito.argThat(
				dictionary -> Objects.equals(
					dictionary.get(
						"com.liferay.frontend.js.importmaps.company.id"),
					companyId))
		);
	}

}