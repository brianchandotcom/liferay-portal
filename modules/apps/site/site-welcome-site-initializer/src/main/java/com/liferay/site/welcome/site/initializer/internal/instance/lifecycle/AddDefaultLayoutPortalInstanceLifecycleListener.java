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

package com.liferay.site.welcome.site.initializer.internal.instance.lifecycle;

import com.liferay.fragment.contributor.FragmentCollectionContributorRegistration;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.FriendlyURLNormalizer;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.store.StoreFactory;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.welcome.site.initializer.internal.WelcomeSiteInitializer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class AddDefaultLayoutPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		Group group = _groupLocalService.getGroup(
			company.getCompanyId(), GroupConstants.GUEST);

		String friendlyURL = _friendlyURLNormalizer.normalizeWithEncoding(
			PropsValues.DEFAULT_GUEST_PUBLIC_LAYOUT_FRIENDLY_URL);

		Layout defaultLayout = _layoutLocalService.fetchLayoutByFriendlyURL(
			group.getGroupId(), false, friendlyURL);

		if (defaultLayout == null) {
			defaultLayout = _layoutLocalService.fetchFirstLayout(
				group.getGroupId(), false,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, false);

			if (defaultLayout == null) {
				_siteInitializer.initialize(group.getGroupId());
			}
		}
	}

	@Reference(
		target = "(fragment.collection.key=BASIC_COMPONENT)", unbind = "-"
	)
	protected void setFragmentCollectionContributorRegistration(
		FragmentCollectionContributorRegistration
			fragmentCollectionContributorRegistration) {
	}

	@Reference(
		target = ModuleServiceLifecycle.PORTLETS_INITIALIZED, unbind = "-"
	)
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference
	private FriendlyURLNormalizer _friendlyURLNormalizer;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference(
		target = "(site.initializer.key=" + WelcomeSiteInitializer.KEY + ")"
	)
	private SiteInitializer _siteInitializer;

	@Reference(target = "(dl.store.impl.enabled=true)")
	private StoreFactory _storeFactory;

}