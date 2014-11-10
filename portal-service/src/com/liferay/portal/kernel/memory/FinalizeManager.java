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

package com.liferay.portal.kernel.memory;

import com.liferay.portal.kernel.concurrent.ConcurrentIdentityHashMap;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class FinalizeManager {

	public static final ReferenceFactory PHANTOM_REFERENCE_FACTORY =
		new ReferenceFactory() {

			@Override
			public <T> Reference<T> createReference(
				T reference, ReferenceQueue<? super T> referenceQueue) {

				return new EqualityPhantomReference<T>(
					reference, referenceQueue) {

					@Override
					public void clear() {
						_finalizeActions.remove(this);

						super.clear();
					}

				};
			}

		};

	public static final ReferenceFactory SOFT_REFERENCE_FACTORY =
		new ReferenceFactory() {

			@Override
			public <T> Reference<T> createReference(
				T reference, ReferenceQueue<? super T> referenceQueue) {

				return new EqualitySoftReference<T>(reference, referenceQueue) {

					@Override
					public void clear() {
						_finalizeActions.remove(this);

						super.clear();
					}

				};
			}

		};

	public static final boolean THREAD_ENABLED = Boolean.getBoolean(
		FinalizeManager.class.getName() + ".thread.enabled");

	public static final ReferenceFactory WEAK_REFERENCE_FACTORY =
		new ReferenceFactory() {

			@Override
			public <T> Reference<T> createReference(
				T reference, ReferenceQueue<? super T> referenceQueue) {

				return new EqualityWeakReference<T>(reference, referenceQueue) {

					@Override
					public void clear() {
						_finalizeActions.remove(this);

						super.clear();
					}

				};
			}

		};

	static {
		if (THREAD_ENABLED) {
			Thread thread = new FinalizeThread("Finalize Thread");

			thread.setContextClassLoader(
				FinalizeManager.class.getClassLoader());

			thread.setDaemon(true);

			thread.start();
		}
	}

	public static <T> Reference<T> register(
		T reference, FinalizeAction finalizeAction,
		ReferenceFactory referenceFactory) {

		Reference<T> newReference = referenceFactory.createReference(
			reference, _referenceQueue);

		_finalizeActions.put(newReference, finalizeAction);

		if (!THREAD_ENABLED) {
			_pollingCleanup();
		}

		return newReference;
	}

	public interface ReferenceFactory {

		public <T> Reference<T> createReference(
			T realReference, ReferenceQueue<? super T> referenceQueue);

	}

	private static void _finalizeReference(
		Reference<? extends Object> reference) {

		FinalizeAction finalizeAction = _finalizeActions.remove(reference);

		if (finalizeAction != null) {
			try {
				finalizeAction.doFinalize(reference);
			}
			finally {
				reference.clear();
			}
		}
	}

	private static void _pollingCleanup() {
		Reference<? extends Object> reference = null;

		while ((reference = _referenceQueue.poll()) != null) {
			_finalizeReference(reference);
		}
	}

	private static final Map<Reference<?>, FinalizeAction> _finalizeActions =
		new ConcurrentIdentityHashMap<Reference<?>, FinalizeAction>();
	private static final ReferenceQueue<Object> _referenceQueue =
		new ReferenceQueue<Object>();

	private static class FinalizeThread extends Thread {

		public FinalizeThread(String name) {
			super(name);
		}

		@Override
		public void run() {
			while (true) {
				try {
					_finalizeReference(_referenceQueue.remove());
				}
				catch (InterruptedException ie) {
				}
			}
		}

	}

}