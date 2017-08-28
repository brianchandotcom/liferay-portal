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

package com.liferay.portal.workflow.web.internal.util.filter;

import com.liferay.portal.kernel.workflow.WorkflowDefinition;

import java.io.InputStream;

import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class WorkflowDefinitionImpl implements WorkflowDefinition {

	public WorkflowDefinitionImpl(String name, String title) {
		_name = name;
		_title = title;
	}

	@Override
	public String getContent() {
		return null;
	}

	@Override
	public InputStream getInputStream() {
		return null;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public Map<String, Object> getOptionalAttributes() {
		return null;
	}

	@Override
	public String getTitle() {
		return _title;
	}

	@Override
	public String getTitle(String languageId) {
		return _title;
	}

	@Override
	public int getVersion() {
		return 0;
	}

	@Override
	public boolean isActive() {
		return false;
	}

	private final String _name;
	private final String _title;

}