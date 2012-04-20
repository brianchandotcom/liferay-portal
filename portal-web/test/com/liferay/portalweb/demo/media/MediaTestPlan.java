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

package com.liferay.portalweb.demo.media;

import com.liferay.portalweb.demo.media.dmautomaticallyextractedmetadata.DMAutomaticallyExtractedMetaDataTests;
import com.liferay.portalweb.demo.media.dmcheckincheckoutdocument.DMCheckinCheckoutDocumentTests;
import com.liferay.portalweb.demo.media.dmdocumenttypemusic.DMDocumentTypeMusicTests;
import com.liferay.portalweb.demo.media.dmdraganddropdocument.DMDragAndDropDocumentTests;
import com.liferay.portalweb.demo.media.dmkaleo1workflow.DMKaleo1WorkflowTests;
import com.liferay.portalweb.demo.media.dmkaleo2workflow.DMKaleo2WorkflowTests;
import com.liferay.portalweb.demo.media.dmlardocumenttypemusic.DMLARDocumentTypeMusicTests;
import com.liferay.portalweb.demo.media.dmstagingdocumenttypemusic.DMStagingDocumentTypeMusicTests;
import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class MediaTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(DMAutomaticallyExtractedMetaDataTests.suite());
		testSuite.addTest(DMCheckinCheckoutDocumentTests.suite());
		testSuite.addTest(DMDocumentTypeMusicTests.suite());
		testSuite.addTest(DMDragAndDropDocumentTests.suite());
		testSuite.addTest(DMKaleo1WorkflowTests.suite());
		testSuite.addTest(DMKaleo2WorkflowTests.suite());
		testSuite.addTest(DMLARDocumentTypeMusicTests.suite());
		testSuite.addTest(DMStagingDocumentTypeMusicTests.suite());

		return testSuite;
	}

}