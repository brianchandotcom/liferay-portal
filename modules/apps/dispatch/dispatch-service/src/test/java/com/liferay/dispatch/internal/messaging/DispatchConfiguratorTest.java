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

import org.mockito.Mockito;

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
		ReflectionTestUtil.setFieldValue(
			_dispatchConfigurator, "_clusterMasterExecutor",
			_clusterMasterExecutor);
		ReflectionTestUtil.setFieldValue(
			_dispatchConfigurator, "_destinationFactory", _destinationFactory);
		ReflectionTestUtil.setFieldValue(
			_dispatchConfigurator, "_dispatchTriggerHelper",
			_dispatchTriggerHelper);
		ReflectionTestUtil.setFieldValue(
			_dispatchConfigurator, "_dispatchTriggerLocalService",
			_dispatchTriggerLocalService);

		Mockito.when(
			_destinationFactory.createDestination(Mockito.any())
		).thenReturn(
			Mockito.mock(Destination.class)
		);

		_allNodesDispatchTrigger = Mockito.mock(DispatchTrigger.class);

		Mockito.when(
			_dispatchTriggerLocalService.getDispatchTriggers(
				true, DispatchTaskClusterMode.ALL_NODES)
		).thenReturn(
			ListUtil.fromArray(_allNodesDispatchTrigger)
		);

		_singleNodeMemoryClusteredDispatchTrigger = Mockito.mock(
			DispatchTrigger.class);

		Mockito.when(
			_dispatchTriggerLocalService.getDispatchTriggers(
				true, DispatchTaskClusterMode.SINGLE_NODE_MEMORY_CLUSTERED)
		).thenReturn(
			ListUtil.fromArray(_singleNodeMemoryClusteredDispatchTrigger)
		);

		_singleNodePersistedDispatchTrigger = Mockito.mock(
			DispatchTrigger.class);

		Mockito.when(
			_dispatchTriggerLocalService.getDispatchTriggers(
				true, DispatchTaskClusterMode.SINGLE_NODE_PERSISTED)
		).thenReturn(
			ListUtil.fromArray(_singleNodePersistedDispatchTrigger)
		);
	}

	@Test
	public void testOnActivationSchedulesAllTypeOfJobsOnMasterNode()
		throws Exception {

		Mockito.when(
			_clusterMasterExecutor.isMaster()
		).thenReturn(
			true
		);

		ReflectionTestUtil.invoke(
			_dispatchConfigurator, "activate",
			new Class<?>[] {BundleContext.class}, _bundleContext);

		Mockito.verify(
			_dispatchTriggerLocalService, Mockito.times(1)
		).getDispatchTriggers(
			Mockito.eq(true), Mockito.eq(DispatchTaskClusterMode.ALL_NODES)
		);

		Mockito.verify(
			_dispatchTriggerLocalService, Mockito.times(1)
		).getDispatchTriggers(
			Mockito.eq(true),
			Mockito.eq(DispatchTaskClusterMode.SINGLE_NODE_MEMORY_CLUSTERED)
		);

		Mockito.verify(
			_dispatchTriggerLocalService, Mockito.times(1)
		).getDispatchTriggers(
			Mockito.eq(true),
			Mockito.eq(DispatchTaskClusterMode.SINGLE_NODE_PERSISTED)
		);

		Mockito.verify(
			_dispatchTriggerHelper, Mockito.times(1)
		).addSchedulerJob(
			Mockito.same(_allNodesDispatchTrigger),
			Mockito.eq(StorageType.MEMORY), Mockito.any()
		);

		Mockito.verify(
			_dispatchTriggerHelper, Mockito.times(1)
		).addSchedulerJob(
			Mockito.same(_singleNodeMemoryClusteredDispatchTrigger),
			Mockito.eq(StorageType.MEMORY_CLUSTERED), Mockito.any()
		);

		Mockito.verify(
			_dispatchTriggerHelper, Mockito.times(1)
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

		ReflectionTestUtil.invoke(
			_dispatchConfigurator, "activate",
			new Class<?>[] {BundleContext.class}, _bundleContext);

		Mockito.verify(
			_dispatchTriggerLocalService, Mockito.times(1)
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
			_dispatchTriggerHelper, Mockito.times(1)
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
	public void testOnDeactivationUnschedulesAllTypeOfJobsOnMasterNode()
		throws Exception {

		Mockito.when(
			_clusterMasterExecutor.isMaster()
		).thenReturn(
			true
		);

		ServiceRegistration<Destination> serviceRegistration = Mockito.mock(
			ServiceRegistration.class);

		ReflectionTestUtil.setFieldValue(
			_dispatchConfigurator, "_serviceRegistration", serviceRegistration);

		ReflectionTestUtil.invoke(
			_dispatchConfigurator, "deactivate", new Class<?>[0]);

		Mockito.verify(
			_dispatchTriggerLocalService, Mockito.times(1)
		).getDispatchTriggers(
			Mockito.eq(true), Mockito.eq(DispatchTaskClusterMode.ALL_NODES)
		);

		Mockito.verify(
			_dispatchTriggerLocalService, Mockito.times(1)
		).getDispatchTriggers(
			Mockito.eq(true),
			Mockito.eq(DispatchTaskClusterMode.SINGLE_NODE_MEMORY_CLUSTERED)
		);

		Mockito.verify(
			_dispatchTriggerLocalService, Mockito.times(1)
		).getDispatchTriggers(
			Mockito.eq(true),
			Mockito.eq(DispatchTaskClusterMode.SINGLE_NODE_PERSISTED)
		);

		Mockito.verify(
			_dispatchTriggerHelper, Mockito.times(1)
		).deleteSchedulerJob(
			Mockito.same(_allNodesDispatchTrigger),
			Mockito.eq(StorageType.MEMORY)
		);

		Mockito.verify(
			_dispatchTriggerHelper, Mockito.times(1)
		).deleteSchedulerJob(
			Mockito.same(_singleNodeMemoryClusteredDispatchTrigger),
			Mockito.eq(StorageType.MEMORY_CLUSTERED)
		);

		Mockito.verify(
			_dispatchTriggerHelper, Mockito.times(1)
		).deleteSchedulerJob(
			Mockito.same(_singleNodePersistedDispatchTrigger),
			Mockito.eq(StorageType.PERSISTED)
		);
	}

	@Test
	public void testOnDeactivationUnschedulesOnlyAllNodesJobs()
		throws Exception {

		Mockito.when(
			_clusterMasterExecutor.isMaster()
		).thenReturn(
			false
		);

		ServiceRegistration<Destination> serviceRegistration = Mockito.mock(
			ServiceRegistration.class);

		ReflectionTestUtil.setFieldValue(
			_dispatchConfigurator, "_serviceRegistration", serviceRegistration);

		ReflectionTestUtil.invoke(
			_dispatchConfigurator, "deactivate", new Class<?>[0]);

		Mockito.verify(
			_dispatchTriggerLocalService, Mockito.times(1)
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
			_dispatchTriggerHelper, Mockito.times(1)
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

	private DispatchTrigger _allNodesDispatchTrigger;
	private final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private final ClusterMasterExecutor _clusterMasterExecutor = Mockito.mock(
		ClusterMasterExecutor.class);
	private final DestinationFactory _destinationFactory = Mockito.mock(
		DestinationFactory.class);
	private final DispatchConfigurator _dispatchConfigurator =
		new DispatchConfigurator();
	private final DispatchTriggerHelper _dispatchTriggerHelper = Mockito.mock(
		DispatchTriggerHelper.class);
	private final DispatchTriggerLocalService _dispatchTriggerLocalService =
		Mockito.mock(DispatchTriggerLocalService.class);
	private DispatchTrigger _singleNodeMemoryClusteredDispatchTrigger;
	private DispatchTrigger _singleNodePersistedDispatchTrigger;

}