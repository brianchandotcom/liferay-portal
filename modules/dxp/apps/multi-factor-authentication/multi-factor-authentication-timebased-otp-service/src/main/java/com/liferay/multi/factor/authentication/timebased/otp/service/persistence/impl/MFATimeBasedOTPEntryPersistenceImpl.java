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

package com.liferay.multi.factor.authentication.timebased.otp.service.persistence.impl;

import com.liferay.multi.factor.authentication.timebased.otp.exception.NoSuchEntryException;
import com.liferay.multi.factor.authentication.timebased.otp.model.MFATimeBasedOTPEntry;
import com.liferay.multi.factor.authentication.timebased.otp.model.MFATimeBasedOTPEntryTable;
import com.liferay.multi.factor.authentication.timebased.otp.model.impl.MFATimeBasedOTPEntryImpl;
import com.liferay.multi.factor.authentication.timebased.otp.model.impl.MFATimeBasedOTPEntryModelImpl;
import com.liferay.multi.factor.authentication.timebased.otp.service.persistence.MFATimeBasedOTPEntryPersistence;
import com.liferay.multi.factor.authentication.timebased.otp.service.persistence.impl.constants.MFATimeBasedOTPPersistenceConstants;
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
 * The persistence implementation for the mfa time based otp entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Arthur Chan
 * @generated
 */
@Component(
	service = {MFATimeBasedOTPEntryPersistence.class, BasePersistence.class}
)
public class MFATimeBasedOTPEntryPersistenceImpl
	extends BasePersistenceImpl<MFATimeBasedOTPEntry>
	implements MFATimeBasedOTPEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>MFATimeBasedOTPEntryUtil</code> to access the mfa time based otp entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		MFATimeBasedOTPEntryImpl.class.getName();

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
	 * Returns the mfa time based otp entry where userId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @return the matching mfa time based otp entry
	 * @throws NoSuchEntryException if a matching mfa time based otp entry could not be found
	 */
	@Override
	public MFATimeBasedOTPEntry findByUserId(long userId)
		throws NoSuchEntryException {

		MFATimeBasedOTPEntry mfaTimeBasedOTPEntry = fetchByUserId(userId);

		if (mfaTimeBasedOTPEntry == null) {
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

		return mfaTimeBasedOTPEntry;
	}

	/**
	 * Returns the mfa time based otp entry where userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @return the matching mfa time based otp entry, or <code>null</code> if a matching mfa time based otp entry could not be found
	 */
	@Override
	public MFATimeBasedOTPEntry fetchByUserId(long userId) {
		return fetchByUserId(userId, true);
	}

	/**
	 * Returns the mfa time based otp entry where userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching mfa time based otp entry, or <code>null</code> if a matching mfa time based otp entry could not be found
	 */
	@Override
	public MFATimeBasedOTPEntry fetchByUserId(
		long userId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {userId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByUserId, finderArgs);
		}

		if (result instanceof MFATimeBasedOTPEntry) {
			MFATimeBasedOTPEntry mfaTimeBasedOTPEntry =
				(MFATimeBasedOTPEntry)result;

			if (userId != mfaTimeBasedOTPEntry.getUserId()) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_MFATIMEBASEDOTPENTRY_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				List<MFATimeBasedOTPEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUserId, finderArgs, list);
					}
				}
				else {
					MFATimeBasedOTPEntry mfaTimeBasedOTPEntry = list.get(0);

					result = mfaTimeBasedOTPEntry;

					cacheResult(mfaTimeBasedOTPEntry);
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
			return (MFATimeBasedOTPEntry)result;
		}
	}

	/**
	 * Removes the mfa time based otp entry where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @return the mfa time based otp entry that was removed
	 */
	@Override
	public MFATimeBasedOTPEntry removeByUserId(long userId)
		throws NoSuchEntryException {

		MFATimeBasedOTPEntry mfaTimeBasedOTPEntry = findByUserId(userId);

		return remove(mfaTimeBasedOTPEntry);
	}

	/**
	 * Returns the number of mfa time based otp entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching mfa time based otp entries
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = _finderPathCountByUserId;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_MFATIMEBASEDOTPENTRY_WHERE);

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
		"mfaTimeBasedOTPEntry.userId = ?";

	public MFATimeBasedOTPEntryPersistenceImpl() {
		setModelClass(MFATimeBasedOTPEntry.class);

		setModelImplClass(MFATimeBasedOTPEntryImpl.class);
		setModelPKClass(long.class);

		setTable(MFATimeBasedOTPEntryTable.INSTANCE);
	}

	/**
	 * Caches the mfa time based otp entry in the entity cache if it is enabled.
	 *
	 * @param mfaTimeBasedOTPEntry the mfa time based otp entry
	 */
	@Override
	public void cacheResult(MFATimeBasedOTPEntry mfaTimeBasedOTPEntry) {
		entityCache.putResult(
			MFATimeBasedOTPEntryImpl.class,
			mfaTimeBasedOTPEntry.getPrimaryKey(), mfaTimeBasedOTPEntry);

		finderCache.putResult(
			_finderPathFetchByUserId,
			new Object[] {mfaTimeBasedOTPEntry.getUserId()},
			mfaTimeBasedOTPEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the mfa time based otp entries in the entity cache if it is enabled.
	 *
	 * @param mfaTimeBasedOTPEntries the mfa time based otp entries
	 */
	@Override
	public void cacheResult(List<MFATimeBasedOTPEntry> mfaTimeBasedOTPEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (mfaTimeBasedOTPEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (MFATimeBasedOTPEntry mfaTimeBasedOTPEntry :
				mfaTimeBasedOTPEntries) {

			if (entityCache.getResult(
					MFATimeBasedOTPEntryImpl.class,
					mfaTimeBasedOTPEntry.getPrimaryKey()) == null) {

				cacheResult(mfaTimeBasedOTPEntry);
			}
		}
	}

	/**
	 * Clears the cache for all mfa time based otp entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(MFATimeBasedOTPEntryImpl.class);

		finderCache.clearCache(MFATimeBasedOTPEntryImpl.class);
	}

	/**
	 * Clears the cache for the mfa time based otp entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MFATimeBasedOTPEntry mfaTimeBasedOTPEntry) {
		entityCache.removeResult(
			MFATimeBasedOTPEntryImpl.class, mfaTimeBasedOTPEntry);
	}

	@Override
	public void clearCache(List<MFATimeBasedOTPEntry> mfaTimeBasedOTPEntries) {
		for (MFATimeBasedOTPEntry mfaTimeBasedOTPEntry :
				mfaTimeBasedOTPEntries) {

			entityCache.removeResult(
				MFATimeBasedOTPEntryImpl.class, mfaTimeBasedOTPEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(MFATimeBasedOTPEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				MFATimeBasedOTPEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		MFATimeBasedOTPEntryModelImpl mfaTimeBasedOTPEntryModelImpl) {

		Object[] args = new Object[] {
			mfaTimeBasedOTPEntryModelImpl.getUserId()
		};

		finderCache.putResult(_finderPathCountByUserId, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUserId, args, mfaTimeBasedOTPEntryModelImpl);
	}

	/**
	 * Creates a new mfa time based otp entry with the primary key. Does not add the mfa time based otp entry to the database.
	 *
	 * @param mfaTimeBasedOTPEntryId the primary key for the new mfa time based otp entry
	 * @return the new mfa time based otp entry
	 */
	@Override
	public MFATimeBasedOTPEntry create(long mfaTimeBasedOTPEntryId) {
		MFATimeBasedOTPEntry mfaTimeBasedOTPEntry =
			new MFATimeBasedOTPEntryImpl();

		mfaTimeBasedOTPEntry.setNew(true);
		mfaTimeBasedOTPEntry.setPrimaryKey(mfaTimeBasedOTPEntryId);

		mfaTimeBasedOTPEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return mfaTimeBasedOTPEntry;
	}

	/**
	 * Removes the mfa time based otp entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mfaTimeBasedOTPEntryId the primary key of the mfa time based otp entry
	 * @return the mfa time based otp entry that was removed
	 * @throws NoSuchEntryException if a mfa time based otp entry with the primary key could not be found
	 */
	@Override
	public MFATimeBasedOTPEntry remove(long mfaTimeBasedOTPEntryId)
		throws NoSuchEntryException {

		return remove((Serializable)mfaTimeBasedOTPEntryId);
	}

	/**
	 * Removes the mfa time based otp entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the mfa time based otp entry
	 * @return the mfa time based otp entry that was removed
	 * @throws NoSuchEntryException if a mfa time based otp entry with the primary key could not be found
	 */
	@Override
	public MFATimeBasedOTPEntry remove(Serializable primaryKey)
		throws NoSuchEntryException {

		Session session = null;

		try {
			session = openSession();

			MFATimeBasedOTPEntry mfaTimeBasedOTPEntry =
				(MFATimeBasedOTPEntry)session.get(
					MFATimeBasedOTPEntryImpl.class, primaryKey);

			if (mfaTimeBasedOTPEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(mfaTimeBasedOTPEntry);
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
	protected MFATimeBasedOTPEntry removeImpl(
		MFATimeBasedOTPEntry mfaTimeBasedOTPEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(mfaTimeBasedOTPEntry)) {
				mfaTimeBasedOTPEntry = (MFATimeBasedOTPEntry)session.get(
					MFATimeBasedOTPEntryImpl.class,
					mfaTimeBasedOTPEntry.getPrimaryKeyObj());
			}

			if (mfaTimeBasedOTPEntry != null) {
				session.delete(mfaTimeBasedOTPEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (mfaTimeBasedOTPEntry != null) {
			clearCache(mfaTimeBasedOTPEntry);
		}

		return mfaTimeBasedOTPEntry;
	}

	@Override
	public MFATimeBasedOTPEntry updateImpl(
		MFATimeBasedOTPEntry mfaTimeBasedOTPEntry) {

		boolean isNew = mfaTimeBasedOTPEntry.isNew();

		if (!(mfaTimeBasedOTPEntry instanceof MFATimeBasedOTPEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(mfaTimeBasedOTPEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					mfaTimeBasedOTPEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in mfaTimeBasedOTPEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom MFATimeBasedOTPEntry implementation " +
					mfaTimeBasedOTPEntry.getClass());
		}

		MFATimeBasedOTPEntryModelImpl mfaTimeBasedOTPEntryModelImpl =
			(MFATimeBasedOTPEntryModelImpl)mfaTimeBasedOTPEntry;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (mfaTimeBasedOTPEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				mfaTimeBasedOTPEntry.setCreateDate(date);
			}
			else {
				mfaTimeBasedOTPEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!mfaTimeBasedOTPEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				mfaTimeBasedOTPEntry.setModifiedDate(date);
			}
			else {
				mfaTimeBasedOTPEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(mfaTimeBasedOTPEntry);
			}
			else {
				mfaTimeBasedOTPEntry = (MFATimeBasedOTPEntry)session.merge(
					mfaTimeBasedOTPEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			MFATimeBasedOTPEntryImpl.class, mfaTimeBasedOTPEntryModelImpl,
			false, true);

		cacheUniqueFindersCache(mfaTimeBasedOTPEntryModelImpl);

		if (isNew) {
			mfaTimeBasedOTPEntry.setNew(false);
		}

		mfaTimeBasedOTPEntry.resetOriginalValues();

		return mfaTimeBasedOTPEntry;
	}

	/**
	 * Returns the mfa time based otp entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the mfa time based otp entry
	 * @return the mfa time based otp entry
	 * @throws NoSuchEntryException if a mfa time based otp entry with the primary key could not be found
	 */
	@Override
	public MFATimeBasedOTPEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {

		MFATimeBasedOTPEntry mfaTimeBasedOTPEntry = fetchByPrimaryKey(
			primaryKey);

		if (mfaTimeBasedOTPEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return mfaTimeBasedOTPEntry;
	}

	/**
	 * Returns the mfa time based otp entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param mfaTimeBasedOTPEntryId the primary key of the mfa time based otp entry
	 * @return the mfa time based otp entry
	 * @throws NoSuchEntryException if a mfa time based otp entry with the primary key could not be found
	 */
	@Override
	public MFATimeBasedOTPEntry findByPrimaryKey(long mfaTimeBasedOTPEntryId)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)mfaTimeBasedOTPEntryId);
	}

	/**
	 * Returns the mfa time based otp entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param mfaTimeBasedOTPEntryId the primary key of the mfa time based otp entry
	 * @return the mfa time based otp entry, or <code>null</code> if a mfa time based otp entry with the primary key could not be found
	 */
	@Override
	public MFATimeBasedOTPEntry fetchByPrimaryKey(long mfaTimeBasedOTPEntryId) {
		return fetchByPrimaryKey((Serializable)mfaTimeBasedOTPEntryId);
	}

	/**
	 * Returns all the mfa time based otp entries.
	 *
	 * @return the mfa time based otp entries
	 */
	@Override
	public List<MFATimeBasedOTPEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the mfa time based otp entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MFATimeBasedOTPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mfa time based otp entries
	 * @param end the upper bound of the range of mfa time based otp entries (not inclusive)
	 * @return the range of mfa time based otp entries
	 */
	@Override
	public List<MFATimeBasedOTPEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the mfa time based otp entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MFATimeBasedOTPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mfa time based otp entries
	 * @param end the upper bound of the range of mfa time based otp entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of mfa time based otp entries
	 */
	@Override
	public List<MFATimeBasedOTPEntry> findAll(
		int start, int end,
		OrderByComparator<MFATimeBasedOTPEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the mfa time based otp entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MFATimeBasedOTPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mfa time based otp entries
	 * @param end the upper bound of the range of mfa time based otp entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of mfa time based otp entries
	 */
	@Override
	public List<MFATimeBasedOTPEntry> findAll(
		int start, int end,
		OrderByComparator<MFATimeBasedOTPEntry> orderByComparator,
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

		List<MFATimeBasedOTPEntry> list = null;

		if (useFinderCache) {
			list = (List<MFATimeBasedOTPEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_MFATIMEBASEDOTPENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_MFATIMEBASEDOTPENTRY;

				sql = sql.concat(MFATimeBasedOTPEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<MFATimeBasedOTPEntry>)QueryUtil.list(
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
	 * Removes all the mfa time based otp entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (MFATimeBasedOTPEntry mfaTimeBasedOTPEntry : findAll()) {
			remove(mfaTimeBasedOTPEntry);
		}
	}

	/**
	 * Returns the number of mfa time based otp entries.
	 *
	 * @return the number of mfa time based otp entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_MFATIMEBASEDOTPENTRY);

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
		return "mfaTimeBasedOTPEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_MFATIMEBASEDOTPENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return MFATimeBasedOTPEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the mfa time based otp entry persistence.
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
		entityCache.removeCache(MFATimeBasedOTPEntryImpl.class.getName());
	}

	@Override
	@Reference(
		target = MFATimeBasedOTPPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = MFATimeBasedOTPPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = MFATimeBasedOTPPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_MFATIMEBASEDOTPENTRY =
		"SELECT mfaTimeBasedOTPEntry FROM MFATimeBasedOTPEntry mfaTimeBasedOTPEntry";

	private static final String _SQL_SELECT_MFATIMEBASEDOTPENTRY_WHERE =
		"SELECT mfaTimeBasedOTPEntry FROM MFATimeBasedOTPEntry mfaTimeBasedOTPEntry WHERE ";

	private static final String _SQL_COUNT_MFATIMEBASEDOTPENTRY =
		"SELECT COUNT(mfaTimeBasedOTPEntry) FROM MFATimeBasedOTPEntry mfaTimeBasedOTPEntry";

	private static final String _SQL_COUNT_MFATIMEBASEDOTPENTRY_WHERE =
		"SELECT COUNT(mfaTimeBasedOTPEntry) FROM MFATimeBasedOTPEntry mfaTimeBasedOTPEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"mfaTimeBasedOTPEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No MFATimeBasedOTPEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No MFATimeBasedOTPEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		MFATimeBasedOTPEntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private MFATimeBasedOTPEntryModelArgumentsResolver
		_mfaTimeBasedOTPEntryModelArgumentsResolver;

}