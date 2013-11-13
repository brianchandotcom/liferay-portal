/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.cmis;

import com.liferay.portal.repository.cmis.CMISRepositoryDetector;
import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.junit.Test;

import static org.jgroups.util.Util.assertFalse;
import static org.jgroups.util.Util.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Ivan Zaera
 */
public class CMISRepositoryDetectorTest {

	@Test
	public void testDetectionForNuxeo5_4() {
		CMISRepositoryDetector detector = _createDetectorFor("Nuxeo", "5.4");

		assertTrue(detector.isNuxeo5_4());
		assertFalse(detector.isNuxeo5_5OrHigher());
		assertFalse(detector.isNuxeo5_8OrHigher());
	}

	@Test
	public void testDetectionForNuxeo5_5() {
		CMISRepositoryDetector detector = _createDetectorFor("Nuxeo", "5.5");

		assertFalse(detector.isNuxeo5_4());
		assertTrue(detector.isNuxeo5_5OrHigher());
		assertFalse(detector.isNuxeo5_8OrHigher());
	}

	@Test
	public void testDetectionForNuxeo5_7() {
		CMISRepositoryDetector detector = _createDetectorFor("Nuxeo", "5.7");

		assertFalse(detector.isNuxeo5_4());
		assertTrue(detector.isNuxeo5_5OrHigher());
		assertFalse(detector.isNuxeo5_8OrHigher());
	}

	@Test
	public void testDetectionForNuxeo5_8() {
		CMISRepositoryDetector detector = _createDetectorFor("Nuxeo", "5.8");

		assertFalse(detector.isNuxeo5_4());
		assertTrue(detector.isNuxeo5_5OrHigher());
		assertTrue(detector.isNuxeo5_8OrHigher());
	}

	@Test
	public void testDetectionForNuxeo6_0() {
		CMISRepositoryDetector detector = _createDetectorFor("Nuxeo", "6.0");

		assertFalse(detector.isNuxeo5_4());
		assertTrue(detector.isNuxeo5_5OrHigher());
		assertTrue(detector.isNuxeo5_8OrHigher());
	}

	private CMISRepositoryDetector _createDetectorFor(
		String productName, String productVersion) {

		RepositoryInfo repInfo = _mockRepositoryInfo(
			productName, productVersion);

		return new CMISRepositoryDetector(repInfo);
	}

	private RepositoryInfo _mockRepositoryInfo(
		String productName, String productVersion) {

		RepositoryInfo repInfo = mock(RepositoryInfo.class);

		when(
			repInfo.getProductName()
		).thenReturn(
			productName
		);

		when(
			repInfo.getProductVersion()
		).thenReturn(
			productVersion
		);

		return repInfo;
	}

}