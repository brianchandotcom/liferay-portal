/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.sync.engine.documentlibrary.handler;

import com.liferay.sync.engine.documentlibrary.event.GetSyncContextEvent;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shinn Lok
 */
public class BaseJSONHandlerTest {

	@Test
	public void testGetException() {
		Handler handler = new BaseJSONHandler(new GetSyncContextEvent(0, null));

		String expectedException =
			"com.liferay.portal.kernel.jsonwebservice." +
				"NoSuchJSONWebServiceException";

		String response1 =
			"{\"error\":{\"message\":\"No JSON web service action with path" +
				"/sync-web.syncdlobject/get-sync-context and method POST" +
					"for sync-web\",\"type\":\"java.lang.RuntimeException\"}}";

		Assert.assertEquals(expectedException, handler.getException(response1));

		String response2 =
			"{\"message\":\"No JSON web service action associated with path" +
				"/sync-web.syncdlobject/get-sync-context and method POST for" +
					"sync-web\",\"exception\":\"java.lang.RuntimeException\"}";

		Assert.assertEquals(expectedException, handler.getException(response2));

		String response3 =
			"{\"exception\":\"No JSON web service action associated with path" +
				"/sync-web.syncdlobject/get-sync-context and method POST for" +
					"sync-web\"}";

		Assert.assertEquals(expectedException, handler.getException(response3));

		String response4 =
			"{\"message\":\"Foo\",\"exception\":" +
				"\"java.lang.RuntimeException\"}";

		Assert.assertNotEquals(
			expectedException, handler.getException(response4));
	}

}