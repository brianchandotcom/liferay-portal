/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.admin.rest.internal.resource.v1_0;

import com.liferay.account.model.AccountEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.notification.service.NotificationTemplateLocalService;
import com.liferay.object.admin.rest.dto.v1_0.ObjectAction;
import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.dto.v1_0.ObjectField;
import com.liferay.object.admin.rest.dto.v1_0.ObjectLayout;
import com.liferay.object.admin.rest.dto.v1_0.ObjectRelationship;
import com.liferay.object.admin.rest.dto.v1_0.ObjectValidationRule;
import com.liferay.object.admin.rest.dto.v1_0.ObjectView;
import com.liferay.object.admin.rest.dto.v1_0.Status;
import com.liferay.object.admin.rest.dto.v1_0.util.ObjectActionUtil;
import com.liferay.object.admin.rest.internal.dto.v1_0.converter.constants.DTOConverterConstants;
import com.liferay.object.admin.rest.internal.dto.v1_0.util.ObjectFieldSettingUtil;
import com.liferay.object.admin.rest.internal.dto.v1_0.util.ObjectFieldUtil;
import com.liferay.object.admin.rest.internal.dto.v1_0.util.ObjectLayoutUtil;
import com.liferay.object.admin.rest.internal.odata.entity.v1_0.ObjectDefinitionEntityModel;
import com.liferay.object.admin.rest.resource.v1_0.ObjectActionResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectLayoutResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectRelationshipResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectValidationRuleResource;
import com.liferay.object.admin.rest.resource.v1_0.ObjectViewResource;
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.constants.ObjectConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.exception.ObjectDefinitionEnableLocalizationException;
import com.liferay.object.exception.ObjectDefinitionStorageTypeException;
import com.liferay.object.model.ObjectFolder;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectActionService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.ObjectFilterLocalService;
import com.liferay.object.service.ObjectFolderLocalService;
import com.liferay.object.service.ObjectLayoutLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.service.ObjectValidationRuleLocalService;
import com.liferay.object.service.ObjectViewLocalService;
import com.liferay.object.service.ObjectViewService;
import com.liferay.object.system.JaxRsApplicationDescriptor;
import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.object.system.SystemObjectDefinitionManagerRegistry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.auth.GuestOrUserUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/object-definition.properties",
	scope = ServiceScope.PROTOTYPE, service = ObjectDefinitionResource.class
)
public class ObjectDefinitionResourceImpl
	extends BaseObjectDefinitionResourceImpl {

	@Override
	public void create(
			Collection<ObjectDefinition> objectDefinitions,
			Map<String, Serializable> parameters)
		throws Exception {

		super.create(objectDefinitions, parameters);

		for (ObjectDefinition objectDefinition : objectDefinitions) {
			Status status = objectDefinition.getStatus();

			if ((status == null) ||
				(status.getCode() != WorkflowConstants.STATUS_APPROVED)) {

				continue;
			}

			com.liferay.object.model.ObjectDefinition
				serviceBuilderObjectDefinition =
					_objectDefinitionService.
						getObjectDefinitionByExternalReferenceCode(
							objectDefinition.getExternalReferenceCode(),
							contextCompany.getCompanyId());

			if (serviceBuilderObjectDefinition.isApproved()) {
				continue;
			}

			_objectDefinitionService.publishCustomObjectDefinition(
				serviceBuilderObjectDefinition.getObjectDefinitionId());
		}
	}

	@Override
	public void deleteObjectDefinition(Long objectDefinitionId)
		throws Exception {

		_objectDefinitionService.deleteObjectDefinition(objectDefinitionId);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public ObjectDefinition getObjectDefinition(Long objectDefinitionId)
		throws Exception {

		return _toObjectDefinition(
			_objectDefinitionService.getObjectDefinition(objectDefinitionId));
	}

	@Override
	public ObjectDefinition getObjectDefinitionByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		return _toObjectDefinition(
			_objectDefinitionService.
				fetchObjectDefinitionByExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId()));
	}

	@Override
	public Page<ObjectDefinition> getObjectDefinitionsPage(
			String search, Aggregation aggregation, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			HashMapBuilder.put(
				"create",
				addAction(
					ObjectActionKeys.ADD_OBJECT_DEFINITION,
					"postObjectDefinition", ObjectConstants.RESOURCE_NAME,
					contextCompany.getCompanyId())
			).put(
				"createBatch",
				addAction(
					ObjectActionKeys.ADD_OBJECT_DEFINITION,
					"postObjectDefinitionBatch", ObjectConstants.RESOURCE_NAME,
					contextCompany.getCompanyId())
			).put(
				"deleteBatch",
				addAction(
					ActionKeys.DELETE, "deleteObjectDefinitionBatch",
					ObjectConstants.RESOURCE_NAME, null)
			).put(
				"get",
				addAction(
					ActionKeys.VIEW, "getObjectDefinitionsPage",
					ObjectConstants.RESOURCE_NAME,
					contextCompany.getCompanyId())
			).put(
				"updateBatch",
				addAction(
					ActionKeys.UPDATE, "putObjectDefinitionBatch",
					ObjectConstants.RESOURCE_NAME, null)
			).build(),
			booleanQuery -> {
			},
			filter, com.liferay.object.model.ObjectDefinition.class.getName(),
			search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.addVulcanAggregation(aggregation);
				searchContext.setAttribute(Field.NAME, search);
				searchContext.setCompanyId(contextCompany.getCompanyId());
			},
			sorts,
			document -> _toObjectDefinition(
				_objectDefinitionService.getObjectDefinition(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public ObjectDefinition postObjectDefinition(
			ObjectDefinition objectDefinition)
		throws Exception {

		if (Validator.isNotNull(objectDefinition.getEnableLocalization()) &&
			!FeatureFlagManagerUtil.isEnabled("LPS-172017")) {

			throw new ObjectDefinitionEnableLocalizationException();
		}

		if (Validator.isNotNull(objectDefinition.getModifiable()) &&
			!FeatureFlagManagerUtil.isEnabled("LPS-167253")) {

			throw new UnsupportedOperationException();
		}

		if (Validator.isNotNull(
				objectDefinition.getObjectFolderExternalReferenceCode()) &&
			!FeatureFlagManagerUtil.isEnabled("LPS-148856")) {

			throw new UnsupportedOperationException();
		}

		if (!Validator.isBlank(objectDefinition.getStorageType()) &&
			!FeatureFlagManagerUtil.isEnabled("LPS-135430")) {

			throw new ObjectDefinitionStorageTypeException();
		}

		_addListTypeDefinition(objectDefinition);

		com.liferay.object.model.ObjectDefinition
			serviceBuilderObjectDefinition;

		if (GetterUtil.getBoolean(objectDefinition.getSystem()) &&
			FeatureFlagManagerUtil.isEnabled("LPS-167253")) {

			serviceBuilderObjectDefinition =
				_objectDefinitionService.addSystemObjectDefinition(
					objectDefinition.getExternalReferenceCode(),
					contextUser.getUserId(),
					_getObjectFolderId(
						objectDefinition.
							getObjectFolderExternalReferenceCode()),
					GetterUtil.getBoolean(objectDefinition.getEnableComments()),
					LocalizedMapUtil.getLocalizedMap(
						objectDefinition.getLabel()),
					objectDefinition.getName(),
					objectDefinition.getPanelAppOrder(),
					objectDefinition.getPanelCategoryKey(),
					LocalizedMapUtil.getLocalizedMap(
						objectDefinition.getPluralLabel()),
					objectDefinition.getScope(),
					transformToList(
						objectDefinition.getObjectFields(),
						objectField -> ObjectFieldUtil.toObjectField(
							GetterUtil.getBoolean(
								objectDefinition.getEnableLocalization()),
							_listTypeDefinitionLocalService, objectField,
							_objectFieldLocalService,
							_objectFieldSettingLocalService,
							_objectFilterLocalService)));
		}
		else {
			serviceBuilderObjectDefinition =
				_objectDefinitionService.addCustomObjectDefinition(
					_getObjectFolderId(
						objectDefinition.
							getObjectFolderExternalReferenceCode()),
					GetterUtil.getBoolean(objectDefinition.getEnableComments()),
					GetterUtil.getBoolean(
						objectDefinition.getEnableLocalization()),
					LocalizedMapUtil.getLocalizedMap(
						objectDefinition.getLabel()),
					objectDefinition.getName(),
					objectDefinition.getPanelAppOrder(),
					objectDefinition.getPanelCategoryKey(),
					LocalizedMapUtil.getLocalizedMap(
						objectDefinition.getPluralLabel()),
					GetterUtil.getBoolean(objectDefinition.getPortlet(), true),
					objectDefinition.getScope(),
					objectDefinition.getStorageType(),
					transformToList(
						ArrayUtil.filter(
							objectDefinition.getObjectFields(),
							objectField -> !StringUtil.equals(
								objectField.getBusinessTypeAsString(),
								ObjectFieldConstants.
									BUSINESS_TYPE_AGGREGATION)),
						objectField -> ObjectFieldUtil.toObjectField(
							GetterUtil.getBoolean(
								objectDefinition.getEnableLocalization()),
							_listTypeDefinitionLocalService, objectField,
							_objectFieldLocalService,
							_objectFieldSettingLocalService,
							_objectFilterLocalService)));
		}

		if (!Validator.isBlank(objectDefinition.getExternalReferenceCode())) {
			serviceBuilderObjectDefinition =
				_objectDefinitionService.updateExternalReferenceCode(
					serviceBuilderObjectDefinition.getObjectDefinitionId(),
					objectDefinition.getExternalReferenceCode());
		}

		com.liferay.object.model.ObjectField serviceBuilderObjectField =
			_objectFieldLocalService.fetchObjectField(
				serviceBuilderObjectDefinition.getObjectDefinitionId(),
				objectDefinition.getTitleObjectFieldName());

		if (serviceBuilderObjectField != null) {
			serviceBuilderObjectDefinition =
				_objectDefinitionService.updateTitleObjectFieldId(
					serviceBuilderObjectDefinition.getObjectDefinitionId(),
					serviceBuilderObjectField.getObjectFieldId());
		}

		_addObjectDefinitionResources(
			Collections.emptySet(), objectDefinition.getObjectActions(),
			serviceBuilderObjectDefinition.getObjectDefinitionId(),
			objectDefinition.getObjectLayouts(),
			objectDefinition.getObjectRelationships(),
			objectDefinition.getObjectValidationRules(),
			objectDefinition.getObjectViews());

		for (com.liferay.object.model.ObjectField
				aggregationServiceBuilderObjectField :
					transformToList(
						ArrayUtil.filter(
							objectDefinition.getObjectFields(),
							objectField -> StringUtil.equals(
								objectField.getBusinessTypeAsString(),
								ObjectFieldConstants.
									BUSINESS_TYPE_AGGREGATION)),
						objectField -> ObjectFieldUtil.toObjectField(
							false, _listTypeDefinitionLocalService, objectField,
							_objectFieldLocalService,
							_objectFieldSettingLocalService,
							_objectFilterLocalService))) {

			_objectFieldLocalService.addCustomObjectField(
				aggregationServiceBuilderObjectField.getExternalReferenceCode(),
				GuestOrUserUtil.getUserId(),
				aggregationServiceBuilderObjectField.getListTypeDefinitionId(),
				serviceBuilderObjectDefinition.getObjectDefinitionId(),
				aggregationServiceBuilderObjectField.getBusinessType(),
				aggregationServiceBuilderObjectField.getDBType(),
				aggregationServiceBuilderObjectField.isIndexed(),
				aggregationServiceBuilderObjectField.isIndexedAsKeyword(),
				aggregationServiceBuilderObjectField.getIndexedLanguageId(),
				aggregationServiceBuilderObjectField.getLabelMap(),
				aggregationServiceBuilderObjectField.isLocalized(),
				aggregationServiceBuilderObjectField.getName(),
				aggregationServiceBuilderObjectField.getReadOnly(),
				aggregationServiceBuilderObjectField.
					getReadOnlyConditionExpression(),
				aggregationServiceBuilderObjectField.isRequired(),
				aggregationServiceBuilderObjectField.isState(),
				aggregationServiceBuilderObjectField.getObjectFieldSettings());
		}

		Status status = objectDefinition.getStatus();

		if (FeatureFlagManagerUtil.isEnabled("LPS-167253") &&
			(status != null) &&
			(status.getCode() == WorkflowConstants.STATUS_APPROVED)) {

			postObjectDefinitionPublish(
				serviceBuilderObjectDefinition.getObjectDefinitionId());

			serviceBuilderObjectDefinition =
				_objectDefinitionService.
					fetchObjectDefinitionByExternalReferenceCode(
						serviceBuilderObjectDefinition.
							getExternalReferenceCode(),
						serviceBuilderObjectDefinition.getCompanyId());
		}

		return _toObjectDefinition(serviceBuilderObjectDefinition);
	}

	@Override
	public void postObjectDefinitionPublish(Long objectDefinitionId)
		throws Exception {

		com.liferay.object.model.ObjectDefinition
			serviceBuilderObjectDefinition =
				_objectDefinitionService.getObjectDefinition(
					objectDefinitionId);

		if (GetterUtil.getBoolean(serviceBuilderObjectDefinition.getSystem()) &&
			FeatureFlagManagerUtil.isEnabled("LPS-167253")) {

			_objectDefinitionService.publishSystemObjectDefinition(
				objectDefinitionId);
		}
		else {
			_objectDefinitionService.publishCustomObjectDefinition(
				objectDefinitionId);
		}
	}

	@Override
	public ObjectDefinition putObjectDefinition(
			Long objectDefinitionId, ObjectDefinition objectDefinition)
		throws Exception {

		// TODO Move logic to service

		if (Validator.isNotNull(objectDefinition.getModifiable()) &&
			!FeatureFlagManagerUtil.isEnabled("LPS-167253")) {

			throw new UnsupportedOperationException();
		}

		if (Validator.isNotNull(
				objectDefinition.getObjectFolderExternalReferenceCode()) &&
			!FeatureFlagManagerUtil.isEnabled("LPS-148856")) {

			throw new UnsupportedOperationException();
		}

		if (!Validator.isBlank(objectDefinition.getStorageType()) &&
			!FeatureFlagManagerUtil.isEnabled("LPS-135430")) {

			throw new ObjectDefinitionStorageTypeException();
		}

		com.liferay.object.model.ObjectDefinition
			serviceBuilderObjectDefinition =
				_objectDefinitionService.getObjectDefinition(
					objectDefinitionId);

		if (!serviceBuilderObjectDefinition.isApproved()) {
			_addListTypeDefinition(objectDefinition);
		}

		long accountEntryRestrictedObjectFieldId = 0;

		com.liferay.object.model.ObjectField
			accountEntryRestrictedServiceBuilderObjectField =
				_objectFieldLocalService.fetchObjectField(
					objectDefinitionId,
					objectDefinition.
						getAccountEntryRestrictedObjectFieldName());

		if (accountEntryRestrictedServiceBuilderObjectField != null) {
			accountEntryRestrictedObjectFieldId =
				accountEntryRestrictedServiceBuilderObjectField.
					getObjectFieldId();
		}

		long titleObjectFieldId = 0;

		com.liferay.object.model.ObjectField titleServiceBuilderObjectField =
			_objectFieldLocalService.fetchObjectField(
				objectDefinitionId, objectDefinition.getTitleObjectFieldName());

		if (titleServiceBuilderObjectField != null) {
			titleObjectFieldId =
				titleServiceBuilderObjectField.getObjectFieldId();
		}

		if (serviceBuilderObjectDefinition.isUnmodifiableSystemObject()) {
			serviceBuilderObjectDefinition =
				_objectDefinitionService.updateSystemObjectDefinition(
					objectDefinition.getExternalReferenceCode(),
					objectDefinitionId,
					_getObjectFolderId(
						objectDefinition.
							getObjectFolderExternalReferenceCode()),
					titleObjectFieldId);
		}
		else {
			serviceBuilderObjectDefinition =
				_objectDefinitionService.updateCustomObjectDefinition(
					objectDefinition.getExternalReferenceCode(),
					objectDefinitionId,
					GetterUtil.getLong(accountEntryRestrictedObjectFieldId), 0,
					_getObjectFolderId(
						objectDefinition.
							getObjectFolderExternalReferenceCode()),
					titleObjectFieldId,
					GetterUtil.getBoolean(
						objectDefinition.getAccountEntryRestricted()),
					GetterUtil.getBoolean(
						objectDefinition.getActive(),
						serviceBuilderObjectDefinition.getActive()),
					GetterUtil.getBoolean(
						objectDefinition.getEnableCategorization(), true),
					GetterUtil.getBoolean(objectDefinition.getEnableComments()),
					GetterUtil.getBoolean(
						objectDefinition.getEnableLocalization()),
					GetterUtil.getBoolean(
						objectDefinition.getEnableObjectEntryHistory()),
					LocalizedMapUtil.getLocalizedMap(
						objectDefinition.getLabel()),
					objectDefinition.getName(),
					objectDefinition.getPanelAppOrder(),
					objectDefinition.getPanelCategoryKey(),
					GetterUtil.getBoolean(objectDefinition.getPortlet()),
					LocalizedMapUtil.getLocalizedMap(
						objectDefinition.getPluralLabel()),
					objectDefinition.getScope());
		}

		List<com.liferay.object.model.ObjectField> serviceBuilderObjectFields =
			new ArrayList<>(
				_objectFieldLocalService.getObjectFields(
					objectDefinitionId, false));

		if (objectDefinition.getObjectFields() != null) {
			for (ObjectField objectField : objectDefinition.getObjectFields()) {
				long listTypeDefinitionId =
					ObjectFieldUtil.getListTypeDefinitionId(
						serviceBuilderObjectDefinition.getCompanyId(),
						_listTypeDefinitionLocalService, objectField);

				_objectFieldLocalService.updateObjectField(
					objectField.getExternalReferenceCode(),
					GetterUtil.getLong(objectField.getId()),
					contextUser.getUserId(), listTypeDefinitionId,
					objectDefinitionId, objectField.getBusinessTypeAsString(),
					null, null, objectField.getDBTypeAsString(),
					objectField.getIndexed(), objectField.getIndexedAsKeyword(),
					objectField.getIndexedLanguageId(),
					LocalizedMapUtil.getLocalizedMap(objectField.getLabel()),
					GetterUtil.getBoolean(objectField.getLocalized()),
					objectField.getName(), objectField.getReadOnlyAsString(),
					objectField.getReadOnlyConditionExpression(),
					objectField.getRequired(),
					GetterUtil.getBoolean(objectField.getState()),
					objectField.getSystem(),
					ObjectFieldSettingUtil.toObjectFieldSettings(
						listTypeDefinitionId, objectField,
						_objectFieldSettingLocalService,
						_objectFilterLocalService));

				serviceBuilderObjectFields.removeIf(
					serviceBuilderObjectField -> Objects.equals(
						serviceBuilderObjectField.getName(),
						objectField.getName()));
			}
		}

		for (com.liferay.object.model.ObjectField serviceBuilderObjectField :
				serviceBuilderObjectFields) {

			if (Objects.equals(
					serviceBuilderObjectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_RELATIONSHIP)) {

				continue;
			}

			_objectFieldLocalService.deleteObjectField(
				serviceBuilderObjectField);
		}

		ObjectAction[] objectActions = objectDefinition.getObjectActions();

		if (objectActions != null) {
			_objectActionLocalService.deleteObjectActions(objectDefinitionId);
		}

		ObjectLayout[] objectLayouts = objectDefinition.getObjectLayouts();

		if (objectLayouts != null) {
			_objectLayoutLocalService.deleteObjectLayouts(objectDefinitionId);
		}

		ObjectRelationship[] objectRelationships =
			objectDefinition.getObjectRelationships();

		Set<String> accountEntryRestrictedObjectRelationshipsNames =
			_getAccountEntryRestrictedObjectRelationshipsNames(
				serviceBuilderObjectDefinition, objectRelationships);

		ObjectValidationRule[] objectValidationRules =
			objectDefinition.getObjectValidationRules();

		if (objectValidationRules != null) {
			_objectValidationRuleLocalService.deleteObjectValidationRules(
				objectDefinitionId);
		}

		ObjectView[] objectViews = objectDefinition.getObjectViews();

		if (objectViews != null) {
			_objectViewLocalService.deleteObjectViews(objectDefinitionId);
		}

		_addObjectDefinitionResources(
			accountEntryRestrictedObjectRelationshipsNames, objectActions,
			objectDefinitionId, objectLayouts, objectRelationships,
			objectValidationRules, objectViews);

		return _toObjectDefinition(serviceBuilderObjectDefinition);
	}

	@Override
	public ObjectDefinition putObjectDefinitionByExternalReferenceCode(
			String externalReferenceCode, ObjectDefinition objectDefinition)
		throws Exception {

		com.liferay.object.model.ObjectDefinition
			serviceBuilderObjectDefinition =
				_objectDefinitionService.
					fetchObjectDefinitionByExternalReferenceCode(
						externalReferenceCode, contextCompany.getCompanyId());

		objectDefinition.setExternalReferenceCode(externalReferenceCode);

		if (serviceBuilderObjectDefinition != null) {
			return putObjectDefinition(
				serviceBuilderObjectDefinition.getObjectDefinitionId(),
				objectDefinition);
		}

		return postObjectDefinition(objectDefinition);
	}

	private void _addListTypeDefinition(ObjectDefinition objectDefinition)
		throws Exception {

		if (objectDefinition.getObjectFields() == null) {
			return;
		}

		for (ObjectField objectField : objectDefinition.getObjectFields()) {
			objectField.setListTypeDefinitionId(
				ObjectFieldUtil.addListTypeDefinition(
					contextUser.getCompanyId(), _listTypeDefinitionLocalService,
					_listTypeEntryLocalService, objectField,
					contextUser.getUserId()));
		}
	}

	private void _addObjectDefinitionResources(
			Set<String> accountEntryRestrictedObjectRelationshipsNames,
			ObjectAction[] objectActions, long objectDefinitionId,
			ObjectLayout[] objectLayouts,
			ObjectRelationship[] objectRelationships,
			ObjectValidationRule[] objectValidationRules,
			ObjectView[] objectViews)
		throws Exception {

		if (objectActions != null) {
			ObjectActionResource.Builder builder =
				_objectActionResourceFactory.create();

			ObjectActionResource objectActionResource = builder.user(
				contextUser
			).build();

			for (ObjectAction objectAction : objectActions) {
				objectActionResource.postObjectDefinitionObjectAction(
					objectDefinitionId, objectAction);
			}
		}

		if (objectLayouts != null) {
			ObjectLayoutResource.Builder builder =
				_objectLayoutResourceFactory.create();

			ObjectLayoutResource objectLayoutResource = builder.user(
				contextUser
			).build();

			for (ObjectLayout objectLayout : objectLayouts) {
				objectLayoutResource.postObjectDefinitionObjectLayout(
					objectDefinitionId, objectLayout);
			}
		}

		if (objectRelationships != null) {
			Set<String> deleteObjectRelationshipsNames =
				SetUtil.asymmetricDifference(
					transform(
						_objectRelationshipLocalService.getObjectRelationships(
							objectDefinitionId),
						com.liferay.object.model.ObjectRelationship::getName),
					transformToList(
						objectRelationships, ObjectRelationship::getName));

			for (String deleteObjectRelationshipsName :
					deleteObjectRelationshipsNames) {

				com.liferay.object.model.ObjectRelationship
					serviceBuilderObjectRelationship =
						_objectRelationshipLocalService.
							fetchObjectRelationshipByObjectDefinitionId(
								objectDefinitionId,
								deleteObjectRelationshipsName);

				_objectRelationshipLocalService.deleteObjectRelationship(
					serviceBuilderObjectRelationship.getObjectRelationshipId());
			}

			ObjectRelationshipResource.Builder builder =
				_objectRelationshipResourceFactory.create();

			ObjectRelationshipResource objectRelationshipResource =
				builder.user(
					contextUser
				).build();

			for (ObjectRelationship objectRelationship : objectRelationships) {
				com.liferay.object.model.ObjectRelationship
					serviceBuilderObjectRelationship =
						_objectRelationshipLocalService.
							fetchObjectRelationshipByObjectDefinitionId(
								objectDefinitionId,
								objectRelationship.getName());

				if (serviceBuilderObjectRelationship != null) {
					objectRelationshipResource.putObjectRelationship(
						serviceBuilderObjectRelationship.
							getObjectRelationshipId(),
						objectRelationship);

					continue;
				}

				objectRelationship =
					objectRelationshipResource.
						postObjectDefinitionObjectRelationship(
							objectDefinitionId, objectRelationship);

				if (accountEntryRestrictedObjectRelationshipsNames.contains(
						objectRelationship.getName())) {

					_objectDefinitionLocalService.enableAccountEntryRestricted(
						_objectRelationshipLocalService.getObjectRelationship(
							objectRelationship.getId()));
				}
			}
		}

		if (objectValidationRules != null) {
			ObjectValidationRuleResource.Builder builder =
				_objectValidationRuleResourceFactory.create();

			ObjectValidationRuleResource objectValidationRuleResource =
				builder.user(
					contextUser
				).build();

			for (ObjectValidationRule objectValidationRule :
					objectValidationRules) {

				objectValidationRuleResource.
					postObjectDefinitionObjectValidationRule(
						objectDefinitionId, objectValidationRule);
			}
		}

		if (objectViews != null) {
			ObjectViewResource.Builder builder =
				_objectViewResourceFactory.create();

			ObjectViewResource objectViewResource = builder.user(
				contextUser
			).build();

			for (ObjectView objectView : objectViews) {
				objectViewResource.postObjectDefinitionObjectView(
					objectDefinitionId, objectView);
			}
		}
	}

	private Set<String> _getAccountEntryRestrictedObjectRelationshipsNames(
		com.liferay.object.model.ObjectDefinition objectDefinition1,
		ObjectRelationship[] objectRelationships) {

		if ((objectDefinition1 == null) ||
			!objectDefinition1.isUnmodifiableSystemObject() ||
			(objectRelationships == null) ||
			!StringUtil.equals(
				objectDefinition1.getClassName(),
				AccountEntry.class.getName())) {

			return Collections.emptySet();
		}

		Set<String> accountEntryRestrictedObjectRelationshipsNames =
			new HashSet<>();

		for (ObjectRelationship objectRelationship : objectRelationships) {
			if (!StringUtil.equals(
					objectRelationship.getTypeAsString(),
					ObjectRelationshipConstants.TYPE_ONE_TO_MANY)) {

				continue;
			}

			com.liferay.object.model.ObjectDefinition objectDefinition2 =
				_objectDefinitionLocalService.fetchObjectDefinition(
					objectRelationship.getObjectDefinitionId2());

			if ((objectDefinition2 == null) ||
				!objectDefinition2.getAccountEntryRestricted()) {

				continue;
			}

			com.liferay.object.model.ObjectRelationship
				serviceBuilderObjectRelationship =
					_objectRelationshipLocalService.
						fetchObjectRelationshipByObjectDefinitionId1(
							objectDefinition1.getObjectDefinitionId(),
							objectRelationship.getName());

			if (serviceBuilderObjectRelationship == null) {
				continue;
			}

			if (serviceBuilderObjectRelationship.getObjectFieldId2() ==
					objectDefinition2.
						getAccountEntryRestrictedObjectFieldId()) {

				accountEntryRestrictedObjectRelationshipsNames.add(
					objectRelationship.getName());
			}
		}

		return accountEntryRestrictedObjectRelationshipsNames;
	}

	private long _getObjectFolderId(String objectFolderExternalReferenceCode)
		throws Exception {

		if (Validator.isNull(objectFolderExternalReferenceCode)) {
			return 0;
		}

		ObjectFolder objectFolder =
			_objectFolderLocalService.getObjectFolderByExternalReferenceCode(
				objectFolderExternalReferenceCode,
				contextCompany.getCompanyId());

		return objectFolder.getObjectFolderId();
	}

	private ObjectDefinition _toObjectDefinition(
		com.liferay.object.model.ObjectDefinition objectDefinition) {

		if (objectDefinition == null) {
			return null;
		}

		String permissionName =
			com.liferay.object.model.ObjectDefinition.class.getName();

		String restContextPath = StringPool.BLANK;

		if (objectDefinition.isUnmodifiableSystemObject()) {
			SystemObjectDefinitionManager systemObjectDefinitionManager =
				_systemObjectDefinitionManagerRegistry.
					getSystemObjectDefinitionManager(
						objectDefinition.getName());

			if (systemObjectDefinitionManager != null) {
				JaxRsApplicationDescriptor jaxRsApplicationDescriptor =
					systemObjectDefinitionManager.
						getJaxRsApplicationDescriptor();

				restContextPath =
					"/o/" + jaxRsApplicationDescriptor.getRESTContextPath();
			}
		}
		else {
			restContextPath = "/o" + objectDefinition.getRESTContextPath();
		}

		String finalRESTContextPath = restContextPath;

		return new ObjectDefinition() {
			{
				accountEntryRestricted =
					objectDefinition.isAccountEntryRestricted();
				actions = HashMapBuilder.put(
					"delete",
					() -> {
						if (objectDefinition.isUnmodifiableSystemObject()) {
							return null;
						}

						return addAction(
							ActionKeys.DELETE, "deleteObjectDefinition",
							permissionName,
							objectDefinition.getObjectDefinitionId());
					}
				).put(
					"get",
					addAction(
						ActionKeys.VIEW, "getObjectDefinition", permissionName,
						objectDefinition.getObjectDefinitionId())
				).put(
					"permissions",
					addAction(
						ActionKeys.PERMISSIONS, "patchObjectDefinition",
						permissionName,
						objectDefinition.getObjectDefinitionId())
				).put(
					"publish",
					() -> {
						if (objectDefinition.isApproved()) {
							return null;
						}

						return addAction(
							ActionKeys.UPDATE, "postObjectDefinitionPublish",
							permissionName,
							objectDefinition.getObjectDefinitionId());
					}
				).put(
					"update",
					() -> {
						if (!FeatureFlagManagerUtil.isEnabled("LPS-148856") &&
							objectDefinition.isUnmodifiableSystemObject()) {

							return null;
						}

						return addAction(
							ActionKeys.UPDATE, "putObjectDefinition",
							permissionName,
							objectDefinition.getObjectDefinitionId());
					}
				).build();
				active = objectDefinition.isActive();
				dateCreated = objectDefinition.getCreateDate();
				dateModified = objectDefinition.getModifiedDate();
				defaultLanguageId = _localization.getDefaultLanguageId(
					objectDefinition.getLabel());
				enableCategorization =
					objectDefinition.getEnableCategorization();
				enableComments = objectDefinition.getEnableComments();

				if (FeatureFlagManagerUtil.isEnabled("LPS-172017")) {
					enableLocalization =
						objectDefinition.getEnableLocalization();
				}

				enableObjectEntryHistory =
					objectDefinition.getEnableObjectEntryHistory();
				externalReferenceCode =
					objectDefinition.getExternalReferenceCode();
				id = objectDefinition.getObjectDefinitionId();
				label = LocalizedMapUtil.getLanguageIdMap(
					objectDefinition.getLabelMap());

				if (FeatureFlagManagerUtil.isEnabled("LPS-167253")) {
					modifiable = objectDefinition.getModifiable();
				}

				name = objectDefinition.getShortName();
				objectActions = transformToArray(
					_objectActionLocalService.getObjectActions(
						objectDefinition.getObjectDefinitionId()),
					objectAction -> ObjectActionUtil.toObjectAction(
						null, contextAcceptLanguage.getPreferredLocale(),
						_notificationTemplateLocalService,
						_objectDefinitionLocalService, objectAction),
					ObjectAction.class);
				objectFields = transformToArray(
					_objectFieldLocalService.getObjectFields(
						objectDefinition.getObjectDefinitionId()),
					objectField -> _objectFieldDTOConverter.toDTO(
						new DefaultDTOConverterContext(
							false, null, null, null,
							contextAcceptLanguage.getPreferredLocale(), null,
							null),
						objectField),
					ObjectField.class);

				if (FeatureFlagManagerUtil.isEnabled("LPS-148856")) {
					objectFolderExternalReferenceCode =
						objectDefinition.getObjectFolderExternalReferenceCode();
				}

				objectLayouts = transformToArray(
					_objectLayoutLocalService.getObjectLayouts(
						objectDefinition.getObjectDefinitionId()),
					objectLayout -> ObjectLayoutUtil.toObjectLayout(
						null, _objectDefinitionLocalService,
						_objectFieldLocalService, objectLayout),
					ObjectLayout.class);
				objectRelationships = transformToArray(
					_objectRelationshipLocalService.getObjectRelationships(
						objectDefinition.getObjectDefinitionId(),
						QueryUtil.ALL_POS, QueryUtil.ALL_POS),
					objectRelationship -> _objectRelationshipDTOConverter.toDTO(
						new DefaultDTOConverterContext(
							false, null, null, null,
							contextAcceptLanguage.getPreferredLocale(), null,
							null),
						objectRelationship),
					ObjectRelationship.class);
				objectValidationRules = transformToArray(
					_objectValidationRuleLocalService.getObjectValidationRules(
						objectDefinition.getObjectDefinitionId()),
					objectValidationRule ->
						_objectValidationRuleDTOConverter.toDTO(
							new DefaultDTOConverterContext(
								false, null, null, null,
								contextAcceptLanguage.getPreferredLocale(),
								null, null),
							objectValidationRule),
					ObjectValidationRule.class);
				objectViews = transformToArray(
					_objectViewLocalService.getObjectViews(
						objectDefinition.getObjectDefinitionId()),
					objectView -> _objectViewDTOConverter.toDTO(
						new DefaultDTOConverterContext(
							false, null, null, null,
							contextAcceptLanguage.getPreferredLocale(), null,
							null),
						objectView),
					ObjectView.class);
				panelCategoryKey = objectDefinition.getPanelCategoryKey();
				parameterRequired = finalRESTContextPath.matches(
					".*/\\{\\w+}/.*");
				pluralLabel = LocalizedMapUtil.getLanguageIdMap(
					objectDefinition.getPluralLabelMap());
				portlet = objectDefinition.getPortlet();
				restContextPath = finalRESTContextPath;
				scope = objectDefinition.getScope();
				status = new Status() {
					{
						code = objectDefinition.getStatus();
						label = WorkflowConstants.getStatusLabel(
							objectDefinition.getStatus());
						label_i18n = _language.get(
							LanguageResources.getResourceBundle(
								contextAcceptLanguage.getPreferredLocale()),
							WorkflowConstants.getStatusLabel(
								objectDefinition.getStatus()));
					}
				};

				if (FeatureFlagManagerUtil.isEnabled("LPS-135430")) {
					storageType = objectDefinition.getStorageType();
				}

				system = objectDefinition.isSystem();

				setAccountEntryRestrictedObjectFieldName(
					() -> {
						com.liferay.object.model.ObjectField
							serviceBuilderObjectField =
								_objectFieldLocalService.fetchObjectField(
									objectDefinition.
										getAccountEntryRestrictedObjectFieldId());

						if (serviceBuilderObjectField == null) {
							return "";
						}

						return serviceBuilderObjectField.getName();
					});
				setTitleObjectFieldName(
					() -> {
						com.liferay.object.model.ObjectField
							serviceBuilderObjectField =
								_objectFieldLocalService.fetchObjectField(
									objectDefinition.getTitleObjectFieldId());

						if (serviceBuilderObjectField == null) {
							return null;
						}

						return serviceBuilderObjectField.getName();
					});
			}
		};
	}

	private static final EntityModel _entityModel =
		new ObjectDefinitionEntityModel();

	@Reference
	private Language _language;

	@Reference
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference
	private Localization _localization;

	@Reference
	private NotificationTemplateLocalService _notificationTemplateLocalService;

	@Reference
	private ObjectActionLocalService _objectActionLocalService;

	@Reference
	private ObjectActionResource.Factory _objectActionResourceFactory;

	@Reference
	private ObjectActionService _objectActionService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectDefinitionService _objectDefinitionService;

	@Reference(target = DTOConverterConstants.OBJECT_FIELD_DTO_CONVERTER)
	private DTOConverter<com.liferay.object.model.ObjectField, ObjectField>
		_objectFieldDTOConverter;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

	@Reference
	private ObjectFilterLocalService _objectFilterLocalService;

	@Reference
	private ObjectFolderLocalService _objectFolderLocalService;

	@Reference
	private ObjectLayoutLocalService _objectLayoutLocalService;

	@Reference
	private ObjectLayoutResource.Factory _objectLayoutResourceFactory;

	@Reference(target = DTOConverterConstants.OBJECT_RELATIONSHIP_DTO_CONVERTER)
	private DTOConverter
		<com.liferay.object.model.ObjectRelationship, ObjectRelationship>
			_objectRelationshipDTOConverter;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private ObjectRelationshipResource.Factory
		_objectRelationshipResourceFactory;

	@Reference(
		target = DTOConverterConstants.OBJECT_VALIDATION_RULE_DTO_CONVERTER
	)
	private DTOConverter
		<com.liferay.object.model.ObjectValidationRule, ObjectValidationRule>
			_objectValidationRuleDTOConverter;

	@Reference
	private ObjectValidationRuleLocalService _objectValidationRuleLocalService;

	@Reference
	private ObjectValidationRuleResource.Factory
		_objectValidationRuleResourceFactory;

	@Reference(target = DTOConverterConstants.OBJECT_VIEW_DTO_CONVERTER)
	private DTOConverter<com.liferay.object.model.ObjectView, ObjectView>
		_objectViewDTOConverter;

	@Reference
	private ObjectViewLocalService _objectViewLocalService;

	@Reference
	private ObjectViewResource.Factory _objectViewResourceFactory;

	@Reference
	private ObjectViewService _objectViewService;

	@Reference
	private SystemObjectDefinitionManagerRegistry
		_systemObjectDefinitionManagerRegistry;

}