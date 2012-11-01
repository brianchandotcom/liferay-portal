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
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.documentlibrary.NoSuchFileVersionException;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.impl.DLFileVersionImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileVersionModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the document library file version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileVersionPersistence
 * @see DLFileVersionUtil
 * @generated
 */
public class DLFileVersionPersistenceImpl extends BasePersistenceImpl<DLFileVersion>
	implements DLFileVersionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DLFileVersionUtil} to access the document library file version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DLFileVersionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid", new String[] { String.class.getName() },
			DLFileVersionModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			DLFileVersionModelImpl.UUID_COLUMN_BITMASK |
			DLFileVersionModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			DLFileVersionModelImpl.UUID_COLUMN_BITMASK |
			DLFileVersionModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_FILEENTRYID =
		new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByFileEntryId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYID =
		new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByFileEntryId", new String[] { Long.class.getName() },
			DLFileVersionModelImpl.FILEENTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_FILEENTRYID = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFileEntryId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_F_V = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByF_V",
			new String[] { Long.class.getName(), String.class.getName() },
			DLFileVersionModelImpl.FILEENTRYID_COLUMN_BITMASK |
			DLFileVersionModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_F_V = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByF_V",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_F_S = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByF_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_F_S = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByF_S",
			new String[] { Long.class.getName(), Integer.class.getName() },
			DLFileVersionModelImpl.FILEENTRYID_COLUMN_BITMASK |
			DLFileVersionModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_F_S = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByF_S",
			new String[] { Long.class.getName(), Integer.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F_S = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_F_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_S = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_F_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			DLFileVersionModelImpl.GROUPID_COLUMN_BITMASK |
			DLFileVersionModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileVersionModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F_S = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F_T_V = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_F_T_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_T_V =
		new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_F_T_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName()
			},
			DLFileVersionModelImpl.GROUPID_COLUMN_BITMASK |
			DLFileVersionModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileVersionModelImpl.TITLE_COLUMN_BITMASK |
			DLFileVersionModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F_T_V = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F_T_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), String.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED,
			DLFileVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the document library file version in the entity cache if it is enabled.
	 *
	 * @param dlFileVersion the document library file version
	 */
	public void cacheResult(DLFileVersion dlFileVersion) {
		EntityCacheUtil.putResult(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionImpl.class, dlFileVersion.getPrimaryKey(),
			dlFileVersion);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				dlFileVersion.getUuid(),
				Long.valueOf(dlFileVersion.getGroupId())
			}, dlFileVersion);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_F_V,
			new Object[] {
				Long.valueOf(dlFileVersion.getFileEntryId()),
				
			dlFileVersion.getVersion()
			}, dlFileVersion);

		dlFileVersion.resetOriginalValues();
	}

	/**
	 * Caches the document library file versions in the entity cache if it is enabled.
	 *
	 * @param dlFileVersions the document library file versions
	 */
	public void cacheResult(List<DLFileVersion> dlFileVersions) {
		for (DLFileVersion dlFileVersion : dlFileVersions) {
			if (EntityCacheUtil.getResult(
						DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
						DLFileVersionImpl.class, dlFileVersion.getPrimaryKey()) == null) {
				cacheResult(dlFileVersion);
			}
			else {
				dlFileVersion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all document library file versions.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(DLFileVersionImpl.class.getName());
		}

		EntityCacheUtil.clearCache(DLFileVersionImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the document library file version.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DLFileVersion dlFileVersion) {
		EntityCacheUtil.removeResult(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionImpl.class, dlFileVersion.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(dlFileVersion);
	}

	@Override
	public void clearCache(List<DLFileVersion> dlFileVersions) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DLFileVersion dlFileVersion : dlFileVersions) {
			EntityCacheUtil.removeResult(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
				DLFileVersionImpl.class, dlFileVersion.getPrimaryKey());

			clearUniqueFindersCache(dlFileVersion);
		}
	}

	protected void clearUniqueFindersCache(DLFileVersion dlFileVersion) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				dlFileVersion.getUuid(),
				Long.valueOf(dlFileVersion.getGroupId())
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_F_V,
			new Object[] {
				Long.valueOf(dlFileVersion.getFileEntryId()),
				
			dlFileVersion.getVersion()
			});
	}

	/**
	 * Creates a new document library file version with the primary key. Does not add the document library file version to the database.
	 *
	 * @param fileVersionId the primary key for the new document library file version
	 * @return the new document library file version
	 */
	public DLFileVersion create(long fileVersionId) {
		DLFileVersion dlFileVersion = new DLFileVersionImpl();

		dlFileVersion.setNew(true);
		dlFileVersion.setPrimaryKey(fileVersionId);

		String uuid = PortalUUIDUtil.generate();

		dlFileVersion.setUuid(uuid);

		return dlFileVersion;
	}

	/**
	 * Removes the document library file version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fileVersionId the primary key of the document library file version
	 * @return the document library file version that was removed
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a document library file version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion remove(long fileVersionId)
		throws NoSuchFileVersionException, SystemException {
		return remove(Long.valueOf(fileVersionId));
	}

	/**
	 * Removes the document library file version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the document library file version
	 * @return the document library file version that was removed
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a document library file version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DLFileVersion remove(Serializable primaryKey)
		throws NoSuchFileVersionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLFileVersion dlFileVersion = (DLFileVersion)session.get(DLFileVersionImpl.class,
					primaryKey);

			if (dlFileVersion == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFileVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(dlFileVersion);
		}
		catch (NoSuchFileVersionException nsee) {
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
	protected DLFileVersion removeImpl(DLFileVersion dlFileVersion)
		throws SystemException {
		dlFileVersion = toUnwrappedModel(dlFileVersion);

		Session session = null;

		try {
			session = openSession();

			if (dlFileVersion.isCachedModel()) {
				dlFileVersion = (DLFileVersion)session.get(DLFileVersionImpl.class,
						dlFileVersion.getPrimaryKeyObj());
			}

			if (dlFileVersion != null) {
				session.delete(dlFileVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (dlFileVersion != null) {
			clearCache(dlFileVersion);
		}

		return dlFileVersion;
	}

	@Override
	public DLFileVersion updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion)
		throws SystemException {
		dlFileVersion = toUnwrappedModel(dlFileVersion);

		boolean isNew = dlFileVersion.isNew();

		DLFileVersionModelImpl dlFileVersionModelImpl = (DLFileVersionModelImpl)dlFileVersion;

		if (Validator.isNull(dlFileVersion.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			dlFileVersion.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (dlFileVersion.isNew()) {
				session.save(dlFileVersion);

				dlFileVersion.setNew(false);
			}
			else {
				session.merge(dlFileVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !DLFileVersionModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((dlFileVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileVersionModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { dlFileVersionModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((dlFileVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileVersionModelImpl.getOriginalUuid(),
						Long.valueOf(dlFileVersionModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						dlFileVersionModelImpl.getUuid(),
						Long.valueOf(dlFileVersionModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((dlFileVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(dlFileVersionModelImpl.getOriginalFileEntryId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_FILEENTRYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYID,
					args);

				args = new Object[] {
						Long.valueOf(dlFileVersionModelImpl.getFileEntryId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_FILEENTRYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYID,
					args);
			}

			if ((dlFileVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_F_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(dlFileVersionModelImpl.getOriginalFileEntryId()),
						Integer.valueOf(dlFileVersionModelImpl.getOriginalStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_F_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_F_S,
					args);

				args = new Object[] {
						Long.valueOf(dlFileVersionModelImpl.getFileEntryId()),
						Integer.valueOf(dlFileVersionModelImpl.getStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_F_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_F_S,
					args);
			}

			if ((dlFileVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(dlFileVersionModelImpl.getOriginalGroupId()),
						Long.valueOf(dlFileVersionModelImpl.getOriginalFolderId()),
						Integer.valueOf(dlFileVersionModelImpl.getOriginalStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_F_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_S,
					args);

				args = new Object[] {
						Long.valueOf(dlFileVersionModelImpl.getGroupId()),
						Long.valueOf(dlFileVersionModelImpl.getFolderId()),
						Integer.valueOf(dlFileVersionModelImpl.getStatus())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_F_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_S,
					args);
			}

			if ((dlFileVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_T_V.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(dlFileVersionModelImpl.getOriginalGroupId()),
						Long.valueOf(dlFileVersionModelImpl.getOriginalFolderId()),
						
						dlFileVersionModelImpl.getOriginalTitle(),
						
						dlFileVersionModelImpl.getOriginalVersion()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_F_T_V, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_T_V,
					args);

				args = new Object[] {
						Long.valueOf(dlFileVersionModelImpl.getGroupId()),
						Long.valueOf(dlFileVersionModelImpl.getFolderId()),
						
						dlFileVersionModelImpl.getTitle(),
						
						dlFileVersionModelImpl.getVersion()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_F_T_V, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_T_V,
					args);
			}
		}

		EntityCacheUtil.putResult(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
			DLFileVersionImpl.class, dlFileVersion.getPrimaryKey(),
			dlFileVersion);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					dlFileVersion.getUuid(),
					Long.valueOf(dlFileVersion.getGroupId())
				}, dlFileVersion);

			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_F_V,
				new Object[] {
					Long.valueOf(dlFileVersion.getFileEntryId()),
					
				dlFileVersion.getVersion()
				}, dlFileVersion);
		}
		else {
			if ((dlFileVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileVersionModelImpl.getOriginalUuid(),
						Long.valueOf(dlFileVersionModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
					new Object[] {
						dlFileVersion.getUuid(),
						Long.valueOf(dlFileVersion.getGroupId())
					}, dlFileVersion);
			}

			if ((dlFileVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_F_V.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(dlFileVersionModelImpl.getOriginalFileEntryId()),
						
						dlFileVersionModelImpl.getOriginalVersion()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_F_V, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_F_V, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_F_V,
					new Object[] {
						Long.valueOf(dlFileVersion.getFileEntryId()),
						
					dlFileVersion.getVersion()
					}, dlFileVersion);
			}
		}

		return dlFileVersion;
	}

	protected DLFileVersion toUnwrappedModel(DLFileVersion dlFileVersion) {
		if (dlFileVersion instanceof DLFileVersionImpl) {
			return dlFileVersion;
		}

		DLFileVersionImpl dlFileVersionImpl = new DLFileVersionImpl();

		dlFileVersionImpl.setNew(dlFileVersion.isNew());
		dlFileVersionImpl.setPrimaryKey(dlFileVersion.getPrimaryKey());

		dlFileVersionImpl.setUuid(dlFileVersion.getUuid());
		dlFileVersionImpl.setFileVersionId(dlFileVersion.getFileVersionId());
		dlFileVersionImpl.setGroupId(dlFileVersion.getGroupId());
		dlFileVersionImpl.setCompanyId(dlFileVersion.getCompanyId());
		dlFileVersionImpl.setUserId(dlFileVersion.getUserId());
		dlFileVersionImpl.setUserName(dlFileVersion.getUserName());
		dlFileVersionImpl.setCreateDate(dlFileVersion.getCreateDate());
		dlFileVersionImpl.setModifiedDate(dlFileVersion.getModifiedDate());
		dlFileVersionImpl.setRepositoryId(dlFileVersion.getRepositoryId());
		dlFileVersionImpl.setFolderId(dlFileVersion.getFolderId());
		dlFileVersionImpl.setFileEntryId(dlFileVersion.getFileEntryId());
		dlFileVersionImpl.setExtension(dlFileVersion.getExtension());
		dlFileVersionImpl.setMimeType(dlFileVersion.getMimeType());
		dlFileVersionImpl.setTitle(dlFileVersion.getTitle());
		dlFileVersionImpl.setDescription(dlFileVersion.getDescription());
		dlFileVersionImpl.setChangeLog(dlFileVersion.getChangeLog());
		dlFileVersionImpl.setExtraSettings(dlFileVersion.getExtraSettings());
		dlFileVersionImpl.setFileEntryTypeId(dlFileVersion.getFileEntryTypeId());
		dlFileVersionImpl.setVersion(dlFileVersion.getVersion());
		dlFileVersionImpl.setSize(dlFileVersion.getSize());
		dlFileVersionImpl.setChecksum(dlFileVersion.getChecksum());
		dlFileVersionImpl.setStatus(dlFileVersion.getStatus());
		dlFileVersionImpl.setStatusByUserId(dlFileVersion.getStatusByUserId());
		dlFileVersionImpl.setStatusByUserName(dlFileVersion.getStatusByUserName());
		dlFileVersionImpl.setStatusDate(dlFileVersion.getStatusDate());

		return dlFileVersionImpl;
	}

	/**
	 * Returns the document library file version with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the document library file version
	 * @return the document library file version
	 * @throws com.liferay.portal.NoSuchModelException if a document library file version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DLFileVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the document library file version with the primary key or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileVersionException} if it could not be found.
	 *
	 * @param fileVersionId the primary key of the document library file version
	 * @return the document library file version
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a document library file version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion findByPrimaryKey(long fileVersionId)
		throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = fetchByPrimaryKey(fileVersionId);

		if (dlFileVersion == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + fileVersionId);
			}

			throw new NoSuchFileVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				fileVersionId);
		}

		return dlFileVersion;
	}

	/**
	 * Returns the document library file version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the document library file version
	 * @return the document library file version, or <code>null</code> if a document library file version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DLFileVersion fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the document library file version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fileVersionId the primary key of the document library file version
	 * @return the document library file version, or <code>null</code> if a document library file version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion fetchByPrimaryKey(long fileVersionId)
		throws SystemException {
		DLFileVersion dlFileVersion = (DLFileVersion)EntityCacheUtil.getResult(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
				DLFileVersionImpl.class, fileVersionId);

		if (dlFileVersion == _nullDLFileVersion) {
			return null;
		}

		if (dlFileVersion == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				dlFileVersion = (DLFileVersion)session.get(DLFileVersionImpl.class,
						Long.valueOf(fileVersionId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (dlFileVersion != null) {
					cacheResult(dlFileVersion);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(DLFileVersionModelImpl.ENTITY_CACHE_ENABLED,
						DLFileVersionImpl.class, fileVersionId,
						_nullDLFileVersion);
				}

				closeSession(session);
			}
		}

		return dlFileVersion;
	}

	/**
	 * Returns all the document library file versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching document library file versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileVersion> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileVersion> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileVersion> findByUuid(String uuid, int start, int end,
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

		List<DLFileVersion> list = (List<DLFileVersion>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFileVersion dlFileVersion : list) {
				if (!Validator.equals(uuid, dlFileVersion.getUuid())) {
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

			query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

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
				query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
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

				list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a matching document library file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = fetchByUuid_First(uuid, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the first document library file version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<DLFileVersion> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a matching document library file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = fetchByUuid_Last(uuid, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the last document library file version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<DLFileVersion> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file versions before and after the current document library file version in the ordered set where uuid = &#63;.
	 *
	 * @param fileVersionId the primary key of the current document library file version
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file version
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a document library file version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion[] findByUuid_PrevAndNext(long fileVersionId,
		String uuid, OrderByComparator orderByComparator)
		throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = findByPrimaryKey(fileVersionId);

		Session session = null;

		try {
			session = openSession();

			DLFileVersion[] array = new DLFileVersionImpl[3];

			array[0] = getByUuid_PrevAndNext(session, dlFileVersion, uuid,
					orderByComparator, true);

			array[1] = dlFileVersion;

			array[2] = getByUuid_PrevAndNext(session, dlFileVersion, uuid,
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

	protected DLFileVersion getByUuid_PrevAndNext(Session session,
		DLFileVersion dlFileVersion, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

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
			query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the document library file version where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileVersionException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching document library file version
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a matching document library file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion findByUUID_G(String uuid, long groupId)
		throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = fetchByUUID_G(uuid, groupId);

		if (dlFileVersion == null) {
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

			throw new NoSuchFileVersionException(msg.toString());
		}

		return dlFileVersion;
	}

	/**
	 * Returns the document library file version where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the document library file version where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof DLFileVersion) {
			DLFileVersion dlFileVersion = (DLFileVersion)result;

			if (!Validator.equals(uuid, dlFileVersion.getUuid()) ||
					(groupId != dlFileVersion.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

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

			query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);

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

				List<DLFileVersion> list = q.list();

				result = list;

				DLFileVersion dlFileVersion = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					dlFileVersion = list.get(0);

					cacheResult(dlFileVersion);

					if ((dlFileVersion.getUuid() == null) ||
							!dlFileVersion.getUuid().equals(uuid) ||
							(dlFileVersion.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, dlFileVersion);
					}
				}

				return dlFileVersion;
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
				return (DLFileVersion)result;
			}
		}
	}

	/**
	 * Returns all the document library file versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching document library file versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileVersion> findByUuid_C(String uuid, long companyId)
		throws SystemException {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileVersion> findByUuid_C(String uuid, long companyId,
		int start, int end) throws SystemException {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileVersion> findByUuid_C(String uuid, long companyId,
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

		List<DLFileVersion> list = (List<DLFileVersion>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFileVersion dlFileVersion : list) {
				if (!Validator.equals(uuid, dlFileVersion.getUuid()) ||
						(companyId != dlFileVersion.getCompanyId())) {
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

			query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

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
				query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
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

				list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a matching document library file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion findByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the first document library file version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<DLFileVersion> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a matching document library file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the last document library file version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid_C(uuid, companyId);

		List<DLFileVersion> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file versions before and after the current document library file version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param fileVersionId the primary key of the current document library file version
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file version
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a document library file version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion[] findByUuid_C_PrevAndNext(long fileVersionId,
		String uuid, long companyId, OrderByComparator orderByComparator)
		throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = findByPrimaryKey(fileVersionId);

		Session session = null;

		try {
			session = openSession();

			DLFileVersion[] array = new DLFileVersionImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, dlFileVersion, uuid,
					companyId, orderByComparator, true);

			array[1] = dlFileVersion;

			array[2] = getByUuid_C_PrevAndNext(session, dlFileVersion, uuid,
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

	protected DLFileVersion getByUuid_C_PrevAndNext(Session session,
		DLFileVersion dlFileVersion, String uuid, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

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
			query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file versions where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the matching document library file versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileVersion> findByFileEntryId(long fileEntryId)
		throws SystemException {
		return findByFileEntryId(fileEntryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileVersion> findByFileEntryId(long fileEntryId, int start,
		int end) throws SystemException {
		return findByFileEntryId(fileEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileVersion> findByFileEntryId(long fileEntryId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYID;
			finderArgs = new Object[] { fileEntryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_FILEENTRYID;
			finderArgs = new Object[] { fileEntryId, start, end, orderByComparator };
		}

		List<DLFileVersion> list = (List<DLFileVersion>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFileVersion dlFileVersion : list) {
				if ((fileEntryId != dlFileVersion.getFileEntryId())) {
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

			query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryId);

				list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file version in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a matching document library file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion findByFileEntryId_First(long fileEntryId,
		OrderByComparator orderByComparator)
		throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = fetchByFileEntryId_First(fileEntryId,
				orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileEntryId=");
		msg.append(fileEntryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the first document library file version in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion fetchByFileEntryId_First(long fileEntryId,
		OrderByComparator orderByComparator) throws SystemException {
		List<DLFileVersion> list = findByFileEntryId(fileEntryId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file version in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a matching document library file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion findByFileEntryId_Last(long fileEntryId,
		OrderByComparator orderByComparator)
		throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = fetchByFileEntryId_Last(fileEntryId,
				orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileEntryId=");
		msg.append(fileEntryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the last document library file version in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion fetchByFileEntryId_Last(long fileEntryId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByFileEntryId(fileEntryId);

		List<DLFileVersion> list = findByFileEntryId(fileEntryId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file versions before and after the current document library file version in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileVersionId the primary key of the current document library file version
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file version
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a document library file version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion[] findByFileEntryId_PrevAndNext(long fileVersionId,
		long fileEntryId, OrderByComparator orderByComparator)
		throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = findByPrimaryKey(fileVersionId);

		Session session = null;

		try {
			session = openSession();

			DLFileVersion[] array = new DLFileVersionImpl[3];

			array[0] = getByFileEntryId_PrevAndNext(session, dlFileVersion,
					fileEntryId, orderByComparator, true);

			array[1] = dlFileVersion;

			array[2] = getByFileEntryId_PrevAndNext(session, dlFileVersion,
					fileEntryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileVersion getByFileEntryId_PrevAndNext(Session session,
		DLFileVersion dlFileVersion, long fileEntryId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

		query.append(_FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2);

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
			query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(fileEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the document library file version where fileEntryId = &#63; and version = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileVersionException} if it could not be found.
	 *
	 * @param fileEntryId the file entry ID
	 * @param version the version
	 * @return the matching document library file version
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a matching document library file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion findByF_V(long fileEntryId, String version)
		throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = fetchByF_V(fileEntryId, version);

		if (dlFileVersion == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("fileEntryId=");
			msg.append(fileEntryId);

			msg.append(", version=");
			msg.append(version);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFileVersionException(msg.toString());
		}

		return dlFileVersion;
	}

	/**
	 * Returns the document library file version where fileEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param fileEntryId the file entry ID
	 * @param version the version
	 * @return the matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion fetchByF_V(long fileEntryId, String version)
		throws SystemException {
		return fetchByF_V(fileEntryId, version, true);
	}

	/**
	 * Returns the document library file version where fileEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param fileEntryId the file entry ID
	 * @param version the version
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion fetchByF_V(long fileEntryId, String version,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { fileEntryId, version };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_F_V,
					finderArgs, this);
		}

		if (result instanceof DLFileVersion) {
			DLFileVersion dlFileVersion = (DLFileVersion)result;

			if ((fileEntryId != dlFileVersion.getFileEntryId()) ||
					!Validator.equals(version, dlFileVersion.getVersion())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_F_V_FILEENTRYID_2);

			if (version == null) {
				query.append(_FINDER_COLUMN_F_V_VERSION_1);
			}
			else {
				if (version.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_F_V_VERSION_3);
				}
				else {
					query.append(_FINDER_COLUMN_F_V_VERSION_2);
				}
			}

			query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryId);

				if (version != null) {
					qPos.add(version);
				}

				List<DLFileVersion> list = q.list();

				result = list;

				DLFileVersion dlFileVersion = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_F_V,
						finderArgs, list);
				}
				else {
					dlFileVersion = list.get(0);

					cacheResult(dlFileVersion);

					if ((dlFileVersion.getFileEntryId() != fileEntryId) ||
							(dlFileVersion.getVersion() == null) ||
							!dlFileVersion.getVersion().equals(version)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_F_V,
							finderArgs, dlFileVersion);
					}
				}

				return dlFileVersion;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_F_V,
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
				return (DLFileVersion)result;
			}
		}
	}

	/**
	 * Returns all the document library file versions where fileEntryId = &#63; and status = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @return the matching document library file versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileVersion> findByF_S(long fileEntryId, int status)
		throws SystemException {
		return findByF_S(fileEntryId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where fileEntryId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileVersion> findByF_S(long fileEntryId, int status,
		int start, int end) throws SystemException {
		return findByF_S(fileEntryId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where fileEntryId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileVersion> findByF_S(long fileEntryId, int status,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_F_S;
			finderArgs = new Object[] { fileEntryId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_F_S;
			finderArgs = new Object[] {
					fileEntryId, status,
					
					start, end, orderByComparator
				};
		}

		List<DLFileVersion> list = (List<DLFileVersion>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFileVersion dlFileVersion : list) {
				if ((fileEntryId != dlFileVersion.getFileEntryId()) ||
						(status != dlFileVersion.getStatus())) {
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

			query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_F_S_FILEENTRYID_2);

			query.append(_FINDER_COLUMN_F_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryId);

				qPos.add(status);

				list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file version in the ordered set where fileEntryId = &#63; and status = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a matching document library file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion findByF_S_First(long fileEntryId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = fetchByF_S_First(fileEntryId, status,
				orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileEntryId=");
		msg.append(fileEntryId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the first document library file version in the ordered set where fileEntryId = &#63; and status = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion fetchByF_S_First(long fileEntryId, int status,
		OrderByComparator orderByComparator) throws SystemException {
		List<DLFileVersion> list = findByF_S(fileEntryId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file version in the ordered set where fileEntryId = &#63; and status = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a matching document library file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion findByF_S_Last(long fileEntryId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = fetchByF_S_Last(fileEntryId, status,
				orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileEntryId=");
		msg.append(fileEntryId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the last document library file version in the ordered set where fileEntryId = &#63; and status = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion fetchByF_S_Last(long fileEntryId, int status,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByF_S(fileEntryId, status);

		List<DLFileVersion> list = findByF_S(fileEntryId, status, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file versions before and after the current document library file version in the ordered set where fileEntryId = &#63; and status = &#63;.
	 *
	 * @param fileVersionId the primary key of the current document library file version
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file version
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a document library file version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion[] findByF_S_PrevAndNext(long fileVersionId,
		long fileEntryId, int status, OrderByComparator orderByComparator)
		throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = findByPrimaryKey(fileVersionId);

		Session session = null;

		try {
			session = openSession();

			DLFileVersion[] array = new DLFileVersionImpl[3];

			array[0] = getByF_S_PrevAndNext(session, dlFileVersion,
					fileEntryId, status, orderByComparator, true);

			array[1] = dlFileVersion;

			array[2] = getByF_S_PrevAndNext(session, dlFileVersion,
					fileEntryId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileVersion getByF_S_PrevAndNext(Session session,
		DLFileVersion dlFileVersion, long fileEntryId, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

		query.append(_FINDER_COLUMN_F_S_FILEENTRYID_2);

		query.append(_FINDER_COLUMN_F_S_STATUS_2);

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
			query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(fileEntryId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file versions where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @return the matching document library file versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileVersion> findByG_F_S(long groupId, long folderId,
		int status) throws SystemException {
		return findByG_F_S(groupId, folderId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileVersion> findByG_F_S(long groupId, long folderId,
		int status, int start, int end) throws SystemException {
		return findByG_F_S(groupId, folderId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileVersion> findByG_F_S(long groupId, long folderId,
		int status, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_S;
			finderArgs = new Object[] { groupId, folderId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F_S;
			finderArgs = new Object[] {
					groupId, folderId, status,
					
					start, end, orderByComparator
				};
		}

		List<DLFileVersion> list = (List<DLFileVersion>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFileVersion dlFileVersion : list) {
				if ((groupId != dlFileVersion.getGroupId()) ||
						(folderId != dlFileVersion.getFolderId()) ||
						(status != dlFileVersion.getStatus())) {
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
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_F_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_S_FOLDERID_2);

			query.append(_FINDER_COLUMN_G_F_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				qPos.add(status);

				list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file version in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a matching document library file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion findByG_F_S_First(long groupId, long folderId,
		int status, OrderByComparator orderByComparator)
		throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = fetchByG_F_S_First(groupId, folderId,
				status, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the first document library file version in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion fetchByG_F_S_First(long groupId, long folderId,
		int status, OrderByComparator orderByComparator)
		throws SystemException {
		List<DLFileVersion> list = findByG_F_S(groupId, folderId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file version in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a matching document library file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion findByG_F_S_Last(long groupId, long folderId,
		int status, OrderByComparator orderByComparator)
		throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = fetchByG_F_S_Last(groupId, folderId,
				status, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the last document library file version in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion fetchByG_F_S_Last(long groupId, long folderId,
		int status, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_F_S(groupId, folderId, status);

		List<DLFileVersion> list = findByG_F_S(groupId, folderId, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file versions before and after the current document library file version in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param fileVersionId the primary key of the current document library file version
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file version
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a document library file version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion[] findByG_F_S_PrevAndNext(long fileVersionId,
		long groupId, long folderId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = findByPrimaryKey(fileVersionId);

		Session session = null;

		try {
			session = openSession();

			DLFileVersion[] array = new DLFileVersionImpl[3];

			array[0] = getByG_F_S_PrevAndNext(session, dlFileVersion, groupId,
					folderId, status, orderByComparator, true);

			array[1] = dlFileVersion;

			array[2] = getByG_F_S_PrevAndNext(session, dlFileVersion, groupId,
					folderId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileVersion getByG_F_S_PrevAndNext(Session session,
		DLFileVersion dlFileVersion, long groupId, long folderId, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

		query.append(_FINDER_COLUMN_G_F_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_S_FOLDERID_2);

		query.append(_FINDER_COLUMN_G_F_S_STATUS_2);

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
			query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @return the matching document library file versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileVersion> findByG_F_T_V(long groupId, long folderId,
		String title, String version) throws SystemException {
		return findByG_F_T_V(groupId, folderId, title, version,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of matching document library file versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileVersion> findByG_F_T_V(long groupId, long folderId,
		String title, String version, int start, int end)
		throws SystemException {
		return findByG_F_T_V(groupId, folderId, title, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileVersion> findByG_F_T_V(long groupId, long folderId,
		String title, String version, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_T_V;
			finderArgs = new Object[] { groupId, folderId, title, version };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F_T_V;
			finderArgs = new Object[] {
					groupId, folderId, title, version,
					
					start, end, orderByComparator
				};
		}

		List<DLFileVersion> list = (List<DLFileVersion>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFileVersion dlFileVersion : list) {
				if ((groupId != dlFileVersion.getGroupId()) ||
						(folderId != dlFileVersion.getFolderId()) ||
						!Validator.equals(title, dlFileVersion.getTitle()) ||
						!Validator.equals(version, dlFileVersion.getVersion())) {
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
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_F_T_V_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_T_V_FOLDERID_2);

			if (title == null) {
				query.append(_FINDER_COLUMN_G_F_T_V_TITLE_1);
			}
			else {
				if (title.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_F_T_V_TITLE_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_F_T_V_TITLE_2);
				}
			}

			if (version == null) {
				query.append(_FINDER_COLUMN_G_F_T_V_VERSION_1);
			}
			else {
				if (version.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_F_T_V_VERSION_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_F_T_V_VERSION_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (title != null) {
					qPos.add(title);
				}

				if (version != null) {
					qPos.add(version);
				}

				list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file version in the ordered set where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a matching document library file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion findByG_F_T_V_First(long groupId, long folderId,
		String title, String version, OrderByComparator orderByComparator)
		throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = fetchByG_F_T_V_First(groupId, folderId,
				title, version, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(", title=");
		msg.append(title);

		msg.append(", version=");
		msg.append(version);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the first document library file version in the ordered set where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion fetchByG_F_T_V_First(long groupId, long folderId,
		String title, String version, OrderByComparator orderByComparator)
		throws SystemException {
		List<DLFileVersion> list = findByG_F_T_V(groupId, folderId, title,
				version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file version in the ordered set where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a matching document library file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion findByG_F_T_V_Last(long groupId, long folderId,
		String title, String version, OrderByComparator orderByComparator)
		throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = fetchByG_F_T_V_Last(groupId, folderId,
				title, version, orderByComparator);

		if (dlFileVersion != null) {
			return dlFileVersion;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(", title=");
		msg.append(title);

		msg.append(", version=");
		msg.append(version);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileVersionException(msg.toString());
	}

	/**
	 * Returns the last document library file version in the ordered set where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion fetchByG_F_T_V_Last(long groupId, long folderId,
		String title, String version, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_F_T_V(groupId, folderId, title, version);

		List<DLFileVersion> list = findByG_F_T_V(groupId, folderId, title,
				version, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file versions before and after the current document library file version in the ordered set where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * @param fileVersionId the primary key of the current document library file version
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file version
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a document library file version with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion[] findByG_F_T_V_PrevAndNext(long fileVersionId,
		long groupId, long folderId, String title, String version,
		OrderByComparator orderByComparator)
		throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = findByPrimaryKey(fileVersionId);

		Session session = null;

		try {
			session = openSession();

			DLFileVersion[] array = new DLFileVersionImpl[3];

			array[0] = getByG_F_T_V_PrevAndNext(session, dlFileVersion,
					groupId, folderId, title, version, orderByComparator, true);

			array[1] = dlFileVersion;

			array[2] = getByG_F_T_V_PrevAndNext(session, dlFileVersion,
					groupId, folderId, title, version, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileVersion getByG_F_T_V_PrevAndNext(Session session,
		DLFileVersion dlFileVersion, long groupId, long folderId, String title,
		String version, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEVERSION_WHERE);

		query.append(_FINDER_COLUMN_G_F_T_V_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_T_V_FOLDERID_2);

		if (title == null) {
			query.append(_FINDER_COLUMN_G_F_T_V_TITLE_1);
		}
		else {
			if (title.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_F_T_V_TITLE_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_F_T_V_TITLE_2);
			}
		}

		if (version == null) {
			query.append(_FINDER_COLUMN_G_F_T_V_VERSION_1);
		}
		else {
			if (version.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_F_T_V_VERSION_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_F_T_V_VERSION_2);
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
			query.append(DLFileVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		if (title != null) {
			qPos.add(title);
		}

		if (version != null) {
			qPos.add(version);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file versions.
	 *
	 * @return the document library file versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileVersion> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @return the range of document library file versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileVersion> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file versions
	 * @param end the upper bound of the range of document library file versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of document library file versions
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileVersion> findAll(int start, int end,
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

		List<DLFileVersion> list = (List<DLFileVersion>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DLFILEVERSION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DLFILEVERSION.concat(DLFileVersionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DLFileVersion>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the document library file versions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (DLFileVersion dlFileVersion : findByUuid(uuid)) {
			remove(dlFileVersion);
		}
	}

	/**
	 * Removes the document library file version where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the document library file version that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion removeByUUID_G(String uuid, long groupId)
		throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = findByUUID_G(uuid, groupId);

		return remove(dlFileVersion);
	}

	/**
	 * Removes all the document library file versions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid_C(String uuid, long companyId)
		throws SystemException {
		for (DLFileVersion dlFileVersion : findByUuid_C(uuid, companyId)) {
			remove(dlFileVersion);
		}
	}

	/**
	 * Removes all the document library file versions where fileEntryId = &#63; from the database.
	 *
	 * @param fileEntryId the file entry ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByFileEntryId(long fileEntryId) throws SystemException {
		for (DLFileVersion dlFileVersion : findByFileEntryId(fileEntryId)) {
			remove(dlFileVersion);
		}
	}

	/**
	 * Removes the document library file version where fileEntryId = &#63; and version = &#63; from the database.
	 *
	 * @param fileEntryId the file entry ID
	 * @param version the version
	 * @return the document library file version that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileVersion removeByF_V(long fileEntryId, String version)
		throws NoSuchFileVersionException, SystemException {
		DLFileVersion dlFileVersion = findByF_V(fileEntryId, version);

		return remove(dlFileVersion);
	}

	/**
	 * Removes all the document library file versions where fileEntryId = &#63; and status = &#63; from the database.
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByF_S(long fileEntryId, int status)
		throws SystemException {
		for (DLFileVersion dlFileVersion : findByF_S(fileEntryId, status)) {
			remove(dlFileVersion);
		}
	}

	/**
	 * Removes all the document library file versions where groupId = &#63; and folderId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_F_S(long groupId, long folderId, int status)
		throws SystemException {
		for (DLFileVersion dlFileVersion : findByG_F_S(groupId, folderId, status)) {
			remove(dlFileVersion);
		}
	}

	/**
	 * Removes all the document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_F_T_V(long groupId, long folderId, String title,
		String version) throws SystemException {
		for (DLFileVersion dlFileVersion : findByG_F_T_V(groupId, folderId,
				title, version)) {
			remove(dlFileVersion);
		}
	}

	/**
	 * Removes all the document library file versions from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (DLFileVersion dlFileVersion : findAll()) {
			remove(dlFileVersion);
		}
	}

	/**
	 * Returns the number of document library file versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching document library file versions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEVERSION_WHERE);

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
	 * Returns the number of document library file versions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching document library file versions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILEVERSION_WHERE);

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
	 * Returns the number of document library file versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching document library file versions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid_C(String uuid, long companyId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILEVERSION_WHERE);

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
	 * Returns the number of document library file versions where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the number of matching document library file versions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByFileEntryId(long fileEntryId) throws SystemException {
		Object[] finderArgs = new Object[] { fileEntryId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_FILEENTRYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_FILEENTRYID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library file versions where fileEntryId = &#63; and version = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param version the version
	 * @return the number of matching document library file versions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByF_V(long fileEntryId, String version)
		throws SystemException {
		Object[] finderArgs = new Object[] { fileEntryId, version };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_F_V,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_F_V_FILEENTRYID_2);

			if (version == null) {
				query.append(_FINDER_COLUMN_F_V_VERSION_1);
			}
			else {
				if (version.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_F_V_VERSION_3);
				}
				else {
					query.append(_FINDER_COLUMN_F_V_VERSION_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryId);

				if (version != null) {
					qPos.add(version);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_F_V, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library file versions where fileEntryId = &#63; and status = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param status the status
	 * @return the number of matching document library file versions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByF_S(long fileEntryId, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] { fileEntryId, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_F_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_F_S_FILEENTRYID_2);

			query.append(_FINDER_COLUMN_F_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_F_S, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library file versions where groupId = &#63; and folderId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param status the status
	 * @return the number of matching document library file versions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_F_S(long groupId, long folderId, int status)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, folderId, status };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_F_S,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_F_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_S_FOLDERID_2);

			query.append(_FINDER_COLUMN_G_F_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_F_S,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param version the version
	 * @return the number of matching document library file versions
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_F_T_V(long groupId, long folderId, String title,
		String version) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, folderId, title, version };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_F_T_V,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_DLFILEVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_F_T_V_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_T_V_FOLDERID_2);

			if (title == null) {
				query.append(_FINDER_COLUMN_G_F_T_V_TITLE_1);
			}
			else {
				if (title.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_F_T_V_TITLE_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_F_T_V_TITLE_2);
				}
			}

			if (version == null) {
				query.append(_FINDER_COLUMN_G_F_T_V_VERSION_1);
			}
			else {
				if (version.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_F_T_V_VERSION_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_F_T_V_VERSION_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (title != null) {
					qPos.add(title);
				}

				if (version != null) {
					qPos.add(version);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_F_T_V,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library file versions.
	 *
	 * @return the number of document library file versions
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DLFILEVERSION);

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
	 * Initializes the document library file version persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.documentlibrary.model.DLFileVersion")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DLFileVersion>> listenersList = new ArrayList<ModelListener<DLFileVersion>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DLFileVersion>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(DLFileVersionImpl.class.getName());
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
	private static final String _SQL_SELECT_DLFILEVERSION = "SELECT dlFileVersion FROM DLFileVersion dlFileVersion";
	private static final String _SQL_SELECT_DLFILEVERSION_WHERE = "SELECT dlFileVersion FROM DLFileVersion dlFileVersion WHERE ";
	private static final String _SQL_COUNT_DLFILEVERSION = "SELECT COUNT(dlFileVersion) FROM DLFileVersion dlFileVersion";
	private static final String _SQL_COUNT_DLFILEVERSION_WHERE = "SELECT COUNT(dlFileVersion) FROM DLFileVersion dlFileVersion WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "dlFileVersion.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "dlFileVersion.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(dlFileVersion.uuid IS NULL OR dlFileVersion.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "dlFileVersion.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "dlFileVersion.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(dlFileVersion.uuid IS NULL OR dlFileVersion.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "dlFileVersion.groupId = ?";
	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "dlFileVersion.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "dlFileVersion.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(dlFileVersion.uuid IS NULL OR dlFileVersion.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "dlFileVersion.companyId = ?";
	private static final String _FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2 = "dlFileVersion.fileEntryId = ?";
	private static final String _FINDER_COLUMN_F_V_FILEENTRYID_2 = "dlFileVersion.fileEntryId = ? AND ";
	private static final String _FINDER_COLUMN_F_V_VERSION_1 = "dlFileVersion.version IS NULL";
	private static final String _FINDER_COLUMN_F_V_VERSION_2 = "dlFileVersion.version = ?";
	private static final String _FINDER_COLUMN_F_V_VERSION_3 = "(dlFileVersion.version IS NULL OR dlFileVersion.version = ?)";
	private static final String _FINDER_COLUMN_F_S_FILEENTRYID_2 = "dlFileVersion.fileEntryId = ? AND ";
	private static final String _FINDER_COLUMN_F_S_STATUS_2 = "dlFileVersion.status = ?";
	private static final String _FINDER_COLUMN_G_F_S_GROUPID_2 = "dlFileVersion.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_S_FOLDERID_2 = "dlFileVersion.folderId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_S_STATUS_2 = "dlFileVersion.status = ?";
	private static final String _FINDER_COLUMN_G_F_T_V_GROUPID_2 = "dlFileVersion.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_T_V_FOLDERID_2 = "dlFileVersion.folderId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_T_V_TITLE_1 = "dlFileVersion.title IS NULL AND ";
	private static final String _FINDER_COLUMN_G_F_T_V_TITLE_2 = "dlFileVersion.title = ? AND ";
	private static final String _FINDER_COLUMN_G_F_T_V_TITLE_3 = "(dlFileVersion.title IS NULL OR dlFileVersion.title = ?) AND ";
	private static final String _FINDER_COLUMN_G_F_T_V_VERSION_1 = "dlFileVersion.version IS NULL";
	private static final String _FINDER_COLUMN_G_F_T_V_VERSION_2 = "dlFileVersion.version = ?";
	private static final String _FINDER_COLUMN_G_F_T_V_VERSION_3 = "(dlFileVersion.version IS NULL OR dlFileVersion.version = ?)";
	private static final String _ORDER_BY_ENTITY_ALIAS = "dlFileVersion.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DLFileVersion exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DLFileVersion exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(DLFileVersionPersistenceImpl.class);
	private static DLFileVersion _nullDLFileVersion = new DLFileVersionImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<DLFileVersion> toCacheModel() {
				return _nullDLFileVersionCacheModel;
			}
		};

	private static CacheModel<DLFileVersion> _nullDLFileVersionCacheModel = new CacheModel<DLFileVersion>() {
			public DLFileVersion toEntityModel() {
				return _nullDLFileVersion;
			}
		};
}