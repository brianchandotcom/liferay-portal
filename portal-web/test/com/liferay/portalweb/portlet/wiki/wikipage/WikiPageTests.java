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

package com.liferay.portalweb.portlet.wiki.wikipage;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addchildpage1childpageduplicatechildpage2.AddChildPage1ChildPageDuplicateChildPage2Tests;
import com.liferay.portalweb.portlet.wiki.wikipage.addfrontpage.AddFrontPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addfrontpagechildpage.AddFrontPageChildPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addfrontpagechildpagemultiple.AddFrontPageChildPageMultipleTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addfrontpagechildpagename255character.AddFrontPageChildPageName255CharacterTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addfrontpagechildpagenameduplicate.AddFrontPageChildPageNameDuplicateTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addfrontpagechildpagenamenull.AddFrontPageChildPageNameNullTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addfrontpagechildpagenamesymbol.AddFrontPageChildPageNameSymbolTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addfrontpagecontentjavascript.AddFrontPageContentJavascriptTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addfrontpagecreolebold.AddFrontPageCreoleBoldTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addfrontpagecreolebolditalics.AddFrontPageCreoleBoldItalicsTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addfrontpagecreoleexternallinks.AddFrontPageCreoleExternalLinksTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addfrontpagecreoleheaders.AddFrontPageCreoleHeadersTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addfrontpagecreoleimage.AddFrontPageCreoleImageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addfrontpagecreoleinternallinks.AddFrontPageCreoleInternalLinksTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addfrontpagecreoleitalics.AddFrontPageCreoleItalicsTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addfrontpagecreolelinkedimage.AddFrontPageCreoleLinkedImageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addfrontpagecreoleolists.AddFrontPageCreoleOListsTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addfrontpagecreolepartialbolditalics.AddFrontPageCreolePartialBoldItalicsTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addfrontpagecreolepreformatted.AddFrontPageCreolePreformattedTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addfrontpagecreoletableofcontents.AddFrontPageCreoleTableOfContentsTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addfrontpagecreoleulists.AddFrontPageCreoleUListsTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addfrontpagesearch.AddFrontPageSearchTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addwikipage.AddWikiPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addwikipagechildpage.AddWikiPageChildPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addwikipagechildpagemultiple.AddWikiPageChildPageMultipleTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addwikipageempty.AddWikiPageEmptyTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addwikipageformathtml.AddWikiPageFormatHTMLTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addwikipagemultiple.AddWikiPageMultipleTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addwikipagename255character.AddWikiPageName255CharacterTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addwikipagename255characterchildpage.AddWikiPageName255CharacterChildPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addwikipagename255characterchildpagemultiple.AddWikiPageName255CharacterChildPageMultipleTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addwikipagename256character.AddWikiPageName256CharacterTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addwikipagenamebackslash.AddWikiPageNameBackSlashTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addwikipagenameduplicate.AddWikiPageNameDuplicateTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addwikipagenamenull.AddWikiPageNameNullTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addwikipagenamesymbol.AddWikiPageNameSymbolTests;
import com.liferay.portalweb.portlet.wiki.wikipage.addwikipagesearch.AddWikiPageSearchTests;
import com.liferay.portalweb.portlet.wiki.wikipage.canceladdfrontpage.CancelAddFrontPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.canceladdfrontpagechildpage.CancelAddFrontPageChildPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.changeparentfrontpagechildpage255tonone.ChangeParentFrontPageChildPage255ToNoneTests;
import com.liferay.portalweb.portlet.wiki.wikipage.changeparentfrontpagechildpage255towikipage.ChangeParentFrontPageChildPage255ToWikiPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.changeparentfrontpagechildpagetonone.ChangeParentFrontPageChildPageToNoneTests;
import com.liferay.portalweb.portlet.wiki.wikipage.changeparentfrontpagechildpagetowikipage.ChangeParentFrontPageChildPageToWikiPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.changeparentwikipage255towikipage.ChangeParentWikiPage255ToWikiPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.changeparentwikipagetowikipage.ChangeParentWikiPageToWikiPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.compareversioneditfrontpage.CompareVersionEditFrontPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.compareversioneditfrontpageminorchange.CompareVersionEditFrontPageMinorChangeTests;
import com.liferay.portalweb.portlet.wiki.wikipage.copyfrontpage.CopyFrontPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.deletefrontpage.DeleteFrontPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.deletefrontpagechildpage.DeleteFrontPageChildPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.deletewikipage.DeleteWikiPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.deletewikipagechildpage.DeleteWikiPageChildPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.deletewikipagename255character.DeleteWikiPageName255CharacterTests;
import com.liferay.portalweb.portlet.wiki.wikipage.deletewikipagename255characterchildpage.DeleteWikiPageName255CharacterChildPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.editfrontpage.EditFrontPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.editfrontpageminorchange.EditFrontPageMinorChangeTests;
import com.liferay.portalweb.portlet.wiki.wikipage.linkwikipagetowikipage.LinkWikiPageToWikiPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.previewfrontpage.PreviewFrontPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.previewfrontpagechildpage.PreviewFrontPageChildPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.removeredirectwikipage.RemoveRedirectWikiPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.renamefrontpagechildpage.RenameFrontPageChildPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.renamewikipagetitle.RenameWikiPageTitleTests;
import com.liferay.portalweb.portlet.wiki.wikipage.renamewikipagetitleduplicate.RenameWikiPageTitleDuplicateTests;
import com.liferay.portalweb.portlet.wiki.wikipage.renamewikipagetitlenull.RenameWikiPageTitleNullTests;
import com.liferay.portalweb.portlet.wiki.wikipage.renamewikipagetitlesame.RenameWikiPageTitleSameTests;
import com.liferay.portalweb.portlet.wiki.wikipage.revertchangeparentfrontpagechildpagetonone.RevertChangeParentFrontPageChildPageToNoneTests;
import com.liferay.portalweb.portlet.wiki.wikipage.revertchangeparentfrontpagechildpagetowikipage.RevertChangeParentFrontPageChildPageToWikiPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.revertchangeparentwikipagetowikipage.RevertChangeParentWikiPageToWikiPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.reverteditfrontpage.RevertEditFrontPageTests;
import com.liferay.portalweb.portlet.wiki.wikipage.reverteditfrontpageminorchange.RevertEditFrontPageMinorChangeTests;
import com.liferay.portalweb.portlet.wiki.wikipage.searchrenamewikipagetitle.SearchRenameWikiPageTitleTests;
import com.liferay.portalweb.portlet.wiki.wikipage.searchwikipagecontent.SearchWikiPageContentTests;
import com.liferay.portalweb.portlet.wiki.wikipage.searchwikipagetitle.SearchWikiPageTitleTests;
import com.liferay.portalweb.portlet.wiki.wikipage.viewwikipageallpages.ViewWikiPageAllPagesTests;
import com.liferay.portalweb.portlet.wiki.wikipage.viewwikipageincominglinks.ViewWikiPageIncomingLinksTests;
import com.liferay.portalweb.portlet.wiki.wikipage.viewwikipageorphanpages.ViewWikiPageOrphanPagesTests;
import com.liferay.portalweb.portlet.wiki.wikipage.viewwikipageoutgoinglinks.ViewWikiPageOutgoingLinksTests;
import com.liferay.portalweb.portlet.wiki.wikipage.viewwikipagerecentchanges.ViewWikiPageRecentChangesTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class WikiPageTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(
			AddChildPage1ChildPageDuplicateChildPage2Tests.suite());
		testSuite.addTest(AddFrontPageTests.suite());
		testSuite.addTest(AddFrontPageChildPageTests.suite());
		testSuite.addTest(AddFrontPageChildPageMultipleTests.suite());
		testSuite.addTest(AddFrontPageChildPageName255CharacterTests.suite());
		testSuite.addTest(AddFrontPageChildPageNameDuplicateTests.suite());
		testSuite.addTest(AddFrontPageChildPageNameNullTests.suite());
		testSuite.addTest(AddFrontPageChildPageNameSymbolTests.suite());
		testSuite.addTest(AddFrontPageContentJavascriptTests.suite());
		testSuite.addTest(AddFrontPageCreoleBoldTests.suite());
		testSuite.addTest(AddFrontPageCreoleBoldItalicsTests.suite());
		testSuite.addTest(AddFrontPageCreoleExternalLinksTests.suite());
		testSuite.addTest(AddFrontPageCreoleHeadersTests.suite());
		testSuite.addTest(AddFrontPageCreoleImageTests.suite());
		testSuite.addTest(AddFrontPageCreoleInternalLinksTests.suite());
		testSuite.addTest(AddFrontPageCreoleItalicsTests.suite());
		testSuite.addTest(AddFrontPageCreoleLinkedImageTests.suite());
		testSuite.addTest(AddFrontPageCreoleOListsTests.suite());
		testSuite.addTest(AddFrontPageCreolePartialBoldItalicsTests.suite());
		testSuite.addTest(AddFrontPageCreolePreformattedTests.suite());
		testSuite.addTest(AddFrontPageCreoleTableOfContentsTests.suite());
		testSuite.addTest(AddFrontPageCreoleUListsTests.suite());
		testSuite.addTest(AddFrontPageSearchTests.suite());
		testSuite.addTest(AddWikiPageTests.suite());
		testSuite.addTest(AddWikiPageChildPageTests.suite());
		testSuite.addTest(AddWikiPageChildPageMultipleTests.suite());
		testSuite.addTest(AddWikiPageEmptyTests.suite());
		testSuite.addTest(AddWikiPageFormatHTMLTests.suite());
		testSuite.addTest(AddWikiPageMultipleTests.suite());
		testSuite.addTest(AddWikiPageName255CharacterTests.suite());
		testSuite.addTest(AddWikiPageName255CharacterChildPageTests.suite());
		testSuite.addTest(
			AddWikiPageName255CharacterChildPageMultipleTests.suite());
		testSuite.addTest(AddWikiPageName256CharacterTests.suite());
		testSuite.addTest(AddWikiPageNameBackSlashTests.suite());
		testSuite.addTest(AddWikiPageNameDuplicateTests.suite());
		testSuite.addTest(AddWikiPageNameNullTests.suite());
		testSuite.addTest(AddWikiPageNameSymbolTests.suite());
		testSuite.addTest(AddWikiPageSearchTests.suite());
		testSuite.addTest(CancelAddFrontPageTests.suite());
		testSuite.addTest(CancelAddFrontPageChildPageTests.suite());
		testSuite.addTest(ChangeParentFrontPageChildPage255ToNoneTests.suite());
		testSuite.addTest(
			ChangeParentFrontPageChildPage255ToWikiPageTests.suite());
		testSuite.addTest(ChangeParentFrontPageChildPageToNoneTests.suite());
		testSuite.addTest(
			ChangeParentFrontPageChildPageToWikiPageTests.suite());
		testSuite.addTest(ChangeParentWikiPage255ToWikiPageTests.suite());
		testSuite.addTest(ChangeParentWikiPageToWikiPageTests.suite());
		testSuite.addTest(CompareVersionEditFrontPageTests.suite());
		testSuite.addTest(CompareVersionEditFrontPageMinorChangeTests.suite());
		testSuite.addTest(CopyFrontPageTests.suite());
		testSuite.addTest(DeleteFrontPageTests.suite());
		testSuite.addTest(DeleteFrontPageChildPageTests.suite());
		testSuite.addTest(DeleteWikiPageTests.suite());
		testSuite.addTest(DeleteWikiPageChildPageTests.suite());
		testSuite.addTest(DeleteWikiPageName255CharacterTests.suite());
		testSuite.addTest(DeleteWikiPageName255CharacterChildPageTests.suite());
		testSuite.addTest(EditFrontPageTests.suite());
		testSuite.addTest(EditFrontPageMinorChangeTests.suite());
		testSuite.addTest(LinkWikiPageToWikiPageTests.suite());
		testSuite.addTest(PreviewFrontPageTests.suite());
		testSuite.addTest(PreviewFrontPageChildPageTests.suite());
		testSuite.addTest(RemoveRedirectWikiPageTests.suite());
		testSuite.addTest(RenameFrontPageChildPageTests.suite());
		testSuite.addTest(RenameWikiPageTitleTests.suite());
		testSuite.addTest(RenameWikiPageTitleDuplicateTests.suite());
		testSuite.addTest(RenameWikiPageTitleNullTests.suite());
		testSuite.addTest(RenameWikiPageTitleSameTests.suite());
		testSuite.addTest(
			RevertChangeParentFrontPageChildPageToNoneTests.suite());
		testSuite.addTest(
			RevertChangeParentFrontPageChildPageToWikiPageTests.suite());
		testSuite.addTest(RevertChangeParentWikiPageToWikiPageTests.suite());
		testSuite.addTest(RevertEditFrontPageTests.suite());
		testSuite.addTest(RevertEditFrontPageMinorChangeTests.suite());
		testSuite.addTest(SearchRenameWikiPageTitleTests.suite());
		testSuite.addTest(SearchWikiPageContentTests.suite());
		testSuite.addTest(SearchWikiPageTitleTests.suite());
		testSuite.addTest(ViewWikiPageAllPagesTests.suite());
		testSuite.addTest(ViewWikiPageIncomingLinksTests.suite());
		testSuite.addTest(ViewWikiPageOrphanPagesTests.suite());
		testSuite.addTest(ViewWikiPageOutgoingLinksTests.suite());
		testSuite.addTest(ViewWikiPageRecentChangesTests.suite());

		return testSuite;
	}

}