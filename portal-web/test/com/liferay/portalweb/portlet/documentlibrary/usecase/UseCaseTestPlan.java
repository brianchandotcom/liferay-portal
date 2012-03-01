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

package com.liferay.portalweb.portlet.documentlibrary.usecase;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.documentlibrary.usecase.demo1.Demo1Tests;
import com.liferay.portalweb.portlet.documentlibrary.usecase.demo2.Demo2Tests;
import com.liferay.portalweb.portlet.documentlibrary.usecase.demo3.Demo3Tests;
import com.liferay.portalweb.portlet.documentlibrary.usecase.demo4.Demo4Tests;
import com.liferay.portalweb.portlet.documentlibrary.usecase.demo5.Demo5Tests;
import com.liferay.portalweb.portlet.documentlibrary.usecase.demo6.Demo6Tests;
import com.liferay.portalweb.portlet.documentlibrary.usecase.demo7.Demo7Tests;
import com.liferay.portalweb.portlet.documentlibrary.usecase.demo8.Demo8Tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class UseCaseTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(Demo1Tests.suite());
		testSuite.addTest(Demo2Tests.suite());
		testSuite.addTest(Demo3Tests.suite());
		testSuite.addTest(Demo4Tests.suite());
		testSuite.addTest(Demo5Tests.suite());
		testSuite.addTest(Demo6Tests.suite());
		testSuite.addTest(Demo7Tests.suite());
		testSuite.addTest(Demo8Tests.suite());

		return testSuite;
	}

}