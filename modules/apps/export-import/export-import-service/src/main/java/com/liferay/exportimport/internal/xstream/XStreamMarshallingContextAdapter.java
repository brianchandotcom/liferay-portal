/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.xstream;

import com.liferay.exportimport.kernel.xstream.XStreamConverter;
import com.liferay.exportimport.kernel.xstream.XStreamMarshallingContext;

import com.thoughtworks.xstream.converters.MarshallingContext;

import java.util.Iterator;

/**
 * @author Daniel Kocsis
 */
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