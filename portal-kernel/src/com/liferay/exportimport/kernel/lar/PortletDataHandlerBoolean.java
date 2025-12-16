/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.lar;

import java.util.List;

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
		boolean disabled,
		PortletDataHandlerControl[] childrenPortletDataHandlerControls) {

		this(
			namespace, controlName, defaultState, disabled,
			childrenPortletDataHandlerControls, null);
	}

	public PortletDataHandlerBoolean(
		String namespace, String controlName, boolean defaultState,
		boolean disabled,
		PortletDataHandlerControl[] childrenPortletDataHandlerControls,
		String className) {

		this(
			namespace, controlName, defaultState, disabled,
			childrenPortletDataHandlerControls, className, null);
	}

	public PortletDataHandlerBoolean(
		String namespace, String controlName, boolean defaultState,
		boolean disabled,
		PortletDataHandlerControl[] childrenPortletDataHandlerControls,
		String className, String referrerClassName) {

		this(
			namespace, controlName, controlName, defaultState, disabled,
			childrenPortletDataHandlerControls, className, referrerClassName);
	}

	public PortletDataHandlerBoolean(
		String namespace, String controlName, boolean defaultState,
		PortletDataHandlerControl[] childrenPortletDataHandlerControls) {

		this(
			namespace, controlName, defaultState, false,
			childrenPortletDataHandlerControls);
	}

	public PortletDataHandlerBoolean(
		String namespace, String controlName, String controlLabel,
		boolean defaultState, boolean disabled,
		PortletDataHandlerControl[] childrenPortletDataHandlerControls,
		String className, String referrerClassName) {

		super(
			namespace, controlName, controlLabel, disabled, className,
			referrerClassName);

		_defaultState = defaultState;
		_childrenPortletDataHandlerControls =
			childrenPortletDataHandlerControls;
	}

	public PortletDataHandlerBoolean(
		String namespace, String controlName, String controlLabel,
		List<String> controlSubtitles, String controlTag, boolean defaultState,
		boolean disabled,
		PortletDataHandlerControl[] childrenPortletDataHandlerControls,
		String className, String referrerClassName) {

		super(
			namespace, controlName, controlLabel, controlSubtitles, controlTag,
			disabled, className, referrerClassName);

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