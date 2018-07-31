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

package com.liferay.portal.fabric.client;

import java.util.concurrent.Future;

/**
 * @author Shuyang Zhou
 */
public class FabricClientUtil {

	public static void connect() throws Exception {
		getFabricClient().connect();
	}

	public static Future<?> disconnect() throws Exception {
		return getFabricClient().disconnect();
	}

	public static FabricClient getFabricClient() {
		return _fabricClient;
	}

	public void setFabricClient(FabricClient fabricClient) {
		_fabricClient = fabricClient;
	}

	private static FabricClient _fabricClient;

}