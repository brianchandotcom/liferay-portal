/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.util;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.headless.delivery.dto.v1_0.FragmentStyle;
import com.liferay.headless.delivery.dto.v1_0.FragmentViewport;
import com.liferay.headless.delivery.dto.v1_0.PageWidgetInstanceDefinition;
import com.liferay.headless.delivery.internal.dto.v1_0.mapper.WidgetInstanceMapper;
import com.liferay.headless.delivery.internal.dto.v1_0.mapper.util.StyledLayoutStructureItemUtil;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Jürgen Kappler
 * @author Javier de Arcos
 */
public class PageWidgetInstanceDefinitionUtil {

	public static PageWidgetInstanceDefinition toPageWidgetInstanceDefinition(
		FragmentEntryLink fragmentEntryLink,
		FragmentStyledLayoutStructureItem fragmentStyledLayoutStructureItem,
		String nameValue,
		FragmentStyle pageWidgetInstanceDefinitionFragmentStyle,
		FragmentViewport[] pageWidgetInstanceDefinitionFragmentViewports,
		String portletId, WidgetInstanceMapper widgetInstanceMapper) {

		if (Validator.isNull(portletId)) {
			return null;
		}

		return new PageWidgetInstanceDefinition() {
			{
				setCssClasses(
					() -> StyledLayoutStructureItemUtil.getCssClasses(
						fragmentStyledLayoutStructureItem));
				setCustomCSS(
					() -> StyledLayoutStructureItemUtil.getCustomCSS(
						fragmentStyledLayoutStructureItem));
				setCustomCSSViewports(
					() -> StyledLayoutStructureItemUtil.getCustomCSSViewports(
						fragmentStyledLayoutStructureItem));
				setFragmentStyle(
					() -> pageWidgetInstanceDefinitionFragmentStyle);
				setFragmentViewports(
					() -> pageWidgetInstanceDefinitionFragmentViewports);
				setName(() -> nameValue);
				setWidgetInstance(
					() -> widgetInstanceMapper.getWidgetInstance(
						fragmentEntryLink, portletId));
			}
		};
	}

}