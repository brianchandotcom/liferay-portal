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

package com.liferay.portal.lar;

/**
 * @author     Raymond Augé
 * @deprecated Moved to {@link
 *             com.liferay.portal.kernel.lar.PortletDataHandlerBoolean}
 */
public class PortletDataHandlerBoolean
	extends com.liferay.portal.kernel.lar.PortletDataHandlerBoolean {

	public PortletDataHandlerBoolean(String namespace, String controlName) {
		super(namespace, controlName);
	}

	public PortletDataHandlerBoolean(
		String namespace, String controlName, boolean defaultState) {

		super(namespace, controlName, defaultState);
	}

	public PortletDataHandlerBoolean(
		String namespace, String controlName, boolean defaultState,
		boolean disabled) {

		super(namespace, controlName, defaultState, disabled);
	}

	public PortletDataHandlerBoolean(
		String namespace, String controlName, boolean defaultState,
		boolean disabled, PortletDataHandlerControl[] children) {

		super(namespace, controlName, defaultState, disabled, children);
	}

	public PortletDataHandlerBoolean(
		String namespace, String controlName, boolean defaultState,
		PortletDataHandlerControl[] children) {

		super(namespace, controlName, defaultState, children);
	}

}