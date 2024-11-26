/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.resource.v1_0.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.model.CPConfigurationList;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPConfigurationEntryLocalService;
import com.liferay.commerce.product.service.CPConfigurationListLocalService;
import com.liferay.commerce.product.service.CProductLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.ProductConfiguration;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsUtil;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zoltán Takács
 */
@RunWith(Arquillian.class)
public class ProductConfigurationResourceTest
	extends BaseProductConfigurationResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser();

		_commerceCatalog = _commerceCatalogLocalService.addCommerceCatalog(
			RandomTestUtil.randomString(),
			AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(), "USD", "en_US", false,
			ServiceContextTestUtil.getServiceContext(
				testGroup.getGroupId(), _user.getUserId()));

		_masterCPConfigurationList =
			_cpConfigurationListLocalService.getMasterCPConfigurationList(
				_commerceCatalog.getGroupId());
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		_cpConfigurationEntryLocalService.deleteCPConfigurationEntries(
			_masterCPConfigurationList.getCPConfigurationListId());
	}

	@FeatureFlags("LPD-10889")
	@Override
	@Test
	public void testDeleteProductConfiguration() throws Exception {
		super.testDeleteProductConfiguration();
	}

	@FeatureFlags("LPD-10889")
	@Override
	@Test
	public void testDeleteProductConfigurationByExternalReferenceCode()
		throws Exception {

		super.testDeleteProductConfigurationByExternalReferenceCode();
	}

	@FeatureFlags("LPD-10889")
	@Override
	@Test
	public void testGetProductByExternalReferenceCodeConfiguration()
		throws Exception {

		super.testGetProductByExternalReferenceCodeConfiguration();
	}

	@FeatureFlags("LPD-10889")
	@Override
	@Test
	public void testGetProductConfiguration() throws Exception {
		super.testGetProductConfiguration();
	}

	@FeatureFlags("LPD-10889")
	@Override
	@Test
	public void testGetProductConfigurationByExternalReferenceCode()
		throws Exception {

		super.testGetProductConfigurationByExternalReferenceCode();
	}

	@FeatureFlags("LPD-10889")
	@Override
	@Test
	public void testGetProductConfigurationListByExternalReferenceCodeProductConfigurationsPage()
		throws Exception {

		super.
			testGetProductConfigurationListByExternalReferenceCodeProductConfigurationsPage();
	}

	@FeatureFlags("LPD-10889")
	@Override
	@Test
	public void testGetProductConfigurationListByExternalReferenceCodeProductConfigurationsPageWithPagination()
		throws Exception {

		super.
			testGetProductConfigurationListByExternalReferenceCodeProductConfigurationsPageWithPagination();
	}

	@FeatureFlags("LPD-10889")
	@Override
	@Test
	public void testGetProductConfigurationListIdProductConfigurationsPage()
		throws Exception {

		super.testGetProductConfigurationListIdProductConfigurationsPage();
	}

	@FeatureFlags("LPD-10889")
	@Override
	@Test
	public void testGetProductConfigurationListIdProductConfigurationsPageWithPagination()
		throws Exception {

		super.
			testGetProductConfigurationListIdProductConfigurationsPageWithPagination();
	}

	@FeatureFlags("LPD-10889")
	@Override
	@Test
	public void testGetProductIdConfiguration() throws Exception {
		super.testGetProductIdConfiguration();
	}

	@FeatureFlags("LPD-10889")
	@Override
	@Test
	public void testGraphQLDeleteProductConfiguration() throws Exception {
		super.testGraphQLDeleteProductConfiguration();
	}

	@FeatureFlags("LPD-10889")
	@Override
	@Test
	public void testGraphQLGetProductByExternalReferenceCodeConfiguration()
		throws Exception {

		super.testGraphQLGetProductByExternalReferenceCodeConfiguration();
	}

	@FeatureFlags("LPD-10889")
	@Override
	@Test
	public void testGraphQLGetProductByExternalReferenceCodeConfigurationNotFound()
		throws Exception {

		super.
			testGraphQLGetProductByExternalReferenceCodeConfigurationNotFound();
	}

	@FeatureFlags("LPD-10889")
	@Override
	@Test
	public void testGraphQLGetProductConfiguration() throws Exception {
		super.testGraphQLGetProductConfiguration();
	}

	@FeatureFlags("LPD-10889")
	@Override
	@Test
	public void testGraphQLGetProductConfigurationByExternalReferenceCode()
		throws Exception {

		super.testGraphQLGetProductConfigurationByExternalReferenceCode();
	}

	@FeatureFlags("LPD-10889")
	@Override
	@Test
	public void testGraphQLGetProductIdConfiguration() throws Exception {
		super.testGraphQLGetProductIdConfiguration();
	}

	@FeatureFlags("LPD-10889")
	@Override
	@Test
	public void testGraphQLGetProductIdConfigurationNotFound()
		throws Exception {

		super.testGraphQLGetProductIdConfigurationNotFound();
	}

	@Override
	@Test
	public void testPatchProductByExternalReferenceCodeConfiguration()
		throws Exception {

		ProductConfiguration randomProductConfiguration =
			randomProductConfiguration();

		productConfigurationResource.
			patchProductByExternalReferenceCodeConfiguration(
				randomProductConfiguration.getEntityExternalReferenceCode(),
				randomProductConfiguration);

		ProductConfiguration productConfiguration =
			productConfigurationResource.
				getProductByExternalReferenceCodeConfiguration(
					randomProductConfiguration.
						getEntityExternalReferenceCode());

		Assert.assertTrue(
			equals(productConfiguration, randomProductConfiguration));

		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"LPD-10889", "true"
			).build());

		productConfigurationResource.
			patchProductByExternalReferenceCodeConfiguration(
				randomProductConfiguration.getEntityExternalReferenceCode(),
				randomProductConfiguration);

		productConfiguration =
			productConfigurationResource.
				getProductByExternalReferenceCodeConfiguration(
					randomProductConfiguration.
						getEntityExternalReferenceCode());

		Assert.assertTrue(
			equals(productConfiguration, randomProductConfiguration));

		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"LPD-10889", "false"
			).build());
	}

	@FeatureFlags("LPD-10889")
	@Override
	@Test
	public void testPatchProductConfiguration() throws Exception {
		super.testPatchProductConfiguration();
	}

	@FeatureFlags("LPD-10889")
	@Override
	@Test
	public void testPatchProductConfigurationByExternalReferenceCode()
		throws Exception {

		super.testPatchProductConfigurationByExternalReferenceCode();
	}

	@Override
	@Test
	public void testPatchProductIdConfiguration() throws Exception {
		ProductConfiguration randomProductConfiguration =
			randomProductConfiguration();

		productConfigurationResource.patchProductIdConfiguration(
			randomProductConfiguration.getEntityId(),
			randomProductConfiguration);

		ProductConfiguration productConfiguration =
			productConfigurationResource.getProductIdConfiguration(
				randomProductConfiguration.getEntityId());

		Assert.assertTrue(
			equals(productConfiguration, randomProductConfiguration));

		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"LPD-10889", "true"
			).build());

		productConfigurationResource.patchProductIdConfiguration(
			randomProductConfiguration.getEntityId(),
			randomProductConfiguration);

		productConfiguration =
			productConfigurationResource.getProductIdConfiguration(
				randomProductConfiguration.getEntityId());

		Assert.assertTrue(
			equals(productConfiguration, randomProductConfiguration));

		PropsUtil.addProperties(
			UnicodePropertiesBuilder.setProperty(
				"LPD-10889", "false"
			).build());
	}

	@FeatureFlags("LPD-10889")
	@Override
	@Test
	public void testPostProductConfigurationListByExternalReferenceCodeProductConfiguration()
		throws Exception {

		super.
			testPostProductConfigurationListByExternalReferenceCodeProductConfiguration();
	}

	@FeatureFlags("LPD-10889")
	@Override
	@Test
	public void testPostProductConfigurationListIdProductConfiguration()
		throws Exception {

		super.testPostProductConfigurationListIdProductConfiguration();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"inventoryEngine", "lowStockAction", "maxOrderQuantity",
			"minOrderQuantity", "minStockQuantity", "multipleOrderQuantity"
		};
	}

	@Override
	protected ProductConfiguration randomProductConfiguration() {
		try {
			CPDefinition cpDefinition = CPTestUtil.addCPDefinition(
				_commerceCatalog.getGroupId(), "simple");

			CProduct cProduct = cpDefinition.getCProduct();

			return new ProductConfiguration() {
				{
					allowBackOrder = RandomTestUtil.randomBoolean();
					allowedOrderQuantities = new BigDecimal[0];
					availabilityEstimateId = 0L;
					entityExternalReferenceCode =
						cProduct.getExternalReferenceCode();
					entityId = cpDefinition.getCProductId();
					externalReferenceCode = RandomTestUtil.randomString();
					inventoryEngine = RandomTestUtil.randomString();
					lowStockAction = RandomTestUtil.randomString();
					maxOrderQuantity = BigDecimal.ONE;
					minOrderQuantity = BigDecimal.ONE;
					minStockQuantity = BigDecimal.ONE;
					multipleOrderQuantity = BigDecimal.ONE;
				}
			};
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			throw new SystemException(portalException);
		}
	}

	@Override
	protected ProductConfiguration
			testDeleteProductConfiguration_addProductConfiguration()
		throws Exception {

		return productConfigurationResource.
			postProductConfigurationListIdProductConfiguration(
				_masterCPConfigurationList.getCPConfigurationListId(),
				randomProductConfiguration());
	}

	@Override
	protected ProductConfiguration
			testDeleteProductConfigurationByExternalReferenceCode_addProductConfiguration()
		throws Exception {

		return productConfigurationResource.
			postProductConfigurationListIdProductConfiguration(
				_masterCPConfigurationList.getCPConfigurationListId(),
				randomProductConfiguration());
	}

	@Override
	protected ProductConfiguration
			testGetProductByExternalReferenceCodeConfiguration_addProductConfiguration()
		throws Exception {

		ProductConfiguration productConfiguration =
			randomProductConfiguration();

		productConfigurationResource.
			postProductConfigurationListIdProductConfiguration(
				_masterCPConfigurationList.getCPConfigurationListId(),
				productConfiguration);

		return productConfiguration;
	}

	@Override
	protected String
			testGetProductByExternalReferenceCodeConfiguration_getExternalReferenceCode(
				ProductConfiguration productConfiguration)
		throws Exception {

		return productConfiguration.getEntityExternalReferenceCode();
	}

	@Override
	protected ProductConfiguration
			testGetProductConfiguration_addProductConfiguration()
		throws Exception {

		return productConfigurationResource.
			postProductConfigurationListIdProductConfiguration(
				_masterCPConfigurationList.getCPConfigurationListId(),
				randomProductConfiguration());
	}

	@Override
	protected ProductConfiguration
			testGetProductConfigurationByExternalReferenceCode_addProductConfiguration()
		throws Exception {

		return productConfigurationResource.
			postProductConfigurationListIdProductConfiguration(
				_masterCPConfigurationList.getCPConfigurationListId(),
				randomProductConfiguration());
	}

	@Override
	protected ProductConfiguration
			testGetProductConfigurationListByExternalReferenceCodeProductConfigurationsPage_addProductConfiguration(
				String externalReferenceCode,
				ProductConfiguration productConfiguration)
		throws Exception {

		return productConfigurationResource.
			postProductConfigurationListByExternalReferenceCodeProductConfiguration(
				externalReferenceCode, productConfiguration);
	}

	@Override
	protected String
			testGetProductConfigurationListByExternalReferenceCodeProductConfigurationsPage_getExternalReferenceCode()
		throws Exception {

		return _masterCPConfigurationList.getExternalReferenceCode();
	}

	@Override
	protected ProductConfiguration
			testGetProductConfigurationListIdProductConfigurationsPage_addProductConfiguration(
				Long id, ProductConfiguration productConfiguration)
		throws Exception {

		return productConfigurationResource.
			postProductConfigurationListIdProductConfiguration(
				id, productConfiguration);
	}

	@Override
	protected Long
			testGetProductConfigurationListIdProductConfigurationsPage_getId()
		throws Exception {

		return _masterCPConfigurationList.getCPConfigurationListId();
	}

	@Override
	protected ProductConfiguration
			testGetProductIdConfiguration_addProductConfiguration()
		throws Exception {

		ProductConfiguration productConfiguration =
			randomProductConfiguration();

		productConfigurationResource.
			postProductConfigurationListIdProductConfiguration(
				_masterCPConfigurationList.getCPConfigurationListId(),
				productConfiguration);

		return productConfiguration;
	}

	@Override
	protected Long testGetProductIdConfiguration_getId(
			ProductConfiguration productConfiguration)
		throws Exception {

		return productConfiguration.getEntityId();
	}

	@Override
	protected String
			testGraphQLGetProductByExternalReferenceCodeConfiguration_getExternalReferenceCode(
				ProductConfiguration productConfiguration)
		throws Exception {

		return productConfiguration.getEntityExternalReferenceCode();
	}

	@Override
	protected Long testGraphQLGetProductIdConfiguration_getId(
			ProductConfiguration productConfiguration)
		throws Exception {

		CProduct cProduct =
			_cProductLocalService.getCProductByExternalReferenceCode(
				productConfiguration.getEntityExternalReferenceCode(),
				_masterCPConfigurationList.getCompanyId());

		return cProduct.getCProductId();
	}

	@Override
	protected ProductConfiguration
			testGraphQLProductConfiguration_addProductConfiguration()
		throws Exception {

		return productConfigurationResource.
			postProductConfigurationListIdProductConfiguration(
				_masterCPConfigurationList.getCPConfigurationListId(),
				randomProductConfiguration());
	}

	@Override
	protected ProductConfiguration
			testPatchProductConfiguration_addProductConfiguration()
		throws Exception {

		return productConfigurationResource.
			postProductConfigurationListIdProductConfiguration(
				_masterCPConfigurationList.getCPConfigurationListId(),
				randomProductConfiguration());
	}

	@Override
	protected ProductConfiguration
			testPatchProductConfigurationByExternalReferenceCode_addProductConfiguration()
		throws Exception {

		return productConfigurationResource.
			postProductConfigurationListIdProductConfiguration(
				_masterCPConfigurationList.getCPConfigurationListId(),
				randomProductConfiguration());
	}

	@Override
	protected ProductConfiguration
			testPostProductConfigurationListByExternalReferenceCodeProductConfiguration_addProductConfiguration(
				ProductConfiguration productConfiguration)
		throws Exception {

		return productConfigurationResource.
			postProductConfigurationListIdProductConfiguration(
				_masterCPConfigurationList.getCPConfigurationListId(),
				productConfiguration);
	}

	@Override
	protected ProductConfiguration
			testPostProductConfigurationListIdProductConfiguration_addProductConfiguration(
				ProductConfiguration productConfiguration)
		throws Exception {

		return productConfigurationResource.
			postProductConfigurationListIdProductConfiguration(
				_masterCPConfigurationList.getCPConfigurationListId(),
				productConfiguration);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ProductConfigurationResourceTest.class);

	@DeleteAfterTestRun
	private CommerceCatalog _commerceCatalog;

	@Inject
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	@Inject
	private CPConfigurationEntryLocalService _cpConfigurationEntryLocalService;

	@Inject
	private CPConfigurationListLocalService _cpConfigurationListLocalService;

	@Inject
	private CProductLocalService _cProductLocalService;

	private CPConfigurationList _masterCPConfigurationList;
	private User _user;

}