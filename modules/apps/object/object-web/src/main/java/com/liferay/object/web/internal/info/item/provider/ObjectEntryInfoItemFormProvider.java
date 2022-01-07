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

package com.liferay.object.web.internal.info.item.provider;

import com.liferay.info.exception.NoSuchFormVariationException;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.object.exception.NoSuchObjectDefinitionException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.web.internal.info.item.ObjectEntryInfoItemFields;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;

import java.util.Objects;

/**
 * @author Jorge Ferrer
 * @author Guilherme Camacho
 */
public class ObjectEntryInfoItemFormProvider
	implements InfoItemFormProvider<ObjectEntry> {

	public ObjectEntryInfoItemFormProvider(
		ObjectDefinition objectDefinition,
		InfoItemFieldReaderFieldSetProvider infoItemFieldReaderFieldSetProvider,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService,
		TemplateInfoItemFieldSetProvider templateInfoItemFieldSetProvider) {

		_objectDefinition = objectDefinition;
		_infoItemFieldReaderFieldSetProvider =
			infoItemFieldReaderFieldSetProvider;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectFieldLocalService = objectFieldLocalService;
		_objectRelationshipLocalService = objectRelationshipLocalService;
		_templateInfoItemFieldSetProvider = templateInfoItemFieldSetProvider;
	}

	@Override
	public InfoForm getInfoForm() {
		try {
			return _getInfoForm(0);
		}
		catch (NoSuchFormVariationException noSuchFormVariationException) {
			throw new RuntimeException(noSuchFormVariationException);
		}
	}

	@Override
	public InfoForm getInfoForm(ObjectEntry objectEntry) {
		long objectDefinitionId = objectEntry.getObjectDefinitionId();

		try {
			return _getInfoForm(objectDefinitionId);
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
	public InfoForm getInfoForm(String formVariationKey)
		throws NoSuchFormVariationException {

		return _getInfoForm(GetterUtil.getLong(formVariationKey));
	}

	@Override
	public InfoForm getInfoForm(String formVariationKey, long groupId)
		throws NoSuchFormVariationException {

		long objectDefinitionId = GetterUtil.getLong(formVariationKey);

		if (objectDefinitionId == 0) {
			objectDefinitionId = _objectDefinition.getObjectDefinitionId();
		}

		return _getInfoForm(objectDefinitionId);
	}

	private InfoFieldSet _getBasicInformationInfoFieldSet() {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			ObjectEntryInfoItemFields.createDateInfoField
		).infoFieldSetEntry(
			ObjectEntryInfoItemFields.modifiedDateInfoField
		).infoFieldSetEntry(
			ObjectEntryInfoItemFields.publishDateInfoField
		).infoFieldSetEntry(
			ObjectEntryInfoItemFields.userNameInfoField
		).infoFieldSetEntry(
			ObjectEntryInfoItemFields.userProfileImage
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

	private InfoFieldType _getInfoFieldType(ObjectField objectField) {
		if (Objects.equals(objectField.getDBType(), "Blob")) {
			return ImageInfoFieldType.INSTANCE;
		}

		return TextInfoFieldType.INSTANCE;
	}

	private InfoForm _getInfoForm(long objectDefinitionId)
		throws NoSuchFormVariationException {

		return InfoForm.builder(
		).infoFieldSetEntry(
			_getBasicInformationInfoFieldSet()
		).<NoSuchFormVariationException>infoFieldSetEntry(
			unsafeConsumer -> {
				if (objectDefinitionId != 0) {
					unsafeConsumer.accept(
						_getObjectDefinitionInfoFieldSet(objectDefinitionId));
				}
			}
		).infoFieldSetEntry(
			_getDisplayPageInfoFieldSet()
		).infoFieldSetEntry(
			_infoItemFieldReaderFieldSetProvider.getInfoFieldSet(
				ObjectEntry.class.getName())
		).labelInfoLocalizedValue(
			InfoLocalizedValue.<String>builder(
			).values(
				_objectDefinition.getLabelMap()
			).build()
		).name(
			_objectDefinition.getClassName()
		).build();
	}

	private InfoFieldSet _getObjectDefinitionInfoFieldSet(
			long objectDefinitionId)
		throws NoSuchFormVariationException {

		ObjectDefinition objectDefinition = null;

		try {
			objectDefinition =
				_objectDefinitionLocalService.getObjectDefinition(
					objectDefinitionId);
		}
		catch (NoSuchObjectDefinitionException
					noSuchObjectDefinitionException) {

			throw new NoSuchFormVariationException(
				String.valueOf(objectDefinitionId),
				noSuchObjectDefinitionException);
		}
		catch (PortalException portalException) {
			throw new RuntimeException("Unexpected exception", portalException);
		}

		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			unsafeConsumer -> {
				for (ObjectField objectField :
						_objectFieldLocalService.getObjectFields(
							objectDefinitionId)) {

					if (Validator.isNotNull(
							objectField.getRelationshipType())) {

						try {
							ObjectRelationship objectRelationship =
								_objectRelationshipLocalService.
									fetchObjectRelationshipByObjectFieldId2(
										objectField.getObjectFieldId());

							ObjectDefinition relatedObjectDefinition =
								_objectDefinitionLocalService.
									getObjectDefinition(
										objectRelationship.
											getObjectDefinitionId1());

							if (!relatedObjectDefinition.isActive()) {
								continue;
							}
						}
						catch (PortalException portalException) {
							throw new RuntimeException(
								"Unexpected exception", portalException);
						}
					}

					unsafeConsumer.accept(
						InfoField.builder(
						).infoFieldType(
							_getInfoFieldType(objectField)
						).name(
							objectField.getName()
						).labelInfoLocalizedValue(
							InfoLocalizedValue.<String>builder(
							).values(
								objectField.getLabelMap()
							).build()
						).build());
				}
			}
		).infoFieldSetEntry(
			_templateInfoItemFieldSetProvider.getInfoFieldSet(
				ObjectEntry.class.getName(), String.valueOf(objectDefinitionId))
		).labelInfoLocalizedValue(
			InfoLocalizedValue.<String>builder(
			).values(
				objectDefinition.getLabelMap()
			).build()
		).name(
			objectDefinition.getName()
		).build();
	}

	private final InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;
	private final ObjectDefinition _objectDefinition;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService;
	private final TemplateInfoItemFieldSetProvider
		_templateInfoItemFieldSetProvider;

}