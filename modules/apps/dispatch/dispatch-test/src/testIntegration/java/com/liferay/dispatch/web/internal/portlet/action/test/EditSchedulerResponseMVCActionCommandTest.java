/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.scheduler.TriggerState;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class EditSchedulerResponseMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(TestPropsValues.getUserId());

		_company = CompanyTestUtil.addCompany();

		_companyAdminPermissionChecker = PermissionCheckerFactoryUtil.create(
			UserTestUtil.getAdminUser(_company.getCompanyId()));

		_omniadminPermissionChecker = PermissionCheckerFactoryUtil.create(
			TestPropsValues.getUser());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		PrincipalThreadLocal.setName(_originalName);
	}

	@After
	public void tearDown() throws Exception {
		for (String jobName : _jobNames) {
			_schedulerEngineHelper.delete(
				jobName, jobName, StorageType.MEMORY_CLUSTERED);
		}
	}

	@Test
	public void testPauseDefaultCompanyJobWithCompanyAdmin() throws Exception {
		String jobName = RandomTestUtil.randomString();

		Message message = new Message();

		message.put("companyId", TestPropsValues.getCompanyId());

		_scheduleJob(jobName, message);

		_assertCompanyAdminCannotPause(jobName);
	}

	@Test
	public void testPauseDispatchTriggerJobWithCompanyAdmin() throws Exception {
		String jobName = StringBundler.concat(
			"DISPATCH_JOB_", RandomTestUtil.randomString(), "@",
			_company.getCompanyId());

		Message message = new Message();

		message.put("companyId", _company.getCompanyId());

		_scheduleJob(jobName, message);

		_assertCompanyAdminCannotPause(jobName);
	}

	@Test
	public void testPauseSystemJobWithCompanyAdmin() throws Exception {
		_scheduleJob(_JOB_NAME_SYSTEM, new Message());

		_assertCompanyAdminCannotPause(_JOB_NAME_SYSTEM);
	}

	@Test
	public void testPauseSystemJobWithOmniadmin() throws Exception {
		_scheduleJob(_JOB_NAME_SYSTEM, new Message());

		_invoke(_JOB_NAME_SYSTEM, "_pause", _omniadminPermissionChecker);

		Assert.assertEquals(
			TriggerState.PAUSED, _getTriggerState(_JOB_NAME_SYSTEM));
	}

	@Test
	public void testRun() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			EditSchedulerResponseMVCActionCommandTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		Destination destination = _destinationFactory.createDestination(
			DestinationConfiguration.createSynchronousDestinationConfiguration(
				_TEST_DESTINATION_NAME));

		Dictionary<String, Object> dictionary = MapUtil.singletonDictionary(
			"destination.name", _TEST_DESTINATION_NAME);

		ServiceRegistration<?> serviceRegistration1 =
			bundleContext.registerService(
				Destination.class, destination, dictionary);

		TestMessageListener testMessageListener = new TestMessageListener();

		ServiceRegistration<?> serviceRegistration2 =
			bundleContext.registerService(
				MessageListener.class, testMessageListener, dictionary);

		Message message = new Message();

		message.put("companyId", _company.getCompanyId());

		_scheduleJob(_JOB_NAME_TEST, message);

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(
					_company.getCompanyId())) {

			_invoke(_JOB_NAME_TEST, "_run", _companyAdminPermissionChecker);

			Assert.assertEquals(
				_company.getCompanyId(),
				testMessageListener.getCompanyIdFromCompanyThreadLocal());
			Assert.assertEquals(
				_company.getCompanyId(),
				testMessageListener.getCompanyIdFromMessage());
		}
		finally {
			serviceRegistration2.unregister();

			serviceRegistration1.unregister();
		}
	}

	@Test
	public void testRunSystemJobWithCompanyAdmin() throws Exception {
		_scheduleJob(_JOB_NAME_SYSTEM, new Message());

		try {
			_invoke(_JOB_NAME_SYSTEM, "_run", _companyAdminPermissionChecker);

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertSame(
				PrincipalException.MustHavePermission.class,
				exception.getClass());
			Assert.assertEquals(
				_getMustHavePermissionMessage(), exception.getMessage());
		}
	}

	private void _assertCompanyAdminCannotPause(String jobName)
		throws Exception {

		try {
			_invoke(jobName, "_pause", _companyAdminPermissionChecker);

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertSame(
				PrincipalException.MustHavePermission.class,
				exception.getClass());
			Assert.assertEquals(
				_getMustHavePermissionMessage(), exception.getMessage());
		}

		Assert.assertEquals(TriggerState.NORMAL, _getTriggerState(jobName));
	}

	private String _getMustHavePermissionMessage() {
		return StringBundler.concat(
			"User ", _companyAdminPermissionChecker.getUserId(),
			" must have UPDATE permission for null ");
	}

	private TriggerState _getTriggerState(String jobName) throws Exception {
		SchedulerResponse schedulerResponse =
			_schedulerEngineHelper.getScheduledJob(
				jobName, jobName, StorageType.MEMORY_CLUSTERED);

		return _schedulerEngineHelper.getJobState(schedulerResponse);
	}

	private void _invoke(
		String jobName, String methodName,
		PermissionChecker permissionChecker) {

		ReflectionTestUtil.invoke(
			_mvcActionCommand, methodName,
			new Class<?>[] {
				PermissionChecker.class, String.class, String.class,
				StorageType.class
			},
			permissionChecker, jobName, jobName, StorageType.MEMORY_CLUSTERED);
	}

	private void _scheduleJob(String jobName, Message message)
		throws Exception {

		_schedulerEngineHelper.schedule(
			_triggerFactory.createTrigger(
				jobName, jobName, null, null, 60, TimeUnit.MINUTE),
			StorageType.MEMORY_CLUSTERED, null, _TEST_DESTINATION_NAME,
			message);

		_jobNames.add(jobName);
	}

	private static final String _JOB_NAME_SYSTEM =
		RandomTestUtil.randomString();

	private static final String _JOB_NAME_TEST =
		EditSchedulerResponseMVCActionCommandTest.class.getName();

	private static final String _TEST_DESTINATION_NAME =
		RandomTestUtil.randomString();

	private static Company _company;
	private static PermissionChecker _companyAdminPermissionChecker;
	private static PermissionChecker _omniadminPermissionChecker;
	private static String _originalName;

	@Inject
	private DestinationFactory _destinationFactory;

	private final List<String> _jobNames = new ArrayList<>();

	@Inject(
		filter = "component.name=com.liferay.dispatch.web.internal.portlet.action.EditSchedulerResponseMVCActionCommand"
	)
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Inject
	private TriggerFactory _triggerFactory;

	private class TestMessageListener extends BaseMessageListener {

		public long getCompanyIdFromCompanyThreadLocal() {
			return _companyIdFromCompanyThreadLocal;
		}

		public long getCompanyIdFromMessage() {
			return _companyIdFromMessage;
		}

		@Override
		protected void doReceive(Message message) {
			_companyIdFromCompanyThreadLocal =
				CompanyThreadLocal.getCompanyId();

			_companyIdFromMessage = message.getLong("companyId");
		}

		private long _companyIdFromCompanyThreadLocal;
		private long _companyIdFromMessage;

	}

}