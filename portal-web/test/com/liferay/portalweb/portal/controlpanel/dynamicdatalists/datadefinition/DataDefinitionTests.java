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

package com.liferay.portalweb.portal.controlpanel.dynamicdatalists.datadefinition;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.datadefinition.adddatadefinitionboolean.AddDataDefinitionBooleanTests;
import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.datadefinition.adddatadefinitiondate.AddDataDefinitionDateTests;
import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.datadefinition.adddatadefinitiondecimal.AddDataDefinitionDecimalTests;
import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.datadefinition.adddatadefinitiondocumentlibrary.AddDataDefinitionDocumentLibraryTests;
import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.datadefinition.adddatadefinitionfieldfull.AddDataDefinitionFieldFullTests;
import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.datadefinition.adddatadefinitionfieldnull.AddDataDefinitionFieldNullTests;
import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.datadefinition.adddatadefinitionfileupload.AddDataDefinitionFileUploadTests;
import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.datadefinition.adddatadefinitionnameduplicate.AddDataDefinitionNameDuplicateTests;
import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.datadefinition.adddatadefinitionnamenull.AddDataDefinitionNameNullTests;
import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.datadefinition.adddatadefinitionnumber.AddDataDefinitionNumberTests;
import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.datadefinition.adddatadefinitionradio.AddDataDefinitionRadioTests;
import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.datadefinition.adddatadefinitionselect.AddDataDefinitionSelectTests;
import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.datadefinition.adddatadefinitiontext.AddDataDefinitionTextTests;
import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.datadefinition.adddatadefinitiontextbox.AddDataDefinitionTextBoxTests;
import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.datadefinition.deletedatadefinition.DeleteDataDefinitionTests;
import com.liferay.portalweb.portal.controlpanel.dynamicdatalists.datadefinition.editdatadefinition.EditDataDefinitionTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DataDefinitionTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddDataDefinitionBooleanTests.suite());
		testSuite.addTest(AddDataDefinitionDateTests.suite());
		testSuite.addTest(AddDataDefinitionDecimalTests.suite());
		testSuite.addTest(AddDataDefinitionDocumentLibraryTests.suite());
		testSuite.addTest(AddDataDefinitionFieldFullTests.suite());
		testSuite.addTest(AddDataDefinitionFieldNullTests.suite());
		testSuite.addTest(AddDataDefinitionFileUploadTests.suite());
		testSuite.addTest(AddDataDefinitionNameDuplicateTests.suite());
		testSuite.addTest(AddDataDefinitionNameNullTests.suite());
		testSuite.addTest(AddDataDefinitionNumberTests.suite());
		testSuite.addTest(AddDataDefinitionRadioTests.suite());
		testSuite.addTest(AddDataDefinitionSelectTests.suite());
		testSuite.addTest(AddDataDefinitionTextTests.suite());
		testSuite.addTest(AddDataDefinitionTextBoxTests.suite());
		testSuite.addTest(DeleteDataDefinitionTests.suite());
		testSuite.addTest(EditDataDefinitionTests.suite());

		return testSuite;
	}

}