/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.internal.upgrade.v5_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dispatch.internal.messaging.TestDispatchTaskExecutor;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.dispatch.service.persistence.DispatchTriggerPersistence;
import com.liferay.dispatch.service.test.util.DispatchTriggerTestUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class DispatchJakartaUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_upgradeStepRegistrator.register(
			(fromSchemaVersionString, toSchemaVersionString, upgradeSteps) -> {
				for (UpgradeStep upgradeStep : upgradeSteps) {
					Class<?> clazz = upgradeStep.getClass();

					if (Objects.equals(
							clazz.getName(),
							"com.liferay.dispatch.internal.upgrade.v5_0_0." +
								"DispatchJakartaUpgradeProcess")) {

						_upgradeProcess = (UpgradeProcess)upgradeStep;
					}
				}
			});

		_user = TestPropsValues.getUser();
	}

	@Test
	public void testUpgrade() throws Exception {
		DispatchTrigger dispatchTrigger = null;

		try {
			dispatchTrigger = DispatchTriggerTestUtil.randomDispatchTrigger(
				_user,
				TestDispatchTaskExecutor.DISPATCH_TASK_EXECUTOR_TYPE_TEST, 1);

			dispatchTrigger.setDispatchTaskSettingsUnicodeProperties(
				new UnicodeProperties(
					HashMapBuilder.put(
						_PARAMETERS_KEY, _JAVAX_PARAMETERS
					).build(),
					false));

			dispatchTrigger = _dispatchTriggerLocalService.addDispatchTrigger(
				null, dispatchTrigger.getUserId(),
				dispatchTrigger.getDispatchTaskExecutorType(),
				dispatchTrigger.getDispatchTaskSettingsUnicodeProperties(),
				dispatchTrigger.getName(), dispatchTrigger.isSystem());

			_upgradeProcess.upgrade();

			_dispatchTriggerPersistence.clearCache();

			DispatchTrigger updatedDispatchTrigger =
				_dispatchTriggerLocalService.getDispatchTrigger(
					dispatchTrigger.getDispatchTriggerId());

			Assert.assertNotNull(updatedDispatchTrigger);

			UnicodeProperties unicodeProperties =
				updatedDispatchTrigger.
					getDispatchTaskSettingsUnicodeProperties();

			Assert.assertEquals(
				_JAKARTA_PARAMETERS,
				unicodeProperties.getProperty(_PARAMETERS_KEY));
		}
		finally {
			if (dispatchTrigger != null) {
				_dispatchTriggerLocalService.deleteDispatchTrigger(
					dispatchTrigger.getDispatchTriggerId());
			}
		}
	}

	private static final String _JAKARTA_PARAMETERS =
		"-Xms256M -Xmx1024M -Djakarta.xml.ws.client=xyz";

	private static final String _JAVAX_PARAMETERS =
		"-Xms256M -Xmx1024M -Djavax.xml.ws.client=xyz";

	private static final String _PARAMETERS_KEY = "JAVA_OPTS";

	@Inject(
		filter = "component.name=com.liferay.dispatch.internal.upgrade.registry.DispatchServiceUpgradeStepRegistrator"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

	@Inject
	private DispatchTriggerPersistence _dispatchTriggerPersistence;

	private UpgradeProcess _upgradeProcess;
	private User _user;

}