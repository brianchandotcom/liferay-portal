/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.xstream;

import com.liferay.exportimport.kernel.xstream.XStreamConverter;
import com.liferay.exportimport.kernel.xstream.XStreamUnmarshallingContext;

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