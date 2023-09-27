/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public FormInstanceFieldSettingsException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public FormInstanceFieldSettingsException(Throwable throwable) {
		super(throwable);
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