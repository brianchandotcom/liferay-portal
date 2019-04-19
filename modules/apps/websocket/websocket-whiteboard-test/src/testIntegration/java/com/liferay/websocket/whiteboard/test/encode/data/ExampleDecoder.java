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

package com.liferay.websocket.whiteboard.test.encode.data;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * @author Cristina González
 */
public class ExampleDecoder implements Decoder.Text<Example> {

	@Override
	public Example decode(String message) {
		int index = message.indexOf(CharPool.SPACE);

		return new Example(
			GetterUtil.getInteger(message.substring(0, index)),
			message.substring(index + 1));
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(EndpointConfig endpointConfig) {
	}

	@Override
	public boolean willDecode(String message) {
		if (message != null) {
			return true;
		}

		return false;
	}

}