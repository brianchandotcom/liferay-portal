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

package com.liferay.data.engine.renderer;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Leonardo Barros
 */
public class DataLayoutRendererContext {

	public String getContainerId() {
		return _containerId;
	}

	public Map<String, Object> getDataRecordValues() {
		return _dataRecordValues;
	}

	public String getDefaultLanguageId() {
		return _defaultLanguageId;
	}

	public HttpServletRequest getHttpServletRequest() {
		return _httpServletRequest;
	}

	public HttpServletResponse getHttpServletResponse() {
		return _httpServletResponse;
	}

	public String getLanguageId() {
		return _languageId;
	}

	public String getPortletNamespace() {
		return _portletNamespace;
	}

	public boolean isPersistDefaultValues() {
		return _persistDefaultValues;
	}

	public boolean isPersisted() {
		return _persisted;
	}

	public boolean isReadOnly() {
		return _readOnly;
	}

	public boolean isSubmittable() {
		return _submittable;
	}

	public void setContainerId(String containerId) {
		_containerId = containerId;
	}

	public void setDataRecordValues(Map<String, Object> dataRecordValues) {
		_dataRecordValues = dataRecordValues;
	}

	public void setDefaultLanguageId(String defaultLanguageId) {
		_defaultLanguageId = defaultLanguageId;
	}

	public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
		_httpServletRequest = httpServletRequest;
	}

	public void setHttpServletResponse(
		HttpServletResponse httpServletResponse) {

		_httpServletResponse = httpServletResponse;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	public void setPersistDefaultValues(boolean persistDefaultValues) {
		_persistDefaultValues = persistDefaultValues;
	}

	public void setPersisted(boolean persisted) {
		_persisted = persisted;
	}

	public void setPortletNamespace(String portletNamespace) {
		_portletNamespace = portletNamespace;
	}

	public void setReadOnly(boolean readOnly) {
		_readOnly = readOnly;
	}

	public void setSubmittable(boolean submittable) {
		_submittable = submittable;
	}

	private String _containerId;
	private Map<String, Object> _dataRecordValues;
	private String _defaultLanguageId;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private String _languageId;
	private boolean _persistDefaultValues;
	private boolean _persisted;
	private String _portletNamespace;
	private boolean _readOnly;
	private boolean _submittable;

}