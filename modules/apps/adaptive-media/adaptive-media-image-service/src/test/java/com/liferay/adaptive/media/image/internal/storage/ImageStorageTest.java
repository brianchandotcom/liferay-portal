/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.storage;

import com.liferay.document.library.kernel.store.Store;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class ImageStorageTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_imageStorage.setStore(_store);
	}

	@Test
	public void testGetConfigurationEntryPath() {
		String configurationUuid = RandomTestUtil.randomString();

		String configurationEntryPath = _imageStorage.getConfigurationEntryPath(
			configurationUuid);

		Assert.assertEquals(
			"adaptive/" + configurationUuid, configurationEntryPath);
	}

	@Test
	public void testHasContentWithNoStoreFile() throws Exception {
		Mockito.when(
			_store.hasFile(
				Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString(),
				Mockito.eq(Store.VERSION_DEFAULT))
		).thenReturn(
			false
		);

		Assert.assertFalse(
			_imageStorage.hasContent(
				Mockito.mock(FileVersion.class),
				RandomTestUtil.randomString()));

		_verifyDLStoreMock();
	}

	@Test
	public void testHasContentWithStoreFile() throws Exception {
		Mockito.when(
			_store.hasFile(
				Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString(),
				Mockito.eq(Store.VERSION_DEFAULT))
		).thenReturn(
			true
		);

		Assert.assertTrue(
			_imageStorage.hasContent(
				Mockito.mock(FileVersion.class),
				RandomTestUtil.randomString()));

		_verifyDLStoreMock();
	}

	private void _verifyDLStoreMock() throws Exception {
		Mockito.verify(
			_store, Mockito.times(1)
		).hasFile(
			Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString(),
			Mockito.eq(Store.VERSION_DEFAULT)
		);
	}

	private final ImageStorage _imageStorage = new ImageStorage();
	private final Store _store = Mockito.mock(Store.class);

}