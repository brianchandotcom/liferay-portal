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

import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CProductLocalService;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramEntry;
import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramEntryService;
import com.liferay.headless.commerce.bom.dto.v1_0.Position;
import com.liferay.headless.commerce.bom.dto.v1_0.Spot;
import com.liferay.headless.commerce.bom.internal.dto.v1_0.converter.SpotDTOConverter;
import com.liferay.headless.commerce.bom.resource.v1_0.SpotResource;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, properties = "OSGI-INF/liferay/rest/v1_0/spot.properties",
	scope = ServiceScope.PROTOTYPE, service = SpotResource.class
)
public class SpotResourceImpl extends BaseSpotResourceImpl {

	@Override
	public Response deleteDiagramIdSpot(Long id, Long spotId) throws Exception {
		_cpDefinitionDiagramEntryService.deleteCPDefinitionDiagramEntry(spotId);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Spot postDiagramIdSpot(Long id, Spot spot) throws Exception {
		CProduct cProduct = _cProductLocalService.getCProductByCPInstanceUuid(
			spot.getProductId());

		Position position = spot.getPosition();

		CPDefinitionDiagramEntry cpDefinitionDiagramEntry =
			_cpDefinitionDiagramEntryService.addCPDefinitionDiagramEntry(
				contextUser.getUserId(), id, spot.getProductId(),
				cProduct.getCProductId(), spot.getNumber(), spot.getQuantity(),
				position.getX(), position.getY(), 0D, new ServiceContext());

		return _spotDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				cpDefinitionDiagramEntry.getCPDefinitionDiagramEntryId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Override
	public Response putDiagramIdSpot(Long id, Long spotId, Spot spot)
		throws Exception {

		CPDefinitionDiagramEntry cpDefinitionDiagramEntry =
			_cpDefinitionDiagramEntryService.getCPDefinitionDiagramEntry(
				spotId);

		CProduct cProduct = _cProductLocalService.getCProductByCPInstanceUuid(
			spot.getProductId());

		Position position = spot.getPosition();

		_cpDefinitionDiagramEntryService.updateCPDefinitionDiagramEntry(
			cpDefinitionDiagramEntry.getCPDefinitionDiagramEntryId(),
			spot.getProductId(), cProduct.getCProductId(),
			GetterUtil.get(
				spot.getNumber(), cpDefinitionDiagramEntry.getNumber()),
			GetterUtil.get(
				spot.getQuantity(), cpDefinitionDiagramEntry.getQuantity()),
			GetterUtil.get(
				position.getX(), cpDefinitionDiagramEntry.getPositionX()),
			GetterUtil.get(
				position.getY(), cpDefinitionDiagramEntry.getPositionY()),
			0D, new ServiceContext());

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Reference
	private CPDefinitionDiagramEntryService _cpDefinitionDiagramEntryService;

	@Reference
	private CProductLocalService _cProductLocalService;

	@Reference
	private SpotDTOConverter _spotDTOConverter;

}