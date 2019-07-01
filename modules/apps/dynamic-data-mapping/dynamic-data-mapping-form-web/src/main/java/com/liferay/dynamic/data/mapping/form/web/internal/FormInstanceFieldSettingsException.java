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

package com.liferay.dynamic.data.mapping.form.web.internal;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Map;
import java.util.Set;

/**
 * @author Rafael Praxedes
 */
public class FormInstanceFieldSettingsException extends PortalException {

	public FormInstanceFieldSettingsException() {
	}

	public FormInstanceFieldSettingsException(String msg) {
		super(msg);
	}

	public FormInstanceFieldSettingsException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public FormInstanceFieldSettingsException(Throwable cause) {
		super(cause);
	}

	public static class MustSetValidValueForProperties
		extends FormInstanceFieldSettingsException {

		public MustSetValidValueForProperties(
			Map<String, Set<String>> fieldNamePropertiesMap) {

			super(
				String.format(
					"Invalid value set for the properties of field %s",
					fieldNamePropertiesMap.keySet()));

			_fieldNamePropertiesMap = fieldNamePropertiesMap;
		}

		public Map<String, Set<String>> getFieldNamePropertiesMap() {
			return _fieldNamePropertiesMap;
		}

		private final Map<String, Set<String>> _fieldNamePropertiesMap;

	}

	private static final long serialVersionUID = 1L;

}