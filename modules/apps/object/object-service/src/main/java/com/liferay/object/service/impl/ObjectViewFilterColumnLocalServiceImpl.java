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

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.exception.ObjectViewFilterColumnException;
import com.liferay.object.field.filter.parser.ObjectFieldFilterContext;
import com.liferay.object.field.filter.parser.ObjectFieldFilterContributor;
import com.liferay.object.field.filter.parser.ObjectFieldFilterContributorTracker;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectView;
import com.liferay.object.model.ObjectViewFilterColumn;
import com.liferay.object.service.base.ObjectViewFilterColumnLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectFieldPersistence;
import com.liferay.object.service.persistence.ObjectViewPersistence;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "model.class.name=com.liferay.object.model.ObjectViewFilterColumn",
	service = AopService.class
)
public class ObjectViewFilterColumnLocalServiceImpl
	extends ObjectViewFilterColumnLocalServiceBaseImpl {

	@Override
	public List<ObjectViewFilterColumn> addObjectViewFilterColumns(
			User user, ObjectView objectView,
			List<ObjectViewFilterColumn> objectViewFilterColumns)
		throws PortalException {

		_validate(objectView.getObjectDefinitionId(), objectViewFilterColumns);

		return TransformUtil.transform(
			objectViewFilterColumns,
			objectViewFilterColumn -> {
				ObjectViewFilterColumn newObjectViewFilterColumn =
					objectViewFilterColumnPersistence.create(
						counterLocalService.increment());

				newObjectViewFilterColumn.setCompanyId(user.getCompanyId());
				newObjectViewFilterColumn.setUserId(user.getUserId());
				newObjectViewFilterColumn.setUserName(user.getFullName());
				newObjectViewFilterColumn.setObjectViewId(
					objectView.getObjectViewId());
				newObjectViewFilterColumn.setFilterType(
					objectViewFilterColumn.getFilterType());
				newObjectViewFilterColumn.setJSON(
					objectViewFilterColumn.getJSON());
				newObjectViewFilterColumn.setObjectFieldName(
					objectViewFilterColumn.getObjectFieldName());

				return objectViewFilterColumnPersistence.update(
					newObjectViewFilterColumn);
			});
	}

	@Override
	public ObjectViewFilterColumn updateObjectViewFilterColumn(
			long objectViewFilterColumnId, long objectViewId, String filterType,
			String json, String objectFieldName)
		throws PortalException {

		ObjectView objectView = _objectViewPersistence.findByPrimaryKey(
			objectViewId);

		ObjectViewFilterColumn objectViewFilterColumn =
			objectViewFilterColumnPersistence.findByPrimaryKey(
				objectViewFilterColumnId);

		objectViewFilterColumn.setFilterType(filterType);
		objectViewFilterColumn.setJSON(json);
		objectViewFilterColumn.setObjectFieldName(objectFieldName);

		_validate(
			objectView.getObjectDefinitionId(),
			Collections.singletonList(objectViewFilterColumn));

		return objectViewFilterColumnPersistence.update(objectViewFilterColumn);
	}

	private void _validate(
			long objectDefinitionId,
			List<ObjectViewFilterColumn> objectViewFilterColumns)
		throws PortalException {

		for (ObjectViewFilterColumn objectViewFilterColumn :
				objectViewFilterColumns) {

			if (Validator.isNull(objectViewFilterColumn.getObjectFieldName())) {
				throw new ObjectViewFilterColumnException(
					"Object field name is null");
			}

			ObjectField objectField = _objectFieldPersistence.findByODI_N(
				objectDefinitionId,
				objectViewFilterColumn.getObjectFieldName());

			if (!(_filterableObjectFieldBusinessTypes.contains(
					objectField.getBusinessType()) ||
				  _filterableObjectFieldNames.contains(
					  objectField.getName()))) {

				throw new ObjectViewFilterColumnException(
					StringBundler.concat(
						"Object field name \"",
						objectViewFilterColumn.getObjectFieldName(),
						"\" is not filterable"));
			}

			if (!GetterUtil.getBoolean(
					PropsUtil.get("feature.flag.LPS-152650")) &&
				StringUtil.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_RELATIONSHIP)) {

				throw new ObjectViewFilterColumnException(
					StringBundler.concat(
						"Object field name \"",
						objectViewFilterColumn.getObjectFieldName(),
						"\" is not filterable"));
			}

			if (Validator.isNull(objectViewFilterColumn.getFilterType()) &&
				Validator.isNull(objectViewFilterColumn.getJSON())) {

				continue;
			}

			if ((Validator.isNull(objectViewFilterColumn.getFilterType()) &&
				 Validator.isNotNull(objectViewFilterColumn.getJSON())) ||
				(Validator.isNotNull(objectViewFilterColumn.getFilterType()) &&
				 Validator.isNull(objectViewFilterColumn.getJSON()))) {

				throw new ObjectViewFilterColumnException(
					StringBundler.concat(
						"Object field name \"",
						objectViewFilterColumn.getObjectFieldName(),
						"\" needs to have the filter type and JSON specified"));
			}

			ObjectFieldFilterContributor objectFieldFilterContributor =
				_objectFieldFilterContributorTracker.
					getObjectFieldFilterContributor(
						new ObjectFieldFilterContext(
							null, objectField.getObjectDefinitionId(),
							objectViewFilterColumn));

			objectFieldFilterContributor.validate();
		}
	}

	private static final Set<String> _filterableObjectFieldBusinessTypes =
		Collections.unmodifiableSet(
			SetUtil.fromArray(
				ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST,
				ObjectFieldConstants.BUSINESS_TYPE_PICKLIST,
				ObjectFieldConstants.BUSINESS_TYPE_RELATIONSHIP));
	private static final Set<String> _filterableObjectFieldNames =
		Collections.unmodifiableSet(
			SetUtil.fromArray("status", "createDate", "modifiedDate"));

	@Reference
	private ObjectFieldFilterContributorTracker
		_objectFieldFilterContributorTracker;

	@Reference
	private ObjectFieldPersistence _objectFieldPersistence;

	@Reference
	private ObjectViewPersistence _objectViewPersistence;

}