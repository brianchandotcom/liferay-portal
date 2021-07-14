/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.headless.commerce.shop.by.diagram.internal.dto.v1_0.converter;

import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPin;
import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramEntryService;
import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramPinService;
import com.liferay.headless.commerce.shop.by.diagram.dto.v1_0.Pin;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPin",
	service = {DTOConverter.class, PinDTOConverter.class}
)
public class PinDTOConverter
	implements DTOConverter<CPDefinitionDiagramEntry, Pin> {

	@Override
	public String getContentType() {
		return Pin.class.getSimpleName();
	}

	@Override
	public Pin toDTO(DTOConverterContext dtoConverterContext) throws Exception {
		CPDefinitionDiagramPin cpDefinitionDiagramPin =
			_cpDefinitionDiagramPinService.getCPDefinitionDiagramPin(
				(Long)dtoConverterContext.getId());

		CPDefinitionDiagramEntry cpDefinitionDiagramEntry =
			_cpDefinitionDiagramEntryService.getCPDefinitionDiagramEntry(
				cpDefinitionDiagramPin.getCPDefinitionId(),
				cpDefinitionDiagramPin.getNumber());

		return new Pin() {
			{
				entry = _diagramEntryDTOConverter.toDTO(
					new DefaultDTOConverterContext(
						cpDefinitionDiagramEntry.
							getCPDefinitionDiagramEntryId(),
						dtoConverterContext.getLocale()));
				id = cpDefinitionDiagramPin.getCPDefinitionDiagramPinId();
				number = cpDefinitionDiagramPin.getNumber();
				positionX = cpDefinitionDiagramPin.getPositionX();
				positionY = cpDefinitionDiagramPin.getPositionY();
			}
		};
	}

	@Reference
	private CPDefinitionDiagramEntryService _cpDefinitionDiagramEntryService;

	@Reference
	private CPDefinitionDiagramPinService _cpDefinitionDiagramPinService;

	@Reference
	private DiagramEntryDTOConverter _diagramEntryDTOConverter;

}