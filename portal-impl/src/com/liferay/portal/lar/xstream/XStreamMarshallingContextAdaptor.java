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

package com.liferay.portal.lar.xstream;

import com.liferay.portal.kernel.lar.xstream.XStreamConverter;
import com.liferay.portal.kernel.lar.xstream.XStreamMarshallingContext;

import com.thoughtworks.xstream.converters.MarshallingContext;

import java.util.Iterator;

/**
 * @author Daniel Kocsis
 */
public class XStreamMarshallingContextAdaptor
	implements XStreamMarshallingContext {

	public XStreamMarshallingContextAdaptor(
		MarshallingContext marshallingContext) {

		_marshallingContext = marshallingContext;
	}

	public void convertAnother(Object object) {
		_marshallingContext.convertAnother(object);
	}

	public void convertAnother(
		Object object, XStreamConverter xStreamConverter) {

		_marshallingContext.convertAnother(
			object, new ConverterAdaptor(xStreamConverter));
	}

	public Object get(Object key) {
		return _marshallingContext.get(key);
	}

	public Iterator keys() {
		return _marshallingContext.keys();
	}

	public void put(Object key, Object value) {
		_marshallingContext.put(key, value);
	}

	private MarshallingContext _marshallingContext;

}