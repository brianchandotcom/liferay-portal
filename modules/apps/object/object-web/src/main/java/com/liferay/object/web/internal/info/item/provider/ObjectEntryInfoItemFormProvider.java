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
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.web.internal.info.item.ObjectEntryInfoItemFields;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;

import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(
	immediate = true, property = Constants.SERVICE_RANKING + ":Integer=10",
	service = InfoItemFormProvider.class
)
public class ObjectEntryInfoItemFormProvider
	implements InfoItemFormProvider<ObjectEntry> {

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

		return _getInfoForm(GetterUtil.getLong(formVariationKey));
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
		if (Objects.equals(objectField.getType(), "Blob")) {
			return ImageInfoFieldType.INSTANCE;
		}

		return TextInfoFieldType.INSTANCE;
	}

	private InfoForm _getInfoForm(long objectDefinitionId)
		throws NoSuchFormVariationException {

		Set<Locale> availableLocales = LanguageUtil.getAvailableLocales();

		InfoLocalizedValue.Builder infoLocalizedValueBuilder =
			InfoLocalizedValue.builder();

		for (Locale locale : availableLocales) {
			infoLocalizedValueBuilder.value(
				locale,
				ResourceActionsUtil.getModelResource(
					locale, ObjectEntry.class.getName()));
		}

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
			infoLocalizedValueBuilder.build()
		).name(
			ObjectEntry.class.getName()
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

					unsafeConsumer.accept(
						InfoField.builder(
						).infoFieldType(
							_getInfoFieldType(objectField)
						).name(
							objectField.getName()
						).labelInfoLocalizedValue(
							InfoLocalizedValue.singleValue(
								objectField.getName())
						).build());
				}
			}
		).infoFieldSetEntry(
			_templateInfoItemFieldSetProvider.getInfoFieldSet(
				ObjectEntry.class.getName(), String.valueOf(objectDefinitionId))
		).labelInfoLocalizedValue(
			InfoLocalizedValue.singleValue(objectDefinition.getName())
		).name(
			objectDefinition.getName()
		).build();
	}

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private TemplateInfoItemFieldSetProvider _templateInfoItemFieldSetProvider;

}