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

import com.liferay.object.exception.DefaultObjectLayoutException;
import com.liferay.object.exception.NoSuchObjectDefinitionException;
import com.liferay.object.exception.ObjectLayoutColumnSizeException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectLayout;
import com.liferay.object.model.ObjectLayoutBox;
import com.liferay.object.model.ObjectLayoutColumn;
import com.liferay.object.model.ObjectLayoutRow;
import com.liferay.object.model.ObjectLayoutTab;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.base.ObjectLayoutLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.object.service.persistence.ObjectFieldPersistence;
import com.liferay.object.service.persistence.ObjectLayoutBoxPersistence;
import com.liferay.object.service.persistence.ObjectLayoutColumnPersistence;
import com.liferay.object.service.persistence.ObjectLayoutRowPersistence;
import com.liferay.object.service.persistence.ObjectLayoutTabPersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectLayout addObjectLayout(
			long userId, long objectDefinitionId, boolean defaultObjectLayout,
			Map<Locale, String> nameMap, List<ObjectLayoutTab> objectLayoutTabs)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		if (objectDefinition.isSystem()) {

			// TODO Add test

			throw new NoSuchObjectDefinitionException(
				"Object layouts require a custom object definition");
		}

		if (defaultObjectLayout) {
			_validate(
				0, objectDefinitionId, defaultObjectLayout, objectLayoutTabs);
		}

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

	@Indexable(type = IndexableType.DELETE)
	@Override
	public ObjectLayout deleteObjectLayout(long objectLayoutId)
		throws PortalException {

		ObjectLayout objectLayout = objectLayoutPersistence.findByPrimaryKey(
			objectLayoutId);

		return deleteObjectLayout(objectLayout);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public ObjectLayout deleteObjectLayout(ObjectLayout objectLayout)
		throws PortalException {

		objectLayout = objectLayoutPersistence.remove(objectLayout);

		_deleteObjectLayoutTabs(objectLayout.getObjectLayoutId());

		return objectLayout;
	}

	@Override
	public ObjectLayout getDefaultObjectLayout(long objectDefinitionId)
		throws PortalException {

		ObjectLayout objectLayout = objectLayoutPersistence.findByODI_DOL_First(
			objectDefinitionId, true, null);

		objectLayout.setObjectLayoutTabs(_getObjectLayoutTabs(objectLayout));

		return objectLayout;
	}

	@Override
	public ObjectLayout getObjectLayout(long objectLayoutId)
		throws PortalException {

		ObjectLayout objectLayout = objectLayoutPersistence.findByPrimaryKey(
			objectLayoutId);

		objectLayout.setObjectLayoutTabs(_getObjectLayoutTabs(objectLayout));

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

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectLayout updateObjectLayout(
			long objectLayoutId, boolean defaultObjectLayout,
			Map<Locale, String> nameMap, List<ObjectLayoutTab> objectLayoutTabs)
		throws PortalException {

		ObjectLayout objectLayout = objectLayoutPersistence.findByPrimaryKey(
			objectLayoutId);

		if (defaultObjectLayout) {
			_validate(
				objectLayoutId, objectLayout.getObjectDefinitionId(),
				defaultObjectLayout, objectLayoutTabs);
		}

		_deleteObjectLayoutTabs(objectLayoutId);

		objectLayout.setDefaultObjectLayout(defaultObjectLayout);
		objectLayout.setNameMap(nameMap);

		objectLayout = objectLayoutPersistence.update(objectLayout);

		User user = _userLocalService.getUser(objectLayout.getUserId());

		objectLayout.setObjectLayoutTabs(
			_addObjectLayoutTabs(
				user, objectLayout.getObjectDefinitionId(),
				objectLayout.getObjectLayoutId(), objectLayoutTabs));

		return objectLayout;
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

		return TransformUtil.transform(
			objectLayoutBoxes,
			objectLayoutBox -> _addObjectLayoutBox(
				user, objectDefinitionId, objectLayoutTabId,
				objectLayoutBox.isCollapsable(), objectLayoutBox.getNameMap(),
				objectLayoutBox.getPriority(),
				objectLayoutBox.getObjectLayoutRows()));
	}

	private ObjectLayoutColumn _addObjectLayoutColumn(
			User user, long objectDefinitionId, long objectFieldId,
			long objectLayoutRowId, int priority, int size)
		throws PortalException {

		ObjectField objectField = _objectFieldPersistence.findByPrimaryKey(
			objectFieldId);

		if (objectField.getObjectDefinitionId() != objectDefinitionId) {

			// TODO

			throw new PortalException();
		}

		if ((size < 0) || (size > 12)) {
			throw new ObjectLayoutColumnSizeException(
				"Object layout column size must be more than 0 and less than " +
					"12");
		}

		ObjectLayoutColumn objectLayoutColumn =
			_objectLayoutColumnPersistence.create(
				counterLocalService.increment());

		objectLayoutColumn.setCompanyId(user.getCompanyId());
		objectLayoutColumn.setUserId(user.getUserId());
		objectLayoutColumn.setUserName(user.getFullName());
		objectLayoutColumn.setObjectFieldId(objectField.getObjectFieldId());
		objectLayoutColumn.setObjectLayoutRowId(objectLayoutRowId);
		objectLayoutColumn.setPriority(priority);
		objectLayoutColumn.setSize(size);

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
					user, objectDefinitionId,
					objectLayoutColumn.getObjectFieldId(), objectLayoutRowId,
					objectLayoutColumn.getPriority(),
					objectLayoutColumn.getSize()));
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

		return TransformUtil.transform(
			objectLayoutRows,
			objectLayoutRow -> _addObjectLayoutRow(
				user, objectDefinitionId, objectLayoutBoxId,
				objectLayoutRow.getPriority(),
				objectLayoutRow.getObjectLayoutColumns()));
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

		return TransformUtil.transform(
			objectLayoutTabs,
			objectLayoutTab -> _addObjectLayoutTab(
				user, objectDefinitionId, objectLayoutId,
				objectLayoutTab.getObjectRelationshipId(),
				objectLayoutTab.getNameMap(), objectLayoutTab.getPriority(),
				objectLayoutTab.getObjectLayoutBoxes()));
	}

	private void _deleteObjectLayoutBoxes(
		List<ObjectLayoutTab> objectLayoutTabs) {

		for (ObjectLayoutTab objectLayoutTab : objectLayoutTabs) {
			_objectLayoutBoxPersistence.removeByObjectLayoutTabId(
				objectLayoutTab.getObjectLayoutTabId());

			_deleteObjectLayoutRows(
				_objectLayoutBoxPersistence.findByObjectLayoutTabId(
					objectLayoutTab.getObjectLayoutTabId()));
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
			_objectLayoutRowPersistence.removeByObjectLayoutBoxId(
				objectLayoutBox.getObjectLayoutBoxId());

			_deleteObjectLayoutColumns(
				_objectLayoutRowPersistence.findByObjectLayoutBoxId(
					objectLayoutBox.getObjectLayoutBoxId()));
		}
	}

	private void _deleteObjectLayoutTabs(long objectLayoutId) {
		_objectLayoutTabPersistence.removeByObjectLayoutId(objectLayoutId);

		_deleteObjectLayoutBoxes(
			_objectLayoutTabPersistence.findByObjectLayoutId(objectLayoutId));
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

	private List<ObjectLayoutTab> _getObjectLayoutTabs(
		ObjectLayout objectLayout) {

		List<ObjectLayoutTab> objectLayoutTabs =
			_objectLayoutTabPersistence.findByObjectLayoutId(
				objectLayout.getObjectLayoutId());

		for (ObjectLayoutTab objectLayoutTab : objectLayoutTabs) {
			objectLayoutTab.setObjectLayoutBoxes(
				_getObjectLayoutBoxes(objectLayoutTab));
		}

		return objectLayoutTabs;
	}

	private void _validate(
			long objectLayoutId, long objectDefinitionId,
			boolean defaultObjectLayout, List<ObjectLayoutTab> objectLayoutTabs)
		throws PortalException {

		Set<Long> objectFieldIds = new HashSet<>();

		ObjectLayoutTab objectLayoutTab = objectLayoutTabs.get(0);

		List<ObjectLayoutBox> objectLayoutBoxes =
			objectLayoutTab.getObjectLayoutBoxes();

		if (objectLayoutBoxes == null) {
			objectLayoutBoxes = Collections.<ObjectLayoutBox>emptyList();
		}

		for (ObjectLayoutBox objectLayoutBox : objectLayoutBoxes) {
			List<ObjectLayoutRow> objectLayoutRows =
				objectLayoutBox.getObjectLayoutRows();

			if (objectLayoutRows == null) {
				continue;
			}

			for (ObjectLayoutRow objectLayoutRow : objectLayoutRows) {
				List<ObjectLayoutColumn> objectLayoutColumns =
					objectLayoutRow.getObjectLayoutColumns();

				if (objectLayoutColumns == null) {
					continue;
				}

				for (ObjectLayoutColumn objectLayoutColumn :
						objectLayoutColumns) {

					objectFieldIds.add(objectLayoutColumn.getObjectFieldId());
				}
			}
		}

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(objectDefinitionId);

		for (ObjectField objectField : objectFields) {
			if (!objectField.isRequired()) {
				continue;
			}

			if (!objectFieldIds.contains(objectField.getObjectFieldId())) {
				throw new DefaultObjectLayoutException(
					"All required object fields must be associated to the " +
						"first tab of a default object layout");
			}
		}

		ObjectLayout objectLayout =
			objectLayoutPersistence.fetchByODI_DOL_First(
				objectDefinitionId, defaultObjectLayout, null);

		if ((objectLayout != null) &&
			(objectLayout.getObjectLayoutId() != objectLayoutId)) {

			throw new DefaultObjectLayoutException(
				"There can only be one default object layout");
		}
	}

	@Reference
	private ObjectDefinitionPersistence _objectDefinitionPersistence;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

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