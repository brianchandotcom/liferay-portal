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

package com.liferay.dispatch.talend.web.internal.metadata;

import com.liferay.dispatch.metadata.DispatchTriggerMetadata;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Igor Beslic
 */
public class TalendDispatchTriggerMetadata implements DispatchTriggerMetadata {

	@Override
	public Map<String, String> getAttributes() {
		return _attributes;
	}

	@Override
	public Map<String, String> getErrors() {
		return _errors;
	}

	@Override
	public boolean isDispatchTaskExecutorReady() {
		return _ready;
	}

	public static class Builder {

		public Builder attribute(String key, String value) {
			_attributes.put(key, value);

			return this;
		}

		public TalendDispatchTriggerMetadata build() {
			return new TalendDispatchTriggerMetadata(this);
		}

		public Builder error(String key, String value) {
			_errors.put(key, value);

			return this;
		}

		public Builder ready(boolean ready) {
			_ready = ready;

			return this;
		}

		private final Map<String, String> _attributes = new HashMap<>();
		private final Map<String, String> _errors = new HashMap<>();
		private boolean _ready;

	}

	private TalendDispatchTriggerMetadata(Builder builder) {
		_ready = builder._ready;
		_attributes = Collections.unmodifiableMap(builder._attributes);
		_errors = Collections.unmodifiableMap(builder._errors);
	}

	private final Map<String, String> _attributes;
	private final Map<String, String> _errors;
	private final boolean _ready;

}