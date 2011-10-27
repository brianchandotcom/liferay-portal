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

package com.liferay.portalweb.portal.controlpanel.categories.vocabulary;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portal.controlpanel.categories.vocabulary.addvocabulary.AddVocabularyTests;
import com.liferay.portalweb.portal.controlpanel.categories.vocabulary.addvocabularyassettypeallassettypes.AddVocabularyAssetTypeAllAssetTypesTests;
import com.liferay.portalweb.portal.controlpanel.categories.vocabulary.addvocabularyassettypeblogsentry.AddVocabularyAssetTypeBlogsEntryTests;
import com.liferay.portalweb.portal.controlpanel.categories.vocabulary.addvocabularyassettypecalendarevent.AddVocabularyAssetTypeCalendarEventTests;
import com.liferay.portalweb.portal.controlpanel.categories.vocabulary.addvocabularyassettypecomments.AddVocabularyAssetTypeCommentsTests;
import com.liferay.portalweb.portal.controlpanel.categories.vocabulary.addvocabularyassettypeddlrecord.AddVocabularyAssetTypeDDLRecordTests;
import com.liferay.portalweb.portal.controlpanel.categories.vocabulary.addvocabularyassettypedldocument.AddVocabularyAssetTypeDLDocumentTests;
import com.liferay.portalweb.portal.controlpanel.categories.vocabulary.addvocabularyassettypembcategory.AddVocabularyAssetTypeMBCategoryTests;
import com.liferay.portalweb.portal.controlpanel.categories.vocabulary.addvocabularyassettypembmessage.AddVocabularyAssetTypeMBMessageTests;
import com.liferay.portalweb.portal.controlpanel.categories.vocabulary.addvocabularyassettypepagerevision.AddVocabularyAssetTypePageRevisionTests;
import com.liferay.portalweb.portal.controlpanel.categories.vocabulary.addvocabularyassettyperequired.AddVocabularyAssetTypeRequiredTests;
import com.liferay.portalweb.portal.controlpanel.categories.vocabulary.addvocabularyassettypeuser.AddVocabularyAssetTypeUserTests;
import com.liferay.portalweb.portal.controlpanel.categories.vocabulary.addvocabularyassettypewebcontent.AddVocabularyAssetTypeWebContentTests;
import com.liferay.portalweb.portal.controlpanel.categories.vocabulary.addvocabularyassettypewikipage.AddVocabularyAssetTypeWikiPageTests;
import com.liferay.portalweb.portal.controlpanel.categories.vocabulary.addvocabularydisallowmultiplecategories.AddVocabularyDisallowMultipleCategoriesTests;
import com.liferay.portalweb.portal.controlpanel.categories.vocabulary.addvocabularymultiple.AddVocabularyMultipleTests;
import com.liferay.portalweb.portal.controlpanel.categories.vocabulary.addvocabularynamenull.AddVocabularyNameNullTests;
import com.liferay.portalweb.portal.controlpanel.categories.vocabulary.addvocabularyviewablebyanyone.AddVocabularyViewableByAnyoneTests;
import com.liferay.portalweb.portal.controlpanel.categories.vocabulary.addvocabularyviewablebyowner.AddVocabularyViewableByOwnerTests;
import com.liferay.portalweb.portal.controlpanel.categories.vocabulary.addvocabularyviewablebysitemembers.AddVocabularyViewableBySiteMembersTests;
import com.liferay.portalweb.portal.controlpanel.categories.vocabulary.closevocabulary.CloseVocabularyTests;
import com.liferay.portalweb.portal.controlpanel.categories.vocabulary.deletevocabularyactions.DeleteVocabularyActionsTests;
import com.liferay.portalweb.portal.controlpanel.categories.vocabulary.deletevocabularydetails.DeleteVocabularyDetailsTests;
import com.liferay.portalweb.portal.controlpanel.categories.vocabulary.editvocabulary.EditVocabularyTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class VocabularyTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddVocabularyTests.suite());
		testSuite.addTest(AddVocabularyAssetTypeAllAssetTypesTests.suite());
		testSuite.addTest(AddVocabularyAssetTypeBlogsEntryTests.suite());
		testSuite.addTest(AddVocabularyAssetTypeCalendarEventTests.suite());
		testSuite.addTest(AddVocabularyAssetTypeCommentsTests.suite());
		testSuite.addTest(AddVocabularyAssetTypeDDLRecordTests.suite());
		testSuite.addTest(AddVocabularyAssetTypeDLDocumentTests.suite());
		testSuite.addTest(AddVocabularyAssetTypeMBCategoryTests.suite());
		testSuite.addTest(AddVocabularyAssetTypeMBMessageTests.suite());
		testSuite.addTest(AddVocabularyAssetTypePageRevisionTests.suite());
		testSuite.addTest(AddVocabularyAssetTypeRequiredTests.suite());
		testSuite.addTest(AddVocabularyAssetTypeUserTests.suite());
		testSuite.addTest(AddVocabularyAssetTypeWebContentTests.suite());
		testSuite.addTest(AddVocabularyAssetTypeWikiPageTests.suite());
		testSuite.addTest(
			AddVocabularyDisallowMultipleCategoriesTests.suite());
		testSuite.addTest(AddVocabularyMultipleTests.suite());
		testSuite.addTest(AddVocabularyNameNullTests.suite());
		testSuite.addTest(AddVocabularyViewableByAnyoneTests.suite());
		testSuite.addTest(AddVocabularyViewableByOwnerTests.suite());
		testSuite.addTest(AddVocabularyViewableBySiteMembersTests.suite());
		testSuite.addTest(CloseVocabularyTests.suite());
		testSuite.addTest(DeleteVocabularyActionsTests.suite());
		testSuite.addTest(DeleteVocabularyDetailsTests.suite());
		testSuite.addTest(EditVocabularyTests.suite());

		return testSuite;
	}

}