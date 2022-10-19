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

package com.liferay.object.admin.rest.internal.dto.v1_0.converter;

import com.liferay.object.admin.rest.dto.v1_0.ObjectView;
import com.liferay.object.admin.rest.dto.v1_0.ObjectViewColumn;
import com.liferay.object.admin.rest.dto.v1_0.ObjectViewFilterColumn;
import com.liferay.object.admin.rest.dto.v1_0.ObjectViewSortColumn;
import com.liferay.object.field.filter.parser.ObjectFieldFilterContext;
import com.liferay.object.field.filter.parser.ObjectFieldFilterContributor;
import com.liferay.object.field.filter.parser.ObjectFieldFilterContributorTracker;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(
	property = "dto.class.name=com.liferay.object.model.ObjectView",
	service = {DTOConverter.class, ObjectViewDTOConverter.class}
)
public class ObjectViewDTOConverter
	implements DTOConverter<com.liferay.object.model.ObjectView, ObjectView> {

	@Override
	public String getContentType() {
		return ObjectView.class.getSimpleName();
	}

	@Override
	public ObjectView toDTO(
			DTOConverterContext dtoConverterContext,
			com.liferay.object.model.ObjectView objectView)
		throws Exception {

		if (objectView == null) {
			return null;
		}

		return new ObjectView() {
			{
				actions = dtoConverterContext.getActions();
				dateCreated = objectView.getCreateDate();
				dateModified = objectView.getModifiedDate();
				defaultObjectView = objectView.getDefaultObjectView();
				id = objectView.getObjectViewId();
				name = LocalizedMapUtil.getLanguageIdMap(
					objectView.getNameMap());
				objectDefinitionId = objectView.getObjectDefinitionId();
				objectViewColumns = TransformUtil.transformToArray(
					objectView.getObjectViewColumns(),
					objectViewColumn -> _toObjectViewColumn(objectViewColumn),
					ObjectViewColumn.class);
				objectViewFilterColumns = TransformUtil.transformToArray(
					objectView.getObjectViewFilterColumns(),
					objectViewFilterColumn -> _toObjectViewFilterColumn(
						dtoConverterContext.getLocale(),
						objectView.getObjectDefinitionId(),
						objectViewFilterColumn),
					ObjectViewFilterColumn.class);
				objectViewSortColumns = TransformUtil.transformToArray(
					objectView.getObjectViewSortColumns(),
					objectViewSortColumn -> _toObjectViewSortColumn(
						objectViewSortColumn),
					ObjectViewSortColumn.class);
			}
		};
	}

	private ObjectViewColumn _toObjectViewColumn(
		com.liferay.object.model.ObjectViewColumn objectViewColumn) {

		if (objectViewColumn == null) {
			return null;
		}

		return new ObjectViewColumn() {
			{
				id = objectViewColumn.getObjectViewColumnId();
				label = LocalizedMapUtil.getLanguageIdMap(
					objectViewColumn.getLabelMap());
				objectFieldName = objectViewColumn.getObjectFieldName();
				priority = objectViewColumn.getPriority();
			}
		};
	}

	private ObjectViewFilterColumn _toObjectViewFilterColumn(
		Locale locale, long objectDefinitionId,
		com.liferay.object.model.ObjectViewFilterColumn
			serviceBuilderObjectViewFilterColumn) {

		if (serviceBuilderObjectViewFilterColumn == null) {
			return null;
		}

		ObjectViewFilterColumn objectViewFilterColumn =
			new ObjectViewFilterColumn() {
				{
					id =
						serviceBuilderObjectViewFilterColumn.
							getObjectViewFilterColumnId();
					objectFieldName =
						serviceBuilderObjectViewFilterColumn.
							getObjectFieldName();
				}
			};

		if (Validator.isNull(
				serviceBuilderObjectViewFilterColumn.getFilterType())) {

			return objectViewFilterColumn;
		}

		objectViewFilterColumn.setFilterType(
			ObjectViewFilterColumn.FilterType.create(
				serviceBuilderObjectViewFilterColumn.getFilterType()));
		objectViewFilterColumn.setJson(
			serviceBuilderObjectViewFilterColumn.getJSON());
		objectViewFilterColumn.setValueSummary(
			() -> {
				ObjectFieldFilterContributor objectFieldFilterContributor =
					_objectFieldFilterContributorTracker.
						getObjectFieldFilterContributor(
							new ObjectFieldFilterContext(
								locale, objectDefinitionId,
								serviceBuilderObjectViewFilterColumn));

				return objectFieldFilterContributor.toValueSummary();
			});

		return objectViewFilterColumn;
	}

	private ObjectViewSortColumn _toObjectViewSortColumn(
		com.liferay.object.model.ObjectViewSortColumn objectViewSortColumn) {

		if (objectViewSortColumn == null) {
			return null;
		}

		return new ObjectViewSortColumn() {
			{
				id = objectViewSortColumn.getObjectViewSortColumnId();
				objectFieldName = objectViewSortColumn.getObjectFieldName();
				priority = objectViewSortColumn.getPriority();
				sortOrder = ObjectViewSortColumn.SortOrder.create(
					objectViewSortColumn.getSortOrder());
			}
		};
	}

	@Reference
	private ObjectFieldFilterContributorTracker
		_objectFieldFilterContributorTracker;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}