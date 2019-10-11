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

package com.liferay.mail.reader.service.persistence.impl;

import com.liferay.mail.reader.exception.NoSuchFolderException;
import com.liferay.mail.reader.model.Folder;
import com.liferay.mail.reader.model.impl.FolderImpl;
import com.liferay.mail.reader.model.impl.FolderModelImpl;
import com.liferay.mail.reader.service.persistence.FolderPersistence;
import com.liferay.mail.reader.service.persistence.impl.constants.MailPersistenceConstants;
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
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the folder service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = FolderPersistence.class)
public class FolderPersistenceImpl
	extends BasePersistenceImpl<Folder> implements FolderPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>FolderUtil</code> to access the folder persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		FolderImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByAccountId;
	private FinderPath _finderPathWithoutPaginationFindByAccountId;
	private FinderPath _finderPathCountByAccountId;

	/**
	 * Returns all the folders where accountId = &#63;.
	 *
	 * @param accountId the account ID
	 * @return the matching folders
	 */
	@Override
	public List<Folder> findByAccountId(long accountId) {
		return findByAccountId(
			accountId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the folders where accountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FolderModelImpl</code>.
	 * </p>
	 *
	 * @param accountId the account ID
	 * @param start the lower bound of the range of folders
	 * @param end the upper bound of the range of folders (not inclusive)
	 * @return the range of matching folders
	 */
	@Override
	public List<Folder> findByAccountId(long accountId, int start, int end) {
		return findByAccountId(accountId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the folders where accountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FolderModelImpl</code>.
	 * </p>
	 *
	 * @param accountId the account ID
	 * @param start the lower bound of the range of folders
	 * @param end the upper bound of the range of folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching folders
	 */
	@Override
	public List<Folder> findByAccountId(
		long accountId, int start, int end,
		OrderByComparator<Folder> orderByComparator) {

		return findByAccountId(accountId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the folders where accountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FolderModelImpl</code>.
	 * </p>
	 *
	 * @param accountId the account ID
	 * @param start the lower bound of the range of folders
	 * @param end the upper bound of the range of folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching folders
	 */
	@Override
	public List<Folder> findByAccountId(
		long accountId, int start, int end,
		OrderByComparator<Folder> orderByComparator, boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByAccountId;
				finderArgs = new Object[] {accountId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByAccountId;
			finderArgs = new Object[] {
				accountId, start, end, orderByComparator
			};
		}

		List<Folder> list = null;

		if (useFinderCache) {
			list = (List<Folder>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (Folder folder : list) {
					if (accountId != folder.getAccountId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_FOLDER_WHERE);

			query.append(_FINDER_COLUMN_ACCOUNTID_ACCOUNTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(FolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(accountId);

				list = (List<Folder>)QueryUtil.list(
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
	 * Returns the first folder in the ordered set where accountId = &#63;.
	 *
	 * @param accountId the account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching folder
	 * @throws NoSuchFolderException if a matching folder could not be found
	 */
	@Override
	public Folder findByAccountId_First(
			long accountId, OrderByComparator<Folder> orderByComparator)
		throws NoSuchFolderException {

		Folder folder = fetchByAccountId_First(accountId, orderByComparator);

		if (folder != null) {
			return folder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("accountId=");
		msg.append(accountId);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the first folder in the ordered set where accountId = &#63;.
	 *
	 * @param accountId the account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching folder, or <code>null</code> if a matching folder could not be found
	 */
	@Override
	public Folder fetchByAccountId_First(
		long accountId, OrderByComparator<Folder> orderByComparator) {

		List<Folder> list = findByAccountId(accountId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last folder in the ordered set where accountId = &#63;.
	 *
	 * @param accountId the account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching folder
	 * @throws NoSuchFolderException if a matching folder could not be found
	 */
	@Override
	public Folder findByAccountId_Last(
			long accountId, OrderByComparator<Folder> orderByComparator)
		throws NoSuchFolderException {

		Folder folder = fetchByAccountId_Last(accountId, orderByComparator);

		if (folder != null) {
			return folder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("accountId=");
		msg.append(accountId);

		msg.append("}");

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the last folder in the ordered set where accountId = &#63;.
	 *
	 * @param accountId the account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching folder, or <code>null</code> if a matching folder could not be found
	 */
	@Override
	public Folder fetchByAccountId_Last(
		long accountId, OrderByComparator<Folder> orderByComparator) {

		int count = countByAccountId(accountId);

		if (count == 0) {
			return null;
		}

		List<Folder> list = findByAccountId(
			accountId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the folders before and after the current folder in the ordered set where accountId = &#63;.
	 *
	 * @param folderId the primary key of the current folder
	 * @param accountId the account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next folder
	 * @throws NoSuchFolderException if a folder with the primary key could not be found
	 */
	@Override
	public Folder[] findByAccountId_PrevAndNext(
			long folderId, long accountId,
			OrderByComparator<Folder> orderByComparator)
		throws NoSuchFolderException {

		Folder folder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			Folder[] array = new FolderImpl[3];

			array[0] = getByAccountId_PrevAndNext(
				session, folder, accountId, orderByComparator, true);

			array[1] = folder;

			array[2] = getByAccountId_PrevAndNext(
				session, folder, accountId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Folder getByAccountId_PrevAndNext(
		Session session, Folder folder, long accountId,
		OrderByComparator<Folder> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_FOLDER_WHERE);

		query.append(_FINDER_COLUMN_ACCOUNTID_ACCOUNTID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(FolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(accountId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(folder)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<Folder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the folders where accountId = &#63; from the database.
	 *
	 * @param accountId the account ID
	 */
	@Override
	public void removeByAccountId(long accountId) {
		for (Folder folder :
				findByAccountId(
					accountId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(folder);
		}
	}

	/**
	 * Returns the number of folders where accountId = &#63;.
	 *
	 * @param accountId the account ID
	 * @return the number of matching folders
	 */
	@Override
	public int countByAccountId(long accountId) {
		FinderPath finderPath = _finderPathCountByAccountId;

		Object[] finderArgs = new Object[] {accountId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_FOLDER_WHERE);

			query.append(_FINDER_COLUMN_ACCOUNTID_ACCOUNTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(accountId);

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

	private static final String _FINDER_COLUMN_ACCOUNTID_ACCOUNTID_2 =
		"folder.accountId = ?";

	private FinderPath _finderPathFetchByA_F;
	private FinderPath _finderPathCountByA_F;

	/**
	 * Returns the folder where accountId = &#63; and fullName = &#63; or throws a <code>NoSuchFolderException</code> if it could not be found.
	 *
	 * @param accountId the account ID
	 * @param fullName the full name
	 * @return the matching folder
	 * @throws NoSuchFolderException if a matching folder could not be found
	 */
	@Override
	public Folder findByA_F(long accountId, String fullName)
		throws NoSuchFolderException {

		Folder folder = fetchByA_F(accountId, fullName);

		if (folder == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("accountId=");
			msg.append(accountId);

			msg.append(", fullName=");
			msg.append(fullName);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchFolderException(msg.toString());
		}

		return folder;
	}

	/**
	 * Returns the folder where accountId = &#63; and fullName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param accountId the account ID
	 * @param fullName the full name
	 * @return the matching folder, or <code>null</code> if a matching folder could not be found
	 */
	@Override
	public Folder fetchByA_F(long accountId, String fullName) {
		return fetchByA_F(accountId, fullName, true);
	}

	/**
	 * Returns the folder where accountId = &#63; and fullName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param accountId the account ID
	 * @param fullName the full name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching folder, or <code>null</code> if a matching folder could not be found
	 */
	@Override
	public Folder fetchByA_F(
		long accountId, String fullName, boolean useFinderCache) {

		fullName = Objects.toString(fullName, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {accountId, fullName};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByA_F, finderArgs, this);
		}

		if (result instanceof Folder) {
			Folder folder = (Folder)result;

			if ((accountId != folder.getAccountId()) ||
				!Objects.equals(fullName, folder.getFullName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_FOLDER_WHERE);

			query.append(_FINDER_COLUMN_A_F_ACCOUNTID_2);

			boolean bindFullName = false;

			if (fullName.isEmpty()) {
				query.append(_FINDER_COLUMN_A_F_FULLNAME_3);
			}
			else {
				bindFullName = true;

				query.append(_FINDER_COLUMN_A_F_FULLNAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(accountId);

				if (bindFullName) {
					qPos.add(fullName);
				}

				List<Folder> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByA_F, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {accountId, fullName};
							}

							_log.warn(
								"FolderPersistenceImpl.fetchByA_F(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					Folder folder = list.get(0);

					result = folder;

					cacheResult(folder);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(_finderPathFetchByA_F, finderArgs);
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
			return (Folder)result;
		}
	}

	/**
	 * Removes the folder where accountId = &#63; and fullName = &#63; from the database.
	 *
	 * @param accountId the account ID
	 * @param fullName the full name
	 * @return the folder that was removed
	 */
	@Override
	public Folder removeByA_F(long accountId, String fullName)
		throws NoSuchFolderException {

		Folder folder = findByA_F(accountId, fullName);

		return remove(folder);
	}

	/**
	 * Returns the number of folders where accountId = &#63; and fullName = &#63;.
	 *
	 * @param accountId the account ID
	 * @param fullName the full name
	 * @return the number of matching folders
	 */
	@Override
	public int countByA_F(long accountId, String fullName) {
		fullName = Objects.toString(fullName, "");

		FinderPath finderPath = _finderPathCountByA_F;

		Object[] finderArgs = new Object[] {accountId, fullName};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_FOLDER_WHERE);

			query.append(_FINDER_COLUMN_A_F_ACCOUNTID_2);

			boolean bindFullName = false;

			if (fullName.isEmpty()) {
				query.append(_FINDER_COLUMN_A_F_FULLNAME_3);
			}
			else {
				bindFullName = true;

				query.append(_FINDER_COLUMN_A_F_FULLNAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(accountId);

				if (bindFullName) {
					qPos.add(fullName);
				}

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

	private static final String _FINDER_COLUMN_A_F_ACCOUNTID_2 =
		"folder.accountId = ? AND ";

	private static final String _FINDER_COLUMN_A_F_FULLNAME_2 =
		"folder.fullName = ?";

	private static final String _FINDER_COLUMN_A_F_FULLNAME_3 =
		"(folder.fullName IS NULL OR folder.fullName = '')";

	public FolderPersistenceImpl() {
		setModelClass(Folder.class);

		setModelImplClass(FolderImpl.class);
		setModelPKClass(long.class);
	}

	/**
	 * Caches the folder in the entity cache if it is enabled.
	 *
	 * @param folder the folder
	 */
	@Override
	public void cacheResult(Folder folder) {
		entityCache.putResult(
			entityCacheEnabled, FolderImpl.class, folder.getPrimaryKey(),
			folder);

		finderCache.putResult(
			_finderPathFetchByA_F,
			new Object[] {folder.getAccountId(), folder.getFullName()}, folder);

		folder.resetOriginalValues();
	}

	/**
	 * Caches the folders in the entity cache if it is enabled.
	 *
	 * @param folders the folders
	 */
	@Override
	public void cacheResult(List<Folder> folders) {
		for (Folder folder : folders) {
			if (entityCache.getResult(
					entityCacheEnabled, FolderImpl.class,
					folder.getPrimaryKey()) == null) {

				cacheResult(folder);
			}
			else {
				folder.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all folders.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(FolderImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the folder.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Folder folder) {
		entityCache.removeResult(
			entityCacheEnabled, FolderImpl.class, folder.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((FolderModelImpl)folder, true);
	}

	@Override
	public void clearCache(List<Folder> folders) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Folder folder : folders) {
			entityCache.removeResult(
				entityCacheEnabled, FolderImpl.class, folder.getPrimaryKey());

			clearUniqueFindersCache((FolderModelImpl)folder, true);
		}
	}

	protected void cacheUniqueFindersCache(FolderModelImpl folderModelImpl) {
		Object[] args = new Object[] {
			folderModelImpl.getAccountId(), folderModelImpl.getFullName()
		};

		finderCache.putResult(
			_finderPathCountByA_F, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByA_F, args, folderModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		FolderModelImpl folderModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				folderModelImpl.getAccountId(), folderModelImpl.getFullName()
			};

			finderCache.removeResult(_finderPathCountByA_F, args);
			finderCache.removeResult(_finderPathFetchByA_F, args);
		}

		if ((folderModelImpl.getColumnBitmask() &
			 _finderPathFetchByA_F.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				folderModelImpl.getOriginalAccountId(),
				folderModelImpl.getOriginalFullName()
			};

			finderCache.removeResult(_finderPathCountByA_F, args);
			finderCache.removeResult(_finderPathFetchByA_F, args);
		}
	}

	/**
	 * Creates a new folder with the primary key. Does not add the folder to the database.
	 *
	 * @param folderId the primary key for the new folder
	 * @return the new folder
	 */
	@Override
	public Folder create(long folderId) {
		Folder folder = new FolderImpl();

		folder.setNew(true);
		folder.setPrimaryKey(folderId);

		folder.setCompanyId(CompanyThreadLocal.getCompanyId());

		return folder;
	}

	/**
	 * Removes the folder with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param folderId the primary key of the folder
	 * @return the folder that was removed
	 * @throws NoSuchFolderException if a folder with the primary key could not be found
	 */
	@Override
	public Folder remove(long folderId) throws NoSuchFolderException {
		return remove((Serializable)folderId);
	}

	/**
	 * Removes the folder with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the folder
	 * @return the folder that was removed
	 * @throws NoSuchFolderException if a folder with the primary key could not be found
	 */
	@Override
	public Folder remove(Serializable primaryKey) throws NoSuchFolderException {
		Session session = null;

		try {
			session = openSession();

			Folder folder = (Folder)session.get(FolderImpl.class, primaryKey);

			if (folder == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFolderException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(folder);
		}
		catch (NoSuchFolderException nsee) {
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
	protected Folder removeImpl(Folder folder) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(folder)) {
				folder = (Folder)session.get(
					FolderImpl.class, folder.getPrimaryKeyObj());
			}

			if (folder != null) {
				session.delete(folder);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (folder != null) {
			clearCache(folder);
		}

		return folder;
	}

	@Override
	public Folder updateImpl(Folder folder) {
		boolean isNew = folder.isNew();

		if (!(folder instanceof FolderModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(folder.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(folder);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in folder proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom Folder implementation " +
					folder.getClass());
		}

		FolderModelImpl folderModelImpl = (FolderModelImpl)folder;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (folder.getCreateDate() == null)) {
			if (serviceContext == null) {
				folder.setCreateDate(now);
			}
			else {
				folder.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!folderModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				folder.setModifiedDate(now);
			}
			else {
				folder.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (folder.isNew()) {
				session.save(folder);

				folder.setNew(false);
			}
			else {
				folder = (Folder)session.merge(folder);
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
			Object[] args = new Object[] {folderModelImpl.getAccountId()};

			finderCache.removeResult(_finderPathCountByAccountId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByAccountId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((folderModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByAccountId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					folderModelImpl.getOriginalAccountId()
				};

				finderCache.removeResult(_finderPathCountByAccountId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAccountId, args);

				args = new Object[] {folderModelImpl.getAccountId()};

				finderCache.removeResult(_finderPathCountByAccountId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByAccountId, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, FolderImpl.class, folder.getPrimaryKey(),
			folder, false);

		clearUniqueFindersCache(folderModelImpl, false);
		cacheUniqueFindersCache(folderModelImpl);

		folder.resetOriginalValues();

		return folder;
	}

	/**
	 * Returns the folder with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the folder
	 * @return the folder
	 * @throws NoSuchFolderException if a folder with the primary key could not be found
	 */
	@Override
	public Folder findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFolderException {

		Folder folder = fetchByPrimaryKey(primaryKey);

		if (folder == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFolderException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return folder;
	}

	/**
	 * Returns the folder with the primary key or throws a <code>NoSuchFolderException</code> if it could not be found.
	 *
	 * @param folderId the primary key of the folder
	 * @return the folder
	 * @throws NoSuchFolderException if a folder with the primary key could not be found
	 */
	@Override
	public Folder findByPrimaryKey(long folderId) throws NoSuchFolderException {
		return findByPrimaryKey((Serializable)folderId);
	}

	/**
	 * Returns the folder with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param folderId the primary key of the folder
	 * @return the folder, or <code>null</code> if a folder with the primary key could not be found
	 */
	@Override
	public Folder fetchByPrimaryKey(long folderId) {
		return fetchByPrimaryKey((Serializable)folderId);
	}

	/**
	 * Returns all the folders.
	 *
	 * @return the folders
	 */
	@Override
	public List<Folder> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of folders
	 * @param end the upper bound of the range of folders (not inclusive)
	 * @return the range of folders
	 */
	@Override
	public List<Folder> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of folders
	 * @param end the upper bound of the range of folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of folders
	 */
	@Override
	public List<Folder> findAll(
		int start, int end, OrderByComparator<Folder> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FolderModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of folders
	 * @param end the upper bound of the range of folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of folders
	 */
	@Override
	public List<Folder> findAll(
		int start, int end, OrderByComparator<Folder> orderByComparator,
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

		List<Folder> list = null;

		if (useFinderCache) {
			list = (List<Folder>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_FOLDER);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_FOLDER;

				sql = sql.concat(FolderModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<Folder>)QueryUtil.list(
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
	 * Removes all the folders from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Folder folder : findAll()) {
			remove(folder);
		}
	}

	/**
	 * Returns the number of folders.
	 *
	 * @return the number of folders
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_FOLDER);

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
		return "folderId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_FOLDER;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return FolderModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the folder persistence.
	 */
	@Activate
	public void activate() {
		FolderModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		FolderModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, FolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, FolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByAccountId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, FolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAccountId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByAccountId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, FolderImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAccountId",
			new String[] {Long.class.getName()},
			FolderModelImpl.ACCOUNTID_COLUMN_BITMASK |
			FolderModelImpl.FULLNAME_COLUMN_BITMASK);

		_finderPathCountByAccountId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAccountId",
			new String[] {Long.class.getName()});

		_finderPathFetchByA_F = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, FolderImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByA_F",
			new String[] {Long.class.getName(), String.class.getName()},
			FolderModelImpl.ACCOUNTID_COLUMN_BITMASK |
			FolderModelImpl.FULLNAME_COLUMN_BITMASK);

		_finderPathCountByA_F = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByA_F",
			new String[] {Long.class.getName(), String.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(FolderImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = MailPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.mail.reader.model.Folder"),
			true);
	}

	@Override
	@Reference(
		target = MailPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = MailPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_FOLDER =
		"SELECT folder FROM Folder folder";

	private static final String _SQL_SELECT_FOLDER_WHERE =
		"SELECT folder FROM Folder folder WHERE ";

	private static final String _SQL_COUNT_FOLDER =
		"SELECT COUNT(folder) FROM Folder folder";

	private static final String _SQL_COUNT_FOLDER_WHERE =
		"SELECT COUNT(folder) FROM Folder folder WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "folder.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No Folder exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No Folder exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		FolderPersistenceImpl.class);

	static {
		try {
			Class.forName(MailPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}