/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.model.Resource;

/**
 * Represents a permissionable resource in permissions versions &lt; 6.
 *
 * @author Brian Wing Shun Chan
 */
public class ResourceImpl implements Resource{

	public ResourceImpl() {
	}

	public long getCodeId() {
		return _codeId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public String getName() {
		return _name;
	}

	public String getPrimKey() {
		return _primKey;
	}

	public long getResourceId() {
		return _resourceId;
	}

	public int getScope() {
		return _scope;
	}

	public void setCodeId(long codeId) {
		_codeId = codeId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setPrimKey(String primKey) {
		_primKey = primKey;
	}

	public void setResourceId(long resourceId) {
		_resourceId = resourceId;
	}

	public void setScope(int scope) {
		_scope = scope;
	}

	private long _codeId;
	private long _companyId;
	private String _name;
	private String _primKey;
	private long _resourceId;
	private int _scope;

}