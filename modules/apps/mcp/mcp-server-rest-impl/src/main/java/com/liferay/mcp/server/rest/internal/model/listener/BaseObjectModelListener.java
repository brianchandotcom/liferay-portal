/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.internal.model.listener;

import com.liferay.mcp.server.rest.internal.cache.MCPServerCacheManager;
import com.liferay.mcp.server.rest.internal.search.index.MCPToolIndexInvalidator;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.system.JaxRsApplicationDescriptor;
import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.object.system.SystemObjectDefinitionManagerRegistry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
public abstract class BaseObjectModelListener<T extends BaseModel<T>>
	extends BaseModelListener<T> {

	protected void invalidateToolSet(long objectDefinitionId) {
		invalidateToolSet(
			objectDefinitionLocalService.fetchObjectDefinition(
				objectDefinitionId));
	}

	protected void invalidateToolSet(ObjectDefinition objectDefinition) {
		if ((objectDefinition == null) || !objectDefinition.isApproved()) {
			return;
		}

		long companyId = objectDefinition.getCompanyId();

		mcpServerCacheManager.clearOpenAPIJSONObjectCache(companyId);
		mcpServerCacheManager.clearServletCache(companyId);

		mcpToolIndexInvalidator.invalidate(
			companyId, _getRESTContextPath(objectDefinition));
	}

	@Reference
	protected MCPServerCacheManager mcpServerCacheManager;

	@Reference
	protected MCPToolIndexInvalidator mcpToolIndexInvalidator;

	@Reference
	protected ObjectDefinitionLocalService objectDefinitionLocalService;

	@Reference
	protected SystemObjectDefinitionManagerRegistry
		systemObjectDefinitionManagerRegistry;

	private String _getRESTContextPath(ObjectDefinition objectDefinition) {
		if (!objectDefinition.isUnmodifiableSystemObject()) {
			return objectDefinition.getRESTContextPath();
		}

		if (systemObjectDefinitionManagerRegistry == null) {
			return null;
		}

		SystemObjectDefinitionManager systemObjectDefinitionManager =
			systemObjectDefinitionManagerRegistry.
				getSystemObjectDefinitionManager(objectDefinition.getName());

		if (systemObjectDefinitionManager == null) {
			return null;
		}

		JaxRsApplicationDescriptor jaxRsApplicationDescriptor =
			systemObjectDefinitionManager.getJaxRsApplicationDescriptor();

		if (jaxRsApplicationDescriptor == null) {
			return null;
		}

		String restContextPath =
			jaxRsApplicationDescriptor.getRESTContextPath();

		if (Validator.isNull(restContextPath)) {
			return null;
		}

		if (!StringUtil.startsWith(restContextPath, StringPool.SLASH)) {
			return StringPool.SLASH + restContextPath;
		}

		return restContextPath;
	}

}