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

package com.liferay.portalweb.portal;

import com.liferay.portalweb.plugins.sampleorbeonforms.SampleOrbeonFormsTests;
import com.liferay.portalweb.plugins.samplepermissions.SamplePermissionsTests;
import com.liferay.portalweb.plugins.samplephp.SamplePHPTests;
import com.liferay.portalweb.plugins.sampleportalclient.SamplePortalClientTests;
import com.liferay.portalweb.plugins.sampleportalservice.SamplePortalServiceTests;
import com.liferay.portalweb.plugins.samplepython.SamplePythonTests;
import com.liferay.portalweb.plugins.sampleruby.SampleRubyTests;
import com.liferay.portalweb.plugins.sampleservicebuilder.SampleServiceBuilderTests;
import com.liferay.portalweb.plugins.samplesignin.SampleSignInTests;
import com.liferay.portalweb.plugins.samplespring.SampleSpringTests;
import com.liferay.portalweb.plugins.samplestruts.SampleStrutsTests;
import com.liferay.portalweb.plugins.sampletapestry.SampleTapestryTests;
import com.liferay.portalweb.plugins.sampleuisearchcontainertaglib.SampleUISearchContainerTaglibTests;
import com.liferay.portalweb.plugins.sampleuitaglibs.SampleUITagLibsTests;
import com.liferay.portalweb.plugins.samplewap.SampleWAPTests;
import com.liferay.portalweb.plugins.socialcoding.SocialCodingTests;
import com.liferay.portalweb.plugins.socialnetworking.SocialNetworkingTests;
import com.liferay.portalweb.plugins.stocks.StocksTests;
import com.liferay.portalweb.plugins.suntouripc.SunTourIPCTests;
import com.liferay.portalweb.plugins.testclp.TestCLPTestPlan;
import com.liferay.portalweb.plugins.testdependency.TestDependencyTests;
import com.liferay.portalweb.plugins.testhook.TestHookTests;
import com.liferay.portalweb.plugins.testlocalized.TestLocalizedTests;
import com.liferay.portalweb.plugins.testmisc.TestMiscTests;
import com.liferay.portalweb.plugins.testworkflow.TestWorkflowTests;
import com.liferay.portalweb.plugins.todayinchristianhistory.TodayinChristianHistoryTests;
import com.liferay.portalweb.plugins.twitter.TwitterTests;
import com.liferay.portalweb.plugins.weather.WeatherTests;
import com.liferay.portalweb.plugins.webform.WebFormTests;
import com.liferay.portalweb.plugins.westminstercatechism.WestminsterCatechismTests;
import com.liferay.portalweb.plugins.wikinavigation.WikiNavigationTests;
import com.liferay.portalweb.plugins.wsrp.WSRPTestPlan;
import com.liferay.portalweb.plugins.wysiwyg.WysiwygTests;
import com.liferay.portalweb.portal.login.LoginPlugins2Tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class Plugins2TestSuite extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(LoginPlugins2Tests.suite());
		testSuite.addTest(SampleOrbeonFormsTests.suite());
		testSuite.addTest(SamplePermissionsTests.suite());
		testSuite.addTest(SamplePHPTests.suite());
		testSuite.addTest(SamplePortalClientTests.suite());
		testSuite.addTest(SamplePortalServiceTests.suite());
		testSuite.addTest(SamplePythonTests.suite());
		testSuite.addTest(SampleRubyTests.suite());
		testSuite.addTest(SampleServiceBuilderTests.suite());
		testSuite.addTest(SampleSignInTests.suite());
		testSuite.addTest(SampleSpringTests.suite());
		testSuite.addTest(SampleStrutsTests.suite());
		//testSuite.addTest(SampleStrutsLiferayTests.suite());
		testSuite.addTest(SampleTapestryTests.suite());
		testSuite.addTest(SampleUISearchContainerTaglibTests.suite());
		testSuite.addTest(SampleUITagLibsTests.suite());
		testSuite.addTest(SampleWAPTests.suite());
		testSuite.addTest(SocialCodingTests.suite());
		testSuite.addTest(SocialNetworkingTests.suite());
		testSuite.addTest(StocksTests.suite());
		testSuite.addTest(SunTourIPCTests.suite());
		testSuite.addTest(TestCLPTestPlan.suite());
		testSuite.addTest(TestDependencyTests.suite());
		testSuite.addTest(TestHookTests.suite());
		testSuite.addTest(TestLocalizedTests.suite());
		testSuite.addTest(TestMiscTests.suite());
		testSuite.addTest(TestWorkflowTests.suite());
		testSuite.addTest(TodayinChristianHistoryTests.suite());
		testSuite.addTest(TwitterTests.suite());
		testSuite.addTest(WeatherTests.suite());
		testSuite.addTest(WebFormTests.suite());
		testSuite.addTest(WestminsterCatechismTests.suite());
		testSuite.addTest(WikiNavigationTests.suite());
		testSuite.addTest(WSRPTestPlan.suite());
		testSuite.addTest(WysiwygTests.suite());

		testSuite.addTestSuite(StopSeleniumTest.class);

		return testSuite;
	}

}