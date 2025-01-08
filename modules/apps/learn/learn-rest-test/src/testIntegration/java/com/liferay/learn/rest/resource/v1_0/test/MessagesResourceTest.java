/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.learn.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.learn.LearnMessageUtil;
import com.liferay.learn.rest.client.http.HttpInvoker;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Thiago Buarque
 */
@RunWith(Arquillian.class)
public class MessagesResourceTest extends BaseMessagesResourceTestCase {

	@Override
	@Test
	public void testGetMessages() throws Exception {
		String resource = "click-to-chat-web";

		HttpInvoker.HttpResponse httpResponse =
			messagesResource.getMessagesHttpResponse(resource);

		JSONAssert.assertEquals(
			LearnMessageUtil.getJSONObject(
				resource
			).toString(),
			httpResponse.getContent(), true);

		httpResponse = messagesResource.getMessagesHttpResponse(
			RandomTestUtil.randomString());

		Assert.assertEquals("{}", httpResponse.getContent());
	}

}