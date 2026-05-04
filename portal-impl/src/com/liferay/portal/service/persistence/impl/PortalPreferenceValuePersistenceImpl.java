/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchPreferenceValueException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.PortalPreferenceValue;
import com.liferay.portal.kernel.model.PortalPreferenceValueTable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.PortalPreferenceValuePersistence;
import com.liferay.portal.kernel.service.persistence.PortalPreferenceValueUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.CollectionPersistenceFinder;
import com.liferay.portal.kernel.service.persistence.impl.FinderColumn;
import com.liferay.portal.kernel.service.persistence.impl.UniquePersistenceFinder;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.impl.PortalPreferenceValueImpl;
import com.liferay.portal.model.impl.PortalPreferenceValueModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the portal preference value service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class PortalPreferenceValuePersistenceImpl
	extends BasePersistenceImpl
		<PortalPreferenceValue, NoSuchPreferenceValueException>
	implements PortalPreferenceValuePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>PortalPreferenceValueUtil</code> to access the portal preference value persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		PortalPreferenceValueImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindByPortalPreferencesId;
	private FinderPath _finderPathWithoutPaginationFindByPortalPreferencesId;
	private FinderPath _finderPathCountByPortalPreferencesId;
	private FinderPath _finderPathWithPaginationCountByPortalPreferencesId;

	/**
	 * Returns all the portal preference values where portalPreferencesId = &#63;.
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @return the matching portal preference values
	 */
	@Override
	public List<PortalPreferenceValue> findByPortalPreferencesId(
		long portalPreferencesId) {

		return findByPortalPreferencesId(
			portalPreferencesId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the portal preference values where portalPreferencesId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortalPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param start the lower bound of the range of portal preference values
	 * @param end the upper bound of the range of portal preference values (not inclusive)
	 * @return the range of matching portal preference values
	 */
	@Override
	public List<PortalPreferenceValue> findByPortalPreferencesId(
		long portalPreferencesId, int start, int end) {

		return findByPortalPreferencesId(portalPreferencesId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the portal preference values where portalPreferencesId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortalPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param start the lower bound of the range of portal preference values
	 * @param end the upper bound of the range of portal preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portal preference values
	 */
	@Override
	public List<PortalPreferenceValue> findByPortalPreferencesId(
		long portalPreferencesId, int start, int end,
		OrderByComparator<PortalPreferenceValue> orderByComparator) {

		return findByPortalPreferencesId(
			portalPreferencesId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the portal preference values where portalPreferencesId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortalPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param start the lower bound of the range of portal preference values
	 * @param end the upper bound of the range of portal preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching portal preference values
	 */
	@Override
	public List<PortalPreferenceValue> findByPortalPreferencesId(
		long portalPreferencesId, int start, int end,
		OrderByComparator<PortalPreferenceValue> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByPortalPreferencesId;
				finderArgs = new Object[] {portalPreferencesId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByPortalPreferencesId;
			finderArgs = new Object[] {
				portalPreferencesId, start, end, orderByComparator
			};
		}

		List<PortalPreferenceValue> list = null;

		if (useFinderCache) {
			list = (List<PortalPreferenceValue>)dummyFinderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (PortalPreferenceValue portalPreferenceValue : list) {
					if (portalPreferencesId !=
							portalPreferenceValue.getPortalPreferencesId()) {

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

			sb.append(_SQL_SELECT_PORTALPREFERENCEVALUE_WHERE);

			sb.append(_FINDER_COLUMN_PORTALPREFERENCESID_PORTALPREFERENCESID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
			}
			else {
				sb.append(PortalPreferenceValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(portalPreferencesId);

				list = (List<PortalPreferenceValue>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					dummyFinderCache.putResult(finderPath, finderArgs, list);
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
	 * Returns the first portal preference value in the ordered set where portalPreferencesId = &#63;.
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portal preference value
	 * @throws NoSuchPreferenceValueException if a matching portal preference value could not be found
	 */
	@Override
	public PortalPreferenceValue findByPortalPreferencesId_First(
			long portalPreferencesId,
			OrderByComparator<PortalPreferenceValue> orderByComparator)
		throws NoSuchPreferenceValueException {

		PortalPreferenceValue portalPreferenceValue =
			fetchByPortalPreferencesId_First(
				portalPreferencesId, orderByComparator);

		if (portalPreferenceValue != null) {
			return portalPreferenceValue;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("portalPreferencesId=");
		sb.append(portalPreferencesId);

		sb.append("}");

		throw new NoSuchPreferenceValueException(sb.toString());
	}

	/**
	 * Returns the first portal preference value in the ordered set where portalPreferencesId = &#63;.
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portal preference value, or <code>null</code> if a matching portal preference value could not be found
	 */
	@Override
	public PortalPreferenceValue fetchByPortalPreferencesId_First(
		long portalPreferencesId,
		OrderByComparator<PortalPreferenceValue> orderByComparator) {

		List<PortalPreferenceValue> list = findByPortalPreferencesId(
			portalPreferencesId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the portal preference values where portalPreferencesId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortalPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portalPreferencesIds the portal preferences IDs
	 * @return the matching portal preference values
	 */
	@Override
	public List<PortalPreferenceValue> findByPortalPreferencesId(
		long[] portalPreferencesIds) {

		return findByPortalPreferencesId(
			portalPreferencesIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the portal preference values where portalPreferencesId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortalPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portalPreferencesIds the portal preferences IDs
	 * @param start the lower bound of the range of portal preference values
	 * @param end the upper bound of the range of portal preference values (not inclusive)
	 * @return the range of matching portal preference values
	 */
	@Override
	public List<PortalPreferenceValue> findByPortalPreferencesId(
		long[] portalPreferencesIds, int start, int end) {

		return findByPortalPreferencesId(
			portalPreferencesIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the portal preference values where portalPreferencesId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortalPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portalPreferencesIds the portal preferences IDs
	 * @param start the lower bound of the range of portal preference values
	 * @param end the upper bound of the range of portal preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portal preference values
	 */
	@Override
	public List<PortalPreferenceValue> findByPortalPreferencesId(
		long[] portalPreferencesIds, int start, int end,
		OrderByComparator<PortalPreferenceValue> orderByComparator) {

		return findByPortalPreferencesId(
			portalPreferencesIds, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the portal preference values where portalPreferencesId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortalPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portalPreferencesIds the portal preferences IDs
	 * @param start the lower bound of the range of portal preference values
	 * @param end the upper bound of the range of portal preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching portal preference values
	 */
	@Override
	public List<PortalPreferenceValue> findByPortalPreferencesId(
		long[] portalPreferencesIds, int start, int end,
		OrderByComparator<PortalPreferenceValue> orderByComparator,
		boolean useFinderCache) {

		if (portalPreferencesIds == null) {
			portalPreferencesIds = new long[0];
		}
		else if (portalPreferencesIds.length > 1) {
			portalPreferencesIds = ArrayUtil.sortedUnique(portalPreferencesIds);
		}

		if (portalPreferencesIds.length == 1) {
			return findByPortalPreferencesId(
				portalPreferencesIds[0], start, end, orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					StringUtil.merge(portalPreferencesIds)
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				StringUtil.merge(portalPreferencesIds), start, end,
				orderByComparator
			};
		}

		List<PortalPreferenceValue> list = null;

		if (useFinderCache) {
			list = (List<PortalPreferenceValue>)dummyFinderCache.getResult(
				_finderPathWithPaginationFindByPortalPreferencesId, finderArgs,
				this);

			if ((list != null) && !list.isEmpty()) {
				for (PortalPreferenceValue portalPreferenceValue : list) {
					if (!ArrayUtil.contains(
							portalPreferencesIds,
							portalPreferenceValue.getPortalPreferencesId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_PORTALPREFERENCEVALUE_WHERE);

			if (portalPreferencesIds.length > 0) {
				sb.append("(");

				sb.append(
					_FINDER_COLUMN_PORTALPREFERENCESID_PORTALPREFERENCESID_7);

				sb.append(StringUtil.merge(portalPreferencesIds));

				sb.append(")");

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ENTITY_ALIAS_PREFIX, orderByComparator);
			}
			else {
				sb.append(PortalPreferenceValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<PortalPreferenceValue>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					dummyFinderCache.putResult(
						_finderPathWithPaginationFindByPortalPreferencesId,
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
	 * Removes all the portal preference values where portalPreferencesId = &#63; from the database.
	 *
	 * @param portalPreferencesId the portal preferences ID
	 */
	@Override
	public void removeByPortalPreferencesId(long portalPreferencesId) {
		for (PortalPreferenceValue portalPreferenceValue :
				findByPortalPreferencesId(
					portalPreferencesId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(portalPreferenceValue);
		}
	}

	/**
	 * Returns the number of portal preference values where portalPreferencesId = &#63;.
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @return the number of matching portal preference values
	 */
	@Override
	public int countByPortalPreferencesId(long portalPreferencesId) {
		FinderPath finderPath = _finderPathCountByPortalPreferencesId;

		Object[] finderArgs = new Object[] {portalPreferencesId};

		Long count = (Long)dummyFinderCache.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_PORTALPREFERENCEVALUE_WHERE);

			sb.append(_FINDER_COLUMN_PORTALPREFERENCESID_PORTALPREFERENCESID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(portalPreferencesId);

				count = (Long)query.uniqueResult();

				dummyFinderCache.putResult(finderPath, finderArgs, count);
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
	 * Returns the number of portal preference values where portalPreferencesId = any &#63;.
	 *
	 * @param portalPreferencesIds the portal preferences IDs
	 * @return the number of matching portal preference values
	 */
	@Override
	public int countByPortalPreferencesId(long[] portalPreferencesIds) {
		if (portalPreferencesIds == null) {
			portalPreferencesIds = new long[0];
		}
		else if (portalPreferencesIds.length > 1) {
			portalPreferencesIds = ArrayUtil.sortedUnique(portalPreferencesIds);
		}

		Object[] finderArgs = new Object[] {
			StringUtil.merge(portalPreferencesIds)
		};

		Long count = (Long)dummyFinderCache.getResult(
			_finderPathWithPaginationCountByPortalPreferencesId, finderArgs,
			this);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_PORTALPREFERENCEVALUE_WHERE);

			if (portalPreferencesIds.length > 0) {
				sb.append("(");

				sb.append(
					_FINDER_COLUMN_PORTALPREFERENCESID_PORTALPREFERENCESID_7);

				sb.append(StringUtil.merge(portalPreferencesIds));

				sb.append(")");

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				count = (Long)query.uniqueResult();

				dummyFinderCache.putResult(
					_finderPathWithPaginationCountByPortalPreferencesId,
					finderArgs, count);
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
		_FINDER_COLUMN_PORTALPREFERENCESID_PORTALPREFERENCESID_2 =
			"portalPreferenceValue.portalPreferencesId = ?";

	private static final String
		_FINDER_COLUMN_PORTALPREFERENCESID_PORTALPREFERENCESID_7 =
			"portalPreferenceValue.portalPreferencesId IN (";

	private FinderPath _finderPathWithPaginationFindByP_N;
	private FinderPath _finderPathWithoutPaginationFindByP_N;
	private FinderPath _finderPathCountByP_N;
	private CollectionPersistenceFinder<PortalPreferenceValue>
		_collectionPersistenceFinderByP_N;

	/**
	 * Returns all the portal preference values where portalPreferencesId = &#63; and namespace = &#63;.
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param namespace the namespace
	 * @return the matching portal preference values
	 */
	@Override
	public List<PortalPreferenceValue> findByP_N(
		long portalPreferencesId, String namespace) {

		return findByP_N(
			portalPreferencesId, namespace, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the portal preference values where portalPreferencesId = &#63; and namespace = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortalPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param namespace the namespace
	 * @param start the lower bound of the range of portal preference values
	 * @param end the upper bound of the range of portal preference values (not inclusive)
	 * @return the range of matching portal preference values
	 */
	@Override
	public List<PortalPreferenceValue> findByP_N(
		long portalPreferencesId, String namespace, int start, int end) {

		return findByP_N(portalPreferencesId, namespace, start, end, null);
	}

	/**
	 * Returns an ordered range of all the portal preference values where portalPreferencesId = &#63; and namespace = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortalPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param namespace the namespace
	 * @param start the lower bound of the range of portal preference values
	 * @param end the upper bound of the range of portal preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portal preference values
	 */
	@Override
	public List<PortalPreferenceValue> findByP_N(
		long portalPreferencesId, String namespace, int start, int end,
		OrderByComparator<PortalPreferenceValue> orderByComparator) {

		return findByP_N(
			portalPreferencesId, namespace, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the portal preference values where portalPreferencesId = &#63; and namespace = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortalPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param namespace the namespace
	 * @param start the lower bound of the range of portal preference values
	 * @param end the upper bound of the range of portal preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching portal preference values
	 */
	@Override
	public List<PortalPreferenceValue> findByP_N(
		long portalPreferencesId, String namespace, int start, int end,
		OrderByComparator<PortalPreferenceValue> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByP_N.find(
			dummyFinderCache, new Object[] {portalPreferencesId, namespace},
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first portal preference value in the ordered set where portalPreferencesId = &#63; and namespace = &#63;.
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param namespace the namespace
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portal preference value
	 * @throws NoSuchPreferenceValueException if a matching portal preference value could not be found
	 */
	@Override
	public PortalPreferenceValue findByP_N_First(
			long portalPreferencesId, String namespace,
			OrderByComparator<PortalPreferenceValue> orderByComparator)
		throws NoSuchPreferenceValueException {

		PortalPreferenceValue portalPreferenceValue = fetchByP_N_First(
			portalPreferencesId, namespace, orderByComparator);

		if (portalPreferenceValue != null) {
			return portalPreferenceValue;
		}

		throw new NoSuchPreferenceValueException(
			_collectionPersistenceFinderByP_N.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {portalPreferencesId, namespace}));
	}

	/**
	 * Returns the first portal preference value in the ordered set where portalPreferencesId = &#63; and namespace = &#63;.
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param namespace the namespace
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portal preference value, or <code>null</code> if a matching portal preference value could not be found
	 */
	@Override
	public PortalPreferenceValue fetchByP_N_First(
		long portalPreferencesId, String namespace,
		OrderByComparator<PortalPreferenceValue> orderByComparator) {

		return _collectionPersistenceFinderByP_N.fetchFirst(
			dummyFinderCache, new Object[] {portalPreferencesId, namespace},
			orderByComparator);
	}

	/**
	 * Removes all the portal preference values where portalPreferencesId = &#63; and namespace = &#63; from the database.
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param namespace the namespace
	 */
	@Override
	public void removeByP_N(long portalPreferencesId, String namespace) {
		_collectionPersistenceFinderByP_N.remove(
			dummyFinderCache, new Object[] {portalPreferencesId, namespace});
	}

	/**
	 * Returns the number of portal preference values where portalPreferencesId = &#63; and namespace = &#63;.
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param namespace the namespace
	 * @return the number of matching portal preference values
	 */
	@Override
	public int countByP_N(long portalPreferencesId, String namespace) {
		return _collectionPersistenceFinderByP_N.count(
			dummyFinderCache, new Object[] {portalPreferencesId, namespace});
	}

	private FinderPath _finderPathWithPaginationFindByP_K_N;
	private FinderPath _finderPathWithoutPaginationFindByP_K_N;
	private FinderPath _finderPathCountByP_K_N;
	private CollectionPersistenceFinder<PortalPreferenceValue>
		_collectionPersistenceFinderByP_K_N;

	/**
	 * Returns all the portal preference values where portalPreferencesId = &#63; and key = &#63; and namespace = &#63;.
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param key the key
	 * @param namespace the namespace
	 * @return the matching portal preference values
	 */
	@Override
	public List<PortalPreferenceValue> findByP_K_N(
		long portalPreferencesId, String key, String namespace) {

		return findByP_K_N(
			portalPreferencesId, key, namespace, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the portal preference values where portalPreferencesId = &#63; and key = &#63; and namespace = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortalPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param key the key
	 * @param namespace the namespace
	 * @param start the lower bound of the range of portal preference values
	 * @param end the upper bound of the range of portal preference values (not inclusive)
	 * @return the range of matching portal preference values
	 */
	@Override
	public List<PortalPreferenceValue> findByP_K_N(
		long portalPreferencesId, String key, String namespace, int start,
		int end) {

		return findByP_K_N(
			portalPreferencesId, key, namespace, start, end, null);
	}

	/**
	 * Returns an ordered range of all the portal preference values where portalPreferencesId = &#63; and key = &#63; and namespace = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortalPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param key the key
	 * @param namespace the namespace
	 * @param start the lower bound of the range of portal preference values
	 * @param end the upper bound of the range of portal preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portal preference values
	 */
	@Override
	public List<PortalPreferenceValue> findByP_K_N(
		long portalPreferencesId, String key, String namespace, int start,
		int end, OrderByComparator<PortalPreferenceValue> orderByComparator) {

		return findByP_K_N(
			portalPreferencesId, key, namespace, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the portal preference values where portalPreferencesId = &#63; and key = &#63; and namespace = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortalPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param key the key
	 * @param namespace the namespace
	 * @param start the lower bound of the range of portal preference values
	 * @param end the upper bound of the range of portal preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching portal preference values
	 */
	@Override
	public List<PortalPreferenceValue> findByP_K_N(
		long portalPreferencesId, String key, String namespace, int start,
		int end, OrderByComparator<PortalPreferenceValue> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByP_K_N.find(
			dummyFinderCache,
			new Object[] {portalPreferencesId, key, namespace}, start, end,
			orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first portal preference value in the ordered set where portalPreferencesId = &#63; and key = &#63; and namespace = &#63;.
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param key the key
	 * @param namespace the namespace
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portal preference value
	 * @throws NoSuchPreferenceValueException if a matching portal preference value could not be found
	 */
	@Override
	public PortalPreferenceValue findByP_K_N_First(
			long portalPreferencesId, String key, String namespace,
			OrderByComparator<PortalPreferenceValue> orderByComparator)
		throws NoSuchPreferenceValueException {

		PortalPreferenceValue portalPreferenceValue = fetchByP_K_N_First(
			portalPreferencesId, key, namespace, orderByComparator);

		if (portalPreferenceValue != null) {
			return portalPreferenceValue;
		}

		throw new NoSuchPreferenceValueException(
			_collectionPersistenceFinderByP_K_N.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {portalPreferencesId, key, namespace}));
	}

	/**
	 * Returns the first portal preference value in the ordered set where portalPreferencesId = &#63; and key = &#63; and namespace = &#63;.
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param key the key
	 * @param namespace the namespace
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portal preference value, or <code>null</code> if a matching portal preference value could not be found
	 */
	@Override
	public PortalPreferenceValue fetchByP_K_N_First(
		long portalPreferencesId, String key, String namespace,
		OrderByComparator<PortalPreferenceValue> orderByComparator) {

		return _collectionPersistenceFinderByP_K_N.fetchFirst(
			dummyFinderCache,
			new Object[] {portalPreferencesId, key, namespace},
			orderByComparator);
	}

	/**
	 * Removes all the portal preference values where portalPreferencesId = &#63; and key = &#63; and namespace = &#63; from the database.
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param key the key
	 * @param namespace the namespace
	 */
	@Override
	public void removeByP_K_N(
		long portalPreferencesId, String key, String namespace) {

		_collectionPersistenceFinderByP_K_N.remove(
			dummyFinderCache,
			new Object[] {portalPreferencesId, key, namespace});
	}

	/**
	 * Returns the number of portal preference values where portalPreferencesId = &#63; and key = &#63; and namespace = &#63;.
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param key the key
	 * @param namespace the namespace
	 * @return the number of matching portal preference values
	 */
	@Override
	public int countByP_K_N(
		long portalPreferencesId, String key, String namespace) {

		return _collectionPersistenceFinderByP_K_N.count(
			dummyFinderCache,
			new Object[] {portalPreferencesId, key, namespace});
	}

	private FinderPath _finderPathFetchByP_I_K_N;
	private UniquePersistenceFinder<PortalPreferenceValue>
		_uniquePersistenceFinderByP_I_K_N;

	/**
	 * Returns the portal preference value where portalPreferencesId = &#63; and index = &#63; and key = &#63; and namespace = &#63; or throws a <code>NoSuchPreferenceValueException</code> if it could not be found.
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param index the index
	 * @param key the key
	 * @param namespace the namespace
	 * @return the matching portal preference value
	 * @throws NoSuchPreferenceValueException if a matching portal preference value could not be found
	 */
	@Override
	public PortalPreferenceValue findByP_I_K_N(
			long portalPreferencesId, int index, String key, String namespace)
		throws NoSuchPreferenceValueException {

		PortalPreferenceValue portalPreferenceValue = fetchByP_I_K_N(
			portalPreferencesId, index, key, namespace);

		if (portalPreferenceValue == null) {
			String message =
				_uniquePersistenceFinderByP_I_K_N.buildNoSuchKeyMessage(
					_NO_SUCH_ENTITY_WITH_KEY,
					new Object[] {portalPreferencesId, index, key, namespace});

			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}

			throw new NoSuchPreferenceValueException(message);
		}

		return portalPreferenceValue;
	}

	/**
	 * Returns the portal preference value where portalPreferencesId = &#63; and index = &#63; and key = &#63; and namespace = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param index the index
	 * @param key the key
	 * @param namespace the namespace
	 * @return the matching portal preference value, or <code>null</code> if a matching portal preference value could not be found
	 */
	@Override
	public PortalPreferenceValue fetchByP_I_K_N(
		long portalPreferencesId, int index, String key, String namespace) {

		return fetchByP_I_K_N(portalPreferencesId, index, key, namespace, true);
	}

	/**
	 * Returns the portal preference value where portalPreferencesId = &#63; and index = &#63; and key = &#63; and namespace = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param index the index
	 * @param key the key
	 * @param namespace the namespace
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching portal preference value, or <code>null</code> if a matching portal preference value could not be found
	 */
	@Override
	public PortalPreferenceValue fetchByP_I_K_N(
		long portalPreferencesId, int index, String key, String namespace,
		boolean useFinderCache) {

		return _uniquePersistenceFinderByP_I_K_N.fetch(
			dummyFinderCache,
			new Object[] {portalPreferencesId, index, key, namespace},
			useFinderCache);
	}

	/**
	 * Removes the portal preference value where portalPreferencesId = &#63; and index = &#63; and key = &#63; and namespace = &#63; from the database.
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param index the index
	 * @param key the key
	 * @param namespace the namespace
	 * @return the portal preference value that was removed
	 */
	@Override
	public PortalPreferenceValue removeByP_I_K_N(
			long portalPreferencesId, int index, String key, String namespace)
		throws NoSuchPreferenceValueException {

		PortalPreferenceValue portalPreferenceValue = findByP_I_K_N(
			portalPreferencesId, index, key, namespace);

		return remove(portalPreferenceValue);
	}

	/**
	 * Returns the number of portal preference values where portalPreferencesId = &#63; and index = &#63; and key = &#63; and namespace = &#63;.
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param index the index
	 * @param key the key
	 * @param namespace the namespace
	 * @return the number of matching portal preference values
	 */
	@Override
	public int countByP_I_K_N(
		long portalPreferencesId, int index, String key, String namespace) {

		return _uniquePersistenceFinderByP_I_K_N.count(
			dummyFinderCache,
			new Object[] {portalPreferencesId, index, key, namespace});
	}

	private FinderPath _finderPathWithPaginationFindByP_K_N_SV;
	private FinderPath _finderPathWithoutPaginationFindByP_K_N_SV;
	private FinderPath _finderPathCountByP_K_N_SV;
	private CollectionPersistenceFinder<PortalPreferenceValue>
		_collectionPersistenceFinderByP_K_N_SV;

	/**
	 * Returns all the portal preference values where portalPreferencesId = &#63; and key = &#63; and namespace = &#63; and smallValue = &#63;.
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param key the key
	 * @param namespace the namespace
	 * @param smallValue the small value
	 * @return the matching portal preference values
	 */
	@Override
	public List<PortalPreferenceValue> findByP_K_N_SV(
		long portalPreferencesId, String key, String namespace,
		String smallValue) {

		return findByP_K_N_SV(
			portalPreferencesId, key, namespace, smallValue, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the portal preference values where portalPreferencesId = &#63; and key = &#63; and namespace = &#63; and smallValue = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortalPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param key the key
	 * @param namespace the namespace
	 * @param smallValue the small value
	 * @param start the lower bound of the range of portal preference values
	 * @param end the upper bound of the range of portal preference values (not inclusive)
	 * @return the range of matching portal preference values
	 */
	@Override
	public List<PortalPreferenceValue> findByP_K_N_SV(
		long portalPreferencesId, String key, String namespace,
		String smallValue, int start, int end) {

		return findByP_K_N_SV(
			portalPreferencesId, key, namespace, smallValue, start, end, null);
	}

	/**
	 * Returns an ordered range of all the portal preference values where portalPreferencesId = &#63; and key = &#63; and namespace = &#63; and smallValue = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortalPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param key the key
	 * @param namespace the namespace
	 * @param smallValue the small value
	 * @param start the lower bound of the range of portal preference values
	 * @param end the upper bound of the range of portal preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portal preference values
	 */
	@Override
	public List<PortalPreferenceValue> findByP_K_N_SV(
		long portalPreferencesId, String key, String namespace,
		String smallValue, int start, int end,
		OrderByComparator<PortalPreferenceValue> orderByComparator) {

		return findByP_K_N_SV(
			portalPreferencesId, key, namespace, smallValue, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the portal preference values where portalPreferencesId = &#63; and key = &#63; and namespace = &#63; and smallValue = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PortalPreferenceValueModelImpl</code>.
	 * </p>
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param key the key
	 * @param namespace the namespace
	 * @param smallValue the small value
	 * @param start the lower bound of the range of portal preference values
	 * @param end the upper bound of the range of portal preference values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching portal preference values
	 */
	@Override
	public List<PortalPreferenceValue> findByP_K_N_SV(
		long portalPreferencesId, String key, String namespace,
		String smallValue, int start, int end,
		OrderByComparator<PortalPreferenceValue> orderByComparator,
		boolean useFinderCache) {

		return _collectionPersistenceFinderByP_K_N_SV.find(
			dummyFinderCache,
			new Object[] {portalPreferencesId, key, namespace, smallValue},
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first portal preference value in the ordered set where portalPreferencesId = &#63; and key = &#63; and namespace = &#63; and smallValue = &#63;.
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param key the key
	 * @param namespace the namespace
	 * @param smallValue the small value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portal preference value
	 * @throws NoSuchPreferenceValueException if a matching portal preference value could not be found
	 */
	@Override
	public PortalPreferenceValue findByP_K_N_SV_First(
			long portalPreferencesId, String key, String namespace,
			String smallValue,
			OrderByComparator<PortalPreferenceValue> orderByComparator)
		throws NoSuchPreferenceValueException {

		PortalPreferenceValue portalPreferenceValue = fetchByP_K_N_SV_First(
			portalPreferencesId, key, namespace, smallValue, orderByComparator);

		if (portalPreferenceValue != null) {
			return portalPreferenceValue;
		}

		throw new NoSuchPreferenceValueException(
			_collectionPersistenceFinderByP_K_N_SV.buildNoSuchKeyMessage(
				_NO_SUCH_ENTITY_WITH_KEY,
				new Object[] {
					portalPreferencesId, key, namespace, smallValue
				}));
	}

	/**
	 * Returns the first portal preference value in the ordered set where portalPreferencesId = &#63; and key = &#63; and namespace = &#63; and smallValue = &#63;.
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param key the key
	 * @param namespace the namespace
	 * @param smallValue the small value
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portal preference value, or <code>null</code> if a matching portal preference value could not be found
	 */
	@Override
	public PortalPreferenceValue fetchByP_K_N_SV_First(
		long portalPreferencesId, String key, String namespace,
		String smallValue,
		OrderByComparator<PortalPreferenceValue> orderByComparator) {

		return _collectionPersistenceFinderByP_K_N_SV.fetchFirst(
			dummyFinderCache,
			new Object[] {portalPreferencesId, key, namespace, smallValue},
			orderByComparator);
	}

	/**
	 * Removes all the portal preference values where portalPreferencesId = &#63; and key = &#63; and namespace = &#63; and smallValue = &#63; from the database.
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param key the key
	 * @param namespace the namespace
	 * @param smallValue the small value
	 */
	@Override
	public void removeByP_K_N_SV(
		long portalPreferencesId, String key, String namespace,
		String smallValue) {

		_collectionPersistenceFinderByP_K_N_SV.remove(
			dummyFinderCache,
			new Object[] {portalPreferencesId, key, namespace, smallValue});
	}

	/**
	 * Returns the number of portal preference values where portalPreferencesId = &#63; and key = &#63; and namespace = &#63; and smallValue = &#63;.
	 *
	 * @param portalPreferencesId the portal preferences ID
	 * @param key the key
	 * @param namespace the namespace
	 * @param smallValue the small value
	 * @return the number of matching portal preference values
	 */
	@Override
	public int countByP_K_N_SV(
		long portalPreferencesId, String key, String namespace,
		String smallValue) {

		return _collectionPersistenceFinderByP_K_N_SV.count(
			dummyFinderCache,
			new Object[] {portalPreferencesId, key, namespace, smallValue});
	}

	public PortalPreferenceValuePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("index", "index_");
		dbColumnNames.put("key", "key_");

		setDBColumnNames(dbColumnNames);

		setModelClass(PortalPreferenceValue.class);

		setModelImplClass(PortalPreferenceValueImpl.class);
		setModelPKClass(long.class);

		setTable(PortalPreferenceValueTable.INSTANCE);
	}

	/**
	 * Creates a new portal preference value with the primary key. Does not add the portal preference value to the database.
	 *
	 * @param portalPreferenceValueId the primary key for the new portal preference value
	 * @return the new portal preference value
	 */
	@Override
	public PortalPreferenceValue create(long portalPreferenceValueId) {
		PortalPreferenceValue portalPreferenceValue =
			new PortalPreferenceValueImpl();

		portalPreferenceValue.setNew(true);
		portalPreferenceValue.setPrimaryKey(portalPreferenceValueId);

		portalPreferenceValue.setCompanyId(CompanyThreadLocal.getCompanyId());

		return portalPreferenceValue;
	}

	/**
	 * Removes the portal preference value with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param portalPreferenceValueId the primary key of the portal preference value
	 * @return the portal preference value that was removed
	 * @throws NoSuchPreferenceValueException if a portal preference value with the primary key could not be found
	 */
	@Override
	public PortalPreferenceValue remove(long portalPreferenceValueId)
		throws NoSuchPreferenceValueException {

		return remove((Serializable)portalPreferenceValueId);
	}

	@Override
	protected PortalPreferenceValue removeImpl(
		PortalPreferenceValue portalPreferenceValue) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(portalPreferenceValue)) {
				portalPreferenceValue = (PortalPreferenceValue)session.get(
					PortalPreferenceValueImpl.class,
					portalPreferenceValue.getPrimaryKeyObj());
			}

			if (portalPreferenceValue != null) {
				session.delete(portalPreferenceValue);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (portalPreferenceValue != null) {
			clearCache(portalPreferenceValue);
		}

		return portalPreferenceValue;
	}

	@Override
	public PortalPreferenceValue updateImpl(
		PortalPreferenceValue portalPreferenceValue) {

		boolean isNew = portalPreferenceValue.isNew();

		if (!(portalPreferenceValue instanceof
				PortalPreferenceValueModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(portalPreferenceValue.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					portalPreferenceValue);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in portalPreferenceValue proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom PortalPreferenceValue implementation " +
					portalPreferenceValue.getClass());
		}

		PortalPreferenceValueModelImpl portalPreferenceValueModelImpl =
			(PortalPreferenceValueModelImpl)portalPreferenceValue;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(portalPreferenceValue);
			}
			else {
				portalPreferenceValue = (PortalPreferenceValue)session.merge(
					portalPreferenceValue);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		cacheUniqueFindersResult(portalPreferenceValue, false);

		if (isNew) {
			portalPreferenceValue.setNew(false);
		}

		portalPreferenceValue.resetOriginalValues();

		return portalPreferenceValue;
	}

	/**
	 * Returns the portal preference value with the primary key or throws a <code>NoSuchPreferenceValueException</code> if it could not be found.
	 *
	 * @param portalPreferenceValueId the primary key of the portal preference value
	 * @return the portal preference value
	 * @throws NoSuchPreferenceValueException if a portal preference value with the primary key could not be found
	 */
	@Override
	public PortalPreferenceValue findByPrimaryKey(long portalPreferenceValueId)
		throws NoSuchPreferenceValueException {

		return findByPrimaryKey((Serializable)portalPreferenceValueId);
	}

	/**
	 * Returns the portal preference value with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param portalPreferenceValueId the primary key of the portal preference value
	 * @return the portal preference value, or <code>null</code> if a portal preference value with the primary key could not be found
	 */
	@Override
	public PortalPreferenceValue fetchByPrimaryKey(
		long portalPreferenceValueId) {

		return fetchByPrimaryKey((Serializable)portalPreferenceValueId);
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return dummyEntityCache;
	}

	@Override
	protected String getPKDBName() {
		return "portalPreferenceValueId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_PORTALPREFERENCEVALUE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return PortalPreferenceValueModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the portal preference value persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindByPortalPreferencesId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPortalPreferencesId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"portalPreferencesId"}, true);

		_finderPathWithoutPaginationFindByPortalPreferencesId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByPortalPreferencesId", new String[] {Long.class.getName()},
			new String[] {"portalPreferencesId"}, true);

		_finderPathCountByPortalPreferencesId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByPortalPreferencesId", new String[] {Long.class.getName()},
			new String[] {"portalPreferencesId"}, false);

		_finderPathWithPaginationCountByPortalPreferencesId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByPortalPreferencesId", new String[] {Long.class.getName()},
			new String[] {"portalPreferencesId"}, false);

		_finderPathWithPaginationFindByP_N = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByP_N",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"portalPreferencesId", "namespace"}, true);

		_finderPathWithoutPaginationFindByP_N = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByP_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"portalPreferencesId", "namespace"}, true);

		_finderPathCountByP_N = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByP_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"portalPreferencesId", "namespace"}, false);

		_collectionPersistenceFinderByP_N = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByP_N,
			_finderPathWithoutPaginationFindByP_N, _finderPathCountByP_N,
			_SQL_SELECT_PORTALPREFERENCEVALUE_WHERE,
			_SQL_COUNT_PORTALPREFERENCEVALUE_WHERE,
			PortalPreferenceValueModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
			new FinderColumn<>(
				"portalPreferenceValue.", "portalPreferencesId",
				FinderColumn.Type.LONG, "=", true, false,
				PortalPreferenceValue::getPortalPreferencesId),
			new FinderColumn<>(
				"portalPreferenceValue.", "namespace", FinderColumn.Type.STRING,
				"=", true, true, PortalPreferenceValue::getNamespace));

		_finderPathWithPaginationFindByP_K_N = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByP_K_N",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"portalPreferencesId", "key_", "namespace"}, true);

		_finderPathWithoutPaginationFindByP_K_N = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByP_K_N",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"portalPreferencesId", "key_", "namespace"}, true);

		_finderPathCountByP_K_N = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByP_K_N",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"portalPreferencesId", "key_", "namespace"}, false);

		_collectionPersistenceFinderByP_K_N = new CollectionPersistenceFinder<>(
			this, _finderPathWithPaginationFindByP_K_N,
			_finderPathWithoutPaginationFindByP_K_N, _finderPathCountByP_K_N,
			_SQL_SELECT_PORTALPREFERENCEVALUE_WHERE,
			_SQL_COUNT_PORTALPREFERENCEVALUE_WHERE,
			PortalPreferenceValueModelImpl.ORDER_BY_JPQL, _ENTITY_ALIAS_PREFIX,
			new FinderColumn<>(
				"portalPreferenceValue.", "portalPreferencesId",
				FinderColumn.Type.LONG, "=", true, false,
				PortalPreferenceValue::getPortalPreferencesId),
			new FinderColumn<>(
				"portalPreferenceValue.", "key", FinderColumn.Type.STRING, "=",
				true, false, PortalPreferenceValue::getKey),
			new FinderColumn<>(
				"portalPreferenceValue.", "namespace", FinderColumn.Type.STRING,
				"=", true, true, PortalPreferenceValue::getNamespace));

		_finderPathFetchByP_I_K_N = createUniqueFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByP_I_K_N",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				String.class.getName(), String.class.getName()
			},
			new String[] {"portalPreferencesId", "index_", "key_", "namespace"},
			false, PortalPreferenceValue::getPortalPreferencesId,
			PortalPreferenceValue::getIndex, PortalPreferenceValue::getKey,
			PortalPreferenceValue::getNamespace);

		_uniquePersistenceFinderByP_I_K_N = new UniquePersistenceFinder<>(
			this, _finderPathFetchByP_I_K_N,
			_SQL_SELECT_PORTALPREFERENCEVALUE_WHERE,
			new FinderColumn<>(
				"portalPreferenceValue.", "portalPreferencesId",
				FinderColumn.Type.LONG, "=", true, false,
				PortalPreferenceValue::getPortalPreferencesId),
			new FinderColumn<>(
				"portalPreferenceValue.", "index", FinderColumn.Type.INTEGER,
				"=", true, false, PortalPreferenceValue::getIndex),
			new FinderColumn<>(
				"portalPreferenceValue.", "key", FinderColumn.Type.STRING, "=",
				true, false, PortalPreferenceValue::getKey),
			new FinderColumn<>(
				"portalPreferenceValue.", "namespace", FinderColumn.Type.STRING,
				"=", true, true, PortalPreferenceValue::getNamespace));

		_finderPathWithPaginationFindByP_K_N_SV = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByP_K_N_SV",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {
				"portalPreferencesId", "key_", "namespace", "smallValue"
			},
			true);

		_finderPathWithoutPaginationFindByP_K_N_SV = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByP_K_N_SV",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), String.class.getName()
			},
			new String[] {
				"portalPreferencesId", "key_", "namespace", "smallValue"
			},
			true);

		_finderPathCountByP_K_N_SV = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByP_K_N_SV",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), String.class.getName()
			},
			new String[] {
				"portalPreferencesId", "key_", "namespace", "smallValue"
			},
			false);

		_collectionPersistenceFinderByP_K_N_SV =
			new CollectionPersistenceFinder<>(
				this, _finderPathWithPaginationFindByP_K_N_SV,
				_finderPathWithoutPaginationFindByP_K_N_SV,
				_finderPathCountByP_K_N_SV,
				_SQL_SELECT_PORTALPREFERENCEVALUE_WHERE,
				_SQL_COUNT_PORTALPREFERENCEVALUE_WHERE,
				PortalPreferenceValueModelImpl.ORDER_BY_JPQL,
				_ENTITY_ALIAS_PREFIX,
				new FinderColumn<>(
					"portalPreferenceValue.", "portalPreferencesId",
					FinderColumn.Type.LONG, "=", true, false,
					PortalPreferenceValue::getPortalPreferencesId),
				new FinderColumn<>(
					"portalPreferenceValue.", "key", FinderColumn.Type.STRING,
					"=", true, false, PortalPreferenceValue::getKey),
				new FinderColumn<>(
					"portalPreferenceValue.", "namespace",
					FinderColumn.Type.STRING, "=", true, false,
					PortalPreferenceValue::getNamespace),
				new FinderColumn<>(
					"portalPreferenceValue.", "smallValue",
					FinderColumn.Type.STRING, "=", true, true,
					PortalPreferenceValue::getSmallValue));

		PortalPreferenceValueUtil.setPersistence(this);
	}

	public void destroy() {
		PortalPreferenceValueUtil.setPersistence(null);

		dummyEntityCache.removeCache(PortalPreferenceValueImpl.class.getName());
	}

	private static final String _ENTITY_ALIAS_PREFIX =
		PortalPreferenceValueModelImpl.ENTITY_ALIAS + ".";

	private static final String _SQL_SELECT_PORTALPREFERENCEVALUE =
		"SELECT portalPreferenceValue FROM PortalPreferenceValue portalPreferenceValue";

	private static final String _SQL_SELECT_PORTALPREFERENCEVALUE_WHERE =
		"SELECT portalPreferenceValue FROM PortalPreferenceValue portalPreferenceValue WHERE ";

	private static final String _SQL_COUNT_PORTALPREFERENCEVALUE_WHERE =
		"SELECT COUNT(portalPreferenceValue) FROM PortalPreferenceValue portalPreferenceValue WHERE ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No PortalPreferenceValue exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		PortalPreferenceValuePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"index", "key"});

	@Override
	protected FinderCache getFinderCache() {
		return dummyFinderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:524497222