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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceCode;
import com.liferay.portal.service.ResourceCodeLocalServiceUtil;

/**
 * Represents a permissionable resource in permissions versions &lt; 6.
 *
 * @author Brian Wing Shun Chan
 */
public class ResourceImpl implements Resource{

	public ResourceImpl() {
	}

	public long getCompanyId() throws PortalException, SystemException {
		if (_companyId != 0) {
			return _companyId;
		}
		else {
			ResourceCode resourceCode =
				ResourceCodeLocalServiceUtil.getResourceCode(getCodeId());

			return resourceCode.getCompanyId();
		}
	}

	public String getName() throws PortalException, SystemException {
		if (_name != null) {
			return _name;
		}
		else {
			ResourceCode resourceCode =
				ResourceCodeLocalServiceUtil.getResourceCode(getCodeId());

			return resourceCode.getName();
		}
	}

	public int getScope() throws PortalException, SystemException {
		if (_scope != 0) {
			return _scope;
		}
		else {
			ResourceCode resourceCode =
				ResourceCodeLocalServiceUtil.getResourceCode(getCodeId());

			return resourceCode.getScope();
		}
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setScope(int scope) {
		_scope = scope;
	}

	private long _companyId;
	private String _name;
	private int _scope;

}