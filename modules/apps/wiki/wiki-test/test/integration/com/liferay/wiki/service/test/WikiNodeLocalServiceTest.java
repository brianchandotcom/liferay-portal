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

package com.liferay.wiki.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ProgressTracker;
import com.liferay.portal.kernel.util.ProgressTrackerThreadLocal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.util.test.WikiTestUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.Collections;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
@Sync
public class WikiNodeLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testImportPages() throws Exception {
		_node = WikiTestUtil.addNode(TestPropsValues.getGroupId());

		ProgressTracker progressTracker = new ProgressTracker(
			StringUtil.randomString());

		ProgressTrackerThreadLocal.setProgressTracker(progressTracker);

		byte[] bytes = FileUtil.getBytes(getClass(), _MEDIAWIKI_XML);
		InputStream is = new ByteArrayInputStream(bytes);

		WikiNodeLocalServiceUtil.importPages(
			TestPropsValues.getUserId(), _node.getNodeId(), _IMPORTER,
			new InputStream[] {is, null, null},
			Collections.<String, String[]>emptyMap());

		WikiPage importedPage = WikiPageLocalServiceUtil.fetchPage(
			_node.getNodeId(), _PAGE_NAME);

		Assert.assertNotNull(importedPage);
	}

	private static final String _IMPORTER = "MediaWiki";

	private static final String _MEDIAWIKI_XML =
		"/com/liferay/wiki/service/test/dependencies/LiferayMediaWiki.xml";

	private static final String _PAGE_NAME = "Liferay";

	@DeleteAfterTestRun
	private WikiNode _node;

}