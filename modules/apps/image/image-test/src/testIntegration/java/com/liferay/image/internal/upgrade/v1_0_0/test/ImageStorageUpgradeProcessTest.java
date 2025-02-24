/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.image.internal.upgrade.v1_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mariano Álvaro Sáiz
 */
@RunWith(Arquillian.class)
public class ImageStorageUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@After
	public void tearDown() throws Exception {
		for (Image image : _images) {
			_store.deleteFile(
				CompanyConstants.SYSTEM, _REPOSITORY_ID, _getFileName(image),
				Store.VERSION_DEFAULT);
			_store.deleteFile(
				image.getCompanyId(), _REPOSITORY_ID, _getFileName(image),
				Store.VERSION_DEFAULT);
		}
	}

	@Test
	public void testUpgrade() throws Exception {
		_createImages(100);

		_runUpgrade();

		for (Image image : _images) {
			Assert.assertFalse(
				_store.hasFile(
					CompanyConstants.SYSTEM, _REPOSITORY_ID,
					_getFileName(image), Store.VERSION_DEFAULT));
			Assert.assertTrue(
				_store.hasFile(
					image.getCompanyId(), _REPOSITORY_ID, _getFileName(image),
					Store.VERSION_DEFAULT));
		}
	}

	private void _createImages(long total) throws Exception {
		byte[] bytes = FileUtil.getBytes(
			getClass(), "dependencies/liferay.jpg");

		for (int i = 0; i < total; i++) {
			Image image = _imageLocalService.updateImage(
				TestPropsValues.getCompanyId(),
				_counterLocalService.increment(), bytes);

			_images.add(image);

			_store.addFile(
				CompanyConstants.SYSTEM, _REPOSITORY_ID, _getFileName(image),
				Store.VERSION_DEFAULT, new UnsyncByteArrayInputStream(bytes));
		}
	}

	private String _getFileName(Image image) {
		return image.getImageId() + StringPool.PERIOD + image.getType();
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator, _CLASS_NAME);

		upgradeProcess.upgrade();

		_entityCache.clearCache();
		_multiVMPool.clear();
	}

	private static final String _CLASS_NAME =
		"com.liferay.image.internal.upgrade.v1_0_0.ImageStorageUpgradeProcess";

	private static final long _REPOSITORY_ID = 0;

	@Inject(
		filter = "(&(component.name=com.liferay.image.internal.upgrade.registry.ImageServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private CounterLocalService _counterLocalService;

	@Inject
	private EntityCache _entityCache;

	@Inject
	private ImageLocalService _imageLocalService;

	@DeleteAfterTestRun
	private final List<Image> _images = new ArrayList<>();

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject(
		filter = "(&(objectClass=com.liferay.document.library.kernel.store.Store)(default=true))"
	)
	private Store _store;

}