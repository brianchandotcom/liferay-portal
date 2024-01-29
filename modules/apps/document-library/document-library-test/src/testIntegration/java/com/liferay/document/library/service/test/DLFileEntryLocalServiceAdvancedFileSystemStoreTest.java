/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.ByteArrayInputStream;

import jodd.net.MimeTypes;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
public class DLFileEntryLocalServiceAdvancedFileSystemStoreTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new AssumeTestRule("assume"), new LiferayIntegrationTestRule());

	public static void assume() {
		String storeClassName =
			"com.liferay.portal.store.file.system.AdvancedFileSystemStore";
		String dlStoreImpl = PropsUtil.get(PropsKeys.DL_STORE_IMPL);

		Assume.assumeTrue(
			StringBundler.concat(
				"Property \"", PropsKeys.DL_STORE_IMPL, "\" is not set to \"",
				storeClassName, "\""),
			dlStoreImpl.equals(storeClassName));
	}

	@Test
	public void testDeleteFileEntryDeletesStoreFile() throws Exception {
		DLFileEntry dlFileEntry = _dlFileEntryLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			TestPropsValues.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, null,
			MimeTypes.MIME_APPLICATION_OCTET_STREAM, StringUtil.randomString(),
			null, null, null,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT, null,
			null, new ByteArrayInputStream(new byte[0]), 0, null, null, null,
			ServiceContextTestUtil.getServiceContext());

		DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

		Assert.assertTrue(
			_store.hasFile(
				TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
				dlFileEntry.getName(), dlFileVersion.getStoreFileName()));

		_dlFileEntryLocalService.deleteFileEntry(dlFileEntry);

		Assert.assertFalse(
			_store.hasFile(
				TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
				dlFileEntry.getName(), dlFileVersion.getStoreFileName()));
	}

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Inject(
		filter = "store.type=com.liferay.portal.store.file.system.AdvancedFileSystemStore",
		type = Store.class
	)
	private Store _store;

}