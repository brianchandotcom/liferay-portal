/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.data.handler;

import com.liferay.exportimport.kernel.lar.PortletDataHandler;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Daniel Raposo
 */
@ProviderType
public interface BatchEnginePortletDataHandlerRegistry {

	public PortletDataHandler getByKey(long companyId, String key);

}