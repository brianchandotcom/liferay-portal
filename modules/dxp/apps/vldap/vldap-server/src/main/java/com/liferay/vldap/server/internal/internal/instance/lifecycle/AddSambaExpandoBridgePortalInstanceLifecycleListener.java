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

package com.liferay.vldap.server.internal.internal.instance.lifecycle;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jonathan McCann
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class AddSambaExpandoBridgePortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company)
		throws PortalException {

		ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(
			company.getCompanyId(), User.class.getName());

		if (!expandoBridge.hasAttribute(_SAMBA_LM_PASSWORD)) {
			expandoBridge.addAttribute(_SAMBA_LM_PASSWORD, false);
		}

		if (!expandoBridge.hasAttribute(_SAMBA_NT_PASSWORD)) {
			expandoBridge.addAttribute(_SAMBA_NT_PASSWORD, false);
		}

		UnicodeProperties properties = new UnicodeProperties();

		properties.put(ExpandoColumnConstants.PROPERTY_HIDDEN, StringPool.TRUE);

		expandoBridge.setAttributeProperties(
			_SAMBA_LM_PASSWORD, properties, false);
		expandoBridge.setAttributeProperties(
			_SAMBA_NT_PASSWORD, properties, false);
	}

	private static final String _SAMBA_LM_PASSWORD = "sambaLMPassword";

	private static final String _SAMBA_NT_PASSWORD = "sambaNTPassword";

}