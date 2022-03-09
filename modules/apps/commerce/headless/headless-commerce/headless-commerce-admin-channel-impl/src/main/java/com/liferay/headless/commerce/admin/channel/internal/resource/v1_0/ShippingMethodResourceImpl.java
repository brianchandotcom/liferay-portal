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

package com.liferay.headless.commerce.admin.channel.internal.resource.v1_0;

import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.service.CommerceShippingMethodService;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionService;
import com.liferay.commerce.util.CommerceShippingEngineRegistry;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.ShippingMethod;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.ShippingOption;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.ShippingMethodResource;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Andrea Sbarra
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/shipping-method.properties",
	scope = ServiceScope.PROTOTYPE, service = ShippingMethodResource.class
)
public class ShippingMethodResourceImpl extends BaseShippingMethodResourceImpl {

	@Override
	public Page<ShippingMethod> getChannelShippingMethodsPage(
			Long channelId, Pagination pagination)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelService.getCommerceChannel(channelId);

		Map<String, CommerceShippingEngine> commerceShippingEngines =
			_commerceShippingEngineRegistry.getCommerceShippingEngines();

		return Page.of(
			TransformUtil.transform(
				_commerceShippingMethodService.getCommerceShippingMethods(
					commerceChannel.getGroupId()),
				commerceShippingMethod -> new ShippingMethod() {
					{
						active = commerceShippingMethod.isActive();
						setDescription(
							() -> {
								if (Validator.isNull(
										commerceShippingMethod.
											getDescriptionMap())) {

									CommerceShippingEngine
										commerceShippingEngine =
											commerceShippingEngines.get(
												commerceShippingMethod.
													getEngineKey());

									return HashMapBuilder.put(
										contextAcceptLanguage.
											getPreferredLanguageId(),
										commerceShippingEngine.getDescription(
											contextAcceptLanguage.
												getPreferredLocale())
									).build();
								}

								return LanguageUtils.getLanguageIdMap(
									commerceShippingMethod.getNameMap());
							});
						engineKey = commerceShippingMethod.getEngineKey();
						id =
							commerceShippingMethod.
								getCommerceShippingMethodId();
						setName(
							() -> {
								if (Validator.isNull(
										commerceShippingMethod.getNameMap())) {

									CommerceShippingEngine
										commerceShippingEngine =
											commerceShippingEngines.get(
												commerceShippingMethod.
													getEngineKey());

									return HashMapBuilder.put(
										contextAcceptLanguage.
											getPreferredLanguageId(),
										commerceShippingEngine.getName(
											contextAcceptLanguage.
												getPreferredLocale())
									).build();
								}

								return LanguageUtils.getLanguageIdMap(
									commerceShippingMethod.getNameMap());
							});
						priority = commerceShippingMethod.getPriority();
						shippingOptions = _getShippingOptions(
							commerceShippingMethod.
								getCommerceShippingMethodId());
					}
				}),
			pagination,
			_commerceShippingMethodService.getCommerceShippingMethodsCount(
				commerceChannel.getGroupId(), true));
	}

	private ShippingOption[] _getShippingOptions(long shippingMethodId)
		throws PortalException {

		return TransformUtil.transformToArray(
			_commerceShippingFixedOptionService.getCommerceShippingFixedOptions(
				shippingMethodId, QueryUtil.ALL_POS, QueryUtil.ALL_POS),
			commerceShippingFixedOption -> new ShippingOption() {
				{
					description = LanguageUtils.getLanguageIdMap(
						commerceShippingFixedOption.getDescriptionMap());
					id =
						commerceShippingFixedOption.
							getCommerceShippingFixedOptionId();
					name = LanguageUtils.getLanguageIdMap(
						commerceShippingFixedOption.getNameMap());
					priority = commerceShippingFixedOption.getPriority();
				}
			},
			ShippingOption.class);
	}

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommerceShippingEngineRegistry _commerceShippingEngineRegistry;

	@Reference
	private CommerceShippingFixedOptionService
		_commerceShippingFixedOptionService;

	@Reference
	private CommerceShippingMethodService _commerceShippingMethodService;

}