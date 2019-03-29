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

import com.liferay.portal.kernel.aop.AopMethodInvocation;
import com.liferay.portal.kernel.aop.ChainableMethodAdvice;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.async.Async;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = ChainableMethodAdvice.class)
public class AsyncAdvice extends ChainableMethodAdvice {

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

		return nullResult;
	}

	@Override
	protected Object before(
		AopMethodInvocation aopMethodInvocation, Object[] arguments) {

		if (AsyncInvokeThreadLocal.isEnabled()) {
			return null;
		}

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				Message message = new Message();

				message.setPayload(
					new AsyncProcessCallable(aopMethodInvocation, arguments));

				_messageBus.sendMessage(_DESTINATION_NAME, message);

				return null;
			});

		return nullResult;
	}

	private static final String _DESTINATION_NAME =
		DestinationNames.ASYNC_SERVICE;

	private static final Log _log = LogFactoryUtil.getLog(AsyncAdvice.class);

	@Reference
	private MessageBus _messageBus;

}