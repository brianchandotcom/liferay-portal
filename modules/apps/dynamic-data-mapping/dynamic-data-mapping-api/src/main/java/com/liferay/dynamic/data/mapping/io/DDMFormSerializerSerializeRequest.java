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