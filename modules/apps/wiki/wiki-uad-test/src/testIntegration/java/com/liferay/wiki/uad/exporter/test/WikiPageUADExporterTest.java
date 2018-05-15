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

package com.liferay.wiki.uad.exporter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.exporter.UADExporter;
import com.liferay.user.associated.data.test.util.BaseUADExporterTestCase;
import com.liferay.user.associated.data.test.util.WhenHasStatusByUserIdField;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.uad.test.WikiPageUADTestHelper;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class WikiPageUADExporterTest
	extends BaseUADExporterTestCase<WikiPage>
	implements WhenHasStatusByUserIdField<WikiPage> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	public WikiPage addBaseModelWithStatusByUserId(
			long userId, long statusByUserId)
		throws Exception {

		WikiPage wikiPage =
			_wikiPageUADTestHelper.addWikiPageWithStatusByUserId(
				userId, statusByUserId);

		_wikiPages.add(wikiPage);

		return wikiPage;
	}

	@After
	public void tearDown() throws Exception {
		_wikiPageUADTestHelper.cleanUpDependencies(_wikiPages);
	}

	@Override
	protected WikiPage addBaseModel(long userId) throws Exception {
		WikiPage wikiPage = _wikiPageUADTestHelper.addWikiPage(userId);

		_wikiPages.add(wikiPage);

		return wikiPage;
	}

	@Override
	protected String getPrimaryKeyName() {
		return "pageId";
	}

	@Override
	protected UADExporter getUADExporter() {
		return _uadExporter;
	}

	@Inject(filter = "component.name=*.WikiPageUADExporter")
	private UADExporter _uadExporter;

	@DeleteAfterTestRun
	private final List<WikiPage> _wikiPages = new ArrayList<>();

	@Inject
	private WikiPageUADTestHelper _wikiPageUADTestHelper;

}