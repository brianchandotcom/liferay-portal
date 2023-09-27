/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.DuplicateUserExternalReferenceCodeException;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.GroupPersistence;
import com.liferay.portal.kernel.service.persistence.OrganizationPersistence;
import com.liferay.portal.kernel.service.persistence.RolePersistence;
import com.liferay.portal.kernel.service.persistence.TeamPersistence;
import com.liferay.portal.kernel.service.persistence.UserGroupPersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.service.persistence.UserUtil;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelperUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.TableMapper;
import com.liferay.portal.kernel.service.persistence.impl.TableMapperFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.model.impl.UserModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the user service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class UserPersistenceImpl
	extends BasePersistenceImpl<User> implements UserPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>UserUtil</code> to access the user persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		UserImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the users where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching users
	 */
	@Override
	public List<User> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the users where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of matching users
	 */
	@Override
	public List<User> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the users where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<User> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the users where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<User> orderByComparator, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<User> list = null;

		if (useFinderCache && productionMode) {
			list = (List<User>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (User user : list) {
					if (!uuid.equals(user.getUuid())) {
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

			sb.append(_SQL_SELECT_USER_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(UserModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				list = (List<User>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first user in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByUuid_First(
			String uuid, OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {

		User user = fetchByUuid_First(uuid, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchUserException(sb.toString());
	}

	/**
	 * Returns the first user in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByUuid_First(
		String uuid, OrderByComparator<User> orderByComparator) {

		List<User> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByUuid_Last(
			String uuid, OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {

		User user = fetchByUuid_Last(uuid, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchUserException(sb.toString());
	}

	/**
	 * Returns the last user in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByUuid_Last(
		String uuid, OrderByComparator<User> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<User> list = findByUuid(uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the users before and after the current user in the ordered set where uuid = &#63;.
	 *
	 * @param userId the primary key of the current user
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user
	 * @throws NoSuchUserException if a user with the primary key could not be found
	 */
	@Override
	public User[] findByUuid_PrevAndNext(
			long userId, String uuid, OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {

		uuid = Objects.toString(uuid, "");

		User user = findByPrimaryKey(userId);

		Session session = null;

		try {
			session = openSession();

			User[] array = new UserImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, user, uuid, orderByComparator, true);

			array[1] = user;

			array[2] = getByUuid_PrevAndNext(
				session, user, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected User getByUuid_PrevAndNext(
		Session session, User user, String uuid,
		OrderByComparator<User> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_USER_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2);
		}

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
			sb.append(UserModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(user)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<User> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the users where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (User user :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(user);
		}
	}

	/**
	 * Returns the number of users where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching users
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid;

			finderArgs = new Object[] {uuid};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_USER_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_UUID_UUID_2 = "user.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(user.uuid IS NULL OR user.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the users where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching users
	 */
	@Override
	public List<User> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the users where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of matching users
	 */
	@Override
	public List<User> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the users where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<User> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the users where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<User> orderByComparator, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<User> list = null;

		if (useFinderCache && productionMode) {
			list = (List<User>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (User user : list) {
					if (!uuid.equals(user.getUuid()) ||
						(companyId != user.getCompanyId())) {

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
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_USER_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(UserModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				list = (List<User>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first user in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {

		User user = fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchUserException(sb.toString());
	}

	/**
	 * Returns the first user in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<User> orderByComparator) {

		List<User> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {

		User user = fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchUserException(sb.toString());
	}

	/**
	 * Returns the last user in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<User> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<User> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the users before and after the current user in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param userId the primary key of the current user
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user
	 * @throws NoSuchUserException if a user with the primary key could not be found
	 */
	@Override
	public User[] findByUuid_C_PrevAndNext(
			long userId, String uuid, long companyId,
			OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {

		uuid = Objects.toString(uuid, "");

		User user = findByPrimaryKey(userId);

		Session session = null;

		try {
			session = openSession();

			User[] array = new UserImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, user, uuid, companyId, orderByComparator, true);

			array[1] = user;

			array[2] = getByUuid_C_PrevAndNext(
				session, user, uuid, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected User getByUuid_C_PrevAndNext(
		Session session, User user, String uuid, long companyId,
		OrderByComparator<User> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_USER_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

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
			sb.append(UserModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(user)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<User> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the users where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (User user :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(user);
		}
	}

	/**
	 * Returns the number of users where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching users
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid_C;

			finderArgs = new Object[] {uuid, companyId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_USER_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"user.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(user.uuid IS NULL OR user.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"user.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the users where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching users
	 */
	@Override
	public List<User> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the users where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of matching users
	 */
	@Override
	public List<User> findByCompanyId(long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the users where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<User> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the users where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<User> orderByComparator, boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByCompanyId;
				finderArgs = new Object[] {companyId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByCompanyId;
			finderArgs = new Object[] {
				companyId, start, end, orderByComparator
			};
		}

		List<User> list = null;

		if (useFinderCache && productionMode) {
			list = (List<User>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (User user : list) {
					if (companyId != user.getCompanyId()) {
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

			sb.append(_SQL_SELECT_USER_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(UserModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<User>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first user in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByCompanyId_First(
			long companyId, OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {

		User user = fetchByCompanyId_First(companyId, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchUserException(sb.toString());
	}

	/**
	 * Returns the first user in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByCompanyId_First(
		long companyId, OrderByComparator<User> orderByComparator) {

		List<User> list = findByCompanyId(companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByCompanyId_Last(
			long companyId, OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {

		User user = fetchByCompanyId_Last(companyId, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchUserException(sb.toString());
	}

	/**
	 * Returns the last user in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByCompanyId_Last(
		long companyId, OrderByComparator<User> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<User> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the users before and after the current user in the ordered set where companyId = &#63;.
	 *
	 * @param userId the primary key of the current user
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user
	 * @throws NoSuchUserException if a user with the primary key could not be found
	 */
	@Override
	public User[] findByCompanyId_PrevAndNext(
			long userId, long companyId,
			OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {

		User user = findByPrimaryKey(userId);

		Session session = null;

		try {
			session = openSession();

			User[] array = new UserImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, user, companyId, orderByComparator, true);

			array[1] = user;

			array[2] = getByCompanyId_PrevAndNext(
				session, user, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected User getByCompanyId_PrevAndNext(
		Session session, User user, long companyId,
		OrderByComparator<User> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_USER_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			sb.append(UserModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(user)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<User> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the users where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (User user :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(user);
		}
	}

	/**
	 * Returns the number of users where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching users
	 */
	@Override
	public int countByCompanyId(long companyId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCompanyId;

			finderArgs = new Object[] {companyId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_USER_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"user.companyId = ? AND type_ = 1";

	private FinderPath _finderPathFetchByContactId;
	private FinderPath _finderPathCountByContactId;

	/**
	 * Returns the user where contactId = &#63; or throws a <code>NoSuchUserException</code> if it could not be found.
	 *
	 * @param contactId the contact ID
	 * @return the matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByContactId(long contactId) throws NoSuchUserException {
		User user = fetchByContactId(contactId);

		if (user == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("contactId=");
			sb.append(contactId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchUserException(sb.toString());
		}

		return user;
	}

	/**
	 * Returns the user where contactId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param contactId the contact ID
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByContactId(long contactId) {
		return fetchByContactId(contactId, true);
	}

	/**
	 * Returns the user where contactId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param contactId the contact ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByContactId(long contactId, boolean useFinderCache) {
		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {contactId};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByContactId, finderArgs, this);
		}

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		if (result instanceof User) {
			User user = (User)result;

			if (contactId != user.getContactId()) {
				result = null;
			}
			else if (!CTPersistenceHelperUtil.isProductionMode(
						User.class, user.getPrimaryKey())) {

				result = null;
			}
		}
		else if (!productionMode && (result instanceof List<?>)) {
			result = null;
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_USER_WHERE);

			sb.append(_FINDER_COLUMN_CONTACTID_CONTACTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(contactId);

				List<User> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						FinderCacheUtil.putResult(
							_finderPathFetchByContactId, finderArgs, list);
					}
				}
				else {
					User user = list.get(0);

					result = user;

					cacheResult(user);
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
			return (User)result;
		}
	}

	/**
	 * Removes the user where contactId = &#63; from the database.
	 *
	 * @param contactId the contact ID
	 * @return the user that was removed
	 */
	@Override
	public User removeByContactId(long contactId) throws NoSuchUserException {
		User user = findByContactId(contactId);

		return remove(user);
	}

	/**
	 * Returns the number of users where contactId = &#63;.
	 *
	 * @param contactId the contact ID
	 * @return the number of matching users
	 */
	@Override
	public int countByContactId(long contactId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByContactId;

			finderArgs = new Object[] {contactId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_USER_WHERE);

			sb.append(_FINDER_COLUMN_CONTACTID_CONTACTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(contactId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_CONTACTID_CONTACTID_2 =
		"user.contactId = ?";

	private FinderPath _finderPathWithPaginationFindByEmailAddress;
	private FinderPath _finderPathWithoutPaginationFindByEmailAddress;
	private FinderPath _finderPathCountByEmailAddress;

	/**
	 * Returns all the users where emailAddress = &#63;.
	 *
	 * @param emailAddress the email address
	 * @return the matching users
	 */
	@Override
	public List<User> findByEmailAddress(String emailAddress) {
		return findByEmailAddress(
			emailAddress, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the users where emailAddress = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param emailAddress the email address
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of matching users
	 */
	@Override
	public List<User> findByEmailAddress(
		String emailAddress, int start, int end) {

		return findByEmailAddress(emailAddress, start, end, null);
	}

	/**
	 * Returns an ordered range of all the users where emailAddress = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param emailAddress the email address
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByEmailAddress(
		String emailAddress, int start, int end,
		OrderByComparator<User> orderByComparator) {

		return findByEmailAddress(
			emailAddress, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the users where emailAddress = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param emailAddress the email address
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByEmailAddress(
		String emailAddress, int start, int end,
		OrderByComparator<User> orderByComparator, boolean useFinderCache) {

		emailAddress = Objects.toString(emailAddress, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByEmailAddress;
				finderArgs = new Object[] {emailAddress};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByEmailAddress;
			finderArgs = new Object[] {
				emailAddress, start, end, orderByComparator
			};
		}

		List<User> list = null;

		if (useFinderCache && productionMode) {
			list = (List<User>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (User user : list) {
					if (!emailAddress.equals(user.getEmailAddress())) {
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

			sb.append(_SQL_SELECT_USER_WHERE);

			boolean bindEmailAddress = false;

			if (emailAddress.isEmpty()) {
				sb.append(_FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_3);
			}
			else {
				bindEmailAddress = true;

				sb.append(_FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(UserModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindEmailAddress) {
					queryPos.add(emailAddress);
				}

				list = (List<User>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first user in the ordered set where emailAddress = &#63;.
	 *
	 * @param emailAddress the email address
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByEmailAddress_First(
			String emailAddress, OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {

		User user = fetchByEmailAddress_First(emailAddress, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("emailAddress=");
		sb.append(emailAddress);

		sb.append("}");

		throw new NoSuchUserException(sb.toString());
	}

	/**
	 * Returns the first user in the ordered set where emailAddress = &#63;.
	 *
	 * @param emailAddress the email address
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByEmailAddress_First(
		String emailAddress, OrderByComparator<User> orderByComparator) {

		List<User> list = findByEmailAddress(
			emailAddress, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user in the ordered set where emailAddress = &#63;.
	 *
	 * @param emailAddress the email address
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByEmailAddress_Last(
			String emailAddress, OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {

		User user = fetchByEmailAddress_Last(emailAddress, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("emailAddress=");
		sb.append(emailAddress);

		sb.append("}");

		throw new NoSuchUserException(sb.toString());
	}

	/**
	 * Returns the last user in the ordered set where emailAddress = &#63;.
	 *
	 * @param emailAddress the email address
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByEmailAddress_Last(
		String emailAddress, OrderByComparator<User> orderByComparator) {

		int count = countByEmailAddress(emailAddress);

		if (count == 0) {
			return null;
		}

		List<User> list = findByEmailAddress(
			emailAddress, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the users before and after the current user in the ordered set where emailAddress = &#63;.
	 *
	 * @param userId the primary key of the current user
	 * @param emailAddress the email address
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user
	 * @throws NoSuchUserException if a user with the primary key could not be found
	 */
	@Override
	public User[] findByEmailAddress_PrevAndNext(
			long userId, String emailAddress,
			OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {

		emailAddress = Objects.toString(emailAddress, "");

		User user = findByPrimaryKey(userId);

		Session session = null;

		try {
			session = openSession();

			User[] array = new UserImpl[3];

			array[0] = getByEmailAddress_PrevAndNext(
				session, user, emailAddress, orderByComparator, true);

			array[1] = user;

			array[2] = getByEmailAddress_PrevAndNext(
				session, user, emailAddress, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected User getByEmailAddress_PrevAndNext(
		Session session, User user, String emailAddress,
		OrderByComparator<User> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_USER_WHERE);

		boolean bindEmailAddress = false;

		if (emailAddress.isEmpty()) {
			sb.append(_FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_3);
		}
		else {
			bindEmailAddress = true;

			sb.append(_FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_2);
		}

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
			sb.append(UserModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindEmailAddress) {
			queryPos.add(emailAddress);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(user)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<User> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the users where emailAddress = &#63; from the database.
	 *
	 * @param emailAddress the email address
	 */
	@Override
	public void removeByEmailAddress(String emailAddress) {
		for (User user :
				findByEmailAddress(
					emailAddress, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(user);
		}
	}

	/**
	 * Returns the number of users where emailAddress = &#63;.
	 *
	 * @param emailAddress the email address
	 * @return the number of matching users
	 */
	@Override
	public int countByEmailAddress(String emailAddress) {
		emailAddress = Objects.toString(emailAddress, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByEmailAddress;

			finderArgs = new Object[] {emailAddress};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_USER_WHERE);

			boolean bindEmailAddress = false;

			if (emailAddress.isEmpty()) {
				sb.append(_FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_3);
			}
			else {
				bindEmailAddress = true;

				sb.append(_FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindEmailAddress) {
					queryPos.add(emailAddress);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_2 =
		"user.emailAddress = ?";

	private static final String _FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_3 =
		"(user.emailAddress IS NULL OR user.emailAddress = '')";

	private FinderPath _finderPathFetchByPortraitId;
	private FinderPath _finderPathCountByPortraitId;

	/**
	 * Returns the user where portraitId = &#63; or throws a <code>NoSuchUserException</code> if it could not be found.
	 *
	 * @param portraitId the portrait ID
	 * @return the matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByPortraitId(long portraitId) throws NoSuchUserException {
		User user = fetchByPortraitId(portraitId);

		if (user == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("portraitId=");
			sb.append(portraitId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchUserException(sb.toString());
		}

		return user;
	}

	/**
	 * Returns the user where portraitId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param portraitId the portrait ID
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByPortraitId(long portraitId) {
		return fetchByPortraitId(portraitId, true);
	}

	/**
	 * Returns the user where portraitId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param portraitId the portrait ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByPortraitId(long portraitId, boolean useFinderCache) {
		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {portraitId};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByPortraitId, finderArgs, this);
		}

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		if (result instanceof User) {
			User user = (User)result;

			if (portraitId != user.getPortraitId()) {
				result = null;
			}
			else if (!CTPersistenceHelperUtil.isProductionMode(
						User.class, user.getPrimaryKey())) {

				result = null;
			}
		}
		else if (!productionMode && (result instanceof List<?>)) {
			result = null;
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_USER_WHERE);

			sb.append(_FINDER_COLUMN_PORTRAITID_PORTRAITID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(portraitId);

				List<User> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						FinderCacheUtil.putResult(
							_finderPathFetchByPortraitId, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!productionMode || !useFinderCache) {
								finderArgs = new Object[] {portraitId};
							}

							_log.warn(
								"UserPersistenceImpl.fetchByPortraitId(long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					User user = list.get(0);

					result = user;

					cacheResult(user);
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
			return (User)result;
		}
	}

	/**
	 * Removes the user where portraitId = &#63; from the database.
	 *
	 * @param portraitId the portrait ID
	 * @return the user that was removed
	 */
	@Override
	public User removeByPortraitId(long portraitId) throws NoSuchUserException {
		User user = findByPortraitId(portraitId);

		return remove(user);
	}

	/**
	 * Returns the number of users where portraitId = &#63;.
	 *
	 * @param portraitId the portrait ID
	 * @return the number of matching users
	 */
	@Override
	public int countByPortraitId(long portraitId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByPortraitId;

			finderArgs = new Object[] {portraitId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_USER_WHERE);

			sb.append(_FINDER_COLUMN_PORTRAITID_PORTRAITID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(portraitId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_PORTRAITID_PORTRAITID_2 =
		"user.portraitId = ?";

	private FinderPath _finderPathWithPaginationFindByGtU_C;
	private FinderPath _finderPathWithPaginationCountByGtU_C;

	/**
	 * Returns all the users where userId &gt; &#63; and companyId = &#63;.
	 *
	 * @param userId the user ID
	 * @param companyId the company ID
	 * @return the matching users
	 */
	@Override
	public List<User> findByGtU_C(long userId, long companyId) {
		return findByGtU_C(
			userId, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the users where userId &gt; &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param companyId the company ID
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of matching users
	 */
	@Override
	public List<User> findByGtU_C(
		long userId, long companyId, int start, int end) {

		return findByGtU_C(userId, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the users where userId &gt; &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param companyId the company ID
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByGtU_C(
		long userId, long companyId, int start, int end,
		OrderByComparator<User> orderByComparator) {

		return findByGtU_C(
			userId, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the users where userId &gt; &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param companyId the company ID
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByGtU_C(
		long userId, long companyId, int start, int end,
		OrderByComparator<User> orderByComparator, boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByGtU_C;
		finderArgs = new Object[] {
			userId, companyId, start, end, orderByComparator
		};

		List<User> list = null;

		if (useFinderCache && productionMode) {
			list = (List<User>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (User user : list) {
					if ((userId >= user.getUserId()) ||
						(companyId != user.getCompanyId())) {

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
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_USER_WHERE);

			sb.append(_FINDER_COLUMN_GTU_C_USERID_2);

			sb.append(_FINDER_COLUMN_GTU_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(UserModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(companyId);

				list = (List<User>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first user in the ordered set where userId &gt; &#63; and companyId = &#63;.
	 *
	 * @param userId the user ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByGtU_C_First(
			long userId, long companyId,
			OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {

		User user = fetchByGtU_C_First(userId, companyId, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId>");
		sb.append(userId);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchUserException(sb.toString());
	}

	/**
	 * Returns the first user in the ordered set where userId &gt; &#63; and companyId = &#63;.
	 *
	 * @param userId the user ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByGtU_C_First(
		long userId, long companyId,
		OrderByComparator<User> orderByComparator) {

		List<User> list = findByGtU_C(
			userId, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user in the ordered set where userId &gt; &#63; and companyId = &#63;.
	 *
	 * @param userId the user ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByGtU_C_Last(
			long userId, long companyId,
			OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {

		User user = fetchByGtU_C_Last(userId, companyId, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId>");
		sb.append(userId);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchUserException(sb.toString());
	}

	/**
	 * Returns the last user in the ordered set where userId &gt; &#63; and companyId = &#63;.
	 *
	 * @param userId the user ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByGtU_C_Last(
		long userId, long companyId,
		OrderByComparator<User> orderByComparator) {

		int count = countByGtU_C(userId, companyId);

		if (count == 0) {
			return null;
		}

		List<User> list = findByGtU_C(
			userId, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the users where userId &gt; &#63; and companyId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param companyId the company ID
	 */
	@Override
	public void removeByGtU_C(long userId, long companyId) {
		for (User user :
				findByGtU_C(
					userId, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(user);
		}
	}

	/**
	 * Returns the number of users where userId &gt; &#63; and companyId = &#63;.
	 *
	 * @param userId the user ID
	 * @param companyId the company ID
	 * @return the number of matching users
	 */
	@Override
	public int countByGtU_C(long userId, long companyId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByGtU_C;

			finderArgs = new Object[] {userId, companyId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_USER_WHERE);

			sb.append(_FINDER_COLUMN_GTU_C_USERID_2);

			sb.append(_FINDER_COLUMN_GTU_C_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_GTU_C_USERID_2 =
		"user.userId > ? AND ";

	private static final String _FINDER_COLUMN_GTU_C_COMPANYID_2 =
		"user.companyId = ? AND type_ = 1";

	private FinderPath _finderPathFetchByC_U;
	private FinderPath _finderPathCountByC_U;

	/**
	 * Returns the user where companyId = &#63; and userId = &#63; or throws a <code>NoSuchUserException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_U(long companyId, long userId)
		throws NoSuchUserException {

		User user = fetchByC_U(companyId, userId);

		if (user == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", userId=");
			sb.append(userId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchUserException(sb.toString());
		}

		return user;
	}

	/**
	 * Returns the user where companyId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByC_U(long companyId, long userId) {
		return fetchByC_U(companyId, userId, true);
	}

	/**
	 * Returns the user where companyId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByC_U(
		long companyId, long userId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, userId};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByC_U, finderArgs, this);
		}

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		if (result instanceof User) {
			User user = (User)result;

			if ((companyId != user.getCompanyId()) ||
				(userId != user.getUserId())) {

				result = null;
			}
			else if (!CTPersistenceHelperUtil.isProductionMode(
						User.class, user.getPrimaryKey())) {

				result = null;
			}
		}
		else if (!productionMode && (result instanceof List<?>)) {
			result = null;
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_USER_WHERE);

			sb.append(_FINDER_COLUMN_C_U_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_U_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(userId);

				List<User> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						FinderCacheUtil.putResult(
							_finderPathFetchByC_U, finderArgs, list);
					}
				}
				else {
					User user = list.get(0);

					result = user;

					cacheResult(user);
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
			return (User)result;
		}
	}

	/**
	 * Removes the user where companyId = &#63; and userId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the user that was removed
	 */
	@Override
	public User removeByC_U(long companyId, long userId)
		throws NoSuchUserException {

		User user = findByC_U(companyId, userId);

		return remove(user);
	}

	/**
	 * Returns the number of users where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the number of matching users
	 */
	@Override
	public int countByC_U(long companyId, long userId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_U;

			finderArgs = new Object[] {companyId, userId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_USER_WHERE);

			sb.append(_FINDER_COLUMN_C_U_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_U_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(userId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_C_U_COMPANYID_2 =
		"user.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_U_USERID_2 = "user.userId = ?";

	private FinderPath _finderPathWithPaginationFindByC_CD;
	private FinderPath _finderPathWithoutPaginationFindByC_CD;
	private FinderPath _finderPathCountByC_CD;

	/**
	 * Returns all the users where companyId = &#63; and createDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @return the matching users
	 */
	@Override
	public List<User> findByC_CD(long companyId, Date createDate) {
		return findByC_CD(
			companyId, createDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the users where companyId = &#63; and createDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of matching users
	 */
	@Override
	public List<User> findByC_CD(
		long companyId, Date createDate, int start, int end) {

		return findByC_CD(companyId, createDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the users where companyId = &#63; and createDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByC_CD(
		long companyId, Date createDate, int start, int end,
		OrderByComparator<User> orderByComparator) {

		return findByC_CD(
			companyId, createDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the users where companyId = &#63; and createDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByC_CD(
		long companyId, Date createDate, int start, int end,
		OrderByComparator<User> orderByComparator, boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_CD;
				finderArgs = new Object[] {companyId, _getTime(createDate)};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_CD;
			finderArgs = new Object[] {
				companyId, _getTime(createDate), start, end, orderByComparator
			};
		}

		List<User> list = null;

		if (useFinderCache && productionMode) {
			list = (List<User>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (User user : list) {
					if ((companyId != user.getCompanyId()) ||
						!Objects.equals(createDate, user.getCreateDate())) {

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
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_USER_WHERE);

			sb.append(_FINDER_COLUMN_C_CD_COMPANYID_2);

			boolean bindCreateDate = false;

			if (createDate == null) {
				sb.append(_FINDER_COLUMN_C_CD_CREATEDATE_1);
			}
			else {
				bindCreateDate = true;

				sb.append(_FINDER_COLUMN_C_CD_CREATEDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(UserModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindCreateDate) {
					queryPos.add(new Timestamp(createDate.getTime()));
				}

				list = (List<User>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first user in the ordered set where companyId = &#63; and createDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_CD_First(
			long companyId, Date createDate,
			OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {

		User user = fetchByC_CD_First(companyId, createDate, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", createDate=");
		sb.append(createDate);

		sb.append("}");

		throw new NoSuchUserException(sb.toString());
	}

	/**
	 * Returns the first user in the ordered set where companyId = &#63; and createDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByC_CD_First(
		long companyId, Date createDate,
		OrderByComparator<User> orderByComparator) {

		List<User> list = findByC_CD(
			companyId, createDate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user in the ordered set where companyId = &#63; and createDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_CD_Last(
			long companyId, Date createDate,
			OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {

		User user = fetchByC_CD_Last(companyId, createDate, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", createDate=");
		sb.append(createDate);

		sb.append("}");

		throw new NoSuchUserException(sb.toString());
	}

	/**
	 * Returns the last user in the ordered set where companyId = &#63; and createDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByC_CD_Last(
		long companyId, Date createDate,
		OrderByComparator<User> orderByComparator) {

		int count = countByC_CD(companyId, createDate);

		if (count == 0) {
			return null;
		}

		List<User> list = findByC_CD(
			companyId, createDate, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the users before and after the current user in the ordered set where companyId = &#63; and createDate = &#63;.
	 *
	 * @param userId the primary key of the current user
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user
	 * @throws NoSuchUserException if a user with the primary key could not be found
	 */
	@Override
	public User[] findByC_CD_PrevAndNext(
			long userId, long companyId, Date createDate,
			OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {

		User user = findByPrimaryKey(userId);

		Session session = null;

		try {
			session = openSession();

			User[] array = new UserImpl[3];

			array[0] = getByC_CD_PrevAndNext(
				session, user, companyId, createDate, orderByComparator, true);

			array[1] = user;

			array[2] = getByC_CD_PrevAndNext(
				session, user, companyId, createDate, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected User getByC_CD_PrevAndNext(
		Session session, User user, long companyId, Date createDate,
		OrderByComparator<User> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_USER_WHERE);

		sb.append(_FINDER_COLUMN_C_CD_COMPANYID_2);

		boolean bindCreateDate = false;

		if (createDate == null) {
			sb.append(_FINDER_COLUMN_C_CD_CREATEDATE_1);
		}
		else {
			bindCreateDate = true;

			sb.append(_FINDER_COLUMN_C_CD_CREATEDATE_2);
		}

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
			sb.append(UserModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (bindCreateDate) {
			queryPos.add(new Timestamp(createDate.getTime()));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(user)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<User> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the users where companyId = &#63; and createDate = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 */
	@Override
	public void removeByC_CD(long companyId, Date createDate) {
		for (User user :
				findByC_CD(
					companyId, createDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(user);
		}
	}

	/**
	 * Returns the number of users where companyId = &#63; and createDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @return the number of matching users
	 */
	@Override
	public int countByC_CD(long companyId, Date createDate) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_CD;

			finderArgs = new Object[] {companyId, _getTime(createDate)};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_USER_WHERE);

			sb.append(_FINDER_COLUMN_C_CD_COMPANYID_2);

			boolean bindCreateDate = false;

			if (createDate == null) {
				sb.append(_FINDER_COLUMN_C_CD_CREATEDATE_1);
			}
			else {
				bindCreateDate = true;

				sb.append(_FINDER_COLUMN_C_CD_CREATEDATE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindCreateDate) {
					queryPos.add(new Timestamp(createDate.getTime()));
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_C_CD_COMPANYID_2 =
		"user.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_CD_CREATEDATE_1 =
		"user.createDate IS NULL AND type_ = 1";

	private static final String _FINDER_COLUMN_C_CD_CREATEDATE_2 =
		"user.createDate = ? AND type_ = 1";

	private FinderPath _finderPathWithPaginationFindByC_MD;
	private FinderPath _finderPathWithoutPaginationFindByC_MD;
	private FinderPath _finderPathCountByC_MD;

	/**
	 * Returns all the users where companyId = &#63; and modifiedDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @return the matching users
	 */
	@Override
	public List<User> findByC_MD(long companyId, Date modifiedDate) {
		return findByC_MD(
			companyId, modifiedDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the users where companyId = &#63; and modifiedDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of matching users
	 */
	@Override
	public List<User> findByC_MD(
		long companyId, Date modifiedDate, int start, int end) {

		return findByC_MD(companyId, modifiedDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the users where companyId = &#63; and modifiedDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByC_MD(
		long companyId, Date modifiedDate, int start, int end,
		OrderByComparator<User> orderByComparator) {

		return findByC_MD(
			companyId, modifiedDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the users where companyId = &#63; and modifiedDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByC_MD(
		long companyId, Date modifiedDate, int start, int end,
		OrderByComparator<User> orderByComparator, boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_MD;
				finderArgs = new Object[] {companyId, _getTime(modifiedDate)};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_MD;
			finderArgs = new Object[] {
				companyId, _getTime(modifiedDate), start, end, orderByComparator
			};
		}

		List<User> list = null;

		if (useFinderCache && productionMode) {
			list = (List<User>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (User user : list) {
					if ((companyId != user.getCompanyId()) ||
						!Objects.equals(modifiedDate, user.getModifiedDate())) {

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
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_USER_WHERE);

			sb.append(_FINDER_COLUMN_C_MD_COMPANYID_2);

			boolean bindModifiedDate = false;

			if (modifiedDate == null) {
				sb.append(_FINDER_COLUMN_C_MD_MODIFIEDDATE_1);
			}
			else {
				bindModifiedDate = true;

				sb.append(_FINDER_COLUMN_C_MD_MODIFIEDDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(UserModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindModifiedDate) {
					queryPos.add(new Timestamp(modifiedDate.getTime()));
				}

				list = (List<User>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first user in the ordered set where companyId = &#63; and modifiedDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_MD_First(
			long companyId, Date modifiedDate,
			OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {

		User user = fetchByC_MD_First(
			companyId, modifiedDate, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", modifiedDate=");
		sb.append(modifiedDate);

		sb.append("}");

		throw new NoSuchUserException(sb.toString());
	}

	/**
	 * Returns the first user in the ordered set where companyId = &#63; and modifiedDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByC_MD_First(
		long companyId, Date modifiedDate,
		OrderByComparator<User> orderByComparator) {

		List<User> list = findByC_MD(
			companyId, modifiedDate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user in the ordered set where companyId = &#63; and modifiedDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_MD_Last(
			long companyId, Date modifiedDate,
			OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {

		User user = fetchByC_MD_Last(
			companyId, modifiedDate, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", modifiedDate=");
		sb.append(modifiedDate);

		sb.append("}");

		throw new NoSuchUserException(sb.toString());
	}

	/**
	 * Returns the last user in the ordered set where companyId = &#63; and modifiedDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByC_MD_Last(
		long companyId, Date modifiedDate,
		OrderByComparator<User> orderByComparator) {

		int count = countByC_MD(companyId, modifiedDate);

		if (count == 0) {
			return null;
		}

		List<User> list = findByC_MD(
			companyId, modifiedDate, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the users before and after the current user in the ordered set where companyId = &#63; and modifiedDate = &#63;.
	 *
	 * @param userId the primary key of the current user
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user
	 * @throws NoSuchUserException if a user with the primary key could not be found
	 */
	@Override
	public User[] findByC_MD_PrevAndNext(
			long userId, long companyId, Date modifiedDate,
			OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {

		User user = findByPrimaryKey(userId);

		Session session = null;

		try {
			session = openSession();

			User[] array = new UserImpl[3];

			array[0] = getByC_MD_PrevAndNext(
				session, user, companyId, modifiedDate, orderByComparator,
				true);

			array[1] = user;

			array[2] = getByC_MD_PrevAndNext(
				session, user, companyId, modifiedDate, orderByComparator,
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

	protected User getByC_MD_PrevAndNext(
		Session session, User user, long companyId, Date modifiedDate,
		OrderByComparator<User> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_USER_WHERE);

		sb.append(_FINDER_COLUMN_C_MD_COMPANYID_2);

		boolean bindModifiedDate = false;

		if (modifiedDate == null) {
			sb.append(_FINDER_COLUMN_C_MD_MODIFIEDDATE_1);
		}
		else {
			bindModifiedDate = true;

			sb.append(_FINDER_COLUMN_C_MD_MODIFIEDDATE_2);
		}

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
			sb.append(UserModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (bindModifiedDate) {
			queryPos.add(new Timestamp(modifiedDate.getTime()));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(user)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<User> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the users where companyId = &#63; and modifiedDate = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 */
	@Override
	public void removeByC_MD(long companyId, Date modifiedDate) {
		for (User user :
				findByC_MD(
					companyId, modifiedDate, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(user);
		}
	}

	/**
	 * Returns the number of users where companyId = &#63; and modifiedDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @return the number of matching users
	 */
	@Override
	public int countByC_MD(long companyId, Date modifiedDate) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_MD;

			finderArgs = new Object[] {companyId, _getTime(modifiedDate)};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_USER_WHERE);

			sb.append(_FINDER_COLUMN_C_MD_COMPANYID_2);

			boolean bindModifiedDate = false;

			if (modifiedDate == null) {
				sb.append(_FINDER_COLUMN_C_MD_MODIFIEDDATE_1);
			}
			else {
				bindModifiedDate = true;

				sb.append(_FINDER_COLUMN_C_MD_MODIFIEDDATE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindModifiedDate) {
					queryPos.add(new Timestamp(modifiedDate.getTime()));
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_C_MD_COMPANYID_2 =
		"user.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_MD_MODIFIEDDATE_1 =
		"user.modifiedDate IS NULL AND type_ = 1";

	private static final String _FINDER_COLUMN_C_MD_MODIFIEDDATE_2 =
		"user.modifiedDate = ? AND type_ = 1";

	private FinderPath _finderPathFetchByC_SN;
	private FinderPath _finderPathCountByC_SN;

	/**
	 * Returns the user where companyId = &#63; and screenName = &#63; or throws a <code>NoSuchUserException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param screenName the screen name
	 * @return the matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_SN(long companyId, String screenName)
		throws NoSuchUserException {

		User user = fetchByC_SN(companyId, screenName);

		if (user == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", screenName=");
			sb.append(screenName);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchUserException(sb.toString());
		}

		return user;
	}

	/**
	 * Returns the user where companyId = &#63; and screenName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param screenName the screen name
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByC_SN(long companyId, String screenName) {
		return fetchByC_SN(companyId, screenName, true);
	}

	/**
	 * Returns the user where companyId = &#63; and screenName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param screenName the screen name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByC_SN(
		long companyId, String screenName, boolean useFinderCache) {

		screenName = Objects.toString(screenName, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, screenName};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByC_SN, finderArgs, this);
		}

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		if (result instanceof User) {
			User user = (User)result;

			if ((companyId != user.getCompanyId()) ||
				!Objects.equals(screenName, user.getScreenName())) {

				result = null;
			}
			else if (!CTPersistenceHelperUtil.isProductionMode(
						User.class, user.getPrimaryKey())) {

				result = null;
			}
		}
		else if (!productionMode && (result instanceof List<?>)) {
			result = null;
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_USER_WHERE);

			sb.append(_FINDER_COLUMN_C_SN_COMPANYID_2);

			boolean bindScreenName = false;

			if (screenName.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_SN_SCREENNAME_3);
			}
			else {
				bindScreenName = true;

				sb.append(_FINDER_COLUMN_C_SN_SCREENNAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindScreenName) {
					queryPos.add(screenName);
				}

				List<User> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						FinderCacheUtil.putResult(
							_finderPathFetchByC_SN, finderArgs, list);
					}
				}
				else {
					User user = list.get(0);

					result = user;

					cacheResult(user);
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
			return (User)result;
		}
	}

	/**
	 * Removes the user where companyId = &#63; and screenName = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param screenName the screen name
	 * @return the user that was removed
	 */
	@Override
	public User removeByC_SN(long companyId, String screenName)
		throws NoSuchUserException {

		User user = findByC_SN(companyId, screenName);

		return remove(user);
	}

	/**
	 * Returns the number of users where companyId = &#63; and screenName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param screenName the screen name
	 * @return the number of matching users
	 */
	@Override
	public int countByC_SN(long companyId, String screenName) {
		screenName = Objects.toString(screenName, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_SN;

			finderArgs = new Object[] {companyId, screenName};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_USER_WHERE);

			sb.append(_FINDER_COLUMN_C_SN_COMPANYID_2);

			boolean bindScreenName = false;

			if (screenName.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_SN_SCREENNAME_3);
			}
			else {
				bindScreenName = true;

				sb.append(_FINDER_COLUMN_C_SN_SCREENNAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindScreenName) {
					queryPos.add(screenName);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_C_SN_COMPANYID_2 =
		"user.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_SN_SCREENNAME_2 =
		"user.screenName = ?";

	private static final String _FINDER_COLUMN_C_SN_SCREENNAME_3 =
		"(user.screenName IS NULL OR user.screenName = '')";

	private FinderPath _finderPathFetchByC_EA;
	private FinderPath _finderPathCountByC_EA;

	/**
	 * Returns the user where companyId = &#63; and emailAddress = &#63; or throws a <code>NoSuchUserException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param emailAddress the email address
	 * @return the matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_EA(long companyId, String emailAddress)
		throws NoSuchUserException {

		User user = fetchByC_EA(companyId, emailAddress);

		if (user == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", emailAddress=");
			sb.append(emailAddress);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchUserException(sb.toString());
		}

		return user;
	}

	/**
	 * Returns the user where companyId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param emailAddress the email address
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByC_EA(long companyId, String emailAddress) {
		return fetchByC_EA(companyId, emailAddress, true);
	}

	/**
	 * Returns the user where companyId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param emailAddress the email address
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByC_EA(
		long companyId, String emailAddress, boolean useFinderCache) {

		emailAddress = Objects.toString(emailAddress, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, emailAddress};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByC_EA, finderArgs, this);
		}

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		if (result instanceof User) {
			User user = (User)result;

			if ((companyId != user.getCompanyId()) ||
				!Objects.equals(emailAddress, user.getEmailAddress())) {

				result = null;
			}
			else if (!CTPersistenceHelperUtil.isProductionMode(
						User.class, user.getPrimaryKey())) {

				result = null;
			}
		}
		else if (!productionMode && (result instanceof List<?>)) {
			result = null;
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_USER_WHERE);

			sb.append(_FINDER_COLUMN_C_EA_COMPANYID_2);

			boolean bindEmailAddress = false;

			if (emailAddress.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_EA_EMAILADDRESS_3);
			}
			else {
				bindEmailAddress = true;

				sb.append(_FINDER_COLUMN_C_EA_EMAILADDRESS_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindEmailAddress) {
					queryPos.add(emailAddress);
				}

				List<User> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						FinderCacheUtil.putResult(
							_finderPathFetchByC_EA, finderArgs, list);
					}
				}
				else {
					User user = list.get(0);

					result = user;

					cacheResult(user);
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
			return (User)result;
		}
	}

	/**
	 * Removes the user where companyId = &#63; and emailAddress = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param emailAddress the email address
	 * @return the user that was removed
	 */
	@Override
	public User removeByC_EA(long companyId, String emailAddress)
		throws NoSuchUserException {

		User user = findByC_EA(companyId, emailAddress);

		return remove(user);
	}

	/**
	 * Returns the number of users where companyId = &#63; and emailAddress = &#63;.
	 *
	 * @param companyId the company ID
	 * @param emailAddress the email address
	 * @return the number of matching users
	 */
	@Override
	public int countByC_EA(long companyId, String emailAddress) {
		emailAddress = Objects.toString(emailAddress, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_EA;

			finderArgs = new Object[] {companyId, emailAddress};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_USER_WHERE);

			sb.append(_FINDER_COLUMN_C_EA_COMPANYID_2);

			boolean bindEmailAddress = false;

			if (emailAddress.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_EA_EMAILADDRESS_3);
			}
			else {
				bindEmailAddress = true;

				sb.append(_FINDER_COLUMN_C_EA_EMAILADDRESS_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindEmailAddress) {
					queryPos.add(emailAddress);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_C_EA_COMPANYID_2 =
		"user.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_EA_EMAILADDRESS_2 =
		"user.emailAddress = ?";

	private static final String _FINDER_COLUMN_C_EA_EMAILADDRESS_3 =
		"(user.emailAddress IS NULL OR user.emailAddress = '')";

	private FinderPath _finderPathFetchByC_FID;
	private FinderPath _finderPathCountByC_FID;

	/**
	 * Returns the user where companyId = &#63; and facebookId = &#63; or throws a <code>NoSuchUserException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param facebookId the facebook ID
	 * @return the matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_FID(long companyId, long facebookId)
		throws NoSuchUserException {

		User user = fetchByC_FID(companyId, facebookId);

		if (user == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", facebookId=");
			sb.append(facebookId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchUserException(sb.toString());
		}

		return user;
	}

	/**
	 * Returns the user where companyId = &#63; and facebookId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param facebookId the facebook ID
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByC_FID(long companyId, long facebookId) {
		return fetchByC_FID(companyId, facebookId, true);
	}

	/**
	 * Returns the user where companyId = &#63; and facebookId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param facebookId the facebook ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByC_FID(
		long companyId, long facebookId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, facebookId};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByC_FID, finderArgs, this);
		}

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		if (result instanceof User) {
			User user = (User)result;

			if ((companyId != user.getCompanyId()) ||
				(facebookId != user.getFacebookId())) {

				result = null;
			}
			else if (!CTPersistenceHelperUtil.isProductionMode(
						User.class, user.getPrimaryKey())) {

				result = null;
			}
		}
		else if (!productionMode && (result instanceof List<?>)) {
			result = null;
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_USER_WHERE);

			sb.append(_FINDER_COLUMN_C_FID_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_FID_FACEBOOKID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(facebookId);

				List<User> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						FinderCacheUtil.putResult(
							_finderPathFetchByC_FID, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!productionMode || !useFinderCache) {
								finderArgs = new Object[] {
									companyId, facebookId
								};
							}

							_log.warn(
								"UserPersistenceImpl.fetchByC_FID(long, long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					User user = list.get(0);

					result = user;

					cacheResult(user);
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
			return (User)result;
		}
	}

	/**
	 * Removes the user where companyId = &#63; and facebookId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param facebookId the facebook ID
	 * @return the user that was removed
	 */
	@Override
	public User removeByC_FID(long companyId, long facebookId)
		throws NoSuchUserException {

		User user = findByC_FID(companyId, facebookId);

		return remove(user);
	}

	/**
	 * Returns the number of users where companyId = &#63; and facebookId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param facebookId the facebook ID
	 * @return the number of matching users
	 */
	@Override
	public int countByC_FID(long companyId, long facebookId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_FID;

			finderArgs = new Object[] {companyId, facebookId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_USER_WHERE);

			sb.append(_FINDER_COLUMN_C_FID_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_FID_FACEBOOKID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(facebookId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_C_FID_COMPANYID_2 =
		"user.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_FID_FACEBOOKID_2 =
		"user.facebookId = ?";

	private FinderPath _finderPathFetchByC_GUID;
	private FinderPath _finderPathCountByC_GUID;

	/**
	 * Returns the user where companyId = &#63; and googleUserId = &#63; or throws a <code>NoSuchUserException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param googleUserId the google user ID
	 * @return the matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_GUID(long companyId, String googleUserId)
		throws NoSuchUserException {

		User user = fetchByC_GUID(companyId, googleUserId);

		if (user == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", googleUserId=");
			sb.append(googleUserId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchUserException(sb.toString());
		}

		return user;
	}

	/**
	 * Returns the user where companyId = &#63; and googleUserId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param googleUserId the google user ID
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByC_GUID(long companyId, String googleUserId) {
		return fetchByC_GUID(companyId, googleUserId, true);
	}

	/**
	 * Returns the user where companyId = &#63; and googleUserId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param googleUserId the google user ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByC_GUID(
		long companyId, String googleUserId, boolean useFinderCache) {

		googleUserId = Objects.toString(googleUserId, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, googleUserId};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByC_GUID, finderArgs, this);
		}

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		if (result instanceof User) {
			User user = (User)result;

			if ((companyId != user.getCompanyId()) ||
				!Objects.equals(googleUserId, user.getGoogleUserId())) {

				result = null;
			}
			else if (!CTPersistenceHelperUtil.isProductionMode(
						User.class, user.getPrimaryKey())) {

				result = null;
			}
		}
		else if (!productionMode && (result instanceof List<?>)) {
			result = null;
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_USER_WHERE);

			sb.append(_FINDER_COLUMN_C_GUID_COMPANYID_2);

			boolean bindGoogleUserId = false;

			if (googleUserId.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_GUID_GOOGLEUSERID_3);
			}
			else {
				bindGoogleUserId = true;

				sb.append(_FINDER_COLUMN_C_GUID_GOOGLEUSERID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindGoogleUserId) {
					queryPos.add(googleUserId);
				}

				List<User> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						FinderCacheUtil.putResult(
							_finderPathFetchByC_GUID, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!productionMode || !useFinderCache) {
								finderArgs = new Object[] {
									companyId, googleUserId
								};
							}

							_log.warn(
								"UserPersistenceImpl.fetchByC_GUID(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					User user = list.get(0);

					result = user;

					cacheResult(user);
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
			return (User)result;
		}
	}

	/**
	 * Removes the user where companyId = &#63; and googleUserId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param googleUserId the google user ID
	 * @return the user that was removed
	 */
	@Override
	public User removeByC_GUID(long companyId, String googleUserId)
		throws NoSuchUserException {

		User user = findByC_GUID(companyId, googleUserId);

		return remove(user);
	}

	/**
	 * Returns the number of users where companyId = &#63; and googleUserId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param googleUserId the google user ID
	 * @return the number of matching users
	 */
	@Override
	public int countByC_GUID(long companyId, String googleUserId) {
		googleUserId = Objects.toString(googleUserId, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_GUID;

			finderArgs = new Object[] {companyId, googleUserId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_USER_WHERE);

			sb.append(_FINDER_COLUMN_C_GUID_COMPANYID_2);

			boolean bindGoogleUserId = false;

			if (googleUserId.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_GUID_GOOGLEUSERID_3);
			}
			else {
				bindGoogleUserId = true;

				sb.append(_FINDER_COLUMN_C_GUID_GOOGLEUSERID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindGoogleUserId) {
					queryPos.add(googleUserId);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_C_GUID_COMPANYID_2 =
		"user.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_GUID_GOOGLEUSERID_2 =
		"user.googleUserId = ?";

	private static final String _FINDER_COLUMN_C_GUID_GOOGLEUSERID_3 =
		"(user.googleUserId IS NULL OR user.googleUserId = '')";

	private FinderPath _finderPathFetchByC_O;
	private FinderPath _finderPathCountByC_O;

	/**
	 * Returns the user where companyId = &#63; and openId = &#63; or throws a <code>NoSuchUserException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param openId the open ID
	 * @return the matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_O(long companyId, String openId)
		throws NoSuchUserException {

		User user = fetchByC_O(companyId, openId);

		if (user == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", openId=");
			sb.append(openId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchUserException(sb.toString());
		}

		return user;
	}

	/**
	 * Returns the user where companyId = &#63; and openId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param openId the open ID
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByC_O(long companyId, String openId) {
		return fetchByC_O(companyId, openId, true);
	}

	/**
	 * Returns the user where companyId = &#63; and openId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param openId the open ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByC_O(
		long companyId, String openId, boolean useFinderCache) {

		openId = Objects.toString(openId, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, openId};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByC_O, finderArgs, this);
		}

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		if (result instanceof User) {
			User user = (User)result;

			if ((companyId != user.getCompanyId()) ||
				!Objects.equals(openId, user.getOpenId())) {

				result = null;
			}
			else if (!CTPersistenceHelperUtil.isProductionMode(
						User.class, user.getPrimaryKey())) {

				result = null;
			}
		}
		else if (!productionMode && (result instanceof List<?>)) {
			result = null;
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_USER_WHERE);

			sb.append(_FINDER_COLUMN_C_O_COMPANYID_2);

			boolean bindOpenId = false;

			if (openId.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_O_OPENID_3);
			}
			else {
				bindOpenId = true;

				sb.append(_FINDER_COLUMN_C_O_OPENID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindOpenId) {
					queryPos.add(openId);
				}

				List<User> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						FinderCacheUtil.putResult(
							_finderPathFetchByC_O, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!productionMode || !useFinderCache) {
								finderArgs = new Object[] {companyId, openId};
							}

							_log.warn(
								"UserPersistenceImpl.fetchByC_O(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					User user = list.get(0);

					result = user;

					cacheResult(user);
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
			return (User)result;
		}
	}

	/**
	 * Removes the user where companyId = &#63; and openId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param openId the open ID
	 * @return the user that was removed
	 */
	@Override
	public User removeByC_O(long companyId, String openId)
		throws NoSuchUserException {

		User user = findByC_O(companyId, openId);

		return remove(user);
	}

	/**
	 * Returns the number of users where companyId = &#63; and openId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param openId the open ID
	 * @return the number of matching users
	 */
	@Override
	public int countByC_O(long companyId, String openId) {
		openId = Objects.toString(openId, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_O;

			finderArgs = new Object[] {companyId, openId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_USER_WHERE);

			sb.append(_FINDER_COLUMN_C_O_COMPANYID_2);

			boolean bindOpenId = false;

			if (openId.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_O_OPENID_3);
			}
			else {
				bindOpenId = true;

				sb.append(_FINDER_COLUMN_C_O_OPENID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindOpenId) {
					queryPos.add(openId);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_C_O_COMPANYID_2 =
		"user.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_O_OPENID_2 = "user.openId = ?";

	private static final String _FINDER_COLUMN_C_O_OPENID_3 =
		"(user.openId IS NULL OR user.openId = '')";

	private FinderPath _finderPathWithPaginationFindByC_T;
	private FinderPath _finderPathWithoutPaginationFindByC_T;
	private FinderPath _finderPathCountByC_T;

	/**
	 * Returns all the users where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching users
	 */
	@Override
	public List<User> findByC_T(long companyId, int type) {
		return findByC_T(
			companyId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the users where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of matching users
	 */
	@Override
	public List<User> findByC_T(long companyId, int type, int start, int end) {
		return findByC_T(companyId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the users where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByC_T(
		long companyId, int type, int start, int end,
		OrderByComparator<User> orderByComparator) {

		return findByC_T(companyId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the users where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByC_T(
		long companyId, int type, int start, int end,
		OrderByComparator<User> orderByComparator, boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_T;
				finderArgs = new Object[] {companyId, type};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_T;
			finderArgs = new Object[] {
				companyId, type, start, end, orderByComparator
			};
		}

		List<User> list = null;

		if (useFinderCache && productionMode) {
			list = (List<User>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (User user : list) {
					if ((companyId != user.getCompanyId()) ||
						(type != user.getType())) {

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
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_USER_WHERE);

			sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(UserModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(type);

				list = (List<User>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first user in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_T_First(
			long companyId, int type, OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {

		User user = fetchByC_T_First(companyId, type, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchUserException(sb.toString());
	}

	/**
	 * Returns the first user in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByC_T_First(
		long companyId, int type, OrderByComparator<User> orderByComparator) {

		List<User> list = findByC_T(companyId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_T_Last(
			long companyId, int type, OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {

		User user = fetchByC_T_Last(companyId, type, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchUserException(sb.toString());
	}

	/**
	 * Returns the last user in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByC_T_Last(
		long companyId, int type, OrderByComparator<User> orderByComparator) {

		int count = countByC_T(companyId, type);

		if (count == 0) {
			return null;
		}

		List<User> list = findByC_T(
			companyId, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the users before and after the current user in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param userId the primary key of the current user
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user
	 * @throws NoSuchUserException if a user with the primary key could not be found
	 */
	@Override
	public User[] findByC_T_PrevAndNext(
			long userId, long companyId, int type,
			OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {

		User user = findByPrimaryKey(userId);

		Session session = null;

		try {
			session = openSession();

			User[] array = new UserImpl[3];

			array[0] = getByC_T_PrevAndNext(
				session, user, companyId, type, orderByComparator, true);

			array[1] = user;

			array[2] = getByC_T_PrevAndNext(
				session, user, companyId, type, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected User getByC_T_PrevAndNext(
		Session session, User user, long companyId, int type,
		OrderByComparator<User> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_USER_WHERE);

		sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_T_TYPE_2);

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
			sb.append(UserModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(user)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<User> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the users where companyId = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	@Override
	public void removeByC_T(long companyId, int type) {
		for (User user :
				findByC_T(
					companyId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(user);
		}
	}

	/**
	 * Returns the number of users where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching users
	 */
	@Override
	public int countByC_T(long companyId, int type) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_T;

			finderArgs = new Object[] {companyId, type};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_USER_WHERE);

			sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(type);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_C_T_COMPANYID_2 =
		"user.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_T_TYPE_2 = "user.type = ?";

	private FinderPath _finderPathWithPaginationFindByC_S;
	private FinderPath _finderPathWithoutPaginationFindByC_S;
	private FinderPath _finderPathCountByC_S;

	/**
	 * Returns all the users where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching users
	 */
	@Override
	public List<User> findByC_S(long companyId, int status) {
		return findByC_S(
			companyId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the users where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of matching users
	 */
	@Override
	public List<User> findByC_S(
		long companyId, int status, int start, int end) {

		return findByC_S(companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the users where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByC_S(
		long companyId, int status, int start, int end,
		OrderByComparator<User> orderByComparator) {

		return findByC_S(
			companyId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the users where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByC_S(
		long companyId, int status, int start, int end,
		OrderByComparator<User> orderByComparator, boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_S;
				finderArgs = new Object[] {companyId, status};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_S;
			finderArgs = new Object[] {
				companyId, status, start, end, orderByComparator
			};
		}

		List<User> list = null;

		if (useFinderCache && productionMode) {
			list = (List<User>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (User user : list) {
					if ((companyId != user.getCompanyId()) ||
						(status != user.getStatus())) {

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
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_USER_WHERE);

			sb.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(UserModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(status);

				list = (List<User>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first user in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_S_First(
			long companyId, int status,
			OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {

		User user = fetchByC_S_First(companyId, status, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchUserException(sb.toString());
	}

	/**
	 * Returns the first user in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByC_S_First(
		long companyId, int status, OrderByComparator<User> orderByComparator) {

		List<User> list = findByC_S(companyId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_S_Last(
			long companyId, int status,
			OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {

		User user = fetchByC_S_Last(companyId, status, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchUserException(sb.toString());
	}

	/**
	 * Returns the last user in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByC_S_Last(
		long companyId, int status, OrderByComparator<User> orderByComparator) {

		int count = countByC_S(companyId, status);

		if (count == 0) {
			return null;
		}

		List<User> list = findByC_S(
			companyId, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the users before and after the current user in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param userId the primary key of the current user
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user
	 * @throws NoSuchUserException if a user with the primary key could not be found
	 */
	@Override
	public User[] findByC_S_PrevAndNext(
			long userId, long companyId, int status,
			OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {

		User user = findByPrimaryKey(userId);

		Session session = null;

		try {
			session = openSession();

			User[] array = new UserImpl[3];

			array[0] = getByC_S_PrevAndNext(
				session, user, companyId, status, orderByComparator, true);

			array[1] = user;

			array[2] = getByC_S_PrevAndNext(
				session, user, companyId, status, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected User getByC_S_PrevAndNext(
		Session session, User user, long companyId, int status,
		OrderByComparator<User> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_USER_WHERE);

		sb.append(_FINDER_COLUMN_C_S_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_S_STATUS_2);

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
			sb.append(UserModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(user)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<User> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the users where companyId = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 */
	@Override
	public void removeByC_S(long companyId, int status) {
		for (User user :
				findByC_S(
					companyId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(user);
		}
	}

	/**
	 * Returns the number of users where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching users
	 */
	@Override
	public int countByC_S(long companyId, int status) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_S;

			finderArgs = new Object[] {companyId, status};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_USER_WHERE);

			sb.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(status);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_C_S_COMPANYID_2 =
		"user.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_S_STATUS_2 =
		"user.status = ? AND type_ = 1";

	private FinderPath _finderPathWithPaginationFindByC_CD_MD;
	private FinderPath _finderPathWithoutPaginationFindByC_CD_MD;
	private FinderPath _finderPathCountByC_CD_MD;

	/**
	 * Returns all the users where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param modifiedDate the modified date
	 * @return the matching users
	 */
	@Override
	public List<User> findByC_CD_MD(
		long companyId, Date createDate, Date modifiedDate) {

		return findByC_CD_MD(
			companyId, createDate, modifiedDate, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the users where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of matching users
	 */
	@Override
	public List<User> findByC_CD_MD(
		long companyId, Date createDate, Date modifiedDate, int start,
		int end) {

		return findByC_CD_MD(
			companyId, createDate, modifiedDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the users where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByC_CD_MD(
		long companyId, Date createDate, Date modifiedDate, int start, int end,
		OrderByComparator<User> orderByComparator) {

		return findByC_CD_MD(
			companyId, createDate, modifiedDate, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the users where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByC_CD_MD(
		long companyId, Date createDate, Date modifiedDate, int start, int end,
		OrderByComparator<User> orderByComparator, boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_CD_MD;
				finderArgs = new Object[] {
					companyId, _getTime(createDate), _getTime(modifiedDate)
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_CD_MD;
			finderArgs = new Object[] {
				companyId, _getTime(createDate), _getTime(modifiedDate), start,
				end, orderByComparator
			};
		}

		List<User> list = null;

		if (useFinderCache && productionMode) {
			list = (List<User>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (User user : list) {
					if ((companyId != user.getCompanyId()) ||
						!Objects.equals(createDate, user.getCreateDate()) ||
						!Objects.equals(modifiedDate, user.getModifiedDate())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_USER_WHERE);

			sb.append(_FINDER_COLUMN_C_CD_MD_COMPANYID_2);

			boolean bindCreateDate = false;

			if (createDate == null) {
				sb.append(_FINDER_COLUMN_C_CD_MD_CREATEDATE_1);
			}
			else {
				bindCreateDate = true;

				sb.append(_FINDER_COLUMN_C_CD_MD_CREATEDATE_2);
			}

			boolean bindModifiedDate = false;

			if (modifiedDate == null) {
				sb.append(_FINDER_COLUMN_C_CD_MD_MODIFIEDDATE_1);
			}
			else {
				bindModifiedDate = true;

				sb.append(_FINDER_COLUMN_C_CD_MD_MODIFIEDDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(UserModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindCreateDate) {
					queryPos.add(new Timestamp(createDate.getTime()));
				}

				if (bindModifiedDate) {
					queryPos.add(new Timestamp(modifiedDate.getTime()));
				}

				list = (List<User>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first user in the ordered set where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_CD_MD_First(
			long companyId, Date createDate, Date modifiedDate,
			OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {

		User user = fetchByC_CD_MD_First(
			companyId, createDate, modifiedDate, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", createDate=");
		sb.append(createDate);

		sb.append(", modifiedDate=");
		sb.append(modifiedDate);

		sb.append("}");

		throw new NoSuchUserException(sb.toString());
	}

	/**
	 * Returns the first user in the ordered set where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByC_CD_MD_First(
		long companyId, Date createDate, Date modifiedDate,
		OrderByComparator<User> orderByComparator) {

		List<User> list = findByC_CD_MD(
			companyId, createDate, modifiedDate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user in the ordered set where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_CD_MD_Last(
			long companyId, Date createDate, Date modifiedDate,
			OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {

		User user = fetchByC_CD_MD_Last(
			companyId, createDate, modifiedDate, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", createDate=");
		sb.append(createDate);

		sb.append(", modifiedDate=");
		sb.append(modifiedDate);

		sb.append("}");

		throw new NoSuchUserException(sb.toString());
	}

	/**
	 * Returns the last user in the ordered set where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByC_CD_MD_Last(
		long companyId, Date createDate, Date modifiedDate,
		OrderByComparator<User> orderByComparator) {

		int count = countByC_CD_MD(companyId, createDate, modifiedDate);

		if (count == 0) {
			return null;
		}

		List<User> list = findByC_CD_MD(
			companyId, createDate, modifiedDate, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the users before and after the current user in the ordered set where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	 *
	 * @param userId the primary key of the current user
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user
	 * @throws NoSuchUserException if a user with the primary key could not be found
	 */
	@Override
	public User[] findByC_CD_MD_PrevAndNext(
			long userId, long companyId, Date createDate, Date modifiedDate,
			OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {

		User user = findByPrimaryKey(userId);

		Session session = null;

		try {
			session = openSession();

			User[] array = new UserImpl[3];

			array[0] = getByC_CD_MD_PrevAndNext(
				session, user, companyId, createDate, modifiedDate,
				orderByComparator, true);

			array[1] = user;

			array[2] = getByC_CD_MD_PrevAndNext(
				session, user, companyId, createDate, modifiedDate,
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

	protected User getByC_CD_MD_PrevAndNext(
		Session session, User user, long companyId, Date createDate,
		Date modifiedDate, OrderByComparator<User> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_USER_WHERE);

		sb.append(_FINDER_COLUMN_C_CD_MD_COMPANYID_2);

		boolean bindCreateDate = false;

		if (createDate == null) {
			sb.append(_FINDER_COLUMN_C_CD_MD_CREATEDATE_1);
		}
		else {
			bindCreateDate = true;

			sb.append(_FINDER_COLUMN_C_CD_MD_CREATEDATE_2);
		}

		boolean bindModifiedDate = false;

		if (modifiedDate == null) {
			sb.append(_FINDER_COLUMN_C_CD_MD_MODIFIEDDATE_1);
		}
		else {
			bindModifiedDate = true;

			sb.append(_FINDER_COLUMN_C_CD_MD_MODIFIEDDATE_2);
		}

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
			sb.append(UserModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (bindCreateDate) {
			queryPos.add(new Timestamp(createDate.getTime()));
		}

		if (bindModifiedDate) {
			queryPos.add(new Timestamp(modifiedDate.getTime()));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(user)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<User> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the users where companyId = &#63; and createDate = &#63; and modifiedDate = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param modifiedDate the modified date
	 */
	@Override
	public void removeByC_CD_MD(
		long companyId, Date createDate, Date modifiedDate) {

		for (User user :
				findByC_CD_MD(
					companyId, createDate, modifiedDate, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(user);
		}
	}

	/**
	 * Returns the number of users where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param modifiedDate the modified date
	 * @return the number of matching users
	 */
	@Override
	public int countByC_CD_MD(
		long companyId, Date createDate, Date modifiedDate) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_CD_MD;

			finderArgs = new Object[] {
				companyId, _getTime(createDate), _getTime(modifiedDate)
			};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_USER_WHERE);

			sb.append(_FINDER_COLUMN_C_CD_MD_COMPANYID_2);

			boolean bindCreateDate = false;

			if (createDate == null) {
				sb.append(_FINDER_COLUMN_C_CD_MD_CREATEDATE_1);
			}
			else {
				bindCreateDate = true;

				sb.append(_FINDER_COLUMN_C_CD_MD_CREATEDATE_2);
			}

			boolean bindModifiedDate = false;

			if (modifiedDate == null) {
				sb.append(_FINDER_COLUMN_C_CD_MD_MODIFIEDDATE_1);
			}
			else {
				bindModifiedDate = true;

				sb.append(_FINDER_COLUMN_C_CD_MD_MODIFIEDDATE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindCreateDate) {
					queryPos.add(new Timestamp(createDate.getTime()));
				}

				if (bindModifiedDate) {
					queryPos.add(new Timestamp(modifiedDate.getTime()));
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_C_CD_MD_COMPANYID_2 =
		"user.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_CD_MD_CREATEDATE_1 =
		"user.createDate IS NULL AND ";

	private static final String _FINDER_COLUMN_C_CD_MD_CREATEDATE_2 =
		"user.createDate = ? AND ";

	private static final String _FINDER_COLUMN_C_CD_MD_MODIFIEDDATE_1 =
		"user.modifiedDate IS NULL AND type_ = 1";

	private static final String _FINDER_COLUMN_C_CD_MD_MODIFIEDDATE_2 =
		"user.modifiedDate = ? AND type_ = 1";

	private FinderPath _finderPathWithPaginationFindByC_T_S;
	private FinderPath _finderPathWithoutPaginationFindByC_T_S;
	private FinderPath _finderPathCountByC_T_S;

	/**
	 * Returns all the users where companyId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param status the status
	 * @return the matching users
	 */
	@Override
	public List<User> findByC_T_S(long companyId, int type, int status) {
		return findByC_T_S(
			companyId, type, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the users where companyId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of matching users
	 */
	@Override
	public List<User> findByC_T_S(
		long companyId, int type, int status, int start, int end) {

		return findByC_T_S(companyId, type, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the users where companyId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByC_T_S(
		long companyId, int type, int status, int start, int end,
		OrderByComparator<User> orderByComparator) {

		return findByC_T_S(
			companyId, type, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the users where companyId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByC_T_S(
		long companyId, int type, int status, int start, int end,
		OrderByComparator<User> orderByComparator, boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_T_S;
				finderArgs = new Object[] {companyId, type, status};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_T_S;
			finderArgs = new Object[] {
				companyId, type, status, start, end, orderByComparator
			};
		}

		List<User> list = null;

		if (useFinderCache && productionMode) {
			list = (List<User>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (User user : list) {
					if ((companyId != user.getCompanyId()) ||
						(type != user.getType()) ||
						(status != user.getStatus())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_USER_WHERE);

			sb.append(_FINDER_COLUMN_C_T_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_T_S_TYPE_2);

			sb.append(_FINDER_COLUMN_C_T_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(UserModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(type);

				queryPos.add(status);

				list = (List<User>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first user in the ordered set where companyId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_T_S_First(
			long companyId, int type, int status,
			OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {

		User user = fetchByC_T_S_First(
			companyId, type, status, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", type=");
		sb.append(type);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchUserException(sb.toString());
	}

	/**
	 * Returns the first user in the ordered set where companyId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByC_T_S_First(
		long companyId, int type, int status,
		OrderByComparator<User> orderByComparator) {

		List<User> list = findByC_T_S(
			companyId, type, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user in the ordered set where companyId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_T_S_Last(
			long companyId, int type, int status,
			OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {

		User user = fetchByC_T_S_Last(
			companyId, type, status, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", type=");
		sb.append(type);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchUserException(sb.toString());
	}

	/**
	 * Returns the last user in the ordered set where companyId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByC_T_S_Last(
		long companyId, int type, int status,
		OrderByComparator<User> orderByComparator) {

		int count = countByC_T_S(companyId, type, status);

		if (count == 0) {
			return null;
		}

		List<User> list = findByC_T_S(
			companyId, type, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the users before and after the current user in the ordered set where companyId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param userId the primary key of the current user
	 * @param companyId the company ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user
	 * @throws NoSuchUserException if a user with the primary key could not be found
	 */
	@Override
	public User[] findByC_T_S_PrevAndNext(
			long userId, long companyId, int type, int status,
			OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {

		User user = findByPrimaryKey(userId);

		Session session = null;

		try {
			session = openSession();

			User[] array = new UserImpl[3];

			array[0] = getByC_T_S_PrevAndNext(
				session, user, companyId, type, status, orderByComparator,
				true);

			array[1] = user;

			array[2] = getByC_T_S_PrevAndNext(
				session, user, companyId, type, status, orderByComparator,
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

	protected User getByC_T_S_PrevAndNext(
		Session session, User user, long companyId, int type, int status,
		OrderByComparator<User> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_USER_WHERE);

		sb.append(_FINDER_COLUMN_C_T_S_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_T_S_TYPE_2);

		sb.append(_FINDER_COLUMN_C_T_S_STATUS_2);

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
			sb.append(UserModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(type);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(user)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<User> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the users where companyId = &#63; and type = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param status the status
	 */
	@Override
	public void removeByC_T_S(long companyId, int type, int status) {
		for (User user :
				findByC_T_S(
					companyId, type, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(user);
		}
	}

	/**
	 * Returns the number of users where companyId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param status the status
	 * @return the number of matching users
	 */
	@Override
	public int countByC_T_S(long companyId, int type, int status) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_T_S;

			finderArgs = new Object[] {companyId, type, status};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_USER_WHERE);

			sb.append(_FINDER_COLUMN_C_T_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_T_S_TYPE_2);

			sb.append(_FINDER_COLUMN_C_T_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(type);

				queryPos.add(status);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_C_T_S_COMPANYID_2 =
		"user.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_T_S_TYPE_2 =
		"user.type = ? AND ";

	private static final String _FINDER_COLUMN_C_T_S_STATUS_2 =
		"user.status = ?";

	private FinderPath _finderPathFetchByERC_C;
	private FinderPath _finderPathCountByERC_C;

	/**
	 * Returns the user where externalReferenceCode = &#63; and companyId = &#63; or throws a <code>NoSuchUserException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByERC_C(String externalReferenceCode, long companyId)
		throws NoSuchUserException {

		User user = fetchByERC_C(externalReferenceCode, companyId);

		if (user == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("externalReferenceCode=");
			sb.append(externalReferenceCode);

			sb.append(", companyId=");
			sb.append(companyId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchUserException(sb.toString());
		}

		return user;
	}

	/**
	 * Returns the user where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByERC_C(String externalReferenceCode, long companyId) {
		return fetchByERC_C(externalReferenceCode, companyId, true);
	}

	/**
	 * Returns the user where externalReferenceCode = &#63; and companyId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByERC_C(
		String externalReferenceCode, long companyId, boolean useFinderCache) {

		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {externalReferenceCode, companyId};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByERC_C, finderArgs, this);
		}

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		if (result instanceof User) {
			User user = (User)result;

			if (!Objects.equals(
					externalReferenceCode, user.getExternalReferenceCode()) ||
				(companyId != user.getCompanyId())) {

				result = null;
			}
			else if (!CTPersistenceHelperUtil.isProductionMode(
						User.class, user.getPrimaryKey())) {

				result = null;
			}
		}
		else if (!productionMode && (result instanceof List<?>)) {
			result = null;
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_USER_WHERE);

			boolean bindExternalReferenceCode = false;

			if (externalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_ERC_C_EXTERNALREFERENCECODE_3);
			}
			else {
				bindExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_ERC_C_EXTERNALREFERENCECODE_2);
			}

			sb.append(_FINDER_COLUMN_ERC_C_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindExternalReferenceCode) {
					queryPos.add(externalReferenceCode);
				}

				queryPos.add(companyId);

				List<User> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						FinderCacheUtil.putResult(
							_finderPathFetchByERC_C, finderArgs, list);
					}
				}
				else {
					User user = list.get(0);

					result = user;

					cacheResult(user);
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
			return (User)result;
		}
	}

	/**
	 * Removes the user where externalReferenceCode = &#63; and companyId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the user that was removed
	 */
	@Override
	public User removeByERC_C(String externalReferenceCode, long companyId)
		throws NoSuchUserException {

		User user = findByERC_C(externalReferenceCode, companyId);

		return remove(user);
	}

	/**
	 * Returns the number of users where externalReferenceCode = &#63; and companyId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param companyId the company ID
	 * @return the number of matching users
	 */
	@Override
	public int countByERC_C(String externalReferenceCode, long companyId) {
		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByERC_C;

			finderArgs = new Object[] {externalReferenceCode, companyId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_USER_WHERE);

			boolean bindExternalReferenceCode = false;

			if (externalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_ERC_C_EXTERNALREFERENCECODE_3);
			}
			else {
				bindExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_ERC_C_EXTERNALREFERENCECODE_2);
			}

			sb.append(_FINDER_COLUMN_ERC_C_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindExternalReferenceCode) {
					queryPos.add(externalReferenceCode);
				}

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_ERC_C_EXTERNALREFERENCECODE_2 =
		"user.externalReferenceCode = ? AND ";

	private static final String _FINDER_COLUMN_ERC_C_EXTERNALREFERENCECODE_3 =
		"(user.externalReferenceCode IS NULL OR user.externalReferenceCode = '') AND ";

	private static final String _FINDER_COLUMN_ERC_C_COMPANYID_2 =
		"user.companyId = ?";

	public UserPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("password", "password_");
		dbColumnNames.put("type", "type_");
		dbColumnNames.put("groups", "groups_");

		setDBColumnNames(dbColumnNames);

		setModelClass(User.class);

		setModelImplClass(UserImpl.class);
		setModelPKClass(long.class);

		setTable(UserTable.INSTANCE);
	}

	/**
	 * Caches the user in the entity cache if it is enabled.
	 *
	 * @param user the user
	 */
	@Override
	public void cacheResult(User user) {
		if (user.getCtCollectionId() != 0) {
			return;
		}

		EntityCacheUtil.putResult(UserImpl.class, user.getPrimaryKey(), user);

		FinderCacheUtil.putResult(
			_finderPathFetchByContactId, new Object[] {user.getContactId()},
			user);

		FinderCacheUtil.putResult(
			_finderPathFetchByPortraitId, new Object[] {user.getPortraitId()},
			user);

		FinderCacheUtil.putResult(
			_finderPathFetchByC_U,
			new Object[] {user.getCompanyId(), user.getUserId()}, user);

		FinderCacheUtil.putResult(
			_finderPathFetchByC_SN,
			new Object[] {user.getCompanyId(), user.getScreenName()}, user);

		FinderCacheUtil.putResult(
			_finderPathFetchByC_EA,
			new Object[] {user.getCompanyId(), user.getEmailAddress()}, user);

		FinderCacheUtil.putResult(
			_finderPathFetchByC_FID,
			new Object[] {user.getCompanyId(), user.getFacebookId()}, user);

		FinderCacheUtil.putResult(
			_finderPathFetchByC_GUID,
			new Object[] {user.getCompanyId(), user.getGoogleUserId()}, user);

		FinderCacheUtil.putResult(
			_finderPathFetchByC_O,
			new Object[] {user.getCompanyId(), user.getOpenId()}, user);

		FinderCacheUtil.putResult(
			_finderPathFetchByERC_C,
			new Object[] {user.getExternalReferenceCode(), user.getCompanyId()},
			user);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the users in the entity cache if it is enabled.
	 *
	 * @param users the users
	 */
	@Override
	public void cacheResult(List<User> users) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (users.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (User user : users) {
			if (user.getCtCollectionId() != 0) {
				continue;
			}

			if (EntityCacheUtil.getResult(
					UserImpl.class, user.getPrimaryKey()) == null) {

				cacheResult(user);
			}
		}
	}

	/**
	 * Clears the cache for all users.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(UserImpl.class);

		FinderCacheUtil.clearCache(UserImpl.class);
	}

	/**
	 * Clears the cache for the user.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(User user) {
		EntityCacheUtil.removeResult(UserImpl.class, user);
	}

	@Override
	public void clearCache(List<User> users) {
		for (User user : users) {
			EntityCacheUtil.removeResult(UserImpl.class, user);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(UserImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(UserImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(UserModelImpl userModelImpl) {
		Object[] args = new Object[] {userModelImpl.getContactId()};

		FinderCacheUtil.putResult(
			_finderPathCountByContactId, args, Long.valueOf(1));
		FinderCacheUtil.putResult(
			_finderPathFetchByContactId, args, userModelImpl);

		args = new Object[] {userModelImpl.getPortraitId()};

		FinderCacheUtil.putResult(
			_finderPathCountByPortraitId, args, Long.valueOf(1));
		FinderCacheUtil.putResult(
			_finderPathFetchByPortraitId, args, userModelImpl);

		args = new Object[] {
			userModelImpl.getCompanyId(), userModelImpl.getUserId()
		};

		FinderCacheUtil.putResult(_finderPathCountByC_U, args, Long.valueOf(1));
		FinderCacheUtil.putResult(_finderPathFetchByC_U, args, userModelImpl);

		args = new Object[] {
			userModelImpl.getCompanyId(), userModelImpl.getScreenName()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByC_SN, args, Long.valueOf(1));
		FinderCacheUtil.putResult(_finderPathFetchByC_SN, args, userModelImpl);

		args = new Object[] {
			userModelImpl.getCompanyId(), userModelImpl.getEmailAddress()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByC_EA, args, Long.valueOf(1));
		FinderCacheUtil.putResult(_finderPathFetchByC_EA, args, userModelImpl);

		args = new Object[] {
			userModelImpl.getCompanyId(), userModelImpl.getFacebookId()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByC_FID, args, Long.valueOf(1));
		FinderCacheUtil.putResult(_finderPathFetchByC_FID, args, userModelImpl);

		args = new Object[] {
			userModelImpl.getCompanyId(), userModelImpl.getGoogleUserId()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByC_GUID, args, Long.valueOf(1));
		FinderCacheUtil.putResult(
			_finderPathFetchByC_GUID, args, userModelImpl);

		args = new Object[] {
			userModelImpl.getCompanyId(), userModelImpl.getOpenId()
		};

		FinderCacheUtil.putResult(_finderPathCountByC_O, args, Long.valueOf(1));
		FinderCacheUtil.putResult(_finderPathFetchByC_O, args, userModelImpl);

		args = new Object[] {
			userModelImpl.getExternalReferenceCode(),
			userModelImpl.getCompanyId()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByERC_C, args, Long.valueOf(1));
		FinderCacheUtil.putResult(_finderPathFetchByERC_C, args, userModelImpl);
	}

	/**
	 * Creates a new user with the primary key. Does not add the user to the database.
	 *
	 * @param userId the primary key for the new user
	 * @return the new user
	 */
	@Override
	public User create(long userId) {
		User user = new UserImpl();

		user.setNew(true);
		user.setPrimaryKey(userId);

		String uuid = PortalUUIDUtil.generate();

		user.setUuid(uuid);

		user.setCompanyId(CompanyThreadLocal.getCompanyId());

		return user;
	}

	/**
	 * Removes the user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param userId the primary key of the user
	 * @return the user that was removed
	 * @throws NoSuchUserException if a user with the primary key could not be found
	 */
	@Override
	public User remove(long userId) throws NoSuchUserException {
		return remove((Serializable)userId);
	}

	/**
	 * Removes the user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the user
	 * @return the user that was removed
	 * @throws NoSuchUserException if a user with the primary key could not be found
	 */
	@Override
	public User remove(Serializable primaryKey) throws NoSuchUserException {
		Session session = null;

		try {
			session = openSession();

			User user = (User)session.get(UserImpl.class, primaryKey);

			if (user == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchUserException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(user);
		}
		catch (NoSuchUserException noSuchEntityException) {
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
	protected User removeImpl(User user) {
		userToGroupTableMapper.deleteLeftPrimaryKeyTableMappings(
			user.getPrimaryKey());

		userToOrganizationTableMapper.deleteLeftPrimaryKeyTableMappings(
			user.getPrimaryKey());

		userToRoleTableMapper.deleteLeftPrimaryKeyTableMappings(
			user.getPrimaryKey());

		userToTeamTableMapper.deleteLeftPrimaryKeyTableMappings(
			user.getPrimaryKey());

		userToUserGroupTableMapper.deleteLeftPrimaryKeyTableMappings(
			user.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(user)) {
				user = (User)session.get(
					UserImpl.class, user.getPrimaryKeyObj());
			}

			if ((user != null) && CTPersistenceHelperUtil.isRemove(user)) {
				session.delete(user);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (user != null) {
			clearCache(user);
		}

		return user;
	}

	@Override
	public User updateImpl(User user) {
		boolean isNew = user.isNew();

		if (!(user instanceof UserModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(user.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(user);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in user proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom User implementation " +
					user.getClass());
		}

		UserModelImpl userModelImpl = (UserModelImpl)user;

		if (Validator.isNull(user.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			user.setUuid(uuid);
		}

		if (Validator.isNull(user.getExternalReferenceCode())) {
			user.setExternalReferenceCode(user.getUuid());
		}
		else {
			User ercUser = fetchByERC_C(
				user.getExternalReferenceCode(), user.getCompanyId());

			if (isNew) {
				if (ercUser != null) {
					throw new DuplicateUserExternalReferenceCodeException(
						"Duplicate user with external reference code " +
							user.getExternalReferenceCode() + " and company " +
								user.getCompanyId());
				}
			}
			else {
				if ((ercUser != null) &&
					(user.getUserId() != ercUser.getUserId())) {

					throw new DuplicateUserExternalReferenceCodeException(
						"Duplicate user with external reference code " +
							user.getExternalReferenceCode() + " and company " +
								user.getCompanyId());
				}
			}
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (user.getCreateDate() == null)) {
			if (serviceContext == null) {
				user.setCreateDate(date);
			}
			else {
				user.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!userModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				user.setModifiedDate(date);
			}
			else {
				user.setModifiedDate(serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (CTPersistenceHelperUtil.isInsert(user)) {
				if (!isNew) {
					session.evict(UserImpl.class, user.getPrimaryKeyObj());
				}

				session.save(user);
			}
			else {
				user = (User)session.merge(user);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (user.getCtCollectionId() != 0) {
			if (isNew) {
				user.setNew(false);
			}

			user.resetOriginalValues();

			return user;
		}

		EntityCacheUtil.putResult(UserImpl.class, userModelImpl, false, true);

		cacheUniqueFindersCache(userModelImpl);

		if (isNew) {
			user.setNew(false);
		}

		user.resetOriginalValues();

		return user;
	}

	/**
	 * Returns the user with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the user
	 * @return the user
	 * @throws NoSuchUserException if a user with the primary key could not be found
	 */
	@Override
	public User findByPrimaryKey(Serializable primaryKey)
		throws NoSuchUserException {

		User user = fetchByPrimaryKey(primaryKey);

		if (user == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchUserException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return user;
	}

	/**
	 * Returns the user with the primary key or throws a <code>NoSuchUserException</code> if it could not be found.
	 *
	 * @param userId the primary key of the user
	 * @return the user
	 * @throws NoSuchUserException if a user with the primary key could not be found
	 */
	@Override
	public User findByPrimaryKey(long userId) throws NoSuchUserException {
		return findByPrimaryKey((Serializable)userId);
	}

	/**
	 * Returns the user with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the user
	 * @return the user, or <code>null</code> if a user with the primary key could not be found
	 */
	@Override
	public User fetchByPrimaryKey(Serializable primaryKey) {
		if (CTPersistenceHelperUtil.isProductionMode(User.class, primaryKey)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		User user = null;

		Session session = null;

		try {
			session = openSession();

			user = (User)session.get(UserImpl.class, primaryKey);

			if (user != null) {
				cacheResult(user);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return user;
	}

	/**
	 * Returns the user with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param userId the primary key of the user
	 * @return the user, or <code>null</code> if a user with the primary key could not be found
	 */
	@Override
	public User fetchByPrimaryKey(long userId) {
		return fetchByPrimaryKey((Serializable)userId);
	}

	@Override
	public Map<Serializable, User> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (CTPersistenceHelperUtil.isProductionMode(User.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, User> map = new HashMap<Serializable, User>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			User user = fetchByPrimaryKey(primaryKey);

			if (user != null) {
				map.put(primaryKey, user);
			}

			return map;
		}

		if ((databaseInMaxParameters > 0) &&
			(primaryKeys.size() > databaseInMaxParameters)) {

			Iterator<Serializable> iterator = primaryKeys.iterator();

			while (iterator.hasNext()) {
				Set<Serializable> page = new HashSet<>();

				for (int i = 0;
					 (i < databaseInMaxParameters) && iterator.hasNext(); i++) {

					page.add(iterator.next());
				}

				map.putAll(fetchByPrimaryKeys(page));
			}

			return map;
		}

		StringBundler sb = new StringBundler((primaryKeys.size() * 2) + 1);

		sb.append(getSelectSQL());
		sb.append(" WHERE ");
		sb.append(getPKDBName());
		sb.append(" IN (");

		for (Serializable primaryKey : primaryKeys) {
			sb.append((long)primaryKey);

			sb.append(",");
		}

		sb.setIndex(sb.index() - 1);

		sb.append(")");

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query query = session.createQuery(sql);

			for (User user : (List<User>)query.list()) {
				map.put(user.getPrimaryKeyObj(), user);

				cacheResult(user);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the users.
	 *
	 * @return the users
	 */
	@Override
	public List<User> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of users
	 */
	@Override
	public List<User> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of users
	 */
	@Override
	public List<User> findAll(
		int start, int end, OrderByComparator<User> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of users
	 */
	@Override
	public List<User> findAll(
		int start, int end, OrderByComparator<User> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<User> list = null;

		if (useFinderCache && productionMode) {
			list = (List<User>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_USER);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_USER;

				sql = sql.concat(UserModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<User>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Removes all the users from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (User user : findAll()) {
			remove(user);
		}
	}

	/**
	 * Returns the number of users.
	 *
	 * @return the number of users
	 */
	@Override
	public int countAll() {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			User.class);

		Long count = null;

		if (productionMode) {
			count = (Long)FinderCacheUtil.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_USER);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(
						_finderPathCountAll, FINDER_ARGS_EMPTY, count);
				}
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

	/**
	 * Returns the primaryKeys of groups associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return long[] of the primaryKeys of groups associated with the user
	 */
	@Override
	public long[] getGroupPrimaryKeys(long pk) {
		long[] pks = userToGroupTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the groups associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the groups associated with the user
	 */
	@Override
	public List<com.liferay.portal.kernel.model.Group> getGroups(long pk) {
		return getGroups(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the groups associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of groups associated with the user
	 */
	@Override
	public List<com.liferay.portal.kernel.model.Group> getGroups(
		long pk, int start, int end) {

		return getGroups(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of groups associated with the user
	 */
	@Override
	public List<com.liferay.portal.kernel.model.Group> getGroups(
		long pk, int start, int end,
		OrderByComparator<com.liferay.portal.kernel.model.Group>
			orderByComparator) {

		return userToGroupTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of groups associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the number of groups associated with the user
	 */
	@Override
	public int getGroupsSize(long pk) {
		long[] pks = userToGroupTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the group is associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @param groupPK the primary key of the group
	 * @return <code>true</code> if the group is associated with the user; <code>false</code> otherwise
	 */
	@Override
	public boolean containsGroup(long pk, long groupPK) {
		return userToGroupTableMapper.containsTableMapping(pk, groupPK);
	}

	/**
	 * Returns <code>true</code> if the user has any groups associated with it.
	 *
	 * @param pk the primary key of the user to check for associations with groups
	 * @return <code>true</code> if the user has any groups associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsGroups(long pk) {
		if (getGroupsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the user and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param groupPK the primary key of the group
	 * @return <code>true</code> if an association between the user and the group was added; <code>false</code> if they were already associated
	 */
	@Override
	public boolean addGroup(long pk, long groupPK) {
		User user = fetchByPrimaryKey(pk);

		if (user == null) {
			return userToGroupTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, groupPK);
		}
		else {
			return userToGroupTableMapper.addTableMapping(
				user.getCompanyId(), pk, groupPK);
		}
	}

	/**
	 * Adds an association between the user and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param group the group
	 * @return <code>true</code> if an association between the user and the group was added; <code>false</code> if they were already associated
	 */
	@Override
	public boolean addGroup(
		long pk, com.liferay.portal.kernel.model.Group group) {

		User user = fetchByPrimaryKey(pk);

		if (user == null) {
			return userToGroupTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, group.getPrimaryKey());
		}
		else {
			return userToGroupTableMapper.addTableMapping(
				user.getCompanyId(), pk, group.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the user and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param groupPKs the primary keys of the groups
	 * @return <code>true</code> if at least one association between the user and the groups was added; <code>false</code> if they were all already associated
	 */
	@Override
	public boolean addGroups(long pk, long[] groupPKs) {
		long companyId = 0;

		User user = fetchByPrimaryKey(pk);

		if (user == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = user.getCompanyId();
		}

		long[] addedKeys = userToGroupTableMapper.addTableMappings(
			companyId, pk, groupPKs);

		if (addedKeys.length > 0) {
			return true;
		}

		return false;
	}

	/**
	 * Adds an association between the user and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param groups the groups
	 * @return <code>true</code> if at least one association between the user and the groups was added; <code>false</code> if they were all already associated
	 */
	@Override
	public boolean addGroups(
		long pk, List<com.liferay.portal.kernel.model.Group> groups) {

		return addGroups(
			pk,
			ListUtil.toLongArray(
				groups,
				com.liferay.portal.kernel.model.Group.GROUP_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the user and its groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user to clear the associated groups from
	 */
	@Override
	public void clearGroups(long pk) {
		userToGroupTableMapper.deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the user and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param groupPK the primary key of the group
	 */
	@Override
	public void removeGroup(long pk, long groupPK) {
		userToGroupTableMapper.deleteTableMapping(pk, groupPK);
	}

	/**
	 * Removes the association between the user and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param group the group
	 */
	@Override
	public void removeGroup(
		long pk, com.liferay.portal.kernel.model.Group group) {

		userToGroupTableMapper.deleteTableMapping(pk, group.getPrimaryKey());
	}

	/**
	 * Removes the association between the user and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param groupPKs the primary keys of the groups
	 */
	@Override
	public void removeGroups(long pk, long[] groupPKs) {
		userToGroupTableMapper.deleteTableMappings(pk, groupPKs);
	}

	/**
	 * Removes the association between the user and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param groups the groups
	 */
	@Override
	public void removeGroups(
		long pk, List<com.liferay.portal.kernel.model.Group> groups) {

		removeGroups(
			pk,
			ListUtil.toLongArray(
				groups,
				com.liferay.portal.kernel.model.Group.GROUP_ID_ACCESSOR));
	}

	/**
	 * Sets the groups associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param groupPKs the primary keys of the groups to be associated with the user
	 */
	@Override
	public void setGroups(long pk, long[] groupPKs) {
		Set<Long> newGroupPKsSet = SetUtil.fromArray(groupPKs);
		Set<Long> oldGroupPKsSet = SetUtil.fromArray(
			userToGroupTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeGroupPKsSet = new HashSet<Long>(oldGroupPKsSet);

		removeGroupPKsSet.removeAll(newGroupPKsSet);

		userToGroupTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeGroupPKsSet));

		newGroupPKsSet.removeAll(oldGroupPKsSet);

		long companyId = 0;

		User user = fetchByPrimaryKey(pk);

		if (user == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = user.getCompanyId();
		}

		userToGroupTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newGroupPKsSet));
	}

	/**
	 * Sets the groups associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param groups the groups to be associated with the user
	 */
	@Override
	public void setGroups(
		long pk, List<com.liferay.portal.kernel.model.Group> groups) {

		try {
			long[] groupPKs = new long[groups.size()];

			for (int i = 0; i < groups.size(); i++) {
				com.liferay.portal.kernel.model.Group group = groups.get(i);

				groupPKs[i] = group.getPrimaryKey();
			}

			setGroups(pk, groupPKs);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
	}

	/**
	 * Returns the primaryKeys of organizations associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return long[] of the primaryKeys of organizations associated with the user
	 */
	@Override
	public long[] getOrganizationPrimaryKeys(long pk) {
		long[] pks = userToOrganizationTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the organizations associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the organizations associated with the user
	 */
	@Override
	public List<com.liferay.portal.kernel.model.Organization> getOrganizations(
		long pk) {

		return getOrganizations(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the organizations associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of organizations associated with the user
	 */
	@Override
	public List<com.liferay.portal.kernel.model.Organization> getOrganizations(
		long pk, int start, int end) {

		return getOrganizations(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the organizations associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of organizations associated with the user
	 */
	@Override
	public List<com.liferay.portal.kernel.model.Organization> getOrganizations(
		long pk, int start, int end,
		OrderByComparator<com.liferay.portal.kernel.model.Organization>
			orderByComparator) {

		return userToOrganizationTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of organizations associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the number of organizations associated with the user
	 */
	@Override
	public int getOrganizationsSize(long pk) {
		long[] pks = userToOrganizationTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the organization is associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @param organizationPK the primary key of the organization
	 * @return <code>true</code> if the organization is associated with the user; <code>false</code> otherwise
	 */
	@Override
	public boolean containsOrganization(long pk, long organizationPK) {
		return userToOrganizationTableMapper.containsTableMapping(
			pk, organizationPK);
	}

	/**
	 * Returns <code>true</code> if the user has any organizations associated with it.
	 *
	 * @param pk the primary key of the user to check for associations with organizations
	 * @return <code>true</code> if the user has any organizations associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsOrganizations(long pk) {
		if (getOrganizationsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the user and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organizationPK the primary key of the organization
	 * @return <code>true</code> if an association between the user and the organization was added; <code>false</code> if they were already associated
	 */
	@Override
	public boolean addOrganization(long pk, long organizationPK) {
		User user = fetchByPrimaryKey(pk);

		if (user == null) {
			return userToOrganizationTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, organizationPK);
		}
		else {
			return userToOrganizationTableMapper.addTableMapping(
				user.getCompanyId(), pk, organizationPK);
		}
	}

	/**
	 * Adds an association between the user and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organization the organization
	 * @return <code>true</code> if an association between the user and the organization was added; <code>false</code> if they were already associated
	 */
	@Override
	public boolean addOrganization(
		long pk, com.liferay.portal.kernel.model.Organization organization) {

		User user = fetchByPrimaryKey(pk);

		if (user == null) {
			return userToOrganizationTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk,
				organization.getPrimaryKey());
		}
		else {
			return userToOrganizationTableMapper.addTableMapping(
				user.getCompanyId(), pk, organization.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the user and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organizationPKs the primary keys of the organizations
	 * @return <code>true</code> if at least one association between the user and the organizations was added; <code>false</code> if they were all already associated
	 */
	@Override
	public boolean addOrganizations(long pk, long[] organizationPKs) {
		long companyId = 0;

		User user = fetchByPrimaryKey(pk);

		if (user == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = user.getCompanyId();
		}

		long[] addedKeys = userToOrganizationTableMapper.addTableMappings(
			companyId, pk, organizationPKs);

		if (addedKeys.length > 0) {
			return true;
		}

		return false;
	}

	/**
	 * Adds an association between the user and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organizations the organizations
	 * @return <code>true</code> if at least one association between the user and the organizations was added; <code>false</code> if they were all already associated
	 */
	@Override
	public boolean addOrganizations(
		long pk,
		List<com.liferay.portal.kernel.model.Organization> organizations) {

		return addOrganizations(
			pk,
			ListUtil.toLongArray(
				organizations,
				com.liferay.portal.kernel.model.Organization.
					ORGANIZATION_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the user and its organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user to clear the associated organizations from
	 */
	@Override
	public void clearOrganizations(long pk) {
		userToOrganizationTableMapper.deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the user and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organizationPK the primary key of the organization
	 */
	@Override
	public void removeOrganization(long pk, long organizationPK) {
		userToOrganizationTableMapper.deleteTableMapping(pk, organizationPK);
	}

	/**
	 * Removes the association between the user and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organization the organization
	 */
	@Override
	public void removeOrganization(
		long pk, com.liferay.portal.kernel.model.Organization organization) {

		userToOrganizationTableMapper.deleteTableMapping(
			pk, organization.getPrimaryKey());
	}

	/**
	 * Removes the association between the user and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organizationPKs the primary keys of the organizations
	 */
	@Override
	public void removeOrganizations(long pk, long[] organizationPKs) {
		userToOrganizationTableMapper.deleteTableMappings(pk, organizationPKs);
	}

	/**
	 * Removes the association between the user and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organizations the organizations
	 */
	@Override
	public void removeOrganizations(
		long pk,
		List<com.liferay.portal.kernel.model.Organization> organizations) {

		removeOrganizations(
			pk,
			ListUtil.toLongArray(
				organizations,
				com.liferay.portal.kernel.model.Organization.
					ORGANIZATION_ID_ACCESSOR));
	}

	/**
	 * Sets the organizations associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organizationPKs the primary keys of the organizations to be associated with the user
	 */
	@Override
	public void setOrganizations(long pk, long[] organizationPKs) {
		Set<Long> newOrganizationPKsSet = SetUtil.fromArray(organizationPKs);
		Set<Long> oldOrganizationPKsSet = SetUtil.fromArray(
			userToOrganizationTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeOrganizationPKsSet = new HashSet<Long>(
			oldOrganizationPKsSet);

		removeOrganizationPKsSet.removeAll(newOrganizationPKsSet);

		userToOrganizationTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeOrganizationPKsSet));

		newOrganizationPKsSet.removeAll(oldOrganizationPKsSet);

		long companyId = 0;

		User user = fetchByPrimaryKey(pk);

		if (user == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = user.getCompanyId();
		}

		userToOrganizationTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newOrganizationPKsSet));
	}

	/**
	 * Sets the organizations associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organizations the organizations to be associated with the user
	 */
	@Override
	public void setOrganizations(
		long pk,
		List<com.liferay.portal.kernel.model.Organization> organizations) {

		try {
			long[] organizationPKs = new long[organizations.size()];

			for (int i = 0; i < organizations.size(); i++) {
				com.liferay.portal.kernel.model.Organization organization =
					organizations.get(i);

				organizationPKs[i] = organization.getPrimaryKey();
			}

			setOrganizations(pk, organizationPKs);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
	}

	/**
	 * Returns the primaryKeys of roles associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return long[] of the primaryKeys of roles associated with the user
	 */
	@Override
	public long[] getRolePrimaryKeys(long pk) {
		long[] pks = userToRoleTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the roles associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the roles associated with the user
	 */
	@Override
	public List<com.liferay.portal.kernel.model.Role> getRoles(long pk) {
		return getRoles(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the roles associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of roles associated with the user
	 */
	@Override
	public List<com.liferay.portal.kernel.model.Role> getRoles(
		long pk, int start, int end) {

		return getRoles(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the roles associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of roles associated with the user
	 */
	@Override
	public List<com.liferay.portal.kernel.model.Role> getRoles(
		long pk, int start, int end,
		OrderByComparator<com.liferay.portal.kernel.model.Role>
			orderByComparator) {

		return userToRoleTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of roles associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the number of roles associated with the user
	 */
	@Override
	public int getRolesSize(long pk) {
		long[] pks = userToRoleTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the role is associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @param rolePK the primary key of the role
	 * @return <code>true</code> if the role is associated with the user; <code>false</code> otherwise
	 */
	@Override
	public boolean containsRole(long pk, long rolePK) {
		return userToRoleTableMapper.containsTableMapping(pk, rolePK);
	}

	/**
	 * Returns <code>true</code> if the user has any roles associated with it.
	 *
	 * @param pk the primary key of the user to check for associations with roles
	 * @return <code>true</code> if the user has any roles associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsRoles(long pk) {
		if (getRolesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the user and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param rolePK the primary key of the role
	 * @return <code>true</code> if an association between the user and the role was added; <code>false</code> if they were already associated
	 */
	@Override
	public boolean addRole(long pk, long rolePK) {
		User user = fetchByPrimaryKey(pk);

		if (user == null) {
			return userToRoleTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, rolePK);
		}
		else {
			return userToRoleTableMapper.addTableMapping(
				user.getCompanyId(), pk, rolePK);
		}
	}

	/**
	 * Adds an association between the user and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param role the role
	 * @return <code>true</code> if an association between the user and the role was added; <code>false</code> if they were already associated
	 */
	@Override
	public boolean addRole(long pk, com.liferay.portal.kernel.model.Role role) {
		User user = fetchByPrimaryKey(pk);

		if (user == null) {
			return userToRoleTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, role.getPrimaryKey());
		}
		else {
			return userToRoleTableMapper.addTableMapping(
				user.getCompanyId(), pk, role.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the user and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param rolePKs the primary keys of the roles
	 * @return <code>true</code> if at least one association between the user and the roles was added; <code>false</code> if they were all already associated
	 */
	@Override
	public boolean addRoles(long pk, long[] rolePKs) {
		long companyId = 0;

		User user = fetchByPrimaryKey(pk);

		if (user == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = user.getCompanyId();
		}

		long[] addedKeys = userToRoleTableMapper.addTableMappings(
			companyId, pk, rolePKs);

		if (addedKeys.length > 0) {
			return true;
		}

		return false;
	}

	/**
	 * Adds an association between the user and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param roles the roles
	 * @return <code>true</code> if at least one association between the user and the roles was added; <code>false</code> if they were all already associated
	 */
	@Override
	public boolean addRoles(
		long pk, List<com.liferay.portal.kernel.model.Role> roles) {

		return addRoles(
			pk,
			ListUtil.toLongArray(
				roles, com.liferay.portal.kernel.model.Role.ROLE_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the user and its roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user to clear the associated roles from
	 */
	@Override
	public void clearRoles(long pk) {
		userToRoleTableMapper.deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the user and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param rolePK the primary key of the role
	 */
	@Override
	public void removeRole(long pk, long rolePK) {
		userToRoleTableMapper.deleteTableMapping(pk, rolePK);
	}

	/**
	 * Removes the association between the user and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param role the role
	 */
	@Override
	public void removeRole(long pk, com.liferay.portal.kernel.model.Role role) {
		userToRoleTableMapper.deleteTableMapping(pk, role.getPrimaryKey());
	}

	/**
	 * Removes the association between the user and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param rolePKs the primary keys of the roles
	 */
	@Override
	public void removeRoles(long pk, long[] rolePKs) {
		userToRoleTableMapper.deleteTableMappings(pk, rolePKs);
	}

	/**
	 * Removes the association between the user and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param roles the roles
	 */
	@Override
	public void removeRoles(
		long pk, List<com.liferay.portal.kernel.model.Role> roles) {

		removeRoles(
			pk,
			ListUtil.toLongArray(
				roles, com.liferay.portal.kernel.model.Role.ROLE_ID_ACCESSOR));
	}

	/**
	 * Sets the roles associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param rolePKs the primary keys of the roles to be associated with the user
	 */
	@Override
	public void setRoles(long pk, long[] rolePKs) {
		Set<Long> newRolePKsSet = SetUtil.fromArray(rolePKs);
		Set<Long> oldRolePKsSet = SetUtil.fromArray(
			userToRoleTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeRolePKsSet = new HashSet<Long>(oldRolePKsSet);

		removeRolePKsSet.removeAll(newRolePKsSet);

		userToRoleTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeRolePKsSet));

		newRolePKsSet.removeAll(oldRolePKsSet);

		long companyId = 0;

		User user = fetchByPrimaryKey(pk);

		if (user == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = user.getCompanyId();
		}

		userToRoleTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newRolePKsSet));
	}

	/**
	 * Sets the roles associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param roles the roles to be associated with the user
	 */
	@Override
	public void setRoles(
		long pk, List<com.liferay.portal.kernel.model.Role> roles) {

		try {
			long[] rolePKs = new long[roles.size()];

			for (int i = 0; i < roles.size(); i++) {
				com.liferay.portal.kernel.model.Role role = roles.get(i);

				rolePKs[i] = role.getPrimaryKey();
			}

			setRoles(pk, rolePKs);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
	}

	/**
	 * Returns the primaryKeys of teams associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return long[] of the primaryKeys of teams associated with the user
	 */
	@Override
	public long[] getTeamPrimaryKeys(long pk) {
		long[] pks = userToTeamTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the teams associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the teams associated with the user
	 */
	@Override
	public List<com.liferay.portal.kernel.model.Team> getTeams(long pk) {
		return getTeams(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the teams associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of teams associated with the user
	 */
	@Override
	public List<com.liferay.portal.kernel.model.Team> getTeams(
		long pk, int start, int end) {

		return getTeams(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the teams associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of teams associated with the user
	 */
	@Override
	public List<com.liferay.portal.kernel.model.Team> getTeams(
		long pk, int start, int end,
		OrderByComparator<com.liferay.portal.kernel.model.Team>
			orderByComparator) {

		return userToTeamTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of teams associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the number of teams associated with the user
	 */
	@Override
	public int getTeamsSize(long pk) {
		long[] pks = userToTeamTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the team is associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @param teamPK the primary key of the team
	 * @return <code>true</code> if the team is associated with the user; <code>false</code> otherwise
	 */
	@Override
	public boolean containsTeam(long pk, long teamPK) {
		return userToTeamTableMapper.containsTableMapping(pk, teamPK);
	}

	/**
	 * Returns <code>true</code> if the user has any teams associated with it.
	 *
	 * @param pk the primary key of the user to check for associations with teams
	 * @return <code>true</code> if the user has any teams associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsTeams(long pk) {
		if (getTeamsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the user and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param teamPK the primary key of the team
	 * @return <code>true</code> if an association between the user and the team was added; <code>false</code> if they were already associated
	 */
	@Override
	public boolean addTeam(long pk, long teamPK) {
		User user = fetchByPrimaryKey(pk);

		if (user == null) {
			return userToTeamTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, teamPK);
		}
		else {
			return userToTeamTableMapper.addTableMapping(
				user.getCompanyId(), pk, teamPK);
		}
	}

	/**
	 * Adds an association between the user and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param team the team
	 * @return <code>true</code> if an association between the user and the team was added; <code>false</code> if they were already associated
	 */
	@Override
	public boolean addTeam(long pk, com.liferay.portal.kernel.model.Team team) {
		User user = fetchByPrimaryKey(pk);

		if (user == null) {
			return userToTeamTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, team.getPrimaryKey());
		}
		else {
			return userToTeamTableMapper.addTableMapping(
				user.getCompanyId(), pk, team.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the user and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param teamPKs the primary keys of the teams
	 * @return <code>true</code> if at least one association between the user and the teams was added; <code>false</code> if they were all already associated
	 */
	@Override
	public boolean addTeams(long pk, long[] teamPKs) {
		long companyId = 0;

		User user = fetchByPrimaryKey(pk);

		if (user == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = user.getCompanyId();
		}

		long[] addedKeys = userToTeamTableMapper.addTableMappings(
			companyId, pk, teamPKs);

		if (addedKeys.length > 0) {
			return true;
		}

		return false;
	}

	/**
	 * Adds an association between the user and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param teams the teams
	 * @return <code>true</code> if at least one association between the user and the teams was added; <code>false</code> if they were all already associated
	 */
	@Override
	public boolean addTeams(
		long pk, List<com.liferay.portal.kernel.model.Team> teams) {

		return addTeams(
			pk,
			ListUtil.toLongArray(
				teams, com.liferay.portal.kernel.model.Team.TEAM_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the user and its teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user to clear the associated teams from
	 */
	@Override
	public void clearTeams(long pk) {
		userToTeamTableMapper.deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the user and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param teamPK the primary key of the team
	 */
	@Override
	public void removeTeam(long pk, long teamPK) {
		userToTeamTableMapper.deleteTableMapping(pk, teamPK);
	}

	/**
	 * Removes the association between the user and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param team the team
	 */
	@Override
	public void removeTeam(long pk, com.liferay.portal.kernel.model.Team team) {
		userToTeamTableMapper.deleteTableMapping(pk, team.getPrimaryKey());
	}

	/**
	 * Removes the association between the user and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param teamPKs the primary keys of the teams
	 */
	@Override
	public void removeTeams(long pk, long[] teamPKs) {
		userToTeamTableMapper.deleteTableMappings(pk, teamPKs);
	}

	/**
	 * Removes the association between the user and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param teams the teams
	 */
	@Override
	public void removeTeams(
		long pk, List<com.liferay.portal.kernel.model.Team> teams) {

		removeTeams(
			pk,
			ListUtil.toLongArray(
				teams, com.liferay.portal.kernel.model.Team.TEAM_ID_ACCESSOR));
	}

	/**
	 * Sets the teams associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param teamPKs the primary keys of the teams to be associated with the user
	 */
	@Override
	public void setTeams(long pk, long[] teamPKs) {
		Set<Long> newTeamPKsSet = SetUtil.fromArray(teamPKs);
		Set<Long> oldTeamPKsSet = SetUtil.fromArray(
			userToTeamTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeTeamPKsSet = new HashSet<Long>(oldTeamPKsSet);

		removeTeamPKsSet.removeAll(newTeamPKsSet);

		userToTeamTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeTeamPKsSet));

		newTeamPKsSet.removeAll(oldTeamPKsSet);

		long companyId = 0;

		User user = fetchByPrimaryKey(pk);

		if (user == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = user.getCompanyId();
		}

		userToTeamTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newTeamPKsSet));
	}

	/**
	 * Sets the teams associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param teams the teams to be associated with the user
	 */
	@Override
	public void setTeams(
		long pk, List<com.liferay.portal.kernel.model.Team> teams) {

		try {
			long[] teamPKs = new long[teams.size()];

			for (int i = 0; i < teams.size(); i++) {
				com.liferay.portal.kernel.model.Team team = teams.get(i);

				teamPKs[i] = team.getPrimaryKey();
			}

			setTeams(pk, teamPKs);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
	}

	/**
	 * Returns the primaryKeys of user groups associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return long[] of the primaryKeys of user groups associated with the user
	 */
	@Override
	public long[] getUserGroupPrimaryKeys(long pk) {
		long[] pks = userToUserGroupTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the user groups associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the user groups associated with the user
	 */
	@Override
	public List<com.liferay.portal.kernel.model.UserGroup> getUserGroups(
		long pk) {

		return getUserGroups(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the user groups associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of user groups associated with the user
	 */
	@Override
	public List<com.liferay.portal.kernel.model.UserGroup> getUserGroups(
		long pk, int start, int end) {

		return getUserGroups(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user groups associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>UserModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of user groups associated with the user
	 */
	@Override
	public List<com.liferay.portal.kernel.model.UserGroup> getUserGroups(
		long pk, int start, int end,
		OrderByComparator<com.liferay.portal.kernel.model.UserGroup>
			orderByComparator) {

		return userToUserGroupTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of user groups associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the number of user groups associated with the user
	 */
	@Override
	public int getUserGroupsSize(long pk) {
		long[] pks = userToUserGroupTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the user group is associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @param userGroupPK the primary key of the user group
	 * @return <code>true</code> if the user group is associated with the user; <code>false</code> otherwise
	 */
	@Override
	public boolean containsUserGroup(long pk, long userGroupPK) {
		return userToUserGroupTableMapper.containsTableMapping(pk, userGroupPK);
	}

	/**
	 * Returns <code>true</code> if the user has any user groups associated with it.
	 *
	 * @param pk the primary key of the user to check for associations with user groups
	 * @return <code>true</code> if the user has any user groups associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsUserGroups(long pk) {
		if (getUserGroupsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the user and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroupPK the primary key of the user group
	 * @return <code>true</code> if an association between the user and the user group was added; <code>false</code> if they were already associated
	 */
	@Override
	public boolean addUserGroup(long pk, long userGroupPK) {
		User user = fetchByPrimaryKey(pk);

		if (user == null) {
			return userToUserGroupTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, userGroupPK);
		}
		else {
			return userToUserGroupTableMapper.addTableMapping(
				user.getCompanyId(), pk, userGroupPK);
		}
	}

	/**
	 * Adds an association between the user and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroup the user group
	 * @return <code>true</code> if an association between the user and the user group was added; <code>false</code> if they were already associated
	 */
	@Override
	public boolean addUserGroup(
		long pk, com.liferay.portal.kernel.model.UserGroup userGroup) {

		User user = fetchByPrimaryKey(pk);

		if (user == null) {
			return userToUserGroupTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk,
				userGroup.getPrimaryKey());
		}
		else {
			return userToUserGroupTableMapper.addTableMapping(
				user.getCompanyId(), pk, userGroup.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the user and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroupPKs the primary keys of the user groups
	 * @return <code>true</code> if at least one association between the user and the user groups was added; <code>false</code> if they were all already associated
	 */
	@Override
	public boolean addUserGroups(long pk, long[] userGroupPKs) {
		long companyId = 0;

		User user = fetchByPrimaryKey(pk);

		if (user == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = user.getCompanyId();
		}

		long[] addedKeys = userToUserGroupTableMapper.addTableMappings(
			companyId, pk, userGroupPKs);

		if (addedKeys.length > 0) {
			return true;
		}

		return false;
	}

	/**
	 * Adds an association between the user and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroups the user groups
	 * @return <code>true</code> if at least one association between the user and the user groups was added; <code>false</code> if they were all already associated
	 */
	@Override
	public boolean addUserGroups(
		long pk, List<com.liferay.portal.kernel.model.UserGroup> userGroups) {

		return addUserGroups(
			pk,
			ListUtil.toLongArray(
				userGroups,
				com.liferay.portal.kernel.model.UserGroup.
					USER_GROUP_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the user and its user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user to clear the associated user groups from
	 */
	@Override
	public void clearUserGroups(long pk) {
		userToUserGroupTableMapper.deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the user and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroupPK the primary key of the user group
	 */
	@Override
	public void removeUserGroup(long pk, long userGroupPK) {
		userToUserGroupTableMapper.deleteTableMapping(pk, userGroupPK);
	}

	/**
	 * Removes the association between the user and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroup the user group
	 */
	@Override
	public void removeUserGroup(
		long pk, com.liferay.portal.kernel.model.UserGroup userGroup) {

		userToUserGroupTableMapper.deleteTableMapping(
			pk, userGroup.getPrimaryKey());
	}

	/**
	 * Removes the association between the user and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroupPKs the primary keys of the user groups
	 */
	@Override
	public void removeUserGroups(long pk, long[] userGroupPKs) {
		userToUserGroupTableMapper.deleteTableMappings(pk, userGroupPKs);
	}

	/**
	 * Removes the association between the user and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroups the user groups
	 */
	@Override
	public void removeUserGroups(
		long pk, List<com.liferay.portal.kernel.model.UserGroup> userGroups) {

		removeUserGroups(
			pk,
			ListUtil.toLongArray(
				userGroups,
				com.liferay.portal.kernel.model.UserGroup.
					USER_GROUP_ID_ACCESSOR));
	}

	/**
	 * Sets the user groups associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroupPKs the primary keys of the user groups to be associated with the user
	 */
	@Override
	public void setUserGroups(long pk, long[] userGroupPKs) {
		Set<Long> newUserGroupPKsSet = SetUtil.fromArray(userGroupPKs);
		Set<Long> oldUserGroupPKsSet = SetUtil.fromArray(
			userToUserGroupTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeUserGroupPKsSet = new HashSet<Long>(oldUserGroupPKsSet);

		removeUserGroupPKsSet.removeAll(newUserGroupPKsSet);

		userToUserGroupTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeUserGroupPKsSet));

		newUserGroupPKsSet.removeAll(oldUserGroupPKsSet);

		long companyId = 0;

		User user = fetchByPrimaryKey(pk);

		if (user == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = user.getCompanyId();
		}

		userToUserGroupTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newUserGroupPKsSet));
	}

	/**
	 * Sets the user groups associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroups the user groups to be associated with the user
	 */
	@Override
	public void setUserGroups(
		long pk, List<com.liferay.portal.kernel.model.UserGroup> userGroups) {

		try {
			long[] userGroupPKs = new long[userGroups.size()];

			for (int i = 0; i < userGroups.size(); i++) {
				com.liferay.portal.kernel.model.UserGroup userGroup =
					userGroups.get(i);

				userGroupPKs[i] = userGroup.getPrimaryKey();
			}

			setUserGroups(pk, userGroupPKs);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "userId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_USER;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.getOrDefault(
			ctColumnResolutionType, Collections.emptySet());
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return UserModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "User_";
	}

	@Override
	public List<String[]> getUniqueIndexColumnNames() {
		return _uniqueIndexColumnNames;
	}

	private static final Map<CTColumnResolutionType, Set<String>>
		_ctColumnNamesMap = new EnumMap<CTColumnResolutionType, Set<String>>(
			CTColumnResolutionType.class);
	private static final List<String> _mappingTableNames =
		new ArrayList<String>();
	private static final List<String[]> _uniqueIndexColumnNames =
		new ArrayList<String[]>();

	static {
		Set<String> ctControlColumnNames = new HashSet<String>();
		Set<String> ctIgnoreColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("uuid_");
		ctStrictColumnNames.add("externalReferenceCode");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("contactId");
		ctStrictColumnNames.add("password_");
		ctStrictColumnNames.add("passwordEncrypted");
		ctStrictColumnNames.add("passwordReset");
		ctStrictColumnNames.add("passwordModifiedDate");
		ctStrictColumnNames.add("digest");
		ctStrictColumnNames.add("reminderQueryQuestion");
		ctStrictColumnNames.add("reminderQueryAnswer");
		ctStrictColumnNames.add("graceLoginCount");
		ctStrictColumnNames.add("screenName");
		ctStrictColumnNames.add("emailAddress");
		ctStrictColumnNames.add("facebookId");
		ctStrictColumnNames.add("googleUserId");
		ctStrictColumnNames.add("ldapServerId");
		ctStrictColumnNames.add("openId");
		ctStrictColumnNames.add("portraitId");
		ctStrictColumnNames.add("languageId");
		ctStrictColumnNames.add("timeZoneId");
		ctStrictColumnNames.add("greeting");
		ctStrictColumnNames.add("comments");
		ctStrictColumnNames.add("firstName");
		ctStrictColumnNames.add("middleName");
		ctStrictColumnNames.add("lastName");
		ctStrictColumnNames.add("jobTitle");
		ctStrictColumnNames.add("loginDate");
		ctStrictColumnNames.add("loginIP");
		ctStrictColumnNames.add("lastLoginDate");
		ctStrictColumnNames.add("lastLoginIP");
		ctStrictColumnNames.add("lastFailedLoginDate");
		ctStrictColumnNames.add("failedLoginAttempts");
		ctStrictColumnNames.add("lockout");
		ctStrictColumnNames.add("lockoutDate");
		ctStrictColumnNames.add("agreedToTermsOfUse");
		ctStrictColumnNames.add("emailAddressVerified");
		ctStrictColumnNames.add("type_");
		ctStrictColumnNames.add("status");
		ctStrictColumnNames.add("groups_");
		ctStrictColumnNames.add("orgs");
		ctStrictColumnNames.add("roles");
		ctStrictColumnNames.add("teams");
		ctStrictColumnNames.add("userGroups");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("userId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_mappingTableNames.add("Users_Groups");
		_mappingTableNames.add("Users_Orgs");
		_mappingTableNames.add("Users_Roles");
		_mappingTableNames.add("Users_Teams");
		_mappingTableNames.add("Users_UserGroups");

		_uniqueIndexColumnNames.add(new String[] {"contactId"});

		_uniqueIndexColumnNames.add(new String[] {"companyId", "userId"});

		_uniqueIndexColumnNames.add(new String[] {"companyId", "screenName"});

		_uniqueIndexColumnNames.add(new String[] {"companyId", "emailAddress"});

		_uniqueIndexColumnNames.add(
			new String[] {"externalReferenceCode", "companyId"});
	}

	/**
	 * Initializes the user persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		userToGroupTableMapper = TableMapperFactory.getTableMapper(
			"Users_Groups", "companyId", "userId", "groupId", this,
			groupPersistence);

		userToOrganizationTableMapper = TableMapperFactory.getTableMapper(
			"Users_Orgs", "companyId", "userId", "organizationId", this,
			organizationPersistence);

		userToRoleTableMapper = TableMapperFactory.getTableMapper(
			"Users_Roles", "companyId", "userId", "roleId", this,
			rolePersistence);

		userToTeamTableMapper = TableMapperFactory.getTableMapper(
			"Users_Teams", "companyId", "userId", "teamId", this,
			teamPersistence);

		userToUserGroupTableMapper = TableMapperFactory.getTableMapper(
			"Users_UserGroups", "companyId", "userId", "userGroupId", this,
			userGroupPersistence);

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_"}, true);

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			true);

		_finderPathCountByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			false);

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathCountByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, false);

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId"}, true);

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			true);

		_finderPathCountByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			false);

		_finderPathFetchByContactId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByContactId",
			new String[] {Long.class.getName()}, new String[] {"contactId"},
			true);

		_finderPathCountByContactId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByContactId",
			new String[] {Long.class.getName()}, new String[] {"contactId"},
			false);

		_finderPathWithPaginationFindByEmailAddress = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByEmailAddress",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"emailAddress"}, true);

		_finderPathWithoutPaginationFindByEmailAddress = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByEmailAddress",
			new String[] {String.class.getName()},
			new String[] {"emailAddress"}, true);

		_finderPathCountByEmailAddress = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByEmailAddress",
			new String[] {String.class.getName()},
			new String[] {"emailAddress"}, false);

		_finderPathFetchByPortraitId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByPortraitId",
			new String[] {Long.class.getName()}, new String[] {"portraitId"},
			true);

		_finderPathCountByPortraitId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPortraitId",
			new String[] {Long.class.getName()}, new String[] {"portraitId"},
			false);

		_finderPathWithPaginationFindByGtU_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGtU_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"userId", "companyId"}, true);

		_finderPathWithPaginationCountByGtU_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByGtU_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"userId", "companyId"}, false);

		_finderPathFetchByC_U = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "userId"}, true);

		_finderPathCountByC_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "userId"}, false);

		_finderPathWithPaginationFindByC_CD = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_CD",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "createDate"}, true);

		_finderPathWithoutPaginationFindByC_CD = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_CD",
			new String[] {Long.class.getName(), Date.class.getName()},
			new String[] {"companyId", "createDate"}, true);

		_finderPathCountByC_CD = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_CD",
			new String[] {Long.class.getName(), Date.class.getName()},
			new String[] {"companyId", "createDate"}, false);

		_finderPathWithPaginationFindByC_MD = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_MD",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "modifiedDate"}, true);

		_finderPathWithoutPaginationFindByC_MD = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_MD",
			new String[] {Long.class.getName(), Date.class.getName()},
			new String[] {"companyId", "modifiedDate"}, true);

		_finderPathCountByC_MD = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_MD",
			new String[] {Long.class.getName(), Date.class.getName()},
			new String[] {"companyId", "modifiedDate"}, false);

		_finderPathFetchByC_SN = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_SN",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "screenName"}, true);

		_finderPathCountByC_SN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_SN",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "screenName"}, false);

		_finderPathFetchByC_EA = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_EA",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "emailAddress"}, true);

		_finderPathCountByC_EA = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_EA",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "emailAddress"}, false);

		_finderPathFetchByC_FID = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_FID",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "facebookId"}, true);

		_finderPathCountByC_FID = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_FID",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "facebookId"}, false);

		_finderPathFetchByC_GUID = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_GUID",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "googleUserId"}, true);

		_finderPathCountByC_GUID = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_GUID",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "googleUserId"}, false);

		_finderPathFetchByC_O = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_O",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "openId"}, true);

		_finderPathCountByC_O = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_O",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "openId"}, false);

		_finderPathWithPaginationFindByC_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "type_"}, true);

		_finderPathWithoutPaginationFindByC_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_T",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"companyId", "type_"}, true);

		_finderPathCountByC_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_T",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"companyId", "type_"}, false);

		_finderPathWithPaginationFindByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "status"}, true);

		_finderPathWithoutPaginationFindByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"companyId", "status"}, true);

		_finderPathCountByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"companyId", "status"}, false);

		_finderPathWithPaginationFindByC_CD_MD = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_CD_MD",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "createDate", "modifiedDate"}, true);

		_finderPathWithoutPaginationFindByC_CD_MD = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_CD_MD",
			new String[] {
				Long.class.getName(), Date.class.getName(), Date.class.getName()
			},
			new String[] {"companyId", "createDate", "modifiedDate"}, true);

		_finderPathCountByC_CD_MD = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_CD_MD",
			new String[] {
				Long.class.getName(), Date.class.getName(), Date.class.getName()
			},
			new String[] {"companyId", "createDate", "modifiedDate"}, false);

		_finderPathWithPaginationFindByC_T_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_T_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "type_", "status"}, true);

		_finderPathWithoutPaginationFindByC_T_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_T_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName()
			},
			new String[] {"companyId", "type_", "status"}, true);

		_finderPathCountByC_T_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_T_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName()
			},
			new String[] {"companyId", "type_", "status"}, false);

		_finderPathFetchByERC_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByERC_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"externalReferenceCode", "companyId"}, true);

		_finderPathCountByERC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByERC_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"externalReferenceCode", "companyId"}, false);

		UserUtil.setPersistence(this);
	}

	public void destroy() {
		UserUtil.setPersistence(null);

		EntityCacheUtil.removeCache(UserImpl.class.getName());

		TableMapperFactory.removeTableMapper("Users_Groups");
		TableMapperFactory.removeTableMapper("Users_Orgs");
		TableMapperFactory.removeTableMapper("Users_Roles");
		TableMapperFactory.removeTableMapper("Users_Teams");
		TableMapperFactory.removeTableMapper("Users_UserGroups");
	}

	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;

	protected TableMapper<User, com.liferay.portal.kernel.model.Group>
		userToGroupTableMapper;

	@BeanReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;

	protected TableMapper<User, com.liferay.portal.kernel.model.Organization>
		userToOrganizationTableMapper;

	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;

	protected TableMapper<User, com.liferay.portal.kernel.model.Role>
		userToRoleTableMapper;

	@BeanReference(type = TeamPersistence.class)
	protected TeamPersistence teamPersistence;

	protected TableMapper<User, com.liferay.portal.kernel.model.Team>
		userToTeamTableMapper;

	@BeanReference(type = UserGroupPersistence.class)
	protected UserGroupPersistence userGroupPersistence;

	protected TableMapper<User, com.liferay.portal.kernel.model.UserGroup>
		userToUserGroupTableMapper;

	private static Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_USER = "SELECT user FROM User user";

	private static final String _SQL_SELECT_USER_WHERE =
		"SELECT user FROM User user WHERE ";

	private static final String _SQL_COUNT_USER =
		"SELECT COUNT(user) FROM User user";

	private static final String _SQL_COUNT_USER_WHERE =
		"SELECT COUNT(user) FROM User user WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "user.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No User exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No User exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		UserPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "password", "type", "groups"});

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

}