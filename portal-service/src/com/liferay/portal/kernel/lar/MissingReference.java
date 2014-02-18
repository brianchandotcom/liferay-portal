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

package com.liferay.portal.kernel.lar;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.xml.Element;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Zsolt Berentey
 */
@ProviderType
public class MissingReference implements Serializable {

	public MissingReference(Element element) {
		_className = element.attributeValue("class-name");
		_classPK = element.attributeValue("class-pk");
		_displayName = GetterUtil.getString(
			element.attributeValue("display-name"));
		_groupId = element.attributeValue("group-id");
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

	public String getGroupId() {
		return _groupId;
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

	private String _className;
	private String _classPK;
	private String _displayName;
	private String _groupId;
	private String _referrerClassName;
	private Map<String, String> _referrers = new HashMap<String, String>();
	private String _type;

}