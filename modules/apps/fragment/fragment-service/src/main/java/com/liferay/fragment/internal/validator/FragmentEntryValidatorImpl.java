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

package com.liferay.fragment.internal.validator;

import com.liferay.fragment.exception.FragmentEntryConfigurationException;
import com.liferay.fragment.validator.FragmentEntryValidator;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.InputStream;

import java.util.Iterator;
import java.util.List;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;

import org.json.JSONObject;
import org.json.JSONTokener;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rubén Pulido
 */
@Component(immediate = true, service = FragmentEntryValidator.class)
public class FragmentEntryValidatorImpl implements FragmentEntryValidator {

	@Override
	public void validateConfiguration(String configuration)
		throws FragmentEntryConfigurationException {

		if (Validator.isNull(configuration)) {
			return;
		}

		InputStream configurationJSONSchemaInputStream =
			FragmentEntryValidatorImpl.class.getResourceAsStream(
				"dependencies/configuration-json-schema.json");

		try {
			JSONObject jsonObject = new JSONObject(
				new JSONTokener(configurationJSONSchemaInputStream));

			Schema schema = SchemaLoader.load(jsonObject);

			schema.validate(new JSONObject(configuration));
		}
		catch (Exception e) {
			if (e instanceof ValidationException) {
				ValidationException validationException =
					(ValidationException)e;

				List<String> messages = validationException.getAllMessages();

				Iterator<String> iterator = messages.iterator();

				StringBundler sb = new StringBundler(messages.size() + 1);

				sb.append(validationException.getErrorMessage());

				iterator.forEachRemaining(
					s -> sb.append(StringPool.NEW_LINE + s));

				throw new FragmentEntryConfigurationException(sb.toString(), e);
			}

			throw new FragmentEntryConfigurationException(e);
		}
	}

}