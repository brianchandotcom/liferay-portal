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

package com.liferay.headless.commerce.admin.catalog.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.constants.CommerceChannelConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.service.CommerceChannelRelLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.ProductChannel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Zoltán Takács
 */
@RunWith(Arquillian.class)
public class ProductChannelResourceTest
	extends BaseProductChannelResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			testGroup.getGroupId(), BigDecimal.TEN);

		_cpDefinition = _cpInstance.getCPDefinition();

		_cProduct = _cpDefinition.getCProduct();

		_user = UserTestUtil.addUser(testCompany);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"channelId", "currencyCode", "name", "type"};
	}

	@Override
	protected ProductChannel randomProductChannel() throws Exception {
		CommerceChannel commerceChannel =
			_commerceChannelLocalService.addCommerceChannel(
				RandomTestUtil.randomString(), testGroup.getGroupId(),
				RandomTestUtil.randomString(),
				CommerceChannelConstants.CHANNEL_TYPE_SITE, null,
				RandomTestUtil.randomString(), _serviceContext);

		_commerceChannels.add(commerceChannel);

		return new ProductChannel() {
			{
				channelId = commerceChannel.getCommerceChannelId();
				currencyCode = commerceChannel.getCommerceCurrencyCode();
				externalReferenceCode =
					commerceChannel.getExternalReferenceCode();
				id = RandomTestUtil.randomLong();
				name = commerceChannel.getName();
				type = commerceChannel.getType();
			}
		};
	}

	@Override
	protected ProductChannel testDeleteProductChannel_addProductChannel()
		throws Exception {

		return _addCommerceChannelRel(randomProductChannel());
	}

	@Override
	protected ProductChannel
			testGetProductByExternalReferenceCodeProductChannelsPage_addProductChannel(
				String externalReferenceCode, ProductChannel productChannel)
		throws Exception {

		return _addCommerceChannelRel(productChannel);
	}

	@Override
	protected String
			testGetProductByExternalReferenceCodeProductChannelsPage_getExternalReferenceCode()
		throws Exception {

		return _cProduct.getExternalReferenceCode();
	}

	@Override
	protected ProductChannel testGetProductChannel_addProductChannel()
		throws Exception {

		return _addCommerceChannelRel(randomProductChannel());
	}

	@Override
	protected ProductChannel
			testGetProductIdProductChannelsPage_addProductChannel(
				Long id, ProductChannel productChannel)
		throws Exception {

		return _addCommerceChannelRel(productChannel);
	}

	@Override
	protected Long testGetProductIdProductChannelsPage_getId()
		throws Exception {

		return _cProduct.getCProductId();
	}

	@Override
	protected ProductChannel testGraphQLProductChannel_addProductChannel()
		throws Exception {

		return _addCommerceChannelRel(randomProductChannel());
	}

	private ProductChannel _addCommerceChannelRel(ProductChannel productChannel)
		throws Exception {

		CommerceChannelRel commerceChannelRel =
			_commerceChannelRelLocalService.addCommerceChannelRel(
				CPDefinition.class.getName(), _cpDefinition.getCPDefinitionId(),
				productChannel.getChannelId(), _serviceContext);

		productChannel.setId(commerceChannelRel.getCommerceChannelRelId());

		return productChannel;
	}

	@Inject
	private static CommerceChannelLocalService _commerceChannelLocalService;

	@Inject
	private static CommerceChannelRelLocalService
		_commerceChannelRelLocalService;

	@DeleteAfterTestRun
	private final List<CommerceChannel> _commerceChannels = new ArrayList<>();

	@DeleteAfterTestRun
	private CPDefinition _cpDefinition;

	@DeleteAfterTestRun
	private CPInstance _cpInstance;

	@DeleteAfterTestRun
	private CProduct _cProduct;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}