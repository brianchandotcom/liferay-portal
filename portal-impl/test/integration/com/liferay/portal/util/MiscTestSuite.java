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

package com.liferay.portal.util;

import com.liferay.portal.image.ImageToolImplTest;
import com.liferay.portal.xmlrpc.XmlRpcParserTest;
import com.liferay.portlet.RouterImplTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class MiscTestSuite extends TestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(FileImplExtractTest.class);
		testSuite.addTestSuite(FileMultiValueMapTest.class);
		testSuite.addTestSuite(ImageToolImplTest.class);
		testSuite.addTestSuite(MemoryMultiValueMapTest.class);
		testSuite.addTestSuite(MimeTypesImplTest.class);
		testSuite.addTestSuite(RouterImplTest.class);
		testSuite.addTestSuite(XmlRpcParserTest.class);

		return testSuite;
	}

}