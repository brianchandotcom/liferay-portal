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

package com.liferay.portal.kernel.security.auth;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnvTestRule;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Alberto Chaparro
 */
@PrepareForTest(ServiceProxyFactory.class)
@RunWith(PowerMockRunner.class)
public class CompanyThreadLocalTest extends PowerMockito {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, NewEnvTestRule.INSTANCE);

	@Before
	public void setUp() {
		mockStatic(ServiceProxyFactory.class);

		when(
			ServiceProxyFactory.newServiceTrackedInstance(
				Mockito.anyObject(), Mockito.anyObject(), Mockito.anyString(),
				Mockito.anyBoolean(), Mockito.anyBoolean())
		).thenReturn(
			null
		);
	}

	@Test
	public void testLock() {
		_testLock(companyId -> CompanyThreadLocal.setCompanyId(companyId));
	}

	@Test
	public void testLockWithSetWithSafeCloseable() {
		_testLock(
			companyId -> CompanyThreadLocal.setWithSafeCloseable(companyId));
	}

	private void _testLock(Consumer<Long> consumer) {
		SafeCloseable safeCloseable = CompanyThreadLocal.lock(
			CompanyConstants.SYSTEM);

		try {
			consumer.accept(1L);
		}
		catch (UnsupportedOperationException unsupportedOperationException) {
			return;
		}
		finally {
			safeCloseable.close();
		}

		Assert.fail("Should throw UnsupportedOperationException");
	}

}