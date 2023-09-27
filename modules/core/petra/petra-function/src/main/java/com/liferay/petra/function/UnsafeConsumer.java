/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		T throwable1 = null;

		for (E e : collection) {
			try {
				unsafeConsumer.accept(e);
			}
			catch (Throwable throwable2) {
				if (!throwableClass.isInstance(throwable2)) {

					// Unexpected exception stops the loop and suppresses
					// previous expected exceptions

					if (throwable1 != null) {
						throwable2.addSuppressed(throwable1);
					}

					throw throwable2;
				}

				if (throwable1 == null) {
					throwable1 = throwableClass.cast(throwable2);
				}
				else {
					throwable1.addSuppressed(throwable2);
				}
			}
		}

		if (throwable1 != null) {
			throw throwable1;
		}
	}

	public void accept(E e) throws T;

}