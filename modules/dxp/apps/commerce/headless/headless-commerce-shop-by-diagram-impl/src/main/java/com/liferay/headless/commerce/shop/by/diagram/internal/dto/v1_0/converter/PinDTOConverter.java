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

import com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramPin;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramEntryService;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramPinService;
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
	property = "model.class.name=com.liferay.commerce.shop.by.diagram.model.CSDiagramPin",
	service = {DTOConverter.class, PinDTOConverter.class}
)
public class PinDTOConverter implements DTOConverter<CSDiagramEntry, Pin> {

	@Override
	public String getContentType() {
		return Pin.class.getSimpleName();
	}

	@Override
	public Pin toDTO(DTOConverterContext dtoConverterContext) throws Exception {
		CSDiagramPin csDiagramPin = _csDiagramPinService.getCSDiagramPin(
			(Long)dtoConverterContext.getId());

		return new Pin() {
			{
				id = csDiagramPin.getCSDiagramPinId();
				positionX = csDiagramPin.getPositionX();
				positionY = csDiagramPin.getPositionY();
				sequence = csDiagramPin.getSequence();

				setDiagramEntry(
					() -> {
						CSDiagramEntry csDiagramEntry =
							_csDiagramEntryService.fetchCSDiagramEntry(
								csDiagramPin.getCPDefinitionId(),
								csDiagramPin.getSequence());

						if (csDiagramEntry == null) {
							return null;
						}

						return _diagramEntryDTOConverter.toDTO(
							new DefaultDTOConverterContext(
								csDiagramEntry.getCSDiagramEntryId(),
								dtoConverterContext.getLocale()));
					});
			}
		};
	}

	@Reference
	private CSDiagramEntryService _csDiagramEntryService;

	@Reference
	private CSDiagramPinService _csDiagramPinService;

	@Reference
	private DiagramEntryDTOConverter _diagramEntryDTOConverter;

}