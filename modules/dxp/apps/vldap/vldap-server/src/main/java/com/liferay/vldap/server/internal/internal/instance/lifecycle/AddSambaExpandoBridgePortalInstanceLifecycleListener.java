/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.vldap.server.internal.internal.instance.lifecycle;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
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