/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	protected void addModels(String... keywords) throws Exception {
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