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

package com.liferay.portal.kernel.workflow;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.io.InputStream;
import java.io.Serializable;

import java.util.Date;
import java.util.Map;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 */
public class DefaultWorkflowDefinition
	implements Serializable, WorkflowDefinition {

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public String getContent() {
		return _content;
	}

	@Override
	public String getDescription() {
		if (_description == null) {
			return StringPool.BLANK;
		}

		return _description;
	}

	@Override
	public InputStream getInputStream() {
		return _inputStream;
	}

	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public Map<String, Object> getOptionalAttributes() {
		return _optionalAttributes;
	}

	@Override
	public String getTitle() {
		if (_title == null) {
			return StringPool.BLANK;
		}

		return _title;
	}

	@Override
	public String getTitle(String languageId) {
		return LocalizationUtil.getLocalization(getTitle(), languageId);
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public int getVersion() {
		return _version;
	}

	@Override
	public long getWorkflowDefinitionId() {
		return _workflowDefinitionId;
	}

	@Override
	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setContent(String content) {
		_content = content;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setInputStream(InputStream inputStream) {
		_inputStream = inputStream;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setOptionalAttributes(Map<String, Object> optionalAttributes) {
		_optionalAttributes = optionalAttributes;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public void setVersion(int version) {
		_version = version;
	}

	public void setWorkflowDefinitionId(long workflowDefinitionId) {
		_workflowDefinitionId = workflowDefinitionId;
	}

	private boolean _active;
	private long _companyId;
	private String _content;
	private String _description;
	private InputStream _inputStream;
	private Date _modifiedDate;
	private String _name;
	private Map<String, Object> _optionalAttributes;
	private String _title;
	private long _userId;
	private int _version;
	private long _workflowDefinitionId;

}