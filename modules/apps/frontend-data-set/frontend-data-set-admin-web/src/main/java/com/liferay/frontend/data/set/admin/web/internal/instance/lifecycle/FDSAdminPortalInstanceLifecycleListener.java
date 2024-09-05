/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.admin.web.internal.instance.lifecycle;

import com.liferay.frontend.data.set.admin.manager.FDSAdminObjectDefinitionsManager;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marko Cikos
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class FDSAdminPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		_fdsAdminObjectDefinitionsManager.generateObjectDefinitions(company);
	}

	@Reference
	private FDSAdminObjectDefinitionsManager _fdsAdminObjectDefinitionsManager;

}