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

package com.liferay.headless.builder.internal.operation;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Carlos Correa
 */
public interface OperationContext {

	public String getMediaType();

	public InfoFieldValue<?> getPrimaryKeyInfoFieldValue();

	public static class Builder {

		public OperationContext build() {
			return new OperationContextImpl(this);
		}

		public Builder withInfoFieldValue(InfoFieldValue<?> infoFieldValue) {
			InfoField infoField = infoFieldValue.getInfoField();

			_infoFieldValues.put(infoField.getName(), infoFieldValue);

			return this;
		}

		public Builder withMediaType(String mediaType) {
			_mediaType = mediaType;

			return this;
		}

		public Builder withPrimaryKeyInfoFieldValue(
			InfoFieldValue<?> infoFieldValue) {

			_primaryKeyInfoFieldValue = infoFieldValue;

			return this;
		}

		private Map<String, InfoFieldValue<?>> _infoFieldValues =
			new HashMap<>();
		private String _mediaType;
		private InfoFieldValue<?> _primaryKeyInfoFieldValue;

	}

	public class OperationContextImpl implements OperationContext {

		public OperationContextImpl(Builder builder) {
			_builder = builder;
		}

		@Override
		public String getMediaType() {
			return _builder._mediaType;
		}

		@Override
		public InfoFieldValue<?> getPrimaryKeyInfoFieldValue() {
			return _builder._primaryKeyInfoFieldValue;
		}

		private final Builder _builder;

	}

}