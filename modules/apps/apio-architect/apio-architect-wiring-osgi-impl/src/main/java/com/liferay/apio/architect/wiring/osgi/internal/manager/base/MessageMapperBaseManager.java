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

package com.liferay.apio.architect.wiring.osgi.internal.manager.base;

import com.liferay.apio.architect.logger.ApioLogger;
import com.liferay.apio.architect.message.json.MessageMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import javax.ws.rs.core.MediaType;

import org.osgi.framework.ServiceReference;

/**
 * Manages message mappers.
 *
 * @author Alejandro Hernández
 * @param  <T> the message mapper's type
 */
public abstract class MessageMapperBaseManager<T extends MessageMapper>
	extends BaseManager<T, String> {

	public MessageMapperBaseManager(
		Class<T> managedClass, BiConsumer<MediaType, T> storeBiConsumer) {

		super(managedClass);

		_storeBiConsumer = storeBiConsumer;
	}

	/**
	 * Computes the list of managed message mappers and uses the provided
	 * consumer to store them.
	 */
	protected void computeMessageMappers() {
		Stream<String> stream = getKeyStream();

		stream.forEach(
			key -> {
				T formMessageMapper = serviceTrackerMap.getService(key);

				try {
					MediaType mediaType = MediaType.valueOf(key);

					_storeBiConsumer.accept(mediaType, formMessageMapper);
				}
				catch (IllegalArgumentException iae) {
					Optional<ApioLogger> optional = getLoggerOptional();

					optional.ifPresent(
						apioLogger -> apioLogger.warning(
							"Message mapper has invalid media type: " + key));
				}
			});
	}

	@Override
	protected void emit(
		ServiceReference<T> serviceReference,
		ServiceReferenceMapper.Emitter<String> emitter) {

		T t = bundleContext.getService(serviceReference);

		emitter.emit(t.getMediaType());
	}

	/**
	 * Returns the logger, if present, that this mapper uses; {@code
	 * Optional#empty()} otherwise.
	 *
	 * @return the logger, if present; {@code Optional#empty()} otherwise
	 */
	protected abstract Optional<ApioLogger> getLoggerOptional();

	private final BiConsumer<MediaType, T> _storeBiConsumer;

}