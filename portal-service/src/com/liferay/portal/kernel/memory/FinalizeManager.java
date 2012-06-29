/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Shuyang Zhou
 */
public class FinalizeManager {

	public static final boolean THREAD_ENABLED = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.FINALIZE_MANAGER_THREAD_ENABLED));

	public static <T> Reference<T> register(
		T realReference, FinalizeAction finalizeAction) {

		Reference<T> reference = new EqualityWeakReference<T>(
			realReference, _referenceQueue);

		_referenceActionMap.put(reference, finalizeAction);

		if (!THREAD_ENABLED) {
			_pollingCleanup();
		}

		return reference;
	}

	private static void _pollingCleanup() {
		Reference<? extends Object> reference = null;

		while ((reference = _referenceQueue.poll()) != null) {
			FinalizeAction finalizeAction = _referenceActionMap.remove(
				reference);

			finalizeAction.doFinalize();
		}
	}

	private static Map<Reference<?>, FinalizeAction> _referenceActionMap =
		new ConcurrentHashMap<Reference<?>, FinalizeAction>();
	private static ReferenceQueue<Object> _referenceQueue =
		new ReferenceQueue<Object>();

	private static class FinalizeThread extends Thread {

		public FinalizeThread(String name) {
			super(name);
		}

		@Override
		public void run() {
			while (true) {
				try {
					Reference<? extends Object> reference =
						_referenceQueue.remove();

					FinalizeAction finalizeAction = _referenceActionMap.remove(
						reference);

					finalizeAction.doFinalize();
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