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

package com.liferay.portlet.internal;

import java.io.Serializable;

import javax.portlet.Event;

import javax.xml.namespace.QName;

/**
 * @author Brian Wing Shun Chan
 */
public class EventImpl implements Event, Serializable {

	public EventImpl(String name, QName qName, Serializable value) {
		_name = name;
		_qName = qName;
		_value = value;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public QName getQName() {
		return _qName;
	}

	@Override
	public Serializable getValue() {
		return _value;
	}

	private final String _name;
	private final QName _qName;
	private final Serializable _value;

}