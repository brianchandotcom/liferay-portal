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

package com.liferay.portal.lar;

/**
 * @author     Raymond Augé
 * @deprecated As of 6.2.0, moved to {@link
 *             com.liferay.portlet.exportimport.lar.PortletDataHandlerChoice}
 */
@Deprecated
public class PortletDataHandlerChoice
	extends com.liferay.portlet.exportimport.lar.PortletDataHandlerChoice {

	public PortletDataHandlerChoice(String namespace, String controlName) {
		super(namespace, controlName);
	}

	public PortletDataHandlerChoice(
		String namespace, String controlName, int defaultChoice) {

		super(namespace, controlName, defaultChoice);
	}

	public PortletDataHandlerChoice(
		String namespace, String controlName, int defaultChoice,
		String[] choices) {

		super(namespace, controlName, defaultChoice, choices);
	}

}