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

package com.liferay.portal.kernel.lar.xstream;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.util.List;

/**
 * @author Akos Thurzo
 */
public abstract class BaseXStreamConverter implements Converter {

	@Override
	public abstract boolean canConvert(Class clazz);

	@Override
	public void marshal(
		Object object, HierarchicalStreamWriter hierarchicalStreamWriter,
		MarshallingContext marshallingContext) {

		for (String field : getFields()) {
			hierarchicalStreamWriter.startNode(field);

			Object value = BeanPropertiesUtil.getObject(object, field);

			if (value != null) {
				marshallingContext.convertAnother(value);
			}

			hierarchicalStreamWriter.endNode();
		}
	}

	@Override
	public abstract Object unmarshal(
		HierarchicalStreamReader hierarchicalStreamReader,
		UnmarshallingContext unmarshallingContext);

	protected abstract List<String> getFields();

}