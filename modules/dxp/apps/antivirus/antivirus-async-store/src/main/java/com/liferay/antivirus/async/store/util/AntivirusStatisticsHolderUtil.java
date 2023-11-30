/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.antivirus.async.store.util;

import com.liferay.antivirus.async.store.event.AntivirusAsyncEvent;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Roberto Cassio Silva do Nascimento Junior
 */
public class AntivirusStatisticsHolderUtil {

	public static long getProcessingErrorCount() {
		return _processingErrorCounter.get();
	}

	public static long getSizeExceededCount() {
		return _sizeExceededCounter.get();
	}

	public static long getTotalScannedCount() {
		return _totalScannedCounter.get();
	}

	public static long getVirusFoundCount() {
		return _virusFoundCounter.get();
	}

	public static void onAntivirusEvent(
		AntivirusAsyncEvent antivirusAsyncEvent) {

		if (antivirusAsyncEvent == AntivirusAsyncEvent.PROCESSING_ERROR) {
			_processingErrorCounter.incrementAndGet();
		}
		else if (antivirusAsyncEvent == AntivirusAsyncEvent.SIZE_EXCEEDED) {
			_sizeExceededCounter.incrementAndGet();
		}
		else if (antivirusAsyncEvent == AntivirusAsyncEvent.SUCCESS) {
			_totalScannedCounter.incrementAndGet();
		}
		else if (antivirusAsyncEvent == AntivirusAsyncEvent.VIRUS_FOUND) {
			_totalScannedCounter.incrementAndGet();
			_virusFoundCounter.incrementAndGet();
		}
	}

	public static void reset() {
		_processingErrorCounter.set(0);
		_sizeExceededCounter.set(0);
		_totalScannedCounter.set(0);
		_virusFoundCounter.set(0);
	}

	private static final AtomicLong _processingErrorCounter = new AtomicLong();
	private static final AtomicLong _sizeExceededCounter = new AtomicLong();
	private static final AtomicLong _totalScannedCounter = new AtomicLong();
	private static final AtomicLong _virusFoundCounter = new AtomicLong();

}