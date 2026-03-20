/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.service.persistence.impl;

import com.liferay.cookies.exception.NoSuchConsentPreferenceException;
import com.liferay.cookies.model.ConsentPreference;
import com.liferay.cookies.model.ConsentPreferenceTable;
import com.liferay.cookies.model.impl.ConsentPreferenceImpl;
import com.liferay.cookies.model.impl.ConsentPreferenceModelImpl;
import com.liferay.cookies.service.persistence.ConsentPreferencePersistence;
import com.liferay.cookies.service.persistence.ConsentPreferenceUtil;
import com.liferay.cookies.service.persistence.impl.constants.CookiesPersistenceConstants;
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.sql.Timestamp;

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
 * The persistence implementation for the consent preference service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = ConsentPreferencePersistence.class)
public class ConsentPreferencePersistenceImpl
	extends BasePersistenceImpl<ConsentPreference>
	implements ConsentPreferencePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ConsentPreferenceUtil</code> to access the consent preference persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ConsentPreferenceImpl.class.getName();

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
	 * Returns all the consent preferences where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching consent preferences
	 */
	@Override
	public List<ConsentPreference> findByUserId(long userId) {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the consent preferences where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ConsentPreferenceModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of consent preferences
	 * @param end the upper bound of the range of consent preferences (not inclusive)
	 * @return the range of matching consent preferences
	 */
	@Override
	public List<ConsentPreference> findByUserId(
		long userId, int start, int end) {

		return findByUserId(userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the consent preferences where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ConsentPreferenceModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of consent preferences
	 * @param end the upper bound of the range of consent preferences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching consent preferences
	 */
	@Override
	public List<ConsentPreference> findByUserId(
		long userId, int start, int end,
		OrderByComparator<ConsentPreference> orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the consent preferences where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ConsentPreferenceModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of consent preferences
	 * @param end the upper bound of the range of consent preferences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching consent preferences
	 */
	@Override
	public List<ConsentPreference> findByUserId(
		long userId, int start, int end,
		OrderByComparator<ConsentPreference> orderByComparator,
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

		List<ConsentPreference> list = null;

		if (useFinderCache) {
			list = (List<ConsentPreference>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ConsentPreference consentPreference : list) {
					if (userId != consentPreference.getUserId()) {
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

			sb.append(_SQL_SELECT_CONSENTPREFERENCE_WHERE);

			sb.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ConsentPreferenceModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				list = (List<ConsentPreference>)QueryUtil.list(
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
	 * Returns the first consent preference in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching consent preference
	 * @throws NoSuchConsentPreferenceException if a matching consent preference could not be found
	 */
	@Override
	public ConsentPreference findByUserId_First(
			long userId, OrderByComparator<ConsentPreference> orderByComparator)
		throws NoSuchConsentPreferenceException {

		ConsentPreference consentPreference = fetchByUserId_First(
			userId, orderByComparator);

		if (consentPreference != null) {
			return consentPreference;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchConsentPreferenceException(sb.toString());
	}

	/**
	 * Returns the first consent preference in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching consent preference, or <code>null</code> if a matching consent preference could not be found
	 */
	@Override
	public ConsentPreference fetchByUserId_First(
		long userId, OrderByComparator<ConsentPreference> orderByComparator) {

		List<ConsentPreference> list = findByUserId(
			userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last consent preference in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching consent preference
	 * @throws NoSuchConsentPreferenceException if a matching consent preference could not be found
	 */
	@Override
	public ConsentPreference findByUserId_Last(
			long userId, OrderByComparator<ConsentPreference> orderByComparator)
		throws NoSuchConsentPreferenceException {

		ConsentPreference consentPreference = fetchByUserId_Last(
			userId, orderByComparator);

		if (consentPreference != null) {
			return consentPreference;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchConsentPreferenceException(sb.toString());
	}

	/**
	 * Returns the last consent preference in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching consent preference, or <code>null</code> if a matching consent preference could not be found
	 */
	@Override
	public ConsentPreference fetchByUserId_Last(
		long userId, OrderByComparator<ConsentPreference> orderByComparator) {

		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<ConsentPreference> list = findByUserId(
			userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the consent preferences where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	@Override
	public void removeByUserId(long userId) {
		for (ConsentPreference consentPreference :
				findByUserId(
					userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(consentPreference);
		}
	}

	/**
	 * Returns the number of consent preferences where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching consent preferences
	 */
	@Override
	public int countByUserId(long userId) {
		FinderPath finderPath = _finderPathCountByUserId;

		Object[] finderArgs = new Object[] {userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CONSENTPREFERENCE_WHERE);

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
		"consentPreference.userId = ?";

	private FinderPath _finderPathWithPaginationFindByExpirationDate;
	private FinderPath _finderPathWithoutPaginationFindByExpirationDate;
	private FinderPath _finderPathCountByExpirationDate;

	/**
	 * Returns all the consent preferences where expirationDate = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @return the matching consent preferences
	 */
	@Override
	public List<ConsentPreference> findByExpirationDate(Date expirationDate) {
		return findByExpirationDate(
			expirationDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the consent preferences where expirationDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ConsentPreferenceModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of consent preferences
	 * @param end the upper bound of the range of consent preferences (not inclusive)
	 * @return the range of matching consent preferences
	 */
	@Override
	public List<ConsentPreference> findByExpirationDate(
		Date expirationDate, int start, int end) {

		return findByExpirationDate(expirationDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the consent preferences where expirationDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ConsentPreferenceModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of consent preferences
	 * @param end the upper bound of the range of consent preferences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching consent preferences
	 */
	@Override
	public List<ConsentPreference> findByExpirationDate(
		Date expirationDate, int start, int end,
		OrderByComparator<ConsentPreference> orderByComparator) {

		return findByExpirationDate(
			expirationDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the consent preferences where expirationDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ConsentPreferenceModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of consent preferences
	 * @param end the upper bound of the range of consent preferences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching consent preferences
	 */
	@Override
	public List<ConsentPreference> findByExpirationDate(
		Date expirationDate, int start, int end,
		OrderByComparator<ConsentPreference> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByExpirationDate;
				finderArgs = new Object[] {_getTime(expirationDate)};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByExpirationDate;
			finderArgs = new Object[] {
				_getTime(expirationDate), start, end, orderByComparator
			};
		}

		List<ConsentPreference> list = null;

		if (useFinderCache) {
			list = (List<ConsentPreference>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ConsentPreference consentPreference : list) {
					if (!Objects.equals(
							expirationDate,
							consentPreference.getExpirationDate())) {

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

			sb.append(_SQL_SELECT_CONSENTPREFERENCE_WHERE);

			boolean bindExpirationDate = false;

			if (expirationDate == null) {
				sb.append(_FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_1);
			}
			else {
				bindExpirationDate = true;

				sb.append(_FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ConsentPreferenceModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindExpirationDate) {
					queryPos.add(new Timestamp(expirationDate.getTime()));
				}

				list = (List<ConsentPreference>)QueryUtil.list(
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
	 * Returns the first consent preference in the ordered set where expirationDate = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching consent preference
	 * @throws NoSuchConsentPreferenceException if a matching consent preference could not be found
	 */
	@Override
	public ConsentPreference findByExpirationDate_First(
			Date expirationDate,
			OrderByComparator<ConsentPreference> orderByComparator)
		throws NoSuchConsentPreferenceException {

		ConsentPreference consentPreference = fetchByExpirationDate_First(
			expirationDate, orderByComparator);

		if (consentPreference != null) {
			return consentPreference;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("expirationDate=");
		sb.append(expirationDate);

		sb.append("}");

		throw new NoSuchConsentPreferenceException(sb.toString());
	}

	/**
	 * Returns the first consent preference in the ordered set where expirationDate = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching consent preference, or <code>null</code> if a matching consent preference could not be found
	 */
	@Override
	public ConsentPreference fetchByExpirationDate_First(
		Date expirationDate,
		OrderByComparator<ConsentPreference> orderByComparator) {

		List<ConsentPreference> list = findByExpirationDate(
			expirationDate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last consent preference in the ordered set where expirationDate = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching consent preference
	 * @throws NoSuchConsentPreferenceException if a matching consent preference could not be found
	 */
	@Override
	public ConsentPreference findByExpirationDate_Last(
			Date expirationDate,
			OrderByComparator<ConsentPreference> orderByComparator)
		throws NoSuchConsentPreferenceException {

		ConsentPreference consentPreference = fetchByExpirationDate_Last(
			expirationDate, orderByComparator);

		if (consentPreference != null) {
			return consentPreference;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("expirationDate=");
		sb.append(expirationDate);

		sb.append("}");

		throw new NoSuchConsentPreferenceException(sb.toString());
	}

	/**
	 * Returns the last consent preference in the ordered set where expirationDate = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching consent preference, or <code>null</code> if a matching consent preference could not be found
	 */
	@Override
	public ConsentPreference fetchByExpirationDate_Last(
		Date expirationDate,
		OrderByComparator<ConsentPreference> orderByComparator) {

		int count = countByExpirationDate(expirationDate);

		if (count == 0) {
			return null;
		}

		List<ConsentPreference> list = findByExpirationDate(
			expirationDate, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the consent preferences where expirationDate = &#63; from the database.
	 *
	 * @param expirationDate the expiration date
	 */
	@Override
	public void removeByExpirationDate(Date expirationDate) {
		for (ConsentPreference consentPreference :
				findByExpirationDate(
					expirationDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(consentPreference);
		}
	}

	/**
	 * Returns the number of consent preferences where expirationDate = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @return the number of matching consent preferences
	 */
	@Override
	public int countByExpirationDate(Date expirationDate) {
		FinderPath finderPath = _finderPathCountByExpirationDate;

		Object[] finderArgs = new Object[] {_getTime(expirationDate)};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CONSENTPREFERENCE_WHERE);

			boolean bindExpirationDate = false;

			if (expirationDate == null) {
				sb.append(_FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_1);
			}
			else {
				bindExpirationDate = true;

				sb.append(_FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindExpirationDate) {
					queryPos.add(new Timestamp(expirationDate.getTime()));
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

	private static final String _FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_1 =
		"consentPreference.expirationDate IS NULL";

	private static final String _FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_2 =
		"consentPreference.expirationDate = ?";

	private FinderPath _finderPathWithPaginationFindByU_D;
	private FinderPath _finderPathWithoutPaginationFindByU_D;
	private FinderPath _finderPathCountByU_D;

	/**
	 * Returns all the consent preferences where userId = &#63; and domain = &#63;.
	 *
	 * @param userId the user ID
	 * @param domain the domain
	 * @return the matching consent preferences
	 */
	@Override
	public List<ConsentPreference> findByU_D(long userId, String domain) {
		return findByU_D(
			userId, domain, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the consent preferences where userId = &#63; and domain = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ConsentPreferenceModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param domain the domain
	 * @param start the lower bound of the range of consent preferences
	 * @param end the upper bound of the range of consent preferences (not inclusive)
	 * @return the range of matching consent preferences
	 */
	@Override
	public List<ConsentPreference> findByU_D(
		long userId, String domain, int start, int end) {

		return findByU_D(userId, domain, start, end, null);
	}

	/**
	 * Returns an ordered range of all the consent preferences where userId = &#63; and domain = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ConsentPreferenceModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param domain the domain
	 * @param start the lower bound of the range of consent preferences
	 * @param end the upper bound of the range of consent preferences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching consent preferences
	 */
	@Override
	public List<ConsentPreference> findByU_D(
		long userId, String domain, int start, int end,
		OrderByComparator<ConsentPreference> orderByComparator) {

		return findByU_D(userId, domain, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the consent preferences where userId = &#63; and domain = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ConsentPreferenceModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param domain the domain
	 * @param start the lower bound of the range of consent preferences
	 * @param end the upper bound of the range of consent preferences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching consent preferences
	 */
	@Override
	public List<ConsentPreference> findByU_D(
		long userId, String domain, int start, int end,
		OrderByComparator<ConsentPreference> orderByComparator,
		boolean useFinderCache) {

		domain = Objects.toString(domain, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByU_D;
				finderArgs = new Object[] {userId, domain};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByU_D;
			finderArgs = new Object[] {
				userId, domain, start, end, orderByComparator
			};
		}

		List<ConsentPreference> list = null;

		if (useFinderCache) {
			list = (List<ConsentPreference>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ConsentPreference consentPreference : list) {
					if ((userId != consentPreference.getUserId()) ||
						!domain.equals(consentPreference.getDomain())) {

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

			sb.append(_SQL_SELECT_CONSENTPREFERENCE_WHERE);

			sb.append(_FINDER_COLUMN_U_D_USERID_2);

			boolean bindDomain = false;

			if (domain.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_D_DOMAIN_3);
			}
			else {
				bindDomain = true;

				sb.append(_FINDER_COLUMN_U_D_DOMAIN_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ConsentPreferenceModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				if (bindDomain) {
					queryPos.add(domain);
				}

				list = (List<ConsentPreference>)QueryUtil.list(
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
	 * Returns the first consent preference in the ordered set where userId = &#63; and domain = &#63;.
	 *
	 * @param userId the user ID
	 * @param domain the domain
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching consent preference
	 * @throws NoSuchConsentPreferenceException if a matching consent preference could not be found
	 */
	@Override
	public ConsentPreference findByU_D_First(
			long userId, String domain,
			OrderByComparator<ConsentPreference> orderByComparator)
		throws NoSuchConsentPreferenceException {

		ConsentPreference consentPreference = fetchByU_D_First(
			userId, domain, orderByComparator);

		if (consentPreference != null) {
			return consentPreference;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", domain=");
		sb.append(domain);

		sb.append("}");

		throw new NoSuchConsentPreferenceException(sb.toString());
	}

	/**
	 * Returns the first consent preference in the ordered set where userId = &#63; and domain = &#63;.
	 *
	 * @param userId the user ID
	 * @param domain the domain
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching consent preference, or <code>null</code> if a matching consent preference could not be found
	 */
	@Override
	public ConsentPreference fetchByU_D_First(
		long userId, String domain,
		OrderByComparator<ConsentPreference> orderByComparator) {

		List<ConsentPreference> list = findByU_D(
			userId, domain, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last consent preference in the ordered set where userId = &#63; and domain = &#63;.
	 *
	 * @param userId the user ID
	 * @param domain the domain
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching consent preference
	 * @throws NoSuchConsentPreferenceException if a matching consent preference could not be found
	 */
	@Override
	public ConsentPreference findByU_D_Last(
			long userId, String domain,
			OrderByComparator<ConsentPreference> orderByComparator)
		throws NoSuchConsentPreferenceException {

		ConsentPreference consentPreference = fetchByU_D_Last(
			userId, domain, orderByComparator);

		if (consentPreference != null) {
			return consentPreference;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", domain=");
		sb.append(domain);

		sb.append("}");

		throw new NoSuchConsentPreferenceException(sb.toString());
	}

	/**
	 * Returns the last consent preference in the ordered set where userId = &#63; and domain = &#63;.
	 *
	 * @param userId the user ID
	 * @param domain the domain
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching consent preference, or <code>null</code> if a matching consent preference could not be found
	 */
	@Override
	public ConsentPreference fetchByU_D_Last(
		long userId, String domain,
		OrderByComparator<ConsentPreference> orderByComparator) {

		int count = countByU_D(userId, domain);

		if (count == 0) {
			return null;
		}

		List<ConsentPreference> list = findByU_D(
			userId, domain, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the consent preferences where userId = &#63; and domain = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param domain the domain
	 */
	@Override
	public void removeByU_D(long userId, String domain) {
		for (ConsentPreference consentPreference :
				findByU_D(
					userId, domain, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(consentPreference);
		}
	}

	/**
	 * Returns the number of consent preferences where userId = &#63; and domain = &#63;.
	 *
	 * @param userId the user ID
	 * @param domain the domain
	 * @return the number of matching consent preferences
	 */
	@Override
	public int countByU_D(long userId, String domain) {
		domain = Objects.toString(domain, "");

		FinderPath finderPath = _finderPathCountByU_D;

		Object[] finderArgs = new Object[] {userId, domain};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CONSENTPREFERENCE_WHERE);

			sb.append(_FINDER_COLUMN_U_D_USERID_2);

			boolean bindDomain = false;

			if (domain.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_D_DOMAIN_3);
			}
			else {
				bindDomain = true;

				sb.append(_FINDER_COLUMN_U_D_DOMAIN_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				if (bindDomain) {
					queryPos.add(domain);
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

	private static final String _FINDER_COLUMN_U_D_USERID_2 =
		"consentPreference.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_D_DOMAIN_2 =
		"consentPreference.domain = ?";

	private static final String _FINDER_COLUMN_U_D_DOMAIN_3 =
		"(consentPreference.domain IS NULL OR consentPreference.domain = '')";

	private FinderPath _finderPathFetchByU_D_N;

	/**
	 * Returns the consent preference where userId = &#63; and domain = &#63; and name = &#63; or throws a <code>NoSuchConsentPreferenceException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param domain the domain
	 * @param name the name
	 * @return the matching consent preference
	 * @throws NoSuchConsentPreferenceException if a matching consent preference could not be found
	 */
	@Override
	public ConsentPreference findByU_D_N(
			long userId, String domain, String name)
		throws NoSuchConsentPreferenceException {

		ConsentPreference consentPreference = fetchByU_D_N(
			userId, domain, name);

		if (consentPreference == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("userId=");
			sb.append(userId);

			sb.append(", domain=");
			sb.append(domain);

			sb.append(", name=");
			sb.append(name);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchConsentPreferenceException(sb.toString());
		}

		return consentPreference;
	}

	/**
	 * Returns the consent preference where userId = &#63; and domain = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param domain the domain
	 * @param name the name
	 * @return the matching consent preference, or <code>null</code> if a matching consent preference could not be found
	 */
	@Override
	public ConsentPreference fetchByU_D_N(
		long userId, String domain, String name) {

		return fetchByU_D_N(userId, domain, name, true);
	}

	/**
	 * Returns the consent preference where userId = &#63; and domain = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param domain the domain
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching consent preference, or <code>null</code> if a matching consent preference could not be found
	 */
	@Override
	public ConsentPreference fetchByU_D_N(
		long userId, String domain, String name, boolean useFinderCache) {

		domain = Objects.toString(domain, "");
		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {userId, domain, name};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByU_D_N, finderArgs, this);
		}

		if (result instanceof ConsentPreference) {
			ConsentPreference consentPreference = (ConsentPreference)result;

			if ((userId != consentPreference.getUserId()) ||
				!Objects.equals(domain, consentPreference.getDomain()) ||
				!Objects.equals(name, consentPreference.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_CONSENTPREFERENCE_WHERE);

			sb.append(_FINDER_COLUMN_U_D_N_USERID_2);

			boolean bindDomain = false;

			if (domain.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_D_N_DOMAIN_3);
			}
			else {
				bindDomain = true;

				sb.append(_FINDER_COLUMN_U_D_N_DOMAIN_2);
			}

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_D_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_U_D_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				if (bindDomain) {
					queryPos.add(domain);
				}

				if (bindName) {
					queryPos.add(name);
				}

				List<ConsentPreference> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByU_D_N, finderArgs, list);
					}
				}
				else {
					ConsentPreference consentPreference = list.get(0);

					result = consentPreference;

					cacheResult(consentPreference);
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
			return (ConsentPreference)result;
		}
	}

	/**
	 * Removes the consent preference where userId = &#63; and domain = &#63; and name = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param domain the domain
	 * @param name the name
	 * @return the consent preference that was removed
	 */
	@Override
	public ConsentPreference removeByU_D_N(
			long userId, String domain, String name)
		throws NoSuchConsentPreferenceException {

		ConsentPreference consentPreference = findByU_D_N(userId, domain, name);

		return remove(consentPreference);
	}

	/**
	 * Returns the number of consent preferences where userId = &#63; and domain = &#63; and name = &#63;.
	 *
	 * @param userId the user ID
	 * @param domain the domain
	 * @param name the name
	 * @return the number of matching consent preferences
	 */
	@Override
	public int countByU_D_N(long userId, String domain, String name) {
		ConsentPreference consentPreference = fetchByU_D_N(
			userId, domain, name);

		if (consentPreference == null) {
			return 0;
		}

		return 1;
	}

	private static final String _FINDER_COLUMN_U_D_N_USERID_2 =
		"consentPreference.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_D_N_DOMAIN_2 =
		"consentPreference.domain = ? AND ";

	private static final String _FINDER_COLUMN_U_D_N_DOMAIN_3 =
		"(consentPreference.domain IS NULL OR consentPreference.domain = '') AND ";

	private static final String _FINDER_COLUMN_U_D_N_NAME_2 =
		"consentPreference.name = ?";

	private static final String _FINDER_COLUMN_U_D_N_NAME_3 =
		"(consentPreference.name IS NULL OR consentPreference.name = '')";

	public ConsentPreferencePersistenceImpl() {
		setModelClass(ConsentPreference.class);

		setModelImplClass(ConsentPreferenceImpl.class);
		setModelPKClass(long.class);

		setTable(ConsentPreferenceTable.INSTANCE);
	}

	/**
	 * Caches the consent preference in the entity cache if it is enabled.
	 *
	 * @param consentPreference the consent preference
	 */
	@Override
	public void cacheResult(ConsentPreference consentPreference) {
		entityCache.putResult(
			ConsentPreferenceImpl.class, consentPreference.getPrimaryKey(),
			consentPreference);

		finderCache.putResult(
			_finderPathFetchByU_D_N,
			new Object[] {
				consentPreference.getUserId(), consentPreference.getDomain(),
				consentPreference.getName()
			},
			consentPreference);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the consent preferences in the entity cache if it is enabled.
	 *
	 * @param consentPreferences the consent preferences
	 */
	@Override
	public void cacheResult(List<ConsentPreference> consentPreferences) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (consentPreferences.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ConsentPreference consentPreference : consentPreferences) {
			if (entityCache.getResult(
					ConsentPreferenceImpl.class,
					consentPreference.getPrimaryKey()) == null) {

				cacheResult(consentPreference);
			}
		}
	}

	/**
	 * Clears the cache for all consent preferences.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ConsentPreferenceImpl.class);

		finderCache.clearCache(ConsentPreferenceImpl.class);
	}

	/**
	 * Clears the cache for the consent preference.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ConsentPreference consentPreference) {
		entityCache.removeResult(
			ConsentPreferenceImpl.class, consentPreference);
	}

	@Override
	public void clearCache(List<ConsentPreference> consentPreferences) {
		for (ConsentPreference consentPreference : consentPreferences) {
			entityCache.removeResult(
				ConsentPreferenceImpl.class, consentPreference);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ConsentPreferenceImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(ConsentPreferenceImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ConsentPreferenceModelImpl consentPreferenceModelImpl) {

		Object[] args = new Object[] {
			consentPreferenceModelImpl.getUserId(),
			consentPreferenceModelImpl.getDomain(),
			consentPreferenceModelImpl.getName()
		};

		finderCache.putResult(
			_finderPathFetchByU_D_N, args, consentPreferenceModelImpl);
	}

	/**
	 * Creates a new consent preference with the primary key. Does not add the consent preference to the database.
	 *
	 * @param consentPreferenceId the primary key for the new consent preference
	 * @return the new consent preference
	 */
	@Override
	public ConsentPreference create(long consentPreferenceId) {
		ConsentPreference consentPreference = new ConsentPreferenceImpl();

		consentPreference.setNew(true);
		consentPreference.setPrimaryKey(consentPreferenceId);

		consentPreference.setCompanyId(CompanyThreadLocal.getCompanyId());

		return consentPreference;
	}

	/**
	 * Removes the consent preference with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param consentPreferenceId the primary key of the consent preference
	 * @return the consent preference that was removed
	 * @throws NoSuchConsentPreferenceException if a consent preference with the primary key could not be found
	 */
	@Override
	public ConsentPreference remove(long consentPreferenceId)
		throws NoSuchConsentPreferenceException {

		return remove((Serializable)consentPreferenceId);
	}

	/**
	 * Removes the consent preference with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the consent preference
	 * @return the consent preference that was removed
	 * @throws NoSuchConsentPreferenceException if a consent preference with the primary key could not be found
	 */
	@Override
	public ConsentPreference remove(Serializable primaryKey)
		throws NoSuchConsentPreferenceException {

		Session session = null;

		try {
			session = openSession();

			ConsentPreference consentPreference =
				(ConsentPreference)session.get(
					ConsentPreferenceImpl.class, primaryKey);

			if (consentPreference == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchConsentPreferenceException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(consentPreference);
		}
		catch (NoSuchConsentPreferenceException noSuchEntityException) {
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
	protected ConsentPreference removeImpl(
		ConsentPreference consentPreference) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(consentPreference)) {
				consentPreference = (ConsentPreference)session.get(
					ConsentPreferenceImpl.class,
					consentPreference.getPrimaryKeyObj());
			}

			if (consentPreference != null) {
				session.delete(consentPreference);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (consentPreference != null) {
			clearCache(consentPreference);
		}

		return consentPreference;
	}

	@Override
	public ConsentPreference updateImpl(ConsentPreference consentPreference) {
		boolean isNew = consentPreference.isNew();

		if (!(consentPreference instanceof ConsentPreferenceModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(consentPreference.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					consentPreference);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in consentPreference proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ConsentPreference implementation " +
					consentPreference.getClass());
		}

		ConsentPreferenceModelImpl consentPreferenceModelImpl =
			(ConsentPreferenceModelImpl)consentPreference;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(consentPreference);
			}
			else {
				consentPreference = (ConsentPreference)session.merge(
					consentPreference);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ConsentPreferenceImpl.class, consentPreferenceModelImpl, false,
			true);

		cacheUniqueFindersCache(consentPreferenceModelImpl);

		if (isNew) {
			consentPreference.setNew(false);
		}

		consentPreference.resetOriginalValues();

		return consentPreference;
	}

	/**
	 * Returns the consent preference with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the consent preference
	 * @return the consent preference
	 * @throws NoSuchConsentPreferenceException if a consent preference with the primary key could not be found
	 */
	@Override
	public ConsentPreference findByPrimaryKey(Serializable primaryKey)
		throws NoSuchConsentPreferenceException {

		ConsentPreference consentPreference = fetchByPrimaryKey(primaryKey);

		if (consentPreference == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchConsentPreferenceException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return consentPreference;
	}

	/**
	 * Returns the consent preference with the primary key or throws a <code>NoSuchConsentPreferenceException</code> if it could not be found.
	 *
	 * @param consentPreferenceId the primary key of the consent preference
	 * @return the consent preference
	 * @throws NoSuchConsentPreferenceException if a consent preference with the primary key could not be found
	 */
	@Override
	public ConsentPreference findByPrimaryKey(long consentPreferenceId)
		throws NoSuchConsentPreferenceException {

		return findByPrimaryKey((Serializable)consentPreferenceId);
	}

	/**
	 * Returns the consent preference with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param consentPreferenceId the primary key of the consent preference
	 * @return the consent preference, or <code>null</code> if a consent preference with the primary key could not be found
	 */
	@Override
	public ConsentPreference fetchByPrimaryKey(long consentPreferenceId) {
		return fetchByPrimaryKey((Serializable)consentPreferenceId);
	}

	/**
	 * Returns all the consent preferences.
	 *
	 * @return the consent preferences
	 */
	@Override
	public List<ConsentPreference> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the consent preferences.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ConsentPreferenceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of consent preferences
	 * @param end the upper bound of the range of consent preferences (not inclusive)
	 * @return the range of consent preferences
	 */
	@Override
	public List<ConsentPreference> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the consent preferences.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ConsentPreferenceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of consent preferences
	 * @param end the upper bound of the range of consent preferences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of consent preferences
	 */
	@Override
	public List<ConsentPreference> findAll(
		int start, int end,
		OrderByComparator<ConsentPreference> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the consent preferences.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ConsentPreferenceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of consent preferences
	 * @param end the upper bound of the range of consent preferences (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of consent preferences
	 */
	@Override
	public List<ConsentPreference> findAll(
		int start, int end,
		OrderByComparator<ConsentPreference> orderByComparator,
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

		List<ConsentPreference> list = null;

		if (useFinderCache) {
			list = (List<ConsentPreference>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CONSENTPREFERENCE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CONSENTPREFERENCE;

				sql = sql.concat(ConsentPreferenceModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ConsentPreference>)QueryUtil.list(
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
	 * Removes all the consent preferences from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ConsentPreference consentPreference : findAll()) {
			remove(consentPreference);
		}
	}

	/**
	 * Returns the number of consent preferences.
	 *
	 * @return the number of consent preferences
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_CONSENTPREFERENCE);

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
		return "consentPreferenceId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CONSENTPREFERENCE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ConsentPreferenceModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the consent preference persistence.
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

		_finderPathWithPaginationFindByExpirationDate = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByExpirationDate",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"expirationDate"}, true);

		_finderPathWithoutPaginationFindByExpirationDate = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByExpirationDate",
			new String[] {Date.class.getName()},
			new String[] {"expirationDate"}, true);

		_finderPathCountByExpirationDate = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByExpirationDate",
			new String[] {Date.class.getName()},
			new String[] {"expirationDate"}, false);

		_finderPathWithPaginationFindByU_D = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_D",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"userId", "domain"}, true);

		_finderPathWithoutPaginationFindByU_D = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_D",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"userId", "domain"}, true);

		_finderPathCountByU_D = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_D",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"userId", "domain"}, false);

		_finderPathFetchByU_D_N = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByU_D_N",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"userId", "domain", "name"}, true);

		ConsentPreferenceUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		ConsentPreferenceUtil.setPersistence(null);

		entityCache.removeCache(ConsentPreferenceImpl.class.getName());
	}

	@Override
	@Reference(
		target = CookiesPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = CookiesPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = CookiesPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_CONSENTPREFERENCE =
		"SELECT consentPreference FROM ConsentPreference consentPreference";

	private static final String _SQL_SELECT_CONSENTPREFERENCE_WHERE =
		"SELECT consentPreference FROM ConsentPreference consentPreference WHERE ";

	private static final String _SQL_COUNT_CONSENTPREFERENCE =
		"SELECT COUNT(consentPreference) FROM ConsentPreference consentPreference";

	private static final String _SQL_COUNT_CONSENTPREFERENCE_WHERE =
		"SELECT COUNT(consentPreference) FROM ConsentPreference consentPreference WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "consentPreference.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ConsentPreference exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ConsentPreference exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ConsentPreferencePersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1552982916