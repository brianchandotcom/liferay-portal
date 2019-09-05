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

package com.liferay.exportimport.lifecycle;

import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleEvent;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Daniel Kocsis
 */
public class ExportImportLifecycleEventImpl
	implements ExportImportLifecycleEvent {

	@Override
	public List<Serializable> getAttributes() {
		return _attributes;
	}

	@Override
	public int getCode() {
		return _code;
	}

	@Override
	public int getProcessFlag() {
		return _processFlag;
	}

	@Override
	public String getProcessId() {
		return _processId;
	}

	@Override
	public void setAttributes(Serializable... attributes) {
		Collections.addAll(_attributes, attributes);
	}

	@Override
	public void setCode(int code) {
		_code = code;
	}

	@Override
	public void setProcessFlag(int processFlag) {
		_processFlag = processFlag;
	}

	@Override
	public void setProcessId(String processId) {
		_processId = processId;
	}

	private final List<Serializable> _attributes = new ArrayList<>();
	private int _code;
	private int _processFlag;
	private String _processId;

}