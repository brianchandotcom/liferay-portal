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
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.filter.parser.ObjectFieldFilterParser;
import com.liferay.object.field.filter.parser.ObjectFieldFilterParserTracker;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

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
				ObjectFieldFilterParser objectFieldFilterParser =
					_objectFieldFilterParserTracker.getObjectFieldFilterParser(
						serviceBuilderObjectViewFilterColumn.getFilterType());

				ObjectField objectField =
					_objectFieldLocalService.fetchObjectField(
						objectDefinitionId,
						objectViewFilterColumn.getObjectFieldName());

				if (!Objects.equals(
						objectField.getBusinessType(),
						ObjectFieldConstants.BUSINESS_TYPE_PICKLIST)) {

					Map<String, Object> preloadedData =
						objectFieldFilterParser.parse(
							0L, objectDefinitionId, locale,
							serviceBuilderObjectViewFilterColumn);

					if (Objects.equals(
							serviceBuilderObjectViewFilterColumn.
								getObjectFieldName(),
							"status")) {

						return StringUtil.merge(
							ListUtil.toList(
								(List<Integer>)preloadedData.get("itemsValues"),
								itemValue -> _language.get(
									locale,
									WorkflowConstants.getStatusLabel(
										itemValue))),
							StringPool.COMMA_AND_SPACE);
					}

					ObjectRelationship objectRelationship =
						_objectRelationshipLocalService.
							fetchObjectRelationshipByObjectFieldId2(
								objectField.getObjectFieldId());

					return StringUtil.merge(
						ListUtil.toList(
							(List<Map<String, Object>>)preloadedData.get(
								"itemsValues"),
							itemValue -> {
								try {
									return _objectEntryLocalServiceImpl.
										getTitleValue(
											GetterUtil.getLong(
												itemValue.get("value")),
											objectRelationship.
												getObjectDefinitionId1());
								}
								catch (PortalException portalException) {
									throw new RuntimeException(portalException);
								}
							}),
						StringPool.COMMA_AND_SPACE);
				}

				Map<String, Object> preloadedData =
					objectFieldFilterParser.parse(
						objectField.getListTypeDefinitionId(),
						objectDefinitionId, locale,
						serviceBuilderObjectViewFilterColumn);

				return StringUtil.merge(
					ListUtil.toList(
						(List<Map<String, String>>)preloadedData.get(
							"itemsValues"),
						itemValue -> itemValue.get("label")),
					StringPool.COMMA_AND_SPACE);
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
	private Language _language;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalServiceImpl;

	@Reference
	private ObjectFieldFilterParserTracker _objectFieldFilterParserTracker;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}