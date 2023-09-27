/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.io;

import com.liferay.dynamic.data.mapping.model.DDMForm;

/**
 * @author Leonardo Barros
 */
public final class DDMFormSerializerSerializeRequest {

	public DDMForm getDDMForm() {
		return _ddmForm;
	}

	public static class Builder {

		public static Builder newBuilder(DDMForm ddmForm) {
			return new Builder(ddmForm);
		}

		public DDMFormSerializerSerializeRequest build() {
			return _ddmFormSerializerSerializeRequest;
		}

		private Builder(DDMForm ddmForm) {
			_ddmFormSerializerSerializeRequest._ddmForm = ddmForm;
		}

		private final DDMFormSerializerSerializeRequest
			_ddmFormSerializerSerializeRequest =
				new DDMFormSerializerSerializeRequest();

	}

	private DDMFormSerializerSerializeRequest() {
	}

	private DDMForm _ddmForm;

}