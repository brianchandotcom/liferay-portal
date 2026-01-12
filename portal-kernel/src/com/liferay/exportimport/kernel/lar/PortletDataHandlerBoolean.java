/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.lar;

/**
 * @author Raymond Augé
 */
public class PortletDataHandlerBoolean extends PortletDataHandlerControl {

	public PortletDataHandlerBoolean(String namespace, String name) {
		this(namespace, name, true);
	}

	public PortletDataHandlerBoolean(
		String namespace, String name, boolean defaultState) {

		this(namespace, name, defaultState, false);
	}

	public PortletDataHandlerBoolean(
		String namespace, String name, boolean defaultState, boolean disabled) {

		this(namespace, name, defaultState, disabled, null);
	}

	public PortletDataHandlerBoolean(
		String namespace, String name, boolean defaultState, boolean disabled,
		PortletDataHandlerControl[] childrenPortletDataHandlerControls) {

		this(
			namespace, name, defaultState, disabled,
			childrenPortletDataHandlerControls, null);
	}

	public PortletDataHandlerBoolean(
		String namespace, String name, boolean defaultState, boolean disabled,
		PortletDataHandlerControl[] childrenPortletDataHandlerControls,
		String className) {

		this(
			namespace, name, defaultState, disabled,
			childrenPortletDataHandlerControls, className, null);
	}

	public PortletDataHandlerBoolean(
		String namespace, String name, boolean defaultState, boolean disabled,
		PortletDataHandlerControl[] childrenPortletDataHandlerControls,
		String className, String referrerClassName) {

		this(
			namespace, name, name, defaultState, disabled,
			childrenPortletDataHandlerControls, className, referrerClassName);
	}

	public PortletDataHandlerBoolean(
		String namespace, String name, boolean defaultState,
		PortletDataHandlerControl[] childrenPortletDataHandlerControls) {

		this(
			namespace, name, defaultState, false,
			childrenPortletDataHandlerControls);
	}

	public PortletDataHandlerBoolean(
		String namespace, String name, String label, boolean defaultState,
		boolean disabled,
		PortletDataHandlerControl[] childrenPortletDataHandlerControls,
		String className, String referrerClassName) {

		super(namespace, name, label, disabled, className, referrerClassName);

		_defaultState = defaultState;
		_childrenPortletDataHandlerControls =
			childrenPortletDataHandlerControls;
	}

	public PortletDataHandlerControl[] getChildrenPortletDataHandlerControls() {
		return _childrenPortletDataHandlerControls;
	}

	public boolean getDefaultState() {
		return _defaultState;
	}

	private final PortletDataHandlerControl[]
		_childrenPortletDataHandlerControls;
	private final boolean _defaultState;

}