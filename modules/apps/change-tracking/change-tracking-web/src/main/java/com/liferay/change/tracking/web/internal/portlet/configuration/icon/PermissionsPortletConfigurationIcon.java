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

package com.liferay.change.tracking.web.internal.portlet.configuration.icon;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.web.internal.constants.CTPortletKeys;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.security.PermissionsURLTag;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = {})
public class PermissionsPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return LanguageUtil.get(
			getResourceBundle(getLocale(portletRequest)),
			"publication-permissions");
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			locale, PermissionsPortletConfigurationIcon.class);
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			return PermissionsURLTag.doTag(
				StringPool.BLANK, CTConstants.RESOURCE_NAME, null, null,
				CTConstants.RESOURCE_NAME, LiferayWindowState.POP_UP.toString(),
				new int[] {RoleConstants.TYPE_REGULAR},
				themeDisplay.getRequest());
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		if (!CTCollectionThreadLocal.isProductionMode()) {
			return false;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return _portletResourcePermission.contains(
			themeDisplay.getPermissionChecker(), null, ActionKeys.PERMISSIONS);
	}

	@Override
	public boolean isUseDialog() {
		return true;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_publicationsServiceRegistration = bundleContext.registerService(
			PortletConfigurationIcon.class, this,
			MapUtil.singletonDictionary(
				"javax.portlet.name", CTPortletKeys.PUBLICATIONS));

		_publicationsConfigurationServiceRegistration =
			bundleContext.registerService(
				PortletConfigurationIcon.class, this,
				MapUtil.singletonDictionary(
					"javax.portlet.name",
					CTPortletKeys.PUBLICATIONS_CONFIGURATION));
	}

	@Deactivate
	protected void deactivate() {
		_publicationsServiceRegistration.unregister();

		_publicationsConfigurationServiceRegistration.unregister();
	}

	@Reference(target = "(resource.name=" + CTConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

	private ServiceRegistration<?>
		_publicationsConfigurationServiceRegistration;
	private ServiceRegistration<?> _publicationsServiceRegistration;

}