/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.multi.factor.authentication.email.otp.service.persistence.impl;

import com.liferay.multi.factor.authentication.email.otp.exception.NoSuchEntryException;
import com.liferay.multi.factor.authentication.email.otp.model.MFAEmailOTPEntry;
import com.liferay.multi.factor.authentication.email.otp.model.MFAEmailOTPEntryTable;
import com.liferay.multi.factor.authentication.email.otp.model.impl.MFAEmailOTPEntryImpl;
import com.liferay.multi.factor.authentication.email.otp.model.impl.MFAEmailOTPEntryModelImpl;
import com.liferay.multi.factor.authentication.email.otp.service.persistence.MFAEmailOTPEntryPersistence;
import com.liferay.multi.factor.authentication.email.otp.service.persistence.impl.constants.MFAEmailOTPPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the mfa email otp entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Arthur Chan
 * @generated
 */
@Component(service = {MFAEmailOTPEntryPersistence.class, BasePersistence.class})
public class MFAEmailOTPEntryPersistenceImpl
	extends BasePersistenceImpl<MFAEmailOTPEntry>
	implements MFAEmailOTPEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>MFAEmailOTPEntryUtil</code> to access the mfa email otp entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		MFAEmailOTPEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByUserId;
	private FinderPath _finderPathCountByUserId;

	/**
	 * Returns the mfa email otp entry where userId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @return the matching mfa email otp entry
	 * @throws NoSuchEntryException if a matching mfa email otp entry could not be found
	 */
	@Override
	public MFAEmailOTPEntry findByUserId(long userId)
		throws NoSuchEntryException {

		MFAEmailOTPEntry mfaEmailOTPEntry = fetchByUserId(userId);

		if (mfaEmailOTPEntry == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("userId=");
			sb.append(userId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryException(sb.toString());
		}

		return mfaEmailOTPEntry;
	}

	/**
	 * Returns the mfa email otp entry where userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @return the matching mfa email otp entry, or <code>null</code> if a matching mfa email otp entry could not be found
	 */
	@Override
	public MFAEmailOTPEntry fetchByUserId(long userId) {
		return fetchByUserId(userId, true);
	}

	/**
	 * Returns the mfa email otp entry where userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching mfa email otp entry, or <code>null</code> if a matching mfa email otp entry could not be found
	 */
	@Override
	public MFAEmailOTPEntry fetchByUserId(long userId, boolean useFinderCache) {
		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {userId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByUserId, finderArgs);
		}

		if (result instanceof MFAEmailOTPEntry) {
			MFAEmailOTPEntry mfaEmailOTPEntry = (MFAEmailOTPEntry)result;

			if (userId != mfaEmailOTPEntry.getUserId()) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_MFAEMAILOTPENTRY_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				List<MFAEmailOTPEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUserId, finderArgs, list);
					}
				}
				else {
					MFAEmailOTPEntry mfaEmailOTPEntry = list.get(0);

					result = mfaEmailOTPEntry;

					cacheResult(mfaEmailOTPEntry);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (MFAEmailOTPEntry)result;
		}
	}

	/**
	 * Removes the mfa email otp entry where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @return the mfa email otp entry that was removed
	 */
	@Override
	public MFAEmailOTPEntry removeByUserId(long userId)
		throws NoSuchEntryException {

		MFAEmailOTPEntry mfaEmailOTPEntry = findByUserId(userId);

		return remove(mfaEmailOTPEntry);
	}

	/**
	 * Returns the number of mfa email otp entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching mfa email otp entries
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = _finderPathCountByUserId;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_MFAEMAILOTPENTRY_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_USERID_USERID_2 =
		"mfaEmailOTPEntry.userId = ?";

	public MFAEmailOTPEntryPersistenceImpl() {
		setModelClass(MFAEmailOTPEntry.class);

		setModelImplClass(MFAEmailOTPEntryImpl.class);
		setModelPKClass(long.class);

		setTable(MFAEmailOTPEntryTable.INSTANCE);
	}

	/**
	 * Caches the mfa email otp entry in the entity cache if it is enabled.
	 *
	 * @param mfaEmailOTPEntry the mfa email otp entry
	 */
	@Override
	public void cacheResult(MFAEmailOTPEntry mfaEmailOTPEntry) {
		entityCache.putResult(
			MFAEmailOTPEntryImpl.class, mfaEmailOTPEntry.getPrimaryKey(),
			mfaEmailOTPEntry);

		finderCache.putResult(
			_finderPathFetchByUserId,
			new Object[] {mfaEmailOTPEntry.getUserId()}, mfaEmailOTPEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the mfa email otp entries in the entity cache if it is enabled.
	 *
	 * @param mfaEmailOTPEntries the mfa email otp entries
	 */
	@Override
	public void cacheResult(List<MFAEmailOTPEntry> mfaEmailOTPEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (mfaEmailOTPEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (MFAEmailOTPEntry mfaEmailOTPEntry : mfaEmailOTPEntries) {
			if (entityCache.getResult(
					MFAEmailOTPEntryImpl.class,
					mfaEmailOTPEntry.getPrimaryKey()) == null) {

				cacheResult(mfaEmailOTPEntry);
			}
		}
	}

	/**
	 * Clears the cache for all mfa email otp entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(MFAEmailOTPEntryImpl.class);

		finderCache.clearCache(MFAEmailOTPEntryImpl.class);
	}

	/**
	 * Clears the cache for the mfa email otp entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MFAEmailOTPEntry mfaEmailOTPEntry) {
		entityCache.removeResult(MFAEmailOTPEntryImpl.class, mfaEmailOTPEntry);
	}

	@Override
	public void clearCache(List<MFAEmailOTPEntry> mfaEmailOTPEntries) {
		for (MFAEmailOTPEntry mfaEmailOTPEntry : mfaEmailOTPEntries) {
			entityCache.removeResult(
				MFAEmailOTPEntryImpl.class, mfaEmailOTPEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(MFAEmailOTPEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(MFAEmailOTPEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		MFAEmailOTPEntryModelImpl mfaEmailOTPEntryModelImpl) {

		Object[] args = new Object[] {mfaEmailOTPEntryModelImpl.getUserId()};

		finderCache.putResult(_finderPathCountByUserId, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUserId, args, mfaEmailOTPEntryModelImpl);
	}

	/**
	 * Creates a new mfa email otp entry with the primary key. Does not add the mfa email otp entry to the database.
	 *
	 * @param mfaEmailOTPEntryId the primary key for the new mfa email otp entry
	 * @return the new mfa email otp entry
	 */
	@Override
	public MFAEmailOTPEntry create(long mfaEmailOTPEntryId) {
		MFAEmailOTPEntry mfaEmailOTPEntry = new MFAEmailOTPEntryImpl();

		mfaEmailOTPEntry.setNew(true);
		mfaEmailOTPEntry.setPrimaryKey(mfaEmailOTPEntryId);

		mfaEmailOTPEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return mfaEmailOTPEntry;
	}

	/**
	 * Removes the mfa email otp entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mfaEmailOTPEntryId the primary key of the mfa email otp entry
	 * @return the mfa email otp entry that was removed
	 * @throws NoSuchEntryException if a mfa email otp entry with the primary key could not be found
	 */
	@Override
	public MFAEmailOTPEntry remove(long mfaEmailOTPEntryId)
		throws NoSuchEntryException {

		return remove((Serializable)mfaEmailOTPEntryId);
	}

	/**
	 * Removes the mfa email otp entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the mfa email otp entry
	 * @return the mfa email otp entry that was removed
	 * @throws NoSuchEntryException if a mfa email otp entry with the primary key could not be found
	 */
	@Override
	public MFAEmailOTPEntry remove(Serializable primaryKey)
		throws NoSuchEntryException {

		Session session = null;

		try {
			session = openSession();

			MFAEmailOTPEntry mfaEmailOTPEntry = (MFAEmailOTPEntry)session.get(
				MFAEmailOTPEntryImpl.class, primaryKey);

			if (mfaEmailOTPEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(mfaEmailOTPEntry);
		}
		catch (NoSuchEntryException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected MFAEmailOTPEntry removeImpl(MFAEmailOTPEntry mfaEmailOTPEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(mfaEmailOTPEntry)) {
				mfaEmailOTPEntry = (MFAEmailOTPEntry)session.get(
					MFAEmailOTPEntryImpl.class,
					mfaEmailOTPEntry.getPrimaryKeyObj());
			}

			if (mfaEmailOTPEntry != null) {
				session.delete(mfaEmailOTPEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (mfaEmailOTPEntry != null) {
			clearCache(mfaEmailOTPEntry);
		}

		return mfaEmailOTPEntry;
	}

	@Override
	public MFAEmailOTPEntry updateImpl(MFAEmailOTPEntry mfaEmailOTPEntry) {
		boolean isNew = mfaEmailOTPEntry.isNew();

		if (!(mfaEmailOTPEntry instanceof MFAEmailOTPEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(mfaEmailOTPEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					mfaEmailOTPEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in mfaEmailOTPEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom MFAEmailOTPEntry implementation " +
					mfaEmailOTPEntry.getClass());
		}

		MFAEmailOTPEntryModelImpl mfaEmailOTPEntryModelImpl =
			(MFAEmailOTPEntryModelImpl)mfaEmailOTPEntry;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (mfaEmailOTPEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				mfaEmailOTPEntry.setCreateDate(date);
			}
			else {
				mfaEmailOTPEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!mfaEmailOTPEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				mfaEmailOTPEntry.setModifiedDate(date);
			}
			else {
				mfaEmailOTPEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(mfaEmailOTPEntry);
			}
			else {
				mfaEmailOTPEntry = (MFAEmailOTPEntry)session.merge(
					mfaEmailOTPEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			MFAEmailOTPEntryImpl.class, mfaEmailOTPEntryModelImpl, false, true);

		cacheUniqueFindersCache(mfaEmailOTPEntryModelImpl);

		if (isNew) {
			mfaEmailOTPEntry.setNew(false);
		}

		mfaEmailOTPEntry.resetOriginalValues();

		return mfaEmailOTPEntry;
	}

	/**
	 * Returns the mfa email otp entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the mfa email otp entry
	 * @return the mfa email otp entry
	 * @throws NoSuchEntryException if a mfa email otp entry with the primary key could not be found
	 */
	@Override
	public MFAEmailOTPEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {

		MFAEmailOTPEntry mfaEmailOTPEntry = fetchByPrimaryKey(primaryKey);

		if (mfaEmailOTPEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return mfaEmailOTPEntry;
	}

	/**
	 * Returns the mfa email otp entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param mfaEmailOTPEntryId the primary key of the mfa email otp entry
	 * @return the mfa email otp entry
	 * @throws NoSuchEntryException if a mfa email otp entry with the primary key could not be found
	 */
	@Override
	public MFAEmailOTPEntry findByPrimaryKey(long mfaEmailOTPEntryId)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)mfaEmailOTPEntryId);
	}

	/**
	 * Returns the mfa email otp entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param mfaEmailOTPEntryId the primary key of the mfa email otp entry
	 * @return the mfa email otp entry, or <code>null</code> if a mfa email otp entry with the primary key could not be found
	 */
	@Override
	public MFAEmailOTPEntry fetchByPrimaryKey(long mfaEmailOTPEntryId) {
		return fetchByPrimaryKey((Serializable)mfaEmailOTPEntryId);
	}

	/**
	 * Returns all the mfa email otp entries.
	 *
	 * @return the mfa email otp entries
	 */
	@Override
	public List<MFAEmailOTPEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the mfa email otp entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MFAEmailOTPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mfa email otp entries
	 * @param end the upper bound of the range of mfa email otp entries (not inclusive)
	 * @return the range of mfa email otp entries
	 */
	@Override
	public List<MFAEmailOTPEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the mfa email otp entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MFAEmailOTPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mfa email otp entries
	 * @param end the upper bound of the range of mfa email otp entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of mfa email otp entries
	 */
	@Override
	public List<MFAEmailOTPEntry> findAll(
		int start, int end,
		OrderByComparator<MFAEmailOTPEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the mfa email otp entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MFAEmailOTPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mfa email otp entries
	 * @param end the upper bound of the range of mfa email otp entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of mfa email otp entries
	 */
	@Override
	public List<MFAEmailOTPEntry> findAll(
		int start, int end,
		OrderByComparator<MFAEmailOTPEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<MFAEmailOTPEntry> list = null;

		if (useFinderCache) {
			list = (List<MFAEmailOTPEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_MFAEMAILOTPENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_MFAEMAILOTPENTRY;

				sql = sql.concat(MFAEmailOTPEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<MFAEmailOTPEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the mfa email otp entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (MFAEmailOTPEntry mfaEmailOTPEntry : findAll()) {
			remove(mfaEmailOTPEntry);
		}
	}

	/**
	 * Returns the number of mfa email otp entries.
	 *
	 * @return the number of mfa email otp entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_MFAEMAILOTPENTRY);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "mfaEmailOTPEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_MFAEMAILOTPENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return MFAEmailOTPEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the mfa email otp entry persistence.
	 */
	@Activate
	public void activate() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathFetchByUserId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUserId",
			new String[] {Long.class.getName()}, new String[] {"userId"}, true);

		_finderPathCountByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] {Long.class.getName()}, new String[] {"userId"},
			false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(MFAEmailOTPEntryImpl.class.getName());
	}

	@Override
	@Reference(
		target = MFAEmailOTPPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = MFAEmailOTPPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = MFAEmailOTPPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_MFAEMAILOTPENTRY =
		"SELECT mfaEmailOTPEntry FROM MFAEmailOTPEntry mfaEmailOTPEntry";

	private static final String _SQL_SELECT_MFAEMAILOTPENTRY_WHERE =
		"SELECT mfaEmailOTPEntry FROM MFAEmailOTPEntry mfaEmailOTPEntry WHERE ";

	private static final String _SQL_COUNT_MFAEMAILOTPENTRY =
		"SELECT COUNT(mfaEmailOTPEntry) FROM MFAEmailOTPEntry mfaEmailOTPEntry";

	private static final String _SQL_COUNT_MFAEMAILOTPENTRY_WHERE =
		"SELECT COUNT(mfaEmailOTPEntry) FROM MFAEmailOTPEntry mfaEmailOTPEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "mfaEmailOTPEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No MFAEmailOTPEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No MFAEmailOTPEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		MFAEmailOTPEntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private MFAEmailOTPEntryModelArgumentsResolver
		_mfaEmailOTPEntryModelArgumentsResolver;

}