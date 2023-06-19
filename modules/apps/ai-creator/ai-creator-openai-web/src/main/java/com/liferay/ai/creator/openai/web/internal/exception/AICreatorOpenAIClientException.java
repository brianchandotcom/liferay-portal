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

import com.liferay.portal.kernel.language.LanguageUtil;

import java.net.HttpURLConnection;

import java.util.Locale;
import java.util.Objects;

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

	public String getCode() {
		return _code;
	}

	public String getCompletionLocalizedMessage(Locale locale) {
		if ((_responseCode == 429) || (_responseCode == 500)) {
			return LanguageUtil.get(
				locale, OPENAI_IS_EXPERIENCING_ISSUES_MESSAGE_KEY);
		}

		return LanguageUtil.get(
			locale, AN_UNEXPECTED_ERROR_COMPLETION_MESSAGE_KEY);
	}

	public String getLocalizedMessage(Locale locale) {
		if ((_responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) ||
			Objects.equals(_code, "invalid_api_key")) {

			return LanguageUtil.get(locale, INCORRECT_API_KEY_MESSAGE_KEY);
		}

		return LanguageUtil.get(locale, AN_UNEXPECTED_ERROR_MESSAGE_KEY);
	}

	public int getResponseCode() {
		return _responseCode;
	}

	protected static final String AN_UNEXPECTED_ERROR_COMPLETION_MESSAGE_KEY =
		"an-unexpected-error-occurred-while-generating-your-content.-please-" +
			"ensure-your-api-key-is-correct";

	protected static final String AN_UNEXPECTED_ERROR_MESSAGE_KEY =
		"an-unexpected-error-occurred-while-validating-the-api-key";

	protected static final String INCORRECT_API_KEY_MESSAGE_KEY =
		"incorrect-api-key-provided.-ensure-the-api-key-used-is-correct,-" +
			"clear-your-browser-cache,-or-generate-a-new-key";

	protected static final String OPENAI_IS_EXPERIENCING_ISSUES_MESSAGE_KEY =
		"openai-is-experiencing-issues-on-their-servers";

	private String _code = "unexpected_error";
	private int _responseCode;

}