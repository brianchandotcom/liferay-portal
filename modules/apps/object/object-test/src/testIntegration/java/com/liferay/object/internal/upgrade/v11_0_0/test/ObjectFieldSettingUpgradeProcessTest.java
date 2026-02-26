/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.upgrade.v11_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.persistence.ObjectFieldSettingPersistence;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alberto Sousa
 */
@RunWith(Arquillian.class)
public class ObjectFieldSettingUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testUpgrade() throws Exception {
		long objectFieldId = RandomTestUtil.randomLong();

		ObjectFieldSetting objectFieldSetting1 = _addObjectFieldSetting(
			objectFieldId, ObjectFieldSettingConstants.NAME_FILE_SOURCE,
			"userComputer");
		ObjectFieldSetting objectFieldSetting2 = _addObjectFieldSetting(
			objectFieldId, "showFilesInDocumentsAndMedia", StringPool.FALSE);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME, LoggerTestUtil.OFF)) {

			UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
				_upgradeStepRegistrator, _CLASS_NAME);

			upgradeProcess.upgrade();

			_entityCache.clearCache();
		}

		objectFieldSetting1 =
			_objectFieldSettingLocalService.getObjectFieldSetting(
				objectFieldSetting1.getObjectFieldSettingId());

		Assert.assertEquals(
			ObjectFieldSettingConstants.VALUE_USER_COMPUTER_TO_DOCS_AND_MEDIA,
			objectFieldSetting1.getValue());

		objectFieldSetting2 =
			_objectFieldSettingLocalService.getObjectFieldSetting(
				objectFieldSetting2.getObjectFieldSettingId());

		Assert.assertEquals(
			ObjectFieldSettingConstants.NAME_SHOW_FILES_IN_LIBRARY,
			objectFieldSetting2.getName());
	}

	private ObjectFieldSetting _addObjectFieldSetting(
		long objectFieldId, String name, String value) {

		ObjectFieldSetting objectFieldSetting =
			_objectFieldSettingLocalService.createObjectFieldSetting(
				_counterLocalService.increment());

		objectFieldSetting.setObjectFieldId(objectFieldId);
		objectFieldSetting.setName(name);
		objectFieldSetting.setValue(value);

		return _objectFieldSettingLocalService.updateObjectFieldSetting(
			objectFieldSetting);
	}

	private static final String _CLASS_NAME =
		"com.liferay.object.internal.upgrade.v11_0_0." +
			"ObjectFieldSettingUpgradeProcess";

	@Inject(
		filter = "component.name=com.liferay.object.internal.upgrade.registry.ObjectServiceUpgradeStepRegistrator"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private CounterLocalService _counterLocalService;

	@Inject
	private EntityCache _entityCache;

	@Inject
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

	@Inject
	private ObjectFieldSettingPersistence _objectFieldSettingPersistence;

}