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

package com.liferay.osb.faro.service.persistence.impl;

import com.liferay.osb.faro.exception.NoSuchFaroNotificationException;
import com.liferay.osb.faro.model.FaroNotification;
import com.liferay.osb.faro.model.FaroNotificationTable;
import com.liferay.osb.faro.model.impl.FaroNotificationImpl;
import com.liferay.osb.faro.model.impl.FaroNotificationModelImpl;
import com.liferay.osb.faro.service.persistence.FaroNotificationPersistence;
import com.liferay.osb.faro.service.persistence.FaroNotificationUtil;
import com.liferay.osb.faro.service.persistence.impl.constants.OSBFaroPersistenceConstants;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.util.HashMap;
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
 * The persistence implementation for the faro notification service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Matthew Kong
 * @generated
 */
@Component(service = FaroNotificationPersistence.class)
public class FaroNotificationPersistenceImpl
	extends BasePersistenceImpl<FaroNotification>
	implements FaroNotificationPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>FaroNotificationUtil</code> to access the faro notification persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		FaroNotificationImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByLtCreateTime;
	private FinderPath _finderPathWithPaginationCountByLtCreateTime;

	/**
	 * Returns all the faro notifications where createTime &lt; &#63;.
	 *
	 * @param createTime the create time
	 * @return the matching faro notifications
	 */
	@Override
	public List<FaroNotification> findByLtCreateTime(long createTime) {
		return findByLtCreateTime(
			createTime, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the faro notifications where createTime &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param createTime the create time
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @return the range of matching faro notifications
	 */
	@Override
	public List<FaroNotification> findByLtCreateTime(
		long createTime, int start, int end) {

		return findByLtCreateTime(createTime, start, end, null);
	}

	/**
	 * Returns an ordered range of all the faro notifications where createTime &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param createTime the create time
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro notifications
	 */
	@Override
	public List<FaroNotification> findByLtCreateTime(
		long createTime, int start, int end,
		OrderByComparator<FaroNotification> orderByComparator) {

		return findByLtCreateTime(
			createTime, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the faro notifications where createTime &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param createTime the create time
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro notifications
	 */
	@Override
	public List<FaroNotification> findByLtCreateTime(
		long createTime, int start, int end,
		OrderByComparator<FaroNotification> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByLtCreateTime;
		finderArgs = new Object[] {createTime, start, end, orderByComparator};

		List<FaroNotification> list = null;

		if (useFinderCache) {
			list = (List<FaroNotification>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FaroNotification faroNotification : list) {
					if (createTime <= faroNotification.getCreateTime()) {
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

			sb.append(_SQL_SELECT_FARONOTIFICATION_WHERE);

			sb.append(_FINDER_COLUMN_LTCREATETIME_CREATETIME_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FaroNotificationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(createTime);

				list = (List<FaroNotification>)QueryUtil.list(
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
	 * Returns the first faro notification in the ordered set where createTime &lt; &#63;.
	 *
	 * @param createTime the create time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro notification
	 * @throws NoSuchFaroNotificationException if a matching faro notification could not be found
	 */
	@Override
	public FaroNotification findByLtCreateTime_First(
			long createTime,
			OrderByComparator<FaroNotification> orderByComparator)
		throws NoSuchFaroNotificationException {

		FaroNotification faroNotification = fetchByLtCreateTime_First(
			createTime, orderByComparator);

		if (faroNotification != null) {
			return faroNotification;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("createTime<");
		sb.append(createTime);

		sb.append("}");

		throw new NoSuchFaroNotificationException(sb.toString());
	}

	/**
	 * Returns the first faro notification in the ordered set where createTime &lt; &#63;.
	 *
	 * @param createTime the create time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro notification, or <code>null</code> if a matching faro notification could not be found
	 */
	@Override
	public FaroNotification fetchByLtCreateTime_First(
		long createTime,
		OrderByComparator<FaroNotification> orderByComparator) {

		List<FaroNotification> list = findByLtCreateTime(
			createTime, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last faro notification in the ordered set where createTime &lt; &#63;.
	 *
	 * @param createTime the create time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro notification
	 * @throws NoSuchFaroNotificationException if a matching faro notification could not be found
	 */
	@Override
	public FaroNotification findByLtCreateTime_Last(
			long createTime,
			OrderByComparator<FaroNotification> orderByComparator)
		throws NoSuchFaroNotificationException {

		FaroNotification faroNotification = fetchByLtCreateTime_Last(
			createTime, orderByComparator);

		if (faroNotification != null) {
			return faroNotification;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("createTime<");
		sb.append(createTime);

		sb.append("}");

		throw new NoSuchFaroNotificationException(sb.toString());
	}

	/**
	 * Returns the last faro notification in the ordered set where createTime &lt; &#63;.
	 *
	 * @param createTime the create time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro notification, or <code>null</code> if a matching faro notification could not be found
	 */
	@Override
	public FaroNotification fetchByLtCreateTime_Last(
		long createTime,
		OrderByComparator<FaroNotification> orderByComparator) {

		int count = countByLtCreateTime(createTime);

		if (count == 0) {
			return null;
		}

		List<FaroNotification> list = findByLtCreateTime(
			createTime, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the faro notifications before and after the current faro notification in the ordered set where createTime &lt; &#63;.
	 *
	 * @param faroNotificationId the primary key of the current faro notification
	 * @param createTime the create time
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next faro notification
	 * @throws NoSuchFaroNotificationException if a faro notification with the primary key could not be found
	 */
	@Override
	public FaroNotification[] findByLtCreateTime_PrevAndNext(
			long faroNotificationId, long createTime,
			OrderByComparator<FaroNotification> orderByComparator)
		throws NoSuchFaroNotificationException {

		FaroNotification faroNotification = findByPrimaryKey(
			faroNotificationId);

		Session session = null;

		try {
			session = openSession();

			FaroNotification[] array = new FaroNotificationImpl[3];

			array[0] = getByLtCreateTime_PrevAndNext(
				session, faroNotification, createTime, orderByComparator, true);

			array[1] = faroNotification;

			array[2] = getByLtCreateTime_PrevAndNext(
				session, faroNotification, createTime, orderByComparator,
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

	protected FaroNotification getByLtCreateTime_PrevAndNext(
		Session session, FaroNotification faroNotification, long createTime,
		OrderByComparator<FaroNotification> orderByComparator,
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

		sb.append(_SQL_SELECT_FARONOTIFICATION_WHERE);

		sb.append(_FINDER_COLUMN_LTCREATETIME_CREATETIME_2);

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
			sb.append(FaroNotificationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(createTime);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						faroNotification)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FaroNotification> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the faro notifications where createTime &lt; &#63; from the database.
	 *
	 * @param createTime the create time
	 */
	@Override
	public void removeByLtCreateTime(long createTime) {
		for (FaroNotification faroNotification :
				findByLtCreateTime(
					createTime, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(faroNotification);
		}
	}

	/**
	 * Returns the number of faro notifications where createTime &lt; &#63;.
	 *
	 * @param createTime the create time
	 * @return the number of matching faro notifications
	 */
	@Override
	public int countByLtCreateTime(long createTime) {
		FinderPath finderPath = _finderPathWithPaginationCountByLtCreateTime;

		Object[] finderArgs = new Object[] {createTime};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FARONOTIFICATION_WHERE);

			sb.append(_FINDER_COLUMN_LTCREATETIME_CREATETIME_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(createTime);

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

	private static final String _FINDER_COLUMN_LTCREATETIME_CREATETIME_2 =
		"faroNotification.createTime < ?";

	private FinderPath _finderPathWithPaginationFindByG_GtC_O_T;
	private FinderPath _finderPathWithPaginationCountByG_GtC_O_T;

	/**
	 * Returns all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @return the matching faro notifications
	 */
	@Override
	public List<FaroNotification> findByG_GtC_O_T(
		long groupId, long createTime, long ownerId, String type) {

		return findByG_GtC_O_T(
			groupId, createTime, ownerId, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @return the range of matching faro notifications
	 */
	@Override
	public List<FaroNotification> findByG_GtC_O_T(
		long groupId, long createTime, long ownerId, String type, int start,
		int end) {

		return findByG_GtC_O_T(
			groupId, createTime, ownerId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro notifications
	 */
	@Override
	public List<FaroNotification> findByG_GtC_O_T(
		long groupId, long createTime, long ownerId, String type, int start,
		int end, OrderByComparator<FaroNotification> orderByComparator) {

		return findByG_GtC_O_T(
			groupId, createTime, ownerId, type, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro notifications
	 */
	@Override
	public List<FaroNotification> findByG_GtC_O_T(
		long groupId, long createTime, long ownerId, String type, int start,
		int end, OrderByComparator<FaroNotification> orderByComparator,
		boolean useFinderCache) {

		type = Objects.toString(type, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByG_GtC_O_T;
		finderArgs = new Object[] {
			groupId, createTime, ownerId, type, start, end, orderByComparator
		};

		List<FaroNotification> list = null;

		if (useFinderCache) {
			list = (List<FaroNotification>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FaroNotification faroNotification : list) {
					if ((groupId != faroNotification.getGroupId()) ||
						(createTime >= faroNotification.getCreateTime()) ||
						(ownerId != faroNotification.getOwnerId()) ||
						!type.equals(faroNotification.getType())) {

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
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(6);
			}

			sb.append(_SQL_SELECT_FARONOTIFICATION_WHERE);

			sb.append(_FINDER_COLUMN_G_GTC_O_T_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_GTC_O_T_CREATETIME_2);

			sb.append(_FINDER_COLUMN_G_GTC_O_T_OWNERID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_GTC_O_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_G_GTC_O_T_TYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FaroNotificationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(createTime);

				queryPos.add(ownerId);

				if (bindType) {
					queryPos.add(type);
				}

				list = (List<FaroNotification>)QueryUtil.list(
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
	 * Returns the first faro notification in the ordered set where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro notification
	 * @throws NoSuchFaroNotificationException if a matching faro notification could not be found
	 */
	@Override
	public FaroNotification findByG_GtC_O_T_First(
			long groupId, long createTime, long ownerId, String type,
			OrderByComparator<FaroNotification> orderByComparator)
		throws NoSuchFaroNotificationException {

		FaroNotification faroNotification = fetchByG_GtC_O_T_First(
			groupId, createTime, ownerId, type, orderByComparator);

		if (faroNotification != null) {
			return faroNotification;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", createTime>");
		sb.append(createTime);

		sb.append(", ownerId=");
		sb.append(ownerId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchFaroNotificationException(sb.toString());
	}

	/**
	 * Returns the first faro notification in the ordered set where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro notification, or <code>null</code> if a matching faro notification could not be found
	 */
	@Override
	public FaroNotification fetchByG_GtC_O_T_First(
		long groupId, long createTime, long ownerId, String type,
		OrderByComparator<FaroNotification> orderByComparator) {

		List<FaroNotification> list = findByG_GtC_O_T(
			groupId, createTime, ownerId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last faro notification in the ordered set where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro notification
	 * @throws NoSuchFaroNotificationException if a matching faro notification could not be found
	 */
	@Override
	public FaroNotification findByG_GtC_O_T_Last(
			long groupId, long createTime, long ownerId, String type,
			OrderByComparator<FaroNotification> orderByComparator)
		throws NoSuchFaroNotificationException {

		FaroNotification faroNotification = fetchByG_GtC_O_T_Last(
			groupId, createTime, ownerId, type, orderByComparator);

		if (faroNotification != null) {
			return faroNotification;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", createTime>");
		sb.append(createTime);

		sb.append(", ownerId=");
		sb.append(ownerId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchFaroNotificationException(sb.toString());
	}

	/**
	 * Returns the last faro notification in the ordered set where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro notification, or <code>null</code> if a matching faro notification could not be found
	 */
	@Override
	public FaroNotification fetchByG_GtC_O_T_Last(
		long groupId, long createTime, long ownerId, String type,
		OrderByComparator<FaroNotification> orderByComparator) {

		int count = countByG_GtC_O_T(groupId, createTime, ownerId, type);

		if (count == 0) {
			return null;
		}

		List<FaroNotification> list = findByG_GtC_O_T(
			groupId, createTime, ownerId, type, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the faro notifications before and after the current faro notification in the ordered set where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63;.
	 *
	 * @param faroNotificationId the primary key of the current faro notification
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next faro notification
	 * @throws NoSuchFaroNotificationException if a faro notification with the primary key could not be found
	 */
	@Override
	public FaroNotification[] findByG_GtC_O_T_PrevAndNext(
			long faroNotificationId, long groupId, long createTime,
			long ownerId, String type,
			OrderByComparator<FaroNotification> orderByComparator)
		throws NoSuchFaroNotificationException {

		type = Objects.toString(type, "");

		FaroNotification faroNotification = findByPrimaryKey(
			faroNotificationId);

		Session session = null;

		try {
			session = openSession();

			FaroNotification[] array = new FaroNotificationImpl[3];

			array[0] = getByG_GtC_O_T_PrevAndNext(
				session, faroNotification, groupId, createTime, ownerId, type,
				orderByComparator, true);

			array[1] = faroNotification;

			array[2] = getByG_GtC_O_T_PrevAndNext(
				session, faroNotification, groupId, createTime, ownerId, type,
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

	protected FaroNotification getByG_GtC_O_T_PrevAndNext(
		Session session, FaroNotification faroNotification, long groupId,
		long createTime, long ownerId, String type,
		OrderByComparator<FaroNotification> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_FARONOTIFICATION_WHERE);

		sb.append(_FINDER_COLUMN_G_GTC_O_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_GTC_O_T_CREATETIME_2);

		sb.append(_FINDER_COLUMN_G_GTC_O_T_OWNERID_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_GTC_O_T_TYPE_3);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_G_GTC_O_T_TYPE_2);
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
			sb.append(FaroNotificationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(createTime);

		queryPos.add(ownerId);

		if (bindType) {
			queryPos.add(type);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						faroNotification)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FaroNotification> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = any &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerIds the owner IDs
	 * @param type the type
	 * @return the matching faro notifications
	 */
	@Override
	public List<FaroNotification> findByG_GtC_O_T(
		long groupId, long createTime, long[] ownerIds, String type) {

		return findByG_GtC_O_T(
			groupId, createTime, ownerIds, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = any &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerIds the owner IDs
	 * @param type the type
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @return the range of matching faro notifications
	 */
	@Override
	public List<FaroNotification> findByG_GtC_O_T(
		long groupId, long createTime, long[] ownerIds, String type, int start,
		int end) {

		return findByG_GtC_O_T(
			groupId, createTime, ownerIds, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = any &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerIds the owner IDs
	 * @param type the type
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro notifications
	 */
	@Override
	public List<FaroNotification> findByG_GtC_O_T(
		long groupId, long createTime, long[] ownerIds, String type, int start,
		int end, OrderByComparator<FaroNotification> orderByComparator) {

		return findByG_GtC_O_T(
			groupId, createTime, ownerIds, type, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerIds the owner IDs
	 * @param type the type
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro notifications
	 */
	@Override
	public List<FaroNotification> findByG_GtC_O_T(
		long groupId, long createTime, long[] ownerIds, String type, int start,
		int end, OrderByComparator<FaroNotification> orderByComparator,
		boolean useFinderCache) {

		if (ownerIds == null) {
			ownerIds = new long[0];
		}
		else if (ownerIds.length > 1) {
			ownerIds = ArrayUtil.sortedUnique(ownerIds);
		}

		type = Objects.toString(type, "");

		if (ownerIds.length == 1) {
			return findByG_GtC_O_T(
				groupId, createTime, ownerIds[0], type, start, end,
				orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					groupId, createTime, StringUtil.merge(ownerIds), type
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				groupId, createTime, StringUtil.merge(ownerIds), type, start,
				end, orderByComparator
			};
		}

		List<FaroNotification> list = null;

		if (useFinderCache) {
			list = (List<FaroNotification>)finderCache.getResult(
				_finderPathWithPaginationFindByG_GtC_O_T, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FaroNotification faroNotification : list) {
					if ((groupId != faroNotification.getGroupId()) ||
						(createTime >= faroNotification.getCreateTime()) ||
						!ArrayUtil.contains(
							ownerIds, faroNotification.getOwnerId()) ||
						!type.equals(faroNotification.getType())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_FARONOTIFICATION_WHERE);

			sb.append(_FINDER_COLUMN_G_GTC_O_T_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_GTC_O_T_CREATETIME_2);

			if (ownerIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_GTC_O_T_OWNERID_7);

				sb.append(StringUtil.merge(ownerIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_GTC_O_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_G_GTC_O_T_TYPE_2);
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			sb.append(" AND faroNotification.read = [$FALSE$]");

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FaroNotificationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(createTime);

				if (bindType) {
					queryPos.add(type);
				}

				list = (List<FaroNotification>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByG_GtC_O_T, finderArgs,
						list);
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
	 * Removes all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 */
	@Override
	public void removeByG_GtC_O_T(
		long groupId, long createTime, long ownerId, String type) {

		for (FaroNotification faroNotification :
				findByG_GtC_O_T(
					groupId, createTime, ownerId, type, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(faroNotification);
		}
	}

	/**
	 * Returns the number of faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @return the number of matching faro notifications
	 */
	@Override
	public int countByG_GtC_O_T(
		long groupId, long createTime, long ownerId, String type) {

		type = Objects.toString(type, "");

		FinderPath finderPath = _finderPathWithPaginationCountByG_GtC_O_T;

		Object[] finderArgs = new Object[] {groupId, createTime, ownerId, type};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_FARONOTIFICATION_WHERE);

			sb.append(_FINDER_COLUMN_G_GTC_O_T_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_GTC_O_T_CREATETIME_2);

			sb.append(_FINDER_COLUMN_G_GTC_O_T_OWNERID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_GTC_O_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_G_GTC_O_T_TYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(createTime);

				queryPos.add(ownerId);

				if (bindType) {
					queryPos.add(type);
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

	/**
	 * Returns the number of faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = any &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerIds the owner IDs
	 * @param type the type
	 * @return the number of matching faro notifications
	 */
	@Override
	public int countByG_GtC_O_T(
		long groupId, long createTime, long[] ownerIds, String type) {

		if (ownerIds == null) {
			ownerIds = new long[0];
		}
		else if (ownerIds.length > 1) {
			ownerIds = ArrayUtil.sortedUnique(ownerIds);
		}

		type = Objects.toString(type, "");

		Object[] finderArgs = new Object[] {
			groupId, createTime, StringUtil.merge(ownerIds), type
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByG_GtC_O_T, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_FARONOTIFICATION_WHERE);

			sb.append(_FINDER_COLUMN_G_GTC_O_T_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_GTC_O_T_CREATETIME_2);

			if (ownerIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_GTC_O_T_OWNERID_7);

				sb.append(StringUtil.merge(ownerIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_GTC_O_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_G_GTC_O_T_TYPE_2);
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			sb.append(" AND faroNotification.read = [$FALSE$]");

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(createTime);

				if (bindType) {
					queryPos.add(type);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByG_GtC_O_T, finderArgs,
					count);
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

	private static final String _FINDER_COLUMN_G_GTC_O_T_GROUPID_2 =
		"faroNotification.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_GTC_O_T_CREATETIME_2 =
		"faroNotification.createTime > ? AND ";

	private static final String _FINDER_COLUMN_G_GTC_O_T_OWNERID_2 =
		"faroNotification.ownerId = ? AND ";

	private static final String _FINDER_COLUMN_G_GTC_O_T_OWNERID_7 =
		"faroNotification.ownerId IN (";

	private static final String _FINDER_COLUMN_G_GTC_O_T_TYPE_2 =
		"faroNotification.type = ? AND faroNotification.read = [$FALSE$]";

	private static final String _FINDER_COLUMN_G_GTC_O_T_TYPE_3 =
		"(faroNotification.type IS NULL OR faroNotification.type = '') AND faroNotification.read = [$FALSE$]";

	private FinderPath _finderPathWithPaginationFindByG_GtC_O_T_S;
	private FinderPath _finderPathWithPaginationCountByG_GtC_O_T_S;

	/**
	 * Returns all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param subtype the subtype
	 * @return the matching faro notifications
	 */
	@Override
	public List<FaroNotification> findByG_GtC_O_T_S(
		long groupId, long createTime, long ownerId, String type,
		String subtype) {

		return findByG_GtC_O_T_S(
			groupId, createTime, ownerId, type, subtype, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param subtype the subtype
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @return the range of matching faro notifications
	 */
	@Override
	public List<FaroNotification> findByG_GtC_O_T_S(
		long groupId, long createTime, long ownerId, String type,
		String subtype, int start, int end) {

		return findByG_GtC_O_T_S(
			groupId, createTime, ownerId, type, subtype, start, end, null);
	}

	/**
	 * Returns an ordered range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param subtype the subtype
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro notifications
	 */
	@Override
	public List<FaroNotification> findByG_GtC_O_T_S(
		long groupId, long createTime, long ownerId, String type,
		String subtype, int start, int end,
		OrderByComparator<FaroNotification> orderByComparator) {

		return findByG_GtC_O_T_S(
			groupId, createTime, ownerId, type, subtype, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param subtype the subtype
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro notifications
	 */
	@Override
	public List<FaroNotification> findByG_GtC_O_T_S(
		long groupId, long createTime, long ownerId, String type,
		String subtype, int start, int end,
		OrderByComparator<FaroNotification> orderByComparator,
		boolean useFinderCache) {

		type = Objects.toString(type, "");
		subtype = Objects.toString(subtype, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByG_GtC_O_T_S;
		finderArgs = new Object[] {
			groupId, createTime, ownerId, type, subtype, start, end,
			orderByComparator
		};

		List<FaroNotification> list = null;

		if (useFinderCache) {
			list = (List<FaroNotification>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FaroNotification faroNotification : list) {
					if ((groupId != faroNotification.getGroupId()) ||
						(createTime >= faroNotification.getCreateTime()) ||
						(ownerId != faroNotification.getOwnerId()) ||
						!type.equals(faroNotification.getType()) ||
						!subtype.equals(faroNotification.getSubtype())) {

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
					7 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(7);
			}

			sb.append(_SQL_SELECT_FARONOTIFICATION_WHERE);

			sb.append(_FINDER_COLUMN_G_GTC_O_T_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_GTC_O_T_S_CREATETIME_2);

			sb.append(_FINDER_COLUMN_G_GTC_O_T_S_OWNERID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_GTC_O_T_S_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_G_GTC_O_T_S_TYPE_2);
			}

			boolean bindSubtype = false;

			if (subtype.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_GTC_O_T_S_SUBTYPE_3);
			}
			else {
				bindSubtype = true;

				sb.append(_FINDER_COLUMN_G_GTC_O_T_S_SUBTYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FaroNotificationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(createTime);

				queryPos.add(ownerId);

				if (bindType) {
					queryPos.add(type);
				}

				if (bindSubtype) {
					queryPos.add(subtype);
				}

				list = (List<FaroNotification>)QueryUtil.list(
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
	 * Returns the first faro notification in the ordered set where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param subtype the subtype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro notification
	 * @throws NoSuchFaroNotificationException if a matching faro notification could not be found
	 */
	@Override
	public FaroNotification findByG_GtC_O_T_S_First(
			long groupId, long createTime, long ownerId, String type,
			String subtype,
			OrderByComparator<FaroNotification> orderByComparator)
		throws NoSuchFaroNotificationException {

		FaroNotification faroNotification = fetchByG_GtC_O_T_S_First(
			groupId, createTime, ownerId, type, subtype, orderByComparator);

		if (faroNotification != null) {
			return faroNotification;
		}

		StringBundler sb = new StringBundler(12);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", createTime>");
		sb.append(createTime);

		sb.append(", ownerId=");
		sb.append(ownerId);

		sb.append(", type=");
		sb.append(type);

		sb.append(", subtype=");
		sb.append(subtype);

		sb.append("}");

		throw new NoSuchFaroNotificationException(sb.toString());
	}

	/**
	 * Returns the first faro notification in the ordered set where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param subtype the subtype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro notification, or <code>null</code> if a matching faro notification could not be found
	 */
	@Override
	public FaroNotification fetchByG_GtC_O_T_S_First(
		long groupId, long createTime, long ownerId, String type,
		String subtype, OrderByComparator<FaroNotification> orderByComparator) {

		List<FaroNotification> list = findByG_GtC_O_T_S(
			groupId, createTime, ownerId, type, subtype, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last faro notification in the ordered set where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param subtype the subtype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro notification
	 * @throws NoSuchFaroNotificationException if a matching faro notification could not be found
	 */
	@Override
	public FaroNotification findByG_GtC_O_T_S_Last(
			long groupId, long createTime, long ownerId, String type,
			String subtype,
			OrderByComparator<FaroNotification> orderByComparator)
		throws NoSuchFaroNotificationException {

		FaroNotification faroNotification = fetchByG_GtC_O_T_S_Last(
			groupId, createTime, ownerId, type, subtype, orderByComparator);

		if (faroNotification != null) {
			return faroNotification;
		}

		StringBundler sb = new StringBundler(12);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", createTime>");
		sb.append(createTime);

		sb.append(", ownerId=");
		sb.append(ownerId);

		sb.append(", type=");
		sb.append(type);

		sb.append(", subtype=");
		sb.append(subtype);

		sb.append("}");

		throw new NoSuchFaroNotificationException(sb.toString());
	}

	/**
	 * Returns the last faro notification in the ordered set where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param subtype the subtype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro notification, or <code>null</code> if a matching faro notification could not be found
	 */
	@Override
	public FaroNotification fetchByG_GtC_O_T_S_Last(
		long groupId, long createTime, long ownerId, String type,
		String subtype, OrderByComparator<FaroNotification> orderByComparator) {

		int count = countByG_GtC_O_T_S(
			groupId, createTime, ownerId, type, subtype);

		if (count == 0) {
			return null;
		}

		List<FaroNotification> list = findByG_GtC_O_T_S(
			groupId, createTime, ownerId, type, subtype, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the faro notifications before and after the current faro notification in the ordered set where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * @param faroNotificationId the primary key of the current faro notification
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param subtype the subtype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next faro notification
	 * @throws NoSuchFaroNotificationException if a faro notification with the primary key could not be found
	 */
	@Override
	public FaroNotification[] findByG_GtC_O_T_S_PrevAndNext(
			long faroNotificationId, long groupId, long createTime,
			long ownerId, String type, String subtype,
			OrderByComparator<FaroNotification> orderByComparator)
		throws NoSuchFaroNotificationException {

		type = Objects.toString(type, "");
		subtype = Objects.toString(subtype, "");

		FaroNotification faroNotification = findByPrimaryKey(
			faroNotificationId);

		Session session = null;

		try {
			session = openSession();

			FaroNotification[] array = new FaroNotificationImpl[3];

			array[0] = getByG_GtC_O_T_S_PrevAndNext(
				session, faroNotification, groupId, createTime, ownerId, type,
				subtype, orderByComparator, true);

			array[1] = faroNotification;

			array[2] = getByG_GtC_O_T_S_PrevAndNext(
				session, faroNotification, groupId, createTime, ownerId, type,
				subtype, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FaroNotification getByG_GtC_O_T_S_PrevAndNext(
		Session session, FaroNotification faroNotification, long groupId,
		long createTime, long ownerId, String type, String subtype,
		OrderByComparator<FaroNotification> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				8 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(7);
		}

		sb.append(_SQL_SELECT_FARONOTIFICATION_WHERE);

		sb.append(_FINDER_COLUMN_G_GTC_O_T_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_GTC_O_T_S_CREATETIME_2);

		sb.append(_FINDER_COLUMN_G_GTC_O_T_S_OWNERID_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_GTC_O_T_S_TYPE_3);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_G_GTC_O_T_S_TYPE_2);
		}

		boolean bindSubtype = false;

		if (subtype.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_GTC_O_T_S_SUBTYPE_3);
		}
		else {
			bindSubtype = true;

			sb.append(_FINDER_COLUMN_G_GTC_O_T_S_SUBTYPE_2);
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
			sb.append(FaroNotificationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(createTime);

		queryPos.add(ownerId);

		if (bindType) {
			queryPos.add(type);
		}

		if (bindSubtype) {
			queryPos.add(subtype);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						faroNotification)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FaroNotification> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = any &#63; and type = &#63; and subtype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerIds the owner IDs
	 * @param type the type
	 * @param subtype the subtype
	 * @return the matching faro notifications
	 */
	@Override
	public List<FaroNotification> findByG_GtC_O_T_S(
		long groupId, long createTime, long[] ownerIds, String type,
		String subtype) {

		return findByG_GtC_O_T_S(
			groupId, createTime, ownerIds, type, subtype, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = any &#63; and type = &#63; and subtype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerIds the owner IDs
	 * @param type the type
	 * @param subtype the subtype
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @return the range of matching faro notifications
	 */
	@Override
	public List<FaroNotification> findByG_GtC_O_T_S(
		long groupId, long createTime, long[] ownerIds, String type,
		String subtype, int start, int end) {

		return findByG_GtC_O_T_S(
			groupId, createTime, ownerIds, type, subtype, start, end, null);
	}

	/**
	 * Returns an ordered range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = any &#63; and type = &#63; and subtype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerIds the owner IDs
	 * @param type the type
	 * @param subtype the subtype
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro notifications
	 */
	@Override
	public List<FaroNotification> findByG_GtC_O_T_S(
		long groupId, long createTime, long[] ownerIds, String type,
		String subtype, int start, int end,
		OrderByComparator<FaroNotification> orderByComparator) {

		return findByG_GtC_O_T_S(
			groupId, createTime, ownerIds, type, subtype, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63; and subtype = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerIds the owner IDs
	 * @param type the type
	 * @param subtype the subtype
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro notifications
	 */
	@Override
	public List<FaroNotification> findByG_GtC_O_T_S(
		long groupId, long createTime, long[] ownerIds, String type,
		String subtype, int start, int end,
		OrderByComparator<FaroNotification> orderByComparator,
		boolean useFinderCache) {

		if (ownerIds == null) {
			ownerIds = new long[0];
		}
		else if (ownerIds.length > 1) {
			ownerIds = ArrayUtil.sortedUnique(ownerIds);
		}

		type = Objects.toString(type, "");
		subtype = Objects.toString(subtype, "");

		if (ownerIds.length == 1) {
			return findByG_GtC_O_T_S(
				groupId, createTime, ownerIds[0], type, subtype, start, end,
				orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					groupId, createTime, StringUtil.merge(ownerIds), type,
					subtype
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				groupId, createTime, StringUtil.merge(ownerIds), type, subtype,
				start, end, orderByComparator
			};
		}

		List<FaroNotification> list = null;

		if (useFinderCache) {
			list = (List<FaroNotification>)finderCache.getResult(
				_finderPathWithPaginationFindByG_GtC_O_T_S, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FaroNotification faroNotification : list) {
					if ((groupId != faroNotification.getGroupId()) ||
						(createTime >= faroNotification.getCreateTime()) ||
						!ArrayUtil.contains(
							ownerIds, faroNotification.getOwnerId()) ||
						!type.equals(faroNotification.getType()) ||
						!subtype.equals(faroNotification.getSubtype())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_FARONOTIFICATION_WHERE);

			sb.append(_FINDER_COLUMN_G_GTC_O_T_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_GTC_O_T_S_CREATETIME_2);

			if (ownerIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_GTC_O_T_S_OWNERID_7);

				sb.append(StringUtil.merge(ownerIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_GTC_O_T_S_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_G_GTC_O_T_S_TYPE_2);
			}

			boolean bindSubtype = false;

			if (subtype.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_GTC_O_T_S_SUBTYPE_3);
			}
			else {
				bindSubtype = true;

				sb.append(_FINDER_COLUMN_G_GTC_O_T_S_SUBTYPE_2);
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FaroNotificationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(createTime);

				if (bindType) {
					queryPos.add(type);
				}

				if (bindSubtype) {
					queryPos.add(subtype);
				}

				list = (List<FaroNotification>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByG_GtC_O_T_S, finderArgs,
						list);
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
	 * Removes all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63; and subtype = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param subtype the subtype
	 */
	@Override
	public void removeByG_GtC_O_T_S(
		long groupId, long createTime, long ownerId, String type,
		String subtype) {

		for (FaroNotification faroNotification :
				findByG_GtC_O_T_S(
					groupId, createTime, ownerId, type, subtype,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(faroNotification);
		}
	}

	/**
	 * Returns the number of faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param type the type
	 * @param subtype the subtype
	 * @return the number of matching faro notifications
	 */
	@Override
	public int countByG_GtC_O_T_S(
		long groupId, long createTime, long ownerId, String type,
		String subtype) {

		type = Objects.toString(type, "");
		subtype = Objects.toString(subtype, "");

		FinderPath finderPath = _finderPathWithPaginationCountByG_GtC_O_T_S;

		Object[] finderArgs = new Object[] {
			groupId, createTime, ownerId, type, subtype
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_SQL_COUNT_FARONOTIFICATION_WHERE);

			sb.append(_FINDER_COLUMN_G_GTC_O_T_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_GTC_O_T_S_CREATETIME_2);

			sb.append(_FINDER_COLUMN_G_GTC_O_T_S_OWNERID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_GTC_O_T_S_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_G_GTC_O_T_S_TYPE_2);
			}

			boolean bindSubtype = false;

			if (subtype.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_GTC_O_T_S_SUBTYPE_3);
			}
			else {
				bindSubtype = true;

				sb.append(_FINDER_COLUMN_G_GTC_O_T_S_SUBTYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(createTime);

				queryPos.add(ownerId);

				if (bindType) {
					queryPos.add(type);
				}

				if (bindSubtype) {
					queryPos.add(subtype);
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

	/**
	 * Returns the number of faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = any &#63; and type = &#63; and subtype = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerIds the owner IDs
	 * @param type the type
	 * @param subtype the subtype
	 * @return the number of matching faro notifications
	 */
	@Override
	public int countByG_GtC_O_T_S(
		long groupId, long createTime, long[] ownerIds, String type,
		String subtype) {

		if (ownerIds == null) {
			ownerIds = new long[0];
		}
		else if (ownerIds.length > 1) {
			ownerIds = ArrayUtil.sortedUnique(ownerIds);
		}

		type = Objects.toString(type, "");
		subtype = Objects.toString(subtype, "");

		Object[] finderArgs = new Object[] {
			groupId, createTime, StringUtil.merge(ownerIds), type, subtype
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByG_GtC_O_T_S, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_FARONOTIFICATION_WHERE);

			sb.append(_FINDER_COLUMN_G_GTC_O_T_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_GTC_O_T_S_CREATETIME_2);

			if (ownerIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_GTC_O_T_S_OWNERID_7);

				sb.append(StringUtil.merge(ownerIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_GTC_O_T_S_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_G_GTC_O_T_S_TYPE_2);
			}

			boolean bindSubtype = false;

			if (subtype.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_GTC_O_T_S_SUBTYPE_3);
			}
			else {
				bindSubtype = true;

				sb.append(_FINDER_COLUMN_G_GTC_O_T_S_SUBTYPE_2);
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(createTime);

				if (bindType) {
					queryPos.add(type);
				}

				if (bindSubtype) {
					queryPos.add(subtype);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByG_GtC_O_T_S, finderArgs,
					count);
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

	private static final String _FINDER_COLUMN_G_GTC_O_T_S_GROUPID_2 =
		"faroNotification.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_GTC_O_T_S_CREATETIME_2 =
		"faroNotification.createTime > ? AND ";

	private static final String _FINDER_COLUMN_G_GTC_O_T_S_OWNERID_2 =
		"faroNotification.ownerId = ? AND ";

	private static final String _FINDER_COLUMN_G_GTC_O_T_S_OWNERID_7 =
		"faroNotification.ownerId IN (";

	private static final String _FINDER_COLUMN_G_GTC_O_T_S_TYPE_2 =
		"faroNotification.type = ? AND ";

	private static final String _FINDER_COLUMN_G_GTC_O_T_S_TYPE_3 =
		"(faroNotification.type IS NULL OR faroNotification.type = '') AND ";

	private static final String _FINDER_COLUMN_G_GTC_O_T_S_SUBTYPE_2 =
		"faroNotification.subtype = ?";

	private static final String _FINDER_COLUMN_G_GTC_O_T_S_SUBTYPE_3 =
		"(faroNotification.subtype IS NULL OR faroNotification.subtype = '')";

	private FinderPath _finderPathWithPaginationFindByG_GtC_O_R_T_S;
	private FinderPath _finderPathWithPaginationCountByG_GtC_O_R_T_S;

	/**
	 * Returns all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and read = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param read the read
	 * @param type the type
	 * @param subtype the subtype
	 * @return the matching faro notifications
	 */
	@Override
	public List<FaroNotification> findByG_GtC_O_R_T_S(
		long groupId, long createTime, long ownerId, boolean read, String type,
		String subtype) {

		return findByG_GtC_O_R_T_S(
			groupId, createTime, ownerId, read, type, subtype,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and read = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param read the read
	 * @param type the type
	 * @param subtype the subtype
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @return the range of matching faro notifications
	 */
	@Override
	public List<FaroNotification> findByG_GtC_O_R_T_S(
		long groupId, long createTime, long ownerId, boolean read, String type,
		String subtype, int start, int end) {

		return findByG_GtC_O_R_T_S(
			groupId, createTime, ownerId, read, type, subtype, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and read = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param read the read
	 * @param type the type
	 * @param subtype the subtype
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro notifications
	 */
	@Override
	public List<FaroNotification> findByG_GtC_O_R_T_S(
		long groupId, long createTime, long ownerId, boolean read, String type,
		String subtype, int start, int end,
		OrderByComparator<FaroNotification> orderByComparator) {

		return findByG_GtC_O_R_T_S(
			groupId, createTime, ownerId, read, type, subtype, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and read = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param read the read
	 * @param type the type
	 * @param subtype the subtype
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro notifications
	 */
	@Override
	public List<FaroNotification> findByG_GtC_O_R_T_S(
		long groupId, long createTime, long ownerId, boolean read, String type,
		String subtype, int start, int end,
		OrderByComparator<FaroNotification> orderByComparator,
		boolean useFinderCache) {

		type = Objects.toString(type, "");
		subtype = Objects.toString(subtype, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByG_GtC_O_R_T_S;
		finderArgs = new Object[] {
			groupId, createTime, ownerId, read, type, subtype, start, end,
			orderByComparator
		};

		List<FaroNotification> list = null;

		if (useFinderCache) {
			list = (List<FaroNotification>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FaroNotification faroNotification : list) {
					if ((groupId != faroNotification.getGroupId()) ||
						(createTime >= faroNotification.getCreateTime()) ||
						(ownerId != faroNotification.getOwnerId()) ||
						(read != faroNotification.isRead()) ||
						!type.equals(faroNotification.getType()) ||
						!subtype.equals(faroNotification.getSubtype())) {

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
					8 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(8);
			}

			sb.append(_SQL_SELECT_FARONOTIFICATION_WHERE);

			sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_CREATETIME_2);

			sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_OWNERID_2);

			sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_READ_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_TYPE_2);
			}

			boolean bindSubtype = false;

			if (subtype.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_SUBTYPE_3);
			}
			else {
				bindSubtype = true;

				sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_SUBTYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FaroNotificationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(createTime);

				queryPos.add(ownerId);

				queryPos.add(read);

				if (bindType) {
					queryPos.add(type);
				}

				if (bindSubtype) {
					queryPos.add(subtype);
				}

				list = (List<FaroNotification>)QueryUtil.list(
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
	 * Returns the first faro notification in the ordered set where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and read = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param read the read
	 * @param type the type
	 * @param subtype the subtype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro notification
	 * @throws NoSuchFaroNotificationException if a matching faro notification could not be found
	 */
	@Override
	public FaroNotification findByG_GtC_O_R_T_S_First(
			long groupId, long createTime, long ownerId, boolean read,
			String type, String subtype,
			OrderByComparator<FaroNotification> orderByComparator)
		throws NoSuchFaroNotificationException {

		FaroNotification faroNotification = fetchByG_GtC_O_R_T_S_First(
			groupId, createTime, ownerId, read, type, subtype,
			orderByComparator);

		if (faroNotification != null) {
			return faroNotification;
		}

		StringBundler sb = new StringBundler(14);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", createTime>");
		sb.append(createTime);

		sb.append(", ownerId=");
		sb.append(ownerId);

		sb.append(", read=");
		sb.append(read);

		sb.append(", type=");
		sb.append(type);

		sb.append(", subtype=");
		sb.append(subtype);

		sb.append("}");

		throw new NoSuchFaroNotificationException(sb.toString());
	}

	/**
	 * Returns the first faro notification in the ordered set where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and read = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param read the read
	 * @param type the type
	 * @param subtype the subtype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro notification, or <code>null</code> if a matching faro notification could not be found
	 */
	@Override
	public FaroNotification fetchByG_GtC_O_R_T_S_First(
		long groupId, long createTime, long ownerId, boolean read, String type,
		String subtype, OrderByComparator<FaroNotification> orderByComparator) {

		List<FaroNotification> list = findByG_GtC_O_R_T_S(
			groupId, createTime, ownerId, read, type, subtype, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last faro notification in the ordered set where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and read = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param read the read
	 * @param type the type
	 * @param subtype the subtype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro notification
	 * @throws NoSuchFaroNotificationException if a matching faro notification could not be found
	 */
	@Override
	public FaroNotification findByG_GtC_O_R_T_S_Last(
			long groupId, long createTime, long ownerId, boolean read,
			String type, String subtype,
			OrderByComparator<FaroNotification> orderByComparator)
		throws NoSuchFaroNotificationException {

		FaroNotification faroNotification = fetchByG_GtC_O_R_T_S_Last(
			groupId, createTime, ownerId, read, type, subtype,
			orderByComparator);

		if (faroNotification != null) {
			return faroNotification;
		}

		StringBundler sb = new StringBundler(14);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", createTime>");
		sb.append(createTime);

		sb.append(", ownerId=");
		sb.append(ownerId);

		sb.append(", read=");
		sb.append(read);

		sb.append(", type=");
		sb.append(type);

		sb.append(", subtype=");
		sb.append(subtype);

		sb.append("}");

		throw new NoSuchFaroNotificationException(sb.toString());
	}

	/**
	 * Returns the last faro notification in the ordered set where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and read = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param read the read
	 * @param type the type
	 * @param subtype the subtype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro notification, or <code>null</code> if a matching faro notification could not be found
	 */
	@Override
	public FaroNotification fetchByG_GtC_O_R_T_S_Last(
		long groupId, long createTime, long ownerId, boolean read, String type,
		String subtype, OrderByComparator<FaroNotification> orderByComparator) {

		int count = countByG_GtC_O_R_T_S(
			groupId, createTime, ownerId, read, type, subtype);

		if (count == 0) {
			return null;
		}

		List<FaroNotification> list = findByG_GtC_O_R_T_S(
			groupId, createTime, ownerId, read, type, subtype, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the faro notifications before and after the current faro notification in the ordered set where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and read = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * @param faroNotificationId the primary key of the current faro notification
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param read the read
	 * @param type the type
	 * @param subtype the subtype
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next faro notification
	 * @throws NoSuchFaroNotificationException if a faro notification with the primary key could not be found
	 */
	@Override
	public FaroNotification[] findByG_GtC_O_R_T_S_PrevAndNext(
			long faroNotificationId, long groupId, long createTime,
			long ownerId, boolean read, String type, String subtype,
			OrderByComparator<FaroNotification> orderByComparator)
		throws NoSuchFaroNotificationException {

		type = Objects.toString(type, "");
		subtype = Objects.toString(subtype, "");

		FaroNotification faroNotification = findByPrimaryKey(
			faroNotificationId);

		Session session = null;

		try {
			session = openSession();

			FaroNotification[] array = new FaroNotificationImpl[3];

			array[0] = getByG_GtC_O_R_T_S_PrevAndNext(
				session, faroNotification, groupId, createTime, ownerId, read,
				type, subtype, orderByComparator, true);

			array[1] = faroNotification;

			array[2] = getByG_GtC_O_R_T_S_PrevAndNext(
				session, faroNotification, groupId, createTime, ownerId, read,
				type, subtype, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FaroNotification getByG_GtC_O_R_T_S_PrevAndNext(
		Session session, FaroNotification faroNotification, long groupId,
		long createTime, long ownerId, boolean read, String type,
		String subtype, OrderByComparator<FaroNotification> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				9 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(8);
		}

		sb.append(_SQL_SELECT_FARONOTIFICATION_WHERE);

		sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_CREATETIME_2);

		sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_OWNERID_2);

		sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_READ_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_TYPE_3);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_TYPE_2);
		}

		boolean bindSubtype = false;

		if (subtype.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_SUBTYPE_3);
		}
		else {
			bindSubtype = true;

			sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_SUBTYPE_2);
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
			sb.append(FaroNotificationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(createTime);

		queryPos.add(ownerId);

		queryPos.add(read);

		if (bindType) {
			queryPos.add(type);
		}

		if (bindSubtype) {
			queryPos.add(subtype);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						faroNotification)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FaroNotification> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = any &#63; and read = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerIds the owner IDs
	 * @param read the read
	 * @param type the type
	 * @param subtype the subtype
	 * @return the matching faro notifications
	 */
	@Override
	public List<FaroNotification> findByG_GtC_O_R_T_S(
		long groupId, long createTime, long[] ownerIds, boolean read,
		String type, String subtype) {

		return findByG_GtC_O_R_T_S(
			groupId, createTime, ownerIds, read, type, subtype,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = any &#63; and read = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerIds the owner IDs
	 * @param read the read
	 * @param type the type
	 * @param subtype the subtype
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @return the range of matching faro notifications
	 */
	@Override
	public List<FaroNotification> findByG_GtC_O_R_T_S(
		long groupId, long createTime, long[] ownerIds, boolean read,
		String type, String subtype, int start, int end) {

		return findByG_GtC_O_R_T_S(
			groupId, createTime, ownerIds, read, type, subtype, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = any &#63; and read = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerIds the owner IDs
	 * @param read the read
	 * @param type the type
	 * @param subtype the subtype
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro notifications
	 */
	@Override
	public List<FaroNotification> findByG_GtC_O_R_T_S(
		long groupId, long createTime, long[] ownerIds, boolean read,
		String type, String subtype, int start, int end,
		OrderByComparator<FaroNotification> orderByComparator) {

		return findByG_GtC_O_R_T_S(
			groupId, createTime, ownerIds, read, type, subtype, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and read = &#63; and type = &#63; and subtype = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerIds the owner IDs
	 * @param read the read
	 * @param type the type
	 * @param subtype the subtype
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro notifications
	 */
	@Override
	public List<FaroNotification> findByG_GtC_O_R_T_S(
		long groupId, long createTime, long[] ownerIds, boolean read,
		String type, String subtype, int start, int end,
		OrderByComparator<FaroNotification> orderByComparator,
		boolean useFinderCache) {

		if (ownerIds == null) {
			ownerIds = new long[0];
		}
		else if (ownerIds.length > 1) {
			ownerIds = ArrayUtil.sortedUnique(ownerIds);
		}

		type = Objects.toString(type, "");
		subtype = Objects.toString(subtype, "");

		if (ownerIds.length == 1) {
			return findByG_GtC_O_R_T_S(
				groupId, createTime, ownerIds[0], read, type, subtype, start,
				end, orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					groupId, createTime, StringUtil.merge(ownerIds), read, type,
					subtype
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				groupId, createTime, StringUtil.merge(ownerIds), read, type,
				subtype, start, end, orderByComparator
			};
		}

		List<FaroNotification> list = null;

		if (useFinderCache) {
			list = (List<FaroNotification>)finderCache.getResult(
				_finderPathWithPaginationFindByG_GtC_O_R_T_S, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FaroNotification faroNotification : list) {
					if ((groupId != faroNotification.getGroupId()) ||
						(createTime >= faroNotification.getCreateTime()) ||
						!ArrayUtil.contains(
							ownerIds, faroNotification.getOwnerId()) ||
						(read != faroNotification.isRead()) ||
						!type.equals(faroNotification.getType()) ||
						!subtype.equals(faroNotification.getSubtype())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_FARONOTIFICATION_WHERE);

			sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_CREATETIME_2);

			if (ownerIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_OWNERID_7);

				sb.append(StringUtil.merge(ownerIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_READ_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_TYPE_2);
			}

			boolean bindSubtype = false;

			if (subtype.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_SUBTYPE_3);
			}
			else {
				bindSubtype = true;

				sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_SUBTYPE_2);
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FaroNotificationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(createTime);

				queryPos.add(read);

				if (bindType) {
					queryPos.add(type);
				}

				if (bindSubtype) {
					queryPos.add(subtype);
				}

				list = (List<FaroNotification>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByG_GtC_O_R_T_S,
						finderArgs, list);
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
	 * Removes all the faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and read = &#63; and type = &#63; and subtype = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param read the read
	 * @param type the type
	 * @param subtype the subtype
	 */
	@Override
	public void removeByG_GtC_O_R_T_S(
		long groupId, long createTime, long ownerId, boolean read, String type,
		String subtype) {

		for (FaroNotification faroNotification :
				findByG_GtC_O_R_T_S(
					groupId, createTime, ownerId, read, type, subtype,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(faroNotification);
		}
	}

	/**
	 * Returns the number of faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = &#63; and read = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerId the owner ID
	 * @param read the read
	 * @param type the type
	 * @param subtype the subtype
	 * @return the number of matching faro notifications
	 */
	@Override
	public int countByG_GtC_O_R_T_S(
		long groupId, long createTime, long ownerId, boolean read, String type,
		String subtype) {

		type = Objects.toString(type, "");
		subtype = Objects.toString(subtype, "");

		FinderPath finderPath = _finderPathWithPaginationCountByG_GtC_O_R_T_S;

		Object[] finderArgs = new Object[] {
			groupId, createTime, ownerId, read, type, subtype
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(7);

			sb.append(_SQL_COUNT_FARONOTIFICATION_WHERE);

			sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_CREATETIME_2);

			sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_OWNERID_2);

			sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_READ_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_TYPE_2);
			}

			boolean bindSubtype = false;

			if (subtype.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_SUBTYPE_3);
			}
			else {
				bindSubtype = true;

				sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_SUBTYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(createTime);

				queryPos.add(ownerId);

				queryPos.add(read);

				if (bindType) {
					queryPos.add(type);
				}

				if (bindSubtype) {
					queryPos.add(subtype);
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

	/**
	 * Returns the number of faro notifications where groupId = &#63; and createTime &gt; &#63; and ownerId = any &#63; and read = &#63; and type = &#63; and subtype = &#63;.
	 *
	 * @param groupId the group ID
	 * @param createTime the create time
	 * @param ownerIds the owner IDs
	 * @param read the read
	 * @param type the type
	 * @param subtype the subtype
	 * @return the number of matching faro notifications
	 */
	@Override
	public int countByG_GtC_O_R_T_S(
		long groupId, long createTime, long[] ownerIds, boolean read,
		String type, String subtype) {

		if (ownerIds == null) {
			ownerIds = new long[0];
		}
		else if (ownerIds.length > 1) {
			ownerIds = ArrayUtil.sortedUnique(ownerIds);
		}

		type = Objects.toString(type, "");
		subtype = Objects.toString(subtype, "");

		Object[] finderArgs = new Object[] {
			groupId, createTime, StringUtil.merge(ownerIds), read, type, subtype
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByG_GtC_O_R_T_S, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_FARONOTIFICATION_WHERE);

			sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_CREATETIME_2);

			if (ownerIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_OWNERID_7);

				sb.append(StringUtil.merge(ownerIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_READ_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_TYPE_2);
			}

			boolean bindSubtype = false;

			if (subtype.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_SUBTYPE_3);
			}
			else {
				bindSubtype = true;

				sb.append(_FINDER_COLUMN_G_GTC_O_R_T_S_SUBTYPE_2);
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(createTime);

				queryPos.add(read);

				if (bindType) {
					queryPos.add(type);
				}

				if (bindSubtype) {
					queryPos.add(subtype);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByG_GtC_O_R_T_S, finderArgs,
					count);
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

	private static final String _FINDER_COLUMN_G_GTC_O_R_T_S_GROUPID_2 =
		"faroNotification.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_GTC_O_R_T_S_CREATETIME_2 =
		"faroNotification.createTime > ? AND ";

	private static final String _FINDER_COLUMN_G_GTC_O_R_T_S_OWNERID_2 =
		"faroNotification.ownerId = ? AND ";

	private static final String _FINDER_COLUMN_G_GTC_O_R_T_S_OWNERID_7 =
		"faroNotification.ownerId IN (";

	private static final String _FINDER_COLUMN_G_GTC_O_R_T_S_READ_2 =
		"faroNotification.read = ? AND ";

	private static final String _FINDER_COLUMN_G_GTC_O_R_T_S_TYPE_2 =
		"faroNotification.type = ? AND ";

	private static final String _FINDER_COLUMN_G_GTC_O_R_T_S_TYPE_3 =
		"(faroNotification.type IS NULL OR faroNotification.type = '') AND ";

	private static final String _FINDER_COLUMN_G_GTC_O_R_T_S_SUBTYPE_2 =
		"faroNotification.subtype = ?";

	private static final String _FINDER_COLUMN_G_GTC_O_R_T_S_SUBTYPE_3 =
		"(faroNotification.subtype IS NULL OR faroNotification.subtype = '')";

	public FaroNotificationPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("read", "read_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(FaroNotification.class);

		setModelImplClass(FaroNotificationImpl.class);
		setModelPKClass(long.class);

		setTable(FaroNotificationTable.INSTANCE);
	}

	/**
	 * Caches the faro notification in the entity cache if it is enabled.
	 *
	 * @param faroNotification the faro notification
	 */
	@Override
	public void cacheResult(FaroNotification faroNotification) {
		entityCache.putResult(
			FaroNotificationImpl.class, faroNotification.getPrimaryKey(),
			faroNotification);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the faro notifications in the entity cache if it is enabled.
	 *
	 * @param faroNotifications the faro notifications
	 */
	@Override
	public void cacheResult(List<FaroNotification> faroNotifications) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (faroNotifications.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (FaroNotification faroNotification : faroNotifications) {
			if (entityCache.getResult(
					FaroNotificationImpl.class,
					faroNotification.getPrimaryKey()) == null) {

				cacheResult(faroNotification);
			}
		}
	}

	/**
	 * Clears the cache for all faro notifications.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(FaroNotificationImpl.class);

		finderCache.clearCache(FaroNotificationImpl.class);
	}

	/**
	 * Clears the cache for the faro notification.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(FaroNotification faroNotification) {
		entityCache.removeResult(FaroNotificationImpl.class, faroNotification);
	}

	@Override
	public void clearCache(List<FaroNotification> faroNotifications) {
		for (FaroNotification faroNotification : faroNotifications) {
			entityCache.removeResult(
				FaroNotificationImpl.class, faroNotification);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FaroNotificationImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(FaroNotificationImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new faro notification with the primary key. Does not add the faro notification to the database.
	 *
	 * @param faroNotificationId the primary key for the new faro notification
	 * @return the new faro notification
	 */
	@Override
	public FaroNotification create(long faroNotificationId) {
		FaroNotification faroNotification = new FaroNotificationImpl();

		faroNotification.setNew(true);
		faroNotification.setPrimaryKey(faroNotificationId);

		faroNotification.setCompanyId(CompanyThreadLocal.getCompanyId());

		return faroNotification;
	}

	/**
	 * Removes the faro notification with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param faroNotificationId the primary key of the faro notification
	 * @return the faro notification that was removed
	 * @throws NoSuchFaroNotificationException if a faro notification with the primary key could not be found
	 */
	@Override
	public FaroNotification remove(long faroNotificationId)
		throws NoSuchFaroNotificationException {

		return remove((Serializable)faroNotificationId);
	}

	/**
	 * Removes the faro notification with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the faro notification
	 * @return the faro notification that was removed
	 * @throws NoSuchFaroNotificationException if a faro notification with the primary key could not be found
	 */
	@Override
	public FaroNotification remove(Serializable primaryKey)
		throws NoSuchFaroNotificationException {

		Session session = null;

		try {
			session = openSession();

			FaroNotification faroNotification = (FaroNotification)session.get(
				FaroNotificationImpl.class, primaryKey);

			if (faroNotification == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFaroNotificationException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(faroNotification);
		}
		catch (NoSuchFaroNotificationException noSuchEntityException) {
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
	protected FaroNotification removeImpl(FaroNotification faroNotification) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(faroNotification)) {
				faroNotification = (FaroNotification)session.get(
					FaroNotificationImpl.class,
					faroNotification.getPrimaryKeyObj());
			}

			if (faroNotification != null) {
				session.delete(faroNotification);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (faroNotification != null) {
			clearCache(faroNotification);
		}

		return faroNotification;
	}

	@Override
	public FaroNotification updateImpl(FaroNotification faroNotification) {
		boolean isNew = faroNotification.isNew();

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(faroNotification);
			}
			else {
				faroNotification = (FaroNotification)session.merge(
					faroNotification);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			FaroNotificationImpl.class, faroNotification, false, true);

		if (isNew) {
			faroNotification.setNew(false);
		}

		faroNotification.resetOriginalValues();

		return faroNotification;
	}

	/**
	 * Returns the faro notification with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the faro notification
	 * @return the faro notification
	 * @throws NoSuchFaroNotificationException if a faro notification with the primary key could not be found
	 */
	@Override
	public FaroNotification findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFaroNotificationException {

		FaroNotification faroNotification = fetchByPrimaryKey(primaryKey);

		if (faroNotification == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFaroNotificationException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return faroNotification;
	}

	/**
	 * Returns the faro notification with the primary key or throws a <code>NoSuchFaroNotificationException</code> if it could not be found.
	 *
	 * @param faroNotificationId the primary key of the faro notification
	 * @return the faro notification
	 * @throws NoSuchFaroNotificationException if a faro notification with the primary key could not be found
	 */
	@Override
	public FaroNotification findByPrimaryKey(long faroNotificationId)
		throws NoSuchFaroNotificationException {

		return findByPrimaryKey((Serializable)faroNotificationId);
	}

	/**
	 * Returns the faro notification with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param faroNotificationId the primary key of the faro notification
	 * @return the faro notification, or <code>null</code> if a faro notification with the primary key could not be found
	 */
	@Override
	public FaroNotification fetchByPrimaryKey(long faroNotificationId) {
		return fetchByPrimaryKey((Serializable)faroNotificationId);
	}

	/**
	 * Returns all the faro notifications.
	 *
	 * @return the faro notifications
	 */
	@Override
	public List<FaroNotification> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the faro notifications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @return the range of faro notifications
	 */
	@Override
	public List<FaroNotification> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the faro notifications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of faro notifications
	 */
	@Override
	public List<FaroNotification> findAll(
		int start, int end,
		OrderByComparator<FaroNotification> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the faro notifications.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroNotificationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro notifications
	 * @param end the upper bound of the range of faro notifications (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of faro notifications
	 */
	@Override
	public List<FaroNotification> findAll(
		int start, int end,
		OrderByComparator<FaroNotification> orderByComparator,
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

		List<FaroNotification> list = null;

		if (useFinderCache) {
			list = (List<FaroNotification>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_FARONOTIFICATION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_FARONOTIFICATION;

				sql = sql.concat(FaroNotificationModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<FaroNotification>)QueryUtil.list(
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
	 * Removes all the faro notifications from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (FaroNotification faroNotification : findAll()) {
			remove(faroNotification);
		}
	}

	/**
	 * Returns the number of faro notifications.
	 *
	 * @return the number of faro notifications
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_FARONOTIFICATION);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "faroNotificationId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_FARONOTIFICATION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return FaroNotificationModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the faro notification persistence.
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

		_finderPathWithPaginationFindByLtCreateTime = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLtCreateTime",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"createTime"}, true);

		_finderPathWithPaginationCountByLtCreateTime = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLtCreateTime",
			new String[] {Long.class.getName()}, new String[] {"createTime"},
			false);

		_finderPathWithPaginationFindByG_GtC_O_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_GtC_O_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "createTime", "ownerId", "type_"}, true);

		_finderPathWithPaginationCountByG_GtC_O_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_GtC_O_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName()
			},
			new String[] {"groupId", "createTime", "ownerId", "type_"}, false);

		_finderPathWithPaginationFindByG_GtC_O_T_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_GtC_O_T_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {
				"groupId", "createTime", "ownerId", "type_", "subtype"
			},
			true);

		_finderPathWithPaginationCountByG_GtC_O_T_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_GtC_O_T_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {
				"groupId", "createTime", "ownerId", "type_", "subtype"
			},
			false);

		_finderPathWithPaginationFindByG_GtC_O_R_T_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_GtC_O_R_T_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {
				"groupId", "createTime", "ownerId", "read_", "type_", "subtype"
			},
			true);

		_finderPathWithPaginationCountByG_GtC_O_R_T_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_GtC_O_R_T_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), String.class.getName()
			},
			new String[] {
				"groupId", "createTime", "ownerId", "read_", "type_", "subtype"
			},
			false);

		FaroNotificationUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		FaroNotificationUtil.setPersistence(null);

		entityCache.removeCache(FaroNotificationImpl.class.getName());
	}

	@Override
	@Reference(
		target = OSBFaroPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = OSBFaroPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = OSBFaroPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_FARONOTIFICATION =
		"SELECT faroNotification FROM FaroNotification faroNotification";

	private static final String _SQL_SELECT_FARONOTIFICATION_WHERE =
		"SELECT faroNotification FROM FaroNotification faroNotification WHERE ";

	private static final String _SQL_COUNT_FARONOTIFICATION =
		"SELECT COUNT(faroNotification) FROM FaroNotification faroNotification";

	private static final String _SQL_COUNT_FARONOTIFICATION_WHERE =
		"SELECT COUNT(faroNotification) FROM FaroNotification faroNotification WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "faroNotification.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No FaroNotification exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No FaroNotification exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		FaroNotificationPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"read", "type"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}