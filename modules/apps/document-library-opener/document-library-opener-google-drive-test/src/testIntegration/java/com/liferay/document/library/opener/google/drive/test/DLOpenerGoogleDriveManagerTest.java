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

package com.liferay.document.library.opener.google.drive.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.opener.google.drive.DLOpenerGoogleDriveManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.documentlibrary.util.test.DLTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class DLOpenerGoogleDriveManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_company = CompanyTestUtil.addCompany();
	}

	@Test
	public void testASimpleFileEntryIsNotAGoogleDriveFileEntry()
		throws Exception {

		DLFolder dlFolder = DLTestUtil.addDLFolder(_company.getGroupId());

		DLFileEntry dlFileEntry = DLTestUtil.addDLFileEntry(
			dlFolder.getFolderId());

		FileEntry fileEntry = _dlAppLocalService.getFileEntry(
			dlFileEntry.getFileEntryId());

		Assert.assertFalse(
			_dlOpenerGoogleDriveManager.isGoogleDriveFile(fileEntry));
	}

	@Test(expected = PortalException.class)
	public void testGetAuthorizationURLFailsIfThereIsNoAuthorizationCodeFlow()
		throws Exception {

		_dlOpenerGoogleDriveManager.getAuthorizationURL(
			_company.getCompanyId(), RandomTestUtil.randomString(),
			"http://localhost:8080");
	}

	@Test
	public void testHasInvalidCredentialByDefault() throws Exception {
		Assert.assertFalse(
			_dlOpenerGoogleDriveManager.hasValidCredential(
				_company.getCompanyId(), TestPropsValues.getUserId()));
	}

	@Test
	public void testIsNotConfiguredByDefault() {
		Assert.assertFalse(
			_dlOpenerGoogleDriveManager.isConfigured(_company.getCompanyId()));
	}

	@DeleteAfterTestRun
	private Company _company;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private DLOpenerGoogleDriveManager _dlOpenerGoogleDriveManager;

}