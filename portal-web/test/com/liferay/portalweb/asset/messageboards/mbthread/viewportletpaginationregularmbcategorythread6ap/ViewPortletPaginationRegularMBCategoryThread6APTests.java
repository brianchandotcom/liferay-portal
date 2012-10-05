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

package com.liferay.portalweb.asset.messageboards.mbthread.viewportletpaginationregularmbcategorythread6ap;

import com.liferay.portalweb.asset.assetpublisher.portlet.addportletap.AddPageAPTest;
import com.liferay.portalweb.asset.assetpublisher.portlet.addportletap.AddPortletAPTest;
import com.liferay.portalweb.asset.assetpublisher.portlet.configureportletmaximumitemstodisplay2.ConfigurePortletMaximumItemsToDisplay2Test;
import com.liferay.portalweb.asset.assetpublisher.portlet.configureportletpaginationtyperegular.ConfigurePortletPaginationTypeRegularTest;
import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.messageboards.mbcategory.addmbcategory.AddMBCategoryTest;
import com.liferay.portalweb.portlet.messageboards.mbthread.postnewmbcategorythread.PostNewMBCategoryThread1Test;
import com.liferay.portalweb.portlet.messageboards.mbthread.postnewmbcategorythread.PostNewMBCategoryThread2Test;
import com.liferay.portalweb.portlet.messageboards.mbthread.postnewmbcategorythread.PostNewMBCategoryThread3Test;
import com.liferay.portalweb.portlet.messageboards.mbthread.postnewmbcategorythread.PostNewMBCategoryThread4Test;
import com.liferay.portalweb.portlet.messageboards.mbthread.postnewmbcategorythread.PostNewMBCategoryThread5Test;
import com.liferay.portalweb.portlet.messageboards.mbthread.postnewmbcategorythread.PostNewMBCategoryThread6Test;
import com.liferay.portalweb.portlet.messageboards.mbthread.postnewmbthread.TearDownMBThreadTest;
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
		testSuite.addTestSuite(PostNewMBCategoryThread1Test.class);
		testSuite.addTestSuite(PostNewMBCategoryThread2Test.class);
		testSuite.addTestSuite(PostNewMBCategoryThread3Test.class);
		testSuite.addTestSuite(PostNewMBCategoryThread4Test.class);
		testSuite.addTestSuite(PostNewMBCategoryThread5Test.class);
		testSuite.addTestSuite(PostNewMBCategoryThread6Test.class);
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