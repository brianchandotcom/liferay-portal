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
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Map;

/**
 * @author Gabriel Albuquerque
 */
public class ObjectLayoutUtil {

	public static ObjectLayout toObjectLayout(
		Map<String, Map<String, String>> actions,
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
					ObjectLayoutUtil::toObjectLayoutTab, ObjectLayoutTab.class);
			}
		};

		objectLayout.setActions(actions);

		return objectLayout;
	}

	public static ObjectLayoutTab toObjectLayoutTab(
		com.liferay.object.model.ObjectLayoutTab
			serviceBuilderObjectLayoutTab) {

		if (serviceBuilderObjectLayoutTab == null) {
			return null;
		}

		return new ObjectLayoutTab() {
			{
				id = serviceBuilderObjectLayoutTab.getObjectLayoutTabId();
				name = LocalizedMapUtil.getLanguageIdMap(
					serviceBuilderObjectLayoutTab.getNameMap());
				objectLayoutBoxes = TransformUtil.transformToArray(
					serviceBuilderObjectLayoutTab.getObjectLayoutBoxes(),
					ObjectLayoutUtil::_toObjectLayoutBox,
					ObjectLayoutBox.class);
				objectRelationshipId =
					serviceBuilderObjectLayoutTab.getObjectRelationshipId();
				priority = serviceBuilderObjectLayoutTab.getPriority();
			}
		};
	}

	private static ObjectLayoutBox _toObjectLayoutBox(
		com.liferay.object.model.ObjectLayoutBox
			serviceBuilderObjectLayoutBox) {

		if (serviceBuilderObjectLayoutBox == null) {
			return null;
		}

		return new ObjectLayoutBox() {
			{
				collapsable = serviceBuilderObjectLayoutBox.getCollapsable();
				id = serviceBuilderObjectLayoutBox.getObjectLayoutBoxId();
				name = LocalizedMapUtil.getLanguageIdMap(
					serviceBuilderObjectLayoutBox.getNameMap());
				objectLayoutRows = TransformUtil.transformToArray(
					serviceBuilderObjectLayoutBox.getObjectLayoutRows(),
					ObjectLayoutUtil::_toObjectLayoutRow,
					ObjectLayoutRow.class);
				priority = serviceBuilderObjectLayoutBox.getPriority();
			}
		};
	}

	private static ObjectLayoutColumn _toObjectLayoutColumn(
		com.liferay.object.model.ObjectLayoutColumn
			serviceBuilderObjectLayoutColumn) {

		if (serviceBuilderObjectLayoutColumn == null) {
			return null;
		}

		return new ObjectLayoutColumn() {
			{
				id = serviceBuilderObjectLayoutColumn.getObjectLayoutColumnId();
				objectFieldId =
					serviceBuilderObjectLayoutColumn.getObjectFieldId();
				priority = serviceBuilderObjectLayoutColumn.getPriority();
				size = serviceBuilderObjectLayoutColumn.getSize();
			}
		};
	}

	private static ObjectLayoutRow _toObjectLayoutRow(
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
					ObjectLayoutUtil::_toObjectLayoutColumn,
					ObjectLayoutColumn.class);
				priority = serviceBuilderObjectLayoutRow.getPriority();
			}
		};
	}

}