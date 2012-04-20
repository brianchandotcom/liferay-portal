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

package com.liferay.portalweb.plugins.knowledgebase;

import com.liferay.portalweb.plugins.knowledgebase.knowledgebaseadmin.KnowledgeBaseAdminTestPlan;
import com.liferay.portalweb.plugins.knowledgebase.knowledgebasearticle.KnowledgeBaseArticleTestPlan;
import com.liferay.portalweb.plugins.knowledgebase.knowledgebasedisplay.KnowledgeBaseDisplayTestPlan;
import com.liferay.portalweb.plugins.knowledgebase.knowledgebasesearch.KnowledgeBaseSearchTestPlan;
import com.liferay.portalweb.plugins.knowledgebase.knowledgebasesection.KnowledgeBaseSectionTestPlan;
import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class KnowledgeBaseTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(KnowledgeBaseAdminTestPlan.suite());
		testSuite.addTest(KnowledgeBaseArticleTestPlan.suite());
		testSuite.addTest(KnowledgeBaseDisplayTestPlan.suite());
		testSuite.addTest(KnowledgeBaseSearchTestPlan.suite());
		testSuite.addTest(KnowledgeBaseSectionTestPlan.suite());

		return testSuite;
	}

}