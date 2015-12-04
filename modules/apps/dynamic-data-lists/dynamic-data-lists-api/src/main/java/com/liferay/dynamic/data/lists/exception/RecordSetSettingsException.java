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

package com.liferay.dynamic.data.lists.exception;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class RecordSetSettingsException extends PortalException {

	public RecordSetSettingsException() {
	}

	public RecordSetSettingsException(String msg) {
		super(msg);
	}

	public RecordSetSettingsException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public RecordSetSettingsException(Throwable cause) {
		super(cause);
	}

	public static class MustEnterValidEmailAddress
		extends RecordSetSettingsException {

		public MustEnterValidEmailAddress(String fieldName) {
			super(
				String.format(
					"Please enter a valid email address for field: '%s'",
					fieldName));
			_fieldName = fieldName;
		}

		public String getFieldName() {
			return _fieldName;
		}

		private final String _fieldName;

	}

	public static class MustEnterValidRedirectURL
		extends RecordSetSettingsException {

		public MustEnterValidRedirectURL() {
			super("Please enter a valid redirect URL");
		}

	}

	public static class RequiredValue extends RecordSetSettingsException {

		public RequiredValue(String fieldName) {
			super(String.format("No value defined for field %s", fieldName));

			_fieldName = fieldName;
		}

		public String getFieldName() {
			return _fieldName;
		}

		private final String _fieldName;

	}

}