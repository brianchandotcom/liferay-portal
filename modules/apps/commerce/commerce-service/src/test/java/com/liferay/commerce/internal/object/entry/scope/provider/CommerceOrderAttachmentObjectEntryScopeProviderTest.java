/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.object.entry.scope.provider;

import com.liferay.commerce.exception.NoSuchOrderException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Stefano Motta
 */
public class CommerceOrderAttachmentObjectEntryScopeProviderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testGetGroupId() throws Exception {
		Mockito.when(
			_commerceOrderLocalService.getCommerceOrder(0)
		).thenThrow(
			new NoSuchOrderException()
		);

		try {
			_commerceOrderAttachmentObjectEntryScopeProvider.getGroupId(_user);

			Assert.fail();
		}
		catch (NoSuchOrderException noSuchOrderException) {
			Assert.assertNotNull(noSuchOrderException);
		}

		long commerceOrderId = RandomTestUtil.randomLong();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAttribute("commerceOrderId", commerceOrderId);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		Mockito.when(
			_commerceOrderLocalService.getCommerceOrder(commerceOrderId)
		).thenThrow(
			new NoSuchOrderException()
		);

		try {
			_commerceOrderAttachmentObjectEntryScopeProvider.getGroupId(_user);

			Assert.fail();
		}
		catch (NoSuchOrderException noSuchOrderException) {
			Assert.assertNotNull(noSuchOrderException);
		}

		Mockito.doReturn(
			_commerceOrder
		).when(
			_commerceOrderLocalService
		).getCommerceOrder(
			commerceOrderId
		);

		long companyId = RandomTestUtil.randomLong();

		Mockito.when(
			_user.getCompanyId()
		).thenReturn(
			companyId
		);

		Mockito.when(
			_commerceOrder.getCompanyId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		try {
			_commerceOrderAttachmentObjectEntryScopeProvider.getGroupId(_user);

			Assert.fail();
		}
		catch (NoSuchOrderException noSuchOrderException) {
			Assert.assertNotNull(noSuchOrderException);
		}

		Mockito.when(
			_commerceOrder.getCompanyId()
		).thenReturn(
			companyId
		);

		long groupId = RandomTestUtil.randomLong();

		Mockito.when(
			_commerceOrder.getGroupId()
		).thenReturn(
			groupId
		);

		Assert.assertEquals(
			String.valueOf(groupId),
			_commerceOrderAttachmentObjectEntryScopeProvider.getGroupId(_user));
	}

	@Mock
	private CommerceOrder _commerceOrder;

	@InjectMocks
	private CommerceOrderAttachmentObjectEntryScopeProvider
		_commerceOrderAttachmentObjectEntryScopeProvider;

	@Mock
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Mock
	private User _user;

}