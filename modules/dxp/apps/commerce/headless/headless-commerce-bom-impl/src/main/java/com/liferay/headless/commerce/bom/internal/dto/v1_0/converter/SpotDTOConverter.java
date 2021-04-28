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

import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry;
import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramEntryService;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.bom.dto.v1_0.Spot;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry",
	service = {DTOConverter.class, SpotDTOConverter.class}
)
public class SpotDTOConverter
	implements DTOConverter<CPDefinitionDiagramEntry, Spot> {

	@Override
	public String getContentType() {
		return Spot.class.getSimpleName();
	}

	@Override
	public Spot toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CPDefinitionDiagramEntry cpDefinitionDiagramEntry =
			_cpDefinitionDiagramEntryService.getCPDefinitionDiagramEntry(
				(Long)dtoConverterContext.getId());

		ExpandoBridge expandoBridge =
			cpDefinitionDiagramEntry.getExpandoBridge();

		CPInstance cpInstance = _cpInstanceService.fetchCProductInstance(
			cpDefinitionDiagramEntry.getCProductId(),
			cpDefinitionDiagramEntry.getCPInstanceUuid());

		return new Spot() {
			{
				expando = expandoBridge.getAttributes();
				id = cpDefinitionDiagramEntry.getCPDefinitionDiagramEntryId();
				number = cpDefinitionDiagramEntry.getNumber();
				position = _positionDTOConverter.toDTO(dtoConverterContext);
				productId = cpDefinitionDiagramEntry.getCPInstanceUuid();
				quantity = cpDefinitionDiagramEntry.getQuantity();

				if (cpInstance == null) {
					sku = StringPool.BLANK;
				}
				else {
					sku = cpInstance.getSku();
				}
			}
		};
	}

	@Reference
	private CPDefinitionDiagramEntryService _cpDefinitionDiagramEntryService;

	@Reference
	private CPInstanceService _cpInstanceService;

	@Reference
	private PositionDTOConverter _positionDTOConverter;

}