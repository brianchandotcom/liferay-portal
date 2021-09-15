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

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramEntryService;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.shop.by.diagram.dto.v1_0.DiagramEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry",
	service = {DiagramEntryDTOConverter.class, DTOConverter.class}
)
public class DiagramEntryDTOConverter
	implements DTOConverter<CSDiagramEntry, DiagramEntry> {

	@Override
	public String getContentType() {
		return DiagramEntry.class.getSimpleName();
	}

	@Override
	public DiagramEntry toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CSDiagramEntry csDiagramEntry =
			_csDiagramEntryService.getCSDiagramEntry(
				(Long)dtoConverterContext.getId());

		ExpandoBridge expandoBridge = csDiagramEntry.getExpandoBridge();

		return new DiagramEntry() {
			{
				diagram = csDiagramEntry.isDiagram();
				expando = expandoBridge.getAttributes();
				id = csDiagramEntry.getCSDiagramEntryId();
				productId = csDiagramEntry.getCProductId();
				quantity = csDiagramEntry.getQuantity();
				sequence = csDiagramEntry.getSequence();
				sku = csDiagramEntry.getSku();
				skuUuid = String.valueOf(csDiagramEntry.getCPInstanceId());

				setProductExternalReferenceCode(
					() -> {
						CPDefinition cpDefinition =
							_cpDefinitionService.fetchCPDefinitionByCProductId(
								csDiagramEntry.getCProductId());

						if (cpDefinition == null) {
							return StringPool.BLANK;
						}

						CProduct cProduct = cpDefinition.getCProduct();

						return cProduct.getExternalReferenceCode();
					});

				setSkuExternalReferenceCode(
					() -> {
						CPInstance cpInstance =
							_cpInstanceService.fetchCPInstance(
								GetterUtil.getLong(
									csDiagramEntry.getCPInstanceId()));

						if (cpInstance == null) {
							return StringPool.BLANK;
						}

						return cpInstance.getExternalReferenceCode();
					});
			}
		};
	}

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private CPInstanceService _cpInstanceService;

	@Reference
	private CSDiagramEntryService _csDiagramEntryService;

}