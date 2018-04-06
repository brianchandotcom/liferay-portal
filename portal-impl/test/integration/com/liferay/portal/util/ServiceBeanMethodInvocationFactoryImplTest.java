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

import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.service.EmailAddressLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.EmailAddressUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ServiceBeanMethodInvocationFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.lang.reflect.Method;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 * @see    OrderByComparatorFactoryImplTest
 */
public class ServiceBeanMethodInvocationFactoryImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

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
		rollbackFor = Exception.class
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
	private final Set<EmailAddress> _emailAddresses = new HashSet<>();

}