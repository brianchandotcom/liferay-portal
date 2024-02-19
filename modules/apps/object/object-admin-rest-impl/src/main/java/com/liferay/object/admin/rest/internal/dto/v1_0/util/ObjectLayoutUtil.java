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
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
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
			ObjectRelationshipLocalService objectRelationshipLocalService,
			com.liferay.object.model.ObjectLayout serviceBuilderObjectLayout)
		throws PortalException {

		if (serviceBuilderObjectLayout == null) {
			return null;
		}

		ObjectLayout objectLayout = new ObjectLayout() {
			{
				setDateCreated(serviceBuilderObjectLayout::getCreateDate);
				setDateModified(serviceBuilderObjectLayout::getModifiedDate);
				setDefaultObjectLayout(
					serviceBuilderObjectLayout::isDefaultObjectLayout);
				setId(serviceBuilderObjectLayout::getObjectLayoutId);
				setName(
					() -> LocalizedMapUtil.getLanguageIdMap(
						serviceBuilderObjectLayout.getNameMap()));
				setObjectDefinitionExternalReferenceCode(
					() -> {
						ObjectDefinition objectDefinition =
							objectDefinitionLocalService.getObjectDefinition(
								serviceBuilderObjectLayout.
									getObjectDefinitionId());

						return objectDefinition.getExternalReferenceCode();
					});
				setObjectDefinitionId(
					serviceBuilderObjectLayout::getObjectDefinitionId);
				setObjectLayoutTabs(
					() -> TransformUtil.transformToArray(
						serviceBuilderObjectLayout.getObjectLayoutTabs(),
						objectLayoutTab -> toObjectLayoutTab(
							objectFieldLocalService, objectLayoutTab,
							objectRelationshipLocalService),
						ObjectLayoutTab.class));
			}
		};

		objectLayout.setActions(() -> actions);

		return objectLayout;
	}

	public static ObjectLayoutTab toObjectLayoutTab(
		ObjectFieldLocalService objectFieldLocalService,
		com.liferay.object.model.ObjectLayoutTab objectLayoutTab,
		ObjectRelationshipLocalService objectRelationshipLocalService) {

		if (objectLayoutTab == null) {
			return null;
		}

		return new ObjectLayoutTab() {
			{
				setId(objectLayoutTab::getObjectLayoutTabId);
				setName(
					() -> LocalizedMapUtil.getLanguageIdMap(
						objectLayoutTab.getNameMap()));
				setObjectLayoutBoxes(
					() -> TransformUtil.transformToArray(
						objectLayoutTab.getObjectLayoutBoxes(),
						objectLayoutBox -> _toObjectLayoutBox(
							objectFieldLocalService, objectLayoutBox),
						ObjectLayoutBox.class));
				setObjectRelationshipExternalReferenceCode(
					() -> {
						ObjectRelationship objectRelationship =
							objectRelationshipLocalService.
								fetchObjectRelationship(
									objectLayoutTab.getObjectRelationshipId());

						if (objectRelationship == null) {
							return null;
						}

						return objectRelationship.getExternalReferenceCode();
					});
				setObjectRelationshipId(
					objectLayoutTab::getObjectRelationshipId);
				setPriority(objectLayoutTab::getPriority);
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
				setCollapsable(objectLayoutBox::isCollapsable);
				setId(objectLayoutBox::getObjectLayoutBoxId);
				setName(
					() -> LocalizedMapUtil.getLanguageIdMap(
						objectLayoutBox.getNameMap()));
				setObjectLayoutRows(
					() -> TransformUtil.transformToArray(
						objectLayoutBox.getObjectLayoutRows(),
						objectLayoutRow -> _toObjectLayoutRow(
							objectFieldLocalService, objectLayoutRow),
						ObjectLayoutRow.class));
				setPriority(objectLayoutBox::getPriority);
				setType(
					() -> ObjectLayoutBox.Type.create(
						objectLayoutBox.getType()));
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

		return new ObjectLayoutColumn() {
			{
				setId(
					() ->
						serviceBuilderObjectLayoutColumn.
							getObjectLayoutColumnId());
				setObjectFieldName(
					() -> {
						ObjectField objectField =
							objectFieldLocalService.fetchObjectField(
								serviceBuilderObjectLayoutColumn.
									getObjectFieldId());

						return objectField.getName();
					});
				setPriority(serviceBuilderObjectLayoutColumn::getPriority);
				setSize(serviceBuilderObjectLayoutColumn::getSize);
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
				setId(serviceBuilderObjectLayoutRow::getObjectLayoutRowId);
				setObjectLayoutColumns(
					() -> TransformUtil.transformToArray(
						serviceBuilderObjectLayoutRow.getObjectLayoutColumns(),
						objectLayoutColumn -> _toObjectLayoutColumn(
							objectFieldLocalService, objectLayoutColumn),
						ObjectLayoutColumn.class));
				setPriority(serviceBuilderObjectLayoutRow::getPriority);
			}
		};
	}

}