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

package com.liferay.portal.security.sso.facebook.connect.language.resource.bundle;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.language.resource.bundle.BaseResourceBundlePublisher;
import com.liferay.portal.settings.web.constants.PortalSettingsPortletKeys;

import java.io.IOException;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stian Sigvartsen
 * @author Philip Jones
 */
@Component(immediate = true)
public class PortalSettingsPortletResourceBundlePublisher
	extends BaseResourceBundlePublisher {

	@Activate
	protected void activate(BundleContext bundleContext) throws IOException {
		super.activate(bundleContext);
	}

	@Deactivate
	protected void deactivate() {
		super.deactivate();
	}

	protected String getPortletName() {
		return PortalSettingsPortletKeys.PORTAL_SETTINGS;
	}

	@Modified
	protected void modified(BundleContext bundleContext) throws IOException {
		super.modified(bundleContext);
	}

	@Reference(unbind = "-")
	protected void setLanguageUtil(LanguageUtil languageUtil) {
		super.setLanguageUtil(languageUtil);
	}

}