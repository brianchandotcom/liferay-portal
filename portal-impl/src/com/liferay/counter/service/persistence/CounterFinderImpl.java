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

package com.liferay.counter.service.persistence;

import com.liferay.counter.model.Counter;
import com.liferay.counter.model.CounterHolder;
import com.liferay.counter.model.CounterRegister;
import com.liferay.counter.model.impl.CounterImpl;
import com.liferay.portal.kernel.cache.CacheRegistryItem;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.concurrent.CompeteLatch;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.LockMode;
import com.liferay.portal.kernel.dao.orm.ObjectNotFoundException;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.model.Dummy;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Michael Young
 * @author Shuyang Zhou
 * @author Edward Han
 */
public class CounterFinderImpl
	extends BasePersistenceImpl<Dummy>
	implements CacheRegistryItem, CounterFinder {

	@Override
	public void afterPropertiesSet() {
		CacheRegistryUtil.register(this);
	}

	@Override
	public List<String> getNames() throws SystemException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = getConnection();

			preparedStatement = connection.prepareStatement(_SQL_SELECT_NAMES);

			resultSet = preparedStatement.executeQuery();

			List<String> list = new ArrayList<String>();

			while (resultSet.next()) {
				list.add(resultSet.getString(1));
			}

			return list;
		}
		catch (SQLException sqle) {
			throw processException(sqle);
		}
		finally {
			DataAccess.cleanUp(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public String getRegistryName() {
		return CounterFinderImpl.class.getName();
	}

	@Override
	public long increment() throws SystemException {
		return increment(_NAME);
	}

	@Override
	public long increment(String name) throws SystemException {
		return increment(name, _MINIMUM_INCREMENT_SIZE);
	}

	@Override
	public long increment(String name, int size) throws SystemException {
		if (size < _MINIMUM_INCREMENT_SIZE) {
			size = _MINIMUM_INCREMENT_SIZE;
		}

		CounterRegister counterRegister = getCounterRegister(name);

		return _competeIncrement(counterRegister, size);
	}

	@Override
	public void invalidate() {
		_counterRegisterMap.clear();
	}

	@Override
	public void rename(String oldName, String newName) throws SystemException {
		CounterRegister counterRegister = getCounterRegister(oldName);

		synchronized (counterRegister) {
			if (_counterRegisterMap.containsKey(newName)) {
				throw new SystemException(
					"Cannot rename " + oldName + " to " + newName);
			}

			Connection connection = null;
			PreparedStatement preparedStatement = null;

			try {
				connection = getConnection();

				preparedStatement = connection.prepareStatement(
					_SQL_UPDATE_NAME_BY_NAME);

				preparedStatement.setString(1, newName);
				preparedStatement.setString(2, oldName);

				preparedStatement.executeUpdate();
			}
			catch (ObjectNotFoundException onfe) {
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				DataAccess.cleanUp(connection, preparedStatement);
			}

			counterRegister.setName(newName);

			_counterRegisterMap.put(newName, counterRegister);
			_counterRegisterMap.remove(oldName);
		}
	}

	@Override
	public void reset(String name) throws SystemException {
		CounterRegister counterRegister = getCounterRegister(name);

		synchronized (counterRegister) {
			Session session = null;

			try {
				session = openSession();

				Counter counter = (Counter)session.get(CounterImpl.class, name);

				session.delete(counter);

				session.flush();
			}
			catch (ObjectNotFoundException onfe) {
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				closeSession(session);
			}

			_counterRegisterMap.remove(name);
		}
	}

	@Override
	public void reset(String name, long size) throws SystemException {
		CounterRegister counterRegister = createCounterRegister(name, size);

		_counterRegisterMap.put(name, counterRegister);
	}

	protected CounterRegister createCounterRegister(String name)
		throws SystemException {

		return createCounterRegister(name, -1);
	}

	protected CounterRegister createCounterRegister(String name, long size)
		throws SystemException {

		long rangeMin = -1;
		int rangeSize = getRangeSize(name);

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = getConnection();

			preparedStatement = connection.prepareStatement(
				_SQL_SELECT_ID_BY_NAME);

			preparedStatement.setString(1, name);

			resultSet = preparedStatement.executeQuery();

			if (!resultSet.next()) {
				rangeMin = _DEFAULT_CURRENT_ID;

				if (size > rangeMin) {
					rangeMin = size;
				}

				resultSet.close();
				preparedStatement.close();

				preparedStatement = connection.prepareStatement(_SQL_INSERT);

				preparedStatement.setString(1, name);
				preparedStatement.setLong(2, rangeMin);

				preparedStatement.executeUpdate();
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			DataAccess.cleanUp(connection, preparedStatement, resultSet);
		}

		CounterHolder counterHolder = _obtainIncrement(name, rangeSize, size);

		return new CounterRegister(name, counterHolder, rangeSize);
	}

	protected Connection getConnection() throws SQLException {
		Connection connection = getDataSource().getConnection();

		return connection;
	}

	protected CounterRegister getCounterRegister(String name)
		throws SystemException {

		CounterRegister counterRegister = _counterRegisterMap.get(name);

		if (counterRegister != null) {
			return counterRegister;
		}
		else {
			synchronized (_counterRegisterMap) {

				// Double check

				counterRegister = _counterRegisterMap.get(name);

				if (counterRegister == null) {
					counterRegister = createCounterRegister(name);

					_counterRegisterMap.put(name, counterRegister);
				}

				return counterRegister;
			}
		}
	}

	protected int getRangeSize(String name) {
		if (name.equals(_NAME)) {
			return PropsValues.COUNTER_INCREMENT;
		}

		String incrementType = null;

		int pos = name.indexOf(CharPool.POUND);

		if (pos != -1) {
			incrementType = name.substring(0, pos);
		}
		else {
			incrementType = name;
		}

		Integer rangeSize = _rangeSizeMap.get(incrementType);

		if (rangeSize == null) {
			rangeSize = GetterUtil.getInteger(
				PropsUtil.get(
					PropsKeys.COUNTER_INCREMENT_PREFIX + incrementType),
				PropsValues.COUNTER_INCREMENT);

			_rangeSizeMap.put(incrementType, rangeSize);
		}

		return rangeSize.intValue();
	}

	private long _competeIncrement(CounterRegister counterRegister, int size)
		throws SystemException {

		CounterHolder counterHolder = counterRegister.getCounterHolder();

		// Try to use the fast path

		long newValue = counterHolder.addAndGet(size);

		if (newValue <= counterHolder.getRangeMax()) {
			return newValue;
		}

		// Use the slow path

		CompeteLatch completeLatch = counterRegister.getCompeteLatch();

		if (!completeLatch.compete()) {

			// Loser thread has to wait for the winner thread to finish its job

			try {
				completeLatch.await();
			}
			catch (InterruptedException ie) {
				throw processException(ie);
			}

			// Compete again

			return _competeIncrement(counterRegister, size);
		}

		// Winner thread

		try {

			// Double check

			counterHolder = counterRegister.getCounterHolder();
			newValue = counterHolder.addAndGet(size);

			if (newValue > counterHolder.getRangeMax()) {
				CounterHolder newCounterHolder = _obtainIncrement(
					counterRegister.getName(), counterRegister.getRangeSize(),
					0);

				newValue = newCounterHolder.addAndGet(size);

				counterRegister.setCounterHolder(newCounterHolder);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {

			// Winner thread opens the latch so that loser threads can continue

			completeLatch.done();
		}

		return newValue;
	}

	private CounterHolder _obtainIncrement(
			String counterName, long range, long size)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			Counter counter = (Counter)session.get(
				CounterImpl.class, counterName, LockMode.UPGRADE);

			long newValue = counter.getCurrentId();

			if (size > newValue) {
				newValue = size;
			}

			long rangeMax = newValue + range;

			counter.setCurrentId(rangeMax);

			CounterHolder counterHolder = new CounterHolder(newValue, rangeMax);

			session.saveOrUpdate(counter);

			session.flush();

			return counterHolder;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final int _DEFAULT_CURRENT_ID = 0;

	private static final int _MINIMUM_INCREMENT_SIZE = 1;

	private static final String _NAME = Counter.class.getName();

	private static final String _SQL_INSERT =
		"insert into Counter(name, currentId) values (?, ?)";

	private static final String _SQL_SELECT_ID_BY_NAME =
		"select currentId from Counter where name = ?";

	private static final String _SQL_SELECT_NAMES =
		"select name from Counter order by name asc";

	private static final String _SQL_UPDATE_NAME_BY_NAME =
		"update Counter set name = ? where name = ?";

	private Map<String, CounterRegister> _counterRegisterMap =
		new ConcurrentHashMap<String, CounterRegister>();
	private Map<String, Integer> _rangeSizeMap =
		new ConcurrentHashMap<String, Integer>();

}