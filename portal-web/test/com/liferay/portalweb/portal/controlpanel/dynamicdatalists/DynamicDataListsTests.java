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

package com.liferay.portalweb.portal.controlpanel.dynamicdatalists;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.datadefinition.DataDefinitionTests;
import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldboolean.FieldBooleanTests;
import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldradio.FieldRadioTests;
import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.fieldselect.FieldSelectTests;
import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.list.ListTests;
import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.record.RecordTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DynamicDataListsTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(DataDefinitionTests.suite());
		testSuite.addTest(FieldBooleanTests.suite());
		testSuite.addTest(FieldRadioTests.suite());
		testSuite.addTest(FieldSelectTests.suite());
		testSuite.addTest(ListTests.suite());
		testSuite.addTest(RecordTests.suite());

		return testSuite;
	}

}