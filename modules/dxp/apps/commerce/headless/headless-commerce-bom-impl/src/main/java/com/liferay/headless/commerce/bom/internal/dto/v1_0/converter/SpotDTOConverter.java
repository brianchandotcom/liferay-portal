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

import com.liferay.commerce.bom.model.CommerceBOMEntry;
import com.liferay.commerce.bom.service.CommerceBOMEntryService;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceService;
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
	property = "model.class.name=com.liferay.commerce.bom.model.CommerceBOMEntry",
	service = {DTOConverter.class, SpotDTOConverter.class}
)
public class SpotDTOConverter implements DTOConverter<CommerceBOMEntry, Spot> {

	@Override
	public String getContentType() {
		return Spot.class.getSimpleName();
	}

	public Spot toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceBOMEntry commerceBOMEntry =
			_commerceBOMEntryService.getCommerceBOMEntry(
				(Long)dtoConverterContext.getId());

		CPInstance cpInstance = _cpInstanceService.fetchCProductInstance(
			commerceBOMEntry.getCProductId(),
			commerceBOMEntry.getCPInstanceUuid());

		return new Spot() {
			{
				id = commerceBOMEntry.getCommerceBOMEntryId();
				number = commerceBOMEntry.getNumber();
				position = _positionDTOConverter.toDTO(dtoConverterContext);
				productId = commerceBOMEntry.getCPInstanceUuid();

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
	private CommerceBOMEntryService _commerceBOMEntryService;

	@Reference
	private CPInstanceService _cpInstanceService;

	@Reference
	private PositionDTOConverter _positionDTOConverter;

}