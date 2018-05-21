/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.security.audit.event.generators.internal.model.listener.util;

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