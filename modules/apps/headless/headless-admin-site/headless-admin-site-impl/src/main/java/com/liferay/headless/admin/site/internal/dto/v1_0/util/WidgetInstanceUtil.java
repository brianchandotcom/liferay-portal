/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.headless.admin.site.dto.v1_0.WidgetInstance;
import com.liferay.headless.admin.site.dto.v1_0.WidgetPermission;
import com.liferay.layout.exporter.PortletPermissionsExporter;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortletKeys;

import jakarta.portlet.PortletPreferences;

import java.util.Map;
import java.util.TreeMap;

import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Mikel Lorza
 */
public class WidgetInstanceUtil {

	public static WidgetInstance getWidgetInstance(
		FragmentEntryLink fragmentEntryLink) {

		JSONObject jsonObject = fragmentEntryLink.getEditableValuesJSONObject();

		if (JSONUtil.isEmpty(jsonObject) || !jsonObject.has("portletId")) {
			return null;
		}

		String instanceId = jsonObject.getString("instanceId", null);

		String portletId = PortletIdCodec.encode(
			jsonObject.getString("portletId"), instanceId);

		return new WidgetInstance() {
			{
				setWidgetConfig(
					() -> _getWidgetConfig(
						fragmentEntryLink.getPlid(), portletId));
				setWidgetInstanceId(() -> instanceId);
				setWidgetName(
					() -> PortletIdCodec.decodePortletName(portletId));
				setWidgetPermissions(
					() -> _getWidgetPermissions(
						fragmentEntryLink.getPlid(), portletId));
			}
		};
	}

	private static Map<String, Object> _getWidgetConfig(
		long plid, String portletId) {

		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

		if (layout == null) {
			return null;
		}

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			PortletIdCodec.decodePortletName(portletId));

		if (portlet == null) {
			return null;
		}

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getLayoutPortletSetup(
				layout.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
				portletId, portlet.getDefaultPreferences());

		if (portletPreferences == null) {
			return null;
		}

		Map<String, Object> portletConfigurationMap = new TreeMap<>();

		Map<String, String[]> portletPreferencesMap =
			portletPreferences.getMap();

		for (Map.Entry<String, String[]> entrySet :
				portletPreferencesMap.entrySet()) {

			String[] values = entrySet.getValue();

			if (values == null) {
				portletConfigurationMap.put(
					entrySet.getKey(), StringPool.BLANK);
			}
			else if (values.length == 1) {
				portletConfigurationMap.put(entrySet.getKey(), values[0]);
			}
			else {
				portletConfigurationMap.put(entrySet.getKey(), values);
			}
		}

		return portletConfigurationMap;
	}

	private static WidgetPermission[] _getWidgetPermissions(
		long plid, String portletId) {

		PortletPermissionsExporter portletPermissionsExporter =
			_portletPermissionsExporterServiceTracker.getService();

		if (portletPermissionsExporter == null) {
			return null;
		}

		Map<String, String[]> permissionsMap =
			portletPermissionsExporter.getPortletPermissions(plid, portletId);

		if (MapUtil.isEmpty(permissionsMap)) {
			return new WidgetPermission[0];
		}

		return TransformUtil.transformToArray(
			permissionsMap.entrySet(),
			entry -> {
				if (ArrayUtil.isEmpty(entry.getValue())) {
					return null;
				}

				return new WidgetPermission() {
					{
						setActionIds(entry::getValue);
						setRoleName(entry::getKey);
					}
				};
			},
			WidgetPermission.class);
	}

	private static final ServiceTracker
		<PortletPermissionsExporter, PortletPermissionsExporter>
			_portletPermissionsExporterServiceTracker =
				ServiceTrackerFactory.open(
					FrameworkUtil.getBundle(WidgetInstanceUtil.class),
					PortletPermissionsExporter.class);

}