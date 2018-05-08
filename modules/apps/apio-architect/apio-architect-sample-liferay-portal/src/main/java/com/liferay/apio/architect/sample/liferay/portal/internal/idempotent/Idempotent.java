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

package com.liferay.apio.architect.sample.liferay.portal.internal.idempotent;

import com.liferay.apio.architect.consumer.throwable.ThrowableConsumer;
import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * Provides methods for performing idempotent operations by discarding
 * exceptions thrown by Liferay services.
 *
 * @author Alejandro Hernández
 */
public class Idempotent {

	/**
	 * Calls the received consumer, ignoring all {@code NoSuchModelException}.
	 *
	 * @param  throwableConsumer the throwable consumer
	 * @return the new idempotent throwable consumer
	 */
	public static <T> ThrowableConsumer<T> idempotent(
		ThrowableConsumer<T> throwableConsumer) {

		return t -> {
			try {
				throwableConsumer.accept(t);
			}
			catch (NoSuchModelException nsme) {
			}
		};
	}

	private Idempotent() {
		throw new UnsupportedOperationException();
	}

}