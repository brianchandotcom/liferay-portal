/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.scope.liferay.spi;

import com.liferay.oauth2.provider.scope.spi.scope.descriptor.ScopeDescriptor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Carlos Sierra Andrés
 */
@ProviderType
public interface ScopeDescriptorLocator {

	public ScopeDescriptor getScopeDescriptor(long companyId);

	public ScopeDescriptor getScopeDescriptor(
		long companyId, String applicationName);

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public ScopeDescriptor getScopeDescriptor(String applicationName);

}