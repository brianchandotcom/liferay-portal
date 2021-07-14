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
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPin;
import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramPinService;
import com.liferay.headless.commerce.shop.by.diagram.dto.v1_0.Pin;
import com.liferay.headless.commerce.shop.by.diagram.internal.dto.v1_0.converter.PinDTOConverter;
import com.liferay.headless.commerce.shop.by.diagram.resource.v1_0.PinResource;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import javax.ws.rs.core.Response;

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
	scope = ServiceScope.PROTOTYPE, service = PinResource.class
)
public class PinResourceImpl extends BasePinResourceImpl {

	@Override
	public Response deletePin(@NotNull Long id) throws Exception {
		_cpDefinitionDiagramPinService.deleteCPDefinitionDiagramPin(id);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Page<Pin> getProductByExternalReferenceCodePinsPage(
			@NotNull String externalReferenceCode, String search,
			Pagination pagination, Sort[] sorts)
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

		List<CPDefinitionDiagramPin> cpDefinitionDiagramPins =
			_cpDefinitionDiagramPinService.getCPDefinitionDiagramPins(
				cpDefinition.getCPDefinitionId(), pagination.getStartPosition(),
				pagination.getEndPosition());

		int cpDefinitionDiagramPinsCount =
			_cpDefinitionDiagramPinService.getCPDefinitionDiagramPinsCount(
				cpDefinition.getCPDefinitionId());

		return Page.of(
			_toPins(cpDefinitionDiagramPins), pagination,
			cpDefinitionDiagramPinsCount);
	}

	@Override
	public Page<Pin> getProductIdPinsPage(
			@NotNull Long id, String search, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(id);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with ID: " + id);
		}

		List<CPDefinitionDiagramPin> cpDefinitionDiagramPins =
			_cpDefinitionDiagramPinService.getCPDefinitionDiagramPins(
				cpDefinition.getCPDefinitionId(), pagination.getStartPosition(),
				pagination.getEndPosition());

		int cpDefinitionDiagramPinsCount =
			_cpDefinitionDiagramPinService.getCPDefinitionDiagramPinsCount(
				cpDefinition.getCPDefinitionId());

		return Page.of(
			_toPins(cpDefinitionDiagramPins), pagination,
			cpDefinitionDiagramPinsCount);
	}

	@Override
	public Response patchPin(@NotNull Long id, Pin pin) throws Exception {
		CPDefinitionDiagramPin cpDefinitionDiagramPin =
			_cpDefinitionDiagramPinService.getCPDefinitionDiagramPin(id);

		_cpDefinitionDiagramPinService.updateCPDefinitionDiagramPin(
			cpDefinitionDiagramPin.getCPDefinitionDiagramPinId(),
			GetterUtil.get(pin.getNumber(), cpDefinitionDiagramPin.getNumber()),
			GetterUtil.get(
				pin.getPositionX(), cpDefinitionDiagramPin.getPositionX()),
			GetterUtil.get(
				pin.getPositionY(), cpDefinitionDiagramPin.getPositionY()));

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Pin postProductByExternalReferenceCodePin(
			@NotNull String externalReferenceCode, Pin pin)
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

		return _addPin(cpDefinition.getCPDefinitionId(), pin);
	}

	@Override
	public Pin postProductIdPin(@NotNull Long id, Pin pin) throws Exception {
		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(id);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with ID: " + id);
		}

		return _addPin(cpDefinition.getCPDefinitionId(), pin);
	}

	private Pin _addPin(long cpDefinitionId, Pin pin) throws Exception {
		CPDefinitionDiagramPin cpDefinitionDiagramPin =
			_cpDefinitionDiagramPinService.addCPDefinitionDiagramPin(
				contextUser.getUserId(), cpDefinitionId,
				GetterUtil.getInteger(pin.getNumber()),
				GetterUtil.getDouble(pin.getPositionX()),
				GetterUtil.getDouble(pin.getPositionY()));

		return _pinDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				cpDefinitionDiagramPin.getCPDefinitionDiagramPinId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	private List<Pin> _toPins(
			List<CPDefinitionDiagramPin> cpDefinitionDiagramPins)
		throws Exception {

		List<Pin> pins = new ArrayList<>();

		for (CPDefinitionDiagramPin cpDefinitionDiagramPin :
				cpDefinitionDiagramPins) {

			pins.add(
				_pinDTOConverter.toDTO(
					new DefaultDTOConverterContext(
						cpDefinitionDiagramPin.getCPDefinitionDiagramPinId(),
						contextAcceptLanguage.getPreferredLocale())));
		}

		return pins;
	}

	@Reference
	private CPDefinitionDiagramPinService _cpDefinitionDiagramPinService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private PinDTOConverter _pinDTOConverter;

}