/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ServiceBeanMethodInvocationFactoryUtil;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.service.EmailAddressLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.EmailAddressPersistence;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;

import java.lang.reflect.Method;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 */
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class ServiceBeanMethodInvocationFactoryImplTest {

	@After
	public void tearDown() throws Exception {
		for (EmailAddress emailAddress : _emailAddresses) {
			EmailAddressLocalServiceUtil.deleteEmailAddress(emailAddress);
		}

		_emailAddresses.clear();
	}

	@Test
	public void testRollback() throws Exception {
		EmailAddress emailAddress1 = newEmailAddress("abc@liferay.com");
		EmailAddress emailAddress2 = newEmailAddress("def@liferay.com");

		_emailAddresses.add(emailAddress1);
		_emailAddresses.add(emailAddress2);

		try {
			ServiceBeanMethodInvocationFactoryUtil.proceed(
				this, ServiceBeanMethodInvocationFactoryImplTest.class,
				getSaveMethod(), new Object[] {_emailAddresses, true});
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

		try {
			ServiceBeanMethodInvocationFactoryUtil.proceed(
				this, ServiceBeanMethodInvocationFactoryImplTest.class,
				getSaveMethod(), new Object[] {_emailAddresses, false});
		}
		catch (Exception e) {
		}

		Assert.assertEquals(
			2, EmailAddressLocalServiceUtil.getEmailAddressesCount());
	}

	protected Method getSaveMethod() {
		Class<?> clazz = getClass();

		try {
			return clazz.getDeclaredMethod(
				"save",
				new Class<?>[] {
					Set.class, boolean.class});
		}
		catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	protected EmailAddress newEmailAddress(String address) throws Exception {
		long pk = ServiceTestUtil.nextLong();

		EmailAddress emailAddress = _emailAddressPersistence.create(pk);

		emailAddress.setAddress(address);

		return emailAddress;
	}

	@Transactional(
		isolation = Isolation.PORTAL, propagation = Propagation.REQUIRES_NEW,
		rollbackFor = {Exception.class}
	)
	protected void save(Set<EmailAddress> emailAddresses, boolean rollback)
		throws Exception {

		for (EmailAddress emailAddress : emailAddresses) {
			_emailAddressPersistence.update(emailAddress);
		}

		if (rollback) {
			throw new Exception("Rollback transaction");
		}
	}

	private Set<EmailAddress> _emailAddresses = new HashSet<EmailAddress>();
	private EmailAddressPersistence _emailAddressPersistence =
		(EmailAddressPersistence)PortalBeanLocatorUtil.locate(
			EmailAddressPersistence.class.getName());

}