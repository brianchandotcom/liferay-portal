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

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Shuyang Zhou
 */
public class FinalizeManager {

	public static final ReferenceFactory PHANTOM_REFERENCE_FACTORY =
		new ReferenceFactory() {

			@Override
			public <T> Reference<T> createReference(
				T realReference, ReferenceQueue<? super T> referenceQueue) {

				return new EqualityPhantomReference<T>(
					realReference, referenceQueue);
			}

		};

	public static final ReferenceFactory SOFT_REFERENCE_FACTORY =
		new ReferenceFactory() {

			@Override
			public <T> Reference<T> createReference(
				T realReference, ReferenceQueue<? super T> referenceQueue) {

				return new EqualitySoftReference<T>(
					realReference, referenceQueue);
			}

		};

	public static final boolean THREAD_ENABLED = Boolean.getBoolean(
		FinalizeManager.class.getName() + ".thread.enabled");

	public static final ReferenceFactory WEAK_REFERENCE_FACTORY =
		new ReferenceFactory() {

			@Override
			public <T> Reference<T> createReference(
				T realReference, ReferenceQueue<? super T> referenceQueue) {

				return new EqualityWeakReference<T>(
					realReference, referenceQueue);
			}

		};

	public static <T> Reference<T> register(
		T realReference, FinalizeAction finalizeAction,
		ReferenceFactory referenceFactory) {

		Reference<T> reference = referenceFactory.createReference(
			realReference, _referenceQueue);

		_referenceActionMap.put(reference, finalizeAction);

		if (!THREAD_ENABLED) {
			_pollingCleanup();
		}

		return reference;
	}

	public interface ReferenceFactory {

		public <T> Reference<T> createReference(
			T realReference, ReferenceQueue<? super T> referenceQueue);

	}

	private static void _finalizeReference(
		Reference<? extends Object> reference) {

		FinalizeAction finalizeAction = _referenceActionMap.remove(reference);

		try {
			finalizeAction.doFinalize(reference);
		}
		finally {
			reference.clear();
		}
	}

	private static void _pollingCleanup() {
		Reference<? extends Object> reference = null;

		while ((reference = _referenceQueue.poll()) != null) {
			_finalizeReference(reference);
		}
	}

	private static final Map<Reference<?>, FinalizeAction> _referenceActionMap =
		new ConcurrentHashMap<Reference<?>, FinalizeAction>();
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

	static {
		if (THREAD_ENABLED) {
			Thread thread = new FinalizeThread("Finalize Thread");

			thread.setContextClassLoader(
				FinalizeManager.class.getClassLoader());

			thread.setDaemon(true);

			thread.start();
		}
	}

}