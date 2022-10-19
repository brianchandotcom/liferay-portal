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

package com.liferay.object.admin.rest.internal.dto.v1_0.util;

import com.liferay.object.admin.rest.dto.v1_0.ObjectLayout;
import com.liferay.object.admin.rest.dto.v1_0.ObjectLayoutBox;
import com.liferay.object.admin.rest.dto.v1_0.ObjectLayoutColumn;
import com.liferay.object.admin.rest.dto.v1_0.ObjectLayoutRow;
import com.liferay.object.admin.rest.dto.v1_0.ObjectLayoutTab;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.petra.function.transform.TransformUtil;

import java.util.Map;

/**
 * @author Gabriel Albuquerque
 */
public class ObjectLayoutUtil {

	public static ObjectLayout toObjectLayout(
		Map<String, Map<String, String>> actions,
		ObjectFieldLocalService objectFieldLocalService,
		com.liferay.object.model.ObjectLayout serviceBuilderObjectLayout) {

		if (serviceBuilderObjectLayout == null) {
			return null;
		}

		ObjectLayout objectLayout = new ObjectLayout() {
			{
				dateCreated = serviceBuilderObjectLayout.getCreateDate();
				dateModified = serviceBuilderObjectLayout.getModifiedDate();
				defaultObjectLayout =
					serviceBuilderObjectLayout.getDefaultObjectLayout();
				id = serviceBuilderObjectLayout.getObjectLayoutId();
				name = LocalizedMapUtil.getLanguageIdMap(
					serviceBuilderObjectLayout.getNameMap());
				objectDefinitionId =
					serviceBuilderObjectLayout.getObjectDefinitionId();
				objectLayoutTabs = TransformUtil.transformToArray(
					serviceBuilderObjectLayout.getObjectLayoutTabs(),
					objectLayoutTab -> toObjectLayoutTab(
						objectFieldLocalService, objectLayoutTab),
					ObjectLayoutTab.class);
			}
		};

		objectLayout.setActions(actions);

		return objectLayout;
	}

	public static ObjectLayoutTab toObjectLayoutTab(
		ObjectFieldLocalService objectFieldLocalService,
		com.liferay.object.model.ObjectLayoutTab objectLayoutTab) {

		if (objectLayoutTab == null) {
			return null;
		}

		return new ObjectLayoutTab() {
			{
				id = objectLayoutTab.getObjectLayoutTabId();
				name = LocalizedMapUtil.getLanguageIdMap(
					objectLayoutTab.getNameMap());
				objectLayoutBoxes = TransformUtil.transformToArray(
					objectLayoutTab.getObjectLayoutBoxes(),
					objectLayoutBox -> _toObjectLayoutBox(
						objectFieldLocalService, objectLayoutBox),
					ObjectLayoutBox.class);
				objectRelationshipId =
					objectLayoutTab.getObjectRelationshipId();
				priority = objectLayoutTab.getPriority();
			}
		};
	}

	private static ObjectLayoutBox _toObjectLayoutBox(
		ObjectFieldLocalService objectFieldLocalService,
		com.liferay.object.model.ObjectLayoutBox objectLayoutBox) {

		if (objectLayoutBox == null) {
			return null;
		}

		return new ObjectLayoutBox() {
			{
				collapsable = objectLayoutBox.getCollapsable();
				id = objectLayoutBox.getObjectLayoutBoxId();
				name = LocalizedMapUtil.getLanguageIdMap(
					objectLayoutBox.getNameMap());
				objectLayoutRows = TransformUtil.transformToArray(
					objectLayoutBox.getObjectLayoutRows(),
					objectLayoutRow -> _toObjectLayoutRow(
						objectFieldLocalService, objectLayoutRow),
					ObjectLayoutRow.class);
				priority = objectLayoutBox.getPriority();
				type = ObjectLayoutBox.Type.create(objectLayoutBox.getType());
			}
		};
	}

	private static ObjectLayoutColumn _toObjectLayoutColumn(
		ObjectFieldLocalService objectFieldLocalService,
		com.liferay.object.model.ObjectLayoutColumn
			serviceBuilderObjectLayoutColumn) {

		if (serviceBuilderObjectLayoutColumn == null) {
			return null;
		}

		ObjectField objectField = objectFieldLocalService.fetchObjectField(
			serviceBuilderObjectLayoutColumn.getObjectFieldId());

		return new ObjectLayoutColumn() {
			{
				id = serviceBuilderObjectLayoutColumn.getObjectLayoutColumnId();
				objectFieldName = objectField.getName();
				priority = serviceBuilderObjectLayoutColumn.getPriority();
				size = serviceBuilderObjectLayoutColumn.getSize();
			}
		};
	}

	private static ObjectLayoutRow _toObjectLayoutRow(
		ObjectFieldLocalService objectFieldLocalService,
		com.liferay.object.model.ObjectLayoutRow
			serviceBuilderObjectLayoutRow) {

		if (serviceBuilderObjectLayoutRow == null) {
			return null;
		}

		return new ObjectLayoutRow() {
			{
				id = serviceBuilderObjectLayoutRow.getObjectLayoutRowId();
				objectLayoutColumns = TransformUtil.transformToArray(
					serviceBuilderObjectLayoutRow.getObjectLayoutColumns(),
					objectLayoutColumn -> _toObjectLayoutColumn(
						objectFieldLocalService, objectLayoutColumn),
					ObjectLayoutColumn.class);
				priority = serviceBuilderObjectLayoutRow.getPriority();
			}
		};
	}

}