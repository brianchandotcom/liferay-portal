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
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
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
		Document document = new DocumentImpl();
		ModelSummaryContributor modelSummaryContributor =
			_modelSearchConfigurator.getModelSummaryContributor();
		String title1 = RandomTestUtil.randomString();
		String title2 = RandomTestUtil.randomString();

		document.addText("localized_label_en_US", title1);
		document.addText("localized_label_es_ES", title2);

		Summary summary = modelSummaryContributor.getSummary(
			document, LocaleUtil.SPAIN, "");

		Assert.assertEquals(title2, summary.getTitle());

		summary = modelSummaryContributor.getSummary(
			document, LocaleUtil.FRANCE, "");

		Assert.assertEquals(title1, summary.getTitle());

		String name = RandomTestUtil.randomString();

		_assertSummary(
			title2, modelSummaryContributor, "snippet_title", title2, "title",
			title1);

		_assertSummary(
			title1, modelSummaryContributor, "name", name, "title", title1);

		String lowerCaseTitle = StringUtil.toLowerCase(title1);
		String upperCaseTitle = StringUtil.toUpperCase(title1);

		_assertSummary(
			upperCaseTitle, modelSummaryContributor, "localized_label_en_US",
			lowerCaseTitle, "name", upperCaseTitle);

		_assertSummary(
			title2, modelSummaryContributor, "localized_label_en_US",
			lowerCaseTitle, "name", upperCaseTitle, "snippet_title", title2);
	}

	private void _assertSummary(
		String expectedTitle, ModelSummaryContributor modelSummaryContributor,
		String... values) {

		Document document = new DocumentImpl();

		for (int i = 0; i < values.length; i += 2) {
			document.addText(values[i], values[i + 1]);
		}

		Summary summary = modelSummaryContributor.getSummary(
			document, LocaleUtil.US, "");

		Assert.assertEquals(expectedTitle, summary.getTitle());
	}

	@Inject(
		filter = "component.name=com.liferay.object.internal.search.ObjectEntryFolderModelSearchConfigurator"
	)
	private ModelSearchConfigurator<ObjectEntryFolder> _modelSearchConfigurator;

}