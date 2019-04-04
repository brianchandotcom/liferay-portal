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

package com.liferay.product.navigation.personal.menu;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.product.navigation.personal.menu.configuration.PersonalMenuConfiguration;
import com.liferay.product.navigation.personal.menu.configuration.PersonalMenuConfigurationTracker;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Reference;

/**
 * Provides a skeletal implementation of the {@link PersonalMenuEntry} to
 * minimize the effort required to implement this interface.
 *
 * To implement a user personal menu entry, this class should be extended and
 * {@link #getPortletId()} and {@link#setPortlet(Portlet)} should be overridden.
 *
 * @author Pei-Jung Lan
 * @review
 */
public abstract class BasePersonalMenuEntry implements PersonalMenuEntry {

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(
			getResourceBundle(locale),
			JavaConstants.JAVAX_PORTLET_TITLE + StringPool.PERIOD +
				getPortletId());
	}

	@Override
	public String getPortletURL(HttpServletRequest request)
		throws PortalException {

		if (Validator.isNull(getPortletId())) {
			return null;
		}

		User user = PortalUtil.getUser(request);

		Group group = user.getGroup();

		boolean privateLayout = true;

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PersonalMenuConfiguration personalMenuConfiguration =
			personalMenuConfigurationTracker.
				getCompanyPersonalMenuConfiguration(
					themeDisplay.getCompanyId());

		String personalApplicationsLookAndFeel =
			personalMenuConfiguration.personalApplicationsLookAndFeel();

		if (personalApplicationsLookAndFeel.equals("current-site")) {
			user = UserLocalServiceUtil.getDefaultUser(
				themeDisplay.getCompanyId());

			group = GroupLocalServiceUtil.getGroup(
				PortalUtil.getScopeGroupId(request));

			Layout currentLayout = themeDisplay.getLayout();

			if (currentLayout.isPublicLayout()) {
				privateLayout = false;
			}
		}

		Layout layout = null;

		try {
			layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
				group.getGroupId(), privateLayout,
				PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL);
		}
		catch (NoSuchLayoutException nsle) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(nsle, nsle);
			}

			layout = addEmbeddedPersonalApplicationLayout(
				user.getUserId(), group.getGroupId(), privateLayout);
		}

		LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(
			request, getPortletId(), layout, PortletRequest.RENDER_PHASE);

		return liferayPortletURL.toString();
	}

	@Override
	public boolean isActive(PortletRequest portletRequest, String portletId) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		String layoutFriendlyURL = layout.getFriendlyURL();

		if ((!layout.isSystem() && !layout.isTypeControlPanel()) ||
			!layoutFriendlyURL.equals(
				PropsUtil.get(PropsKeys.CONTROL_PANEL_LAYOUT_FRIENDLY_URL))) {

			return false;
		}

		if (portletId.equals(getPortletId())) {
			return true;
		}

		return false;
	}

	protected Layout addEmbeddedPersonalApplicationLayout(
			long userId, long groupId, boolean privateLayout)
		throws PortalException {

		String friendlyURL = FriendlyURLNormalizerUtil.normalize(
			PropsValues.CONTROL_PANEL_LAYOUT_FRIENDLY_URL);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAttribute(
			"layout.instanceable.allowed", Boolean.TRUE);

		Layout layout = LayoutLocalServiceUtil.addLayout(
			userId, groupId, privateLayout,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			PropsValues.CONTROL_PANEL_LAYOUT_NAME, StringPool.BLANK,
			StringPool.BLANK, LayoutConstants.TYPE_PORTLET, true, true,
			friendlyURL, serviceContext);

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		layoutTypePortlet.setLayoutTemplateId(
			userId, "1_column_dynamic", false);

		return LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());
	}

	/**
	 * Returns the portlet's ID associated with the user personal menu entry.
	 *
	 * @return the portlet's ID associated with the user personal menu entry
	 * @review
	 */
	protected abstract String getPortletId();

	protected ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(locale, getClass());
	}

	@Reference
	protected PersonalMenuConfigurationTracker personalMenuConfigurationTracker;

	private static final Log _log = LogFactoryUtil.getLog(
		BasePersonalMenuEntry.class);

}