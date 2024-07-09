/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.exportimport;

import com.liferay.exportimport.portlet.preferences.processor.Capability;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Gustavo Lima
 */
public abstract class BaseExportImportPortletPreferencesProcessorTestCase {

	@Test
	public void testExportImportPortletPreferencesProcessorCapabilities() {
		ExportImportPortletPreferencesProcessor
			exportImportPortletPreferencesProcessor =
				getExportImportPortletPreferencesProcessor();

		Assert.assertNotNull(exportImportPortletPreferencesProcessor);

		List<Capability> exportCapabilities =
			exportImportPortletPreferencesProcessor.getExportCapabilities();

		Assert.assertFalse(exportCapabilities.isEmpty());

		List<Capability> importCapabilities =
			exportImportPortletPreferencesProcessor.getImportCapabilities();

		Assert.assertFalse(importCapabilities.isEmpty());
	}

	protected abstract ExportImportPortletPreferencesProcessor
		getExportImportPortletPreferencesProcessor();

}