/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.messaging.internal;

import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.MessageListenerRegistry;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.UserLocalService;

/**
 * @author Michael C. Han
 */
public class ParallelDestinationPrototype implements DestinationPrototype {

	public ParallelDestinationPrototype(
		MessageListenerRegistry messageListenerRegistry,
		PortalExecutorManager portalExecutorManager,
		PermissionCheckerFactory permissionCheckerFactory,
		UserLocalService userLocalService) {

		_messageListenerRegistry = messageListenerRegistry;
		_portalExecutorManager = portalExecutorManager;
		_permissionCheckerFactory = permissionCheckerFactory;
		_userLocalService = userLocalService;
	}

	@Override
	public Destination createDestination(
		DestinationConfiguration destinationConfiguration) {

		ParallelDestination parallelDestination = new ParallelDestination();

		parallelDestination.setDestinationType(
			destinationConfiguration.getDestinationType());
		parallelDestination.setName(
			destinationConfiguration.getDestinationName());
		parallelDestination.setMaximumQueueSize(
			destinationConfiguration.getMaximumQueueSize());
		parallelDestination.setMessageListenerRegistry(
			_messageListenerRegistry);
		parallelDestination.setPermissionCheckerFactory(
			_permissionCheckerFactory);
		parallelDestination.setPortalExecutorManager(_portalExecutorManager);
		parallelDestination.setRejectedExecutionHandler(
			destinationConfiguration.getRejectedExecutionHandler());
		parallelDestination.setUserLocalService(_userLocalService);
		parallelDestination.setWorkersSize(
			destinationConfiguration.getWorkersCoreSize(),
			destinationConfiguration.getWorkersMaxSize());

		return parallelDestination;
	}

	private final MessageListenerRegistry _messageListenerRegistry;
	private final PermissionCheckerFactory _permissionCheckerFactory;
	private final PortalExecutorManager _portalExecutorManager;
	private final UserLocalService _userLocalService;

}