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

package com.liferay.headless.commerce.shop.by.diagram.internal.resource.v1_0;

import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting;
import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramSettingService;
import com.liferay.headless.commerce.shop.by.diagram.dto.v1_0.Diagram;
import com.liferay.headless.commerce.shop.by.diagram.internal.dto.v1_0.converter.DiagramDTOConverter;
import com.liferay.headless.commerce.shop.by.diagram.resource.v1_0.DiagramResource;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/diagram.properties",
	scope = ServiceScope.PROTOTYPE, service = DiagramResource.class
)
public class DiagramResourceImpl extends BaseDiagramResourceImpl {

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

		CPDefinitionDiagramSetting cpDefinitionDiagramSetting =
			_cpDefinitionDiagramSettingService.
				getCPDefinitionDiagramSettingByCPDefinitionId(
					cpDefinition.getCPDefinitionId());

		return _toDiagram(
			cpDefinitionDiagramSetting.getCPDefinitionDiagramSettingId());
	}

	@Override
	public Diagram getProductIdDiagram(Long productId) throws Exception {
		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(productId);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with ID " + productId);
		}

		CPDefinitionDiagramSetting cpDefinitionDiagramSetting =
			_cpDefinitionDiagramSettingService.
				getCPDefinitionDiagramSettingByCPDefinitionId(
					cpDefinition.getCPDefinitionId());

		return _toDiagram(
			cpDefinitionDiagramSetting.getCPDefinitionDiagramSettingId());
	}

	@Override
	public Diagram patchDiagram(Long diagramId, Diagram diagram)
		throws Exception {

		CPDefinitionDiagramSetting cpDefinitionDiagramSetting =
			_cpDefinitionDiagramSettingService.getCPDefinitionDiagramSetting(
				diagramId);

		_cpDefinitionDiagramSettingService.updateCPDefinitionDiagramSetting(
			diagramId, cpDefinitionDiagramSetting.getCPAttachmentFileEntryId(),
			GetterUtil.get(
				diagram.getColor(), cpDefinitionDiagramSetting.getColor()),
			GetterUtil.get(
				diagram.getRadius(), cpDefinitionDiagramSetting.getRadius()),
			GetterUtil.get(
				diagram.getType(), cpDefinitionDiagramSetting.getType()));

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

		return _addOrUpdateDiagram(cpDefinition.getCPDefinitionId(), diagram);
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

		return _addOrUpdateDiagram(cpDefinition.getCPDefinitionId(), diagram);
	}

	private Diagram _addOrUpdateDiagram(long cpDefinitionId, Diagram diagram)
		throws Exception {

		CPDefinitionDiagramSetting cpDefinitionDiagramSetting =
			_cpDefinitionDiagramSettingService.
				fetchCPDefinitionDiagramSettingByCPDefinitionId(cpDefinitionId);

		if (cpDefinitionDiagramSetting == null) {
			cpDefinitionDiagramSetting =
				_cpDefinitionDiagramSettingService.
					addCPDefinitionDiagramSetting(
						cpDefinitionId,
						GetterUtil.getLong(diagram.getImageId()),
						GetterUtil.getString(diagram.getColor()),
						GetterUtil.getDouble(diagram.getRadius()),
						GetterUtil.getString(diagram.getType()));
		}
		else {
			cpDefinitionDiagramSetting =
				_cpDefinitionDiagramSettingService.
					updateCPDefinitionDiagramSetting(
						cpDefinitionDiagramSetting.
							getCPDefinitionDiagramSettingId(),
						GetterUtil.get(
							diagram.getImageId(),
							cpDefinitionDiagramSetting.
								getCPAttachmentFileEntryId()),
						GetterUtil.getString(
							diagram.getColor(),
							cpDefinitionDiagramSetting.getColor()),
						GetterUtil.getDouble(
							diagram.getRadius(),
							cpDefinitionDiagramSetting.getRadius()),
						GetterUtil.getString(
							diagram.getType(),
							cpDefinitionDiagramSetting.getType()));
		}

		return _toDiagram(
			cpDefinitionDiagramSetting.getCPDefinitionDiagramSettingId());
	}

	private Diagram _toDiagram(long cpDefinitionDiagramSettingId)
		throws Exception {

		return _diagramDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				cpDefinitionDiagramSettingId,
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference
	private CPDefinitionDiagramSettingService
		_cpDefinitionDiagramSettingService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private DiagramDTOConverter _diagramDTOConverter;

}