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

		String exception1 = handler.getException(response1);

		Assert.assertEquals(expectedException, exception1);

		String response2 =
			"{\"message\":\"No JSON web service action associated with path" +
				"/sync-web.syncdlobject/get-sync-context and method POST for" +
					"sync-web\",\"exception\":\"java.lang.RuntimeException\"}";

		String exception2 = handler.getException(response2);

		Assert.assertEquals(expectedException, exception2);

		String response3 =
			"{\"exception\":\"No JSON web service action associated with path" +
				"/sync-web.syncdlobject/get-sync-context and method POST for" +
					"sync-web\"}";

		String exception3 = handler.getException(response3);

		Assert.assertEquals(expectedException, exception3);

		String response4 =
			"{\"message\":\"Foo\",\"exception\":" +
				"\"java.lang.RuntimeException\"}";

		String exception4 = handler.getException(response4);

		Assert.assertNotEquals(expectedException, exception4);
	}

}