/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.plugins.knowledgebase.knowledgebasesearch.kbaarticle.searchkbaarticlekbsr;

import com.liferay.portalweb.plugins.knowledgebase.knowledgebaseadmin.kbaarticle.addkbaarticle.AddKBAArticleTest;
import com.liferay.portalweb.plugins.knowledgebase.knowledgebaseadmin.kbaarticle.addkbaarticle.TearDownKBAArticleTest;
import com.liferay.portalweb.plugins.knowledgebase.knowledgebasesearch.portlet.addportletkbsr.AddPageKBSrTest;
import com.liferay.portalweb.plugins.knowledgebase.knowledgebasesearch.portlet.addportletkbsr.AddPortletKBSrTest;
import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchKBAArticleKBSrTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageKBSrTest.class);
		testSuite.addTestSuite(AddPortletKBSrTest.class);
		testSuite.addTestSuite(AddKBAArticleTest.class);
		testSuite.addTestSuite(SearchKBAArticleKBSrTest.class);
		testSuite.addTestSuite(SearchKBAArticleQuotesKBSrTest.class);
		testSuite.addTestSuite(TearDownKBAArticleTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}