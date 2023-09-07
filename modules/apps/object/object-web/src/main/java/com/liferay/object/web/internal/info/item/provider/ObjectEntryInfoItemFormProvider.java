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
import com.liferay.info.field.type.FileInfoFieldType;
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.field.type.LongTextInfoFieldType;
import com.liferay.info.field.type.MultiselectInfoFieldType;
import com.liferay.info.field.type.NumberInfoFieldType;
import com.liferay.info.field.type.OptionInfoFieldType;
import com.liferay.info.field.type.RelationshipInfoFieldType;
import com.liferay.info.field.type.SelectInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.field.type.URLInfoFieldType;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.localized.bundle.FunctionInfoLocalizedValue;
import com.liferay.layout.page.template.info.item.provider.DisplayPageInfoItemFieldSetProvider;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldValidationConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.exception.NoSuchObjectDefinitionException;
import com.liferay.object.field.setting.util.ObjectFieldSettingUtil;
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.context.path.RESTContextPathResolver;
import com.liferay.object.rest.context.path.RESTContextPathResolverRegistry;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.web.internal.configuration.util.ObjectConfigurationUtil;
import com.liferay.object.web.internal.info.item.ObjectEntryInfoItemFields;
import com.liferay.object.web.internal.util.ObjectFieldDBTypeUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

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

	private InfoField<?> _addAttributes(
		InfoField.FinalStep finalStep, ObjectField objectField) {

		if (Objects.equals(
				objectField.getBusinessType(),
				ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT)) {

			finalStep.attribute(
				FileInfoFieldType.ALLOWED_FILE_EXTENSIONS,
				_getAcceptedFileExtensions(objectField)
			).attribute(
				FileInfoFieldType.FILE_SOURCE, _getFileSourceType(objectField)
			).attribute(
				FileInfoFieldType.MAX_FILE_SIZE,
				_getMaximumFileSize(objectField)
			);
		}
		else if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_DECIMAL)) {

			finalStep.attribute(
				NumberInfoFieldType.DECIMAL, true
			).attribute(
				NumberInfoFieldType.DECIMAL_PART_MAX_LENGTH, 16
			);
		}
		else if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_INTEGER)) {

			finalStep.attribute(
				NumberInfoFieldType.MAX_VALUE,
				BigDecimal.valueOf(
					ObjectFieldValidationConstants.
						BUSINESS_TYPE_INTEGER_VALUE_MAX)
			).attribute(
				NumberInfoFieldType.MIN_VALUE,
				BigDecimal.valueOf(
					ObjectFieldValidationConstants.
						BUSINESS_TYPE_INTEGER_VALUE_MIN)
			);
		}
		else if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER)) {

			finalStep.attribute(
				NumberInfoFieldType.MAX_VALUE,
				BigDecimal.valueOf(
					ObjectFieldValidationConstants.BUSINESS_TYPE_LONG_VALUE_MAX)
			).attribute(
				NumberInfoFieldType.MIN_VALUE,
				BigDecimal.valueOf(
					ObjectFieldValidationConstants.BUSINESS_TYPE_LONG_VALUE_MIN)
			);
		}
		else if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_LONG_TEXT)) {

			finalStep.attribute(
				LongTextInfoFieldType.MAX_LENGTH,
				_getMaxLength(objectField, 65000));
		}
		else if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST)) {

			finalStep.attribute(
				MultiselectInfoFieldType.OPTIONS,
				TransformUtil.transform(
					_listTypeEntryLocalService.getListTypeEntries(
						objectField.getListTypeDefinitionId()),
					listTypeEntry -> new OptionInfoFieldType(
						Objects.equals(
							ObjectFieldSettingUtil.getDefaultValueAsString(
								null, objectField.getObjectFieldId(),
								_objectFieldSettingLocalService, null),
							listTypeEntry.getKey()),
						new FunctionInfoLocalizedValue<>(
							listTypeEntry::getName),
						listTypeEntry.getKey())));
		}
		else if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_PICKLIST)) {

			if (objectField.compareBusinessType(
					ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST)) {

				finalStep.attribute(
					MultiselectInfoFieldType.OPTIONS,
					_getOptionInfoFieldTypes(objectField));
			}
			else {
				finalStep.attribute(
					SelectInfoFieldType.OPTIONS,
					_getOptionInfoFieldTypes(objectField));
			}
		}
		else if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_PRECISION_DECIMAL)) {

			finalStep.attribute(
				NumberInfoFieldType.DECIMAL, true
			).attribute(
				NumberInfoFieldType.DECIMAL_PART_MAX_LENGTH, 16
			).attribute(
				NumberInfoFieldType.MAX_VALUE,
				new BigDecimal(
					ObjectFieldValidationConstants.
						BUSINESS_TYPE_PRECISION_DECIMAL_VALUE_MAX)
			).attribute(
				NumberInfoFieldType.MIN_VALUE,
				new BigDecimal(
					ObjectFieldValidationConstants.
						BUSINESS_TYPE_PRECISION_DECIMAL_VALUE_MIN)
			);
		}
		else if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_RELATIONSHIP)) {

			finalStep.attribute(
				RelationshipInfoFieldType.LABEL_FIELD_NAME,
				_getRelationshipLabelFieldName(objectField)
			).attribute(
				RelationshipInfoFieldType.URL, _getRelationshipURL(objectField)
			).attribute(
				RelationshipInfoFieldType.VALUE_FIELD_NAME, "id"
			);
		}
		else if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_TEXT)) {

			finalStep.attribute(
				TextInfoFieldType.MAX_LENGTH, _getMaxLength(objectField, 280));
		}

		return finalStep.build();
	}

	private String _getAcceptedFileExtensions(ObjectField objectField) {
		ObjectFieldSetting acceptedFileExtensionsObjectFieldSetting =
			_objectFieldSettingLocalService.fetchObjectFieldSetting(
				objectField.getObjectFieldId(), "acceptedFileExtensions");

		if (acceptedFileExtensionsObjectFieldSetting == null) {
			return StringPool.BLANK;
		}

		return acceptedFileExtensionsObjectFieldSetting.getValue();
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

	private InfoFieldSet _getDisplayPageInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			ObjectEntryInfoItemFields.displayPageURLInfoField
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "configuration")
		).name(
			"configuration"
		).build();
	}

	private FileInfoFieldType.FileSourceType _getFileSourceType(
		ObjectField objectField) {

		ObjectFieldSetting objectFieldSetting =
			_objectFieldSettingLocalService.fetchObjectFieldSetting(
				objectField.getObjectFieldId(), "fileSource");

		if (objectFieldSetting == null) {
			return null;
		}

		if (Objects.equals(
				objectFieldSetting.getValue(), "documentsAndMedia")) {

			return FileInfoFieldType.FileSourceType.DOCUMENTS_AND_MEDIA;
		}
		else if (Objects.equals(
					objectFieldSetting.getValue(), "userComputer")) {

			return FileInfoFieldType.FileSourceType.USER_COMPUTER;
		}

		return null;
	}

	private long _getGroupId(
		HttpServletRequest httpServletRequest,
		ObjectDefinition objectDefinition) {

		try {
			ObjectScopeProvider objectScopeProvider =
				_objectScopeProviderRegistry.getObjectScopeProvider(
					objectDefinition.getScope());

			return objectScopeProvider.getGroupId(httpServletRequest);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return 0L;
		}
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
			unsafeConsumer -> {
				if (!FeatureFlagManagerUtil.isEnabled("LPS-195205")) {
					unsafeConsumer.accept(_getDisplayPageInfoFieldSet());
				}
			}
		).infoFieldSetEntry(
			unsafeConsumer -> {
				if (FeatureFlagManagerUtil.isEnabled("LPS-195205")) {
					unsafeConsumer.accept(displayPageInfoFieldSet);
				}
			}
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

	private long _getMaximumFileSize(ObjectField objectField) {
		ObjectFieldSetting objectFieldSetting =
			_objectFieldSettingLocalService.fetchObjectFieldSetting(
				objectField.getObjectFieldId(), "maximumFileSize");

		long maximumFileSizeForGuestUsers =
			ObjectConfigurationUtil.maximumFileSizeForGuestUsers();

		if (objectFieldSetting == null) {
			return maximumFileSizeForGuestUsers;
		}

		long maximumFileSize = GetterUtil.getLong(
			objectFieldSetting.getValue());

		if ((maximumFileSizeForGuestUsers < maximumFileSize) &&
			_isGuestUser()) {

			maximumFileSize = maximumFileSizeForGuestUsers;
		}

		return maximumFileSize;
	}

	private long _getMaxLength(ObjectField objectField, long defaultMaxLength) {
		ObjectFieldSetting objectFieldSetting =
			_objectFieldSettingLocalService.fetchObjectFieldSetting(
				objectField.getObjectFieldId(), "maxLength");

		if (objectFieldSetting == null) {
			return defaultMaxLength;
		}

		return GetterUtil.getLong(
			objectFieldSetting.getValue(), defaultMaxLength);
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

					if (objectField.isLocalized()) {
						continue;
					}

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
						_addAttributes(
							InfoField.builder(
							).infoFieldType(
								ObjectFieldDBTypeUtil.getInfoFieldType(
									objectField)
							).namespace(
								namespace
							).name(
								objectField.getName()
							).editable(
								editable
							).labelInfoLocalizedValue(
								InfoLocalizedValue.<String>builder(
								).defaultLocale(
									LocaleUtil.fromLanguageId(
										objectField.getDefaultLanguageId())
								).values(
									objectField.getLabelMap()
								).build()
							).required(
								objectField.isRequired()
							),
							objectField));
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

	private List<OptionInfoFieldType> _getOptionInfoFieldTypes(
		ObjectField objectField) {

		return TransformUtil.transform(
			_listTypeEntryLocalService.getListTypeEntries(
				objectField.getListTypeDefinitionId()),
			listTypeEntry -> new OptionInfoFieldType(
				Objects.equals(
					ObjectFieldSettingUtil.getDefaultValueAsString(
						null, objectField.getObjectFieldId(),
						_objectFieldSettingLocalService, null),
					listTypeEntry.getKey()),
				new FunctionInfoLocalizedValue<>(listTypeEntry::getName),
				listTypeEntry.getKey()));
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

	private String _getRelationshipLabelFieldName(ObjectField objectField) {
		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.
				fetchObjectRelationshipByObjectFieldId2(
					objectField.getObjectFieldId());

		ObjectDefinition relatedObjectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				objectRelationship.getObjectDefinitionId1());

		if (relatedObjectDefinition == null) {
			return "id";
		}

		ObjectField titleObjectField =
			_objectFieldLocalService.fetchObjectField(
				relatedObjectDefinition.getTitleObjectFieldId());

		if (titleObjectField == null) {
			return "id";
		}
		else if (Objects.equals(titleObjectField.getName(), "createDate")) {
			return "dateCreated";
		}
		else if (Objects.equals(titleObjectField.getName(), "modifiedDate")) {
			return "dateModified";
		}

		return titleObjectField.getName();
	}

	private String _getRelationshipURL(ObjectField objectField) {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return StringPool.BLANK;
		}

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.
				fetchObjectRelationshipByObjectFieldId2(
					objectField.getObjectFieldId());

		ObjectDefinition relatedObjectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				objectRelationship.getObjectDefinitionId1());

		if (relatedObjectDefinition == null) {
			return StringPool.BLANK;
		}

		RESTContextPathResolver restContextPathResolver =
			_restContextPathResolverRegistry.getRESTContextPathResolver(
				relatedObjectDefinition.getClassName());

		String restContextPath = restContextPathResolver.getRESTContextPath(
			_getGroupId(serviceContext.getRequest(), relatedObjectDefinition));

		return PortalUtil.getPortalURL(serviceContext.getRequest()) +
			PortalUtil.getPathContext() + restContextPath;
	}

	private boolean _isGuestUser() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return true;
		}

		User user = _userLocalService.fetchUser(serviceContext.getUserId());

		if ((user == null) || user.isGuestUser()) {
			return true;
		}

		return false;
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