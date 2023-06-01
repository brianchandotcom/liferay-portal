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

package com.liferay.ai.creator.openai.web.internal.exception;

/**
 * @author Lourdes Fernández Besada
 */
public class AICreatorOpenAIClientException extends RuntimeException {

	public AICreatorOpenAIClientException(int responseCode) {
		_responseCode = responseCode;
	}

	public AICreatorOpenAIClientException(
		String code, String message, int responseCode) {

		super(message);

		_code = code;
		_responseCode = responseCode;
	}

	public AICreatorOpenAIClientException(Throwable throwable) {
		super(throwable.getMessage(), throwable);
	}

	private String _code = "unexpected_error";
	private int _responseCode;

}