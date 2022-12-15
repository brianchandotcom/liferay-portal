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

package com.liferay.portal.json.validator;

import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * @author Rubén Pulido
 */
public class JSONValidator {

	public JSONValidator(URL url) {
		_url = url;
	}

	public void validate(String json) throws JSONValidatorException {
		if (Validator.isNull(json)) {
			return;
		}

		try {
			_validator.performValidation(_getSchema(), new JSONObject(json));
		}
		catch (Exception exception) {
			if (exception instanceof JSONException) {
				JSONException jsonException = (JSONException)exception;

				throw new JSONValidatorException(
					jsonException.getMessage(), jsonException);
			}
			else if (exception instanceof ValidationException) {
				ValidationException validationException =
					(ValidationException)exception;

				String errorMessage = validationException.getErrorMessage();

				List<String> messages = validationException.getAllMessages();

				if (!messages.isEmpty()) {
					List<String> formattedMessages = new ArrayList<>();

					messages.forEach(
						message -> {
							if (message.startsWith("#: ")) {
								message = message.substring(3);
							}
							else if (message.startsWith("#")) {
								message = message.substring(1);
							}

							formattedMessages.add(message);
						});

					errorMessage = StringUtil.merge(
						formattedMessages, StringPool.NEW_LINE);
				}

				throw new JSONValidatorException(errorMessage, exception);
			}

			throw new JSONValidatorException(exception);
		}
	}

	private Schema _getSchema() throws IOException {
		Schema schema = _schema;

		if (schema != null) {
			return schema;
		}

		synchronized (this) {
			if (_schema == null) {
				try (InputStream inputStream = _url.openStream()) {
					_schema = SchemaLoader.load(
						new JSONObject(new JSONTokener(inputStream)));
				}
			}

			schema = _schema;
		}

		return schema;
	}

	private static final org.everit.json.schema.Validator _validator =
		org.everit.json.schema.Validator.builder(
		).build();

	private volatile Schema _schema;
	private final URL _url;

}