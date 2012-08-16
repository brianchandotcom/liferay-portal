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

package com.liferay.portalweb.plugins.samplespring.pet;

import com.liferay.portalweb.plugins.samplespring.pet.addpet.AddPetTests;
import com.liferay.portalweb.plugins.samplespring.pet.addpetdateformatdaymonthdash.AddPetDateFormatDayMonthDashTests;
import com.liferay.portalweb.plugins.samplespring.pet.addpetdateformatdaymonthslash.AddPetDateFormatDayMonthSlashTests;
import com.liferay.portalweb.plugins.samplespring.pet.addpetdateformatmonthdaydash.AddPetDateFormatMonthDayDashTests;
import com.liferay.portalweb.plugins.samplespring.pet.addpetdateformatmonthdayslash.AddPetDateFormatMonthDaySlashTests;
import com.liferay.portalweb.plugins.samplespring.pet.editpetdescription.EditPetDescriptionTests;
import com.liferay.portalweb.plugins.samplespring.pet.removepet.RemovePetTests;
import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PetTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddPetTests.suite());
		testSuite.addTest(AddPetDateFormatDayMonthDashTests.suite());
		testSuite.addTest(AddPetDateFormatDayMonthSlashTests.suite());
		testSuite.addTest(AddPetDateFormatMonthDayDashTests.suite());
		testSuite.addTest(AddPetDateFormatMonthDaySlashTests.suite());
		testSuite.addTest(EditPetDescriptionTests.suite());
		testSuite.addTest(RemovePetTests.suite());

		return testSuite;
	}

}