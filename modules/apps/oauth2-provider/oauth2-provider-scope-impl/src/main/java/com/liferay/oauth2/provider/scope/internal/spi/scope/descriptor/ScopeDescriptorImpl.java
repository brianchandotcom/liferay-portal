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

package com.liferay.oauth2.provider.scope.internal.spi.scope.descriptor;

import com.liferay.oauth2.provider.scope.spi.scope.descriptor.ScopeDescriptor;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Carlos Sierra Andrés
 */
@Component(property = "default=true", service = ScopeDescriptor.class)
public class ScopeDescriptorImpl implements ScopeDescriptor {

	@Override
	public String describeScope(String scope, Locale locale) {
		String key = "oauth2.scope." + scope;
		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(locale);

		return GetterUtil.getString(
			ResourceBundleUtil.getString(resourceBundle, key), scope);
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(bundle.symbolic.name=com.liferay.oauth2.provider.scope.impl)"
	)
	private volatile ResourceBundleLoader _resourceBundleLoader;

}