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