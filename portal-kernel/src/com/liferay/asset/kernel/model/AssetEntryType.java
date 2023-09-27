/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.model;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 */
public class AssetEntryType implements Serializable {

	public String getClassName() {
		return _className;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public String getPortletId() {
		return _portletId;
	}

	public String getPortletTitle() {
		return _portletTitle;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	public void setPortletTitle(String portletTitle) {
		_portletTitle = portletTitle;
	}

	private String _className;
	private long _classNameId;
	private String _portletId;
	private String _portletTitle;

}