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

package com.liferay.html.preview.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.html.preview.exception.NoSuchHtmlPreviewEntryException;
import com.liferay.html.preview.model.HtmlPreviewEntry;
import com.liferay.html.preview.model.impl.HtmlPreviewEntryImpl;
import com.liferay.html.preview.model.impl.HtmlPreviewEntryModelImpl;
import com.liferay.html.preview.service.persistence.HtmlPreviewEntryPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the html preview entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see HtmlPreviewEntryPersistence
 * @see com.liferay.html.preview.service.persistence.HtmlPreviewEntryUtil
 * @generated
 */
@ProviderType
public class HtmlPreviewEntryPersistenceImpl extends BasePersistenceImpl<HtmlPreviewEntry>
	implements HtmlPreviewEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link HtmlPreviewEntryUtil} to access the html preview entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = HtmlPreviewEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(HtmlPreviewEntryModelImpl.ENTITY_CACHE_ENABLED,
			HtmlPreviewEntryModelImpl.FINDER_CACHE_ENABLED,
			HtmlPreviewEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(HtmlPreviewEntryModelImpl.ENTITY_CACHE_ENABLED,
			HtmlPreviewEntryModelImpl.FINDER_CACHE_ENABLED,
			HtmlPreviewEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(HtmlPreviewEntryModelImpl.ENTITY_CACHE_ENABLED,
			HtmlPreviewEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_FETCH_BY_G_C_C = new FinderPath(HtmlPreviewEntryModelImpl.ENTITY_CACHE_ENABLED,
			HtmlPreviewEntryModelImpl.FINDER_CACHE_ENABLED,
			HtmlPreviewEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			HtmlPreviewEntryModelImpl.GROUPID_COLUMN_BITMASK |
			HtmlPreviewEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			HtmlPreviewEntryModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C = new FinderPath(HtmlPreviewEntryModelImpl.ENTITY_CACHE_ENABLED,
			HtmlPreviewEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns the html preview entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchHtmlPreviewEntryException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching html preview entry
	 * @throws NoSuchHtmlPreviewEntryException if a matching html preview entry could not be found
	 */
	@Override
	public HtmlPreviewEntry findByG_C_C(long groupId, long classNameId,
		long classPK) throws NoSuchHtmlPreviewEntryException {
		HtmlPreviewEntry htmlPreviewEntry = fetchByG_C_C(groupId, classNameId,
				classPK);

		if (htmlPreviewEntry == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchHtmlPreviewEntryException(msg.toString());
		}

		return htmlPreviewEntry;
	}

	/**
	 * Returns the html preview entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching html preview entry, or <code>null</code> if a matching html preview entry could not be found
	 */
	@Override
	public HtmlPreviewEntry fetchByG_C_C(long groupId, long classNameId,
		long classPK) {
		return fetchByG_C_C(groupId, classNameId, classPK, true);
	}

	/**
	 * Returns the html preview entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching html preview entry, or <code>null</code> if a matching html preview entry could not be found
	 */
	@Override
	public HtmlPreviewEntry fetchByG_C_C(long groupId, long classNameId,
		long classPK, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, classNameId, classPK };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_C_C,
					finderArgs, this);
		}

		if (result instanceof HtmlPreviewEntry) {
			HtmlPreviewEntry htmlPreviewEntry = (HtmlPreviewEntry)result;

			if ((groupId != htmlPreviewEntry.getGroupId()) ||
					(classNameId != htmlPreviewEntry.getClassNameId()) ||
					(classPK != htmlPreviewEntry.getClassPK())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_HTMLPREVIEWENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<HtmlPreviewEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C,
						finderArgs, list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"HtmlPreviewEntryPersistenceImpl.fetchByG_C_C(long, long, long, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					HtmlPreviewEntry htmlPreviewEntry = list.get(0);

					result = htmlPreviewEntry;

					cacheResult(htmlPreviewEntry);

					if ((htmlPreviewEntry.getGroupId() != groupId) ||
							(htmlPreviewEntry.getClassNameId() != classNameId) ||
							(htmlPreviewEntry.getClassPK() != classPK)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C,
							finderArgs, htmlPreviewEntry);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C, finderArgs);

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
			return (HtmlPreviewEntry)result;
		}
	}

	/**
	 * Removes the html preview entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the html preview entry that was removed
	 */
	@Override
	public HtmlPreviewEntry removeByG_C_C(long groupId, long classNameId,
		long classPK) throws NoSuchHtmlPreviewEntryException {
		HtmlPreviewEntry htmlPreviewEntry = findByG_C_C(groupId, classNameId,
				classPK);

		return remove(htmlPreviewEntry);
	}

	/**
	 * Returns the number of html preview entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching html preview entries
	 */
	@Override
	public int countByG_C_C(long groupId, long classNameId, long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_C;

		Object[] finderArgs = new Object[] { groupId, classNameId, classPK };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_HTMLPREVIEWENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

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

	private static final String _FINDER_COLUMN_G_C_C_GROUPID_2 = "htmlPreviewEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_CLASSNAMEID_2 = "htmlPreviewEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_CLASSPK_2 = "htmlPreviewEntry.classPK = ?";

	public HtmlPreviewEntryPersistenceImpl() {
		setModelClass(HtmlPreviewEntry.class);
	}

	/**
	 * Caches the html preview entry in the entity cache if it is enabled.
	 *
	 * @param htmlPreviewEntry the html preview entry
	 */
	@Override
	public void cacheResult(HtmlPreviewEntry htmlPreviewEntry) {
		entityCache.putResult(HtmlPreviewEntryModelImpl.ENTITY_CACHE_ENABLED,
			HtmlPreviewEntryImpl.class, htmlPreviewEntry.getPrimaryKey(),
			htmlPreviewEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C,
			new Object[] {
				htmlPreviewEntry.getGroupId(), htmlPreviewEntry.getClassNameId(),
				htmlPreviewEntry.getClassPK()
			}, htmlPreviewEntry);

		htmlPreviewEntry.resetOriginalValues();
	}

	/**
	 * Caches the html preview entries in the entity cache if it is enabled.
	 *
	 * @param htmlPreviewEntries the html preview entries
	 */
	@Override
	public void cacheResult(List<HtmlPreviewEntry> htmlPreviewEntries) {
		for (HtmlPreviewEntry htmlPreviewEntry : htmlPreviewEntries) {
			if (entityCache.getResult(
						HtmlPreviewEntryModelImpl.ENTITY_CACHE_ENABLED,
						HtmlPreviewEntryImpl.class,
						htmlPreviewEntry.getPrimaryKey()) == null) {
				cacheResult(htmlPreviewEntry);
			}
			else {
				htmlPreviewEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all html preview entries.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(HtmlPreviewEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the html preview entry.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(HtmlPreviewEntry htmlPreviewEntry) {
		entityCache.removeResult(HtmlPreviewEntryModelImpl.ENTITY_CACHE_ENABLED,
			HtmlPreviewEntryImpl.class, htmlPreviewEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((HtmlPreviewEntryModelImpl)htmlPreviewEntry,
			true);
	}

	@Override
	public void clearCache(List<HtmlPreviewEntry> htmlPreviewEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (HtmlPreviewEntry htmlPreviewEntry : htmlPreviewEntries) {
			entityCache.removeResult(HtmlPreviewEntryModelImpl.ENTITY_CACHE_ENABLED,
				HtmlPreviewEntryImpl.class, htmlPreviewEntry.getPrimaryKey());

			clearUniqueFindersCache((HtmlPreviewEntryModelImpl)htmlPreviewEntry,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		HtmlPreviewEntryModelImpl htmlPreviewEntryModelImpl) {
		Object[] args = new Object[] {
				htmlPreviewEntryModelImpl.getGroupId(),
				htmlPreviewEntryModelImpl.getClassNameId(),
				htmlPreviewEntryModelImpl.getClassPK()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_C_C, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C, args,
			htmlPreviewEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		HtmlPreviewEntryModelImpl htmlPreviewEntryModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					htmlPreviewEntryModelImpl.getGroupId(),
					htmlPreviewEntryModelImpl.getClassNameId(),
					htmlPreviewEntryModelImpl.getClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C, args);
		}

		if ((htmlPreviewEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_C_C.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					htmlPreviewEntryModelImpl.getOriginalGroupId(),
					htmlPreviewEntryModelImpl.getOriginalClassNameId(),
					htmlPreviewEntryModelImpl.getOriginalClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C, args);
		}
	}

	/**
	 * Creates a new html preview entry with the primary key. Does not add the html preview entry to the database.
	 *
	 * @param htmlPreviewEntryId the primary key for the new html preview entry
	 * @return the new html preview entry
	 */
	@Override
	public HtmlPreviewEntry create(long htmlPreviewEntryId) {
		HtmlPreviewEntry htmlPreviewEntry = new HtmlPreviewEntryImpl();

		htmlPreviewEntry.setNew(true);
		htmlPreviewEntry.setPrimaryKey(htmlPreviewEntryId);

		htmlPreviewEntry.setCompanyId(companyProvider.getCompanyId());

		return htmlPreviewEntry;
	}

	/**
	 * Removes the html preview entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param htmlPreviewEntryId the primary key of the html preview entry
	 * @return the html preview entry that was removed
	 * @throws NoSuchHtmlPreviewEntryException if a html preview entry with the primary key could not be found
	 */
	@Override
	public HtmlPreviewEntry remove(long htmlPreviewEntryId)
		throws NoSuchHtmlPreviewEntryException {
		return remove((Serializable)htmlPreviewEntryId);
	}

	/**
	 * Removes the html preview entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the html preview entry
	 * @return the html preview entry that was removed
	 * @throws NoSuchHtmlPreviewEntryException if a html preview entry with the primary key could not be found
	 */
	@Override
	public HtmlPreviewEntry remove(Serializable primaryKey)
		throws NoSuchHtmlPreviewEntryException {
		Session session = null;

		try {
			session = openSession();

			HtmlPreviewEntry htmlPreviewEntry = (HtmlPreviewEntry)session.get(HtmlPreviewEntryImpl.class,
					primaryKey);

			if (htmlPreviewEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchHtmlPreviewEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(htmlPreviewEntry);
		}
		catch (NoSuchHtmlPreviewEntryException nsee) {
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
	protected HtmlPreviewEntry removeImpl(HtmlPreviewEntry htmlPreviewEntry) {
		htmlPreviewEntry = toUnwrappedModel(htmlPreviewEntry);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(htmlPreviewEntry)) {
				htmlPreviewEntry = (HtmlPreviewEntry)session.get(HtmlPreviewEntryImpl.class,
						htmlPreviewEntry.getPrimaryKeyObj());
			}

			if (htmlPreviewEntry != null) {
				session.delete(htmlPreviewEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (htmlPreviewEntry != null) {
			clearCache(htmlPreviewEntry);
		}

		return htmlPreviewEntry;
	}

	@Override
	public HtmlPreviewEntry updateImpl(HtmlPreviewEntry htmlPreviewEntry) {
		htmlPreviewEntry = toUnwrappedModel(htmlPreviewEntry);

		boolean isNew = htmlPreviewEntry.isNew();

		HtmlPreviewEntryModelImpl htmlPreviewEntryModelImpl = (HtmlPreviewEntryModelImpl)htmlPreviewEntry;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (htmlPreviewEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				htmlPreviewEntry.setCreateDate(now);
			}
			else {
				htmlPreviewEntry.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!htmlPreviewEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				htmlPreviewEntry.setModifiedDate(now);
			}
			else {
				htmlPreviewEntry.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (htmlPreviewEntry.isNew()) {
				session.save(htmlPreviewEntry);

				htmlPreviewEntry.setNew(false);
			}
			else {
				htmlPreviewEntry = (HtmlPreviewEntry)session.merge(htmlPreviewEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!HtmlPreviewEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(HtmlPreviewEntryModelImpl.ENTITY_CACHE_ENABLED,
			HtmlPreviewEntryImpl.class, htmlPreviewEntry.getPrimaryKey(),
			htmlPreviewEntry, false);

		clearUniqueFindersCache(htmlPreviewEntryModelImpl, false);
		cacheUniqueFindersCache(htmlPreviewEntryModelImpl);

		htmlPreviewEntry.resetOriginalValues();

		return htmlPreviewEntry;
	}

	protected HtmlPreviewEntry toUnwrappedModel(
		HtmlPreviewEntry htmlPreviewEntry) {
		if (htmlPreviewEntry instanceof HtmlPreviewEntryImpl) {
			return htmlPreviewEntry;
		}

		HtmlPreviewEntryImpl htmlPreviewEntryImpl = new HtmlPreviewEntryImpl();

		htmlPreviewEntryImpl.setNew(htmlPreviewEntry.isNew());
		htmlPreviewEntryImpl.setPrimaryKey(htmlPreviewEntry.getPrimaryKey());

		htmlPreviewEntryImpl.setHtmlPreviewEntryId(htmlPreviewEntry.getHtmlPreviewEntryId());
		htmlPreviewEntryImpl.setGroupId(htmlPreviewEntry.getGroupId());
		htmlPreviewEntryImpl.setCompanyId(htmlPreviewEntry.getCompanyId());
		htmlPreviewEntryImpl.setUserId(htmlPreviewEntry.getUserId());
		htmlPreviewEntryImpl.setUserName(htmlPreviewEntry.getUserName());
		htmlPreviewEntryImpl.setCreateDate(htmlPreviewEntry.getCreateDate());
		htmlPreviewEntryImpl.setModifiedDate(htmlPreviewEntry.getModifiedDate());
		htmlPreviewEntryImpl.setClassNameId(htmlPreviewEntry.getClassNameId());
		htmlPreviewEntryImpl.setClassPK(htmlPreviewEntry.getClassPK());
		htmlPreviewEntryImpl.setFileEntryId(htmlPreviewEntry.getFileEntryId());

		return htmlPreviewEntryImpl;
	}

	/**
	 * Returns the html preview entry with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the html preview entry
	 * @return the html preview entry
	 * @throws NoSuchHtmlPreviewEntryException if a html preview entry with the primary key could not be found
	 */
	@Override
	public HtmlPreviewEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchHtmlPreviewEntryException {
		HtmlPreviewEntry htmlPreviewEntry = fetchByPrimaryKey(primaryKey);

		if (htmlPreviewEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchHtmlPreviewEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return htmlPreviewEntry;
	}

	/**
	 * Returns the html preview entry with the primary key or throws a {@link NoSuchHtmlPreviewEntryException} if it could not be found.
	 *
	 * @param htmlPreviewEntryId the primary key of the html preview entry
	 * @return the html preview entry
	 * @throws NoSuchHtmlPreviewEntryException if a html preview entry with the primary key could not be found
	 */
	@Override
	public HtmlPreviewEntry findByPrimaryKey(long htmlPreviewEntryId)
		throws NoSuchHtmlPreviewEntryException {
		return findByPrimaryKey((Serializable)htmlPreviewEntryId);
	}

	/**
	 * Returns the html preview entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the html preview entry
	 * @return the html preview entry, or <code>null</code> if a html preview entry with the primary key could not be found
	 */
	@Override
	public HtmlPreviewEntry fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(HtmlPreviewEntryModelImpl.ENTITY_CACHE_ENABLED,
				HtmlPreviewEntryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		HtmlPreviewEntry htmlPreviewEntry = (HtmlPreviewEntry)serializable;

		if (htmlPreviewEntry == null) {
			Session session = null;

			try {
				session = openSession();

				htmlPreviewEntry = (HtmlPreviewEntry)session.get(HtmlPreviewEntryImpl.class,
						primaryKey);

				if (htmlPreviewEntry != null) {
					cacheResult(htmlPreviewEntry);
				}
				else {
					entityCache.putResult(HtmlPreviewEntryModelImpl.ENTITY_CACHE_ENABLED,
						HtmlPreviewEntryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(HtmlPreviewEntryModelImpl.ENTITY_CACHE_ENABLED,
					HtmlPreviewEntryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return htmlPreviewEntry;
	}

	/**
	 * Returns the html preview entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param htmlPreviewEntryId the primary key of the html preview entry
	 * @return the html preview entry, or <code>null</code> if a html preview entry with the primary key could not be found
	 */
	@Override
	public HtmlPreviewEntry fetchByPrimaryKey(long htmlPreviewEntryId) {
		return fetchByPrimaryKey((Serializable)htmlPreviewEntryId);
	}

	@Override
	public Map<Serializable, HtmlPreviewEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, HtmlPreviewEntry> map = new HashMap<Serializable, HtmlPreviewEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			HtmlPreviewEntry htmlPreviewEntry = fetchByPrimaryKey(primaryKey);

			if (htmlPreviewEntry != null) {
				map.put(primaryKey, htmlPreviewEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(HtmlPreviewEntryModelImpl.ENTITY_CACHE_ENABLED,
					HtmlPreviewEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (HtmlPreviewEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_HTMLPREVIEWENTRY_WHERE_PKS_IN);

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

			for (HtmlPreviewEntry htmlPreviewEntry : (List<HtmlPreviewEntry>)q.list()) {
				map.put(htmlPreviewEntry.getPrimaryKeyObj(), htmlPreviewEntry);

				cacheResult(htmlPreviewEntry);

				uncachedPrimaryKeys.remove(htmlPreviewEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(HtmlPreviewEntryModelImpl.ENTITY_CACHE_ENABLED,
					HtmlPreviewEntryImpl.class, primaryKey, nullModel);
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
	 * Returns all the html preview entries.
	 *
	 * @return the html preview entries
	 */
	@Override
	public List<HtmlPreviewEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the html preview entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link HtmlPreviewEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of html preview entries
	 * @param end the upper bound of the range of html preview entries (not inclusive)
	 * @return the range of html preview entries
	 */
	@Override
	public List<HtmlPreviewEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the html preview entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link HtmlPreviewEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of html preview entries
	 * @param end the upper bound of the range of html preview entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of html preview entries
	 */
	@Override
	public List<HtmlPreviewEntry> findAll(int start, int end,
		OrderByComparator<HtmlPreviewEntry> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the html preview entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link HtmlPreviewEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of html preview entries
	 * @param end the upper bound of the range of html preview entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of html preview entries
	 */
	@Override
	public List<HtmlPreviewEntry> findAll(int start, int end,
		OrderByComparator<HtmlPreviewEntry> orderByComparator,
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

		List<HtmlPreviewEntry> list = null;

		if (retrieveFromCache) {
			list = (List<HtmlPreviewEntry>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_HTMLPREVIEWENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_HTMLPREVIEWENTRY;

				if (pagination) {
					sql = sql.concat(HtmlPreviewEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<HtmlPreviewEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<HtmlPreviewEntry>)QueryUtil.list(q,
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
	 * Removes all the html preview entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (HtmlPreviewEntry htmlPreviewEntry : findAll()) {
			remove(htmlPreviewEntry);
		}
	}

	/**
	 * Returns the number of html preview entries.
	 *
	 * @return the number of html preview entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_HTMLPREVIEWENTRY);

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
		return HtmlPreviewEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the html preview entry persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(HtmlPreviewEntryImpl.class.getName());
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
	private static final String _SQL_SELECT_HTMLPREVIEWENTRY = "SELECT htmlPreviewEntry FROM HtmlPreviewEntry htmlPreviewEntry";
	private static final String _SQL_SELECT_HTMLPREVIEWENTRY_WHERE_PKS_IN = "SELECT htmlPreviewEntry FROM HtmlPreviewEntry htmlPreviewEntry WHERE htmlPreviewEntryId IN (";
	private static final String _SQL_SELECT_HTMLPREVIEWENTRY_WHERE = "SELECT htmlPreviewEntry FROM HtmlPreviewEntry htmlPreviewEntry WHERE ";
	private static final String _SQL_COUNT_HTMLPREVIEWENTRY = "SELECT COUNT(htmlPreviewEntry) FROM HtmlPreviewEntry htmlPreviewEntry";
	private static final String _SQL_COUNT_HTMLPREVIEWENTRY_WHERE = "SELECT COUNT(htmlPreviewEntry) FROM HtmlPreviewEntry htmlPreviewEntry WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "htmlPreviewEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No HtmlPreviewEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No HtmlPreviewEntry exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(HtmlPreviewEntryPersistenceImpl.class);
}