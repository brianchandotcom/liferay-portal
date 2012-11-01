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

package com.liferay.portlet.documentlibrary.service.persistence;

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
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.service.persistence.AssetEntryPersistence;
import com.liferay.portlet.asset.service.persistence.AssetTagPersistence;
import com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutModelImpl;
import com.liferay.portlet.trash.service.persistence.TrashEntryPersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the document library file shortcut service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileShortcutPersistence
 * @see DLFileShortcutUtil
 * @generated
 */
public class DLFileShortcutPersistenceImpl extends BasePersistenceImpl<DLFileShortcut>
	implements DLFileShortcutPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DLFileShortcutUtil} to access the document library file shortcut persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DLFileShortcutImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			DLFileShortcutImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			DLFileShortcutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			DLFileShortcutModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			DLFileShortcutImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			DLFileShortcutModelImpl.UUID_COLUMN_BITMASK |
			DLFileShortcutModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			DLFileShortcutImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			DLFileShortcutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			DLFileShortcutModelImpl.UUID_COLUMN_BITMASK |
			DLFileShortcutModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_TOFILEENTRYID =
		new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			DLFileShortcutImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByToFileEntryId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TOFILEENTRYID =
		new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			DLFileShortcutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByToFileEntryId",
			new String[] { Long.class.getName() },
			DLFileShortcutModelImpl.TOFILEENTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_TOFILEENTRYID = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByToFileEntryId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			DLFileShortcutImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			DLFileShortcutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_F",
			new String[] { Long.class.getName(), Long.class.getName() },
			DLFileShortcutModelImpl.GROUPID_COLUMN_BITMASK |
			DLFileShortcutModelImpl.FOLDERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F_A = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			DLFileShortcutImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_F_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_A = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			DLFileShortcutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_F_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			DLFileShortcutModelImpl.GROUPID_COLUMN_BITMASK |
			DLFileShortcutModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileShortcutModelImpl.ACTIVE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F_A = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F_A_S = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			DLFileShortcutImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_F_A_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_A_S =
		new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			DLFileShortcutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_F_A_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName()
			},
			DLFileShortcutModelImpl.GROUPID_COLUMN_BITMASK |
			DLFileShortcutModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileShortcutModelImpl.ACTIVE_COLUMN_BITMASK |
			DLFileShortcutModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F_A_S = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F_A_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			DLFileShortcutImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			DLFileShortcutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the document library file shortcut in the entity cache if it is enabled.
	 *
	 * @param dlFileShortcut the document library file shortcut
	 */
	public void cacheResult(DLFileShortcut dlFileShortcut) {
		EntityCacheUtil.putResult(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutImpl.class, dlFileShortcut.getPrimaryKey(),
			dlFileShortcut);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				dlFileShortcut.getUuid(),
				Long.valueOf(dlFileShortcut.getGroupId())
			}, dlFileShortcut);

		dlFileShortcut.resetOriginalValues();
	}

	/**
	 * Caches the document library file shortcuts in the entity cache if it is enabled.
	 *
	 * @param dlFileShortcuts the document library file shortcuts
	 */
	public void cacheResult(List<DLFileShortcut> dlFileShortcuts) {
		for (DLFileShortcut dlFileShortcut : dlFileShortcuts) {
			if (EntityCacheUtil.getResult(
						DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
						DLFileShortcutImpl.class, dlFileShortcut.getPrimaryKey()) == null) {
				cacheResult(dlFileShortcut);
			}
			else {
				dlFileShortcut.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all document library file shortcuts.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(DLFileShortcutImpl.class.getName());
		}

		EntityCacheUtil.clearCache(DLFileShortcutImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the document library file shortcut.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DLFileShortcut dlFileShortcut) {
		EntityCacheUtil.removeResult(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutImpl.class, dlFileShortcut.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(dlFileShortcut);
	}

	@Override
	public void clearCache(List<DLFileShortcut> dlFileShortcuts) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DLFileShortcut dlFileShortcut : dlFileShortcuts) {
			EntityCacheUtil.removeResult(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
				DLFileShortcutImpl.class, dlFileShortcut.getPrimaryKey());

			clearUniqueFindersCache(dlFileShortcut);
		}
	}

	protected void clearUniqueFindersCache(DLFileShortcut dlFileShortcut) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				dlFileShortcut.getUuid(),
				Long.valueOf(dlFileShortcut.getGroupId())
			});
	}

	/**
	 * Creates a new document library file shortcut with the primary key. Does not add the document library file shortcut to the database.
	 *
	 * @param fileShortcutId the primary key for the new document library file shortcut
	 * @return the new document library file shortcut
	 */
	public DLFileShortcut create(long fileShortcutId) {
		DLFileShortcut dlFileShortcut = new DLFileShortcutImpl();

		dlFileShortcut.setNew(true);
		dlFileShortcut.setPrimaryKey(fileShortcutId);

		String uuid = PortalUUIDUtil.generate();

		dlFileShortcut.setUuid(uuid);

		return dlFileShortcut;
	}

	/**
	 * Removes the document library file shortcut with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fileShortcutId the primary key of the document library file shortcut
	 * @return the document library file shortcut that was removed
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut remove(long fileShortcutId)
		throws NoSuchFileShortcutException, SystemException {
		return remove(Long.valueOf(fileShortcutId));
	}

	/**
	 * Removes the document library file shortcut with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the document library file shortcut
	 * @return the document library file shortcut that was removed
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DLFileShortcut remove(Serializable primaryKey)
		throws NoSuchFileShortcutException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLFileShortcut dlFileShortcut = (DLFileShortcut)session.get(DLFileShortcutImpl.class,
					primaryKey);

			if (dlFileShortcut == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFileShortcutException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(dlFileShortcut);
		}
		catch (NoSuchFileShortcutException nsee) {
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
	protected DLFileShortcut removeImpl(DLFileShortcut dlFileShortcut)
		throws SystemException {
		dlFileShortcut = toUnwrappedModel(dlFileShortcut);

		Session session = null;

		try {
			session = openSession();

			if (dlFileShortcut.isCachedModel()) {
				dlFileShortcut = (DLFileShortcut)session.get(DLFileShortcutImpl.class,
						dlFileShortcut.getPrimaryKeyObj());
			}

			if (dlFileShortcut != null) {
				session.delete(dlFileShortcut);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (dlFileShortcut != null) {
			clearCache(dlFileShortcut);
		}

		return dlFileShortcut;
	}

	@Override
	public DLFileShortcut updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut)
		throws SystemException {
		dlFileShortcut = toUnwrappedModel(dlFileShortcut);

		boolean isNew = dlFileShortcut.isNew();

		DLFileShortcutModelImpl dlFileShortcutModelImpl = (DLFileShortcutModelImpl)dlFileShortcut;

		if (Validator.isNull(dlFileShortcut.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			dlFileShortcut.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (dlFileShortcut.isNew()) {
				session.save(dlFileShortcut);

				dlFileShortcut.setNew(false);
			}
			else {
				session.merge(dlFileShortcut);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !DLFileShortcutModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((dlFileShortcutModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileShortcutModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { dlFileShortcutModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((dlFileShortcutModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileShortcutModelImpl.getOriginalUuid(),
						Long.valueOf(dlFileShortcutModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						dlFileShortcutModelImpl.getUuid(),
						Long.valueOf(dlFileShortcutModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((dlFileShortcutModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TOFILEENTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(dlFileShortcutModelImpl.getOriginalToFileEntryId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_TOFILEENTRYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TOFILEENTRYID,
					args);

				args = new Object[] {
						Long.valueOf(dlFileShortcutModelImpl.getToFileEntryId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_TOFILEENTRYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TOFILEENTRYID,
					args);
			}

			if ((dlFileShortcutModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(dlFileShortcutModelImpl.getOriginalGroupId()),
						Long.valueOf(dlFileShortcutModelImpl.getOriginalFolderId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_F, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F,
					args);

				args = new Object[] {
						Long.valueOf(dlFileShortcutModelImpl.getGroupId()),
						Long.valueOf(dlFileShortcutModelImpl.getFolderId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_F, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F,
					args);
			}

			if ((dlFileShortcutModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(dlFileShortcutModelImpl.getOriginalGroupId()),
						Long.valueOf(dlFileShortcutModelImpl.getOriginalFolderId()),
						Boolean.valueOf(dlFileShortcutModelImpl.getOriginalActive())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_F_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_A,
					args);

				args = new Object[] {
						Long.valueOf(dlFileShortcutModelImpl.getGroupId()),
						Long.valueOf(dlFileShortcutModelImpl.getFolderId()),
						Boolean.valueOf(dlFileShortcutModelImpl.getActive())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_F_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_A,
					args);
			}

			if ((dlFileShortcutModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_A_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(dlFileShortcutModelImpl.getOriginalGroupId()),
						Long.valueOf(dlFileShortcutModelImpl.getOriginalFolderId()),
						Boolean.valueOf(dlFileShortcutModelImpl.getOriginalActive()),
						Integer.valueOf(dlFileShortcutModelImpl.getOriginalStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_F_A_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_A_S,
					args);

				args = new Object[] {
						Long.valueOf(dlFileShortcutModelImpl.getGroupId()),
						Long.valueOf(dlFileShortcutModelImpl.getFolderId()),
						Boolean.valueOf(dlFileShortcutModelImpl.getActive()),
						Integer.valueOf(dlFileShortcutModelImpl.getStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_F_A_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_A_S,
					args);
			}
		}

		EntityCacheUtil.putResult(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutImpl.class, dlFileShortcut.getPrimaryKey(),
			dlFileShortcut);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					dlFileShortcut.getUuid(),
					Long.valueOf(dlFileShortcut.getGroupId())
				}, dlFileShortcut);
		}
		else {
			if ((dlFileShortcutModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileShortcutModelImpl.getOriginalUuid(),
						Long.valueOf(dlFileShortcutModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
					new Object[] {
						dlFileShortcut.getUuid(),
						Long.valueOf(dlFileShortcut.getGroupId())
					}, dlFileShortcut);
			}
		}

		return dlFileShortcut;
	}

	protected DLFileShortcut toUnwrappedModel(DLFileShortcut dlFileShortcut) {
		if (dlFileShortcut instanceof DLFileShortcutImpl) {
			return dlFileShortcut;
		}

		DLFileShortcutImpl dlFileShortcutImpl = new DLFileShortcutImpl();

		dlFileShortcutImpl.setNew(dlFileShortcut.isNew());
		dlFileShortcutImpl.setPrimaryKey(dlFileShortcut.getPrimaryKey());

		dlFileShortcutImpl.setUuid(dlFileShortcut.getUuid());
		dlFileShortcutImpl.setFileShortcutId(dlFileShortcut.getFileShortcutId());
		dlFileShortcutImpl.setGroupId(dlFileShortcut.getGroupId());
		dlFileShortcutImpl.setCompanyId(dlFileShortcut.getCompanyId());
		dlFileShortcutImpl.setUserId(dlFileShortcut.getUserId());
		dlFileShortcutImpl.setUserName(dlFileShortcut.getUserName());
		dlFileShortcutImpl.setCreateDate(dlFileShortcut.getCreateDate());
		dlFileShortcutImpl.setModifiedDate(dlFileShortcut.getModifiedDate());
		dlFileShortcutImpl.setRepositoryId(dlFileShortcut.getRepositoryId());
		dlFileShortcutImpl.setFolderId(dlFileShortcut.getFolderId());
		dlFileShortcutImpl.setToFileEntryId(dlFileShortcut.getToFileEntryId());
		dlFileShortcutImpl.setActive(dlFileShortcut.isActive());
		dlFileShortcutImpl.setStatus(dlFileShortcut.getStatus());
		dlFileShortcutImpl.setStatusByUserId(dlFileShortcut.getStatusByUserId());
		dlFileShortcutImpl.setStatusByUserName(dlFileShortcut.getStatusByUserName());
		dlFileShortcutImpl.setStatusDate(dlFileShortcut.getStatusDate());

		return dlFileShortcutImpl;
	}

	/**
	 * Returns the document library file shortcut with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the document library file shortcut
	 * @return the document library file shortcut
	 * @throws com.liferay.portal.NoSuchModelException if a document library file shortcut with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DLFileShortcut findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the document library file shortcut with the primary key or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileShortcutException} if it could not be found.
	 *
	 * @param fileShortcutId the primary key of the document library file shortcut
	 * @return the document library file shortcut
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut findByPrimaryKey(long fileShortcutId)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = fetchByPrimaryKey(fileShortcutId);

		if (dlFileShortcut == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + fileShortcutId);
			}

			throw new NoSuchFileShortcutException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				fileShortcutId);
		}

		return dlFileShortcut;
	}

	/**
	 * Returns the document library file shortcut with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the document library file shortcut
	 * @return the document library file shortcut, or <code>null</code> if a document library file shortcut with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DLFileShortcut fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the document library file shortcut with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fileShortcutId the primary key of the document library file shortcut
	 * @return the document library file shortcut, or <code>null</code> if a document library file shortcut with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut fetchByPrimaryKey(long fileShortcutId)
		throws SystemException {
		DLFileShortcut dlFileShortcut = (DLFileShortcut)EntityCacheUtil.getResult(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
				DLFileShortcutImpl.class, fileShortcutId);

		if (dlFileShortcut == _nullDLFileShortcut) {
			return null;
		}

		if (dlFileShortcut == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				dlFileShortcut = (DLFileShortcut)session.get(DLFileShortcutImpl.class,
						Long.valueOf(fileShortcutId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (dlFileShortcut != null) {
					cacheResult(dlFileShortcut);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
						DLFileShortcutImpl.class, fileShortcutId,
						_nullDLFileShortcut);
				}

				closeSession(session);
			}
		}

		return dlFileShortcut;
	}

	/**
	 * Returns all the document library file shortcuts where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching document library file shortcuts
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileShortcut> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file shortcuts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @return the range of matching document library file shortcuts
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileShortcut> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file shortcuts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file shortcuts
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileShortcut> findByUuid(String uuid, int start, int end,
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

		List<DLFileShortcut> list = (List<DLFileShortcut>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFileShortcut dlFileShortcut : list) {
				if (!Validator.equals(uuid, dlFileShortcut.getUuid())) {
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

			query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

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

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				list = (List<DLFileShortcut>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file shortcut in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file shortcut
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching document library file shortcut could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = fetchByUuid_First(uuid,
				orderByComparator);

		if (dlFileShortcut != null) {
			return dlFileShortcut;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileShortcutException(msg.toString());
	}

	/**
	 * Returns the first document library file shortcut in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<DLFileShortcut> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file shortcut in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file shortcut
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching document library file shortcut could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = fetchByUuid_Last(uuid, orderByComparator);

		if (dlFileShortcut != null) {
			return dlFileShortcut;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileShortcutException(msg.toString());
	}

	/**
	 * Returns the last document library file shortcut in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<DLFileShortcut> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where uuid = &#63;.
	 *
	 * @param fileShortcutId the primary key of the current document library file shortcut
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file shortcut
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut[] findByUuid_PrevAndNext(long fileShortcutId,
		String uuid, OrderByComparator orderByComparator)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = findByPrimaryKey(fileShortcutId);

		Session session = null;

		try {
			session = openSession();

			DLFileShortcut[] array = new DLFileShortcutImpl[3];

			array[0] = getByUuid_PrevAndNext(session, dlFileShortcut, uuid,
					orderByComparator, true);

			array[1] = dlFileShortcut;

			array[2] = getByUuid_PrevAndNext(session, dlFileShortcut, uuid,
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

	protected DLFileShortcut getByUuid_PrevAndNext(Session session,
		DLFileShortcut dlFileShortcut, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileShortcut);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileShortcut> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the document library file shortcut where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileShortcutException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching document library file shortcut
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching document library file shortcut could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut findByUUID_G(String uuid, long groupId)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = fetchByUUID_G(uuid, groupId);

		if (dlFileShortcut == null) {
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

			throw new NoSuchFileShortcutException(msg.toString());
		}

		return dlFileShortcut;
	}

	/**
	 * Returns the document library file shortcut where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the document library file shortcut where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof DLFileShortcut) {
			DLFileShortcut dlFileShortcut = (DLFileShortcut)result;

			if (!Validator.equals(uuid, dlFileShortcut.getUuid()) ||
					(groupId != dlFileShortcut.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

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

				List<DLFileShortcut> list = q.list();

				result = list;

				DLFileShortcut dlFileShortcut = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					dlFileShortcut = list.get(0);

					cacheResult(dlFileShortcut);

					if ((dlFileShortcut.getUuid() == null) ||
							!dlFileShortcut.getUuid().equals(uuid) ||
							(dlFileShortcut.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, dlFileShortcut);
					}
				}

				return dlFileShortcut;
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
				return (DLFileShortcut)result;
			}
		}
	}

	/**
	 * Returns all the document library file shortcuts where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching document library file shortcuts
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileShortcut> findByUuid_C(String uuid, long companyId)
		throws SystemException {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file shortcuts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @return the range of matching document library file shortcuts
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileShortcut> findByUuid_C(String uuid, long companyId,
		int start, int end) throws SystemException {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file shortcuts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file shortcuts
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileShortcut> findByUuid_C(String uuid, long companyId,
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

		List<DLFileShortcut> list = (List<DLFileShortcut>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFileShortcut dlFileShortcut : list) {
				if (!Validator.equals(uuid, dlFileShortcut.getUuid()) ||
						(companyId != dlFileShortcut.getCompanyId())) {
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

			query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

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

				list = (List<DLFileShortcut>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file shortcut in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file shortcut
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching document library file shortcut could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut findByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (dlFileShortcut != null) {
			return dlFileShortcut;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileShortcutException(msg.toString());
	}

	/**
	 * Returns the first document library file shortcut in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<DLFileShortcut> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file shortcut in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file shortcut
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching document library file shortcut could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (dlFileShortcut != null) {
			return dlFileShortcut;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileShortcutException(msg.toString());
	}

	/**
	 * Returns the last document library file shortcut in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid_C(uuid, companyId);

		List<DLFileShortcut> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param fileShortcutId the primary key of the current document library file shortcut
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file shortcut
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut[] findByUuid_C_PrevAndNext(long fileShortcutId,
		String uuid, long companyId, OrderByComparator orderByComparator)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = findByPrimaryKey(fileShortcutId);

		Session session = null;

		try {
			session = openSession();

			DLFileShortcut[] array = new DLFileShortcutImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, dlFileShortcut, uuid,
					companyId, orderByComparator, true);

			array[1] = dlFileShortcut;

			array[2] = getByUuid_C_PrevAndNext(session, dlFileShortcut, uuid,
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

	protected DLFileShortcut getByUuid_C_PrevAndNext(Session session,
		DLFileShortcut dlFileShortcut, String uuid, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileShortcut);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileShortcut> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file shortcuts where toFileEntryId = &#63;.
	 *
	 * @param toFileEntryId the to file entry ID
	 * @return the matching document library file shortcuts
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileShortcut> findByToFileEntryId(long toFileEntryId)
		throws SystemException {
		return findByToFileEntryId(toFileEntryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file shortcuts where toFileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param toFileEntryId the to file entry ID
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @return the range of matching document library file shortcuts
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileShortcut> findByToFileEntryId(long toFileEntryId,
		int start, int end) throws SystemException {
		return findByToFileEntryId(toFileEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file shortcuts where toFileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param toFileEntryId the to file entry ID
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file shortcuts
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileShortcut> findByToFileEntryId(long toFileEntryId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TOFILEENTRYID;
			finderArgs = new Object[] { toFileEntryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_TOFILEENTRYID;
			finderArgs = new Object[] {
					toFileEntryId,
					
					start, end, orderByComparator
				};
		}

		List<DLFileShortcut> list = (List<DLFileShortcut>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFileShortcut dlFileShortcut : list) {
				if ((toFileEntryId != dlFileShortcut.getToFileEntryId())) {
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

			query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

			query.append(_FINDER_COLUMN_TOFILEENTRYID_TOFILEENTRYID_2);

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

				qPos.add(toFileEntryId);

				list = (List<DLFileShortcut>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file shortcut in the ordered set where toFileEntryId = &#63;.
	 *
	 * @param toFileEntryId the to file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file shortcut
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching document library file shortcut could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut findByToFileEntryId_First(long toFileEntryId,
		OrderByComparator orderByComparator)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = fetchByToFileEntryId_First(toFileEntryId,
				orderByComparator);

		if (dlFileShortcut != null) {
			return dlFileShortcut;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("toFileEntryId=");
		msg.append(toFileEntryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileShortcutException(msg.toString());
	}

	/**
	 * Returns the first document library file shortcut in the ordered set where toFileEntryId = &#63;.
	 *
	 * @param toFileEntryId the to file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut fetchByToFileEntryId_First(long toFileEntryId,
		OrderByComparator orderByComparator) throws SystemException {
		List<DLFileShortcut> list = findByToFileEntryId(toFileEntryId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file shortcut in the ordered set where toFileEntryId = &#63;.
	 *
	 * @param toFileEntryId the to file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file shortcut
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching document library file shortcut could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut findByToFileEntryId_Last(long toFileEntryId,
		OrderByComparator orderByComparator)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = fetchByToFileEntryId_Last(toFileEntryId,
				orderByComparator);

		if (dlFileShortcut != null) {
			return dlFileShortcut;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("toFileEntryId=");
		msg.append(toFileEntryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileShortcutException(msg.toString());
	}

	/**
	 * Returns the last document library file shortcut in the ordered set where toFileEntryId = &#63;.
	 *
	 * @param toFileEntryId the to file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut fetchByToFileEntryId_Last(long toFileEntryId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByToFileEntryId(toFileEntryId);

		List<DLFileShortcut> list = findByToFileEntryId(toFileEntryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where toFileEntryId = &#63;.
	 *
	 * @param fileShortcutId the primary key of the current document library file shortcut
	 * @param toFileEntryId the to file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file shortcut
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut[] findByToFileEntryId_PrevAndNext(
		long fileShortcutId, long toFileEntryId,
		OrderByComparator orderByComparator)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = findByPrimaryKey(fileShortcutId);

		Session session = null;

		try {
			session = openSession();

			DLFileShortcut[] array = new DLFileShortcutImpl[3];

			array[0] = getByToFileEntryId_PrevAndNext(session, dlFileShortcut,
					toFileEntryId, orderByComparator, true);

			array[1] = dlFileShortcut;

			array[2] = getByToFileEntryId_PrevAndNext(session, dlFileShortcut,
					toFileEntryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileShortcut getByToFileEntryId_PrevAndNext(Session session,
		DLFileShortcut dlFileShortcut, long toFileEntryId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

		query.append(_FINDER_COLUMN_TOFILEENTRYID_TOFILEENTRYID_2);

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

		qPos.add(toFileEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileShortcut);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileShortcut> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file shortcuts where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the matching document library file shortcuts
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileShortcut> findByG_F(long groupId, long folderId)
		throws SystemException {
		return findByG_F(groupId, folderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file shortcuts where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @return the range of matching document library file shortcuts
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileShortcut> findByG_F(long groupId, long folderId,
		int start, int end) throws SystemException {
		return findByG_F(groupId, folderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file shortcuts where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file shortcuts
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileShortcut> findByG_F(long groupId, long folderId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F;
			finderArgs = new Object[] { groupId, folderId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F;
			finderArgs = new Object[] {
					groupId, folderId,
					
					start, end, orderByComparator
				};
		}

		List<DLFileShortcut> list = (List<DLFileShortcut>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFileShortcut dlFileShortcut : list) {
				if ((groupId != dlFileShortcut.getGroupId()) ||
						(folderId != dlFileShortcut.getFolderId())) {
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

			query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

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

				qPos.add(folderId);

				list = (List<DLFileShortcut>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file shortcut
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching document library file shortcut could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut findByG_F_First(long groupId, long folderId,
		OrderByComparator orderByComparator)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = fetchByG_F_First(groupId, folderId,
				orderByComparator);

		if (dlFileShortcut != null) {
			return dlFileShortcut;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileShortcutException(msg.toString());
	}

	/**
	 * Returns the first document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut fetchByG_F_First(long groupId, long folderId,
		OrderByComparator orderByComparator) throws SystemException {
		List<DLFileShortcut> list = findByG_F(groupId, folderId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file shortcut
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching document library file shortcut could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut findByG_F_Last(long groupId, long folderId,
		OrderByComparator orderByComparator)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = fetchByG_F_Last(groupId, folderId,
				orderByComparator);

		if (dlFileShortcut != null) {
			return dlFileShortcut;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileShortcutException(msg.toString());
	}

	/**
	 * Returns the last document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut fetchByG_F_Last(long groupId, long folderId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_F(groupId, folderId);

		List<DLFileShortcut> list = findByG_F(groupId, folderId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param fileShortcutId the primary key of the current document library file shortcut
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file shortcut
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut[] findByG_F_PrevAndNext(long fileShortcutId,
		long groupId, long folderId, OrderByComparator orderByComparator)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = findByPrimaryKey(fileShortcutId);

		Session session = null;

		try {
			session = openSession();

			DLFileShortcut[] array = new DLFileShortcutImpl[3];

			array[0] = getByG_F_PrevAndNext(session, dlFileShortcut, groupId,
					folderId, orderByComparator, true);

			array[1] = dlFileShortcut;

			array[2] = getByG_F_PrevAndNext(session, dlFileShortcut, groupId,
					folderId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileShortcut getByG_F_PrevAndNext(Session session,
		DLFileShortcut dlFileShortcut, long groupId, long folderId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

		query.append(_FINDER_COLUMN_G_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

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

		qPos.add(folderId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileShortcut);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileShortcut> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the matching document library file shortcuts that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileShortcut> filterFindByG_F(long groupId, long folderId)
		throws SystemException {
		return filterFindByG_F(groupId, folderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @return the range of matching document library file shortcuts that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileShortcut> filterFindByG_F(long groupId, long folderId,
		int start, int end) throws SystemException {
		return filterFindByG_F(groupId, folderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file shortcuts that the user has permissions to view where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file shortcuts that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileShortcut> filterFindByG_F(long groupId, long folderId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F(groupId, folderId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_NO_INLINE_DISTINCT_WHERE_2);
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

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileShortcut.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFileShortcutImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFileShortcutImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

			return (List<DLFileShortcut>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set of document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * @param fileShortcutId the primary key of the current document library file shortcut
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file shortcut
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut[] filterFindByG_F_PrevAndNext(long fileShortcutId,
		long groupId, long folderId, OrderByComparator orderByComparator)
		throws NoSuchFileShortcutException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_PrevAndNext(fileShortcutId, groupId, folderId,
				orderByComparator);
		}

		DLFileShortcut dlFileShortcut = findByPrimaryKey(fileShortcutId);

		Session session = null;

		try {
			session = openSession();

			DLFileShortcut[] array = new DLFileShortcutImpl[3];

			array[0] = filterGetByG_F_PrevAndNext(session, dlFileShortcut,
					groupId, folderId, orderByComparator, true);

			array[1] = dlFileShortcut;

			array[2] = filterGetByG_F_PrevAndNext(session, dlFileShortcut,
					groupId, folderId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileShortcut filterGetByG_F_PrevAndNext(Session session,
		DLFileShortcut dlFileShortcut, long groupId, long folderId,
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
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_NO_INLINE_DISTINCT_WHERE_2);
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

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileShortcut.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DLFileShortcutImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DLFileShortcutImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileShortcut);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileShortcut> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @return the matching document library file shortcuts
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileShortcut> findByG_F_A(long groupId, long folderId,
		boolean active) throws SystemException {
		return findByG_F_A(groupId, folderId, active, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @return the range of matching document library file shortcuts
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileShortcut> findByG_F_A(long groupId, long folderId,
		boolean active, int start, int end) throws SystemException {
		return findByG_F_A(groupId, folderId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file shortcuts
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileShortcut> findByG_F_A(long groupId, long folderId,
		boolean active, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_A;
			finderArgs = new Object[] { groupId, folderId, active };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F_A;
			finderArgs = new Object[] {
					groupId, folderId, active,
					
					start, end, orderByComparator
				};
		}

		List<DLFileShortcut> list = (List<DLFileShortcut>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFileShortcut dlFileShortcut : list) {
				if ((groupId != dlFileShortcut.getGroupId()) ||
						(folderId != dlFileShortcut.getFolderId()) ||
						(active != dlFileShortcut.getActive())) {
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

			query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

			query.append(_FINDER_COLUMN_G_F_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_A_FOLDERID_2);

			query.append(_FINDER_COLUMN_G_F_A_ACTIVE_2);

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

				qPos.add(folderId);

				qPos.add(active);

				list = (List<DLFileShortcut>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file shortcut
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching document library file shortcut could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut findByG_F_A_First(long groupId, long folderId,
		boolean active, OrderByComparator orderByComparator)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = fetchByG_F_A_First(groupId, folderId,
				active, orderByComparator);

		if (dlFileShortcut != null) {
			return dlFileShortcut;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(", active=");
		msg.append(active);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileShortcutException(msg.toString());
	}

	/**
	 * Returns the first document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut fetchByG_F_A_First(long groupId, long folderId,
		boolean active, OrderByComparator orderByComparator)
		throws SystemException {
		List<DLFileShortcut> list = findByG_F_A(groupId, folderId, active, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file shortcut
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching document library file shortcut could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut findByG_F_A_Last(long groupId, long folderId,
		boolean active, OrderByComparator orderByComparator)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = fetchByG_F_A_Last(groupId, folderId,
				active, orderByComparator);

		if (dlFileShortcut != null) {
			return dlFileShortcut;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(", active=");
		msg.append(active);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileShortcutException(msg.toString());
	}

	/**
	 * Returns the last document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut fetchByG_F_A_Last(long groupId, long folderId,
		boolean active, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_F_A(groupId, folderId, active);

		List<DLFileShortcut> list = findByG_F_A(groupId, folderId, active,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63;.
	 *
	 * @param fileShortcutId the primary key of the current document library file shortcut
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file shortcut
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut[] findByG_F_A_PrevAndNext(long fileShortcutId,
		long groupId, long folderId, boolean active,
		OrderByComparator orderByComparator)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = findByPrimaryKey(fileShortcutId);

		Session session = null;

		try {
			session = openSession();

			DLFileShortcut[] array = new DLFileShortcutImpl[3];

			array[0] = getByG_F_A_PrevAndNext(session, dlFileShortcut, groupId,
					folderId, active, orderByComparator, true);

			array[1] = dlFileShortcut;

			array[2] = getByG_F_A_PrevAndNext(session, dlFileShortcut, groupId,
					folderId, active, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileShortcut getByG_F_A_PrevAndNext(Session session,
		DLFileShortcut dlFileShortcut, long groupId, long folderId,
		boolean active, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

		query.append(_FINDER_COLUMN_G_F_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_A_FOLDERID_2);

		query.append(_FINDER_COLUMN_G_F_A_ACTIVE_2);

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

		qPos.add(folderId);

		qPos.add(active);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileShortcut);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileShortcut> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @return the matching document library file shortcuts that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileShortcut> filterFindByG_F_A(long groupId, long folderId,
		boolean active) throws SystemException {
		return filterFindByG_F_A(groupId, folderId, active, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @return the range of matching document library file shortcuts that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileShortcut> filterFindByG_F_A(long groupId, long folderId,
		boolean active, int start, int end) throws SystemException {
		return filterFindByG_F_A(groupId, folderId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file shortcuts that the user has permissions to view where groupId = &#63; and folderId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file shortcuts that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileShortcut> filterFindByG_F_A(long groupId, long folderId,
		boolean active, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_A(groupId, folderId, active, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_F_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_A_FOLDERID_2);

		query.append(_FINDER_COLUMN_G_F_A_ACTIVE_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_NO_INLINE_DISTINCT_WHERE_2);
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

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileShortcut.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFileShortcutImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFileShortcutImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

			qPos.add(active);

			return (List<DLFileShortcut>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set of document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63;.
	 *
	 * @param fileShortcutId the primary key of the current document library file shortcut
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file shortcut
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut[] filterFindByG_F_A_PrevAndNext(long fileShortcutId,
		long groupId, long folderId, boolean active,
		OrderByComparator orderByComparator)
		throws NoSuchFileShortcutException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_A_PrevAndNext(fileShortcutId, groupId, folderId,
				active, orderByComparator);
		}

		DLFileShortcut dlFileShortcut = findByPrimaryKey(fileShortcutId);

		Session session = null;

		try {
			session = openSession();

			DLFileShortcut[] array = new DLFileShortcutImpl[3];

			array[0] = filterGetByG_F_A_PrevAndNext(session, dlFileShortcut,
					groupId, folderId, active, orderByComparator, true);

			array[1] = dlFileShortcut;

			array[2] = filterGetByG_F_A_PrevAndNext(session, dlFileShortcut,
					groupId, folderId, active, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileShortcut filterGetByG_F_A_PrevAndNext(Session session,
		DLFileShortcut dlFileShortcut, long groupId, long folderId,
		boolean active, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_F_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_A_FOLDERID_2);

		query.append(_FINDER_COLUMN_G_F_A_ACTIVE_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_NO_INLINE_DISTINCT_WHERE_2);
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

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileShortcut.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DLFileShortcutImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DLFileShortcutImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		qPos.add(active);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileShortcut);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileShortcut> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param status the status
	 * @return the matching document library file shortcuts
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileShortcut> findByG_F_A_S(long groupId, long folderId,
		boolean active, int status) throws SystemException {
		return findByG_F_A_S(groupId, folderId, active, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param status the status
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @return the range of matching document library file shortcuts
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileShortcut> findByG_F_A_S(long groupId, long folderId,
		boolean active, int status, int start, int end)
		throws SystemException {
		return findByG_F_A_S(groupId, folderId, active, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param status the status
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file shortcuts
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileShortcut> findByG_F_A_S(long groupId, long folderId,
		boolean active, int status, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_A_S;
			finderArgs = new Object[] { groupId, folderId, active, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F_A_S;
			finderArgs = new Object[] {
					groupId, folderId, active, status,
					
					start, end, orderByComparator
				};
		}

		List<DLFileShortcut> list = (List<DLFileShortcut>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFileShortcut dlFileShortcut : list) {
				if ((groupId != dlFileShortcut.getGroupId()) ||
						(folderId != dlFileShortcut.getFolderId()) ||
						(active != dlFileShortcut.getActive()) ||
						(status != dlFileShortcut.getStatus())) {
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

			query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

			query.append(_FINDER_COLUMN_G_F_A_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_A_S_FOLDERID_2);

			query.append(_FINDER_COLUMN_G_F_A_S_ACTIVE_2);

			query.append(_FINDER_COLUMN_G_F_A_S_STATUS_2);

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

				qPos.add(folderId);

				qPos.add(active);

				qPos.add(status);

				list = (List<DLFileShortcut>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file shortcut
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching document library file shortcut could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut findByG_F_A_S_First(long groupId, long folderId,
		boolean active, int status, OrderByComparator orderByComparator)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = fetchByG_F_A_S_First(groupId, folderId,
				active, status, orderByComparator);

		if (dlFileShortcut != null) {
			return dlFileShortcut;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(", active=");
		msg.append(active);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileShortcutException(msg.toString());
	}

	/**
	 * Returns the first document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut fetchByG_F_A_S_First(long groupId, long folderId,
		boolean active, int status, OrderByComparator orderByComparator)
		throws SystemException {
		List<DLFileShortcut> list = findByG_F_A_S(groupId, folderId, active,
				status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file shortcut
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a matching document library file shortcut could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut findByG_F_A_S_Last(long groupId, long folderId,
		boolean active, int status, OrderByComparator orderByComparator)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = fetchByG_F_A_S_Last(groupId, folderId,
				active, status, orderByComparator);

		if (dlFileShortcut != null) {
			return dlFileShortcut;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(", active=");
		msg.append(active);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileShortcutException(msg.toString());
	}

	/**
	 * Returns the last document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut fetchByG_F_A_S_Last(long groupId, long folderId,
		boolean active, int status, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_F_A_S(groupId, folderId, active, status);

		List<DLFileShortcut> list = findByG_F_A_S(groupId, folderId, active,
				status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param fileShortcutId the primary key of the current document library file shortcut
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file shortcut
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut[] findByG_F_A_S_PrevAndNext(long fileShortcutId,
		long groupId, long folderId, boolean active, int status,
		OrderByComparator orderByComparator)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = findByPrimaryKey(fileShortcutId);

		Session session = null;

		try {
			session = openSession();

			DLFileShortcut[] array = new DLFileShortcutImpl[3];

			array[0] = getByG_F_A_S_PrevAndNext(session, dlFileShortcut,
					groupId, folderId, active, status, orderByComparator, true);

			array[1] = dlFileShortcut;

			array[2] = getByG_F_A_S_PrevAndNext(session, dlFileShortcut,
					groupId, folderId, active, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileShortcut getByG_F_A_S_PrevAndNext(Session session,
		DLFileShortcut dlFileShortcut, long groupId, long folderId,
		boolean active, int status, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

		query.append(_FINDER_COLUMN_G_F_A_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_A_S_FOLDERID_2);

		query.append(_FINDER_COLUMN_G_F_A_S_ACTIVE_2);

		query.append(_FINDER_COLUMN_G_F_A_S_STATUS_2);

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

		qPos.add(folderId);

		qPos.add(active);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileShortcut);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileShortcut> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param status the status
	 * @return the matching document library file shortcuts that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileShortcut> filterFindByG_F_A_S(long groupId,
		long folderId, boolean active, int status) throws SystemException {
		return filterFindByG_F_A_S(groupId, folderId, active, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param status the status
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @return the range of matching document library file shortcuts that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileShortcut> filterFindByG_F_A_S(long groupId,
		long folderId, boolean active, int status, int start, int end)
		throws SystemException {
		return filterFindByG_F_A_S(groupId, folderId, active, status, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the document library file shortcuts that the user has permissions to view where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param status the status
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file shortcuts that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileShortcut> filterFindByG_F_A_S(long groupId,
		long folderId, boolean active, int status, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_A_S(groupId, folderId, active, status, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_F_A_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_A_S_FOLDERID_2);

		query.append(_FINDER_COLUMN_G_F_A_S_ACTIVE_2);

		query.append(_FINDER_COLUMN_G_F_A_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_NO_INLINE_DISTINCT_WHERE_2);
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

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileShortcut.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFileShortcutImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFileShortcutImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

			qPos.add(active);

			qPos.add(status);

			return (List<DLFileShortcut>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set of document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param fileShortcutId the primary key of the current document library file shortcut
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file shortcut
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut[] filterFindByG_F_A_S_PrevAndNext(
		long fileShortcutId, long groupId, long folderId, boolean active,
		int status, OrderByComparator orderByComparator)
		throws NoSuchFileShortcutException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_A_S_PrevAndNext(fileShortcutId, groupId, folderId,
				active, status, orderByComparator);
		}

		DLFileShortcut dlFileShortcut = findByPrimaryKey(fileShortcutId);

		Session session = null;

		try {
			session = openSession();

			DLFileShortcut[] array = new DLFileShortcutImpl[3];

			array[0] = filterGetByG_F_A_S_PrevAndNext(session, dlFileShortcut,
					groupId, folderId, active, status, orderByComparator, true);

			array[1] = dlFileShortcut;

			array[2] = filterGetByG_F_A_S_PrevAndNext(session, dlFileShortcut,
					groupId, folderId, active, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileShortcut filterGetByG_F_A_S_PrevAndNext(Session session,
		DLFileShortcut dlFileShortcut, long groupId, long folderId,
		boolean active, int status, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_F_A_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_A_S_FOLDERID_2);

		query.append(_FINDER_COLUMN_G_F_A_S_ACTIVE_2);

		query.append(_FINDER_COLUMN_G_F_A_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_NO_INLINE_DISTINCT_WHERE_2);
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

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileShortcut.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DLFileShortcutImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DLFileShortcutImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		qPos.add(active);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileShortcut);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileShortcut> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file shortcuts.
	 *
	 * @return the document library file shortcuts
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileShortcut> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file shortcuts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @return the range of document library file shortcuts
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileShortcut> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file shortcuts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of document library file shortcuts
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileShortcut> findAll(int start, int end,
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

		List<DLFileShortcut> list = (List<DLFileShortcut>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DLFILESHORTCUT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DLFILESHORTCUT;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<DLFileShortcut>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DLFileShortcut>)QueryUtil.list(q,
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
	 * Removes all the document library file shortcuts where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (DLFileShortcut dlFileShortcut : findByUuid(uuid)) {
			remove(dlFileShortcut);
		}
	}

	/**
	 * Removes the document library file shortcut where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the document library file shortcut that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileShortcut removeByUUID_G(String uuid, long groupId)
		throws NoSuchFileShortcutException, SystemException {
		DLFileShortcut dlFileShortcut = findByUUID_G(uuid, groupId);

		return remove(dlFileShortcut);
	}

	/**
	 * Removes all the document library file shortcuts where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid_C(String uuid, long companyId)
		throws SystemException {
		for (DLFileShortcut dlFileShortcut : findByUuid_C(uuid, companyId)) {
			remove(dlFileShortcut);
		}
	}

	/**
	 * Removes all the document library file shortcuts where toFileEntryId = &#63; from the database.
	 *
	 * @param toFileEntryId the to file entry ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByToFileEntryId(long toFileEntryId)
		throws SystemException {
		for (DLFileShortcut dlFileShortcut : findByToFileEntryId(toFileEntryId)) {
			remove(dlFileShortcut);
		}
	}

	/**
	 * Removes all the document library file shortcuts where groupId = &#63; and folderId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_F(long groupId, long folderId)
		throws SystemException {
		for (DLFileShortcut dlFileShortcut : findByG_F(groupId, folderId)) {
			remove(dlFileShortcut);
		}
	}

	/**
	 * Removes all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_F_A(long groupId, long folderId, boolean active)
		throws SystemException {
		for (DLFileShortcut dlFileShortcut : findByG_F_A(groupId, folderId,
				active)) {
			remove(dlFileShortcut);
		}
	}

	/**
	 * Removes all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param status the status
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_F_A_S(long groupId, long folderId, boolean active,
		int status) throws SystemException {
		for (DLFileShortcut dlFileShortcut : findByG_F_A_S(groupId, folderId,
				active, status)) {
			remove(dlFileShortcut);
		}
	}

	/**
	 * Removes all the document library file shortcuts from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (DLFileShortcut dlFileShortcut : findAll()) {
			remove(dlFileShortcut);
		}
	}

	/**
	 * Returns the number of document library file shortcuts where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching document library file shortcuts
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILESHORTCUT_WHERE);

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
	 * Returns the number of document library file shortcuts where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching document library file shortcuts
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILESHORTCUT_WHERE);

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
	 * Returns the number of document library file shortcuts where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching document library file shortcuts
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid_C(String uuid, long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILESHORTCUT_WHERE);

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
	 * Returns the number of document library file shortcuts where toFileEntryId = &#63;.
	 *
	 * @param toFileEntryId the to file entry ID
	 * @return the number of matching document library file shortcuts
	 * @throws SystemException if a system exception occurred
	 */
	public int countByToFileEntryId(long toFileEntryId)
		throws SystemException {
		Object[] finderArgs = new Object[] { toFileEntryId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_TOFILEENTRYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILESHORTCUT_WHERE);

			query.append(_FINDER_COLUMN_TOFILEENTRYID_TOFILEENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(toFileEntryId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_TOFILEENTRYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library file shortcuts where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the number of matching document library file shortcuts
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_F(long groupId, long folderId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, folderId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_F,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILESHORTCUT_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_F, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the number of matching document library file shortcuts that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_F(long groupId, long folderId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F(groupId, folderId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_DLFILESHORTCUT_WHERE);

		query.append(_FINDER_COLUMN_G_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileShortcut.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

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
	 * Returns the number of document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @return the number of matching document library file shortcuts
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_F_A(long groupId, long folderId, boolean active)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, folderId, active };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_F_A,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DLFILESHORTCUT_WHERE);

			query.append(_FINDER_COLUMN_G_F_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_A_FOLDERID_2);

			query.append(_FINDER_COLUMN_G_F_A_ACTIVE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				qPos.add(active);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_F_A,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @return the number of matching document library file shortcuts that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_F_A(long groupId, long folderId, boolean active)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F_A(groupId, folderId, active);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_DLFILESHORTCUT_WHERE);

		query.append(_FINDER_COLUMN_G_F_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_A_FOLDERID_2);

		query.append(_FINDER_COLUMN_G_F_A_ACTIVE_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileShortcut.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

			qPos.add(active);

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
	 * Returns the number of document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param status the status
	 * @return the number of matching document library file shortcuts
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_F_A_S(long groupId, long folderId, boolean active,
		int status) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, folderId, active, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_F_A_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_DLFILESHORTCUT_WHERE);

			query.append(_FINDER_COLUMN_G_F_A_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_A_S_FOLDERID_2);

			query.append(_FINDER_COLUMN_G_F_A_S_ACTIVE_2);

			query.append(_FINDER_COLUMN_G_F_A_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				qPos.add(active);

				qPos.add(status);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_F_A_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param status the status
	 * @return the number of matching document library file shortcuts that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_F_A_S(long groupId, long folderId,
		boolean active, int status) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F_A_S(groupId, folderId, active, status);
		}

		StringBundler query = new StringBundler(5);

		query.append(_FILTER_SQL_COUNT_DLFILESHORTCUT_WHERE);

		query.append(_FINDER_COLUMN_G_F_A_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_A_S_FOLDERID_2);

		query.append(_FINDER_COLUMN_G_F_A_S_ACTIVE_2);

		query.append(_FINDER_COLUMN_G_F_A_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileShortcut.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

			qPos.add(active);

			qPos.add(status);

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
	 * Returns the number of document library file shortcuts.
	 *
	 * @return the number of document library file shortcuts
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DLFILESHORTCUT);

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
	 * Initializes the document library file shortcut persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.documentlibrary.model.DLFileShortcut")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DLFileShortcut>> listenersList = new ArrayList<ModelListener<DLFileShortcut>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DLFileShortcut>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(DLFileShortcutImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = DLContentPersistence.class)
	protected DLContentPersistence dlContentPersistence;
	@BeanReference(type = DLFileEntryPersistence.class)
	protected DLFileEntryPersistence dlFileEntryPersistence;
	@BeanReference(type = DLFileEntryMetadataPersistence.class)
	protected DLFileEntryMetadataPersistence dlFileEntryMetadataPersistence;
	@BeanReference(type = DLFileEntryTypePersistence.class)
	protected DLFileEntryTypePersistence dlFileEntryTypePersistence;
	@BeanReference(type = DLFileRankPersistence.class)
	protected DLFileRankPersistence dlFileRankPersistence;
	@BeanReference(type = DLFileShortcutPersistence.class)
	protected DLFileShortcutPersistence dlFileShortcutPersistence;
	@BeanReference(type = DLFileVersionPersistence.class)
	protected DLFileVersionPersistence dlFileVersionPersistence;
	@BeanReference(type = DLFolderPersistence.class)
	protected DLFolderPersistence dlFolderPersistence;
	@BeanReference(type = DLSyncPersistence.class)
	protected DLSyncPersistence dlSyncPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	@BeanReference(type = AssetTagPersistence.class)
	protected AssetTagPersistence assetTagPersistence;
	@BeanReference(type = TrashEntryPersistence.class)
	protected TrashEntryPersistence trashEntryPersistence;
	private static final String _SQL_SELECT_DLFILESHORTCUT = "SELECT dlFileShortcut FROM DLFileShortcut dlFileShortcut";
	private static final String _SQL_SELECT_DLFILESHORTCUT_WHERE = "SELECT dlFileShortcut FROM DLFileShortcut dlFileShortcut WHERE ";
	private static final String _SQL_COUNT_DLFILESHORTCUT = "SELECT COUNT(dlFileShortcut) FROM DLFileShortcut dlFileShortcut";
	private static final String _SQL_COUNT_DLFILESHORTCUT_WHERE = "SELECT COUNT(dlFileShortcut) FROM DLFileShortcut dlFileShortcut WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "dlFileShortcut.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "dlFileShortcut.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(dlFileShortcut.uuid IS NULL OR dlFileShortcut.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "dlFileShortcut.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "dlFileShortcut.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(dlFileShortcut.uuid IS NULL OR dlFileShortcut.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "dlFileShortcut.groupId = ?";
	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "dlFileShortcut.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "dlFileShortcut.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(dlFileShortcut.uuid IS NULL OR dlFileShortcut.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "dlFileShortcut.companyId = ?";
	private static final String _FINDER_COLUMN_TOFILEENTRYID_TOFILEENTRYID_2 = "dlFileShortcut.toFileEntryId = ?";
	private static final String _FINDER_COLUMN_G_F_GROUPID_2 = "dlFileShortcut.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_FOLDERID_2 = "dlFileShortcut.folderId = ?";
	private static final String _FINDER_COLUMN_G_F_A_GROUPID_2 = "dlFileShortcut.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_A_FOLDERID_2 = "dlFileShortcut.folderId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_A_ACTIVE_2 = "dlFileShortcut.active = ?";
	private static final String _FINDER_COLUMN_G_F_A_S_GROUPID_2 = "dlFileShortcut.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_A_S_FOLDERID_2 = "dlFileShortcut.folderId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_A_S_ACTIVE_2 = "dlFileShortcut.active = ? AND ";
	private static final String _FINDER_COLUMN_G_F_A_S_STATUS_2 = "dlFileShortcut.status = ?";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "dlFileShortcut.fileShortcutId";
	private static final String _FILTER_SQL_SELECT_DLFILESHORTCUT_WHERE = "SELECT DISTINCT {dlFileShortcut.*} FROM DLFileShortcut dlFileShortcut WHERE ";
	private static final String _FILTER_SQL_SELECT_DLFILESHORTCUT_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {DLFileShortcut.*} FROM (SELECT DISTINCT dlFileShortcut.fileShortcutId FROM DLFileShortcut dlFileShortcut WHERE ";
	private static final String _FILTER_SQL_SELECT_DLFILESHORTCUT_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN DLFileShortcut ON TEMP_TABLE.fileShortcutId = DLFileShortcut.fileShortcutId";
	private static final String _FILTER_SQL_COUNT_DLFILESHORTCUT_WHERE = "SELECT COUNT(DISTINCT dlFileShortcut.fileShortcutId) AS COUNT_VALUE FROM DLFileShortcut dlFileShortcut WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "dlFileShortcut";
	private static final String _FILTER_ENTITY_TABLE = "DLFileShortcut";
	private static final String _ORDER_BY_ENTITY_ALIAS = "dlFileShortcut.";
	private static final String _ORDER_BY_ENTITY_TABLE = "DLFileShortcut.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DLFileShortcut exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DLFileShortcut exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(DLFileShortcutPersistenceImpl.class);
	private static DLFileShortcut _nullDLFileShortcut = new DLFileShortcutImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<DLFileShortcut> toCacheModel() {
				return _nullDLFileShortcutCacheModel;
			}
		};

	private static CacheModel<DLFileShortcut> _nullDLFileShortcutCacheModel = new CacheModel<DLFileShortcut>() {
			public DLFileShortcut toEntityModel() {
				return _nullDLFileShortcut;
			}
		};
}