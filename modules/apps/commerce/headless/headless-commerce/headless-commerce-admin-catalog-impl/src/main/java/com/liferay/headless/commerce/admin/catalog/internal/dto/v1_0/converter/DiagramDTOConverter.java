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

package com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter;

import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramSettingService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Diagram;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting",
	service = {DiagramDTOConverter.class, DTOConverter.class}
)
public class DiagramDTOConverter
	implements DTOConverter<CSDiagramSetting, Diagram> {

	@Override
	public String getContentType() {
		return Diagram.class.getSimpleName();
	}

	@Override
	public Diagram toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CSDiagramSetting csDiagramSetting =
			_csDiagramSettingService.getCSDiagramSetting(
				(Long)dtoConverterContext.getId());

		CPDefinition cpDefinition = csDiagramSetting.getCPDefinition();

		CProduct cProduct = cpDefinition.getCProduct();

		return new Diagram() {
			{
				color = csDiagramSetting.getColor();
				id = csDiagramSetting.getCSDiagramSettingId();
				productExternalReferenceCode =
					cProduct.getExternalReferenceCode();
				productId = cProduct.getCProductId();
				radius = csDiagramSetting.getRadius();
				type = csDiagramSetting.getType();

				setImageURL(
					() -> {
						CPAttachmentFileEntry cpAttachmentFileEntry =
							csDiagramSetting.getCPAttachmentFileEntry();

						FileEntry fileEntry =
							cpAttachmentFileEntry.fetchFileEntry();

						if (fileEntry == null) {
							return null;
						}

						return DLUtil.getDownloadURL(
							fileEntry, fileEntry.getFileVersion(), null, null);
					});
			}
		};
	}

	@Reference
	private CSDiagramSettingService _csDiagramSettingService;

}