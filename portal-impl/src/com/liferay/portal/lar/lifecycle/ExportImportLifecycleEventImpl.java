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

package com.liferay.portal.lar.lifecycle;

import com.liferay.portal.kernel.lar.lifecycle.ExportImportLifecycleEvent;
import com.liferay.portal.kernel.util.ListUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	public Map<Integer, Boolean> getProcessFlags() {
		return _processFlags;
	}

	@Override
	public void setAttributes(Serializable... attributes) {
		_attributes.addAll(ListUtil.fromArray(attributes));
	}

	@Override
	public void setCode(int code) {
		_code = code;
	}

	@Override
	public void setProcessFlags(Map<Integer, Boolean> processFlags) {
		_processFlags = processFlags;
	}

	private final List<Serializable> _attributes = new ArrayList<>();
	private int _code;
	private Map<Integer, Boolean> _processFlags;

}