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

package com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter;

import com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramPin;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramEntryLocalService;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramPinLocalService;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Pin;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.shop.by.diagram.model.CSDiagramPin",
	service = {DTOConverter.class, PinDTOConverter.class}
)
public class PinDTOConverter implements DTOConverter<CSDiagramEntry, Pin> {

	@Override
	public String getContentType() {
		return Pin.class.getSimpleName();
	}

	@Override
	public Pin toDTO(DTOConverterContext dtoConverterContext) throws Exception {
		PinDTOConverterContext pinDTOConverterContext =
			(PinDTOConverterContext)dtoConverterContext;

		CSDiagramPin csDiagramPin = _csDiagramPinLocalService.getCSDiagramPin(
			(Long)pinDTOConverterContext.getId());

		return new Pin() {
			{
				id = csDiagramPin.getCSDiagramPinId();
				positionX = csDiagramPin.getPositionX();
				positionY = csDiagramPin.getPositionY();
				sequence = csDiagramPin.getSequence();

				setMappedProduct(
					() -> {
						CSDiagramEntry csDiagramEntry =
							_csDiagramEntryLocalService.fetchCSDiagramEntry(
								csDiagramPin.getCPDefinitionId(),
								csDiagramPin.getSequence());

						if (csDiagramEntry == null) {
							return null;
						}

						return _mappedProductDTOConverter.toDTO(
							new MappedProductDTOConverterContext(
								pinDTOConverterContext.getCommerceContext(),
								pinDTOConverterContext.getCompanyId(),
								csDiagramEntry.getCSDiagramEntryId(),
								dtoConverterContext.getLocale()));
					});
			}
		};
	}

	@Reference
	private CSDiagramEntryLocalService _csDiagramEntryLocalService;

	@Reference
	private CSDiagramPinLocalService _csDiagramPinLocalService;

	@Reference
	private MappedProductDTOConverter _mappedProductDTOConverter;

}