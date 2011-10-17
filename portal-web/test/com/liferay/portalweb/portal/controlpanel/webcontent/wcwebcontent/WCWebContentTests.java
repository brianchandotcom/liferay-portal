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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwebcontent.AddWebContentTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwebcontentapproved.AddWebContentApprovedTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwebcontentdraft.AddWebContentDraftTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwebcontentexpirationdate.AddWebContentExpirationDateTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwebcontentexpired.AddWebContentExpiredTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwebcontentlocalized.AddWebContentLocalizedTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwebcontentmultiple.AddWebContentMultipleTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwebcontentnameescapecharacter.AddWebContentNameEscapeCharacterTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwebcontentnamenull.AddWebContentNameNullTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwebcontentreviewdate.AddWebContentReviewDateTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.advancedsearchwebcontent.AdvancedSearchWebContentTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.approvewebcontentdraft.ApproveWebContentDraftTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.approvewebcontentexpired.ApproveWebContentExpiredTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.assertdisableddeletebuttonwebcontentcp.AssertDisabledDeleteButtonWebContentCPTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.assertdisabledexpirebuttonwebcontentcp.AssertDisabledExpireButtonWebContentCPTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.deletewebcontentaction.DeleteWebContentActionTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.deletewebcontentnameescapecharacteraction.DeleteWebContentNameEscapeCharacterActionTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.expirewebcontentapproved.ExpireWebContentApprovedTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.incrementversionwebcontentapproved.IncrementVersionWebContentApprovedTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.searchwebcontent.SearchWebContentTests;
import com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.viewwebcontentrecent.ViewWebContentRecentTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class WCWebContentTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddWebContentTests.suite());
		testSuite.addTest(AddWebContentApprovedTests.suite());
		testSuite.addTest(AddWebContentDraftTests.suite());
		testSuite.addTest(AddWebContentExpirationDateTests.suite());
		testSuite.addTest(AddWebContentExpiredTests.suite());
		testSuite.addTest(AddWebContentLocalizedTests.suite());
		testSuite.addTest(AddWebContentMultipleTests.suite());
		testSuite.addTest(AddWebContentNameEscapeCharacterTests.suite());
		testSuite.addTest(AddWebContentNameNullTests.suite());
		testSuite.addTest(AddWebContentReviewDateTests.suite());
		testSuite.addTest(AdvancedSearchWebContentTests.suite());
		testSuite.addTest(ApproveWebContentDraftTests.suite());
		testSuite.addTest(ApproveWebContentExpiredTests.suite());
		testSuite.addTest(AssertDisabledDeleteButtonWebContentCPTests.suite());
		testSuite.addTest(AssertDisabledExpireButtonWebContentCPTests.suite());
		testSuite.addTest(DeleteWebContentActionTests.suite());
		testSuite.addTest(
			DeleteWebContentNameEscapeCharacterActionTests.suite());
		testSuite.addTest(ExpireWebContentApprovedTests.suite());
		testSuite.addTest(IncrementVersionWebContentApprovedTests.suite());
		testSuite.addTest(SearchWebContentTests.suite());
		testSuite.addTest(ViewWebContentRecentTests.suite());

		return testSuite;
	}

}