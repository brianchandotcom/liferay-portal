/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.channel.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.product.model.CPDisplayLayout;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPDisplayLayoutLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.headless.commerce.admin.channel.client.dto.v1_0.CategoryDisplayPage;
import com.liferay.headless.commerce.admin.channel.client.pagination.Page;
import com.liferay.headless.commerce.admin.channel.client.pagination.Pagination;
import com.liferay.layout.test.util.LayoutTestUtil;
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
public class CategoryDisplayPageResourceTest
	extends BaseCategoryDisplayPageResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		_assetVocabulary = AssetTestUtil.addVocabulary(_user.getGroupId());

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			testGroup.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			testGroup.getGroupId(), _commerceCurrency.getCode());
	}

	@Override
	@Test
	public void testGetChannelByExternalReferenceCodeCategoryDisplayPagesPageWithPagination()
		throws Exception {

		String externalReferenceCode =
			_commerceChannel.getExternalReferenceCode();

		Page<CategoryDisplayPage> categoryDisplayPagePage =
			categoryDisplayPageResource.
				getChannelByExternalReferenceCodeCategoryDisplayPagesPage(
					externalReferenceCode, null, null, null);

		int totalCount = GetterUtil.getInteger(
			categoryDisplayPagePage.getTotalCount());

		CategoryDisplayPage categoryDisplayPage1 =
			_addChannelCategoryDisplayPage(
				externalReferenceCode, randomCategoryDisplayPage());

		CategoryDisplayPage categoryDisplayPage2 =
			_addChannelCategoryDisplayPage(
				externalReferenceCode, randomCategoryDisplayPage());

		CategoryDisplayPage categoryDisplayPage3 =
			_addChannelCategoryDisplayPage(
				externalReferenceCode, randomCategoryDisplayPage());

		int pageSizeLimit = 500;

		if (totalCount >= (pageSizeLimit - 2)) {
			Page<CategoryDisplayPage> page1 =
				categoryDisplayPageResource.
					getChannelByExternalReferenceCodeCategoryDisplayPagesPage(
						externalReferenceCode, null,
						Pagination.of(
							(int)Math.ceil((totalCount + 1.0) / pageSizeLimit),
							pageSizeLimit),
						null);

			Assert.assertEquals(totalCount + 3, page1.getTotalCount());

			assertContains(
				categoryDisplayPage1,
				(List<CategoryDisplayPage>)page1.getItems());

			Page<CategoryDisplayPage> page2 =
				categoryDisplayPageResource.
					getChannelByExternalReferenceCodeCategoryDisplayPagesPage(
						externalReferenceCode, null,
						Pagination.of(
							(int)Math.ceil((totalCount + 2.0) / pageSizeLimit),
							pageSizeLimit),
						null);

			assertContains(
				categoryDisplayPage2,
				(List<CategoryDisplayPage>)page2.getItems());

			Page<CategoryDisplayPage> page3 =
				categoryDisplayPageResource.
					getChannelByExternalReferenceCodeCategoryDisplayPagesPage(
						externalReferenceCode, null,
						Pagination.of(
							(int)Math.ceil((totalCount + 3.0) / pageSizeLimit),
							pageSizeLimit),
						null);

			assertContains(
				categoryDisplayPage3,
				(List<CategoryDisplayPage>)page3.getItems());
		}
		else {
			Page<CategoryDisplayPage> page1 =
				categoryDisplayPageResource.
					getChannelByExternalReferenceCodeCategoryDisplayPagesPage(
						externalReferenceCode, null,
						Pagination.of(1, totalCount + 2), null);

			List<CategoryDisplayPage> categoryDisplayPages1 =
				(List<CategoryDisplayPage>)page1.getItems();

			Assert.assertEquals(
				categoryDisplayPages1.toString(), totalCount + 2,
				categoryDisplayPages1.size());

			Page<CategoryDisplayPage> page2 =
				categoryDisplayPageResource.
					getChannelByExternalReferenceCodeCategoryDisplayPagesPage(
						externalReferenceCode, null,
						Pagination.of(2, totalCount + 2), null);

			Assert.assertEquals(totalCount + 1, page2.getTotalCount());

			List<CategoryDisplayPage> categoryDisplayPages2 =
				(List<CategoryDisplayPage>)page2.getItems();

			Assert.assertEquals(
				categoryDisplayPages2.toString(), 1,
				categoryDisplayPages2.size());

			Page<CategoryDisplayPage> page3 =
				categoryDisplayPageResource.
					getChannelByExternalReferenceCodeCategoryDisplayPagesPage(
						externalReferenceCode, null,
						Pagination.of(1, (int)totalCount + 3), null);

			assertContains(
				categoryDisplayPage1,
				(List<CategoryDisplayPage>)page3.getItems());
			assertContains(
				categoryDisplayPage2,
				(List<CategoryDisplayPage>)page3.getItems());
			assertContains(
				categoryDisplayPage3,
				(List<CategoryDisplayPage>)page3.getItems());
		}
	}

	@Override
	@Test
	public void testGetChannelIdCategoryDisplayPagesPageWithPagination()
		throws Exception {

		Long id = testGetChannelIdCategoryDisplayPagesPage_getId();

		Page<CategoryDisplayPage> categoryDisplayPagePage =
			categoryDisplayPageResource.getChannelIdCategoryDisplayPagesPage(
				id, null, null, null);

		int totalCount = GetterUtil.getInteger(
			categoryDisplayPagePage.getTotalCount());

		CategoryDisplayPage categoryDisplayPage1 =
			testGetChannelIdCategoryDisplayPagesPage_addCategoryDisplayPage(
				id, randomCategoryDisplayPage());

		CategoryDisplayPage categoryDisplayPage2 =
			testGetChannelIdCategoryDisplayPagesPage_addCategoryDisplayPage(
				id, randomCategoryDisplayPage());

		CategoryDisplayPage categoryDisplayPage3 =
			testGetChannelIdCategoryDisplayPagesPage_addCategoryDisplayPage(
				id, randomCategoryDisplayPage());

		int pageSizeLimit = 500;

		if (totalCount >= (pageSizeLimit - 2)) {
			Page<CategoryDisplayPage> page1 =
				categoryDisplayPageResource.
					getChannelIdCategoryDisplayPagesPage(
						id, null,
						Pagination.of(
							(int)Math.ceil((totalCount + 1.0) / pageSizeLimit),
							pageSizeLimit),
						null);

			Assert.assertEquals(totalCount + 3, page1.getTotalCount());

			assertContains(
				categoryDisplayPage1,
				(List<CategoryDisplayPage>)page1.getItems());

			Page<CategoryDisplayPage> page2 =
				categoryDisplayPageResource.
					getChannelIdCategoryDisplayPagesPage(
						id, null,
						Pagination.of(
							(int)Math.ceil((totalCount + 2.0) / pageSizeLimit),
							pageSizeLimit),
						null);

			assertContains(
				categoryDisplayPage2,
				(List<CategoryDisplayPage>)page2.getItems());

			Page<CategoryDisplayPage> page3 =
				categoryDisplayPageResource.
					getChannelIdCategoryDisplayPagesPage(
						id, null,
						Pagination.of(
							(int)Math.ceil((totalCount + 3.0) / pageSizeLimit),
							pageSizeLimit),
						null);

			assertContains(
				categoryDisplayPage3,
				(List<CategoryDisplayPage>)page3.getItems());
		}
		else {
			Page<CategoryDisplayPage> page1 =
				categoryDisplayPageResource.
					getChannelIdCategoryDisplayPagesPage(
						id, null, Pagination.of(1, totalCount + 2), null);

			List<CategoryDisplayPage> categoryDisplayPages1 =
				(List<CategoryDisplayPage>)page1.getItems();

			Assert.assertEquals(
				categoryDisplayPages1.toString(), totalCount + 2,
				categoryDisplayPages1.size());

			Page<CategoryDisplayPage> page2 =
				categoryDisplayPageResource.
					getChannelIdCategoryDisplayPagesPage(
						id, null, Pagination.of(2, totalCount + 2), null);

			Assert.assertEquals(totalCount + 1, page2.getTotalCount());

			List<CategoryDisplayPage> categoryDisplayPages2 =
				(List<CategoryDisplayPage>)page2.getItems();

			Assert.assertEquals(
				categoryDisplayPages2.toString(), 1,
				categoryDisplayPages2.size());

			Page<CategoryDisplayPage> page3 =
				categoryDisplayPageResource.
					getChannelIdCategoryDisplayPagesPage(
						id, null, Pagination.of(1, (int)totalCount + 3), null);

			assertContains(
				categoryDisplayPage1,
				(List<CategoryDisplayPage>)page3.getItems());
			assertContains(
				categoryDisplayPage2,
				(List<CategoryDisplayPage>)page3.getItems());
			assertContains(
				categoryDisplayPage3,
				(List<CategoryDisplayPage>)page3.getItems());
		}
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"pageUuid"};
	}

	@Override
	protected CategoryDisplayPage randomCategoryDisplayPage() throws Exception {
		AssetCategory assetCategory = AssetTestUtil.addCategory(
			_user.getGroupId(), _assetVocabulary.getVocabularyId());

		_assetCategories.add(assetCategory);

		Layout layout = LayoutTestUtil.addTypePortletLayout(
			testGroup.getGroupId());

		_layouts.add(layout);

		return new CategoryDisplayPage() {
			{
				categoryId = assetCategory.getCategoryId();
				id = RandomTestUtil.randomLong();
				pageUuid = layout.getUuid();
			}
		};
	}

	@Override
	protected CategoryDisplayPage
			testDeleteCategoryDisplayPage_addCategoryDisplayPage()
		throws Exception {

		return _addChannelCategoryDisplayPage(
			_commerceChannel, randomCategoryDisplayPage());
	}

	@Override
	protected CategoryDisplayPage
			testGetCategoryDisplayPage_addCategoryDisplayPage()
		throws Exception {

		return _addChannelCategoryDisplayPage(
			_commerceChannel, randomCategoryDisplayPage());
	}

	@Override
	protected CategoryDisplayPage
			testGetChannelByExternalReferenceCodeCategoryDisplayPagesPage_addCategoryDisplayPage(
				String externalReferenceCode,
				CategoryDisplayPage categoryDisplayPage)
		throws Exception {

		return _addChannelCategoryDisplayPage(
			externalReferenceCode, categoryDisplayPage);
	}

	@Override
	protected String
			testGetChannelByExternalReferenceCodeCategoryDisplayPagesPage_getExternalReferenceCode()
		throws Exception {

		return _commerceChannel.getExternalReferenceCode();
	}

	@Override
	protected CategoryDisplayPage
			testGetChannelIdCategoryDisplayPagesPage_addCategoryDisplayPage(
				Long id, CategoryDisplayPage categoryDisplayPage)
		throws Exception {

		return _addChannelCategoryDisplayPage(
			_commerceChannel, categoryDisplayPage);
	}

	@Override
	protected Long testGetChannelIdCategoryDisplayPagesPage_getId()
		throws Exception {

		return _commerceChannel.getCommerceChannelId();
	}

	@Override
	protected CategoryDisplayPage
			testGraphQLCategoryDisplayPage_addCategoryDisplayPage()
		throws Exception {

		return _addChannelCategoryDisplayPage(
			_commerceChannel, randomCategoryDisplayPage());
	}

	@Override
	protected CategoryDisplayPage
			testPatchCategoryDisplayPage_addCategoryDisplayPage()
		throws Exception {

		return _addChannelCategoryDisplayPage(
			_commerceChannel, randomCategoryDisplayPage());
	}

	@Override
	protected CategoryDisplayPage
			testPostChannelByExternalReferenceCodeCategoryDisplayPage_addCategoryDisplayPage(
				CategoryDisplayPage categoryDisplayPage)
		throws Exception {

		return _addChannelCategoryDisplayPage(
			_commerceChannel, categoryDisplayPage);
	}

	@Override
	protected CategoryDisplayPage
			testPostChannelIdCategoryDisplayPage_addCategoryDisplayPage(
				CategoryDisplayPage categoryDisplayPage)
		throws Exception {

		return _addChannelCategoryDisplayPage(
			_commerceChannel, categoryDisplayPage);
	}

	private CategoryDisplayPage _addChannelCategoryDisplayPage(
			CommerceChannel commerceChannel,
			CategoryDisplayPage categoryDisplayPage)
		throws Exception {

		CPDisplayLayout cpDisplayLayout =
			_cpDisplayLayoutLocalService.addCPDisplayLayout(
				_user.getUserId(), commerceChannel.getSiteGroupId(),
				AssetCategory.class, categoryDisplayPage.getCategoryId(), null,
				categoryDisplayPage.getPageUuid());

		_cpDisplayLayouts.add(cpDisplayLayout);

		return new CategoryDisplayPage() {
			{
				setCategoryId(cpDisplayLayout::getClassPK);
				setId(cpDisplayLayout::getCPDisplayLayoutId);
				setPageUuid(cpDisplayLayout::getLayoutUuid);
			}
		};
	}

	private CategoryDisplayPage _addChannelCategoryDisplayPage(
			String externalReferenceCode,
			CategoryDisplayPage categoryDisplayPage)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.fetchByExternalReferenceCode(
				externalReferenceCode, testCompany.getCompanyId());

		if (commerceChannel == null) {
			commerceChannel = CommerceTestUtil.addCommerceChannel(
				testGroup.getGroupId(), _commerceCurrency.getCode());

			_commerceChannelLocalService.
				updateCommerceChannelExternalReferenceCode(
					externalReferenceCode,
					commerceChannel.getCommerceChannelId());

			_commerceChannels.add(commerceChannel);
		}

		_commerceChannelLocalService.updateCommerceChannelExternalReferenceCode(
			externalReferenceCode, commerceChannel.getCommerceChannelId());

		return _addChannelCategoryDisplayPage(
			commerceChannel, categoryDisplayPage);
	}

	@DeleteAfterTestRun
	private List<AssetCategory> _assetCategories = new ArrayList<>();

	@DeleteAfterTestRun
	private AssetVocabulary _assetVocabulary;

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@Inject
	private CommerceChannelLocalService _commerceChannelLocalService;

	@DeleteAfterTestRun
	private List<CommerceChannel> _commerceChannels = new ArrayList<>();

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@Inject
	private CPDisplayLayoutLocalService _cpDisplayLayoutLocalService;

	@DeleteAfterTestRun
	private List<CPDisplayLayout> _cpDisplayLayouts = new ArrayList<>();

	@DeleteAfterTestRun
	private List<Layout> _layouts = new ArrayList<>();

	@DeleteAfterTestRun
	private User _user;

}