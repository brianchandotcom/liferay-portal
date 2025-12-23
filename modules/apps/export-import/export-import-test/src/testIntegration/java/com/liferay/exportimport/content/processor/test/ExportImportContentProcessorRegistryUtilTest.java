/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.content.processor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.content.processor.ExportImportContentProcessorRegistryUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Serializable;

import java.util.Dictionary;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Carlos Correa
 */
@RunWith(Arquillian.class)
public class ExportImportContentProcessorRegistryUtilTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		Bundle bundle = FrameworkUtil.getBundle(
			ExportImportContentProcessorRegistryUtilTest.class);

		_bundleContext = bundle.getBundleContext();
	}

	@Test
	public void testGetExportImportContentProcessorByContentProcessorType()
		throws Exception {

		String contentProcessorType = RandomTestUtil.randomString();

		Assert.assertNull(
			ExportImportContentProcessorRegistryUtil.
				getExportImportContentProcessorByContentProcessorType(
					contentProcessorType));

		try (AutoCloseable autoCloseable = _registerService(
				null, new TestExportImportContentProcessor())) {

			Assert.assertNull(
				ExportImportContentProcessorRegistryUtil.
					getExportImportContentProcessorByContentProcessorType(
						contentProcessorType));
		}

		TestExportImportContentProcessor exportImportContentProcessor =
			new TestExportImportContentProcessor();

		try (AutoCloseable autoCloseable = _registerService(
				HashMapDictionaryBuilder.put(
					"content.processor.type", contentProcessorType
				).build(),
				exportImportContentProcessor)) {

			Assert.assertSame(
				exportImportContentProcessor,
				ExportImportContentProcessorRegistryUtil.
					getExportImportContentProcessorByContentProcessorType(
						contentProcessorType));
		}
	}

	@Test
	public void testGetExportImportContentProcessorByModelClassName()
		throws Exception {

		String modelClassName = RandomTestUtil.randomString();

		Assert.assertNull(
			ExportImportContentProcessorRegistryUtil.
				getExportImportContentProcessor(modelClassName));

		try (AutoCloseable autoCloseable = _registerService(
				null, new TestExportImportContentProcessor())) {

			Assert.assertNull(
				ExportImportContentProcessorRegistryUtil.
					getExportImportContentProcessorByContentProcessorType(
						modelClassName));
		}

		TestExportImportContentProcessor exportImportContentProcessor =
			new TestExportImportContentProcessor();

		try (AutoCloseable autoCloseable = _registerService(
				HashMapDictionaryBuilder.put(
					"model.class.name", modelClassName
				).build(),
				exportImportContentProcessor)) {

			Assert.assertSame(
				exportImportContentProcessor,
				ExportImportContentProcessorRegistryUtil.
					getExportImportContentProcessor(modelClassName));
		}
	}

	private AutoCloseable _registerService(
		Dictionary<String, String> dictionary,
		ExportImportContentProcessor<Serializable>
			exportImportContentProcessor) {

		ServiceRegistration<ExportImportContentProcessor<Serializable>>
			serviceRegistration = _bundleContext.registerService(
				(Class<ExportImportContentProcessor<Serializable>>)
					(Class<?>)ExportImportContentProcessor.class,
				exportImportContentProcessor, dictionary);

		return serviceRegistration::unregister;
	}

	private static BundleContext _bundleContext;

	private class TestExportImportContentProcessor
		implements ExportImportContentProcessor<Serializable> {

		@Override
		public Serializable replaceExportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			Serializable content, boolean exportReferencedContent,
			boolean escapeContent) {

			return null;
		}

		@Override
		public Serializable replaceImportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			Serializable content) {

			return null;
		}

		@Override
		public void validateContentReferences(
			long groupId, Serializable content) {
		}

	}

}