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
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramPin;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramEntryService;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramPinService;
import com.liferay.headless.commerce.shop.by.diagram.dto.v1_0.DiagramEntry;
import com.liferay.headless.commerce.shop.by.diagram.dto.v1_0.Pin;
import com.liferay.headless.commerce.shop.by.diagram.internal.dto.v1_0.converter.PinDTOConverter;
import com.liferay.headless.commerce.shop.by.diagram.resource.v1_0.PinResource;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
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
		_csDiagramPinService.deleteCSDiagramPin(pinId);
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
				_csDiagramPinService.getCSDiagramPins(
					cpDefinition.getCPDefinitionId(),
					pagination.getStartPosition(),
					pagination.getEndPosition())),
			pagination,
			_csDiagramPinService.getCSDiagramPinsCount(
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
				_csDiagramPinService.getCSDiagramPins(
					cpDefinition.getCPDefinitionId(),
					pagination.getStartPosition(),
					pagination.getEndPosition())),
			pagination,
			_csDiagramPinService.getCSDiagramPinsCount(
				cpDefinition.getCPDefinitionId()));
	}

	@Override
	public Pin patchPin(Long pinId, Pin pin) throws Exception {
		CSDiagramPin csDiagramPin = _csDiagramPinService.getCSDiagramPin(pinId);

		_csDiagramPinService.updateCSDiagramPin(
			csDiagramPin.getCSDiagramPinId(),
			GetterUtil.get(pin.getPositionX(), csDiagramPin.getPositionX()),
			GetterUtil.get(pin.getPositionY(), csDiagramPin.getPositionY()),
			GetterUtil.get(pin.getSequence(), csDiagramPin.getSequence()));

		return _toPin(csDiagramPin.getCSDiagramPinId());
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
		CSDiagramPin csDiagramPin = _csDiagramPinService.addCSDiagramPin(
			cpDefinitionId, GetterUtil.getDouble(pin.getPositionX()),
			GetterUtil.getDouble(pin.getPositionY()),
			GetterUtil.getString(pin.getSequence()));

		DiagramEntry diagramEntry = pin.getDiagramEntry();

		if (diagramEntry != null) {
			long skuId = GetterUtil.getLong(diagramEntry.getSkuUuid());

			CPInstance cpInstance =
				_cpInstanceService.fetchByExternalReferenceCode(
					diagramEntry.getSkuExternalReferenceCode(),
					contextCompany.getCompanyId());

			if (cpInstance != null) {
				skuId = cpInstance.getCPInstanceId();
			}

			long productId = GetterUtil.getLong(diagramEntry.getProductId());

			CPDefinition cpDefinition =
				_cpDefinitionService.
					fetchCPDefinitionByCProductExternalReferenceCode(
						diagramEntry.getProductExternalReferenceCode(),
						contextCompany.getCompanyId());

			if (cpDefinition != null) {
				productId = cpDefinition.getCProductId();
			}

			CSDiagramEntry csDiagramEntry =
				_csDiagramEntryService.fetchCSDiagramEntry(
					cpDefinitionId, GetterUtil.getString(pin.getSequence()));

			if (csDiagramEntry == null) {
				_csDiagramEntryService.addCSDiagramEntry(
					cpDefinitionId, skuId, productId,
					GetterUtil.getBoolean(diagramEntry.getDiagram()),
					GetterUtil.getInteger(diagramEntry.getQuantity()),
					GetterUtil.getString(diagramEntry.getSequence()),
					GetterUtil.getString(diagramEntry.getSku()),
					new ServiceContext());
			}
			else {
				_csDiagramEntryService.updateCSDiagramEntry(
					csDiagramEntry.getCSDiagramEntryId(), skuId, productId,
					GetterUtil.getBoolean(diagramEntry.getDiagram()),
					GetterUtil.getInteger(diagramEntry.getQuantity()),
					GetterUtil.getString(diagramEntry.getSequence()),
					GetterUtil.getString(diagramEntry.getSku()),
					new ServiceContext());
			}
		}

		return _toPin(csDiagramPin.getCSDiagramPinId());
	}

	private Pin _toPin(long csDiagramPinId) throws Exception {
		return _pinDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				csDiagramPinId, contextAcceptLanguage.getPreferredLocale()));
	}

	private List<Pin> _toPins(List<CSDiagramPin> csDiagramPins) {
		return TransformUtil.transform(
			csDiagramPins,
			csDiagramPin -> _toPin(csDiagramPin.getCSDiagramPinId()));
	}

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private CPInstanceService _cpInstanceService;

	@Reference
	private CSDiagramEntryService _csDiagramEntryService;

	@Reference
	private CSDiagramPinService _csDiagramPinService;

	@Reference
	private PinDTOConverter _pinDTOConverter;

}