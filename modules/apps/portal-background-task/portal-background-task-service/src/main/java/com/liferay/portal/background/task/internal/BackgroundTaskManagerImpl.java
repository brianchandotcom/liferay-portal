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

package com.liferay.portal.background.task.internal;

import com.liferay.background.task.kernel.util.comparator.BackgroundTaskCompletionDateComparator;
import com.liferay.background.task.kernel.util.comparator.BackgroundTaskCreateDateComparator;
import com.liferay.background.task.kernel.util.comparator.BackgroundTaskNameComparator;
import com.liferay.portal.background.task.internal.messaging.BackgroundTaskMessageListener;
import com.liferay.portal.background.task.internal.messaging.BackgroundTaskQueuingMessageListener;
import com.liferay.portal.background.task.internal.messaging.RemoveOnCompletionBackgroundTaskStatusMessageListener;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutorRegistry;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusRegistry;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocalManager;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lock.LockManager;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = BackgroundTaskManager.class)
public class BackgroundTaskManagerImpl implements BackgroundTaskManager {

	@Override
	public BackgroundTask addBackgroundTask(
			long userId, long groupId, String name,
			String taskExecutorClassName,
			Map<String, Serializable> taskContextMap,
			ServiceContext serviceContext)
		throws PortalException {

		com.liferay.portal.background.task.model.BackgroundTask backgroundTask =
			_backgroundTaskLocalService.addBackgroundTask(
				userId, groupId, name, taskExecutorClassName, taskContextMap,
				serviceContext);

		return new BackgroundTaskImpl(backgroundTask);
	}

	@Override
	public BackgroundTask addBackgroundTask(
			long userId, long groupId, String name,
			String[] servletContextNames, Class<?> taskExecutorClass,
			Map<String, Serializable> taskContextMap,
			ServiceContext serviceContext)
		throws PortalException {

		com.liferay.portal.background.task.model.BackgroundTask backgroundTask =
			_backgroundTaskLocalService.addBackgroundTask(
				userId, groupId, name, servletContextNames, taskExecutorClass,
				taskContextMap, serviceContext);

		return new BackgroundTaskImpl(backgroundTask);
	}

	@Override
	public void addBackgroundTaskAttachment(
			long userId, long backgroundTaskId, String fileName, File file)
		throws PortalException {

		_backgroundTaskLocalService.addBackgroundTaskAttachment(
			userId, backgroundTaskId, fileName, file);
	}

	@Override
	public void addBackgroundTaskAttachment(
			long userId, long backgroundTaskId, String fileName,
			InputStream inputStream)
		throws PortalException {

		_backgroundTaskLocalService.addBackgroundTaskAttachment(
			userId, backgroundTaskId, fileName, inputStream);
	}

	@Override
	public BackgroundTask amendBackgroundTask(
		long backgroundTaskId, Map<String, Serializable> taskContextMap,
		int status, ServiceContext serviceContext) {

		com.liferay.portal.background.task.model.BackgroundTask backgroundTask =
			_backgroundTaskLocalService.amendBackgroundTask(
				backgroundTaskId, taskContextMap, status, serviceContext);

		if (backgroundTask == null) {
			return null;
		}

		return new BackgroundTaskImpl(backgroundTask);
	}

	@Override
	public BackgroundTask amendBackgroundTask(
		long backgroundTaskId, Map<String, Serializable> taskContextMap,
		int status, String statusMessage, ServiceContext serviceContext) {

		com.liferay.portal.background.task.model.BackgroundTask backgroundTask =
			_backgroundTaskLocalService.amendBackgroundTask(
				backgroundTaskId, taskContextMap, status, statusMessage,
				serviceContext);

		if (backgroundTask == null) {
			return null;
		}

		return new BackgroundTaskImpl(backgroundTask);
	}

	@Override
	public void cleanUpBackgroundTask(
		BackgroundTask backgroundTask, int status) {

		_backgroundTaskLocalService.cleanUpBackgroundTask(
			backgroundTask.getBackgroundTaskId(), status);
	}

	@Override
	public void cleanUpBackgroundTasks() {
		_backgroundTaskLocalService.cleanUpBackgroundTasks();
	}

	@Override
	public BackgroundTask deleteBackgroundTask(long backgroundTaskId)
		throws PortalException {

		com.liferay.portal.background.task.model.BackgroundTask backgroundTask =
			_backgroundTaskLocalService.deleteBackgroundTask(backgroundTaskId);

		return new BackgroundTaskImpl(backgroundTask);
	}

	@Override
	public void deleteCompanyBackgroundTasks(long companyId)
		throws PortalException {

		_backgroundTaskLocalService.deleteCompanyBackgroundTasks(companyId);
	}

	@Override
	public void deleteGroupBackgroundTasks(long groupId)
		throws PortalException {

		_backgroundTaskLocalService.deleteGroupBackgroundTasks(groupId);
	}

	@Override
	public void deleteGroupBackgroundTasks(
			long groupId, String name, String taskExecutorClassName)
		throws PortalException {

		_backgroundTaskLocalService.deleteGroupBackgroundTasks(
			groupId, name, taskExecutorClassName);
	}

	@Override
	public BackgroundTask fetchBackgroundTask(long backgroundTaskId) {
		com.liferay.portal.background.task.model.BackgroundTask backgroundTask =
			_backgroundTaskLocalService.fetchBackgroundTask(backgroundTaskId);

		if (backgroundTask == null) {
			return null;
		}

		return new BackgroundTaskImpl(backgroundTask);
	}

	@Override
	public BackgroundTask fetchFirstBackgroundTask(
		long groupId, String taskExecutorClassName, boolean completed,
		OrderByComparator<BackgroundTask> orderByComparator) {

		com.liferay.portal.background.task.model.BackgroundTask backgroundTask =
			_backgroundTaskLocalService.fetchFirstBackgroundTask(
				groupId, taskExecutorClassName, completed,
				translate(orderByComparator));

		if (backgroundTask == null) {
			return null;
		}

		return new BackgroundTaskImpl(backgroundTask);
	}

	@Override
	public BackgroundTask fetchFirstBackgroundTask(
		String taskExecutorClassName, int status) {

		com.liferay.portal.background.task.model.BackgroundTask
			bcakgroundTaskModel =
				_backgroundTaskLocalService.fetchFirstBackgroundTask(
					taskExecutorClassName, status);

		if (bcakgroundTaskModel == null) {
			return null;
		}

		return new BackgroundTaskImpl(bcakgroundTaskModel);
	}

	@Override
	public BackgroundTask fetchFirstBackgroundTask(
		String taskExecutorClassName, int status,
		OrderByComparator<BackgroundTask> orderByComparator) {

		com.liferay.portal.background.task.model.BackgroundTask backgroundTask =
			_backgroundTaskLocalService.fetchFirstBackgroundTask(
				taskExecutorClassName, status, translate(orderByComparator));

		if (backgroundTask == null) {
			return null;
		}

		return new BackgroundTaskImpl(backgroundTask);
	}

	@Override
	public BackgroundTask getBackgroundTask(long backgroundTaskId)
		throws PortalException {

		return new BackgroundTaskImpl(
			_backgroundTaskLocalService.getBackgroundTask(backgroundTaskId));
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(long groupId, int status) {
		List<com.liferay.portal.background.task.model.BackgroundTask>
			backgroundTasks = _backgroundTaskLocalService.getBackgroundTasks(
				groupId, status);

		return translate(backgroundTasks);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		long groupId, String taskExecutorClassName) {

		List<com.liferay.portal.background.task.model.BackgroundTask>
			backgroundTasks = _backgroundTaskLocalService.getBackgroundTasks(
				groupId, taskExecutorClassName);

		return translate(backgroundTasks);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		long groupId, String taskExecutorClassName, boolean completed,
		int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		List<com.liferay.portal.background.task.model.BackgroundTask>
			backgroundTasks = _backgroundTaskLocalService.getBackgroundTasks(
				groupId, taskExecutorClassName, completed, start, end,
				translate(orderByComparator));

		return translate(backgroundTasks);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		long groupId, String taskExecutorClassName, int status) {

		List<com.liferay.portal.background.task.model.BackgroundTask>
			backgroundTasks = _backgroundTaskLocalService.getBackgroundTasks(
				groupId, taskExecutorClassName, status);

		return translate(backgroundTasks);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		long groupId, String taskExecutorClassName, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		List<com.liferay.portal.background.task.model.BackgroundTask>
			backgroundTasks = _backgroundTaskLocalService.getBackgroundTasks(
				groupId, taskExecutorClassName, start, end,
				translate(orderByComparator));

		return translate(backgroundTasks);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		long groupId, String name, String taskExecutorClassName, int start,
		int end, OrderByComparator<BackgroundTask> orderByComparator) {

		List<com.liferay.portal.background.task.model.BackgroundTask>
			backgroundTasks = _backgroundTaskLocalService.getBackgroundTasks(
				groupId, name, taskExecutorClassName, start, end,
				translate(orderByComparator));

		return translate(backgroundTasks);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		long groupId, String[] taskExecutorClassNames) {

		List<com.liferay.portal.background.task.model.BackgroundTask>
			backgroundTasks = _backgroundTaskLocalService.getBackgroundTasks(
				new long[] {groupId}, taskExecutorClassNames);

		return translate(backgroundTasks);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		long groupId, String[] taskExecutorClassNames, int status) {

		List<com.liferay.portal.background.task.model.BackgroundTask>
			backgroundTasks = _backgroundTaskLocalService.getBackgroundTasks(
				groupId, taskExecutorClassNames, status);

		return translate(backgroundTasks);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		long groupId, String[] taskExecutorClassNames, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		List<com.liferay.portal.background.task.model.BackgroundTask>
			backgroundTasks = _backgroundTaskLocalService.getBackgroundTasks(
				new long[] {groupId}, taskExecutorClassNames, start, end,
				translate(orderByComparator));

		return translate(backgroundTasks);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		long[] groupIds, String taskExecutorClassName, boolean completed,
		int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		List<com.liferay.portal.background.task.model.BackgroundTask>
			backgroundTasks = _backgroundTaskLocalService.getBackgroundTasks(
				groupIds, new String[] {taskExecutorClassName}, completed,
				start, end, translate(orderByComparator));

		return translate(backgroundTasks);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		long[] groupIds, String taskExecutorClassName, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		List<com.liferay.portal.background.task.model.BackgroundTask>
			backgroundTasks = _backgroundTaskLocalService.getBackgroundTasks(
				groupIds, new String[] {taskExecutorClassName}, start, end,
				translate(orderByComparator));

		return translate(backgroundTasks);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		long[] groupIds, String name, String taskExecutorClassName, int start,
		int end, OrderByComparator<BackgroundTask> orderByComparator) {

		List<com.liferay.portal.background.task.model.BackgroundTask>
			backgroundTasks = _backgroundTaskLocalService.getBackgroundTasks(
				groupIds, name, taskExecutorClassName, start, end,
				translate(orderByComparator));

		return translate(backgroundTasks);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		long[] groupIds, String name, String[] taskExecutorClassNames,
		int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		List<com.liferay.portal.background.task.model.BackgroundTask>
			backgroundTasks = _backgroundTaskLocalService.getBackgroundTasks(
				groupIds, name, taskExecutorClassNames, start, end,
				translate(orderByComparator));

		return translate(backgroundTasks);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		String taskExecutorClassName, int status) {

		List<com.liferay.portal.background.task.model.BackgroundTask>
			backgroundTasks = _backgroundTaskLocalService.getBackgroundTasks(
				taskExecutorClassName, status);

		return translate(backgroundTasks);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		String taskExecutorClassName, int status, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		List<com.liferay.portal.background.task.model.BackgroundTask>
			backgroundTasks = _backgroundTaskLocalService.getBackgroundTasks(
				taskExecutorClassName, status, start, end,
				translate(orderByComparator));

		return translate(backgroundTasks);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		String[] taskExecutorClassNames, int status) {

		List<com.liferay.portal.background.task.model.BackgroundTask>
			backgroundTasks = _backgroundTaskLocalService.getBackgroundTasks(
				taskExecutorClassNames, status);

		return translate(backgroundTasks);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasks(
		String[] taskExecutorClassNames, int status, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		List<com.liferay.portal.background.task.model.BackgroundTask>
			backgroundTasks = _backgroundTaskLocalService.getBackgroundTasks(
				taskExecutorClassNames, status, start, end,
				translate(orderByComparator));

		return translate(backgroundTasks);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasksByDuration(
		long[] groupIds, String[] taskExecutorClassName, boolean completed,
		int start, int end, boolean orderByType) {

		List<com.liferay.portal.background.task.model.BackgroundTask>
			backgroundTasks =
				_backgroundTaskLocalService.getBackgroundTasksByDuration(
					groupIds, taskExecutorClassName, completed, start, end,
					orderByType);

		return translate(backgroundTasks);
	}

	@Override
	public List<BackgroundTask> getBackgroundTasksByDuration(
		long[] groupIds, String[] taskExecutorClassName, int start, int end,
		boolean orderByType) {

		List<com.liferay.portal.background.task.model.BackgroundTask>
			backgroundTasks =
				_backgroundTaskLocalService.getBackgroundTasksByDuration(
					groupIds, taskExecutorClassName, start, end, orderByType);

		return translate(backgroundTasks);
	}

	@Override
	public int getBackgroundTasksCount(
		long groupId, String taskExecutorClassName) {

		return _backgroundTaskLocalService.getBackgroundTasksCount(
			groupId, taskExecutorClassName);
	}

	@Override
	public int getBackgroundTasksCount(
		long groupId, String taskExecutorClassName, boolean completed) {

		return _backgroundTaskLocalService.getBackgroundTasksCount(
			groupId, taskExecutorClassName, completed);
	}

	@Override
	public int getBackgroundTasksCount(
		long groupId, String name, String taskExecutorClassName) {

		return _backgroundTaskLocalService.getBackgroundTasksCount(
			groupId, name, taskExecutorClassName);
	}

	@Override
	public int getBackgroundTasksCount(
		long groupId, String name, String taskExecutorClassName,
		boolean completed) {

		return _backgroundTaskLocalService.getBackgroundTasksCount(
			groupId, name, taskExecutorClassName, completed);
	}

	@Override
	public int getBackgroundTasksCount(
		long groupId, String[] taskExecutorClassNames) {

		return _backgroundTaskLocalService.getBackgroundTasksCount(
			new long[] {groupId}, taskExecutorClassNames);
	}

	@Override
	public int getBackgroundTasksCount(
		long groupId, String[] taskExecutorClassNames, boolean completed) {

		return _backgroundTaskLocalService.getBackgroundTasksCount(
			new long[] {groupId}, taskExecutorClassNames, completed);
	}

	@Override
	public int getBackgroundTasksCount(
		long[] groupIds, String taskExecutorClassName) {

		return _backgroundTaskLocalService.getBackgroundTasksCount(
			groupIds, new String[] {taskExecutorClassName});
	}

	@Override
	public int getBackgroundTasksCount(
		long[] groupIds, String taskExecutorClassName, boolean completed) {

		return _backgroundTaskLocalService.getBackgroundTasksCount(
			groupIds, new String[] {taskExecutorClassName}, completed);
	}

	@Override
	public int getBackgroundTasksCount(
		long[] groupIds, String name, String taskExecutorClassName) {

		return _backgroundTaskLocalService.getBackgroundTasksCount(
			groupIds, name, taskExecutorClassName);
	}

	@Override
	public int getBackgroundTasksCount(
		long[] groupIds, String name, String taskExecutorClassName,
		boolean completed) {

		return _backgroundTaskLocalService.getBackgroundTasksCount(
			groupIds, name, taskExecutorClassName, completed);
	}

	@Override
	public int getBackgroundTasksCount(
		long[] groupIds, String name, String[] taskExecutorClassNames) {

		return _backgroundTaskLocalService.getBackgroundTasksCount(
			groupIds, name, taskExecutorClassNames);
	}

	@Override
	public String getBackgroundTaskStatusJSON(long backgroundTaskId) {
		return _backgroundTaskLocalService.getBackgroundTaskStatusJSON(
			backgroundTaskId);
	}

	@Override
	public void resumeBackgroundTask(long backgroundTaskId) {
		_backgroundTaskLocalService.resumeBackgroundTask(backgroundTaskId);
	}

	@Override
	public void triggerBackgroundTask(long backgroundTaskId) {
		_backgroundTaskLocalService.triggerBackgroundTask(backgroundTaskId);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		Destination backgroundTaskDestination = registerDestination(
			bundleContext, DestinationConfiguration.DESTINATION_TYPE_PARALLEL,
			DestinationNames.BACKGROUND_TASK, 5, 10);

		BackgroundTaskMessageListener backgroundTaskMessageListener =
			new BackgroundTaskMessageListener(
				_backgroundTaskExecutorRegistry, this,
				_backgroundTaskStatusRegistry,
				_backgroundTaskThreadLocalManager, _lockManager, _messageBus);

		backgroundTaskDestination.register(backgroundTaskMessageListener);

		Destination backgroundTaskStatusDestination = registerDestination(
			bundleContext, DestinationConfiguration.DESTINATION_TYPE_SERIAL,
			DestinationNames.BACKGROUND_TASK_STATUS, 1, 1);

		BackgroundTaskQueuingMessageListener
			backgroundTaskQueuingMessageListener =
				new BackgroundTaskQueuingMessageListener(this, _lockManager);

		backgroundTaskStatusDestination.register(
			backgroundTaskQueuingMessageListener);

		RemoveOnCompletionBackgroundTaskStatusMessageListener
			removeOnCompletionBackgroundTaskStatusMessageListener =
				new RemoveOnCompletionBackgroundTaskStatusMessageListener(this);

		backgroundTaskStatusDestination.register(
			removeOnCompletionBackgroundTaskStatusMessageListener);

		if (!_clusterMasterExecutor.isEnabled()) {
			cleanUpBackgroundTasks();
		}
	}

	@Deactivate
	protected void deactivate() {
		for (ServiceRegistration<Destination> serviceRegistration :
				_serviceRegistrations) {

			Destination destination = _bundleContext.getService(
				serviceRegistration.getReference());

			serviceRegistration.unregister();

			destination.destroy();
		}

		_bundleContext = null;
	}

	protected Destination registerDestination(
		BundleContext bundleContext, String destinationType,
		String destinationName, int workersCoreSize, int workersMaxSize) {

		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(destinationType, destinationName);

		destinationConfiguration.setWorkersCoreSize(workersCoreSize);
		destinationConfiguration.setWorkersMaxSize(workersMaxSize);

		Destination destination = _destinationFactory.createDestination(
			destinationConfiguration);

		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		dictionary.put("destination.name", destination.getName());

		ServiceRegistration<Destination> serviceRegistration =
			bundleContext.registerService(
				Destination.class, destination, dictionary);

		_serviceRegistrations.add(serviceRegistration);

		return destination;
	}

	@Reference(unbind = "-")
	protected void setLockManager(LockManager lockManager) {
	}

	protected List<BackgroundTask> translate(
		List<com.liferay.portal.background.task.model.BackgroundTask>
			backgroundTaskModels) {

		if (backgroundTaskModels.isEmpty()) {
			return Collections.emptyList();
		}

		List<BackgroundTask> backgroundTasks = new ArrayList<>(
			backgroundTaskModels.size());

		for (com.liferay.portal.background.task.model.BackgroundTask
				backgroundTaskModel : backgroundTaskModels) {

			backgroundTasks.add(new BackgroundTaskImpl(backgroundTaskModel));
		}

		return backgroundTasks;
	}

	protected OrderByComparator
		<com.liferay.portal.background.task.model.BackgroundTask> translate(
			OrderByComparator<BackgroundTask> orderByComparator) {

		if (orderByComparator instanceof
				BackgroundTaskCompletionDateComparator) {

			return new com.liferay.portal.background.task.internal.comparator.
				BackgroundTaskCompletionDateComparator(
					orderByComparator.isAscending());
		}
		else if (orderByComparator instanceof
					BackgroundTaskCreateDateComparator) {

			return new com.liferay.portal.background.task.internal.comparator.
				BackgroundTaskCreateDateComparator(
					orderByComparator.isAscending());
		}
		else if (orderByComparator instanceof BackgroundTaskNameComparator) {
			return new com.liferay.portal.background.task.internal.comparator.
				BackgroundTaskNameComparator(orderByComparator.isAscending());
		}

		throw new IllegalArgumentException(
			"Invalid class " + ClassUtil.getClassName(orderByComparator));
	}

	@Reference
	private BackgroundTaskExecutorRegistry _backgroundTaskExecutorRegistry;

	@Reference
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@Reference
	private BackgroundTaskStatusRegistry _backgroundTaskStatusRegistry;

	@Reference
	private BackgroundTaskThreadLocalManager _backgroundTaskThreadLocalManager;

	private volatile BundleContext _bundleContext;

	@Reference
	private ClusterMasterExecutor _clusterMasterExecutor;

	@Reference
	private DestinationFactory _destinationFactory;

	@Reference
	private LockManager _lockManager;

	@Reference
	private MessageBus _messageBus;

	private final Set<ServiceRegistration<Destination>> _serviceRegistrations =
		new HashSet<>();

}