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

package com.liferay.wiki.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.search.test.util.BaseSearchRequestBuilderHighlightTestCase;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.test.util.WikiTestUtil;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Joshua Cords
 */
@RunWith(Arquillian.class)
public class WikiPageSearchRequestBuilderHighlightTest
	extends BaseSearchRequestBuilderHighlightTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_node = WikiTestUtil.addNode(group.getGroupId());
	}

	@Override
	protected void addAssets(String... keywords) throws Exception {
		for (String keyword : keywords) {
			WikiTestUtil.addPage(
				TestPropsValues.getUserId(), _node.getNodeId(), keyword,
				keyword, true, serviceContext);
		}
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return WikiPage.class;
	}

	protected String[] getFieldNames() {
		return new String[] {"content_en_US", "title_en_US"};
	}

	private WikiNode _node;

}