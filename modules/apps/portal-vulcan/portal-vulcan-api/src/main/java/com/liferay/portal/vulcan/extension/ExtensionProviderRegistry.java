/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.extension;

import java.util.List;

/**
 * @author Javier de Arcos
 */
public interface ExtensionProviderRegistry {

	public List<ExtensionProvider> getExtensionProviders(
		long companyId, String className);

}