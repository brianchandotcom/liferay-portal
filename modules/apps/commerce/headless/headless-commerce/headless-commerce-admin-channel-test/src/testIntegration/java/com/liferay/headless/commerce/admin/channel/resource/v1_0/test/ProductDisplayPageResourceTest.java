/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.channel.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDisplayLayout;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPDisplayLayoutLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.headless.commerce.admin.channel.client.dto.v1_0.ProductDisplayPage;
import com.liferay.headless.commerce.admin.channel.client.pagination.Page;
import com.liferay.headless.commerce.admin.channel.client.pagination.Pagination;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Danny Situ
 */
@RunWith(Arquillian.class)
public class ProductDisplayPageResourceTest
	extends BaseProductDisplayPageResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addOmniadminUser();

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			testCompany.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			testCompany.getGroupId(), _commerceCurrency.getCode());

		_commerceCatalog = CommerceTestUtil.addCommerceCatalog(
			testCompany.getCompanyId(), testCompany.getGroupId(),
			_user.getUserId(), _commerceCurrency.getCode());
	}

	@Override
	@Test
	public void testGetChannelByExternalReferenceCodeProductDisplayPagesPageWithPagination()
		throws Exception {

		String externalReferenceCode =
			testGetChannelByExternalReferenceCodeProductDisplayPagesPage_getExternalReferenceCode();

		Page<ProductDisplayPage> productDisplayPagePage =
			productDisplayPageResource.
				getChannelByExternalReferenceCodeProductDisplayPagesPage(
					externalReferenceCode, null, null, null);

		int totalCount = GetterUtil.getInteger(
			productDisplayPagePage.getTotalCount());

		ProductDisplayPage productDisplayPage1 =
			testGetChannelByExternalReferenceCodeProductDisplayPagesPage_addProductDisplayPage(
				externalReferenceCode, randomProductDisplayPage());

		ProductDisplayPage productDisplayPage2 =
			testGetChannelByExternalReferenceCodeProductDisplayPagesPage_addProductDisplayPage(
				externalReferenceCode, randomProductDisplayPage());

		ProductDisplayPage productDisplayPage3 =
			testGetChannelByExternalReferenceCodeProductDisplayPagesPage_addProductDisplayPage(
				externalReferenceCode, randomProductDisplayPage());

		int pageSizeLimit = 500;

		if (totalCount >= (pageSizeLimit - 2)) {
			Page<ProductDisplayPage> page1 =
				productDisplayPageResource.
					getChannelByExternalReferenceCodeProductDisplayPagesPage(
						externalReferenceCode, null,
						Pagination.of(
							(int)Math.ceil((totalCount + 1.0) / pageSizeLimit),
							pageSizeLimit),
						null);

			Assert.assertEquals(totalCount + 3, page1.getTotalCount());

			assertContains(
				productDisplayPage1,
				(List<ProductDisplayPage>)page1.getItems());

			Page<ProductDisplayPage> page2 =
				productDisplayPageResource.
					getChannelByExternalReferenceCodeProductDisplayPagesPage(
						externalReferenceCode, null,
						Pagination.of(
							(int)Math.ceil((totalCount + 2.0) / pageSizeLimit),
							pageSizeLimit),
						null);

			assertContains(
				productDisplayPage2,
				(List<ProductDisplayPage>)page2.getItems());

			Page<ProductDisplayPage> page3 =
				productDisplayPageResource.
					getChannelByExternalReferenceCodeProductDisplayPagesPage(
						externalReferenceCode, null,
						Pagination.of(
							(int)Math.ceil((totalCount + 3.0) / pageSizeLimit),
							pageSizeLimit),
						null);

			assertContains(
				productDisplayPage3,
				(List<ProductDisplayPage>)page3.getItems());
		}
		else {
			Page<ProductDisplayPage> page1 =
				productDisplayPageResource.
					getChannelByExternalReferenceCodeProductDisplayPagesPage(
						externalReferenceCode, null,
						Pagination.of(1, totalCount + 2), null);

			List<ProductDisplayPage> productDisplayPages1 =
				(List<ProductDisplayPage>)page1.getItems();

			Assert.assertEquals(
				productDisplayPages1.toString(), totalCount + 2,
				productDisplayPages1.size());

			Page<ProductDisplayPage> page2 =
				productDisplayPageResource.
					getChannelByExternalReferenceCodeProductDisplayPagesPage(
						externalReferenceCode, null,
						Pagination.of(2, totalCount + 2), null);

			Assert.assertEquals(totalCount + 1, page2.getTotalCount());

			List<ProductDisplayPage> productDisplayPages2 =
				(List<ProductDisplayPage>)page2.getItems();

			Assert.assertEquals(
				productDisplayPages2.toString(), 1,
				productDisplayPages2.size());

			Page<ProductDisplayPage> page3 =
				productDisplayPageResource.
					getChannelByExternalReferenceCodeProductDisplayPagesPage(
						externalReferenceCode, null,
						Pagination.of(1, (int)totalCount + 3), null);

			assertContains(
				productDisplayPage1,
				(List<ProductDisplayPage>)page3.getItems());
			assertContains(
				productDisplayPage2,
				(List<ProductDisplayPage>)page3.getItems());
			assertContains(
				productDisplayPage3,
				(List<ProductDisplayPage>)page3.getItems());
		}
	}

	@Override
	@Test
	public void testGetChannelIdProductDisplayPagesPageWithPagination()
		throws Exception {

		Long id = testGetChannelIdProductDisplayPagesPage_getId();

		Page<ProductDisplayPage> productDisplayPagePage =
			productDisplayPageResource.getChannelIdProductDisplayPagesPage(
				id, null, null, null);

		int totalCount = GetterUtil.getInteger(
			productDisplayPagePage.getTotalCount());

		ProductDisplayPage productDisplayPage1 =
			testGetChannelIdProductDisplayPagesPage_addProductDisplayPage(
				id, randomProductDisplayPage());

		ProductDisplayPage productDisplayPage2 =
			testGetChannelIdProductDisplayPagesPage_addProductDisplayPage(
				id, randomProductDisplayPage());

		ProductDisplayPage productDisplayPage3 =
			testGetChannelIdProductDisplayPagesPage_addProductDisplayPage(
				id, randomProductDisplayPage());

		int pageSizeLimit = 500;

		if (totalCount >= (pageSizeLimit - 2)) {
			Page<ProductDisplayPage> page1 =
				productDisplayPageResource.getChannelIdProductDisplayPagesPage(
					id, null,
					Pagination.of(
						(int)Math.ceil((totalCount + 1.0) / pageSizeLimit),
						pageSizeLimit),
					null);

			Assert.assertEquals(totalCount + 3, page1.getTotalCount());

			assertContains(
				productDisplayPage1,
				(List<ProductDisplayPage>)page1.getItems());

			Page<ProductDisplayPage> page2 =
				productDisplayPageResource.getChannelIdProductDisplayPagesPage(
					id, null,
					Pagination.of(
						(int)Math.ceil((totalCount + 2.0) / pageSizeLimit),
						pageSizeLimit),
					null);

			assertContains(
				productDisplayPage2,
				(List<ProductDisplayPage>)page2.getItems());

			Page<ProductDisplayPage> page3 =
				productDisplayPageResource.getChannelIdProductDisplayPagesPage(
					id, null,
					Pagination.of(
						(int)Math.ceil((totalCount + 3.0) / pageSizeLimit),
						pageSizeLimit),
					null);

			assertContains(
				productDisplayPage3,
				(List<ProductDisplayPage>)page3.getItems());
		}
		else {
			Page<ProductDisplayPage> page1 =
				productDisplayPageResource.getChannelIdProductDisplayPagesPage(
					id, null, Pagination.of(1, totalCount + 2), null);

			List<ProductDisplayPage> productDisplayPages1 =
				(List<ProductDisplayPage>)page1.getItems();

			Assert.assertEquals(
				productDisplayPages1.toString(), totalCount + 2,
				productDisplayPages1.size());

			Page<ProductDisplayPage> page2 =
				productDisplayPageResource.getChannelIdProductDisplayPagesPage(
					id, null, Pagination.of(2, totalCount + 2), null);

			Assert.assertEquals(totalCount + 1, page2.getTotalCount());

			List<ProductDisplayPage> productDisplayPages2 =
				(List<ProductDisplayPage>)page2.getItems();

			Assert.assertEquals(
				productDisplayPages2.toString(), 1,
				productDisplayPages2.size());

			Page<ProductDisplayPage> page3 =
				productDisplayPageResource.getChannelIdProductDisplayPagesPage(
					id, null, Pagination.of(1, (int)totalCount + 3), null);

			assertContains(
				productDisplayPage1,
				(List<ProductDisplayPage>)page3.getItems());
			assertContains(
				productDisplayPage2,
				(List<ProductDisplayPage>)page3.getItems());
			assertContains(
				productDisplayPage3,
				(List<ProductDisplayPage>)page3.getItems());
		}
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"pageUuid"};
	}

	@Override
	protected ProductDisplayPage randomProductDisplayPage() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(
			testCompany.getGroupId());

		_layouts.add(layout);

		CPDefinition cpDefinition = CPTestUtil.addCPDefinition(
			_commerceCatalog.getGroupId());

		_cpDefinitions.add(cpDefinition);

		return new ProductDisplayPage() {
			{
				id = RandomTestUtil.randomLong();
				pageTemplateUuid = StringPool.BLANK;
				pageUuid = layout.getUuid();
				productId = cpDefinition.getCPDefinitionId();
			}
		};
	}

	@Override
	protected ProductDisplayPage
			testDeleteProductDisplayPage_addProductDisplayPage()
		throws Exception {

		return _addChannelProductDisplayPage(
			_commerceChannel, randomProductDisplayPage());
	}

	@Override
	protected ProductDisplayPage
			testGetChannelByExternalReferenceCodeProductDisplayPagesPage_addProductDisplayPage(
				String externalReferenceCode,
				ProductDisplayPage productDisplayPage)
		throws Exception {

		return _addChannelProductDisplayPage(
			externalReferenceCode, productDisplayPage);
	}

	@Override
	protected String
			testGetChannelByExternalReferenceCodeProductDisplayPagesPage_getExternalReferenceCode()
		throws Exception {

		return _commerceChannel.getExternalReferenceCode();
	}

	protected ProductDisplayPage
			testGetChannelIdProductDisplayPagesPage_addProductDisplayPage(
				Long id, ProductDisplayPage productDisplayPage)
		throws Exception {

		return _addChannelProductDisplayPage(
			_commerceChannel, productDisplayPage);
	}

	@Override
	protected Long testGetChannelIdProductDisplayPagesPage_getId()
		throws Exception {

		return _commerceChannel.getCommerceChannelId();
	}

	@Override
	protected ProductDisplayPage
			testGetProductDisplayPage_addProductDisplayPage()
		throws Exception {

		return _addChannelProductDisplayPage(
			_commerceChannel, randomProductDisplayPage());
	}

	@Override
	protected ProductDisplayPage
			testGraphQLProductDisplayPage_addProductDisplayPage()
		throws Exception {

		return _addChannelProductDisplayPage(
			_commerceChannel, randomProductDisplayPage());
	}

	@Override
	protected ProductDisplayPage
			testPatchProductDisplayPage_addProductDisplayPage()
		throws Exception {

		return _addChannelProductDisplayPage(
			_commerceChannel, randomProductDisplayPage());
	}

	@Override
	protected ProductDisplayPage
			testPostChannelByExternalReferenceCodeProductDisplayPage_addProductDisplayPage(
				ProductDisplayPage productDisplayPage)
		throws Exception {

		return _addChannelProductDisplayPage(
			_commerceChannel, productDisplayPage);
	}

	@Override
	protected ProductDisplayPage
			testPostChannelIdProductDisplayPage_addProductDisplayPage(
				ProductDisplayPage productDisplayPage)
		throws Exception {

		return _addChannelProductDisplayPage(
			_commerceChannel, productDisplayPage);
	}

	private ProductDisplayPage _addChannelProductDisplayPage(
			CommerceChannel commerceChannel,
			ProductDisplayPage productDisplayPage)
		throws Exception {

		CPDisplayLayout cpDisplayLayout =
			_cpDisplayLayoutLocalService.addCPDisplayLayout(
				_user.getUserId(), commerceChannel.getSiteGroupId(),
				CPDefinition.class, productDisplayPage.getProductId(),
				productDisplayPage.getPageTemplateUuid(),
				productDisplayPage.getPageUuid());

		_cpDisplayLayouts.add(cpDisplayLayout);

		return new ProductDisplayPage() {
			{
				setId(cpDisplayLayout::getCPDisplayLayoutId);
				setPageTemplateUuid(
					cpDisplayLayout::getLayoutPageTemplateEntryUuid);
				setPageUuid(cpDisplayLayout::getLayoutUuid);
				setProductId(cpDisplayLayout::getClassPK);
			}
		};
	}

	private ProductDisplayPage _addChannelProductDisplayPage(
			String externalReferenceCode, ProductDisplayPage productDisplayPage)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.fetchByExternalReferenceCode(
				externalReferenceCode, testCompany.getCompanyId());

		if (commerceChannel == null) {
			commerceChannel = CommerceTestUtil.addCommerceChannel(
				testCompany.getGroupId(), _commerceCurrency.getCode());

			_commerceChannels.add(commerceChannel);
		}

		commerceChannel =
			_commerceChannelLocalService.
				updateCommerceChannelExternalReferenceCode(
					externalReferenceCode,
					commerceChannel.getCommerceChannelId());

		return _addChannelProductDisplayPage(
			commerceChannel, productDisplayPage);
	}

	@DeleteAfterTestRun
	private CommerceCatalog _commerceCatalog;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@Inject
	private CommerceChannelLocalService _commerceChannelLocalService;

	@DeleteAfterTestRun
	private List<CommerceChannel> _commerceChannels = new ArrayList<>();

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@DeleteAfterTestRun
	private List<CPDefinition> _cpDefinitions = new ArrayList<>();

	@Inject
	private CPDisplayLayoutLocalService _cpDisplayLayoutLocalService;

	@DeleteAfterTestRun
	private List<CPDisplayLayout> _cpDisplayLayouts = new ArrayList<>();

	@DeleteAfterTestRun
	private List<Layout> _layouts = new ArrayList<>();

	@DeleteAfterTestRun
	private User _user;

}