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

package com.liferay.headless.commerce.admin.site.setting.internal.dto.v1_0.converter;

import com.liferay.commerce.product.constants.CPMeasurementUnitConstants;
import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.commerce.product.service.CPMeasurementUnitService;
import com.liferay.headless.commerce.admin.site.setting.dto.v1_0.MeasurementUnit;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Crescenzo Rega
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.product.model.CPMeasurementUnit",
	service = {DTOConverter.class, MeasurementUnitDTOConverter.class}
)
public class MeasurementUnitDTOConverter
	implements DTOConverter<CPMeasurementUnit, MeasurementUnit> {

	public String getContentType() {
		return MeasurementUnit.class.getSimpleName();
	}

	@Override
	public MeasurementUnit toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CPMeasurementUnit cpMeasurementUnit =
			_cpMeasurementUnitService.getCPMeasurementUnit(
				(Long)dtoConverterContext.getId());

		return new MeasurementUnit() {
			{
				companyId = cpMeasurementUnit.getCompanyId();
				externalReferenceCode =
					cpMeasurementUnit.getExternalReferenceCode();
				id = cpMeasurementUnit.getCPMeasurementUnitId();
				key = cpMeasurementUnit.getKey();
				name = LanguageUtils.getLanguageIdMap(
					cpMeasurementUnit.getNameMap());
				primary = cpMeasurementUnit.isPrimary();
				priority = cpMeasurementUnit.getPriority();
				rate = cpMeasurementUnit.getRate();
				type = _language.get(
					dtoConverterContext.getLocale(),
					StringUtil.toLowerCase(
						CPMeasurementUnitConstants.typesMap.get(
							cpMeasurementUnit.getType())));
			}
		};
	}

	@Reference
	private CPMeasurementUnitService _cpMeasurementUnitService;

	@Reference
	private Language _language;

}