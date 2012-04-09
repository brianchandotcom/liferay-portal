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

package com.liferay.portalweb.portlet.assetpublisher.mbthreadmessage.viewportletpaginationregularmbcategorythread6ap;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.assetpublisher.portlet.addportletap.AddPageAPTest;
import com.liferay.portalweb.portlet.assetpublisher.portlet.addportletap.AddPortletAPTest;
import com.liferay.portalweb.portlet.messageboards.mbcategory.addmbcategory.AddMBCategoryTest;
import com.liferay.portalweb.portlet.messageboards.mbmessage.postnewmbthreadmessage.TearDownMBThreadTest;
import com.liferay.portalweb.portlet.messageboards.portlet.addportletmb.AddPageMBTest;
import com.liferay.portalweb.portlet.messageboards.portlet.addportletmb.AddPortletMBTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPortletPaginationRegularMBCategoryThread6APTests
	extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageAPTest.class);
		testSuite.addTestSuite(AddPortletAPTest.class);
		testSuite.addTestSuite(AddPageMBTest.class);
		testSuite.addTestSuite(AddPortletMBTest.class);
		testSuite.addTestSuite(AddMBCategoryTest.class);
		testSuite.addTestSuite(PostNewMBCategoryThreadMessage1Test.class);
		testSuite.addTestSuite(PostNewMBCategoryThreadMessage2Test.class);
		testSuite.addTestSuite(PostNewMBCategoryThreadMessage3Test.class);
		testSuite.addTestSuite(PostNewMBCategoryThreadMessage4Test.class);
		testSuite.addTestSuite(PostNewMBCategoryThreadMessage5Test.class);
		testSuite.addTestSuite(PostNewMBCategoryThreadMessage6Test.class);
		testSuite.addTestSuite(ConfigurePortletMaximumItemsToDisplay2Test.class);
		testSuite.addTestSuite(ConfigurePortletPaginationTypeRegularTest.class);
		testSuite.addTestSuite(FirstButtonAPTest.class);
		testSuite.addTestSuite(LastButtonAPTest.class);
		testSuite.addTestSuite(NextButtonAPTest.class);
		testSuite.addTestSuite(PreviousButtonAPTest.class);
		testSuite.addTestSuite(SelectPageAPTest.class);
		testSuite.addTestSuite(TearDownMBThreadTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}