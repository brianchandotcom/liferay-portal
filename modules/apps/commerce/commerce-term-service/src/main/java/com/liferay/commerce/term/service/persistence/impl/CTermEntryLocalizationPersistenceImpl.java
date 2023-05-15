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

package com.liferay.commerce.term.service.persistence.impl;

import com.liferay.commerce.term.exception.NoSuchCTermEntryLocalizationException;
import com.liferay.commerce.term.model.CTermEntryLocalization;
import com.liferay.commerce.term.model.CTermEntryLocalizationTable;
import com.liferay.commerce.term.model.impl.CTermEntryLocalizationImpl;
import com.liferay.commerce.term.model.impl.CTermEntryLocalizationModelImpl;
import com.liferay.commerce.term.service.persistence.CTermEntryLocalizationPersistence;
import com.liferay.commerce.term.service.persistence.CTermEntryLocalizationUtil;
import com.liferay.commerce.term.service.persistence.impl.constants.CommercePersistenceConstants;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

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
 * The persistence implementation for the c term entry localization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @generated
 */
@Component(service = CTermEntryLocalizationPersistence.class)
public class CTermEntryLocalizationPersistenceImpl
	extends BasePersistenceImpl<CTermEntryLocalization>
	implements CTermEntryLocalizationPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CTermEntryLocalizationUtil</code> to access the c term entry localization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CTermEntryLocalizationImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCommerceTermEntryId;
	private FinderPath _finderPathWithoutPaginationFindByCommerceTermEntryId;
	private FinderPath _finderPathCountByCommerceTermEntryId;

	/**
	 * Returns all the c term entry localizations where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @return the matching c term entry localizations
	 */
	@Override
	public List<CTermEntryLocalization> findByCommerceTermEntryId(
		long commerceTermEntryId) {

		return findByCommerceTermEntryId(
			commerceTermEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the c term entry localizations where commerceTermEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTermEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param start the lower bound of the range of c term entry localizations
	 * @param end the upper bound of the range of c term entry localizations (not inclusive)
	 * @return the range of matching c term entry localizations
	 */
	@Override
	public List<CTermEntryLocalization> findByCommerceTermEntryId(
		long commerceTermEntryId, int start, int end) {

		return findByCommerceTermEntryId(commerceTermEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the c term entry localizations where commerceTermEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTermEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param start the lower bound of the range of c term entry localizations
	 * @param end the upper bound of the range of c term entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching c term entry localizations
	 */
	@Override
	public List<CTermEntryLocalization> findByCommerceTermEntryId(
		long commerceTermEntryId, int start, int end,
		OrderByComparator<CTermEntryLocalization> orderByComparator) {

		return findByCommerceTermEntryId(
			commerceTermEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the c term entry localizations where commerceTermEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTermEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param start the lower bound of the range of c term entry localizations
	 * @param end the upper bound of the range of c term entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching c term entry localizations
	 */
	@Override
	public List<CTermEntryLocalization> findByCommerceTermEntryId(
		long commerceTermEntryId, int start, int end,
		OrderByComparator<CTermEntryLocalization> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceTermEntryId;
				finderArgs = new Object[] {commerceTermEntryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCommerceTermEntryId;
			finderArgs = new Object[] {
				commerceTermEntryId, start, end, orderByComparator
			};
		}

		List<CTermEntryLocalization> list = null;

		if (useFinderCache) {
			list = (List<CTermEntryLocalization>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CTermEntryLocalization cTermEntryLocalization : list) {
					if (commerceTermEntryId !=
							cTermEntryLocalization.getCommerceTermEntryId()) {

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

			sb.append(_SQL_SELECT_CTERMENTRYLOCALIZATION_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCETERMENTRYID_COMMERCETERMENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CTermEntryLocalizationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceTermEntryId);

				list = (List<CTermEntryLocalization>)QueryUtil.list(
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
	 * Returns the first c term entry localization in the ordered set where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching c term entry localization
	 * @throws NoSuchCTermEntryLocalizationException if a matching c term entry localization could not be found
	 */
	@Override
	public CTermEntryLocalization findByCommerceTermEntryId_First(
			long commerceTermEntryId,
			OrderByComparator<CTermEntryLocalization> orderByComparator)
		throws NoSuchCTermEntryLocalizationException {

		CTermEntryLocalization cTermEntryLocalization =
			fetchByCommerceTermEntryId_First(
				commerceTermEntryId, orderByComparator);

		if (cTermEntryLocalization != null) {
			return cTermEntryLocalization;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceTermEntryId=");
		sb.append(commerceTermEntryId);

		sb.append("}");

		throw new NoSuchCTermEntryLocalizationException(sb.toString());
	}

	/**
	 * Returns the first c term entry localization in the ordered set where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching c term entry localization, or <code>null</code> if a matching c term entry localization could not be found
	 */
	@Override
	public CTermEntryLocalization fetchByCommerceTermEntryId_First(
		long commerceTermEntryId,
		OrderByComparator<CTermEntryLocalization> orderByComparator) {

		List<CTermEntryLocalization> list = findByCommerceTermEntryId(
			commerceTermEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last c term entry localization in the ordered set where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching c term entry localization
	 * @throws NoSuchCTermEntryLocalizationException if a matching c term entry localization could not be found
	 */
	@Override
	public CTermEntryLocalization findByCommerceTermEntryId_Last(
			long commerceTermEntryId,
			OrderByComparator<CTermEntryLocalization> orderByComparator)
		throws NoSuchCTermEntryLocalizationException {

		CTermEntryLocalization cTermEntryLocalization =
			fetchByCommerceTermEntryId_Last(
				commerceTermEntryId, orderByComparator);

		if (cTermEntryLocalization != null) {
			return cTermEntryLocalization;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceTermEntryId=");
		sb.append(commerceTermEntryId);

		sb.append("}");

		throw new NoSuchCTermEntryLocalizationException(sb.toString());
	}

	/**
	 * Returns the last c term entry localization in the ordered set where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching c term entry localization, or <code>null</code> if a matching c term entry localization could not be found
	 */
	@Override
	public CTermEntryLocalization fetchByCommerceTermEntryId_Last(
		long commerceTermEntryId,
		OrderByComparator<CTermEntryLocalization> orderByComparator) {

		int count = countByCommerceTermEntryId(commerceTermEntryId);

		if (count == 0) {
			return null;
		}

		List<CTermEntryLocalization> list = findByCommerceTermEntryId(
			commerceTermEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the c term entry localizations before and after the current c term entry localization in the ordered set where commerceTermEntryId = &#63;.
	 *
	 * @param cTermEntryLocalizationId the primary key of the current c term entry localization
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next c term entry localization
	 * @throws NoSuchCTermEntryLocalizationException if a c term entry localization with the primary key could not be found
	 */
	@Override
	public CTermEntryLocalization[] findByCommerceTermEntryId_PrevAndNext(
			long cTermEntryLocalizationId, long commerceTermEntryId,
			OrderByComparator<CTermEntryLocalization> orderByComparator)
		throws NoSuchCTermEntryLocalizationException {

		CTermEntryLocalization cTermEntryLocalization = findByPrimaryKey(
			cTermEntryLocalizationId);

		Session session = null;

		try {
			session = openSession();

			CTermEntryLocalization[] array = new CTermEntryLocalizationImpl[3];

			array[0] = getByCommerceTermEntryId_PrevAndNext(
				session, cTermEntryLocalization, commerceTermEntryId,
				orderByComparator, true);

			array[1] = cTermEntryLocalization;

			array[2] = getByCommerceTermEntryId_PrevAndNext(
				session, cTermEntryLocalization, commerceTermEntryId,
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

	protected CTermEntryLocalization getByCommerceTermEntryId_PrevAndNext(
		Session session, CTermEntryLocalization cTermEntryLocalization,
		long commerceTermEntryId,
		OrderByComparator<CTermEntryLocalization> orderByComparator,
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

		sb.append(_SQL_SELECT_CTERMENTRYLOCALIZATION_WHERE);

		sb.append(_FINDER_COLUMN_COMMERCETERMENTRYID_COMMERCETERMENTRYID_2);

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
			sb.append(CTermEntryLocalizationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceTermEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cTermEntryLocalization)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CTermEntryLocalization> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the c term entry localizations where commerceTermEntryId = &#63; from the database.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 */
	@Override
	public void removeByCommerceTermEntryId(long commerceTermEntryId) {
		for (CTermEntryLocalization cTermEntryLocalization :
				findByCommerceTermEntryId(
					commerceTermEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(cTermEntryLocalization);
		}
	}

	/**
	 * Returns the number of c term entry localizations where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @return the number of matching c term entry localizations
	 */
	@Override
	public int countByCommerceTermEntryId(long commerceTermEntryId) {
		FinderPath finderPath = _finderPathCountByCommerceTermEntryId;

		Object[] finderArgs = new Object[] {commerceTermEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CTERMENTRYLOCALIZATION_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCETERMENTRYID_COMMERCETERMENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceTermEntryId);

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
		_FINDER_COLUMN_COMMERCETERMENTRYID_COMMERCETERMENTRYID_2 =
			"cTermEntryLocalization.commerceTermEntryId = ?";

	private FinderPath _finderPathFetchByCommerceTermEntryId_LanguageId;
	private FinderPath _finderPathCountByCommerceTermEntryId_LanguageId;

	/**
	 * Returns the c term entry localization where commerceTermEntryId = &#63; and languageId = &#63; or throws a <code>NoSuchCTermEntryLocalizationException</code> if it could not be found.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param languageId the language ID
	 * @return the matching c term entry localization
	 * @throws NoSuchCTermEntryLocalizationException if a matching c term entry localization could not be found
	 */
	@Override
	public CTermEntryLocalization findByCommerceTermEntryId_LanguageId(
			long commerceTermEntryId, String languageId)
		throws NoSuchCTermEntryLocalizationException {

		CTermEntryLocalization cTermEntryLocalization =
			fetchByCommerceTermEntryId_LanguageId(
				commerceTermEntryId, languageId);

		if (cTermEntryLocalization == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("commerceTermEntryId=");
			sb.append(commerceTermEntryId);

			sb.append(", languageId=");
			sb.append(languageId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCTermEntryLocalizationException(sb.toString());
		}

		return cTermEntryLocalization;
	}

	/**
	 * Returns the c term entry localization where commerceTermEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param languageId the language ID
	 * @return the matching c term entry localization, or <code>null</code> if a matching c term entry localization could not be found
	 */
	@Override
	public CTermEntryLocalization fetchByCommerceTermEntryId_LanguageId(
		long commerceTermEntryId, String languageId) {

		return fetchByCommerceTermEntryId_LanguageId(
			commerceTermEntryId, languageId, true);
	}

	/**
	 * Returns the c term entry localization where commerceTermEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param languageId the language ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching c term entry localization, or <code>null</code> if a matching c term entry localization could not be found
	 */
	@Override
	public CTermEntryLocalization fetchByCommerceTermEntryId_LanguageId(
		long commerceTermEntryId, String languageId, boolean useFinderCache) {

		languageId = Objects.toString(languageId, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {commerceTermEntryId, languageId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByCommerceTermEntryId_LanguageId, finderArgs,
				this);
		}

		if (result instanceof CTermEntryLocalization) {
			CTermEntryLocalization cTermEntryLocalization =
				(CTermEntryLocalization)result;

			if ((commerceTermEntryId !=
					cTermEntryLocalization.getCommerceTermEntryId()) ||
				!Objects.equals(
					languageId, cTermEntryLocalization.getLanguageId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_CTERMENTRYLOCALIZATION_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCETERMENTRYID_LANGUAGEID_COMMERCETERMENTRYID_2);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(
					_FINDER_COLUMN_COMMERCETERMENTRYID_LANGUAGEID_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(
					_FINDER_COLUMN_COMMERCETERMENTRYID_LANGUAGEID_LANGUAGEID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceTermEntryId);

				if (bindLanguageId) {
					queryPos.add(languageId);
				}

				List<CTermEntryLocalization> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByCommerceTermEntryId_LanguageId,
							finderArgs, list);
					}
				}
				else {
					CTermEntryLocalization cTermEntryLocalization = list.get(0);

					result = cTermEntryLocalization;

					cacheResult(cTermEntryLocalization);
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
			return (CTermEntryLocalization)result;
		}
	}

	/**
	 * Removes the c term entry localization where commerceTermEntryId = &#63; and languageId = &#63; from the database.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param languageId the language ID
	 * @return the c term entry localization that was removed
	 */
	@Override
	public CTermEntryLocalization removeByCommerceTermEntryId_LanguageId(
			long commerceTermEntryId, String languageId)
		throws NoSuchCTermEntryLocalizationException {

		CTermEntryLocalization cTermEntryLocalization =
			findByCommerceTermEntryId_LanguageId(
				commerceTermEntryId, languageId);

		return remove(cTermEntryLocalization);
	}

	/**
	 * Returns the number of c term entry localizations where commerceTermEntryId = &#63; and languageId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param languageId the language ID
	 * @return the number of matching c term entry localizations
	 */
	@Override
	public int countByCommerceTermEntryId_LanguageId(
		long commerceTermEntryId, String languageId) {

		languageId = Objects.toString(languageId, "");

		FinderPath finderPath =
			_finderPathCountByCommerceTermEntryId_LanguageId;

		Object[] finderArgs = new Object[] {commerceTermEntryId, languageId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CTERMENTRYLOCALIZATION_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCETERMENTRYID_LANGUAGEID_COMMERCETERMENTRYID_2);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(
					_FINDER_COLUMN_COMMERCETERMENTRYID_LANGUAGEID_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(
					_FINDER_COLUMN_COMMERCETERMENTRYID_LANGUAGEID_LANGUAGEID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceTermEntryId);

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
		_FINDER_COLUMN_COMMERCETERMENTRYID_LANGUAGEID_COMMERCETERMENTRYID_2 =
			"cTermEntryLocalization.commerceTermEntryId = ? AND ";

	private static final String
		_FINDER_COLUMN_COMMERCETERMENTRYID_LANGUAGEID_LANGUAGEID_2 =
			"cTermEntryLocalization.languageId = ?";

	private static final String
		_FINDER_COLUMN_COMMERCETERMENTRYID_LANGUAGEID_LANGUAGEID_3 =
			"(cTermEntryLocalization.languageId IS NULL OR cTermEntryLocalization.languageId = '')";

	public CTermEntryLocalizationPersistenceImpl() {
		setModelClass(CTermEntryLocalization.class);

		setModelImplClass(CTermEntryLocalizationImpl.class);
		setModelPKClass(long.class);

		setTable(CTermEntryLocalizationTable.INSTANCE);
	}

	/**
	 * Caches the c term entry localization in the entity cache if it is enabled.
	 *
	 * @param cTermEntryLocalization the c term entry localization
	 */
	@Override
	public void cacheResult(CTermEntryLocalization cTermEntryLocalization) {
		entityCache.putResult(
			CTermEntryLocalizationImpl.class,
			cTermEntryLocalization.getPrimaryKey(), cTermEntryLocalization);

		finderCache.putResult(
			_finderPathFetchByCommerceTermEntryId_LanguageId,
			new Object[] {
				cTermEntryLocalization.getCommerceTermEntryId(),
				cTermEntryLocalization.getLanguageId()
			},
			cTermEntryLocalization);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the c term entry localizations in the entity cache if it is enabled.
	 *
	 * @param cTermEntryLocalizations the c term entry localizations
	 */
	@Override
	public void cacheResult(
		List<CTermEntryLocalization> cTermEntryLocalizations) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (cTermEntryLocalizations.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CTermEntryLocalization cTermEntryLocalization :
				cTermEntryLocalizations) {

			if (entityCache.getResult(
					CTermEntryLocalizationImpl.class,
					cTermEntryLocalization.getPrimaryKey()) == null) {

				cacheResult(cTermEntryLocalization);
			}
		}
	}

	/**
	 * Clears the cache for all c term entry localizations.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CTermEntryLocalizationImpl.class);

		finderCache.clearCache(CTermEntryLocalizationImpl.class);
	}

	/**
	 * Clears the cache for the c term entry localization.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CTermEntryLocalization cTermEntryLocalization) {
		entityCache.removeResult(
			CTermEntryLocalizationImpl.class, cTermEntryLocalization);
	}

	@Override
	public void clearCache(
		List<CTermEntryLocalization> cTermEntryLocalizations) {

		for (CTermEntryLocalization cTermEntryLocalization :
				cTermEntryLocalizations) {

			entityCache.removeResult(
				CTermEntryLocalizationImpl.class, cTermEntryLocalization);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CTermEntryLocalizationImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CTermEntryLocalizationImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CTermEntryLocalizationModelImpl cTermEntryLocalizationModelImpl) {

		Object[] args = new Object[] {
			cTermEntryLocalizationModelImpl.getCommerceTermEntryId(),
			cTermEntryLocalizationModelImpl.getLanguageId()
		};

		finderCache.putResult(
			_finderPathCountByCommerceTermEntryId_LanguageId, args,
			Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByCommerceTermEntryId_LanguageId, args,
			cTermEntryLocalizationModelImpl);
	}

	/**
	 * Creates a new c term entry localization with the primary key. Does not add the c term entry localization to the database.
	 *
	 * @param cTermEntryLocalizationId the primary key for the new c term entry localization
	 * @return the new c term entry localization
	 */
	@Override
	public CTermEntryLocalization create(long cTermEntryLocalizationId) {
		CTermEntryLocalization cTermEntryLocalization =
			new CTermEntryLocalizationImpl();

		cTermEntryLocalization.setNew(true);
		cTermEntryLocalization.setPrimaryKey(cTermEntryLocalizationId);

		cTermEntryLocalization.setCompanyId(CompanyThreadLocal.getCompanyId());

		return cTermEntryLocalization;
	}

	/**
	 * Removes the c term entry localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param cTermEntryLocalizationId the primary key of the c term entry localization
	 * @return the c term entry localization that was removed
	 * @throws NoSuchCTermEntryLocalizationException if a c term entry localization with the primary key could not be found
	 */
	@Override
	public CTermEntryLocalization remove(long cTermEntryLocalizationId)
		throws NoSuchCTermEntryLocalizationException {

		return remove((Serializable)cTermEntryLocalizationId);
	}

	/**
	 * Removes the c term entry localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the c term entry localization
	 * @return the c term entry localization that was removed
	 * @throws NoSuchCTermEntryLocalizationException if a c term entry localization with the primary key could not be found
	 */
	@Override
	public CTermEntryLocalization remove(Serializable primaryKey)
		throws NoSuchCTermEntryLocalizationException {

		Session session = null;

		try {
			session = openSession();

			CTermEntryLocalization cTermEntryLocalization =
				(CTermEntryLocalization)session.get(
					CTermEntryLocalizationImpl.class, primaryKey);

			if (cTermEntryLocalization == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCTermEntryLocalizationException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(cTermEntryLocalization);
		}
		catch (NoSuchCTermEntryLocalizationException noSuchEntityException) {
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
	protected CTermEntryLocalization removeImpl(
		CTermEntryLocalization cTermEntryLocalization) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cTermEntryLocalization)) {
				cTermEntryLocalization = (CTermEntryLocalization)session.get(
					CTermEntryLocalizationImpl.class,
					cTermEntryLocalization.getPrimaryKeyObj());
			}

			if (cTermEntryLocalization != null) {
				session.delete(cTermEntryLocalization);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (cTermEntryLocalization != null) {
			clearCache(cTermEntryLocalization);
		}

		return cTermEntryLocalization;
	}

	@Override
	public CTermEntryLocalization updateImpl(
		CTermEntryLocalization cTermEntryLocalization) {

		boolean isNew = cTermEntryLocalization.isNew();

		if (!(cTermEntryLocalization instanceof
				CTermEntryLocalizationModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cTermEntryLocalization.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					cTermEntryLocalization);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cTermEntryLocalization proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CTermEntryLocalization implementation " +
					cTermEntryLocalization.getClass());
		}

		CTermEntryLocalizationModelImpl cTermEntryLocalizationModelImpl =
			(CTermEntryLocalizationModelImpl)cTermEntryLocalization;

		long userId = GetterUtil.getLong(PrincipalThreadLocal.getName());

		if (userId > 0) {
			long companyId = cTermEntryLocalization.getCompanyId();

			long groupId = 0;

			long cTermEntryLocalizationId = 0;

			if (!isNew) {
				cTermEntryLocalizationId =
					cTermEntryLocalization.getPrimaryKey();
			}

			try {
				cTermEntryLocalization.setDescription(
					SanitizerUtil.sanitize(
						companyId, groupId, userId,
						CTermEntryLocalization.class.getName(),
						cTermEntryLocalizationId, ContentTypes.TEXT_HTML,
						Sanitizer.MODE_ALL,
						cTermEntryLocalization.getDescription(), null));
			}
			catch (SanitizerException sanitizerException) {
				throw new SystemException(sanitizerException);
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(cTermEntryLocalization);
			}
			else {
				cTermEntryLocalization = (CTermEntryLocalization)session.merge(
					cTermEntryLocalization);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CTermEntryLocalizationImpl.class, cTermEntryLocalizationModelImpl,
			false, true);

		cacheUniqueFindersCache(cTermEntryLocalizationModelImpl);

		if (isNew) {
			cTermEntryLocalization.setNew(false);
		}

		cTermEntryLocalization.resetOriginalValues();

		return cTermEntryLocalization;
	}

	/**
	 * Returns the c term entry localization with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the c term entry localization
	 * @return the c term entry localization
	 * @throws NoSuchCTermEntryLocalizationException if a c term entry localization with the primary key could not be found
	 */
	@Override
	public CTermEntryLocalization findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCTermEntryLocalizationException {

		CTermEntryLocalization cTermEntryLocalization = fetchByPrimaryKey(
			primaryKey);

		if (cTermEntryLocalization == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCTermEntryLocalizationException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return cTermEntryLocalization;
	}

	/**
	 * Returns the c term entry localization with the primary key or throws a <code>NoSuchCTermEntryLocalizationException</code> if it could not be found.
	 *
	 * @param cTermEntryLocalizationId the primary key of the c term entry localization
	 * @return the c term entry localization
	 * @throws NoSuchCTermEntryLocalizationException if a c term entry localization with the primary key could not be found
	 */
	@Override
	public CTermEntryLocalization findByPrimaryKey(
			long cTermEntryLocalizationId)
		throws NoSuchCTermEntryLocalizationException {

		return findByPrimaryKey((Serializable)cTermEntryLocalizationId);
	}

	/**
	 * Returns the c term entry localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param cTermEntryLocalizationId the primary key of the c term entry localization
	 * @return the c term entry localization, or <code>null</code> if a c term entry localization with the primary key could not be found
	 */
	@Override
	public CTermEntryLocalization fetchByPrimaryKey(
		long cTermEntryLocalizationId) {

		return fetchByPrimaryKey((Serializable)cTermEntryLocalizationId);
	}

	/**
	 * Returns all the c term entry localizations.
	 *
	 * @return the c term entry localizations
	 */
	@Override
	public List<CTermEntryLocalization> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the c term entry localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTermEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of c term entry localizations
	 * @param end the upper bound of the range of c term entry localizations (not inclusive)
	 * @return the range of c term entry localizations
	 */
	@Override
	public List<CTermEntryLocalization> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the c term entry localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTermEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of c term entry localizations
	 * @param end the upper bound of the range of c term entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of c term entry localizations
	 */
	@Override
	public List<CTermEntryLocalization> findAll(
		int start, int end,
		OrderByComparator<CTermEntryLocalization> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the c term entry localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTermEntryLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of c term entry localizations
	 * @param end the upper bound of the range of c term entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of c term entry localizations
	 */
	@Override
	public List<CTermEntryLocalization> findAll(
		int start, int end,
		OrderByComparator<CTermEntryLocalization> orderByComparator,
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

		List<CTermEntryLocalization> list = null;

		if (useFinderCache) {
			list = (List<CTermEntryLocalization>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CTERMENTRYLOCALIZATION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CTERMENTRYLOCALIZATION;

				sql = sql.concat(CTermEntryLocalizationModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CTermEntryLocalization>)QueryUtil.list(
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
	 * Removes all the c term entry localizations from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CTermEntryLocalization cTermEntryLocalization : findAll()) {
			remove(cTermEntryLocalization);
		}
	}

	/**
	 * Returns the number of c term entry localizations.
	 *
	 * @return the number of c term entry localizations
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
					_SQL_COUNT_CTERMENTRYLOCALIZATION);

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
		return "cTermEntryLocalizationId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CTERMENTRYLOCALIZATION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CTermEntryLocalizationModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the c term entry localization persistence.
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

		_finderPathWithPaginationFindByCommerceTermEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCommerceTermEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"commerceTermEntryId"}, true);

		_finderPathWithoutPaginationFindByCommerceTermEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceTermEntryId", new String[] {Long.class.getName()},
			new String[] {"commerceTermEntryId"}, true);

		_finderPathCountByCommerceTermEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceTermEntryId", new String[] {Long.class.getName()},
			new String[] {"commerceTermEntryId"}, false);

		_finderPathFetchByCommerceTermEntryId_LanguageId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByCommerceTermEntryId_LanguageId",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"commerceTermEntryId", "languageId"}, true);

		_finderPathCountByCommerceTermEntryId_LanguageId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceTermEntryId_LanguageId",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"commerceTermEntryId", "languageId"}, false);

		CTermEntryLocalizationUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CTermEntryLocalizationUtil.setPersistence(null);

		entityCache.removeCache(CTermEntryLocalizationImpl.class.getName());
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

	private static final String _SQL_SELECT_CTERMENTRYLOCALIZATION =
		"SELECT cTermEntryLocalization FROM CTermEntryLocalization cTermEntryLocalization";

	private static final String _SQL_SELECT_CTERMENTRYLOCALIZATION_WHERE =
		"SELECT cTermEntryLocalization FROM CTermEntryLocalization cTermEntryLocalization WHERE ";

	private static final String _SQL_COUNT_CTERMENTRYLOCALIZATION =
		"SELECT COUNT(cTermEntryLocalization) FROM CTermEntryLocalization cTermEntryLocalization";

	private static final String _SQL_COUNT_CTERMENTRYLOCALIZATION_WHERE =
		"SELECT COUNT(cTermEntryLocalization) FROM CTermEntryLocalization cTermEntryLocalization WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"cTermEntryLocalization.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CTermEntryLocalization exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CTermEntryLocalization exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CTermEntryLocalizationPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}