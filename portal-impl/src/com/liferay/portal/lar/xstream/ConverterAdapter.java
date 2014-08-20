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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * @author Daniel Kocsis
 */
public class ConverterAdapter implements Converter {

	public ConverterAdapter(XStreamConverter xStreamConverter) {
		_xStreamConverter = xStreamConverter;
	}

	@Override
	public boolean canConvert(@SuppressWarnings("rawtypes") Class clazz) {
		return _xStreamConverter.canConvert(clazz);
	}

	@Override
	public void marshal(
		Object object, HierarchicalStreamWriter hierarchicalStreamWriter,
		MarshallingContext marshallingContext) {

		try {
			_xStreamConverter.marshal(
				object,
				new XStreamHierarchicalStreamWriterAdapter(
					hierarchicalStreamWriter),
				new XStreamMarshallingContextAdapter(marshallingContext));
		}
		catch (Exception e) {
			_log.error("Unable to marshal object", e);
		}
	}

	@Override
	public Object unmarshal(
		HierarchicalStreamReader hierarchicalStreamReader,
		UnmarshallingContext unmarshallingContext) {

		try {
			return _xStreamConverter.unmarshal(
				new XStreamHierarchicalStreamReaderAdapter(
					hierarchicalStreamReader),
				new XStreamUnmarshallingContextAdapter(unmarshallingContext));
		}
		catch (Exception e) {
			_log.error("Unable to un-marshal object", e);

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ConverterAdapter.class);

	private final XStreamConverter _xStreamConverter;

}