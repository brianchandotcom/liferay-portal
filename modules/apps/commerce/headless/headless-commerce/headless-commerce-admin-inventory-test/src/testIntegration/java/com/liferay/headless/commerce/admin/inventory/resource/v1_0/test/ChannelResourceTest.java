/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.inventory.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.commerce.product.service.CommerceChannelRelLocalServiceUtil;
import com.liferay.commerce.test.util.CommerceInventoryTestUtil;
import com.liferay.commerce.test.util.CommerceTestUtil;
import com.liferay.headless.commerce.admin.inventory.client.dto.v1_0.Channel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Crescenzo Rega
 */
@RunWith(Arquillian.class)
public class ChannelResourceTest extends BaseChannelResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_commerceCurrency = CommerceCurrencyTestUtil.addCommerceCurrency(
			testGroup.getCompanyId());

		_user = UserTestUtil.addUser(testCompany);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());

		_commerceInventoryWarehouse =
			CommerceInventoryTestUtil.addCommerceInventoryWarehouse(
				_serviceContext);
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetWarehouseChannelChannel() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetWarehouseChannelChannelNotFound()
		throws Exception {
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"externalReferenceCode", "name", "siteGroupId", "type"
		};
	}

	@Override
	protected Channel testGetWarehouseChannelChannel_addChannel()
		throws Exception {

		return _addChannel();
	}

	@Override
	protected Long testGetWarehouseChannelChannel_getWarehouseChannelId()
		throws Exception {

		return _getWarehouseChannelId();
	}

	@Override
	protected Channel testGraphQLChannel_addChannel() throws Exception {
		return _addChannel();
	}

	@Override
	protected Long testGraphQLGetWarehouseChannelChannel_getWarehouseChannelId()
		throws Exception {

		return _getWarehouseChannelId();
	}

	private Channel _addChannel() throws Exception {
		_commerceChannel = CommerceTestUtil.addCommerceChannel(
			testGroup.getGroupId(), _commerceCurrency.getCode());

		return new Channel() {
			{
				currencyCode = _commerceChannel.getCommerceCurrencyCode();
				externalReferenceCode =
					_commerceChannel.getExternalReferenceCode();
				id = _commerceChannel.getCommerceChannelId();
				name = _commerceChannel.getName();
				siteGroupId = _commerceChannel.getSiteGroupId();
				type = _commerceChannel.getType();
			}
		};
	}

	private long _getWarehouseChannelId() throws Exception {
		if (_commerceChannelRel != null) {
			return _commerceChannelRel.getCommerceChannelRelId();
		}

		_commerceChannelRel =
			CommerceChannelRelLocalServiceUtil.addCommerceChannelRel(
				CommerceInventoryWarehouse.class.getName(),
				_commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
				_commerceChannel.getCommerceChannelId(), _serviceContext);

		return _commerceChannelRel.getCommerceChannelRelId();
	}

	@DeleteAfterTestRun
	private CommerceChannel _commerceChannel;

	@DeleteAfterTestRun
	private CommerceChannelRel _commerceChannelRel;

	@DeleteAfterTestRun
	private CommerceCurrency _commerceCurrency;

	@DeleteAfterTestRun
	private CommerceInventoryWarehouse _commerceInventoryWarehouse;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}