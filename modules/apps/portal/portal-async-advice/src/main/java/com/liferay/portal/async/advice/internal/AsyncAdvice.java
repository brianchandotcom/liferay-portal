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

package com.liferay.portal.async.advice.internal;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.async.advice.internal.configuration.AsyncAdviceConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.aop.AopMethodInvocation;
import com.liferay.portal.kernel.aop.ChainableMethodAdvice;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.async.Async;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
@Component(
	configurationPid = "com.liferay.portal.async.advice.internal.configuration.AsyncAdviceConfiguration",
	immediate = true, service = ChainableMethodAdvice.class
)
public class AsyncAdvice extends ChainableMethodAdvice {

	@Activate
	@Modified
	protected void activate(Map<String, String> properties) {
		_asyncAdviceConfiguration = ConfigurableUtil.createConfigurable(
			AsyncAdviceConfiguration.class, properties);

		String[] targetClassNamesToDestinationNames =
			_asyncAdviceConfiguration.targetClassNamesToDestinationNames();

		if (targetClassNamesToDestinationNames != null) {
			Map<String, String> destinationNames = new HashMap<>();

			for (String targetClassNameToDestinationName :
					targetClassNamesToDestinationNames) {

				int index = targetClassNameToDestinationName.indexOf(
					CharPool.EQUAL);

				if (index <= 0) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Invalid target class name to destination name \"" +
								targetClassNameToDestinationName + "\"");
					}
				}
				else {
					destinationNames.put(
						targetClassNameToDestinationName.substring(0, index),
						targetClassNameToDestinationName.substring(index + 1));
				}
			}

			if (!destinationNames.isEmpty()) {
				_destinationNames = destinationNames;
			}
		}
	}

	@Override
	public Object createMethodContext(
		Class<?> targetClass, Method method,
		Map<Class<? extends Annotation>, Annotation> annotations) {

		Annotation annotation = annotations.get(Async.class);

		if (annotation == null) {
			return null;
		}

		if (method.getReturnType() != void.class) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Async annotation on method " + method.getName() +
						" does not return void");
			}

			return null;
		}

		String destinationName = null;

		if (_destinationNames != null) {
			destinationName = _destinationNames.get(targetClass.getName());
		}

		if (destinationName == null) {
			return _asyncAdviceConfiguration.defaultDestinationName();
		}

		return destinationName;
	}

	@Override
	protected Object before(
		AopMethodInvocation aopMethodInvocation, Object[] arguments) {

		if (AsyncInvokeThreadLocal.isEnabled()) {
			return null;
		}

		String destinationName = aopMethodInvocation.getAdviceMethodContext();

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				Message message = new Message();

				message.setPayload(
					new AsyncProcessCallable(aopMethodInvocation, arguments));

				_messageBus.sendMessage(destinationName, message);

				return null;
			});

		return nullResult;
	}

	private static final Log _log = LogFactoryUtil.getLog(AsyncAdvice.class);

	private AsyncAdviceConfiguration _asyncAdviceConfiguration;
	private Map<String, String> _destinationNames;

	@Reference
	private MessageBus _messageBus;

}