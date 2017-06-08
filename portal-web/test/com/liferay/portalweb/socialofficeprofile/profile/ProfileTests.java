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

package com.liferay.portalweb.socialofficeprofile.profile;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.socialofficeprofile.profile.addusersoadditionalemailaddress.AddUserSOAdditionalEmailAddressTests;
import com.liferay.portalweb.socialofficeprofile.profile.addusersoaddress.AddUserSOAddressTests;
import com.liferay.portalweb.socialofficeprofile.profile.addusersoexpertise.AddUserSOExpertiseTests;
import com.liferay.portalweb.socialofficeprofile.profile.addusersoinstantmessenger.AddUserSOInstantMessengerTests;
import com.liferay.portalweb.socialofficeprofile.profile.addusersophonenumber.AddUserSOPhoneNumberTests;
import com.liferay.portalweb.socialofficeprofile.profile.addusersosms.AddUserSOSMSTests;
import com.liferay.portalweb.socialofficeprofile.profile.addusersosocialnetwork.AddUserSOSocialNetworkTests;
import com.liferay.portalweb.socialofficeprofile.profile.addusersowebsite.AddUserSOWebsiteTests;
import com.liferay.portalweb.socialofficeprofile.profile.viewprofileadditionalemailaddress.ViewProfileAdditionalEmailAddressTests;
import com.liferay.portalweb.socialofficeprofile.profile.viewprofileaddress.ViewProfileAddressTests;
import com.liferay.portalweb.socialofficeprofile.profile.viewprofileexpertise.ViewProfileExpertiseTests;
import com.liferay.portalweb.socialofficeprofile.profile.viewprofileinstantmessenger.ViewProfileInstantMessengerTests;
import com.liferay.portalweb.socialofficeprofile.profile.viewprofilephonenumber.ViewProfilePhoneNumberTests;
import com.liferay.portalweb.socialofficeprofile.profile.viewprofilesms.ViewProfileSMSTests;
import com.liferay.portalweb.socialofficeprofile.profile.viewprofilesocialnetwork.ViewProfileSocialNetworkTests;
import com.liferay.portalweb.socialofficeprofile.profile.viewprofilewebsite.ViewProfileWebsiteTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ProfileTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddUserSOAdditionalEmailAddressTests.suite());
		testSuite.addTest(AddUserSOAddressTests.suite());
		testSuite.addTest(AddUserSOExpertiseTests.suite());
		testSuite.addTest(AddUserSOInstantMessengerTests.suite());
		testSuite.addTest(AddUserSOPhoneNumberTests.suite());
		testSuite.addTest(AddUserSOSMSTests.suite());
		testSuite.addTest(AddUserSOSocialNetworkTests.suite());
		testSuite.addTest(AddUserSOWebsiteTests.suite());
		testSuite.addTest(ViewProfileAdditionalEmailAddressTests.suite());
		testSuite.addTest(ViewProfileAddressTests.suite());
		testSuite.addTest(ViewProfileExpertiseTests.suite());
		testSuite.addTest(ViewProfileInstantMessengerTests.suite());
		testSuite.addTest(ViewProfilePhoneNumberTests.suite());
		testSuite.addTest(ViewProfileSMSTests.suite());
		testSuite.addTest(ViewProfileSocialNetworkTests.suite());
		testSuite.addTest(ViewProfileWebsiteTests.suite());

		return testSuite;
	}

}