/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Resource;

/**
 * @author Brian Wing Shun Chan
 */
public class ResourceImpl implements Resource {

	@Override
	public long getCodeId() {
		return _codeId;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public String getPrimKey() {
		return _primKey;
	}

	@Override
	public long getResourceId() {
		return _resourceId;
	}

	@Override
	public int getScope() {
		return _scope;
	}

	@Override
	public void setCodeId(long codeId) {
		_codeId = codeId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@Override
	public void setPrimKey(String primKey) {
		_primKey = primKey;
	}

	@Override
	public void setResourceId(long resourceId) {
		_resourceId = resourceId;
	}

	@Override
	public void setScope(int scope) {
		_scope = scope;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{companyid=", _companyId, ", name=", _name, ", primKey=", _primKey,
			", scope=", _scope, "}");
	}

	private long _codeId;
	private long _companyId;
	private String _name;
	private String _primKey;
	private long _resourceId;
	private int _scope;

}