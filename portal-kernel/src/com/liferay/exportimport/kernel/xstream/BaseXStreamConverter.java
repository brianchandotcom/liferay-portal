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

package com.liferay.exportimport.kernel.xstream;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;

import java.util.List;

/**
 * @author Akos Thurzo
 */
public abstract class BaseXStreamConverter implements XStreamConverter {

	@Override
	public abstract boolean canConvert(Class<?> clazz);

	@Override
	public void marshal(
			Object object, XStreamHierarchicalStreamWriter writer,
			XStreamMarshallingContext xStreamMarshallingContext)
		throws Exception {

		for (String field : getFields()) {
			writer.startNode(field);

			Object value = BeanPropertiesUtil.getObject(object, field);

			if (value != null) {
				xStreamMarshallingContext.convertAnother(value);
			}

			writer.endNode();
		}
	}

	@Override
	public abstract Object unmarshal(
			XStreamHierarchicalStreamReader xStreamHierarchicalStreamReader,
			XStreamUnmarshallingContext xStreamUnmarshallingContext)
		throws Exception;

	protected abstract List<String> getFields();

}