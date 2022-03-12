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
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramPin;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramEntryService;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramPinService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.MappedProduct;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Pin;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Product;
import com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter.PinDTOConverter;
import com.liferay.headless.commerce.admin.catalog.internal.util.v1_0.MappedProductUtil;
import com.liferay.headless.commerce.admin.catalog.internal.util.v1_0.PinUtil;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.PinResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, properties = "OSGI-INF/liferay/rest/v1_0/pin.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {NestedFieldSupport.class, PinResource.class}
)
public class PinResourceImpl
	extends BasePinResourceImpl implements NestedFieldSupport {

	@Override
	public void deletePin(Long pinId) throws Exception {
		CSDiagramPin csDiagramPin = _csDiagramPinService.getCSDiagramPin(pinId);

		CSDiagramEntry csDiagramEntry =
			_csDiagramEntryService.fetchCSDiagramEntry(
				csDiagramPin.getCPDefinitionId(), csDiagramPin.getSequence());

		if (csDiagramEntry != null) {
			List<CSDiagramPin> csDiagramPins =
				_csDiagramPinService.getCSDiagramPins(
					csDiagramPin.getCPDefinitionId(), -1, -1);

			Stream<CSDiagramPin> csDiagramPinsStream = csDiagramPins.stream();

			if (csDiagramPinsStream.filter(
					curCSDiagramPin ->
						curCSDiagramPin.getCSDiagramPinId() !=
							csDiagramPin.getCSDiagramPinId()
				).noneMatch(
					curCSDiagramPin -> Objects.equals(
						curCSDiagramPin.getSequence(),
						csDiagramPin.getSequence())
				)) {

				_csDiagramEntryService.deleteCSDiagramEntry(csDiagramEntry);
			}
		}

		_csDiagramPinService.deleteCSDiagramPin(csDiagramPin);
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

	@NestedField(parentClass = Product.class, value = "pins")
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

		PinUtil.updateCSDiagramPin(csDiagramPin, _csDiagramPinService, pin);

		CPDefinition cpDefinition = csDiagramPin.getCPDefinition();

		_addOrUpdateMappedProduct(
			csDiagramPin.getCPDefinitionId(), cpDefinition.getGroupId(), pin);

		return _toPin(pinId);
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

		return _addPin(
			cpDefinition.getCPDefinitionId(), cpDefinition.getGroupId(), pin);
	}

	@Override
	public Pin postProductIdPin(Long productId, Pin pin) throws Exception {
		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(productId);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with ID " + productId);
		}

		return _addPin(
			cpDefinition.getCPDefinitionId(), cpDefinition.getGroupId(), pin);
	}

	private void _addOrUpdateMappedProduct(
			long cpDefinitionId, long groupId, Pin pin)
		throws Exception {

		MappedProduct mappedProduct = pin.getMappedProduct();

		if (mappedProduct != null) {
			long skuId = GetterUtil.getLong(mappedProduct.getSkuId());

			CPInstance cpInstance =
				_cpInstanceService.fetchByExternalReferenceCode(
					mappedProduct.getSkuExternalReferenceCode(),
					contextCompany.getCompanyId());

			if (cpInstance != null) {
				skuId = cpInstance.getCPInstanceId();
			}

			long productId = GetterUtil.getLong(mappedProduct.getProductId());

			CPDefinition cpDefinition =
				_cpDefinitionService.
					fetchCPDefinitionByCProductExternalReferenceCode(
						mappedProduct.getProductExternalReferenceCode(),
						contextCompany.getCompanyId());

			if (cpDefinition != null) {
				productId = cpDefinition.getCProductId();
			}

			ServiceContext serviceContext =
				_serviceContextHelper.getServiceContext(groupId);

			Map<String, Serializable> expandoBridgeAttributes =
				MappedProductUtil.getExpandoBridgeAttributes(
					contextCompany.getCompanyId(),
					contextAcceptLanguage.getPreferredLocale(), mappedProduct);

			if (expandoBridgeAttributes != null) {
				serviceContext.setExpandoBridgeAttributes(
					expandoBridgeAttributes);
			}

			CSDiagramEntry csDiagramEntry =
				_csDiagramEntryService.fetchCSDiagramEntry(
					cpDefinitionId, GetterUtil.getString(pin.getSequence()));

			if (csDiagramEntry == null) {
				_csDiagramEntryService.addCSDiagramEntry(
					cpDefinitionId, skuId, productId,
					MappedProductUtil.isDiagram(csDiagramEntry, mappedProduct),
					GetterUtil.getInteger(mappedProduct.getQuantity()),
					GetterUtil.getString(mappedProduct.getSequence()),
					GetterUtil.getString(mappedProduct.getSku()),
					serviceContext);
			}
			else {
				_csDiagramEntryService.updateCSDiagramEntry(
					csDiagramEntry.getCSDiagramEntryId(), skuId, productId,
					MappedProductUtil.isDiagram(csDiagramEntry, mappedProduct),
					GetterUtil.getInteger(mappedProduct.getQuantity()),
					GetterUtil.getString(mappedProduct.getSequence()),
					GetterUtil.getString(mappedProduct.getSku()),
					serviceContext);
			}
		}
	}

	private Pin _addPin(long cpDefinitionId, long groupId, Pin pin)
		throws Exception {

		CSDiagramPin csDiagramPin = PinUtil.addCSDiagramPin(
			cpDefinitionId, _csDiagramPinService, pin);

		_addOrUpdateMappedProduct(cpDefinitionId, groupId, pin);

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

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}