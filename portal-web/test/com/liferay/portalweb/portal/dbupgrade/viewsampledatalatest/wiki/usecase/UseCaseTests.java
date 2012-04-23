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

package com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.wiki.usecase;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.users.user.signin.SignOutTest;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.wiki.usecase.Guest_ViewPortletGuestViewOffTest;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.wiki.usecase.SignInTest;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.wiki.usecase.ViewRateWikiFrontPageChildPageComment1Test;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.wiki.usecase.ViewRateWikiFrontPageChildPageTest;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.wiki.usecase.ViewWikiFrontPageAttachmentTest;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.wiki.usecase.ViewWikiFrontPageChildPageComment1Test;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.wiki.usecase.ViewWikiFrontPageChildPageComment2Test;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.wiki.usecase.ViewWikiFrontPageChildPageTag1Test;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.wiki.usecase.ViewWikiFrontPageChildPageTag2Test;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.wiki.usecase.ViewWikiFrontPageChildPageTest;
import com.liferay.portalweb.portal.dbupgrade.sampledatalatest.wiki.usecase.ViewWikiFrontPageTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class UseCaseTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(ViewWikiFrontPageTest.class);
		testSuite.addTestSuite(ViewWikiFrontPageAttachmentTest.class);
		testSuite.addTestSuite(ViewWikiFrontPageChildPageTest.class);
		testSuite.addTestSuite(ViewWikiFrontPageChildPageTag1Test.class);
		testSuite.addTestSuite(ViewWikiFrontPageChildPageTag2Test.class);
		testSuite.addTestSuite(ViewRateWikiFrontPageChildPageTest.class);
		testSuite.addTestSuite(SignOutTest.class);
		testSuite.addTestSuite(Guest_ViewPortletGuestViewOffTest.class);
		testSuite.addTestSuite(SignInTest.class);
		testSuite.addTestSuite(ViewWikiFrontPageChildPageComment1Test.class);
		testSuite.addTestSuite(ViewWikiFrontPageChildPageComment2Test.class);
		testSuite.addTestSuite(ViewRateWikiFrontPageChildPageComment1Test.class);

		return testSuite;
	}
}