/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.staging.model.listener.StagingModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(service = ModelListener.class)
public class SiteNavigationMenuItemStagingModelListener
	extends BaseModelListener<SiteNavigationMenuItem> {

	@Override
	public void onAfterCreate(SiteNavigationMenuItem siteNavigationMenuItem)
		throws ModelListenerException {

		_stagingModelListener.onAfterCreate(siteNavigationMenuItem);
	}

	@Override
	public void onAfterRemove(SiteNavigationMenuItem siteNavigationMenuItem)
		throws ModelListenerException {

		_stagingModelListener.onAfterRemove(siteNavigationMenuItem);
	}

	@Override
	public void onAfterUpdate(
			SiteNavigationMenuItem originalSiteNavigationMenuItem,
			SiteNavigationMenuItem siteNavigationMenuItem)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(siteNavigationMenuItem);
	}

	@Reference
	private StagingModelListener<SiteNavigationMenuItem> _stagingModelListener;

}