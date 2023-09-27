/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.kernel.exception.NoSuchPortletPreferenceValueException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.PortletPreferenceValue;
import com.liferay.portal.kernel.model.PortletPreferenceValueTable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.PortletPreferenceValuePersistence;
import com.liferay.portal.kernel.service.persistence.PortletPreferenceValueUtil;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelperUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.model.impl.PortletPreferenceValueImpl;
import com.liferay.portal.model.impl.PortletPreferenceValueModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the portlet preference value service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class PortletPreferenceValuePersistenceImpl
	extends BasePersistenceImpl<PortletPreferenceValue>
	implements PortletPreferenceValuePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>PortletPreferenceValueUtil</code> to access the portlet preference value persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		PortletPreferenceValueImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByPortletPreferencesId;
	private FinderPath _finderPathWithoutPaginationFindByPortletPreferencesId;
	private FinderPath _finderPathCountByPortletPreferencesId;

	/**
	 * Returns all the portlet preference values where portletPreferencesId = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @return the matching portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findByPortletPreferencesId(
		long portletPreferencesId) {

		return findByPortletPreferencesId(
			portletPreferencesId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the portlet preference values where portletPreferencesId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @return the range of matching portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findByPortletPreferencesId(
		long portletPreferencesId, int start, int end) {

		return findByPortletPreferencesId(
			portletPreferencesId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the portlet preference values where portletPreferencesId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findByPortletPreferencesId(
		long portletPreferencesId, int start, int end,
		OrderByComparator<PortletPreferenceValue> orderByComparator) {

		return findByPortletPreferencesId(
			portletPreferencesId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the portlet preference values where portletPreferencesId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findByPortletPreferencesId(
		long portletPreferencesId, int start, int end,
		OrderByComparator<PortletPreferenceValue> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			PortletPreferenceValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath =
					_finderPathWithoutPaginationFindByPortletPreferencesId;
				finderArgs = new Object[] {portletPreferencesId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByPortletPreferencesId;
			finderArgs = new Object[] {
				portletPreferencesId, start, end, orderByComparator
			};
		}

		List<PortletPreferenceValue> list = null;

		if (useFinderCache && productionMode) {
			list = (List<PortletPreferenceValue>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (PortletPreferenceValue portletPreferenceValue : list) {
					if (portletPreferencesId !=
							portletPreferenceValue.getPortletPreferencesId()) {

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

			sb.append(_SQL_SELECT_PORTLETPREFERENCEVALUE_WHERE);

			sb.append(
				_FINDER_COLUMN_PORTLETPREFERENCESID_PORTLETPREFERENCESID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(PortletPreferenceValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(portletPreferencesId);

				list = (List<PortletPreferenceValue>)QueryUtil.list(
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
	 * Returns the first portlet preference value in the ordered set where portletPreferencesId = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preference value
	 * @throws NoSuchPortletPreferenceValueException if a matching portlet preference value could not be found
	 */
	@Override
	public PortletPreferenceValue findByPortletPreferencesId_First(
			long portletPreferencesId,
			OrderByComparator<PortletPreferenceValue> orderByComparator)
		throws NoSuchPortletPreferenceValueException {

		PortletPreferenceValue portletPreferenceValue =
			fetchByPortletPreferencesId_First(
				portletPreferencesId, orderByComparator);

		if (portletPreferenceValue != null) {
			return portletPreferenceValue;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("portletPreferencesId=");
		sb.append(portletPreferencesId);

		sb.append("}");

		throw new NoSuchPortletPreferenceValueException(sb.toString());
	}

	/**
	 * Returns the first portlet preference value in the ordered set where portletPreferencesId = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preference value, or <code>null</code> if a matching portlet preference value could not be found
	 */
	@Override
	public PortletPreferenceValue fetchByPortletPreferencesId_First(
		long portletPreferencesId,
		OrderByComparator<PortletPreferenceValue> orderByComparator) {

		List<PortletPreferenceValue> list = findByPortletPreferencesId(
			portletPreferencesId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last portlet preference value in the ordered set where portletPreferencesId = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preference value
	 * @throws NoSuchPortletPreferenceValueException if a matching portlet preference value could not be found
	 */
	@Override
	public PortletPreferenceValue findByPortletPreferencesId_Last(
			long portletPreferencesId,
			OrderByComparator<PortletPreferenceValue> orderByComparator)
		throws NoSuchPortletPreferenceValueException {

		PortletPreferenceValue portletPreferenceValue =
			fetchByPortletPreferencesId_Last(
				portletPreferencesId, orderByComparator);

		if (portletPreferenceValue != null) {
			return portletPreferenceValue;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("portletPreferencesId=");
		sb.append(portletPreferencesId);

		sb.append("}");

		throw new NoSuchPortletPreferenceValueException(sb.toString());
	}

	/**
	 * Returns the last portlet preference value in the ordered set where portletPreferencesId = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preference value, or <code>null</code> if a matching portlet preference value could not be found
	 */
	@Override
	public PortletPreferenceValue fetchByPortletPreferencesId_Last(
		long portletPreferencesId,
		OrderByComparator<PortletPreferenceValue> orderByComparator) {

		int count = countByPortletPreferencesId(portletPreferencesId);

		if (count == 0) {
			return null;
		}

		List<PortletPreferenceValue> list = findByPortletPreferencesId(
			portletPreferencesId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the portlet preference values before and after the current portlet preference value in the ordered set where portletPreferencesId = &#63;.
	 *
	 * @param portletPreferenceValueId the primary key of the current portlet preference value
	 * @param portletPreferencesId the portlet preferences ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next portlet preference value
	 * @throws NoSuchPortletPreferenceValueException if a portlet preference value with the primary key could not be found
	 */
	@Override
	public PortletPreferenceValue[] findByPortletPreferencesId_PrevAndNext(
			long portletPreferenceValueId, long portletPreferencesId,
			OrderByComparator<PortletPreferenceValue> orderByComparator)
		throws NoSuchPortletPreferenceValueException {

		PortletPreferenceValue portletPreferenceValue = findByPrimaryKey(
			portletPreferenceValueId);

		Session session = null;

		try {
			session = openSession();

			PortletPreferenceValue[] array = new PortletPreferenceValueImpl[3];

			array[0] = getByPortletPreferencesId_PrevAndNext(
				session, portletPreferenceValue, portletPreferencesId,
				orderByComparator, true);

			array[1] = portletPreferenceValue;

			array[2] = getByPortletPreferencesId_PrevAndNext(
				session, portletPreferenceValue, portletPreferencesId,
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

	protected PortletPreferenceValue getByPortletPreferencesId_PrevAndNext(
		Session session, PortletPreferenceValue portletPreferenceValue,
		long portletPreferencesId,
		OrderByComparator<PortletPreferenceValue> orderByComparator,
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

		sb.append(_SQL_SELECT_PORTLETPREFERENCEVALUE_WHERE);

		sb.append(_FINDER_COLUMN_PORTLETPREFERENCESID_PORTLETPREFERENCESID_2);

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
			sb.append(PortletPreferenceValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(portletPreferencesId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						portletPreferenceValue)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<PortletPreferenceValue> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the portlet preference values where portletPreferencesId = &#63; from the database.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 */
	@Override
	public void removeByPortletPreferencesId(long portletPreferencesId) {
		for (PortletPreferenceValue portletPreferenceValue :
				findByPortletPreferencesId(
					portletPreferencesId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(portletPreferenceValue);
		}
	}

	/**
	 * Returns the number of portlet preference values where portletPreferencesId = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @return the number of matching portlet preference values
	 */
	@Override
	public int countByPortletPreferencesId(long portletPreferencesId) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			PortletPreferenceValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByPortletPreferencesId;

			finderArgs = new Object[] {portletPreferencesId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_PORTLETPREFERENCEVALUE_WHERE);

			sb.append(
				_FINDER_COLUMN_PORTLETPREFERENCESID_PORTLETPREFERENCESID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(portletPreferencesId);

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

	private static final String
		_FINDER_COLUMN_PORTLETPREFERENCESID_PORTLETPREFERENCESID_2 =
			"portletPreferenceValue.portletPreferencesId = ?";

	private FinderPath _finderPathWithPaginationFindByP_N;
	private FinderPath _finderPathWithoutPaginationFindByP_N;
	private FinderPath _finderPathCountByP_N;

	/**
	 * Returns all the portlet preference values where portletPreferencesId = &#63; and name = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @return the matching portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findByP_N(
		long portletPreferencesId, String name) {

		return findByP_N(
			portletPreferencesId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the portlet preference values where portletPreferencesId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @return the range of matching portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findByP_N(
		long portletPreferencesId, String name, int start, int end) {

		return findByP_N(portletPreferencesId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the portlet preference values where portletPreferencesId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findByP_N(
		long portletPreferencesId, String name, int start, int end,
		OrderByComparator<PortletPreferenceValue> orderByComparator) {

		return findByP_N(
			portletPreferencesId, name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the portlet preference values where portletPreferencesId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findByP_N(
		long portletPreferencesId, String name, int start, int end,
		OrderByComparator<PortletPreferenceValue> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			PortletPreferenceValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByP_N;
				finderArgs = new Object[] {portletPreferencesId, name};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByP_N;
			finderArgs = new Object[] {
				portletPreferencesId, name, start, end, orderByComparator
			};
		}

		List<PortletPreferenceValue> list = null;

		if (useFinderCache && productionMode) {
			list = (List<PortletPreferenceValue>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (PortletPreferenceValue portletPreferenceValue : list) {
					if ((portletPreferencesId !=
							portletPreferenceValue.getPortletPreferencesId()) ||
						!name.equals(portletPreferenceValue.getName())) {

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

			sb.append(_SQL_SELECT_PORTLETPREFERENCEVALUE_WHERE);

			sb.append(_FINDER_COLUMN_P_N_PORTLETPREFERENCESID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_P_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_P_N_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(PortletPreferenceValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(portletPreferencesId);

				if (bindName) {
					queryPos.add(name);
				}

				list = (List<PortletPreferenceValue>)QueryUtil.list(
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
	 * Returns the first portlet preference value in the ordered set where portletPreferencesId = &#63; and name = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preference value
	 * @throws NoSuchPortletPreferenceValueException if a matching portlet preference value could not be found
	 */
	@Override
	public PortletPreferenceValue findByP_N_First(
			long portletPreferencesId, String name,
			OrderByComparator<PortletPreferenceValue> orderByComparator)
		throws NoSuchPortletPreferenceValueException {

		PortletPreferenceValue portletPreferenceValue = fetchByP_N_First(
			portletPreferencesId, name, orderByComparator);

		if (portletPreferenceValue != null) {
			return portletPreferenceValue;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("portletPreferencesId=");
		sb.append(portletPreferencesId);

		sb.append(", name=");
		sb.append(name);

		sb.append("}");

		throw new NoSuchPortletPreferenceValueException(sb.toString());
	}

	/**
	 * Returns the first portlet preference value in the ordered set where portletPreferencesId = &#63; and name = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preference value, or <code>null</code> if a matching portlet preference value could not be found
	 */
	@Override
	public PortletPreferenceValue fetchByP_N_First(
		long portletPreferencesId, String name,
		OrderByComparator<PortletPreferenceValue> orderByComparator) {

		List<PortletPreferenceValue> list = findByP_N(
			portletPreferencesId, name, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last portlet preference value in the ordered set where portletPreferencesId = &#63; and name = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preference value
	 * @throws NoSuchPortletPreferenceValueException if a matching portlet preference value could not be found
	 */
	@Override
	public PortletPreferenceValue findByP_N_Last(
			long portletPreferencesId, String name,
			OrderByComparator<PortletPreferenceValue> orderByComparator)
		throws NoSuchPortletPreferenceValueException {

		PortletPreferenceValue portletPreferenceValue = fetchByP_N_Last(
			portletPreferencesId, name, orderByComparator);

		if (portletPreferenceValue != null) {
			return portletPreferenceValue;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("portletPreferencesId=");
		sb.append(portletPreferencesId);

		sb.append(", name=");
		sb.append(name);

		sb.append("}");

		throw new NoSuchPortletPreferenceValueException(sb.toString());
	}

	/**
	 * Returns the last portlet preference value in the ordered set where portletPreferencesId = &#63; and name = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preference value, or <code>null</code> if a matching portlet preference value could not be found
	 */
	@Override
	public PortletPreferenceValue fetchByP_N_Last(
		long portletPreferencesId, String name,
		OrderByComparator<PortletPreferenceValue> orderByComparator) {

		int count = countByP_N(portletPreferencesId, name);

		if (count == 0) {
			return null;
		}

		List<PortletPreferenceValue> list = findByP_N(
			portletPreferencesId, name, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the portlet preference values before and after the current portlet preference value in the ordered set where portletPreferencesId = &#63; and name = &#63;.
	 *
	 * @param portletPreferenceValueId the primary key of the current portlet preference value
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next portlet preference value
	 * @throws NoSuchPortletPreferenceValueException if a portlet preference value with the primary key could not be found
	 */
	@Override
	public PortletPreferenceValue[] findByP_N_PrevAndNext(
			long portletPreferenceValueId, long portletPreferencesId,
			String name,
			OrderByComparator<PortletPreferenceValue> orderByComparator)
		throws NoSuchPortletPreferenceValueException {

		name = Objects.toString(name, "");

		PortletPreferenceValue portletPreferenceValue = findByPrimaryKey(
			portletPreferenceValueId);

		Session session = null;

		try {
			session = openSession();

			PortletPreferenceValue[] array = new PortletPreferenceValueImpl[3];

			array[0] = getByP_N_PrevAndNext(
				session, portletPreferenceValue, portletPreferencesId, name,
				orderByComparator, true);

			array[1] = portletPreferenceValue;

			array[2] = getByP_N_PrevAndNext(
				session, portletPreferenceValue, portletPreferencesId, name,
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

	protected PortletPreferenceValue getByP_N_PrevAndNext(
		Session session, PortletPreferenceValue portletPreferenceValue,
		long portletPreferencesId, String name,
		OrderByComparator<PortletPreferenceValue> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_PORTLETPREFERENCEVALUE_WHERE);

		sb.append(_FINDER_COLUMN_P_N_PORTLETPREFERENCESID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_P_N_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_P_N_NAME_2);
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
			sb.append(PortletPreferenceValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(portletPreferencesId);

		if (bindName) {
			queryPos.add(name);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						portletPreferenceValue)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<PortletPreferenceValue> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the portlet preference values where portletPreferencesId = &#63; and name = &#63; from the database.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 */
	@Override
	public void removeByP_N(long portletPreferencesId, String name) {
		for (PortletPreferenceValue portletPreferenceValue :
				findByP_N(
					portletPreferencesId, name, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(portletPreferenceValue);
		}
	}

	/**
	 * Returns the number of portlet preference values where portletPreferencesId = &#63; and name = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @return the number of matching portlet preference values
	 */
	@Override
	public int countByP_N(long portletPreferencesId, String name) {
		name = Objects.toString(name, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			PortletPreferenceValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByP_N;

			finderArgs = new Object[] {portletPreferencesId, name};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_PORTLETPREFERENCEVALUE_WHERE);

			sb.append(_FINDER_COLUMN_P_N_PORTLETPREFERENCESID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_P_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_P_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(portletPreferencesId);

				if (bindName) {
					queryPos.add(name);
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

	private static final String _FINDER_COLUMN_P_N_PORTLETPREFERENCESID_2 =
		"portletPreferenceValue.portletPreferencesId = ? AND ";

	private static final String _FINDER_COLUMN_P_N_NAME_2 =
		"portletPreferenceValue.name = ?";

	private static final String _FINDER_COLUMN_P_N_NAME_3 =
		"(portletPreferenceValue.name IS NULL OR portletPreferenceValue.name = '')";

	private FinderPath _finderPathWithPaginationFindByC_N_SV;
	private FinderPath _finderPathWithoutPaginationFindByC_N_SV;
	private FinderPath _finderPathCountByC_N_SV;

	/**
	 * Returns all the portlet preference values where companyId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param smallValue the small value
	 * @return the matching portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findByC_N_SV(
		long companyId, String name, String smallValue) {

		return findByC_N_SV(
			companyId, name, smallValue, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the portlet preference values where companyId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @return the range of matching portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findByC_N_SV(
		long companyId, String name, String smallValue, int start, int end) {

		return findByC_N_SV(companyId, name, smallValue, start, end, null);
	}

	/**
	 * Returns an ordered range of all the portlet preference values where companyId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findByC_N_SV(
		long companyId, String name, String smallValue, int start, int end,
		OrderByComparator<PortletPreferenceValue> orderByComparator) {

		return findByC_N_SV(
			companyId, name, smallValue, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the portlet preference values where companyId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findByC_N_SV(
		long companyId, String name, String smallValue, int start, int end,
		OrderByComparator<PortletPreferenceValue> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");
		smallValue = Objects.toString(smallValue, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			PortletPreferenceValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_N_SV;
				finderArgs = new Object[] {companyId, name, smallValue};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_N_SV;
			finderArgs = new Object[] {
				companyId, name, smallValue, start, end, orderByComparator
			};
		}

		List<PortletPreferenceValue> list = null;

		if (useFinderCache && productionMode) {
			list = (List<PortletPreferenceValue>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (PortletPreferenceValue portletPreferenceValue : list) {
					if ((companyId != portletPreferenceValue.getCompanyId()) ||
						!name.equals(portletPreferenceValue.getName()) ||
						!smallValue.equals(
							portletPreferenceValue.getSmallValue())) {

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

			sb.append(_SQL_SELECT_PORTLETPREFERENCEVALUE_WHERE);

			sb.append(_FINDER_COLUMN_C_N_SV_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_N_SV_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_C_N_SV_NAME_2);
			}

			boolean bindSmallValue = false;

			if (smallValue.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_N_SV_SMALLVALUE_3);
			}
			else {
				bindSmallValue = true;

				sb.append(_FINDER_COLUMN_C_N_SV_SMALLVALUE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(PortletPreferenceValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindName) {
					queryPos.add(name);
				}

				if (bindSmallValue) {
					queryPos.add(smallValue);
				}

				list = (List<PortletPreferenceValue>)QueryUtil.list(
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
	 * Returns the first portlet preference value in the ordered set where companyId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preference value
	 * @throws NoSuchPortletPreferenceValueException if a matching portlet preference value could not be found
	 */
	@Override
	public PortletPreferenceValue findByC_N_SV_First(
			long companyId, String name, String smallValue,
			OrderByComparator<PortletPreferenceValue> orderByComparator)
		throws NoSuchPortletPreferenceValueException {

		PortletPreferenceValue portletPreferenceValue = fetchByC_N_SV_First(
			companyId, name, smallValue, orderByComparator);

		if (portletPreferenceValue != null) {
			return portletPreferenceValue;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", name=");
		sb.append(name);

		sb.append(", smallValue=");
		sb.append(smallValue);

		sb.append("}");

		throw new NoSuchPortletPreferenceValueException(sb.toString());
	}

	/**
	 * Returns the first portlet preference value in the ordered set where companyId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preference value, or <code>null</code> if a matching portlet preference value could not be found
	 */
	@Override
	public PortletPreferenceValue fetchByC_N_SV_First(
		long companyId, String name, String smallValue,
		OrderByComparator<PortletPreferenceValue> orderByComparator) {

		List<PortletPreferenceValue> list = findByC_N_SV(
			companyId, name, smallValue, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last portlet preference value in the ordered set where companyId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preference value
	 * @throws NoSuchPortletPreferenceValueException if a matching portlet preference value could not be found
	 */
	@Override
	public PortletPreferenceValue findByC_N_SV_Last(
			long companyId, String name, String smallValue,
			OrderByComparator<PortletPreferenceValue> orderByComparator)
		throws NoSuchPortletPreferenceValueException {

		PortletPreferenceValue portletPreferenceValue = fetchByC_N_SV_Last(
			companyId, name, smallValue, orderByComparator);

		if (portletPreferenceValue != null) {
			return portletPreferenceValue;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", name=");
		sb.append(name);

		sb.append(", smallValue=");
		sb.append(smallValue);

		sb.append("}");

		throw new NoSuchPortletPreferenceValueException(sb.toString());
	}

	/**
	 * Returns the last portlet preference value in the ordered set where companyId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preference value, or <code>null</code> if a matching portlet preference value could not be found
	 */
	@Override
	public PortletPreferenceValue fetchByC_N_SV_Last(
		long companyId, String name, String smallValue,
		OrderByComparator<PortletPreferenceValue> orderByComparator) {

		int count = countByC_N_SV(companyId, name, smallValue);

		if (count == 0) {
			return null;
		}

		List<PortletPreferenceValue> list = findByC_N_SV(
			companyId, name, smallValue, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the portlet preference values before and after the current portlet preference value in the ordered set where companyId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * @param portletPreferenceValueId the primary key of the current portlet preference value
	 * @param companyId the company ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next portlet preference value
	 * @throws NoSuchPortletPreferenceValueException if a portlet preference value with the primary key could not be found
	 */
	@Override
	public PortletPreferenceValue[] findByC_N_SV_PrevAndNext(
			long portletPreferenceValueId, long companyId, String name,
			String smallValue,
			OrderByComparator<PortletPreferenceValue> orderByComparator)
		throws NoSuchPortletPreferenceValueException {

		name = Objects.toString(name, "");
		smallValue = Objects.toString(smallValue, "");

		PortletPreferenceValue portletPreferenceValue = findByPrimaryKey(
			portletPreferenceValueId);

		Session session = null;

		try {
			session = openSession();

			PortletPreferenceValue[] array = new PortletPreferenceValueImpl[3];

			array[0] = getByC_N_SV_PrevAndNext(
				session, portletPreferenceValue, companyId, name, smallValue,
				orderByComparator, true);

			array[1] = portletPreferenceValue;

			array[2] = getByC_N_SV_PrevAndNext(
				session, portletPreferenceValue, companyId, name, smallValue,
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

	protected PortletPreferenceValue getByC_N_SV_PrevAndNext(
		Session session, PortletPreferenceValue portletPreferenceValue,
		long companyId, String name, String smallValue,
		OrderByComparator<PortletPreferenceValue> orderByComparator,
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

		sb.append(_SQL_SELECT_PORTLETPREFERENCEVALUE_WHERE);

		sb.append(_FINDER_COLUMN_C_N_SV_COMPANYID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_N_SV_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_C_N_SV_NAME_2);
		}

		boolean bindSmallValue = false;

		if (smallValue.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_N_SV_SMALLVALUE_3);
		}
		else {
			bindSmallValue = true;

			sb.append(_FINDER_COLUMN_C_N_SV_SMALLVALUE_2);
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
			sb.append(PortletPreferenceValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (bindName) {
			queryPos.add(name);
		}

		if (bindSmallValue) {
			queryPos.add(smallValue);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						portletPreferenceValue)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<PortletPreferenceValue> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the portlet preference values where companyId = &#63; and name = &#63; and smallValue = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param smallValue the small value
	 */
	@Override
	public void removeByC_N_SV(long companyId, String name, String smallValue) {
		for (PortletPreferenceValue portletPreferenceValue :
				findByC_N_SV(
					companyId, name, smallValue, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(portletPreferenceValue);
		}
	}

	/**
	 * Returns the number of portlet preference values where companyId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param smallValue the small value
	 * @return the number of matching portlet preference values
	 */
	@Override
	public int countByC_N_SV(long companyId, String name, String smallValue) {
		name = Objects.toString(name, "");
		smallValue = Objects.toString(smallValue, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			PortletPreferenceValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_N_SV;

			finderArgs = new Object[] {companyId, name, smallValue};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_PORTLETPREFERENCEVALUE_WHERE);

			sb.append(_FINDER_COLUMN_C_N_SV_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_N_SV_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_C_N_SV_NAME_2);
			}

			boolean bindSmallValue = false;

			if (smallValue.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_N_SV_SMALLVALUE_3);
			}
			else {
				bindSmallValue = true;

				sb.append(_FINDER_COLUMN_C_N_SV_SMALLVALUE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindName) {
					queryPos.add(name);
				}

				if (bindSmallValue) {
					queryPos.add(smallValue);
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

	private static final String _FINDER_COLUMN_C_N_SV_COMPANYID_2 =
		"portletPreferenceValue.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_N_SV_NAME_2 =
		"portletPreferenceValue.name = ? AND ";

	private static final String _FINDER_COLUMN_C_N_SV_NAME_3 =
		"(portletPreferenceValue.name IS NULL OR portletPreferenceValue.name = '') AND ";

	private static final String _FINDER_COLUMN_C_N_SV_SMALLVALUE_2 =
		"portletPreferenceValue.smallValue = ?";

	private static final String _FINDER_COLUMN_C_N_SV_SMALLVALUE_3 =
		"(portletPreferenceValue.smallValue IS NULL OR portletPreferenceValue.smallValue = '')";

	private FinderPath _finderPathFetchByP_I_N;
	private FinderPath _finderPathCountByP_I_N;

	/**
	 * Returns the portlet preference value where portletPreferencesId = &#63; and index = &#63; and name = &#63; or throws a <code>NoSuchPortletPreferenceValueException</code> if it could not be found.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param index the index
	 * @param name the name
	 * @return the matching portlet preference value
	 * @throws NoSuchPortletPreferenceValueException if a matching portlet preference value could not be found
	 */
	@Override
	public PortletPreferenceValue findByP_I_N(
			long portletPreferencesId, int index, String name)
		throws NoSuchPortletPreferenceValueException {

		PortletPreferenceValue portletPreferenceValue = fetchByP_I_N(
			portletPreferencesId, index, name);

		if (portletPreferenceValue == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("portletPreferencesId=");
			sb.append(portletPreferencesId);

			sb.append(", index=");
			sb.append(index);

			sb.append(", name=");
			sb.append(name);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchPortletPreferenceValueException(sb.toString());
		}

		return portletPreferenceValue;
	}

	/**
	 * Returns the portlet preference value where portletPreferencesId = &#63; and index = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param index the index
	 * @param name the name
	 * @return the matching portlet preference value, or <code>null</code> if a matching portlet preference value could not be found
	 */
	@Override
	public PortletPreferenceValue fetchByP_I_N(
		long portletPreferencesId, int index, String name) {

		return fetchByP_I_N(portletPreferencesId, index, name, true);
	}

	/**
	 * Returns the portlet preference value where portletPreferencesId = &#63; and index = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param index the index
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching portlet preference value, or <code>null</code> if a matching portlet preference value could not be found
	 */
	@Override
	public PortletPreferenceValue fetchByP_I_N(
		long portletPreferencesId, int index, String name,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {portletPreferencesId, index, name};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByP_I_N, finderArgs, this);
		}

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			PortletPreferenceValue.class);

		if (result instanceof PortletPreferenceValue) {
			PortletPreferenceValue portletPreferenceValue =
				(PortletPreferenceValue)result;

			if ((portletPreferencesId !=
					portletPreferenceValue.getPortletPreferencesId()) ||
				(index != portletPreferenceValue.getIndex()) ||
				!Objects.equals(name, portletPreferenceValue.getName())) {

				result = null;
			}
			else if (!CTPersistenceHelperUtil.isProductionMode(
						PortletPreferenceValue.class,
						portletPreferenceValue.getPrimaryKey())) {

				result = null;
			}
		}
		else if (!productionMode && (result instanceof List<?>)) {
			result = null;
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_PORTLETPREFERENCEVALUE_WHERE);

			sb.append(_FINDER_COLUMN_P_I_N_PORTLETPREFERENCESID_2);

			sb.append(_FINDER_COLUMN_P_I_N_INDEX_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_P_I_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_P_I_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(portletPreferencesId);

				queryPos.add(index);

				if (bindName) {
					queryPos.add(name);
				}

				List<PortletPreferenceValue> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						FinderCacheUtil.putResult(
							_finderPathFetchByP_I_N, finderArgs, list);
					}
				}
				else {
					PortletPreferenceValue portletPreferenceValue = list.get(0);

					result = portletPreferenceValue;

					cacheResult(portletPreferenceValue);
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
			return (PortletPreferenceValue)result;
		}
	}

	/**
	 * Removes the portlet preference value where portletPreferencesId = &#63; and index = &#63; and name = &#63; from the database.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param index the index
	 * @param name the name
	 * @return the portlet preference value that was removed
	 */
	@Override
	public PortletPreferenceValue removeByP_I_N(
			long portletPreferencesId, int index, String name)
		throws NoSuchPortletPreferenceValueException {

		PortletPreferenceValue portletPreferenceValue = findByP_I_N(
			portletPreferencesId, index, name);

		return remove(portletPreferenceValue);
	}

	/**
	 * Returns the number of portlet preference values where portletPreferencesId = &#63; and index = &#63; and name = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param index the index
	 * @param name the name
	 * @return the number of matching portlet preference values
	 */
	@Override
	public int countByP_I_N(long portletPreferencesId, int index, String name) {
		name = Objects.toString(name, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			PortletPreferenceValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByP_I_N;

			finderArgs = new Object[] {portletPreferencesId, index, name};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_PORTLETPREFERENCEVALUE_WHERE);

			sb.append(_FINDER_COLUMN_P_I_N_PORTLETPREFERENCESID_2);

			sb.append(_FINDER_COLUMN_P_I_N_INDEX_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_P_I_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_P_I_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(portletPreferencesId);

				queryPos.add(index);

				if (bindName) {
					queryPos.add(name);
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

	private static final String _FINDER_COLUMN_P_I_N_PORTLETPREFERENCESID_2 =
		"portletPreferenceValue.portletPreferencesId = ? AND ";

	private static final String _FINDER_COLUMN_P_I_N_INDEX_2 =
		"portletPreferenceValue.index = ? AND ";

	private static final String _FINDER_COLUMN_P_I_N_NAME_2 =
		"portletPreferenceValue.name = ?";

	private static final String _FINDER_COLUMN_P_I_N_NAME_3 =
		"(portletPreferenceValue.name IS NULL OR portletPreferenceValue.name = '')";

	private FinderPath _finderPathWithPaginationFindByP_N_SV;
	private FinderPath _finderPathWithoutPaginationFindByP_N_SV;
	private FinderPath _finderPathCountByP_N_SV;

	/**
	 * Returns all the portlet preference values where portletPreferencesId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param smallValue the small value
	 * @return the matching portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findByP_N_SV(
		long portletPreferencesId, String name, String smallValue) {

		return findByP_N_SV(
			portletPreferencesId, name, smallValue, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the portlet preference values where portletPreferencesId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @return the range of matching portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findByP_N_SV(
		long portletPreferencesId, String name, String smallValue, int start,
		int end) {

		return findByP_N_SV(
			portletPreferencesId, name, smallValue, start, end, null);
	}

	/**
	 * Returns an ordered range of all the portlet preference values where portletPreferencesId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findByP_N_SV(
		long portletPreferencesId, String name, String smallValue, int start,
		int end, OrderByComparator<PortletPreferenceValue> orderByComparator) {

		return findByP_N_SV(
			portletPreferencesId, name, smallValue, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the portlet preference values where portletPreferencesId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findByP_N_SV(
		long portletPreferencesId, String name, String smallValue, int start,
		int end, OrderByComparator<PortletPreferenceValue> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");
		smallValue = Objects.toString(smallValue, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			PortletPreferenceValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByP_N_SV;
				finderArgs = new Object[] {
					portletPreferencesId, name, smallValue
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByP_N_SV;
			finderArgs = new Object[] {
				portletPreferencesId, name, smallValue, start, end,
				orderByComparator
			};
		}

		List<PortletPreferenceValue> list = null;

		if (useFinderCache && productionMode) {
			list = (List<PortletPreferenceValue>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (PortletPreferenceValue portletPreferenceValue : list) {
					if ((portletPreferencesId !=
							portletPreferenceValue.getPortletPreferencesId()) ||
						!name.equals(portletPreferenceValue.getName()) ||
						!smallValue.equals(
							portletPreferenceValue.getSmallValue())) {

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

			sb.append(_SQL_SELECT_PORTLETPREFERENCEVALUE_WHERE);

			sb.append(_FINDER_COLUMN_P_N_SV_PORTLETPREFERENCESID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_P_N_SV_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_P_N_SV_NAME_2);
			}

			boolean bindSmallValue = false;

			if (smallValue.isEmpty()) {
				sb.append(_FINDER_COLUMN_P_N_SV_SMALLVALUE_3);
			}
			else {
				bindSmallValue = true;

				sb.append(_FINDER_COLUMN_P_N_SV_SMALLVALUE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(PortletPreferenceValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(portletPreferencesId);

				if (bindName) {
					queryPos.add(name);
				}

				if (bindSmallValue) {
					queryPos.add(smallValue);
				}

				list = (List<PortletPreferenceValue>)QueryUtil.list(
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
	 * Returns the first portlet preference value in the ordered set where portletPreferencesId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preference value
	 * @throws NoSuchPortletPreferenceValueException if a matching portlet preference value could not be found
	 */
	@Override
	public PortletPreferenceValue findByP_N_SV_First(
			long portletPreferencesId, String name, String smallValue,
			OrderByComparator<PortletPreferenceValue> orderByComparator)
		throws NoSuchPortletPreferenceValueException {

		PortletPreferenceValue portletPreferenceValue = fetchByP_N_SV_First(
			portletPreferencesId, name, smallValue, orderByComparator);

		if (portletPreferenceValue != null) {
			return portletPreferenceValue;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("portletPreferencesId=");
		sb.append(portletPreferencesId);

		sb.append(", name=");
		sb.append(name);

		sb.append(", smallValue=");
		sb.append(smallValue);

		sb.append("}");

		throw new NoSuchPortletPreferenceValueException(sb.toString());
	}

	/**
	 * Returns the first portlet preference value in the ordered set where portletPreferencesId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portlet preference value, or <code>null</code> if a matching portlet preference value could not be found
	 */
	@Override
	public PortletPreferenceValue fetchByP_N_SV_First(
		long portletPreferencesId, String name, String smallValue,
		OrderByComparator<PortletPreferenceValue> orderByComparator) {

		List<PortletPreferenceValue> list = findByP_N_SV(
			portletPreferencesId, name, smallValue, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last portlet preference value in the ordered set where portletPreferencesId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preference value
	 * @throws NoSuchPortletPreferenceValueException if a matching portlet preference value could not be found
	 */
	@Override
	public PortletPreferenceValue findByP_N_SV_Last(
			long portletPreferencesId, String name, String smallValue,
			OrderByComparator<PortletPreferenceValue> orderByComparator)
		throws NoSuchPortletPreferenceValueException {

		PortletPreferenceValue portletPreferenceValue = fetchByP_N_SV_Last(
			portletPreferencesId, name, smallValue, orderByComparator);

		if (portletPreferenceValue != null) {
			return portletPreferenceValue;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("portletPreferencesId=");
		sb.append(portletPreferencesId);

		sb.append(", name=");
		sb.append(name);

		sb.append(", smallValue=");
		sb.append(smallValue);

		sb.append("}");

		throw new NoSuchPortletPreferenceValueException(sb.toString());
	}

	/**
	 * Returns the last portlet preference value in the ordered set where portletPreferencesId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portlet preference value, or <code>null</code> if a matching portlet preference value could not be found
	 */
	@Override
	public PortletPreferenceValue fetchByP_N_SV_Last(
		long portletPreferencesId, String name, String smallValue,
		OrderByComparator<PortletPreferenceValue> orderByComparator) {

		int count = countByP_N_SV(portletPreferencesId, name, smallValue);

		if (count == 0) {
			return null;
		}

		List<PortletPreferenceValue> list = findByP_N_SV(
			portletPreferencesId, name, smallValue, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the portlet preference values before and after the current portlet preference value in the ordered set where portletPreferencesId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * @param portletPreferenceValueId the primary key of the current portlet preference value
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param smallValue the small value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next portlet preference value
	 * @throws NoSuchPortletPreferenceValueException if a portlet preference value with the primary key could not be found
	 */
	@Override
	public PortletPreferenceValue[] findByP_N_SV_PrevAndNext(
			long portletPreferenceValueId, long portletPreferencesId,
			String name, String smallValue,
			OrderByComparator<PortletPreferenceValue> orderByComparator)
		throws NoSuchPortletPreferenceValueException {

		name = Objects.toString(name, "");
		smallValue = Objects.toString(smallValue, "");

		PortletPreferenceValue portletPreferenceValue = findByPrimaryKey(
			portletPreferenceValueId);

		Session session = null;

		try {
			session = openSession();

			PortletPreferenceValue[] array = new PortletPreferenceValueImpl[3];

			array[0] = getByP_N_SV_PrevAndNext(
				session, portletPreferenceValue, portletPreferencesId, name,
				smallValue, orderByComparator, true);

			array[1] = portletPreferenceValue;

			array[2] = getByP_N_SV_PrevAndNext(
				session, portletPreferenceValue, portletPreferencesId, name,
				smallValue, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected PortletPreferenceValue getByP_N_SV_PrevAndNext(
		Session session, PortletPreferenceValue portletPreferenceValue,
		long portletPreferencesId, String name, String smallValue,
		OrderByComparator<PortletPreferenceValue> orderByComparator,
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

		sb.append(_SQL_SELECT_PORTLETPREFERENCEVALUE_WHERE);

		sb.append(_FINDER_COLUMN_P_N_SV_PORTLETPREFERENCESID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_P_N_SV_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_P_N_SV_NAME_2);
		}

		boolean bindSmallValue = false;

		if (smallValue.isEmpty()) {
			sb.append(_FINDER_COLUMN_P_N_SV_SMALLVALUE_3);
		}
		else {
			bindSmallValue = true;

			sb.append(_FINDER_COLUMN_P_N_SV_SMALLVALUE_2);
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
			sb.append(PortletPreferenceValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(portletPreferencesId);

		if (bindName) {
			queryPos.add(name);
		}

		if (bindSmallValue) {
			queryPos.add(smallValue);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						portletPreferenceValue)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<PortletPreferenceValue> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the portlet preference values where portletPreferencesId = &#63; and name = &#63; and smallValue = &#63; from the database.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param smallValue the small value
	 */
	@Override
	public void removeByP_N_SV(
		long portletPreferencesId, String name, String smallValue) {

		for (PortletPreferenceValue portletPreferenceValue :
				findByP_N_SV(
					portletPreferencesId, name, smallValue, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(portletPreferenceValue);
		}
	}

	/**
	 * Returns the number of portlet preference values where portletPreferencesId = &#63; and name = &#63; and smallValue = &#63;.
	 *
	 * @param portletPreferencesId the portlet preferences ID
	 * @param name the name
	 * @param smallValue the small value
	 * @return the number of matching portlet preference values
	 */
	@Override
	public int countByP_N_SV(
		long portletPreferencesId, String name, String smallValue) {

		name = Objects.toString(name, "");
		smallValue = Objects.toString(smallValue, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			PortletPreferenceValue.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByP_N_SV;

			finderArgs = new Object[] {portletPreferencesId, name, smallValue};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_PORTLETPREFERENCEVALUE_WHERE);

			sb.append(_FINDER_COLUMN_P_N_SV_PORTLETPREFERENCESID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_P_N_SV_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_P_N_SV_NAME_2);
			}

			boolean bindSmallValue = false;

			if (smallValue.isEmpty()) {
				sb.append(_FINDER_COLUMN_P_N_SV_SMALLVALUE_3);
			}
			else {
				bindSmallValue = true;

				sb.append(_FINDER_COLUMN_P_N_SV_SMALLVALUE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(portletPreferencesId);

				if (bindName) {
					queryPos.add(name);
				}

				if (bindSmallValue) {
					queryPos.add(smallValue);
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

	private static final String _FINDER_COLUMN_P_N_SV_PORTLETPREFERENCESID_2 =
		"portletPreferenceValue.portletPreferencesId = ? AND ";

	private static final String _FINDER_COLUMN_P_N_SV_NAME_2 =
		"portletPreferenceValue.name = ? AND ";

	private static final String _FINDER_COLUMN_P_N_SV_NAME_3 =
		"(portletPreferenceValue.name IS NULL OR portletPreferenceValue.name = '') AND ";

	private static final String _FINDER_COLUMN_P_N_SV_SMALLVALUE_2 =
		"portletPreferenceValue.smallValue = ?";

	private static final String _FINDER_COLUMN_P_N_SV_SMALLVALUE_3 =
		"(portletPreferenceValue.smallValue IS NULL OR portletPreferenceValue.smallValue = '')";

	public PortletPreferenceValuePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("index", "index_");

		setDBColumnNames(dbColumnNames);

		setModelClass(PortletPreferenceValue.class);

		setModelImplClass(PortletPreferenceValueImpl.class);
		setModelPKClass(long.class);

		setTable(PortletPreferenceValueTable.INSTANCE);
	}

	/**
	 * Caches the portlet preference value in the entity cache if it is enabled.
	 *
	 * @param portletPreferenceValue the portlet preference value
	 */
	@Override
	public void cacheResult(PortletPreferenceValue portletPreferenceValue) {
		if (portletPreferenceValue.getCtCollectionId() != 0) {
			return;
		}

		EntityCacheUtil.putResult(
			PortletPreferenceValueImpl.class,
			portletPreferenceValue.getPrimaryKey(), portletPreferenceValue);

		FinderCacheUtil.putResult(
			_finderPathFetchByP_I_N,
			new Object[] {
				portletPreferenceValue.getPortletPreferencesId(),
				portletPreferenceValue.getIndex(),
				portletPreferenceValue.getName()
			},
			portletPreferenceValue);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the portlet preference values in the entity cache if it is enabled.
	 *
	 * @param portletPreferenceValues the portlet preference values
	 */
	@Override
	public void cacheResult(
		List<PortletPreferenceValue> portletPreferenceValues) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (portletPreferenceValues.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (PortletPreferenceValue portletPreferenceValue :
				portletPreferenceValues) {

			if (portletPreferenceValue.getCtCollectionId() != 0) {
				continue;
			}

			if (EntityCacheUtil.getResult(
					PortletPreferenceValueImpl.class,
					portletPreferenceValue.getPrimaryKey()) == null) {

				cacheResult(portletPreferenceValue);
			}
		}
	}

	/**
	 * Clears the cache for all portlet preference values.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(PortletPreferenceValueImpl.class);

		FinderCacheUtil.clearCache(PortletPreferenceValueImpl.class);
	}

	/**
	 * Clears the cache for the portlet preference value.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(PortletPreferenceValue portletPreferenceValue) {
		EntityCacheUtil.removeResult(
			PortletPreferenceValueImpl.class, portletPreferenceValue);
	}

	@Override
	public void clearCache(
		List<PortletPreferenceValue> portletPreferenceValues) {

		for (PortletPreferenceValue portletPreferenceValue :
				portletPreferenceValues) {

			EntityCacheUtil.removeResult(
				PortletPreferenceValueImpl.class, portletPreferenceValue);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(PortletPreferenceValueImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				PortletPreferenceValueImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		PortletPreferenceValueModelImpl portletPreferenceValueModelImpl) {

		Object[] args = new Object[] {
			portletPreferenceValueModelImpl.getPortletPreferencesId(),
			portletPreferenceValueModelImpl.getIndex(),
			portletPreferenceValueModelImpl.getName()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByP_I_N, args, Long.valueOf(1));
		FinderCacheUtil.putResult(
			_finderPathFetchByP_I_N, args, portletPreferenceValueModelImpl);
	}

	/**
	 * Creates a new portlet preference value with the primary key. Does not add the portlet preference value to the database.
	 *
	 * @param portletPreferenceValueId the primary key for the new portlet preference value
	 * @return the new portlet preference value
	 */
	@Override
	public PortletPreferenceValue create(long portletPreferenceValueId) {
		PortletPreferenceValue portletPreferenceValue =
			new PortletPreferenceValueImpl();

		portletPreferenceValue.setNew(true);
		portletPreferenceValue.setPrimaryKey(portletPreferenceValueId);

		portletPreferenceValue.setCompanyId(CompanyThreadLocal.getCompanyId());

		return portletPreferenceValue;
	}

	/**
	 * Removes the portlet preference value with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param portletPreferenceValueId the primary key of the portlet preference value
	 * @return the portlet preference value that was removed
	 * @throws NoSuchPortletPreferenceValueException if a portlet preference value with the primary key could not be found
	 */
	@Override
	public PortletPreferenceValue remove(long portletPreferenceValueId)
		throws NoSuchPortletPreferenceValueException {

		return remove((Serializable)portletPreferenceValueId);
	}

	/**
	 * Removes the portlet preference value with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the portlet preference value
	 * @return the portlet preference value that was removed
	 * @throws NoSuchPortletPreferenceValueException if a portlet preference value with the primary key could not be found
	 */
	@Override
	public PortletPreferenceValue remove(Serializable primaryKey)
		throws NoSuchPortletPreferenceValueException {

		Session session = null;

		try {
			session = openSession();

			PortletPreferenceValue portletPreferenceValue =
				(PortletPreferenceValue)session.get(
					PortletPreferenceValueImpl.class, primaryKey);

			if (portletPreferenceValue == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPortletPreferenceValueException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(portletPreferenceValue);
		}
		catch (NoSuchPortletPreferenceValueException noSuchEntityException) {
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
	protected PortletPreferenceValue removeImpl(
		PortletPreferenceValue portletPreferenceValue) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(portletPreferenceValue)) {
				portletPreferenceValue = (PortletPreferenceValue)session.get(
					PortletPreferenceValueImpl.class,
					portletPreferenceValue.getPrimaryKeyObj());
			}

			if ((portletPreferenceValue != null) &&
				CTPersistenceHelperUtil.isRemove(portletPreferenceValue)) {

				session.delete(portletPreferenceValue);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (portletPreferenceValue != null) {
			clearCache(portletPreferenceValue);
		}

		return portletPreferenceValue;
	}

	@Override
	public PortletPreferenceValue updateImpl(
		PortletPreferenceValue portletPreferenceValue) {

		boolean isNew = portletPreferenceValue.isNew();

		if (!(portletPreferenceValue instanceof
				PortletPreferenceValueModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(portletPreferenceValue.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					portletPreferenceValue);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in portletPreferenceValue proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom PortletPreferenceValue implementation " +
					portletPreferenceValue.getClass());
		}

		PortletPreferenceValueModelImpl portletPreferenceValueModelImpl =
			(PortletPreferenceValueModelImpl)portletPreferenceValue;

		Session session = null;

		try {
			session = openSession();

			if (CTPersistenceHelperUtil.isInsert(portletPreferenceValue)) {
				if (!isNew) {
					session.evict(
						PortletPreferenceValueImpl.class,
						portletPreferenceValue.getPrimaryKeyObj());
				}

				session.save(portletPreferenceValue);
			}
			else {
				portletPreferenceValue = (PortletPreferenceValue)session.merge(
					portletPreferenceValue);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (portletPreferenceValue.getCtCollectionId() != 0) {
			if (isNew) {
				portletPreferenceValue.setNew(false);
			}

			portletPreferenceValue.resetOriginalValues();

			return portletPreferenceValue;
		}

		EntityCacheUtil.putResult(
			PortletPreferenceValueImpl.class, portletPreferenceValueModelImpl,
			false, true);

		cacheUniqueFindersCache(portletPreferenceValueModelImpl);

		if (isNew) {
			portletPreferenceValue.setNew(false);
		}

		portletPreferenceValue.resetOriginalValues();

		return portletPreferenceValue;
	}

	/**
	 * Returns the portlet preference value with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the portlet preference value
	 * @return the portlet preference value
	 * @throws NoSuchPortletPreferenceValueException if a portlet preference value with the primary key could not be found
	 */
	@Override
	public PortletPreferenceValue findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPortletPreferenceValueException {

		PortletPreferenceValue portletPreferenceValue = fetchByPrimaryKey(
			primaryKey);

		if (portletPreferenceValue == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPortletPreferenceValueException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return portletPreferenceValue;
	}

	/**
	 * Returns the portlet preference value with the primary key or throws a <code>NoSuchPortletPreferenceValueException</code> if it could not be found.
	 *
	 * @param portletPreferenceValueId the primary key of the portlet preference value
	 * @return the portlet preference value
	 * @throws NoSuchPortletPreferenceValueException if a portlet preference value with the primary key could not be found
	 */
	@Override
	public PortletPreferenceValue findByPrimaryKey(
			long portletPreferenceValueId)
		throws NoSuchPortletPreferenceValueException {

		return findByPrimaryKey((Serializable)portletPreferenceValueId);
	}

	/**
	 * Returns the portlet preference value with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the portlet preference value
	 * @return the portlet preference value, or <code>null</code> if a portlet preference value with the primary key could not be found
	 */
	@Override
	public PortletPreferenceValue fetchByPrimaryKey(Serializable primaryKey) {
		if (CTPersistenceHelperUtil.isProductionMode(
				PortletPreferenceValue.class, primaryKey)) {

			return super.fetchByPrimaryKey(primaryKey);
		}

		PortletPreferenceValue portletPreferenceValue = null;

		Session session = null;

		try {
			session = openSession();

			portletPreferenceValue = (PortletPreferenceValue)session.get(
				PortletPreferenceValueImpl.class, primaryKey);

			if (portletPreferenceValue != null) {
				cacheResult(portletPreferenceValue);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return portletPreferenceValue;
	}

	/**
	 * Returns the portlet preference value with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param portletPreferenceValueId the primary key of the portlet preference value
	 * @return the portlet preference value, or <code>null</code> if a portlet preference value with the primary key could not be found
	 */
	@Override
	public PortletPreferenceValue fetchByPrimaryKey(
		long portletPreferenceValueId) {

		return fetchByPrimaryKey((Serializable)portletPreferenceValueId);
	}

	@Override
	public Map<Serializable, PortletPreferenceValue> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (CTPersistenceHelperUtil.isProductionMode(
				PortletPreferenceValue.class)) {

			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, PortletPreferenceValue> map =
			new HashMap<Serializable, PortletPreferenceValue>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			PortletPreferenceValue portletPreferenceValue = fetchByPrimaryKey(
				primaryKey);

			if (portletPreferenceValue != null) {
				map.put(primaryKey, portletPreferenceValue);
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

			for (PortletPreferenceValue portletPreferenceValue :
					(List<PortletPreferenceValue>)query.list()) {

				map.put(
					portletPreferenceValue.getPrimaryKeyObj(),
					portletPreferenceValue);

				cacheResult(portletPreferenceValue);
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
	 * Returns all the portlet preference values.
	 *
	 * @return the portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the portlet preference values.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @return the range of portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the portlet preference values.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findAll(
		int start, int end,
		OrderByComparator<PortletPreferenceValue> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the portlet preference values.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortletPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of portlet preference values
	 * @param end the upper bound of the range of portlet preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of portlet preference values
	 */
	@Override
	public List<PortletPreferenceValue> findAll(
		int start, int end,
		OrderByComparator<PortletPreferenceValue> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			PortletPreferenceValue.class);

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

		List<PortletPreferenceValue> list = null;

		if (useFinderCache && productionMode) {
			list = (List<PortletPreferenceValue>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_PORTLETPREFERENCEVALUE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_PORTLETPREFERENCEVALUE;

				sql = sql.concat(PortletPreferenceValueModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<PortletPreferenceValue>)QueryUtil.list(
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
	 * Removes all the portlet preference values from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (PortletPreferenceValue portletPreferenceValue : findAll()) {
			remove(portletPreferenceValue);
		}
	}

	/**
	 * Returns the number of portlet preference values.
	 *
	 * @return the number of portlet preference values
	 */
	@Override
	public int countAll() {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			PortletPreferenceValue.class);

		Long count = null;

		if (productionMode) {
			count = (Long)FinderCacheUtil.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_PORTLETPREFERENCEVALUE);

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
		return "portletPreferenceValueId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_PORTLETPREFERENCEVALUE;
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
		return PortletPreferenceValueModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "PortletPreferenceValue";
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
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("portletPreferencesId");
		ctStrictColumnNames.add("index_");
		ctStrictColumnNames.add("largeValue");
		ctStrictColumnNames.add("name");
		ctStrictColumnNames.add("readOnly");
		ctStrictColumnNames.add("smallValue");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("portletPreferenceValueId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {"portletPreferencesId", "index_", "name"});
	}

	/**
	 * Initializes the portlet preference value persistence.
	 */
	public void afterPropertiesSet() {
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

		_finderPathWithPaginationFindByPortletPreferencesId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByPortletPreferencesId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"portletPreferencesId"}, true);

		_finderPathWithoutPaginationFindByPortletPreferencesId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByPortletPreferencesId", new String[] {Long.class.getName()},
			new String[] {"portletPreferencesId"}, true);

		_finderPathCountByPortletPreferencesId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByPortletPreferencesId", new String[] {Long.class.getName()},
			new String[] {"portletPreferencesId"}, false);

		_finderPathWithPaginationFindByP_N = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByP_N",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"portletPreferencesId", "name"}, true);

		_finderPathWithoutPaginationFindByP_N = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByP_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"portletPreferencesId", "name"}, true);

		_finderPathCountByP_N = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByP_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"portletPreferencesId", "name"}, false);

		_finderPathWithPaginationFindByC_N_SV = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_N_SV",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "name", "smallValue"}, true);

		_finderPathWithoutPaginationFindByC_N_SV = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_N_SV",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"companyId", "name", "smallValue"}, true);

		_finderPathCountByC_N_SV = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N_SV",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"companyId", "name", "smallValue"}, false);

		_finderPathFetchByP_I_N = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByP_I_N",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				String.class.getName()
			},
			new String[] {"portletPreferencesId", "index_", "name"}, true);

		_finderPathCountByP_I_N = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByP_I_N",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				String.class.getName()
			},
			new String[] {"portletPreferencesId", "index_", "name"}, false);

		_finderPathWithPaginationFindByP_N_SV = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByP_N_SV",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"portletPreferencesId", "name", "smallValue"}, true);

		_finderPathWithoutPaginationFindByP_N_SV = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByP_N_SV",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"portletPreferencesId", "name", "smallValue"}, true);

		_finderPathCountByP_N_SV = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByP_N_SV",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"portletPreferencesId", "name", "smallValue"}, false);

		PortletPreferenceValueUtil.setPersistence(this);
	}

	public void destroy() {
		PortletPreferenceValueUtil.setPersistence(null);

		EntityCacheUtil.removeCache(PortletPreferenceValueImpl.class.getName());
	}

	private static final String _SQL_SELECT_PORTLETPREFERENCEVALUE =
		"SELECT portletPreferenceValue FROM PortletPreferenceValue portletPreferenceValue";

	private static final String _SQL_SELECT_PORTLETPREFERENCEVALUE_WHERE =
		"SELECT portletPreferenceValue FROM PortletPreferenceValue portletPreferenceValue WHERE ";

	private static final String _SQL_COUNT_PORTLETPREFERENCEVALUE =
		"SELECT COUNT(portletPreferenceValue) FROM PortletPreferenceValue portletPreferenceValue";

	private static final String _SQL_COUNT_PORTLETPREFERENCEVALUE_WHERE =
		"SELECT COUNT(portletPreferenceValue) FROM PortletPreferenceValue portletPreferenceValue WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"portletPreferenceValue.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No PortletPreferenceValue exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No PortletPreferenceValue exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		PortletPreferenceValuePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"index"});

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

}