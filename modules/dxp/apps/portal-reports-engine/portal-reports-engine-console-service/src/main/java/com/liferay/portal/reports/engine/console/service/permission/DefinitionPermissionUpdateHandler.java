/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.reports.engine.console.service.permission;

import com.liferay.portal.kernel.security.permission.PermissionUpdateHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.reports.engine.console.model.Definition;
import com.liferay.portal.reports.engine.console.service.DefinitionLocalService;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Prathima Shreenath
 */
@Component(
	property = "model.class.name=com.liferay.portal.reports.engine.console.model.Definition",
	service = PermissionUpdateHandler.class
)
public class DefinitionPermissionUpdateHandler
	implements PermissionUpdateHandler {

	@Override
	public void updatedPermission(String primKey) {
		Definition definition = _definitionLocalService.fetchDefinition(
			GetterUtil.getLong(primKey));

		if (definition == null) {
			return;
		}

		definition.setModifiedDate(new Date());

		_definitionLocalService.updateDefinition(definition);
	}

	@Reference
	private DefinitionLocalService _definitionLocalService;

}