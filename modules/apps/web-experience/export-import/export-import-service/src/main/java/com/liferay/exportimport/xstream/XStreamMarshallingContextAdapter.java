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

package com.liferay.exportimport.xstream;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.xstream.XStreamConverter;
import com.liferay.exportimport.kernel.xstream.XStreamMarshallingContext;

import com.thoughtworks.xstream.converters.MarshallingContext;

import java.util.Iterator;

/**
 * @author Daniel Kocsis
 */
@ProviderType
public class XStreamMarshallingContextAdapter
	implements XStreamMarshallingContext {

	public XStreamMarshallingContextAdapter(
		MarshallingContext marshallingContext) {

		_marshallingContext = marshallingContext;
	}

	@Override
	public void convertAnother(Object object) {
		_marshallingContext.convertAnother(object);
	}

	@Override
	public void convertAnother(
		Object object, XStreamConverter xStreamConverter) {

		_marshallingContext.convertAnother(
			object, new ConverterAdapter(xStreamConverter));
	}

	@Override
	public Object get(Object key) {
		return _marshallingContext.get(key);
	}

	@Override
	public Iterator<String> keys() {
		return _marshallingContext.keys();
	}

	@Override
	public void put(Object key, Object value) {
		_marshallingContext.put(key, value);
	}

	private final MarshallingContext _marshallingContext;

}