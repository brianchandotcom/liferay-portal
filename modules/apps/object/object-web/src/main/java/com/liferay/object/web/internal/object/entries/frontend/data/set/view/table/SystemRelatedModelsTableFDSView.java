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

package com.liferay.object.web.internal.object.entries.frontend.data.set.view.table;

import com.liferay.frontend.data.set.provider.FDSActionProvider;
import com.liferay.frontend.data.set.provider.FDSDataProvider;
import com.liferay.frontend.data.set.provider.search.FDSKeywords;
import com.liferay.frontend.data.set.provider.search.FDSPagination;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.table.BaseTableFDSView;
import com.liferay.frontend.data.set.view.table.FDSTableSchema;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilder;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilderFactory;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.related.models.ObjectRelatedModelsProvider;
import com.liferay.object.related.models.ObjectRelatedModelsProviderRegistry;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.web.internal.object.entries.constants.ObjectEntriesFDSNames;
import com.liferay.object.web.internal.object.entries.frontend.data.set.data.model.RelatedModel;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = {
		"fds.data.provider.key=" + ObjectEntriesFDSNames.SYSTEM_RELATED_MODELS,
		"frontend.data.set.name=" + ObjectEntriesFDSNames.SYSTEM_RELATED_MODELS
	},
	service = {FDSActionProvider.class, FDSDataProvider.class, FDSView.class}
)
public class SystemRelatedModelsTableFDSView
	extends BaseTableFDSView
	implements FDSActionProvider, FDSDataProvider<RelatedModel> {

	@Override
	public List<DropdownItem> getDropdownItems(
			long groupId, HttpServletRequest httpServletRequest, Object model)
		throws PortalException {

		if (ParamUtil.getBoolean(httpServletRequest, "readOnly")) {
			return null;
		}

		RelatedModel relatedModel = (RelatedModel)model;

		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setHref(
					_getDeleteURL(
						relatedModel.getClassName(), relatedModel.getId(),
						httpServletRequest));
				dropdownItem.setIcon("trash");
				dropdownItem.setLabel(
					_language.get(httpServletRequest, Constants.DELETE));
			}
		).build();
	}

	@Override
	public FDSTableSchema getFDSTableSchema(Locale locale) {
		FDSTableSchemaBuilder fdsTableSchemaBuilder =
			_fdsTableSchemaBuilderFactory.create();

		return fdsTableSchemaBuilder.add(
			"id", "id"
		).add(
			"label", "label"
		).build();
	}

	@Override
	public List<RelatedModel> getItems(
			FDSKeywords fdsKeywords, FDSPagination fdsPagination,
			HttpServletRequest httpServletRequest, Sort sort)
		throws PortalException {

		long objectRelationshipId = ParamUtil.getLong(
			httpServletRequest, "objectRelationshipId");

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.getObjectRelationship(
				objectRelationshipId);

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectRelationship.getObjectDefinitionId2());

		ObjectRelatedModelsProvider objectRelatedModelsProvider =
			_objectRelatedModelsProviderRegistry.getObjectRelatedModelsProvider(
				objectDefinition.getClassName(), objectRelationship.getType());

		ObjectScopeProvider objectScopeProvider =
			_objectScopeProviderRegistry.getObjectScopeProvider(
				objectDefinition.getScope());

		long objectEntryId = ParamUtil.getLong(
			httpServletRequest, "objectEntryId");

		return TransformUtil.transform(
			(List<BaseModel<?>>)objectRelatedModelsProvider.getRelatedModels(
				objectScopeProvider.getGroupId(httpServletRequest),
				objectRelationshipId, objectEntryId,
				fdsPagination.getStartPosition(),
				fdsPagination.getEndPosition()),
			relatedModel -> {
				String objectFieldDBColumnName =
					objectDefinition.getPKObjectFieldDBColumnName();

				if (objectDefinition.getTitleObjectFieldId() > 0) {
					ObjectField objectField =
						_objectFieldLocalService.getObjectField(
							objectDefinition.getTitleObjectFieldId());

					objectFieldDBColumnName = objectField.getDBColumnName();
				}

				Map<String, Object> modelAttributes =
					relatedModel.getModelAttributes();

				Object value = modelAttributes.get(objectFieldDBColumnName);

				return new RelatedModel(
					objectDefinition.getClassName(),
					GetterUtil.getLong(
						modelAttributes.get(
							objectDefinition.getPKObjectFieldDBColumnName())),
					value.toString(), true);
			});
	}

	@Override
	public int getItemsCount(
			FDSKeywords fdsKeywords, HttpServletRequest httpServletRequest)
		throws PortalException {

		long objectRelationshipId = ParamUtil.getLong(
			httpServletRequest, "objectRelationshipId");

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.getObjectRelationship(
				objectRelationshipId);

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectRelationship.getObjectDefinitionId2());

		ObjectRelatedModelsProvider objectRelatedModelsProvider =
			_objectRelatedModelsProviderRegistry.getObjectRelatedModelsProvider(
				objectDefinition.getClassName(), objectRelationship.getType());

		ObjectScopeProvider objectScopeProvider =
			_objectScopeProviderRegistry.getObjectScopeProvider(
				objectDefinition.getScope());

		long objectEntryId = ParamUtil.getLong(
			httpServletRequest, "objectEntryId");

		return objectRelatedModelsProvider.getRelatedModelsCount(
			objectScopeProvider.getGroupId(httpServletRequest),
			objectRelationshipId, objectEntryId);
	}

	private PortletURL _getDeleteURL(
			String className, long id, HttpServletRequest httpServletRequest)
		throws PortalException {

		long objectEntryId = ParamUtil.getLong(
			httpServletRequest, "objectEntryId");

		ObjectEntry objectEntry = _objectEntryLocalService.getObjectEntry(
			objectEntryId);

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectEntry.getObjectDefinitionId());

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				httpServletRequest, objectDefinition.getPortletId(),
				PortletRequest.ACTION_PHASE)
		).setActionName(
			"/object_entries/edit_object_entry"
		).setCMD(
			"disassociateRelatedModels"
		).setRedirect(
			ParamUtil.getString(
				httpServletRequest, "currentUrl",
				_portal.getCurrentURL(httpServletRequest))
		).setParameter(
			"className", className
		).setParameter(
			"objectEntryId", objectEntryId
		).setParameter(
			"objectRelationshipId",
			ParamUtil.getLong(httpServletRequest, "objectRelationshipId")
		).setParameter(
			"relatedModelId", id
		).buildPortletURL();
	}

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

	@Reference
	private Language _language;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectRelatedModelsProviderRegistry
		_objectRelatedModelsProviderRegistry;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private ObjectScopeProviderRegistry _objectScopeProviderRegistry;

	@Reference
	private Portal _portal;

}