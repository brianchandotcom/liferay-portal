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

package com.liferay.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleConstants;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleEvent;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleEventListenerRegistryUtil;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleListener;
import com.liferay.exportimport.test.util.constants.DummyFolderPortletKeys;
import com.liferay.exportimport.test.util.exportimport.data.handler.DummyFolderWithMissingDummyPortletDataHandler;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.File;
import java.io.Serializable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;

/**
 * @author Akos Thurzo
 */
@RunWith(Arquillian.class)
public class ExportedMissingReferenceBackwardCompatbilityExportImportTest
	extends ExportedMissingReferenceExportImportTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_removeAttributeFromLARExportImportLifecycleListener =
			new RemoveAttributeFromLARExportImportLifecycleListener();

		ExportImportLifecycleEventListenerRegistryUtil.register(
			_removeAttributeFromLARExportImportLifecycleListener);
	}

	@After
	@Override
	public void tearDown() throws Exception {
		ExportImportLifecycleEventListenerRegistryUtil.unregister(
			_removeAttributeFromLARExportImportLifecycleListener);

		super.tearDown();
	}

	@Test
	public void testBackwardCompatibility() throws Exception {
		List<PortletDataHandler> portletDataHandlers = setPortletDataHandler(
			DummyFolderPortletKeys.DUMMY_FOLDER_WITH_MISSING_REFERENCE,
			DummyFolderWithMissingDummyPortletDataHandler.class);

		long[] layoutIds = {layout.getLayoutId()};

		try {
			exportImportLayouts(layoutIds, getExportParameterMap());
		}
		catch (PortletDataException pde) {
			Throwable cause = pde.getCause();

			if (!(cause instanceof NullPointerException)) {
				throw pde;
			}

			StackTraceElement[] stackTrace = cause.getStackTrace();

			if (Objects.equals(
					stackTrace[0].getClassName(),
					StagedModelDataHandlerUtil.class.getName()) &&
				Objects.equals(
					stackTrace[0].getMethodName(),
					"doImportReferenceStagedModel")) {

				throw pde;
			}
		}

		setPortletDataHandler(
			DummyFolderPortletKeys.DUMMY_FOLDER_WITH_MISSING_REFERENCE,
			portletDataHandlers);
	}

	@Rule
	public final TestRule skipParentTestsRule =
		(statement, description) -> new Statement() {

			@Override
			public void evaluate() throws Throwable {
				Stream<Method> methodStream = _parentTestMethods.stream();

				Assume.assumeTrue(
					methodStream.noneMatch(
						m -> Objects.equals(
							m.getName(), description.getMethodName())));

				statement.evaluate();
			}

		};

	public class RemoveAttributeFromLARExportImportLifecycleListener
		implements ExportImportLifecycleListener {

		@Override
		public boolean isParallel() {
			return false;
		}

		@Override
		public void onExportImportLifecycleEvent(
				ExportImportLifecycleEvent exportImportLifecycleEvent)
			throws Exception {

			if (((exportImportLifecycleEvent.getCode() !=
					ExportImportLifecycleConstants.
						EVENT_PORTLET_EXPORT_SUCCEEDED) ||
				 (exportImportLifecycleEvent.getProcessFlag() !=
					 ExportImportLifecycleConstants.
						 PROCESS_FLAG_PORTLET_EXPORT_IN_PROCESS)) &&
				(exportImportLifecycleEvent.getCode() !=
					ExportImportLifecycleConstants.
						EVENT_LAYOUT_EXPORT_SUCCEEDED)) {

				return;
			}

			List<Serializable> attributes =
				exportImportLifecycleEvent.getAttributes();

			PortletDataContext portletDataContext =
				(PortletDataContext)attributes.get(0);

			ZipWriter zipWriter = portletDataContext.getZipWriter();

			File larFile = zipWriter.getFile();

			removeAttributeFromLAR(larFile);
		}

		protected void removeAttributeFromLAR(File larFile) throws Exception {
			String larFileName = larFile.getName();
			String larFilePath = larFile.getPath();

			int lastIndexOfPeriod = larFileName.lastIndexOf(CharPool.PERIOD);

			File file = new File(
				StringBundler.concat(
					FileUtil.getPath(larFilePath), File.separator,
					larFileName.substring(0, lastIndexOfPeriod), "-original",
					larFileName.substring(lastIndexOfPeriod)));

			FileUtil.move(larFile, file);

			ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(file);

			ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter(
				new File(larFilePath));

			List<String> entries = zipReader.getEntries();

			Stream<String> entriesStream = entries.stream();

			entriesStream.forEach(
				zipEntry -> {
					try {
						if (zipEntry.equals("manifest.xml")) {
							Document document = SAXReaderUtil.read(
								zipReader.getEntryAsInputStream(zipEntry));

							Element rootElement = document.getRootElement();

							List<Element> missingReferencesElements =
								rootElement.elements("missing-references");

							Element missingReferencesElement =
								missingReferencesElements.get(0);

							List<Element> missingReferenceElements =
								missingReferencesElement.elements(
									"missing-reference");

							for (Element missingReferenceElement :
									missingReferenceElements) {

								Attribute elementPathAttribute =
									missingReferenceElement.attribute(
										"element-path");

								if (elementPathAttribute != null) {
									missingReferencesElement.remove(
										missingReferenceElement);
								}
							}

							zipWriter.addEntry(
								zipEntry, document.formattedString());
						}
						else {
							zipWriter.addEntry(
								zipEntry,
								zipReader.getEntryAsInputStream(zipEntry));
						}
					}
					catch (Exception e) {
						throw new RuntimeException(e);
					}
				});

			zipWriter.finish();

			FileUtil.delete(file);
		}

	}

	protected static List<Method> getMethodsAnnotatedWith(
		Class<?> clazz, Class<? extends Annotation> annotation) {

		List<Method> methods = new ArrayList<>();

		Class<?> currentClazz = clazz;

		while (currentClazz != Object.class) {
			for (Method method : currentClazz.getDeclaredMethods()) {
				if (method.isAnnotationPresent(annotation)) {
					methods.add(method);
				}
			}

			currentClazz = currentClazz.getSuperclass();
		}

		return methods;
	}

	private final List<Method> _parentTestMethods = getMethodsAnnotatedWith(
		getClass().getSuperclass(), Test.class);
	private RemoveAttributeFromLARExportImportLifecycleListener
		_removeAttributeFromLARExportImportLifecycleListener;

}