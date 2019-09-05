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

package com.liferay.portal.security.audit.event.generators.util;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Brian Wing Shun Chan
 */
public class AttributesBuilder {

	public AttributesBuilder(Object newBean, Object oldBean) {
		_newBean = newBean;
		_oldBean = oldBean;
	}

	public void add(String name) {
		String newValue = String.valueOf(
			BeanPropertiesUtil.getObject(_newBean, name));
		String oldValue = String.valueOf(
			BeanPropertiesUtil.getObject(_oldBean, name));

		if (!Objects.equals(newValue, oldValue)) {
			Attribute attribute = new Attribute(name, newValue, oldValue);

			_attributes.add(attribute);
		}
	}

	public List<Attribute> getAttributes() {
		return _attributes;
	}

	private final List<Attribute> _attributes = new ArrayList<>();
	private final Object _newBean;
	private final Object _oldBean;

}