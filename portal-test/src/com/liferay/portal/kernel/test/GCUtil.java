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
import java.lang.ref.WeakReference;

import org.junit.Assert;

/**
 * @author Shuyang Zhou
 */
public class GCUtil {

	public static void gc() throws InterruptedException {
		ReferenceQueue<Object> referenceQueue = new ReferenceQueue<Object>();

		WeakReference<Object> weakReference = new WeakReference<Object>(
			new Object(), referenceQueue);

		while (weakReference.get() != null) {
			System.gc();

			System.runFinalization();
		}

		Assert.assertSame(weakReference, referenceQueue.remove());
	}

}