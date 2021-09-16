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
import com.liferay.sharepoint.rest.oauth2.exception.NoSuch2TokenEntryException;
import com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry;
import com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntryTable;
import com.liferay.sharepoint.rest.oauth2.model.impl.SharepointOAuth2TokenEntryImpl;
import com.liferay.sharepoint.rest.oauth2.model.impl.SharepointOAuth2TokenEntryModelImpl;
import com.liferay.sharepoint.rest.oauth2.service.persistence.SharepointOAuth2TokenEntryPersistence;
import com.liferay.sharepoint.rest.oauth2.service.persistence.impl.constants.SharepointOAuthPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

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
@Component(
	service = {
		SharepointOAuth2TokenEntryPersistence.class, BasePersistence.class
	}
)
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharepointOAuth2TokenEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharepointOAuth2TokenEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharepointOAuth2TokenEntryModelImpl</code>.
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

		List<SharepointOAuth2TokenEntry> list = null;

		if (useFinderCache) {
			list = (List<SharepointOAuth2TokenEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry :
						list) {

					if (userId != sharepointOAuth2TokenEntry.getUserId()) {
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

			sb.append(_SQL_SELECT_SHAREPOINTOAUTH2TOKENENTRY_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(SharepointOAuth2TokenEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				list = (List<SharepointOAuth2TokenEntry>)QueryUtil.list(
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

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuch2TokenEntryException(sb.toString());
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

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuch2TokenEntryException(sb.toString());
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
		catch (Exception exception) {
			throw processException(exception);
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

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_SHAREPOINTOAUTH2TOKENENTRY_WHERE);

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
			sb.append(SharepointOAuth2TokenEntryModelImpl.ORDER_BY_JPQL);
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
						sharepointOAuth2TokenEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<SharepointOAuth2TokenEntry> list = query.list();

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

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SHAREPOINTOAUTH2TOKENENTRY_WHERE);

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
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("userId=");
			sb.append(userId);

			sb.append(", configurationPid=");
			sb.append(configurationPid);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuch2TokenEntryException(sb.toString());
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
			result = finderCache.getResult(_finderPathFetchByU_C, finderArgs);
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
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_SHAREPOINTOAUTH2TOKENENTRY_WHERE);

			sb.append(_FINDER_COLUMN_U_C_USERID_2);

			boolean bindConfigurationPid = false;

			if (configurationPid.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_C_CONFIGURATIONPID_3);
			}
			else {
				bindConfigurationPid = true;

				sb.append(_FINDER_COLUMN_U_C_CONFIGURATIONPID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				if (bindConfigurationPid) {
					queryPos.add(configurationPid);
				}

				List<SharepointOAuth2TokenEntry> list = query.list();

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

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SHAREPOINTOAUTH2TOKENENTRY_WHERE);

			sb.append(_FINDER_COLUMN_U_C_USERID_2);

			boolean bindConfigurationPid = false;

			if (configurationPid.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_C_CONFIGURATIONPID_3);
			}
			else {
				bindConfigurationPid = true;

				sb.append(_FINDER_COLUMN_U_C_CONFIGURATIONPID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				if (bindConfigurationPid) {
					queryPos.add(configurationPid);
				}

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
		"sharepointOAuth2TokenEntry.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_C_CONFIGURATIONPID_2 =
		"sharepointOAuth2TokenEntry.configurationPid = ?";

	private static final String _FINDER_COLUMN_U_C_CONFIGURATIONPID_3 =
		"(sharepointOAuth2TokenEntry.configurationPid IS NULL OR sharepointOAuth2TokenEntry.configurationPid = '')";

	public SharepointOAuth2TokenEntryPersistenceImpl() {
		setModelClass(SharepointOAuth2TokenEntry.class);

		setModelImplClass(SharepointOAuth2TokenEntryImpl.class);
		setModelPKClass(long.class);

		setTable(SharepointOAuth2TokenEntryTable.INSTANCE);
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
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the sharepoint o auth2 token entries in the entity cache if it is enabled.
	 *
	 * @param sharepointOAuth2TokenEntries the sharepoint o auth2 token entries
	 */
	@Override
	public void cacheResult(
		List<SharepointOAuth2TokenEntry> sharepointOAuth2TokenEntries) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (sharepointOAuth2TokenEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry :
				sharepointOAuth2TokenEntries) {

			if (entityCache.getResult(
					SharepointOAuth2TokenEntryImpl.class,
					sharepointOAuth2TokenEntry.getPrimaryKey()) == null) {

				cacheResult(sharepointOAuth2TokenEntry);
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

		finderCache.clearCache(SharepointOAuth2TokenEntryImpl.class);
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
			SharepointOAuth2TokenEntryImpl.class, sharepointOAuth2TokenEntry);
	}

	@Override
	public void clearCache(
		List<SharepointOAuth2TokenEntry> sharepointOAuth2TokenEntries) {

		for (SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry :
				sharepointOAuth2TokenEntries) {

			entityCache.removeResult(
				SharepointOAuth2TokenEntryImpl.class,
				sharepointOAuth2TokenEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(SharepointOAuth2TokenEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				SharepointOAuth2TokenEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		SharepointOAuth2TokenEntryModelImpl
			sharepointOAuth2TokenEntryModelImpl) {

		Object[] args = new Object[] {
			sharepointOAuth2TokenEntryModelImpl.getUserId(),
			sharepointOAuth2TokenEntryModelImpl.getConfigurationPid()
		};

		finderCache.putResult(_finderPathCountByU_C, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByU_C, args, sharepointOAuth2TokenEntryModelImpl);
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

		sharepointOAuth2TokenEntry.setCompanyId(
			CompanyThreadLocal.getCompanyId());

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
		catch (NoSuch2TokenEntryException noSuchEntityException) {
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
		catch (Exception exception) {
			throw processException(exception);
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

		if (isNew && (sharepointOAuth2TokenEntry.getCreateDate() == null)) {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			Date date = new Date();

			if (serviceContext == null) {
				sharepointOAuth2TokenEntry.setCreateDate(date);
			}
			else {
				sharepointOAuth2TokenEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(sharepointOAuth2TokenEntry);
			}
			else {
				sharepointOAuth2TokenEntry =
					(SharepointOAuth2TokenEntry)session.merge(
						sharepointOAuth2TokenEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			SharepointOAuth2TokenEntryImpl.class,
			sharepointOAuth2TokenEntryModelImpl, false, true);

		cacheUniqueFindersCache(sharepointOAuth2TokenEntryModelImpl);

		if (isNew) {
			sharepointOAuth2TokenEntry.setNew(false);
		}

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharepointOAuth2TokenEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharepointOAuth2TokenEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SharepointOAuth2TokenEntryModelImpl</code>.
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

		List<SharepointOAuth2TokenEntry> list = null;

		if (useFinderCache) {
			list = (List<SharepointOAuth2TokenEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_SHAREPOINTOAUTH2TOKENENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_SHAREPOINTOAUTH2TOKENENTRY;

				sql = sql.concat(
					SharepointOAuth2TokenEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<SharepointOAuth2TokenEntry>)QueryUtil.list(
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
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_SHAREPOINTOAUTH2TOKENENTRY);

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

		_finderPathFetchByU_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByU_C",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"userId", "configurationPid"}, true);

		_finderPathCountByU_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_C",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"userId", "configurationPid"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(SharepointOAuth2TokenEntryImpl.class.getName());
	}

	@Override
	@Reference(
		target = SharepointOAuthPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = SharepointOAuthPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = SharepointOAuthPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
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

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private SharepointOAuth2TokenEntryModelArgumentsResolver
		_sharepointOAuth2TokenEntryModelArgumentsResolver;

}