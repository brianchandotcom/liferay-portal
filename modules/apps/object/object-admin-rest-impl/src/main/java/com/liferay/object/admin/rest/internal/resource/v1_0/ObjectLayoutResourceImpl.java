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

package com.liferay.object.admin.rest.internal.resource.v1_0;

import com.liferay.object.admin.rest.dto.v1_0.ObjectLayout;
import com.liferay.object.admin.rest.dto.v1_0.ObjectLayoutBox;
import com.liferay.object.admin.rest.dto.v1_0.ObjectLayoutColumn;
import com.liferay.object.admin.rest.dto.v1_0.ObjectLayoutRow;
import com.liferay.object.admin.rest.dto.v1_0.ObjectLayoutTab;
import com.liferay.object.admin.rest.internal.dto.v1_0.util.ObjectLayoutUtil;
import com.liferay.object.admin.rest.resource.v1_0.ObjectLayoutResource;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectLayoutService;
import com.liferay.object.service.persistence.ObjectLayoutBoxPersistence;
import com.liferay.object.service.persistence.ObjectLayoutColumnPersistence;
import com.liferay.object.service.persistence.ObjectLayoutRowPersistence;
import com.liferay.object.service.persistence.ObjectLayoutTabPersistence;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.util.SearchUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/object-layout.properties",
	scope = ServiceScope.PROTOTYPE, service = ObjectLayoutResource.class
)
public class ObjectLayoutResourceImpl extends BaseObjectLayoutResourceImpl {

	@Override
	public void deleteObjectLayout(Long objectLayoutId) throws Exception {
		_objectLayoutService.deleteObjectLayout(objectLayoutId);
	}

	@Override
	public Page<ObjectLayout> getObjectDefinitionObjectLayoutsPage(
			Long objectDefinitionId, String search, Pagination pagination)
		throws Exception {

		return SearchUtil.search(
			HashMapBuilder.put(
				"create",
				addAction(
					ActionKeys.UPDATE, "postObjectDefinitionObjectLayout",
					ObjectDefinition.class.getName(), objectDefinitionId)
			).put(
				"get",
				addAction(
					ActionKeys.VIEW, "getObjectDefinitionObjectLayoutsPage",
					ObjectDefinition.class.getName(), objectDefinitionId)
			).build(),
			booleanQuery -> {
			},
			null, com.liferay.object.model.ObjectLayout.class.getName(), search,
			pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute(Field.NAME, search);
				searchContext.setAttribute(
					"objectDefinitionId", objectDefinitionId);
				searchContext.setCompanyId(contextCompany.getCompanyId());
			},
			null,
			document -> _toObjectLayout(
				_objectLayoutService.getObjectLayout(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public ObjectLayout getObjectLayout(Long objectLayoutId) throws Exception {
		return _toObjectLayout(
			_objectLayoutService.getObjectLayout(objectLayoutId));
	}

	@Override
	public ObjectLayout postObjectDefinitionObjectLayout(
			Long objectDefinitionId, ObjectLayout objectLayout)
		throws Exception {

		return _toObjectLayout(
			_objectLayoutService.addObjectLayout(
				objectDefinitionId,
				GetterUtil.getBoolean(objectLayout.getDefaultObjectLayout()),
				LocalizedMapUtil.getLocalizedMap(objectLayout.getName()),
				transformToList(
					objectLayout.getObjectLayoutTabs(),
					this::_toObjectLayoutTab)));
	}

	@Override
	public ObjectLayout putObjectLayout(
			Long objectLayoutId, ObjectLayout objectLayout)
		throws Exception {

		return _toObjectLayout(
			_objectLayoutService.updateObjectLayout(
				objectLayoutId, objectLayout.getDefaultObjectLayout(),
				LocalizedMapUtil.getLocalizedMap(objectLayout.getName()),
				transformToList(
					objectLayout.getObjectLayoutTabs(),
					this::_toObjectLayoutTab)));
	}

	private ObjectLayout _toObjectLayout(
		com.liferay.object.model.ObjectLayout serviceBuilderObjectLayout) {

		return ObjectLayoutUtil.toObjectLayout(
			HashMapBuilder.put(
				"delete",
				addAction(
					ActionKeys.DELETE, "deleteObjectLayout",
					ObjectDefinition.class.getName(),
					serviceBuilderObjectLayout.getObjectDefinitionId())
			).put(
				"get",
				addAction(
					ActionKeys.VIEW, "getObjectLayout",
					ObjectDefinition.class.getName(),
					serviceBuilderObjectLayout.getObjectDefinitionId())
			).put(
				"update",
				addAction(
					ActionKeys.UPDATE, "putObjectLayout",
					ObjectDefinition.class.getName(),
					serviceBuilderObjectLayout.getObjectDefinitionId())
			).build(),
			serviceBuilderObjectLayout);
	}

	private com.liferay.object.model.ObjectLayoutBox _toObjectLayoutBox(
		ObjectLayoutBox objectLayoutBox) {

		com.liferay.object.model.ObjectLayoutBox serviceBuilderObjectLayoutBox =
			_objectLayoutBoxPersistence.create(0L);

		serviceBuilderObjectLayoutBox.setCollapsable(
			objectLayoutBox.getCollapsable());
		serviceBuilderObjectLayoutBox.setNameMap(
			LocalizedMapUtil.getLocalizedMap(objectLayoutBox.getName()));
		serviceBuilderObjectLayoutBox.setObjectLayoutRows(
			transformToList(
				objectLayoutBox.getObjectLayoutRows(),
				this::_toObjectLayoutRow));
		serviceBuilderObjectLayoutBox.setPriority(
			objectLayoutBox.getPriority());

		return serviceBuilderObjectLayoutBox;
	}

	private com.liferay.object.model.ObjectLayoutColumn _toObjectLayoutColumn(
		ObjectLayoutColumn objectLayoutColumn) {

		com.liferay.object.model.ObjectLayoutColumn
			serviceBuilderObjectLayoutColumn =
				_objectLayoutColumnPersistence.create(0L);

		serviceBuilderObjectLayoutColumn.setObjectFieldId(
			objectLayoutColumn.getObjectFieldId());
		serviceBuilderObjectLayoutColumn.setPriority(
			objectLayoutColumn.getPriority());
		serviceBuilderObjectLayoutColumn.setSize(
			GetterUtil.getInteger(objectLayoutColumn.getSize(), 12));

		return serviceBuilderObjectLayoutColumn;
	}

	private com.liferay.object.model.ObjectLayoutRow _toObjectLayoutRow(
		ObjectLayoutRow objectLayoutRow) {

		com.liferay.object.model.ObjectLayoutRow serviceBuilderObjectLayoutRow =
			_objectLayoutRowPersistence.create(0L);

		serviceBuilderObjectLayoutRow.setObjectLayoutColumns(
			transformToList(
				objectLayoutRow.getObjectLayoutColumns(),
				this::_toObjectLayoutColumn));
		serviceBuilderObjectLayoutRow.setPriority(
			objectLayoutRow.getPriority());

		return serviceBuilderObjectLayoutRow;
	}

	private com.liferay.object.model.ObjectLayoutTab _toObjectLayoutTab(
		ObjectLayoutTab objectLayoutTab) {

		com.liferay.object.model.ObjectLayoutTab serviceBuilderObjectLayoutTab =
			_objectLayoutTabPersistence.create(0L);

		serviceBuilderObjectLayoutTab.setNameMap(
			LocalizedMapUtil.getLocalizedMap(objectLayoutTab.getName()));
		serviceBuilderObjectLayoutTab.setObjectLayoutBoxes(
			transformToList(
				objectLayoutTab.getObjectLayoutBoxes(),
				this::_toObjectLayoutBox));
		serviceBuilderObjectLayoutTab.setObjectRelationshipId(
			GetterUtil.getLong(objectLayoutTab.getObjectRelationshipId()));
		serviceBuilderObjectLayoutTab.setPriority(
			objectLayoutTab.getPriority());

		return serviceBuilderObjectLayoutTab;
	}

	@Reference
	private ObjectLayoutBoxPersistence _objectLayoutBoxPersistence;

	@Reference
	private ObjectLayoutColumnPersistence _objectLayoutColumnPersistence;

	@Reference
	private ObjectLayoutRowPersistence _objectLayoutRowPersistence;

	@Reference
	private ObjectLayoutService _objectLayoutService;

	@Reference
	private ObjectLayoutTabPersistence _objectLayoutTabPersistence;

}