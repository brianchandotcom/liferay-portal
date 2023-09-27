/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.io;

import com.liferay.dynamic.data.mapping.model.DDMFormLayout;

/**
 * @author Leonardo Barros
 */
public final class DDMFormLayoutSerializerSerializeRequest {

	public DDMFormLayout getDDMFormLayout() {
		return _ddmFormLayout;
	}

	public static class Builder {

		public static Builder newBuilder(DDMFormLayout ddmFormLayout) {
			return new Builder(ddmFormLayout);
		}

		public DDMFormLayoutSerializerSerializeRequest build() {
			return _ddmFormLayoutSerializerSerializeRequest;
		}

		private Builder(DDMFormLayout ddmFormLayout) {
			_ddmFormLayoutSerializerSerializeRequest._ddmFormLayout =
				ddmFormLayout;
		}

		private final DDMFormLayoutSerializerSerializeRequest
			_ddmFormLayoutSerializerSerializeRequest =
				new DDMFormLayoutSerializerSerializeRequest();

	}

	private DDMFormLayoutSerializerSerializeRequest() {
	}

	private DDMFormLayout _ddmFormLayout;

}