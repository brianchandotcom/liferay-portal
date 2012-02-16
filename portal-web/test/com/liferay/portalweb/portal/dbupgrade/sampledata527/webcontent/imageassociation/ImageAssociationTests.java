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

package com.liferay.portalweb.portal.dbupgrade.sampledata527.webcontent.imageassociation;

import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ImageAssociationTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddCommunityWebContentImageAssociationTest.class);
		testSuite.addTestSuite(AddPageIGTest.class);
		testSuite.addTestSuite(AddPortletIGTest.class);
		testSuite.addTestSuite(AddPageWCDTest.class);
		testSuite.addTestSuite(AddPortletWCDTest.class);
		testSuite.addTestSuite(AddIGFolderTest.class);
		testSuite.addTestSuite(AddIGFolderImageTest.class);
		testSuite.addTestSuite(AddStructureImageAssociationTest.class);
		testSuite.addTestSuite(AddTemplateImageAssociationTest.class);
		testSuite.addTestSuite(AddWebContentImageAssociationTest.class);
		testSuite.addTestSuite(SelectWebContentImageAssociationTest.class);
		testSuite.addTestSuite(ViewWebContentImageAssociationTest.class);

		return testSuite;
	}
}