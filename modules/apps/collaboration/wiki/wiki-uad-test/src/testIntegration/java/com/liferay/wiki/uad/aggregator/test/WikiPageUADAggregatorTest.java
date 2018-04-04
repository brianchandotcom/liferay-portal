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

package com.liferay.wiki.uad.aggregator.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import com.liferay.user.associated.data.aggregator.UADAggregator;
import com.liferay.user.associated.data.test.util.BaseUADAggregatorTestCase;
import com.liferay.user.associated.data.test.util.WhenHasStatusByUserIdField;

import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.uad.constants.WikiUADConstants;
import com.liferay.wiki.uad.test.WikiPageUADEntityTestHelper;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Rule;

import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@RunWith(Arquillian.class)
public class WikiPageUADAggregatorTest extends BaseUADAggregatorTestCase
	implements WhenHasStatusByUserIdField {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new LiferayIntegrationTestRule();

	@Override
	public BaseModel<?> addBaseModelWithStatusByUserId(long userId,
		long statusByUserId) throws Exception {
		WikiPage wikiPage = _wikiPageUADEntityTestHelper.addWikiPageWithStatusByUserId(userId,
				statusByUserId);

		_wikiPages.add(wikiPage);

		return wikiPage;
	}

	@Override
	protected BaseModel<?> addBaseModel(long userId) throws Exception {
		WikiPage wikiPage = _wikiPageUADEntityTestHelper.addWikiPage(userId);

		_wikiPages.add(wikiPage);

		return wikiPage;
	}

	@Override
	protected UADAggregator getUADAggregator() {
		return _uadAggregator;
	}

	@After
	public void tearDown() throws Exception {
		_wikiPageUADEntityTestHelper.cleanUpDependencies(_wikiPages);
	}

	@DeleteAfterTestRun
	private final List<WikiPage> _wikiPages = new ArrayList<WikiPage>();
	@Inject
	private WikiPageUADEntityTestHelper _wikiPageUADEntityTestHelper;
	@Inject(filter = "model.class.name=" +
	WikiUADConstants.CLASS_NAME_WIKI_PAGE)
	private UADAggregator _uadAggregator;
}