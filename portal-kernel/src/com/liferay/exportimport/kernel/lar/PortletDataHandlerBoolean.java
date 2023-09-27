/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.lar;

/**
 * @author Raymond Augé
 */
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