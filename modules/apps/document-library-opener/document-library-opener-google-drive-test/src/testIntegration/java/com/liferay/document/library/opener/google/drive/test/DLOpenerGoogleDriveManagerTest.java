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
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.opener.google.drive.DLOpenerGoogleDriveManager;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;

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
		_user = UserTestUtil.addUser(_company);
	}

	@Test(expected = PortalException.class)
	public void testGetAuthorizationURLFailsIfThereIsNoAuthorizationCodeFlow()
		throws Exception {

		_dlOpenerGoogleDriveManager.getAuthorizationURL(
			_company.getCompanyId(), RandomTestUtil.randomString(),
			"http://localhost:8080");
	}

	@Test
	public void testGetAuthorizationURLSucceedsIfThereAreValidCredentials()
		throws Exception {

		_withGoogleDriveEnabled(
			() -> {
				String redirectUri = "http://localhost:8080";
				String state = RandomTestUtil.randomString();

				Assert.assertEquals(
					StringBundler.concat(
						"https://accounts.google.com/o/oauth2/auth?client_id=",
						_getGoogleDriveClientId(), "&redirect_uri=",
						redirectUri, "&response_type=code",
						"&scope=https://www.googleapis.com/auth/drive.file",
						"&state=", state),
					_dlOpenerGoogleDriveManager.getAuthorizationURL(
						_company.getCompanyId(), state, redirectUri));
			});
	}

	@Test
	public void testHasValidCredentialIsFalseByDefault() throws Exception {
		Assert.assertFalse(
			_dlOpenerGoogleDriveManager.hasValidCredential(
				_company.getCompanyId(), _user.getUserId()));
	}

	@Test
	public void testIsConfiguredIsFalseByDefault() {
		Assert.assertFalse(
			_dlOpenerGoogleDriveManager.isConfigured(_company.getCompanyId()));
	}

	@Test
	public void testIsConfiguredIsTrueWhenGoogleDriveSettingsAreFilled()
		throws Exception {

		_withGoogleDriveEnabled(
			() -> Assert.assertTrue(
				_dlOpenerGoogleDriveManager.isConfigured(
					_company.getCompanyId())));
	}

	@Test
	public void testIsGoogleDriveFileIsFalseByDefault() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_company.getGroupId());

		Folder folder = _dlAppLocalService.addFolder(
			TestPropsValues.getUserId(), _company.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			serviceContext.getUserId(), folder.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN, "liferay.txt",
			StringPool.BLANK, StringPool.BLANK, "liferay".getBytes(),
			serviceContext);

		Assert.assertFalse(
			_dlOpenerGoogleDriveManager.isGoogleDriveFile(fileEntry));
	}

	private String _getGoogleDriveClientId() {
		return "clientId";
	}

	private String _getGoogleDriveClientSecret() {
		return "clientSecret";
	}

	private <E extends Exception> void _withGoogleDriveEnabled(
			UnsafeRunnable<E> unsafeRunnable)
		throws Exception {

		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		dictionary.put("clientId", _getGoogleDriveClientId());
		dictionary.put("clientSecret", _getGoogleDriveClientSecret());

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.document.library.google.drive.configuration." +
						"DLGoogleDriveCompanyConfiguration",
					dictionary)) {

			unsafeRunnable.run();
		}
	}

	@DeleteAfterTestRun
	private Company _company;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private DLOpenerGoogleDriveManager _dlOpenerGoogleDriveManager;

	private User _user;

}