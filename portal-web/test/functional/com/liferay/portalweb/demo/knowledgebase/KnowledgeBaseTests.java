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

package com.liferay.portalweb.demo.knowledgebase;

import com.liferay.portalweb.plugins.knowledgebase.knowledgebaseadmin.kbaarticle.addkbaarticle.TearDownKBAArticleTest;
import com.liferay.portalweb.plugins.knowledgebase.knowledgebasearticle.portlet.addportletkbar.AddPageKBArTest;
import com.liferay.portalweb.plugins.knowledgebase.knowledgebasearticle.portlet.addportletkbar.AddPortletKBArTest;
import com.liferay.portalweb.plugins.knowledgebase.knowledgebasedisplay.portlet.addportletkbd.AddPageKBDTest;
import com.liferay.portalweb.plugins.knowledgebase.knowledgebasedisplay.portlet.addportletkbd.AddPortletKBDTest;
import com.liferay.portalweb.plugins.knowledgebase.knowledgebasesearch.portlet.addportletkbsr.AddPageKBSrTest;
import com.liferay.portalweb.plugins.knowledgebase.knowledgebasesearch.portlet.addportletkbsr.AddPortletKBSrTest;
import com.liferay.portalweb.plugins.knowledgebase.knowledgebasesection.portlet.addportletkbsc.AddPageKBScTest;
import com.liferay.portalweb.plugins.knowledgebase.knowledgebasesection.portlet.addportletkbsc.AddPortletKBScTest;
import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class KnowledgeBaseTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageKBArTest.class);
		testSuite.addTestSuite(AddPortletKBArTest.class);
		testSuite.addTestSuite(AddPageKBDTest.class);
		testSuite.addTestSuite(AddPortletKBDTest.class);
		testSuite.addTestSuite(AddPageKBSrTest.class);
		testSuite.addTestSuite(AddPortletKBSrTest.class);
		testSuite.addTestSuite(AddPageKBScTest.class);
		testSuite.addTestSuite(AddPortletKBScTest.class);
		testSuite.addTestSuite(AddKBAArticleSectionsPortletsTest.class);
		testSuite.addTestSuite(ViewKBAArticleSectionsPortletsTest.class);
		testSuite.addTestSuite(EditKBAArticleSectionsPortletsAttachmentTest.class);
		testSuite.addTestSuite(ViewEditKBAArticleSectionsPortletsAttachmentTest.class);
		testSuite.addTestSuite(DeleteKBAArticleSectionsPortletsKBDTest.class);
		testSuite.addTestSuite(AddKBAArticleSectionsCMTest.class);
		testSuite.addTestSuite(AddKBAArticleSectionsDevelopmentTest.class);
		testSuite.addTestSuite(AddKBAArticleSectionsASTest.class);
		testSuite.addTestSuite(SelectKBAArticleSectionsCMKBArTest.class);
		testSuite.addTestSuite(ViewSelectKBAArticleSectionsCMKBArTest.class);
		testSuite.addTestSuite(ConfigurePortletKBSSectionsASTest.class);
		testSuite.addTestSuite(ViewConfigurePortletKBSSectionsASTest.class);
		testSuite.addTestSuite(SearchKBAArticleContentKBSTest.class);
		testSuite.addTestSuite(SearchKBAArticleKnowledgeKBSTest.class);
		testSuite.addTestSuite(SearchKBAArticleTomcatKBSTest.class);
		testSuite.addTestSuite(DeleteKBAArticleSectionsCMKBArTest.class);
		testSuite.addTestSuite(TearDownKBAArticleTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}