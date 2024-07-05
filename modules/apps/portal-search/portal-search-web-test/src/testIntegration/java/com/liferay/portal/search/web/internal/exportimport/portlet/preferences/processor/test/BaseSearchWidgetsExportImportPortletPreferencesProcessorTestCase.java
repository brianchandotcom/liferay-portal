/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.exportimport.portlet.preferences.processor.test;

import com.liferay.exportimport.portlet.preferences.processor.Capability;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Gustavo Lima
 */
public abstract class
	BaseSearchWidgetsExportImportPortletPreferencesProcessorTestCase {

	@Before
	public void setUp() {
		_exportImportPortletPreferencesProcessor =
			getExportImportPortletPreferencesProcessor();
	}

	@Test
	public void testExportImportPortletPreferencesProcessorCapabilities() {
		Assert.assertNotNull(_exportImportPortletPreferencesProcessor);

		List<Capability> exportCapabilities =
			_exportImportPortletPreferencesProcessor.getExportCapabilities();

		Assert.assertFalse(exportCapabilities.isEmpty());

		List<Capability> importCapabilities =
			_exportImportPortletPreferencesProcessor.getImportCapabilities();

		Assert.assertFalse(importCapabilities.isEmpty());
	}

	protected abstract ExportImportPortletPreferencesProcessor
		getExportImportPortletPreferencesProcessor();

	private ExportImportPortletPreferencesProcessor
		_exportImportPortletPreferencesProcessor;

}