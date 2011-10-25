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

package com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldselect;

import com.liferay.portalweb.portal.BaseTests;
//import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldselect.addfieldselect.AddFieldSelectTests;
//import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldselect.deletefieldselect.DeleteFieldSelectTests;
//import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldselect.editfieldselectfieldlabel.EditFieldSelectFieldLabelTests;
//import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldselect.editfieldselectshowlabel.EditFieldSelectShowLabelTests;
//import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldselect.editfieldselectrequired.EditFieldSelectRequiredTests;
//import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldselect.editfieldselectname.EditFieldSelectNameTests;
//import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldselect.editfieldselectpredefinedvalue.EditFieldSelectPredefinedValueTests;
//import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldselect.editfieldselecttip.EditFieldSelectTipTests;
//import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldselect.editfieldselectoptions.EditFieldSelectOptionsTests;
import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldselect.editfieldselectoptionsutf8.EditFieldSelectOptionsUTF8Tests;
//import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldselect.editfieldselectmultiple.EditFieldSelectMultipleTests;


import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class FieldSelectTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

//		testSuite.addTest(AddFieldSelectTests.suite());
//		testSuite.addTest(DeleteFieldSelectTests.suite());
//		testSuite.addTest(EditFieldSelectFieldLabelTests.suite());
//		testSuite.addTest(EditFieldSelectShowLabelTests.suite());
//		testSuite.addTest(EditFieldSelectRequiredTests.suite());
//		testSuite.addTest(EditFieldSelectNameTests.suite());
//		testSuite.addTest(EditFieldSelectPredefinedValueTests.suite());
//		testSuite.addTest(EditFieldSelectTipTests.suite());
//		testSuite.addTest(EditFieldSelectOptionsTests.suite());
		testSuite.addTest(EditFieldSelectOptionsUTF8Tests.suite());
//		testSuite.addTest(EditFieldSelectMultipleTests.suite());

		return testSuite;
	}

}