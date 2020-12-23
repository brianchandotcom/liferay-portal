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

package com.liferay.headless.delivery.internal.dto.v1_0.util;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.headless.delivery.dto.v1_0.FragmentStyle;
import com.liferay.headless.delivery.dto.v1_0.FragmentViewport;
import com.liferay.headless.delivery.dto.v1_0.PageWidgetInstanceDefinition;
import com.liferay.headless.delivery.internal.dto.v1_0.mapper.WidgetInstanceMapper;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Jürgen Kappler
 * @author Javier de Arcos
 */
public class PageWidgetInstanceDefinitionUtil {

	public static PageWidgetInstanceDefinition toPageWidgetInstanceDefinition(
		FragmentEntryLink fragmentEntryLink,
		FragmentStyle pageWidgetInstanceDefinitionFragmentStyle,
		FragmentViewport[] pageWidgetInstanceDefinitionFragmentViewports,
		String portletId, WidgetInstanceMapper widgetInstanceMapper) {

		if (Validator.isNull(portletId)) {
			return null;
		}

		return new PageWidgetInstanceDefinition() {
			{
				fragmentStyle = pageWidgetInstanceDefinitionFragmentStyle;
				fragmentViewports =
					pageWidgetInstanceDefinitionFragmentViewports;
				widgetInstance = widgetInstanceMapper.getWidgetInstance(
					fragmentEntryLink, portletId);
			}
		};
	}

}