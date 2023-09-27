/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter;

import com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramPin;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramEntryLocalService;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramPinLocalService;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.MappedProduct;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Pin;
import com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter.constants.DTOConverterConstants;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.shop.by.diagram.model.CSDiagramPin",
	service = DTOConverter.class
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

	@Reference(target = DTOConverterConstants.MAPPED_PRODUCT_DTO_CONVERTER)
	private DTOConverter<CSDiagramEntry, MappedProduct>
		_mappedProductDTOConverter;

}