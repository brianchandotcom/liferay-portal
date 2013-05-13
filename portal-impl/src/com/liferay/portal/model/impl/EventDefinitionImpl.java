/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.model.EventDefinition;
import com.liferay.portal.model.PortletApp;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class EventDefinitionImpl implements EventDefinition {

	public EventDefinitionImpl(
		QName qName, String valueType, PortletApp portletApp) {

		_qName = qName;
		_valueType = valueType;
		_portletApp = portletApp;

		_qNames = new HashSet<QName>();

		_qNames.add(_qName);
	}

	@Override
	public void addAliasQName(QName aliasQName) {
		_qNames.add(aliasQName);
	}

	@Override
	public PortletApp getPortletApp() {
		return _portletApp;
	}

	@Override
	public QName getQName() {
		return _qName;
	}

	@Override
	public Set<QName> getQNames() {
		return _qNames;
	}

	@Override
	public String getValueType() {
		return _valueType;
	}

	@Override
	public void setPortletApp(PortletApp portletApp) {
		_portletApp = portletApp;
	}

	@Override
	public void setQName(QName qName) {
		_qName = qName;
	}

	@Override
	public void setValueType(String valueType) {
		_valueType = valueType;
	}

	private PortletApp _portletApp;
	private QName _qName;
	private Set<QName> _qNames;
	private String _valueType;

}