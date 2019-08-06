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

package com.liferay.sharepoint.rest.oauth2.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.sharepoint.rest.oauth2.exception.NoSuch2TokenEntryException;
import com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry;
import com.liferay.sharepoint.rest.oauth2.model.impl.SharepointOAuth2TokenEntryImpl;
import com.liferay.sharepoint.rest.oauth2.model.impl.SharepointOAuth2TokenEntryModelImpl;
import com.liferay.sharepoint.rest.oauth2.service.persistence.SharepointOAuth2TokenEntryPersistence;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence implementation for the sharepoint o auth2 token entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Adolfo Pérez
 * @generated
 */
@ProviderType
public class SharepointOAuth2TokenEntryPersistenceImpl
	extends BasePersistenceImpl<SharepointOAuth2TokenEntry>
	implements SharepointOAuth2TokenEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>SharepointOAuth2TokenEntryUtil</code> to access the sharepoint o auth2 token entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		SharepointOAuth2TokenEntryImpl.class.getName();

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
	 * Returns all the sharepoint o auth2 token entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching sharepoint o auth2 token entries
	 */
	@Override
	public List<SharepointOAuth2TokenEntry> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sharepoint o auth2 token entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SharepointOAuth2TokenEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of sharepoint o auth2 token entries
	 * @param end the upper bound of the range of sharepoint o auth2 token entries (not inclusive)
	 * @return the range of matching sharepoint o auth2 token entries
	 */
	@Override
	public List<SharepointOAuth2TokenEntry> findByUserId(
		long userId, int start, int end) {

		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the sharepoint o auth2 token entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SharepointOAuth2TokenEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of sharepoint o auth2 token entries
	 * @param end the upper bound of the range of sharepoint o auth2 token entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sharepoint o auth2 token entries
	 */
	@Override
	public List<SharepointOAuth2TokenEntry> findByUserId(
		long userId, int start, int end,
		OrderByComparator<SharepointOAuth2TokenEntry> orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sharepoint o auth2 token entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SharepointOAuth2TokenEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of sharepoint o auth2 token entries
	 * @param end the upper bound of the range of sharepoint o auth2 token entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sharepoint o auth2 token entries
	 */
	@Override
	public List<SharepointOAuth2TokenEntry> findByUserId(
		long userId, int start, int end,
		OrderByComparator<SharepointOAuth2TokenEntry> orderByComparator,
		boolean useFinderCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUserId;
				finderArgs = new Object[] {userId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUserId;
			finderArgs = new Object[] {userId, start, end, orderByComparator};
		}

		List<SharepointOAuth2TokenEntry> list = null;

		if (useFinderCache) {
			list = (List<SharepointOAuth2TokenEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry :
						list) {

					if ((userId != sharepointOAuth2TokenEntry.getUserId())) {
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

			query.append(_SQL_SELECT_SHAREPOINTOAUTH2TOKENENTRY_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(SharepointOAuth2TokenEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (!pagination) {
					list = (List<SharepointOAuth2TokenEntry>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SharepointOAuth2TokenEntry>)QueryUtil.list(
						q, getDialect(), start, end);
				}

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
	 * Returns the first sharepoint o auth2 token entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharepoint o auth2 token entry
	 * @throws NoSuch2TokenEntryException if a matching sharepoint o auth2 token entry could not be found
	 */
	@Override
	public SharepointOAuth2TokenEntry findByUserId_First(
			long userId,
			OrderByComparator<SharepointOAuth2TokenEntry> orderByComparator)
		throws NoSuch2TokenEntryException {

		SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry =
			fetchByUserId_First(userId, orderByComparator);

		if (sharepointOAuth2TokenEntry != null) {
			return sharepointOAuth2TokenEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append("}");

		throw new NoSuch2TokenEntryException(msg.toString());
	}

	/**
	 * Returns the first sharepoint o auth2 token entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharepoint o auth2 token entry, or <code>null</code> if a matching sharepoint o auth2 token entry could not be found
	 */
	@Override
	public SharepointOAuth2TokenEntry fetchByUserId_First(
		long userId,
		OrderByComparator<SharepointOAuth2TokenEntry> orderByComparator) {

		List<SharepointOAuth2TokenEntry> list = findByUserId(
			userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last sharepoint o auth2 token entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharepoint o auth2 token entry
	 * @throws NoSuch2TokenEntryException if a matching sharepoint o auth2 token entry could not be found
	 */
	@Override
	public SharepointOAuth2TokenEntry findByUserId_Last(
			long userId,
			OrderByComparator<SharepointOAuth2TokenEntry> orderByComparator)
		throws NoSuch2TokenEntryException {

		SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry =
			fetchByUserId_Last(userId, orderByComparator);

		if (sharepointOAuth2TokenEntry != null) {
			return sharepointOAuth2TokenEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append("}");

		throw new NoSuch2TokenEntryException(msg.toString());
	}

	/**
	 * Returns the last sharepoint o auth2 token entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching sharepoint o auth2 token entry, or <code>null</code> if a matching sharepoint o auth2 token entry could not be found
	 */
	@Override
	public SharepointOAuth2TokenEntry fetchByUserId_Last(
		long userId,
		OrderByComparator<SharepointOAuth2TokenEntry> orderByComparator) {

		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<SharepointOAuth2TokenEntry> list = findByUserId(
			userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the sharepoint o auth2 token entries before and after the current sharepoint o auth2 token entry in the ordered set where userId = &#63;.
	 *
	 * @param sharepointOAuth2TokenEntryId the primary key of the current sharepoint o auth2 token entry
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next sharepoint o auth2 token entry
	 * @throws NoSuch2TokenEntryException if a sharepoint o auth2 token entry with the primary key could not be found
	 */
	@Override
	public SharepointOAuth2TokenEntry[] findByUserId_PrevAndNext(
			long sharepointOAuth2TokenEntryId, long userId,
			OrderByComparator<SharepointOAuth2TokenEntry> orderByComparator)
		throws NoSuch2TokenEntryException {

		SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry =
			findByPrimaryKey(sharepointOAuth2TokenEntryId);

		Session session = null;

		try {
			session = openSession();

			SharepointOAuth2TokenEntry[] array =
				new SharepointOAuth2TokenEntryImpl[3];

			array[0] = getByUserId_PrevAndNext(
				session, sharepointOAuth2TokenEntry, userId, orderByComparator,
				true);

			array[1] = sharepointOAuth2TokenEntry;

			array[2] = getByUserId_PrevAndNext(
				session, sharepointOAuth2TokenEntry, userId, orderByComparator,
				false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SharepointOAuth2TokenEntry getByUserId_PrevAndNext(
		Session session, SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry,
		long userId,
		OrderByComparator<SharepointOAuth2TokenEntry> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SHAREPOINTOAUTH2TOKENENTRY_WHERE);

		query.append(_FINDER_COLUMN_USERID_USERID_2);

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
			query.append(SharepointOAuth2TokenEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						sharepointOAuth2TokenEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SharepointOAuth2TokenEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the sharepoint o auth2 token entries where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry :
				findByUserId(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(sharepointOAuth2TokenEntry);
		}
	}

	/**
	 * Returns the number of sharepoint o auth2 token entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching sharepoint o auth2 token entries
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = _finderPathCountByUserId;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SHAREPOINTOAUTH2TOKENENTRY_WHERE);

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
		"sharepointOAuth2TokenEntry.userId = ?";

	private FinderPath _finderPathFetchByU_C;
	private FinderPath _finderPathCountByU_C;

	/**
	 * Returns the sharepoint o auth2 token entry where userId = &#63; and configurationPid = &#63; or throws a <code>NoSuch2TokenEntryException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param configurationPid the configuration pid
	 * @return the matching sharepoint o auth2 token entry
	 * @throws NoSuch2TokenEntryException if a matching sharepoint o auth2 token entry could not be found
	 */
	@Override
	public SharepointOAuth2TokenEntry findByU_C(
			long userId, String configurationPid)
		throws NoSuch2TokenEntryException {

		SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry = fetchByU_C(
			userId, configurationPid);

		if (sharepointOAuth2TokenEntry == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", configurationPid=");
			msg.append(configurationPid);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuch2TokenEntryException(msg.toString());
		}

		return sharepointOAuth2TokenEntry;
	}

	/**
	 * Returns the sharepoint o auth2 token entry where userId = &#63; and configurationPid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param configurationPid the configuration pid
	 * @return the matching sharepoint o auth2 token entry, or <code>null</code> if a matching sharepoint o auth2 token entry could not be found
	 */
	@Override
	public SharepointOAuth2TokenEntry fetchByU_C(
		long userId, String configurationPid) {

		return fetchByU_C(userId, configurationPid, true);
	}

	/**
	 * Returns the sharepoint o auth2 token entry where userId = &#63; and configurationPid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param configurationPid the configuration pid
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching sharepoint o auth2 token entry, or <code>null</code> if a matching sharepoint o auth2 token entry could not be found
	 */
	@Override
	public SharepointOAuth2TokenEntry fetchByU_C(
		long userId, String configurationPid, boolean useFinderCache) {

		configurationPid = Objects.toString(configurationPid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {userId, configurationPid};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByU_C, finderArgs, this);
		}

		if (result instanceof SharepointOAuth2TokenEntry) {
			SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry =
				(SharepointOAuth2TokenEntry)result;

			if ((userId != sharepointOAuth2TokenEntry.getUserId()) ||
				!Objects.equals(
					configurationPid,
					sharepointOAuth2TokenEntry.getConfigurationPid())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SHAREPOINTOAUTH2TOKENENTRY_WHERE);

			query.append(_FINDER_COLUMN_U_C_USERID_2);

			boolean bindConfigurationPid = false;

			if (configurationPid.isEmpty()) {
				query.append(_FINDER_COLUMN_U_C_CONFIGURATIONPID_3);
			}
			else {
				bindConfigurationPid = true;

				query.append(_FINDER_COLUMN_U_C_CONFIGURATIONPID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (bindConfigurationPid) {
					qPos.add(configurationPid);
				}

				List<SharepointOAuth2TokenEntry> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByU_C, finderArgs, list);
					}
				}
				else {
					SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry =
						list.get(0);

					result = sharepointOAuth2TokenEntry;

					cacheResult(sharepointOAuth2TokenEntry);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(_finderPathFetchByU_C, finderArgs);
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
			return (SharepointOAuth2TokenEntry)result;
		}
	}

	/**
	 * Removes the sharepoint o auth2 token entry where userId = &#63; and configurationPid = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param configurationPid the configuration pid
	 * @return the sharepoint o auth2 token entry that was removed
	 */
	@Override
	public SharepointOAuth2TokenEntry removeByU_C(
			long userId, String configurationPid)
		throws NoSuch2TokenEntryException {

		SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry = findByU_C(
			userId, configurationPid);

		return remove(sharepointOAuth2TokenEntry);
	}

	/**
	 * Returns the number of sharepoint o auth2 token entries where userId = &#63; and configurationPid = &#63;.
	 *
	 * @param userId the user ID
	 * @param configurationPid the configuration pid
	 * @return the number of matching sharepoint o auth2 token entries
	 */
	@Override
	public int countByU_C(long userId, String configurationPid) {
		configurationPid = Objects.toString(configurationPid, "");

		FinderPath finderPath = _finderPathCountByU_C;

		Object[] finderArgs = new Object[] {userId, configurationPid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SHAREPOINTOAUTH2TOKENENTRY_WHERE);

			query.append(_FINDER_COLUMN_U_C_USERID_2);

			boolean bindConfigurationPid = false;

			if (configurationPid.isEmpty()) {
				query.append(_FINDER_COLUMN_U_C_CONFIGURATIONPID_3);
			}
			else {
				bindConfigurationPid = true;

				query.append(_FINDER_COLUMN_U_C_CONFIGURATIONPID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (bindConfigurationPid) {
					qPos.add(configurationPid);
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

	private static final String _FINDER_COLUMN_U_C_USERID_2 =
		"sharepointOAuth2TokenEntry.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_C_CONFIGURATIONPID_2 =
		"sharepointOAuth2TokenEntry.configurationPid = ?";

	private static final String _FINDER_COLUMN_U_C_CONFIGURATIONPID_3 =
		"(sharepointOAuth2TokenEntry.configurationPid IS NULL OR sharepointOAuth2TokenEntry.configurationPid = '')";

	public SharepointOAuth2TokenEntryPersistenceImpl() {
		setModelClass(SharepointOAuth2TokenEntry.class);

		setModelImplClass(SharepointOAuth2TokenEntryImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(
			SharepointOAuth2TokenEntryModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the sharepoint o auth2 token entry in the entity cache if it is enabled.
	 *
	 * @param sharepointOAuth2TokenEntry the sharepoint o auth2 token entry
	 */
	@Override
	public void cacheResult(
		SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry) {

		entityCache.putResult(
			SharepointOAuth2TokenEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharepointOAuth2TokenEntryImpl.class,
			sharepointOAuth2TokenEntry.getPrimaryKey(),
			sharepointOAuth2TokenEntry);

		finderCache.putResult(
			_finderPathFetchByU_C,
			new Object[] {
				sharepointOAuth2TokenEntry.getUserId(),
				sharepointOAuth2TokenEntry.getConfigurationPid()
			},
			sharepointOAuth2TokenEntry);

		sharepointOAuth2TokenEntry.resetOriginalValues();
	}

	/**
	 * Caches the sharepoint o auth2 token entries in the entity cache if it is enabled.
	 *
	 * @param sharepointOAuth2TokenEntries the sharepoint o auth2 token entries
	 */
	@Override
	public void cacheResult(
		List<SharepointOAuth2TokenEntry> sharepointOAuth2TokenEntries) {

		for (SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry :
				sharepointOAuth2TokenEntries) {

			if (entityCache.getResult(
					SharepointOAuth2TokenEntryModelImpl.ENTITY_CACHE_ENABLED,
					SharepointOAuth2TokenEntryImpl.class,
					sharepointOAuth2TokenEntry.getPrimaryKey()) == null) {

				cacheResult(sharepointOAuth2TokenEntry);
			}
			else {
				sharepointOAuth2TokenEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all sharepoint o auth2 token entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SharepointOAuth2TokenEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the sharepoint o auth2 token entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry) {

		entityCache.removeResult(
			SharepointOAuth2TokenEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharepointOAuth2TokenEntryImpl.class,
			sharepointOAuth2TokenEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(SharepointOAuth2TokenEntryModelImpl)sharepointOAuth2TokenEntry,
			true);
	}

	@Override
	public void clearCache(
		List<SharepointOAuth2TokenEntry> sharepointOAuth2TokenEntries) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry :
				sharepointOAuth2TokenEntries) {

			entityCache.removeResult(
				SharepointOAuth2TokenEntryModelImpl.ENTITY_CACHE_ENABLED,
				SharepointOAuth2TokenEntryImpl.class,
				sharepointOAuth2TokenEntry.getPrimaryKey());

			clearUniqueFindersCache(
				(SharepointOAuth2TokenEntryModelImpl)sharepointOAuth2TokenEntry,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		SharepointOAuth2TokenEntryModelImpl
			sharepointOAuth2TokenEntryModelImpl) {

		Object[] args = new Object[] {
			sharepointOAuth2TokenEntryModelImpl.getUserId(),
			sharepointOAuth2TokenEntryModelImpl.getConfigurationPid()
		};

		finderCache.putResult(
			_finderPathCountByU_C, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByU_C, args, sharepointOAuth2TokenEntryModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		SharepointOAuth2TokenEntryModelImpl sharepointOAuth2TokenEntryModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				sharepointOAuth2TokenEntryModelImpl.getUserId(),
				sharepointOAuth2TokenEntryModelImpl.getConfigurationPid()
			};

			finderCache.removeResult(_finderPathCountByU_C, args);
			finderCache.removeResult(_finderPathFetchByU_C, args);
		}

		if ((sharepointOAuth2TokenEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByU_C.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				sharepointOAuth2TokenEntryModelImpl.getOriginalUserId(),
				sharepointOAuth2TokenEntryModelImpl.
					getOriginalConfigurationPid()
			};

			finderCache.removeResult(_finderPathCountByU_C, args);
			finderCache.removeResult(_finderPathFetchByU_C, args);
		}
	}

	/**
	 * Creates a new sharepoint o auth2 token entry with the primary key. Does not add the sharepoint o auth2 token entry to the database.
	 *
	 * @param sharepointOAuth2TokenEntryId the primary key for the new sharepoint o auth2 token entry
	 * @return the new sharepoint o auth2 token entry
	 */
	@Override
	public SharepointOAuth2TokenEntry create(
		long sharepointOAuth2TokenEntryId) {

		SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry =
			new SharepointOAuth2TokenEntryImpl();

		sharepointOAuth2TokenEntry.setNew(true);
		sharepointOAuth2TokenEntry.setPrimaryKey(sharepointOAuth2TokenEntryId);

		return sharepointOAuth2TokenEntry;
	}

	/**
	 * Removes the sharepoint o auth2 token entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param sharepointOAuth2TokenEntryId the primary key of the sharepoint o auth2 token entry
	 * @return the sharepoint o auth2 token entry that was removed
	 * @throws NoSuch2TokenEntryException if a sharepoint o auth2 token entry with the primary key could not be found
	 */
	@Override
	public SharepointOAuth2TokenEntry remove(long sharepointOAuth2TokenEntryId)
		throws NoSuch2TokenEntryException {

		return remove((Serializable)sharepointOAuth2TokenEntryId);
	}

	/**
	 * Removes the sharepoint o auth2 token entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the sharepoint o auth2 token entry
	 * @return the sharepoint o auth2 token entry that was removed
	 * @throws NoSuch2TokenEntryException if a sharepoint o auth2 token entry with the primary key could not be found
	 */
	@Override
	public SharepointOAuth2TokenEntry remove(Serializable primaryKey)
		throws NoSuch2TokenEntryException {

		Session session = null;

		try {
			session = openSession();

			SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry =
				(SharepointOAuth2TokenEntry)session.get(
					SharepointOAuth2TokenEntryImpl.class, primaryKey);

			if (sharepointOAuth2TokenEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuch2TokenEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(sharepointOAuth2TokenEntry);
		}
		catch (NoSuch2TokenEntryException nsee) {
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
	protected SharepointOAuth2TokenEntry removeImpl(
		SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(sharepointOAuth2TokenEntry)) {
				sharepointOAuth2TokenEntry =
					(SharepointOAuth2TokenEntry)session.get(
						SharepointOAuth2TokenEntryImpl.class,
						sharepointOAuth2TokenEntry.getPrimaryKeyObj());
			}

			if (sharepointOAuth2TokenEntry != null) {
				session.delete(sharepointOAuth2TokenEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (sharepointOAuth2TokenEntry != null) {
			clearCache(sharepointOAuth2TokenEntry);
		}

		return sharepointOAuth2TokenEntry;
	}

	@Override
	public SharepointOAuth2TokenEntry updateImpl(
		SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry) {

		boolean isNew = sharepointOAuth2TokenEntry.isNew();

		if (!(sharepointOAuth2TokenEntry instanceof
				SharepointOAuth2TokenEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(sharepointOAuth2TokenEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					sharepointOAuth2TokenEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in sharepointOAuth2TokenEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SharepointOAuth2TokenEntry implementation " +
					sharepointOAuth2TokenEntry.getClass());
		}

		SharepointOAuth2TokenEntryModelImpl
			sharepointOAuth2TokenEntryModelImpl =
				(SharepointOAuth2TokenEntryModelImpl)sharepointOAuth2TokenEntry;

		Session session = null;

		try {
			session = openSession();

			if (sharepointOAuth2TokenEntry.isNew()) {
				session.save(sharepointOAuth2TokenEntry);

				sharepointOAuth2TokenEntry.setNew(false);
			}
			else {
				sharepointOAuth2TokenEntry =
					(SharepointOAuth2TokenEntry)session.merge(
						sharepointOAuth2TokenEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!SharepointOAuth2TokenEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				sharepointOAuth2TokenEntryModelImpl.getUserId()
			};

			finderCache.removeResult(_finderPathCountByUserId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUserId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((sharepointOAuth2TokenEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUserId.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					sharepointOAuth2TokenEntryModelImpl.getOriginalUserId()
				};

				finderCache.removeResult(_finderPathCountByUserId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserId, args);

				args = new Object[] {
					sharepointOAuth2TokenEntryModelImpl.getUserId()
				};

				finderCache.removeResult(_finderPathCountByUserId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUserId, args);
			}
		}

		entityCache.putResult(
			SharepointOAuth2TokenEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharepointOAuth2TokenEntryImpl.class,
			sharepointOAuth2TokenEntry.getPrimaryKey(),
			sharepointOAuth2TokenEntry, false);

		clearUniqueFindersCache(sharepointOAuth2TokenEntryModelImpl, false);
		cacheUniqueFindersCache(sharepointOAuth2TokenEntryModelImpl);

		sharepointOAuth2TokenEntry.resetOriginalValues();

		return sharepointOAuth2TokenEntry;
	}

	/**
	 * Returns the sharepoint o auth2 token entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the sharepoint o auth2 token entry
	 * @return the sharepoint o auth2 token entry
	 * @throws NoSuch2TokenEntryException if a sharepoint o auth2 token entry with the primary key could not be found
	 */
	@Override
	public SharepointOAuth2TokenEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuch2TokenEntryException {

		SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry =
			fetchByPrimaryKey(primaryKey);

		if (sharepointOAuth2TokenEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuch2TokenEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return sharepointOAuth2TokenEntry;
	}

	/**
	 * Returns the sharepoint o auth2 token entry with the primary key or throws a <code>NoSuch2TokenEntryException</code> if it could not be found.
	 *
	 * @param sharepointOAuth2TokenEntryId the primary key of the sharepoint o auth2 token entry
	 * @return the sharepoint o auth2 token entry
	 * @throws NoSuch2TokenEntryException if a sharepoint o auth2 token entry with the primary key could not be found
	 */
	@Override
	public SharepointOAuth2TokenEntry findByPrimaryKey(
			long sharepointOAuth2TokenEntryId)
		throws NoSuch2TokenEntryException {

		return findByPrimaryKey((Serializable)sharepointOAuth2TokenEntryId);
	}

	/**
	 * Returns the sharepoint o auth2 token entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param sharepointOAuth2TokenEntryId the primary key of the sharepoint o auth2 token entry
	 * @return the sharepoint o auth2 token entry, or <code>null</code> if a sharepoint o auth2 token entry with the primary key could not be found
	 */
	@Override
	public SharepointOAuth2TokenEntry fetchByPrimaryKey(
		long sharepointOAuth2TokenEntryId) {

		return fetchByPrimaryKey((Serializable)sharepointOAuth2TokenEntryId);
	}

	/**
	 * Returns all the sharepoint o auth2 token entries.
	 *
	 * @return the sharepoint o auth2 token entries
	 */
	@Override
	public List<SharepointOAuth2TokenEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the sharepoint o auth2 token entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SharepointOAuth2TokenEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of sharepoint o auth2 token entries
	 * @param end the upper bound of the range of sharepoint o auth2 token entries (not inclusive)
	 * @return the range of sharepoint o auth2 token entries
	 */
	@Override
	public List<SharepointOAuth2TokenEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the sharepoint o auth2 token entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SharepointOAuth2TokenEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of sharepoint o auth2 token entries
	 * @param end the upper bound of the range of sharepoint o auth2 token entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of sharepoint o auth2 token entries
	 */
	@Override
	public List<SharepointOAuth2TokenEntry> findAll(
		int start, int end,
		OrderByComparator<SharepointOAuth2TokenEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the sharepoint o auth2 token entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>SharepointOAuth2TokenEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of sharepoint o auth2 token entries
	 * @param end the upper bound of the range of sharepoint o auth2 token entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of sharepoint o auth2 token entries
	 */
	@Override
	public List<SharepointOAuth2TokenEntry> findAll(
		int start, int end,
		OrderByComparator<SharepointOAuth2TokenEntry> orderByComparator,
		boolean useFinderCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<SharepointOAuth2TokenEntry> list = null;

		if (useFinderCache) {
			list = (List<SharepointOAuth2TokenEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_SHAREPOINTOAUTH2TOKENENTRY);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SHAREPOINTOAUTH2TOKENENTRY;

				if (pagination) {
					sql = sql.concat(
						SharepointOAuth2TokenEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<SharepointOAuth2TokenEntry>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SharepointOAuth2TokenEntry>)QueryUtil.list(
						q, getDialect(), start, end);
				}

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
	 * Removes all the sharepoint o auth2 token entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry :
				findAll()) {

			remove(sharepointOAuth2TokenEntry);
		}
	}

	/**
	 * Returns the number of sharepoint o auth2 token entries.
	 *
	 * @return the number of sharepoint o auth2 token entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
					_SQL_COUNT_SHAREPOINTOAUTH2TOKENENTRY);

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
		return "sharepointOAuth2TokenEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SHAREPOINTOAUTH2TOKENENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return SharepointOAuth2TokenEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the sharepoint o auth2 token entry persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			SharepointOAuth2TokenEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharepointOAuth2TokenEntryModelImpl.FINDER_CACHE_ENABLED,
			SharepointOAuth2TokenEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			SharepointOAuth2TokenEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharepointOAuth2TokenEntryModelImpl.FINDER_CACHE_ENABLED,
			SharepointOAuth2TokenEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			SharepointOAuth2TokenEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharepointOAuth2TokenEntryModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUserId = new FinderPath(
			SharepointOAuth2TokenEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharepointOAuth2TokenEntryModelImpl.FINDER_CACHE_ENABLED,
			SharepointOAuth2TokenEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUserId = new FinderPath(
			SharepointOAuth2TokenEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharepointOAuth2TokenEntryModelImpl.FINDER_CACHE_ENABLED,
			SharepointOAuth2TokenEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] {Long.class.getName()},
			SharepointOAuth2TokenEntryModelImpl.USERID_COLUMN_BITMASK);

		_finderPathCountByUserId = new FinderPath(
			SharepointOAuth2TokenEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharepointOAuth2TokenEntryModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUserId", new String[] {Long.class.getName()});

		_finderPathFetchByU_C = new FinderPath(
			SharepointOAuth2TokenEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharepointOAuth2TokenEntryModelImpl.FINDER_CACHE_ENABLED,
			SharepointOAuth2TokenEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByU_C",
			new String[] {Long.class.getName(), String.class.getName()},
			SharepointOAuth2TokenEntryModelImpl.USERID_COLUMN_BITMASK |
			SharepointOAuth2TokenEntryModelImpl.
				CONFIGURATIONPID_COLUMN_BITMASK);

		_finderPathCountByU_C = new FinderPath(
			SharepointOAuth2TokenEntryModelImpl.ENTITY_CACHE_ENABLED,
			SharepointOAuth2TokenEntryModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_C",
			new String[] {Long.class.getName(), String.class.getName()});
	}

	public void destroy() {
		entityCache.removeCache(SharepointOAuth2TokenEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_SHAREPOINTOAUTH2TOKENENTRY =
		"SELECT sharepointOAuth2TokenEntry FROM SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry";

	private static final String _SQL_SELECT_SHAREPOINTOAUTH2TOKENENTRY_WHERE =
		"SELECT sharepointOAuth2TokenEntry FROM SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry WHERE ";

	private static final String _SQL_COUNT_SHAREPOINTOAUTH2TOKENENTRY =
		"SELECT COUNT(sharepointOAuth2TokenEntry) FROM SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry";

	private static final String _SQL_COUNT_SHAREPOINTOAUTH2TOKENENTRY_WHERE =
		"SELECT COUNT(sharepointOAuth2TokenEntry) FROM SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"sharepointOAuth2TokenEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No SharepointOAuth2TokenEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No SharepointOAuth2TokenEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		SharepointOAuth2TokenEntryPersistenceImpl.class);

}