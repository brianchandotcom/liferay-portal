/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.upgrade.v2_12_3.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTMessage;
import com.liferay.change.tracking.service.CTMessageLocalService;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author István András Dézsi
 */
@RunWith(Arquillian.class)
public class CTMessageCompanyIdUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.change.tracking.internal.upgrade.v2_12_3." +
				"CTMessageCompanyIdUpgradeProcess");
	}

	@Test
	public void testUpgrade() throws Exception {
		long ctMessageId = RandomTestUtil.nextLong();

		CTMessage ctMessage = _ctMessageLocalService.createCTMessage(
			ctMessageId);

		long companyId = TestPropsValues.getCompanyId();

		ctMessage.setCompanyId(companyId);

		long ctCollectionId = RandomTestUtil.nextLong();

		ctMessage.setCtCollectionId(ctCollectionId);

		Message message = new Message();

		message.put("companyId", companyId);

		String messageContent = JSONFactoryUtil.serialize(message);

		Assert.assertTrue(messageContent.contains("\"companyId\""));

		ctMessage.setMessageContent(messageContent);

		_ctMessageLocalService.updateCTMessage(ctMessage);

		_upgradeProcess.upgrade();

		CacheRegistryUtil.clear();

		ctMessage = _ctMessageLocalService.getCTMessage(ctMessageId);

		messageContent = ctMessage.getMessageContent();

		Assert.assertFalse(messageContent.contains("\"companyId\""));

		List<Message> messages = _ctMessageLocalService.getMessages(
			ctCollectionId);

		Message deserializedMessage = messages.get(0);

		Assert.assertEquals(companyId, deserializedMessage.get("companyId"));
	}

	@Inject
	private static CTMessageLocalService _ctMessageLocalService;

	private static UpgradeProcess _upgradeProcess;

	@Inject(
		filter = "(&(component.name=com.liferay.change.tracking.internal.upgrade.registry.ChangeTrackingServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

}