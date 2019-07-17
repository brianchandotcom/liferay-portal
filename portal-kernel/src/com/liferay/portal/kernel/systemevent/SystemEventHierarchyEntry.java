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

package com.liferay.portal.kernel.systemevent;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author Zsolt Berentey
 */
public class SystemEventHierarchyEntry {

	public SystemEventHierarchyEntry(
		long systemEventId, long classNameId, long classPK,
		long parentSystemEventId, long systemEventSetKey, int action) {

		_systemEventId = systemEventId;
		_classNameId = classNameId;
		_classPK = classPK;
		_parentSystemEventId = parentSystemEventId;
		_systemEventSetKey = systemEventSetKey;
		_action = action;
	}

	public int getAction() {
		return _action;
	}

	public String getClassName() {
		return PortalUtil.getClassName(_classNameId);
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public String getExtraData() {
		if (_extraDataJSONObject == null) {
			return StringPool.BLANK;
		}

		return _extraDataJSONObject.toString();
	}

	public long getParentSystemEventId() {
		return _parentSystemEventId;
	}

	public long getSystemEventId() {
		return _systemEventId;
	}

	public long getSystemEventSetKey() {
		return _systemEventSetKey;
	}

	public String getUuid() {
		return _uuid;
	}

	public boolean hasTypedModel(long classNameId, long classPK) {
		if ((_classNameId == classNameId) && (_classPK == classPK)) {
			return true;
		}

		return false;
	}

	public boolean hasTypedModel(String className, long classPK) {
		return hasTypedModel(PortalUtil.getClassNameId(className), classPK);
	}

	public void setClassName(String className) {
		_classNameId = PortalUtil.getClassNameId(className);
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public void setExtraDataValue(String key, String value) {
		if (_extraDataJSONObject == null) {
			_extraDataJSONObject = JSONFactoryUtil.createJSONObject();
		}

		_extraDataJSONObject.put(key, value);
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	private final int _action;
	private long _classNameId;
	private long _classPK;
	private JSONObject _extraDataJSONObject;
	private final long _parentSystemEventId;
	private final long _systemEventId;
	private final long _systemEventSetKey;
	private String _uuid;

}