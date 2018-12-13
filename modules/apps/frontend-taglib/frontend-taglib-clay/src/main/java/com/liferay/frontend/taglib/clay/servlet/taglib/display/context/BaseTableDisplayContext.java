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

package com.liferay.frontend.taglib.clay.servlet.taglib.display.context;

import com.liferay.frontend.taglib.clay.internal.SoyDataFactoryProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.table.Field;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.table.Schema;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.template.soy.data.SoyDataFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Iván Zaera Avellón
 */
public abstract class BaseTableDisplayContext<T>
	implements TableDisplayContext {

	@Override
	public Collection<?> getItems() {
		List<Map<String, ?>> itemsList = new ArrayList<>();

		Schema schema = getSchema();

		Collection<Field> fields = schema.getFields();

		SoyDataFactory soyDataFactory =
			SoyDataFactoryProvider.getSoyDataFactory();

		for (T item : doGetItems()) {
			Map<String, Object> itemMap = new HashMap<>();

			for (Field field : fields) {
				Object value = getFieldValue(field, item);

				if ((value != null) && !field.isEscaping()) {
					value = soyDataFactory.createSoyHTMLData(value.toString());
				}

				itemMap.put(field.getFieldName(), value);
			}

			itemsList.add(itemMap);
		}

		return itemsList;
	}

	@Override
	public Schema getSchema() {
		if (_schema == null) {
			_schema = new Schema();

			configureSchema(_schema);
		}

		return _schema;
	}

	protected abstract void configureSchema(Schema schema);

	protected abstract Collection<T> doGetItems();

	protected Object getFieldValue(Field field, T item) {
		return BeanPropertiesUtil.getStringSilent(item, field.getFieldName());
	}

	private Schema _schema;

}