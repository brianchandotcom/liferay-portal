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

import javax.validation.constraints.NotNull;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = {
		"api.version=v1.0", "batch.engine.task.item.delegate=true",
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Headless.Commerce.Admin.Catalog)",
		"osgi.jaxrs.resource=true"
	},
	scope = ServiceScope.PROTOTYPE, service = DiagramResource.class
)
public class DiagramResourceImpl extends BaseDiagramResourceImpl {

	@Override
	public Diagram getProductByExternalReferenceCodeDiagram(
			@NotNull String externalReferenceCode)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with externalReferenceCode: " +
					externalReferenceCode);
		}

		CPDefinitionDiagramSetting cpDefinitionDiagramSetting =
			_cpDefinitionDiagramSettingService.
				getCPDefinitionDiagramSettingByCPDefinitionId(
					cpDefinition.getCPDefinitionId());

		return _diagramDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				cpDefinitionDiagramSetting.getCPDefinitionDiagramSettingId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Override
	public Diagram getProductIdDiagram(@NotNull Long id) throws Exception {
		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(id);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with ID: " + id);
		}

		CPDefinitionDiagramSetting cpDefinitionDiagramSetting =
			_cpDefinitionDiagramSettingService.
				getCPDefinitionDiagramSettingByCPDefinitionId(
					cpDefinition.getCPDefinitionId());

		return _diagramDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				cpDefinitionDiagramSetting.getCPDefinitionDiagramSettingId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Override
	public Diagram putProductByExternalReferenceCodeDiagram(
			@NotNull String externalReferenceCode, Diagram diagram)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with externalReferenceCode: " +
					externalReferenceCode);
		}

		return _addOrUpdateDiagram(cpDefinition.getCPDefinitionId(), diagram);
	}

	@Override
	public Diagram putProductIdDiagram(@NotNull Long id, Diagram diagram)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(id);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with ID: " + id);
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
						contextUser.getUserId(), cpDefinitionId,
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

		return _diagramDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				cpDefinitionDiagramSetting.getCPDefinitionDiagramSettingId(),
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