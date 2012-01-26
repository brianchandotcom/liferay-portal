/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.lar;

import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Raymond Augé
 */
public class PortletDataHandlerControl {

	public static String getNamespacedControlName(
		String namespace, String controlName) {

		StringBuilder sb = new StringBuilder();

		sb.append(StringPool.UNDERLINE);
		sb.append(namespace);
		sb.append(StringPool.UNDERLINE);
		sb.append(controlName);

		return sb.toString();
	}

	public PortletDataHandlerControl(String namespace, String controlName) {
		this(namespace, controlName, false);
	}

	public PortletDataHandlerControl(
		String namespace, String controlName, boolean disabled) {

		_namespace = namespace;
		_controlName = controlName;
		_disabled = disabled;
	}

	public String getControlName() {
		return _controlName;
	}

	public String getNamespace() {
		return _namespace;
	}

	public String getNamespacedControlName() {
		return getNamespacedControlName(_namespace, getControlName());
	}

	public boolean isDisabled() {
		return _disabled;
	}

	private String _controlName;
	private boolean _disabled;
	private String _namespace;

}