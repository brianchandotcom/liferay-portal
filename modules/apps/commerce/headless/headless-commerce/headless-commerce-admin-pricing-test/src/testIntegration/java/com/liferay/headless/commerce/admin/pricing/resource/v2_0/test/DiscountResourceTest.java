/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.pricing.resource.v2_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.discount.constants.CommerceDiscountConstants;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.service.CommerceDiscountLocalService;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelRelLocalServiceUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.Discount;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Page;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Pagination;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Fabio Monaco
 */
@RunWith(Arquillian.class)
public class DiscountResourceTest extends BaseDiscountResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			testCompany.getCompanyId());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			UserTestUtil.addUser(
				testCompany
			).getUserId());
	}

	@Override
	@Test
	public void testGetDiscountsPage() throws Exception {
		super.testGetDiscountsPage();

		_testGetDiscountsPageWithChannelFilter();
	}

	@Ignore
	@Override
	@Test
	public void testGetDiscountsPageWithFilterDateTimeEquals()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetDiscountsPageWithFilterStringContains()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetDiscountsPageWithFilterStringEquals() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetDiscountsPageWithFilterStringStartsWith()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetDiscountsPageWithSortString() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetDiscount() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetDiscountByExternalReferenceCode()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetDiscountByExternalReferenceCodeNotFound()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetDiscountNotFound() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetDiscountsPage() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testPutDiscountByExternalReferenceCode() throws Exception {
	}

	@Override
	protected Discount randomDiscount() throws Exception {
		Discount discount = super.randomDiscount();

		discount.setLevel(CommerceDiscountConstants.LEVEL_L1);
		discount.setLimitationType(
			CommerceDiscountConstants.LIMITATION_TYPE_UNLIMITED);
		discount.setNeverExpire(true);
		discount.setTarget(CommerceDiscountConstants.TARGET_TOTAL);
		discount.setUseCouponCode(false);
		discount.setUsePercentage(false);

		return discount;
	}

	@Override
	protected Discount testDeleteDiscount_addDiscount() throws Exception {
		return discountResource.postDiscount(randomDiscount());
	}

	@Override
	protected Discount testDeleteDiscountByExternalReferenceCode_addDiscount()
		throws Exception {

		return discountResource.postDiscount(randomDiscount());
	}

	@Override
	protected Discount testGetDiscount_addDiscount() throws Exception {
		return discountResource.postDiscount(randomDiscount());
	}

	@Override
	protected Discount testGetDiscountByExternalReferenceCode_addDiscount()
		throws Exception {

		return discountResource.postDiscount(randomDiscount());
	}

	@Override
	protected Discount testGetDiscountsPage_addDiscount(Discount discount)
		throws Exception {

		return discountResource.postDiscount(discount);
	}

	@Override
	protected Discount testPatchDiscount_addDiscount() throws Exception {
		return discountResource.postDiscount(randomDiscount());
	}

	@Override
	protected Discount testPatchDiscountByExternalReferenceCode_addDiscount()
		throws Exception {

		return discountResource.postDiscount(randomDiscount());
	}

	@Override
	protected Discount testPostDiscount_addDiscount(Discount discount)
		throws Exception {

		return discountResource.postDiscount(discount);
	}

	@Override
	protected Discount testPutDiscountByExternalReferenceCode_addDiscount()
		throws Exception {

		return discountResource.postDiscount(randomDiscount());
	}

	private void _testGetDiscountsPageWithChannelFilter() throws Exception {
		CommerceChannel commerceChannel1 = CommerceTestUtil.addCommerceChannel(
			testGroup.getGroupId(), _commerceCurrency.getCode());
		CommerceChannel commerceChannel2 = CommerceTestUtil.addCommerceChannel(
			testGroup.getGroupId(), _commerceCurrency.getCode());

		_commerceChannels.add(commerceChannel1);
		_commerceChannels.add(commerceChannel2);

		Discount discount1 = discountResource.postDiscount(randomDiscount());
		Discount discount2 = discountResource.postDiscount(randomDiscount());

		_commerceDiscounts.add(
			_commerceDiscountLocalService.getCommerceDiscount(
				discount1.getId()));
		_commerceDiscounts.add(
			_commerceDiscountLocalService.getCommerceDiscount(
				discount2.getId()));

		CommerceChannelRelLocalServiceUtil.addCommerceChannelRel(
			CommerceDiscount.class.getName(), discount1.getId(),
			commerceChannel1.getCommerceChannelId(), _serviceContext);
		CommerceChannelRelLocalServiceUtil.addCommerceChannelRel(
			CommerceDiscount.class.getName(), discount2.getId(),
			commerceChannel2.getCommerceChannelId(), _serviceContext);

		Indexer<CommerceDiscount> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CommerceDiscount.class);

		indexer.reindex(CommerceDiscount.class.getName(), discount1.getId());
		indexer.reindex(CommerceDiscount.class.getName(), discount2.getId());

		Page<Discount> page = discountResource.getDiscountsPage(
			null,
			String.format(
				"(channelId/any(x:(x eq %s)))",
				commerceChannel1.getCommerceChannelId()),
			Pagination.of(1, 10), null);

		assertEquals(
			Collections.singletonList(discount1),
			(List<Discount>)page.getItems());
	}

	@DeleteAfterTestRun
	private List<CommerceChannel> _commerceChannels = new ArrayList<>();

	private CommerceCurrency _commerceCurrency;

	@Inject
	private CommerceDiscountLocalService _commerceDiscountLocalService;

	@DeleteAfterTestRun
	private List<CommerceDiscount> _commerceDiscounts = new ArrayList<>();

	private ServiceContext _serviceContext;

}