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
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
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