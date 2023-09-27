/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.qualifier.service.persistence.impl;

import com.liferay.commerce.qualifier.exception.NoSuchCommerceQualifierEntryException;
import com.liferay.commerce.qualifier.model.CommerceQualifierEntry;
import com.liferay.commerce.qualifier.model.CommerceQualifierEntryTable;
import com.liferay.commerce.qualifier.model.impl.CommerceQualifierEntryImpl;
import com.liferay.commerce.qualifier.model.impl.CommerceQualifierEntryModelImpl;
import com.liferay.commerce.qualifier.service.persistence.CommerceQualifierEntryPersistence;
import com.liferay.commerce.qualifier.service.persistence.CommerceQualifierEntryUtil;
import com.liferay.commerce.qualifier.service.persistence.impl.constants.CommercePersistenceConstants;
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
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the commerce qualifier entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Riccardo Alberti
 * @generated
 */
@Component(service = CommerceQualifierEntryPersistence.class)
public class CommerceQualifierEntryPersistenceImpl
	extends BasePersistenceImpl<CommerceQualifierEntry>
	implements CommerceQualifierEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceQualifierEntryUtil</code> to access the commerce qualifier entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceQualifierEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByS_S;
	private FinderPath _finderPathWithoutPaginationFindByS_S;
	private FinderPath _finderPathCountByS_S;

	/**
	 * Returns all the commerce qualifier entries where sourceClassNameId = &#63; and sourceClassPK = &#63;.
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param sourceClassPK the source class pk
	 * @return the matching commerce qualifier entries
	 */
	@Override
	public List<CommerceQualifierEntry> findByS_S(
		long sourceClassNameId, long sourceClassPK) {

		return findByS_S(
			sourceClassNameId, sourceClassPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce qualifier entries where sourceClassNameId = &#63; and sourceClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceQualifierEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param sourceClassPK the source class pk
	 * @param start the lower bound of the range of commerce qualifier entries
	 * @param end the upper bound of the range of commerce qualifier entries (not inclusive)
	 * @return the range of matching commerce qualifier entries
	 */
	@Override
	public List<CommerceQualifierEntry> findByS_S(
		long sourceClassNameId, long sourceClassPK, int start, int end) {

		return findByS_S(sourceClassNameId, sourceClassPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce qualifier entries where sourceClassNameId = &#63; and sourceClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceQualifierEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param sourceClassPK the source class pk
	 * @param start the lower bound of the range of commerce qualifier entries
	 * @param end the upper bound of the range of commerce qualifier entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce qualifier entries
	 */
	@Override
	public List<CommerceQualifierEntry> findByS_S(
		long sourceClassNameId, long sourceClassPK, int start, int end,
		OrderByComparator<CommerceQualifierEntry> orderByComparator) {

		return findByS_S(
			sourceClassNameId, sourceClassPK, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the commerce qualifier entries where sourceClassNameId = &#63; and sourceClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceQualifierEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param sourceClassPK the source class pk
	 * @param start the lower bound of the range of commerce qualifier entries
	 * @param end the upper bound of the range of commerce qualifier entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce qualifier entries
	 */
	@Override
	public List<CommerceQualifierEntry> findByS_S(
		long sourceClassNameId, long sourceClassPK, int start, int end,
		OrderByComparator<CommerceQualifierEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByS_S;
				finderArgs = new Object[] {sourceClassNameId, sourceClassPK};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByS_S;
			finderArgs = new Object[] {
				sourceClassNameId, sourceClassPK, start, end, orderByComparator
			};
		}

		List<CommerceQualifierEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceQualifierEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceQualifierEntry commerceQualifierEntry : list) {
					if ((sourceClassNameId !=
							commerceQualifierEntry.getSourceClassNameId()) ||
						(sourceClassPK !=
							commerceQualifierEntry.getSourceClassPK())) {

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

			sb.append(_SQL_SELECT_COMMERCEQUALIFIERENTRY_WHERE);

			sb.append(_FINDER_COLUMN_S_S_SOURCECLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_S_S_SOURCECLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceQualifierEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(sourceClassNameId);

				queryPos.add(sourceClassPK);

				list = (List<CommerceQualifierEntry>)QueryUtil.list(
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
	 * Returns the first commerce qualifier entry in the ordered set where sourceClassNameId = &#63; and sourceClassPK = &#63;.
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param sourceClassPK the source class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce qualifier entry
	 * @throws NoSuchCommerceQualifierEntryException if a matching commerce qualifier entry could not be found
	 */
	@Override
	public CommerceQualifierEntry findByS_S_First(
			long sourceClassNameId, long sourceClassPK,
			OrderByComparator<CommerceQualifierEntry> orderByComparator)
		throws NoSuchCommerceQualifierEntryException {

		CommerceQualifierEntry commerceQualifierEntry = fetchByS_S_First(
			sourceClassNameId, sourceClassPK, orderByComparator);

		if (commerceQualifierEntry != null) {
			return commerceQualifierEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("sourceClassNameId=");
		sb.append(sourceClassNameId);

		sb.append(", sourceClassPK=");
		sb.append(sourceClassPK);

		sb.append("}");

		throw new NoSuchCommerceQualifierEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce qualifier entry in the ordered set where sourceClassNameId = &#63; and sourceClassPK = &#63;.
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param sourceClassPK the source class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce qualifier entry, or <code>null</code> if a matching commerce qualifier entry could not be found
	 */
	@Override
	public CommerceQualifierEntry fetchByS_S_First(
		long sourceClassNameId, long sourceClassPK,
		OrderByComparator<CommerceQualifierEntry> orderByComparator) {

		List<CommerceQualifierEntry> list = findByS_S(
			sourceClassNameId, sourceClassPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce qualifier entry in the ordered set where sourceClassNameId = &#63; and sourceClassPK = &#63;.
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param sourceClassPK the source class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce qualifier entry
	 * @throws NoSuchCommerceQualifierEntryException if a matching commerce qualifier entry could not be found
	 */
	@Override
	public CommerceQualifierEntry findByS_S_Last(
			long sourceClassNameId, long sourceClassPK,
			OrderByComparator<CommerceQualifierEntry> orderByComparator)
		throws NoSuchCommerceQualifierEntryException {

		CommerceQualifierEntry commerceQualifierEntry = fetchByS_S_Last(
			sourceClassNameId, sourceClassPK, orderByComparator);

		if (commerceQualifierEntry != null) {
			return commerceQualifierEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("sourceClassNameId=");
		sb.append(sourceClassNameId);

		sb.append(", sourceClassPK=");
		sb.append(sourceClassPK);

		sb.append("}");

		throw new NoSuchCommerceQualifierEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce qualifier entry in the ordered set where sourceClassNameId = &#63; and sourceClassPK = &#63;.
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param sourceClassPK the source class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce qualifier entry, or <code>null</code> if a matching commerce qualifier entry could not be found
	 */
	@Override
	public CommerceQualifierEntry fetchByS_S_Last(
		long sourceClassNameId, long sourceClassPK,
		OrderByComparator<CommerceQualifierEntry> orderByComparator) {

		int count = countByS_S(sourceClassNameId, sourceClassPK);

		if (count == 0) {
			return null;
		}

		List<CommerceQualifierEntry> list = findByS_S(
			sourceClassNameId, sourceClassPK, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce qualifier entries before and after the current commerce qualifier entry in the ordered set where sourceClassNameId = &#63; and sourceClassPK = &#63;.
	 *
	 * @param commerceQualifierEntryId the primary key of the current commerce qualifier entry
	 * @param sourceClassNameId the source class name ID
	 * @param sourceClassPK the source class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce qualifier entry
	 * @throws NoSuchCommerceQualifierEntryException if a commerce qualifier entry with the primary key could not be found
	 */
	@Override
	public CommerceQualifierEntry[] findByS_S_PrevAndNext(
			long commerceQualifierEntryId, long sourceClassNameId,
			long sourceClassPK,
			OrderByComparator<CommerceQualifierEntry> orderByComparator)
		throws NoSuchCommerceQualifierEntryException {

		CommerceQualifierEntry commerceQualifierEntry = findByPrimaryKey(
			commerceQualifierEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceQualifierEntry[] array = new CommerceQualifierEntryImpl[3];

			array[0] = getByS_S_PrevAndNext(
				session, commerceQualifierEntry, sourceClassNameId,
				sourceClassPK, orderByComparator, true);

			array[1] = commerceQualifierEntry;

			array[2] = getByS_S_PrevAndNext(
				session, commerceQualifierEntry, sourceClassNameId,
				sourceClassPK, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceQualifierEntry getByS_S_PrevAndNext(
		Session session, CommerceQualifierEntry commerceQualifierEntry,
		long sourceClassNameId, long sourceClassPK,
		OrderByComparator<CommerceQualifierEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEQUALIFIERENTRY_WHERE);

		sb.append(_FINDER_COLUMN_S_S_SOURCECLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_S_S_SOURCECLASSPK_2);

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
			sb.append(CommerceQualifierEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(sourceClassNameId);

		queryPos.add(sourceClassPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceQualifierEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceQualifierEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce qualifier entries where sourceClassNameId = &#63; and sourceClassPK = &#63; from the database.
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param sourceClassPK the source class pk
	 */
	@Override
	public void removeByS_S(long sourceClassNameId, long sourceClassPK) {
		for (CommerceQualifierEntry commerceQualifierEntry :
				findByS_S(
					sourceClassNameId, sourceClassPK, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceQualifierEntry);
		}
	}

	/**
	 * Returns the number of commerce qualifier entries where sourceClassNameId = &#63; and sourceClassPK = &#63;.
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param sourceClassPK the source class pk
	 * @return the number of matching commerce qualifier entries
	 */
	@Override
	public int countByS_S(long sourceClassNameId, long sourceClassPK) {
		FinderPath finderPath = _finderPathCountByS_S;

		Object[] finderArgs = new Object[] {sourceClassNameId, sourceClassPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEQUALIFIERENTRY_WHERE);

			sb.append(_FINDER_COLUMN_S_S_SOURCECLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_S_S_SOURCECLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(sourceClassNameId);

				queryPos.add(sourceClassPK);

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

	private static final String _FINDER_COLUMN_S_S_SOURCECLASSNAMEID_2 =
		"commerceQualifierEntry.sourceClassNameId = ? AND ";

	private static final String _FINDER_COLUMN_S_S_SOURCECLASSPK_2 =
		"commerceQualifierEntry.sourceClassPK = ?";

	private FinderPath _finderPathWithPaginationFindByT_T;
	private FinderPath _finderPathWithoutPaginationFindByT_T;
	private FinderPath _finderPathCountByT_T;

	/**
	 * Returns all the commerce qualifier entries where targetClassNameId = &#63; and targetClassPK = &#63;.
	 *
	 * @param targetClassNameId the target class name ID
	 * @param targetClassPK the target class pk
	 * @return the matching commerce qualifier entries
	 */
	@Override
	public List<CommerceQualifierEntry> findByT_T(
		long targetClassNameId, long targetClassPK) {

		return findByT_T(
			targetClassNameId, targetClassPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce qualifier entries where targetClassNameId = &#63; and targetClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceQualifierEntryModelImpl</code>.
	 * </p>
	 *
	 * @param targetClassNameId the target class name ID
	 * @param targetClassPK the target class pk
	 * @param start the lower bound of the range of commerce qualifier entries
	 * @param end the upper bound of the range of commerce qualifier entries (not inclusive)
	 * @return the range of matching commerce qualifier entries
	 */
	@Override
	public List<CommerceQualifierEntry> findByT_T(
		long targetClassNameId, long targetClassPK, int start, int end) {

		return findByT_T(targetClassNameId, targetClassPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce qualifier entries where targetClassNameId = &#63; and targetClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceQualifierEntryModelImpl</code>.
	 * </p>
	 *
	 * @param targetClassNameId the target class name ID
	 * @param targetClassPK the target class pk
	 * @param start the lower bound of the range of commerce qualifier entries
	 * @param end the upper bound of the range of commerce qualifier entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce qualifier entries
	 */
	@Override
	public List<CommerceQualifierEntry> findByT_T(
		long targetClassNameId, long targetClassPK, int start, int end,
		OrderByComparator<CommerceQualifierEntry> orderByComparator) {

		return findByT_T(
			targetClassNameId, targetClassPK, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the commerce qualifier entries where targetClassNameId = &#63; and targetClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceQualifierEntryModelImpl</code>.
	 * </p>
	 *
	 * @param targetClassNameId the target class name ID
	 * @param targetClassPK the target class pk
	 * @param start the lower bound of the range of commerce qualifier entries
	 * @param end the upper bound of the range of commerce qualifier entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce qualifier entries
	 */
	@Override
	public List<CommerceQualifierEntry> findByT_T(
		long targetClassNameId, long targetClassPK, int start, int end,
		OrderByComparator<CommerceQualifierEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByT_T;
				finderArgs = new Object[] {targetClassNameId, targetClassPK};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByT_T;
			finderArgs = new Object[] {
				targetClassNameId, targetClassPK, start, end, orderByComparator
			};
		}

		List<CommerceQualifierEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceQualifierEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceQualifierEntry commerceQualifierEntry : list) {
					if ((targetClassNameId !=
							commerceQualifierEntry.getTargetClassNameId()) ||
						(targetClassPK !=
							commerceQualifierEntry.getTargetClassPK())) {

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

			sb.append(_SQL_SELECT_COMMERCEQUALIFIERENTRY_WHERE);

			sb.append(_FINDER_COLUMN_T_T_TARGETCLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_T_T_TARGETCLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceQualifierEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(targetClassNameId);

				queryPos.add(targetClassPK);

				list = (List<CommerceQualifierEntry>)QueryUtil.list(
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
	 * Returns the first commerce qualifier entry in the ordered set where targetClassNameId = &#63; and targetClassPK = &#63;.
	 *
	 * @param targetClassNameId the target class name ID
	 * @param targetClassPK the target class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce qualifier entry
	 * @throws NoSuchCommerceQualifierEntryException if a matching commerce qualifier entry could not be found
	 */
	@Override
	public CommerceQualifierEntry findByT_T_First(
			long targetClassNameId, long targetClassPK,
			OrderByComparator<CommerceQualifierEntry> orderByComparator)
		throws NoSuchCommerceQualifierEntryException {

		CommerceQualifierEntry commerceQualifierEntry = fetchByT_T_First(
			targetClassNameId, targetClassPK, orderByComparator);

		if (commerceQualifierEntry != null) {
			return commerceQualifierEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("targetClassNameId=");
		sb.append(targetClassNameId);

		sb.append(", targetClassPK=");
		sb.append(targetClassPK);

		sb.append("}");

		throw new NoSuchCommerceQualifierEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce qualifier entry in the ordered set where targetClassNameId = &#63; and targetClassPK = &#63;.
	 *
	 * @param targetClassNameId the target class name ID
	 * @param targetClassPK the target class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce qualifier entry, or <code>null</code> if a matching commerce qualifier entry could not be found
	 */
	@Override
	public CommerceQualifierEntry fetchByT_T_First(
		long targetClassNameId, long targetClassPK,
		OrderByComparator<CommerceQualifierEntry> orderByComparator) {

		List<CommerceQualifierEntry> list = findByT_T(
			targetClassNameId, targetClassPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce qualifier entry in the ordered set where targetClassNameId = &#63; and targetClassPK = &#63;.
	 *
	 * @param targetClassNameId the target class name ID
	 * @param targetClassPK the target class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce qualifier entry
	 * @throws NoSuchCommerceQualifierEntryException if a matching commerce qualifier entry could not be found
	 */
	@Override
	public CommerceQualifierEntry findByT_T_Last(
			long targetClassNameId, long targetClassPK,
			OrderByComparator<CommerceQualifierEntry> orderByComparator)
		throws NoSuchCommerceQualifierEntryException {

		CommerceQualifierEntry commerceQualifierEntry = fetchByT_T_Last(
			targetClassNameId, targetClassPK, orderByComparator);

		if (commerceQualifierEntry != null) {
			return commerceQualifierEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("targetClassNameId=");
		sb.append(targetClassNameId);

		sb.append(", targetClassPK=");
		sb.append(targetClassPK);

		sb.append("}");

		throw new NoSuchCommerceQualifierEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce qualifier entry in the ordered set where targetClassNameId = &#63; and targetClassPK = &#63;.
	 *
	 * @param targetClassNameId the target class name ID
	 * @param targetClassPK the target class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce qualifier entry, or <code>null</code> if a matching commerce qualifier entry could not be found
	 */
	@Override
	public CommerceQualifierEntry fetchByT_T_Last(
		long targetClassNameId, long targetClassPK,
		OrderByComparator<CommerceQualifierEntry> orderByComparator) {

		int count = countByT_T(targetClassNameId, targetClassPK);

		if (count == 0) {
			return null;
		}

		List<CommerceQualifierEntry> list = findByT_T(
			targetClassNameId, targetClassPK, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce qualifier entries before and after the current commerce qualifier entry in the ordered set where targetClassNameId = &#63; and targetClassPK = &#63;.
	 *
	 * @param commerceQualifierEntryId the primary key of the current commerce qualifier entry
	 * @param targetClassNameId the target class name ID
	 * @param targetClassPK the target class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce qualifier entry
	 * @throws NoSuchCommerceQualifierEntryException if a commerce qualifier entry with the primary key could not be found
	 */
	@Override
	public CommerceQualifierEntry[] findByT_T_PrevAndNext(
			long commerceQualifierEntryId, long targetClassNameId,
			long targetClassPK,
			OrderByComparator<CommerceQualifierEntry> orderByComparator)
		throws NoSuchCommerceQualifierEntryException {

		CommerceQualifierEntry commerceQualifierEntry = findByPrimaryKey(
			commerceQualifierEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceQualifierEntry[] array = new CommerceQualifierEntryImpl[3];

			array[0] = getByT_T_PrevAndNext(
				session, commerceQualifierEntry, targetClassNameId,
				targetClassPK, orderByComparator, true);

			array[1] = commerceQualifierEntry;

			array[2] = getByT_T_PrevAndNext(
				session, commerceQualifierEntry, targetClassNameId,
				targetClassPK, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceQualifierEntry getByT_T_PrevAndNext(
		Session session, CommerceQualifierEntry commerceQualifierEntry,
		long targetClassNameId, long targetClassPK,
		OrderByComparator<CommerceQualifierEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEQUALIFIERENTRY_WHERE);

		sb.append(_FINDER_COLUMN_T_T_TARGETCLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_T_T_TARGETCLASSPK_2);

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
			sb.append(CommerceQualifierEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(targetClassNameId);

		queryPos.add(targetClassPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceQualifierEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceQualifierEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce qualifier entries where targetClassNameId = &#63; and targetClassPK = &#63; from the database.
	 *
	 * @param targetClassNameId the target class name ID
	 * @param targetClassPK the target class pk
	 */
	@Override
	public void removeByT_T(long targetClassNameId, long targetClassPK) {
		for (CommerceQualifierEntry commerceQualifierEntry :
				findByT_T(
					targetClassNameId, targetClassPK, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceQualifierEntry);
		}
	}

	/**
	 * Returns the number of commerce qualifier entries where targetClassNameId = &#63; and targetClassPK = &#63;.
	 *
	 * @param targetClassNameId the target class name ID
	 * @param targetClassPK the target class pk
	 * @return the number of matching commerce qualifier entries
	 */
	@Override
	public int countByT_T(long targetClassNameId, long targetClassPK) {
		FinderPath finderPath = _finderPathCountByT_T;

		Object[] finderArgs = new Object[] {targetClassNameId, targetClassPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEQUALIFIERENTRY_WHERE);

			sb.append(_FINDER_COLUMN_T_T_TARGETCLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_T_T_TARGETCLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(targetClassNameId);

				queryPos.add(targetClassPK);

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

	private static final String _FINDER_COLUMN_T_T_TARGETCLASSNAMEID_2 =
		"commerceQualifierEntry.targetClassNameId = ? AND ";

	private static final String _FINDER_COLUMN_T_T_TARGETCLASSPK_2 =
		"commerceQualifierEntry.targetClassPK = ?";

	private FinderPath _finderPathWithPaginationFindByS_S_T;
	private FinderPath _finderPathWithoutPaginationFindByS_S_T;
	private FinderPath _finderPathCountByS_S_T;

	/**
	 * Returns all the commerce qualifier entries where sourceClassNameId = &#63; and sourceClassPK = &#63; and targetClassNameId = &#63;.
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param sourceClassPK the source class pk
	 * @param targetClassNameId the target class name ID
	 * @return the matching commerce qualifier entries
	 */
	@Override
	public List<CommerceQualifierEntry> findByS_S_T(
		long sourceClassNameId, long sourceClassPK, long targetClassNameId) {

		return findByS_S_T(
			sourceClassNameId, sourceClassPK, targetClassNameId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce qualifier entries where sourceClassNameId = &#63; and sourceClassPK = &#63; and targetClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceQualifierEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param sourceClassPK the source class pk
	 * @param targetClassNameId the target class name ID
	 * @param start the lower bound of the range of commerce qualifier entries
	 * @param end the upper bound of the range of commerce qualifier entries (not inclusive)
	 * @return the range of matching commerce qualifier entries
	 */
	@Override
	public List<CommerceQualifierEntry> findByS_S_T(
		long sourceClassNameId, long sourceClassPK, long targetClassNameId,
		int start, int end) {

		return findByS_S_T(
			sourceClassNameId, sourceClassPK, targetClassNameId, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the commerce qualifier entries where sourceClassNameId = &#63; and sourceClassPK = &#63; and targetClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceQualifierEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param sourceClassPK the source class pk
	 * @param targetClassNameId the target class name ID
	 * @param start the lower bound of the range of commerce qualifier entries
	 * @param end the upper bound of the range of commerce qualifier entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce qualifier entries
	 */
	@Override
	public List<CommerceQualifierEntry> findByS_S_T(
		long sourceClassNameId, long sourceClassPK, long targetClassNameId,
		int start, int end,
		OrderByComparator<CommerceQualifierEntry> orderByComparator) {

		return findByS_S_T(
			sourceClassNameId, sourceClassPK, targetClassNameId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce qualifier entries where sourceClassNameId = &#63; and sourceClassPK = &#63; and targetClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceQualifierEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param sourceClassPK the source class pk
	 * @param targetClassNameId the target class name ID
	 * @param start the lower bound of the range of commerce qualifier entries
	 * @param end the upper bound of the range of commerce qualifier entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce qualifier entries
	 */
	@Override
	public List<CommerceQualifierEntry> findByS_S_T(
		long sourceClassNameId, long sourceClassPK, long targetClassNameId,
		int start, int end,
		OrderByComparator<CommerceQualifierEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByS_S_T;
				finderArgs = new Object[] {
					sourceClassNameId, sourceClassPK, targetClassNameId
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByS_S_T;
			finderArgs = new Object[] {
				sourceClassNameId, sourceClassPK, targetClassNameId, start, end,
				orderByComparator
			};
		}

		List<CommerceQualifierEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceQualifierEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceQualifierEntry commerceQualifierEntry : list) {
					if ((sourceClassNameId !=
							commerceQualifierEntry.getSourceClassNameId()) ||
						(sourceClassPK !=
							commerceQualifierEntry.getSourceClassPK()) ||
						(targetClassNameId !=
							commerceQualifierEntry.getTargetClassNameId())) {

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

			sb.append(_SQL_SELECT_COMMERCEQUALIFIERENTRY_WHERE);

			sb.append(_FINDER_COLUMN_S_S_T_SOURCECLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_S_S_T_SOURCECLASSPK_2);

			sb.append(_FINDER_COLUMN_S_S_T_TARGETCLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceQualifierEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(sourceClassNameId);

				queryPos.add(sourceClassPK);

				queryPos.add(targetClassNameId);

				list = (List<CommerceQualifierEntry>)QueryUtil.list(
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
	 * Returns the first commerce qualifier entry in the ordered set where sourceClassNameId = &#63; and sourceClassPK = &#63; and targetClassNameId = &#63;.
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param sourceClassPK the source class pk
	 * @param targetClassNameId the target class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce qualifier entry
	 * @throws NoSuchCommerceQualifierEntryException if a matching commerce qualifier entry could not be found
	 */
	@Override
	public CommerceQualifierEntry findByS_S_T_First(
			long sourceClassNameId, long sourceClassPK, long targetClassNameId,
			OrderByComparator<CommerceQualifierEntry> orderByComparator)
		throws NoSuchCommerceQualifierEntryException {

		CommerceQualifierEntry commerceQualifierEntry = fetchByS_S_T_First(
			sourceClassNameId, sourceClassPK, targetClassNameId,
			orderByComparator);

		if (commerceQualifierEntry != null) {
			return commerceQualifierEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("sourceClassNameId=");
		sb.append(sourceClassNameId);

		sb.append(", sourceClassPK=");
		sb.append(sourceClassPK);

		sb.append(", targetClassNameId=");
		sb.append(targetClassNameId);

		sb.append("}");

		throw new NoSuchCommerceQualifierEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce qualifier entry in the ordered set where sourceClassNameId = &#63; and sourceClassPK = &#63; and targetClassNameId = &#63;.
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param sourceClassPK the source class pk
	 * @param targetClassNameId the target class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce qualifier entry, or <code>null</code> if a matching commerce qualifier entry could not be found
	 */
	@Override
	public CommerceQualifierEntry fetchByS_S_T_First(
		long sourceClassNameId, long sourceClassPK, long targetClassNameId,
		OrderByComparator<CommerceQualifierEntry> orderByComparator) {

		List<CommerceQualifierEntry> list = findByS_S_T(
			sourceClassNameId, sourceClassPK, targetClassNameId, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce qualifier entry in the ordered set where sourceClassNameId = &#63; and sourceClassPK = &#63; and targetClassNameId = &#63;.
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param sourceClassPK the source class pk
	 * @param targetClassNameId the target class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce qualifier entry
	 * @throws NoSuchCommerceQualifierEntryException if a matching commerce qualifier entry could not be found
	 */
	@Override
	public CommerceQualifierEntry findByS_S_T_Last(
			long sourceClassNameId, long sourceClassPK, long targetClassNameId,
			OrderByComparator<CommerceQualifierEntry> orderByComparator)
		throws NoSuchCommerceQualifierEntryException {

		CommerceQualifierEntry commerceQualifierEntry = fetchByS_S_T_Last(
			sourceClassNameId, sourceClassPK, targetClassNameId,
			orderByComparator);

		if (commerceQualifierEntry != null) {
			return commerceQualifierEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("sourceClassNameId=");
		sb.append(sourceClassNameId);

		sb.append(", sourceClassPK=");
		sb.append(sourceClassPK);

		sb.append(", targetClassNameId=");
		sb.append(targetClassNameId);

		sb.append("}");

		throw new NoSuchCommerceQualifierEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce qualifier entry in the ordered set where sourceClassNameId = &#63; and sourceClassPK = &#63; and targetClassNameId = &#63;.
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param sourceClassPK the source class pk
	 * @param targetClassNameId the target class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce qualifier entry, or <code>null</code> if a matching commerce qualifier entry could not be found
	 */
	@Override
	public CommerceQualifierEntry fetchByS_S_T_Last(
		long sourceClassNameId, long sourceClassPK, long targetClassNameId,
		OrderByComparator<CommerceQualifierEntry> orderByComparator) {

		int count = countByS_S_T(
			sourceClassNameId, sourceClassPK, targetClassNameId);

		if (count == 0) {
			return null;
		}

		List<CommerceQualifierEntry> list = findByS_S_T(
			sourceClassNameId, sourceClassPK, targetClassNameId, count - 1,
			count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce qualifier entries before and after the current commerce qualifier entry in the ordered set where sourceClassNameId = &#63; and sourceClassPK = &#63; and targetClassNameId = &#63;.
	 *
	 * @param commerceQualifierEntryId the primary key of the current commerce qualifier entry
	 * @param sourceClassNameId the source class name ID
	 * @param sourceClassPK the source class pk
	 * @param targetClassNameId the target class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce qualifier entry
	 * @throws NoSuchCommerceQualifierEntryException if a commerce qualifier entry with the primary key could not be found
	 */
	@Override
	public CommerceQualifierEntry[] findByS_S_T_PrevAndNext(
			long commerceQualifierEntryId, long sourceClassNameId,
			long sourceClassPK, long targetClassNameId,
			OrderByComparator<CommerceQualifierEntry> orderByComparator)
		throws NoSuchCommerceQualifierEntryException {

		CommerceQualifierEntry commerceQualifierEntry = findByPrimaryKey(
			commerceQualifierEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceQualifierEntry[] array = new CommerceQualifierEntryImpl[3];

			array[0] = getByS_S_T_PrevAndNext(
				session, commerceQualifierEntry, sourceClassNameId,
				sourceClassPK, targetClassNameId, orderByComparator, true);

			array[1] = commerceQualifierEntry;

			array[2] = getByS_S_T_PrevAndNext(
				session, commerceQualifierEntry, sourceClassNameId,
				sourceClassPK, targetClassNameId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceQualifierEntry getByS_S_T_PrevAndNext(
		Session session, CommerceQualifierEntry commerceQualifierEntry,
		long sourceClassNameId, long sourceClassPK, long targetClassNameId,
		OrderByComparator<CommerceQualifierEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEQUALIFIERENTRY_WHERE);

		sb.append(_FINDER_COLUMN_S_S_T_SOURCECLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_S_S_T_SOURCECLASSPK_2);

		sb.append(_FINDER_COLUMN_S_S_T_TARGETCLASSNAMEID_2);

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
			sb.append(CommerceQualifierEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(sourceClassNameId);

		queryPos.add(sourceClassPK);

		queryPos.add(targetClassNameId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceQualifierEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceQualifierEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce qualifier entries where sourceClassNameId = &#63; and sourceClassPK = &#63; and targetClassNameId = &#63; from the database.
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param sourceClassPK the source class pk
	 * @param targetClassNameId the target class name ID
	 */
	@Override
	public void removeByS_S_T(
		long sourceClassNameId, long sourceClassPK, long targetClassNameId) {

		for (CommerceQualifierEntry commerceQualifierEntry :
				findByS_S_T(
					sourceClassNameId, sourceClassPK, targetClassNameId,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceQualifierEntry);
		}
	}

	/**
	 * Returns the number of commerce qualifier entries where sourceClassNameId = &#63; and sourceClassPK = &#63; and targetClassNameId = &#63;.
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param sourceClassPK the source class pk
	 * @param targetClassNameId the target class name ID
	 * @return the number of matching commerce qualifier entries
	 */
	@Override
	public int countByS_S_T(
		long sourceClassNameId, long sourceClassPK, long targetClassNameId) {

		FinderPath finderPath = _finderPathCountByS_S_T;

		Object[] finderArgs = new Object[] {
			sourceClassNameId, sourceClassPK, targetClassNameId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_COMMERCEQUALIFIERENTRY_WHERE);

			sb.append(_FINDER_COLUMN_S_S_T_SOURCECLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_S_S_T_SOURCECLASSPK_2);

			sb.append(_FINDER_COLUMN_S_S_T_TARGETCLASSNAMEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(sourceClassNameId);

				queryPos.add(sourceClassPK);

				queryPos.add(targetClassNameId);

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

	private static final String _FINDER_COLUMN_S_S_T_SOURCECLASSNAMEID_2 =
		"commerceQualifierEntry.sourceClassNameId = ? AND ";

	private static final String _FINDER_COLUMN_S_S_T_SOURCECLASSPK_2 =
		"commerceQualifierEntry.sourceClassPK = ? AND ";

	private static final String _FINDER_COLUMN_S_S_T_TARGETCLASSNAMEID_2 =
		"commerceQualifierEntry.targetClassNameId = ?";

	private FinderPath _finderPathWithPaginationFindByS_T_T;
	private FinderPath _finderPathWithoutPaginationFindByS_T_T;
	private FinderPath _finderPathCountByS_T_T;

	/**
	 * Returns all the commerce qualifier entries where sourceClassNameId = &#63; and targetClassNameId = &#63; and targetClassPK = &#63;.
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param targetClassNameId the target class name ID
	 * @param targetClassPK the target class pk
	 * @return the matching commerce qualifier entries
	 */
	@Override
	public List<CommerceQualifierEntry> findByS_T_T(
		long sourceClassNameId, long targetClassNameId, long targetClassPK) {

		return findByS_T_T(
			sourceClassNameId, targetClassNameId, targetClassPK,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce qualifier entries where sourceClassNameId = &#63; and targetClassNameId = &#63; and targetClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceQualifierEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param targetClassNameId the target class name ID
	 * @param targetClassPK the target class pk
	 * @param start the lower bound of the range of commerce qualifier entries
	 * @param end the upper bound of the range of commerce qualifier entries (not inclusive)
	 * @return the range of matching commerce qualifier entries
	 */
	@Override
	public List<CommerceQualifierEntry> findByS_T_T(
		long sourceClassNameId, long targetClassNameId, long targetClassPK,
		int start, int end) {

		return findByS_T_T(
			sourceClassNameId, targetClassNameId, targetClassPK, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the commerce qualifier entries where sourceClassNameId = &#63; and targetClassNameId = &#63; and targetClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceQualifierEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param targetClassNameId the target class name ID
	 * @param targetClassPK the target class pk
	 * @param start the lower bound of the range of commerce qualifier entries
	 * @param end the upper bound of the range of commerce qualifier entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce qualifier entries
	 */
	@Override
	public List<CommerceQualifierEntry> findByS_T_T(
		long sourceClassNameId, long targetClassNameId, long targetClassPK,
		int start, int end,
		OrderByComparator<CommerceQualifierEntry> orderByComparator) {

		return findByS_T_T(
			sourceClassNameId, targetClassNameId, targetClassPK, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce qualifier entries where sourceClassNameId = &#63; and targetClassNameId = &#63; and targetClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceQualifierEntryModelImpl</code>.
	 * </p>
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param targetClassNameId the target class name ID
	 * @param targetClassPK the target class pk
	 * @param start the lower bound of the range of commerce qualifier entries
	 * @param end the upper bound of the range of commerce qualifier entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce qualifier entries
	 */
	@Override
	public List<CommerceQualifierEntry> findByS_T_T(
		long sourceClassNameId, long targetClassNameId, long targetClassPK,
		int start, int end,
		OrderByComparator<CommerceQualifierEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByS_T_T;
				finderArgs = new Object[] {
					sourceClassNameId, targetClassNameId, targetClassPK
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByS_T_T;
			finderArgs = new Object[] {
				sourceClassNameId, targetClassNameId, targetClassPK, start, end,
				orderByComparator
			};
		}

		List<CommerceQualifierEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceQualifierEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceQualifierEntry commerceQualifierEntry : list) {
					if ((sourceClassNameId !=
							commerceQualifierEntry.getSourceClassNameId()) ||
						(targetClassNameId !=
							commerceQualifierEntry.getTargetClassNameId()) ||
						(targetClassPK !=
							commerceQualifierEntry.getTargetClassPK())) {

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

			sb.append(_SQL_SELECT_COMMERCEQUALIFIERENTRY_WHERE);

			sb.append(_FINDER_COLUMN_S_T_T_SOURCECLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_S_T_T_TARGETCLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_S_T_T_TARGETCLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceQualifierEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(sourceClassNameId);

				queryPos.add(targetClassNameId);

				queryPos.add(targetClassPK);

				list = (List<CommerceQualifierEntry>)QueryUtil.list(
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
	 * Returns the first commerce qualifier entry in the ordered set where sourceClassNameId = &#63; and targetClassNameId = &#63; and targetClassPK = &#63;.
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param targetClassNameId the target class name ID
	 * @param targetClassPK the target class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce qualifier entry
	 * @throws NoSuchCommerceQualifierEntryException if a matching commerce qualifier entry could not be found
	 */
	@Override
	public CommerceQualifierEntry findByS_T_T_First(
			long sourceClassNameId, long targetClassNameId, long targetClassPK,
			OrderByComparator<CommerceQualifierEntry> orderByComparator)
		throws NoSuchCommerceQualifierEntryException {

		CommerceQualifierEntry commerceQualifierEntry = fetchByS_T_T_First(
			sourceClassNameId, targetClassNameId, targetClassPK,
			orderByComparator);

		if (commerceQualifierEntry != null) {
			return commerceQualifierEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("sourceClassNameId=");
		sb.append(sourceClassNameId);

		sb.append(", targetClassNameId=");
		sb.append(targetClassNameId);

		sb.append(", targetClassPK=");
		sb.append(targetClassPK);

		sb.append("}");

		throw new NoSuchCommerceQualifierEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce qualifier entry in the ordered set where sourceClassNameId = &#63; and targetClassNameId = &#63; and targetClassPK = &#63;.
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param targetClassNameId the target class name ID
	 * @param targetClassPK the target class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce qualifier entry, or <code>null</code> if a matching commerce qualifier entry could not be found
	 */
	@Override
	public CommerceQualifierEntry fetchByS_T_T_First(
		long sourceClassNameId, long targetClassNameId, long targetClassPK,
		OrderByComparator<CommerceQualifierEntry> orderByComparator) {

		List<CommerceQualifierEntry> list = findByS_T_T(
			sourceClassNameId, targetClassNameId, targetClassPK, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce qualifier entry in the ordered set where sourceClassNameId = &#63; and targetClassNameId = &#63; and targetClassPK = &#63;.
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param targetClassNameId the target class name ID
	 * @param targetClassPK the target class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce qualifier entry
	 * @throws NoSuchCommerceQualifierEntryException if a matching commerce qualifier entry could not be found
	 */
	@Override
	public CommerceQualifierEntry findByS_T_T_Last(
			long sourceClassNameId, long targetClassNameId, long targetClassPK,
			OrderByComparator<CommerceQualifierEntry> orderByComparator)
		throws NoSuchCommerceQualifierEntryException {

		CommerceQualifierEntry commerceQualifierEntry = fetchByS_T_T_Last(
			sourceClassNameId, targetClassNameId, targetClassPK,
			orderByComparator);

		if (commerceQualifierEntry != null) {
			return commerceQualifierEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("sourceClassNameId=");
		sb.append(sourceClassNameId);

		sb.append(", targetClassNameId=");
		sb.append(targetClassNameId);

		sb.append(", targetClassPK=");
		sb.append(targetClassPK);

		sb.append("}");

		throw new NoSuchCommerceQualifierEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce qualifier entry in the ordered set where sourceClassNameId = &#63; and targetClassNameId = &#63; and targetClassPK = &#63;.
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param targetClassNameId the target class name ID
	 * @param targetClassPK the target class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce qualifier entry, or <code>null</code> if a matching commerce qualifier entry could not be found
	 */
	@Override
	public CommerceQualifierEntry fetchByS_T_T_Last(
		long sourceClassNameId, long targetClassNameId, long targetClassPK,
		OrderByComparator<CommerceQualifierEntry> orderByComparator) {

		int count = countByS_T_T(
			sourceClassNameId, targetClassNameId, targetClassPK);

		if (count == 0) {
			return null;
		}

		List<CommerceQualifierEntry> list = findByS_T_T(
			sourceClassNameId, targetClassNameId, targetClassPK, count - 1,
			count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce qualifier entries before and after the current commerce qualifier entry in the ordered set where sourceClassNameId = &#63; and targetClassNameId = &#63; and targetClassPK = &#63;.
	 *
	 * @param commerceQualifierEntryId the primary key of the current commerce qualifier entry
	 * @param sourceClassNameId the source class name ID
	 * @param targetClassNameId the target class name ID
	 * @param targetClassPK the target class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce qualifier entry
	 * @throws NoSuchCommerceQualifierEntryException if a commerce qualifier entry with the primary key could not be found
	 */
	@Override
	public CommerceQualifierEntry[] findByS_T_T_PrevAndNext(
			long commerceQualifierEntryId, long sourceClassNameId,
			long targetClassNameId, long targetClassPK,
			OrderByComparator<CommerceQualifierEntry> orderByComparator)
		throws NoSuchCommerceQualifierEntryException {

		CommerceQualifierEntry commerceQualifierEntry = findByPrimaryKey(
			commerceQualifierEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceQualifierEntry[] array = new CommerceQualifierEntryImpl[3];

			array[0] = getByS_T_T_PrevAndNext(
				session, commerceQualifierEntry, sourceClassNameId,
				targetClassNameId, targetClassPK, orderByComparator, true);

			array[1] = commerceQualifierEntry;

			array[2] = getByS_T_T_PrevAndNext(
				session, commerceQualifierEntry, sourceClassNameId,
				targetClassNameId, targetClassPK, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceQualifierEntry getByS_T_T_PrevAndNext(
		Session session, CommerceQualifierEntry commerceQualifierEntry,
		long sourceClassNameId, long targetClassNameId, long targetClassPK,
		OrderByComparator<CommerceQualifierEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEQUALIFIERENTRY_WHERE);

		sb.append(_FINDER_COLUMN_S_T_T_SOURCECLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_S_T_T_TARGETCLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_S_T_T_TARGETCLASSPK_2);

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
			sb.append(CommerceQualifierEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(sourceClassNameId);

		queryPos.add(targetClassNameId);

		queryPos.add(targetClassPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceQualifierEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceQualifierEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce qualifier entries where sourceClassNameId = &#63; and targetClassNameId = &#63; and targetClassPK = &#63; from the database.
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param targetClassNameId the target class name ID
	 * @param targetClassPK the target class pk
	 */
	@Override
	public void removeByS_T_T(
		long sourceClassNameId, long targetClassNameId, long targetClassPK) {

		for (CommerceQualifierEntry commerceQualifierEntry :
				findByS_T_T(
					sourceClassNameId, targetClassNameId, targetClassPK,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceQualifierEntry);
		}
	}

	/**
	 * Returns the number of commerce qualifier entries where sourceClassNameId = &#63; and targetClassNameId = &#63; and targetClassPK = &#63;.
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param targetClassNameId the target class name ID
	 * @param targetClassPK the target class pk
	 * @return the number of matching commerce qualifier entries
	 */
	@Override
	public int countByS_T_T(
		long sourceClassNameId, long targetClassNameId, long targetClassPK) {

		FinderPath finderPath = _finderPathCountByS_T_T;

		Object[] finderArgs = new Object[] {
			sourceClassNameId, targetClassNameId, targetClassPK
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_COMMERCEQUALIFIERENTRY_WHERE);

			sb.append(_FINDER_COLUMN_S_T_T_SOURCECLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_S_T_T_TARGETCLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_S_T_T_TARGETCLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(sourceClassNameId);

				queryPos.add(targetClassNameId);

				queryPos.add(targetClassPK);

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

	private static final String _FINDER_COLUMN_S_T_T_SOURCECLASSNAMEID_2 =
		"commerceQualifierEntry.sourceClassNameId = ? AND ";

	private static final String _FINDER_COLUMN_S_T_T_TARGETCLASSNAMEID_2 =
		"commerceQualifierEntry.targetClassNameId = ? AND ";

	private static final String _FINDER_COLUMN_S_T_T_TARGETCLASSPK_2 =
		"commerceQualifierEntry.targetClassPK = ?";

	private FinderPath _finderPathFetchByS_S_T_T;
	private FinderPath _finderPathCountByS_S_T_T;

	/**
	 * Returns the commerce qualifier entry where sourceClassNameId = &#63; and sourceClassPK = &#63; and targetClassNameId = &#63; and targetClassPK = &#63; or throws a <code>NoSuchCommerceQualifierEntryException</code> if it could not be found.
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param sourceClassPK the source class pk
	 * @param targetClassNameId the target class name ID
	 * @param targetClassPK the target class pk
	 * @return the matching commerce qualifier entry
	 * @throws NoSuchCommerceQualifierEntryException if a matching commerce qualifier entry could not be found
	 */
	@Override
	public CommerceQualifierEntry findByS_S_T_T(
			long sourceClassNameId, long sourceClassPK, long targetClassNameId,
			long targetClassPK)
		throws NoSuchCommerceQualifierEntryException {

		CommerceQualifierEntry commerceQualifierEntry = fetchByS_S_T_T(
			sourceClassNameId, sourceClassPK, targetClassNameId, targetClassPK);

		if (commerceQualifierEntry == null) {
			StringBundler sb = new StringBundler(10);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("sourceClassNameId=");
			sb.append(sourceClassNameId);

			sb.append(", sourceClassPK=");
			sb.append(sourceClassPK);

			sb.append(", targetClassNameId=");
			sb.append(targetClassNameId);

			sb.append(", targetClassPK=");
			sb.append(targetClassPK);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCommerceQualifierEntryException(sb.toString());
		}

		return commerceQualifierEntry;
	}

	/**
	 * Returns the commerce qualifier entry where sourceClassNameId = &#63; and sourceClassPK = &#63; and targetClassNameId = &#63; and targetClassPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param sourceClassPK the source class pk
	 * @param targetClassNameId the target class name ID
	 * @param targetClassPK the target class pk
	 * @return the matching commerce qualifier entry, or <code>null</code> if a matching commerce qualifier entry could not be found
	 */
	@Override
	public CommerceQualifierEntry fetchByS_S_T_T(
		long sourceClassNameId, long sourceClassPK, long targetClassNameId,
		long targetClassPK) {

		return fetchByS_S_T_T(
			sourceClassNameId, sourceClassPK, targetClassNameId, targetClassPK,
			true);
	}

	/**
	 * Returns the commerce qualifier entry where sourceClassNameId = &#63; and sourceClassPK = &#63; and targetClassNameId = &#63; and targetClassPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param sourceClassPK the source class pk
	 * @param targetClassNameId the target class name ID
	 * @param targetClassPK the target class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce qualifier entry, or <code>null</code> if a matching commerce qualifier entry could not be found
	 */
	@Override
	public CommerceQualifierEntry fetchByS_S_T_T(
		long sourceClassNameId, long sourceClassPK, long targetClassNameId,
		long targetClassPK, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				sourceClassNameId, sourceClassPK, targetClassNameId,
				targetClassPK
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByS_S_T_T, finderArgs, this);
		}

		if (result instanceof CommerceQualifierEntry) {
			CommerceQualifierEntry commerceQualifierEntry =
				(CommerceQualifierEntry)result;

			if ((sourceClassNameId !=
					commerceQualifierEntry.getSourceClassNameId()) ||
				(sourceClassPK != commerceQualifierEntry.getSourceClassPK()) ||
				(targetClassNameId !=
					commerceQualifierEntry.getTargetClassNameId()) ||
				(targetClassPK != commerceQualifierEntry.getTargetClassPK())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_SQL_SELECT_COMMERCEQUALIFIERENTRY_WHERE);

			sb.append(_FINDER_COLUMN_S_S_T_T_SOURCECLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_S_S_T_T_SOURCECLASSPK_2);

			sb.append(_FINDER_COLUMN_S_S_T_T_TARGETCLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_S_S_T_T_TARGETCLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(sourceClassNameId);

				queryPos.add(sourceClassPK);

				queryPos.add(targetClassNameId);

				queryPos.add(targetClassPK);

				List<CommerceQualifierEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByS_S_T_T, finderArgs, list);
					}
				}
				else {
					CommerceQualifierEntry commerceQualifierEntry = list.get(0);

					result = commerceQualifierEntry;

					cacheResult(commerceQualifierEntry);
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
			return (CommerceQualifierEntry)result;
		}
	}

	/**
	 * Removes the commerce qualifier entry where sourceClassNameId = &#63; and sourceClassPK = &#63; and targetClassNameId = &#63; and targetClassPK = &#63; from the database.
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param sourceClassPK the source class pk
	 * @param targetClassNameId the target class name ID
	 * @param targetClassPK the target class pk
	 * @return the commerce qualifier entry that was removed
	 */
	@Override
	public CommerceQualifierEntry removeByS_S_T_T(
			long sourceClassNameId, long sourceClassPK, long targetClassNameId,
			long targetClassPK)
		throws NoSuchCommerceQualifierEntryException {

		CommerceQualifierEntry commerceQualifierEntry = findByS_S_T_T(
			sourceClassNameId, sourceClassPK, targetClassNameId, targetClassPK);

		return remove(commerceQualifierEntry);
	}

	/**
	 * Returns the number of commerce qualifier entries where sourceClassNameId = &#63; and sourceClassPK = &#63; and targetClassNameId = &#63; and targetClassPK = &#63;.
	 *
	 * @param sourceClassNameId the source class name ID
	 * @param sourceClassPK the source class pk
	 * @param targetClassNameId the target class name ID
	 * @param targetClassPK the target class pk
	 * @return the number of matching commerce qualifier entries
	 */
	@Override
	public int countByS_S_T_T(
		long sourceClassNameId, long sourceClassPK, long targetClassNameId,
		long targetClassPK) {

		FinderPath finderPath = _finderPathCountByS_S_T_T;

		Object[] finderArgs = new Object[] {
			sourceClassNameId, sourceClassPK, targetClassNameId, targetClassPK
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_COMMERCEQUALIFIERENTRY_WHERE);

			sb.append(_FINDER_COLUMN_S_S_T_T_SOURCECLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_S_S_T_T_SOURCECLASSPK_2);

			sb.append(_FINDER_COLUMN_S_S_T_T_TARGETCLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_S_S_T_T_TARGETCLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(sourceClassNameId);

				queryPos.add(sourceClassPK);

				queryPos.add(targetClassNameId);

				queryPos.add(targetClassPK);

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

	private static final String _FINDER_COLUMN_S_S_T_T_SOURCECLASSNAMEID_2 =
		"commerceQualifierEntry.sourceClassNameId = ? AND ";

	private static final String _FINDER_COLUMN_S_S_T_T_SOURCECLASSPK_2 =
		"commerceQualifierEntry.sourceClassPK = ? AND ";

	private static final String _FINDER_COLUMN_S_S_T_T_TARGETCLASSNAMEID_2 =
		"commerceQualifierEntry.targetClassNameId = ? AND ";

	private static final String _FINDER_COLUMN_S_S_T_T_TARGETCLASSPK_2 =
		"commerceQualifierEntry.targetClassPK = ?";

	public CommerceQualifierEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put(
			"sourceCommerceQualifierMetadataKey",
			"sourceCQualifierMetadataKey");
		dbColumnNames.put(
			"targetCommerceQualifierMetadataKey",
			"targetCQualifierMetadataKey");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceQualifierEntry.class);

		setModelImplClass(CommerceQualifierEntryImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceQualifierEntryTable.INSTANCE);
	}

	/**
	 * Caches the commerce qualifier entry in the entity cache if it is enabled.
	 *
	 * @param commerceQualifierEntry the commerce qualifier entry
	 */
	@Override
	public void cacheResult(CommerceQualifierEntry commerceQualifierEntry) {
		entityCache.putResult(
			CommerceQualifierEntryImpl.class,
			commerceQualifierEntry.getPrimaryKey(), commerceQualifierEntry);

		finderCache.putResult(
			_finderPathFetchByS_S_T_T,
			new Object[] {
				commerceQualifierEntry.getSourceClassNameId(),
				commerceQualifierEntry.getSourceClassPK(),
				commerceQualifierEntry.getTargetClassNameId(),
				commerceQualifierEntry.getTargetClassPK()
			},
			commerceQualifierEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce qualifier entries in the entity cache if it is enabled.
	 *
	 * @param commerceQualifierEntries the commerce qualifier entries
	 */
	@Override
	public void cacheResult(
		List<CommerceQualifierEntry> commerceQualifierEntries) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceQualifierEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceQualifierEntry commerceQualifierEntry :
				commerceQualifierEntries) {

			if (entityCache.getResult(
					CommerceQualifierEntryImpl.class,
					commerceQualifierEntry.getPrimaryKey()) == null) {

				cacheResult(commerceQualifierEntry);
			}
		}
	}

	/**
	 * Clears the cache for all commerce qualifier entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceQualifierEntryImpl.class);

		finderCache.clearCache(CommerceQualifierEntryImpl.class);
	}

	/**
	 * Clears the cache for the commerce qualifier entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceQualifierEntry commerceQualifierEntry) {
		entityCache.removeResult(
			CommerceQualifierEntryImpl.class, commerceQualifierEntry);
	}

	@Override
	public void clearCache(
		List<CommerceQualifierEntry> commerceQualifierEntries) {

		for (CommerceQualifierEntry commerceQualifierEntry :
				commerceQualifierEntries) {

			entityCache.removeResult(
				CommerceQualifierEntryImpl.class, commerceQualifierEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceQualifierEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceQualifierEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceQualifierEntryModelImpl commerceQualifierEntryModelImpl) {

		Object[] args = new Object[] {
			commerceQualifierEntryModelImpl.getSourceClassNameId(),
			commerceQualifierEntryModelImpl.getSourceClassPK(),
			commerceQualifierEntryModelImpl.getTargetClassNameId(),
			commerceQualifierEntryModelImpl.getTargetClassPK()
		};

		finderCache.putResult(_finderPathCountByS_S_T_T, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByS_S_T_T, args, commerceQualifierEntryModelImpl);
	}

	/**
	 * Creates a new commerce qualifier entry with the primary key. Does not add the commerce qualifier entry to the database.
	 *
	 * @param commerceQualifierEntryId the primary key for the new commerce qualifier entry
	 * @return the new commerce qualifier entry
	 */
	@Override
	public CommerceQualifierEntry create(long commerceQualifierEntryId) {
		CommerceQualifierEntry commerceQualifierEntry =
			new CommerceQualifierEntryImpl();

		commerceQualifierEntry.setNew(true);
		commerceQualifierEntry.setPrimaryKey(commerceQualifierEntryId);

		commerceQualifierEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return commerceQualifierEntry;
	}

	/**
	 * Removes the commerce qualifier entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceQualifierEntryId the primary key of the commerce qualifier entry
	 * @return the commerce qualifier entry that was removed
	 * @throws NoSuchCommerceQualifierEntryException if a commerce qualifier entry with the primary key could not be found
	 */
	@Override
	public CommerceQualifierEntry remove(long commerceQualifierEntryId)
		throws NoSuchCommerceQualifierEntryException {

		return remove((Serializable)commerceQualifierEntryId);
	}

	/**
	 * Removes the commerce qualifier entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce qualifier entry
	 * @return the commerce qualifier entry that was removed
	 * @throws NoSuchCommerceQualifierEntryException if a commerce qualifier entry with the primary key could not be found
	 */
	@Override
	public CommerceQualifierEntry remove(Serializable primaryKey)
		throws NoSuchCommerceQualifierEntryException {

		Session session = null;

		try {
			session = openSession();

			CommerceQualifierEntry commerceQualifierEntry =
				(CommerceQualifierEntry)session.get(
					CommerceQualifierEntryImpl.class, primaryKey);

			if (commerceQualifierEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCommerceQualifierEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceQualifierEntry);
		}
		catch (NoSuchCommerceQualifierEntryException noSuchEntityException) {
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
	protected CommerceQualifierEntry removeImpl(
		CommerceQualifierEntry commerceQualifierEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceQualifierEntry)) {
				commerceQualifierEntry = (CommerceQualifierEntry)session.get(
					CommerceQualifierEntryImpl.class,
					commerceQualifierEntry.getPrimaryKeyObj());
			}

			if (commerceQualifierEntry != null) {
				session.delete(commerceQualifierEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceQualifierEntry != null) {
			clearCache(commerceQualifierEntry);
		}

		return commerceQualifierEntry;
	}

	@Override
	public CommerceQualifierEntry updateImpl(
		CommerceQualifierEntry commerceQualifierEntry) {

		boolean isNew = commerceQualifierEntry.isNew();

		if (!(commerceQualifierEntry instanceof
				CommerceQualifierEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceQualifierEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceQualifierEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceQualifierEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceQualifierEntry implementation " +
					commerceQualifierEntry.getClass());
		}

		CommerceQualifierEntryModelImpl commerceQualifierEntryModelImpl =
			(CommerceQualifierEntryModelImpl)commerceQualifierEntry;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commerceQualifierEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceQualifierEntry.setCreateDate(date);
			}
			else {
				commerceQualifierEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceQualifierEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceQualifierEntry.setModifiedDate(date);
			}
			else {
				commerceQualifierEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceQualifierEntry);
			}
			else {
				commerceQualifierEntry = (CommerceQualifierEntry)session.merge(
					commerceQualifierEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceQualifierEntryImpl.class, commerceQualifierEntryModelImpl,
			false, true);

		cacheUniqueFindersCache(commerceQualifierEntryModelImpl);

		if (isNew) {
			commerceQualifierEntry.setNew(false);
		}

		commerceQualifierEntry.resetOriginalValues();

		return commerceQualifierEntry;
	}

	/**
	 * Returns the commerce qualifier entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce qualifier entry
	 * @return the commerce qualifier entry
	 * @throws NoSuchCommerceQualifierEntryException if a commerce qualifier entry with the primary key could not be found
	 */
	@Override
	public CommerceQualifierEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCommerceQualifierEntryException {

		CommerceQualifierEntry commerceQualifierEntry = fetchByPrimaryKey(
			primaryKey);

		if (commerceQualifierEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCommerceQualifierEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceQualifierEntry;
	}

	/**
	 * Returns the commerce qualifier entry with the primary key or throws a <code>NoSuchCommerceQualifierEntryException</code> if it could not be found.
	 *
	 * @param commerceQualifierEntryId the primary key of the commerce qualifier entry
	 * @return the commerce qualifier entry
	 * @throws NoSuchCommerceQualifierEntryException if a commerce qualifier entry with the primary key could not be found
	 */
	@Override
	public CommerceQualifierEntry findByPrimaryKey(
			long commerceQualifierEntryId)
		throws NoSuchCommerceQualifierEntryException {

		return findByPrimaryKey((Serializable)commerceQualifierEntryId);
	}

	/**
	 * Returns the commerce qualifier entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceQualifierEntryId the primary key of the commerce qualifier entry
	 * @return the commerce qualifier entry, or <code>null</code> if a commerce qualifier entry with the primary key could not be found
	 */
	@Override
	public CommerceQualifierEntry fetchByPrimaryKey(
		long commerceQualifierEntryId) {

		return fetchByPrimaryKey((Serializable)commerceQualifierEntryId);
	}

	/**
	 * Returns all the commerce qualifier entries.
	 *
	 * @return the commerce qualifier entries
	 */
	@Override
	public List<CommerceQualifierEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce qualifier entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceQualifierEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce qualifier entries
	 * @param end the upper bound of the range of commerce qualifier entries (not inclusive)
	 * @return the range of commerce qualifier entries
	 */
	@Override
	public List<CommerceQualifierEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce qualifier entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceQualifierEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce qualifier entries
	 * @param end the upper bound of the range of commerce qualifier entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce qualifier entries
	 */
	@Override
	public List<CommerceQualifierEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceQualifierEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce qualifier entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceQualifierEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce qualifier entries
	 * @param end the upper bound of the range of commerce qualifier entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce qualifier entries
	 */
	@Override
	public List<CommerceQualifierEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceQualifierEntry> orderByComparator,
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

		List<CommerceQualifierEntry> list = null;

		if (useFinderCache) {
			list = (List<CommerceQualifierEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEQUALIFIERENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEQUALIFIERENTRY;

				sql = sql.concat(CommerceQualifierEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceQualifierEntry>)QueryUtil.list(
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
	 * Removes all the commerce qualifier entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceQualifierEntry commerceQualifierEntry : findAll()) {
			remove(commerceQualifierEntry);
		}
	}

	/**
	 * Returns the number of commerce qualifier entries.
	 *
	 * @return the number of commerce qualifier entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_COMMERCEQUALIFIERENTRY);

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
		return "commerceQualifierEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEQUALIFIERENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceQualifierEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce qualifier entry persistence.
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

		_finderPathWithPaginationFindByS_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByS_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"sourceClassNameId", "sourceClassPK"}, true);

		_finderPathWithoutPaginationFindByS_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByS_S",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"sourceClassNameId", "sourceClassPK"}, true);

		_finderPathCountByS_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByS_S",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"sourceClassNameId", "sourceClassPK"}, false);

		_finderPathWithPaginationFindByT_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByT_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"targetClassNameId", "targetClassPK"}, true);

		_finderPathWithoutPaginationFindByT_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByT_T",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"targetClassNameId", "targetClassPK"}, true);

		_finderPathCountByT_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByT_T",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"targetClassNameId", "targetClassPK"}, false);

		_finderPathWithPaginationFindByS_S_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByS_S_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {
				"sourceClassNameId", "sourceClassPK", "targetClassNameId"
			},
			true);

		_finderPathWithoutPaginationFindByS_S_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByS_S_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {
				"sourceClassNameId", "sourceClassPK", "targetClassNameId"
			},
			true);

		_finderPathCountByS_S_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByS_S_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {
				"sourceClassNameId", "sourceClassPK", "targetClassNameId"
			},
			false);

		_finderPathWithPaginationFindByS_T_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByS_T_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {
				"sourceClassNameId", "targetClassNameId", "targetClassPK"
			},
			true);

		_finderPathWithoutPaginationFindByS_T_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByS_T_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {
				"sourceClassNameId", "targetClassNameId", "targetClassPK"
			},
			true);

		_finderPathCountByS_T_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByS_T_T",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {
				"sourceClassNameId", "targetClassNameId", "targetClassPK"
			},
			false);

		_finderPathFetchByS_S_T_T = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByS_S_T_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Long.class.getName()
			},
			new String[] {
				"sourceClassNameId", "sourceClassPK", "targetClassNameId",
				"targetClassPK"
			},
			true);

		_finderPathCountByS_S_T_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByS_S_T_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Long.class.getName()
			},
			new String[] {
				"sourceClassNameId", "sourceClassPK", "targetClassNameId",
				"targetClassPK"
			},
			false);

		CommerceQualifierEntryUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CommerceQualifierEntryUtil.setPersistence(null);

		entityCache.removeCache(CommerceQualifierEntryImpl.class.getName());
	}

	@Override
	@Reference(
		target = CommercePersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = CommercePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = CommercePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCEQUALIFIERENTRY =
		"SELECT commerceQualifierEntry FROM CommerceQualifierEntry commerceQualifierEntry";

	private static final String _SQL_SELECT_COMMERCEQUALIFIERENTRY_WHERE =
		"SELECT commerceQualifierEntry FROM CommerceQualifierEntry commerceQualifierEntry WHERE ";

	private static final String _SQL_COUNT_COMMERCEQUALIFIERENTRY =
		"SELECT COUNT(commerceQualifierEntry) FROM CommerceQualifierEntry commerceQualifierEntry";

	private static final String _SQL_COUNT_COMMERCEQUALIFIERENTRY_WHERE =
		"SELECT COUNT(commerceQualifierEntry) FROM CommerceQualifierEntry commerceQualifierEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceQualifierEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceQualifierEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceQualifierEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceQualifierEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {
			"sourceCommerceQualifierMetadataKey",
			"targetCommerceQualifierMetadataKey"
		});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}