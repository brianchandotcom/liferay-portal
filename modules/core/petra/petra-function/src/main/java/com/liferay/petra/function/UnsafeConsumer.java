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

package com.liferay.petra.function;

import java.util.Collection;

/**
 * @author Shuyang Zhou
 */
@FunctionalInterface
public interface UnsafeConsumer<E, T extends Throwable> {

	public static <E> void accept(
			Collection<E> collection,
			UnsafeConsumer<E, ? super Throwable> unsafeConsumer)
		throws Throwable {

		accept(collection, unsafeConsumer, Throwable.class);
	}

	public static <E, T extends Throwable> void accept(
			Collection<E> collection, UnsafeConsumer<E, T> unsafeConsumer,
			Class<? extends T> throwableClass)
		throws T {

		T throwable = null;

		for (E e : collection) {
			try {
				unsafeConsumer.accept(e);
			}
			catch (Throwable t) {
				if (!throwableClass.isInstance(t)) {

					// Unexpected exception stops the loop and suppresses
					// previous expected exceptions

					if (throwable != null) {
						t.addSuppressed(throwable);
					}

					throw t;
				}

				if (throwable == null) {
					throwable = throwableClass.cast(t);
				}
				else {
					throwable.addSuppressed(t);
				}
			}
		}

		if (throwable != null) {
			throw throwable;
		}
	}

	public void accept(E e) throws T;

}