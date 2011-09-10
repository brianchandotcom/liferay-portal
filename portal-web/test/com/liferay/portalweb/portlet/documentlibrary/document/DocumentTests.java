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

package com.liferay.portalweb.portlet.documentlibrary.document;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.documentlibrary.document.addfolderdocument.AddFolderDocumentTests;
import com.liferay.portalweb.portlet.documentlibrary.document.addfolderdocumentdocumentnull.AddFolderDocumentDocumentNullTests;
import com.liferay.portalweb.portlet.documentlibrary.document.addfolderdocumentmultiple.AddFolderDocumentMultipleTests;
import com.liferay.portalweb.portlet.documentlibrary.document.addfolderdocumentnull.AddFolderDocumentNullTests;
import com.liferay.portalweb.portlet.documentlibrary.document.addfolderdocumenttitleduplicate.AddFolderDocumentTitleDuplicateTests;
import com.liferay.portalweb.portlet.documentlibrary.document.addfolderdocumenttitlenull.AddFolderDocumentTitleNullTests;
import com.liferay.portalweb.portlet.documentlibrary.document.addsubfolderdocument.AddSubfolderDocumentTests;
import com.liferay.portalweb.portlet.documentlibrary.document.comparefolderdocumentversion.CompareFolderDocumentVersionTests;
import com.liferay.portalweb.portlet.documentlibrary.document.deletefolderdocument.DeleteFolderDocumentTests;
import com.liferay.portalweb.portlet.documentlibrary.document.editdlfolderdocumenttitleapostrophe.EditDLFolderDocumentTitleApostropheTests;
import com.liferay.portalweb.portlet.documentlibrary.document.editdlfolderdocumenttitlebackslash.EditDLFolderDocumentTitleBackSlashTests;
import com.liferay.portalweb.portlet.documentlibrary.document.editdlfolderdocumenttitleclosebracket.EditDLFolderDocumentTitleCloseBracketTests;
import com.liferay.portalweb.portlet.documentlibrary.document.editdlfolderdocumenttitlecolon.EditDLFolderDocumentTitleColonTests;
import com.liferay.portalweb.portlet.documentlibrary.document.editdlfolderdocumenttitleforwardslash.EditDLFolderDocumentTitleForwardSlashTests;
import com.liferay.portalweb.portlet.documentlibrary.document.editdlfolderdocumenttitlegreaterthan.EditDLFolderDocumentTitleGreaterThanTests;
import com.liferay.portalweb.portlet.documentlibrary.document.editdlfolderdocumenttitlelessthan.EditDLFolderDocumentTitleLessThanTests;
import com.liferay.portalweb.portlet.documentlibrary.document.editdlfolderdocumenttitleopenbracket.EditDLFolderDocumentTitleOpenBracketTests;
import com.liferay.portalweb.portlet.documentlibrary.document.editdlfolderdocumenttitlepipe.EditDLFolderDocumentTitlePipeTests;
import com.liferay.portalweb.portlet.documentlibrary.document.editdlfolderdocumenttitlequestion.EditDLFolderDocumentTitleQuestionTests;
import com.liferay.portalweb.portlet.documentlibrary.document.editdlfolderdocumenttitlequote.EditDLFolderDocumentTitleQuoteTests;
import com.liferay.portalweb.portlet.documentlibrary.document.editdlfolderdocumenttitlestar.EditDLFolderDocumentTitleStarTests;
import com.liferay.portalweb.portlet.documentlibrary.document.editfolderdocument.EditFolderDocumentTests;
import com.liferay.portalweb.portlet.documentlibrary.document.guestviewpermissionsdldocumentguestviewoff.Guest_ViewPermissionsDLDocumentGuestViewOffTests;
import com.liferay.portalweb.portlet.documentlibrary.document.lockfolderdocument.LockFolderDocumentTests;
import com.liferay.portalweb.portlet.documentlibrary.document.movefolderdocumentduplicatetofolder.MoveFolderDocumentDuplicateToFolderTests;
import com.liferay.portalweb.portlet.documentlibrary.document.movefolderdocumenttofolder.MoveFolderDocumentToFolderTests;
import com.liferay.portalweb.portlet.documentlibrary.document.searchfolderdocument.SearchFolderDocumentTests;
import com.liferay.portalweb.portlet.documentlibrary.document.unlockfolderdocument.UnlockFolderDocumentTests;
import com.liferay.portalweb.portlet.documentlibrary.document.viewfolderdocumentmydocuments.ViewFolderDocumentMyDocumentsTests;
import com.liferay.portalweb.portlet.documentlibrary.document.viewfolderdocumentrecentdocuments.ViewFolderDocumentRecentDocumentsTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DocumentTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddFolderDocumentTests.suite());
		testSuite.addTest(AddFolderDocumentDocumentNullTests.suite());
		testSuite.addTest(AddFolderDocumentMultipleTests.suite());
		testSuite.addTest(AddFolderDocumentNullTests.suite());
		testSuite.addTest(AddFolderDocumentTitleDuplicateTests.suite());
		testSuite.addTest(AddFolderDocumentTitleNullTests.suite());
		testSuite.addTest(AddSubfolderDocumentTests.suite());
		testSuite.addTest(CompareFolderDocumentVersionTests.suite());
		testSuite.addTest(DeleteFolderDocumentTests.suite());
		testSuite.addTest(EditDLFolderDocumentTitleApostropheTests.suite());
		testSuite.addTest(EditDLFolderDocumentTitleBackSlashTests.suite());
		testSuite.addTest(EditDLFolderDocumentTitleCloseBracketTests.suite());
		testSuite.addTest(EditDLFolderDocumentTitleColonTests.suite());
		testSuite.addTest(EditDLFolderDocumentTitleForwardSlashTests.suite());
		testSuite.addTest(EditDLFolderDocumentTitleGreaterThanTests.suite());
		testSuite.addTest(EditDLFolderDocumentTitleLessThanTests.suite());
		testSuite.addTest(EditDLFolderDocumentTitleOpenBracketTests.suite());
		testSuite.addTest(EditDLFolderDocumentTitlePipeTests.suite());
		testSuite.addTest(EditDLFolderDocumentTitleQuestionTests.suite());
		testSuite.addTest(EditDLFolderDocumentTitleQuoteTests.suite());
		testSuite.addTest(EditDLFolderDocumentTitleStarTests.suite());
		testSuite.addTest(EditFolderDocumentTests.suite());
		testSuite.addTest(
			Guest_ViewPermissionsDLDocumentGuestViewOffTests.suite());
		testSuite.addTest(LockFolderDocumentTests.suite());
		testSuite.addTest(MoveFolderDocumentDuplicateToFolderTests.suite());
		testSuite.addTest(MoveFolderDocumentToFolderTests.suite());
		testSuite.addTest(SearchFolderDocumentTests.suite());
		testSuite.addTest(UnlockFolderDocumentTests.suite());
		testSuite.addTest(ViewFolderDocumentMyDocumentsTests.suite());
		testSuite.addTest(ViewFolderDocumentRecentDocumentsTests.suite());

		return testSuite;
	}

}