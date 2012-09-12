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

package com.liferay.portalweb.demo.media.dmdocumenttypemusic;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.documentsandmedia.dmdocument.adddmdocument.TearDownDMDocumentTest;
import com.liferay.portalweb.portlet.documentsandmedia.dmfolder.adddmfolder.AddDMFolderTest;
import com.liferay.portalweb.portlet.documentsandmedia.dmfolder.adddmfolder.TearDownDMFolderTest;
import com.liferay.portalweb.portlet.documentsandmedia.portlet.addportletdm.AddPageDMTest;
import com.liferay.portalweb.portlet.documentsandmedia.portlet.addportletdm.AddPortletDMTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class DMDocumentTypeMusicTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageDMTest.class);
		testSuite.addTestSuite(AddPortletDMTest.class);
		testSuite.addTestSuite(ConfigureDMMaximumFileSizeCPTest.class);
		testSuite.addTestSuite(AddDMMetadataSetSongInformationTest.class);
		testSuite.addTestSuite(AddDMDocumentTypeMusicTest.class);
		testSuite.addTestSuite(AddDMFolderTest.class);
		testSuite.addTestSuite(AddDMFolderMusicArtistNullTest.class);
		testSuite.addTestSuite(AddDMFolderMusicTest.class);
		testSuite.addTestSuite(AddDMFolderMusicTagMP3Test.class);
		testSuite.addTestSuite(AddDMFolderMusicTagMusicTest.class);
		testSuite.addTestSuite(ViewDMFolderMusicTest.class);
		testSuite.addTestSuite(SearchDMMusicTest.class);
		testSuite.addTestSuite(TearDownDMFolderTest.class);
		testSuite.addTestSuite(TearDownDMDocumentTest.class);
		testSuite.addTestSuite(TearDownDMDocumentTypeTest.class);
		testSuite.addTestSuite(TearDownDMMetadataSetTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}