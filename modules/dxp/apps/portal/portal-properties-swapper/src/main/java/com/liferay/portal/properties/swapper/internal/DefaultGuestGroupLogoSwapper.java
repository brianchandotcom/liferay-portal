/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.properties.swapper.internal;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.util.Portal;

import java.io.InputStream;

import java.net.URL;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(enabled = false, service = {})
public class DefaultGuestGroupLogoSwapper {

	@Activate
	protected void activate(BundleContext bundleContext) throws Exception {
		Group group = _groupLocalService.getGroup(
			_portal.getDefaultCompanyId(), GroupConstants.GUEST);

		LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
			group.getGroupId(), false);

		if (layoutSet.getLogoId() != 0) {
			return;
		}

		Bundle bundle = bundleContext.getBundle();

		URL url = bundle.getResource(
			"com/liferay/portal/properties/swapper/internal" +
				"/default_guest_group_logo.png");

		try (InputStream inputStream = url.openStream()) {
			_layoutSetLocalService.updateLogo(
				group.getGroupId(), false, true, inputStream);
		}
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference(target = ModuleServiceLifecycle.PORTLETS_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private Portal _portal;

}