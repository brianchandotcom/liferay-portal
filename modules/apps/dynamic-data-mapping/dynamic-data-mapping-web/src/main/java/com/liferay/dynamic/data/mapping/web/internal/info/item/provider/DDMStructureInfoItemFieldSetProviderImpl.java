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

package com.liferay.dynamic.data.mapping.web.internal.info.item.provider;

import com.liferay.dynamic.data.mapping.info.item.provider.DDMStructureInfoItemFieldSetProvider;
import com.liferay.dynamic.data.mapping.kernel.DDMFormField;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManagerUtil;
import com.liferay.dynamic.data.mapping.kernel.LocalizedValue;
import com.liferay.dynamic.data.mapping.kernel.NoSuchStructureException;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 * @author Jorge Ferrer
 */
@Component(service = DDMStructureInfoItemFieldSetProvider.class)
public class DDMStructureInfoItemFieldSetProviderImpl
	implements DDMStructureInfoItemFieldSetProvider {

	@Override
	public InfoFieldSet getInfoItemFieldSet(long ddmStructureId)
		throws NoSuchStructureException {

		try {
			DDMStructure ddmStructure = DDMStructureManagerUtil.getStructure(
				ddmStructureId);

			InfoFieldSet infoFieldSet = new InfoFieldSet(
				InfoLocalizedValue.builder(
				).addValues(
					ddmStructure.getNameMap()
				).build(),
				ddmStructure.getStructureKey());

			List<DDMFormField> ddmFormFields = ddmStructure.getDDMFormFields(
				false);

			for (DDMFormField ddmFormField : ddmFormFields) {
				if (Validator.isNull(ddmFormField.getIndexType()) ||
					!ArrayUtil.contains(
						_SELECTABLE_DDM_STRUCTURE_FIELDS,
						ddmFormField.getType())) {

					continue;
				}

				LocalizedValue label = ddmFormField.getLabel();

				InfoLocalizedValue labelInfoLocalizedValue =
					InfoLocalizedValue.builder(
					).addValues(
						label.getValues()
					).defaultLocale(
						label.getDefaultLocale()
					).build();

				InfoFieldType itemFieldType = TextInfoFieldType.INSTANCE;

				infoFieldSet.add(
					new InfoField(
						itemFieldType, labelInfoLocalizedValue,
						ddmFormField.getName()));
			}

			return infoFieldSet;
		}
		catch (NoSuchStructureException noSuchStructureException) {
			throw noSuchStructureException;
		}
		catch (PortalException portalException) {
			throw new RuntimeException(
				"Caught unexpected exception", portalException);
		}
	}

	private static final String[] _SELECTABLE_DDM_STRUCTURE_FIELDS = {
		"checkbox", "ddm-date", "ddm-decimal", "ddm-image", "ddm-integer",
		"ddm-number", "ddm-text-html", "radio", "select", "text", "textarea"
	};

}