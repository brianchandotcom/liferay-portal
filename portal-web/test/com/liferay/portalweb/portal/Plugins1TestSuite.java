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

import com.liferay.portalweb.plugins.analogclock.AnalogClockTests;
import com.liferay.portalweb.plugins.assetmanagementsystem.AssetManagementSystemTestPlan;
import com.liferay.portalweb.plugins.biblegateway.BibleGatewayTests;
import com.liferay.portalweb.plugins.chat.ChatTests;
import com.liferay.portalweb.plugins.eventsdisplay.EventsDisplayTestPlan;
import com.liferay.portalweb.plugins.flash.FlashTests;
import com.liferay.portalweb.plugins.googleadsense.GoogleAdSenseTests;
import com.liferay.portalweb.plugins.googlemaps.GoogleMapsTests;
import com.liferay.portalweb.plugins.gospelforasia.GospelForAsiaTests;
import com.liferay.portalweb.plugins.ipgeocoder.IPGeocoderTests;
import com.liferay.portalweb.plugins.journalpolice.JournalPoliceTests;
import com.liferay.portalweb.plugins.mail.MailTestPlan;
import com.liferay.portalweb.plugins.novellcollaboration.NovellCollaborationTests;
import com.liferay.portalweb.plugins.opensocial.OpenSocialTests;
import com.liferay.portalweb.plugins.privatemessaging.PrivateMessagingTestPlan;
import com.liferay.portalweb.plugins.randombibleverse.RandomBibleVerseTests;
import com.liferay.portalweb.plugins.releasetools.ReleaseToolsTests;
import com.liferay.portalweb.plugins.rubyconsole.RubyConsoleTests;
import com.liferay.portalweb.plugins.sampledao.SampleDAOTests;
import com.liferay.portalweb.plugins.samplegroovy.SampleGroovyTests;
import com.liferay.portalweb.plugins.samplehibernate.SampleHibernateTests;
import com.liferay.portalweb.plugins.sampleicefacesipcajaxpush.SampleIcefacesIPCAjaxPushTests;
import com.liferay.portalweb.plugins.sampleicefacesjsf11sunfacelets.SampleIcefacesJSF11SunFaceletsTests;
import com.liferay.portalweb.plugins.sampleicefacesjsf11sunjsp.SampleIcefacesJSF11SunJSPTests;
import com.liferay.portalweb.plugins.sampleicefacesjsf11sunmyfacesjsp.SampleIcefacesJSF11SunMyfacesJSPTests;
import com.liferay.portalweb.plugins.sampleicefacesjsf12sunfacelets.SampleIcefacesJSF12SunFaceletsTests;
import com.liferay.portalweb.plugins.samplejsf11myfacesfacelets.SampleJSF11MyfacesFaceletsTests;
import com.liferay.portalweb.plugins.samplejsf11myfacesjsp.SampleJSF11MyfacesJSPTests;
import com.liferay.portalweb.plugins.samplejsf11sunfacelets.SampleJSF11SunFaceletsTests;
import com.liferay.portalweb.plugins.samplejsf11sunjsp.SampleJSF11SunJSPTests;
import com.liferay.portalweb.plugins.samplejsf12sunfacelets.SampleJSF12SunFaceletsTests;
import com.liferay.portalweb.plugins.samplejsf12sunjsp.SampleJSF12SunJSPTests;
import com.liferay.portalweb.plugins.samplejson.SampleJSONTests;
import com.liferay.portalweb.plugins.samplejsp.SampleJSPTests;
import com.liferay.portalweb.plugins.samplelar.SampleLARTests;
import com.liferay.portalweb.plugins.samplelaszlo.SampleLaszloTests;
import com.liferay.portalweb.portal.login.LoginTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class Plugins1TestSuite extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(LoginTests.suite());
		testSuite.addTest(AnalogClockTests.suite());
		testSuite.addTest(AssetManagementSystemTestPlan.suite());
		testSuite.addTest(BibleGatewayTests.suite());
		testSuite.addTest(ChatTests.suite());
		testSuite.addTest(EventsDisplayTestPlan.suite());
		testSuite.addTest(FlashTests.suite());
		testSuite.addTest(GoogleAdSenseTests.suite());
		testSuite.addTest(GoogleMapsTests.suite());
		testSuite.addTest(GospelForAsiaTests.suite());
		testSuite.addTest(IPGeocoderTests.suite());
		testSuite.addTest(JournalPoliceTests.suite());
		testSuite.addTest(MailTestPlan.suite());
		testSuite.addTest(NovellCollaborationTests.suite());
		testSuite.addTest(OpenSocialTests.suite());
		testSuite.addTest(PrivateMessagingTestPlan.suite());
		testSuite.addTest(RandomBibleVerseTests.suite());
		testSuite.addTest(ReleaseToolsTests.suite());
		testSuite.addTest(RubyConsoleTests.suite());
		testSuite.addTest(SampleDAOTests.suite());
		testSuite.addTest(SampleGroovyTests.suite());
		testSuite.addTest(SampleHibernateTests.suite());
		testSuite.addTest(SampleIcefacesIPCAjaxPushTests.suite());
		testSuite.addTest(SampleIcefacesJSF11SunFaceletsTests.suite());
		testSuite.addTest(SampleIcefacesJSF11SunJSPTests.suite());
		testSuite.addTest(SampleIcefacesJSF11SunMyfacesJSPTests.suite());
		testSuite.addTest(SampleIcefacesJSF12SunFaceletsTests.suite());
		testSuite.addTest(SampleJSF11MyfacesFaceletsTests.suite());
		testSuite.addTest(SampleJSF11MyfacesJSPTests.suite());
		testSuite.addTest(SampleJSF11SunFaceletsTests.suite());
		testSuite.addTest(SampleJSF11SunJSPTests.suite());
		testSuite.addTest(SampleJSF12SunFaceletsTests.suite());
		testSuite.addTest(SampleJSF12SunJSPTests.suite());
		testSuite.addTest(SampleJSONTests.suite());
		testSuite.addTest(SampleJSPTests.suite());
		testSuite.addTest(SampleLARTests.suite());
		testSuite.addTest(SampleLaszloTests.suite());

		testSuite.addTestSuite(StopSeleniumTest.class);

		return testSuite;
	}

}