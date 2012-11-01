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
import com.liferay.portal.kernel.dao.orm.SQLQuery;
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
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence;
import com.liferay.portlet.journal.NoSuchFolderException;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.impl.JournalFolderImpl;
import com.liferay.portlet.journal.model.impl.JournalFolderModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the journal folder service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalFolderPersistence
 * @see JournalFolderUtil
 * @generated
 */
public class JournalFolderPersistenceImpl extends BasePersistenceImpl<JournalFolder>
	implements JournalFolderPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link JournalFolderUtil} to access the journal folder persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = JournalFolderImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(JournalFolderModelImpl.ENTITY_CACHE_ENABLED,
			JournalFolderModelImpl.FINDER_CACHE_ENABLED,
			JournalFolderImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(JournalFolderModelImpl.ENTITY_CACHE_ENABLED,
			JournalFolderModelImpl.FINDER_CACHE_ENABLED,
			JournalFolderImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid", new String[] { String.class.getName() },
			JournalFolderModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(JournalFolderModelImpl.ENTITY_CACHE_ENABLED,
			JournalFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(JournalFolderModelImpl.ENTITY_CACHE_ENABLED,
			JournalFolderModelImpl.FINDER_CACHE_ENABLED,
			JournalFolderImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			JournalFolderModelImpl.UUID_COLUMN_BITMASK |
			JournalFolderModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(JournalFolderModelImpl.ENTITY_CACHE_ENABLED,
			JournalFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(JournalFolderModelImpl.ENTITY_CACHE_ENABLED,
			JournalFolderModelImpl.FINDER_CACHE_ENABLED,
			JournalFolderImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(JournalFolderModelImpl.ENTITY_CACHE_ENABLED,
			JournalFolderModelImpl.FINDER_CACHE_ENABLED,
			JournalFolderImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			JournalFolderModelImpl.UUID_COLUMN_BITMASK |
			JournalFolderModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(JournalFolderModelImpl.ENTITY_CACHE_ENABLED,
			JournalFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(JournalFolderModelImpl.ENTITY_CACHE_ENABLED,
			JournalFolderModelImpl.FINDER_CACHE_ENABLED,
			JournalFolderImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(JournalFolderModelImpl.ENTITY_CACHE_ENABLED,
			JournalFolderModelImpl.FINDER_CACHE_ENABLED,
			JournalFolderImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByGroupId", new String[] { Long.class.getName() },
			JournalFolderModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(JournalFolderModelImpl.ENTITY_CACHE_ENABLED,
			JournalFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(JournalFolderModelImpl.ENTITY_CACHE_ENABLED,
			JournalFolderModelImpl.FINDER_CACHE_ENABLED,
			JournalFolderImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(JournalFolderModelImpl.ENTITY_CACHE_ENABLED,
			JournalFolderModelImpl.FINDER_CACHE_ENABLED,
			JournalFolderImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCompanyId", new String[] { Long.class.getName() },
			JournalFolderModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(JournalFolderModelImpl.ENTITY_CACHE_ENABLED,
			JournalFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P = new FinderPath(JournalFolderModelImpl.ENTITY_CACHE_ENABLED,
			JournalFolderModelImpl.FINDER_CACHE_ENABLED,
			JournalFolderImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P = new FinderPath(JournalFolderModelImpl.ENTITY_CACHE_ENABLED,
			JournalFolderModelImpl.FINDER_CACHE_ENABLED,
			JournalFolderImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_P",
			new String[] { Long.class.getName(), Long.class.getName() },
			JournalFolderModelImpl.GROUPID_COLUMN_BITMASK |
			JournalFolderModelImpl.PARENTFOLDERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P = new FinderPath(JournalFolderModelImpl.ENTITY_CACHE_ENABLED,
			JournalFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_N = new FinderPath(JournalFolderModelImpl.ENTITY_CACHE_ENABLED,
			JournalFolderModelImpl.FINDER_CACHE_ENABLED,
			JournalFolderImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByG_N",
			new String[] { Long.class.getName(), String.class.getName() },
			JournalFolderModelImpl.GROUPID_COLUMN_BITMASK |
			JournalFolderModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_N = new FinderPath(JournalFolderModelImpl.ENTITY_CACHE_ENABLED,
			JournalFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_N",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_P_N = new FinderPath(JournalFolderModelImpl.ENTITY_CACHE_ENABLED,
			JournalFolderModelImpl.FINDER_CACHE_ENABLED,
			JournalFolderImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByG_P_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			JournalFolderModelImpl.GROUPID_COLUMN_BITMASK |
			JournalFolderModelImpl.PARENTFOLDERID_COLUMN_BITMASK |
			JournalFolderModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P_N = new FinderPath(JournalFolderModelImpl.ENTITY_CACHE_ENABLED,
			JournalFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(JournalFolderModelImpl.ENTITY_CACHE_ENABLED,
			JournalFolderModelImpl.FINDER_CACHE_ENABLED,
			JournalFolderImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(JournalFolderModelImpl.ENTITY_CACHE_ENABLED,
			JournalFolderModelImpl.FINDER_CACHE_ENABLED,
			JournalFolderImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(JournalFolderModelImpl.ENTITY_CACHE_ENABLED,
			JournalFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the journal folder in the entity cache if it is enabled.
	 *
	 * @param journalFolder the journal folder
	 */
	public void cacheResult(JournalFolder journalFolder) {
		EntityCacheUtil.putResult(JournalFolderModelImpl.ENTITY_CACHE_ENABLED,
			JournalFolderImpl.class, journalFolder.getPrimaryKey(),
			journalFolder);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				journalFolder.getUuid(),
				Long.valueOf(journalFolder.getGroupId())
			}, journalFolder);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
			new Object[] {
				Long.valueOf(journalFolder.getGroupId()),
				
			journalFolder.getName()
			}, journalFolder);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_N,
			new Object[] {
				Long.valueOf(journalFolder.getGroupId()),
				Long.valueOf(journalFolder.getParentFolderId()),
				
			journalFolder.getName()
			}, journalFolder);

		journalFolder.resetOriginalValues();
	}

	/**
	 * Caches the journal folders in the entity cache if it is enabled.
	 *
	 * @param journalFolders the journal folders
	 */
	public void cacheResult(List<JournalFolder> journalFolders) {
		for (JournalFolder journalFolder : journalFolders) {
			if (EntityCacheUtil.getResult(
						JournalFolderModelImpl.ENTITY_CACHE_ENABLED,
						JournalFolderImpl.class, journalFolder.getPrimaryKey()) == null) {
				cacheResult(journalFolder);
			}
			else {
				journalFolder.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all journal folders.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(JournalFolderImpl.class.getName());
		}

		EntityCacheUtil.clearCache(JournalFolderImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the journal folder.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(JournalFolder journalFolder) {
		EntityCacheUtil.removeResult(JournalFolderModelImpl.ENTITY_CACHE_ENABLED,
			JournalFolderImpl.class, journalFolder.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(journalFolder);
	}

	@Override
	public void clearCache(List<JournalFolder> journalFolders) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (JournalFolder journalFolder : journalFolders) {
			EntityCacheUtil.removeResult(JournalFolderModelImpl.ENTITY_CACHE_ENABLED,
				JournalFolderImpl.class, journalFolder.getPrimaryKey());

			clearUniqueFindersCache(journalFolder);
		}
	}

	protected void clearUniqueFindersCache(JournalFolder journalFolder) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				journalFolder.getUuid(),
				Long.valueOf(journalFolder.getGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_N,
			new Object[] {
				Long.valueOf(journalFolder.getGroupId()),
				
			journalFolder.getName()
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P_N,
			new Object[] {
				Long.valueOf(journalFolder.getGroupId()),
				Long.valueOf(journalFolder.getParentFolderId()),
				
			journalFolder.getName()
			});
	}

	/**
	 * Creates a new journal folder with the primary key. Does not add the journal folder to the database.
	 *
	 * @param folderId the primary key for the new journal folder
	 * @return the new journal folder
	 */
	public JournalFolder create(long folderId) {
		JournalFolder journalFolder = new JournalFolderImpl();

		journalFolder.setNew(true);
		journalFolder.setPrimaryKey(folderId);

		String uuid = PortalUUIDUtil.generate();

		journalFolder.setUuid(uuid);

		return journalFolder;
	}

	/**
	 * Removes the journal folder with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param folderId the primary key of the journal folder
	 * @return the journal folder that was removed
	 * @throws com.liferay.portlet.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder remove(long folderId)
		throws NoSuchFolderException, SystemException {
		return remove(Long.valueOf(folderId));
	}

	/**
	 * Removes the journal folder with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the journal folder
	 * @return the journal folder that was removed
	 * @throws com.liferay.portlet.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public JournalFolder remove(Serializable primaryKey)
		throws NoSuchFolderException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalFolder journalFolder = (JournalFolder)session.get(JournalFolderImpl.class,
					primaryKey);

			if (journalFolder == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFolderException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(journalFolder);
		}
		catch (NoSuchFolderException nsee) {
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
	protected JournalFolder removeImpl(JournalFolder journalFolder)
		throws SystemException {
		journalFolder = toUnwrappedModel(journalFolder);

		Session session = null;

		try {
			session = openSession();

			if (journalFolder.isCachedModel()) {
				journalFolder = (JournalFolder)session.get(JournalFolderImpl.class,
						journalFolder.getPrimaryKeyObj());
			}

			if (journalFolder != null) {
				session.delete(journalFolder);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (journalFolder != null) {
			clearCache(journalFolder);
		}

		return journalFolder;
	}

	@Override
	public JournalFolder updateImpl(
		com.liferay.portlet.journal.model.JournalFolder journalFolder)
		throws SystemException {
		journalFolder = toUnwrappedModel(journalFolder);

		boolean isNew = journalFolder.isNew();

		JournalFolderModelImpl journalFolderModelImpl = (JournalFolderModelImpl)journalFolder;

		if (Validator.isNull(journalFolder.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			journalFolder.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (journalFolder.isNew()) {
				session.save(journalFolder);

				journalFolder.setNew(false);
			}
			else {
				session.merge(journalFolder);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !JournalFolderModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((journalFolderModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalFolderModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { journalFolderModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((journalFolderModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalFolderModelImpl.getOriginalUuid(),
						Long.valueOf(journalFolderModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						journalFolderModelImpl.getUuid(),
						Long.valueOf(journalFolderModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((journalFolderModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(journalFolderModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						Long.valueOf(journalFolderModelImpl.getGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((journalFolderModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(journalFolderModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						Long.valueOf(journalFolderModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((journalFolderModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(journalFolderModelImpl.getOriginalGroupId()),
						Long.valueOf(journalFolderModelImpl.getOriginalParentFolderId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P,
					args);

				args = new Object[] {
						Long.valueOf(journalFolderModelImpl.getGroupId()),
						Long.valueOf(journalFolderModelImpl.getParentFolderId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P,
					args);
			}
		}

		EntityCacheUtil.putResult(JournalFolderModelImpl.ENTITY_CACHE_ENABLED,
			JournalFolderImpl.class, journalFolder.getPrimaryKey(),
			journalFolder);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					journalFolder.getUuid(),
					Long.valueOf(journalFolder.getGroupId())
				}, journalFolder);

			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
				new Object[] {
					Long.valueOf(journalFolder.getGroupId()),
					
				journalFolder.getName()
				}, journalFolder);

			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_N,
				new Object[] {
					Long.valueOf(journalFolder.getGroupId()),
					Long.valueOf(journalFolder.getParentFolderId()),
					
				journalFolder.getName()
				}, journalFolder);
		}
		else {
			if ((journalFolderModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalFolderModelImpl.getOriginalUuid(),
						Long.valueOf(journalFolderModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
					new Object[] {
						journalFolder.getUuid(),
						Long.valueOf(journalFolder.getGroupId())
					}, journalFolder);
			}

			if ((journalFolderModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(journalFolderModelImpl.getOriginalGroupId()),
						
						journalFolderModelImpl.getOriginalName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_N, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_N, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
					new Object[] {
						Long.valueOf(journalFolder.getGroupId()),
						
					journalFolder.getName()
					}, journalFolder);
			}

			if ((journalFolderModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_P_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(journalFolderModelImpl.getOriginalGroupId()),
						Long.valueOf(journalFolderModelImpl.getOriginalParentFolderId()),
						
						journalFolderModelImpl.getOriginalName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P_N, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P_N, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_N,
					new Object[] {
						Long.valueOf(journalFolder.getGroupId()),
						Long.valueOf(journalFolder.getParentFolderId()),
						
					journalFolder.getName()
					}, journalFolder);
			}
		}

		return journalFolder;
	}

	protected JournalFolder toUnwrappedModel(JournalFolder journalFolder) {
		if (journalFolder instanceof JournalFolderImpl) {
			return journalFolder;
		}

		JournalFolderImpl journalFolderImpl = new JournalFolderImpl();

		journalFolderImpl.setNew(journalFolder.isNew());
		journalFolderImpl.setPrimaryKey(journalFolder.getPrimaryKey());

		journalFolderImpl.setUuid(journalFolder.getUuid());
		journalFolderImpl.setFolderId(journalFolder.getFolderId());
		journalFolderImpl.setGroupId(journalFolder.getGroupId());
		journalFolderImpl.setCompanyId(journalFolder.getCompanyId());
		journalFolderImpl.setUserId(journalFolder.getUserId());
		journalFolderImpl.setUserName(journalFolder.getUserName());
		journalFolderImpl.setCreateDate(journalFolder.getCreateDate());
		journalFolderImpl.setModifiedDate(journalFolder.getModifiedDate());
		journalFolderImpl.setParentFolderId(journalFolder.getParentFolderId());
		journalFolderImpl.setName(journalFolder.getName());
		journalFolderImpl.setDescription(journalFolder.getDescription());

		return journalFolderImpl;
	}

	/**
	 * Returns the journal folder with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the journal folder
	 * @return the journal folder
	 * @throws com.liferay.portal.NoSuchModelException if a journal folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public JournalFolder findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the journal folder with the primary key or throws a {@link com.liferay.portlet.journal.NoSuchFolderException} if it could not be found.
	 *
	 * @param folderId the primary key of the journal folder
	 * @return the journal folder
	 * @throws com.liferay.portlet.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder findByPrimaryKey(long folderId)
		throws NoSuchFolderException, SystemException {
		JournalFolder journalFolder = fetchByPrimaryKey(folderId);

		if (journalFolder == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + folderId);
			}

			throw new NoSuchFolderException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				folderId);
		}

		return journalFolder;
	}

	/**
	 * Returns the journal folder with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the journal folder
	 * @return the journal folder, or <code>null</code> if a journal folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public JournalFolder fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the journal folder with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param folderId the primary key of the journal folder
	 * @return the journal folder, or <code>null</code> if a journal folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder fetchByPrimaryKey(long folderId)
		throws SystemException {
		JournalFolder journalFolder = (JournalFolder)EntityCacheUtil.getResult(JournalFolderModelImpl.ENTITY_CACHE_ENABLED,
				JournalFolderImpl.class, folderId);

		if (journalFolder == _nullJournalFolder) {
			return null;
		}

		if (journalFolder == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				journalFolder = (JournalFolder)session.get(JournalFolderImpl.class,
						Long.valueOf(folderId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (journalFolder != null) {
					cacheResult(journalFolder);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(JournalFolderModelImpl.ENTITY_CACHE_ENABLED,
						JournalFolderImpl.class, folderId, _nullJournalFolder);
				}

				closeSession(session);
			}
		}

		return journalFolder;
	}

	/**
	 * Returns all the journal folders where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching journal folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFolder> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal folders where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @return the range of matching journal folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFolder> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal folders where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFolder> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid, start, end, orderByComparator };
		}

		List<JournalFolder> list = (List<JournalFolder>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalFolder journalFolder : list) {
				if (!Validator.equals(uuid, journalFolder.getUuid())) {
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
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_JOURNALFOLDER_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_UUID_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(JournalFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<JournalFolder>)QueryUtil.list(q, getDialect(),
						start, end);
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
	 * Returns the first journal folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal folder
	 * @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		JournalFolder journalFolder = fetchByUuid_First(uuid, orderByComparator);

		if (journalFolder != null) {
			return journalFolder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the first journal folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<JournalFolder> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal folder
	 * @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		JournalFolder journalFolder = fetchByUuid_Last(uuid, orderByComparator);

		if (journalFolder != null) {
			return journalFolder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the last journal folder in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<JournalFolder> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal folders before and after the current journal folder in the ordered set where uuid = &#63;.
	 *
	 * @param folderId the primary key of the current journal folder
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal folder
	 * @throws com.liferay.portlet.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder[] findByUuid_PrevAndNext(long folderId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		JournalFolder journalFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			JournalFolder[] array = new JournalFolderImpl[3];

			array[0] = getByUuid_PrevAndNext(session, journalFolder, uuid,
					orderByComparator, true);

			array[1] = journalFolder;

			array[2] = getByUuid_PrevAndNext(session, journalFolder, uuid,
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

	protected JournalFolder getByUuid_PrevAndNext(Session session,
		JournalFolder journalFolder, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALFOLDER_WHERE);

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else {
			if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				query.append(_FINDER_COLUMN_UUID_UUID_2);
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

		else {
			query.append(JournalFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the journal folder where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchFolderException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching journal folder
	 * @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder findByUUID_G(String uuid, long groupId)
		throws NoSuchFolderException, SystemException {
		JournalFolder journalFolder = fetchByUUID_G(uuid, groupId);

		if (journalFolder == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFolderException(msg.toString());
		}

		return journalFolder;
	}

	/**
	 * Returns the journal folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the journal folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof JournalFolder) {
			JournalFolder journalFolder = (JournalFolder)result;

			if (!Validator.equals(uuid, journalFolder.getUuid()) ||
					(groupId != journalFolder.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_JOURNALFOLDER_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_G_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_G_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			query.append(JournalFolderModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<JournalFolder> list = q.list();

				result = list;

				JournalFolder journalFolder = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					journalFolder = list.get(0);

					cacheResult(journalFolder);

					if ((journalFolder.getUuid() == null) ||
							!journalFolder.getUuid().equals(uuid) ||
							(journalFolder.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, journalFolder);
					}
				}

				return journalFolder;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
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
				return (JournalFolder)result;
			}
		}
	}

	/**
	 * Returns all the journal folders where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching journal folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFolder> findByUuid_C(String uuid, long companyId)
		throws SystemException {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal folders where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @return the range of matching journal folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFolder> findByUuid_C(String uuid, long companyId,
		int start, int end) throws SystemException {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal folders where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFolder> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] { uuid, companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] {
					uuid, companyId,
					
					start, end, orderByComparator
				};
		}

		List<JournalFolder> list = (List<JournalFolder>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalFolder journalFolder : list) {
				if (!Validator.equals(uuid, journalFolder.getUuid()) ||
						(companyId != journalFolder.getCompanyId())) {
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
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_JOURNALFOLDER_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_C_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_C_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(JournalFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				list = (List<JournalFolder>)QueryUtil.list(q, getDialect(),
						start, end);
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
	 * Returns the first journal folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal folder
	 * @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder findByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		JournalFolder journalFolder = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (journalFolder != null) {
			return journalFolder;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the first journal folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<JournalFolder> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal folder
	 * @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		JournalFolder journalFolder = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (journalFolder != null) {
			return journalFolder;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the last journal folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid_C(uuid, companyId);

		List<JournalFolder> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal folders before and after the current journal folder in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param folderId the primary key of the current journal folder
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal folder
	 * @throws com.liferay.portlet.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder[] findByUuid_C_PrevAndNext(long folderId, String uuid,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		JournalFolder journalFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			JournalFolder[] array = new JournalFolderImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, journalFolder, uuid,
					companyId, orderByComparator, true);

			array[1] = journalFolder;

			array[2] = getByUuid_C_PrevAndNext(session, journalFolder, uuid,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalFolder getByUuid_C_PrevAndNext(Session session,
		JournalFolder journalFolder, String uuid, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALFOLDER_WHERE);

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else {
			if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

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
			query.append(JournalFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal folders where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching journal folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFolder> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal folders where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @return the range of matching journal folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFolder> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal folders where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFolder> findByGroupId(long groupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<JournalFolder> list = (List<JournalFolder>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalFolder journalFolder : list) {
				if ((groupId != journalFolder.getGroupId())) {
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
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_JOURNALFOLDER_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(JournalFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<JournalFolder>)QueryUtil.list(q, getDialect(),
						start, end);
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
	 * Returns the first journal folder in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal folder
	 * @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		JournalFolder journalFolder = fetchByGroupId_First(groupId,
				orderByComparator);

		if (journalFolder != null) {
			return journalFolder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the first journal folder in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<JournalFolder> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal folder in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal folder
	 * @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		JournalFolder journalFolder = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (journalFolder != null) {
			return journalFolder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the last journal folder in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByGroupId(groupId);

		List<JournalFolder> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal folders before and after the current journal folder in the ordered set where groupId = &#63;.
	 *
	 * @param folderId the primary key of the current journal folder
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal folder
	 * @throws com.liferay.portlet.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder[] findByGroupId_PrevAndNext(long folderId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		JournalFolder journalFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			JournalFolder[] array = new JournalFolderImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, journalFolder,
					groupId, orderByComparator, true);

			array[1] = journalFolder;

			array[2] = getByGroupId_PrevAndNext(session, journalFolder,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalFolder getByGroupId_PrevAndNext(Session session,
		JournalFolder journalFolder, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALFOLDER_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
			query.append(JournalFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal folders that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching journal folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFolder> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal folders that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @return the range of matching journal folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFolder> filterFindByGroupId(long groupId, int start,
		int end) throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal folders that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFolder> filterFindByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALFOLDER_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALFOLDER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalFolder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalFolderImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalFolderImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<JournalFolder>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the journal folders before and after the current journal folder in the ordered set of journal folders that the user has permission to view where groupId = &#63;.
	 *
	 * @param folderId the primary key of the current journal folder
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal folder
	 * @throws com.liferay.portlet.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder[] filterFindByGroupId_PrevAndNext(long folderId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(folderId, groupId,
				orderByComparator);
		}

		JournalFolder journalFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			JournalFolder[] array = new JournalFolderImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, journalFolder,
					groupId, orderByComparator, true);

			array[1] = journalFolder;

			array[2] = filterGetByGroupId_PrevAndNext(session, journalFolder,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalFolder filterGetByGroupId_PrevAndNext(Session session,
		JournalFolder journalFolder, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALFOLDER_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALFOLDER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalFolder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, JournalFolderImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, JournalFolderImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal folders where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching journal folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFolder> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal folders where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @return the range of matching journal folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFolder> findByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal folders where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFolder> findByCompanyId(long companyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<JournalFolder> list = (List<JournalFolder>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalFolder journalFolder : list) {
				if ((companyId != journalFolder.getCompanyId())) {
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
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_JOURNALFOLDER_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(JournalFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<JournalFolder>)QueryUtil.list(q, getDialect(),
						start, end);
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
	 * Returns the first journal folder in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal folder
	 * @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		JournalFolder journalFolder = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (journalFolder != null) {
			return journalFolder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the first journal folder in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder fetchByCompanyId_First(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<JournalFolder> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal folder in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal folder
	 * @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		JournalFolder journalFolder = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (journalFolder != null) {
			return journalFolder;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the last journal folder in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder fetchByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCompanyId(companyId);

		List<JournalFolder> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal folders before and after the current journal folder in the ordered set where companyId = &#63;.
	 *
	 * @param folderId the primary key of the current journal folder
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal folder
	 * @throws com.liferay.portlet.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder[] findByCompanyId_PrevAndNext(long folderId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		JournalFolder journalFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			JournalFolder[] array = new JournalFolderImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, journalFolder,
					companyId, orderByComparator, true);

			array[1] = journalFolder;

			array[2] = getByCompanyId_PrevAndNext(session, journalFolder,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalFolder getByCompanyId_PrevAndNext(Session session,
		JournalFolder journalFolder, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALFOLDER_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			query.append(JournalFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @return the matching journal folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFolder> findByG_P(long groupId, long parentFolderId)
		throws SystemException {
		return findByG_P(groupId, parentFolderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @return the range of matching journal folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFolder> findByG_P(long groupId, long parentFolderId,
		int start, int end) throws SystemException {
		return findByG_P(groupId, parentFolderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFolder> findByG_P(long groupId, long parentFolderId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P;
			finderArgs = new Object[] { groupId, parentFolderId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P;
			finderArgs = new Object[] {
					groupId, parentFolderId,
					
					start, end, orderByComparator
				};
		}

		List<JournalFolder> list = (List<JournalFolder>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalFolder journalFolder : list) {
				if ((groupId != journalFolder.getGroupId()) ||
						(parentFolderId != journalFolder.getParentFolderId())) {
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
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_JOURNALFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_PARENTFOLDERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(JournalFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentFolderId);

				list = (List<JournalFolder>)QueryUtil.list(q, getDialect(),
						start, end);
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
	 * Returns the first journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal folder
	 * @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder findByG_P_First(long groupId, long parentFolderId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		JournalFolder journalFolder = fetchByG_P_First(groupId, parentFolderId,
				orderByComparator);

		if (journalFolder != null) {
			return journalFolder;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", parentFolderId=");
		msg.append(parentFolderId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the first journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder fetchByG_P_First(long groupId, long parentFolderId,
		OrderByComparator orderByComparator) throws SystemException {
		List<JournalFolder> list = findByG_P(groupId, parentFolderId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal folder
	 * @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder findByG_P_Last(long groupId, long parentFolderId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		JournalFolder journalFolder = fetchByG_P_Last(groupId, parentFolderId,
				orderByComparator);

		if (journalFolder != null) {
			return journalFolder;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", parentFolderId=");
		msg.append(parentFolderId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFolderException(msg.toString());
	}

	/**
	 * Returns the last journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder fetchByG_P_Last(long groupId, long parentFolderId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_P(groupId, parentFolderId);

		List<JournalFolder> list = findByG_P(groupId, parentFolderId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal folders before and after the current journal folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param folderId the primary key of the current journal folder
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal folder
	 * @throws com.liferay.portlet.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder[] findByG_P_PrevAndNext(long folderId, long groupId,
		long parentFolderId, OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		JournalFolder journalFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			JournalFolder[] array = new JournalFolderImpl[3];

			array[0] = getByG_P_PrevAndNext(session, journalFolder, groupId,
					parentFolderId, orderByComparator, true);

			array[1] = journalFolder;

			array[2] = getByG_P_PrevAndNext(session, journalFolder, groupId,
					parentFolderId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalFolder getByG_P_PrevAndNext(Session session,
		JournalFolder journalFolder, long groupId, long parentFolderId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTFOLDERID_2);

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
			query.append(JournalFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(parentFolderId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @return the matching journal folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFolder> filterFindByG_P(long groupId, long parentFolderId)
		throws SystemException {
		return filterFindByG_P(groupId, parentFolderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @return the range of matching journal folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFolder> filterFindByG_P(long groupId,
		long parentFolderId, int start, int end) throws SystemException {
		return filterFindByG_P(groupId, parentFolderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal folders that the user has permissions to view where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFolder> filterFindByG_P(long groupId,
		long parentFolderId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P(groupId, parentFolderId, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALFOLDER_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTFOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALFOLDER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}

		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalFolder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalFolderImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalFolderImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(parentFolderId);

			return (List<JournalFolder>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the journal folders before and after the current journal folder in the ordered set of journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param folderId the primary key of the current journal folder
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal folder
	 * @throws com.liferay.portlet.journal.NoSuchFolderException if a journal folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder[] filterFindByG_P_PrevAndNext(long folderId,
		long groupId, long parentFolderId, OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_PrevAndNext(folderId, groupId, parentFolderId,
				orderByComparator);
		}

		JournalFolder journalFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			JournalFolder[] array = new JournalFolderImpl[3];

			array[0] = filterGetByG_P_PrevAndNext(session, journalFolder,
					groupId, parentFolderId, orderByComparator, true);

			array[1] = journalFolder;

			array[2] = filterGetByG_P_PrevAndNext(session, journalFolder,
					groupId, parentFolderId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalFolder filterGetByG_P_PrevAndNext(Session session,
		JournalFolder journalFolder, long groupId, long parentFolderId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALFOLDER_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALFOLDER_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTFOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALFOLDER_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalFolderModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalFolderModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalFolder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, JournalFolderImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, JournalFolderImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(parentFolderId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the journal folder where groupId = &#63; and name = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchFolderException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching journal folder
	 * @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder findByG_N(long groupId, String name)
		throws NoSuchFolderException, SystemException {
		JournalFolder journalFolder = fetchByG_N(groupId, name);

		if (journalFolder == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFolderException(msg.toString());
		}

		return journalFolder;
	}

	/**
	 * Returns the journal folder where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder fetchByG_N(long groupId, String name)
		throws SystemException {
		return fetchByG_N(groupId, name, true);
	}

	/**
	 * Returns the journal folder where groupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder fetchByG_N(long groupId, String name,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_N,
					finderArgs, this);
		}

		if (result instanceof JournalFolder) {
			JournalFolder journalFolder = (JournalFolder)result;

			if ((groupId != journalFolder.getGroupId()) ||
					!Validator.equals(name, journalFolder.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_JOURNALFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_N_GROUPID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_N_NAME_2);
				}
			}

			query.append(JournalFolderModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (name != null) {
					qPos.add(name);
				}

				List<JournalFolder> list = q.list();

				result = list;

				JournalFolder journalFolder = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
						finderArgs, list);
				}
				else {
					journalFolder = list.get(0);

					cacheResult(journalFolder);

					if ((journalFolder.getGroupId() != groupId) ||
							(journalFolder.getName() == null) ||
							!journalFolder.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_N,
							finderArgs, journalFolder);
					}
				}

				return journalFolder;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_N,
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
				return (JournalFolder)result;
			}
		}
	}

	/**
	 * Returns the journal folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchFolderException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @return the matching journal folder
	 * @throws com.liferay.portlet.journal.NoSuchFolderException if a matching journal folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder findByG_P_N(long groupId, long parentFolderId,
		String name) throws NoSuchFolderException, SystemException {
		JournalFolder journalFolder = fetchByG_P_N(groupId, parentFolderId, name);

		if (journalFolder == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", parentFolderId=");
			msg.append(parentFolderId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFolderException(msg.toString());
		}

		return journalFolder;
	}

	/**
	 * Returns the journal folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder fetchByG_P_N(long groupId, long parentFolderId,
		String name) throws SystemException {
		return fetchByG_P_N(groupId, parentFolderId, name, true);
	}

	/**
	 * Returns the journal folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching journal folder, or <code>null</code> if a matching journal folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder fetchByG_P_N(long groupId, long parentFolderId,
		String name, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, parentFolderId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_P_N,
					finderArgs, this);
		}

		if (result instanceof JournalFolder) {
			JournalFolder journalFolder = (JournalFolder)result;

			if ((groupId != journalFolder.getGroupId()) ||
					(parentFolderId != journalFolder.getParentFolderId()) ||
					!Validator.equals(name, journalFolder.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_JOURNALFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_P_N_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_N_PARENTFOLDERID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_P_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_P_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_P_N_NAME_2);
				}
			}

			query.append(JournalFolderModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentFolderId);

				if (name != null) {
					qPos.add(name);
				}

				List<JournalFolder> list = q.list();

				result = list;

				JournalFolder journalFolder = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_N,
						finderArgs, list);
				}
				else {
					journalFolder = list.get(0);

					cacheResult(journalFolder);

					if ((journalFolder.getGroupId() != groupId) ||
							(journalFolder.getParentFolderId() != parentFolderId) ||
							(journalFolder.getName() == null) ||
							!journalFolder.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_P_N,
							finderArgs, journalFolder);
					}
				}

				return journalFolder;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_P_N,
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
				return (JournalFolder)result;
			}
		}
	}

	/**
	 * Returns all the journal folders.
	 *
	 * @return the journal folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFolder> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @return the range of journal folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFolder> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal folders
	 * @param end the upper bound of the range of journal folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of journal folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalFolder> findAll(int start, int end,
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

		List<JournalFolder> list = (List<JournalFolder>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_JOURNALFOLDER);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_JOURNALFOLDER.concat(JournalFolderModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<JournalFolder>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<JournalFolder>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Removes all the journal folders where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (JournalFolder journalFolder : findByUuid(uuid)) {
			remove(journalFolder);
		}
	}

	/**
	 * Removes the journal folder where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the journal folder that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder removeByUUID_G(String uuid, long groupId)
		throws NoSuchFolderException, SystemException {
		JournalFolder journalFolder = findByUUID_G(uuid, groupId);

		return remove(journalFolder);
	}

	/**
	 * Removes all the journal folders where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid_C(String uuid, long companyId)
		throws SystemException {
		for (JournalFolder journalFolder : findByUuid_C(uuid, companyId)) {
			remove(journalFolder);
		}
	}

	/**
	 * Removes all the journal folders where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (JournalFolder journalFolder : findByGroupId(groupId)) {
			remove(journalFolder);
		}
	}

	/**
	 * Removes all the journal folders where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (JournalFolder journalFolder : findByCompanyId(companyId)) {
			remove(journalFolder);
		}
	}

	/**
	 * Removes all the journal folders where groupId = &#63; and parentFolderId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_P(long groupId, long parentFolderId)
		throws SystemException {
		for (JournalFolder journalFolder : findByG_P(groupId, parentFolderId)) {
			remove(journalFolder);
		}
	}

	/**
	 * Removes the journal folder where groupId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the journal folder that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder removeByG_N(long groupId, String name)
		throws NoSuchFolderException, SystemException {
		JournalFolder journalFolder = findByG_N(groupId, name);

		return remove(journalFolder);
	}

	/**
	 * Removes the journal folder where groupId = &#63; and parentFolderId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @return the journal folder that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public JournalFolder removeByG_P_N(long groupId, long parentFolderId,
		String name) throws NoSuchFolderException, SystemException {
		JournalFolder journalFolder = findByG_P_N(groupId, parentFolderId, name);

		return remove(journalFolder);
	}

	/**
	 * Removes all the journal folders from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (JournalFolder journalFolder : findAll()) {
			remove(journalFolder);
		}
	}

	/**
	 * Returns the number of journal folders where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching journal folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALFOLDER_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_UUID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of journal folders where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching journal folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALFOLDER_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_G_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_G_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_G,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of journal folders where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching journal folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid_C(String uuid, long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALFOLDER_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_C_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_C_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_C,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of journal folders where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching journal folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALFOLDER_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_GROUPID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of journal folders that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching journal folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_JOURNALFOLDER_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalFolder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of journal folders where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching journal folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALFOLDER_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_COMPANYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of journal folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @return the number of matching journal folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_P(long groupId, long parentFolderId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, parentFolderId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_PARENTFOLDERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentFolderId);

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
	 * Returns the number of journal folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @return the number of matching journal folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_P(long groupId, long parentFolderId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_P(groupId, parentFolderId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_JOURNALFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTFOLDERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalFolder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(parentFolderId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of journal folders where groupId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching journal folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_N(long groupId, String name) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_N,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_N_GROUPID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_N_NAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (name != null) {
					qPos.add(name);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_N, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of journal folders where groupId = &#63; and parentFolderId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param name the name
	 * @return the number of matching journal folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_P_N(long groupId, long parentFolderId, String name)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, parentFolderId, name };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P_N,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_JOURNALFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_P_N_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_N_PARENTFOLDERID_2);

			if (name == null) {
				query.append(_FINDER_COLUMN_G_P_N_NAME_1);
			}
			else {
				if (name.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_P_N_NAME_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_P_N_NAME_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentFolderId);

				if (name != null) {
					qPos.add(name);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_P_N,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of journal folders.
	 *
	 * @return the number of journal folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_JOURNALFOLDER);

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
	 * Initializes the journal folder persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.journal.model.JournalFolder")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<JournalFolder>> listenersList = new ArrayList<ModelListener<JournalFolder>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<JournalFolder>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(JournalFolderImpl.class.getName());
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
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = ExpandoValuePersistence.class)
	protected ExpandoValuePersistence expandoValuePersistence;
	private static final String _SQL_SELECT_JOURNALFOLDER = "SELECT journalFolder FROM JournalFolder journalFolder";
	private static final String _SQL_SELECT_JOURNALFOLDER_WHERE = "SELECT journalFolder FROM JournalFolder journalFolder WHERE ";
	private static final String _SQL_COUNT_JOURNALFOLDER = "SELECT COUNT(journalFolder) FROM JournalFolder journalFolder";
	private static final String _SQL_COUNT_JOURNALFOLDER_WHERE = "SELECT COUNT(journalFolder) FROM JournalFolder journalFolder WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "journalFolder.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "journalFolder.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(journalFolder.uuid IS NULL OR journalFolder.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "journalFolder.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "journalFolder.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(journalFolder.uuid IS NULL OR journalFolder.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "journalFolder.groupId = ?";
	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "journalFolder.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "journalFolder.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(journalFolder.uuid IS NULL OR journalFolder.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "journalFolder.companyId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "journalFolder.groupId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "journalFolder.companyId = ?";
	private static final String _FINDER_COLUMN_G_P_GROUPID_2 = "journalFolder.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_PARENTFOLDERID_2 = "journalFolder.parentFolderId = ?";
	private static final String _FINDER_COLUMN_G_N_GROUPID_2 = "journalFolder.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_N_NAME_1 = "journalFolder.name IS NULL";
	private static final String _FINDER_COLUMN_G_N_NAME_2 = "journalFolder.name = ?";
	private static final String _FINDER_COLUMN_G_N_NAME_3 = "(journalFolder.name IS NULL OR journalFolder.name = ?)";
	private static final String _FINDER_COLUMN_G_P_N_GROUPID_2 = "journalFolder.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_N_PARENTFOLDERID_2 = "journalFolder.parentFolderId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_N_NAME_1 = "journalFolder.name IS NULL";
	private static final String _FINDER_COLUMN_G_P_N_NAME_2 = "journalFolder.name = ?";
	private static final String _FINDER_COLUMN_G_P_N_NAME_3 = "(journalFolder.name IS NULL OR journalFolder.name = ?)";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "journalFolder.folderId";
	private static final String _FILTER_SQL_SELECT_JOURNALFOLDER_WHERE = "SELECT DISTINCT {journalFolder.*} FROM JournalFolder journalFolder WHERE ";
	private static final String _FILTER_SQL_SELECT_JOURNALFOLDER_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {JournalFolder.*} FROM (SELECT DISTINCT journalFolder.folderId FROM JournalFolder journalFolder WHERE ";
	private static final String _FILTER_SQL_SELECT_JOURNALFOLDER_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN JournalFolder ON TEMP_TABLE.folderId = JournalFolder.folderId";
	private static final String _FILTER_SQL_COUNT_JOURNALFOLDER_WHERE = "SELECT COUNT(DISTINCT journalFolder.folderId) AS COUNT_VALUE FROM JournalFolder journalFolder WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "journalFolder";
	private static final String _FILTER_ENTITY_TABLE = "JournalFolder";
	private static final String _ORDER_BY_ENTITY_ALIAS = "journalFolder.";
	private static final String _ORDER_BY_ENTITY_TABLE = "JournalFolder.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No JournalFolder exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No JournalFolder exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(JournalFolderPersistenceImpl.class);
	private static JournalFolder _nullJournalFolder = new JournalFolderImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<JournalFolder> toCacheModel() {
				return _nullJournalFolderCacheModel;
			}
		};

	private static CacheModel<JournalFolder> _nullJournalFolderCacheModel = new CacheModel<JournalFolder>() {
			public JournalFolder toEntityModel() {
				return _nullJournalFolder;
			}
		};
}