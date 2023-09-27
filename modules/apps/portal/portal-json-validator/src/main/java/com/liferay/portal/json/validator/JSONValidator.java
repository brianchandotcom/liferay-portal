/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.json.validator;

import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.petra.reflect.ReflectionUtil;
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
			_validator.performValidation(
				_schemaDCLSingleton.getSingleton(this::_createSchema),
				new JSONObject(json));
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

	private Schema _createSchema() {
		try (InputStream inputStream = _url.openStream()) {
			return SchemaLoader.load(
				new JSONObject(new JSONTokener(inputStream)));
		}
		catch (IOException ioException) {
			return ReflectionUtil.throwException(ioException);
		}
	}

	private static final org.everit.json.schema.Validator _validator =
		org.everit.json.schema.Validator.builder(
		).build();

	private final DCLSingleton<Schema> _schemaDCLSingleton =
		new DCLSingleton<>();
	private final URL _url;

}