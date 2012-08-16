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

package com.liferay.portalweb.plugins.samplespring.pet.editpetdescription;

import com.liferay.portalweb.plugins.samplespring.pet.addpet.AddPetTest;
import com.liferay.portalweb.plugins.samplespring.pet.addpet.TearDownPetTest;
import com.liferay.portalweb.plugins.samplespring.portlet.addportletdu.AddPageDUTest;
import com.liferay.portalweb.plugins.samplespring.portlet.addportletdu.AddPortletDUTest;
import com.liferay.portalweb.plugins.samplespring.portlet.addportletpets.AddPagePetsTest;
import com.liferay.portalweb.plugins.samplespring.portlet.addportletpets.AddPortletPetsTest;
import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class EditPetDescriptionTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPagePetsTest.class);
		testSuite.addTestSuite(AddPortletPetsTest.class);
		testSuite.addTestSuite(AddPageDUTest.class);
		testSuite.addTestSuite(AddPortletDUTest.class);
		testSuite.addTestSuite(AddPetTest.class);
		testSuite.addTestSuite(EditPetDescriptionTest.class);
		testSuite.addTestSuite(ViewEditPetDescriptionTest.class);
		testSuite.addTestSuite(TearDownPetTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}