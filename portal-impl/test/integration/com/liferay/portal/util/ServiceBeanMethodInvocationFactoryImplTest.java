/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.util;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ServiceBeanMethodInvocationFactoryUtil;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.service.EmailAddressLocalServiceUtil;
import com.liferay.portal.service.persistence.EmailAddressUtil;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.TestPropsValues;

import java.lang.reflect.Method;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 * @see    OrderByComparatorFactoryImplTest
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class ServiceBeanMethodInvocationFactoryImplTest {

	@Test
	public void testRollback() throws Exception {
		EmailAddress emailAddress1 = newEmailAddress("abc@liferay.com");
		EmailAddress emailAddress2 = newEmailAddress("def@liferay.com");

		_emailAddresses.add(emailAddress1);
		_emailAddresses.add(emailAddress2);

		try {
			ServiceBeanMethodInvocationFactoryUtil.proceed(
				this, ServiceBeanMethodInvocationFactoryImplTest.class,
				getSaveMethod(), new Object[] {true},
				new String[] {"transactionAdvice"});

			Assert.fail();
		}
		catch (Exception e) {
		}

		Assert.assertEquals(
			0, EmailAddressLocalServiceUtil.getEmailAddressesCount());
	}

	@Test
	public void testSave() throws Exception {
		EmailAddress emailAddress1 = newEmailAddress("abc@liferay.com");
		EmailAddress emailAddress2 = newEmailAddress("def@liferay.com");

		_emailAddresses.add(emailAddress1);
		_emailAddresses.add(emailAddress2);

		ServiceBeanMethodInvocationFactoryUtil.proceed(
			this, ServiceBeanMethodInvocationFactoryImplTest.class,
			getSaveMethod(), new Object[] {false},
			new String[] {"transactionAdvice"});

		Assert.assertEquals(
			2, EmailAddressLocalServiceUtil.getEmailAddressesCount());
	}

	protected Method getSaveMethod() throws Exception {
		Class<?> clazz = getClass();

		return clazz.getDeclaredMethod("save", new Class<?>[] {boolean.class});
	}

	protected EmailAddress newEmailAddress(String address) throws Exception {
		long emailAddressId = RandomTestUtil.nextLong();

		EmailAddress emailAddress = EmailAddressUtil.create(emailAddressId);

		emailAddress.setCompanyId(TestPropsValues.getCompanyId());
		emailAddress.setAddress(address);

		return emailAddress;
	}

	@Transactional(
		isolation = Isolation.PORTAL, propagation = Propagation.REQUIRES_NEW,
		rollbackFor = {Exception.class}
	)
	protected void save(boolean rollback) throws Exception {
		for (EmailAddress emailAddress : _emailAddresses) {
			EmailAddressUtil.update(emailAddress);
		}

		if (rollback) {
			throw new Exception();
		}
	}

	@DeleteAfterTestRun
	private final Set<EmailAddress> _emailAddresses =
		new HashSet<EmailAddress>();

}