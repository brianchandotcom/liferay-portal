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

package com.liferay.portalweb.portal.controlpanel.adt.categoriesnavigation.viewportletcndisplaytemplatemulticolumn;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.categories.category.addcategory.AddCategoryTest;
import com.liferay.portalweb.portal.controlpanel.categories.category.deletecategoryactions.DeleteCategoryActionsTest;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.categoriesnavigation.portlet.addportletcn.AddPageCNTest;
import com.liferay.portalweb.portlet.categoriesnavigation.portlet.addportletcn.AddPortletCNTest;
import com.liferay.portalweb.portlet.categoriesnavigation.portlet.configureportletcndisplaytemplatemulticolumn.ConfigurePortletCNDisplayTemplateDefaultTest;
import com.liferay.portalweb.portlet.categoriesnavigation.portlet.configureportletcndisplaytemplatemulticolumn.ConfigurePortletCNDisplayTemplateMultiColumnTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPortletCNDisplayTemplateMultiColumnTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageCNTest.class);
		testSuite.addTestSuite(AddPortletCNTest.class);
		testSuite.addTestSuite(AddCategoryTest.class);
		testSuite.addTestSuite(ConfigurePortletCNDisplayTemplateMultiColumnTest.class);
		testSuite.addTestSuite(ViewPortletCNDisplayTemplateMultiColumnTest.class);
		testSuite.addTestSuite(ConfigurePortletCNDisplayTemplateDefaultTest.class);
		testSuite.addTestSuite(DeleteCategoryActionsTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}