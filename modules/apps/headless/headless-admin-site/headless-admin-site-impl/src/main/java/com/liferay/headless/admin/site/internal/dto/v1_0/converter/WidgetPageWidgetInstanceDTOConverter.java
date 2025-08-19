/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.converter;

import com.liferay.headless.admin.site.dto.v1_0.WidgetPageWidgetInstance;
import com.liferay.headless.admin.site.dto.v1_0.WidgetPermission;
import com.liferay.headless.admin.site.internal.resource.v1_0.util.LayoutUtil;
import com.liferay.layout.exporter.PortletPermissionsExporter;
import com.liferay.layout.exporter.PortletPreferencesPortletConfigurationExporter;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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
			dtoConverterContext.getAttribute("portletId"), null);

		if ((portletId == null) || !layout.isTypePortlet()) {
			return null;
		}

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		if (!layoutTypePortlet.hasPortletId(portletId)) {
			return null;
		}

		return new WidgetPageWidgetInstance() {
			{
				setExternalReferenceCode(() -> portletId);
				setParentSectionId(
					() -> LayoutUtil.getParentSectionId(layout, portletId));
				setPosition(() -> LayoutUtil.getPosition(layout, portletId));
				setWidgetConfig(
					() -> {
						Map<String, Object> portletConfigurationMap =
							_portletPreferencesPortletConfigurationExporter.
								getPortletConfiguration(
									layout.getPlid(), portletId);

						if (MapUtil.isEmpty(portletConfigurationMap)) {
							return null;
						}

						return portletConfigurationMap;
					});
				setWidgetInstanceId(
					() -> PortletIdCodec.decodeInstanceId(portletId));
				setWidgetName(
					() -> PortletIdCodec.decodePortletName(portletId));
				setWidgetPermissions(
					() -> {
						Map<String, String[]> permissionsMap =
							_portletPermissionsExporter.getPortletPermissions(
								layout.getPlid(), portletId);

						if (MapUtil.isEmpty(permissionsMap)) {
							return null;
						}

						return TransformUtil.transformToArray(
							permissionsMap.entrySet(),
							entry -> new WidgetPermission() {
								{
									setActionIds(entry::getValue);
									setRoleName(entry::getKey);
								}
							},
							WidgetPermission.class);
					});
			}
		};
	}

	@Override
	public WidgetPageWidgetInstance toDTO(Layout layout) {
		return null;
	}

	@Reference
	private PortletPermissionsExporter _portletPermissionsExporter;

	@Reference
	private PortletPreferencesPortletConfigurationExporter
		_portletPreferencesPortletConfigurationExporter;

}