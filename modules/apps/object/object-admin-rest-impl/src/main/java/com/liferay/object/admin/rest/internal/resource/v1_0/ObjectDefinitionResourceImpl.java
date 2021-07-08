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

package com.liferay.object.admin.rest.internal.resource.v1_0;

import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.dto.v1_0.Status;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/object-definition.properties",
	scope = ServiceScope.PROTOTYPE, service = ObjectDefinitionResource.class
)
public class ObjectDefinitionResourceImpl
	extends BaseObjectDefinitionResourceImpl {

	@Override
	public void deleteObjectDefinition(Long objectDefinitionId)
		throws Exception {

		_objectDefinitionLocalService.deleteObjectDefinition(
			objectDefinitionId);
	}

	@Override
	public ObjectDefinition getObjectDefinition(Long objectDefinitionId)
		throws Exception {

		return _toObjectDefinition(
			_objectDefinitionLocalService.getObjectDefinition(
				objectDefinitionId));
	}

	@Override
	public Page<ObjectDefinition> getObjectDefinitionsPage(
		Pagination pagination) {

		return Page.of(
			transform(
				_objectDefinitionLocalService.getObjectDefinitions(
					pagination.getStartPosition(), pagination.getEndPosition()),
				this::_toObjectDefinition),
			pagination,
			_objectDefinitionLocalService.getObjectDefinitionsCount());
	}

	@Override
	public ObjectDefinition postObjectDefinition(
			ObjectDefinition objectDefinition)
		throws Exception {

		return _toObjectDefinition(
			_objectDefinitionLocalService.addCustomObjectDefinition(
				contextUser.getUserId(), objectDefinition.getName(), null));
	}

	protected Map<String, String> addAction(String actionKey) {

		//TODO Replace this with proper logic using permission

		return HashMapBuilder.put(
			actionKey, ""
		).build();
	}

	private ObjectDefinition _toObjectDefinition(
		com.liferay.object.model.ObjectDefinition objectDefinition) {

		ResourceBundle resourceBundle = LanguageResources.getResourceBundle(
			contextAcceptLanguage.getPreferredLocale());

		HashMapBuilder.HashMapWrapper<String, Map<String, String>> mapBuilder =
			HashMapBuilder.<String, Map<String, String>>put(
				"get", addAction(ActionKeys.VIEW)
			).put(
				"update", addAction(ActionKeys.UPDATE)
			);

		if (!objectDefinition.isSystem()) {
			mapBuilder.put("delete", addAction(ActionKeys.DELETE));
		}

		return new ObjectDefinition() {
			{
				actions = mapBuilder.build();
				dateCreated = objectDefinition.getCreateDate();
				dateModified = objectDefinition.getModifiedDate();
				id = objectDefinition.getObjectDefinitionId();
				name = objectDefinition.getName();
				status = new Status() {
					{
						code = objectDefinition.getStatus();
						label = WorkflowConstants.getStatusLabel(
							objectDefinition.getStatus());
						label_i18n = LanguageUtil.get(
							resourceBundle,
							WorkflowConstants.getStatusLabel(
								objectDefinition.getStatus()));
					}
				};
				system = objectDefinition.isSystem();
			}
		};
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}