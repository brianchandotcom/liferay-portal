/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.nexus.rest.resource.v1_0.test;

import com.liferay.ai.hub.nexus.rest.resource.v1_0.test.util.SseEventSourceTestUtil;
import com.liferay.ai.hub.nexus.rest.resource.v1_0.util.SseUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.test.rule.FeatureFlag;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Feliphe Marinho
 */
@FeatureFlag("LPD-62272")
@RunWith(Arquillian.class)
public class ChatResourceTest extends BaseChatResourceTestCase {

	@After
	public void tearDown() {
		SseUtil.closeAll();
	}

	@Override
	@Test
	public void testGetChatSubscribe() throws Exception {
		Assert.assertNotNull(
			SseEventSourceTestUtil.open(
				List.of(), new ArrayList<>(), "chats/subscribe"));
	}

}