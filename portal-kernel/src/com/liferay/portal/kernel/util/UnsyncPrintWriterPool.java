/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.memory.PoolAction;
import com.liferay.petra.memory.SoftReferencePool;
import com.liferay.portal.kernel.io.OutputStreamWriter;
import com.liferay.portal.kernel.io.unsync.UnsyncPrintWriter;

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
		_borrowedUnsyncPrintWritersThreadLocal = new CentralizedThreadLocal<>(
			UnsyncPrintWriterPool.class.getName() +
				"._borrowedUnsyncPrintWritersThreadLocal",
			ArrayList::new);
	private static final ThreadLocal<Boolean> _enabledThreadLocal =
		new CentralizedThreadLocal<>(
			UnsyncPrintWriterPool.class.getName() + "._enabledThreadLocal",
			() -> Boolean.FALSE);
	private static final SoftReferencePool<UnsyncPrintWriter, Writer>
		_unsyncPrintWriterSoftReferencePool = new SoftReferencePool<>(
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