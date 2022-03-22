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

package com.liferay.portal.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.util.PropsValues;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class UpgradeManagedStartupTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_connection = DataAccess.getConnection();

		_bundle = FrameworkUtil.getBundle(UpgradeManagedStartupTest.class);
	}

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "UPGRADE_DATABASE_LOCK_REFRESH_TIME",
			_REFRESH_TIME);
	}

	@After
	public void tearDown() throws IllegalAccessException, SQLException {
		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "UPGRADE_DATABASE_MANAGED_STARTUP",
			_ORIGINAL_UPGRADE_DATABASE_MANAGED_STARTUP);

		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "UPGRADE_DATABASE_LOCK_REFRESH_TIME",
			_ORIGINAL_UPGRADE_DATABASE_LOCK_REFRESH_TIME);
	}

	@Test(timeout = 4 * _REFRESH_TIME)
	public void testAcquireLockWhenLocksExists() throws Exception {
		_insertLock();

		LockInfo lock1 = _getLock();

		AtomicReference<Thread> internalThreadReference =
			new AtomicReference<>();

		Class<?> clazz = _bundle.loadClass(DBUpgrader.class.getName());

		CountDownLatch countDownLatch = new CountDownLatch(1);

		try {
			Thread thread = new Thread(
				() -> {
					internalThreadReference.set(
						ReflectionTestUtil.invoke(
							clazz, "_acquireLock",
							new Class<?>[] {String.class}, _OWNERS[1]));
					countDownLatch.countDown();
				});

			thread.start();

			countDownLatch.await(2 * _REFRESH_TIME, TimeUnit.MILLISECONDS);

			Thread internalThread = internalThreadReference.get();

			Assert.assertNotNull(internalThread);
			Assert.assertTrue(internalThread.isAlive());

			LockInfo lock2 = _getLock();

			Assert.assertNotNull(lock2);
			Assert.assertEquals(lock2.getOwner(), _OWNERS[1]);
			Assert.assertNotEquals(lock1.getLockId(), lock2.getLockId());

			Thread.sleep(_REFRESH_TIME);

			LockInfo lock3 = _getLock();

			Assert.assertTrue(internalThread.isAlive());
			Assert.assertNotNull(lock3);
			Assert.assertEquals(lock3.getOwner(), _OWNERS[1]);

			Timestamp lock2ExpirationDate = lock2.getExpirationDate();
			Timestamp lock3ExpirationDate = lock3.getExpirationDate();

			Assert.assertTrue(lock3ExpirationDate.after(lock2ExpirationDate));

			Assert.assertEquals(lock2.getLockId(), lock3.getLockId());
		}
		finally {
			Thread internalThread = internalThreadReference.get();

			if (internalThread != null) {
				internalThread.interrupt();
				internalThread.join();
			}

			_deleteLock(_OWNERS[0]);
			_deleteLock(_OWNERS[1]);
		}
	}

	@Test(timeout = 4 * _REFRESH_TIME)
	public void testAcquireLockWhenNoLocksExists() throws Exception {
		AtomicReference<Thread> internalThreadReference =
			new AtomicReference<>();

		Class<?> clazz = _bundle.loadClass(DBUpgrader.class.getName());

		CountDownLatch countDownLatch = new CountDownLatch(1);

		Thread internalThread = null;

		try {
			Thread thread = new Thread(
				() -> {
					internalThreadReference.set(
						ReflectionTestUtil.invoke(
							clazz, "_acquireLock",
							new Class<?>[] {String.class}, _OWNERS[0]));
					countDownLatch.countDown();
				});

			thread.start();

			countDownLatch.await(_REFRESH_TIME, TimeUnit.MILLISECONDS);

			internalThread = internalThreadReference.get();

			Assert.assertNotNull(internalThread);
			Assert.assertTrue(internalThread.isAlive());

			LockInfo lock1 = _getLock();

			Assert.assertNotNull(lock1);
			Assert.assertEquals(lock1.getOwner(), _OWNERS[0]);

			Thread.sleep(_REFRESH_TIME);

			LockInfo lock2 = _getLock();

			Assert.assertTrue(internalThread.isAlive());
			Assert.assertNotNull(lock2);
			Assert.assertEquals(lock2.getOwner(), _OWNERS[0]);

			Timestamp lock1ExpirationDate = lock1.getExpirationDate();
			Timestamp lock2ExpirationDate = lock2.getExpirationDate();

			Assert.assertTrue(lock2ExpirationDate.after(lock1ExpirationDate));

			Assert.assertEquals(lock1.getLockId(), lock2.getLockId());
		}
		finally {
			if (internalThread != null) {
				internalThread.interrupt();
				internalThread.join();
			}

			_deleteLock(_OWNERS[0]);
		}
	}

	@Test(timeout = 4 * _REFRESH_TIME)
	public void testReleaseLocks() throws Exception {
		_insertLock();

		Thread thread = new Thread(
			() -> {
				Thread currentThread = Thread.currentThread();

				while (!currentThread.isInterrupted()) {
				}
			});

		try {
			thread.start();

			Class<?> clazz = _bundle.loadClass(DBUpgrader.class.getName());

			ReflectionTestUtil.invoke(
				clazz, "_releaseLock",
				new Class<?>[] {Thread.class, String.class}, thread,
				_OWNERS[0]);

			Assert.assertFalse(thread.isAlive());
			Assert.assertNull(_getLock());
		}
		finally {
			if (thread.isAlive()) {
				thread.interrupt();
			}

			_deleteLock(_OWNERS[0]);
		}
	}

	@Test(timeout = 4 * _REFRESH_TIME)
	public void testWaitForLocksWhenManagedStartupDisabled() throws Exception {
		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "UPGRADE_DATABASE_MANAGED_STARTUP", false);

		_insertLock();

		Thread thread = new Thread(
			() -> {
				try {
					DBUpgrader.waitForLocks();
				}
				catch (Exception exception) {
				}
			});

		thread.start();

		Thread.sleep(_REFRESH_TIME / 2);

		Assert.assertFalse(thread.isAlive());
		Assert.assertNotNull(_getLock());

		_deleteLock(_OWNERS[0]);
	}

	@Test(timeout = 4 * _REFRESH_TIME)
	public void testWaitForLocksWhenManagedStartupEnabled() throws Exception {
		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "UPGRADE_DATABASE_MANAGED_STARTUP", true);

		_insertLock();

		Thread thread = new Thread(
			() -> {
				try {
					DBUpgrader.waitForLocks();
				}
				catch (Exception exception) {
				}
			});

		thread.start();

		Thread.sleep(_REFRESH_TIME / 2);

		Assert.assertTrue(thread.isAlive());
		Assert.assertNotNull(_getLock());

		thread.join(2 * _REFRESH_TIME);

		Assert.assertFalse(thread.isAlive());
		Assert.assertNull(_getLock());

		_deleteLock(_OWNERS[0]);
	}

	private void _deleteLock(String owner) throws SQLException {
		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				"delete from Lock_ where className = ? and key_ = ? and " +
					"owner = ?")) {

			preparedStatement.setString(1, DBUpgrader.class.getName());
			preparedStatement.setString(2, _LOCK_KEY);
			preparedStatement.setString(3, owner);

			preparedStatement.executeUpdate();
		}
	}

	private LockInfo _getLock() throws Exception {
		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				"select lockId, owner, expirationDate from Lock_ where " +
					"className = ? and key_ = ?")) {

			preparedStatement.setString(1, DBUpgrader.class.getName());
			preparedStatement.setString(2, _LOCK_KEY);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					long lockId = resultSet.getLong(1);
					String owner = resultSet.getString(2);
					Timestamp expirationDate = resultSet.getTimestamp(3);

					return new LockInfo(expirationDate, lockId, owner);
				}

				return null;
			}
		}
	}

	private void _insertLock() throws Exception {
		try (PreparedStatement preparedStatement = _connection.prepareStatement(
				"insert into Lock_ (lockId, createDate, className, key_, " +
					"owner, expirationDate) values (?, ?, ?, ?, ?, ?)")) {

			long lockId = SecureRandomUtil.nextLong();

			Timestamp now = new Timestamp(System.currentTimeMillis());

			Timestamp expirationDate = new Timestamp(
				now.getTime() + _REFRESH_TIME);

			preparedStatement.setLong(1, lockId);
			preparedStatement.setTimestamp(2, now);
			preparedStatement.setString(3, DBUpgrader.class.getName());
			preparedStatement.setString(4, _LOCK_KEY);
			preparedStatement.setString(5, _OWNERS[0]);
			preparedStatement.setTimestamp(6, expirationDate);

			preparedStatement.executeUpdate();
		}
	}

	private static final String _LOCK_KEY = ReflectionTestUtil.getFieldValue(
		DBUpgrader.class, "_LOCK_KEY");

	private static final long _ORIGINAL_UPGRADE_DATABASE_LOCK_REFRESH_TIME =
		ReflectionTestUtil.getFieldValue(
			PropsValues.class, "UPGRADE_DATABASE_LOCK_REFRESH_TIME");

	private static final boolean _ORIGINAL_UPGRADE_DATABASE_MANAGED_STARTUP =
		ReflectionTestUtil.getFieldValue(
			PropsValues.class, "UPGRADE_DATABASE_MANAGED_STARTUP");

	private static final String[] _OWNERS = {"testOwner", "testOwner2"};

	private static final long _REFRESH_TIME = 2 * Time.SECOND;

	private static Bundle _bundle;
	private static Connection _connection;

	private class LockInfo {

		public LockInfo(Timestamp expirationDate, long lockId, String owner) {
			_expirationDate = expirationDate;
			_lockId = lockId;
			_owner = owner;
		}

		public Timestamp getExpirationDate() {
			return _expirationDate;
		}

		public long getLockId() {
			return _lockId;
		}

		public String getOwner() {
			return _owner;
		}

		private final Timestamp _expirationDate;
		private final long _lockId;
		private final String _owner;

	}

}