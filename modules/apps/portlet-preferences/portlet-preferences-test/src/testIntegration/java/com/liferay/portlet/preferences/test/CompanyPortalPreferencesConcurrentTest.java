/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.preferences.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.PortalPreferenceValue;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.PropsValues;

import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Dante Wang
 */
@RunWith(Arquillian.class)
public class CompanyPortalPreferencesConcurrentTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_company = CompanyTestUtil.addCompany();

		_originalName = PrincipalThreadLocal.getName();

		UserTestUtil.setUser(UserTestUtil.addCompanyAdminUser(_company));

		_countDownLatch1 = new CountDownLatch(1);
		_countDownLatch2 = new CountDownLatch(1);

		Bundle bundle = FrameworkUtil.getBundle(
			CompanyPortalPreferencesConcurrentTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			ModelListener.class,
			new TestModelListener(_countDownLatch1, _countDownLatch2), null);
	}

	@After
	public void tearDown() {
		PrincipalThreadLocal.setName(_originalName);

		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	@Test
	public void test() throws Exception {
		boolean autoLogin = _company.isAutoLogin();

		Assert.assertEquals(PropsValues.COMPANY_SECURITY_AUTO_LOGIN, autoLogin);

		FutureTask<Boolean> futureTask = new FutureTask<>(
			() -> {
				_countDownLatch1.await();

				_prefsProps.getString(
					_company.getCompanyId(),
					PropsKeys.COMPANY_SECURITY_AUTO_LOGIN,
					String.valueOf(PropsValues.COMPANY_SECURITY_AUTO_LOGIN));

				_countDownLatch2.countDown();

				return null;
			});

		Thread thread = new Thread(futureTask);

		thread.start();

		User guestUser = _userLocalService.getGuestUser(
			_company.getCompanyId());

		UnicodeProperties unicodeProperties = new UnicodeProperties(
			HashMapBuilder.put(
				PropsKeys.COMPANY_SECURITY_AUTO_LOGIN,
				String.valueOf(!autoLogin)
			).build(),
			false);

		_companyService.updateCompany(
			_company.getCompanyId(), _company.getVirtualHostname(),
			_company.getMx(), _company.getHomeURL(), true, null,
			_company.getName(), _company.getLegalName(), _company.getLegalId(),
			_company.getLegalType(), _company.getSicCode(),
			_company.getTickerSymbol(), _company.getIndustry(),
			_company.getType(), _company.getSize(), guestUser.getLanguageId(),
			guestUser.getTimeZoneId(), Collections.emptyList(),
			Collections.emptyList(), Collections.emptyList(),
			Collections.emptyList(), unicodeProperties);

		futureTask.get();

		Company company = _companyLocalService.fetchCompany(
			_company.getCompanyId());

		Assert.assertNotEquals(autoLogin, company.isAutoLogin());
	}

	@DeleteAfterTestRun
	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private CompanyService _companyService;

	private CountDownLatch _countDownLatch1;
	private CountDownLatch _countDownLatch2;
	private String _originalName;

	@Inject
	private PrefsProps _prefsProps;

	private ServiceRegistration<?> _serviceRegistration;

	@Inject
	private UserLocalService _userLocalService;

	private static class TestModelListener
		extends BaseModelListener<PortalPreferenceValue> {

		public TestModelListener(
			CountDownLatch countDownLatch1, CountDownLatch countDownLatch2) {

			_countDownLatch1 = countDownLatch1;
			_countDownLatch2 = countDownLatch2;
		}

		@Override
		public Class<?> getModelClass() {
			return PortalPreferenceValue.class;
		}

		@Override
		public void onAfterCreate(PortalPreferenceValue portalPreferenceValue)
			throws ModelListenerException {

			_countDownLatch1.countDown();

			try {
				_countDownLatch2.await(5, TimeUnit.SECONDS);
			}
			catch (InterruptedException interruptedException) {
				ReflectionUtil.throwException(interruptedException);
			}
		}

		private final CountDownLatch _countDownLatch1;
		private final CountDownLatch _countDownLatch2;

	}

}