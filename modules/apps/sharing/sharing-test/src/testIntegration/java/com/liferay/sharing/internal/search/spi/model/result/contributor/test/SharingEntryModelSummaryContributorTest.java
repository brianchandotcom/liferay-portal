/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.internal.search.spi.model.result.contributor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.sharing.model.SharingEntry;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mikel Lorza
 */
@RunWith(Arquillian.class)
public class SharingEntryModelSummaryContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetSummary() {
		ModelSummaryContributor modelSummaryContributor =
			_modelSearchConfigurator.getModelSummaryContributor();

		Document document = new DocumentImpl();

		document.addText("localized_title_es_ES", "título");
		document.addText("localized_title_en_US", "title");

		Summary summary = modelSummaryContributor.getSummary(
			document, LocaleUtil.SPAIN, StringPool.BLANK);

		Assert.assertEquals("título", summary.getTitle());

		summary = modelSummaryContributor.getSummary(
			document, LocaleUtil.FRANCE, StringPool.BLANK);

		Assert.assertEquals("title", summary.getTitle());
	}

	@Inject(
		filter = "component.name=com.liferay.sharing.internal.search.SharingEntryModelSearchConfigurator"
	)
	private ModelSearchConfigurator<SharingEntry> _modelSearchConfigurator;

}