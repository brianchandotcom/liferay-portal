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

package com.liferay.adaptive.media.internal.processor;

import com.liferay.adaptive.media.internal.constants.AMDestinationNames;
import com.liferay.adaptive.media.internal.messaging.AMProcessorCommand;
import com.liferay.adaptive.media.processor.AMAsyncProcessor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Sergio González
 */
public final class AMAsyncProcessorImpl<M, T>
	implements AMAsyncProcessor<M, T> {

	public AMAsyncProcessorImpl(Class<M> clazz, MessageBus messageBus) {
		_clazz = clazz;
		_messageBus = messageBus;
	}

	@Override
	public void cleanQueue(
		AMProcessorCommand amProcessorCommand, String modelId) {

		List<String> commandModelIds = _modelIds.get(amProcessorCommand);

		commandModelIds.remove(modelId);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Cleaned queue for model id " + modelId + " and command " +
					amProcessorCommand);
		}
	}

	@Override
	public void triggerCleanUp(M model, String modelId) throws PortalException {
		if (Validator.isNotNull(modelId)) {
			_modelIds.putIfAbsent(
				AMProcessorCommand.CLEAN_UP, new CopyOnWriteArrayList<>());

			List<String> cleanUpModelIds = _modelIds.get(
				AMProcessorCommand.CLEAN_UP);

			if (cleanUpModelIds.contains(modelId)) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Omitted clean up for model id " + modelId +
							" because it is already in progress");
				}

				return;
			}

			cleanUpModelIds.add(modelId);

			if (_log.isInfoEnabled()) {
				_log.info(
					"Added clean up for model id " + modelId + " to the queue");
			}
		}

		Message message = new Message();

		message.put("className", _clazz.getName());
		message.put("model", model);
		message.put("command", AMProcessorCommand.CLEAN_UP);

		if (Validator.isNotNull(modelId)) {
			message.put("modelId", modelId);
		}

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				_messageBus.sendMessage(
					AMDestinationNames.AM_PROCESSOR, message);

				return null;
			});
	}

	@Override
	public void triggerProcess(M model, String modelId) throws PortalException {
		if (Validator.isNotNull(modelId)) {
			_modelIds.putIfAbsent(
				AMProcessorCommand.PROCESS, new CopyOnWriteArrayList<>());

			List<String> processModelIds = _modelIds.get(
				AMProcessorCommand.PROCESS);

			if (processModelIds.contains(modelId)) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Omitted process for model id " + modelId +
							" because it is already in progress");
				}

				return;
			}

			processModelIds.add(modelId);

			if (_log.isInfoEnabled()) {
				_log.info(
					"Added process for model id " + modelId + " to the queue");
			}
		}

		Message message = new Message();

		message.put("className", _clazz.getName());
		message.put("model", model);
		message.put("command", AMProcessorCommand.PROCESS);

		if (Validator.isNotNull(modelId)) {
			message.put("modelId", modelId);
		}

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				_messageBus.sendMessage(
					AMDestinationNames.AM_PROCESSOR, message);

				return null;
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AMAsyncProcessorImpl.class);

	private static final Map<AMProcessorCommand, List<String>> _modelIds =
		new ConcurrentHashMap<>();

	private final Class<M> _clazz;
	private final MessageBus _messageBus;

}