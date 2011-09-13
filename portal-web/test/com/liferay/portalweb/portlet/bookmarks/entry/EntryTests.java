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

package com.liferay.portalweb.portlet.bookmarks.entry;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.bookmarks.entry.addfolderentry.AddFolderEntryTests;
import com.liferay.portalweb.portlet.bookmarks.entry.addfolderentrymultiple.AddFolderEntryMultipleTests;
import com.liferay.portalweb.portlet.bookmarks.entry.addfolderentrynamenull.AddFolderEntryNameNullTests;
import com.liferay.portalweb.portlet.bookmarks.entry.addfolderentryurlinvalid.AddFolderEntryURLInvalidTests;
import com.liferay.portalweb.portlet.bookmarks.entry.addfolderentryurlnull.AddFolderEntryURLNullTests;
import com.liferay.portalweb.portlet.bookmarks.entry.addsubfolderentry.AddSubfolderEntryTests;
import com.liferay.portalweb.portlet.bookmarks.entry.deletefolderentry.DeleteFolderEntryTests;
import com.liferay.portalweb.portlet.bookmarks.entry.editfolderentry.EditFolderEntryTests;
import com.liferay.portalweb.portlet.bookmarks.entry.guestviewpermissionsbookmarksentryguestviewoff.Guest_ViewPermissionsBookmarksEntryGuestViewOffTests;
import com.liferay.portalweb.portlet.bookmarks.entry.movefolderentrytofolder.MoveFolderEntryToFolderTests;
import com.liferay.portalweb.portlet.bookmarks.entry.movefolderentrytosubfolder.MoveFolderEntryToSubfolderTests;
import com.liferay.portalweb.portlet.bookmarks.entry.movesubfolderentrytofolder.MoveSubfolderEntryToFolderTests;
import com.liferay.portalweb.portlet.bookmarks.entry.movesubfolderentrytosubfolder.MoveSubfolderEntryToSubfolderTests;
import com.liferay.portalweb.portlet.bookmarks.entry.searchfolderentry.SearchFolderEntryTests;
import com.liferay.portalweb.portlet.bookmarks.entry.viewfolderentrymyentries.ViewFolderEntryMyEntriesTests;
import com.liferay.portalweb.portlet.bookmarks.entry.viewfolderentryrecententries.ViewFolderEntryRecentEntriesTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class EntryTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddFolderEntryTests.suite());
		testSuite.addTest(AddFolderEntryMultipleTests.suite());
		testSuite.addTest(AddFolderEntryNameNullTests.suite());
		testSuite.addTest(AddFolderEntryURLInvalidTests.suite());
		testSuite.addTest(AddFolderEntryURLNullTests.suite());
		testSuite.addTest(AddSubfolderEntryTests.suite());
		testSuite.addTest(DeleteFolderEntryTests.suite());
		testSuite.addTest(EditFolderEntryTests.suite());
		testSuite.addTest(
			Guest_ViewPermissionsBookmarksEntryGuestViewOffTests.suite());
		testSuite.addTest(MoveFolderEntryToFolderTests.suite());
		testSuite.addTest(MoveFolderEntryToSubfolderTests.suite());
		testSuite.addTest(MoveSubfolderEntryToFolderTests.suite());
		testSuite.addTest(MoveSubfolderEntryToSubfolderTests.suite());
		testSuite.addTest(SearchFolderEntryTests.suite());
		testSuite.addTest(ViewFolderEntryMyEntriesTests.suite());
		testSuite.addTest(ViewFolderEntryRecentEntriesTests.suite());

		return testSuite;
	}

}