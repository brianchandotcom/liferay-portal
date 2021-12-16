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

package com.liferay.redirect.internal.tracker;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.redirect.internal.configuration.RedirectConfiguration;
import com.liferay.redirect.service.RedirectNotFoundEntryLocalService;
import com.liferay.redirect.tracker.RedirectNotFoundTracker;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	configurationPid = "com.liferay.redirect.internal.configuration.RedirectConfiguration",
	service = RedirectNotFoundTracker.class
)
public class RedirectNotFoundTrackerImpl implements RedirectNotFoundTracker {

	@Override
	public void trackURL(Group group, String url) {
		if (_redirectConfiguration.enabled()) {
			_redirectNotFoundEntryLocalService.addOrUpdateRedirectNotFoundEntry(
				group, url);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_redirectConfiguration = ConfigurableUtil.createConfigurable(
			RedirectConfiguration.class, properties);
	}

	private volatile RedirectConfiguration _redirectConfiguration;

	@Reference
	private RedirectNotFoundEntryLocalService
		_redirectNotFoundEntryLocalService;

}