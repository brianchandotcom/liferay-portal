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

import com.liferay.object.admin.rest.dto.v1_0.ObjectView;
import com.liferay.object.admin.rest.dto.v1_0.ObjectViewColumn;
import com.liferay.object.admin.rest.dto.v1_0.ObjectViewSortColumn;
import com.liferay.object.admin.rest.internal.configuration.activator.FFObjectViewSortColumnConfigurationUtil;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Map;

/**
 * @author Gabriel Albuquerque
 */
public class ObjectViewUtil {

	public static ObjectView toObjectView(
		Map<String, Map<String, String>> actions,
		com.liferay.object.model.ObjectView serviceBuilderObjectView) {

		if (serviceBuilderObjectView == null) {
			return null;
		}

		ObjectView objectView = new ObjectView() {
			{
				dateCreated = serviceBuilderObjectView.getCreateDate();
				dateModified = serviceBuilderObjectView.getModifiedDate();
				defaultObjectView =
					serviceBuilderObjectView.getDefaultObjectView();
				id = serviceBuilderObjectView.getObjectViewId();
				name = LocalizedMapUtil.getLanguageIdMap(
					serviceBuilderObjectView.getNameMap());
				objectDefinitionId =
					serviceBuilderObjectView.getObjectDefinitionId();
				objectViewColumns = TransformUtil.transformToArray(
					serviceBuilderObjectView.getObjectViewColumns(),
					ObjectViewUtil::_toObjectViewColumn,
					ObjectViewColumn.class);

				if (FFObjectViewSortColumnConfigurationUtil.enabled()) {
					objectViewSortColumns = TransformUtil.transformToArray(
						serviceBuilderObjectView.getObjectViewSortColumns(),
						ObjectViewUtil::_toObjectViewSortColumn,
						ObjectViewSortColumn.class);
				}
			}
		};

		objectView.setActions(actions);

		return objectView;
	}

	private static ObjectViewColumn _toObjectViewColumn(
		com.liferay.object.model.ObjectViewColumn
			serviceBuilderObjectViewColumn) {

		if (serviceBuilderObjectViewColumn == null) {
			return null;
		}

		return new ObjectViewColumn() {
			{
				id = serviceBuilderObjectViewColumn.getObjectViewColumnId();
				objectFieldName =
					serviceBuilderObjectViewColumn.getObjectFieldName();
				priority = serviceBuilderObjectViewColumn.getPriority();
			}
		};
	}

	private static ObjectViewSortColumn _toObjectViewSortColumn(
		com.liferay.object.model.ObjectViewSortColumn
			serviceBuilderObjectViewSortColumn) {

		if (serviceBuilderObjectViewSortColumn == null) {
			return null;
		}

		return new ObjectViewSortColumn() {
			{
				id =
					serviceBuilderObjectViewSortColumn.
						getObjectViewSortColumnId();
				objectFieldName =
					serviceBuilderObjectViewSortColumn.getObjectFieldName();
				priority = serviceBuilderObjectViewSortColumn.getPriority();
				sortOrder = ObjectViewSortColumn.SortOrder.create(
					serviceBuilderObjectViewSortColumn.getSortOrder());
			}
		};
	}

}