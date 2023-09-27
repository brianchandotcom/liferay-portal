/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.admin.rest.internal.dto.v1_0.util;

import com.liferay.object.admin.rest.dto.v1_0.ObjectLayout;
import com.liferay.object.admin.rest.dto.v1_0.ObjectLayoutBox;
import com.liferay.object.admin.rest.dto.v1_0.ObjectLayoutColumn;
import com.liferay.object.admin.rest.dto.v1_0.ObjectLayoutRow;
import com.liferay.object.admin.rest.dto.v1_0.ObjectLayoutTab;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Map;

/**
 * @author Gabriel Albuquerque
 */
public class ObjectLayoutUtil {

	public static ObjectLayout toObjectLayout(
			Map<String, Map<String, String>> actions,
			ObjectDefinitionLocalService objectDefinitionLocalService,
			ObjectFieldLocalService objectFieldLocalService,
			com.liferay.object.model.ObjectLayout serviceBuilderObjectLayout)
		throws PortalException {

		if (serviceBuilderObjectLayout == null) {
			return null;
		}

		ObjectDefinition objectDefinition =
			objectDefinitionLocalService.getObjectDefinition(
				serviceBuilderObjectLayout.getObjectDefinitionId());

		ObjectLayout objectLayout = new ObjectLayout() {
			{
				dateCreated = serviceBuilderObjectLayout.getCreateDate();
				dateModified = serviceBuilderObjectLayout.getModifiedDate();
				defaultObjectLayout =
					serviceBuilderObjectLayout.getDefaultObjectLayout();
				id = serviceBuilderObjectLayout.getObjectLayoutId();
				name = LocalizedMapUtil.getLanguageIdMap(
					serviceBuilderObjectLayout.getNameMap());
				objectDefinitionExternalReferenceCode =
					objectDefinition.getExternalReferenceCode();
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