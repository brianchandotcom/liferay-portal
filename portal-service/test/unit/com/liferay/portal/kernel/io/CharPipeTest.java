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

package com.liferay.portal.kernel.io;

import com.liferay.portal.kernel.test.TestCase;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import java.nio.CharBuffer;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class CharPipeTest extends TestCase {

	@Test
	public void testCloseForce() {
		CharPipe charPipe = new CharPipe();

		assertFalse(charPipe.finished);

		charPipe.close(true);

		assertNull(charPipe.buffer);
		assertFalse(charPipe.finished);

		try {
			Reader reader = charPipe.getReader();

			reader.read();

			fail();
		}
		catch (IOException ioe) {
		}

		try {
			Reader reader = charPipe.getReader();

			reader.read(new char[1]);

			fail();
		}
		catch (IOException ioe) {
		}

		try {
			Reader reader = charPipe.getReader();

			reader.read(CharBuffer.allocate(1));

			fail();
		}
		catch (IOException ioe) {
		}

		try {
			Reader reader = charPipe.getReader();

			reader.ready();

			fail();
		}
		catch (IOException ioe) {
		}

		try {
			Reader reader = charPipe.getReader();

			reader.skip(1);

			fail();
		}
		catch (IOException ioe) {
		}

		try {
			Writer writer = charPipe.getWriter();

			writer.append('a');

			fail();
		}
		catch (IOException ioe) {
		}

		try {
			Writer writer = charPipe.getWriter();

			writer.append("a");

			fail();
		}
		catch (IOException ioe) {
		}

		try {
			Writer writer = charPipe.getWriter();

			writer.write("abc".toCharArray());

			fail();
		}
		catch (IOException ioe) {
		}

		try {
			Writer writer = charPipe.getWriter();

			writer.write('a');

			fail();
		}
		catch (IOException ioe) {
		}

		try {
			Writer writer = charPipe.getWriter();

			writer.write("a");

			fail();
		}
		catch (IOException ioe) {
		}
	}

	@Test
	public void testClosePeacefullyBlocking() throws Exception {
		final CharPipe charPipe = new CharPipe();

		Reader reader = charPipe.getReader();

		final AtomicLong timestampBeforeClose = new AtomicLong();

		Thread thread = new Thread() {

			@Override
			public void run() {
				try {
					timestampBeforeClose.set(System.currentTimeMillis());

					charPipe.close();
				}
				catch (Exception e) {
					fail(e.getMessage());
				}
			}

		};

		thread.start();

		int result = reader.read();

		long timestampAfterRead = System.currentTimeMillis();

		thread.join();

		assertEquals(-1, result);
		assertTrue(timestampAfterRead >= timestampBeforeClose.get());
	}

	@Test
	public void testClosePeacefullyEmpty() throws IOException {
		CharPipe charPipe = new CharPipe();

		assertFalse(charPipe.finished);

		charPipe.close();

		assertNotNull(charPipe.buffer);
		assertTrue(charPipe.finished);

		Reader reader = charPipe.getReader();

		assertEquals(-1, reader.read());
	}

	@Test
	public void testClosePeacefullyNotEmpty() throws IOException {
		CharPipe charPipe = new CharPipe();

		Writer writer = charPipe.getWriter();

		writer.write("abcd");

		assertFalse(charPipe.finished);

		charPipe.close();

		assertNotNull(charPipe.buffer);
		assertTrue(charPipe.finished);

		char[] buffer = new char[5];

		Reader reader = charPipe.getReader();

		int result = reader.read(buffer);

		assertEquals(4, result);
		assertEquals('a', buffer[0]);
		assertEquals('b', buffer[1]);
		assertEquals('c', buffer[2]);
		assertEquals('d', buffer[3]);
		assertEquals(0, buffer[4]);
		assertEquals(-1, reader.read());
	}

	@Test
	public void testConstructor() {
		CharPipe charPipe = new CharPipe();

		assertNotNull(charPipe.buffer);
		assertEquals(8192, charPipe.buffer.length);
		assertEquals(0, charPipe.count);
		assertEquals(0, charPipe.readIndex);
		assertEquals(0, charPipe.writeIndex);
		assertNotNull(charPipe.bufferLock);
		assertNotNull(charPipe.notEmpty);
		assertNotNull(charPipe.notFull);

		charPipe = new CharPipe(1024);

		assertNotNull(charPipe.buffer);
		assertEquals(1024, charPipe.buffer.length);
		assertEquals(0, charPipe.count);
		assertEquals(0, charPipe.readIndex);
		assertEquals(0, charPipe.writeIndex);
		assertNotNull(charPipe.bufferLock);
		assertNotNull(charPipe.notEmpty);
		assertNotNull(charPipe.notFull);

		charPipe.close();
	}

	@Test
	public void testGetReader() {
		CharPipe charPipe = new CharPipe();

		Reader reader1 = charPipe.getReader();
		Reader reader2 = charPipe.getReader();

		assertSame(reader1, reader2);
		assertFalse(reader1.markSupported());

		try {
			reader1.mark(1);

			fail();
		}
		catch (IOException ioe) {
		}

		try {
			reader1.reset();

			fail();
		}
		catch (IOException ioe) {
		}

		charPipe.close();
	}

	@Test
	public void testGetWriter() throws IOException {
		CharPipe charPipe = new CharPipe();

		Writer writer1 = charPipe.getWriter();
		Writer writer2 = charPipe.getWriter();

		assertSame(writer1, writer2);

		writer1.flush();

		charPipe.close();
	}

	@Test
	public void testPipingChar() throws IOException {
		CharPipe charPipe = new CharPipe(4);

		Reader reader = charPipe.getReader();

		assertFalse(reader.ready());

		Writer writer = charPipe.getWriter();

		writer.write('a');

		assertTrue(reader.ready());
		assertEquals('a', reader.read());
		assertFalse(reader.ready());

		writer.append('b');

		assertTrue(reader.ready());
		assertEquals('b', reader.read());
		assertFalse(reader.ready());

		charPipe.close();
	}

	@Test
	public void testPipingCharArray() throws IOException {
		CharPipe charPipe = new CharPipe(4);

		Writer writer = charPipe.getWriter();

		char[] data = "abcd".toCharArray();

		writer.write(data);

		char[] buffer = new char[4];

		Reader reader = charPipe.getReader();

		int result = reader.read(buffer);

		assertEquals(4, result);
		assertTrue(Arrays.equals(data, buffer));

		writer.append(new String(data));

		result = reader.read(buffer);

		assertEquals(4, result);
		assertTrue(Arrays.equals(data, buffer));

		charPipe.close();
	}

	@Test
	public void testPipingCharArrayWithOffset() throws IOException {
		CharPipe charPipe = new CharPipe(4);

		Writer writer = charPipe.getWriter();

		char[] data = "abcd".toCharArray();

		writer.write(data, 0, 0);

		Reader reader = charPipe.getReader();

		assertFalse(reader.ready());

		writer.write(data, 1, 2);

		char[] buffer = new char[4];

		int result = reader.read(buffer, 1, 0);

		assertEquals(0, result);

		result = reader.read(buffer, 1, 3);

		assertEquals(2, result);
		assertEquals('b', buffer[1]);
		assertEquals('c', buffer[2]);

		writer.append(new String(data), 1, 3);

		result = reader.read(buffer, 1, 0);

		assertEquals(0, result);

		result = reader.read(buffer, 1, 3);

		assertEquals(2, result);
		assertEquals('b', buffer[1]);
		assertEquals('c', buffer[2]);

		charPipe.close();
	}

	@Test
	public void testPipingCharArrayWithOffsetTwoStep() throws IOException {
		CharPipe charPipe = new CharPipe(4);

		Writer writer = charPipe.getWriter();

		char[] data = "abcd".toCharArray();

		writer.write(data);

		Reader reader = charPipe.getReader();

		char[] buffer = new char[4];

		int result = reader.read(buffer, 0, 3);

		assertEquals(3, result);
		assertEquals('a', buffer[0]);
		assertEquals('b', buffer[1]);
		assertEquals('c', buffer[2]);

		writer.write(data, 0, 3);

		result = reader.read(buffer);

		assertEquals(4, result);
		assertEquals('d', buffer[0]);
		assertEquals('a', buffer[1]);
		assertEquals('b', buffer[2]);
		assertEquals('c', buffer[3]);

		writer.write(data);

		result = reader.read(buffer);

		assertEquals(4, result);
		assertEquals('a', buffer[0]);
		assertEquals('b', buffer[1]);
		assertEquals('c', buffer[2]);
		assertEquals('d', buffer[3]);

		charPipe.close();
	}

	@Test
	public void testPipingCharBuffer() throws IOException {
		CharPipe charPipe = new CharPipe(4);

		Writer writer = charPipe.getWriter();

		writer.write("abcd");

		CharBuffer charBuffer = CharBuffer.allocate(0);

		Reader reader = charPipe.getReader();

		int result = reader.read(charBuffer);

		assertEquals(0, result);

		charBuffer = CharBuffer.allocate(2);

		result = reader.read(charBuffer);

		assertEquals(2, result);

		charBuffer.flip();

		assertEquals('a', charBuffer.get());
		assertEquals('b', charBuffer.get());

		charPipe.close();
	}

	@Test
	public void testPipingString() throws IOException {
		CharPipe charPipe = new CharPipe(4);

		Reader reader = charPipe.getReader();
		Writer writer = charPipe.getWriter();

		writer.write("abcd");

		char[] buffer = new char[4];

		int result = reader.read(buffer);

		assertEquals(4, result);
		assertEquals("abcd", new String(buffer));

		charPipe.close();
	}

	@Test
	public void testPipingStringWithOffset() throws IOException {
		CharPipe charPipe = new CharPipe(4);

		Writer writer = charPipe.getWriter();

		writer.write("abcd", 0, 0);

		Reader reader = charPipe.getReader();

		assertFalse(reader.ready());

		writer.write("abcd", 1, 3);

		char[] buffer = new char[4];

		int result = reader.read(buffer, 1, 0);

		assertEquals(0, result);

		result = reader.read(buffer, 1, 3);

		assertEquals(3, result);
		assertEquals('b', buffer[1]);
		assertEquals('c', buffer[2]);
		assertEquals('d', buffer[3]);

		charPipe.close();
	}

	@Test
	public void testPipingStringWithOffsetTwoStep() throws IOException {
		CharPipe charPipe = new CharPipe(4);

		Writer writer = charPipe.getWriter();

		writer.write("abcd");

		char[] buffer = new char[4];

		Reader reader = charPipe.getReader();

		int result = reader.read(buffer, 0, 3);

		assertEquals(3, result);
		assertEquals('a', buffer[0]);
		assertEquals('b', buffer[1]);
		assertEquals('c', buffer[2]);

		writer.write("abcd", 0, 3);

		result = reader.read(buffer);

		assertEquals(4, result);
		assertEquals('d', buffer[0]);
		assertEquals('a', buffer[1]);
		assertEquals('b', buffer[2]);
		assertEquals('c', buffer[3]);

		writer.write("abcd");

		result = reader.read(buffer);

		assertEquals(4, result);
		assertEquals('a', buffer[0]);
		assertEquals('b', buffer[1]);
		assertEquals('c', buffer[2]);
		assertEquals('d', buffer[3]);

		charPipe.close();
	}

	@Test
	public void testSkip() throws Exception {
		CharPipe charPipe = new CharPipe(4);

		Reader reader = charPipe.getReader();

		try {
			reader.skip(-1);

			fail();
		}
		catch (IllegalArgumentException iae) {
		}

		Writer writer = charPipe.getWriter();

		SlowWriterJob slowWriterJob = new SlowWriterJob(writer, 4, false);

		Thread thread = new Thread(slowWriterJob);

		thread.start();

		for (int i = 0; i < 10; i++) {
			long timestampBeforeWrite = slowWriterJob.getTimestampBeforeWrite();
			long timestampAfterSkip1 = _timestampedSkip(reader, 2);
			long timestampAfterSkip2 = _timestampedSkip(reader, 2);

			assertTrue(timestampAfterSkip1 >= timestampBeforeWrite);
			assertTrue(timestampAfterSkip2 >= timestampAfterSkip1);

			if (System.getenv("JENKINS_URL") == null) {
				assertTrue(
					(timestampAfterSkip1 - timestampBeforeWrite) >=
						(timestampAfterSkip2 - timestampAfterSkip1));
			}
		}

		charPipe.close();

		thread.join();

		assertFalse(slowWriterJob.isFailed());
	}

	@Test
	public void testSlowReader() throws Exception {
		CharPipe charPipe = new CharPipe(4);

		Reader reader = charPipe.getReader();

		SlowReaderJob slowReaderJob = new SlowReaderJob(
			reader, 4, false, false);

		Thread thread = new Thread(slowReaderJob);

		Writer writer = charPipe.getWriter();

		writer.write("abcd");

		thread.start();

		for (int i = 0; i < 5; i++) {
			if ((i % 2) == 0) {
				assertTrue(
					_timestampedWrite(writer, "abcdefgh") >=
						slowReaderJob.getTimestampBeforeRead());
			}
			else {
				assertTrue(
					_timestampedWrite(writer, "abcdefgh".toCharArray()) >=
						slowReaderJob.getTimestampBeforeRead());
			}
		}

		charPipe.close();

		thread.join();

		assertFalse(slowReaderJob.isFailed());
	}

	@Test
	public void testSlowReaderOnCloseForce() throws Exception {
		CharPipe charPipe = new CharPipe(4);

		Reader reader = charPipe.getReader();

		SlowReaderJob slowReaderJob = new SlowReaderJob(reader, 4, true, true);

		Thread thread = new Thread(slowReaderJob);

		Writer writer = charPipe.getWriter();

		writer.write("abcd");

		thread.start();

		for (int i = 0; i < 2; i++) {
			assertTrue(
				_timestampedWrite(writer, "abcdefgh") >=
					slowReaderJob.getTimestampBeforeRead());
		}

		charPipe.close(true);

		thread.join();

		assertFalse(slowReaderJob.isFailed());
	}

	@Test
	public void testSlowReaderOnClosePeacefully() throws Exception {
		CharPipe charPipe = new CharPipe(4);

		Reader reader = charPipe.getReader();

		SlowReaderJob slowReaderJob = new SlowReaderJob(reader, 4, true, false);

		Thread thread = new Thread(slowReaderJob);

		Writer writer = charPipe.getWriter();

		writer.write("abcd");

		thread.start();

		for (int i = 0; i < 2; i++) {
			assertTrue(
				_timestampedWrite(writer, "abcdefgh") >=
					slowReaderJob.getTimestampBeforeRead());
		}

		charPipe.close();

		thread.join();

		assertFalse(slowReaderJob.isFailed());
	}

	@Test
	public void testSlowWriter() throws Exception {
		CharPipe charPipe = new CharPipe(4);

		Writer writer = charPipe.getWriter();

		SlowWriterJob slowWriterJob = new SlowWriterJob(writer, 4, false);

		Thread thread = new Thread(slowWriterJob);

		thread.start();

		for (int i = 0; i < 10; i++) {
			Reader reader = charPipe.getReader();

			char[] buffer = new char[8];

			assertTrue(
				_timestampedRead(reader, buffer) >=
					slowWriterJob.getTimestampBeforeWrite());
		}

		charPipe.close();

		thread.join();

		assertFalse(slowWriterJob.isFailed());
	}

	@Test
	public void testSlowWriterOnClose() throws Exception {
		CharPipe charPipe = new CharPipe(4);

		Writer writer = charPipe.getWriter();

		SlowWriterJob slowWriterJob = new SlowWriterJob(writer, 4, true);

		Thread thread = new Thread(slowWriterJob);

		thread.start();

		for (int i = 0; i < 5; i++) {
			Reader reader = charPipe.getReader();

			char[] buffer = new char[8];

			assertTrue(
				_timestampedRead(reader, buffer) >=
					slowWriterJob.getTimestampBeforeWrite());
		}

		charPipe.close();

		thread.join();

		assertFalse(slowWriterJob.isFailed());
	}

	private void _randomWait(int time) throws InterruptedException {
		if (time < 0) {
			throw new IllegalArgumentException();
		}

		int range = time / 2;

		int waitTime = new Random().nextInt(range) + range;

		Thread.sleep(waitTime);
	}

	private long _timestampedRead(Reader reader, char[] buffer)
		throws IOException {

		reader.read(buffer);

		return System.currentTimeMillis();
	}

	private long _timestampedSkip(Reader reader, int skipSize)
		throws IOException {

		reader.skip(skipSize);

		return System.currentTimeMillis();
	}

	private long _timestampedWrite(Writer writer, char[] data)
		throws IOException {

		writer.write(data);

		return System.currentTimeMillis();
	}

	private long _timestampedWrite(Writer writer, String data)
		throws IOException {

		writer.write(data);

		return System.currentTimeMillis();
	}

	private class SlowReaderJob implements Runnable {

		public SlowReaderJob(
			Reader reader, int bufferSize, boolean close, boolean force) {

			_reader = reader;
			_buffer = new char[bufferSize];
			_close = close;
			_force = force;
		}

		public long getTimestampBeforeRead() throws InterruptedException {
			return _timestampsBeforeRead.take();
		}

		public boolean isFailed() {
			return _failed;
		}

		@Override
		public void run() {
			try {
				for (int i = 0; i < 10; i++) {
					_randomWait(100);

					_timestampsBeforeRead.put(System.currentTimeMillis());

					int result = _reader.read(_buffer);

					if (result == _buffer.length) {
						continue;
					}
					else if (_close && !_force && (result == -1)) {
						return;
					}
					else {
						_failed = true;

						break;
					}
				}

				if (_close && _force) {
					_failed = true;
				}
			}
			catch (Exception e) {
				if (!_close) {
					_failed = true;
				}
			}
		}

		private char[] _buffer;
		private boolean _close;
		private boolean _failed;
		private boolean _force;
		private Reader _reader;
		private final BlockingQueue<Long> _timestampsBeforeRead =
			new LinkedBlockingQueue<Long>();

	}

	private class SlowWriterJob implements Runnable {

		public SlowWriterJob(
			Writer writer, int dataSize, boolean expectException) {

			_writer = writer;
			_dataSize = dataSize;
			_expectException = expectException;
		}

		public long getTimestampBeforeWrite() throws InterruptedException {
			return _timestampsBeforeWrite.take();
		}

		public boolean isFailed() {
			return _failed;
		}

		@Override
		public void run() {
			try {
				for (int i = 0; i < 10; i++) {
					_randomWait(100);

					_timestampsBeforeWrite.put(System.currentTimeMillis());

					_writer.write(new char[_dataSize]);
				}

				if (_expectException) {
					_failed = true;
				}
			}
			catch (Exception e) {
				if (!_expectException) {
					_failed = true;
				}
			}

		}

		private int _dataSize;
		private boolean _expectException;
		private boolean _failed;
		private final BlockingQueue<Long> _timestampsBeforeWrite =
			new LinkedBlockingQueue<Long>();
		private Writer _writer;

	}

}