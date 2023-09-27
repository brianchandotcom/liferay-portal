/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.related.models;

import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface ObjectRelatedModelsProviderRegistry {

	public ObjectRelatedModelsProvider getObjectRelatedModelsProvider(
			String className, long companyId, String type)
		throws PortalException;

}