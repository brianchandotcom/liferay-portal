/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.store.s3.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLTrashLocalServiceUtil;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.document.library.test.util.DLAppTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.store.test.util.BaseStoreTestCase;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import jakarta.annotation.Generated;

import org.junit.Assume;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 * @author Manuel de la Peña
 */
@Generated("")
@RunWith(Arquillian.class)
public class IBMS3StoreTest extends BaseStoreTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new AssumeTestRule("assume"), new LiferayIntegrationTestRule());

	public static void assume() {
		String s3StoreClassName = "com.liferay.portal.store.s3.IBMS3Store";
		String dlStoreImpl = PropsUtil.get(PropsKeys.DL_STORE_IMPL);

		Assume.assumeTrue(
			StringBundler.concat(
				"Property \"", PropsKeys.DL_STORE_IMPL, "\" is not set to \"",
				s3StoreClassName, "\""),
			dlStoreImpl.equals(s3StoreClassName));
	}

	@Test
	public void testSmoke() throws Exception {
		Group group = GroupTestUtil.addGroup();

		try {
			FileEntry fileEntry = DLAppTestUtil.addFileEntry(
				group.getGroupId());

			DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

			DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

			Assert.assertTrue(
				_store.hasFile(
					dlFileEntry.getCompanyId(),
					dlFileEntry.getDataRepositoryId(), dlFileEntry.getName(),
					dlFileVersion.getStoreFileName()));

			DLTrashLocalServiceUtil.moveFileEntryToTrash(
				dlFileEntry.getUserId(), dlFileEntry.getRepositoryId(),
				dlFileEntry.getFileEntryId());

			Assert.assertTrue(
				_store.hasFile(
					dlFileEntry.getCompanyId(),
					dlFileEntry.getDataRepositoryId(), dlFileEntry.getName(),
					dlFileVersion.getStoreFileName()));

			TrashHandler trashHandler =
				TrashHandlerRegistryUtil.getTrashHandler(
					DLFileEntry.class.getName());

			trashHandler.deleteTrashEntry(dlFileEntry.getPrimaryKey());

			Assert.assertFalse(
				_store.hasFile(
					dlFileEntry.getCompanyId(),
					dlFileEntry.getDataRepositoryId(), dlFileEntry.getName(),
					dlFileVersion.getStoreFileName()));
		}
		finally {
			GroupTestUtil.deleteGroup(group);
		}
	}

	@Override
	protected Store getStore() {
		return _store;
	}

	@Inject(
		filter = "store.type=com.liferay.portal.store.s3.IBMS3Store",
		type = Store.class
	)
	private Store _store;

}