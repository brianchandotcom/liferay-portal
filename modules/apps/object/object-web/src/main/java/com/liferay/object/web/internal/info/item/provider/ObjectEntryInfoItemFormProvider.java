/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.info.item.provider;

import com.liferay.info.exception.NoSuchFormVariationException;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldSetEntry;
import com.liferay.info.field.type.ActionInfoFieldType;
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.field.type.URLInfoFieldType;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.layout.page.template.info.item.provider.DisplayPageInfoItemFieldSetProvider;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.exception.NoSuchObjectDefinitionException;
import com.liferay.object.info.field.converter.ObjectFieldInfoFieldConverter;
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.context.path.RESTContextPathResolverRegistry;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.web.internal.info.item.ObjectEntryInfoItemFields;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Jorge Ferrer
 * @author Guilherme Camacho
 */
public class ObjectEntryInfoItemFormProvider
	implements InfoItemFormProvider<ObjectEntry> {

	public ObjectEntryInfoItemFormProvider(
		DisplayPageInfoItemFieldSetProvider displayPageInfoItemFieldSetProvider,
		ObjectDefinition objectDefinition,
		InfoItemFieldReaderFieldSetProvider infoItemFieldReaderFieldSetProvider,
		ListTypeEntryLocalService listTypeEntryLocalService,
		ObjectActionLocalService objectActionLocalService,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectFieldInfoFieldConverter objectFieldInfoFieldConverter,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectFieldSettingLocalService objectFieldSettingLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService,
		ObjectScopeProviderRegistry objectScopeProviderRegistry,
		RESTContextPathResolverRegistry restContextPathResolverRegistry,
		TemplateInfoItemFieldSetProvider templateInfoItemFieldSetProvider,
		UserLocalService userLocalService) {

		_displayPageInfoItemFieldSetProvider =
			displayPageInfoItemFieldSetProvider;
		_objectDefinition = objectDefinition;
		_infoItemFieldReaderFieldSetProvider =
			infoItemFieldReaderFieldSetProvider;
		_listTypeEntryLocalService = listTypeEntryLocalService;
		_objectActionLocalService = objectActionLocalService;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectFieldInfoFieldConverter = objectFieldInfoFieldConverter;
		_objectFieldLocalService = objectFieldLocalService;
		_objectFieldSettingLocalService = objectFieldSettingLocalService;
		_objectRelationshipLocalService = objectRelationshipLocalService;
		_objectScopeProviderRegistry = objectScopeProviderRegistry;
		_restContextPathResolverRegistry = restContextPathResolverRegistry;
		_templateInfoItemFieldSetProvider = templateInfoItemFieldSetProvider;
		_userLocalService = userLocalService;
	}

	@Override
	public InfoForm getInfoForm() {
		try {
			return _getInfoForm(
				0,
				_displayPageInfoItemFieldSetProvider.getInfoFieldSet(
					_getModelClassName(0), StringPool.BLANK,
					ObjectEntry.class.getSimpleName(), 0));
		}
		catch (NoSuchFormVariationException noSuchFormVariationException) {
			throw new RuntimeException(noSuchFormVariationException);
		}
	}

	@Override
	public InfoForm getInfoForm(ObjectEntry objectEntry) {
		long objectDefinitionId = _objectDefinition.getObjectDefinitionId();

		try {
			return _getInfoForm(
				objectDefinitionId,
				_displayPageInfoItemFieldSetProvider.getInfoFieldSet(
					_getModelClassName(objectDefinitionId), StringPool.BLANK,
					ObjectEntry.class.getSimpleName(), 0));
		}
		catch (PortalException portalException) {
			throw new RuntimeException(
				StringBundler.concat(
					"Unable to get object definition ", objectDefinitionId,
					" for object entry ", objectEntry.getObjectEntryId()),
				portalException);
		}
	}

	@Override
	public InfoForm getInfoForm(String formVariationKey, long groupId)
		throws NoSuchFormVariationException {

		long objectDefinitionId = GetterUtil.getLong(formVariationKey);

		if (objectDefinitionId == 0) {
			objectDefinitionId = _objectDefinition.getObjectDefinitionId();
		}

		return _getInfoForm(
			objectDefinitionId,
			_displayPageInfoItemFieldSetProvider.getInfoFieldSet(
				_getModelClassName(objectDefinitionId), StringPool.BLANK,
				ObjectEntry.class.getSimpleName(), groupId));
	}

	private List<InfoFieldSetEntry>
		_getAttachmentObjectDefinitionInfoFieldSetEntries(
			long objectDefinitionId) {

		List<InfoFieldSetEntry> infoFieldSetEntries = new ArrayList<>();

		for (ObjectField objectField :
				_objectFieldLocalService.getObjectFields(
					objectDefinitionId, false)) {

			if (!Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT)) {

				continue;
			}

			infoFieldSetEntries.add(
				InfoFieldSet.builder(
				).infoFieldSetEntry(
					InfoField.builder(
					).infoFieldType(
						URLInfoFieldType.INSTANCE
					).namespace(
						ObjectField.class.getSimpleName()
					).name(
						objectField.getObjectFieldId() + "#downloadURL"
					).labelInfoLocalizedValue(
						InfoLocalizedValue.localize(
							ObjectEntryInfoItemFields.class, "download-url")
					).build()
				).infoFieldSetEntry(
					InfoField.builder(
					).infoFieldType(
						TextInfoFieldType.INSTANCE
					).namespace(
						ObjectField.class.getSimpleName()
					).name(
						objectField.getObjectFieldId() + "#fileName"
					).labelInfoLocalizedValue(
						InfoLocalizedValue.localize(
							ObjectEntryInfoItemFields.class, "file-name")
					).build()
				).infoFieldSetEntry(
					InfoField.builder(
					).infoFieldType(
						TextInfoFieldType.INSTANCE
					).namespace(
						ObjectField.class.getSimpleName()
					).name(
						objectField.getObjectFieldId() + "#mimeType"
					).labelInfoLocalizedValue(
						InfoLocalizedValue.localize(
							ObjectEntryInfoItemFields.class, "mime-type")
					).build()
				).infoFieldSetEntry(
					InfoField.builder(
					).infoFieldType(
						ImageInfoFieldType.INSTANCE
					).namespace(
						ObjectField.class.getSimpleName()
					).name(
						objectField.getObjectFieldId() + "#previewURL"
					).labelInfoLocalizedValue(
						InfoLocalizedValue.localize(
							ObjectEntryInfoItemFields.class, "preview-url")
					).build()
				).infoFieldSetEntry(
					InfoField.builder(
					).infoFieldType(
						TextInfoFieldType.INSTANCE
					).namespace(
						ObjectField.class.getSimpleName()
					).name(
						objectField.getObjectFieldId() + "#size"
					).labelInfoLocalizedValue(
						InfoLocalizedValue.localize(
							ObjectEntryInfoItemFields.class, "size")
					).build()
				).labelInfoLocalizedValue(
					InfoLocalizedValue.<String>builder(
					).defaultLocale(
						LocaleUtil.fromLanguageId(
							objectField.getDefaultLanguageId())
					).values(
						objectField.getLabelMap()
					).build()
				).name(
					objectField.getName()
				).build());
		}

		return infoFieldSetEntries;
	}

	private InfoFieldSet _getBasicInformationInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			ObjectEntryInfoItemFields.authorInfoField
		).infoFieldSetEntry(
			ObjectEntryInfoItemFields.createDateInfoField
		).infoFieldSetEntry(
			ObjectEntryInfoItemFields.externalReferenceCodeInfoField
		).infoFieldSetEntry(
			ObjectEntryInfoItemFields.modifiedDateInfoField
		).infoFieldSetEntry(
			ObjectEntryInfoItemFields.objectEntryIdInfoField
		).infoFieldSetEntry(
			ObjectEntryInfoItemFields.publishDateInfoField
		).infoFieldSetEntry(
			ObjectEntryInfoItemFields.statusInfoField
		).infoFieldSetEntry(
			ObjectEntryInfoItemFields.userProfileImageInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "basic-information")
		).name(
			"basic-information"
		).build();
	}

	private InfoForm _getInfoForm(
			long objectDefinitionId, InfoFieldSet displayPageInfoFieldSet)
		throws NoSuchFormVariationException {

		String modelClassName = _getModelClassName(objectDefinitionId);

		return InfoForm.builder(
		).infoFieldSetEntry(
			_getBasicInformationInfoFieldSet()
		).<NoSuchFormVariationException>infoFieldSetEntry(
			unsafeConsumer -> {
				if (objectDefinitionId != 0) {
					ObjectDefinition objectDefinition =
						_objectDefinitionLocalService.fetchObjectDefinition(
							objectDefinitionId);

					if (objectDefinition == null) {
						throw new NoSuchFormVariationException(
							String.valueOf(objectDefinitionId),
							new NoSuchObjectDefinitionException());
					}

					unsafeConsumer.accept(
						_getObjectDefinitionInfoFieldSet(
							true, objectDefinition.getLabelMap(),
							objectDefinition.getName(),
							ObjectField.class.getSimpleName(),
							objectDefinition));
				}
			}
		).infoFieldSetEntries(
			_getAttachmentObjectDefinitionInfoFieldSetEntries(
				objectDefinitionId)
		).infoFieldSetEntries(
			_getParentsInfoFieldSets(objectDefinitionId)
		).infoFieldSetEntry(
			_templateInfoItemFieldSetProvider.getInfoFieldSet(modelClassName)
		).infoFieldSetEntry(
			displayPageInfoFieldSet
		).infoFieldSetEntry(
			_infoItemFieldReaderFieldSetProvider.getInfoFieldSet(modelClassName)
		).infoFieldSetEntries(
			_getObjectActionInfoFieldSetEntries()
		).labelInfoLocalizedValue(
			InfoLocalizedValue.<String>builder(
			).defaultLocale(
				LocaleUtil.fromLanguageId(
					_objectDefinition.getDefaultLanguageId())
			).values(
				_objectDefinition.getLabelMap()
			).build()
		).name(
			modelClassName
		).build();
	}

	private String _getModelClassName(long objectDefinitionId) {
		return ObjectDefinition.class.getName() + "#" + objectDefinitionId;
	}

	private List<InfoFieldSetEntry> _getObjectActionInfoFieldSetEntries() {
		InfoFieldSet.Builder infoFieldSetBuilder = InfoFieldSet.builder(
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				ObjectEntryInfoItemFields.class, "actions")
		).name(
			"actions"
		);

		return TransformUtil.transform(
			_objectActionLocalService.getObjectActions(
				_objectDefinition.getObjectDefinitionId(),
				ObjectActionTriggerConstants.KEY_STANDALONE),
			objectAction -> infoFieldSetBuilder.infoFieldSetEntry(
				InfoField.builder(
				).infoFieldType(
					ActionInfoFieldType.INSTANCE
				).namespace(
					ObjectAction.class.getSimpleName()
				).name(
					objectAction.getName()
				).labelInfoLocalizedValue(
					InfoLocalizedValue.<String>builder(
					).defaultLocale(
						LocaleUtil.fromLanguageId(
							objectAction.getDefaultLanguageId())
					).values(
						objectAction.getLabelMap()
					).build()
				).build()
			).build());
	}

	private InfoFieldSet _getObjectDefinitionInfoFieldSet(
		boolean editable, Map<Locale, String> labelMap, String name,
		String namespace, ObjectDefinition objectDefinition) {

		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			unsafeConsumer -> {
				for (ObjectField objectField :
						_objectFieldLocalService.getObjectFields(
							objectDefinition.getObjectDefinitionId(), false)) {

					if (Validator.isNotNull(
							objectField.getRelationshipType())) {

						ObjectRelationship objectRelationship =
							_objectRelationshipLocalService.
								fetchObjectRelationshipByObjectFieldId2(
									objectField.getObjectFieldId());

						ObjectDefinition relatedObjectDefinition =
							_objectDefinitionLocalService.fetchObjectDefinition(
								objectRelationship.getObjectDefinitionId1());

						if ((relatedObjectDefinition == null) ||
							!relatedObjectDefinition.isActive()) {

							continue;
						}
					}

					unsafeConsumer.accept(
						_objectFieldInfoFieldConverter.getInfoField(
							editable, namespace, objectField));
				}
			}
		).labelInfoLocalizedValue(
			InfoLocalizedValue.<String>builder(
			).defaultLocale(
				LocaleUtil.fromLanguageId(
					objectDefinition.getDefaultLanguageId())
			).values(
				labelMap
			).build()
		).name(
			name
		).build();
	}

	private List<InfoFieldSetEntry> _getParentsInfoFieldSets(
		long objectDefinitionId2) {

		List<InfoFieldSetEntry> infoFieldSetEntries = new ArrayList<>();

		if (objectDefinitionId2 == 0) {
			return infoFieldSetEntries;
		}

		List<ObjectRelationship> objectRelationships =
			_objectRelationshipLocalService.getObjectRelationships(
				objectDefinitionId2,
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY);

		for (ObjectRelationship objectRelationship : objectRelationships) {
			ObjectDefinition objectDefinition1 =
				_objectDefinitionLocalService.fetchObjectDefinition(
					objectRelationship.getObjectDefinitionId1());

			if (objectDefinition1 == null) {
				_log.error(
					new NoSuchObjectDefinitionException(
						String.valueOf(
							objectRelationship.getObjectDefinitionId1())));

				continue;
			}

			if (objectDefinition1.isUnmodifiableSystemObject()) {
				continue;
			}

			Map<Locale, String> fieldSetLabelMap = new HashMap<>();

			Map<Locale, String> labelMap = objectDefinition1.getLabelMap();

			for (Map.Entry<Locale, String> entry : labelMap.entrySet()) {
				Locale locale = entry.getKey();

				fieldSetLabelMap.put(
					locale,
					StringBundler.concat(
						objectRelationship.getLabel(locale), StringPool.SPACE,
						StringPool.OPEN_PARENTHESIS, entry.getValue(),
						StringPool.CLOSE_PARENTHESIS));
			}

			infoFieldSetEntries.add(
				_getObjectDefinitionInfoFieldSet(
					false, fieldSetLabelMap, objectRelationship.getName(),
					StringBundler.concat(
						ObjectRelationship.class.getSimpleName(),
						StringPool.POUND, objectDefinition1.getName(),
						StringPool.POUND, objectRelationship.getName()),
					objectDefinition1));
		}

		return infoFieldSetEntries;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryInfoItemFormProvider.class);

	private final DisplayPageInfoItemFieldSetProvider
		_displayPageInfoItemFieldSetProvider;
	private final InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;
	private final ListTypeEntryLocalService _listTypeEntryLocalService;
	private final ObjectActionLocalService _objectActionLocalService;
	private final ObjectDefinition _objectDefinition;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectFieldInfoFieldConverter _objectFieldInfoFieldConverter;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectFieldSettingLocalService
		_objectFieldSettingLocalService;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService;
	private final ObjectScopeProviderRegistry _objectScopeProviderRegistry;
	private final RESTContextPathResolverRegistry
		_restContextPathResolverRegistry;
	private final TemplateInfoItemFieldSetProvider
		_templateInfoItemFieldSetProvider;
	private final UserLocalService _userLocalService;

}