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
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, properties = "OSGI-INF/liferay/rest/v1_0/pin.properties",
	scope = ServiceScope.PROTOTYPE, service = PinResource.class
)
public class PinResourceImpl extends BasePinResourceImpl {

	@Override
	public void deletePin(Long pinId) throws Exception {
		_cpDefinitionDiagramPinService.deleteCPDefinitionDiagramPin(pinId);
	}

	@Override
	public Page<Pin> getProductByExternalReferenceCodePinsPage(
			String externalReferenceCode, String search, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with external reference code: " +
					externalReferenceCode);
		}

		return Page.of(
			_toPins(
				_cpDefinitionDiagramPinService.getCPDefinitionDiagramPins(
					cpDefinition.getCPDefinitionId(),
					pagination.getStartPosition(),
					pagination.getEndPosition())),
			pagination,
			_cpDefinitionDiagramPinService.getCPDefinitionDiagramPinsCount(
				cpDefinition.getCPDefinitionId()));
	}

	@Override
	public Page<Pin> getProductIdPinsPage(
			Long productId, String search, Pagination pagination, Sort[] sorts)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(productId);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with ID " + productId);
		}

		return Page.of(
			_toPins(
				_cpDefinitionDiagramPinService.getCPDefinitionDiagramPins(
					cpDefinition.getCPDefinitionId(),
					pagination.getStartPosition(),
					pagination.getEndPosition())),
			pagination,
			_cpDefinitionDiagramPinService.getCPDefinitionDiagramPinsCount(
				cpDefinition.getCPDefinitionId()));
	}

	@Override
	public Pin patchPin(Long pinId, Pin pin) throws Exception {
		CPDefinitionDiagramPin cpDefinitionDiagramPin =
			_cpDefinitionDiagramPinService.getCPDefinitionDiagramPin(pinId);

		_cpDefinitionDiagramPinService.updateCPDefinitionDiagramPin(
			cpDefinitionDiagramPin.getCPDefinitionDiagramPinId(),
			GetterUtil.get(
				pin.getPositionX(), cpDefinitionDiagramPin.getPositionX()),
			GetterUtil.get(
				pin.getPositionY(), cpDefinitionDiagramPin.getPositionY()),
			GetterUtil.get(
				pin.getSequence(), cpDefinitionDiagramPin.getSequence()));

		return _toPin(cpDefinitionDiagramPin.getCPDefinitionDiagramPinId());
	}

	@Override
	public Pin postProductByExternalReferenceCodePin(
			String externalReferenceCode, Pin pin)
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

		return _addPin(cpDefinition.getCPDefinitionId(), pin);
	}

	@Override
	public Pin postProductIdPin(Long productId, Pin pin) throws Exception {
		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(productId);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with ID " + productId);
		}

		return _addPin(cpDefinition.getCPDefinitionId(), pin);
	}

	private Pin _addPin(long cpDefinitionId, Pin pin) throws Exception {
		CPDefinitionDiagramPin cpDefinitionDiagramPin =
			_cpDefinitionDiagramPinService.addCPDefinitionDiagramPin(
				cpDefinitionId, GetterUtil.getDouble(pin.getPositionX()),
				GetterUtil.getDouble(pin.getPositionY()),
				GetterUtil.getString(pin.getSequence()));

		return _toPin(cpDefinitionDiagramPin.getCPDefinitionDiagramPinId());
	}

	private Pin _toPin(long cpDefinitionDiagramPinId) throws Exception {
		return _pinDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				cpDefinitionDiagramPinId,
				contextAcceptLanguage.getPreferredLocale()));
	}

	private List<Pin> _toPins(
		List<CPDefinitionDiagramPin> cpDefinitionDiagramPins) {

		return TransformUtil.transform(
			cpDefinitionDiagramPins,
			cpDefinitionDiagramPin -> _toPin(
				cpDefinitionDiagramPin.getCPDefinitionDiagramPinId()));
	}

	@Reference
	private CPDefinitionDiagramPinService _cpDefinitionDiagramPinService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private PinDTOConverter _pinDTOConverter;

}