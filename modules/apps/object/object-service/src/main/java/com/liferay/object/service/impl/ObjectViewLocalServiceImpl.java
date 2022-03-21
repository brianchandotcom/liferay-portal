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

import com.liferay.object.exception.DefaultObjectViewException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectView;
import com.liferay.object.model.ObjectViewColumn;
import com.liferay.object.model.ObjectViewSortColumn;
import com.liferay.object.service.base.ObjectViewLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.object.service.persistence.ObjectViewColumnPersistence;
import com.liferay.object.service.persistence.ObjectViewSortColumnPersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	property = "model.class.name=com.liferay.object.model.ObjectView",
	service = AopService.class
)
public class ObjectViewLocalServiceImpl extends ObjectViewLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectView addObjectView(
			long userId, long objectDefinitionId, boolean defaultObjectView,
			Map<Locale, String> nameMap,
			List<ObjectViewColumn> objectViewColumns,
			List<ObjectViewSortColumn> objectViewSortColumns)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		if (defaultObjectView) {
			_validate(0, objectDefinitionId);
		}

		ObjectView objectView = objectViewPersistence.create(
			counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		objectView.setCompanyId(user.getCompanyId());
		objectView.setUserId(user.getUserId());
		objectView.setUserName(user.getFullName());

		objectView.setObjectDefinitionId(
			objectDefinition.getObjectDefinitionId());
		objectView.setDefaultObjectView(defaultObjectView);
		objectView.setNameMap(nameMap);

		objectView = objectViewPersistence.update(objectView);

		objectView.setObjectViewColumns(
			_addObjectViewColumns(
				user, objectView.getObjectViewId(), objectViewColumns));
		objectView.setObjectViewSortColumns(
			_addObjectViewSortColumns(
				user, objectView.getObjectViewId(), objectViewSortColumns));

		return objectView;
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public ObjectView deleteObjectView(long objectViewId)
		throws PortalException {

		return deleteObjectView(
			objectViewPersistence.findByPrimaryKey(objectViewId));
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public ObjectView deleteObjectView(ObjectView objectView) {
		objectView = objectViewPersistence.remove(objectView);

		_objectViewColumnPersistence.removeByObjectViewId(
			objectView.getObjectViewId());

		_objectViewSortColumnPersistence.removeByObjectViewId(
			objectView.getObjectViewId());

		return objectView;
	}

	@Override
	public ObjectView fetchDefaultObjectView(long objectDefinitionId) {
		ObjectView objectView = objectViewPersistence.fetchByODI_DOV_First(
			objectDefinitionId, true, null);

		if (objectView != null) {
			objectView.setObjectViewColumns(
				_objectViewColumnPersistence.findByObjectViewId(
					objectView.getObjectViewId()));
			objectView.setObjectViewSortColumns(
				_objectViewSortColumnPersistence.findByObjectViewId(
					objectView.getObjectViewId()));
		}

		return objectView;
	}

	@Override
	public ObjectView getObjectView(long objectViewId) throws PortalException {
		ObjectView objectView = objectViewPersistence.findByPrimaryKey(
			objectViewId);

		objectView.setObjectViewColumns(
			_objectViewColumnPersistence.findByObjectViewId(
				objectView.getObjectViewId()));

		objectView.setObjectViewSortColumns(
			_objectViewSortColumnPersistence.findByObjectViewId(
				objectView.getObjectViewId()));

		return objectView;
	}

	@Override
	public List<ObjectView> getObjectViews(long objectDefinitionId) {
		List<ObjectView> objectViews =
			objectViewPersistence.findByObjectDefinitionId(objectDefinitionId);

		for (ObjectView objectView : objectViews) {
			objectView.setObjectViewColumns(
				_objectViewColumnPersistence.findByObjectViewId(
					objectView.getObjectViewId()));
			objectView.setObjectViewSortColumns(
				_objectViewSortColumnPersistence.findByObjectViewId(
					objectView.getObjectViewId()));
		}

		return objectViews;
	}

	@Override
	public void unassociateObjectField(ObjectField objectField) {
		List<ObjectView> objectViews =
			objectViewPersistence.findByObjectDefinitionId(
				objectField.getObjectDefinitionId());

		for (ObjectView objectView : objectViews) {
			_objectViewColumnPersistence.removeByOVI_OFN(
				objectView.getObjectViewId(), objectField.getName());

			_objectViewSortColumnPersistence.removeByOVI_OFN(
				objectView.getObjectViewId(), objectField.getName());
		}
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectView updateObjectView(
			long objectViewId, boolean defaultObjectView,
			Map<Locale, String> nameMap,
			List<ObjectViewColumn> objectViewColumns,
			List<ObjectViewSortColumn> objectViewSortColumns)
		throws PortalException {

		ObjectView objectView = objectViewPersistence.findByPrimaryKey(
			objectViewId);

		if (defaultObjectView) {
			_validate(objectViewId, objectView.getObjectDefinitionId());
		}

		_objectViewColumnPersistence.removeByObjectViewId(
			objectView.getObjectViewId());

		_objectViewSortColumnPersistence.removeByObjectViewId(
			objectView.getObjectViewId());

		objectView.setDefaultObjectView(defaultObjectView);
		objectView.setNameMap(nameMap);

		objectView = objectViewPersistence.update(objectView);

		objectView.setObjectViewColumns(
			_addObjectViewColumns(
				_userLocalService.getUser(objectView.getUserId()),
				objectView.getObjectViewId(), objectViewColumns));
		objectView.setObjectViewSortColumns(
			_addObjectViewSortColumns(
				_userLocalService.getUser(objectView.getUserId()),
				objectView.getObjectViewId(), objectViewSortColumns));

		return objectView;
	}

	private List<ObjectViewColumn> _addObjectViewColumns(
		User user, long objectViewId,
		List<ObjectViewColumn> objectViewColumns) {

		return TransformUtil.transform(
			objectViewColumns,
			objectViewColumn -> {
				ObjectViewColumn newObjectViewColumn =
					_objectViewColumnPersistence.create(
						counterLocalService.increment());

				newObjectViewColumn.setCompanyId(user.getCompanyId());
				newObjectViewColumn.setUserId(user.getUserId());
				newObjectViewColumn.setUserName(user.getFullName());
				newObjectViewColumn.setObjectViewId(objectViewId);
				newObjectViewColumn.setLabelMap(objectViewColumn.getLabelMap());
				newObjectViewColumn.setObjectFieldName(
					objectViewColumn.getObjectFieldName());
				newObjectViewColumn.setPriority(objectViewColumn.getPriority());

				return _objectViewColumnPersistence.update(newObjectViewColumn);
			});
	}

	private List<ObjectViewSortColumn> _addObjectViewSortColumns(
		User user, long objectViewId,
		List<ObjectViewSortColumn> objectViewSortColumns) {

		return TransformUtil.transform(
			objectViewSortColumns,
			objectViewSortColumn -> {
				ObjectViewSortColumn newObjectViewSortColumn =
					_objectViewSortColumnPersistence.create(
						counterLocalService.increment());

				newObjectViewSortColumn.setCompanyId(user.getCompanyId());
				newObjectViewSortColumn.setUserId(user.getUserId());
				newObjectViewSortColumn.setUserName(user.getFullName());
				newObjectViewSortColumn.setObjectViewId(objectViewId);
				newObjectViewSortColumn.setObjectFieldName(
					objectViewSortColumn.getObjectFieldName());
				newObjectViewSortColumn.setPriority(
					objectViewSortColumn.getPriority());
				newObjectViewSortColumn.setSortOrder(
					objectViewSortColumn.getSortOrder());

				return _objectViewSortColumnPersistence.update(
					newObjectViewSortColumn);
			});
	}

	private void _validate(long objectViewId, long objectDefinitionId)
		throws PortalException {

		ObjectView objectView = objectViewPersistence.fetchByODI_DOV_First(
			objectDefinitionId, true, null);

		if ((objectView != null) &&
			(objectView.getObjectViewId() != objectViewId)) {

			throw new DefaultObjectViewException(
				"There can only be one default object view");
		}
	}

	@Reference
	private ObjectDefinitionPersistence _objectDefinitionPersistence;

	@Reference
	private ObjectViewColumnPersistence _objectViewColumnPersistence;

	@Reference
	private ObjectViewSortColumnPersistence _objectViewSortColumnPersistence;

	@Reference
	private UserLocalService _userLocalService;

}