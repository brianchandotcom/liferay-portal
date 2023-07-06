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

package com.liferay.portal.vulcan.internal.batch.engine.action;

import com.liferay.batch.engine.BatchEngineTaskOperation;
import com.liferay.batch.engine.action.ItemReaderPostAction;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.extension.EntityExtensionHandler;
import com.liferay.portal.vulcan.extension.ExtensionProviderRegistry;
import com.liferay.portal.vulcan.extension.util.ExtensionUtil;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Correa
 */
@Component(service = ItemReaderPostAction.class)
public class EntityExtensionItemReaderPostAction
	implements ItemReaderPostAction {

	@Override
	public void run(
			BatchEngineImportTask batchEngineImportTask,
			Map<String, Serializable> extendedProperties, Object item)
		throws ReflectiveOperationException {

		EntityExtensionHandler entityExtensionHandler =
			ExtensionUtil.getEntityExtensionHandler(
				batchEngineImportTask.getClassName(),
				batchEngineImportTask.getCompanyId(),
				_extensionProviderRegistry);

		if (entityExtensionHandler == null) {
			if (MapUtil.isNotEmpty(extendedProperties)) {
				throw new NoSuchFieldException(
					String.valueOf(extendedProperties.keySet()));
			}

			return;
		}

		try {
			entityExtensionHandler.validate(
				batchEngineImportTask.getCompanyId(), extendedProperties,
				_isPartialUpdate(batchEngineImportTask));

			ExtensionUtil.setExtendedProperties(item, extendedProperties);
		}
		catch (Exception exception) {
			throw new ReflectiveOperationException(exception);
		}
	}

	private boolean _isPartialUpdate(
		BatchEngineImportTask batchEngineImportTask) {

		Map<String, Serializable> parameters =
			batchEngineImportTask.getParameters();

		if (parameters == null) {
			return false;
		}

		BatchEngineTaskOperation batchEngineTaskOperation =
			BatchEngineTaskOperation.valueOf(
				batchEngineImportTask.getOperation());
		String createStrategy = MapUtil.getString(parameters, "createStrategy");
		String updateStrategy = MapUtil.getString(parameters, "updateStrategy");

		if ((batchEngineTaskOperation == BatchEngineTaskOperation.CREATE) &&
			StringUtil.equals(createStrategy, "UPSERT") &&
			StringUtil.equals(updateStrategy, "PARTIAL_UPDATE")) {

			return true;
		}

		if ((batchEngineTaskOperation == BatchEngineTaskOperation.UPDATE) &&
			StringUtil.equals(updateStrategy, "PARTIAL_UPDATE")) {

			return true;
		}

		return false;
	}

	@Reference
	private ExtensionProviderRegistry _extensionProviderRegistry;

}