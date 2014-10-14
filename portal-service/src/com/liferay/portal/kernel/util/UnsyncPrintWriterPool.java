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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.io.OutputStreamWriter;
import com.liferay.portal.kernel.io.unsync.UnsyncPrintWriter;
import com.liferay.portal.kernel.memory.PoolAction;
import com.liferay.portal.kernel.memory.SoftReferencePool;

import java.io.OutputStream;
import java.io.Writer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class UnsyncPrintWriterPool {

	public static UnsyncPrintWriter borrow(OutputStream outputStream) {
		return borrow(new OutputStreamWriter(outputStream));
	}

	public static UnsyncPrintWriter borrow(
		OutputStream outputStream, String charsetName) {

		return borrow(new OutputStreamWriter(outputStream, charsetName, true));
	}

	public static UnsyncPrintWriter borrow(Writer writer) {
		if (!isEnabled()) {
			return new UnsyncPrintWriter(writer);
		}

		UnsyncPrintWriter unsyncPrintWriter =
			_unsyncPrintWriterSoftReferencePool.borrowObject(writer);

		List<UnsyncPrintWriter> unsyncPrintWriters =
			_borrowedUnsyncPrintWritersThreadLocal.get();

		unsyncPrintWriters.add(unsyncPrintWriter);

		return unsyncPrintWriter;
	}

	public static void cleanUp() {
		List<UnsyncPrintWriter> unsyncPrintWriters =
			_borrowedUnsyncPrintWritersThreadLocal.get();

		for (UnsyncPrintWriter unsyncPrintWriter : unsyncPrintWriters) {
			_unsyncPrintWriterSoftReferencePool.returnObject(unsyncPrintWriter);
		}

		unsyncPrintWriters.clear();
	}

	public static boolean isEnabled() {
		return _enabledThreadLocal.get();
	}

	public static void setEnabled(boolean enabled) {
		_enabledThreadLocal.set(enabled);
	}

	private static final ThreadLocal<List<UnsyncPrintWriter>>
		_borrowedUnsyncPrintWritersThreadLocal =
			new AutoResetThreadLocal<List<UnsyncPrintWriter>>(
				UnsyncPrintWriterPool.class.getName() +
					"._borrowedUnsyncPrintWritersThreadLocal",
				new ArrayList<UnsyncPrintWriter>());
	private static final ThreadLocal<Boolean> _enabledThreadLocal =
		new AutoResetThreadLocal<Boolean>(
			UnsyncPrintWriterPool.class.getName() + "._enabledThreadLocal",
			false);
	private static final SoftReferencePool<UnsyncPrintWriter, Writer>
		_unsyncPrintWriterSoftReferencePool =
			new SoftReferencePool<UnsyncPrintWriter, Writer>(
				new UnsyncPrintWriterPoolAction(), 8192);

	private static class UnsyncPrintWriterPoolAction
		implements PoolAction<UnsyncPrintWriter, Writer> {

		@Override
		public UnsyncPrintWriter onBorrow(
			UnsyncPrintWriter unsyncPrintWriter, Writer writer) {

			unsyncPrintWriter.reset(writer);

			return unsyncPrintWriter;
		}

		@Override
		public UnsyncPrintWriter onCreate(Writer writer) {
			return new UnsyncPrintWriter(writer);
		}

		@Override
		public void onReturn(UnsyncPrintWriter unsyncPrintWriter) {
			unsyncPrintWriter.reset(null);
		}

	}

}