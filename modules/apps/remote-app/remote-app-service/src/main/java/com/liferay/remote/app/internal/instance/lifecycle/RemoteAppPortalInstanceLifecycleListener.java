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

package com.liferay.remote.app.internal.instance.lifecycle;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.remote.app.model.RemoteAppEntry;
import com.liferay.remote.app.service.RemoteAppEntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class RemoteAppPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	public void portalInstanceRegistered(Company company) throws Exception {
		long count = _remoteAppEntryLocalService.countByCompanyId(
			company.getCompanyId());

		if (count != 0) {
			return;
		}

		_remoteAppEntryLocalService.addCustomElementRemoteAppEntry(
			_userLocalService.getDefaultUserId(company.getCompanyId()),
			StringPool.BLANK, "vanilla-counter",
			"https://liferay.github.io/liferay-frontend-projects" +
				"/vanilla-counter/index.js",
			"Sample vanilla counter remote application", "vanilla_counter",
			false,
			LocalizationUtil.getMap(new LocalizedValuesMap("Vanilla Counter")),
			"category.remote-apps", "friendly-url-mapping=vanilla_counter",
			"https://liferay.github.io/liferay-frontend-projects");
	}

	@Reference
	private RemoteAppEntryLocalService _remoteAppEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.remote.app.model.RemoteAppEntry)"
	)
	private WorkflowHandler<RemoteAppEntry> _workflowHandler;

}