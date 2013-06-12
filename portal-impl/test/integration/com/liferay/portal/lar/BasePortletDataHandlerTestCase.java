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
import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portlet.PortletPreferencesImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

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
	public void testPrepareManifestSummary() throws Exception {
		initExport();

		addStagedModels();

		portletDataContext.setEndDate(getEndDate());

		portletDataHandler.prepareManifestSummary(portletDataContext);

		ManifestSummary manifestSummary =
			portletDataContext.getManifestSummary();

		Map<String, LongWrapper> modelAdditionCounters =
			manifestSummary.getModelAdditionCounters();

		Map<String, LongWrapper> expectedModelAdditionCounters =
			new HashMap<String, LongWrapper>(modelAdditionCounters);

		modelAdditionCounters.clear();

		portletDataHandler.exportData(
			portletDataContext, portletId, new PortletPreferencesImpl());

		checkManifestSummary(expectedModelAdditionCounters);
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

	protected void checkManifestSummary(
		Map<String, LongWrapper> expectedModelAdditionCounters) {

		ManifestSummary manifestSummary =
			portletDataContext.getManifestSummary();

		Map<String, LongWrapper> modelAdditionCounters =
			manifestSummary.getModelAdditionCounters();

		int expectedModelAdditionCountersSize =
			expectedModelAdditionCounters.size();

		for (String manifestSummaryKey :
				expectedModelAdditionCounters.keySet()) {

			LongWrapper expectedModelAdditionCounter =
				expectedModelAdditionCounters.get(manifestSummaryKey);

			if (expectedModelAdditionCounter.getValue() == 0) {
				expectedModelAdditionCountersSize--;
			}
			else {
				LongWrapper modelAdditionCounter = modelAdditionCounters.get(
					manifestSummaryKey);

				Assert.assertEquals(
					expectedModelAdditionCounter.getValue(),
					modelAdditionCounter.getValue());
			}
		}

		Assert.assertEquals(
			modelAdditionCounters.size(), expectedModelAdditionCountersSize);
	}

	protected abstract PortletDataHandler createPortletDataHandler();

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

		addParameters(parameterMap);

		zipWriter = ZipWriterFactoryUtil.getZipWriter();

		portletDataContext =
			PortletDataContextFactoryUtil.createExportPortletDataContext(
				stagingGroup.getCompanyId(), stagingGroup.getGroupId(),
				parameterMap, getStartDate(), getEndDate(), zipWriter);

		rootElement = SAXReaderUtil.createElement("root");

		portletDataContext.setExportDataRootElement(rootElement);

		missingReferencesElement = SAXReaderUtil.createElement(
			"missing-references");

		portletDataContext.setMissingReferencesElement(
			missingReferencesElement);
	}

	protected Element missingReferencesElement;
	protected PortletDataContext portletDataContext;
	protected PortletDataHandler portletDataHandler;
	protected String portletId;
	protected Element rootElement;
	protected Group stagingGroup;
	protected ZipWriter zipWriter;

}