/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.layout.display.page.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class DefaultJournalArticleLayoutDisplayPageProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testDefaultURLSeparator() {
		Assert.assertEquals(
			JournalArticleConstants.CANONICAL_URL_SEPARATOR,
			_layoutDisplayPageProvider.getDefaultURLSeparator());
	}

	@Test
	public void testGetURLSeparator() {
		Assert.assertEquals(
			JournalArticleConstants.CANONICAL_URL_SEPARATOR,
			_layoutDisplayPageProvider.getURLSeparator());
	}

	@Inject(
		filter = "component.name=com.liferay.journal.web.internal.layout.display.page.DefaultJournalArticleLayoutDisplayPageProvider"
	)
	private LayoutDisplayPageProvider<JournalArticle>
		_layoutDisplayPageProvider;

}