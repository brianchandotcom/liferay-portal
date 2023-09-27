/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.xstream;

import com.liferay.exportimport.kernel.xstream.XStreamConverter;
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
		catch (Exception exception) {
			_log.error("Unable to marshal object", exception);
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
		catch (Exception exception) {
			_log.error("Unable to un-marshal object", exception);

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ConverterAdapter.class);

	private final XStreamConverter _xStreamConverter;

}