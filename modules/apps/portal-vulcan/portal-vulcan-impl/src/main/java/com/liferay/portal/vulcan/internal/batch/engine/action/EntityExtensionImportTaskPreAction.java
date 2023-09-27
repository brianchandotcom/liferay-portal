/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.batch.engine.action;

import com.liferay.batch.engine.action.ImportTaskPreAction;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.portal.vulcan.extension.EntityExtensionHandler;
import com.liferay.portal.vulcan.extension.EntityExtensionThreadLocal;
import com.liferay.portal.vulcan.extension.ExtensionProviderRegistry;
import com.liferay.portal.vulcan.extension.util.ExtensionUtil;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Correa
 */
@Component(service = ImportTaskPreAction.class)
public class EntityExtensionImportTaskPreAction implements ImportTaskPreAction {

	@Override
	public void run(BatchEngineImportTask batchEngineImportTask, Object item)
		throws Exception {

		EntityExtensionHandler entityExtensionHandler =
			ExtensionUtil.getEntityExtensionHandler(
				batchEngineImportTask.getClassName(),
				batchEngineImportTask.getCompanyId(),
				_extensionProviderRegistry);

		if (entityExtensionHandler == null) {
			return;
		}

		Map<String, Serializable> extendedProperties =
			ExtensionUtil.getExtendedProperties(item);

		if (extendedProperties == null) {
			return;
		}

		EntityExtensionThreadLocal.setExtendedProperties(extendedProperties);
	}

	@Reference
	private ExtensionProviderRegistry _extensionProviderRegistry;

}