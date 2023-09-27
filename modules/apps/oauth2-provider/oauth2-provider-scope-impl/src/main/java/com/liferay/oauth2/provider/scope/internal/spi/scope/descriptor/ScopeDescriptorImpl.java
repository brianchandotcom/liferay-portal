/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.scope.internal.spi.scope.descriptor;

import com.liferay.oauth2.provider.scope.spi.scope.descriptor.ScopeDescriptor;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Sierra Andrés
 */
@Component(property = "default=true", service = ScopeDescriptor.class)
public class ScopeDescriptorImpl implements ScopeDescriptor {

	@Override
	public String describeScope(String scope, Locale locale) {
		String key = "oauth2.scope." + scope;
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, getClass());

		return GetterUtil.getString(
			ResourceBundleUtil.getString(resourceBundle, key), scope);
	}

}