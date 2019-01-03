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
@Component(immediate = true, service = ModelListener.class)
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
	public void onAfterUpdate(SiteNavigationMenuItem siteNavigationMenuItem)
		throws ModelListenerException {

		_stagingModelListener.onAfterUpdate(siteNavigationMenuItem);
	}

	@Reference
	private StagingModelListener<SiteNavigationMenuItem> _stagingModelListener;

}