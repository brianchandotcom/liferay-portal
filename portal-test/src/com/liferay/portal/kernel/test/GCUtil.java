/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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