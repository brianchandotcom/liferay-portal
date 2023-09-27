/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.scope.liferay.spi;

import com.liferay.oauth2.provider.scope.spi.application.descriptor.ApplicationDescriptor;

/**
 * @author Tomas Polesovsky
 */
public interface ApplicationDescriptorLocator {

	public ApplicationDescriptor getApplicationDescriptor(
		String applicationName);

}