/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.ElementHandler;
import com.liferay.portal.kernel.xml.ElementProcessor;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portlet.PortletPreferencesImpl;

import java.io.StringReader;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.xerces.parsers.SAXParser;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

import org.xml.sax.InputSource;

/**
 * @author Zsolt Berentey
 */
public abstract class BasePortletDataHandlerTestCase extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();

		stagingGroup = GroupTestUtil.addGroup();

		portletDataHandler = createPortletDataHandler();
		portletId = getPortletId();
	}

	@After
	public void tearDown() throws Exception {
		GroupLocalServiceUtil.deleteGroup(stagingGroup);
	}

	@Test
	@Transactional
	public void testDeletions() throws Exception {
		initExport();

		addStagedModels();

		portletDataContext.setEndDate(getEndDate());

		portletDataHandler.prepareManifestSummary(portletDataContext);

		ManifestSummary manifestSummary =
			portletDataContext.getManifestSummary();

		Map<String, LongWrapper> modelAdditionCounters =
			manifestSummary.getModelAdditionCounters();

		Map<String, LongWrapper> initialModelDeletionCounters =
			new HashMap<String, LongWrapper>(modelAdditionCounters);

		removeUnsupportedDeletionSystemEventStagedModelTypes(
			initialModelDeletionCounters);

		modelAdditionCounters.clear();

		portletDataHandler.exportData(
			portletDataContext, portletId, new PortletPreferencesImpl());

		deleteStagedModels();

		portletDataContext.setEndDate(getEndDate());

		portletDataHandler.prepareManifestSummary(portletDataContext);

		Map<String, LongWrapper> modelDeletionCounters =
			manifestSummary.getModelDeletionCounters();

		removeUnsupportedDeletionSystemEventStagedModelTypes(
			modelDeletionCounters);

		checkCounters(initialModelDeletionCounters, modelDeletionCounters);

		modelDeletionCounters.clear();

		DeletionSystemEventExporter deletionSystemEventExporter =
			new DeletionSystemEventExporter();

		deletionSystemEventExporter.exportDeletionSystemEvents(
			portletDataContext);

		checkDeletions(initialModelDeletionCounters);
	}

	@Test
	@Transactional
	public void testPrepareManifestSummary() throws Exception {
		initExport();

		addStagedModels();

		portletDataContext.setEndDate(getEndDate());

		portletDataHandler.prepareManifestSummary(portletDataContext);

		ManifestSummary manifestSummary =
			portletDataContext.getManifestSummary();

		Map<String, LongWrapper> modelAdditionCounters =
			manifestSummary.getModelAdditionCounters();

		Map<String, LongWrapper> initialModelAdditionCounters =
			new HashMap<String, LongWrapper>(modelAdditionCounters);

		modelAdditionCounters.clear();

		portletDataHandler.exportData(
			portletDataContext, portletId, new PortletPreferencesImpl());

		Set<String> classNames = initialModelAdditionCounters.keySet();

		Iterator<String> iterator = classNames.iterator();

		while (iterator.hasNext()) {
			String className = iterator.next();

			LongWrapper counter = initialModelAdditionCounters.get(className);

			if (counter.getValue() == 0) {
				iterator.remove();
			}
		}

		checkCounters(
			manifestSummary.getModelAdditionCounters(),
			initialModelAdditionCounters);
	}

	protected void addBooleanParameter(
		Map<String, String[]> parameterMap, String namespace, String name,
		boolean value) {

		PortletDataHandlerBoolean portletDataHandlerBoolean =
			new PortletDataHandlerBoolean(namespace, name);

		parameterMap.put(
			portletDataHandlerBoolean.getNamespacedControlName(),
			new String[] {String.valueOf(value)});
	}

	protected void addParameters(Map<String, String[]> parameterMap) {
	}

	protected abstract void addStagedModels() throws Exception;

	protected void checkCounters(
		Map<String, LongWrapper> expectedCounters,
		Map<String, LongWrapper> actualCounters) {

		for (String className : expectedCounters.keySet()) {
			LongWrapper expectedCounter = expectedCounters.get(className);
			LongWrapper actualCounter = actualCounters.get(className);

			Assert.assertEquals(
				className, expectedCounter.getValue(),
				actualCounter.getValue());
		}

		Assert.assertEquals(expectedCounters.size(), actualCounters.size());
	}

	protected void checkDeletions(
			Map<String, LongWrapper> expectedModelDeletionCounters)
		throws Exception {

		final Map<String, LongWrapper> actualModelDeletionCounters =
			new HashMap<String, LongWrapper>();

		SAXParser saxParser = new SAXParser();

		ElementHandler elementHandler = new ElementHandler(
			new ElementProcessor() {

				@Override
				public void processElement(Element element) {
					StagedModelType stagedModelType =
						new StagedModelType(
							element.attributeValue("class-name"),
							element.attributeValue("referrer-class-name"));

					LongWrapper counter = actualModelDeletionCounters.get(
						stagedModelType.toString());

					if (counter != null) {
						counter.increment();
					}
					else {
						actualModelDeletionCounters.put(
							stagedModelType.toString(), new LongWrapper(1));
					}
				}

			},
			new String[] {"deletion-system-event"});

		saxParser.setContentHandler(elementHandler);

		ZipReader zipReader = (ZipReader)portletDataContext.getZipWriter();

		String deletionSystemEventsXML = zipReader.getEntryAsString(
			ExportImportPathUtil.getRootPath(portletDataContext) +
				"/deletion-system-events.xml");

		Assert.assertNotNull(deletionSystemEventsXML);

		saxParser.parse(
			new InputSource(new StringReader(deletionSystemEventsXML)));

		ManifestSummary manifestSummary =
			portletDataContext.getManifestSummary();

		Set<String> classNames = expectedModelDeletionCounters.keySet();

		Iterator<String> iterator = classNames.iterator();

		while (iterator.hasNext()) {
			String className = iterator.next();

			LongWrapper counter = expectedModelDeletionCounters.get(className);

			if (counter.getValue() == 0) {
				iterator.remove();
			}
		}

		checkCounters(
			manifestSummary.getModelDeletionCounters(),
			actualModelDeletionCounters);
		checkCounters(
			expectedModelDeletionCounters, actualModelDeletionCounters);
	}

	protected abstract PortletDataHandler createPortletDataHandler();

	protected void deleteStagedModels() throws Exception {
		portletDataHandler.deleteData(portletDataContext, portletId, null);
	}

	protected Date getEndDate() {
		return new Date();
	}

	protected abstract String getPortletId();

	protected Date getStartDate() {
		return new Date(System.currentTimeMillis() - Time.HOUR);
	}

	protected void initExport() throws Exception {
		Map<String, String[]> parameterMap =
			new LinkedHashMap<String, String[]>();

		parameterMap.put(
			PortletDataHandlerKeys.DELETIONS,
			new String[] {Boolean.TRUE.toString()});

		addParameters(parameterMap);

		TestReaderWriter testReaderWriter = new TestReaderWriter();

		portletDataContext =
			PortletDataContextFactoryUtil.createExportPortletDataContext(
				stagingGroup.getCompanyId(), stagingGroup.getGroupId(),
				parameterMap, getStartDate(), getEndDate(), testReaderWriter);

		rootElement = SAXReaderUtil.createElement("root");

		portletDataContext.setExportDataRootElement(rootElement);

		missingReferencesElement = SAXReaderUtil.createElement(
			"missing-references");

		portletDataContext.setMissingReferencesElement(
			missingReferencesElement);
	}

	protected void removeUnsupportedDeletionSystemEventStagedModelTypes(
		Map<String, LongWrapper> expectedModelDeletionCounters) {

		Set<String> stagedModelTypes = new HashSet<String>();

		for (Object deletionSystemEventStagedModelType :
				portletDataHandler.getDeletionSystemEventStagedModelTypes()) {

			if (deletionSystemEventStagedModelType instanceof Class) {
				Class<?> clazz = (Class<?>)deletionSystemEventStagedModelType;

				stagedModelTypes.add(clazz.getName());
			}
			else {
				stagedModelTypes.add(
					deletionSystemEventStagedModelType.toString());
			}
		}

		Set<Map.Entry<String, LongWrapper>> set =
			expectedModelDeletionCounters.entrySet();

		Iterator<Map.Entry<String, LongWrapper>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, LongWrapper> entry = iterator.next();

			if (!stagedModelTypes.contains(entry.getKey())) {
				iterator.remove();
			}
		}
	}

	protected Element missingReferencesElement;
	protected PortletDataContext portletDataContext;
	protected PortletDataHandler portletDataHandler;
	protected String portletId;
	protected Element rootElement;
	protected Group stagingGroup;

}