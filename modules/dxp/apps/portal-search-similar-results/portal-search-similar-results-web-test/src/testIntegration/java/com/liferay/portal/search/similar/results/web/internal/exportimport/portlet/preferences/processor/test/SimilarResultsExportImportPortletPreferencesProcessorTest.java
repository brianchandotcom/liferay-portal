/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.similar.results.web.internal.exportimport.portlet.preferences.processor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.portlet.preferences.processor.Capability;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.search.similar.results.web.internal.constants.SimilarResultsPortletKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gustavo Lima
 */
@RunWith(Arquillian.class)
public class SimilarResultsExportImportPortletPreferencesProcessorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testExportImportPortletPreferencesProcessorCapabilities() {
		Assert.assertNotNull(_exportImportPortletPreferencesProcessor);

		List<Capability> exportCapabilities =
			_exportImportPortletPreferencesProcessor.getExportCapabilities();
		List<Capability> importCapabilities =
			_exportImportPortletPreferencesProcessor.getImportCapabilities();

		Assert.assertFalse(exportCapabilities.isEmpty());

		Assert.assertFalse(importCapabilities.isEmpty());
	}

	@Inject(
		filter = "javax.portlet.name=" + SimilarResultsPortletKeys.SIMILAR_RESULTS
	)
	private ExportImportPortletPreferencesProcessor
		_exportImportPortletPreferencesProcessor;

}