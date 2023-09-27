/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.lar;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.xml.Element;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Zsolt Berentey
 */
public class MissingReference implements Serializable {

	public MissingReference(Element element) {
		_className = element.attributeValue("class-name");
		_classPK = element.attributeValue("class-pk");
		_displayName = GetterUtil.getString(
			element.attributeValue("display-name"));
		_elementPath = GetterUtil.getString(
			element.attributeValue("element-path"));
		_referenceGroupId = GetterUtil.getLong(
			element.attributeValue("group-id"));
		_referrerClassName = element.attributeValue("referrer-class-name");
		_type = GetterUtil.getString(element.attributeValue("type"));

		String referrerDisplayName = GetterUtil.getString(
			element.attributeValue("referrer-display-name"));

		addReferrer(_referrerClassName, referrerDisplayName);
	}

	public void addReferrer(
		String referrerClassName, String referrerDisplayName) {

		_referrers.put(referrerDisplayName, referrerClassName);
	}

	public void addReferrers(Map<String, String> referrers) {
		_referrers.putAll(referrers);
	}

	public String getClassName() {
		return _className;
	}

	public String getClassPK() {
		return _classPK;
	}

	public String getDisplayName() {
		return _displayName;
	}

	public String getElementPath() {
		return _elementPath;
	}

	public long getGroupId() {
		return _groupId;
	}

	public long getReferenceGroupId() {
		return _referenceGroupId;
	}

	public String getReferrerClassName() {
		return _referrerClassName;
	}

	public Set<String> getReferrerDisplayNames() {
		return _referrers.keySet();
	}

	public Map<String, String> getReferrers() {
		return _referrers;
	}

	public String getType() {
		return _type;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setReferenceGroupId(long referenceGroupId) {
		_referenceGroupId = referenceGroupId;
	}

	private final String _className;
	private final String _classPK;
	private final String _displayName;
	private final String _elementPath;
	private long _groupId;
	private long _referenceGroupId;
	private final String _referrerClassName;
	private final Map<String, String> _referrers = new HashMap<>();
	private final String _type;

}