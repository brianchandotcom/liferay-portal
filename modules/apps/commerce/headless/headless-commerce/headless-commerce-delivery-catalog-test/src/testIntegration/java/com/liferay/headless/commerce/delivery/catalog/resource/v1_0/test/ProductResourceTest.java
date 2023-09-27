/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.catalog.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.headless.commerce.delivery.catalog.client.dto.v1_0.Product;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Andrea Sbarra
 */
@RunWith(Arquillian.class)
public class ProductResourceTest extends BaseProductResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			testGroup.getGroupId(), RandomTestUtil.randomString());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"productType"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"catalogId", "productType", "statusCode"};
	}

	@Override
	protected Product testGetChannelProduct_addProduct() throws Exception {
		return _addCPDefinition(randomProduct());
	}

	@Override
	protected Long testGetChannelProduct_getChannelId() throws Exception {
		return _commerceChannel.getCommerceChannelId();
	}

	@Override
	protected Product testGetChannelProductsPage_addProduct(
			Long channelId, Product product)
		throws Exception {

		return _addCPDefinition(product);
	}

	@Override
	protected Long testGetChannelProductsPage_getChannelId() throws Exception {
		return _commerceChannel.getCommerceChannelId();
	}

	@Override
	protected Long testGraphQLGetChannelProduct_getChannelId()
		throws Exception {

		return _commerceChannel.getCommerceChannelId();
	}

	@Override
	protected Product testGraphQLProduct_addProduct() throws Exception {
		return _addCPDefinition(randomProduct());
	}

	private Product _addCPDefinition(Product product) throws Exception {
		CPDefinition cpDefinition1 = CPTestUtil.addCPDefinition(
			testGroup.getGroupId(), "simple", true, false);
		Locale siteDefaultLocale = LocaleUtil.getSiteDefault();

		CPDefinition cpDefinition2 =
			_cpDefinitionLocalService.updateCPDefinition(
				cpDefinition1.getCPDefinitionId(),
				HashMapBuilder.put(
					siteDefaultLocale, product.getName()
				).build(),
				HashMapBuilder.put(
					siteDefaultLocale, product.getShortDescription()
				).build(),
				HashMapBuilder.put(
					siteDefaultLocale, product.getDescription()
				).build(),
				cpDefinition1.getUrlTitleMap(), cpDefinition1.getMetaTitleMap(),
				cpDefinition1.getMetaDescriptionMap(),
				cpDefinition1.getMetaKeywordsMap(),
				cpDefinition1.isIgnoreSKUCombinations(),
				cpDefinition1.isShippable(), cpDefinition1.isFreeShipping(),
				cpDefinition1.isShipSeparately(),
				cpDefinition1.getShippingExtraPrice(), cpDefinition1.getWidth(),
				cpDefinition1.getHeight(), cpDefinition1.getDepth(),
				cpDefinition1.getWeight(), cpDefinition1.getCPTaxCategoryId(),
				cpDefinition1.isTaxExempt(),
				cpDefinition1.isTelcoOrElectronics(),
				cpDefinition1.getDDMStructureKey(), cpDefinition1.isPublished(),
				1, 1, 2022, 12, 0, 0, 0, 0, 0, 0, true, _serviceContext);

		_cpDefinitions.add(cpDefinition2);

		return new Product() {
			{
				createDate = cpDefinition2.getCreateDate();
				description = product.getDescription();
				id = cpDefinition2.getCProductId();
				metaDescription = cpDefinition2.getMetaDescription();
				metaKeyword = cpDefinition2.getMetaKeywords();
				metaTitle = cpDefinition2.getMetaTitle();
				modifiedDate = cpDefinition2.getModifiedDate();
				name = product.getName();
				productId = cpDefinition2.getCProductId();
				productType = cpDefinition2.getProductTypeName();
				shortDescription = product.getShortDescription();
			}
		};
	}

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@Inject
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@DeleteAfterTestRun
	private final List<CPDefinition> _cpDefinitions = new ArrayList<>();

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}