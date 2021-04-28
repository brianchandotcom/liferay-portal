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

package com.liferay.headless.commerce.bom.internal.resource.v1_0;

import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings;
import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramSettingsService;
import com.liferay.headless.commerce.bom.dto.v1_0.Diagram;
import com.liferay.headless.commerce.bom.dto.v1_0.DiagramData;
import com.liferay.headless.commerce.bom.internal.dto.v1_0.converter.DiagramDTOConverter;
import com.liferay.headless.commerce.bom.resource.v1_0.DiagramResource;
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
	public Diagram getProductIdDiagram(Long id) throws Exception {
		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(id);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with ID: " + id);
		}

		CPDefinitionDiagramSettings cpDefinitionDiagramSettings =
			_cpDefinitionDiagramSettingsService.
				getCPDefinitionDiagramSettingsByCPDefinitionId(
					cpDefinition.getCPDefinitionId());

		return _diagramDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				cpDefinitionDiagramSettings.getCPDefinitionDiagramSettingsId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Override
	public Diagram putProductIdDiagram(Long id, Diagram diagram)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(id);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with ID: " + id);
		}

		DiagramData data = diagram.getData();

		if (data == null) {
			throw new Exception(
				"Unable to find Diagram data with product ID: " + id);
		}

		CPDefinitionDiagramSettings cpDefinitionDiagramSettings =
			_cpDefinitionDiagramSettingsService.
				fetchCPDefinitionDiagramSettingsByCPDefinitionId(
					cpDefinition.getCPDefinitionId());

		if (cpDefinitionDiagramSettings == null) {
			cpDefinitionDiagramSettings =
				_cpDefinitionDiagramSettingsService.
					addCPDefinitionDiagramSettings(
						contextUser.getUserId(),
						cpDefinition.getCPDefinitionId(),
						GetterUtil.getLong(data.getFolderId()),
						GetterUtil.getString(
							cpDefinitionDiagramSettings.getName()));
		}
		else {
			cpDefinitionDiagramSettings =
				_cpDefinitionDiagramSettingsService.
					updateCPDefinitionDiagramSettings(
						cpDefinitionDiagramSettings.
							getCPDefinitionDiagramSettingsId(),
						GetterUtil.getLong(data.getFolderId()),
						GetterUtil.getString(
							cpDefinitionDiagramSettings.getName()));
		}

		return _diagramDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				cpDefinitionDiagramSettings.getCPDefinitionDiagramSettingsId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference
	private CPDefinitionDiagramSettingsService
		_cpDefinitionDiagramSettingsService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private DiagramDTOConverter _diagramDTOConverter;

}