/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.document.library.repository.cmis.internal;

import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;

import org.junit.Assert;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Iván Zaera
 */
public class CMISRepositoryDetectorTest extends PowerMockito {

	@Test
	public void testCMISDetectorWhenNuxeo5_4() {
		CMISRepositoryDetector cmisRepositoryDetector =
			getCMISRepositoryDetector("Nuxeo", "5.4");

		Assert.assertTrue(cmisRepositoryDetector.isNuxeo5_4());
		Assert.assertFalse(cmisRepositoryDetector.isNuxeo5_5OrHigher());
		Assert.assertFalse(cmisRepositoryDetector.isNuxeo5_8OrHigher());
	}

	@Test
	public void testCMISDetectorWhenNuxeo5_5() {
		CMISRepositoryDetector cmisRepositoryDetector =
			getCMISRepositoryDetector("Nuxeo", "5.5");

		Assert.assertFalse(cmisRepositoryDetector.isNuxeo5_4());
		Assert.assertTrue(cmisRepositoryDetector.isNuxeo5_5OrHigher());
		Assert.assertFalse(cmisRepositoryDetector.isNuxeo5_8OrHigher());
	}

	@Test
	public void testCMISDetectorWhenNuxeo5_7() {
		CMISRepositoryDetector cmisRepositoryDetector =
			getCMISRepositoryDetector("Nuxeo", "5.7");

		Assert.assertFalse(cmisRepositoryDetector.isNuxeo5_4());
		Assert.assertTrue(cmisRepositoryDetector.isNuxeo5_5OrHigher());
		Assert.assertFalse(cmisRepositoryDetector.isNuxeo5_8OrHigher());
	}

	@Test
	public void testCMISDetectorWhenNuxeo5_8() {
		CMISRepositoryDetector cmisRepositoryDetector =
			getCMISRepositoryDetector("Nuxeo", "5.8");

		Assert.assertFalse(cmisRepositoryDetector.isNuxeo5_4());
		Assert.assertTrue(cmisRepositoryDetector.isNuxeo5_5OrHigher());
		Assert.assertTrue(cmisRepositoryDetector.isNuxeo5_8OrHigher());
	}

	@Test
	public void testCMISDetectorWhenNuxeo6_0() {
		CMISRepositoryDetector cmisRepositoryDetector =
			getCMISRepositoryDetector("Nuxeo", "6.0");

		Assert.assertFalse(cmisRepositoryDetector.isNuxeo5_4());
		Assert.assertTrue(cmisRepositoryDetector.isNuxeo5_5OrHigher());
		Assert.assertTrue(cmisRepositoryDetector.isNuxeo5_8OrHigher());
	}

	protected CMISRepositoryDetector getCMISRepositoryDetector(
		String productName, String productVersion) {

		RepositoryInfo repositoryInfo = getRepositoryInfo(
			productName, productVersion);

		return new CMISRepositoryDetector(repositoryInfo);
	}

	protected RepositoryInfo getRepositoryInfo(
		String productName, String productVersion) {

		RepositoryInfo repositoryInfo = mock(RepositoryInfo.class);

		when(
			repositoryInfo.getProductName()
		).thenReturn(
			productName
		);

		when(
			repositoryInfo.getProductVersion()
		).thenReturn(
			productVersion
		);

		return repositoryInfo;
	}

}