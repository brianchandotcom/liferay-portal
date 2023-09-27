/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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