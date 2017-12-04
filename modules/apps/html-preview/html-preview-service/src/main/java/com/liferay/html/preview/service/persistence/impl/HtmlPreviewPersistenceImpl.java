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

import com.liferay.html.preview.exception.NoSuchHtmlPreviewException;
import com.liferay.html.preview.model.HtmlPreview;
import com.liferay.html.preview.model.impl.HtmlPreviewImpl;
import com.liferay.html.preview.model.impl.HtmlPreviewModelImpl;
import com.liferay.html.preview.service.persistence.HtmlPreviewPersistence;

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
 * The persistence implementation for the html preview service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see HtmlPreviewPersistence
 * @see com.liferay.html.preview.service.persistence.HtmlPreviewUtil
 * @generated
 */
@ProviderType
public class HtmlPreviewPersistenceImpl extends BasePersistenceImpl<HtmlPreview>
	implements HtmlPreviewPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link HtmlPreviewUtil} to access the html preview persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = HtmlPreviewImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(HtmlPreviewModelImpl.ENTITY_CACHE_ENABLED,
			HtmlPreviewModelImpl.FINDER_CACHE_ENABLED, HtmlPreviewImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(HtmlPreviewModelImpl.ENTITY_CACHE_ENABLED,
			HtmlPreviewModelImpl.FINDER_CACHE_ENABLED, HtmlPreviewImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(HtmlPreviewModelImpl.ENTITY_CACHE_ENABLED,
			HtmlPreviewModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_FETCH_BY_G_C_C = new FinderPath(HtmlPreviewModelImpl.ENTITY_CACHE_ENABLED,
			HtmlPreviewModelImpl.FINDER_CACHE_ENABLED, HtmlPreviewImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			HtmlPreviewModelImpl.GROUPID_COLUMN_BITMASK |
			HtmlPreviewModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			HtmlPreviewModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C = new FinderPath(HtmlPreviewModelImpl.ENTITY_CACHE_ENABLED,
			HtmlPreviewModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns the html preview where groupId = &#63; and classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchHtmlPreviewException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching html preview
	 * @throws NoSuchHtmlPreviewException if a matching html preview could not be found
	 */
	@Override
	public HtmlPreview findByG_C_C(long groupId, long classNameId, long classPK)
		throws NoSuchHtmlPreviewException {
		HtmlPreview htmlPreview = fetchByG_C_C(groupId, classNameId, classPK);

		if (htmlPreview == null) {
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

			throw new NoSuchHtmlPreviewException(msg.toString());
		}

		return htmlPreview;
	}

	/**
	 * Returns the html preview where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching html preview, or <code>null</code> if a matching html preview could not be found
	 */
	@Override
	public HtmlPreview fetchByG_C_C(long groupId, long classNameId, long classPK) {
		return fetchByG_C_C(groupId, classNameId, classPK, true);
	}

	/**
	 * Returns the html preview where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching html preview, or <code>null</code> if a matching html preview could not be found
	 */
	@Override
	public HtmlPreview fetchByG_C_C(long groupId, long classNameId,
		long classPK, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, classNameId, classPK };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_C_C,
					finderArgs, this);
		}

		if (result instanceof HtmlPreview) {
			HtmlPreview htmlPreview = (HtmlPreview)result;

			if ((groupId != htmlPreview.getGroupId()) ||
					(classNameId != htmlPreview.getClassNameId()) ||
					(classPK != htmlPreview.getClassPK())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_HTMLPREVIEW_WHERE);

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

				List<HtmlPreview> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C,
						finderArgs, list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"HtmlPreviewPersistenceImpl.fetchByG_C_C(long, long, long, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					HtmlPreview htmlPreview = list.get(0);

					result = htmlPreview;

					cacheResult(htmlPreview);

					if ((htmlPreview.getGroupId() != groupId) ||
							(htmlPreview.getClassNameId() != classNameId) ||
							(htmlPreview.getClassPK() != classPK)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C,
							finderArgs, htmlPreview);
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
			return (HtmlPreview)result;
		}
	}

	/**
	 * Removes the html preview where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the html preview that was removed
	 */
	@Override
	public HtmlPreview removeByG_C_C(long groupId, long classNameId,
		long classPK) throws NoSuchHtmlPreviewException {
		HtmlPreview htmlPreview = findByG_C_C(groupId, classNameId, classPK);

		return remove(htmlPreview);
	}

	/**
	 * Returns the number of html previews where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching html previews
	 */
	@Override
	public int countByG_C_C(long groupId, long classNameId, long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_C;

		Object[] finderArgs = new Object[] { groupId, classNameId, classPK };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_HTMLPREVIEW_WHERE);

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

	private static final String _FINDER_COLUMN_G_C_C_GROUPID_2 = "htmlPreview.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_CLASSNAMEID_2 = "htmlPreview.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_CLASSPK_2 = "htmlPreview.classPK = ?";

	public HtmlPreviewPersistenceImpl() {
		setModelClass(HtmlPreview.class);
	}

	/**
	 * Caches the html preview in the entity cache if it is enabled.
	 *
	 * @param htmlPreview the html preview
	 */
	@Override
	public void cacheResult(HtmlPreview htmlPreview) {
		entityCache.putResult(HtmlPreviewModelImpl.ENTITY_CACHE_ENABLED,
			HtmlPreviewImpl.class, htmlPreview.getPrimaryKey(), htmlPreview);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C,
			new Object[] {
				htmlPreview.getGroupId(), htmlPreview.getClassNameId(),
				htmlPreview.getClassPK()
			}, htmlPreview);

		htmlPreview.resetOriginalValues();
	}

	/**
	 * Caches the html previews in the entity cache if it is enabled.
	 *
	 * @param htmlPreviews the html previews
	 */
	@Override
	public void cacheResult(List<HtmlPreview> htmlPreviews) {
		for (HtmlPreview htmlPreview : htmlPreviews) {
			if (entityCache.getResult(
						HtmlPreviewModelImpl.ENTITY_CACHE_ENABLED,
						HtmlPreviewImpl.class, htmlPreview.getPrimaryKey()) == null) {
				cacheResult(htmlPreview);
			}
			else {
				htmlPreview.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all html previews.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(HtmlPreviewImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the html preview.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(HtmlPreview htmlPreview) {
		entityCache.removeResult(HtmlPreviewModelImpl.ENTITY_CACHE_ENABLED,
			HtmlPreviewImpl.class, htmlPreview.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((HtmlPreviewModelImpl)htmlPreview, true);
	}

	@Override
	public void clearCache(List<HtmlPreview> htmlPreviews) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (HtmlPreview htmlPreview : htmlPreviews) {
			entityCache.removeResult(HtmlPreviewModelImpl.ENTITY_CACHE_ENABLED,
				HtmlPreviewImpl.class, htmlPreview.getPrimaryKey());

			clearUniqueFindersCache((HtmlPreviewModelImpl)htmlPreview, true);
		}
	}

	protected void cacheUniqueFindersCache(
		HtmlPreviewModelImpl htmlPreviewModelImpl) {
		Object[] args = new Object[] {
				htmlPreviewModelImpl.getGroupId(),
				htmlPreviewModelImpl.getClassNameId(),
				htmlPreviewModelImpl.getClassPK()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_C_C, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C, args,
			htmlPreviewModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		HtmlPreviewModelImpl htmlPreviewModelImpl, boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					htmlPreviewModelImpl.getGroupId(),
					htmlPreviewModelImpl.getClassNameId(),
					htmlPreviewModelImpl.getClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C, args);
		}

		if ((htmlPreviewModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_C_C.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					htmlPreviewModelImpl.getOriginalGroupId(),
					htmlPreviewModelImpl.getOriginalClassNameId(),
					htmlPreviewModelImpl.getOriginalClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C, args);
		}
	}

	/**
	 * Creates a new html preview with the primary key. Does not add the html preview to the database.
	 *
	 * @param htmlPreviewId the primary key for the new html preview
	 * @return the new html preview
	 */
	@Override
	public HtmlPreview create(long htmlPreviewId) {
		HtmlPreview htmlPreview = new HtmlPreviewImpl();

		htmlPreview.setNew(true);
		htmlPreview.setPrimaryKey(htmlPreviewId);

		htmlPreview.setCompanyId(companyProvider.getCompanyId());

		return htmlPreview;
	}

	/**
	 * Removes the html preview with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param htmlPreviewId the primary key of the html preview
	 * @return the html preview that was removed
	 * @throws NoSuchHtmlPreviewException if a html preview with the primary key could not be found
	 */
	@Override
	public HtmlPreview remove(long htmlPreviewId)
		throws NoSuchHtmlPreviewException {
		return remove((Serializable)htmlPreviewId);
	}

	/**
	 * Removes the html preview with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the html preview
	 * @return the html preview that was removed
	 * @throws NoSuchHtmlPreviewException if a html preview with the primary key could not be found
	 */
	@Override
	public HtmlPreview remove(Serializable primaryKey)
		throws NoSuchHtmlPreviewException {
		Session session = null;

		try {
			session = openSession();

			HtmlPreview htmlPreview = (HtmlPreview)session.get(HtmlPreviewImpl.class,
					primaryKey);

			if (htmlPreview == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchHtmlPreviewException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(htmlPreview);
		}
		catch (NoSuchHtmlPreviewException nsee) {
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
	protected HtmlPreview removeImpl(HtmlPreview htmlPreview) {
		htmlPreview = toUnwrappedModel(htmlPreview);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(htmlPreview)) {
				htmlPreview = (HtmlPreview)session.get(HtmlPreviewImpl.class,
						htmlPreview.getPrimaryKeyObj());
			}

			if (htmlPreview != null) {
				session.delete(htmlPreview);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (htmlPreview != null) {
			clearCache(htmlPreview);
		}

		return htmlPreview;
	}

	@Override
	public HtmlPreview updateImpl(HtmlPreview htmlPreview) {
		htmlPreview = toUnwrappedModel(htmlPreview);

		boolean isNew = htmlPreview.isNew();

		HtmlPreviewModelImpl htmlPreviewModelImpl = (HtmlPreviewModelImpl)htmlPreview;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (htmlPreview.getCreateDate() == null)) {
			if (serviceContext == null) {
				htmlPreview.setCreateDate(now);
			}
			else {
				htmlPreview.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!htmlPreviewModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				htmlPreview.setModifiedDate(now);
			}
			else {
				htmlPreview.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (htmlPreview.isNew()) {
				session.save(htmlPreview);

				htmlPreview.setNew(false);
			}
			else {
				htmlPreview = (HtmlPreview)session.merge(htmlPreview);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!HtmlPreviewModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(HtmlPreviewModelImpl.ENTITY_CACHE_ENABLED,
			HtmlPreviewImpl.class, htmlPreview.getPrimaryKey(), htmlPreview,
			false);

		clearUniqueFindersCache(htmlPreviewModelImpl, false);
		cacheUniqueFindersCache(htmlPreviewModelImpl);

		htmlPreview.resetOriginalValues();

		return htmlPreview;
	}

	protected HtmlPreview toUnwrappedModel(HtmlPreview htmlPreview) {
		if (htmlPreview instanceof HtmlPreviewImpl) {
			return htmlPreview;
		}

		HtmlPreviewImpl htmlPreviewImpl = new HtmlPreviewImpl();

		htmlPreviewImpl.setNew(htmlPreview.isNew());
		htmlPreviewImpl.setPrimaryKey(htmlPreview.getPrimaryKey());

		htmlPreviewImpl.setHtmlPreviewId(htmlPreview.getHtmlPreviewId());
		htmlPreviewImpl.setGroupId(htmlPreview.getGroupId());
		htmlPreviewImpl.setCompanyId(htmlPreview.getCompanyId());
		htmlPreviewImpl.setUserId(htmlPreview.getUserId());
		htmlPreviewImpl.setUserName(htmlPreview.getUserName());
		htmlPreviewImpl.setCreateDate(htmlPreview.getCreateDate());
		htmlPreviewImpl.setModifiedDate(htmlPreview.getModifiedDate());
		htmlPreviewImpl.setClassNameId(htmlPreview.getClassNameId());
		htmlPreviewImpl.setClassPK(htmlPreview.getClassPK());
		htmlPreviewImpl.setFileEntryId(htmlPreview.getFileEntryId());

		return htmlPreviewImpl;
	}

	/**
	 * Returns the html preview with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the html preview
	 * @return the html preview
	 * @throws NoSuchHtmlPreviewException if a html preview with the primary key could not be found
	 */
	@Override
	public HtmlPreview findByPrimaryKey(Serializable primaryKey)
		throws NoSuchHtmlPreviewException {
		HtmlPreview htmlPreview = fetchByPrimaryKey(primaryKey);

		if (htmlPreview == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchHtmlPreviewException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return htmlPreview;
	}

	/**
	 * Returns the html preview with the primary key or throws a {@link NoSuchHtmlPreviewException} if it could not be found.
	 *
	 * @param htmlPreviewId the primary key of the html preview
	 * @return the html preview
	 * @throws NoSuchHtmlPreviewException if a html preview with the primary key could not be found
	 */
	@Override
	public HtmlPreview findByPrimaryKey(long htmlPreviewId)
		throws NoSuchHtmlPreviewException {
		return findByPrimaryKey((Serializable)htmlPreviewId);
	}

	/**
	 * Returns the html preview with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the html preview
	 * @return the html preview, or <code>null</code> if a html preview with the primary key could not be found
	 */
	@Override
	public HtmlPreview fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(HtmlPreviewModelImpl.ENTITY_CACHE_ENABLED,
				HtmlPreviewImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		HtmlPreview htmlPreview = (HtmlPreview)serializable;

		if (htmlPreview == null) {
			Session session = null;

			try {
				session = openSession();

				htmlPreview = (HtmlPreview)session.get(HtmlPreviewImpl.class,
						primaryKey);

				if (htmlPreview != null) {
					cacheResult(htmlPreview);
				}
				else {
					entityCache.putResult(HtmlPreviewModelImpl.ENTITY_CACHE_ENABLED,
						HtmlPreviewImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(HtmlPreviewModelImpl.ENTITY_CACHE_ENABLED,
					HtmlPreviewImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return htmlPreview;
	}

	/**
	 * Returns the html preview with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param htmlPreviewId the primary key of the html preview
	 * @return the html preview, or <code>null</code> if a html preview with the primary key could not be found
	 */
	@Override
	public HtmlPreview fetchByPrimaryKey(long htmlPreviewId) {
		return fetchByPrimaryKey((Serializable)htmlPreviewId);
	}

	@Override
	public Map<Serializable, HtmlPreview> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, HtmlPreview> map = new HashMap<Serializable, HtmlPreview>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			HtmlPreview htmlPreview = fetchByPrimaryKey(primaryKey);

			if (htmlPreview != null) {
				map.put(primaryKey, htmlPreview);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(HtmlPreviewModelImpl.ENTITY_CACHE_ENABLED,
					HtmlPreviewImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (HtmlPreview)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_HTMLPREVIEW_WHERE_PKS_IN);

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

			for (HtmlPreview htmlPreview : (List<HtmlPreview>)q.list()) {
				map.put(htmlPreview.getPrimaryKeyObj(), htmlPreview);

				cacheResult(htmlPreview);

				uncachedPrimaryKeys.remove(htmlPreview.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(HtmlPreviewModelImpl.ENTITY_CACHE_ENABLED,
					HtmlPreviewImpl.class, primaryKey, nullModel);
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
	 * Returns all the html previews.
	 *
	 * @return the html previews
	 */
	@Override
	public List<HtmlPreview> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the html previews.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link HtmlPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of html previews
	 * @param end the upper bound of the range of html previews (not inclusive)
	 * @return the range of html previews
	 */
	@Override
	public List<HtmlPreview> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the html previews.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link HtmlPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of html previews
	 * @param end the upper bound of the range of html previews (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of html previews
	 */
	@Override
	public List<HtmlPreview> findAll(int start, int end,
		OrderByComparator<HtmlPreview> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the html previews.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link HtmlPreviewModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of html previews
	 * @param end the upper bound of the range of html previews (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of html previews
	 */
	@Override
	public List<HtmlPreview> findAll(int start, int end,
		OrderByComparator<HtmlPreview> orderByComparator,
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

		List<HtmlPreview> list = null;

		if (retrieveFromCache) {
			list = (List<HtmlPreview>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_HTMLPREVIEW);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_HTMLPREVIEW;

				if (pagination) {
					sql = sql.concat(HtmlPreviewModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<HtmlPreview>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<HtmlPreview>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Removes all the html previews from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (HtmlPreview htmlPreview : findAll()) {
			remove(htmlPreview);
		}
	}

	/**
	 * Returns the number of html previews.
	 *
	 * @return the number of html previews
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_HTMLPREVIEW);

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
		return HtmlPreviewModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the html preview persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(HtmlPreviewImpl.class.getName());
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
	private static final String _SQL_SELECT_HTMLPREVIEW = "SELECT htmlPreview FROM HtmlPreview htmlPreview";
	private static final String _SQL_SELECT_HTMLPREVIEW_WHERE_PKS_IN = "SELECT htmlPreview FROM HtmlPreview htmlPreview WHERE htmlPreviewId IN (";
	private static final String _SQL_SELECT_HTMLPREVIEW_WHERE = "SELECT htmlPreview FROM HtmlPreview htmlPreview WHERE ";
	private static final String _SQL_COUNT_HTMLPREVIEW = "SELECT COUNT(htmlPreview) FROM HtmlPreview htmlPreview";
	private static final String _SQL_COUNT_HTMLPREVIEW_WHERE = "SELECT COUNT(htmlPreview) FROM HtmlPreview htmlPreview WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "htmlPreview.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No HtmlPreview exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No HtmlPreview exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(HtmlPreviewPersistenceImpl.class);
}