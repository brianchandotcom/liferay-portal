/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.search.spi.model.result.contributor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mikel Lorza
 */
@RunWith(Arquillian.class)
public class ObjectEntryFolderModelSummaryContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetSummary() {
		ModelSummaryContributor modelSummaryContributor =
			_modelSearchConfigurator.getModelSummaryContributor();

		Document document = new DocumentImpl();

		document.addText("localized_label_en_US", "label");
		document.addText("localized_label_es_ES", "etiqueta");

		Summary summary = modelSummaryContributor.getSummary(
			document, LocaleUtil.SPAIN, "");

		Assert.assertEquals("etiqueta", summary.getTitle());

		summary = modelSummaryContributor.getSummary(
			document, LocaleUtil.FRANCE, "");

		Assert.assertEquals("label", summary.getTitle());

		document.addText("snippet_title", "Label");
		document.addText("title", "Label");

		summary = modelSummaryContributor.getSummary(
			document, LocaleUtil.US, "Label");

		Assert.assertEquals("Label", summary.getTitle());
	}

	@Inject(
		filter = "component.name=com.liferay.object.internal.search.ObjectEntryFolderModelSearchConfigurator"
	)
	private ModelSearchConfigurator<ObjectEntryFolder> _modelSearchConfigurator;

}