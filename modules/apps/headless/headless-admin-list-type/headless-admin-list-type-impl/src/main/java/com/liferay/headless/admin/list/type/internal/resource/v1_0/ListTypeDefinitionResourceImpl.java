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

package com.liferay.headless.admin.list.type.internal.resource.v1_0;

import com.liferay.headless.admin.list.type.dto.v1_0.ListTypeDefinition;
import com.liferay.headless.admin.list.type.dto.v1_0.ListTypeEntry;
import com.liferay.headless.admin.list.type.internal.configuration.activator.FFListTypeDefinitionPermissionsActionConfigurationActivator;
import com.liferay.headless.admin.list.type.internal.dto.v1_0.util.ListTypeEntryUtil;
import com.liferay.headless.admin.list.type.internal.odata.entity.v1_0.ListTypeDefinitionEntityModel;
import com.liferay.headless.admin.list.type.resource.v1_0.ListTypeDefinitionResource;
import com.liferay.list.type.constants.ListTypeActionKeys;
import com.liferay.list.type.constants.ListTypeConstants;
import com.liferay.list.type.service.ListTypeDefinitionService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.util.SearchUtil;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/list-type-definition.properties",
	scope = ServiceScope.PROTOTYPE, service = ListTypeDefinitionResource.class
)
public class ListTypeDefinitionResourceImpl
	extends BaseListTypeDefinitionResourceImpl implements EntityModelResource {

	@Override
	public void deleteListTypeDefinition(Long listTypeDefinitionId)
		throws Exception {

		_listTypeDefinitionService.deleteListTypeDefinition(
			listTypeDefinitionId);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public ListTypeDefinition getListTypeDefinition(Long listTypeDefinitionId)
		throws Exception {

		return _toListTypeDefinition(
			_listTypeDefinitionService.getListTypeDefinition(
				listTypeDefinitionId));
	}

	@Override
	public Page<ListTypeDefinition> getListTypeDefinitionsPage(
			String search, Aggregation aggregation, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			HashMapBuilder.put(
				"create",
				addAction(
					ListTypeActionKeys.ADD_LIST_TYPE_DEFINITION,
					"postListTypeDefinition", ListTypeConstants.RESOURCE_NAME,
					contextCompany.getCompanyId())
			).put(
				"get",
				addAction(
					ActionKeys.VIEW, "getListTypeDefinitionsPage",
					ListTypeConstants.RESOURCE_NAME,
					contextCompany.getCompanyId())
			).build(),
			booleanQuery -> {
			},
			filter,
			com.liferay.list.type.model.ListTypeDefinition.class.getName(),
			search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute(Field.NAME, search);
				searchContext.setCompanyId(contextCompany.getCompanyId());
			},
			sorts,
			document -> _toListTypeDefinition(
				_listTypeDefinitionService.getListTypeDefinition(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public ListTypeDefinition postListTypeDefinition(
			ListTypeDefinition listTypeDefinition)
		throws Exception {

		return _toListTypeDefinition(
			_listTypeDefinitionService.addListTypeDefinition(
				LocalizedMapUtil.getLocalizedMap(
					listTypeDefinition.getName_i18n())));
	}

	@Override
	public ListTypeDefinition putListTypeDefinition(
			Long listTypeDefinitionId, ListTypeDefinition listTypeDefinition)
		throws Exception {

		return _toListTypeDefinition(
			_listTypeDefinitionService.updateListTypeDefinition(
				listTypeDefinitionId,
				LocalizedMapUtil.getLocalizedMap(
					listTypeDefinition.getName_i18n())));
	}

	private ListTypeDefinition _toListTypeDefinition(
		com.liferay.list.type.model.ListTypeDefinition
			serviceBuilderListTypeDefinition) {

		return new ListTypeDefinition() {
			{
				actions = HashMapBuilder.put(
					"delete",
					() -> {
						int count =
							_objectFieldLocalService.
								getObjectFieldsCountByListTypeDefinitionId(
									serviceBuilderListTypeDefinition.
										getListTypeDefinitionId());

						if (count > 0) {
							return null;
						}

						return addAction(
							ActionKeys.DELETE, "deleteListTypeDefinition",
							com.liferay.list.type.model.ListTypeDefinition.
								class.getName(),
							serviceBuilderListTypeDefinition.
								getListTypeDefinitionId());
					}
				).put(
					"get",
					addAction(
						ActionKeys.VIEW, "getListTypeDefinition",
						com.liferay.list.type.model.ListTypeDefinition.class.
							getName(),
						serviceBuilderListTypeDefinition.
							getListTypeDefinitionId())
				).put(
					"permissions",
					() -> {
						if (!_ffListTypeDefinitionPermissionsActionConfigurationActivator.
								enabled()) {

							return null;
						}

						return addAction(
							ActionKeys.PERMISSIONS, "patchListTypeDefinition",
							com.liferay.list.type.model.ListTypeDefinition.
								class.getName(),
							serviceBuilderListTypeDefinition.
								getListTypeDefinitionId());
					}
				).put(
					"update",
					addAction(
						ActionKeys.UPDATE, "putListTypeDefinition",
						com.liferay.list.type.model.ListTypeDefinition.class.
							getName(),
						serviceBuilderListTypeDefinition.
							getListTypeDefinitionId())
				).build();
				dateCreated = serviceBuilderListTypeDefinition.getCreateDate();
				dateModified =
					serviceBuilderListTypeDefinition.getModifiedDate();
				id = serviceBuilderListTypeDefinition.getListTypeDefinitionId();
				listTypeEntries = transformToArray(
					_listTypeEntryLocalService.getListTypeEntries(
						serviceBuilderListTypeDefinition.
							getListTypeDefinitionId(),
						QueryUtil.ALL_POS, QueryUtil.ALL_POS),
					listTypeEntry -> ListTypeEntryUtil.toListTypeEntry(
						null, contextAcceptLanguage.getPreferredLocale(),
						listTypeEntry),
					ListTypeEntry.class);
				name = serviceBuilderListTypeDefinition.getName(
					contextAcceptLanguage.getPreferredLocale());
				name_i18n = LocalizedMapUtil.getI18nMap(
					serviceBuilderListTypeDefinition.getNameMap());
			}
		};
	}

	private static final EntityModel _entityModel =
		new ListTypeDefinitionEntityModel();

	@Reference
	private FFListTypeDefinitionPermissionsActionConfigurationActivator
		_ffListTypeDefinitionPermissionsActionConfigurationActivator;

	@Reference
	private ListTypeDefinitionService _listTypeDefinitionService;

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}