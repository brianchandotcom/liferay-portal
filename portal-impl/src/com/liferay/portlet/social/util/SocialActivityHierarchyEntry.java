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

package com.liferay.portlet.social.util;

import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author Zsolt Berentey
 */
public class SocialActivityHierarchyEntry {

	public SocialActivityHierarchyEntry() {
	}

	public SocialActivityHierarchyEntry(long classNameId, long classPK) {
		_classNameId = classNameId;
		_classPK = classPK;
	}

	public String getClassName() {
		return PortalUtil.getClassName(_classNameId);
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	private long _classNameId;
	private long _classPK;

}