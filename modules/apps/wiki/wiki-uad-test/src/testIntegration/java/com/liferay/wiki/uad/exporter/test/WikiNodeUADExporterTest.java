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
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.WikiNodeLocalService;
import com.liferay.wiki.uad.test.WikiNodeUADTestHelper;

import java.util.ArrayList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class WikiNodeUADExporterTest
	extends BaseUADExporterTestCase<WikiNode>
	implements WhenHasStatusByUserIdField<WikiNode> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	public WikiNode addBaseModelWithStatusByUserId(
			long userId, long statusByUserId)
		throws Exception {

		WikiNode wikiNode = WikiNodeUADTestHelper.addWikiNodeWithStatusByUserId(
			_wikiNodeLocalService, userId, statusByUserId);

		_wikiNodes.add(wikiNode);

		return wikiNode;
	}

	@Override
	protected WikiNode addBaseModel(long userId) throws Exception {
		WikiNode wikiNode = WikiNodeUADTestHelper.addWikiNode(
			_wikiNodeLocalService, userId);

		_wikiNodes.add(wikiNode);

		return wikiNode;
	}

	@Override
	protected String getPrimaryKeyName() {
		return "nodeId";
	}

	@Override
	protected UADExporter getUADExporter() {
		return _uadExporter;
	}

	@Inject(filter = "component.name=*.WikiNodeUADExporter")
	private UADExporter _uadExporter;

	@Inject
	private WikiNodeLocalService _wikiNodeLocalService;

	@DeleteAfterTestRun
	private final List<WikiNode> _wikiNodes = new ArrayList<>();

}