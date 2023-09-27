/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceLocalServiceUtil;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.SkuSubscriptionConfiguration;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Crescenzo Rega
 */
@Ignore
@RunWith(Arquillian.class)
public class SkuSubscriptionConfigurationResourceTest
	extends BaseSkuSubscriptionConfigurationResourceTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);
	}

	@Override
	@Test
	public void testGetSkuByExternalReferenceCodeSkuSubscriptionConfiguration()
		throws Exception {

		SkuSubscriptionConfiguration addSkuSubscriptionConfiguration =
			_addSkuSubscriptionConfiguration(
				randomSkuSubscriptionConfiguration());

		SkuSubscriptionConfiguration
			skuByExternalReferenceCodeSkuSubscriptionConfiguration =
				skuSubscriptionConfigurationResource.
					getSkuByExternalReferenceCodeSkuSubscriptionConfiguration(
						_cpInstance.getExternalReferenceCode());

		assertValid(skuByExternalReferenceCodeSkuSubscriptionConfiguration);
		assertEquals(
			addSkuSubscriptionConfiguration,
			skuByExternalReferenceCodeSkuSubscriptionConfiguration);
	}

	@Override
	@Test
	public void testGetSkuIdSkuSubscriptionConfiguration() throws Exception {
		SkuSubscriptionConfiguration addSkuSubscriptionConfiguration =
			_addSkuSubscriptionConfiguration(
				randomSkuSubscriptionConfiguration());

		SkuSubscriptionConfiguration idSkuSubscriptionConfiguration =
			skuSubscriptionConfigurationResource.
				getSkuIdSkuSubscriptionConfiguration(
					_cpInstance.getCPInstanceId());

		assertValid(idSkuSubscriptionConfiguration);
		assertEquals(
			addSkuSubscriptionConfiguration, idSkuSubscriptionConfiguration);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"deliverySubscriptionEnable", "deliverySubscriptionLength",
			"deliverySubscriptionNumberOfLength", "deliverySubscriptionType",
			"enable", "length", "numberOfLength", "overrideSubscriptionInfo",
			"subscriptionType"
		};
	}

	@Override
	protected SkuSubscriptionConfiguration randomSkuSubscriptionConfiguration()
		throws Exception {

		return new SkuSubscriptionConfiguration() {
			{
				deliverySubscriptionEnable = RandomTestUtil.randomBoolean();
				deliverySubscriptionLength = RandomTestUtil.randomInt();
				deliverySubscriptionNumberOfLength =
					RandomTestUtil.randomLong();

				deliverySubscriptionType = DeliverySubscriptionType.create(
					_SUBSCRIPTIONTYPES
						[RandomTestUtil.randomInt(
							0, _SUBSCRIPTIONTYPES.length - 1)]);

				enable = RandomTestUtil.randomBoolean();
				length = RandomTestUtil.randomInt();
				numberOfLength = RandomTestUtil.randomLong();
				overrideSubscriptionInfo = RandomTestUtil.randomBoolean();
				subscriptionType = SubscriptionType.create(
					_SUBSCRIPTIONTYPES
						[RandomTestUtil.randomInt(
							0, _SUBSCRIPTIONTYPES.length - 1)]);
			}
		};
	}

	private SkuSubscriptionConfiguration _addSkuSubscriptionConfiguration(
			SkuSubscriptionConfiguration skuSubscriptionConfiguration)
		throws Exception {

		_cpInstance = CPTestUtil.addCPInstance(_user.getGroupId());

		_cpInstance.setOverrideSubscriptionInfo(
			skuSubscriptionConfiguration.getOverrideSubscriptionInfo());
		_cpInstance.setSubscriptionEnabled(
			skuSubscriptionConfiguration.getEnable());
		_cpInstance.setSubscriptionLength(
			skuSubscriptionConfiguration.getLength());
		_cpInstance.setSubscriptionType(
			skuSubscriptionConfiguration.getSubscriptionTypeAsString());
		_cpInstance.setMaxSubscriptionCycles(
			skuSubscriptionConfiguration.getNumberOfLength());
		_cpInstance.setDeliverySubscriptionEnabled(
			skuSubscriptionConfiguration.getDeliverySubscriptionEnable());
		_cpInstance.setDeliverySubscriptionLength(
			skuSubscriptionConfiguration.getDeliverySubscriptionLength());
		_cpInstance.setDeliverySubscriptionType(
			skuSubscriptionConfiguration.getSubscriptionTypeAsString());
		_cpInstance.setDeliveryMaxSubscriptionCycles(
			skuSubscriptionConfiguration.
				getDeliverySubscriptionNumberOfLength());

		_cpInstance = CPInstanceLocalServiceUtil.updateCPInstance(_cpInstance);

		return _toSkuSubscriptionConfiguration();
	}

	private SkuSubscriptionConfiguration _toSkuSubscriptionConfiguration() {
		return new SkuSubscriptionConfiguration() {
			{
				deliverySubscriptionEnable =
					_cpInstance.isDeliverySubscriptionEnabled();
				deliverySubscriptionLength =
					_cpInstance.getDeliverySubscriptionLength();
				deliverySubscriptionNumberOfLength =
					_cpInstance.getDeliveryMaxSubscriptionCycles();

				deliverySubscriptionType =
					SkuSubscriptionConfiguration.DeliverySubscriptionType.
						create(_cpInstance.getSubscriptionType());
				deliverySubscriptionTypeSettings =
					_cpInstance.
						getDeliverySubscriptionTypeSettingsUnicodeProperties();
				enable = _cpInstance.isSubscriptionEnabled();
				length = _cpInstance.getSubscriptionLength();
				numberOfLength = _cpInstance.getMaxSubscriptionCycles();
				overrideSubscriptionInfo =
					_cpInstance.isOverrideSubscriptionInfo();
				subscriptionType = SubscriptionType.create(
					_cpInstance.getSubscriptionType());
				subscriptionTypeSettings =
					_cpInstance.getSubscriptionTypeSettingsUnicodeProperties();
			}
		};
	}

	private static final String[] _SUBSCRIPTIONTYPES = {
		"daily", "monthly", "weekly", "yearly"
	};

	@DeleteAfterTestRun
	private CPInstance _cpInstance;

	@DeleteAfterTestRun
	private User _user;

}