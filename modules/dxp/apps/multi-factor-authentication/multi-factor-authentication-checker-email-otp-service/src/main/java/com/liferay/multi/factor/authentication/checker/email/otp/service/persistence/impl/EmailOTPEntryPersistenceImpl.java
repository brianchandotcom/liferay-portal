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

package com.liferay.multi.factor.authentication.checker.email.otp.service.persistence.impl;

import com.liferay.multi.factor.authentication.checker.email.otp.exception.NoSuchEmailOTPEntryException;
import com.liferay.multi.factor.authentication.checker.email.otp.model.EmailOTPEntry;
import com.liferay.multi.factor.authentication.checker.email.otp.model.impl.EmailOTPEntryImpl;
import com.liferay.multi.factor.authentication.checker.email.otp.model.impl.EmailOTPEntryModelImpl;
import com.liferay.multi.factor.authentication.checker.email.otp.service.persistence.EmailOTPEntryPersistence;
import com.liferay.multi.factor.authentication.checker.email.otp.service.persistence.impl.constants.MFAEmailOTPPersistenceConstants;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the email otp entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Arthur Chan
 * @generated
 */
@Component(service = EmailOTPEntryPersistence.class)
public class EmailOTPEntryPersistenceImpl
	extends BasePersistenceImpl<EmailOTPEntry>
	implements EmailOTPEntryPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>EmailOTPEntryUtil</code> to access the email otp entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		EmailOTPEntryImpl.class.getName();

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
	 * Returns the email otp entry where userId = &#63; or throws a <code>NoSuchEmailOTPEntryException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @return the matching email otp entry
	 * @throws NoSuchEmailOTPEntryException if a matching email otp entry could not be found
	 */
	@Override
	public EmailOTPEntry findByUserId(long userId)
		throws NoSuchEmailOTPEntryException {

		EmailOTPEntry emailOTPEntry = fetchByUserId(userId);

		if (emailOTPEntry == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchEmailOTPEntryException(msg.toString());
		}

		return emailOTPEntry;
	}

	/**
	 * Returns the email otp entry where userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @return the matching email otp entry, or <code>null</code> if a matching email otp entry could not be found
	 */
	@Override
	public EmailOTPEntry fetchByUserId(long userId) {
		return fetchByUserId(userId, true);
	}

	/**
	 * Returns the email otp entry where userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching email otp entry, or <code>null</code> if a matching email otp entry could not be found
	 */
	@Override
	public EmailOTPEntry fetchByUserId(long userId, boolean useFinderCache) {
		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {userId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByUserId, finderArgs, this);
		}

		if (result instanceof EmailOTPEntry) {
			EmailOTPEntry emailOTPEntry = (EmailOTPEntry)result;

			if (userId != emailOTPEntry.getUserId()) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_EMAILOTPENTRY_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				List<EmailOTPEntry> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUserId, finderArgs, list);
					}
				}
				else {
					EmailOTPEntry emailOTPEntry = list.get(0);

					result = emailOTPEntry;

					cacheResult(emailOTPEntry);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByUserId, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (EmailOTPEntry)result;
		}
	}

	/**
	 * Removes the email otp entry where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @return the email otp entry that was removed
	 */
	@Override
	public EmailOTPEntry removeByUserId(long userId)
		throws NoSuchEmailOTPEntryException {

		EmailOTPEntry emailOTPEntry = findByUserId(userId);

		return remove(emailOTPEntry);
	}

	/**
	 * Returns the number of email otp entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching email otp entries
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = _finderPathCountByUserId;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_EMAILOTPENTRY_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_USERID_USERID_2 =
		"emailOTPEntry.userId = ?";

	public EmailOTPEntryPersistenceImpl() {
		setModelClass(EmailOTPEntry.class);

		setModelImplClass(EmailOTPEntryImpl.class);
		setModelPKClass(long.class);
	}

	/**
	 * Caches the email otp entry in the entity cache if it is enabled.
	 *
	 * @param emailOTPEntry the email otp entry
	 */
	@Override
	public void cacheResult(EmailOTPEntry emailOTPEntry) {
		entityCache.putResult(
			entityCacheEnabled, EmailOTPEntryImpl.class,
			emailOTPEntry.getPrimaryKey(), emailOTPEntry);

		finderCache.putResult(
			_finderPathFetchByUserId, new Object[] {emailOTPEntry.getUserId()},
			emailOTPEntry);

		emailOTPEntry.resetOriginalValues();
	}

	/**
	 * Caches the email otp entries in the entity cache if it is enabled.
	 *
	 * @param emailOTPEntries the email otp entries
	 */
	@Override
	public void cacheResult(List<EmailOTPEntry> emailOTPEntries) {
		for (EmailOTPEntry emailOTPEntry : emailOTPEntries) {
			if (entityCache.getResult(
					entityCacheEnabled, EmailOTPEntryImpl.class,
					emailOTPEntry.getPrimaryKey()) == null) {

				cacheResult(emailOTPEntry);
			}
			else {
				emailOTPEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all email otp entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(EmailOTPEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the email otp entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(EmailOTPEntry emailOTPEntry) {
		entityCache.removeResult(
			entityCacheEnabled, EmailOTPEntryImpl.class,
			emailOTPEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((EmailOTPEntryModelImpl)emailOTPEntry, true);
	}

	@Override
	public void clearCache(List<EmailOTPEntry> emailOTPEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (EmailOTPEntry emailOTPEntry : emailOTPEntries) {
			entityCache.removeResult(
				entityCacheEnabled, EmailOTPEntryImpl.class,
				emailOTPEntry.getPrimaryKey());

			clearUniqueFindersCache(
				(EmailOTPEntryModelImpl)emailOTPEntry, true);
		}
	}

	protected void cacheUniqueFindersCache(
		EmailOTPEntryModelImpl emailOTPEntryModelImpl) {

		Object[] args = new Object[] {emailOTPEntryModelImpl.getUserId()};

		finderCache.putResult(
			_finderPathCountByUserId, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUserId, args, emailOTPEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		EmailOTPEntryModelImpl emailOTPEntryModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {emailOTPEntryModelImpl.getUserId()};

			finderCache.removeResult(_finderPathCountByUserId, args);
			finderCache.removeResult(_finderPathFetchByUserId, args);
		}

		if ((emailOTPEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByUserId.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				emailOTPEntryModelImpl.getOriginalUserId()
			};

			finderCache.removeResult(_finderPathCountByUserId, args);
			finderCache.removeResult(_finderPathFetchByUserId, args);
		}
	}

	/**
	 * Creates a new email otp entry with the primary key. Does not add the email otp entry to the database.
	 *
	 * @param entryId the primary key for the new email otp entry
	 * @return the new email otp entry
	 */
	@Override
	public EmailOTPEntry create(long entryId) {
		EmailOTPEntry emailOTPEntry = new EmailOTPEntryImpl();

		emailOTPEntry.setNew(true);
		emailOTPEntry.setPrimaryKey(entryId);

		emailOTPEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return emailOTPEntry;
	}

	/**
	 * Removes the email otp entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param entryId the primary key of the email otp entry
	 * @return the email otp entry that was removed
	 * @throws NoSuchEmailOTPEntryException if a email otp entry with the primary key could not be found
	 */
	@Override
	public EmailOTPEntry remove(long entryId)
		throws NoSuchEmailOTPEntryException {

		return remove((Serializable)entryId);
	}

	/**
	 * Removes the email otp entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the email otp entry
	 * @return the email otp entry that was removed
	 * @throws NoSuchEmailOTPEntryException if a email otp entry with the primary key could not be found
	 */
	@Override
	public EmailOTPEntry remove(Serializable primaryKey)
		throws NoSuchEmailOTPEntryException {

		Session session = null;

		try {
			session = openSession();

			EmailOTPEntry emailOTPEntry = (EmailOTPEntry)session.get(
				EmailOTPEntryImpl.class, primaryKey);

			if (emailOTPEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEmailOTPEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(emailOTPEntry);
		}
		catch (NoSuchEmailOTPEntryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected EmailOTPEntry removeImpl(EmailOTPEntry emailOTPEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(emailOTPEntry)) {
				emailOTPEntry = (EmailOTPEntry)session.get(
					EmailOTPEntryImpl.class, emailOTPEntry.getPrimaryKeyObj());
			}

			if (emailOTPEntry != null) {
				session.delete(emailOTPEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (emailOTPEntry != null) {
			clearCache(emailOTPEntry);
		}

		return emailOTPEntry;
	}

	@Override
	public EmailOTPEntry updateImpl(EmailOTPEntry emailOTPEntry) {
		boolean isNew = emailOTPEntry.isNew();

		if (!(emailOTPEntry instanceof EmailOTPEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(emailOTPEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					emailOTPEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in emailOTPEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom EmailOTPEntry implementation " +
					emailOTPEntry.getClass());
		}

		EmailOTPEntryModelImpl emailOTPEntryModelImpl =
			(EmailOTPEntryModelImpl)emailOTPEntry;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (emailOTPEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				emailOTPEntry.setCreateDate(now);
			}
			else {
				emailOTPEntry.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!emailOTPEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				emailOTPEntry.setModifiedDate(now);
			}
			else {
				emailOTPEntry.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (emailOTPEntry.isNew()) {
				session.save(emailOTPEntry);

				emailOTPEntry.setNew(false);
			}
			else {
				emailOTPEntry = (EmailOTPEntry)session.merge(emailOTPEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!_columnBitmaskEnabled) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			entityCacheEnabled, EmailOTPEntryImpl.class,
			emailOTPEntry.getPrimaryKey(), emailOTPEntry, false);

		clearUniqueFindersCache(emailOTPEntryModelImpl, false);
		cacheUniqueFindersCache(emailOTPEntryModelImpl);

		emailOTPEntry.resetOriginalValues();

		return emailOTPEntry;
	}

	/**
	 * Returns the email otp entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the email otp entry
	 * @return the email otp entry
	 * @throws NoSuchEmailOTPEntryException if a email otp entry with the primary key could not be found
	 */
	@Override
	public EmailOTPEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEmailOTPEntryException {

		EmailOTPEntry emailOTPEntry = fetchByPrimaryKey(primaryKey);

		if (emailOTPEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEmailOTPEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return emailOTPEntry;
	}

	/**
	 * Returns the email otp entry with the primary key or throws a <code>NoSuchEmailOTPEntryException</code> if it could not be found.
	 *
	 * @param entryId the primary key of the email otp entry
	 * @return the email otp entry
	 * @throws NoSuchEmailOTPEntryException if a email otp entry with the primary key could not be found
	 */
	@Override
	public EmailOTPEntry findByPrimaryKey(long entryId)
		throws NoSuchEmailOTPEntryException {

		return findByPrimaryKey((Serializable)entryId);
	}

	/**
	 * Returns the email otp entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param entryId the primary key of the email otp entry
	 * @return the email otp entry, or <code>null</code> if a email otp entry with the primary key could not be found
	 */
	@Override
	public EmailOTPEntry fetchByPrimaryKey(long entryId) {
		return fetchByPrimaryKey((Serializable)entryId);
	}

	/**
	 * Returns all the email otp entries.
	 *
	 * @return the email otp entries
	 */
	@Override
	public List<EmailOTPEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the email otp entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EmailOTPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of email otp entries
	 * @param end the upper bound of the range of email otp entries (not inclusive)
	 * @return the range of email otp entries
	 */
	@Override
	public List<EmailOTPEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the email otp entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EmailOTPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of email otp entries
	 * @param end the upper bound of the range of email otp entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of email otp entries
	 */
	@Override
	public List<EmailOTPEntry> findAll(
		int start, int end,
		OrderByComparator<EmailOTPEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the email otp entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>EmailOTPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of email otp entries
	 * @param end the upper bound of the range of email otp entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of email otp entries
	 */
	@Override
	public List<EmailOTPEntry> findAll(
		int start, int end, OrderByComparator<EmailOTPEntry> orderByComparator,
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

		List<EmailOTPEntry> list = null;

		if (useFinderCache) {
			list = (List<EmailOTPEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_EMAILOTPENTRY);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_EMAILOTPENTRY;

				sql = sql.concat(EmailOTPEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<EmailOTPEntry>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the email otp entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (EmailOTPEntry emailOTPEntry : findAll()) {
			remove(emailOTPEntry);
		}
	}

	/**
	 * Returns the number of email otp entries.
	 *
	 * @return the number of email otp entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_EMAILOTPENTRY);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(e);
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
		return "entryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_EMAILOTPENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return EmailOTPEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the email otp entry persistence.
	 */
	@Activate
	public void activate() {
		EmailOTPEntryModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		EmailOTPEntryModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, EmailOTPEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, EmailOTPEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathFetchByUserId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, EmailOTPEntryImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUserId",
			new String[] {Long.class.getName()},
			EmailOTPEntryModelImpl.USERID_COLUMN_BITMASK);

		_finderPathCountByUserId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] {Long.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(EmailOTPEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = MFAEmailOTPPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.multi.factor.authentication.checker.email.otp.model.EmailOTPEntry"),
			true);
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

	private boolean _columnBitmaskEnabled;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_EMAILOTPENTRY =
		"SELECT emailOTPEntry FROM EmailOTPEntry emailOTPEntry";

	private static final String _SQL_SELECT_EMAILOTPENTRY_WHERE =
		"SELECT emailOTPEntry FROM EmailOTPEntry emailOTPEntry WHERE ";

	private static final String _SQL_COUNT_EMAILOTPENTRY =
		"SELECT COUNT(emailOTPEntry) FROM EmailOTPEntry emailOTPEntry";

	private static final String _SQL_COUNT_EMAILOTPENTRY_WHERE =
		"SELECT COUNT(emailOTPEntry) FROM EmailOTPEntry emailOTPEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "emailOTPEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No EmailOTPEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No EmailOTPEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		EmailOTPEntryPersistenceImpl.class);

	static {
		try {
			Class.forName(MFAEmailOTPPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}