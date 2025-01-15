/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.unit.BatchEngineUnitProcessor;
import com.liferay.batch.engine.unit.BatchEngineUnitReader;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.File;

import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Daniel Sanz
 */
@FeatureFlags({"LPD-34594", "LPS-164563"})
@RunWith(Arquillian.class)
public class DataSetObjectDefinitionsImportTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_triggerObjectDefinitionsBatchImport();
	}

	@Test
	public void testImportObjectDefinitionsWithNondefaultLocale()
		throws Exception {

		_assertDataSetObjectDefinitionLabel("Data Set");

		ObjectDefinition dataSetObjectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_DATA_SET", TestPropsValues.getCompanyId());

		dataSetObjectDefinition.setLabel("Foo", LocaleUtil.SPAIN);

		_objectDefinitionLocalService.updateObjectDefinition(
			dataSetObjectDefinition);

		_assertDataSetObjectDefinitionLabel("Foo");

		Set<Locale> availableLocales = LanguageUtil.getAvailableLocales();
		Locale defaultLocale = LocaleUtil.getDefault();

		try {
			CompanyTestUtil.resetCompanyLocales(
				TestPropsValues.getCompanyId(),
				SetUtil.fromArray(LocaleUtil.SPAIN), LocaleUtil.SPAIN);

			_triggerObjectDefinitionsBatchImport();

			_assertDataSetObjectDefinitionLabel("Data Set");
		}
		finally {
			CompanyTestUtil.resetCompanyLocales(
				TestPropsValues.getCompanyId(), availableLocales,
				defaultLocale);
		}
	}

	private void _assertDataSetObjectDefinitionLabel(String label)
		throws Exception {

		ObjectDefinition dataSetObjectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_DATA_SET", TestPropsValues.getCompanyId());

		Assert.assertNotNull(dataSetObjectDefinition);

		Assert.assertEquals(
			label, dataSetObjectDefinition.getLabel(LocaleUtil.SPAIN));
	}

	private void _triggerObjectDefinitionsBatchImport() {
		Bundle testBundle = FrameworkUtil.getBundle(
			DataSetObjectDefinitionsImportTest.class);

		BundleContext bundleContext = testBundle.getBundleContext();

		for (Bundle bundle : bundleContext.getBundles()) {
			if (Objects.equals(
					bundle.getSymbolicName(),
					"com.liferay.frontend.data.set.impl")) {

				File processedFile = bundle.getDataFile(
					".com.liferay.frontend.data.set.internal.batch.01.object." +
						"definition.batch.engine.data.json.0.processed");

				if ((processedFile != null) && processedFile.exists()) {
					processedFile.delete();
				}

				CompletableFuture<Void> completableFuture =
					_batchEngineUnitProcessor.processBatchEngineUnits(
						_batchEngineUnitReader.getBatchEngineUnits(bundle));

				completableFuture.join();
			}
		}
	}

	@Inject
	private BatchEngineUnitProcessor _batchEngineUnitProcessor;

	@Inject
	private BatchEngineUnitReader _batchEngineUnitReader;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}