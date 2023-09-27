/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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