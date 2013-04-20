/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.nio.intraband.mailbox;

import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.IntraBand;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.SystemDataType;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.nio.ByteBuffer;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Shuyang Zhou
 */
public class MailboxUtil {

	public static final boolean REAPER_THREAD_ENABLED = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.INTRABAND_MAILBOX_REAPER_THREAD_ENABLED));

	public static final long STORAGE_LIFE = GetterUtil.getLong(
		PropsUtil.get(PropsKeys.INTRABAND_MAILBOX_STORAGE_LIFE));

	public static ByteBuffer receiveMail(long receipt) {
		ByteBuffer byteBuffer = _mailMap.remove(receipt);

		_overdueMailQueue.remove(new ReceiptStub(receipt));

		if (!REAPER_THREAD_ENABLED) {
			_pollingCleanup();
		}

		return byteBuffer;
	}

	public static long sendMail(
			RegistrationReference registrationReference, ByteBuffer byteBuffer)
		throws MailboxException {

		IntraBand intraBand = registrationReference.getIntraBand();

		try {
			SystemDataType systemDataType = SystemDataType.MAILBOX;

			Datagram responseDatagram = intraBand.sendSyncDatagram(
				registrationReference,
				Datagram.createRequestDatagram(
					systemDataType.getValue(), byteBuffer));

			byteBuffer = responseDatagram.getDataByteBuffer();

			return byteBuffer.getLong();
		}
		catch (Exception e) {
			throw new MailboxException(e);
		}
	}

	static long depositMail(ByteBuffer byteBuffer) {
		long receipt = _receiptGenerator.getAndIncrement();

		_mailMap.put(receipt, byteBuffer);

		_overdueMailQueue.offer(new ReceiptStub(receipt, System.nanoTime()));

		if (!REAPER_THREAD_ENABLED) {
			_pollingCleanup();
		}

		return receipt;
	}

	private static void _pollingCleanup() {
		ReceiptStub receiptStub = null;

		while ((receiptStub = _overdueMailQueue.poll()) != null) {
			_mailMap.remove(receiptStub.getReceipt());
		}
	}

	private static final Map<Long, ByteBuffer> _mailMap =
		new ConcurrentHashMap<Long, ByteBuffer>();

	private static final BlockingQueue<ReceiptStub> _overdueMailQueue =
		new DelayQueue<ReceiptStub>();

	private static final AtomicLong _receiptGenerator = new AtomicLong();

	private static class OverdueMailReaperThread extends Thread {

		public OverdueMailReaperThread(String name) {
			super(name);
		}

		@Override
		public void run() {
			while (true) {
				try {
					ReceiptStub receiptStub = _overdueMailQueue.take();

					_mailMap.remove(receiptStub.getReceipt());
				}
				catch (InterruptedException ie) {
				}
			}
		}
	}

	private static class ReceiptStub implements Delayed {

		public ReceiptStub(long receipt) {
			this(receipt, -1);
		}

		public ReceiptStub(long receipt, long currentNanoTime) {
			_expireTime = currentNanoTime + TimeUnit.MILLISECONDS.toNanos(
				STORAGE_LIFE);
			_receipt = receipt;
		}

		@Override
		public int compareTo(Delayed delayed) {
			ReceiptStub receiptStub = (ReceiptStub)delayed;

			return (int)(_expireTime - receiptStub._expireTime);
		}

		@Override
		public boolean equals(Object obj) {
			ReceiptStub receiptStub = (ReceiptStub)obj;

			return _receipt == receiptStub._receipt;
		}

		public long getReceipt() {
			return _receipt;
		}

		@Override
		public long getDelay(TimeUnit unit) {
			return _expireTime - System.nanoTime();
		}

		private final long _expireTime;
		private final long _receipt;

	}

	static {
		if (REAPER_THREAD_ENABLED) {
			Thread thread = new OverdueMailReaperThread(
				"Overdue Mail Reaper Thread");

			thread.setContextClassLoader(MailboxUtil.class.getClassLoader());

			thread.setDaemon(true);

			thread.start();
		}
	}

}