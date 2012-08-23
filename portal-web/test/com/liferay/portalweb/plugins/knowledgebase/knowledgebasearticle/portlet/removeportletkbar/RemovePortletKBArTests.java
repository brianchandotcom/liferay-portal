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

package com.liferay.portalweb.plugins.knowledgebase.knowledgebasearticle.portlet.removeportletkbar;

import com.liferay.portalweb.plugins.knowledgebase.knowledgebasearticle.portlet.addportletkbar.AddPageKBArTest;
import com.liferay.portalweb.plugins.knowledgebase.knowledgebasearticle.portlet.addportletkbar.AddPortletKBArTest;
import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class RemovePortletKBArTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageKBArTest.class);
		testSuite.addTestSuite(AddPortletKBArTest.class);
		testSuite.addTestSuite(RemovePortletKBArTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}