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

package com.liferay.exportimport.kernel.lar;

import aQute.bnd.annotation.ProviderType;

/**
 * @author Raymond Augé
 */
@ProviderType
public class PortletDataHandlerBoolean extends PortletDataHandlerControl {

	public PortletDataHandlerBoolean(String namespace, String controlName) {
		this(namespace, controlName, true);
	}

	public PortletDataHandlerBoolean(
		String namespace, String controlName, boolean defaultState) {

		this(namespace, controlName, defaultState, false);
	}

	public PortletDataHandlerBoolean(
		String namespace, String controlName, boolean defaultState,
		boolean disabled) {

		this(namespace, controlName, defaultState, disabled, null);
	}

	public PortletDataHandlerBoolean(
		String namespace, String controlName, boolean defaultState,
		boolean disabled, PortletDataHandlerControl[] children) {

		this(namespace, controlName, defaultState, disabled, children, null);
	}

	public PortletDataHandlerBoolean(
		String namespace, String controlName, boolean defaultState,
		boolean disabled, PortletDataHandlerControl[] children,
		String className) {

		this(
			namespace, controlName, defaultState, disabled, children, className,
			null);
	}

	public PortletDataHandlerBoolean(
		String namespace, String controlName, boolean defaultState,
		boolean disabled, PortletDataHandlerControl[] children,
		String className, String referrerClassName) {

		this(
			namespace, controlName, controlName, defaultState, disabled,
			children, className, referrerClassName);
	}

	public PortletDataHandlerBoolean(
		String namespace, String controlName, boolean defaultState,
		PortletDataHandlerControl[] children) {

		this(namespace, controlName, defaultState, false, children);
	}

	public PortletDataHandlerBoolean(
		String namespace, String controlName, String controlLabel,
		boolean defaultState, boolean disabled,
		PortletDataHandlerControl[] children, String className,
		String referrerClassName) {

		super(
			namespace, controlName, controlLabel, disabled, className,
			referrerClassName);

		_defaultState = defaultState;
		_children = children;
	}

	public PortletDataHandlerControl[] getChildren() {
		return _children;
	}

	public boolean getDefaultState() {
		return _defaultState;
	}

	private final PortletDataHandlerControl[] _children;
	private final boolean _defaultState;

}