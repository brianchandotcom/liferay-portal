/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.channel.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.service.CommerceShippingMethodLocalServiceUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.headless.commerce.admin.channel.client.dto.v1_0.ShippingMethod;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Andrea Sbarra
 * @author Crescenzo Rega
 */
@RunWith(Arquillian.class)
public class ShippingMethodResourceTest
	extends BaseShippingMethodResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			testGroup.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			testGroup.getGroupId(), _commerceCurrency.getCode());

		_shippingMethods = new ArrayList<>(
			Arrays.asList(
				new ShippingMethod() {
					{
						active = Boolean.TRUE;
						description = LanguageUtils.getLanguageIdMap(
							Collections.singletonMap(
								LocaleUtil.US, "fedex-description"));
						engineKey = "fedex";
						name = LanguageUtils.getLanguageIdMap(
							Collections.singletonMap(LocaleUtil.US, "fedex"));
					}
				},
				new ShippingMethod() {
					{
						active = Boolean.TRUE;
						description = LanguageUtils.getLanguageIdMap(
							Collections.singletonMap(
								LocaleUtil.US, "fixed-shipping-description"));
						engineKey = "fixed";
						name = LanguageUtils.getLanguageIdMap(
							Collections.singletonMap(
								LocaleUtil.US, "flat-rate"));
					}
				},
				new ShippingMethod() {
					{
						active = Boolean.TRUE;
						description = LanguageUtils.getLanguageIdMap(
							Collections.singletonMap(
								LocaleUtil.US, "by-weight-description"));
						engineKey = "by-weight";
						name = LanguageUtils.getLanguageIdMap(
							Collections.singletonMap(
								LocaleUtil.US, "variable-rate"));
					}
				}));
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		_shippingMethods.clear();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"active", "engineKey", "name", "description"};
	}

	@Override
	protected ShippingMethod randomShippingMethod() throws Exception {
		Random random = new Random();

		return _shippingMethods.remove(random.nextInt(_shippingMethods.size()));
	}

	@Override
	protected ShippingMethod
			testGetChannelShippingMethodsPage_addShippingMethod(
				Long channelId, ShippingMethod shippingMethod)
		throws Exception {

		CommerceShippingMethod commerceShippingMethod =
			CommerceShippingMethodLocalServiceUtil.addCommerceShippingMethod(
				_user.getUserId(), _commerceChannel.getGroupId(),
				LanguageUtils.getLocalizedMap(shippingMethod.getName()),
				LanguageUtils.getLocalizedMap(shippingMethod.getDescription()),
				shippingMethod.getActive(), shippingMethod.getEngineKey(), null,
				1, RandomTestUtil.randomString());

		_commerceShippingMethods.add(commerceShippingMethod);

		return new ShippingMethod() {
			{
				active = commerceShippingMethod.isActive();
				description = LanguageUtils.getLanguageIdMap(
					commerceShippingMethod.getDescriptionMap());
				engineKey = commerceShippingMethod.getEngineKey();
				name = LanguageUtils.getLanguageIdMap(
					commerceShippingMethod.getNameMap());
			}
		};
	}

	@Override
	protected Long testGetChannelShippingMethodsPage_getChannelId()
		throws Exception {

		return _commerceChannel.getCommerceChannelId();
	}

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@DeleteAfterTestRun
	private List<CommerceShippingMethod> _commerceShippingMethods =
		new ArrayList<>();

	private List<ShippingMethod> _shippingMethods;

	@DeleteAfterTestRun
	private User _user;

}