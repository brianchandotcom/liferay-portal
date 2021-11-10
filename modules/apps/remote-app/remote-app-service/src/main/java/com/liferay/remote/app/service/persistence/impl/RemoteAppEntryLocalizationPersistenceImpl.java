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

package com.liferay.remote.app.service.persistence.impl;

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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.remote.app.exception.NoSuchRemoteAppEntryLocalizationException;
import com.liferay.remote.app.model.RemoteAppEntryLocalization;
import com.liferay.remote.app.model.RemoteAppEntryLocalizationTable;
import com.liferay.remote.app.model.impl.RemoteAppEntryLocalizationImpl;
import com.liferay.remote.app.model.impl.RemoteAppEntryLocalizationModelImpl;
import com.liferay.remote.app.service.persistence.RemoteAppEntryLocalizationPersistence;
import com.liferay.remote.app.service.persistence.RemoteAppEntryLocalizationUtil;
import com.liferay.remote.app.service.persistence.impl.constants.RemoteAppPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

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
 * The persistence implementation for the remote app entry localization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	service = {
		RemoteAppEntryLocalizationPersistence.class, BasePersistence.class
	}
)
public class RemoteAppEntryLocalizationPersistenceImpl
	extends BasePersistenceImpl<RemoteAppEntryLocalization>
	implements RemoteAppEntryLocalizationPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>RemoteAppEntryLocalizationUtil</code> to access the remote app entry localization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		RemoteAppEntryLocalizationImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByRemoteAppEntryId;
	private FinderPath _finderPathWithoutPaginationFindByRemoteAppEntryId;
	private FinderPath _finderPathCountByRemoteAppEntryId;

	/**
	 * Returns all the remote app entry localizations where remoteAppEntryId = &#63;.
	 *
	 * @param remoteAppEntryId the remote app entry ID
	 * @return the matching remote app entry localizations
	 */
	@Override
	public List<RemoteAppEntryLocalization> findByRemoteAppEntryId(
		long remoteAppEntryId) {

		return findByRemoteAppEntryId(
			remoteAppEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the remote app entry localizations where remoteAppEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param remoteAppEntryId the remote app entry ID
	 * @param start the lower bound of the range of remote app entry localizations
	 * @param end the upper bound of the range of remote app entry localizations (not inclusive)
	 * @return the range of matching remote app entry localizations
	 */
	@Override
	public List<RemoteAppEntryLocalization> findByRemoteAppEntryId(
		long remoteAppEntryId, int start, int end) {

		return findByRemoteAppEntryId(remoteAppEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the remote app entry localizations where remoteAppEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param remoteAppEntryId the remote app entry ID
	 * @param start the lower bound of the range of remote app entry localizations
	 * @param end the upper bound of the range of remote app entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching remote app entry localizations
	 */
	@Override
	public List<RemoteAppEntryLocalization> findByRemoteAppEntryId(
		long remoteAppEntryId, int start, int end,
		OrderByComparator<RemoteAppEntryLocalization> orderByComparator) {

		return findByRemoteAppEntryId(
			remoteAppEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the remote app entry localizations where remoteAppEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param remoteAppEntryId the remote app entry ID
	 * @param start the lower bound of the range of remote app entry localizations
	 * @param end the upper bound of the range of remote app entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching remote app entry localizations
	 */
	@Override
	public List<RemoteAppEntryLocalization> findByRemoteAppEntryId(
		long remoteAppEntryId, int start, int end,
		OrderByComparator<RemoteAppEntryLocalization> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByRemoteAppEntryId;
				finderArgs = new Object[] {remoteAppEntryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByRemoteAppEntryId;
			finderArgs = new Object[] {
				remoteAppEntryId, start, end, orderByComparator
			};
		}

		List<RemoteAppEntryLocalization> list = null;

		if (useFinderCache) {
			list = (List<RemoteAppEntryLocalization>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (RemoteAppEntryLocalization remoteAppEntryLocalization :
						list) {

					if (remoteAppEntryId !=
							remoteAppEntryLocalization.getRemoteAppEntryId()) {

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

			sb.append(_SQL_SELECT_REMOTEAPPENTRYLOCALIZATION_WHERE);

			sb.append(_FINDER_COLUMN_REMOTEAPPENTRYID_REMOTEAPPENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(RemoteAppEntryLocalizationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(remoteAppEntryId);

				list = (List<RemoteAppEntryLocalization>)QueryUtil.list(
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
	 * Returns the first remote app entry localization in the ordered set where remoteAppEntryId = &#63;.
	 *
	 * @param remoteAppEntryId the remote app entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote app entry localization
	 * @throws NoSuchRemoteAppEntryLocalizationException if a matching remote app entry localization could not be found
	 */
	@Override
	public RemoteAppEntryLocalization findByRemoteAppEntryId_First(
			long remoteAppEntryId,
			OrderByComparator<RemoteAppEntryLocalization> orderByComparator)
		throws NoSuchRemoteAppEntryLocalizationException {

		RemoteAppEntryLocalization remoteAppEntryLocalization =
			fetchByRemoteAppEntryId_First(remoteAppEntryId, orderByComparator);

		if (remoteAppEntryLocalization != null) {
			return remoteAppEntryLocalization;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("remoteAppEntryId=");
		sb.append(remoteAppEntryId);

		sb.append("}");

		throw new NoSuchRemoteAppEntryLocalizationException(sb.toString());
	}

	/**
	 * Returns the first remote app entry localization in the ordered set where remoteAppEntryId = &#63;.
	 *
	 * @param remoteAppEntryId the remote app entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching remote app entry localization, or <code>null</code> if a matching remote app entry localization could not be found
	 */
	@Override
	public RemoteAppEntryLocalization fetchByRemoteAppEntryId_First(
		long remoteAppEntryId,
		OrderByComparator<RemoteAppEntryLocalization> orderByComparator) {

		List<RemoteAppEntryLocalization> list = findByRemoteAppEntryId(
			remoteAppEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last remote app entry localization in the ordered set where remoteAppEntryId = &#63;.
	 *
	 * @param remoteAppEntryId the remote app entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote app entry localization
	 * @throws NoSuchRemoteAppEntryLocalizationException if a matching remote app entry localization could not be found
	 */
	@Override
	public RemoteAppEntryLocalization findByRemoteAppEntryId_Last(
			long remoteAppEntryId,
			OrderByComparator<RemoteAppEntryLocalization> orderByComparator)
		throws NoSuchRemoteAppEntryLocalizationException {

		RemoteAppEntryLocalization remoteAppEntryLocalization =
			fetchByRemoteAppEntryId_Last(remoteAppEntryId, orderByComparator);

		if (remoteAppEntryLocalization != null) {
			return remoteAppEntryLocalization;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("remoteAppEntryId=");
		sb.append(remoteAppEntryId);

		sb.append("}");

		throw new NoSuchRemoteAppEntryLocalizationException(sb.toString());
	}

	/**
	 * Returns the last remote app entry localization in the ordered set where remoteAppEntryId = &#63;.
	 *
	 * @param remoteAppEntryId the remote app entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching remote app entry localization, or <code>null</code> if a matching remote app entry localization could not be found
	 */
	@Override
	public RemoteAppEntryLocalization fetchByRemoteAppEntryId_Last(
		long remoteAppEntryId,
		OrderByComparator<RemoteAppEntryLocalization> orderByComparator) {

		int count = countByRemoteAppEntryId(remoteAppEntryId);

		if (count == 0) {
			return null;
		}

		List<RemoteAppEntryLocalization> list = findByRemoteAppEntryId(
			remoteAppEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the remote app entry localizations before and after the current remote app entry localization in the ordered set where remoteAppEntryId = &#63;.
	 *
	 * @param remoteAppEntryLocalizationId the primary key of the current remote app entry localization
	 * @param remoteAppEntryId the remote app entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next remote app entry localization
	 * @throws NoSuchRemoteAppEntryLocalizationException if a remote app entry localization with the primary key could not be found
	 */
	@Override
	public RemoteAppEntryLocalization[] findByRemoteAppEntryId_PrevAndNext(
			long remoteAppEntryLocalizationId, long remoteAppEntryId,
			OrderByComparator<RemoteAppEntryLocalization> orderByComparator)
		throws NoSuchRemoteAppEntryLocalizationException {

		RemoteAppEntryLocalization remoteAppEntryLocalization =
			findByPrimaryKey(remoteAppEntryLocalizationId);

		Session session = null;

		try {
			session = openSession();

			RemoteAppEntryLocalization[] array =
				new RemoteAppEntryLocalizationImpl[3];

			array[0] = getByRemoteAppEntryId_PrevAndNext(
				session, remoteAppEntryLocalization, remoteAppEntryId,
				orderByComparator, true);

			array[1] = remoteAppEntryLocalization;

			array[2] = getByRemoteAppEntryId_PrevAndNext(
				session, remoteAppEntryLocalization, remoteAppEntryId,
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

	protected RemoteAppEntryLocalization getByRemoteAppEntryId_PrevAndNext(
		Session session, RemoteAppEntryLocalization remoteAppEntryLocalization,
		long remoteAppEntryId,
		OrderByComparator<RemoteAppEntryLocalization> orderByComparator,
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

		sb.append(_SQL_SELECT_REMOTEAPPENTRYLOCALIZATION_WHERE);

		sb.append(_FINDER_COLUMN_REMOTEAPPENTRYID_REMOTEAPPENTRYID_2);

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
			sb.append(RemoteAppEntryLocalizationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(remoteAppEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						remoteAppEntryLocalization)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<RemoteAppEntryLocalization> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the remote app entry localizations where remoteAppEntryId = &#63; from the database.
	 *
	 * @param remoteAppEntryId the remote app entry ID
	 */
	@Override
	public void removeByRemoteAppEntryId(long remoteAppEntryId) {
		for (RemoteAppEntryLocalization remoteAppEntryLocalization :
				findByRemoteAppEntryId(
					remoteAppEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(remoteAppEntryLocalization);
		}
	}

	/**
	 * Returns the number of remote app entry localizations where remoteAppEntryId = &#63;.
	 *
	 * @param remoteAppEntryId the remote app entry ID
	 * @return the number of matching remote app entry localizations
	 */
	@Override
	public int countByRemoteAppEntryId(long remoteAppEntryId) {
		FinderPath finderPath = _finderPathCountByRemoteAppEntryId;

		Object[] finderArgs = new Object[] {remoteAppEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_REMOTEAPPENTRYLOCALIZATION_WHERE);

			sb.append(_FINDER_COLUMN_REMOTEAPPENTRYID_REMOTEAPPENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(remoteAppEntryId);

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
		_FINDER_COLUMN_REMOTEAPPENTRYID_REMOTEAPPENTRYID_2 =
			"remoteAppEntryLocalization.remoteAppEntryId = ?";

	private FinderPath _finderPathFetchByRemoteAppEntryId_LanguageId;
	private FinderPath _finderPathCountByRemoteAppEntryId_LanguageId;

	/**
	 * Returns the remote app entry localization where remoteAppEntryId = &#63; and languageId = &#63; or throws a <code>NoSuchRemoteAppEntryLocalizationException</code> if it could not be found.
	 *
	 * @param remoteAppEntryId the remote app entry ID
	 * @param languageId the language ID
	 * @return the matching remote app entry localization
	 * @throws NoSuchRemoteAppEntryLocalizationException if a matching remote app entry localization could not be found
	 */
	@Override
	public RemoteAppEntryLocalization findByRemoteAppEntryId_LanguageId(
			long remoteAppEntryId, String languageId)
		throws NoSuchRemoteAppEntryLocalizationException {

		RemoteAppEntryLocalization remoteAppEntryLocalization =
			fetchByRemoteAppEntryId_LanguageId(remoteAppEntryId, languageId);

		if (remoteAppEntryLocalization == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("remoteAppEntryId=");
			sb.append(remoteAppEntryId);

			sb.append(", languageId=");
			sb.append(languageId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchRemoteAppEntryLocalizationException(sb.toString());
		}

		return remoteAppEntryLocalization;
	}

	/**
	 * Returns the remote app entry localization where remoteAppEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param remoteAppEntryId the remote app entry ID
	 * @param languageId the language ID
	 * @return the matching remote app entry localization, or <code>null</code> if a matching remote app entry localization could not be found
	 */
	@Override
	public RemoteAppEntryLocalization fetchByRemoteAppEntryId_LanguageId(
		long remoteAppEntryId, String languageId) {

		return fetchByRemoteAppEntryId_LanguageId(
			remoteAppEntryId, languageId, true);
	}

	/**
	 * Returns the remote app entry localization where remoteAppEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param remoteAppEntryId the remote app entry ID
	 * @param languageId the language ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching remote app entry localization, or <code>null</code> if a matching remote app entry localization could not be found
	 */
	@Override
	public RemoteAppEntryLocalization fetchByRemoteAppEntryId_LanguageId(
		long remoteAppEntryId, String languageId, boolean useFinderCache) {

		languageId = Objects.toString(languageId, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {remoteAppEntryId, languageId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByRemoteAppEntryId_LanguageId, finderArgs);
		}

		if (result instanceof RemoteAppEntryLocalization) {
			RemoteAppEntryLocalization remoteAppEntryLocalization =
				(RemoteAppEntryLocalization)result;

			if ((remoteAppEntryId !=
					remoteAppEntryLocalization.getRemoteAppEntryId()) ||
				!Objects.equals(
					languageId, remoteAppEntryLocalization.getLanguageId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_REMOTEAPPENTRYLOCALIZATION_WHERE);

			sb.append(
				_FINDER_COLUMN_REMOTEAPPENTRYID_LANGUAGEID_REMOTEAPPENTRYID_2);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(
					_FINDER_COLUMN_REMOTEAPPENTRYID_LANGUAGEID_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(
					_FINDER_COLUMN_REMOTEAPPENTRYID_LANGUAGEID_LANGUAGEID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(remoteAppEntryId);

				if (bindLanguageId) {
					queryPos.add(languageId);
				}

				List<RemoteAppEntryLocalization> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByRemoteAppEntryId_LanguageId,
							finderArgs, list);
					}
				}
				else {
					RemoteAppEntryLocalization remoteAppEntryLocalization =
						list.get(0);

					result = remoteAppEntryLocalization;

					cacheResult(remoteAppEntryLocalization);
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
			return (RemoteAppEntryLocalization)result;
		}
	}

	/**
	 * Removes the remote app entry localization where remoteAppEntryId = &#63; and languageId = &#63; from the database.
	 *
	 * @param remoteAppEntryId the remote app entry ID
	 * @param languageId the language ID
	 * @return the remote app entry localization that was removed
	 */
	@Override
	public RemoteAppEntryLocalization removeByRemoteAppEntryId_LanguageId(
			long remoteAppEntryId, String languageId)
		throws NoSuchRemoteAppEntryLocalizationException {

		RemoteAppEntryLocalization remoteAppEntryLocalization =
			findByRemoteAppEntryId_LanguageId(remoteAppEntryId, languageId);

		return remove(remoteAppEntryLocalization);
	}

	/**
	 * Returns the number of remote app entry localizations where remoteAppEntryId = &#63; and languageId = &#63;.
	 *
	 * @param remoteAppEntryId the remote app entry ID
	 * @param languageId the language ID
	 * @return the number of matching remote app entry localizations
	 */
	@Override
	public int countByRemoteAppEntryId_LanguageId(
		long remoteAppEntryId, String languageId) {

		languageId = Objects.toString(languageId, "");

		FinderPath finderPath = _finderPathCountByRemoteAppEntryId_LanguageId;

		Object[] finderArgs = new Object[] {remoteAppEntryId, languageId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_REMOTEAPPENTRYLOCALIZATION_WHERE);

			sb.append(
				_FINDER_COLUMN_REMOTEAPPENTRYID_LANGUAGEID_REMOTEAPPENTRYID_2);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(
					_FINDER_COLUMN_REMOTEAPPENTRYID_LANGUAGEID_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(
					_FINDER_COLUMN_REMOTEAPPENTRYID_LANGUAGEID_LANGUAGEID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(remoteAppEntryId);

				if (bindLanguageId) {
					queryPos.add(languageId);
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

	private static final String
		_FINDER_COLUMN_REMOTEAPPENTRYID_LANGUAGEID_REMOTEAPPENTRYID_2 =
			"remoteAppEntryLocalization.remoteAppEntryId = ? AND ";

	private static final String
		_FINDER_COLUMN_REMOTEAPPENTRYID_LANGUAGEID_LANGUAGEID_2 =
			"remoteAppEntryLocalization.languageId = ?";

	private static final String
		_FINDER_COLUMN_REMOTEAPPENTRYID_LANGUAGEID_LANGUAGEID_3 =
			"(remoteAppEntryLocalization.languageId IS NULL OR remoteAppEntryLocalization.languageId = '')";

	public RemoteAppEntryLocalizationPersistenceImpl() {
		setModelClass(RemoteAppEntryLocalization.class);

		setModelImplClass(RemoteAppEntryLocalizationImpl.class);
		setModelPKClass(long.class);

		setTable(RemoteAppEntryLocalizationTable.INSTANCE);
	}

	/**
	 * Caches the remote app entry localization in the entity cache if it is enabled.
	 *
	 * @param remoteAppEntryLocalization the remote app entry localization
	 */
	@Override
	public void cacheResult(
		RemoteAppEntryLocalization remoteAppEntryLocalization) {

		entityCache.putResult(
			RemoteAppEntryLocalizationImpl.class,
			remoteAppEntryLocalization.getPrimaryKey(),
			remoteAppEntryLocalization);

		finderCache.putResult(
			_finderPathFetchByRemoteAppEntryId_LanguageId,
			new Object[] {
				remoteAppEntryLocalization.getRemoteAppEntryId(),
				remoteAppEntryLocalization.getLanguageId()
			},
			remoteAppEntryLocalization);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the remote app entry localizations in the entity cache if it is enabled.
	 *
	 * @param remoteAppEntryLocalizations the remote app entry localizations
	 */
	@Override
	public void cacheResult(
		List<RemoteAppEntryLocalization> remoteAppEntryLocalizations) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (remoteAppEntryLocalizations.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (RemoteAppEntryLocalization remoteAppEntryLocalization :
				remoteAppEntryLocalizations) {

			if (entityCache.getResult(
					RemoteAppEntryLocalizationImpl.class,
					remoteAppEntryLocalization.getPrimaryKey()) == null) {

				cacheResult(remoteAppEntryLocalization);
			}
		}
	}

	/**
	 * Clears the cache for all remote app entry localizations.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(RemoteAppEntryLocalizationImpl.class);

		finderCache.clearCache(RemoteAppEntryLocalizationImpl.class);
	}

	/**
	 * Clears the cache for the remote app entry localization.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		RemoteAppEntryLocalization remoteAppEntryLocalization) {

		entityCache.removeResult(
			RemoteAppEntryLocalizationImpl.class, remoteAppEntryLocalization);
	}

	@Override
	public void clearCache(
		List<RemoteAppEntryLocalization> remoteAppEntryLocalizations) {

		for (RemoteAppEntryLocalization remoteAppEntryLocalization :
				remoteAppEntryLocalizations) {

			entityCache.removeResult(
				RemoteAppEntryLocalizationImpl.class,
				remoteAppEntryLocalization);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(RemoteAppEntryLocalizationImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				RemoteAppEntryLocalizationImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		RemoteAppEntryLocalizationModelImpl
			remoteAppEntryLocalizationModelImpl) {

		Object[] args = new Object[] {
			remoteAppEntryLocalizationModelImpl.getRemoteAppEntryId(),
			remoteAppEntryLocalizationModelImpl.getLanguageId()
		};

		finderCache.putResult(
			_finderPathCountByRemoteAppEntryId_LanguageId, args,
			Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByRemoteAppEntryId_LanguageId, args,
			remoteAppEntryLocalizationModelImpl);
	}

	/**
	 * Creates a new remote app entry localization with the primary key. Does not add the remote app entry localization to the database.
	 *
	 * @param remoteAppEntryLocalizationId the primary key for the new remote app entry localization
	 * @return the new remote app entry localization
	 */
	@Override
	public RemoteAppEntryLocalization create(
		long remoteAppEntryLocalizationId) {

		RemoteAppEntryLocalization remoteAppEntryLocalization =
			new RemoteAppEntryLocalizationImpl();

		remoteAppEntryLocalization.setNew(true);
		remoteAppEntryLocalization.setPrimaryKey(remoteAppEntryLocalizationId);

		remoteAppEntryLocalization.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return remoteAppEntryLocalization;
	}

	/**
	 * Removes the remote app entry localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param remoteAppEntryLocalizationId the primary key of the remote app entry localization
	 * @return the remote app entry localization that was removed
	 * @throws NoSuchRemoteAppEntryLocalizationException if a remote app entry localization with the primary key could not be found
	 */
	@Override
	public RemoteAppEntryLocalization remove(long remoteAppEntryLocalizationId)
		throws NoSuchRemoteAppEntryLocalizationException {

		return remove((Serializable)remoteAppEntryLocalizationId);
	}

	/**
	 * Removes the remote app entry localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the remote app entry localization
	 * @return the remote app entry localization that was removed
	 * @throws NoSuchRemoteAppEntryLocalizationException if a remote app entry localization with the primary key could not be found
	 */
	@Override
	public RemoteAppEntryLocalization remove(Serializable primaryKey)
		throws NoSuchRemoteAppEntryLocalizationException {

		Session session = null;

		try {
			session = openSession();

			RemoteAppEntryLocalization remoteAppEntryLocalization =
				(RemoteAppEntryLocalization)session.get(
					RemoteAppEntryLocalizationImpl.class, primaryKey);

			if (remoteAppEntryLocalization == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRemoteAppEntryLocalizationException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(remoteAppEntryLocalization);
		}
		catch (NoSuchRemoteAppEntryLocalizationException
					noSuchEntityException) {

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
	protected RemoteAppEntryLocalization removeImpl(
		RemoteAppEntryLocalization remoteAppEntryLocalization) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(remoteAppEntryLocalization)) {
				remoteAppEntryLocalization =
					(RemoteAppEntryLocalization)session.get(
						RemoteAppEntryLocalizationImpl.class,
						remoteAppEntryLocalization.getPrimaryKeyObj());
			}

			if (remoteAppEntryLocalization != null) {
				session.delete(remoteAppEntryLocalization);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (remoteAppEntryLocalization != null) {
			clearCache(remoteAppEntryLocalization);
		}

		return remoteAppEntryLocalization;
	}

	@Override
	public RemoteAppEntryLocalization updateImpl(
		RemoteAppEntryLocalization remoteAppEntryLocalization) {

		boolean isNew = remoteAppEntryLocalization.isNew();

		if (!(remoteAppEntryLocalization instanceof
				RemoteAppEntryLocalizationModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(remoteAppEntryLocalization.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					remoteAppEntryLocalization);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in remoteAppEntryLocalization proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom RemoteAppEntryLocalization implementation " +
					remoteAppEntryLocalization.getClass());
		}

		RemoteAppEntryLocalizationModelImpl
			remoteAppEntryLocalizationModelImpl =
				(RemoteAppEntryLocalizationModelImpl)remoteAppEntryLocalization;

		long userId = GetterUtil.getLong(PrincipalThreadLocal.getName());

		if (userId > 0) {
			long companyId = remoteAppEntryLocalization.getCompanyId();

			long groupId = 0;

			long remoteAppEntryLocalizationId = 0;

			if (!isNew) {
				remoteAppEntryLocalizationId =
					remoteAppEntryLocalization.getPrimaryKey();
			}

			try {
				remoteAppEntryLocalization.setDescription(
					SanitizerUtil.sanitize(
						companyId, groupId, userId,
						RemoteAppEntryLocalization.class.getName(),
						remoteAppEntryLocalizationId, ContentTypes.TEXT_HTML,
						Sanitizer.MODE_ALL,
						remoteAppEntryLocalization.getDescription(), null));
			}
			catch (SanitizerException sanitizerException) {
				throw new SystemException(sanitizerException);
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(remoteAppEntryLocalization);
			}
			else {
				remoteAppEntryLocalization =
					(RemoteAppEntryLocalization)session.merge(
						remoteAppEntryLocalization);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			RemoteAppEntryLocalizationImpl.class,
			remoteAppEntryLocalizationModelImpl, false, true);

		cacheUniqueFindersCache(remoteAppEntryLocalizationModelImpl);

		if (isNew) {
			remoteAppEntryLocalization.setNew(false);
		}

		remoteAppEntryLocalization.resetOriginalValues();

		return remoteAppEntryLocalization;
	}

	/**
	 * Returns the remote app entry localization with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the remote app entry localization
	 * @return the remote app entry localization
	 * @throws NoSuchRemoteAppEntryLocalizationException if a remote app entry localization with the primary key could not be found
	 */
	@Override
	public RemoteAppEntryLocalization findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRemoteAppEntryLocalizationException {

		RemoteAppEntryLocalization remoteAppEntryLocalization =
			fetchByPrimaryKey(primaryKey);

		if (remoteAppEntryLocalization == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRemoteAppEntryLocalizationException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return remoteAppEntryLocalization;
	}

	/**
	 * Returns the remote app entry localization with the primary key or throws a <code>NoSuchRemoteAppEntryLocalizationException</code> if it could not be found.
	 *
	 * @param remoteAppEntryLocalizationId the primary key of the remote app entry localization
	 * @return the remote app entry localization
	 * @throws NoSuchRemoteAppEntryLocalizationException if a remote app entry localization with the primary key could not be found
	 */
	@Override
	public RemoteAppEntryLocalization findByPrimaryKey(
			long remoteAppEntryLocalizationId)
		throws NoSuchRemoteAppEntryLocalizationException {

		return findByPrimaryKey((Serializable)remoteAppEntryLocalizationId);
	}

	/**
	 * Returns the remote app entry localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param remoteAppEntryLocalizationId the primary key of the remote app entry localization
	 * @return the remote app entry localization, or <code>null</code> if a remote app entry localization with the primary key could not be found
	 */
	@Override
	public RemoteAppEntryLocalization fetchByPrimaryKey(
		long remoteAppEntryLocalizationId) {

		return fetchByPrimaryKey((Serializable)remoteAppEntryLocalizationId);
	}

	/**
	 * Returns all the remote app entry localizations.
	 *
	 * @return the remote app entry localizations
	 */
	@Override
	public List<RemoteAppEntryLocalization> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the remote app entry localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote app entry localizations
	 * @param end the upper bound of the range of remote app entry localizations (not inclusive)
	 * @return the range of remote app entry localizations
	 */
	@Override
	public List<RemoteAppEntryLocalization> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the remote app entry localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote app entry localizations
	 * @param end the upper bound of the range of remote app entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of remote app entry localizations
	 */
	@Override
	public List<RemoteAppEntryLocalization> findAll(
		int start, int end,
		OrderByComparator<RemoteAppEntryLocalization> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the remote app entry localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RemoteAppEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of remote app entry localizations
	 * @param end the upper bound of the range of remote app entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of remote app entry localizations
	 */
	@Override
	public List<RemoteAppEntryLocalization> findAll(
		int start, int end,
		OrderByComparator<RemoteAppEntryLocalization> orderByComparator,
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

		List<RemoteAppEntryLocalization> list = null;

		if (useFinderCache) {
			list = (List<RemoteAppEntryLocalization>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_REMOTEAPPENTRYLOCALIZATION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_REMOTEAPPENTRYLOCALIZATION;

				sql = sql.concat(
					RemoteAppEntryLocalizationModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<RemoteAppEntryLocalization>)QueryUtil.list(
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
	 * Removes all the remote app entry localizations from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (RemoteAppEntryLocalization remoteAppEntryLocalization :
				findAll()) {

			remove(remoteAppEntryLocalization);
		}
	}

	/**
	 * Returns the number of remote app entry localizations.
	 *
	 * @return the number of remote app entry localizations
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
					_SQL_COUNT_REMOTEAPPENTRYLOCALIZATION);

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
		return "remoteAppEntryLocalizationId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_REMOTEAPPENTRYLOCALIZATION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return RemoteAppEntryLocalizationModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the remote app entry localization persistence.
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

		_finderPathWithPaginationFindByRemoteAppEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByRemoteAppEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"remoteAppEntryId"}, true);

		_finderPathWithoutPaginationFindByRemoteAppEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRemoteAppEntryId",
			new String[] {Long.class.getName()},
			new String[] {"remoteAppEntryId"}, true);

		_finderPathCountByRemoteAppEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByRemoteAppEntryId", new String[] {Long.class.getName()},
			new String[] {"remoteAppEntryId"}, false);

		_finderPathFetchByRemoteAppEntryId_LanguageId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByRemoteAppEntryId_LanguageId",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"remoteAppEntryId", "languageId"}, true);

		_finderPathCountByRemoteAppEntryId_LanguageId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByRemoteAppEntryId_LanguageId",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"remoteAppEntryId", "languageId"}, false);

		_setRemoteAppEntryLocalizationUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		_setRemoteAppEntryLocalizationUtilPersistence(null);

		entityCache.removeCache(RemoteAppEntryLocalizationImpl.class.getName());
	}

	private void _setRemoteAppEntryLocalizationUtilPersistence(
		RemoteAppEntryLocalizationPersistence
			remoteAppEntryLocalizationPersistence) {

		try {
			Field field = RemoteAppEntryLocalizationUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, remoteAppEntryLocalizationPersistence);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@Override
	@Reference(
		target = RemoteAppPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = RemoteAppPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = RemoteAppPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_REMOTEAPPENTRYLOCALIZATION =
		"SELECT remoteAppEntryLocalization FROM RemoteAppEntryLocalization remoteAppEntryLocalization";

	private static final String _SQL_SELECT_REMOTEAPPENTRYLOCALIZATION_WHERE =
		"SELECT remoteAppEntryLocalization FROM RemoteAppEntryLocalization remoteAppEntryLocalization WHERE ";

	private static final String _SQL_COUNT_REMOTEAPPENTRYLOCALIZATION =
		"SELECT COUNT(remoteAppEntryLocalization) FROM RemoteAppEntryLocalization remoteAppEntryLocalization";

	private static final String _SQL_COUNT_REMOTEAPPENTRYLOCALIZATION_WHERE =
		"SELECT COUNT(remoteAppEntryLocalization) FROM RemoteAppEntryLocalization remoteAppEntryLocalization WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"remoteAppEntryLocalization.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No RemoteAppEntryLocalization exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No RemoteAppEntryLocalization exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		RemoteAppEntryLocalizationPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private RemoteAppEntryLocalizationModelArgumentsResolver
		_remoteAppEntryLocalizationModelArgumentsResolver;

}