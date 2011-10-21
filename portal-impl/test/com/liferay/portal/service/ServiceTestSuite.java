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

package com.liferay.portal.service;

import com.liferay.counter.service.CounterLocalServiceTest;
import com.liferay.portlet.bookmarks.service.BookmarksEntryServiceTest;
import com.liferay.portlet.bookmarks.service.BookmarksFolderServiceTest;
import com.liferay.portlet.documentlibrary.service.DLAppServiceTest;
import com.liferay.portlet.documentlibrary.service.DLContentLocalServiceTest;
import com.liferay.portlet.messageboards.service.MBMessageServiceTest;
import com.liferay.portlet.social.service.SocialRelationLocalServiceTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ServiceTestSuite extends TestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(CounterLocalServiceTest.class);

		testSuite.addTestSuite(ResourceLocalServiceTest.class);
		testSuite.addTestSuite(UserServiceTest.class);

		testSuite.addTestSuite(BookmarksFolderServiceTest.class);
		testSuite.addTestSuite(BookmarksEntryServiceTest.class);

		testSuite.addTestSuite(DLAppServiceTest.class);
		testSuite.addTestSuite(DLContentLocalServiceTest.class);

		testSuite.addTestSuite(MBMessageServiceTest.class);

		testSuite.addTestSuite(SocialRelationLocalServiceTest.class);

		return new ServiceTestSetup(testSuite);
	}

}