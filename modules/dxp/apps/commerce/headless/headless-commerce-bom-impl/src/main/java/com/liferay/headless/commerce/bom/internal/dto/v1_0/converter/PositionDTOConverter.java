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

package com.liferay.headless.commerce.bom.internal.dto.v1_0.converter;

import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry;
import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramEntryService;
import com.liferay.headless.commerce.bom.dto.v1_0.Position;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "model.class.name=cpDefinitionDiagramEntryPosition",
	service = {DTOConverter.class, PositionDTOConverter.class}
)
public class PositionDTOConverter
	implements DTOConverter<CPDefinitionDiagramEntry, Position> {

	@Override
	public String getContentType() {
		return Position.class.getSimpleName();
	}

	@Override
	public Position toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CPDefinitionDiagramEntry cpDefinitionDiagramEntry =
			_cpDefinitionDiagramEntryService.getCPDefinitionDiagramEntry(
				(Long)dtoConverterContext.getId());

		return new Position() {
			{
				x = cpDefinitionDiagramEntry.getPositionX();
				y = cpDefinitionDiagramEntry.getPositionY();
			}
		};
	}

	@Reference
	private CPDefinitionDiagramEntryService _cpDefinitionDiagramEntryService;

}