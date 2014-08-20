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
import com.liferay.portal.kernel.lar.xstream.XStreamUnmarshallingContext;

import com.thoughtworks.xstream.converters.UnmarshallingContext;

/**
 * @author Daniel Kocsis
 */
public class XStreamUnmarshallingContextAdapter
	implements XStreamUnmarshallingContext {

	public XStreamUnmarshallingContextAdapter(
		UnmarshallingContext unmarshallingContext) {

		_unmarshallingContext = unmarshallingContext;
	}

	@Override
	public void addCompletionCallback(Runnable work, int priority) {
		_unmarshallingContext.addCompletionCallback(work, priority);
	}

	@Override
	public Object convertAnother(Object object, Class<?> clazz) {
		return _unmarshallingContext.convertAnother(object, clazz);
	}

	@Override
	public Object convertAnother(
		Object object, Class<?> clazz, XStreamConverter xStreamConverter) {

		return _unmarshallingContext.convertAnother(
			object, clazz, new ConverterAdapter(xStreamConverter));
	}

	@Override
	public Object currentObject() {
		return _unmarshallingContext.currentObject();
	}

	@Override
	public Object getRequiredType() {
		return _unmarshallingContext.getRequiredType();
	}

	private final UnmarshallingContext _unmarshallingContext;

}