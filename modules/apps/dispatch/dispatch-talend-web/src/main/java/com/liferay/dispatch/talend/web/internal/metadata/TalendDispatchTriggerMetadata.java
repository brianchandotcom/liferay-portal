/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		_attributes = Collections.unmodifiableMap(builder._attributes);
		_errors = Collections.unmodifiableMap(builder._errors);
		_ready = builder._ready;
	}

	private final Map<String, String> _attributes;
	private final Map<String, String> _errors;
	private final boolean _ready;

}