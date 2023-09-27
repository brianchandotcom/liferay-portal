/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.channel.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRelQualifier;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelLocalService;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelQualifierLocalService;
import com.liferay.commerce.payment.test.util.TestCommercePaymentMethod;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionQualifier;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionLocalService;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionQualifierLocalService;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.service.CommerceTermEntryLocalService;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.headless.commerce.admin.channel.client.dto.v1_0.Term;
import com.liferay.headless.commerce.core.util.DateConfig;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Andrea Sbarra
 */
@RunWith(Arquillian.class)
public class TermResourceTest extends BaseTermResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			testGroup.getCompanyId());

		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			testGroup.getGroupId(), _commerceCurrency.getCode());

		_user = UserTestUtil.addUser(testCompany);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name"};
	}

	@Override
	protected Term testGetPaymentMethodGroupRelTermTerm_addTerm()
		throws Exception {

		return _toTerm(_addCommerceTermEntry());
	}

	@Override
	protected Long
			testGetPaymentMethodGroupRelTermTerm_getPaymentMethodGroupRelTermId()
		throws Exception {

		return _getPaymentMethodGroupRelTermId();
	}

	@Override
	protected Term testGetShippingFixedOptionTermTerm_addTerm()
		throws Exception {

		return _toTerm(_addCommerceTermEntry());
	}

	@Override
	protected Long
			testGetShippingFixedOptionTermTerm_getShippingFixedOptionTermId()
		throws Exception {

		return _getShippingFixedOptionTermId();
	}

	@Override
	protected Long
			testGraphQLGetPaymentMethodGroupRelTermTerm_getPaymentMethodGroupRelTermId()
		throws Exception {

		return _getPaymentMethodGroupRelTermId();
	}

	@Override
	protected Long
			testGraphQLGetShippingFixedOptionTermTerm_getShippingFixedOptionTermId()
		throws Exception {

		return _getShippingFixedOptionTermId();
	}

	@Override
	protected Term testGraphQLTerm_addTerm() throws Exception {
		return _toTerm(_addCommerceTermEntry());
	}

	private CommerceTermEntry _addCommerceTermEntry() throws Exception {
		if (_commerceTermEntry != null) {
			return _commerceTermEntry;
		}

		DateConfig displayDateConfig = DateConfig.toDisplayDateConfig(
			RandomTestUtil.nextDate(), _user.getTimeZone());
		DateConfig expirationDateConfig = DateConfig.toExpirationDateConfig(
			RandomTestUtil.nextDate(), _user.getTimeZone());

		_commerceTermEntry =
			_commerceTermEntryLocalService.addCommerceTermEntry(
				RandomTestUtil.randomString(), _user.getUserId(),
				RandomTestUtil.randomBoolean(),
				RandomTestUtil.randomLocaleStringMap(),
				displayDateConfig.getMonth(), displayDateConfig.getDay(),
				displayDateConfig.getYear(), displayDateConfig.getHour(),
				displayDateConfig.getMinute(), expirationDateConfig.getMonth(),
				expirationDateConfig.getDay(), expirationDateConfig.getYear(),
				expirationDateConfig.getHour(),
				expirationDateConfig.getMinute(), true,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), RandomTestUtil.nextDouble(),
				RandomTestUtil.randomString(), StringPool.BLANK,
				_serviceContext);

		return _commerceTermEntry;
	}

	private long _getPaymentMethodGroupRelTermId() throws Exception {
		_commercePaymentMethodGroupRel =
			_commercePaymentMethodGroupRelLocalService.
				addCommercePaymentMethodGroupRel(
					_user.getUserId(), _commerceChannel.getGroupId(),
					RandomTestUtil.randomLocaleStringMap(),
					RandomTestUtil.randomLocaleStringMap(), true, null,
					TestCommercePaymentMethod.KEY, 99, null);

		Term term = _toTerm(_addCommerceTermEntry());

		_commercePaymentMethodGroupRelQualifier =
			_commercePaymentMethodGroupRelQualifierLocalService.
				addCommercePaymentMethodGroupRelQualifier(
					_user.getUserId(), CommerceOrderType.class.getName(),
					term.getId(),
					_commercePaymentMethodGroupRel.
						getCommercePaymentMethodGroupRelId());

		return _commercePaymentMethodGroupRelQualifier.
			getCommercePaymentMethodGroupRelQualifierId();
	}

	private long _getShippingFixedOptionTermId() throws Exception {
		if (_commerceShippingFixedOption == null) {
			_commerceShippingFixedOption =
				_commerceShippingFixedOptionLocalService.
					addCommerceShippingFixedOption(
						_user.getUserId(), _commerceChannel.getGroupId(),
						RandomTestUtil.nextLong(),
						BigDecimal.valueOf(RandomTestUtil.nextDouble()),
						RandomTestUtil.randomLocaleStringMap(),
						RandomTestUtil.randomString(),
						RandomTestUtil.randomLocaleStringMap(),
						RandomTestUtil.nextDouble());
		}

		Term term = _toTerm(_addCommerceTermEntry());

		CommerceShippingFixedOptionQualifier
			commerceShippingFixedOptionQualifier =
				_commerceShippingFixedOptionQualifierLocalService.
					addCommerceShippingFixedOptionQualifier(
						_user.getUserId(), CommerceOrderType.class.getName(),
						term.getId(),
						_commerceShippingFixedOption.
							getCommerceShippingFixedOptionId());

		return commerceShippingFixedOptionQualifier.
			getCommerceShippingFixedOptionQualifierId();
	}

	private Term _toTerm(CommerceTermEntry commerceTermEntry) {
		return new Term() {
			{
				id = commerceTermEntry.getCommerceTermEntryId();
				name = commerceTermEntry.getName();
			}
		};
	}

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@DeleteAfterTestRun
	private CommercePaymentMethodGroupRel _commercePaymentMethodGroupRel;

	@Inject
	private CommercePaymentMethodGroupRelLocalService
		_commercePaymentMethodGroupRelLocalService;

	@DeleteAfterTestRun
	private CommercePaymentMethodGroupRelQualifier
		_commercePaymentMethodGroupRelQualifier;

	@Inject
	private CommercePaymentMethodGroupRelQualifierLocalService
		_commercePaymentMethodGroupRelQualifierLocalService;

	@DeleteAfterTestRun
	private CommerceShippingFixedOption _commerceShippingFixedOption;

	@Inject
	private CommerceShippingFixedOptionLocalService
		_commerceShippingFixedOptionLocalService;

	@Inject
	private CommerceShippingFixedOptionQualifierLocalService
		_commerceShippingFixedOptionQualifierLocalService;

	@DeleteAfterTestRun
	private CommerceTermEntry _commerceTermEntry;

	@Inject
	private CommerceTermEntryLocalService _commerceTermEntryLocalService;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}