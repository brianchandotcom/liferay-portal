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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.ArrayList;
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
			Map<Locale, String> nameMap, List<ObjectLayoutTab> objectLayoutTabs)
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

		objectLayout.setObjectLayoutTabs(
			_addObjectLayoutTabs(
				user, objectDefinitionId, objectLayout.getObjectLayoutId(),
				objectLayoutTabs));

		return objectLayout;
	}

	@Override
	public ObjectLayout deleteObjectLayout(long objectLayoutId)
		throws PortalException {

		ObjectLayout objectLayout = objectLayoutPersistence.findByPrimaryKey(
			objectLayoutId);

		_deleteObjectLayoutTabs(objectLayoutId);

		objectLayoutPersistence.remove(objectLayoutId);

		return objectLayout;
	}

	@Override
	public ObjectLayout getObjectLayout(long objectLayoutId)
		throws PortalException {

		ObjectLayout objectLayout = objectLayoutPersistence.findByPrimaryKey(
			objectLayoutId);

		List<ObjectLayoutTab> objectLayoutTabs =
			_objectLayoutTabPersistence.findByObjectLayoutId(objectLayoutId);

		for (ObjectLayoutTab objectLayoutTab : objectLayoutTabs) {
			objectLayoutTab.setObjectLayoutBoxes(
				_getObjectLayoutBoxes(objectLayoutTab));
		}

		objectLayout.setObjectLayoutTabs(objectLayoutTabs);

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

	private ObjectLayoutBox _addObjectLayoutBox(
			User user, long objectDefinitionId, long objectLayoutTabId,
			boolean collapsable, Map<Locale, String> nameMap, int priority,
			List<ObjectLayoutRow> objectLayoutRows)
		throws PortalException {

		ObjectLayoutBox objectLayoutBox = _objectLayoutBoxPersistence.create(
			counterLocalService.increment());

		objectLayoutBox.setCompanyId(user.getCompanyId());
		objectLayoutBox.setUserId(user.getUserId());
		objectLayoutBox.setUserName(user.getFullName());
		objectLayoutBox.setObjectLayoutTabId(objectLayoutTabId);
		objectLayoutBox.setCollapsable(collapsable);
		objectLayoutBox.setNameMap(nameMap);
		objectLayoutBox.setPriority(priority);

		objectLayoutBox = _objectLayoutBoxPersistence.update(objectLayoutBox);

		objectLayoutBox.setObjectLayoutRows(
			_addObjectLayoutRows(
				user, objectDefinitionId,
				objectLayoutBox.getObjectLayoutBoxId(), objectLayoutRows));

		return objectLayoutBox;
	}

	private List<ObjectLayoutBox> _addObjectLayoutBoxes(
			User user, long objectDefinitionId, long objectLayoutTabId,
			List<ObjectLayoutBox> objectLayoutBoxes)
		throws PortalException {

		List<ObjectLayoutBox> newObjectLayoutBoxes = new ArrayList<>();

		for (ObjectLayoutBox objectLayoutBox : objectLayoutBoxes) {
			newObjectLayoutBoxes.add(
				_addObjectLayoutBox(
					user, objectDefinitionId, objectLayoutTabId,
					objectLayoutBox.isCollapsable(),
					objectLayoutBox.getNameMap(), objectLayoutBox.getPriority(),
					objectLayoutBox.getObjectLayoutRows()));
		}

		return newObjectLayoutBoxes;
	}

	private ObjectLayoutColumn _addObjectLayoutColumn(
			User user, long objectDefinitionId, long objectLayoutRowId,
			long objectFieldId, int priority)
		throws PortalException {

		ObjectLayoutColumn objectLayoutColumn =
			_objectLayoutColumnPersistence.create(
				counterLocalService.increment());

		ObjectField objectField = _objectFieldPersistence.findByPrimaryKey(
			objectFieldId);

		if (objectField.getObjectDefinitionId() != objectDefinitionId) {

			// TODO

			throw new PortalException();
		}

		objectLayoutColumn.setCompanyId(user.getCompanyId());
		objectLayoutColumn.setUserId(user.getUserId());
		objectLayoutColumn.setUserName(user.getFullName());
		objectLayoutColumn.setObjectFieldId(objectField.getObjectFieldId());
		objectLayoutColumn.setObjectLayoutRowId(objectLayoutRowId);
		objectLayoutColumn.setPriority(priority);

		return _objectLayoutColumnPersistence.update(objectLayoutColumn);
	}

	private List<ObjectLayoutColumn> _addObjectLayoutColumns(
			User user, long objectDefinitionId, long objectLayoutRowId,
			List<ObjectLayoutColumn> objectLayoutColumns)
		throws PortalException {

		List<ObjectLayoutColumn> addObjectLayoutColumns = new ArrayList<>();

		for (ObjectLayoutColumn objectLayoutColumn : objectLayoutColumns) {
			addObjectLayoutColumns.add(
				_addObjectLayoutColumn(
					user, objectDefinitionId, objectLayoutRowId,
					objectLayoutColumn.getObjectFieldId(),
					objectLayoutColumn.getPriority()));
		}

		return addObjectLayoutColumns;
	}

	private ObjectLayoutRow _addObjectLayoutRow(
			User user, long objectDefinitionId, long objectLayoutBoxId,
			int priority, List<ObjectLayoutColumn> objectLayoutColumns)
		throws PortalException {

		ObjectLayoutRow objectLayoutRow = _objectLayoutRowPersistence.create(
			counterLocalService.increment());

		objectLayoutRow.setCompanyId(user.getCompanyId());
		objectLayoutRow.setUserId(user.getUserId());
		objectLayoutRow.setUserName(user.getFullName());
		objectLayoutRow.setObjectLayoutBoxId(objectLayoutBoxId);
		objectLayoutRow.setPriority(priority);

		objectLayoutRow = _objectLayoutRowPersistence.update(objectLayoutRow);

		objectLayoutRow.setObjectLayoutColumns(
			_addObjectLayoutColumns(
				user, objectDefinitionId,
				objectLayoutRow.getObjectLayoutRowId(), objectLayoutColumns));

		return objectLayoutRow;
	}

	private List<ObjectLayoutRow> _addObjectLayoutRows(
			User user, long objectDefinitionId, long objectLayoutBoxId,
			List<ObjectLayoutRow> objectLayoutRows)
		throws PortalException {

		List<ObjectLayoutRow> addObjectLayoutRows = new ArrayList<>();

		for (ObjectLayoutRow objectLayoutRow : objectLayoutRows) {
			addObjectLayoutRows.add(
				_addObjectLayoutRow(
					user, objectDefinitionId, objectLayoutBoxId,
					objectLayoutRow.getPriority(),
					objectLayoutRow.getObjectLayoutColumns()));
		}

		return addObjectLayoutRows;
	}

	private ObjectLayoutTab _addObjectLayoutTab(
			User user, long objectDefinitionId, long objectLayoutId,
			long objectRelationshipId, Map<Locale, String> nameMap,
			int priority, List<ObjectLayoutBox> objectLayoutBoxes)
		throws PortalException {

		ObjectLayoutTab objectLayoutTab = _objectLayoutTabPersistence.create(
			counterLocalService.increment());

		objectLayoutTab.setCompanyId(user.getCompanyId());
		objectLayoutTab.setUserId(user.getUserId());
		objectLayoutTab.setUserName(user.getFullName());
		objectLayoutTab.setObjectLayoutId(objectLayoutId);
		objectLayoutTab.setObjectRelationshipId(objectRelationshipId);
		objectLayoutTab.setNameMap(nameMap);
		objectLayoutTab.setPriority(priority);

		objectLayoutTab = _objectLayoutTabPersistence.update(objectLayoutTab);

		objectLayoutTab.setObjectLayoutBoxes(
			_addObjectLayoutBoxes(
				user, objectDefinitionId,
				objectLayoutTab.getObjectLayoutTabId(), objectLayoutBoxes));

		return objectLayoutTab;
	}

	private List<ObjectLayoutTab> _addObjectLayoutTabs(
			User user, long objectDefinitionId, long objectLayoutId,
			List<ObjectLayoutTab> objectLayoutTabs)
		throws PortalException {

		List<ObjectLayoutTab> newObjectLayoutTabs = new ArrayList<>();

		for (ObjectLayoutTab objectLayoutTab : objectLayoutTabs) {
			newObjectLayoutTabs.add(
				_addObjectLayoutTab(
					user, objectDefinitionId, objectLayoutId,
					objectLayoutTab.getObjectRelationshipId(),
					objectLayoutTab.getNameMap(), objectLayoutTab.getPriority(),
					objectLayoutTab.getObjectLayoutBoxes()));
		}

		return newObjectLayoutTabs;
	}

	private void _deleteObjectLayoutBoxes(
		List<ObjectLayoutTab> objectLayoutTabs) {

		for (ObjectLayoutTab objectLayoutTab : objectLayoutTabs) {
			_deleteObjectLayoutRows(
				_objectLayoutBoxPersistence.findByObjectLayoutTabId(
					objectLayoutTab.getObjectLayoutTabId()));

			_objectLayoutBoxPersistence.removeByObjectLayoutTabId(
				objectLayoutTab.getObjectLayoutTabId());
		}
	}

	private void _deleteObjectLayoutColumns(
		List<ObjectLayoutRow> objectLayoutRows) {

		for (ObjectLayoutRow objectLayoutRow : objectLayoutRows) {
			_objectLayoutColumnPersistence.removeByObjectLayoutRowId(
				objectLayoutRow.getObjectLayoutRowId());
		}
	}

	private void _deleteObjectLayoutRows(
		List<ObjectLayoutBox> objectLayoutBoxes) {

		for (ObjectLayoutBox objectLayoutBox : objectLayoutBoxes) {
			_deleteObjectLayoutColumns(
				_objectLayoutRowPersistence.findByObjectLayoutBoxId(
					objectLayoutBox.getObjectLayoutBoxId()));

			_objectLayoutRowPersistence.removeByObjectLayoutBoxId(
				objectLayoutBox.getObjectLayoutBoxId());
		}
	}

	private void _deleteObjectLayoutTabs(long objectLayoutId) {
		_deleteObjectLayoutBoxes(
			_objectLayoutTabPersistence.findByObjectLayoutId(objectLayoutId));

		_objectLayoutTabPersistence.removeByObjectLayoutId(objectLayoutId);
	}

	private List<ObjectLayoutBox> _getObjectLayoutBoxes(
		ObjectLayoutTab objectLayoutTab) {

		List<ObjectLayoutBox> objectLayoutBoxes =
			_objectLayoutBoxPersistence.findByObjectLayoutTabId(
				objectLayoutTab.getObjectLayoutTabId());

		for (ObjectLayoutBox objectLayoutBox : objectLayoutBoxes) {
			objectLayoutBox.setObjectLayoutRows(
				_getObjectLayoutRows(objectLayoutBox));
		}

		return objectLayoutBoxes;
	}

	private List<ObjectLayoutRow> _getObjectLayoutRows(
		ObjectLayoutBox objectLayoutBox) {

		List<ObjectLayoutRow> objectLayoutRows =
			_objectLayoutRowPersistence.findByObjectLayoutBoxId(
				objectLayoutBox.getObjectLayoutBoxId());

		for (ObjectLayoutRow objectLayoutRow : objectLayoutRows) {
			objectLayoutRow.setObjectLayoutColumns(
				_objectLayoutColumnPersistence.findByObjectLayoutRowId(
					objectLayoutRow.getObjectLayoutRowId()));
		}

		return objectLayoutRows;
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