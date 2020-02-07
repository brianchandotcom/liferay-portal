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

package com.liferay.sharing.web.internal.servlet.taglib.ui;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.servlet.taglib.ui.BaseJSPFormNavigatorEntry;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorConstants;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.sharing.configuration.SharingConfiguration;
import com.liferay.sharing.configuration.SharingConfigurationFactory;
import com.liferay.sharing.web.internal.constants.SharingWebKeys;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = FormNavigatorEntry.class)
public class SharingSitesFormNavigatorEntry
	extends BaseJSPFormNavigatorEntry<Group>
	implements FormNavigatorEntry<Group> {

	@Override
	public String getCategoryKey() {
		return FormNavigatorConstants.CATEGORY_KEY_SITES_GENERAL;
	}

	@Override
	public String getFormNavigatorId() {
		return FormNavigatorConstants.FORM_NAVIGATOR_ID_SITES;
	}

	@Override
	public String getKey() {
		return "sharing-configuration-name";
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, getKey());
	}

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		Group liveGroup = (Group)httpServletRequest.getAttribute(
			"site.liveGroup");

		httpServletRequest.setAttribute(
			SharingWebKeys.GROUP_SHARING_CONFIGURATION,
			_sharingConfigurationFactory.getGroupSharingConfiguration(
				liveGroup));

		super.include(httpServletRequest, httpServletResponse);
	}

	@Override
	public boolean isVisible(User user, Group group) {
		SharingConfiguration groupSharingConfiguration =
			_sharingConfigurationFactory.getGroupSharingConfiguration(group);

		return groupSharingConfiguration.isAvailable();
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.sharing.web)", unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Override
	protected String getJspPath() {
		return "/sites_admin/sharing.jsp";
	}

	@Reference(target = "(bundle.symbolic.name=com.liferay.sharing.web)")
	private ResourceBundleLoader _resourceBundleLoader;

	@Reference
	private SharingConfigurationFactory _sharingConfigurationFactory;

}