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

package com.liferay.dynamic.data.mapping.form.field.type.internal.geolocation;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.form.field.type.internal.util.DDMFormFieldTypeUtil;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.map.util.MapProviderHelperUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;

import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcela Cunha
 */
@Component(
	property = "ddm.form.field.type.name=" + DDMFormFieldTypeConstants.GEOLOCATION,
	service = {
		DDMFormFieldTemplateContextContributor.class,
		GeolocationDDMFormFieldTemplateContextContributor.class
	}
)
public class GeolocationDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		HttpServletRequest httpServletRequest =
			ddmFormFieldRenderingContext.getHttpServletRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group group = _getGroup(httpServletRequest, themeDisplay);

		String mapProviderKey = GetterUtil.getString(
			MapProviderHelperUtil.getMapProviderKey(
				_groupLocalService, themeDisplay.getCompanyId(),
				group.getGroupId()),
			"OpenStreetMap");

		return HashMapBuilder.<String, Object>put(
			"googleMapsAPIKey", _getGoogleMapsAPIKey(group, themeDisplay)
		).put(
			"mapProviderKey", mapProviderKey
		).put(
			"moduleName", getModuleName(mapProviderKey)
		).put(
			"predefinedValue",
			DDMFormFieldTypeUtil.getPropertyValue(
				ddmFormField, ddmFormFieldRenderingContext.getLocale(),
				"predefinedValue")
		).put(
			"value",
			DDMFormFieldTypeUtil.getPropertyValue(
				ddmFormFieldRenderingContext, "value")
		).put(
			"viewMode",
			GetterUtil.getBoolean(ddmFormFieldRenderingContext.isViewMode())
		).build();
	}

	protected String getModuleName(String mapProviderKey) {
		if (StringUtil.equals(mapProviderKey, "GoogleMaps")) {
			return _npmResolver.resolveModuleName(
				"@liferay/map-google-maps/js/MapGoogleMaps");
		}

		return _npmResolver.resolveModuleName(
			"@liferay/map-openstreetmap/js/MapOpenStreetMap");
	}

	private String _getGoogleMapsAPIKey(
		Group group, ThemeDisplay themeDisplay) {

		PortletPreferences companyPortletPreferences =
			PrefsPropsUtil.getPreferences(themeDisplay.getCompanyId());

		if (group == null) {
			return companyPortletPreferences.getValue("googleMapsAPIKey", null);
		}

		return GetterUtil.getString(
			group.getTypeSettingsProperty("googleMapsAPIKey"),
			companyPortletPreferences.getValue("googleMapsAPIKey", null));
	}

	private Group _getGroup(
		HttpServletRequest httpServletRequest, ThemeDisplay themeDisplay) {

		Group group = (Group)httpServletRequest.getAttribute("site.liveGroup");

		if (group != null) {
			return group;
		}

		group = themeDisplay.getScopeGroup();

		if (!group.isControlPanel()) {
			return group;
		}

		return null;
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private NPMResolver _npmResolver;

}