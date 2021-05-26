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
import java.util.Map;

/**
 * @author Igor Beslic
 */
public class TalendDispatchTriggerMetadata implements DispatchTriggerMetadata {

	public TalendDispatchTriggerMetadata(boolean ready) {
		this(ready, Collections.emptyMap());
	}

	public TalendDispatchTriggerMetadata(
		boolean ready, Map<String, String> errors) {

		_ready = ready;
		_errors = Collections.unmodifiableMap(errors);
	}

	@Override
	public Map<String, String> getAttributes() {
		return Collections.emptyMap();
	}

	@Override
	public Map<String, String> getErrors() {
		return _errors;
	}

	@Override
	public boolean isDispatchTaskExecutorReady() {
		return _ready;
	}

	private final Map<String, String> _errors;
	private final boolean _ready;

}