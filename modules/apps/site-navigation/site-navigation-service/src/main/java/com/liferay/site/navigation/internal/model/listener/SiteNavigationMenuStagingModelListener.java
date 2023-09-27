/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.staging.model.listener.StagingModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(service = ModelListener.class)
public class SiteNavigationMenuStagingModelListener
	extends BaseModelListener<SiteNavigationMenu> {

	@Override
	public void onAfterCreate(SiteNavigationMenu siteNavigationMenu)
		throws ModelListenerException {

		_stagingModelListener.onAfterCreate(siteNavigationMenu);
	}

	@Override
	public void onAfterRemove(SiteNavigationMenu siteNavigationMenu)
		throws ModelListenerException {

		_stagingModelListener.onAfterRemove(siteNavigationMenu);
	}

	@Override
	public void onAfterUpdate(
			SiteNavigationMenu originalSiteNavigationMenu,
			SiteNavigationMenu siteNavigationMenu)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(siteNavigationMenu);
	}

	@Reference
	private StagingModelListener<SiteNavigationMenu> _stagingModelListener;

}