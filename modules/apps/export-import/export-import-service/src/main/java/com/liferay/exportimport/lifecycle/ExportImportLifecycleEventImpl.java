/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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