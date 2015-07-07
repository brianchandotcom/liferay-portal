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

package com.liferay.portal.kernel.test;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

/**
 * @author Shuyang Zhou
 */
public class GCUtil {

	public static void fullGC(boolean ensureEnqueuedReferences)
		throws InterruptedException {

		ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();

		SoftReference<Object> softReference = new SoftReference<>(
			new Object(), referenceQueue);

		List<byte[]> list = new ArrayList<>();

		while (true) {
			try {
				list.add(new byte[100 * 1024 * 1024]);
			}
			catch (OutOfMemoryError oome) {
				list.clear();

				list = null;

				break;
			}
		}

		Assert.assertNull(softReference.get());
		Assert.assertSame(softReference, referenceQueue.remove());

		if (ensureEnqueuedReferences) {
			fullGC(false);
		}
	}

	public static void gc(boolean ensureEnqueuedReferences)
		throws InterruptedException {

		gc(true, ensureEnqueuedReferences);
	}

	public static void gc(boolean actively, boolean ensureEnqueuedReferences)
		throws InterruptedException {

		ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();

		WeakReference<Object> weakReference = new WeakReference<>(
			new Object(), referenceQueue);

		if (actively) {
			while (weakReference.get() != null) {
				System.gc();

				System.runFinalization();
			}
		}

		Assert.assertSame(weakReference, referenceQueue.remove());

		if (ensureEnqueuedReferences) {
			gc(actively, false);
		}
	}

}