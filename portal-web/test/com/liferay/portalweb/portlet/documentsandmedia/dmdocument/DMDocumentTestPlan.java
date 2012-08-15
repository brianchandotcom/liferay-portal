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

package com.liferay.portalweb.portlet.documentsandmedia.dmdocument;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.adddmdocument.AddDMDocumentTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.adddmdocumentdoc.AddDMDocumentDocTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.adddmdocumentdocx.AddDMDocumentDocxTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.adddmdocumentjpeg.AddDMDocumentJpegTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.adddmdocumentjpg.AddDMDocumentJpgTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.adddmdocumentmp3.AddDMDocumentMp3Tests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.adddmdocumentodt.AddDMDocumentOdtTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.adddmdocumentpdf.AddDMDocumentPdfTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.adddmdocumentpng.AddDMDocumentPngTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.adddmdocumentxls.AddDMDocumentXlsTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.adddmdocumentxlsx.AddDMDocumentXlsxTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.adddmfolderdocument.AddDMFolderDocumentTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.adddmfolderdocumentdocumentnull.AddDMFolderDocumentDocumentNullTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.adddmfolderdocumentnull.AddDMFolderDocumentNullTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.adddmfolderdocuments.AddDMFolderDocumentsTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.adddmfolderdocumenttitleduplicate.AddDMFolderDocumentTitleDuplicateTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.adddmfolderdocumenttitlenull.AddDMFolderDocumentTitleNullTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.adddmsubfolderdocument.AddDMSubfolderDocumentTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.comparedmfolderdocumentversion.CompareDMFolderDocumentVersionTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.deletedmfolderdocument.DeleteDMFolderDocumentTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.deleteversiondmfolderdocumentfile10test.DeleteVersionDMFolderDocumentFile10Tests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.deleteversiondmfolderdocumentfile11test.DeleteVersionDMFolderDocumentFile11Tests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.deleteversiondmfolderdocumenttitle10test.DeleteVersionDMFolderDocumentTitle10Tests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.deleteversiondmfolderdocumenttitle11test.DeleteVersionDMFolderDocumentTitle11Tests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.editdmfolderdocument.EditDMFolderDocumentTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.editdmfolderdocumenttitleapostrophe.EditDMFolderDocumentTitleApostropheTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.editdmfolderdocumenttitlebackslash.EditDMFolderDocumentTitleBackSlashTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.editdmfolderdocumenttitleclosebracket.EditDMFolderDocumentTitleCloseBracketTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.editdmfolderdocumenttitlecolon.EditDMFolderDocumentTitleColonTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.editdmfolderdocumenttitleforwardslash.EditDMFolderDocumentTitleForwardSlashTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.editdmfolderdocumenttitlegreaterthan.EditDMFolderDocumentTitleGreaterThanTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.editdmfolderdocumenttitlelessthan.EditDMFolderDocumentTitleLessThanTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.editdmfolderdocumenttitleopenbracket.EditDMFolderDocumentTitleOpenBracketTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.editdmfolderdocumenttitlepipe.EditDMFolderDocumentTitlePipeTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.editdmfolderdocumenttitlequestion.EditDMFolderDocumentTitleQuestionTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.editdmfolderdocumenttitlequote.EditDMFolderDocumentTitleQuoteTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.editdmfolderdocumenttitlestar.EditDMFolderDocumentTitleStarTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.guestviewdmdocumentguestviewoff.Guest_ViewDMDocumentGuestViewOffTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.lockdmfolderdocument.LockDMFolderDocumentTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.movedmfolderdocumentduplicatetofolder.MoveDMFolderDocumentDuplicateToFolderTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.movedmfolderdocumenttofolder.MoveDMFolderDocumentToFolderTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.revertversiondmfolderdocumentfile10test.RevertVersionDMFolderDocumentFile10Tests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.revertversiondmfolderdocumenttitle10test.RevertVersionDMFolderDocumentTitle10Tests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.searchdmfolderdocument.SearchDMFolderDocumentTests;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.unlockdmfolderdocument.UnlockDMFolderDocumentTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DMDocumentTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddDMDocumentTests.suite());
		testSuite.addTest(AddDMDocumentDocTests.suite());
		testSuite.addTest(AddDMDocumentDocxTests.suite());
		testSuite.addTest(AddDMDocumentJpegTests.suite());
		testSuite.addTest(AddDMDocumentJpgTests.suite());
		testSuite.addTest(AddDMDocumentMp3Tests.suite());
		testSuite.addTest(AddDMDocumentOdtTests.suite());
		testSuite.addTest(AddDMDocumentPdfTests.suite());
		testSuite.addTest(AddDMDocumentPngTests.suite());
		testSuite.addTest(AddDMDocumentXlsTests.suite());
		testSuite.addTest(AddDMDocumentXlsxTests.suite());
		testSuite.addTest(AddDMFolderDocumentTests.suite());
		testSuite.addTest(AddDMFolderDocumentDocumentNullTests.suite());
		testSuite.addTest(AddDMFolderDocumentNullTests.suite());
		testSuite.addTest(AddDMFolderDocumentsTests.suite());
		testSuite.addTest(AddDMFolderDocumentTitleDuplicateTests.suite());
		testSuite.addTest(AddDMFolderDocumentTitleNullTests.suite());
		testSuite.addTest(AddDMSubfolderDocumentTests.suite());
		testSuite.addTest(CompareDMFolderDocumentVersionTests.suite());
		testSuite.addTest(DeleteDMFolderDocumentTests.suite());
		testSuite.addTest(DeleteVersionDMFolderDocumentFile10Tests.suite());
		testSuite.addTest(DeleteVersionDMFolderDocumentFile11Tests.suite());
		testSuite.addTest(DeleteVersionDMFolderDocumentTitle10Tests.suite());
		testSuite.addTest(DeleteVersionDMFolderDocumentTitle11Tests.suite());
		testSuite.addTest(EditDMFolderDocumentTitleApostropheTests.suite());
		testSuite.addTest(EditDMFolderDocumentTitleBackSlashTests.suite());
		testSuite.addTest(EditDMFolderDocumentTitleCloseBracketTests.suite());
		testSuite.addTest(EditDMFolderDocumentTitleColonTests.suite());
		testSuite.addTest(EditDMFolderDocumentTitleForwardSlashTests.suite());
		testSuite.addTest(EditDMFolderDocumentTitleGreaterThanTests.suite());
		testSuite.addTest(EditDMFolderDocumentTitleLessThanTests.suite());
		testSuite.addTest(EditDMFolderDocumentTitleOpenBracketTests.suite());
		testSuite.addTest(EditDMFolderDocumentTitlePipeTests.suite());
		testSuite.addTest(EditDMFolderDocumentTitleQuestionTests.suite());
		testSuite.addTest(EditDMFolderDocumentTitleQuoteTests.suite());
		testSuite.addTest(EditDMFolderDocumentTitleStarTests.suite());
		testSuite.addTest(EditDMFolderDocumentTests.suite());
		testSuite.addTest(Guest_ViewDMDocumentGuestViewOffTests.suite());
		testSuite.addTest(LockDMFolderDocumentTests.suite());
		testSuite.addTest(MoveDMFolderDocumentDuplicateToFolderTests.suite());
		testSuite.addTest(MoveDMFolderDocumentToFolderTests.suite());
		testSuite.addTest(RevertVersionDMFolderDocumentFile10Tests.suite());
		testSuite.addTest(RevertVersionDMFolderDocumentTitle10Tests.suite());
		testSuite.addTest(SearchDMFolderDocumentTests.suite());
		testSuite.addTest(UnlockDMFolderDocumentTests.suite());

		return testSuite;
	}

}