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

import com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataModelImpl;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMStructureLinkPersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the document library file entry metadata service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryMetadataPersistence
 * @see DLFileEntryMetadataUtil
 * @generated
 */
public class DLFileEntryMetadataPersistenceImpl extends BasePersistenceImpl<DLFileEntryMetadata>
	implements DLFileEntryMetadataPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DLFileEntryMetadataUtil} to access the document library file entry metadata persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DLFileEntryMetadataImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED,
			DLFileEntryMetadataImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED,
			DLFileEntryMetadataImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			DLFileEntryMetadataModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_FILEENTRYTYPEID =
		new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED,
			DLFileEntryMetadataImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFileEntryTypeId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYTYPEID =
		new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED,
			DLFileEntryMetadataImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFileEntryTypeId",
			new String[] { Long.class.getName() },
			DLFileEntryMetadataModelImpl.FILEENTRYTYPEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_FILEENTRYTYPEID = new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByFileEntryTypeId", new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_FILEENTRYID =
		new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED,
			DLFileEntryMetadataImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFileEntryId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYID =
		new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED,
			DLFileEntryMetadataImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFileEntryId",
			new String[] { Long.class.getName() },
			DLFileEntryMetadataModelImpl.FILEENTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_FILEENTRYID = new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFileEntryId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_FILEVERSIONID =
		new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED,
			DLFileEntryMetadataImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFileVersionId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEVERSIONID =
		new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED,
			DLFileEntryMetadataImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFileVersionId",
			new String[] { Long.class.getName() },
			DLFileEntryMetadataModelImpl.FILEVERSIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_FILEVERSIONID = new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFileVersionId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_D_F = new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED,
			DLFileEntryMetadataImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByD_F",
			new String[] { Long.class.getName(), Long.class.getName() },
			DLFileEntryMetadataModelImpl.DDMSTRUCTUREID_COLUMN_BITMASK |
			DLFileEntryMetadataModelImpl.FILEVERSIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_D_F = new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByD_F",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED,
			DLFileEntryMetadataImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED,
			DLFileEntryMetadataImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the document library file entry metadata in the entity cache if it is enabled.
	 *
	 * @param dlFileEntryMetadata the document library file entry metadata
	 */
	public void cacheResult(DLFileEntryMetadata dlFileEntryMetadata) {
		EntityCacheUtil.putResult(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataImpl.class, dlFileEntryMetadata.getPrimaryKey(),
			dlFileEntryMetadata);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_D_F,
			new Object[] {
				Long.valueOf(dlFileEntryMetadata.getDDMStructureId()),
				Long.valueOf(dlFileEntryMetadata.getFileVersionId())
			}, dlFileEntryMetadata);

		dlFileEntryMetadata.resetOriginalValues();
	}

	/**
	 * Caches the document library file entry metadatas in the entity cache if it is enabled.
	 *
	 * @param dlFileEntryMetadatas the document library file entry metadatas
	 */
	public void cacheResult(List<DLFileEntryMetadata> dlFileEntryMetadatas) {
		for (DLFileEntryMetadata dlFileEntryMetadata : dlFileEntryMetadatas) {
			if (EntityCacheUtil.getResult(
						DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
						DLFileEntryMetadataImpl.class,
						dlFileEntryMetadata.getPrimaryKey()) == null) {
				cacheResult(dlFileEntryMetadata);
			}
			else {
				dlFileEntryMetadata.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all document library file entry metadatas.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(DLFileEntryMetadataImpl.class.getName());
		}

		EntityCacheUtil.clearCache(DLFileEntryMetadataImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the document library file entry metadata.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DLFileEntryMetadata dlFileEntryMetadata) {
		EntityCacheUtil.removeResult(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataImpl.class, dlFileEntryMetadata.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(dlFileEntryMetadata);
	}

	@Override
	public void clearCache(List<DLFileEntryMetadata> dlFileEntryMetadatas) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DLFileEntryMetadata dlFileEntryMetadata : dlFileEntryMetadatas) {
			EntityCacheUtil.removeResult(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
				DLFileEntryMetadataImpl.class,
				dlFileEntryMetadata.getPrimaryKey());

			clearUniqueFindersCache(dlFileEntryMetadata);
		}
	}

	protected void clearUniqueFindersCache(
		DLFileEntryMetadata dlFileEntryMetadata) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_D_F,
			new Object[] {
				Long.valueOf(dlFileEntryMetadata.getDDMStructureId()),
				Long.valueOf(dlFileEntryMetadata.getFileVersionId())
			});
	}

	/**
	 * Creates a new document library file entry metadata with the primary key. Does not add the document library file entry metadata to the database.
	 *
	 * @param fileEntryMetadataId the primary key for the new document library file entry metadata
	 * @return the new document library file entry metadata
	 */
	public DLFileEntryMetadata create(long fileEntryMetadataId) {
		DLFileEntryMetadata dlFileEntryMetadata = new DLFileEntryMetadataImpl();

		dlFileEntryMetadata.setNew(true);
		dlFileEntryMetadata.setPrimaryKey(fileEntryMetadataId);

		String uuid = PortalUUIDUtil.generate();

		dlFileEntryMetadata.setUuid(uuid);

		return dlFileEntryMetadata;
	}

	/**
	 * Removes the document library file entry metadata with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fileEntryMetadataId the primary key of the document library file entry metadata
	 * @return the document library file entry metadata that was removed
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryMetadata remove(long fileEntryMetadataId)
		throws NoSuchFileEntryMetadataException, SystemException {
		return remove(Long.valueOf(fileEntryMetadataId));
	}

	/**
	 * Removes the document library file entry metadata with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the document library file entry metadata
	 * @return the document library file entry metadata that was removed
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DLFileEntryMetadata remove(Serializable primaryKey)
		throws NoSuchFileEntryMetadataException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DLFileEntryMetadata dlFileEntryMetadata = (DLFileEntryMetadata)session.get(DLFileEntryMetadataImpl.class,
					primaryKey);

			if (dlFileEntryMetadata == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFileEntryMetadataException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(dlFileEntryMetadata);
		}
		catch (NoSuchFileEntryMetadataException nsee) {
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
	protected DLFileEntryMetadata removeImpl(
		DLFileEntryMetadata dlFileEntryMetadata) throws SystemException {
		dlFileEntryMetadata = toUnwrappedModel(dlFileEntryMetadata);

		Session session = null;

		try {
			session = openSession();

			if (dlFileEntryMetadata.isCachedModel()) {
				dlFileEntryMetadata = (DLFileEntryMetadata)session.get(DLFileEntryMetadataImpl.class,
						dlFileEntryMetadata.getPrimaryKeyObj());
			}

			if (dlFileEntryMetadata != null) {
				session.delete(dlFileEntryMetadata);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (dlFileEntryMetadata != null) {
			clearCache(dlFileEntryMetadata);
		}

		return dlFileEntryMetadata;
	}

	@Override
	public DLFileEntryMetadata updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata dlFileEntryMetadata)
		throws SystemException {
		dlFileEntryMetadata = toUnwrappedModel(dlFileEntryMetadata);

		boolean isNew = dlFileEntryMetadata.isNew();

		DLFileEntryMetadataModelImpl dlFileEntryMetadataModelImpl = (DLFileEntryMetadataModelImpl)dlFileEntryMetadata;

		if (Validator.isNull(dlFileEntryMetadata.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			dlFileEntryMetadata.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (dlFileEntryMetadata.isNew()) {
				session.save(dlFileEntryMetadata);

				dlFileEntryMetadata.setNew(false);
			}
			else {
				session.merge(dlFileEntryMetadata);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !DLFileEntryMetadataModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((dlFileEntryMetadataModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileEntryMetadataModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { dlFileEntryMetadataModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((dlFileEntryMetadataModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYTYPEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(dlFileEntryMetadataModelImpl.getOriginalFileEntryTypeId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_FILEENTRYTYPEID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYTYPEID,
					args);

				args = new Object[] {
						Long.valueOf(dlFileEntryMetadataModelImpl.getFileEntryTypeId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_FILEENTRYTYPEID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYTYPEID,
					args);
			}

			if ((dlFileEntryMetadataModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(dlFileEntryMetadataModelImpl.getOriginalFileEntryId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_FILEENTRYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYID,
					args);

				args = new Object[] {
						Long.valueOf(dlFileEntryMetadataModelImpl.getFileEntryId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_FILEENTRYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYID,
					args);
			}

			if ((dlFileEntryMetadataModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEVERSIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(dlFileEntryMetadataModelImpl.getOriginalFileVersionId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_FILEVERSIONID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEVERSIONID,
					args);

				args = new Object[] {
						Long.valueOf(dlFileEntryMetadataModelImpl.getFileVersionId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_FILEVERSIONID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEVERSIONID,
					args);
			}
		}

		EntityCacheUtil.putResult(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryMetadataImpl.class, dlFileEntryMetadata.getPrimaryKey(),
			dlFileEntryMetadata);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_D_F,
				new Object[] {
					Long.valueOf(dlFileEntryMetadata.getDDMStructureId()),
					Long.valueOf(dlFileEntryMetadata.getFileVersionId())
				}, dlFileEntryMetadata);
		}
		else {
			if ((dlFileEntryMetadataModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_D_F.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(dlFileEntryMetadataModelImpl.getOriginalDDMStructureId()),
						Long.valueOf(dlFileEntryMetadataModelImpl.getOriginalFileVersionId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_D_F, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_D_F, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_D_F,
					new Object[] {
						Long.valueOf(dlFileEntryMetadata.getDDMStructureId()),
						Long.valueOf(dlFileEntryMetadata.getFileVersionId())
					}, dlFileEntryMetadata);
			}
		}

		return dlFileEntryMetadata;
	}

	protected DLFileEntryMetadata toUnwrappedModel(
		DLFileEntryMetadata dlFileEntryMetadata) {
		if (dlFileEntryMetadata instanceof DLFileEntryMetadataImpl) {
			return dlFileEntryMetadata;
		}

		DLFileEntryMetadataImpl dlFileEntryMetadataImpl = new DLFileEntryMetadataImpl();

		dlFileEntryMetadataImpl.setNew(dlFileEntryMetadata.isNew());
		dlFileEntryMetadataImpl.setPrimaryKey(dlFileEntryMetadata.getPrimaryKey());

		dlFileEntryMetadataImpl.setUuid(dlFileEntryMetadata.getUuid());
		dlFileEntryMetadataImpl.setFileEntryMetadataId(dlFileEntryMetadata.getFileEntryMetadataId());
		dlFileEntryMetadataImpl.setDDMStorageId(dlFileEntryMetadata.getDDMStorageId());
		dlFileEntryMetadataImpl.setDDMStructureId(dlFileEntryMetadata.getDDMStructureId());
		dlFileEntryMetadataImpl.setFileEntryTypeId(dlFileEntryMetadata.getFileEntryTypeId());
		dlFileEntryMetadataImpl.setFileEntryId(dlFileEntryMetadata.getFileEntryId());
		dlFileEntryMetadataImpl.setFileVersionId(dlFileEntryMetadata.getFileVersionId());

		return dlFileEntryMetadataImpl;
	}

	/**
	 * Returns the document library file entry metadata with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the document library file entry metadata
	 * @return the document library file entry metadata
	 * @throws com.liferay.portal.NoSuchModelException if a document library file entry metadata with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DLFileEntryMetadata findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the document library file entry metadata with the primary key or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException} if it could not be found.
	 *
	 * @param fileEntryMetadataId the primary key of the document library file entry metadata
	 * @return the document library file entry metadata
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryMetadata findByPrimaryKey(long fileEntryMetadataId)
		throws NoSuchFileEntryMetadataException, SystemException {
		DLFileEntryMetadata dlFileEntryMetadata = fetchByPrimaryKey(fileEntryMetadataId);

		if (dlFileEntryMetadata == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					fileEntryMetadataId);
			}

			throw new NoSuchFileEntryMetadataException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				fileEntryMetadataId);
		}

		return dlFileEntryMetadata;
	}

	/**
	 * Returns the document library file entry metadata with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the document library file entry metadata
	 * @return the document library file entry metadata, or <code>null</code> if a document library file entry metadata with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DLFileEntryMetadata fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the document library file entry metadata with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fileEntryMetadataId the primary key of the document library file entry metadata
	 * @return the document library file entry metadata, or <code>null</code> if a document library file entry metadata with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryMetadata fetchByPrimaryKey(long fileEntryMetadataId)
		throws SystemException {
		DLFileEntryMetadata dlFileEntryMetadata = (DLFileEntryMetadata)EntityCacheUtil.getResult(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
				DLFileEntryMetadataImpl.class, fileEntryMetadataId);

		if (dlFileEntryMetadata == _nullDLFileEntryMetadata) {
			return null;
		}

		if (dlFileEntryMetadata == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				dlFileEntryMetadata = (DLFileEntryMetadata)session.get(DLFileEntryMetadataImpl.class,
						Long.valueOf(fileEntryMetadataId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (dlFileEntryMetadata != null) {
					cacheResult(dlFileEntryMetadata);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(DLFileEntryMetadataModelImpl.ENTITY_CACHE_ENABLED,
						DLFileEntryMetadataImpl.class, fileEntryMetadataId,
						_nullDLFileEntryMetadata);
				}

				closeSession(session);
			}
		}

		return dlFileEntryMetadata;
	}

	/**
	 * Returns all the document library file entry metadatas where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching document library file entry metadatas
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntryMetadata> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entry metadatas where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @return the range of matching document library file entry metadatas
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntryMetadata> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entry metadatas where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entry metadatas
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntryMetadata> findByUuid(String uuid, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
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

		List<DLFileEntryMetadata> list = (List<DLFileEntryMetadata>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFileEntryMetadata dlFileEntryMetadata : list) {
				if (!Validator.equals(uuid, dlFileEntryMetadata.getUuid())) {
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

			query.append(_SQL_SELECT_DLFILEENTRYMETADATA_WHERE);

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

				list = (List<DLFileEntryMetadata>)QueryUtil.list(q,
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
	 * Returns the first document library file entry metadata in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry metadata
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryMetadata findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryMetadataException, SystemException {
		DLFileEntryMetadata dlFileEntryMetadata = fetchByUuid_First(uuid,
				orderByComparator);

		if (dlFileEntryMetadata != null) {
			return dlFileEntryMetadata;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryMetadataException(msg.toString());
	}

	/**
	 * Returns the first document library file entry metadata in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryMetadata fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<DLFileEntryMetadata> list = findByUuid(uuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry metadata in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry metadata
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryMetadata findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryMetadataException, SystemException {
		DLFileEntryMetadata dlFileEntryMetadata = fetchByUuid_Last(uuid,
				orderByComparator);

		if (dlFileEntryMetadata != null) {
			return dlFileEntryMetadata;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryMetadataException(msg.toString());
	}

	/**
	 * Returns the last document library file entry metadata in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryMetadata fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<DLFileEntryMetadata> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entry metadatas before and after the current document library file entry metadata in the ordered set where uuid = &#63;.
	 *
	 * @param fileEntryMetadataId the primary key of the current document library file entry metadata
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry metadata
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryMetadata[] findByUuid_PrevAndNext(
		long fileEntryMetadataId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryMetadataException, SystemException {
		DLFileEntryMetadata dlFileEntryMetadata = findByPrimaryKey(fileEntryMetadataId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntryMetadata[] array = new DLFileEntryMetadataImpl[3];

			array[0] = getByUuid_PrevAndNext(session, dlFileEntryMetadata,
					uuid, orderByComparator, true);

			array[1] = dlFileEntryMetadata;

			array[2] = getByUuid_PrevAndNext(session, dlFileEntryMetadata,
					uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntryMetadata getByUuid_PrevAndNext(Session session,
		DLFileEntryMetadata dlFileEntryMetadata, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRYMETADATA_WHERE);

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
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntryMetadata);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntryMetadata> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file entry metadatas where fileEntryTypeId = &#63;.
	 *
	 * @param fileEntryTypeId the file entry type ID
	 * @return the matching document library file entry metadatas
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntryMetadata> findByFileEntryTypeId(long fileEntryTypeId)
		throws SystemException {
		return findByFileEntryTypeId(fileEntryTypeId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entry metadatas where fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @return the range of matching document library file entry metadatas
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntryMetadata> findByFileEntryTypeId(
		long fileEntryTypeId, int start, int end) throws SystemException {
		return findByFileEntryTypeId(fileEntryTypeId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entry metadatas where fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entry metadatas
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntryMetadata> findByFileEntryTypeId(
		long fileEntryTypeId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYTYPEID;
			finderArgs = new Object[] { fileEntryTypeId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_FILEENTRYTYPEID;
			finderArgs = new Object[] {
					fileEntryTypeId,
					
					start, end, orderByComparator
				};
		}

		List<DLFileEntryMetadata> list = (List<DLFileEntryMetadata>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFileEntryMetadata dlFileEntryMetadata : list) {
				if ((fileEntryTypeId != dlFileEntryMetadata.getFileEntryTypeId())) {
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

			query.append(_SQL_SELECT_DLFILEENTRYMETADATA_WHERE);

			query.append(_FINDER_COLUMN_FILEENTRYTYPEID_FILEENTRYTYPEID_2);

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

				qPos.add(fileEntryTypeId);

				list = (List<DLFileEntryMetadata>)QueryUtil.list(q,
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
	 * Returns the first document library file entry metadata in the ordered set where fileEntryTypeId = &#63;.
	 *
	 * @param fileEntryTypeId the file entry type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry metadata
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryMetadata findByFileEntryTypeId_First(
		long fileEntryTypeId, OrderByComparator orderByComparator)
		throws NoSuchFileEntryMetadataException, SystemException {
		DLFileEntryMetadata dlFileEntryMetadata = fetchByFileEntryTypeId_First(fileEntryTypeId,
				orderByComparator);

		if (dlFileEntryMetadata != null) {
			return dlFileEntryMetadata;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileEntryTypeId=");
		msg.append(fileEntryTypeId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryMetadataException(msg.toString());
	}

	/**
	 * Returns the first document library file entry metadata in the ordered set where fileEntryTypeId = &#63;.
	 *
	 * @param fileEntryTypeId the file entry type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryMetadata fetchByFileEntryTypeId_First(
		long fileEntryTypeId, OrderByComparator orderByComparator)
		throws SystemException {
		List<DLFileEntryMetadata> list = findByFileEntryTypeId(fileEntryTypeId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry metadata in the ordered set where fileEntryTypeId = &#63;.
	 *
	 * @param fileEntryTypeId the file entry type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry metadata
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryMetadata findByFileEntryTypeId_Last(
		long fileEntryTypeId, OrderByComparator orderByComparator)
		throws NoSuchFileEntryMetadataException, SystemException {
		DLFileEntryMetadata dlFileEntryMetadata = fetchByFileEntryTypeId_Last(fileEntryTypeId,
				orderByComparator);

		if (dlFileEntryMetadata != null) {
			return dlFileEntryMetadata;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileEntryTypeId=");
		msg.append(fileEntryTypeId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryMetadataException(msg.toString());
	}

	/**
	 * Returns the last document library file entry metadata in the ordered set where fileEntryTypeId = &#63;.
	 *
	 * @param fileEntryTypeId the file entry type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryMetadata fetchByFileEntryTypeId_Last(
		long fileEntryTypeId, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByFileEntryTypeId(fileEntryTypeId);

		List<DLFileEntryMetadata> list = findByFileEntryTypeId(fileEntryTypeId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entry metadatas before and after the current document library file entry metadata in the ordered set where fileEntryTypeId = &#63;.
	 *
	 * @param fileEntryMetadataId the primary key of the current document library file entry metadata
	 * @param fileEntryTypeId the file entry type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry metadata
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryMetadata[] findByFileEntryTypeId_PrevAndNext(
		long fileEntryMetadataId, long fileEntryTypeId,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryMetadataException, SystemException {
		DLFileEntryMetadata dlFileEntryMetadata = findByPrimaryKey(fileEntryMetadataId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntryMetadata[] array = new DLFileEntryMetadataImpl[3];

			array[0] = getByFileEntryTypeId_PrevAndNext(session,
					dlFileEntryMetadata, fileEntryTypeId, orderByComparator,
					true);

			array[1] = dlFileEntryMetadata;

			array[2] = getByFileEntryTypeId_PrevAndNext(session,
					dlFileEntryMetadata, fileEntryTypeId, orderByComparator,
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

	protected DLFileEntryMetadata getByFileEntryTypeId_PrevAndNext(
		Session session, DLFileEntryMetadata dlFileEntryMetadata,
		long fileEntryTypeId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRYMETADATA_WHERE);

		query.append(_FINDER_COLUMN_FILEENTRYTYPEID_FILEENTRYTYPEID_2);

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

		qPos.add(fileEntryTypeId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntryMetadata);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntryMetadata> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file entry metadatas where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the matching document library file entry metadatas
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntryMetadata> findByFileEntryId(long fileEntryId)
		throws SystemException {
		return findByFileEntryId(fileEntryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entry metadatas where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @return the range of matching document library file entry metadatas
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntryMetadata> findByFileEntryId(long fileEntryId,
		int start, int end) throws SystemException {
		return findByFileEntryId(fileEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entry metadatas where fileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param fileEntryId the file entry ID
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entry metadatas
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntryMetadata> findByFileEntryId(long fileEntryId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
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

		List<DLFileEntryMetadata> list = (List<DLFileEntryMetadata>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFileEntryMetadata dlFileEntryMetadata : list) {
				if ((fileEntryId != dlFileEntryMetadata.getFileEntryId())) {
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

			query.append(_SQL_SELECT_DLFILEENTRYMETADATA_WHERE);

			query.append(_FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2);

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

				qPos.add(fileEntryId);

				list = (List<DLFileEntryMetadata>)QueryUtil.list(q,
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
	 * Returns the first document library file entry metadata in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry metadata
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryMetadata findByFileEntryId_First(long fileEntryId,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryMetadataException, SystemException {
		DLFileEntryMetadata dlFileEntryMetadata = fetchByFileEntryId_First(fileEntryId,
				orderByComparator);

		if (dlFileEntryMetadata != null) {
			return dlFileEntryMetadata;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileEntryId=");
		msg.append(fileEntryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryMetadataException(msg.toString());
	}

	/**
	 * Returns the first document library file entry metadata in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryMetadata fetchByFileEntryId_First(long fileEntryId,
		OrderByComparator orderByComparator) throws SystemException {
		List<DLFileEntryMetadata> list = findByFileEntryId(fileEntryId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry metadata in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry metadata
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryMetadata findByFileEntryId_Last(long fileEntryId,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryMetadataException, SystemException {
		DLFileEntryMetadata dlFileEntryMetadata = fetchByFileEntryId_Last(fileEntryId,
				orderByComparator);

		if (dlFileEntryMetadata != null) {
			return dlFileEntryMetadata;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileEntryId=");
		msg.append(fileEntryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryMetadataException(msg.toString());
	}

	/**
	 * Returns the last document library file entry metadata in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryMetadata fetchByFileEntryId_Last(long fileEntryId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByFileEntryId(fileEntryId);

		List<DLFileEntryMetadata> list = findByFileEntryId(fileEntryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entry metadatas before and after the current document library file entry metadata in the ordered set where fileEntryId = &#63;.
	 *
	 * @param fileEntryMetadataId the primary key of the current document library file entry metadata
	 * @param fileEntryId the file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry metadata
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryMetadata[] findByFileEntryId_PrevAndNext(
		long fileEntryMetadataId, long fileEntryId,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryMetadataException, SystemException {
		DLFileEntryMetadata dlFileEntryMetadata = findByPrimaryKey(fileEntryMetadataId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntryMetadata[] array = new DLFileEntryMetadataImpl[3];

			array[0] = getByFileEntryId_PrevAndNext(session,
					dlFileEntryMetadata, fileEntryId, orderByComparator, true);

			array[1] = dlFileEntryMetadata;

			array[2] = getByFileEntryId_PrevAndNext(session,
					dlFileEntryMetadata, fileEntryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntryMetadata getByFileEntryId_PrevAndNext(
		Session session, DLFileEntryMetadata dlFileEntryMetadata,
		long fileEntryId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRYMETADATA_WHERE);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(fileEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntryMetadata);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntryMetadata> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file entry metadatas where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @return the matching document library file entry metadatas
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntryMetadata> findByFileVersionId(long fileVersionId)
		throws SystemException {
		return findByFileVersionId(fileVersionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entry metadatas where fileVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param fileVersionId the file version ID
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @return the range of matching document library file entry metadatas
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntryMetadata> findByFileVersionId(long fileVersionId,
		int start, int end) throws SystemException {
		return findByFileVersionId(fileVersionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entry metadatas where fileVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param fileVersionId the file version ID
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entry metadatas
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntryMetadata> findByFileVersionId(long fileVersionId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEVERSIONID;
			finderArgs = new Object[] { fileVersionId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_FILEVERSIONID;
			finderArgs = new Object[] {
					fileVersionId,
					
					start, end, orderByComparator
				};
		}

		List<DLFileEntryMetadata> list = (List<DLFileEntryMetadata>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DLFileEntryMetadata dlFileEntryMetadata : list) {
				if ((fileVersionId != dlFileEntryMetadata.getFileVersionId())) {
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

			query.append(_SQL_SELECT_DLFILEENTRYMETADATA_WHERE);

			query.append(_FINDER_COLUMN_FILEVERSIONID_FILEVERSIONID_2);

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

				qPos.add(fileVersionId);

				list = (List<DLFileEntryMetadata>)QueryUtil.list(q,
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
	 * Returns the first document library file entry metadata in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry metadata
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryMetadata findByFileVersionId_First(long fileVersionId,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryMetadataException, SystemException {
		DLFileEntryMetadata dlFileEntryMetadata = fetchByFileVersionId_First(fileVersionId,
				orderByComparator);

		if (dlFileEntryMetadata != null) {
			return dlFileEntryMetadata;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileVersionId=");
		msg.append(fileVersionId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryMetadataException(msg.toString());
	}

	/**
	 * Returns the first document library file entry metadata in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryMetadata fetchByFileVersionId_First(long fileVersionId,
		OrderByComparator orderByComparator) throws SystemException {
		List<DLFileEntryMetadata> list = findByFileVersionId(fileVersionId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry metadata in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry metadata
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryMetadata findByFileVersionId_Last(long fileVersionId,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryMetadataException, SystemException {
		DLFileEntryMetadata dlFileEntryMetadata = fetchByFileVersionId_Last(fileVersionId,
				orderByComparator);

		if (dlFileEntryMetadata != null) {
			return dlFileEntryMetadata;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileVersionId=");
		msg.append(fileVersionId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryMetadataException(msg.toString());
	}

	/**
	 * Returns the last document library file entry metadata in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryMetadata fetchByFileVersionId_Last(long fileVersionId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByFileVersionId(fileVersionId);

		List<DLFileEntryMetadata> list = findByFileVersionId(fileVersionId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entry metadatas before and after the current document library file entry metadata in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileEntryMetadataId the primary key of the current document library file entry metadata
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry metadata
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a document library file entry metadata with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryMetadata[] findByFileVersionId_PrevAndNext(
		long fileEntryMetadataId, long fileVersionId,
		OrderByComparator orderByComparator)
		throws NoSuchFileEntryMetadataException, SystemException {
		DLFileEntryMetadata dlFileEntryMetadata = findByPrimaryKey(fileEntryMetadataId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntryMetadata[] array = new DLFileEntryMetadataImpl[3];

			array[0] = getByFileVersionId_PrevAndNext(session,
					dlFileEntryMetadata, fileVersionId, orderByComparator, true);

			array[1] = dlFileEntryMetadata;

			array[2] = getByFileVersionId_PrevAndNext(session,
					dlFileEntryMetadata, fileVersionId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntryMetadata getByFileVersionId_PrevAndNext(
		Session session, DLFileEntryMetadata dlFileEntryMetadata,
		long fileVersionId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRYMETADATA_WHERE);

		query.append(_FINDER_COLUMN_FILEVERSIONID_FILEVERSIONID_2);

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

		qPos.add(fileVersionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntryMetadata);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntryMetadata> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the document library file entry metadata where DDMStructureId = &#63; and fileVersionId = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException} if it could not be found.
	 *
	 * @param DDMStructureId the d d m structure ID
	 * @param fileVersionId the file version ID
	 * @return the matching document library file entry metadata
	 * @throws com.liferay.portlet.documentlibrary.NoSuchFileEntryMetadataException if a matching document library file entry metadata could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryMetadata findByD_F(long DDMStructureId, long fileVersionId)
		throws NoSuchFileEntryMetadataException, SystemException {
		DLFileEntryMetadata dlFileEntryMetadata = fetchByD_F(DDMStructureId,
				fileVersionId);

		if (dlFileEntryMetadata == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("DDMStructureId=");
			msg.append(DDMStructureId);

			msg.append(", fileVersionId=");
			msg.append(fileVersionId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFileEntryMetadataException(msg.toString());
		}

		return dlFileEntryMetadata;
	}

	/**
	 * Returns the document library file entry metadata where DDMStructureId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param DDMStructureId the d d m structure ID
	 * @param fileVersionId the file version ID
	 * @return the matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryMetadata fetchByD_F(long DDMStructureId,
		long fileVersionId) throws SystemException {
		return fetchByD_F(DDMStructureId, fileVersionId, true);
	}

	/**
	 * Returns the document library file entry metadata where DDMStructureId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param DDMStructureId the d d m structure ID
	 * @param fileVersionId the file version ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching document library file entry metadata, or <code>null</code> if a matching document library file entry metadata could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryMetadata fetchByD_F(long DDMStructureId,
		long fileVersionId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { DDMStructureId, fileVersionId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_D_F,
					finderArgs, this);
		}

		if (result instanceof DLFileEntryMetadata) {
			DLFileEntryMetadata dlFileEntryMetadata = (DLFileEntryMetadata)result;

			if ((DDMStructureId != dlFileEntryMetadata.getDDMStructureId()) ||
					(fileVersionId != dlFileEntryMetadata.getFileVersionId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_DLFILEENTRYMETADATA_WHERE);

			query.append(_FINDER_COLUMN_D_F_DDMSTRUCTUREID_2);

			query.append(_FINDER_COLUMN_D_F_FILEVERSIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(DDMStructureId);

				qPos.add(fileVersionId);

				List<DLFileEntryMetadata> list = q.list();

				result = list;

				DLFileEntryMetadata dlFileEntryMetadata = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_D_F,
						finderArgs, list);
				}
				else {
					dlFileEntryMetadata = list.get(0);

					cacheResult(dlFileEntryMetadata);

					if ((dlFileEntryMetadata.getDDMStructureId() != DDMStructureId) ||
							(dlFileEntryMetadata.getFileVersionId() != fileVersionId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_D_F,
							finderArgs, dlFileEntryMetadata);
					}
				}

				return dlFileEntryMetadata;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_D_F,
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
				return (DLFileEntryMetadata)result;
			}
		}
	}

	/**
	 * Returns all the document library file entry metadatas.
	 *
	 * @return the document library file entry metadatas
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntryMetadata> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entry metadatas.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @return the range of document library file entry metadatas
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntryMetadata> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entry metadatas.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file entry metadatas
	 * @param end the upper bound of the range of document library file entry metadatas (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of document library file entry metadatas
	 * @throws SystemException if a system exception occurred
	 */
	public List<DLFileEntryMetadata> findAll(int start, int end,
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

		List<DLFileEntryMetadata> list = (List<DLFileEntryMetadata>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DLFILEENTRYMETADATA);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DLFILEENTRYMETADATA;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<DLFileEntryMetadata>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<DLFileEntryMetadata>)QueryUtil.list(q,
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
	 * Removes all the document library file entry metadatas where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (DLFileEntryMetadata dlFileEntryMetadata : findByUuid(uuid)) {
			remove(dlFileEntryMetadata);
		}
	}

	/**
	 * Removes all the document library file entry metadatas where fileEntryTypeId = &#63; from the database.
	 *
	 * @param fileEntryTypeId the file entry type ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByFileEntryTypeId(long fileEntryTypeId)
		throws SystemException {
		for (DLFileEntryMetadata dlFileEntryMetadata : findByFileEntryTypeId(
				fileEntryTypeId)) {
			remove(dlFileEntryMetadata);
		}
	}

	/**
	 * Removes all the document library file entry metadatas where fileEntryId = &#63; from the database.
	 *
	 * @param fileEntryId the file entry ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByFileEntryId(long fileEntryId) throws SystemException {
		for (DLFileEntryMetadata dlFileEntryMetadata : findByFileEntryId(
				fileEntryId)) {
			remove(dlFileEntryMetadata);
		}
	}

	/**
	 * Removes all the document library file entry metadatas where fileVersionId = &#63; from the database.
	 *
	 * @param fileVersionId the file version ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByFileVersionId(long fileVersionId)
		throws SystemException {
		for (DLFileEntryMetadata dlFileEntryMetadata : findByFileVersionId(
				fileVersionId)) {
			remove(dlFileEntryMetadata);
		}
	}

	/**
	 * Removes the document library file entry metadata where DDMStructureId = &#63; and fileVersionId = &#63; from the database.
	 *
	 * @param DDMStructureId the d d m structure ID
	 * @param fileVersionId the file version ID
	 * @return the document library file entry metadata that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public DLFileEntryMetadata removeByD_F(long DDMStructureId,
		long fileVersionId)
		throws NoSuchFileEntryMetadataException, SystemException {
		DLFileEntryMetadata dlFileEntryMetadata = findByD_F(DDMStructureId,
				fileVersionId);

		return remove(dlFileEntryMetadata);
	}

	/**
	 * Removes all the document library file entry metadatas from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (DLFileEntryMetadata dlFileEntryMetadata : findAll()) {
			remove(dlFileEntryMetadata);
		}
	}

	/**
	 * Returns the number of document library file entry metadatas where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching document library file entry metadatas
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEENTRYMETADATA_WHERE);

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
	 * Returns the number of document library file entry metadatas where fileEntryTypeId = &#63;.
	 *
	 * @param fileEntryTypeId the file entry type ID
	 * @return the number of matching document library file entry metadatas
	 * @throws SystemException if a system exception occurred
	 */
	public int countByFileEntryTypeId(long fileEntryTypeId)
		throws SystemException {
		Object[] finderArgs = new Object[] { fileEntryTypeId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_FILEENTRYTYPEID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEENTRYMETADATA_WHERE);

			query.append(_FINDER_COLUMN_FILEENTRYTYPEID_FILEENTRYTYPEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryTypeId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_FILEENTRYTYPEID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library file entry metadatas where fileEntryId = &#63;.
	 *
	 * @param fileEntryId the file entry ID
	 * @return the number of matching document library file entry metadatas
	 * @throws SystemException if a system exception occurred
	 */
	public int countByFileEntryId(long fileEntryId) throws SystemException {
		Object[] finderArgs = new Object[] { fileEntryId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_FILEENTRYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEENTRYMETADATA_WHERE);

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
	 * Returns the number of document library file entry metadatas where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @return the number of matching document library file entry metadatas
	 * @throws SystemException if a system exception occurred
	 */
	public int countByFileVersionId(long fileVersionId)
		throws SystemException {
		Object[] finderArgs = new Object[] { fileVersionId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_FILEVERSIONID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEENTRYMETADATA_WHERE);

			query.append(_FINDER_COLUMN_FILEVERSIONID_FILEVERSIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileVersionId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_FILEVERSIONID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library file entry metadatas where DDMStructureId = &#63; and fileVersionId = &#63;.
	 *
	 * @param DDMStructureId the d d m structure ID
	 * @param fileVersionId the file version ID
	 * @return the number of matching document library file entry metadatas
	 * @throws SystemException if a system exception occurred
	 */
	public int countByD_F(long DDMStructureId, long fileVersionId)
		throws SystemException {
		Object[] finderArgs = new Object[] { DDMStructureId, fileVersionId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_D_F,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILEENTRYMETADATA_WHERE);

			query.append(_FINDER_COLUMN_D_F_DDMSTRUCTUREID_2);

			query.append(_FINDER_COLUMN_D_F_FILEVERSIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(DDMStructureId);

				qPos.add(fileVersionId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_D_F, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library file entry metadatas.
	 *
	 * @return the number of document library file entry metadatas
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DLFILEENTRYMETADATA);

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
	 * Initializes the document library file entry metadata persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DLFileEntryMetadata>> listenersList = new ArrayList<ModelListener<DLFileEntryMetadata>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DLFileEntryMetadata>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(DLFileEntryMetadataImpl.class.getName());
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
	@BeanReference(type = DDMStructureLinkPersistence.class)
	protected DDMStructureLinkPersistence ddmStructureLinkPersistence;
	private static final String _SQL_SELECT_DLFILEENTRYMETADATA = "SELECT dlFileEntryMetadata FROM DLFileEntryMetadata dlFileEntryMetadata";
	private static final String _SQL_SELECT_DLFILEENTRYMETADATA_WHERE = "SELECT dlFileEntryMetadata FROM DLFileEntryMetadata dlFileEntryMetadata WHERE ";
	private static final String _SQL_COUNT_DLFILEENTRYMETADATA = "SELECT COUNT(dlFileEntryMetadata) FROM DLFileEntryMetadata dlFileEntryMetadata";
	private static final String _SQL_COUNT_DLFILEENTRYMETADATA_WHERE = "SELECT COUNT(dlFileEntryMetadata) FROM DLFileEntryMetadata dlFileEntryMetadata WHERE ";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "dlFileEntryMetadata.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "dlFileEntryMetadata.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(dlFileEntryMetadata.uuid IS NULL OR dlFileEntryMetadata.uuid = ?)";
	private static final String _FINDER_COLUMN_FILEENTRYTYPEID_FILEENTRYTYPEID_2 =
		"dlFileEntryMetadata.fileEntryTypeId = ?";
	private static final String _FINDER_COLUMN_FILEENTRYID_FILEENTRYID_2 = "dlFileEntryMetadata.fileEntryId = ?";
	private static final String _FINDER_COLUMN_FILEVERSIONID_FILEVERSIONID_2 = "dlFileEntryMetadata.fileVersionId = ?";
	private static final String _FINDER_COLUMN_D_F_DDMSTRUCTUREID_2 = "dlFileEntryMetadata.DDMStructureId = ? AND ";
	private static final String _FINDER_COLUMN_D_F_FILEVERSIONID_2 = "dlFileEntryMetadata.fileVersionId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "dlFileEntryMetadata.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DLFileEntryMetadata exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DLFileEntryMetadata exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(DLFileEntryMetadataPersistenceImpl.class);
	private static DLFileEntryMetadata _nullDLFileEntryMetadata = new DLFileEntryMetadataImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<DLFileEntryMetadata> toCacheModel() {
				return _nullDLFileEntryMetadataCacheModel;
			}
		};

	private static CacheModel<DLFileEntryMetadata> _nullDLFileEntryMetadataCacheModel =
		new CacheModel<DLFileEntryMetadata>() {
			public DLFileEntryMetadata toEntityModel() {
				return _nullDLFileEntryMetadata;
			}
		};
}