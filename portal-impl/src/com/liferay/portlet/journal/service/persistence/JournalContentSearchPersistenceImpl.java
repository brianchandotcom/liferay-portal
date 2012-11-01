/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.LayoutPersistence;
import com.liferay.portal.service.persistence.PortletPreferencesPersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.journal.NoSuchContentSearchException;
import com.liferay.portlet.journal.model.JournalContentSearch;
import com.liferay.portlet.journal.model.impl.JournalContentSearchImpl;
import com.liferay.portlet.journal.model.impl.JournalContentSearchModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the journal content search service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalContentSearchPersistence
 * @see JournalContentSearchUtil
 * @generated
 */
public class JournalContentSearchPersistenceImpl extends BasePersistenceImpl<JournalContentSearch>
	implements JournalContentSearchPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link JournalContentSearchUtil} to access the journal content search persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = JournalContentSearchImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_PORTLETID =
		new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPortletId",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PORTLETID =
		new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByPortletId",
			new String[] { String.class.getName() },
			JournalContentSearchModelImpl.PORTLETID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_PORTLETID = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPortletId",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ARTICLEID =
		new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByArticleId",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ARTICLEID =
		new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByArticleId",
			new String[] { String.class.getName() },
			JournalContentSearchModelImpl.ARTICLEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ARTICLEID = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByArticleId",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			JournalContentSearchModelImpl.GROUPID_COLUMN_BITMASK |
			JournalContentSearchModelImpl.PRIVATELAYOUT_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P",
			new String[] { Long.class.getName(), Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_A",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_A",
			new String[] { Long.class.getName(), String.class.getName() },
			JournalContentSearchModelImpl.GROUPID_COLUMN_BITMASK |
			JournalContentSearchModelImpl.ARTICLEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_A = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_A",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P_L = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P_L",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_L = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P_L",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			},
			JournalContentSearchModelImpl.GROUPID_COLUMN_BITMASK |
			JournalContentSearchModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			JournalContentSearchModelImpl.LAYOUTID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P_L = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_L",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P_A = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_A = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			},
			JournalContentSearchModelImpl.GROUPID_COLUMN_BITMASK |
			JournalContentSearchModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			JournalContentSearchModelImpl.ARTICLEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P_A = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P_L_P = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P_L_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_L_P =
		new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P_L_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), String.class.getName()
			},
			JournalContentSearchModelImpl.GROUPID_COLUMN_BITMASK |
			JournalContentSearchModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			JournalContentSearchModelImpl.LAYOUTID_COLUMN_BITMASK |
			JournalContentSearchModelImpl.PORTLETID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P_L_P = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_L_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), String.class.getName()
			});
	public static final FinderPath FINDER_PATH_FETCH_BY_G_P_L_P_A = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			JournalContentSearchImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_P_L_P_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			JournalContentSearchModelImpl.GROUPID_COLUMN_BITMASK |
			JournalContentSearchModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			JournalContentSearchModelImpl.LAYOUTID_COLUMN_BITMASK |
			JournalContentSearchModelImpl.PORTLETID_COLUMN_BITMASK |
			JournalContentSearchModelImpl.ARTICLEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P_L_P_A = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_L_P_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED,
			JournalContentSearchImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the journal content search in the entity cache if it is enabled.
	 *
	 * @param journalContentSearch the journal content search
	 */
	public void cacheResult(JournalContentSearch journalContentSearch) {
		EntityCacheUtil.putResult(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchImpl.class,
			journalContentSearch.getPrimaryKey(), journalContentSearch);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_L_P_A,
			new Object[] {
				Long.valueOf(journalContentSearch.getGroupId()),
				Boolean.valueOf(journalContentSearch.getPrivateLayout()),
				Long.valueOf(journalContentSearch.getLayoutId()),
				
			journalContentSearch.getPortletId(),
				
			journalContentSearch.getArticleId()
			}, journalContentSearch);

		journalContentSearch.resetOriginalValues();
	}

	/**
	 * Caches the journal content searchs in the entity cache if it is enabled.
	 *
	 * @param journalContentSearchs the journal content searchs
	 */
	public void cacheResult(List<JournalContentSearch> journalContentSearchs) {
		for (JournalContentSearch journalContentSearch : journalContentSearchs) {
			if (EntityCacheUtil.getResult(
						JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
						JournalContentSearchImpl.class,
						journalContentSearch.getPrimaryKey()) == null) {
				cacheResult(journalContentSearch);
			}
			else {
				journalContentSearch.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all journal content searchs.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(JournalContentSearchImpl.class.getName());
		}

		EntityCacheUtil.clearCache(JournalContentSearchImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the journal content search.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(JournalContentSearch journalContentSearch) {
		EntityCacheUtil.removeResult(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchImpl.class, journalContentSearch.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(journalContentSearch);
	}

	@Override
	public void clearCache(List<JournalContentSearch> journalContentSearchs) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (JournalContentSearch journalContentSearch : journalContentSearchs) {
			EntityCacheUtil.removeResult(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
				JournalContentSearchImpl.class,
				journalContentSearch.getPrimaryKey());

			clearUniqueFindersCache(journalContentSearch);
		}
	}

	protected void clearUniqueFindersCache(
		JournalContentSearch journalContentSearch) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P_L_P_A,
			new Object[] {
				Long.valueOf(journalContentSearch.getGroupId()),
				Boolean.valueOf(journalContentSearch.getPrivateLayout()),
				Long.valueOf(journalContentSearch.getLayoutId()),
				
			journalContentSearch.getPortletId(),
				
			journalContentSearch.getArticleId()
			});
	}

	/**
	 * Creates a new journal content search with the primary key. Does not add the journal content search to the database.
	 *
	 * @param contentSearchId the primary key for the new journal content search
	 * @return the new journal content search
	 */
	public JournalContentSearch create(long contentSearchId) {
		JournalContentSearch journalContentSearch = new JournalContentSearchImpl();

		journalContentSearch.setNew(true);
		journalContentSearch.setPrimaryKey(contentSearchId);

		return journalContentSearch;
	}

	/**
	 * Removes the journal content search with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param contentSearchId the primary key of the journal content search
	 * @return the journal content search that was removed
	 * @throws com.liferay.portlet.journal.NoSuchContentSearchException if a journal content search with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch remove(long contentSearchId)
		throws NoSuchContentSearchException, SystemException {
		return remove(Long.valueOf(contentSearchId));
	}

	/**
	 * Removes the journal content search with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the journal content search
	 * @return the journal content search that was removed
	 * @throws com.liferay.portlet.journal.NoSuchContentSearchException if a journal content search with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public JournalContentSearch remove(Serializable primaryKey)
		throws NoSuchContentSearchException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalContentSearch journalContentSearch = (JournalContentSearch)session.get(JournalContentSearchImpl.class,
					primaryKey);

			if (journalContentSearch == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchContentSearchException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(journalContentSearch);
		}
		catch (NoSuchContentSearchException nsee) {
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
	protected JournalContentSearch removeImpl(
		JournalContentSearch journalContentSearch) throws SystemException {
		journalContentSearch = toUnwrappedModel(journalContentSearch);

		Session session = null;

		try {
			session = openSession();

			if (journalContentSearch.isCachedModel()) {
				journalContentSearch = (JournalContentSearch)session.get(JournalContentSearchImpl.class,
						journalContentSearch.getPrimaryKeyObj());
			}

			if (journalContentSearch != null) {
				session.delete(journalContentSearch);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (journalContentSearch != null) {
			clearCache(journalContentSearch);
		}

		return journalContentSearch;
	}

	@Override
	public JournalContentSearch updateImpl(
		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch)
		throws SystemException {
		journalContentSearch = toUnwrappedModel(journalContentSearch);

		boolean isNew = journalContentSearch.isNew();

		JournalContentSearchModelImpl journalContentSearchModelImpl = (JournalContentSearchModelImpl)journalContentSearch;

		Session session = null;

		try {
			session = openSession();

			if (journalContentSearch.isNew()) {
				session.save(journalContentSearch);

				journalContentSearch.setNew(false);
			}
			else {
				session.merge(journalContentSearch);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !JournalContentSearchModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((journalContentSearchModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PORTLETID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalContentSearchModelImpl.getOriginalPortletId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PORTLETID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PORTLETID,
					args);

				args = new Object[] { journalContentSearchModelImpl.getPortletId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PORTLETID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PORTLETID,
					args);
			}

			if ((journalContentSearchModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ARTICLEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalContentSearchModelImpl.getOriginalArticleId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ARTICLEID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ARTICLEID,
					args);

				args = new Object[] { journalContentSearchModelImpl.getArticleId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ARTICLEID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ARTICLEID,
					args);
			}

			if ((journalContentSearchModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(journalContentSearchModelImpl.getOriginalGroupId()),
						Boolean.valueOf(journalContentSearchModelImpl.getOriginalPrivateLayout())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P,
					args);

				args = new Object[] {
						Long.valueOf(journalContentSearchModelImpl.getGroupId()),
						Boolean.valueOf(journalContentSearchModelImpl.getPrivateLayout())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P,
					args);
			}

			if ((journalContentSearchModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(journalContentSearchModelImpl.getOriginalGroupId()),
						
						journalContentSearchModelImpl.getOriginalArticleId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A,
					args);

				args = new Object[] {
						Long.valueOf(journalContentSearchModelImpl.getGroupId()),
						
						journalContentSearchModelImpl.getArticleId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A,
					args);
			}

			if ((journalContentSearchModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_L.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(journalContentSearchModelImpl.getOriginalGroupId()),
						Boolean.valueOf(journalContentSearchModelImpl.getOriginalPrivateLayout()),
						Long.valueOf(journalContentSearchModelImpl.getOriginalLayoutId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P_L, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_L,
					args);

				args = new Object[] {
						Long.valueOf(journalContentSearchModelImpl.getGroupId()),
						Boolean.valueOf(journalContentSearchModelImpl.getPrivateLayout()),
						Long.valueOf(journalContentSearchModelImpl.getLayoutId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P_L, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_L,
					args);
			}

			if ((journalContentSearchModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(journalContentSearchModelImpl.getOriginalGroupId()),
						Boolean.valueOf(journalContentSearchModelImpl.getOriginalPrivateLayout()),
						
						journalContentSearchModelImpl.getOriginalArticleId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_A,
					args);

				args = new Object[] {
						Long.valueOf(journalContentSearchModelImpl.getGroupId()),
						Boolean.valueOf(journalContentSearchModelImpl.getPrivateLayout()),
						
						journalContentSearchModelImpl.getArticleId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_A,
					args);
			}

			if ((journalContentSearchModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_L_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(journalContentSearchModelImpl.getOriginalGroupId()),
						Boolean.valueOf(journalContentSearchModelImpl.getOriginalPrivateLayout()),
						Long.valueOf(journalContentSearchModelImpl.getOriginalLayoutId()),
						
						journalContentSearchModelImpl.getOriginalPortletId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P_L_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_L_P,
					args);

				args = new Object[] {
						Long.valueOf(journalContentSearchModelImpl.getGroupId()),
						Boolean.valueOf(journalContentSearchModelImpl.getPrivateLayout()),
						Long.valueOf(journalContentSearchModelImpl.getLayoutId()),
						
						journalContentSearchModelImpl.getPortletId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P_L_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_L_P,
					args);
			}
		}

		EntityCacheUtil.putResult(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
			JournalContentSearchImpl.class,
			journalContentSearch.getPrimaryKey(), journalContentSearch);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_L_P_A,
				new Object[] {
					Long.valueOf(journalContentSearch.getGroupId()),
					Boolean.valueOf(journalContentSearch.getPrivateLayout()),
					Long.valueOf(journalContentSearch.getLayoutId()),
					
				journalContentSearch.getPortletId(),
					
				journalContentSearch.getArticleId()
				}, journalContentSearch);
		}
		else {
			if ((journalContentSearchModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_P_L_P_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(journalContentSearchModelImpl.getOriginalGroupId()),
						Boolean.valueOf(journalContentSearchModelImpl.getOriginalPrivateLayout()),
						Long.valueOf(journalContentSearchModelImpl.getOriginalLayoutId()),
						
						journalContentSearchModelImpl.getOriginalPortletId(),
						
						journalContentSearchModelImpl.getOriginalArticleId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P_L_P_A,
					args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P_L_P_A,
					args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_L_P_A,
					new Object[] {
						Long.valueOf(journalContentSearch.getGroupId()),
						Boolean.valueOf(journalContentSearch.getPrivateLayout()),
						Long.valueOf(journalContentSearch.getLayoutId()),
						
					journalContentSearch.getPortletId(),
						
					journalContentSearch.getArticleId()
					}, journalContentSearch);
			}
		}

		return journalContentSearch;
	}

	protected JournalContentSearch toUnwrappedModel(
		JournalContentSearch journalContentSearch) {
		if (journalContentSearch instanceof JournalContentSearchImpl) {
			return journalContentSearch;
		}

		JournalContentSearchImpl journalContentSearchImpl = new JournalContentSearchImpl();

		journalContentSearchImpl.setNew(journalContentSearch.isNew());
		journalContentSearchImpl.setPrimaryKey(journalContentSearch.getPrimaryKey());

		journalContentSearchImpl.setContentSearchId(journalContentSearch.getContentSearchId());
		journalContentSearchImpl.setGroupId(journalContentSearch.getGroupId());
		journalContentSearchImpl.setCompanyId(journalContentSearch.getCompanyId());
		journalContentSearchImpl.setPrivateLayout(journalContentSearch.isPrivateLayout());
		journalContentSearchImpl.setLayoutId(journalContentSearch.getLayoutId());
		journalContentSearchImpl.setPortletId(journalContentSearch.getPortletId());
		journalContentSearchImpl.setArticleId(journalContentSearch.getArticleId());

		return journalContentSearchImpl;
	}

	/**
	 * Returns the journal content search with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the journal content search
	 * @return the journal content search
	 * @throws com.liferay.portal.NoSuchModelException if a journal content search with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public JournalContentSearch findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the journal content search with the primary key or throws a {@link com.liferay.portlet.journal.NoSuchContentSearchException} if it could not be found.
	 *
	 * @param contentSearchId the primary key of the journal content search
	 * @return the journal content search
	 * @throws com.liferay.portlet.journal.NoSuchContentSearchException if a journal content search with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch findByPrimaryKey(long contentSearchId)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = fetchByPrimaryKey(contentSearchId);

		if (journalContentSearch == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + contentSearchId);
			}

			throw new NoSuchContentSearchException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				contentSearchId);
		}

		return journalContentSearch;
	}

	/**
	 * Returns the journal content search with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the journal content search
	 * @return the journal content search, or <code>null</code> if a journal content search with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public JournalContentSearch fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the journal content search with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param contentSearchId the primary key of the journal content search
	 * @return the journal content search, or <code>null</code> if a journal content search with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch fetchByPrimaryKey(long contentSearchId)
		throws SystemException {
		JournalContentSearch journalContentSearch = (JournalContentSearch)EntityCacheUtil.getResult(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
				JournalContentSearchImpl.class, contentSearchId);

		if (journalContentSearch == _nullJournalContentSearch) {
			return null;
		}

		if (journalContentSearch == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				journalContentSearch = (JournalContentSearch)session.get(JournalContentSearchImpl.class,
						Long.valueOf(contentSearchId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (journalContentSearch != null) {
					cacheResult(journalContentSearch);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(JournalContentSearchModelImpl.ENTITY_CACHE_ENABLED,
						JournalContentSearchImpl.class, contentSearchId,
						_nullJournalContentSearch);
				}

				closeSession(session);
			}
		}

		return journalContentSearch;
	}

	/**
	 * Returns all the journal content searchs where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @return the matching journal content searchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalContentSearch> findByPortletId(String portletId)
		throws SystemException {
		return findByPortletId(portletId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal content searchs where portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of journal content searchs
	 * @param end the upper bound of the range of journal content searchs (not inclusive)
	 * @return the range of matching journal content searchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalContentSearch> findByPortletId(String portletId,
		int start, int end) throws SystemException {
		return findByPortletId(portletId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal content searchs where portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of journal content searchs
	 * @param end the upper bound of the range of journal content searchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal content searchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalContentSearch> findByPortletId(String portletId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PORTLETID;
			finderArgs = new Object[] { portletId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_PORTLETID;
			finderArgs = new Object[] { portletId, start, end, orderByComparator };
		}

		List<JournalContentSearch> list = (List<JournalContentSearch>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalContentSearch journalContentSearch : list) {
				if (!Validator.equals(portletId,
							journalContentSearch.getPortletId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE);

			if (portletId == null) {
				query.append(_FINDER_COLUMN_PORTLETID_PORTLETID_1);
			}
			else {
				if (portletId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_PORTLETID_PORTLETID_3);
				}
				else {
					query.append(_FINDER_COLUMN_PORTLETID_PORTLETID_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (portletId != null) {
					qPos.add(portletId);
				}

				list = (List<JournalContentSearch>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first journal content search in the ordered set where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search
	 * @throws com.liferay.portlet.journal.NoSuchContentSearchException if a matching journal content search could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch findByPortletId_First(String portletId,
		OrderByComparator orderByComparator)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = fetchByPortletId_First(portletId,
				orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("portletId=");
		msg.append(portletId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchContentSearchException(msg.toString());
	}

	/**
	 * Returns the first journal content search in the ordered set where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch fetchByPortletId_First(String portletId,
		OrderByComparator orderByComparator) throws SystemException {
		List<JournalContentSearch> list = findByPortletId(portletId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal content search in the ordered set where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal content search
	 * @throws com.liferay.portlet.journal.NoSuchContentSearchException if a matching journal content search could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch findByPortletId_Last(String portletId,
		OrderByComparator orderByComparator)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = fetchByPortletId_Last(portletId,
				orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("portletId=");
		msg.append(portletId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchContentSearchException(msg.toString());
	}

	/**
	 * Returns the last journal content search in the ordered set where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch fetchByPortletId_Last(String portletId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByPortletId(portletId);

		List<JournalContentSearch> list = findByPortletId(portletId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal content searchs before and after the current journal content search in the ordered set where portletId = &#63;.
	 *
	 * @param contentSearchId the primary key of the current journal content search
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal content search
	 * @throws com.liferay.portlet.journal.NoSuchContentSearchException if a journal content search with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch[] findByPortletId_PrevAndNext(
		long contentSearchId, String portletId,
		OrderByComparator orderByComparator)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = findByPrimaryKey(contentSearchId);

		Session session = null;

		try {
			session = openSession();

			JournalContentSearch[] array = new JournalContentSearchImpl[3];

			array[0] = getByPortletId_PrevAndNext(session,
					journalContentSearch, portletId, orderByComparator, true);

			array[1] = journalContentSearch;

			array[2] = getByPortletId_PrevAndNext(session,
					journalContentSearch, portletId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalContentSearch getByPortletId_PrevAndNext(Session session,
		JournalContentSearch journalContentSearch, String portletId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE);

		if (portletId == null) {
			query.append(_FINDER_COLUMN_PORTLETID_PORTLETID_1);
		}
		else {
			if (portletId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_PORTLETID_PORTLETID_3);
			}
			else {
				query.append(_FINDER_COLUMN_PORTLETID_PORTLETID_2);
			}
		}

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (portletId != null) {
			qPos.add(portletId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalContentSearch);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalContentSearch> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal content searchs where articleId = &#63;.
	 *
	 * @param articleId the article ID
	 * @return the matching journal content searchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalContentSearch> findByArticleId(String articleId)
		throws SystemException {
		return findByArticleId(articleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal content searchs where articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal content searchs
	 * @param end the upper bound of the range of journal content searchs (not inclusive)
	 * @return the range of matching journal content searchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalContentSearch> findByArticleId(String articleId,
		int start, int end) throws SystemException {
		return findByArticleId(articleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal content searchs where articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal content searchs
	 * @param end the upper bound of the range of journal content searchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal content searchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalContentSearch> findByArticleId(String articleId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ARTICLEID;
			finderArgs = new Object[] { articleId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ARTICLEID;
			finderArgs = new Object[] { articleId, start, end, orderByComparator };
		}

		List<JournalContentSearch> list = (List<JournalContentSearch>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalContentSearch journalContentSearch : list) {
				if (!Validator.equals(articleId,
							journalContentSearch.getArticleId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(2);
			}

			query.append(_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE);

			if (articleId == null) {
				query.append(_FINDER_COLUMN_ARTICLEID_ARTICLEID_1);
			}
			else {
				if (articleId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_ARTICLEID_ARTICLEID_3);
				}
				else {
					query.append(_FINDER_COLUMN_ARTICLEID_ARTICLEID_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (articleId != null) {
					qPos.add(articleId);
				}

				list = (List<JournalContentSearch>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first journal content search in the ordered set where articleId = &#63;.
	 *
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search
	 * @throws com.liferay.portlet.journal.NoSuchContentSearchException if a matching journal content search could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch findByArticleId_First(String articleId,
		OrderByComparator orderByComparator)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = fetchByArticleId_First(articleId,
				orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("articleId=");
		msg.append(articleId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchContentSearchException(msg.toString());
	}

	/**
	 * Returns the first journal content search in the ordered set where articleId = &#63;.
	 *
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch fetchByArticleId_First(String articleId,
		OrderByComparator orderByComparator) throws SystemException {
		List<JournalContentSearch> list = findByArticleId(articleId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal content search in the ordered set where articleId = &#63;.
	 *
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal content search
	 * @throws com.liferay.portlet.journal.NoSuchContentSearchException if a matching journal content search could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch findByArticleId_Last(String articleId,
		OrderByComparator orderByComparator)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = fetchByArticleId_Last(articleId,
				orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("articleId=");
		msg.append(articleId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchContentSearchException(msg.toString());
	}

	/**
	 * Returns the last journal content search in the ordered set where articleId = &#63;.
	 *
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch fetchByArticleId_Last(String articleId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByArticleId(articleId);

		List<JournalContentSearch> list = findByArticleId(articleId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal content searchs before and after the current journal content search in the ordered set where articleId = &#63;.
	 *
	 * @param contentSearchId the primary key of the current journal content search
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal content search
	 * @throws com.liferay.portlet.journal.NoSuchContentSearchException if a journal content search with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch[] findByArticleId_PrevAndNext(
		long contentSearchId, String articleId,
		OrderByComparator orderByComparator)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = findByPrimaryKey(contentSearchId);

		Session session = null;

		try {
			session = openSession();

			JournalContentSearch[] array = new JournalContentSearchImpl[3];

			array[0] = getByArticleId_PrevAndNext(session,
					journalContentSearch, articleId, orderByComparator, true);

			array[1] = journalContentSearch;

			array[2] = getByArticleId_PrevAndNext(session,
					journalContentSearch, articleId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalContentSearch getByArticleId_PrevAndNext(Session session,
		JournalContentSearch journalContentSearch, String articleId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE);

		if (articleId == null) {
			query.append(_FINDER_COLUMN_ARTICLEID_ARTICLEID_1);
		}
		else {
			if (articleId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_ARTICLEID_ARTICLEID_3);
			}
			else {
				query.append(_FINDER_COLUMN_ARTICLEID_ARTICLEID_2);
			}
		}

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (articleId != null) {
			qPos.add(articleId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalContentSearch);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalContentSearch> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal content searchs where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the matching journal content searchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalContentSearch> findByG_P(long groupId,
		boolean privateLayout) throws SystemException {
		return findByG_P(groupId, privateLayout, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal content searchs where groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of journal content searchs
	 * @param end the upper bound of the range of journal content searchs (not inclusive)
	 * @return the range of matching journal content searchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalContentSearch> findByG_P(long groupId,
		boolean privateLayout, int start, int end) throws SystemException {
		return findByG_P(groupId, privateLayout, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal content searchs where groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of journal content searchs
	 * @param end the upper bound of the range of journal content searchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal content searchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalContentSearch> findByG_P(long groupId,
		boolean privateLayout, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P;
			finderArgs = new Object[] { groupId, privateLayout };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P;
			finderArgs = new Object[] {
					groupId, privateLayout,
					
					start, end, orderByComparator
				};
		}

		List<JournalContentSearch> list = (List<JournalContentSearch>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalContentSearch journalContentSearch : list) {
				if ((groupId != journalContentSearch.getGroupId()) ||
						(privateLayout != journalContentSearch.getPrivateLayout())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_PRIVATELAYOUT_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				list = (List<JournalContentSearch>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search
	 * @throws com.liferay.portlet.journal.NoSuchContentSearchException if a matching journal content search could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch findByG_P_First(long groupId,
		boolean privateLayout, OrderByComparator orderByComparator)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = fetchByG_P_First(groupId,
				privateLayout, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchContentSearchException(msg.toString());
	}

	/**
	 * Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch fetchByG_P_First(long groupId,
		boolean privateLayout, OrderByComparator orderByComparator)
		throws SystemException {
		List<JournalContentSearch> list = findByG_P(groupId, privateLayout, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal content search
	 * @throws com.liferay.portlet.journal.NoSuchContentSearchException if a matching journal content search could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch findByG_P_Last(long groupId,
		boolean privateLayout, OrderByComparator orderByComparator)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = fetchByG_P_Last(groupId,
				privateLayout, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchContentSearchException(msg.toString());
	}

	/**
	 * Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch fetchByG_P_Last(long groupId,
		boolean privateLayout, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_P(groupId, privateLayout);

		List<JournalContentSearch> list = findByG_P(groupId, privateLayout,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal content searchs before and after the current journal content search in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param contentSearchId the primary key of the current journal content search
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal content search
	 * @throws com.liferay.portlet.journal.NoSuchContentSearchException if a journal content search with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch[] findByG_P_PrevAndNext(long contentSearchId,
		long groupId, boolean privateLayout, OrderByComparator orderByComparator)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = findByPrimaryKey(contentSearchId);

		Session session = null;

		try {
			session = openSession();

			JournalContentSearch[] array = new JournalContentSearchImpl[3];

			array[0] = getByG_P_PrevAndNext(session, journalContentSearch,
					groupId, privateLayout, orderByComparator, true);

			array[1] = journalContentSearch;

			array[2] = getByG_P_PrevAndNext(session, journalContentSearch,
					groupId, privateLayout, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalContentSearch getByG_P_PrevAndNext(Session session,
		JournalContentSearch journalContentSearch, long groupId,
		boolean privateLayout, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE);

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PRIVATELAYOUT_2);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(privateLayout);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalContentSearch);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalContentSearch> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal content searchs where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @return the matching journal content searchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalContentSearch> findByG_A(long groupId, String articleId)
		throws SystemException {
		return findByG_A(groupId, articleId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal content searchs where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal content searchs
	 * @param end the upper bound of the range of journal content searchs (not inclusive)
	 * @return the range of matching journal content searchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalContentSearch> findByG_A(long groupId, String articleId,
		int start, int end) throws SystemException {
		return findByG_A(groupId, articleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal content searchs where groupId = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal content searchs
	 * @param end the upper bound of the range of journal content searchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal content searchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalContentSearch> findByG_A(long groupId, String articleId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A;
			finderArgs = new Object[] { groupId, articleId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A;
			finderArgs = new Object[] {
					groupId, articleId,
					
					start, end, orderByComparator
				};
		}

		List<JournalContentSearch> list = (List<JournalContentSearch>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalContentSearch journalContentSearch : list) {
				if ((groupId != journalContentSearch.getGroupId()) ||
						!Validator.equals(articleId,
							journalContentSearch.getArticleId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE);

			query.append(_FINDER_COLUMN_G_A_GROUPID_2);

			if (articleId == null) {
				query.append(_FINDER_COLUMN_G_A_ARTICLEID_1);
			}
			else {
				if (articleId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_A_ARTICLEID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_A_ARTICLEID_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (articleId != null) {
					qPos.add(articleId);
				}

				list = (List<JournalContentSearch>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first journal content search in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search
	 * @throws com.liferay.portlet.journal.NoSuchContentSearchException if a matching journal content search could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch findByG_A_First(long groupId, String articleId,
		OrderByComparator orderByComparator)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = fetchByG_A_First(groupId,
				articleId, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", articleId=");
		msg.append(articleId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchContentSearchException(msg.toString());
	}

	/**
	 * Returns the first journal content search in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch fetchByG_A_First(long groupId,
		String articleId, OrderByComparator orderByComparator)
		throws SystemException {
		List<JournalContentSearch> list = findByG_A(groupId, articleId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal content search in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal content search
	 * @throws com.liferay.portlet.journal.NoSuchContentSearchException if a matching journal content search could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch findByG_A_Last(long groupId, String articleId,
		OrderByComparator orderByComparator)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = fetchByG_A_Last(groupId,
				articleId, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", articleId=");
		msg.append(articleId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchContentSearchException(msg.toString());
	}

	/**
	 * Returns the last journal content search in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch fetchByG_A_Last(long groupId, String articleId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_A(groupId, articleId);

		List<JournalContentSearch> list = findByG_A(groupId, articleId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal content searchs before and after the current journal content search in the ordered set where groupId = &#63; and articleId = &#63;.
	 *
	 * @param contentSearchId the primary key of the current journal content search
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal content search
	 * @throws com.liferay.portlet.journal.NoSuchContentSearchException if a journal content search with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch[] findByG_A_PrevAndNext(long contentSearchId,
		long groupId, String articleId, OrderByComparator orderByComparator)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = findByPrimaryKey(contentSearchId);

		Session session = null;

		try {
			session = openSession();

			JournalContentSearch[] array = new JournalContentSearchImpl[3];

			array[0] = getByG_A_PrevAndNext(session, journalContentSearch,
					groupId, articleId, orderByComparator, true);

			array[1] = journalContentSearch;

			array[2] = getByG_A_PrevAndNext(session, journalContentSearch,
					groupId, articleId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalContentSearch getByG_A_PrevAndNext(Session session,
		JournalContentSearch journalContentSearch, long groupId,
		String articleId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE);

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		if (articleId == null) {
			query.append(_FINDER_COLUMN_G_A_ARTICLEID_1);
		}
		else {
			if (articleId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_A_ARTICLEID_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_A_ARTICLEID_2);
			}
		}

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (articleId != null) {
			qPos.add(articleId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalContentSearch);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalContentSearch> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the matching journal content searchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalContentSearch> findByG_P_L(long groupId,
		boolean privateLayout, long layoutId) throws SystemException {
		return findByG_P_L(groupId, privateLayout, layoutId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param start the lower bound of the range of journal content searchs
	 * @param end the upper bound of the range of journal content searchs (not inclusive)
	 * @return the range of matching journal content searchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalContentSearch> findByG_P_L(long groupId,
		boolean privateLayout, long layoutId, int start, int end)
		throws SystemException {
		return findByG_P_L(groupId, privateLayout, layoutId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param start the lower bound of the range of journal content searchs
	 * @param end the upper bound of the range of journal content searchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal content searchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalContentSearch> findByG_P_L(long groupId,
		boolean privateLayout, long layoutId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_L;
			finderArgs = new Object[] { groupId, privateLayout, layoutId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P_L;
			finderArgs = new Object[] {
					groupId, privateLayout, layoutId,
					
					start, end, orderByComparator
				};
		}

		List<JournalContentSearch> list = (List<JournalContentSearch>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalContentSearch journalContentSearch : list) {
				if ((groupId != journalContentSearch.getGroupId()) ||
						(privateLayout != journalContentSearch.getPrivateLayout()) ||
						(layoutId != journalContentSearch.getLayoutId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE);

			query.append(_FINDER_COLUMN_G_P_L_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_L_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_G_P_L_LAYOUTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(layoutId);

				list = (List<JournalContentSearch>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search
	 * @throws com.liferay.portlet.journal.NoSuchContentSearchException if a matching journal content search could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch findByG_P_L_First(long groupId,
		boolean privateLayout, long layoutId,
		OrderByComparator orderByComparator)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = fetchByG_P_L_First(groupId,
				privateLayout, layoutId, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", layoutId=");
		msg.append(layoutId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchContentSearchException(msg.toString());
	}

	/**
	 * Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch fetchByG_P_L_First(long groupId,
		boolean privateLayout, long layoutId,
		OrderByComparator orderByComparator) throws SystemException {
		List<JournalContentSearch> list = findByG_P_L(groupId, privateLayout,
				layoutId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal content search
	 * @throws com.liferay.portlet.journal.NoSuchContentSearchException if a matching journal content search could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch findByG_P_L_Last(long groupId,
		boolean privateLayout, long layoutId,
		OrderByComparator orderByComparator)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = fetchByG_P_L_Last(groupId,
				privateLayout, layoutId, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", layoutId=");
		msg.append(layoutId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchContentSearchException(msg.toString());
	}

	/**
	 * Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch fetchByG_P_L_Last(long groupId,
		boolean privateLayout, long layoutId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_P_L(groupId, privateLayout, layoutId);

		List<JournalContentSearch> list = findByG_P_L(groupId, privateLayout,
				layoutId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal content searchs before and after the current journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param contentSearchId the primary key of the current journal content search
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal content search
	 * @throws com.liferay.portlet.journal.NoSuchContentSearchException if a journal content search with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch[] findByG_P_L_PrevAndNext(
		long contentSearchId, long groupId, boolean privateLayout,
		long layoutId, OrderByComparator orderByComparator)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = findByPrimaryKey(contentSearchId);

		Session session = null;

		try {
			session = openSession();

			JournalContentSearch[] array = new JournalContentSearchImpl[3];

			array[0] = getByG_P_L_PrevAndNext(session, journalContentSearch,
					groupId, privateLayout, layoutId, orderByComparator, true);

			array[1] = journalContentSearch;

			array[2] = getByG_P_L_PrevAndNext(session, journalContentSearch,
					groupId, privateLayout, layoutId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalContentSearch getByG_P_L_PrevAndNext(Session session,
		JournalContentSearch journalContentSearch, long groupId,
		boolean privateLayout, long layoutId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE);

		query.append(_FINDER_COLUMN_G_P_L_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_L_PRIVATELAYOUT_2);

		query.append(_FINDER_COLUMN_G_P_L_LAYOUTID_2);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(privateLayout);

		qPos.add(layoutId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalContentSearch);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalContentSearch> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal content searchs where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param articleId the article ID
	 * @return the matching journal content searchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalContentSearch> findByG_P_A(long groupId,
		boolean privateLayout, String articleId) throws SystemException {
		return findByG_P_A(groupId, privateLayout, articleId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal content searchs where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal content searchs
	 * @param end the upper bound of the range of journal content searchs (not inclusive)
	 * @return the range of matching journal content searchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalContentSearch> findByG_P_A(long groupId,
		boolean privateLayout, String articleId, int start, int end)
		throws SystemException {
		return findByG_P_A(groupId, privateLayout, articleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal content searchs where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param articleId the article ID
	 * @param start the lower bound of the range of journal content searchs
	 * @param end the upper bound of the range of journal content searchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal content searchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalContentSearch> findByG_P_A(long groupId,
		boolean privateLayout, String articleId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_A;
			finderArgs = new Object[] { groupId, privateLayout, articleId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P_A;
			finderArgs = new Object[] {
					groupId, privateLayout, articleId,
					
					start, end, orderByComparator
				};
		}

		List<JournalContentSearch> list = (List<JournalContentSearch>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalContentSearch journalContentSearch : list) {
				if ((groupId != journalContentSearch.getGroupId()) ||
						(privateLayout != journalContentSearch.getPrivateLayout()) ||
						!Validator.equals(articleId,
							journalContentSearch.getArticleId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE);

			query.append(_FINDER_COLUMN_G_P_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_A_PRIVATELAYOUT_2);

			if (articleId == null) {
				query.append(_FINDER_COLUMN_G_P_A_ARTICLEID_1);
			}
			else {
				if (articleId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_P_A_ARTICLEID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_P_A_ARTICLEID_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				if (articleId != null) {
					qPos.add(articleId);
				}

				list = (List<JournalContentSearch>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search
	 * @throws com.liferay.portlet.journal.NoSuchContentSearchException if a matching journal content search could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch findByG_P_A_First(long groupId,
		boolean privateLayout, String articleId,
		OrderByComparator orderByComparator)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = fetchByG_P_A_First(groupId,
				privateLayout, articleId, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", articleId=");
		msg.append(articleId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchContentSearchException(msg.toString());
	}

	/**
	 * Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch fetchByG_P_A_First(long groupId,
		boolean privateLayout, String articleId,
		OrderByComparator orderByComparator) throws SystemException {
		List<JournalContentSearch> list = findByG_P_A(groupId, privateLayout,
				articleId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal content search
	 * @throws com.liferay.portlet.journal.NoSuchContentSearchException if a matching journal content search could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch findByG_P_A_Last(long groupId,
		boolean privateLayout, String articleId,
		OrderByComparator orderByComparator)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = fetchByG_P_A_Last(groupId,
				privateLayout, articleId, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", articleId=");
		msg.append(articleId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchContentSearchException(msg.toString());
	}

	/**
	 * Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch fetchByG_P_A_Last(long groupId,
		boolean privateLayout, String articleId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_P_A(groupId, privateLayout, articleId);

		List<JournalContentSearch> list = findByG_P_A(groupId, privateLayout,
				articleId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal content searchs before and after the current journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	 *
	 * @param contentSearchId the primary key of the current journal content search
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal content search
	 * @throws com.liferay.portlet.journal.NoSuchContentSearchException if a journal content search with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch[] findByG_P_A_PrevAndNext(
		long contentSearchId, long groupId, boolean privateLayout,
		String articleId, OrderByComparator orderByComparator)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = findByPrimaryKey(contentSearchId);

		Session session = null;

		try {
			session = openSession();

			JournalContentSearch[] array = new JournalContentSearchImpl[3];

			array[0] = getByG_P_A_PrevAndNext(session, journalContentSearch,
					groupId, privateLayout, articleId, orderByComparator, true);

			array[1] = journalContentSearch;

			array[2] = getByG_P_A_PrevAndNext(session, journalContentSearch,
					groupId, privateLayout, articleId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalContentSearch getByG_P_A_PrevAndNext(Session session,
		JournalContentSearch journalContentSearch, long groupId,
		boolean privateLayout, String articleId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE);

		query.append(_FINDER_COLUMN_G_P_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_A_PRIVATELAYOUT_2);

		if (articleId == null) {
			query.append(_FINDER_COLUMN_G_P_A_ARTICLEID_1);
		}
		else {
			if (articleId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_P_A_ARTICLEID_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_P_A_ARTICLEID_2);
			}
		}

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(privateLayout);

		if (articleId != null) {
			qPos.add(articleId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalContentSearch);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalContentSearch> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @return the matching journal content searchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalContentSearch> findByG_P_L_P(long groupId,
		boolean privateLayout, long layoutId, String portletId)
		throws SystemException {
		return findByG_P_L_P(groupId, privateLayout, layoutId, portletId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of journal content searchs
	 * @param end the upper bound of the range of journal content searchs (not inclusive)
	 * @return the range of matching journal content searchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalContentSearch> findByG_P_L_P(long groupId,
		boolean privateLayout, long layoutId, String portletId, int start,
		int end) throws SystemException {
		return findByG_P_L_P(groupId, privateLayout, layoutId, portletId,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of journal content searchs
	 * @param end the upper bound of the range of journal content searchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal content searchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalContentSearch> findByG_P_L_P(long groupId,
		boolean privateLayout, long layoutId, String portletId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P_L_P;
			finderArgs = new Object[] {
					groupId, privateLayout, layoutId, portletId
				};
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P_L_P;
			finderArgs = new Object[] {
					groupId, privateLayout, layoutId, portletId,
					
					start, end, orderByComparator
				};
		}

		List<JournalContentSearch> list = (List<JournalContentSearch>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalContentSearch journalContentSearch : list) {
				if ((groupId != journalContentSearch.getGroupId()) ||
						(privateLayout != journalContentSearch.getPrivateLayout()) ||
						(layoutId != journalContentSearch.getLayoutId()) ||
						!Validator.equals(portletId,
							journalContentSearch.getPortletId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE);

			query.append(_FINDER_COLUMN_G_P_L_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_L_P_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_G_P_L_P_LAYOUTID_2);

			if (portletId == null) {
				query.append(_FINDER_COLUMN_G_P_L_P_PORTLETID_1);
			}
			else {
				if (portletId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_P_L_P_PORTLETID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_P_L_P_PORTLETID_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(layoutId);

				if (portletId != null) {
					qPos.add(portletId);
				}

				list = (List<JournalContentSearch>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search
	 * @throws com.liferay.portlet.journal.NoSuchContentSearchException if a matching journal content search could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch findByG_P_L_P_First(long groupId,
		boolean privateLayout, long layoutId, String portletId,
		OrderByComparator orderByComparator)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = fetchByG_P_L_P_First(groupId,
				privateLayout, layoutId, portletId, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", layoutId=");
		msg.append(layoutId);

		msg.append(", portletId=");
		msg.append(portletId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchContentSearchException(msg.toString());
	}

	/**
	 * Returns the first journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch fetchByG_P_L_P_First(long groupId,
		boolean privateLayout, long layoutId, String portletId,
		OrderByComparator orderByComparator) throws SystemException {
		List<JournalContentSearch> list = findByG_P_L_P(groupId, privateLayout,
				layoutId, portletId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal content search
	 * @throws com.liferay.portlet.journal.NoSuchContentSearchException if a matching journal content search could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch findByG_P_L_P_Last(long groupId,
		boolean privateLayout, long layoutId, String portletId,
		OrderByComparator orderByComparator)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = fetchByG_P_L_P_Last(groupId,
				privateLayout, layoutId, portletId, orderByComparator);

		if (journalContentSearch != null) {
			return journalContentSearch;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", layoutId=");
		msg.append(layoutId);

		msg.append(", portletId=");
		msg.append(portletId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchContentSearchException(msg.toString());
	}

	/**
	 * Returns the last journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch fetchByG_P_L_P_Last(long groupId,
		boolean privateLayout, long layoutId, String portletId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_P_L_P(groupId, privateLayout, layoutId, portletId);

		List<JournalContentSearch> list = findByG_P_L_P(groupId, privateLayout,
				layoutId, portletId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal content searchs before and after the current journal content search in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	 *
	 * @param contentSearchId the primary key of the current journal content search
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal content search
	 * @throws com.liferay.portlet.journal.NoSuchContentSearchException if a journal content search with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch[] findByG_P_L_P_PrevAndNext(
		long contentSearchId, long groupId, boolean privateLayout,
		long layoutId, String portletId, OrderByComparator orderByComparator)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = findByPrimaryKey(contentSearchId);

		Session session = null;

		try {
			session = openSession();

			JournalContentSearch[] array = new JournalContentSearchImpl[3];

			array[0] = getByG_P_L_P_PrevAndNext(session, journalContentSearch,
					groupId, privateLayout, layoutId, portletId,
					orderByComparator, true);

			array[1] = journalContentSearch;

			array[2] = getByG_P_L_P_PrevAndNext(session, journalContentSearch,
					groupId, privateLayout, layoutId, portletId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalContentSearch getByG_P_L_P_PrevAndNext(Session session,
		JournalContentSearch journalContentSearch, long groupId,
		boolean privateLayout, long layoutId, String portletId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE);

		query.append(_FINDER_COLUMN_G_P_L_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_L_P_PRIVATELAYOUT_2);

		query.append(_FINDER_COLUMN_G_P_L_P_LAYOUTID_2);

		if (portletId == null) {
			query.append(_FINDER_COLUMN_G_P_L_P_PORTLETID_1);
		}
		else {
			if (portletId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_P_L_P_PORTLETID_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_P_L_P_PORTLETID_2);
			}
		}

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(privateLayout);

		qPos.add(layoutId);

		if (portletId != null) {
			qPos.add(portletId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalContentSearch);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalContentSearch> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the journal content search where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; and articleId = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchContentSearchException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param articleId the article ID
	 * @return the matching journal content search
	 * @throws com.liferay.portlet.journal.NoSuchContentSearchException if a matching journal content search could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch findByG_P_L_P_A(long groupId,
		boolean privateLayout, long layoutId, String portletId, String articleId)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = fetchByG_P_L_P_A(groupId,
				privateLayout, layoutId, portletId, articleId);

		if (journalContentSearch == null) {
			StringBundler msg = new StringBundler(12);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", privateLayout=");
			msg.append(privateLayout);

			msg.append(", layoutId=");
			msg.append(layoutId);

			msg.append(", portletId=");
			msg.append(portletId);

			msg.append(", articleId=");
			msg.append(articleId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchContentSearchException(msg.toString());
		}

		return journalContentSearch;
	}

	/**
	 * Returns the journal content search where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; and articleId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param articleId the article ID
	 * @return the matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch fetchByG_P_L_P_A(long groupId,
		boolean privateLayout, long layoutId, String portletId, String articleId)
		throws SystemException {
		return fetchByG_P_L_P_A(groupId, privateLayout, layoutId, portletId,
			articleId, true);
	}

	/**
	 * Returns the journal content search where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; and articleId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param articleId the article ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching journal content search, or <code>null</code> if a matching journal content search could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch fetchByG_P_L_P_A(long groupId,
		boolean privateLayout, long layoutId, String portletId,
		String articleId, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, privateLayout, layoutId, portletId, articleId
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_P_L_P_A,
					finderArgs, this);
		}

		if (result instanceof JournalContentSearch) {
			JournalContentSearch journalContentSearch = (JournalContentSearch)result;

			if ((groupId != journalContentSearch.getGroupId()) ||
					(privateLayout != journalContentSearch.getPrivateLayout()) ||
					(layoutId != journalContentSearch.getLayoutId()) ||
					!Validator.equals(portletId,
						journalContentSearch.getPortletId()) ||
					!Validator.equals(articleId,
						journalContentSearch.getArticleId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_SELECT_JOURNALCONTENTSEARCH_WHERE);

			query.append(_FINDER_COLUMN_G_P_L_P_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_L_P_A_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_G_P_L_P_A_LAYOUTID_2);

			if (portletId == null) {
				query.append(_FINDER_COLUMN_G_P_L_P_A_PORTLETID_1);
			}
			else {
				if (portletId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_P_L_P_A_PORTLETID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_P_L_P_A_PORTLETID_2);
				}
			}

			if (articleId == null) {
				query.append(_FINDER_COLUMN_G_P_L_P_A_ARTICLEID_1);
			}
			else {
				if (articleId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_P_L_P_A_ARTICLEID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_P_L_P_A_ARTICLEID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(layoutId);

				if (portletId != null) {
					qPos.add(portletId);
				}

				if (articleId != null) {
					qPos.add(articleId);
				}

				List<JournalContentSearch> list = q.list();

				result = list;

				JournalContentSearch journalContentSearch = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_L_P_A,
						finderArgs, list);
				}
				else {
					journalContentSearch = list.get(0);

					cacheResult(journalContentSearch);

					if ((journalContentSearch.getGroupId() != groupId) ||
							(journalContentSearch.getPrivateLayout() != privateLayout) ||
							(journalContentSearch.getLayoutId() != layoutId) ||
							(journalContentSearch.getPortletId() == null) ||
							!journalContentSearch.getPortletId()
													 .equals(portletId) ||
							(journalContentSearch.getArticleId() == null) ||
							!journalContentSearch.getArticleId()
													 .equals(articleId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_L_P_A,
							finderArgs, journalContentSearch);
					}
				}

				return journalContentSearch;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P_L_P_A,
						finderArgs);
				}

				closeSession(session);
			}
		}
		else {
			if (result instanceof List<?>) {
				return null;
			}
			else {
				return (JournalContentSearch)result;
			}
		}
	}

	/**
	 * Returns all the journal content searchs.
	 *
	 * @return the journal content searchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalContentSearch> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal content searchs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal content searchs
	 * @param end the upper bound of the range of journal content searchs (not inclusive)
	 * @return the range of journal content searchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalContentSearch> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal content searchs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal content searchs
	 * @param end the upper bound of the range of journal content searchs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of journal content searchs
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalContentSearch> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<JournalContentSearch> list = (List<JournalContentSearch>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_JOURNALCONTENTSEARCH);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_JOURNALCONTENTSEARCH;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<JournalContentSearch>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<JournalContentSearch>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the journal content searchs where portletId = &#63; from the database.
	 *
	 * @param portletId the portlet ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByPortletId(String portletId) throws SystemException {
		for (JournalContentSearch journalContentSearch : findByPortletId(
				portletId)) {
			remove(journalContentSearch);
		}
	}

	/**
	 * Removes all the journal content searchs where articleId = &#63; from the database.
	 *
	 * @param articleId the article ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByArticleId(String articleId) throws SystemException {
		for (JournalContentSearch journalContentSearch : findByArticleId(
				articleId)) {
			remove(journalContentSearch);
		}
	}

	/**
	 * Removes all the journal content searchs where groupId = &#63; and privateLayout = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_P(long groupId, boolean privateLayout)
		throws SystemException {
		for (JournalContentSearch journalContentSearch : findByG_P(groupId,
				privateLayout)) {
			remove(journalContentSearch);
		}
	}

	/**
	 * Removes all the journal content searchs where groupId = &#63; and articleId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_A(long groupId, String articleId)
		throws SystemException {
		for (JournalContentSearch journalContentSearch : findByG_A(groupId,
				articleId)) {
			remove(journalContentSearch);
		}
	}

	/**
	 * Removes all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_P_L(long groupId, boolean privateLayout, long layoutId)
		throws SystemException {
		for (JournalContentSearch journalContentSearch : findByG_P_L(groupId,
				privateLayout, layoutId)) {
			remove(journalContentSearch);
		}
	}

	/**
	 * Removes all the journal content searchs where groupId = &#63; and privateLayout = &#63; and articleId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param articleId the article ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_P_A(long groupId, boolean privateLayout,
		String articleId) throws SystemException {
		for (JournalContentSearch journalContentSearch : findByG_P_A(groupId,
				privateLayout, articleId)) {
			remove(journalContentSearch);
		}
	}

	/**
	 * Removes all the journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_P_L_P(long groupId, boolean privateLayout,
		long layoutId, String portletId) throws SystemException {
		for (JournalContentSearch journalContentSearch : findByG_P_L_P(
				groupId, privateLayout, layoutId, portletId)) {
			remove(journalContentSearch);
		}
	}

	/**
	 * Removes the journal content search where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; and articleId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param articleId the article ID
	 * @return the journal content search that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public JournalContentSearch removeByG_P_L_P_A(long groupId,
		boolean privateLayout, long layoutId, String portletId, String articleId)
		throws NoSuchContentSearchException, SystemException {
		JournalContentSearch journalContentSearch = findByG_P_L_P_A(groupId,
				privateLayout, layoutId, portletId, articleId);

		return remove(journalContentSearch);
	}

	/**
	 * Removes all the journal content searchs from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (JournalContentSearch journalContentSearch : findAll()) {
			remove(journalContentSearch);
		}
	}

	/**
	 * Returns the number of journal content searchs where portletId = &#63;.
	 *
	 * @param portletId the portlet ID
	 * @return the number of matching journal content searchs
	 * @throws SystemException if a system exception occurred
	 */
	public int countByPortletId(String portletId) throws SystemException {
		Object[] finderArgs = new Object[] { portletId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_PORTLETID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALCONTENTSEARCH_WHERE);

			if (portletId == null) {
				query.append(_FINDER_COLUMN_PORTLETID_PORTLETID_1);
			}
			else {
				if (portletId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_PORTLETID_PORTLETID_3);
				}
				else {
					query.append(_FINDER_COLUMN_PORTLETID_PORTLETID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (portletId != null) {
					qPos.add(portletId);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_PORTLETID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of journal content searchs where articleId = &#63;.
	 *
	 * @param articleId the article ID
	 * @return the number of matching journal content searchs
	 * @throws SystemException if a system exception occurred
	 */
	public int countByArticleId(String articleId) throws SystemException {
		Object[] finderArgs = new Object[] { articleId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ARTICLEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALCONTENTSEARCH_WHERE);

			if (articleId == null) {
				query.append(_FINDER_COLUMN_ARTICLEID_ARTICLEID_1);
			}
			else {
				if (articleId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_ARTICLEID_ARTICLEID_3);
				}
				else {
					query.append(_FINDER_COLUMN_ARTICLEID_ARTICLEID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (articleId != null) {
					qPos.add(articleId);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ARTICLEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of journal content searchs where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the number of matching journal content searchs
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_P(long groupId, boolean privateLayout)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, privateLayout };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALCONTENTSEARCH_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_PRIVATELAYOUT_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of journal content searchs where groupId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param articleId the article ID
	 * @return the number of matching journal content searchs
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_A(long groupId, String articleId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, articleId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_A,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALCONTENTSEARCH_WHERE);

			query.append(_FINDER_COLUMN_G_A_GROUPID_2);

			if (articleId == null) {
				query.append(_FINDER_COLUMN_G_A_ARTICLEID_1);
			}
			else {
				if (articleId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_A_ARTICLEID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_A_ARTICLEID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (articleId != null) {
					qPos.add(articleId);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_A, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the number of matching journal content searchs
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_P_L(long groupId, boolean privateLayout, long layoutId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, privateLayout, layoutId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P_L,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_JOURNALCONTENTSEARCH_WHERE);

			query.append(_FINDER_COLUMN_G_P_L_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_L_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_G_P_L_LAYOUTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(layoutId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P_L,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of journal content searchs where groupId = &#63; and privateLayout = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param articleId the article ID
	 * @return the number of matching journal content searchs
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_P_A(long groupId, boolean privateLayout,
		String articleId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, privateLayout, articleId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P_A,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_JOURNALCONTENTSEARCH_WHERE);

			query.append(_FINDER_COLUMN_G_P_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_A_PRIVATELAYOUT_2);

			if (articleId == null) {
				query.append(_FINDER_COLUMN_G_P_A_ARTICLEID_1);
			}
			else {
				if (articleId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_P_A_ARTICLEID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_P_A_ARTICLEID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				if (articleId != null) {
					qPos.add(articleId);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P_A,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @return the number of matching journal content searchs
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_P_L_P(long groupId, boolean privateLayout,
		long layoutId, String portletId) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, privateLayout, layoutId, portletId
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P_L_P,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_JOURNALCONTENTSEARCH_WHERE);

			query.append(_FINDER_COLUMN_G_P_L_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_L_P_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_G_P_L_P_LAYOUTID_2);

			if (portletId == null) {
				query.append(_FINDER_COLUMN_G_P_L_P_PORTLETID_1);
			}
			else {
				if (portletId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_P_L_P_PORTLETID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_P_L_P_PORTLETID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(layoutId);

				if (portletId != null) {
					qPos.add(portletId);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P_L_P,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of journal content searchs where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and portletId = &#63; and articleId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param portletId the portlet ID
	 * @param articleId the article ID
	 * @return the number of matching journal content searchs
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_P_L_P_A(long groupId, boolean privateLayout,
		long layoutId, String portletId, String articleId)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, privateLayout, layoutId, portletId, articleId
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P_L_P_A,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_COUNT_JOURNALCONTENTSEARCH_WHERE);

			query.append(_FINDER_COLUMN_G_P_L_P_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_L_P_A_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_G_P_L_P_A_LAYOUTID_2);

			if (portletId == null) {
				query.append(_FINDER_COLUMN_G_P_L_P_A_PORTLETID_1);
			}
			else {
				if (portletId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_P_L_P_A_PORTLETID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_P_L_P_A_PORTLETID_2);
				}
			}

			if (articleId == null) {
				query.append(_FINDER_COLUMN_G_P_L_P_A_ARTICLEID_1);
			}
			else {
				if (articleId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_P_L_P_A_ARTICLEID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_P_L_P_A_ARTICLEID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(layoutId);

				if (portletId != null) {
					qPos.add(portletId);
				}

				if (articleId != null) {
					qPos.add(articleId);
				}

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P_L_P_A,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of journal content searchs.
	 *
	 * @return the number of journal content searchs
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_JOURNALCONTENTSEARCH);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the journal content search persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.journal.model.JournalContentSearch")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<JournalContentSearch>> listenersList = new ArrayList<ModelListener<JournalContentSearch>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<JournalContentSearch>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(JournalContentSearchImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = JournalArticlePersistence.class)
	protected JournalArticlePersistence journalArticlePersistence;
	@BeanReference(type = JournalArticleImagePersistence.class)
	protected JournalArticleImagePersistence journalArticleImagePersistence;
	@BeanReference(type = JournalArticleResourcePersistence.class)
	protected JournalArticleResourcePersistence journalArticleResourcePersistence;
	@BeanReference(type = JournalContentSearchPersistence.class)
	protected JournalContentSearchPersistence journalContentSearchPersistence;
	@BeanReference(type = JournalFeedPersistence.class)
	protected JournalFeedPersistence journalFeedPersistence;
	@BeanReference(type = JournalFolderPersistence.class)
	protected JournalFolderPersistence journalFolderPersistence;
	@BeanReference(type = JournalStructurePersistence.class)
	protected JournalStructurePersistence journalStructurePersistence;
	@BeanReference(type = JournalTemplatePersistence.class)
	protected JournalTemplatePersistence journalTemplatePersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = LayoutPersistence.class)
	protected LayoutPersistence layoutPersistence;
	@BeanReference(type = PortletPreferencesPersistence.class)
	protected PortletPreferencesPersistence portletPreferencesPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static final String _SQL_SELECT_JOURNALCONTENTSEARCH = "SELECT journalContentSearch FROM JournalContentSearch journalContentSearch";
	private static final String _SQL_SELECT_JOURNALCONTENTSEARCH_WHERE = "SELECT journalContentSearch FROM JournalContentSearch journalContentSearch WHERE ";
	private static final String _SQL_COUNT_JOURNALCONTENTSEARCH = "SELECT COUNT(journalContentSearch) FROM JournalContentSearch journalContentSearch";
	private static final String _SQL_COUNT_JOURNALCONTENTSEARCH_WHERE = "SELECT COUNT(journalContentSearch) FROM JournalContentSearch journalContentSearch WHERE ";
	private static final String _FINDER_COLUMN_PORTLETID_PORTLETID_1 = "journalContentSearch.portletId IS NULL";
	private static final String _FINDER_COLUMN_PORTLETID_PORTLETID_2 = "journalContentSearch.portletId = ?";
	private static final String _FINDER_COLUMN_PORTLETID_PORTLETID_3 = "(journalContentSearch.portletId IS NULL OR journalContentSearch.portletId = ?)";
	private static final String _FINDER_COLUMN_ARTICLEID_ARTICLEID_1 = "journalContentSearch.articleId IS NULL";
	private static final String _FINDER_COLUMN_ARTICLEID_ARTICLEID_2 = "journalContentSearch.articleId = ?";
	private static final String _FINDER_COLUMN_ARTICLEID_ARTICLEID_3 = "(journalContentSearch.articleId IS NULL OR journalContentSearch.articleId = ?)";
	private static final String _FINDER_COLUMN_G_P_GROUPID_2 = "journalContentSearch.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_PRIVATELAYOUT_2 = "journalContentSearch.privateLayout = ?";
	private static final String _FINDER_COLUMN_G_A_GROUPID_2 = "journalContentSearch.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_ARTICLEID_1 = "journalContentSearch.articleId IS NULL";
	private static final String _FINDER_COLUMN_G_A_ARTICLEID_2 = "journalContentSearch.articleId = ?";
	private static final String _FINDER_COLUMN_G_A_ARTICLEID_3 = "(journalContentSearch.articleId IS NULL OR journalContentSearch.articleId = ?)";
	private static final String _FINDER_COLUMN_G_P_L_GROUPID_2 = "journalContentSearch.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_L_PRIVATELAYOUT_2 = "journalContentSearch.privateLayout = ? AND ";
	private static final String _FINDER_COLUMN_G_P_L_LAYOUTID_2 = "journalContentSearch.layoutId = ?";
	private static final String _FINDER_COLUMN_G_P_A_GROUPID_2 = "journalContentSearch.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_A_PRIVATELAYOUT_2 = "journalContentSearch.privateLayout = ? AND ";
	private static final String _FINDER_COLUMN_G_P_A_ARTICLEID_1 = "journalContentSearch.articleId IS NULL";
	private static final String _FINDER_COLUMN_G_P_A_ARTICLEID_2 = "journalContentSearch.articleId = ?";
	private static final String _FINDER_COLUMN_G_P_A_ARTICLEID_3 = "(journalContentSearch.articleId IS NULL OR journalContentSearch.articleId = ?)";
	private static final String _FINDER_COLUMN_G_P_L_P_GROUPID_2 = "journalContentSearch.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_L_P_PRIVATELAYOUT_2 = "journalContentSearch.privateLayout = ? AND ";
	private static final String _FINDER_COLUMN_G_P_L_P_LAYOUTID_2 = "journalContentSearch.layoutId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_L_P_PORTLETID_1 = "journalContentSearch.portletId IS NULL";
	private static final String _FINDER_COLUMN_G_P_L_P_PORTLETID_2 = "journalContentSearch.portletId = ?";
	private static final String _FINDER_COLUMN_G_P_L_P_PORTLETID_3 = "(journalContentSearch.portletId IS NULL OR journalContentSearch.portletId = ?)";
	private static final String _FINDER_COLUMN_G_P_L_P_A_GROUPID_2 = "journalContentSearch.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_L_P_A_PRIVATELAYOUT_2 = "journalContentSearch.privateLayout = ? AND ";
	private static final String _FINDER_COLUMN_G_P_L_P_A_LAYOUTID_2 = "journalContentSearch.layoutId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_L_P_A_PORTLETID_1 = "journalContentSearch.portletId IS NULL AND ";
	private static final String _FINDER_COLUMN_G_P_L_P_A_PORTLETID_2 = "journalContentSearch.portletId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_L_P_A_PORTLETID_3 = "(journalContentSearch.portletId IS NULL OR journalContentSearch.portletId = ?) AND ";
	private static final String _FINDER_COLUMN_G_P_L_P_A_ARTICLEID_1 = "journalContentSearch.articleId IS NULL";
	private static final String _FINDER_COLUMN_G_P_L_P_A_ARTICLEID_2 = "journalContentSearch.articleId = ?";
	private static final String _FINDER_COLUMN_G_P_L_P_A_ARTICLEID_3 = "(journalContentSearch.articleId IS NULL OR journalContentSearch.articleId = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "journalContentSearch.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No JournalContentSearch exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No JournalContentSearch exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(JournalContentSearchPersistenceImpl.class);
	private static JournalContentSearch _nullJournalContentSearch = new JournalContentSearchImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<JournalContentSearch> toCacheModel() {
				return _nullJournalContentSearchCacheModel;
			}
		};

	private static CacheModel<JournalContentSearch> _nullJournalContentSearchCacheModel =
		new CacheModel<JournalContentSearch>() {
			public JournalContentSearch toEntityModel() {
				return _nullJournalContentSearch;
			}
		};
}