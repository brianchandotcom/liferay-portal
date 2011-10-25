/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldradio;

import com.liferay.portalweb.portal.BaseTests;
//import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldradio.addfieldradio.AddFieldRadioTests;
//import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldradio.deletefieldradio.DeleteFieldRadioTests;
//import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldradio.editfieldradiofieldlabel.EditFieldRadioFieldLabelTests;
//import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldradio.editfieldradioshowlabel.EditFieldRadioShowLabelTests;
//import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldradio.editfieldradiorequired.EditFieldRadioRequiredTests;
//import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldradio.editfieldradioname.EditFieldRadioNameTests;
//import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldradio.editfieldradiopredefinedvalue.EditFieldRadioPredefinedValueTests;
//import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldradio.editfieldradiotip.EditFieldRadioTipTests;
//import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldradio.editfieldradiooptions.EditFieldRadioOptionsTests;
import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldradio.editfieldradiooptionsutf8.EditFieldRadioOptionsUTF8Tests;
//import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldradio.editfieldradiomultiple.EditFieldRadioMultipleTests;


import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class FieldRadioTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

//		testSuite.addTest(AddFieldRadioTests.suite());
//		testSuite.addTest(DeleteFieldRadioTests.suite());
//		testSuite.addTest(EditFieldRadioFieldLabelTests.suite());
//		testSuite.addTest(EditFieldRadioShowLabelTests.suite());
//		testSuite.addTest(EditFieldRadioRequiredTests.suite());
//		testSuite.addTest(EditFieldRadioNameTests.suite());
//		testSuite.addTest(EditFieldRadioPredefinedValueTests.suite());
//		testSuite.addTest(EditFieldRadioTipTests.suite());
//		testSuite.addTest(EditFieldRadioOptionsTests.suite());
		testSuite.addTest(EditFieldRadioOptionsUTF8Tests.suite());
//		testSuite.addTest(EditFieldRadioMultipleTests.suite());

		return testSuite;
	}

}