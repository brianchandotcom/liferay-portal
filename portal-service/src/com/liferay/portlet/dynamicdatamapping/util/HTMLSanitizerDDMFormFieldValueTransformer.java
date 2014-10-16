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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portlet.dynamicdatamapping.model.Value;

import java.util.Locale;

/**
 * @author Marcellus Tavares
 */
public class HTMLSanitizerDDMFormFieldValueTransformer
	implements DDMFormFieldValueTransformer {

	public HTMLSanitizerDDMFormFieldValueTransformer(
		long companyId, long groupId, long userId) {

		_companyId = companyId;
		_groupId = groupId;
		_userId = userId;
	}

	@Override
	public String getFieldType() {
		return "ddm-text-html";
	}

	@Override
	public void transform(Value value) throws PortalException {
		for (Locale locale : value.getAvailableLocales()) {
			String sanitizedValue = sanitize(value.getString(locale));

			value.addString(locale, sanitizedValue);
		}
	}

	protected String sanitize(String value) throws PortalException {
		return SanitizerUtil.sanitize(
			_companyId, _groupId, _userId, Value.class.getName(), 0,
			ContentTypes.TEXT_HTML, Sanitizer.MODE_ALL, value, null);
	}

	private final long _companyId;
	private final long _groupId;
	private final long _userId;

}