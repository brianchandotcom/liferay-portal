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

package com.liferay.portal.db.partition.messaging.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.db.partition.DBPartitionUtil;
import com.liferay.portal.db.partition.test.util.BaseDBPartitionTestCase;
import com.liferay.portal.db.partition.test.util.DBPartitionTestUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationInterceptor;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.test.rule.Inject;

import java.util.Dictionary;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Alberto Chaparro
 */
@RunWith(Arquillian.class)
public class DBPartitionMessageBusInterceptorTest
	extends BaseDBPartitionTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			DBPartitionMessageBusInterceptorTest.class);

		_bundleContext = bundle.getBundleContext();

		DBPartitionTestUtil.enableDBPartition();

		_company = CompanyTestUtil.addCompany();

		_currentDatabasePartitionEnabled =
			ReflectionTestUtil.getAndSetFieldValue(
				_dbPartitionDestinationInterceptor, "_databasePartitionEnabled",
				true);

		_currentExcludedMessageBusDestinationNames =
			ReflectionTestUtil.getFieldValue(
				_dbPartitionDestinationInterceptor,
				"_excludedMessageBusDestinationNames");

		_currentExcludedSchedulerJobNames = ReflectionTestUtil.getFieldValue(
			_dbPartitionDestinationInterceptor, "_excludedSchedulerJobNames");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		ReflectionTestUtil.setFieldValue(
			DBPartitionUtil.class, "_DATABASE_PARTITION_ENABLED", false);

		_companyLocalService.deleteCompany(_company);

		ReflectionTestUtil.setFieldValue(
			DBPartitionUtil.class, "_DATABASE_PARTITION_ENABLED", true);

		DBPartitionTestUtil.disableDBPartition();

		ReflectionTestUtil.setFieldValue(
			_dbPartitionDestinationInterceptor, "_databasePartitionEnabled",
			true);

		getDB().runSQL(
			"drop schema " +
				DBPartitionTestUtil.getSchemaName(_company.getCompanyId()));
	}

	@Before
	public void setUp() {
		_testMessageListener = new TestDBPartitionMessageListener();

		_serviceRegistration = _registerDestination(_testMessageListener);
	}

	@After
	public void tearDown() {
		ReflectionTestUtil.setFieldValue(
			_dbPartitionDestinationInterceptor,
			"_excludedMessageBusDestinationNames",
			_currentExcludedMessageBusDestinationNames);

		ReflectionTestUtil.setFieldValue(
			_dbPartitionDestinationInterceptor, "_excludedSchedulerJobNames",
			_currentExcludedSchedulerJobNames);

		Destination destination = _bundleContext.getService(
			_serviceRegistration.getReference());

		_serviceRegistration.unregister();

		destination.destroy();
	}

	@Test
	public void testSendMessage() {
		Message message = new Message();

		message.put("companyId", CompanyConstants.SYSTEM);

		_messageBus.sendMessage(_DESTINATION_NAME, message);

		Assert.assertArrayEquals(
			_getActiveCompanyIds(),
			_testMessageListener.getThreadLocalCompanyIds());
	}

	@Test
	public void testSendMessageExcludingDestination() {
		long currentCompanyId = CompanyThreadLocal.getCompanyId();

		ReflectionTestUtil.setFieldValue(
			_dbPartitionDestinationInterceptor,
			"_excludedMessageBusDestinationNames",
			new HashSet<String>() {
				{
					add(_DESTINATION_NAME);
				}
			});

		_messageBus.sendMessage(_DESTINATION_NAME, new Message());

		Assert.assertArrayEquals(
			new Long[] {currentCompanyId},
			_testMessageListener.getThreadLocalCompanyIds());
	}

	@Test
	public void testSendMessageExcludingScheduledJob() {
		long currentCompanyId = CompanyThreadLocal.getCompanyId();

		Class<?> testDBPartitionMessageListenerClass =
			TestDBPartitionMessageListener.class;

		ReflectionTestUtil.setFieldValue(
			_dbPartitionDestinationInterceptor, "_excludedSchedulerJobNames",
			new HashSet<String>() {
				{
					add(testDBPartitionMessageListenerClass.toString());
				}
			});

		_messageBus.sendMessage(_DESTINATION_NAME, new Message());

		Assert.assertArrayEquals(
			new Long[] {currentCompanyId},
			_testMessageListener.getThreadLocalCompanyIds());
	}

	@Test
	public void testSendMessageWithCompanyId() {
		Message message = new Message();

		message.put("companyId", _company.getCompanyId());

		_messageBus.sendMessage(_DESTINATION_NAME, message);

		Assert.assertArrayEquals(
			new Long[] {_company.getCompanyId()},
			_testMessageListener.getThreadLocalCompanyIds());
	}

	private static Long[] _getActiveCompanyIds() {
		List<Company> companies = _companyLocalService.getCompanies(false);

		Set<Long> companyIds = new TreeSet<>();

		for (Company company : companies) {
			if (company.isActive()) {
				companyIds.add(company.getCompanyId());
			}
		}

		return companyIds.toArray(new Long[0]);
	}

	private ServiceRegistration<Destination> _registerDestination(
		MessageListener messageListener) {

		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_SYNCHRONOUS,
				_DESTINATION_NAME);

		Destination destination = DestinationFactoryUtil.createDestination(
			destinationConfiguration);

		destination.register(messageListener);

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("destination.name", destination.getName());

		return _bundleContext.registerService(
			Destination.class, destination, properties);
	}

	private static final String _DESTINATION_NAME = "liferay/test_dbpartition";

	private static BundleContext _bundleContext;
	private static Company _company;

	@Inject
	private static CompanyLocalService _companyLocalService;

	private static boolean _currentDatabasePartitionEnabled;
	private static Set<String> _currentExcludedMessageBusDestinationNames;
	private static Set<String> _currentExcludedSchedulerJobNames;

	@Inject
	private static DestinationInterceptor _dbPartitionDestinationInterceptor;

	@Inject
	private static MessageBus _messageBus;

	private static ServiceRegistration<Destination> _serviceRegistration;
	private static TestDBPartitionMessageListener _testMessageListener;

	private class TestDBPartitionMessageListener extends BaseMessageListener {

		public Long[] getThreadLocalCompanyIds() {
			return _threadLocalCompanyIds.toArray(new Long[0]);
		}

		@Override
		protected void doReceive(Message message) {
			_threadLocalCompanyIds.add(CompanyThreadLocal.getCompanyId());
		}

		private final Set<Long> _threadLocalCompanyIds = new TreeSet<>();

	}

}