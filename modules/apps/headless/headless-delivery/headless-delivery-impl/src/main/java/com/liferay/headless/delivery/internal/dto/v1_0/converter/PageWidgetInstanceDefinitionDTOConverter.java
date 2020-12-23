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

package com.liferay.headless.delivery.internal.dto.v1_0.converter;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.headless.delivery.dto.v1_0.FragmentStyle;
import com.liferay.headless.delivery.dto.v1_0.FragmentViewport;
import com.liferay.headless.delivery.dto.v1_0.PageWidgetInstanceDefinition;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = PageWidgetInstanceDefinitionDTOConverter.class)
public class PageWidgetInstanceDefinitionDTOConverter {

	public PageWidgetInstanceDefinition toDTO(
		FragmentEntryLink fragmentEntryLink,
		FragmentStyle pageWidgetInstanceDefinitionFragmentStyle,
		FragmentViewport[] pageWidgetInstanceDefinitionFragmentViewports,
		String portletId) {

		if (Validator.isNull(portletId)) {
			return null;
		}

		return new PageWidgetInstanceDefinition() {
			{
				fragmentStyle = pageWidgetInstanceDefinitionFragmentStyle;
				fragmentViewports =
					pageWidgetInstanceDefinitionFragmentViewports;
				widgetInstance = _widgetInstanceDTOConverter.toDTO(
					fragmentEntryLink, portletId);
			}
		};
	}

	public PageWidgetInstanceDefinition toDTO(
		FragmentEntryLink fragmentEntryLink, String portletId) {

		return toDTO(fragmentEntryLink, null, null, portletId);
	}

	@Reference
	private WidgetInstanceDTOConverter _widgetInstanceDTOConverter;

}