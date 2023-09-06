/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.dto.v1_0.converter;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.entry.util.ObjectEntryDTOConverterUtil;
import com.liferay.object.entry.util.ObjectEntryValuesUtil;
import com.liferay.object.field.setting.util.ObjectFieldSettingUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.related.models.ObjectRelatedModelsProvider;
import com.liferay.object.related.models.ObjectRelatedModelsProviderRegistry;
import com.liferay.object.rest.dto.v1_0.AuditEvent;
import com.liferay.object.rest.dto.v1_0.AuditFieldChange;
import com.liferay.object.rest.dto.v1_0.FileEntry;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.dto.v1_0.Status;
import com.liferay.object.rest.dto.v1_0.TaxonomyCategoryBrief;
import com.liferay.object.rest.dto.v1_0.util.CreatorUtil;
import com.liferay.object.rest.dto.v1_0.util.LinkUtil;
import com.liferay.object.rest.internal.dto.v1_0.util.TaxonomyCategoryBriefUtil;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.object.system.SystemObjectDefinitionManagerRegistry;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.security.audit.event.generators.constants.EventTypes;
import com.liferay.portal.security.audit.storage.service.AuditEventLocalService;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.extension.EntityExtensionHandler;
import com.liferay.portal.vulcan.extension.ExtensionProviderRegistry;
import com.liferay.portal.vulcan.extension.util.ExtensionUtil;
import com.liferay.portal.vulcan.fields.NestedFieldsSupplier;
import com.liferay.portal.vulcan.jaxrs.extension.ExtendedEntity;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.Serializable;

import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier de Arcos
 */
@Component(
	property = "dto.class.name=com.liferay.object.model.ObjectEntry",
	service = DTOConverter.class
)
public class ObjectEntryDTOConverter
	implements DTOConverter<com.liferay.object.model.ObjectEntry, ObjectEntry> {

	@Override
	public String getContentType() {
		return ObjectEntry.class.getSimpleName();
	}

	@Override
	public ObjectEntry toDTO(
			DTOConverterContext dtoConverterContext,
			com.liferay.object.model.ObjectEntry objectEntry)
		throws Exception {

		return _toDTO(dtoConverterContext, objectEntry);
	}

	private void _addManyToOneObjectRelationshipNames(
		Map<String, Object> map, ObjectField objectField,
		String objectFieldName, ObjectRelationship objectRelationship,
		long primaryKey, Map<String, Serializable> values) {

		String objectRelationshipERCObjectFieldName =
			ObjectFieldSettingUtil.getValue(
				ObjectFieldSettingConstants.
					NAME_OBJECT_RELATIONSHIP_ERC_OBJECT_FIELD_NAME,
				objectField);

		String relatedObjectEntryERC = GetterUtil.getString(
			values.get(objectRelationshipERCObjectFieldName));

		if (map.get(objectRelationship.getName()) == null) {
			map.put(
				objectRelationship.getName() + "ERC", relatedObjectEntryERC);
		}

		map.put(objectFieldName, primaryKey);

		map.put(objectRelationshipERCObjectFieldName, relatedObjectEntryERC);
	}

	private void _addManyToOneRelatedObjectEntries(
			DTOConverterContext dtoConverterContext, Map<String, Object> map,
			String objectFieldName, ObjectRelationship objectRelationship,
			long primaryKey)
		throws Exception {

		String relatedObjectDefinitionName = StringUtil.replaceLast(
			objectFieldName.substring(
				objectFieldName.lastIndexOf(StringPool.UNDERLINE) + 1),
			"Id", "");

		String manyToOneRelationshipName = StringUtil.removeLast(
			objectFieldName, "Id");

		AtomicReference<Serializable> relatedObjectEntryAtomicReference =
			new AtomicReference<>();

		Map<String, Serializable> nestedFieldValues =
			NestedFieldsSupplier.supply(
				nestedFieldName -> {
					if (!nestedFieldName.contains(
							relatedObjectDefinitionName) &&
						!StringUtil.equals(
							nestedFieldName, objectRelationship.getName())) {

						return null;
					}

					if (!StringUtil.equals(
							nestedFieldName, manyToOneRelationshipName) &&
						!StringUtil.equals(
							nestedFieldName, objectRelationship.getName()) &&
						!StringUtil.equals(
							nestedFieldName, relatedObjectDefinitionName) &&
						_log.isWarnEnabled()) {

						_log.warn(
							StringBundler.concat(
								"Replace the deprecated nested field \"",
								nestedFieldName, "\" with \"",
								objectRelationship.getName()));
					}

					if (relatedObjectEntryAtomicReference.get() != null) {
						return relatedObjectEntryAtomicReference.get();
					}

					ObjectDefinition objectDefinition =
						_objectDefinitionLocalService.getObjectDefinition(
							objectRelationship.getObjectDefinitionId1());

					if (objectDefinition.isUnmodifiableSystemObject()) {
						if (FeatureFlagManagerUtil.isEnabled("LPS-183882")) {
							SystemObjectDefinitionManager
								systemObjectDefinitionManager =
									_systemObjectDefinitionManagerRegistry.
										getSystemObjectDefinitionManager(
											objectDefinition.getName());

							BaseModel<?> baseModel =
								systemObjectDefinitionManager.
									getBaseModelByExternalReferenceCode(
										systemObjectDefinitionManager.
											getBaseModelExternalReferenceCode(
												primaryKey),
										objectDefinition.getCompanyId());

							Map<String, Object> values =
								ObjectEntryDTOConverterUtil.toValues(
									baseModel,
									dtoConverterContext.
										getDTOConverterRegistry(),
									objectDefinition.getName(),
									_systemObjectDefinitionManagerRegistry,
									dtoConverterContext.getUser());

							if (MapUtil.isNotEmpty(values)) {
								ObjectField objectField =
									_objectFieldLocalService.fetchObjectField(
										objectDefinition.
											getTitleObjectFieldId());

								values.put(
									objectField.getName(),
									ObjectEntryValuesUtil.getTitleFieldValue(
										objectField.getBusinessType(),
										baseModel.getModelAttributes(),
										objectField,
										dtoConverterContext.getUser(), values));
							}

							relatedObjectEntryAtomicReference.set(
								(Serializable)values);
						}
						else {
							relatedObjectEntryAtomicReference.set(
								(Serializable)
									_objectEntryLocalService.
										getSystemModelAttributes(
											objectDefinition, primaryKey));
						}
					}
					else {
						relatedObjectEntryAtomicReference.set(
							toDTO(
								_getDTOConverterContext(
									dtoConverterContext, primaryKey),
								_objectEntryLocalService.getObjectEntry(
									primaryKey)));
					}

					return relatedObjectEntryAtomicReference.get();
				});

		if (nestedFieldValues == null) {
			return;
		}

		for (Map.Entry<String, Serializable> entry :
				nestedFieldValues.entrySet()) {

			String nestedFieldName = entry.getKey();

			if (StringUtil.equals(
					nestedFieldName, objectRelationship.getName())) {

				map.put(objectRelationship.getName(), entry.getValue());
			}

			if (nestedFieldName.contains(relatedObjectDefinitionName) ||
				StringUtil.equals(
					nestedFieldName, objectRelationship.getName())) {

				map.put(manyToOneRelationshipName, entry.getValue());
			}
		}
	}

	private DTOConverterContext _getDTOConverterContext(
		DTOConverterContext dtoConverterContext, long objectEntryId) {

		UriInfo uriInfo = dtoConverterContext.getUriInfo();

		return new DefaultDTOConverterContext(
			dtoConverterContext.isAcceptAllLanguages(), null,
			dtoConverterContext.getDTOConverterRegistry(),
			dtoConverterContext.getHttpServletRequest(), objectEntryId,
			dtoConverterContext.getLocale(), uriInfo,
			dtoConverterContext.getUser());
	}

	private ListEntry _getListEntry(
		DTOConverterContext dtoConverterContext, String key,
		long listTypeDefinitionId) {

		ListTypeEntry listTypeEntry =
			_listTypeEntryLocalService.fetchListTypeEntry(
				listTypeDefinitionId, key);

		if (listTypeEntry == null) {
			return null;
		}

		return new ListEntry() {
			{
				key = listTypeEntry.getKey();
				name = listTypeEntry.getName(dtoConverterContext.getLocale());
				name_i18n = LocalizedMapUtil.getI18nMap(
					dtoConverterContext.isAcceptAllLanguages(),
					listTypeEntry.getNameMap());
			}
		};
	}

	private Map<String, Serializable> _getNestedFieldsRelatedProperties(
			DTOConverterContext dtoConverterContext, long groupId,
			ObjectDefinition objectDefinition, long primaryKey)
		throws Exception {

		return NestedFieldsSupplier.supply(
			nestedFieldName -> {
				ObjectRelationship objectRelationship =
					_objectRelationshipLocalService.
						fetchObjectRelationshipByObjectDefinitionId1(
							objectDefinition.getObjectDefinitionId(),
							nestedFieldName);

				if ((objectRelationship == null) ||
					!objectRelationship.isAllowedObjectRelationshipType(
						objectRelationship.getType())) {

					return null;
				}

				ObjectDefinition relatedObjectDefinition =
					_objectDefinitionLocalService.getObjectDefinition(
						objectRelationship.getObjectDefinitionId2());

				if (!relatedObjectDefinition.isActive()) {
					return null;
				}

				ObjectRelatedModelsProvider objectRelatedModelsProvider =
					_objectRelatedModelsProviderRegistry.
						getObjectRelatedModelsProvider(
							relatedObjectDefinition.getClassName(),
							relatedObjectDefinition.getCompanyId(),
							objectRelationship.getType());

				List<?> relatedModels =
					objectRelatedModelsProvider.getRelatedModels(
						groupId, objectRelationship.getObjectRelationshipId(),
						primaryKey, null, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

				if (relatedObjectDefinition.isUnmodifiableSystemObject()) {
					SystemObjectDefinitionManager
						systemObjectDefinitionManager =
							_systemObjectDefinitionManagerRegistry.
								getSystemObjectDefinitionManager(
									relatedObjectDefinition.getName());

					return TransformUtil.transformToArray(
						relatedModels,
						relatedModel -> _toExtendedEntity(
							(BaseModel<?>)relatedModel, dtoConverterContext,
							relatedObjectDefinition,
							systemObjectDefinitionManager),
						Object.class);
				}

				return TransformUtil.transformToArray(
					relatedModels,
					relatedModel -> {
						com.liferay.object.model.ObjectEntry objectEntry =
							(com.liferay.object.model.ObjectEntry)relatedModel;

						return _toDTO(
							_getDTOConverterContext(
								dtoConverterContext,
								objectEntry.getObjectEntryId()),
							objectEntry);
					},
					ObjectEntry.class);
			});
	}

	private ObjectDefinition _getObjectDefinition(
			DTOConverterContext dtoConverterContext,
			com.liferay.object.model.ObjectEntry objectEntry)
		throws Exception {

		ObjectDefinition objectDefinition =
			(ObjectDefinition)dtoConverterContext.getAttribute(
				"objectDefinition");

		if (objectDefinition == null) {
			objectDefinition =
				_objectDefinitionLocalService.getObjectDefinition(
					objectEntry.getObjectDefinitionId());
		}

		return objectDefinition;
	}

	private String _getScopeKey(
		ObjectDefinition objectDefinition,
		com.liferay.object.model.ObjectEntry objectEntry) {

		ObjectScopeProvider objectScopeProvider =
			_objectScopeProviderRegistry.getObjectScopeProvider(
				objectDefinition.getScope());

		if (objectScopeProvider.isGroupAware()) {
			Group group = _groupLocalService.fetchGroup(
				objectEntry.getGroupId());

			if (group == null) {
				return null;
			}

			return group.getGroupKey();
		}

		return null;
	}

	private AuditEvent[] _toAuditEvents(
			DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition,
			com.liferay.object.model.ObjectEntry objectEntry)
		throws Exception {

		return NestedFieldsSupplier.supply(
			"auditEvents",
			nestedFieldNames -> {
				if (!objectDefinition.isEnableObjectEntryHistory() ||
					!_objectEntryService.hasModelResourcePermission(
						objectDefinition.getObjectDefinitionId(),
						objectEntry.getObjectEntryId(),
						ObjectActionKeys.OBJECT_ENTRY_HISTORY)) {

					return null;
				}

				return TransformUtil.transformToArray(
					_auditEventLocalService.getAuditEvents(
						0, 0, 0, null, null, null, null, null,
						String.valueOf(objectEntry.getObjectEntryId()), null,
						null, null, 0, null, false, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS),
					auditEvent -> {
						AuditEvent newAuditEvent = new AuditEvent();

						newAuditEvent.setAuditFieldChanges(
							_toAuditFieldChanges(
								auditEvent.getAdditionalInfo(),
								auditEvent.getEventType()));
						newAuditEvent.setCreator(
							CreatorUtil.toCreator(
								_portal, dtoConverterContext.getUriInfo(),
								_userLocalService.fetchUser(
									auditEvent.getUserId())));
						newAuditEvent.setDateCreated(
							auditEvent.getCreateDate());
						newAuditEvent.setEventType(auditEvent.getEventType());

						return newAuditEvent;
					},
					AuditEvent.class);
			});
	}

	private AuditFieldChange[] _toAuditFieldChanges(
			String additionalInfo, String eventType)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject(additionalInfo);

		if (StringUtil.equals(eventType, EventTypes.ADD)) {
			Map<String, Object> map = jsonObject.toMap();

			return TransformUtil.transformToArray(
				map.keySet(),
				key -> new AuditFieldChange() {
					{
						name = key;
						newValue = map.get(key);
					}
				},
				AuditFieldChange.class);
		}

		return JSONUtil.toArray(
			jsonObject.getJSONArray("attributes"),
			attributeJSONObject -> new AuditFieldChange() {
				{
					name = attributeJSONObject.getString("name");
					newValue = attributeJSONObject.get("newValue");
					oldValue = attributeJSONObject.get("oldValue");
				}
			},
			AuditFieldChange.class);
	}

	private ObjectEntry _toDTO(
			DTOConverterContext dtoConverterContext,
			com.liferay.object.model.ObjectEntry objectEntry)
		throws Exception {

		ObjectDefinition objectDefinition = _getObjectDefinition(
			dtoConverterContext, objectEntry);

		return new ObjectEntry() {
			{
				actions = dtoConverterContext.getActions();
				auditEvents = _toAuditEvents(
					dtoConverterContext, objectDefinition, objectEntry);
				creator = CreatorUtil.toCreator(
					_portal, dtoConverterContext.getUriInfo(),
					_userLocalService.fetchUser(objectEntry.getUserId()));
				dateCreated = objectEntry.getCreateDate();
				dateModified = objectEntry.getModifiedDate();
				externalReferenceCode = objectEntry.getExternalReferenceCode();
				id = objectEntry.getObjectEntryId();
				properties = _toProperties(
					dtoConverterContext, objectDefinition, objectEntry);
				scopeKey = _getScopeKey(objectDefinition, objectEntry);
				status = new Status() {
					{
						code = objectEntry.getStatus();
						label = WorkflowConstants.getStatusLabel(
							objectEntry.getStatus());
						label_i18n = _language.get(
							LanguageResources.getResourceBundle(
								dtoConverterContext.getLocale()),
							WorkflowConstants.getStatusLabel(
								objectEntry.getStatus()));
					}
				};

				setKeywords(
					() -> {
						if (!objectDefinition.isEnableCategorization()) {
							return null;
						}

						return ListUtil.toArray(
							_assetTagLocalService.getTags(
								objectDefinition.getClassName(),
								objectEntry.getObjectEntryId()),
							AssetTag.NAME_ACCESSOR);
					});
				setTaxonomyCategoryBriefs(
					() -> {
						if (!objectDefinition.isEnableCategorization()) {
							return null;
						}

						return TransformUtil.transformToArray(
							_assetCategoryLocalService.getCategories(
								objectDefinition.getClassName(),
								objectEntry.getObjectEntryId()),
							assetCategory ->
								TaxonomyCategoryBriefUtil.
									toTaxonomyCategoryBrief(
										assetCategory, dtoConverterContext),
							TaxonomyCategoryBrief.class);
					});
			}
		};
	}

	private ExtendedEntity _toExtendedEntity(
			BaseModel<?> baseModel, DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition,
			SystemObjectDefinitionManager systemObjectDefinitionManager)
		throws Exception {

		DTOConverter<BaseModel<?>, ?> dtoConverter =
			ObjectEntryDTOConverterUtil.getDTOConverter(
				dtoConverterContext.getDTOConverterRegistry(),
				systemObjectDefinitionManager);

		Object dto = ObjectEntryDTOConverterUtil.toDTO(
			baseModel, dtoConverterContext.getDTOConverterRegistry(),
			systemObjectDefinitionManager, dtoConverterContext.getUser());

		Map<String, Serializable> nestedFieldsRelatedProperties = null;

		EntityExtensionHandler entityExtensionHandler =
			ExtensionUtil.getEntityExtensionHandler(
				dtoConverter.getExternalDTOClassName(),
				objectDefinition.getCompanyId(), _extensionProviderRegistry);

		if (entityExtensionHandler != null) {
			nestedFieldsRelatedProperties =
				entityExtensionHandler.getExtendedProperties(
					objectDefinition.getCompanyId(), dto);
		}

		return ExtendedEntity.extend(dto, nestedFieldsRelatedProperties, null);
	}

	private Map<String, Object> _toProperties(
			DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition,
			com.liferay.object.model.ObjectEntry objectEntry)
		throws Exception {

		Map<String, Object> map = new HashMap<>();

		Map<String, Serializable> values = objectEntry.getValues();

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(
				objectDefinition.getObjectDefinitionId());

		for (ObjectField objectField : objectFields) {
			if (objectField.isMetadata()) {
				continue;
			}

			if (FeatureFlagManagerUtil.isEnabled("LPS-172017") &&
				objectField.isLocalized()) {

				map.put(
					objectField.getI18nObjectFieldName(),
					values.get(objectField.getI18nObjectFieldName()));
			}

			String objectFieldName = objectField.getName();

			Serializable serializable = values.get(objectFieldName);

			if (objectField.compareBusinessType(
					ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT)) {

				long fileEntryId = GetterUtil.getLong(
					values.get(objectField.getName()));

				if (fileEntryId == 0) {
					continue;
				}

				FileEntry fileEntry = new FileEntry();

				DLFileEntry dlFileEntry =
					_dLFileEntryLocalService.fetchDLFileEntry(fileEntryId);

				if (dlFileEntry != null) {
					fileEntry.setId(dlFileEntry.getFileEntryId());
					fileEntry.setLink(
						LinkUtil.toLink(
							_dlAppService, dlFileEntry, _dlURLHelper,
							objectDefinition.getExternalReferenceCode(),
							objectEntry.getExternalReferenceCode(), _portal));
					fileEntry.setName(dlFileEntry.getFileName());
				}

				map.put(objectFieldName, fileEntry);
			}
			else if (objectField.compareBusinessType(
						ObjectFieldConstants.BUSINESS_TYPE_DATE_TIME)) {

				Timestamp timestamp = (Timestamp)serializable;

				if (timestamp == null) {
					continue;
				}

				String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS";

				if (StringUtil.equals(
						ObjectFieldSettingUtil.getValue(
							ObjectFieldSettingConstants.NAME_TIME_STORAGE,
							objectField),
						ObjectFieldSettingConstants.VALUE_CONVERT_TO_UTC)) {

					pattern += "'Z'";
				}

				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					pattern);

				map.put(objectFieldName, simpleDateFormat.format(timestamp));
			}
			else if (objectField.compareBusinessType(
						ObjectFieldConstants.
							BUSINESS_TYPE_MULTISELECT_PICKLIST)) {

				if (objectField.getListTypeDefinitionId() == 0) {
					continue;
				}

				map.put(
					objectFieldName,
					TransformUtil.transformToList(
						StringUtil.split(
							(String)serializable, StringPool.COMMA_AND_SPACE),
						key -> _getListEntry(
							dtoConverterContext, key,
							objectField.getListTypeDefinitionId())));
			}
			else if (objectField.compareBusinessType(
						ObjectFieldConstants.BUSINESS_TYPE_PICKLIST)) {

				if (objectField.getListTypeDefinitionId() == 0) {
					continue;
				}

				ListEntry listEntry = _getListEntry(
					dtoConverterContext, (String)serializable,
					objectField.getListTypeDefinitionId());

				if (listEntry != null) {
					map.put(objectFieldName, listEntry);
				}
			}
			else if (objectField.compareBusinessType(
						ObjectFieldConstants.BUSINESS_TYPE_RICH_TEXT)) {

				map.put(objectFieldName, serializable);
				map.put(
					objectFieldName + "RawText",
					ObjectEntryValuesUtil.getValueString(objectField, values));
			}
			else if (Objects.equals(
						objectField.getRelationshipType(),
						ObjectRelationshipConstants.TYPE_ONE_TO_MANY)) {

				long primaryKey = GetterUtil.getLong(serializable);

				ObjectRelationship objectRelationship =
					_objectRelationshipLocalService.
						fetchObjectRelationshipByObjectFieldId2(
							objectField.getObjectFieldId());

				if (primaryKey > 0) {
					_addManyToOneRelatedObjectEntries(
						dtoConverterContext, map, objectFieldName,
						objectRelationship, primaryKey);
				}

				_addManyToOneObjectRelationshipNames(
					map, objectField, objectFieldName, objectRelationship,
					primaryKey, values);
			}
			else {
				map.put(objectFieldName, serializable);
			}
		}

		values.remove(objectDefinition.getPKObjectFieldName());

		Map<String, Serializable> nestedFieldsRelatedProperties =
			_getNestedFieldsRelatedProperties(
				dtoConverterContext, objectEntry.getGroupId(), objectDefinition,
				objectEntry.getObjectEntryId());

		if (nestedFieldsRelatedProperties != null) {
			map.putAll(nestedFieldsRelatedProperties);
		}

		return map;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryDTOConverter.class);

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private AuditEventLocalService _auditEventLocalService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLFileEntryLocalService _dLFileEntryLocalService;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private ExtensionProviderRegistry _extensionProviderRegistry;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectEntryService _objectEntryService;

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

	@Reference
	private SystemObjectDefinitionManagerRegistry
		_systemObjectDefinitionManagerRegistry;

	@Reference
	private UserLocalService _userLocalService;

}