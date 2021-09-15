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

package com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0;

import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramSettingService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Diagram;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Product;
import com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter.DiagramDTOConverter;
import com.liferay.headless.commerce.admin.catalog.internal.util.v1_0.DiagramUtil;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.DiagramResource;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/diagram.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {DiagramResource.class, NestedFieldSupport.class}
)
public class DiagramResourceImpl
	extends BaseDiagramResourceImpl implements NestedFieldSupport {

	@Override
	public Diagram getProductByExternalReferenceCodeDiagram(
			String externalReferenceCode)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with external reference code " +
					externalReferenceCode);
		}

		CSDiagramSetting csDiagramSetting =
			_csDiagramSettingService.getCSDiagramSettingByCPDefinitionId(
				cpDefinition.getCPDefinitionId());

		return _toDiagram(csDiagramSetting.getCSDiagramSettingId());
	}

	@NestedField(parentClass = Product.class, value = "diagram")
	@Override
	public Diagram getProductIdDiagram(Long productId) throws Exception {
		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(productId);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with ID " + productId);
		}

		CSDiagramSetting csDiagramSetting =
			_csDiagramSettingService.getCSDiagramSettingByCPDefinitionId(
				cpDefinition.getCPDefinitionId());

		return _toDiagram(csDiagramSetting.getCSDiagramSettingId());
	}

	@Override
	public Diagram patchDiagram(Long diagramId, Diagram diagram)
		throws Exception {

		CSDiagramSetting csDiagramSetting =
			_csDiagramSettingService.getCSDiagramSetting(diagramId);

		_csDiagramSettingService.updateCSDiagramSetting(
			diagramId, csDiagramSetting.getCPAttachmentFileEntryId(),
			GetterUtil.getString(
				diagram.getColor(), csDiagramSetting.getColor()),
			GetterUtil.getDouble(
				diagram.getRadius(), csDiagramSetting.getRadius()),
			GetterUtil.getString(
				diagram.getType(), csDiagramSetting.getType()));

		return _toDiagram(diagramId);
	}

	@Override
	public Diagram putProductByExternalReferenceCodeDiagram(
			String externalReferenceCode, Diagram diagram)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with external reference code " +
					externalReferenceCode);
		}

		CSDiagramSetting csDiagramSetting =
			DiagramUtil.addOrUpdateCSDiagramSetting(
				cpDefinition.getCPDefinitionId(), _csDiagramSettingService,
				diagram);

		return _toDiagram(csDiagramSetting.getCSDiagramSettingId());
	}

	@Override
	public Diagram putProductIdDiagram(Long productId, Diagram diagram)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(productId);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with ID " + productId);
		}

		CSDiagramSetting csDiagramSetting =
			DiagramUtil.addOrUpdateCSDiagramSetting(
				cpDefinition.getCPDefinitionId(), _csDiagramSettingService,
				diagram);

		return _toDiagram(csDiagramSetting.getCSDiagramSettingId());
	}

	private Diagram _toDiagram(long csDiagramSettingId) throws Exception {
		return _diagramDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				csDiagramSettingId,
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private CSDiagramSettingService _csDiagramSettingService;

	@Reference
	private DiagramDTOConverter _diagramDTOConverter;

}