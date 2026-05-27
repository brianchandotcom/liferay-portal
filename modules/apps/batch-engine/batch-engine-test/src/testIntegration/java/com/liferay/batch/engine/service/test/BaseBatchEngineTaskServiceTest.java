/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.service.test;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Vendel Toreki
 */
public class BaseBatchEngineTaskServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		defaultCompany = CompanyLocalServiceUtil.getCompany(
			TestPropsValues.getCompanyId());
		omniadminUser = TestPropsValues.getUser();

		company = CompanyTestUtil.addCompany();

		companyAdminUser = UserTestUtil.addCompanyAdminUser(company);

		user = UserTestUtil.addUser(company);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		CompanyLocalServiceUtil.deleteCompany(company);
	}

	protected void assertConcurrentRunnables(
			CheckedRunnable runnable1, CheckedRunnable runnable2)
		throws Exception {

		AtomicReference<Throwable> throwableReference = new AtomicReference<>();

		ExecutorService executorService = Executors.newFixedThreadPool(2);

		Future<?> future1 = executorService.submit(
			() -> {
				try {
					runnable1.run();
				}
				catch (Throwable throwable) {
					throwableReference.compareAndSet(null, throwable);
				}
			});

		Future<?> future2 = executorService.submit(
			() -> {
				try {
					runnable2.run();
				}
				catch (Throwable throwable) {
					throwableReference.compareAndSet(null, throwable);
				}
			});

		try {
			future1.get(10, TimeUnit.SECONDS);
			future2.get(10, TimeUnit.SECONDS);
		}
		catch (TimeoutException timeoutException) {
			throw new AssertionError(timeoutException);
		}
		finally {
			future1.cancel(true);
			future2.cancel(true);

			executorService.shutdownNow();
		}

		Assert.assertNull(throwableReference.get());
	}

	protected static final TransactionConfig REQUIRED_TRANSACTION_CONFIG =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	protected static Company company;
	protected static User companyAdminUser;
	protected static Company defaultCompany;
	protected static User omniadminUser;
	protected static User user;

	@FunctionalInterface
	protected interface CheckedRunnable {

		public void run() throws Throwable;

	}

}