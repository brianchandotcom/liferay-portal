/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * @author Shuyang Zhou
 */
public class BatchProcessor<T> {

	public BatchProcessor(
		String name, Consumer<List<T>> flushConsumer, long flushInterval,
		int batchSize) {

		_flushConsumer = flushConsumer;

		_scheduledExecutorService = new ScheduledThreadPoolExecutor(
			1, new NamedThreadFactory(name, Thread.NORM_PRIORITY, null));

		configure(flushInterval, batchSize);
	}

	public void add(T item) {
		_queue.add(item);

		int size = _queueSize.incrementAndGet();

		if (size >= _batchSize) {
			_flush();
		}
	}

	public void close() {
		_scheduledExecutorService.shutdown();

		_flush();
	}

	public void configure(long flushInterval, int batchSize) {
		synchronized (this) {
			if (_scheduledFuture != null) {
				_scheduledFuture.cancel(false);
			}

			if (flushInterval > 0) {
				_scheduledFuture =
					_scheduledExecutorService.scheduleWithFixedDelay(
						this::_flush, flushInterval, flushInterval,
						TimeUnit.MILLISECONDS);
			}
			else {
				_scheduledFuture = null;
			}

			_batchSize = batchSize;
		}
	}

	private void _flush() {
		List<T> items = new ArrayList<>();

		T item = null;

		while ((item = _queue.poll()) != null) {
			items.add(item);
		}

		int flushSize = items.size();

		if (flushSize > 0) {
			int size = _queueSize.get();

			while (!_queueSize.compareAndSet(size, size - flushSize)) {
				size = _queueSize.get();
			}

			_flushConsumer.accept(items);
		}
	}

	private volatile int _batchSize;
	private final Consumer<List<T>> _flushConsumer;
	private final Queue<T> _queue = new ConcurrentLinkedQueue<>();
	private final AtomicInteger _queueSize = new AtomicInteger();
	private final ScheduledExecutorService _scheduledExecutorService;
	private volatile ScheduledFuture<?> _scheduledFuture;

}