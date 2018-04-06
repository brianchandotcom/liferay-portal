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

package com.liferay.portal.jmx.bundle.jmxwhiteboard;

import javax.management.DynamicMBean;
import javax.management.NotCompliantMBeanException;
import javax.management.StandardMBean;

import org.osgi.service.component.annotations.Component;

/**
 * @author Raymond Augé
 */
@Component(
	property = "jmx.objectname=" + JMXWhiteboardByDynamicMBean.OBJECT_NAME,
	service = DynamicMBean.class
)
public class JMXWhiteboardByDynamicMBean
	extends StandardMBean implements JMXWhiteboardByInterfaceMBean {

	public static final String OBJECT_NAME =
		"JMXWhiteboard:name=com.liferay.portal.jmx.bundle.jmxwhiteboard." +
			"JMXWhiteboardByDynamicMBean";

	public JMXWhiteboardByDynamicMBean() throws NotCompliantMBeanException {
		super(JMXWhiteboardByInterfaceMBean.class);
	}

	@Override
	public String returnValue(String value) {
		return "{" + value + "}";
	}

}