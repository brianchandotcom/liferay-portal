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

package com.liferay.portalweb.portlet.documentsandmedia.dmbreadcrumb;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.documentsandmedia.dmbreadcrumb.viewdmbreadcrumbfolder.ViewDMBreadcrumbFolderTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmbreadcrumb.viewdmbreadcrumbroot.ViewDMBreadcrumbRootTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmbreadcrumb.viewdmbreadcrumbsubfolder.ViewDMBreadcrumbSubfolderTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DMBreadcrumbTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(ViewDMBreadcrumbFolderTests.suite());
		testSuite.addTest(ViewDMBreadcrumbRootTests.suite());
		testSuite.addTest(ViewDMBreadcrumbSubfolderTests.suite());

		return testSuite;
	}

}