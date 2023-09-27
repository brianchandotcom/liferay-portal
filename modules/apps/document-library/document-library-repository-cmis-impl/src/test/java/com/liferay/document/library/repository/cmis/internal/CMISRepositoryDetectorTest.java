/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.repository.cmis.internal;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Iván Zaera
 */
public class CMISRepositoryDetectorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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

		RepositoryInfo repositoryInfo = Mockito.mock(RepositoryInfo.class);

		Mockito.when(
			repositoryInfo.getProductName()
		).thenReturn(
			productName
		);

		Mockito.when(
			repositoryInfo.getProductVersion()
		).thenReturn(
			productVersion
		);

		return repositoryInfo;
	}

}