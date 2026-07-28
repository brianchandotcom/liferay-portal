/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence.impl;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchTimestampTypeEntryException;
import com.liferay.portal.tools.service.builder.test.model.TimestampTypeEntry;
import com.liferay.portal.tools.service.builder.test.model.TimestampTypeEntryTable;
import com.liferay.portal.tools.service.builder.test.model.impl.TimestampTypeEntryImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.TimestampTypeEntryModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.TimestampTypeEntryPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.TimestampTypeEntryUtil;

import java.io.Serializable;

import java.util.Map;

/**
 * The persistence implementation for the timestamp type entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class TimestampTypeEntryPersistenceImpl
	extends BasePersistenceImpl
		<TimestampTypeEntry, NoSuchTimestampTypeEntryException>
	implements TimestampTypeEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>TimestampTypeEntryUtil</code> to access the timestamp type entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		TimestampTypeEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	public TimestampTypeEntryPersistenceImpl() {
		setModelClass(TimestampTypeEntry.class);

		setModelImplClass(TimestampTypeEntryImpl.class);
		setModelPKClass(long.class);

		setTable(TimestampTypeEntryTable.INSTANCE);
	}

	/**
	 * Creates a new timestamp type entry with the primary key. Does not add the timestamp type entry to the database.
	 *
	 * @param timestampTypeEntryId the primary key for the new timestamp type entry
	 * @return the new timestamp type entry
	 */
	@Override
	public TimestampTypeEntry create(long timestampTypeEntryId) {
		TimestampTypeEntry timestampTypeEntry = new TimestampTypeEntryImpl();

		timestampTypeEntry.setNew(true);
		timestampTypeEntry.setPrimaryKey(timestampTypeEntryId);

		return timestampTypeEntry;
	}

	/**
	 * Removes the timestamp type entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param timestampTypeEntryId the primary key of the timestamp type entry
	 * @return the timestamp type entry that was removed
	 * @throws NoSuchTimestampTypeEntryException if a timestamp type entry with the primary key could not be found
	 */
	@Override
	public TimestampTypeEntry remove(long timestampTypeEntryId)
		throws NoSuchTimestampTypeEntryException {

		return remove((Serializable)timestampTypeEntryId);
	}

	@Override
	protected TimestampTypeEntry removeImpl(
		TimestampTypeEntry timestampTypeEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(timestampTypeEntry)) {
				timestampTypeEntry = (TimestampTypeEntry)session.get(
					TimestampTypeEntryImpl.class,
					timestampTypeEntry.getPrimaryKeyObj());
			}

			if (timestampTypeEntry != null) {
				session.delete(timestampTypeEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (timestampTypeEntry != null) {
			clearCache(timestampTypeEntry);
		}

		return timestampTypeEntry;
	}

	@Override
	public TimestampTypeEntry updateImpl(
		TimestampTypeEntry timestampTypeEntry) {

		boolean isNew = timestampTypeEntry.isNew();

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(timestampTypeEntry);
			}
			else {
				timestampTypeEntry = (TimestampTypeEntry)session.merge(
					timestampTypeEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		cacheUniqueFindersResult(timestampTypeEntry, false);

		if (isNew) {
			timestampTypeEntry.setNew(false);
		}

		timestampTypeEntry.resetOriginalValues();

		return timestampTypeEntry;
	}

	/**
	 * Returns the timestamp type entry with the primary key or throws a <code>NoSuchTimestampTypeEntryException</code> if it could not be found.
	 *
	 * @param timestampTypeEntryId the primary key of the timestamp type entry
	 * @return the timestamp type entry
	 * @throws NoSuchTimestampTypeEntryException if a timestamp type entry with the primary key could not be found
	 */
	@Override
	public TimestampTypeEntry findByPrimaryKey(long timestampTypeEntryId)
		throws NoSuchTimestampTypeEntryException {

		return findByPrimaryKey((Serializable)timestampTypeEntryId);
	}

	/**
	 * Returns the timestamp type entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param timestampTypeEntryId the primary key of the timestamp type entry
	 * @return the timestamp type entry, or <code>null</code> if a timestamp type entry with the primary key could not be found
	 */
	@Override
	public TimestampTypeEntry fetchByPrimaryKey(long timestampTypeEntryId) {
		return fetchByPrimaryKey((Serializable)timestampTypeEntryId);
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "timestampTypeEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_TIMESTAMPTYPEENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return TimestampTypeEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the timestamp type entry persistence.
	 */
	public void afterPropertiesSet() {
		TimestampTypeEntryUtil.setPersistence(this);
	}

	public void destroy() {
		TimestampTypeEntryUtil.setPersistence(null);

		entityCache.removeCache(TimestampTypeEntryImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_TIMESTAMPTYPEENTRY =
		"SELECT timestampTypeEntry FROM TimestampTypeEntry timestampTypeEntry";

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1488959109