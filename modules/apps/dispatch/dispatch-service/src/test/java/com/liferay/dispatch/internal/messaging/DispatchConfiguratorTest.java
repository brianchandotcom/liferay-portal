/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.internal.messaging;

import com.liferay.dispatch.executor.DispatchTaskClusterMode;
import com.liferay.dispatch.internal.helper.DispatchTriggerHelper;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutor;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Vendel Toreki
 */
public class DispatchConfiguratorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		Mockito.when(
			_destinationFactory.createDestination(Mockito.any())
		).thenReturn(
			Mockito.mock(Destination.class)
		);

		Mockito.when(
			_dispatchTriggerLocalService.getDispatchTriggers(
				true, DispatchTaskClusterMode.ALL_NODES)
		).thenReturn(
			ListUtil.fromArray(_allNodesDispatchTrigger)
		);

		Mockito.when(
			_dispatchTriggerLocalService.getDispatchTriggers(
				true, DispatchTaskClusterMode.SINGLE_NODE_MEMORY_CLUSTERED)
		).thenReturn(
			ListUtil.fromArray(_singleNodeMemoryClusteredDispatchTrigger)
		);

		Mockito.when(
			_dispatchTriggerLocalService.getDispatchTriggers(
				true, DispatchTaskClusterMode.SINGLE_NODE_PERSISTED)
		).thenReturn(
			ListUtil.fromArray(_singleNodePersistedDispatchTrigger)
		);
	}

	@Test
	public void testOnActivationSchedulesAllTypesOfJobsOnMasterNode()
		throws Exception {

		Mockito.when(
			_clusterMasterExecutor.isMaster()
		).thenReturn(
			true
		);

		_dispatchConfigurator.activate(_bundleContext);

		Mockito.verify(
			_dispatchTriggerLocalService
		).getDispatchTriggers(
			Mockito.eq(true), Mockito.eq(DispatchTaskClusterMode.ALL_NODES)
		);

		Mockito.verify(
			_dispatchTriggerLocalService
		).getDispatchTriggers(
			Mockito.eq(true),
			Mockito.eq(DispatchTaskClusterMode.SINGLE_NODE_MEMORY_CLUSTERED)
		);

		Mockito.verify(
			_dispatchTriggerLocalService
		).getDispatchTriggers(
			Mockito.eq(true),
			Mockito.eq(DispatchTaskClusterMode.SINGLE_NODE_PERSISTED)
		);

		Mockito.verify(
			_dispatchTriggerHelper
		).addSchedulerJob(
			Mockito.same(_allNodesDispatchTrigger),
			Mockito.eq(StorageType.MEMORY), Mockito.any()
		);

		Mockito.verify(
			_dispatchTriggerHelper
		).addSchedulerJob(
			Mockito.same(_singleNodeMemoryClusteredDispatchTrigger),
			Mockito.eq(StorageType.MEMORY_CLUSTERED), Mockito.any()
		);

		Mockito.verify(
			_dispatchTriggerHelper
		).addSchedulerJob(
			Mockito.same(_singleNodePersistedDispatchTrigger),
			Mockito.eq(StorageType.PERSISTED), Mockito.any()
		);
	}

	@Test
	public void testOnActivationSchedulesOnlyAllNodesJobs() throws Exception {
		Mockito.when(
			_clusterMasterExecutor.isMaster()
		).thenReturn(
			false
		);

		_dispatchConfigurator.activate(_bundleContext);

		Mockito.verify(
			_dispatchTriggerLocalService
		).getDispatchTriggers(
			Mockito.eq(true), Mockito.eq(DispatchTaskClusterMode.ALL_NODES)
		);

		Mockito.verify(
			_dispatchTriggerLocalService, Mockito.never()
		).getDispatchTriggers(
			Mockito.eq(true),
			Mockito.eq(DispatchTaskClusterMode.SINGLE_NODE_MEMORY_CLUSTERED)
		);

		Mockito.verify(
			_dispatchTriggerLocalService, Mockito.never()
		).getDispatchTriggers(
			Mockito.eq(true),
			Mockito.eq(DispatchTaskClusterMode.SINGLE_NODE_PERSISTED)
		);

		Mockito.verify(
			_dispatchTriggerHelper
		).addSchedulerJob(
			Mockito.same(_allNodesDispatchTrigger),
			Mockito.eq(StorageType.MEMORY), Mockito.any()
		);

		Mockito.verify(
			_dispatchTriggerHelper, Mockito.never()
		).addSchedulerJob(
			Mockito.same(_singleNodeMemoryClusteredDispatchTrigger),
			Mockito.eq(StorageType.MEMORY), Mockito.any()
		);

		Mockito.verify(
			_dispatchTriggerHelper, Mockito.never()
		).addSchedulerJob(
			Mockito.same(_singleNodePersistedDispatchTrigger),
			Mockito.eq(StorageType.PERSISTED), Mockito.any()
		);
	}

	@Test
	public void testOnDeactivationUnschedulesAllTypeOfJobsOnMasterNode() {
		Mockito.when(
			_clusterMasterExecutor.isMaster()
		).thenReturn(
			true
		);

		ServiceRegistration<Destination> serviceRegistration = Mockito.mock(
			ServiceRegistration.class);

		ReflectionTestUtil.setFieldValue(
			_dispatchConfigurator, "_serviceRegistration", serviceRegistration);

		_dispatchConfigurator.deactivate();

		Mockito.verify(
			_dispatchTriggerLocalService
		).getDispatchTriggers(
			Mockito.eq(true), Mockito.eq(DispatchTaskClusterMode.ALL_NODES)
		);

		Mockito.verify(
			_dispatchTriggerLocalService
		).getDispatchTriggers(
			Mockito.eq(true),
			Mockito.eq(DispatchTaskClusterMode.SINGLE_NODE_MEMORY_CLUSTERED)
		);

		Mockito.verify(
			_dispatchTriggerLocalService
		).getDispatchTriggers(
			Mockito.eq(true),
			Mockito.eq(DispatchTaskClusterMode.SINGLE_NODE_PERSISTED)
		);

		Mockito.verify(
			_dispatchTriggerHelper
		).deleteSchedulerJob(
			Mockito.same(_allNodesDispatchTrigger),
			Mockito.eq(StorageType.MEMORY)
		);

		Mockito.verify(
			_dispatchTriggerHelper
		).deleteSchedulerJob(
			Mockito.same(_singleNodeMemoryClusteredDispatchTrigger),
			Mockito.eq(StorageType.MEMORY_CLUSTERED)
		);

		Mockito.verify(
			_dispatchTriggerHelper
		).deleteSchedulerJob(
			Mockito.same(_singleNodePersistedDispatchTrigger),
			Mockito.eq(StorageType.PERSISTED)
		);
	}

	@Test
	public void testOnDeactivationUnschedulesOnlyAllNodesJobs() {
		Mockito.when(
			_clusterMasterExecutor.isMaster()
		).thenReturn(
			false
		);

		ServiceRegistration<Destination> serviceRegistration = Mockito.mock(
			ServiceRegistration.class);

		ReflectionTestUtil.setFieldValue(
			_dispatchConfigurator, "_serviceRegistration", serviceRegistration);

		_dispatchConfigurator.deactivate();

		Mockito.verify(
			_dispatchTriggerLocalService
		).getDispatchTriggers(
			Mockito.eq(true), Mockito.eq(DispatchTaskClusterMode.ALL_NODES)
		);

		Mockito.verify(
			_dispatchTriggerLocalService, Mockito.never()
		).getDispatchTriggers(
			Mockito.eq(true),
			Mockito.eq(DispatchTaskClusterMode.SINGLE_NODE_MEMORY_CLUSTERED)
		);

		Mockito.verify(
			_dispatchTriggerLocalService, Mockito.never()
		).getDispatchTriggers(
			Mockito.eq(true),
			Mockito.eq(DispatchTaskClusterMode.SINGLE_NODE_PERSISTED)
		);

		Mockito.verify(
			_dispatchTriggerHelper
		).deleteSchedulerJob(
			Mockito.same(_allNodesDispatchTrigger),
			Mockito.eq(StorageType.MEMORY)
		);

		Mockito.verify(
			_dispatchTriggerHelper, Mockito.never()
		).deleteSchedulerJob(
			Mockito.same(_singleNodeMemoryClusteredDispatchTrigger),
			Mockito.eq(StorageType.MEMORY)
		);

		Mockito.verify(
			_dispatchTriggerHelper, Mockito.never()
		).deleteSchedulerJob(
			Mockito.same(_singleNodePersistedDispatchTrigger),
			Mockito.eq(StorageType.PERSISTED)
		);
	}

	@Mock
	private DispatchTrigger _allNodesDispatchTrigger;

	private final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();

	@Mock
	private ClusterMasterExecutor _clusterMasterExecutor;

	@Mock
	private DestinationFactory _destinationFactory;

	@InjectMocks
	private final DispatchConfigurator _dispatchConfigurator =
		new DispatchConfigurator();

	@Mock
	private DispatchTriggerHelper _dispatchTriggerHelper;

	@Mock
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

	@Mock
	private DispatchTrigger _singleNodeMemoryClusteredDispatchTrigger;

	@Mock
	private DispatchTrigger _singleNodePersistedDispatchTrigger;

}