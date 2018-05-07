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

package com.liferay.journal.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.journal.exception.NoSuchArticleLocalizationException;
import com.liferay.journal.model.JournalArticleLocalization;
import com.liferay.journal.model.impl.JournalArticleLocalizationImpl;
import com.liferay.journal.model.impl.JournalArticleLocalizationModelImpl;
import com.liferay.journal.service.persistence.JournalArticleLocalizationPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the journal article localization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticleLocalizationPersistence
 * @see com.liferay.journal.service.persistence.JournalArticleLocalizationUtil
 * @generated
 */
@ProviderType
public class JournalArticleLocalizationPersistenceImpl
	extends BasePersistenceImpl<JournalArticleLocalization>
	implements JournalArticleLocalizationPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link JournalArticleLocalizationUtil} to access the journal article localization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = JournalArticleLocalizationImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(JournalArticleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleLocalizationModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(JournalArticleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleLocalizationModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(JournalArticleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleLocalizationModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ARTICLEPK =
		new FinderPath(JournalArticleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleLocalizationModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByArticlePK",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ARTICLEPK =
		new FinderPath(JournalArticleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleLocalizationModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByArticlePK",
			new String[] { Long.class.getName() },
			JournalArticleLocalizationModelImpl.ARTICLEPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ARTICLEPK = new FinderPath(JournalArticleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleLocalizationModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByArticlePK", new String[] { Long.class.getName() });

	/**
	 * Returns all the journal article localizations where articlePK = &#63;.
	 *
	 * @param articlePK the article pk
	 * @return the matching journal article localizations
	 */
	@Override
	public List<JournalArticleLocalization> findByArticlePK(long articlePK) {
		return findByArticlePK(articlePK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal article localizations where articlePK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param articlePK the article pk
	 * @param start the lower bound of the range of journal article localizations
	 * @param end the upper bound of the range of journal article localizations (not inclusive)
	 * @return the range of matching journal article localizations
	 */
	@Override
	public List<JournalArticleLocalization> findByArticlePK(long articlePK,
		int start, int end) {
		return findByArticlePK(articlePK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal article localizations where articlePK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param articlePK the article pk
	 * @param start the lower bound of the range of journal article localizations
	 * @param end the upper bound of the range of journal article localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal article localizations
	 */
	@Override
	public List<JournalArticleLocalization> findByArticlePK(long articlePK,
		int start, int end,
		OrderByComparator<JournalArticleLocalization> orderByComparator) {
		return findByArticlePK(articlePK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal article localizations where articlePK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param articlePK the article pk
	 * @param start the lower bound of the range of journal article localizations
	 * @param end the upper bound of the range of journal article localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching journal article localizations
	 */
	@Override
	public List<JournalArticleLocalization> findByArticlePK(long articlePK,
		int start, int end,
		OrderByComparator<JournalArticleLocalization> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ARTICLEPK;
			finderArgs = new Object[] { articlePK };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ARTICLEPK;
			finderArgs = new Object[] { articlePK, start, end, orderByComparator };
		}

		List<JournalArticleLocalization> list = null;

		if (retrieveFromCache) {
			list = (List<JournalArticleLocalization>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (JournalArticleLocalization journalArticleLocalization : list) {
					if ((articlePK != journalArticleLocalization.getArticlePK())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_JOURNALARTICLELOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_ARTICLEPK_ARTICLEPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalArticleLocalizationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(articlePK);

				if (!pagination) {
					list = (List<JournalArticleLocalization>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticleLocalization>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first journal article localization in the ordered set where articlePK = &#63;.
	 *
	 * @param articlePK the article pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article localization
	 * @throws NoSuchArticleLocalizationException if a matching journal article localization could not be found
	 */
	@Override
	public JournalArticleLocalization findByArticlePK_First(long articlePK,
		OrderByComparator<JournalArticleLocalization> orderByComparator)
		throws NoSuchArticleLocalizationException {
		JournalArticleLocalization journalArticleLocalization = fetchByArticlePK_First(articlePK,
				orderByComparator);

		if (journalArticleLocalization != null) {
			return journalArticleLocalization;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("articlePK=");
		msg.append(articlePK);

		msg.append("}");

		throw new NoSuchArticleLocalizationException(msg.toString());
	}

	/**
	 * Returns the first journal article localization in the ordered set where articlePK = &#63;.
	 *
	 * @param articlePK the article pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal article localization, or <code>null</code> if a matching journal article localization could not be found
	 */
	@Override
	public JournalArticleLocalization fetchByArticlePK_First(long articlePK,
		OrderByComparator<JournalArticleLocalization> orderByComparator) {
		List<JournalArticleLocalization> list = findByArticlePK(articlePK, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal article localization in the ordered set where articlePK = &#63;.
	 *
	 * @param articlePK the article pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article localization
	 * @throws NoSuchArticleLocalizationException if a matching journal article localization could not be found
	 */
	@Override
	public JournalArticleLocalization findByArticlePK_Last(long articlePK,
		OrderByComparator<JournalArticleLocalization> orderByComparator)
		throws NoSuchArticleLocalizationException {
		JournalArticleLocalization journalArticleLocalization = fetchByArticlePK_Last(articlePK,
				orderByComparator);

		if (journalArticleLocalization != null) {
			return journalArticleLocalization;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("articlePK=");
		msg.append(articlePK);

		msg.append("}");

		throw new NoSuchArticleLocalizationException(msg.toString());
	}

	/**
	 * Returns the last journal article localization in the ordered set where articlePK = &#63;.
	 *
	 * @param articlePK the article pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal article localization, or <code>null</code> if a matching journal article localization could not be found
	 */
	@Override
	public JournalArticleLocalization fetchByArticlePK_Last(long articlePK,
		OrderByComparator<JournalArticleLocalization> orderByComparator) {
		int count = countByArticlePK(articlePK);

		if (count == 0) {
			return null;
		}

		List<JournalArticleLocalization> list = findByArticlePK(articlePK,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal article localizations before and after the current journal article localization in the ordered set where articlePK = &#63;.
	 *
	 * @param articleLocalizationId the primary key of the current journal article localization
	 * @param articlePK the article pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal article localization
	 * @throws NoSuchArticleLocalizationException if a journal article localization with the primary key could not be found
	 */
	@Override
	public JournalArticleLocalization[] findByArticlePK_PrevAndNext(
		long articleLocalizationId, long articlePK,
		OrderByComparator<JournalArticleLocalization> orderByComparator)
		throws NoSuchArticleLocalizationException {
		JournalArticleLocalization journalArticleLocalization = findByPrimaryKey(articleLocalizationId);

		Session session = null;

		try {
			session = openSession();

			JournalArticleLocalization[] array = new JournalArticleLocalizationImpl[3];

			array[0] = getByArticlePK_PrevAndNext(session,
					journalArticleLocalization, articlePK, orderByComparator,
					true);

			array[1] = journalArticleLocalization;

			array[2] = getByArticlePK_PrevAndNext(session,
					journalArticleLocalization, articlePK, orderByComparator,
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

	protected JournalArticleLocalization getByArticlePK_PrevAndNext(
		Session session, JournalArticleLocalization journalArticleLocalization,
		long articlePK,
		OrderByComparator<JournalArticleLocalization> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALARTICLELOCALIZATION_WHERE);

		query.append(_FINDER_COLUMN_ARTICLEPK_ARTICLEPK_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

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
			query.append(JournalArticleLocalizationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(articlePK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalArticleLocalization);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalArticleLocalization> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal article localizations where articlePK = &#63; from the database.
	 *
	 * @param articlePK the article pk
	 */
	@Override
	public void removeByArticlePK(long articlePK) {
		for (JournalArticleLocalization journalArticleLocalization : findByArticlePK(
				articlePK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalArticleLocalization);
		}
	}

	/**
	 * Returns the number of journal article localizations where articlePK = &#63;.
	 *
	 * @param articlePK the article pk
	 * @return the number of matching journal article localizations
	 */
	@Override
	public int countByArticlePK(long articlePK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ARTICLEPK;

		Object[] finderArgs = new Object[] { articlePK };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALARTICLELOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_ARTICLEPK_ARTICLEPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(articlePK);

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

	private static final String _FINDER_COLUMN_ARTICLEPK_ARTICLEPK_2 = "journalArticleLocalization.articlePK = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_A_L = new FinderPath(JournalArticleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleLocalizationModelImpl.FINDER_CACHE_ENABLED,
			JournalArticleLocalizationImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByA_L",
			new String[] { Long.class.getName(), String.class.getName() },
			JournalArticleLocalizationModelImpl.ARTICLEPK_COLUMN_BITMASK |
			JournalArticleLocalizationModelImpl.LANGUAGEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_A_L = new FinderPath(JournalArticleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleLocalizationModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByA_L",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the journal article localization where articlePK = &#63; and languageId = &#63; or throws a {@link NoSuchArticleLocalizationException} if it could not be found.
	 *
	 * @param articlePK the article pk
	 * @param languageId the language ID
	 * @return the matching journal article localization
	 * @throws NoSuchArticleLocalizationException if a matching journal article localization could not be found
	 */
	@Override
	public JournalArticleLocalization findByA_L(long articlePK,
		String languageId) throws NoSuchArticleLocalizationException {
		JournalArticleLocalization journalArticleLocalization = fetchByA_L(articlePK,
				languageId);

		if (journalArticleLocalization == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("articlePK=");
			msg.append(articlePK);

			msg.append(", languageId=");
			msg.append(languageId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchArticleLocalizationException(msg.toString());
		}

		return journalArticleLocalization;
	}

	/**
	 * Returns the journal article localization where articlePK = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param articlePK the article pk
	 * @param languageId the language ID
	 * @return the matching journal article localization, or <code>null</code> if a matching journal article localization could not be found
	 */
	@Override
	public JournalArticleLocalization fetchByA_L(long articlePK,
		String languageId) {
		return fetchByA_L(articlePK, languageId, true);
	}

	/**
	 * Returns the journal article localization where articlePK = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param articlePK the article pk
	 * @param languageId the language ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching journal article localization, or <code>null</code> if a matching journal article localization could not be found
	 */
	@Override
	public JournalArticleLocalization fetchByA_L(long articlePK,
		String languageId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { articlePK, languageId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_A_L,
					finderArgs, this);
		}

		if (result instanceof JournalArticleLocalization) {
			JournalArticleLocalization journalArticleLocalization = (JournalArticleLocalization)result;

			if ((articlePK != journalArticleLocalization.getArticlePK()) ||
					!Objects.equals(languageId,
						journalArticleLocalization.getLanguageId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_JOURNALARTICLELOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_A_L_ARTICLEPK_2);

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_A_L_LANGUAGEID_1);
			}
			else if (languageId.equals("")) {
				query.append(_FINDER_COLUMN_A_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_A_L_LANGUAGEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(articlePK);

				if (bindLanguageId) {
					qPos.add(languageId);
				}

				List<JournalArticleLocalization> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_A_L, finderArgs,
						list);
				}
				else {
					JournalArticleLocalization journalArticleLocalization = list.get(0);

					result = journalArticleLocalization;

					cacheResult(journalArticleLocalization);

					if ((journalArticleLocalization.getArticlePK() != articlePK) ||
							(journalArticleLocalization.getLanguageId() == null) ||
							!journalArticleLocalization.getLanguageId()
														   .equals(languageId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_A_L,
							finderArgs, journalArticleLocalization);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_A_L, finderArgs);

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
			return (JournalArticleLocalization)result;
		}
	}

	/**
	 * Removes the journal article localization where articlePK = &#63; and languageId = &#63; from the database.
	 *
	 * @param articlePK the article pk
	 * @param languageId the language ID
	 * @return the journal article localization that was removed
	 */
	@Override
	public JournalArticleLocalization removeByA_L(long articlePK,
		String languageId) throws NoSuchArticleLocalizationException {
		JournalArticleLocalization journalArticleLocalization = findByA_L(articlePK,
				languageId);

		return remove(journalArticleLocalization);
	}

	/**
	 * Returns the number of journal article localizations where articlePK = &#63; and languageId = &#63;.
	 *
	 * @param articlePK the article pk
	 * @param languageId the language ID
	 * @return the number of matching journal article localizations
	 */
	@Override
	public int countByA_L(long articlePK, String languageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_A_L;

		Object[] finderArgs = new Object[] { articlePK, languageId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALARTICLELOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_A_L_ARTICLEPK_2);

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_A_L_LANGUAGEID_1);
			}
			else if (languageId.equals("")) {
				query.append(_FINDER_COLUMN_A_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_A_L_LANGUAGEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(articlePK);

				if (bindLanguageId) {
					qPos.add(languageId);
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

	private static final String _FINDER_COLUMN_A_L_ARTICLEPK_2 = "journalArticleLocalization.articlePK = ? AND ";
	private static final String _FINDER_COLUMN_A_L_LANGUAGEID_1 = "journalArticleLocalization.languageId IS NULL";
	private static final String _FINDER_COLUMN_A_L_LANGUAGEID_2 = "journalArticleLocalization.languageId = ?";
	private static final String _FINDER_COLUMN_A_L_LANGUAGEID_3 = "(journalArticleLocalization.languageId IS NULL OR journalArticleLocalization.languageId = '')";

	public JournalArticleLocalizationPersistenceImpl() {
		setModelClass(JournalArticleLocalization.class);
	}

	/**
	 * Caches the journal article localization in the entity cache if it is enabled.
	 *
	 * @param journalArticleLocalization the journal article localization
	 */
	@Override
	public void cacheResult(
		JournalArticleLocalization journalArticleLocalization) {
		entityCache.putResult(JournalArticleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleLocalizationImpl.class,
			journalArticleLocalization.getPrimaryKey(),
			journalArticleLocalization);

		finderCache.putResult(FINDER_PATH_FETCH_BY_A_L,
			new Object[] {
				journalArticleLocalization.getArticlePK(),
				journalArticleLocalization.getLanguageId()
			}, journalArticleLocalization);

		journalArticleLocalization.resetOriginalValues();
	}

	/**
	 * Caches the journal article localizations in the entity cache if it is enabled.
	 *
	 * @param journalArticleLocalizations the journal article localizations
	 */
	@Override
	public void cacheResult(
		List<JournalArticleLocalization> journalArticleLocalizations) {
		for (JournalArticleLocalization journalArticleLocalization : journalArticleLocalizations) {
			if (entityCache.getResult(
						JournalArticleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
						JournalArticleLocalizationImpl.class,
						journalArticleLocalization.getPrimaryKey()) == null) {
				cacheResult(journalArticleLocalization);
			}
			else {
				journalArticleLocalization.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all journal article localizations.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(JournalArticleLocalizationImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the journal article localization.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		JournalArticleLocalization journalArticleLocalization) {
		entityCache.removeResult(JournalArticleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleLocalizationImpl.class,
			journalArticleLocalization.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((JournalArticleLocalizationModelImpl)journalArticleLocalization,
			true);
	}

	@Override
	public void clearCache(
		List<JournalArticleLocalization> journalArticleLocalizations) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (JournalArticleLocalization journalArticleLocalization : journalArticleLocalizations) {
			entityCache.removeResult(JournalArticleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
				JournalArticleLocalizationImpl.class,
				journalArticleLocalization.getPrimaryKey());

			clearUniqueFindersCache((JournalArticleLocalizationModelImpl)journalArticleLocalization,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		JournalArticleLocalizationModelImpl journalArticleLocalizationModelImpl) {
		Object[] args = new Object[] {
				journalArticleLocalizationModelImpl.getArticlePK(),
				journalArticleLocalizationModelImpl.getLanguageId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_A_L, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_A_L, args,
			journalArticleLocalizationModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		JournalArticleLocalizationModelImpl journalArticleLocalizationModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					journalArticleLocalizationModelImpl.getArticlePK(),
					journalArticleLocalizationModelImpl.getLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_A_L, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_A_L, args);
		}

		if ((journalArticleLocalizationModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_A_L.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					journalArticleLocalizationModelImpl.getOriginalArticlePK(),
					journalArticleLocalizationModelImpl.getOriginalLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_A_L, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_A_L, args);
		}
	}

	/**
	 * Creates a new journal article localization with the primary key. Does not add the journal article localization to the database.
	 *
	 * @param articleLocalizationId the primary key for the new journal article localization
	 * @return the new journal article localization
	 */
	@Override
	public JournalArticleLocalization create(long articleLocalizationId) {
		JournalArticleLocalization journalArticleLocalization = new JournalArticleLocalizationImpl();

		journalArticleLocalization.setNew(true);
		journalArticleLocalization.setPrimaryKey(articleLocalizationId);

		journalArticleLocalization.setCompanyId(companyProvider.getCompanyId());

		return journalArticleLocalization;
	}

	/**
	 * Removes the journal article localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param articleLocalizationId the primary key of the journal article localization
	 * @return the journal article localization that was removed
	 * @throws NoSuchArticleLocalizationException if a journal article localization with the primary key could not be found
	 */
	@Override
	public JournalArticleLocalization remove(long articleLocalizationId)
		throws NoSuchArticleLocalizationException {
		return remove((Serializable)articleLocalizationId);
	}

	/**
	 * Removes the journal article localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the journal article localization
	 * @return the journal article localization that was removed
	 * @throws NoSuchArticleLocalizationException if a journal article localization with the primary key could not be found
	 */
	@Override
	public JournalArticleLocalization remove(Serializable primaryKey)
		throws NoSuchArticleLocalizationException {
		Session session = null;

		try {
			session = openSession();

			JournalArticleLocalization journalArticleLocalization = (JournalArticleLocalization)session.get(JournalArticleLocalizationImpl.class,
					primaryKey);

			if (journalArticleLocalization == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchArticleLocalizationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(journalArticleLocalization);
		}
		catch (NoSuchArticleLocalizationException nsee) {
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
	protected JournalArticleLocalization removeImpl(
		JournalArticleLocalization journalArticleLocalization) {
		journalArticleLocalization = toUnwrappedModel(journalArticleLocalization);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(journalArticleLocalization)) {
				journalArticleLocalization = (JournalArticleLocalization)session.get(JournalArticleLocalizationImpl.class,
						journalArticleLocalization.getPrimaryKeyObj());
			}

			if (journalArticleLocalization != null) {
				session.delete(journalArticleLocalization);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (journalArticleLocalization != null) {
			clearCache(journalArticleLocalization);
		}

		return journalArticleLocalization;
	}

	@Override
	public JournalArticleLocalization updateImpl(
		JournalArticleLocalization journalArticleLocalization) {
		journalArticleLocalization = toUnwrappedModel(journalArticleLocalization);

		boolean isNew = journalArticleLocalization.isNew();

		JournalArticleLocalizationModelImpl journalArticleLocalizationModelImpl = (JournalArticleLocalizationModelImpl)journalArticleLocalization;

		Session session = null;

		try {
			session = openSession();

			if (journalArticleLocalization.isNew()) {
				session.save(journalArticleLocalization);

				journalArticleLocalization.setNew(false);
			}
			else {
				journalArticleLocalization = (JournalArticleLocalization)session.merge(journalArticleLocalization);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!JournalArticleLocalizationModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					journalArticleLocalizationModelImpl.getArticlePK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_ARTICLEPK, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ARTICLEPK,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((journalArticleLocalizationModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ARTICLEPK.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalArticleLocalizationModelImpl.getOriginalArticlePK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ARTICLEPK, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ARTICLEPK,
					args);

				args = new Object[] {
						journalArticleLocalizationModelImpl.getArticlePK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ARTICLEPK, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ARTICLEPK,
					args);
			}
		}

		entityCache.putResult(JournalArticleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			JournalArticleLocalizationImpl.class,
			journalArticleLocalization.getPrimaryKey(),
			journalArticleLocalization, false);

		clearUniqueFindersCache(journalArticleLocalizationModelImpl, false);
		cacheUniqueFindersCache(journalArticleLocalizationModelImpl);

		journalArticleLocalization.resetOriginalValues();

		return journalArticleLocalization;
	}

	protected JournalArticleLocalization toUnwrappedModel(
		JournalArticleLocalization journalArticleLocalization) {
		if (journalArticleLocalization instanceof JournalArticleLocalizationImpl) {
			return journalArticleLocalization;
		}

		JournalArticleLocalizationImpl journalArticleLocalizationImpl = new JournalArticleLocalizationImpl();

		journalArticleLocalizationImpl.setNew(journalArticleLocalization.isNew());
		journalArticleLocalizationImpl.setPrimaryKey(journalArticleLocalization.getPrimaryKey());

		journalArticleLocalizationImpl.setArticleLocalizationId(journalArticleLocalization.getArticleLocalizationId());
		journalArticleLocalizationImpl.setCompanyId(journalArticleLocalization.getCompanyId());
		journalArticleLocalizationImpl.setArticlePK(journalArticleLocalization.getArticlePK());
		journalArticleLocalizationImpl.setTitle(journalArticleLocalization.getTitle());
		journalArticleLocalizationImpl.setDescription(journalArticleLocalization.getDescription());
		journalArticleLocalizationImpl.setLanguageId(journalArticleLocalization.getLanguageId());

		return journalArticleLocalizationImpl;
	}

	/**
	 * Returns the journal article localization with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the journal article localization
	 * @return the journal article localization
	 * @throws NoSuchArticleLocalizationException if a journal article localization with the primary key could not be found
	 */
	@Override
	public JournalArticleLocalization findByPrimaryKey(Serializable primaryKey)
		throws NoSuchArticleLocalizationException {
		JournalArticleLocalization journalArticleLocalization = fetchByPrimaryKey(primaryKey);

		if (journalArticleLocalization == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchArticleLocalizationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return journalArticleLocalization;
	}

	/**
	 * Returns the journal article localization with the primary key or throws a {@link NoSuchArticleLocalizationException} if it could not be found.
	 *
	 * @param articleLocalizationId the primary key of the journal article localization
	 * @return the journal article localization
	 * @throws NoSuchArticleLocalizationException if a journal article localization with the primary key could not be found
	 */
	@Override
	public JournalArticleLocalization findByPrimaryKey(
		long articleLocalizationId) throws NoSuchArticleLocalizationException {
		return findByPrimaryKey((Serializable)articleLocalizationId);
	}

	/**
	 * Returns the journal article localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the journal article localization
	 * @return the journal article localization, or <code>null</code> if a journal article localization with the primary key could not be found
	 */
	@Override
	public JournalArticleLocalization fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(JournalArticleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
				JournalArticleLocalizationImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		JournalArticleLocalization journalArticleLocalization = (JournalArticleLocalization)serializable;

		if (journalArticleLocalization == null) {
			Session session = null;

			try {
				session = openSession();

				journalArticleLocalization = (JournalArticleLocalization)session.get(JournalArticleLocalizationImpl.class,
						primaryKey);

				if (journalArticleLocalization != null) {
					cacheResult(journalArticleLocalization);
				}
				else {
					entityCache.putResult(JournalArticleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
						JournalArticleLocalizationImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(JournalArticleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
					JournalArticleLocalizationImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return journalArticleLocalization;
	}

	/**
	 * Returns the journal article localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param articleLocalizationId the primary key of the journal article localization
	 * @return the journal article localization, or <code>null</code> if a journal article localization with the primary key could not be found
	 */
	@Override
	public JournalArticleLocalization fetchByPrimaryKey(
		long articleLocalizationId) {
		return fetchByPrimaryKey((Serializable)articleLocalizationId);
	}

	@Override
	public Map<Serializable, JournalArticleLocalization> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, JournalArticleLocalization> map = new HashMap<Serializable, JournalArticleLocalization>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			JournalArticleLocalization journalArticleLocalization = fetchByPrimaryKey(primaryKey);

			if (journalArticleLocalization != null) {
				map.put(primaryKey, journalArticleLocalization);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(JournalArticleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
					JournalArticleLocalizationImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (JournalArticleLocalization)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_JOURNALARTICLELOCALIZATION_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(",");
		}

		query.setIndex(query.index() - 1);

		query.append(")");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (JournalArticleLocalization journalArticleLocalization : (List<JournalArticleLocalization>)q.list()) {
				map.put(journalArticleLocalization.getPrimaryKeyObj(),
					journalArticleLocalization);

				cacheResult(journalArticleLocalization);

				uncachedPrimaryKeys.remove(journalArticleLocalization.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(JournalArticleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
					JournalArticleLocalizationImpl.class, primaryKey, nullModel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the journal article localizations.
	 *
	 * @return the journal article localizations
	 */
	@Override
	public List<JournalArticleLocalization> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal article localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal article localizations
	 * @param end the upper bound of the range of journal article localizations (not inclusive)
	 * @return the range of journal article localizations
	 */
	@Override
	public List<JournalArticleLocalization> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal article localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal article localizations
	 * @param end the upper bound of the range of journal article localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of journal article localizations
	 */
	@Override
	public List<JournalArticleLocalization> findAll(int start, int end,
		OrderByComparator<JournalArticleLocalization> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the journal article localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalArticleLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal article localizations
	 * @param end the upper bound of the range of journal article localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of journal article localizations
	 */
	@Override
	public List<JournalArticleLocalization> findAll(int start, int end,
		OrderByComparator<JournalArticleLocalization> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<JournalArticleLocalization> list = null;

		if (retrieveFromCache) {
			list = (List<JournalArticleLocalization>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_JOURNALARTICLELOCALIZATION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_JOURNALARTICLELOCALIZATION;

				if (pagination) {
					sql = sql.concat(JournalArticleLocalizationModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<JournalArticleLocalization>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<JournalArticleLocalization>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the journal article localizations from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (JournalArticleLocalization journalArticleLocalization : findAll()) {
			remove(journalArticleLocalization);
		}
	}

	/**
	 * Returns the number of journal article localizations.
	 *
	 * @return the number of journal article localizations
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_JOURNALARTICLELOCALIZATION);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return JournalArticleLocalizationModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the journal article localization persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(JournalArticleLocalizationImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_JOURNALARTICLELOCALIZATION = "SELECT journalArticleLocalization FROM JournalArticleLocalization journalArticleLocalization";
	private static final String _SQL_SELECT_JOURNALARTICLELOCALIZATION_WHERE_PKS_IN =
		"SELECT journalArticleLocalization FROM JournalArticleLocalization journalArticleLocalization WHERE articleLocalizationId IN (";
	private static final String _SQL_SELECT_JOURNALARTICLELOCALIZATION_WHERE = "SELECT journalArticleLocalization FROM JournalArticleLocalization journalArticleLocalization WHERE ";
	private static final String _SQL_COUNT_JOURNALARTICLELOCALIZATION = "SELECT COUNT(journalArticleLocalization) FROM JournalArticleLocalization journalArticleLocalization";
	private static final String _SQL_COUNT_JOURNALARTICLELOCALIZATION_WHERE = "SELECT COUNT(journalArticleLocalization) FROM JournalArticleLocalization journalArticleLocalization WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "journalArticleLocalization.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No JournalArticleLocalization exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No JournalArticleLocalization exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(JournalArticleLocalizationPersistenceImpl.class);
}