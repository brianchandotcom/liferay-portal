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

import com.liferay.commerce.bom.model.CommerceBOMEntry;
import com.liferay.commerce.bom.service.CommerceBOMEntryService;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CProductLocalService;
import com.liferay.headless.commerce.bom.dto.v1_0.Position;
import com.liferay.headless.commerce.bom.dto.v1_0.Spot;
import com.liferay.headless.commerce.bom.internal.dto.v1_0.converter.SpotDTOConverter;
import com.liferay.headless.commerce.bom.resource.v1_0.SpotResource;
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
	properties = "OSGI-INF/liferay/rest/v1_0/spot.properties",
	scope = ServiceScope.PROTOTYPE, service = SpotResource.class
)
public class SpotResourceImpl extends BaseSpotResourceImpl {

	@Override
	public Response deleteAreaIdSpot(Long id, Long spotId) throws Exception {
		_commerceBOMEntryService.deleteCommerceBOMEntry(spotId);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Spot postAreaIdSpot(Long id, Spot spot) throws Exception {
		CProduct cProduct = _cProductLocalService.getCProductByCPInstanceUuid(
			spot.getProductId());

		Position position = spot.getPosition();

		CommerceBOMEntry commerceBOMEntry =
			_commerceBOMEntryService.addCommerceBOMEntry(
				contextUser.getUserId(), spot.getNumber(), spot.getProductId(),
				cProduct.getCProductId(), id, position.getX(), position.getY(),
				0D);

		return _spotDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceBOMEntry.getCommerceBOMEntryId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Override
	public Response putAreaIdSpot(Long id, Long spotId, Spot spot)
		throws Exception {

		CommerceBOMEntry commerceBOMEntry =
			_commerceBOMEntryService.getCommerceBOMEntry(spotId);

		CProduct cProduct = _cProductLocalService.getCProductByCPInstanceUuid(
			spot.getProductId());

		Position position = spot.getPosition();

		_commerceBOMEntryService.updateCommerceBOMEntry(
			commerceBOMEntry.getCommerceBOMEntryId(),
			GetterUtil.get(spot.getNumber(), commerceBOMEntry.getNumber()),
			spot.getProductId(), cProduct.getCProductId(),
			GetterUtil.get(position.getX(), commerceBOMEntry.getPositionX()),
			GetterUtil.get(position.getY(), commerceBOMEntry.getPositionY()),
			0D);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Reference
	private CommerceBOMEntryService _commerceBOMEntryService;

	@Reference
	private CProductLocalService _cProductLocalService;

	@Reference
	private SpotDTOConverter _spotDTOConverter;

}