/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.converter;

import com.liferay.headless.admin.site.dto.v1_0.WidgetPageWidgetInstance;
import com.liferay.headless.admin.site.internal.resource.v1_0.util.LayoutUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = DTOConverter.class)
public class WidgetPageWidgetInstanceDTOConverter
	implements DTOConverter<Layout, WidgetPageWidgetInstance> {

	@Override
	public String getContentType() {
		return WidgetPageWidgetInstance.class.getSimpleName();
	}

	@Override
	public WidgetPageWidgetInstance toDTO(
			DTOConverterContext dtoConverterContext, Layout layout)
		throws Exception {

		String portletId = GetterUtil.getString(
			dtoConverterContext.getAttribute("portletId"));

		return new WidgetPageWidgetInstance() {
			{
				setExternalReferenceCode(() -> portletId);
				setParentSectionId(
					() -> LayoutUtil.getParentSectionId(layout, portletId));
				setPosition(() -> LayoutUtil.getPosition(layout, portletId));
				setWidgetInstanceId(
					() -> PortletIdCodec.decodeInstanceId(portletId));
				setWidgetName(
					() -> PortletIdCodec.decodePortletName(portletId));
			}
		};
	}

}