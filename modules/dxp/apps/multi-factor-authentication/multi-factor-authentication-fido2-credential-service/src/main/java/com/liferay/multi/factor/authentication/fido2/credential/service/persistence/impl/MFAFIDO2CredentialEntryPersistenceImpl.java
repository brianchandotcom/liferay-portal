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

package com.liferay.multi.factor.authentication.fido2.credential.service.persistence.impl;

import com.liferay.multi.factor.authentication.fido2.credential.exception.NoSuchMFAFIDO2CredentialEntryException;
import com.liferay.multi.factor.authentication.fido2.credential.model.MFAFIDO2CredentialEntry;
import com.liferay.multi.factor.authentication.fido2.credential.model.MFAFIDO2CredentialEntryTable;
import com.liferay.multi.factor.authentication.fido2.credential.model.impl.MFAFIDO2CredentialEntryImpl;
import com.liferay.multi.factor.authentication.fido2.credential.model.impl.MFAFIDO2CredentialEntryModelImpl;
import com.liferay.multi.factor.authentication.fido2.credential.service.persistence.MFAFIDO2CredentialEntryPersistence;
import com.liferay.multi.factor.authentication.fido2.credential.service.persistence.impl.constants.MFAFIDOTwoCredentialPersistenceConstants;
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
 * The persistence implementation for the mfafido2 credential entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Arthur Chan
 * @generated
 */
@Component(
	service = {MFAFIDO2CredentialEntryPersistence.class, BasePersistence.class}
)
public class MFAFIDO2CredentialEntryPersistenceImpl
	extends BasePersistenceImpl<MFAFIDO2CredentialEntry>
	implements MFAFIDO2CredentialEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>MFAFIDO2CredentialEntryUtil</code> to access the mfafido2 credential entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		MFAFIDO2CredentialEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUserId;
	private FinderPath _finderPathWithoutPaginationFindByUserId;
	private FinderPath _finderPathCountByUserId;

	/**
	 * Returns all the mfafido2 credential entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching mfafido2 credential entries
	 */
	@Override
	public List<MFAFIDO2CredentialEntry> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the mfafido2 credential entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MFAFIDO2CredentialEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of mfafido2 credential entries
	 * @param end the upper bound of the range of mfafido2 credential entries (not inclusive)
	 * @return the range of matching mfafido2 credential entries
	 */
	@Override
	public List<MFAFIDO2CredentialEntry> findByUserId(
		long userId, int start, int end) {

		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the mfafido2 credential entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MFAFIDO2CredentialEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of mfafido2 credential entries
	 * @param end the upper bound of the range of mfafido2 credential entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mfafido2 credential entries
	 */
	@Override
	public List<MFAFIDO2CredentialEntry> findByUserId(
		long userId, int start, int end,
		OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the mfafido2 credential entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MFAFIDO2CredentialEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of mfafido2 credential entries
	 * @param end the upper bound of the range of mfafido2 credential entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching mfafido2 credential entries
	 */
	@Override
	public List<MFAFIDO2CredentialEntry> findByUserId(
		long userId, int start, int end,
		OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUserId;
				finderArgs = new Object[] {userId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUserId;
			finderArgs = new Object[] {userId, start, end, orderByComparator};
		}

		List<MFAFIDO2CredentialEntry> list = null;

		if (useFinderCache) {
			list = (List<MFAFIDO2CredentialEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry : list) {
					if (userId != mfaFIDO2CredentialEntry.getUserId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_MFAFIDO2CREDENTIALENTRY_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MFAFIDO2CredentialEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				list = (List<MFAFIDO2CredentialEntry>)QueryUtil.list(
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
	 * Returns the first mfafido2 credential entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mfafido2 credential entry
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a matching mfafido2 credential entry could not be found
	 */
	@Override
	public MFAFIDO2CredentialEntry findByUserId_First(
			long userId,
			OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator)
		throws NoSuchMFAFIDO2CredentialEntryException {

		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry = fetchByUserId_First(
			userId, orderByComparator);

		if (mfaFIDO2CredentialEntry != null) {
			return mfaFIDO2CredentialEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchMFAFIDO2CredentialEntryException(sb.toString());
	}

	/**
	 * Returns the first mfafido2 credential entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mfafido2 credential entry, or <code>null</code> if a matching mfafido2 credential entry could not be found
	 */
	@Override
	public MFAFIDO2CredentialEntry fetchByUserId_First(
		long userId,
		OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator) {

		List<MFAFIDO2CredentialEntry> list = findByUserId(
			userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last mfafido2 credential entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mfafido2 credential entry
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a matching mfafido2 credential entry could not be found
	 */
	@Override
	public MFAFIDO2CredentialEntry findByUserId_Last(
			long userId,
			OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator)
		throws NoSuchMFAFIDO2CredentialEntryException {

		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry = fetchByUserId_Last(
			userId, orderByComparator);

		if (mfaFIDO2CredentialEntry != null) {
			return mfaFIDO2CredentialEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchMFAFIDO2CredentialEntryException(sb.toString());
	}

	/**
	 * Returns the last mfafido2 credential entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mfafido2 credential entry, or <code>null</code> if a matching mfafido2 credential entry could not be found
	 */
	@Override
	public MFAFIDO2CredentialEntry fetchByUserId_Last(
		long userId,
		OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator) {

		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<MFAFIDO2CredentialEntry> list = findByUserId(
			userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the mfafido2 credential entries before and after the current mfafido2 credential entry in the ordered set where userId = &#63;.
	 *
	 * @param mfaFIDO2CredentialEntryId the primary key of the current mfafido2 credential entry
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mfafido2 credential entry
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a mfafido2 credential entry with the primary key could not be found
	 */
	@Override
	public MFAFIDO2CredentialEntry[] findByUserId_PrevAndNext(
			long mfaFIDO2CredentialEntryId, long userId,
			OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator)
		throws NoSuchMFAFIDO2CredentialEntryException {

		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry = findByPrimaryKey(
			mfaFIDO2CredentialEntryId);

		Session session = null;

		try {
			session = openSession();

			MFAFIDO2CredentialEntry[] array =
				new MFAFIDO2CredentialEntryImpl[3];

			array[0] = getByUserId_PrevAndNext(
				session, mfaFIDO2CredentialEntry, userId, orderByComparator,
				true);

			array[1] = mfaFIDO2CredentialEntry;

			array[2] = getByUserId_PrevAndNext(
				session, mfaFIDO2CredentialEntry, userId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected MFAFIDO2CredentialEntry getByUserId_PrevAndNext(
		Session session, MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry,
		long userId,
		OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_MFAFIDO2CREDENTIALENTRY_WHERE);

		sb.append(_FINDER_COLUMN_USERID_USERID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(MFAFIDO2CredentialEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						mfaFIDO2CredentialEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MFAFIDO2CredentialEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the mfafido2 credential entries where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry :
				findByUserId(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(mfaFIDO2CredentialEntry);
		}
	}

	/**
	 * Returns the number of mfafido2 credential entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching mfafido2 credential entries
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = _finderPathCountByUserId;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_MFAFIDO2CREDENTIALENTRY_WHERE);

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
		"mfafido2CredentialEntry.userId = ?";

	private FinderPath _finderPathWithPaginationFindByCredentialKeyHash;
	private FinderPath _finderPathWithoutPaginationFindByCredentialKeyHash;
	private FinderPath _finderPathCountByCredentialKeyHash;

	/**
	 * Returns all the mfafido2 credential entries where credentialKeyHash = &#63;.
	 *
	 * @param credentialKeyHash the credential key hash
	 * @return the matching mfafido2 credential entries
	 */
	@Override
	public List<MFAFIDO2CredentialEntry> findByCredentialKeyHash(
		long credentialKeyHash) {

		return findByCredentialKeyHash(
			credentialKeyHash, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the mfafido2 credential entries where credentialKeyHash = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MFAFIDO2CredentialEntryModelImpl</code>.
	 * </p>
	 *
	 * @param credentialKeyHash the credential key hash
	 * @param start the lower bound of the range of mfafido2 credential entries
	 * @param end the upper bound of the range of mfafido2 credential entries (not inclusive)
	 * @return the range of matching mfafido2 credential entries
	 */
	@Override
	public List<MFAFIDO2CredentialEntry> findByCredentialKeyHash(
		long credentialKeyHash, int start, int end) {

		return findByCredentialKeyHash(credentialKeyHash, start, end, null);
	}

	/**
	 * Returns an ordered range of all the mfafido2 credential entries where credentialKeyHash = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MFAFIDO2CredentialEntryModelImpl</code>.
	 * </p>
	 *
	 * @param credentialKeyHash the credential key hash
	 * @param start the lower bound of the range of mfafido2 credential entries
	 * @param end the upper bound of the range of mfafido2 credential entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching mfafido2 credential entries
	 */
	@Override
	public List<MFAFIDO2CredentialEntry> findByCredentialKeyHash(
		long credentialKeyHash, int start, int end,
		OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator) {

		return findByCredentialKeyHash(
			credentialKeyHash, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the mfafido2 credential entries where credentialKeyHash = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MFAFIDO2CredentialEntryModelImpl</code>.
	 * </p>
	 *
	 * @param credentialKeyHash the credential key hash
	 * @param start the lower bound of the range of mfafido2 credential entries
	 * @param end the upper bound of the range of mfafido2 credential entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching mfafido2 credential entries
	 */
	@Override
	public List<MFAFIDO2CredentialEntry> findByCredentialKeyHash(
		long credentialKeyHash, int start, int end,
		OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCredentialKeyHash;
				finderArgs = new Object[] {credentialKeyHash};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCredentialKeyHash;
			finderArgs = new Object[] {
				credentialKeyHash, start, end, orderByComparator
			};
		}

		List<MFAFIDO2CredentialEntry> list = null;

		if (useFinderCache) {
			list = (List<MFAFIDO2CredentialEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry : list) {
					if (credentialKeyHash !=
							mfaFIDO2CredentialEntry.getCredentialKeyHash()) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_MFAFIDO2CREDENTIALENTRY_WHERE);

			sb.append(_FINDER_COLUMN_CREDENTIALKEYHASH_CREDENTIALKEYHASH_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(MFAFIDO2CredentialEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(credentialKeyHash);

				list = (List<MFAFIDO2CredentialEntry>)QueryUtil.list(
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
	 * Returns the first mfafido2 credential entry in the ordered set where credentialKeyHash = &#63;.
	 *
	 * @param credentialKeyHash the credential key hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mfafido2 credential entry
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a matching mfafido2 credential entry could not be found
	 */
	@Override
	public MFAFIDO2CredentialEntry findByCredentialKeyHash_First(
			long credentialKeyHash,
			OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator)
		throws NoSuchMFAFIDO2CredentialEntryException {

		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry =
			fetchByCredentialKeyHash_First(
				credentialKeyHash, orderByComparator);

		if (mfaFIDO2CredentialEntry != null) {
			return mfaFIDO2CredentialEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("credentialKeyHash=");
		sb.append(credentialKeyHash);

		sb.append("}");

		throw new NoSuchMFAFIDO2CredentialEntryException(sb.toString());
	}

	/**
	 * Returns the first mfafido2 credential entry in the ordered set where credentialKeyHash = &#63;.
	 *
	 * @param credentialKeyHash the credential key hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching mfafido2 credential entry, or <code>null</code> if a matching mfafido2 credential entry could not be found
	 */
	@Override
	public MFAFIDO2CredentialEntry fetchByCredentialKeyHash_First(
		long credentialKeyHash,
		OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator) {

		List<MFAFIDO2CredentialEntry> list = findByCredentialKeyHash(
			credentialKeyHash, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last mfafido2 credential entry in the ordered set where credentialKeyHash = &#63;.
	 *
	 * @param credentialKeyHash the credential key hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mfafido2 credential entry
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a matching mfafido2 credential entry could not be found
	 */
	@Override
	public MFAFIDO2CredentialEntry findByCredentialKeyHash_Last(
			long credentialKeyHash,
			OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator)
		throws NoSuchMFAFIDO2CredentialEntryException {

		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry =
			fetchByCredentialKeyHash_Last(credentialKeyHash, orderByComparator);

		if (mfaFIDO2CredentialEntry != null) {
			return mfaFIDO2CredentialEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("credentialKeyHash=");
		sb.append(credentialKeyHash);

		sb.append("}");

		throw new NoSuchMFAFIDO2CredentialEntryException(sb.toString());
	}

	/**
	 * Returns the last mfafido2 credential entry in the ordered set where credentialKeyHash = &#63;.
	 *
	 * @param credentialKeyHash the credential key hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching mfafido2 credential entry, or <code>null</code> if a matching mfafido2 credential entry could not be found
	 */
	@Override
	public MFAFIDO2CredentialEntry fetchByCredentialKeyHash_Last(
		long credentialKeyHash,
		OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator) {

		int count = countByCredentialKeyHash(credentialKeyHash);

		if (count == 0) {
			return null;
		}

		List<MFAFIDO2CredentialEntry> list = findByCredentialKeyHash(
			credentialKeyHash, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the mfafido2 credential entries before and after the current mfafido2 credential entry in the ordered set where credentialKeyHash = &#63;.
	 *
	 * @param mfaFIDO2CredentialEntryId the primary key of the current mfafido2 credential entry
	 * @param credentialKeyHash the credential key hash
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next mfafido2 credential entry
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a mfafido2 credential entry with the primary key could not be found
	 */
	@Override
	public MFAFIDO2CredentialEntry[] findByCredentialKeyHash_PrevAndNext(
			long mfaFIDO2CredentialEntryId, long credentialKeyHash,
			OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator)
		throws NoSuchMFAFIDO2CredentialEntryException {

		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry = findByPrimaryKey(
			mfaFIDO2CredentialEntryId);

		Session session = null;

		try {
			session = openSession();

			MFAFIDO2CredentialEntry[] array =
				new MFAFIDO2CredentialEntryImpl[3];

			array[0] = getByCredentialKeyHash_PrevAndNext(
				session, mfaFIDO2CredentialEntry, credentialKeyHash,
				orderByComparator, true);

			array[1] = mfaFIDO2CredentialEntry;

			array[2] = getByCredentialKeyHash_PrevAndNext(
				session, mfaFIDO2CredentialEntry, credentialKeyHash,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected MFAFIDO2CredentialEntry getByCredentialKeyHash_PrevAndNext(
		Session session, MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry,
		long credentialKeyHash,
		OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_MFAFIDO2CREDENTIALENTRY_WHERE);

		sb.append(_FINDER_COLUMN_CREDENTIALKEYHASH_CREDENTIALKEYHASH_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(MFAFIDO2CredentialEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(credentialKeyHash);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						mfaFIDO2CredentialEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<MFAFIDO2CredentialEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the mfafido2 credential entries where credentialKeyHash = &#63; from the database.
	 *
	 * @param credentialKeyHash the credential key hash
	 */
	@Override
	public void removeByCredentialKeyHash(long credentialKeyHash) {
		for (MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry :
				findByCredentialKeyHash(
					credentialKeyHash, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(mfaFIDO2CredentialEntry);
		}
	}

	/**
	 * Returns the number of mfafido2 credential entries where credentialKeyHash = &#63;.
	 *
	 * @param credentialKeyHash the credential key hash
	 * @return the number of matching mfafido2 credential entries
	 */
	@Override
	public int countByCredentialKeyHash(long credentialKeyHash) {
		FinderPath finderPath = _finderPathCountByCredentialKeyHash;

		Object[] finderArgs = new Object[] {credentialKeyHash};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_MFAFIDO2CREDENTIALENTRY_WHERE);

			sb.append(_FINDER_COLUMN_CREDENTIALKEYHASH_CREDENTIALKEYHASH_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(credentialKeyHash);

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

	private static final String
		_FINDER_COLUMN_CREDENTIALKEYHASH_CREDENTIALKEYHASH_2 =
			"mfafido2CredentialEntry.credentialKeyHash = ?";

	private FinderPath _finderPathFetchByU_C;
	private FinderPath _finderPathCountByU_C;

	/**
	 * Returns the mfafido2 credential entry where userId = &#63; and credentialKeyHash = &#63; or throws a <code>NoSuchMFAFIDO2CredentialEntryException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param credentialKeyHash the credential key hash
	 * @return the matching mfafido2 credential entry
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a matching mfafido2 credential entry could not be found
	 */
	@Override
	public MFAFIDO2CredentialEntry findByU_C(
			long userId, long credentialKeyHash)
		throws NoSuchMFAFIDO2CredentialEntryException {

		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry = fetchByU_C(
			userId, credentialKeyHash);

		if (mfaFIDO2CredentialEntry == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("userId=");
			sb.append(userId);

			sb.append(", credentialKeyHash=");
			sb.append(credentialKeyHash);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchMFAFIDO2CredentialEntryException(sb.toString());
		}

		return mfaFIDO2CredentialEntry;
	}

	/**
	 * Returns the mfafido2 credential entry where userId = &#63; and credentialKeyHash = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param credentialKeyHash the credential key hash
	 * @return the matching mfafido2 credential entry, or <code>null</code> if a matching mfafido2 credential entry could not be found
	 */
	@Override
	public MFAFIDO2CredentialEntry fetchByU_C(
		long userId, long credentialKeyHash) {

		return fetchByU_C(userId, credentialKeyHash, true);
	}

	/**
	 * Returns the mfafido2 credential entry where userId = &#63; and credentialKeyHash = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param credentialKeyHash the credential key hash
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching mfafido2 credential entry, or <code>null</code> if a matching mfafido2 credential entry could not be found
	 */
	@Override
	public MFAFIDO2CredentialEntry fetchByU_C(
		long userId, long credentialKeyHash, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {userId, credentialKeyHash};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByU_C, finderArgs);
		}

		if (result instanceof MFAFIDO2CredentialEntry) {
			MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry =
				(MFAFIDO2CredentialEntry)result;

			if ((userId != mfaFIDO2CredentialEntry.getUserId()) ||
				(credentialKeyHash !=
					mfaFIDO2CredentialEntry.getCredentialKeyHash())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_MFAFIDO2CREDENTIALENTRY_WHERE);

			sb.append(_FINDER_COLUMN_U_C_USERID_2);

			sb.append(_FINDER_COLUMN_U_C_CREDENTIALKEYHASH_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(credentialKeyHash);

				List<MFAFIDO2CredentialEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByU_C, finderArgs, list);
					}
				}
				else {
					MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry = list.get(
						0);

					result = mfaFIDO2CredentialEntry;

					cacheResult(mfaFIDO2CredentialEntry);
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
			return (MFAFIDO2CredentialEntry)result;
		}
	}

	/**
	 * Removes the mfafido2 credential entry where userId = &#63; and credentialKeyHash = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param credentialKeyHash the credential key hash
	 * @return the mfafido2 credential entry that was removed
	 */
	@Override
	public MFAFIDO2CredentialEntry removeByU_C(
			long userId, long credentialKeyHash)
		throws NoSuchMFAFIDO2CredentialEntryException {

		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry = findByU_C(
			userId, credentialKeyHash);

		return remove(mfaFIDO2CredentialEntry);
	}

	/**
	 * Returns the number of mfafido2 credential entries where userId = &#63; and credentialKeyHash = &#63;.
	 *
	 * @param userId the user ID
	 * @param credentialKeyHash the credential key hash
	 * @return the number of matching mfafido2 credential entries
	 */
	@Override
	public int countByU_C(long userId, long credentialKeyHash) {
		FinderPath finderPath = _finderPathCountByU_C;

		Object[] finderArgs = new Object[] {userId, credentialKeyHash};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_MFAFIDO2CREDENTIALENTRY_WHERE);

			sb.append(_FINDER_COLUMN_U_C_USERID_2);

			sb.append(_FINDER_COLUMN_U_C_CREDENTIALKEYHASH_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(credentialKeyHash);

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

	private static final String _FINDER_COLUMN_U_C_USERID_2 =
		"mfafido2CredentialEntry.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_C_CREDENTIALKEYHASH_2 =
		"mfafido2CredentialEntry.credentialKeyHash = ?";

	public MFAFIDO2CredentialEntryPersistenceImpl() {
		setModelClass(MFAFIDO2CredentialEntry.class);

		setModelImplClass(MFAFIDO2CredentialEntryImpl.class);
		setModelPKClass(long.class);

		setTable(MFAFIDO2CredentialEntryTable.INSTANCE);
	}

	/**
	 * Caches the mfafido2 credential entry in the entity cache if it is enabled.
	 *
	 * @param mfaFIDO2CredentialEntry the mfafido2 credential entry
	 */
	@Override
	public void cacheResult(MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry) {
		entityCache.putResult(
			MFAFIDO2CredentialEntryImpl.class,
			mfaFIDO2CredentialEntry.getPrimaryKey(), mfaFIDO2CredentialEntry);

		finderCache.putResult(
			_finderPathFetchByU_C,
			new Object[] {
				mfaFIDO2CredentialEntry.getUserId(),
				mfaFIDO2CredentialEntry.getCredentialKeyHash()
			},
			mfaFIDO2CredentialEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the mfafido2 credential entries in the entity cache if it is enabled.
	 *
	 * @param mfaFIDO2CredentialEntries the mfafido2 credential entries
	 */
	@Override
	public void cacheResult(
		List<MFAFIDO2CredentialEntry> mfaFIDO2CredentialEntries) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (mfaFIDO2CredentialEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry :
				mfaFIDO2CredentialEntries) {

			if (entityCache.getResult(
					MFAFIDO2CredentialEntryImpl.class,
					mfaFIDO2CredentialEntry.getPrimaryKey()) == null) {

				cacheResult(mfaFIDO2CredentialEntry);
			}
		}
	}

	/**
	 * Clears the cache for all mfafido2 credential entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(MFAFIDO2CredentialEntryImpl.class);

		finderCache.clearCache(MFAFIDO2CredentialEntryImpl.class);
	}

	/**
	 * Clears the cache for the mfafido2 credential entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry) {
		entityCache.removeResult(
			MFAFIDO2CredentialEntryImpl.class, mfaFIDO2CredentialEntry);
	}

	@Override
	public void clearCache(
		List<MFAFIDO2CredentialEntry> mfaFIDO2CredentialEntries) {

		for (MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry :
				mfaFIDO2CredentialEntries) {

			entityCache.removeResult(
				MFAFIDO2CredentialEntryImpl.class, mfaFIDO2CredentialEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(MFAFIDO2CredentialEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				MFAFIDO2CredentialEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		MFAFIDO2CredentialEntryModelImpl mfaFIDO2CredentialEntryModelImpl) {

		Object[] args = new Object[] {
			mfaFIDO2CredentialEntryModelImpl.getUserId(),
			mfaFIDO2CredentialEntryModelImpl.getCredentialKeyHash()
		};

		finderCache.putResult(_finderPathCountByU_C, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByU_C, args, mfaFIDO2CredentialEntryModelImpl);
	}

	/**
	 * Creates a new mfafido2 credential entry with the primary key. Does not add the mfafido2 credential entry to the database.
	 *
	 * @param mfaFIDO2CredentialEntryId the primary key for the new mfafido2 credential entry
	 * @return the new mfafido2 credential entry
	 */
	@Override
	public MFAFIDO2CredentialEntry create(long mfaFIDO2CredentialEntryId) {
		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry =
			new MFAFIDO2CredentialEntryImpl();

		mfaFIDO2CredentialEntry.setNew(true);
		mfaFIDO2CredentialEntry.setPrimaryKey(mfaFIDO2CredentialEntryId);

		mfaFIDO2CredentialEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return mfaFIDO2CredentialEntry;
	}

	/**
	 * Removes the mfafido2 credential entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param mfaFIDO2CredentialEntryId the primary key of the mfafido2 credential entry
	 * @return the mfafido2 credential entry that was removed
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a mfafido2 credential entry with the primary key could not be found
	 */
	@Override
	public MFAFIDO2CredentialEntry remove(long mfaFIDO2CredentialEntryId)
		throws NoSuchMFAFIDO2CredentialEntryException {

		return remove((Serializable)mfaFIDO2CredentialEntryId);
	}

	/**
	 * Removes the mfafido2 credential entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the mfafido2 credential entry
	 * @return the mfafido2 credential entry that was removed
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a mfafido2 credential entry with the primary key could not be found
	 */
	@Override
	public MFAFIDO2CredentialEntry remove(Serializable primaryKey)
		throws NoSuchMFAFIDO2CredentialEntryException {

		Session session = null;

		try {
			session = openSession();

			MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry =
				(MFAFIDO2CredentialEntry)session.get(
					MFAFIDO2CredentialEntryImpl.class, primaryKey);

			if (mfaFIDO2CredentialEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchMFAFIDO2CredentialEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(mfaFIDO2CredentialEntry);
		}
		catch (NoSuchMFAFIDO2CredentialEntryException noSuchEntityException) {
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
	protected MFAFIDO2CredentialEntry removeImpl(
		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(mfaFIDO2CredentialEntry)) {
				mfaFIDO2CredentialEntry = (MFAFIDO2CredentialEntry)session.get(
					MFAFIDO2CredentialEntryImpl.class,
					mfaFIDO2CredentialEntry.getPrimaryKeyObj());
			}

			if (mfaFIDO2CredentialEntry != null) {
				session.delete(mfaFIDO2CredentialEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (mfaFIDO2CredentialEntry != null) {
			clearCache(mfaFIDO2CredentialEntry);
		}

		return mfaFIDO2CredentialEntry;
	}

	@Override
	public MFAFIDO2CredentialEntry updateImpl(
		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry) {

		boolean isNew = mfaFIDO2CredentialEntry.isNew();

		if (!(mfaFIDO2CredentialEntry instanceof
				MFAFIDO2CredentialEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(mfaFIDO2CredentialEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					mfaFIDO2CredentialEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in mfaFIDO2CredentialEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom MFAFIDO2CredentialEntry implementation " +
					mfaFIDO2CredentialEntry.getClass());
		}

		MFAFIDO2CredentialEntryModelImpl mfaFIDO2CredentialEntryModelImpl =
			(MFAFIDO2CredentialEntryModelImpl)mfaFIDO2CredentialEntry;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (mfaFIDO2CredentialEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				mfaFIDO2CredentialEntry.setCreateDate(date);
			}
			else {
				mfaFIDO2CredentialEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!mfaFIDO2CredentialEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				mfaFIDO2CredentialEntry.setModifiedDate(date);
			}
			else {
				mfaFIDO2CredentialEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(mfaFIDO2CredentialEntry);
			}
			else {
				mfaFIDO2CredentialEntry =
					(MFAFIDO2CredentialEntry)session.merge(
						mfaFIDO2CredentialEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			MFAFIDO2CredentialEntryImpl.class, mfaFIDO2CredentialEntryModelImpl,
			false, true);

		cacheUniqueFindersCache(mfaFIDO2CredentialEntryModelImpl);

		if (isNew) {
			mfaFIDO2CredentialEntry.setNew(false);
		}

		mfaFIDO2CredentialEntry.resetOriginalValues();

		return mfaFIDO2CredentialEntry;
	}

	/**
	 * Returns the mfafido2 credential entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the mfafido2 credential entry
	 * @return the mfafido2 credential entry
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a mfafido2 credential entry with the primary key could not be found
	 */
	@Override
	public MFAFIDO2CredentialEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchMFAFIDO2CredentialEntryException {

		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry = fetchByPrimaryKey(
			primaryKey);

		if (mfaFIDO2CredentialEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchMFAFIDO2CredentialEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return mfaFIDO2CredentialEntry;
	}

	/**
	 * Returns the mfafido2 credential entry with the primary key or throws a <code>NoSuchMFAFIDO2CredentialEntryException</code> if it could not be found.
	 *
	 * @param mfaFIDO2CredentialEntryId the primary key of the mfafido2 credential entry
	 * @return the mfafido2 credential entry
	 * @throws NoSuchMFAFIDO2CredentialEntryException if a mfafido2 credential entry with the primary key could not be found
	 */
	@Override
	public MFAFIDO2CredentialEntry findByPrimaryKey(
			long mfaFIDO2CredentialEntryId)
		throws NoSuchMFAFIDO2CredentialEntryException {

		return findByPrimaryKey((Serializable)mfaFIDO2CredentialEntryId);
	}

	/**
	 * Returns the mfafido2 credential entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param mfaFIDO2CredentialEntryId the primary key of the mfafido2 credential entry
	 * @return the mfafido2 credential entry, or <code>null</code> if a mfafido2 credential entry with the primary key could not be found
	 */
	@Override
	public MFAFIDO2CredentialEntry fetchByPrimaryKey(
		long mfaFIDO2CredentialEntryId) {

		return fetchByPrimaryKey((Serializable)mfaFIDO2CredentialEntryId);
	}

	/**
	 * Returns all the mfafido2 credential entries.
	 *
	 * @return the mfafido2 credential entries
	 */
	@Override
	public List<MFAFIDO2CredentialEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the mfafido2 credential entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MFAFIDO2CredentialEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mfafido2 credential entries
	 * @param end the upper bound of the range of mfafido2 credential entries (not inclusive)
	 * @return the range of mfafido2 credential entries
	 */
	@Override
	public List<MFAFIDO2CredentialEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the mfafido2 credential entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MFAFIDO2CredentialEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mfafido2 credential entries
	 * @param end the upper bound of the range of mfafido2 credential entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of mfafido2 credential entries
	 */
	@Override
	public List<MFAFIDO2CredentialEntry> findAll(
		int start, int end,
		OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the mfafido2 credential entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>MFAFIDO2CredentialEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mfafido2 credential entries
	 * @param end the upper bound of the range of mfafido2 credential entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of mfafido2 credential entries
	 */
	@Override
	public List<MFAFIDO2CredentialEntry> findAll(
		int start, int end,
		OrderByComparator<MFAFIDO2CredentialEntry> orderByComparator,
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

		List<MFAFIDO2CredentialEntry> list = null;

		if (useFinderCache) {
			list = (List<MFAFIDO2CredentialEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_MFAFIDO2CREDENTIALENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_MFAFIDO2CREDENTIALENTRY;

				sql = sql.concat(
					MFAFIDO2CredentialEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<MFAFIDO2CredentialEntry>)QueryUtil.list(
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
	 * Removes all the mfafido2 credential entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry : findAll()) {
			remove(mfaFIDO2CredentialEntry);
		}
	}

	/**
	 * Returns the number of mfafido2 credential entries.
	 *
	 * @return the number of mfafido2 credential entries
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
					_SQL_COUNT_MFAFIDO2CREDENTIALENTRY);

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
		return "mfaFIDO2CredentialEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_MFAFIDO2CREDENTIALENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return MFAFIDO2CredentialEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the mfafido2 credential entry persistence.
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

		_finderPathWithPaginationFindByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"userId"}, true);

		_finderPathWithoutPaginationFindByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] {Long.class.getName()}, new String[] {"userId"}, true);

		_finderPathCountByUserId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] {Long.class.getName()}, new String[] {"userId"},
			false);

		_finderPathWithPaginationFindByCredentialKeyHash = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCredentialKeyHash",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"credentialKeyHash"}, true);

		_finderPathWithoutPaginationFindByCredentialKeyHash = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCredentialKeyHash", new String[] {Long.class.getName()},
			new String[] {"credentialKeyHash"}, true);

		_finderPathCountByCredentialKeyHash = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCredentialKeyHash", new String[] {Long.class.getName()},
			new String[] {"credentialKeyHash"}, false);

		_finderPathFetchByU_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByU_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"userId", "credentialKeyHash"}, true);

		_finderPathCountByU_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"userId", "credentialKeyHash"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(MFAFIDO2CredentialEntryImpl.class.getName());
	}

	@Override
	@Reference(
		target = MFAFIDOTwoCredentialPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = MFAFIDOTwoCredentialPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = MFAFIDOTwoCredentialPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_MFAFIDO2CREDENTIALENTRY =
		"SELECT mfafido2CredentialEntry FROM MFAFIDO2CredentialEntry mfafido2CredentialEntry";

	private static final String _SQL_SELECT_MFAFIDO2CREDENTIALENTRY_WHERE =
		"SELECT mfafido2CredentialEntry FROM MFAFIDO2CredentialEntry mfafido2CredentialEntry WHERE ";

	private static final String _SQL_COUNT_MFAFIDO2CREDENTIALENTRY =
		"SELECT COUNT(mfafido2CredentialEntry) FROM MFAFIDO2CredentialEntry mfafido2CredentialEntry";

	private static final String _SQL_COUNT_MFAFIDO2CREDENTIALENTRY_WHERE =
		"SELECT COUNT(mfafido2CredentialEntry) FROM MFAFIDO2CredentialEntry mfafido2CredentialEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"mfafido2CredentialEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No MFAFIDO2CredentialEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No MFAFIDO2CredentialEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		MFAFIDO2CredentialEntryPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private MFAFIDO2CredentialEntryModelArgumentsResolver
		_mfaFIDO2CredentialEntryModelArgumentsResolver;

}