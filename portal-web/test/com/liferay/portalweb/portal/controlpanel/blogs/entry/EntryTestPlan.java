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

package com.liferay.portalweb.portal.controlpanel.blogs.entry;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.blogs.entry.addblogsentryautodraftcp.AddBlogsEntryAutoDraftCPTests;
import com.liferay.portalweb.portal.controlpanel.blogs.entry.addblogsentrycontentnullcp.AddBlogsEntryContentNullCPTests;
import com.liferay.portalweb.portal.controlpanel.blogs.entry.addblogsentryckeditorcp.AddBlogsEntryCKEditorCPTests;
import com.liferay.portalweb.portal.controlpanel.blogs.entry.addblogsentrycp.AddBlogsEntryCPTests;
import com.liferay.portalweb.portal.controlpanel.blogs.entry.addblogsentrydraftcp.AddBlogsEntryDraftCPTests;
import com.liferay.portalweb.portal.controlpanel.blogs.entry.addblogsentrymultiplecp.AddBlogsEntryMultipleCPTests;
import com.liferay.portalweb.portal.controlpanel.blogs.entry.addblogsentryratingcp.AddBlogsEntryRatingCPTests;
import com.liferay.portalweb.portal.controlpanel.blogs.entry.addblogsentrytitleescapecharactercp.AddBlogsEntryTitleEscapeCharacterCPTests;
import com.liferay.portalweb.portal.controlpanel.blogs.entry.addblogsentrytitlenullcp.AddBlogsEntryTitleNullCPTests;
import com.liferay.portalweb.portal.controlpanel.blogs.entry.deleteblogsentrycp.DeleteBlogsEntryCPTests;
import com.liferay.portalweb.portal.controlpanel.blogs.entry.deleteblogsentrytitleescapecharactercp.DeleteBlogsEntryTitleEscapeCharacterCPTests;
import com.liferay.portalweb.portal.controlpanel.blogs.entry.editblogsentrycontentcp.EditBlogsEntryContentCPTests;
import com.liferay.portalweb.portal.controlpanel.blogs.entry.editblogsentrytitlecp.EditBlogsEntryTitleCPTests;
import com.liferay.portalweb.portal.controlpanel.blogs.entry.publishblogsentrydraftcp.PublishBlogsEntryDraftCPTests;
import com.liferay.portalweb.portal.controlpanel.blogs.entry.searchblogsentrycp.SearchBlogsEntryCPTests;
import com.liferay.portalweb.portal.controlpanel.blogs.entry.viewblogsentryviewcountcp.ViewBlogsEntryViewCountCPTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class EntryTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddBlogsEntryAutoDraftCPTests.suite());
		testSuite.addTest(AddBlogsEntryContentNullCPTests.suite());
		testSuite.addTest(AddBlogsEntryCKEditorCPTests.suite());
		testSuite.addTest(AddBlogsEntryCPTests.suite());
		testSuite.addTest(AddBlogsEntryDraftCPTests.suite());
		testSuite.addTest(AddBlogsEntryMultipleCPTests.suite());
		testSuite.addTest(AddBlogsEntryRatingCPTests.suite());
		testSuite.addTest(AddBlogsEntryTitleEscapeCharacterCPTests.suite());
		testSuite.addTest(AddBlogsEntryTitleNullCPTests.suite());
		testSuite.addTest(DeleteBlogsEntryCPTests.suite());
		testSuite.addTest(DeleteBlogsEntryTitleEscapeCharacterCPTests.suite());
		testSuite.addTest(EditBlogsEntryContentCPTests.suite());
		testSuite.addTest(EditBlogsEntryTitleCPTests.suite());
		testSuite.addTest(PublishBlogsEntryDraftCPTests.suite());
		testSuite.addTest(SearchBlogsEntryCPTests.suite());
		testSuite.addTest(ViewBlogsEntryViewCountCPTests.suite());

		return testSuite;
	}

}