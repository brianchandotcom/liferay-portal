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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

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
		_message = message;
		_responseCode = responseCode;
	}

	public AICreatorOpenAIClientException(Throwable throwable) {
		super(throwable.getMessage(), throwable);
	}

	public String getCode() {
		return _code;
	}

	public String getCompletionLocalizedMessage(Locale locale) {
		return _getLocalizedMessage(
			locale, MESSAGE_KEY_AN_UNEXPECTED_ERROR_COMPLETION);
	}

	public String getLocalizedMessage(Locale locale) {
		return _getLocalizedMessage(
			locale, MESSAGE_KEY_AN_UNEXPECTED_ERROR_VALIDATION);
	}

	public int getResponseCode() {
		return _responseCode;
	}

	protected static final String MESSAGE_KEY_AN_UNEXPECTED_ERROR_COMPLETION =
		"an-unexpected-error-occurred";

	protected static final String MESSAGE_KEY_AN_UNEXPECTED_ERROR_VALIDATION =
		"an-unexpected-error-occurred-while-validating-the-api-key";

	protected static final String MESSAGE_KEY_OPENAI_API_ERRORS =
		"check-this-link-for-further-information-about-openai-issues";

	protected static final String OPENAI_API_ERRORS_LINK =
		"https://platform.openai.com/docs/guides/error-codes/api-errors";

	private String _getLocalizedMessage(Locale locale, String defaultKey) {
		if (Validator.isNull(_message)) {
			return LanguageUtil.get(locale, defaultKey);
		}

		return StringBundler.concat(
			_message, " <a href=\"", OPENAI_API_ERRORS_LINK,
			"\" target=\"_blank\">",
			HtmlUtil.escape(
				LanguageUtil.get(locale, MESSAGE_KEY_OPENAI_API_ERRORS)),
			"</a>");
	}

	private String _code = "unexpected_error";
	private String _message;
	private int _responseCode;

}