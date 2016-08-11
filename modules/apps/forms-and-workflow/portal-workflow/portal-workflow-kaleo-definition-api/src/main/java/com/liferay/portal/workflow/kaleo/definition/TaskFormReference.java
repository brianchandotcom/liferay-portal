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

package com.liferay.portal.workflow.kaleo.definition;

/**
 * @author Michael C. Han
 */
public class TaskFormReference {

	public long getCompanyId() {
		return _companyId;
	}

	public long getFormId() {
		return _formId;
	}

	public String getFormUuid() {
		return _formUuid;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setFormId(long formId) {
		_formId = formId;
	}

	public void setFormUuid(String formUuid) {
		_formUuid = formUuid;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	private long _companyId;
	private long _formId;
	private String _formUuid;
	private long _groupId;

}