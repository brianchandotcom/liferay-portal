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

package com.liferay.portal.vulcan.internal.extension.util;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.vulcan.extension.ExtensionProvider;
import com.liferay.portal.vulcan.extension.ExtensionProviderRegistry;
import com.liferay.portal.vulcan.internal.extension.EntityExtensionHandler;

import java.util.List;

/**
 * @author Carlos Correa
 */
public class ExtensionUtil {

	public static EntityExtensionHandler getEntityExtensionHandler(
		String className, long companyId,
		ExtensionProviderRegistry extensionProviderRegistry) {

		List<ExtensionProvider> extensionProviders =
			extensionProviderRegistry.getExtensionProviders(
				companyId, className);

		if (ListUtil.isEmpty(extensionProviders)) {
			return null;
		}

		return new EntityExtensionHandler(className, extensionProviders);
	}

}