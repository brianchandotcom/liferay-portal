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

package com.liferay.portal.upgrade.v7_0_0.util;

/**
 * @author Iván Zaera
 * @deprecated As of 7.0.0, with no direct replacement
 */
@Deprecated
public class PortletPreferencesRow {

	public PortletPreferencesRow(
		long portletPreferencesId, long ownerId, int ownerType, long plid,
		String portletId, String preferences) {

		_portletPreferencesId = portletPreferencesId;
		_ownerId = ownerId;
		_ownerType = ownerType;
		_plid = plid;
		_portletId = portletId;
		_preferences = preferences;
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public long getOwnerId() {
		return _ownerId;
	}

	public int getOwnerType() {
		return _ownerType;
	}

	public long getPlid() {
		return _plid;
	}

	public String getPortletId() {
		return _portletId;
	}

	public long getPortletPreferencesId() {
		return _portletPreferencesId;
	}

	public String getPreferences() {
		return _preferences;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public void setOwnerId(long ownerId) {
		_ownerId = ownerId;
	}

	public void setOwnerType(int ownerType) {
		_ownerType = ownerType;
	}

	public void setPlid(long plid) {
		_plid = plid;
	}

	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	public void setPortletPreferencesId(long portletPreferencesId) {
		_portletPreferencesId = portletPreferencesId;
	}

	public void setPreferences(String preferences) {
		_preferences = preferences;
	}

	private long _mvccVersion;
	private long _ownerId;
	private int _ownerType;
	private long _plid;
	private String _portletId;
	private long _portletPreferencesId;
	private String _preferences;

}