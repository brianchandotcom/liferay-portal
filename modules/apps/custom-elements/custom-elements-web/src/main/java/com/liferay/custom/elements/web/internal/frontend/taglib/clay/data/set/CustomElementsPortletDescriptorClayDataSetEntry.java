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

package com.liferay.custom.elements.web.internal.frontend.taglib.clay.data.set;

import com.liferay.custom.elements.model.CustomElementsPortletDescriptor;

/**
 * @author Iván Zaera Avellón
 */
public class CustomElementsPortletDescriptorClayDataSetEntry {

	public CustomElementsPortletDescriptorClayDataSetEntry(
		CustomElementsPortletDescriptor customElementsPortletDescriptor) {

		_customElementsPortletDescriptor = customElementsPortletDescriptor;
	}

	public long getCustomElementsPortletDescriptorId() {
		return _customElementsPortletDescriptor.
			getCustomElementsPortletDescriptorId();
	}

	public String getHtmlElementName() {
		return _customElementsPortletDescriptor.getHTMLElementName();
	}

	public String getName() {
		return _customElementsPortletDescriptor.getName();
	}

	private final CustomElementsPortletDescriptor
		_customElementsPortletDescriptor;

}