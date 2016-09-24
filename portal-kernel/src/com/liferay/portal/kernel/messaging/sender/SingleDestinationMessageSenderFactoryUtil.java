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

package com.liferay.portal.kernel.messaging.sender;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

/**
 * @author Michael C. Han
 */
public class SingleDestinationMessageSenderFactoryUtil {

	public static SingleDestinationMessageSender
		createSingleDestinationMessageSender(String destinationName) {

		return _instance.getSingleDestinationMessageSenderFactory().
			createSingleDestinationMessageSender(destinationName);
	}

	public static SingleDestinationSynchronousMessageSender
		createSingleDestinationSynchronousMessageSender(
			String destinationName, SynchronousMessageSender.Mode mode) {

		return _instance.getSingleDestinationMessageSenderFactory().
			createSingleDestinationSynchronousMessageSender(
				destinationName, mode);
	}

	public static int getModeCount() {
		return _instance.getSingleDestinationMessageSenderFactory().
			getModesCount();
	}

	public static SynchronousMessageSender getSynchronousMessageSender(
		SynchronousMessageSender.Mode mode) {

		return _instance.getSingleDestinationMessageSenderFactory().
			getSynchronousMessageSender(mode);
	}

	protected SingleDestinationMessageSenderFactory
		getSingleDestinationMessageSenderFactory() {

		try {
			while (_singleDestinationMessageSenderFactory == null) {
				Registry registry = RegistryUtil.getRegistry();

				_singleDestinationMessageSenderFactory = registry.getService(
					SingleDestinationMessageSenderFactory.class);

				if (_singleDestinationMessageSenderFactory != null) {
					return _singleDestinationMessageSenderFactory;
				}

				if (_log.isDebugEnabled()) {
					_log.debug("Waiting for a destination factory");
				}

				Thread.sleep(500);
			}
		}
		catch (InterruptedException ie) {
			throw new IllegalStateException(
				"Unable to initialize " +
					"SingleDestinationMessageSenderFactoryUtil",
				ie);
		}

		return _singleDestinationMessageSenderFactory;
	}

	private SingleDestinationMessageSenderFactoryUtil() {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SingleDestinationMessageSenderFactoryUtil.class);

	private static final SingleDestinationMessageSenderFactoryUtil _instance =
		new SingleDestinationMessageSenderFactoryUtil();

	private static volatile SingleDestinationMessageSenderFactory
		_singleDestinationMessageSenderFactory =
			ProxyFactory.newServiceTrackedInstanceWithoutDummyService(
				SingleDestinationMessageSenderFactory.class,
				SingleDestinationMessageSenderFactoryUtil.class,
				"_singleDestinationMessageSenderFactory");

}