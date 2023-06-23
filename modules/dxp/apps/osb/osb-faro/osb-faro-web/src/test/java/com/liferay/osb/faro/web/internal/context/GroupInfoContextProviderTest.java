/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.web.internal.context;

import com.liferay.osb.faro.engine.client.exception.InvalidOAuthTokenException;

import org.apache.cxf.message.Message;

import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Thiago Buarque
 */
public class GroupInfoContextProviderTest {

	@Test(expected = InvalidOAuthTokenException.class)
	public void testInvalidAccessToken() {
		Message message = Mockito.mock(Message.class);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest() {
				{
					addHeader(
						"Authorization", "Bearer pxcvm289qywetypam89ds8a");
				}
			};

		Mockito.when(
			message.getContextualProperty("HTTP.REQUEST")
		).thenReturn(
			mockHttpServletRequest
		);

		GroupInfoContextProvider groupInfoContextProvider =
			new GroupInfoContextProvider();

		groupInfoContextProvider.createContext(message);
	}

}