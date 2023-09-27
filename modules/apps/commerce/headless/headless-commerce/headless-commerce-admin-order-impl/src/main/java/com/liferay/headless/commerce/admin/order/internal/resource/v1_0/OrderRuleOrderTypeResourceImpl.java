/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.order.internal.resource.v1_0;

import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.order.rule.exception.NoSuchCOREntryException;
import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.model.COREntryRel;
import com.liferay.commerce.order.rule.service.COREntryRelService;
import com.liferay.commerce.order.rule.service.COREntryService;
import com.liferay.commerce.service.CommerceOrderTypeService;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRule;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRuleOrderType;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderRuleOrderTypeResource;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Marco Leo
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/order-rule-order-type.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = OrderRuleOrderTypeResource.class
)
public class OrderRuleOrderTypeResourceImpl
	extends BaseOrderRuleOrderTypeResourceImpl {

	@Override
	public void deleteOrderRuleOrderType(Long id) throws Exception {
		_corEntryRelService.deleteCOREntryRel(id);
	}

	@Override
	public Page<OrderRuleOrderType>
			getOrderRuleByExternalReferenceCodeOrderRuleOrderTypesPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		COREntry corEntry = _corEntryService.fetchByExternalReferenceCode(
			contextCompany.getCompanyId(), externalReferenceCode);

		if (corEntry == null) {
			throw new NoSuchCOREntryException(
				"Unable to find order rule with external reference code " +
					externalReferenceCode);
		}

		return Page.of(
			transform(
				_corEntryRelService.getCommerceOrderTypeCOREntryRels(
					corEntry.getCOREntryId(), null,
					pagination.getStartPosition(), pagination.getEndPosition()),
				corEntryRel -> _toOrderRuleOrderType(corEntryRel)),
			pagination,
			_corEntryRelService.getCommerceOrderTypeCOREntryRelsCount(
				corEntry.getCOREntryId(), null));
	}

	@NestedField(parentClass = OrderRule.class, value = "orderRuleOrderTypes")
	@Override
	public Page<OrderRuleOrderType> getOrderRuleIdOrderRuleOrderTypesPage(
			Long id, String search, Pagination pagination)
		throws Exception {

		COREntry corEntry = _corEntryService.fetchCOREntry(id);

		if (corEntry == null) {
			throw new NoSuchCOREntryException(
				"Unable to find order rule with ID " + id);
		}

		return Page.of(
			transform(
				_corEntryRelService.getCommerceOrderTypeCOREntryRels(
					id, search, pagination.getStartPosition(),
					pagination.getEndPosition()),
				corEntryRel -> _toOrderRuleOrderType(corEntryRel)),
			pagination,
			_corEntryRelService.getCommerceOrderTypeCOREntryRelsCount(
				id, search));
	}

	@Override
	public OrderRuleOrderType
			postOrderRuleByExternalReferenceCodeOrderRuleOrderType(
				String externalReferenceCode,
				OrderRuleOrderType orderRuleOrderType)
		throws Exception {

		COREntry corEntry = _corEntryService.fetchByExternalReferenceCode(
			contextCompany.getCompanyId(), externalReferenceCode);

		if (corEntry == null) {
			throw new NoSuchCOREntryException(
				"Unable to find order rule with external reference code " +
					externalReferenceCode);
		}

		CommerceOrderType commerceOrderType = _getCommerceOrderType(
			orderRuleOrderType);

		return _toOrderRuleOrderType(
			_corEntryRelService.addCOREntryRel(
				CommerceOrderType.class.getName(),
				commerceOrderType.getCommerceOrderTypeId(),
				corEntry.getCOREntryId()));
	}

	@Override
	public OrderRuleOrderType postOrderRuleIdOrderRuleOrderType(
			Long id, OrderRuleOrderType orderRuleOrderType)
		throws Exception {

		CommerceOrderType commerceOrderType = _getCommerceOrderType(
			orderRuleOrderType);

		return _toOrderRuleOrderType(
			_corEntryRelService.addCOREntryRel(
				CommerceOrderType.class.getName(),
				commerceOrderType.getCommerceOrderTypeId(), id));
	}

	private Map<String, Map<String, String>> _getActions(
			COREntryRel corEntryRel)
		throws Exception {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				"UPDATE", corEntryRel.getCOREntryRelId(),
				"deleteOrderRuleOrderType", _corEntryRelModelResourcePermission)
		).build();
	}

	private CommerceOrderType _getCommerceOrderType(
			OrderRuleOrderType orderRuleOrderType)
		throws Exception {

		CommerceOrderType commerceOrderType = null;

		if (orderRuleOrderType.getOrderTypeId() > 0) {
			commerceOrderType = _commerceOrderTypeService.getCommerceOrderType(
				orderRuleOrderType.getOrderTypeId());
		}
		else {
			commerceOrderType =
				_commerceOrderTypeService.fetchByExternalReferenceCode(
					orderRuleOrderType.getOrderTypeExternalReferenceCode(),
					contextCompany.getCompanyId());
		}

		return commerceOrderType;
	}

	private OrderRuleOrderType _toOrderRuleOrderType(COREntryRel corEntryRel)
		throws Exception {

		return _orderRuleOrderTypeDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(corEntryRel), _dtoConverterRegistry,
				corEntryRel.getCOREntryRelId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	@Reference
	private CommerceOrderTypeService _commerceOrderTypeService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.order.rule.model.COREntryRel)"
	)
	private ModelResourcePermission<COREntryRel>
		_corEntryRelModelResourcePermission;

	@Reference
	private COREntryRelService _corEntryRelService;

	@Reference
	private COREntryService _corEntryService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.order.internal.dto.v1_0.converter.OrderRuleOrderTypeDTOConverter)"
	)
	private DTOConverter<COREntryRel, OrderRuleOrderType>
		_orderRuleOrderTypeDTOConverter;

}