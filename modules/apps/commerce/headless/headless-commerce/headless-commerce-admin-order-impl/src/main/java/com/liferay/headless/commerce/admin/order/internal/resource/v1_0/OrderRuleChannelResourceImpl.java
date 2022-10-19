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

package com.liferay.headless.commerce.admin.order.internal.resource.v1_0;

import com.liferay.commerce.order.rule.exception.NoSuchCOREntryException;
import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.model.COREntryRel;
import com.liferay.commerce.order.rule.service.COREntryRelService;
import com.liferay.commerce.order.rule.service.COREntryService;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRule;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderRuleChannel;
import com.liferay.headless.commerce.admin.order.internal.dto.v1_0.converter.OrderRuleChannelDTOConverter;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderRuleChannelResource;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
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
	properties = "OSGI-INF/liferay/rest/v1_0/order-rule-channel.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {NestedFieldSupport.class, OrderRuleChannelResource.class}
)
public class OrderRuleChannelResourceImpl
	extends BaseOrderRuleChannelResourceImpl implements NestedFieldSupport {

	@Override
	public void deleteOrderRuleChannel(Long id) throws Exception {
		_corEntryRelService.deleteCOREntryRel(id);
	}

	@Override
	public Page<OrderRuleChannel>
			getOrderRuleByExternalReferenceCodeOrderRuleChannelsPage(
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
			TransformUtil.transform(
				_corEntryRelService.getCommerceChannelCOREntryRels(
					corEntry.getCOREntryId(), null,
					pagination.getStartPosition(), pagination.getEndPosition()),
				corEntryRel -> _toOrderRuleChannel(corEntryRel)),
			pagination,
			_corEntryRelService.getCommerceChannelCOREntryRelsCount(
				corEntry.getCOREntryId(), null));
	}

	@NestedField(parentClass = OrderRule.class, value = "orderRuleChannels")
	@Override
	public Page<OrderRuleChannel> getOrderRuleIdOrderRuleChannelsPage(
			Long id, String search, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		COREntry corEntry = _corEntryService.fetchCOREntry(id);

		if (corEntry == null) {
			throw new NoSuchCOREntryException(
				"Unable to find order rule with ID " + id);
		}

		return Page.of(
			TransformUtil.transform(
				_corEntryRelService.getCommerceChannelCOREntryRels(
					id, search, pagination.getStartPosition(),
					pagination.getEndPosition()),
				corEntryRel -> _toOrderRuleChannel(corEntryRel)),
			pagination,
			_corEntryRelService.getCommerceChannelCOREntryRelsCount(
				id, search));
	}

	@Override
	public OrderRuleChannel
			postOrderRuleByExternalReferenceCodeOrderRuleChannel(
				String externalReferenceCode, OrderRuleChannel orderRuleChannel)
		throws Exception {

		COREntry corEntry = _corEntryService.fetchByExternalReferenceCode(
			contextCompany.getCompanyId(), externalReferenceCode);

		if (corEntry == null) {
			throw new NoSuchCOREntryException(
				"Unable to find order rule with external reference code " +
					externalReferenceCode);
		}

		CommerceChannel commerceChannel = _getCommerceChannel(orderRuleChannel);

		return _toOrderRuleChannel(
			_corEntryRelService.addCOREntryRel(
				CommerceChannel.class.getName(),
				commerceChannel.getCommerceChannelId(),
				corEntry.getCOREntryId()));
	}

	@Override
	public OrderRuleChannel postOrderRuleIdOrderRuleChannel(
			Long id, OrderRuleChannel orderRuleChannel)
		throws Exception {

		CommerceChannel commerceChannel = _getCommerceChannel(orderRuleChannel);

		return _toOrderRuleChannel(
			_corEntryRelService.addCOREntryRel(
				CommerceChannel.class.getName(),
				commerceChannel.getCommerceChannelId(), id));
	}

	private Map<String, Map<String, String>> _getActions(
			COREntryRel corEntryRel)
		throws Exception {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				"UPDATE", corEntryRel.getCOREntryRelId(),
				"deleteOrderRuleChannel", _corEntryRelModelResourcePermission)
		).build();
	}

	private CommerceChannel _getCommerceChannel(
			OrderRuleChannel orderRuleChannel)
		throws Exception {

		CommerceChannel commerceChannel = null;

		if (orderRuleChannel.getChannelId() > 0) {
			commerceChannel = _commerceChannelService.getCommerceChannel(
				orderRuleChannel.getChannelId());
		}
		else {
			commerceChannel =
				_commerceChannelService.fetchByExternalReferenceCode(
					orderRuleChannel.getChannelExternalReferenceCode(),
					contextCompany.getCompanyId());
		}

		return commerceChannel;
	}

	private OrderRuleChannel _toOrderRuleChannel(COREntryRel corEntryRel)
		throws Exception {

		return _orderRuleChannelDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(corEntryRel), _dtoConverterRegistry,
				corEntryRel.getCOREntryRelId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	@Reference
	private CommerceChannelService _commerceChannelService;

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

	@Reference
	private OrderRuleChannelDTOConverter _orderRuleChannelDTOConverter;

}