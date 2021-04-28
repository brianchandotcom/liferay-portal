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

import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings;
import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramSettingsService;
import com.liferay.headless.commerce.bom.dto.v1_0.Diagram;
import com.liferay.headless.commerce.bom.internal.dto.v1_0.converter.util.BreadcrumbDTOConverterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings",
	service = {DiagramDTOConverter.class, DTOConverter.class}
)
public class DiagramDTOConverter
	implements DTOConverter<CPDefinitionDiagramSettings, Diagram> {

	@Override
	public String getContentType() {
		return Diagram.class.getSimpleName();
	}

	@Override
	public Diagram toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CPDefinitionDiagramSettings cpDefinitionDiagramSettings =
			_cpDefinitionDiagramSettingsService.getCPDefinitionDiagramSettings(
				(Long)dtoConverterContext.getId());

		return new Diagram() {
			{
				breadcrumbs = BreadcrumbDTOConverterUtil.getBreadcrumbs(
					_breadcrumbDTOConverter,
					cpDefinitionDiagramSettings.fetchAssetCategory(),
					dtoConverterContext.getLocale());
				data = _diagramDataDTOConverter.toDTO(dtoConverterContext);
			}
		};
	}

	@Reference
	private BreadcrumbDTOConverter _breadcrumbDTOConverter;

	@Reference
	private CPDefinitionDiagramSettingsService
		_cpDefinitionDiagramSettingsService;

	@Reference
	private CPInstanceService _cpInstanceService;

	@Reference
	private DiagramDataDTOConverter _diagramDataDTOConverter;

}