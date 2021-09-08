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

package com.liferay.object.service.impl;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectLayout;
import com.liferay.object.model.ObjectLayoutBox;
import com.liferay.object.model.ObjectLayoutColumn;
import com.liferay.object.model.ObjectLayoutRow;
import com.liferay.object.model.ObjectLayoutTab;
import com.liferay.object.service.base.ObjectLayoutLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.object.service.persistence.ObjectFieldPersistence;
import com.liferay.object.service.persistence.ObjectLayoutBoxPersistence;
import com.liferay.object.service.persistence.ObjectLayoutColumnPersistence;
import com.liferay.object.service.persistence.ObjectLayoutRowPersistence;
import com.liferay.object.service.persistence.ObjectLayoutTabPersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.object.model.ObjectLayout",
	service = AopService.class
)
public class ObjectLayoutLocalServiceImpl
	extends ObjectLayoutLocalServiceBaseImpl {

	@Override
	public ObjectLayout addObjectLayout(
			long userId, long objectDefinitionId, boolean defaultObjectLayout,
			Map<Locale, String> nameMap, JSONArray objectLayoutTabsJSONArray)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		ObjectLayout objectLayout = objectLayoutPersistence.create(
			counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		objectLayout.setCompanyId(user.getCompanyId());
		objectLayout.setUserId(user.getUserId());
		objectLayout.setUserName(user.getFullName());

		objectLayout.setObjectDefinitionId(
			objectDefinition.getObjectDefinitionId());
		objectLayout.setDefaultObjectLayout(defaultObjectLayout);
		objectLayout.setNameMap(nameMap);

		objectLayout = objectLayoutPersistence.update(objectLayout);

		_addObjectLayoutTabs(
			user, objectDefinitionId, objectLayout.getObjectLayoutId(),
			objectLayoutTabsJSONArray);

		return objectLayout;
	}

	@Override
	public List<ObjectLayout> getObjectLayouts(
		long objectDefinitionId, int start, int end) {

		return objectLayoutPersistence.findByObjectDefinitionId(
			objectDefinitionId, start, end);
	}

	@Override
	public int getObjectLayoutsCount(long objectDefinitionId) {
		return objectLayoutPersistence.countByObjectDefinitionId(
			objectDefinitionId);
	}

	private void _addObjectLayoutBox(
			User user, long objectDefinitionId, long objectLayoutTabId,
			JSONObject jsonObject)
		throws PortalException {

		ObjectLayoutBox objectLayoutBox = _objectLayoutBoxPersistence.create(
			counterLocalService.increment());

		objectLayoutBox.setCompanyId(user.getCompanyId());
		objectLayoutBox.setUserId(user.getUserId());
		objectLayoutBox.setUserName(user.getFullName());
		objectLayoutBox.setObjectLayoutTabId(objectLayoutTabId);
		objectLayoutBox.setCollapsable(jsonObject.getBoolean("collapsable"));
		//objectLayoutBox.setNameMap(nameMap);
		objectLayoutBox.setPriority(jsonObject.getInt("priority"));

		objectLayoutBox = _objectLayoutBoxPersistence.update(objectLayoutBox);

		_addObjectLayoutRows(
			user, objectDefinitionId, objectLayoutBox.getObjectLayoutBoxId(),
			jsonObject.getJSONArray("objectLayoutRow"));
	}

	private void _addObjectLayoutBoxes(
			User user, long objectDefinitionId, long objectLayoutTabId,
			JSONArray jsonArray)
		throws PortalException {

		for (int i = 0; i < jsonArray.length(); i++) {
			_addObjectLayoutBox(
				user, objectDefinitionId, objectLayoutTabId,
				jsonArray.getJSONObject(i));
		}
	}

	private void _addObjectLayoutColumn(
			User user, long objectDefinitionId, long objectLayoutRowId,
			JSONObject jsonObject)
		throws PortalException {

		ObjectField objectField = _objectFieldPersistence.findByPrimaryKey(
			jsonObject.getLong("objectFieldId"));

		if (objectField.getObjectDefinitionId() != objectDefinitionId) {

			// TODO

			throw new PortalException();
		}

		ObjectLayoutColumn objectLayoutColumn =
			_objectLayoutColumnPersistence.create(
				counterLocalService.increment());

		objectLayoutColumn.setCompanyId(user.getCompanyId());
		objectLayoutColumn.setUserId(user.getUserId());
		objectLayoutColumn.setUserName(user.getFullName());
		objectLayoutColumn.setObjectFieldId(objectField.getObjectFieldId());
		objectLayoutColumn.setObjectLayoutRowId(objectLayoutRowId);
		objectLayoutColumn.setPriority(jsonObject.getInt("priority"));

		_objectLayoutColumnPersistence.update(objectLayoutColumn);
	}

	private void _addObjectLayoutColumns(
			User user, long objectDefinitionId, long objectLayoutRowId,
			JSONArray jsonArray)
		throws PortalException {

		for (int i = 0; i < jsonArray.length(); i++) {
			_addObjectLayoutColumn(
				user, objectDefinitionId, objectLayoutRowId,
				jsonArray.getJSONObject(i));
		}
	}

	private void _addObjectLayoutRow(
			User user, long objectDefinitionId, long objectLayoutBoxId,
			JSONObject jsonObject)
		throws PortalException {

		ObjectLayoutRow objectLayoutRow = _objectLayoutRowPersistence.create(
			counterLocalService.increment());

		objectLayoutRow.setCompanyId(user.getCompanyId());
		objectLayoutRow.setUserId(user.getUserId());
		objectLayoutRow.setUserName(user.getFullName());
		objectLayoutRow.setObjectLayoutBoxId(objectLayoutBoxId);
		objectLayoutRow.setPriority(jsonObject.getInt("priority"));

		objectLayoutRow = _objectLayoutRowPersistence.update(objectLayoutRow);

		_addObjectLayoutColumns(
			user, objectDefinitionId, objectLayoutRow.getObjectLayoutRowId(),
			jsonObject.getJSONArray("objectLayoutColumn"));
	}

	private void _addObjectLayoutRows(
			User user, long objectDefinitionId, long objectLayoutBoxId,
			JSONArray jsonArray)
		throws PortalException {

		for (int i = 0; i < jsonArray.length(); i++) {
			_addObjectLayoutRow(
				user, objectDefinitionId, objectLayoutBoxId,
				jsonArray.getJSONObject(i));
		}
	}

	private void _addObjectLayoutTab(
			User user, long objectDefinitionId, long objectLayoutId,
			JSONObject jsonObject)
		throws PortalException {

		ObjectLayoutTab objectLayoutTab = _objectLayoutTabPersistence.create(
			counterLocalService.increment());

		objectLayoutTab.setCompanyId(user.getCompanyId());
		objectLayoutTab.setUserId(user.getUserId());
		objectLayoutTab.setUserName(user.getFullName());
		objectLayoutTab.setObjectLayoutId(objectLayoutId);
		//objectLayoutTab.setNameMap(nameMap);
		objectLayoutTab.setPriority(jsonObject.getInt("priority"));

		objectLayoutTab = _objectLayoutTabPersistence.update(objectLayoutTab);

		_addObjectLayoutBoxes(
			user, objectDefinitionId, objectLayoutTab.getObjectLayoutTabId(),
			jsonObject.getJSONArray("objectLayoutBox"));
	}

	private void _addObjectLayoutTabs(
			User user, long objectDefinitionId, long objectLayoutId,
			JSONArray jsonArray)
		throws PortalException {

		for (int i = 0; i < jsonArray.length(); i++) {
			_addObjectLayoutTab(
				user, objectDefinitionId, objectLayoutId,
				jsonArray.getJSONObject(i));
		}
	}

	@Reference
	private ObjectDefinitionPersistence _objectDefinitionPersistence;

	@Reference
	private ObjectFieldPersistence _objectFieldPersistence;

	@Reference
	private ObjectLayoutBoxPersistence _objectLayoutBoxPersistence;

	@Reference
	private ObjectLayoutColumnPersistence _objectLayoutColumnPersistence;

	@Reference
	private ObjectLayoutRowPersistence _objectLayoutRowPersistence;

	@Reference
	private ObjectLayoutTabPersistence _objectLayoutTabPersistence;

	@Reference
	private UserLocalService _userLocalService;

}