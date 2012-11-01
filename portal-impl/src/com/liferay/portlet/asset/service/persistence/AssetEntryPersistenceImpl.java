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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQuery;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
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
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.CompanyPersistence;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.impl.AssetEntryImpl;
import com.liferay.portlet.asset.model.impl.AssetEntryModelImpl;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryPersistence;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksEntryPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderPersistence;
import com.liferay.portlet.journal.service.persistence.JournalArticlePersistence;
import com.liferay.portlet.journal.service.persistence.JournalArticleResourcePersistence;
import com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence;
import com.liferay.portlet.social.service.persistence.SocialActivityCounterPersistence;
import com.liferay.portlet.social.service.persistence.SocialActivityPersistence;
import com.liferay.portlet.wiki.service.persistence.WikiPagePersistence;
import com.liferay.portlet.wiki.service.persistence.WikiPageResourcePersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * The persistence implementation for the asset entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryPersistence
 * @see AssetEntryUtil
 * @generated
 */
public class AssetEntryPersistenceImpl extends BasePersistenceImpl<AssetEntry>
	implements AssetEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link AssetEntryUtil} to access the asset entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = AssetEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED, AssetEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED, AssetEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			AssetEntryModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_VISIBLE = new FinderPath(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED, AssetEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByVisible",
			new String[] {
				Boolean.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VISIBLE =
		new FinderPath(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED, AssetEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByVisible",
			new String[] { Boolean.class.getName() },
			AssetEntryModelImpl.VISIBLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_VISIBLE = new FinderPath(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByVisible",
			new String[] { Boolean.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_PUBLISHDATE =
		new FinderPath(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED, AssetEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPublishDate",
			new String[] {
				Date.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PUBLISHDATE =
		new FinderPath(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED, AssetEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByPublishDate",
			new String[] { Date.class.getName() },
			AssetEntryModelImpl.PUBLISHDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_PUBLISHDATE = new FinderPath(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPublishDate",
			new String[] { Date.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_EXPIRATIONDATE =
		new FinderPath(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED, AssetEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByExpirationDate",
			new String[] {
				Date.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_EXPIRATIONDATE =
		new FinderPath(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED, AssetEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByExpirationDate",
			new String[] { Date.class.getName() },
			AssetEntryModelImpl.EXPIRATIONDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_EXPIRATIONDATE = new FinderPath(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByExpirationDate",
			new String[] { Date.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_LAYOUTUUID =
		new FinderPath(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED, AssetEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLayoutUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTUUID =
		new FinderPath(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED, AssetEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByLayoutUuid",
			new String[] { String.class.getName() },
			AssetEntryModelImpl.LAYOUTUUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_LAYOUTUUID = new FinderPath(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByLayoutUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_G_CU = new FinderPath(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED, AssetEntryImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_CU",
			new String[] { Long.class.getName(), String.class.getName() },
			AssetEntryModelImpl.GROUPID_COLUMN_BITMASK |
			AssetEntryModelImpl.CLASSUUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_CU = new FinderPath(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_CU",
			new String[] { Long.class.getName(), String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_C_C = new FinderPath(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED, AssetEntryImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			AssetEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			AssetEntryModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED, AssetEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED, AssetEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	/**
	 * Caches the asset entry in the entity cache if it is enabled.
	 *
	 * @param assetEntry the asset entry
	 */
	public void cacheResult(AssetEntry assetEntry) {
		EntityCacheUtil.putResult(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryImpl.class, assetEntry.getPrimaryKey(), assetEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_CU,
			new Object[] {
				Long.valueOf(assetEntry.getGroupId()),
				
			assetEntry.getClassUuid()
			}, assetEntry);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				Long.valueOf(assetEntry.getClassNameId()),
				Long.valueOf(assetEntry.getClassPK())
			}, assetEntry);

		assetEntry.resetOriginalValues();
	}

	/**
	 * Caches the asset entries in the entity cache if it is enabled.
	 *
	 * @param assetEntries the asset entries
	 */
	public void cacheResult(List<AssetEntry> assetEntries) {
		for (AssetEntry assetEntry : assetEntries) {
			if (EntityCacheUtil.getResult(
						AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
						AssetEntryImpl.class, assetEntry.getPrimaryKey()) == null) {
				cacheResult(assetEntry);
			}
			else {
				assetEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all asset entries.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(AssetEntryImpl.class.getName());
		}

		EntityCacheUtil.clearCache(AssetEntryImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the asset entry.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AssetEntry assetEntry) {
		EntityCacheUtil.removeResult(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryImpl.class, assetEntry.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(assetEntry);
	}

	@Override
	public void clearCache(List<AssetEntry> assetEntries) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AssetEntry assetEntry : assetEntries) {
			EntityCacheUtil.removeResult(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
				AssetEntryImpl.class, assetEntry.getPrimaryKey());

			clearUniqueFindersCache(assetEntry);
		}
	}

	protected void clearUniqueFindersCache(AssetEntry assetEntry) {
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_CU,
			new Object[] {
				Long.valueOf(assetEntry.getGroupId()),
				
			assetEntry.getClassUuid()
			});

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				Long.valueOf(assetEntry.getClassNameId()),
				Long.valueOf(assetEntry.getClassPK())
			});
	}

	/**
	 * Creates a new asset entry with the primary key. Does not add the asset entry to the database.
	 *
	 * @param entryId the primary key for the new asset entry
	 * @return the new asset entry
	 */
	public AssetEntry create(long entryId) {
		AssetEntry assetEntry = new AssetEntryImpl();

		assetEntry.setNew(true);
		assetEntry.setPrimaryKey(entryId);

		return assetEntry;
	}

	/**
	 * Removes the asset entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param entryId the primary key of the asset entry
	 * @return the asset entry that was removed
	 * @throws com.liferay.portlet.asset.NoSuchEntryException if a asset entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry remove(long entryId)
		throws NoSuchEntryException, SystemException {
		return remove(Long.valueOf(entryId));
	}

	/**
	 * Removes the asset entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset entry
	 * @return the asset entry that was removed
	 * @throws com.liferay.portlet.asset.NoSuchEntryException if a asset entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetEntry remove(Serializable primaryKey)
		throws NoSuchEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			AssetEntry assetEntry = (AssetEntry)session.get(AssetEntryImpl.class,
					primaryKey);

			if (assetEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(assetEntry);
		}
		catch (NoSuchEntryException nsee) {
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
	protected AssetEntry removeImpl(AssetEntry assetEntry)
		throws SystemException {
		assetEntry = toUnwrappedModel(assetEntry);

		try {
			clearAssetCategories.clear(assetEntry.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}

		try {
			clearAssetTags.clear(assetEntry.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}

		Session session = null;

		try {
			session = openSession();

			if (assetEntry.isCachedModel()) {
				assetEntry = (AssetEntry)session.get(AssetEntryImpl.class,
						assetEntry.getPrimaryKeyObj());
			}

			if (assetEntry != null) {
				session.delete(assetEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (assetEntry != null) {
			clearCache(assetEntry);
		}

		return assetEntry;
	}

	@Override
	public AssetEntry updateImpl(
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws SystemException {
		assetEntry = toUnwrappedModel(assetEntry);

		boolean isNew = assetEntry.isNew();

		AssetEntryModelImpl assetEntryModelImpl = (AssetEntryModelImpl)assetEntry;

		Session session = null;

		try {
			session = openSession();

			if (assetEntry.isNew()) {
				session.save(assetEntry);

				assetEntry.setNew(false);
			}
			else {
				session.merge(assetEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !AssetEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((assetEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(assetEntryModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						Long.valueOf(assetEntryModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((assetEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VISIBLE.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Boolean.valueOf(assetEntryModelImpl.getOriginalVisible())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_VISIBLE, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VISIBLE,
					args);

				args = new Object[] {
						Boolean.valueOf(assetEntryModelImpl.getVisible())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_VISIBLE, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VISIBLE,
					args);
			}

			if ((assetEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PUBLISHDATE.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetEntryModelImpl.getOriginalPublishDate()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PUBLISHDATE,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PUBLISHDATE,
					args);

				args = new Object[] { assetEntryModelImpl.getPublishDate() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PUBLISHDATE,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PUBLISHDATE,
					args);
			}

			if ((assetEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_EXPIRATIONDATE.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetEntryModelImpl.getOriginalExpirationDate()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_EXPIRATIONDATE,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_EXPIRATIONDATE,
					args);

				args = new Object[] { assetEntryModelImpl.getExpirationDate() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_EXPIRATIONDATE,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_EXPIRATIONDATE,
					args);
			}

			if ((assetEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTUUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetEntryModelImpl.getOriginalLayoutUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_LAYOUTUUID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTUUID,
					args);

				args = new Object[] { assetEntryModelImpl.getLayoutUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_LAYOUTUUID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTUUID,
					args);
			}
		}

		EntityCacheUtil.putResult(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryImpl.class, assetEntry.getPrimaryKey(), assetEntry);

		if (isNew) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_CU,
				new Object[] {
					Long.valueOf(assetEntry.getGroupId()),
					
				assetEntry.getClassUuid()
				}, assetEntry);

			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
				new Object[] {
					Long.valueOf(assetEntry.getClassNameId()),
					Long.valueOf(assetEntry.getClassPK())
				}, assetEntry);
		}
		else {
			if ((assetEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_CU.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(assetEntryModelImpl.getOriginalGroupId()),
						
						assetEntryModelImpl.getOriginalClassUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_CU, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_CU, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_CU,
					new Object[] {
						Long.valueOf(assetEntry.getGroupId()),
						
					assetEntry.getClassUuid()
					}, assetEntry);
			}

			if ((assetEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(assetEntryModelImpl.getOriginalClassNameId()),
						Long.valueOf(assetEntryModelImpl.getOriginalClassPK())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C, args);

				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C, args);

				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
					new Object[] {
						Long.valueOf(assetEntry.getClassNameId()),
						Long.valueOf(assetEntry.getClassPK())
					}, assetEntry);
			}
		}

		return assetEntry;
	}

	protected AssetEntry toUnwrappedModel(AssetEntry assetEntry) {
		if (assetEntry instanceof AssetEntryImpl) {
			return assetEntry;
		}

		AssetEntryImpl assetEntryImpl = new AssetEntryImpl();

		assetEntryImpl.setNew(assetEntry.isNew());
		assetEntryImpl.setPrimaryKey(assetEntry.getPrimaryKey());

		assetEntryImpl.setEntryId(assetEntry.getEntryId());
		assetEntryImpl.setGroupId(assetEntry.getGroupId());
		assetEntryImpl.setCompanyId(assetEntry.getCompanyId());
		assetEntryImpl.setUserId(assetEntry.getUserId());
		assetEntryImpl.setUserName(assetEntry.getUserName());
		assetEntryImpl.setCreateDate(assetEntry.getCreateDate());
		assetEntryImpl.setModifiedDate(assetEntry.getModifiedDate());
		assetEntryImpl.setClassNameId(assetEntry.getClassNameId());
		assetEntryImpl.setClassPK(assetEntry.getClassPK());
		assetEntryImpl.setClassUuid(assetEntry.getClassUuid());
		assetEntryImpl.setClassTypeId(assetEntry.getClassTypeId());
		assetEntryImpl.setVisible(assetEntry.isVisible());
		assetEntryImpl.setStartDate(assetEntry.getStartDate());
		assetEntryImpl.setEndDate(assetEntry.getEndDate());
		assetEntryImpl.setPublishDate(assetEntry.getPublishDate());
		assetEntryImpl.setExpirationDate(assetEntry.getExpirationDate());
		assetEntryImpl.setMimeType(assetEntry.getMimeType());
		assetEntryImpl.setTitle(assetEntry.getTitle());
		assetEntryImpl.setDescription(assetEntry.getDescription());
		assetEntryImpl.setSummary(assetEntry.getSummary());
		assetEntryImpl.setUrl(assetEntry.getUrl());
		assetEntryImpl.setLayoutUuid(assetEntry.getLayoutUuid());
		assetEntryImpl.setHeight(assetEntry.getHeight());
		assetEntryImpl.setWidth(assetEntry.getWidth());
		assetEntryImpl.setPriority(assetEntry.getPriority());
		assetEntryImpl.setViewCount(assetEntry.getViewCount());

		return assetEntryImpl;
	}

	/**
	 * Returns the asset entry with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset entry
	 * @return the asset entry
	 * @throws com.liferay.portal.NoSuchModelException if a asset entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the asset entry with the primary key or throws a {@link com.liferay.portlet.asset.NoSuchEntryException} if it could not be found.
	 *
	 * @param entryId the primary key of the asset entry
	 * @return the asset entry
	 * @throws com.liferay.portlet.asset.NoSuchEntryException if a asset entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry findByPrimaryKey(long entryId)
		throws NoSuchEntryException, SystemException {
		AssetEntry assetEntry = fetchByPrimaryKey(entryId);

		if (assetEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + entryId);
			}

			throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				entryId);
		}

		return assetEntry;
	}

	/**
	 * Returns the asset entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset entry
	 * @return the asset entry, or <code>null</code> if a asset entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetEntry fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the asset entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param entryId the primary key of the asset entry
	 * @return the asset entry, or <code>null</code> if a asset entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry fetchByPrimaryKey(long entryId) throws SystemException {
		AssetEntry assetEntry = (AssetEntry)EntityCacheUtil.getResult(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
				AssetEntryImpl.class, entryId);

		if (assetEntry == _nullAssetEntry) {
			return null;
		}

		if (assetEntry == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				assetEntry = (AssetEntry)session.get(AssetEntryImpl.class,
						Long.valueOf(entryId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (assetEntry != null) {
					cacheResult(assetEntry);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(AssetEntryModelImpl.ENTITY_CACHE_ENABLED,
						AssetEntryImpl.class, entryId, _nullAssetEntry);
				}

				closeSession(session);
			}
		}

		return assetEntry;
	}

	/**
	 * Returns all the asset entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetEntry> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the asset entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @return the range of matching asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetEntry> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetEntry> findByCompanyId(long companyId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
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

		List<AssetEntry> list = (List<AssetEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetEntry assetEntry : list) {
				if ((companyId != assetEntry.getCompanyId())) {
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

			query.append(_SQL_SELECT_ASSETENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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

				qPos.add(companyId);

				list = (List<AssetEntry>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first asset entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry
	 * @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AssetEntry assetEntry = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (assetEntry != null) {
			return assetEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first asset entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry fetchByCompanyId_First(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<AssetEntry> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry
	 * @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AssetEntry assetEntry = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (assetEntry != null) {
			return assetEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last asset entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry fetchByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCompanyId(companyId);

		List<AssetEntry> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entries before and after the current asset entry in the ordered set where companyId = &#63;.
	 *
	 * @param entryId the primary key of the current asset entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry
	 * @throws com.liferay.portlet.asset.NoSuchEntryException if a asset entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry[] findByCompanyId_PrevAndNext(long entryId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AssetEntry assetEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			AssetEntry[] array = new AssetEntryImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, assetEntry,
					companyId, orderByComparator, true);

			array[1] = assetEntry;

			array[2] = getByCompanyId_PrevAndNext(session, assetEntry,
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

	protected AssetEntry getByCompanyId_PrevAndNext(Session session,
		AssetEntry assetEntry, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETENTRY_WHERE);

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

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset entries where visible = &#63;.
	 *
	 * @param visible the visible
	 * @return the matching asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetEntry> findByVisible(boolean visible)
		throws SystemException {
		return findByVisible(visible, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entries where visible = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param visible the visible
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @return the range of matching asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetEntry> findByVisible(boolean visible, int start, int end)
		throws SystemException {
		return findByVisible(visible, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entries where visible = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param visible the visible
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetEntry> findByVisible(boolean visible, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VISIBLE;
			finderArgs = new Object[] { visible };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_VISIBLE;
			finderArgs = new Object[] { visible, start, end, orderByComparator };
		}

		List<AssetEntry> list = (List<AssetEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetEntry assetEntry : list) {
				if ((visible != assetEntry.getVisible())) {
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

			query.append(_SQL_SELECT_ASSETENTRY_WHERE);

			query.append(_FINDER_COLUMN_VISIBLE_VISIBLE_2);

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

				qPos.add(visible);

				list = (List<AssetEntry>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first asset entry in the ordered set where visible = &#63;.
	 *
	 * @param visible the visible
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry
	 * @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry findByVisible_First(boolean visible,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AssetEntry assetEntry = fetchByVisible_First(visible, orderByComparator);

		if (assetEntry != null) {
			return assetEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("visible=");
		msg.append(visible);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first asset entry in the ordered set where visible = &#63;.
	 *
	 * @param visible the visible
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry fetchByVisible_First(boolean visible,
		OrderByComparator orderByComparator) throws SystemException {
		List<AssetEntry> list = findByVisible(visible, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry in the ordered set where visible = &#63;.
	 *
	 * @param visible the visible
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry
	 * @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry findByVisible_Last(boolean visible,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AssetEntry assetEntry = fetchByVisible_Last(visible, orderByComparator);

		if (assetEntry != null) {
			return assetEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("visible=");
		msg.append(visible);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last asset entry in the ordered set where visible = &#63;.
	 *
	 * @param visible the visible
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry fetchByVisible_Last(boolean visible,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByVisible(visible);

		List<AssetEntry> list = findByVisible(visible, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entries before and after the current asset entry in the ordered set where visible = &#63;.
	 *
	 * @param entryId the primary key of the current asset entry
	 * @param visible the visible
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry
	 * @throws com.liferay.portlet.asset.NoSuchEntryException if a asset entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry[] findByVisible_PrevAndNext(long entryId,
		boolean visible, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AssetEntry assetEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			AssetEntry[] array = new AssetEntryImpl[3];

			array[0] = getByVisible_PrevAndNext(session, assetEntry, visible,
					orderByComparator, true);

			array[1] = assetEntry;

			array[2] = getByVisible_PrevAndNext(session, assetEntry, visible,
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

	protected AssetEntry getByVisible_PrevAndNext(Session session,
		AssetEntry assetEntry, boolean visible,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETENTRY_WHERE);

		query.append(_FINDER_COLUMN_VISIBLE_VISIBLE_2);

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

		qPos.add(visible);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset entries where publishDate = &#63;.
	 *
	 * @param publishDate the publish date
	 * @return the matching asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetEntry> findByPublishDate(Date publishDate)
		throws SystemException {
		return findByPublishDate(publishDate, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entries where publishDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param publishDate the publish date
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @return the range of matching asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetEntry> findByPublishDate(Date publishDate, int start,
		int end) throws SystemException {
		return findByPublishDate(publishDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entries where publishDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param publishDate the publish date
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetEntry> findByPublishDate(Date publishDate, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PUBLISHDATE;
			finderArgs = new Object[] { publishDate };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_PUBLISHDATE;
			finderArgs = new Object[] { publishDate, start, end, orderByComparator };
		}

		List<AssetEntry> list = (List<AssetEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetEntry assetEntry : list) {
				if (!Validator.equals(publishDate, assetEntry.getPublishDate())) {
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

			query.append(_SQL_SELECT_ASSETENTRY_WHERE);

			if (publishDate == null) {
				query.append(_FINDER_COLUMN_PUBLISHDATE_PUBLISHDATE_1);
			}
			else {
				query.append(_FINDER_COLUMN_PUBLISHDATE_PUBLISHDATE_2);
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

				if (publishDate != null) {
					qPos.add(CalendarUtil.getTimestamp(publishDate));
				}

				list = (List<AssetEntry>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first asset entry in the ordered set where publishDate = &#63;.
	 *
	 * @param publishDate the publish date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry
	 * @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry findByPublishDate_First(Date publishDate,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AssetEntry assetEntry = fetchByPublishDate_First(publishDate,
				orderByComparator);

		if (assetEntry != null) {
			return assetEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("publishDate=");
		msg.append(publishDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first asset entry in the ordered set where publishDate = &#63;.
	 *
	 * @param publishDate the publish date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry fetchByPublishDate_First(Date publishDate,
		OrderByComparator orderByComparator) throws SystemException {
		List<AssetEntry> list = findByPublishDate(publishDate, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry in the ordered set where publishDate = &#63;.
	 *
	 * @param publishDate the publish date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry
	 * @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry findByPublishDate_Last(Date publishDate,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AssetEntry assetEntry = fetchByPublishDate_Last(publishDate,
				orderByComparator);

		if (assetEntry != null) {
			return assetEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("publishDate=");
		msg.append(publishDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last asset entry in the ordered set where publishDate = &#63;.
	 *
	 * @param publishDate the publish date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry fetchByPublishDate_Last(Date publishDate,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByPublishDate(publishDate);

		List<AssetEntry> list = findByPublishDate(publishDate, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entries before and after the current asset entry in the ordered set where publishDate = &#63;.
	 *
	 * @param entryId the primary key of the current asset entry
	 * @param publishDate the publish date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry
	 * @throws com.liferay.portlet.asset.NoSuchEntryException if a asset entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry[] findByPublishDate_PrevAndNext(long entryId,
		Date publishDate, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AssetEntry assetEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			AssetEntry[] array = new AssetEntryImpl[3];

			array[0] = getByPublishDate_PrevAndNext(session, assetEntry,
					publishDate, orderByComparator, true);

			array[1] = assetEntry;

			array[2] = getByPublishDate_PrevAndNext(session, assetEntry,
					publishDate, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetEntry getByPublishDate_PrevAndNext(Session session,
		AssetEntry assetEntry, Date publishDate,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETENTRY_WHERE);

		if (publishDate == null) {
			query.append(_FINDER_COLUMN_PUBLISHDATE_PUBLISHDATE_1);
		}
		else {
			query.append(_FINDER_COLUMN_PUBLISHDATE_PUBLISHDATE_2);
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

		if (publishDate != null) {
			qPos.add(CalendarUtil.getTimestamp(publishDate));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset entries where expirationDate = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @return the matching asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetEntry> findByExpirationDate(Date expirationDate)
		throws SystemException {
		return findByExpirationDate(expirationDate, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entries where expirationDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @return the range of matching asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetEntry> findByExpirationDate(Date expirationDate,
		int start, int end) throws SystemException {
		return findByExpirationDate(expirationDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entries where expirationDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetEntry> findByExpirationDate(Date expirationDate,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_EXPIRATIONDATE;
			finderArgs = new Object[] { expirationDate };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_EXPIRATIONDATE;
			finderArgs = new Object[] {
					expirationDate,
					
					start, end, orderByComparator
				};
		}

		List<AssetEntry> list = (List<AssetEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetEntry assetEntry : list) {
				if (!Validator.equals(expirationDate,
							assetEntry.getExpirationDate())) {
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

			query.append(_SQL_SELECT_ASSETENTRY_WHERE);

			if (expirationDate == null) {
				query.append(_FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_1);
			}
			else {
				query.append(_FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_2);
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

				if (expirationDate != null) {
					qPos.add(CalendarUtil.getTimestamp(expirationDate));
				}

				list = (List<AssetEntry>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first asset entry in the ordered set where expirationDate = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry
	 * @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry findByExpirationDate_First(Date expirationDate,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AssetEntry assetEntry = fetchByExpirationDate_First(expirationDate,
				orderByComparator);

		if (assetEntry != null) {
			return assetEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("expirationDate=");
		msg.append(expirationDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first asset entry in the ordered set where expirationDate = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry fetchByExpirationDate_First(Date expirationDate,
		OrderByComparator orderByComparator) throws SystemException {
		List<AssetEntry> list = findByExpirationDate(expirationDate, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry in the ordered set where expirationDate = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry
	 * @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry findByExpirationDate_Last(Date expirationDate,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AssetEntry assetEntry = fetchByExpirationDate_Last(expirationDate,
				orderByComparator);

		if (assetEntry != null) {
			return assetEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("expirationDate=");
		msg.append(expirationDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last asset entry in the ordered set where expirationDate = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry fetchByExpirationDate_Last(Date expirationDate,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByExpirationDate(expirationDate);

		List<AssetEntry> list = findByExpirationDate(expirationDate, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entries before and after the current asset entry in the ordered set where expirationDate = &#63;.
	 *
	 * @param entryId the primary key of the current asset entry
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry
	 * @throws com.liferay.portlet.asset.NoSuchEntryException if a asset entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry[] findByExpirationDate_PrevAndNext(long entryId,
		Date expirationDate, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AssetEntry assetEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			AssetEntry[] array = new AssetEntryImpl[3];

			array[0] = getByExpirationDate_PrevAndNext(session, assetEntry,
					expirationDate, orderByComparator, true);

			array[1] = assetEntry;

			array[2] = getByExpirationDate_PrevAndNext(session, assetEntry,
					expirationDate, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetEntry getByExpirationDate_PrevAndNext(Session session,
		AssetEntry assetEntry, Date expirationDate,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETENTRY_WHERE);

		if (expirationDate == null) {
			query.append(_FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_1);
		}
		else {
			query.append(_FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_2);
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

		if (expirationDate != null) {
			qPos.add(CalendarUtil.getTimestamp(expirationDate));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the asset entries where layoutUuid = &#63;.
	 *
	 * @param layoutUuid the layout uuid
	 * @return the matching asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetEntry> findByLayoutUuid(String layoutUuid)
		throws SystemException {
		return findByLayoutUuid(layoutUuid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entries where layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @return the range of matching asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetEntry> findByLayoutUuid(String layoutUuid, int start,
		int end) throws SystemException {
		return findByLayoutUuid(layoutUuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entries where layoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param layoutUuid the layout uuid
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetEntry> findByLayoutUuid(String layoutUuid, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LAYOUTUUID;
			finderArgs = new Object[] { layoutUuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_LAYOUTUUID;
			finderArgs = new Object[] { layoutUuid, start, end, orderByComparator };
		}

		List<AssetEntry> list = (List<AssetEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (AssetEntry assetEntry : list) {
				if (!Validator.equals(layoutUuid, assetEntry.getLayoutUuid())) {
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

			query.append(_SQL_SELECT_ASSETENTRY_WHERE);

			if (layoutUuid == null) {
				query.append(_FINDER_COLUMN_LAYOUTUUID_LAYOUTUUID_1);
			}
			else {
				if (layoutUuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_LAYOUTUUID_LAYOUTUUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_LAYOUTUUID_LAYOUTUUID_2);
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

				if (layoutUuid != null) {
					qPos.add(layoutUuid);
				}

				list = (List<AssetEntry>)QueryUtil.list(q, getDialect(), start,
						end);
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
	 * Returns the first asset entry in the ordered set where layoutUuid = &#63;.
	 *
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry
	 * @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry findByLayoutUuid_First(String layoutUuid,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AssetEntry assetEntry = fetchByLayoutUuid_First(layoutUuid,
				orderByComparator);

		if (assetEntry != null) {
			return assetEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutUuid=");
		msg.append(layoutUuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first asset entry in the ordered set where layoutUuid = &#63;.
	 *
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry fetchByLayoutUuid_First(String layoutUuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<AssetEntry> list = findByLayoutUuid(layoutUuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry in the ordered set where layoutUuid = &#63;.
	 *
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry
	 * @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry findByLayoutUuid_Last(String layoutUuid,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AssetEntry assetEntry = fetchByLayoutUuid_Last(layoutUuid,
				orderByComparator);

		if (assetEntry != null) {
			return assetEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutUuid=");
		msg.append(layoutUuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last asset entry in the ordered set where layoutUuid = &#63;.
	 *
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry fetchByLayoutUuid_Last(String layoutUuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByLayoutUuid(layoutUuid);

		List<AssetEntry> list = findByLayoutUuid(layoutUuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entries before and after the current asset entry in the ordered set where layoutUuid = &#63;.
	 *
	 * @param entryId the primary key of the current asset entry
	 * @param layoutUuid the layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry
	 * @throws com.liferay.portlet.asset.NoSuchEntryException if a asset entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry[] findByLayoutUuid_PrevAndNext(long entryId,
		String layoutUuid, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		AssetEntry assetEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			AssetEntry[] array = new AssetEntryImpl[3];

			array[0] = getByLayoutUuid_PrevAndNext(session, assetEntry,
					layoutUuid, orderByComparator, true);

			array[1] = assetEntry;

			array[2] = getByLayoutUuid_PrevAndNext(session, assetEntry,
					layoutUuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetEntry getByLayoutUuid_PrevAndNext(Session session,
		AssetEntry assetEntry, String layoutUuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ASSETENTRY_WHERE);

		if (layoutUuid == null) {
			query.append(_FINDER_COLUMN_LAYOUTUUID_LAYOUTUUID_1);
		}
		else {
			if (layoutUuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_LAYOUTUUID_LAYOUTUUID_3);
			}
			else {
				query.append(_FINDER_COLUMN_LAYOUTUUID_LAYOUTUUID_2);
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

		if (layoutUuid != null) {
			qPos.add(layoutUuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the asset entry where groupId = &#63; and classUuid = &#63; or throws a {@link com.liferay.portlet.asset.NoSuchEntryException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classUuid the class uuid
	 * @return the matching asset entry
	 * @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry findByG_CU(long groupId, String classUuid)
		throws NoSuchEntryException, SystemException {
		AssetEntry assetEntry = fetchByG_CU(groupId, classUuid);

		if (assetEntry == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classUuid=");
			msg.append(classUuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchEntryException(msg.toString());
		}

		return assetEntry;
	}

	/**
	 * Returns the asset entry where groupId = &#63; and classUuid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classUuid the class uuid
	 * @return the matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry fetchByG_CU(long groupId, String classUuid)
		throws SystemException {
		return fetchByG_CU(groupId, classUuid, true);
	}

	/**
	 * Returns the asset entry where groupId = &#63; and classUuid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classUuid the class uuid
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry fetchByG_CU(long groupId, String classUuid,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, classUuid };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_CU,
					finderArgs, this);
		}

		if (result instanceof AssetEntry) {
			AssetEntry assetEntry = (AssetEntry)result;

			if ((groupId != assetEntry.getGroupId()) ||
					!Validator.equals(classUuid, assetEntry.getClassUuid())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_ASSETENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_CU_GROUPID_2);

			if (classUuid == null) {
				query.append(_FINDER_COLUMN_G_CU_CLASSUUID_1);
			}
			else {
				if (classUuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_CU_CLASSUUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_CU_CLASSUUID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (classUuid != null) {
					qPos.add(classUuid);
				}

				List<AssetEntry> list = q.list();

				result = list;

				AssetEntry assetEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_CU,
						finderArgs, list);
				}
				else {
					assetEntry = list.get(0);

					cacheResult(assetEntry);

					if ((assetEntry.getGroupId() != groupId) ||
							(assetEntry.getClassUuid() == null) ||
							!assetEntry.getClassUuid().equals(classUuid)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_CU,
							finderArgs, assetEntry);
					}
				}

				return assetEntry;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_CU,
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
				return (AssetEntry)result;
			}
		}
	}

	/**
	 * Returns the asset entry where classNameId = &#63; and classPK = &#63; or throws a {@link com.liferay.portlet.asset.NoSuchEntryException} if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching asset entry
	 * @throws com.liferay.portlet.asset.NoSuchEntryException if a matching asset entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry findByC_C(long classNameId, long classPK)
		throws NoSuchEntryException, SystemException {
		AssetEntry assetEntry = fetchByC_C(classNameId, classPK);

		if (assetEntry == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchEntryException(msg.toString());
		}

		return assetEntry;
	}

	/**
	 * Returns the asset entry where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry fetchByC_C(long classNameId, long classPK)
		throws SystemException {
		return fetchByC_C(classNameId, classPK, true);
	}

	/**
	 * Returns the asset entry where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching asset entry, or <code>null</code> if a matching asset entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry fetchByC_C(long classNameId, long classPK,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { classNameId, classPK };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_C,
					finderArgs, this);
		}

		if (result instanceof AssetEntry) {
			AssetEntry assetEntry = (AssetEntry)result;

			if ((classNameId != assetEntry.getClassNameId()) ||
					(classPK != assetEntry.getClassPK())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_ASSETENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<AssetEntry> list = q.list();

				result = list;

				AssetEntry assetEntry = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
						finderArgs, list);
				}
				else {
					assetEntry = list.get(0);

					cacheResult(assetEntry);

					if ((assetEntry.getClassNameId() != classNameId) ||
							(assetEntry.getClassPK() != classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C,
							finderArgs, assetEntry);
					}
				}

				return assetEntry;
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (result == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C,
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
				return (AssetEntry)result;
			}
		}
	}

	/**
	 * Returns all the asset entries.
	 *
	 * @return the asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @return the range of asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetEntry> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<AssetEntry> findAll(int start, int end,
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

		List<AssetEntry> list = (List<AssetEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_ASSETENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETENTRY;
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<AssetEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<AssetEntry>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the asset entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (AssetEntry assetEntry : findByCompanyId(companyId)) {
			remove(assetEntry);
		}
	}

	/**
	 * Removes all the asset entries where visible = &#63; from the database.
	 *
	 * @param visible the visible
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByVisible(boolean visible) throws SystemException {
		for (AssetEntry assetEntry : findByVisible(visible)) {
			remove(assetEntry);
		}
	}

	/**
	 * Removes all the asset entries where publishDate = &#63; from the database.
	 *
	 * @param publishDate the publish date
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByPublishDate(Date publishDate) throws SystemException {
		for (AssetEntry assetEntry : findByPublishDate(publishDate)) {
			remove(assetEntry);
		}
	}

	/**
	 * Removes all the asset entries where expirationDate = &#63; from the database.
	 *
	 * @param expirationDate the expiration date
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByExpirationDate(Date expirationDate)
		throws SystemException {
		for (AssetEntry assetEntry : findByExpirationDate(expirationDate)) {
			remove(assetEntry);
		}
	}

	/**
	 * Removes all the asset entries where layoutUuid = &#63; from the database.
	 *
	 * @param layoutUuid the layout uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByLayoutUuid(String layoutUuid) throws SystemException {
		for (AssetEntry assetEntry : findByLayoutUuid(layoutUuid)) {
			remove(assetEntry);
		}
	}

	/**
	 * Removes the asset entry where groupId = &#63; and classUuid = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classUuid the class uuid
	 * @return the asset entry that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry removeByG_CU(long groupId, String classUuid)
		throws NoSuchEntryException, SystemException {
		AssetEntry assetEntry = findByG_CU(groupId, classUuid);

		return remove(assetEntry);
	}

	/**
	 * Removes the asset entry where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the asset entry that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public AssetEntry removeByC_C(long classNameId, long classPK)
		throws NoSuchEntryException, SystemException {
		AssetEntry assetEntry = findByC_C(classNameId, classPK);

		return remove(assetEntry);
	}

	/**
	 * Removes all the asset entries from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (AssetEntry assetEntry : findAll()) {
			remove(assetEntry);
		}
	}

	/**
	 * Returns the number of asset entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETENTRY_WHERE);

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
	 * Returns the number of asset entries where visible = &#63;.
	 *
	 * @param visible the visible
	 * @return the number of matching asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByVisible(boolean visible) throws SystemException {
		Object[] finderArgs = new Object[] { visible };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_VISIBLE,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETENTRY_WHERE);

			query.append(_FINDER_COLUMN_VISIBLE_VISIBLE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(visible);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_VISIBLE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset entries where publishDate = &#63;.
	 *
	 * @param publishDate the publish date
	 * @return the number of matching asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByPublishDate(Date publishDate) throws SystemException {
		Object[] finderArgs = new Object[] { publishDate };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_PUBLISHDATE,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETENTRY_WHERE);

			if (publishDate == null) {
				query.append(_FINDER_COLUMN_PUBLISHDATE_PUBLISHDATE_1);
			}
			else {
				query.append(_FINDER_COLUMN_PUBLISHDATE_PUBLISHDATE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (publishDate != null) {
					qPos.add(CalendarUtil.getTimestamp(publishDate));
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_PUBLISHDATE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset entries where expirationDate = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @return the number of matching asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByExpirationDate(Date expirationDate)
		throws SystemException {
		Object[] finderArgs = new Object[] { expirationDate };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_EXPIRATIONDATE,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETENTRY_WHERE);

			if (expirationDate == null) {
				query.append(_FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_1);
			}
			else {
				query.append(_FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (expirationDate != null) {
					qPos.add(CalendarUtil.getTimestamp(expirationDate));
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_EXPIRATIONDATE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset entries where layoutUuid = &#63;.
	 *
	 * @param layoutUuid the layout uuid
	 * @return the number of matching asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByLayoutUuid(String layoutUuid) throws SystemException {
		Object[] finderArgs = new Object[] { layoutUuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_LAYOUTUUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ASSETENTRY_WHERE);

			if (layoutUuid == null) {
				query.append(_FINDER_COLUMN_LAYOUTUUID_LAYOUTUUID_1);
			}
			else {
				if (layoutUuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_LAYOUTUUID_LAYOUTUUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_LAYOUTUUID_LAYOUTUUID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (layoutUuid != null) {
					qPos.add(layoutUuid);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_LAYOUTUUID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset entries where groupId = &#63; and classUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classUuid the class uuid
	 * @return the number of matching asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_CU(long groupId, String classUuid)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, classUuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_CU,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_CU_GROUPID_2);

			if (classUuid == null) {
				query.append(_FINDER_COLUMN_G_CU_CLASSUUID_1);
			}
			else {
				if (classUuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_CU_CLASSUUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_CU_CLASSUUID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (classUuid != null) {
					qPos.add(classUuid);
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

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_CU,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_C(long classNameId, long classPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { classNameId, classPK };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_C_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C, finderArgs,
					count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of asset entries.
	 *
	 * @return the number of asset entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ASSETENTRY);

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
	 * Returns all the asset categories associated with the asset entry.
	 *
	 * @param pk the primary key of the asset entry
	 * @return the asset categories associated with the asset entry
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk) throws SystemException {
		return getAssetCategories(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the asset categories associated with the asset entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the asset entry
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @return the range of asset categories associated with the asset entry
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk, int start, int end) throws SystemException {
		return getAssetCategories(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_ASSETCATEGORIES = new FinderPath(com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED_ASSETENTRIES_ASSETCATEGORIES,
			com.liferay.portlet.asset.model.impl.AssetCategoryImpl.class,
			AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME,
			"getAssetCategories",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	static {
		FINDER_PATH_GET_ASSETCATEGORIES.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns an ordered range of all the asset categories associated with the asset entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the asset entry
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset categories associated with the asset entry
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long pk, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, start, end, orderByComparator };

		List<com.liferay.portlet.asset.model.AssetCategory> list = (List<com.liferay.portlet.asset.model.AssetCategory>)FinderCacheUtil.getResult(FINDER_PATH_GET_ASSETCATEGORIES,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETASSETCATEGORIES.concat(ORDER_BY_CLAUSE)
												 .concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETASSETCATEGORIES.concat(com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl.ORDER_BY_SQL);
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("AssetCategory",
					com.liferay.portlet.asset.model.impl.AssetCategoryImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portlet.asset.model.AssetCategory>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_GET_ASSETCATEGORIES,
						finderArgs);
				}
				else {
					assetCategoryPersistence.cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_GET_ASSETCATEGORIES,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_ASSETCATEGORIES_SIZE = new FinderPath(com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED_ASSETENTRIES_ASSETCATEGORIES,
			Long.class,
			AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME,
			"getAssetCategoriesSize", new String[] { Long.class.getName() });

	static {
		FINDER_PATH_GET_ASSETCATEGORIES_SIZE.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns the number of asset categories associated with the asset entry.
	 *
	 * @param pk the primary key of the asset entry
	 * @return the number of asset categories associated with the asset entry
	 * @throws SystemException if a system exception occurred
	 */
	public int getAssetCategoriesSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_ASSETCATEGORIES_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETASSETCATEGORIESSIZE);

				q.addScalar(COUNT_COLUMN_NAME,
					com.liferay.portal.kernel.dao.orm.Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_ASSETCATEGORIES_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_ASSETCATEGORY = new FinderPath(com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED_ASSETENTRIES_ASSETCATEGORIES,
			Boolean.class,
			AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME,
			"containsAssetCategory",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the asset category is associated with the asset entry.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetCategoryPK the primary key of the asset category
	 * @return <code>true</code> if the asset category is associated with the asset entry; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsAssetCategory(long pk, long assetCategoryPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, assetCategoryPK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_ASSETCATEGORY,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsAssetCategory.contains(pk,
							assetCategoryPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_ASSETCATEGORY,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the asset entry has any asset categories associated with it.
	 *
	 * @param pk the primary key of the asset entry to check for associations with asset categories
	 * @return <code>true</code> if the asset entry has any asset categories associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsAssetCategories(long pk) throws SystemException {
		if (getAssetCategoriesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the asset entry and the asset category. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetCategoryPK the primary key of the asset category
	 * @throws SystemException if a system exception occurred
	 */
	public void addAssetCategory(long pk, long assetCategoryPK)
		throws SystemException {
		try {
			addAssetCategory.add(pk, assetCategoryPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	/**
	 * Adds an association between the asset entry and the asset category. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetCategory the asset category
	 * @throws SystemException if a system exception occurred
	 */
	public void addAssetCategory(long pk,
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws SystemException {
		try {
			addAssetCategory.add(pk, assetCategory.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	/**
	 * Adds an association between the asset entry and the asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetCategoryPKs the primary keys of the asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public void addAssetCategories(long pk, long[] assetCategoryPKs)
		throws SystemException {
		try {
			for (long assetCategoryPK : assetCategoryPKs) {
				addAssetCategory.add(pk, assetCategoryPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	/**
	 * Adds an association between the asset entry and the asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetCategories the asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public void addAssetCategories(long pk,
		List<com.liferay.portlet.asset.model.AssetCategory> assetCategories)
		throws SystemException {
		try {
			for (com.liferay.portlet.asset.model.AssetCategory assetCategory : assetCategories) {
				addAssetCategory.add(pk, assetCategory.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	/**
	 * Clears all associations between the asset entry and its asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry to clear the associated asset categories from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearAssetCategories(long pk) throws SystemException {
		try {
			clearAssetCategories.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	/**
	 * Removes the association between the asset entry and the asset category. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetCategoryPK the primary key of the asset category
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAssetCategory(long pk, long assetCategoryPK)
		throws SystemException {
		try {
			removeAssetCategory.remove(pk, assetCategoryPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	/**
	 * Removes the association between the asset entry and the asset category. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetCategory the asset category
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAssetCategory(long pk,
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws SystemException {
		try {
			removeAssetCategory.remove(pk, assetCategory.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	/**
	 * Removes the association between the asset entry and the asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetCategoryPKs the primary keys of the asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAssetCategories(long pk, long[] assetCategoryPKs)
		throws SystemException {
		try {
			for (long assetCategoryPK : assetCategoryPKs) {
				removeAssetCategory.remove(pk, assetCategoryPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	/**
	 * Removes the association between the asset entry and the asset categories. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetCategories the asset categories
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAssetCategories(long pk,
		List<com.liferay.portlet.asset.model.AssetCategory> assetCategories)
		throws SystemException {
		try {
			for (com.liferay.portlet.asset.model.AssetCategory assetCategory : assetCategories) {
				removeAssetCategory.remove(pk, assetCategory.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	/**
	 * Sets the asset categories associated with the asset entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetCategoryPKs the primary keys of the asset categories to be associated with the asset entry
	 * @throws SystemException if a system exception occurred
	 */
	public void setAssetCategories(long pk, long[] assetCategoryPKs)
		throws SystemException {
		try {
			Set<Long> assetCategoryPKSet = SetUtil.fromArray(assetCategoryPKs);

			List<com.liferay.portlet.asset.model.AssetCategory> assetCategories = getAssetCategories(pk);

			for (com.liferay.portlet.asset.model.AssetCategory assetCategory : assetCategories) {
				if (!assetCategoryPKSet.remove(assetCategory.getPrimaryKey())) {
					removeAssetCategory.remove(pk, assetCategory.getPrimaryKey());
				}
			}

			for (Long assetCategoryPK : assetCategoryPKSet) {
				addAssetCategory.add(pk, assetCategoryPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	/**
	 * Sets the asset categories associated with the asset entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetCategories the asset categories to be associated with the asset entry
	 * @throws SystemException if a system exception occurred
	 */
	public void setAssetCategories(long pk,
		List<com.liferay.portlet.asset.model.AssetCategory> assetCategories)
		throws SystemException {
		try {
			long[] assetCategoryPKs = new long[assetCategories.size()];

			for (int i = 0; i < assetCategories.size(); i++) {
				com.liferay.portlet.asset.model.AssetCategory assetCategory = assetCategories.get(i);

				assetCategoryPKs[i] = assetCategory.getPrimaryKey();
			}

			setAssetCategories(pk, assetCategoryPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETCATEGORIES_NAME);
		}
	}

	/**
	 * Returns all the asset tags associated with the asset entry.
	 *
	 * @param pk the primary key of the asset entry
	 * @return the asset tags associated with the asset entry
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(long pk)
		throws SystemException {
		return getAssetTags(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the asset tags associated with the asset entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the asset entry
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @return the range of asset tags associated with the asset entry
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		long pk, int start, int end) throws SystemException {
		return getAssetTags(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_ASSETTAGS = new FinderPath(com.liferay.portlet.asset.model.impl.AssetTagModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED_ASSETENTRIES_ASSETTAGS,
			com.liferay.portlet.asset.model.impl.AssetTagImpl.class,
			AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME,
			"getAssetTags",
			new String[] {
				Long.class.getName(), "java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});

	static {
		FINDER_PATH_GET_ASSETTAGS.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns an ordered range of all the asset tags associated with the asset entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param pk the primary key of the asset entry
	 * @param start the lower bound of the range of asset entries
	 * @param end the upper bound of the range of asset entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset tags associated with the asset entry
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portlet.asset.model.AssetTag> getAssetTags(
		long pk, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, start, end, orderByComparator };

		List<com.liferay.portlet.asset.model.AssetTag> list = (List<com.liferay.portlet.asset.model.AssetTag>)FinderCacheUtil.getResult(FINDER_PATH_GET_ASSETTAGS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETASSETTAGS.concat(ORDER_BY_CLAUSE)
										   .concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETASSETTAGS.concat(com.liferay.portlet.asset.model.impl.AssetTagModelImpl.ORDER_BY_SQL);
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("AssetTag",
					com.liferay.portlet.asset.model.impl.AssetTagImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				list = (List<com.liferay.portlet.asset.model.AssetTag>)QueryUtil.list(q,
						getDialect(), start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_GET_ASSETTAGS,
						finderArgs);
				}
				else {
					assetTagPersistence.cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_GET_ASSETTAGS,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_ASSETTAGS_SIZE = new FinderPath(com.liferay.portlet.asset.model.impl.AssetTagModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED_ASSETENTRIES_ASSETTAGS,
			Long.class,
			AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME,
			"getAssetTagsSize", new String[] { Long.class.getName() });

	static {
		FINDER_PATH_GET_ASSETTAGS_SIZE.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns the number of asset tags associated with the asset entry.
	 *
	 * @param pk the primary key of the asset entry
	 * @return the number of asset tags associated with the asset entry
	 * @throws SystemException if a system exception occurred
	 */
	public int getAssetTagsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_ASSETTAGS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETASSETTAGSSIZE);

				q.addScalar(COUNT_COLUMN_NAME,
					com.liferay.portal.kernel.dao.orm.Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_GET_ASSETTAGS_SIZE,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_ASSETTAG = new FinderPath(com.liferay.portlet.asset.model.impl.AssetTagModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryModelImpl.FINDER_CACHE_ENABLED_ASSETENTRIES_ASSETTAGS,
			Boolean.class,
			AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME,
			"containsAssetTag",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the asset tag is associated with the asset entry.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetTagPK the primary key of the asset tag
	 * @return <code>true</code> if the asset tag is associated with the asset entry; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsAssetTag(long pk, long assetTagPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, assetTagPK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_ASSETTAG,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsAssetTag.contains(pk, assetTagPK));
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (value == null) {
					value = Boolean.FALSE;
				}

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_ASSETTAG,
					finderArgs, value);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the asset entry has any asset tags associated with it.
	 *
	 * @param pk the primary key of the asset entry to check for associations with asset tags
	 * @return <code>true</code> if the asset entry has any asset tags associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsAssetTags(long pk) throws SystemException {
		if (getAssetTagsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the asset entry and the asset tag. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetTagPK the primary key of the asset tag
	 * @throws SystemException if a system exception occurred
	 */
	public void addAssetTag(long pk, long assetTagPK) throws SystemException {
		try {
			addAssetTag.add(pk, assetTagPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}
	}

	/**
	 * Adds an association between the asset entry and the asset tag. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetTag the asset tag
	 * @throws SystemException if a system exception occurred
	 */
	public void addAssetTag(long pk,
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws SystemException {
		try {
			addAssetTag.add(pk, assetTag.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}
	}

	/**
	 * Adds an association between the asset entry and the asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetTagPKs the primary keys of the asset tags
	 * @throws SystemException if a system exception occurred
	 */
	public void addAssetTags(long pk, long[] assetTagPKs)
		throws SystemException {
		try {
			for (long assetTagPK : assetTagPKs) {
				addAssetTag.add(pk, assetTagPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}
	}

	/**
	 * Adds an association between the asset entry and the asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetTags the asset tags
	 * @throws SystemException if a system exception occurred
	 */
	public void addAssetTags(long pk,
		List<com.liferay.portlet.asset.model.AssetTag> assetTags)
		throws SystemException {
		try {
			for (com.liferay.portlet.asset.model.AssetTag assetTag : assetTags) {
				addAssetTag.add(pk, assetTag.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}
	}

	/**
	 * Clears all associations between the asset entry and its asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry to clear the associated asset tags from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearAssetTags(long pk) throws SystemException {
		try {
			clearAssetTags.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}
	}

	/**
	 * Removes the association between the asset entry and the asset tag. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetTagPK the primary key of the asset tag
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAssetTag(long pk, long assetTagPK)
		throws SystemException {
		try {
			removeAssetTag.remove(pk, assetTagPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}
	}

	/**
	 * Removes the association between the asset entry and the asset tag. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetTag the asset tag
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAssetTag(long pk,
		com.liferay.portlet.asset.model.AssetTag assetTag)
		throws SystemException {
		try {
			removeAssetTag.remove(pk, assetTag.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}
	}

	/**
	 * Removes the association between the asset entry and the asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetTagPKs the primary keys of the asset tags
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAssetTags(long pk, long[] assetTagPKs)
		throws SystemException {
		try {
			for (long assetTagPK : assetTagPKs) {
				removeAssetTag.remove(pk, assetTagPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}
	}

	/**
	 * Removes the association between the asset entry and the asset tags. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetTags the asset tags
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAssetTags(long pk,
		List<com.liferay.portlet.asset.model.AssetTag> assetTags)
		throws SystemException {
		try {
			for (com.liferay.portlet.asset.model.AssetTag assetTag : assetTags) {
				removeAssetTag.remove(pk, assetTag.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}
	}

	/**
	 * Sets the asset tags associated with the asset entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetTagPKs the primary keys of the asset tags to be associated with the asset entry
	 * @throws SystemException if a system exception occurred
	 */
	public void setAssetTags(long pk, long[] assetTagPKs)
		throws SystemException {
		try {
			Set<Long> assetTagPKSet = SetUtil.fromArray(assetTagPKs);

			List<com.liferay.portlet.asset.model.AssetTag> assetTags = getAssetTags(pk);

			for (com.liferay.portlet.asset.model.AssetTag assetTag : assetTags) {
				if (!assetTagPKSet.remove(assetTag.getPrimaryKey())) {
					removeAssetTag.remove(pk, assetTag.getPrimaryKey());
				}
			}

			for (Long assetTagPK : assetTagPKSet) {
				addAssetTag.add(pk, assetTagPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}
	}

	/**
	 * Sets the asset tags associated with the asset entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the asset entry
	 * @param assetTags the asset tags to be associated with the asset entry
	 * @throws SystemException if a system exception occurred
	 */
	public void setAssetTags(long pk,
		List<com.liferay.portlet.asset.model.AssetTag> assetTags)
		throws SystemException {
		try {
			long[] assetTagPKs = new long[assetTags.size()];

			for (int i = 0; i < assetTags.size(); i++) {
				com.liferay.portlet.asset.model.AssetTag assetTag = assetTags.get(i);

				assetTagPKs[i] = assetTag.getPrimaryKey();
			}

			setAssetTags(pk, assetTagPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(AssetEntryModelImpl.MAPPING_TABLE_ASSETENTRIES_ASSETTAGS_NAME);
		}
	}

	/**
	 * Initializes the asset entry persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.asset.model.AssetEntry")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<AssetEntry>> listenersList = new ArrayList<ModelListener<AssetEntry>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<AssetEntry>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsAssetCategory = new ContainsAssetCategory();

		addAssetCategory = new AddAssetCategory();
		clearAssetCategories = new ClearAssetCategories();
		removeAssetCategory = new RemoveAssetCategory();

		containsAssetTag = new ContainsAssetTag();

		addAssetTag = new AddAssetTag();
		clearAssetTags = new ClearAssetTags();
		removeAssetTag = new RemoveAssetTag();
	}

	public void destroy() {
		EntityCacheUtil.removeCache(AssetEntryImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = AssetCategoryPersistence.class)
	protected AssetCategoryPersistence assetCategoryPersistence;
	@BeanReference(type = AssetCategoryPropertyPersistence.class)
	protected AssetCategoryPropertyPersistence assetCategoryPropertyPersistence;
	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	@BeanReference(type = AssetLinkPersistence.class)
	protected AssetLinkPersistence assetLinkPersistence;
	@BeanReference(type = AssetTagPersistence.class)
	protected AssetTagPersistence assetTagPersistence;
	@BeanReference(type = AssetTagPropertyPersistence.class)
	protected AssetTagPropertyPersistence assetTagPropertyPersistence;
	@BeanReference(type = AssetTagStatsPersistence.class)
	protected AssetTagStatsPersistence assetTagStatsPersistence;
	@BeanReference(type = AssetVocabularyPersistence.class)
	protected AssetVocabularyPersistence assetVocabularyPersistence;
	@BeanReference(type = CompanyPersistence.class)
	protected CompanyPersistence companyPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = BlogsEntryPersistence.class)
	protected BlogsEntryPersistence blogsEntryPersistence;
	@BeanReference(type = BookmarksEntryPersistence.class)
	protected BookmarksEntryPersistence bookmarksEntryPersistence;
	@BeanReference(type = DLFileEntryPersistence.class)
	protected DLFileEntryPersistence dlFileEntryPersistence;
	@BeanReference(type = DLFolderPersistence.class)
	protected DLFolderPersistence dlFolderPersistence;
	@BeanReference(type = JournalArticlePersistence.class)
	protected JournalArticlePersistence journalArticlePersistence;
	@BeanReference(type = JournalArticleResourcePersistence.class)
	protected JournalArticleResourcePersistence journalArticleResourcePersistence;
	@BeanReference(type = MBMessagePersistence.class)
	protected MBMessagePersistence mbMessagePersistence;
	@BeanReference(type = SocialActivityPersistence.class)
	protected SocialActivityPersistence socialActivityPersistence;
	@BeanReference(type = SocialActivityCounterPersistence.class)
	protected SocialActivityCounterPersistence socialActivityCounterPersistence;
	@BeanReference(type = WikiPagePersistence.class)
	protected WikiPagePersistence wikiPagePersistence;
	@BeanReference(type = WikiPageResourcePersistence.class)
	protected WikiPageResourcePersistence wikiPageResourcePersistence;
	protected ContainsAssetCategory containsAssetCategory;
	protected AddAssetCategory addAssetCategory;
	protected ClearAssetCategories clearAssetCategories;
	protected RemoveAssetCategory removeAssetCategory;
	protected ContainsAssetTag containsAssetTag;
	protected AddAssetTag addAssetTag;
	protected ClearAssetTags clearAssetTags;
	protected RemoveAssetTag removeAssetTag;

	protected class ContainsAssetCategory {
		protected ContainsAssetCategory() {
			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSASSETCATEGORY,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long entryId, long categoryId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(entryId), new Long(categoryId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery<Integer> _mappingSqlQuery;
	}

	protected class AddAssetCategory {
		protected AddAssetCategory() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO AssetEntries_AssetCategories (entryId, categoryId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void add(long entryId, long categoryId)
			throws SystemException {
			if (!containsAssetCategory.contains(entryId, categoryId)) {
				ModelListener<com.liferay.portlet.asset.model.AssetCategory>[] assetCategoryListeners =
					assetCategoryPersistence.getListeners();

				for (ModelListener<AssetEntry> listener : listeners) {
					listener.onBeforeAddAssociation(entryId,
						com.liferay.portlet.asset.model.AssetCategory.class.getName(),
						categoryId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetCategory> listener : assetCategoryListeners) {
					listener.onBeforeAddAssociation(categoryId,
						AssetEntry.class.getName(), entryId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(entryId), new Long(categoryId)
					});

				for (ModelListener<AssetEntry> listener : listeners) {
					listener.onAfterAddAssociation(entryId,
						com.liferay.portlet.asset.model.AssetCategory.class.getName(),
						categoryId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetCategory> listener : assetCategoryListeners) {
					listener.onAfterAddAssociation(categoryId,
						AssetEntry.class.getName(), entryId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ClearAssetCategories {
		protected ClearAssetCategories() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM AssetEntries_AssetCategories WHERE entryId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long entryId) throws SystemException {
			ModelListener<com.liferay.portlet.asset.model.AssetCategory>[] assetCategoryListeners =
				assetCategoryPersistence.getListeners();

			List<com.liferay.portlet.asset.model.AssetCategory> assetCategories = null;

			if ((listeners.length > 0) || (assetCategoryListeners.length > 0)) {
				assetCategories = getAssetCategories(entryId);

				for (com.liferay.portlet.asset.model.AssetCategory assetCategory : assetCategories) {
					for (ModelListener<AssetEntry> listener : listeners) {
						listener.onBeforeRemoveAssociation(entryId,
							com.liferay.portlet.asset.model.AssetCategory.class.getName(),
							assetCategory.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.asset.model.AssetCategory> listener : assetCategoryListeners) {
						listener.onBeforeRemoveAssociation(assetCategory.getPrimaryKey(),
							AssetEntry.class.getName(), entryId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(entryId) });

			if ((listeners.length > 0) || (assetCategoryListeners.length > 0)) {
				for (com.liferay.portlet.asset.model.AssetCategory assetCategory : assetCategories) {
					for (ModelListener<AssetEntry> listener : listeners) {
						listener.onAfterRemoveAssociation(entryId,
							com.liferay.portlet.asset.model.AssetCategory.class.getName(),
							assetCategory.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.asset.model.AssetCategory> listener : assetCategoryListeners) {
						listener.onAfterRemoveAssociation(assetCategory.getPrimaryKey(),
							AssetEntry.class.getName(), entryId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveAssetCategory {
		protected RemoveAssetCategory() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM AssetEntries_AssetCategories WHERE entryId = ? AND categoryId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void remove(long entryId, long categoryId)
			throws SystemException {
			if (containsAssetCategory.contains(entryId, categoryId)) {
				ModelListener<com.liferay.portlet.asset.model.AssetCategory>[] assetCategoryListeners =
					assetCategoryPersistence.getListeners();

				for (ModelListener<AssetEntry> listener : listeners) {
					listener.onBeforeRemoveAssociation(entryId,
						com.liferay.portlet.asset.model.AssetCategory.class.getName(),
						categoryId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetCategory> listener : assetCategoryListeners) {
					listener.onBeforeRemoveAssociation(categoryId,
						AssetEntry.class.getName(), entryId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(entryId), new Long(categoryId)
					});

				for (ModelListener<AssetEntry> listener : listeners) {
					listener.onAfterRemoveAssociation(entryId,
						com.liferay.portlet.asset.model.AssetCategory.class.getName(),
						categoryId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetCategory> listener : assetCategoryListeners) {
					listener.onAfterRemoveAssociation(categoryId,
						AssetEntry.class.getName(), entryId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ContainsAssetTag {
		protected ContainsAssetTag() {
			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSASSETTAG,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long entryId, long tagId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(entryId), new Long(tagId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery<Integer> _mappingSqlQuery;
	}

	protected class AddAssetTag {
		protected AddAssetTag() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO AssetEntries_AssetTags (entryId, tagId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void add(long entryId, long tagId) throws SystemException {
			if (!containsAssetTag.contains(entryId, tagId)) {
				ModelListener<com.liferay.portlet.asset.model.AssetTag>[] assetTagListeners =
					assetTagPersistence.getListeners();

				for (ModelListener<AssetEntry> listener : listeners) {
					listener.onBeforeAddAssociation(entryId,
						com.liferay.portlet.asset.model.AssetTag.class.getName(),
						tagId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetTag> listener : assetTagListeners) {
					listener.onBeforeAddAssociation(tagId,
						AssetEntry.class.getName(), entryId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(entryId), new Long(tagId)
					});

				for (ModelListener<AssetEntry> listener : listeners) {
					listener.onAfterAddAssociation(entryId,
						com.liferay.portlet.asset.model.AssetTag.class.getName(),
						tagId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetTag> listener : assetTagListeners) {
					listener.onAfterAddAssociation(tagId,
						AssetEntry.class.getName(), entryId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ClearAssetTags {
		protected ClearAssetTags() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM AssetEntries_AssetTags WHERE entryId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long entryId) throws SystemException {
			ModelListener<com.liferay.portlet.asset.model.AssetTag>[] assetTagListeners =
				assetTagPersistence.getListeners();

			List<com.liferay.portlet.asset.model.AssetTag> assetTags = null;

			if ((listeners.length > 0) || (assetTagListeners.length > 0)) {
				assetTags = getAssetTags(entryId);

				for (com.liferay.portlet.asset.model.AssetTag assetTag : assetTags) {
					for (ModelListener<AssetEntry> listener : listeners) {
						listener.onBeforeRemoveAssociation(entryId,
							com.liferay.portlet.asset.model.AssetTag.class.getName(),
							assetTag.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.asset.model.AssetTag> listener : assetTagListeners) {
						listener.onBeforeRemoveAssociation(assetTag.getPrimaryKey(),
							AssetEntry.class.getName(), entryId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(entryId) });

			if ((listeners.length > 0) || (assetTagListeners.length > 0)) {
				for (com.liferay.portlet.asset.model.AssetTag assetTag : assetTags) {
					for (ModelListener<AssetEntry> listener : listeners) {
						listener.onAfterRemoveAssociation(entryId,
							com.liferay.portlet.asset.model.AssetTag.class.getName(),
							assetTag.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portlet.asset.model.AssetTag> listener : assetTagListeners) {
						listener.onAfterRemoveAssociation(assetTag.getPrimaryKey(),
							AssetEntry.class.getName(), entryId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveAssetTag {
		protected RemoveAssetTag() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM AssetEntries_AssetTags WHERE entryId = ? AND tagId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void remove(long entryId, long tagId)
			throws SystemException {
			if (containsAssetTag.contains(entryId, tagId)) {
				ModelListener<com.liferay.portlet.asset.model.AssetTag>[] assetTagListeners =
					assetTagPersistence.getListeners();

				for (ModelListener<AssetEntry> listener : listeners) {
					listener.onBeforeRemoveAssociation(entryId,
						com.liferay.portlet.asset.model.AssetTag.class.getName(),
						tagId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetTag> listener : assetTagListeners) {
					listener.onBeforeRemoveAssociation(tagId,
						AssetEntry.class.getName(), entryId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(entryId), new Long(tagId)
					});

				for (ModelListener<AssetEntry> listener : listeners) {
					listener.onAfterRemoveAssociation(entryId,
						com.liferay.portlet.asset.model.AssetTag.class.getName(),
						tagId);
				}

				for (ModelListener<com.liferay.portlet.asset.model.AssetTag> listener : assetTagListeners) {
					listener.onAfterRemoveAssociation(tagId,
						AssetEntry.class.getName(), entryId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	private static final String _SQL_SELECT_ASSETENTRY = "SELECT assetEntry FROM AssetEntry assetEntry";
	private static final String _SQL_SELECT_ASSETENTRY_WHERE = "SELECT assetEntry FROM AssetEntry assetEntry WHERE ";
	private static final String _SQL_COUNT_ASSETENTRY = "SELECT COUNT(assetEntry) FROM AssetEntry assetEntry";
	private static final String _SQL_COUNT_ASSETENTRY_WHERE = "SELECT COUNT(assetEntry) FROM AssetEntry assetEntry WHERE ";
	private static final String _SQL_GETASSETCATEGORIES = "SELECT {AssetCategory.*} FROM AssetCategory INNER JOIN AssetEntries_AssetCategories ON (AssetEntries_AssetCategories.categoryId = AssetCategory.categoryId) WHERE (AssetEntries_AssetCategories.entryId = ?)";
	private static final String _SQL_GETASSETCATEGORIESSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM AssetEntries_AssetCategories WHERE entryId = ?";
	private static final String _SQL_CONTAINSASSETCATEGORY = "SELECT COUNT(*) AS COUNT_VALUE FROM AssetEntries_AssetCategories WHERE entryId = ? AND categoryId = ?";
	private static final String _SQL_GETASSETTAGS = "SELECT {AssetTag.*} FROM AssetTag INNER JOIN AssetEntries_AssetTags ON (AssetEntries_AssetTags.tagId = AssetTag.tagId) WHERE (AssetEntries_AssetTags.entryId = ?)";
	private static final String _SQL_GETASSETTAGSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM AssetEntries_AssetTags WHERE entryId = ?";
	private static final String _SQL_CONTAINSASSETTAG = "SELECT COUNT(*) AS COUNT_VALUE FROM AssetEntries_AssetTags WHERE entryId = ? AND tagId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "assetEntry.companyId = ?";
	private static final String _FINDER_COLUMN_VISIBLE_VISIBLE_2 = "assetEntry.visible = ?";
	private static final String _FINDER_COLUMN_PUBLISHDATE_PUBLISHDATE_1 = "assetEntry.publishDate IS NULL";
	private static final String _FINDER_COLUMN_PUBLISHDATE_PUBLISHDATE_2 = "assetEntry.publishDate = ?";
	private static final String _FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_1 = "assetEntry.expirationDate IS NULL";
	private static final String _FINDER_COLUMN_EXPIRATIONDATE_EXPIRATIONDATE_2 = "assetEntry.expirationDate = ?";
	private static final String _FINDER_COLUMN_LAYOUTUUID_LAYOUTUUID_1 = "assetEntry.layoutUuid IS NULL";
	private static final String _FINDER_COLUMN_LAYOUTUUID_LAYOUTUUID_2 = "assetEntry.layoutUuid = ?";
	private static final String _FINDER_COLUMN_LAYOUTUUID_LAYOUTUUID_3 = "(assetEntry.layoutUuid IS NULL OR assetEntry.layoutUuid = ?)";
	private static final String _FINDER_COLUMN_G_CU_GROUPID_2 = "assetEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_CU_CLASSUUID_1 = "assetEntry.classUuid IS NULL";
	private static final String _FINDER_COLUMN_G_CU_CLASSUUID_2 = "assetEntry.classUuid = ?";
	private static final String _FINDER_COLUMN_G_CU_CLASSUUID_3 = "(assetEntry.classUuid IS NULL OR assetEntry.classUuid = ?)";
	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "assetEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 = "assetEntry.classPK = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "assetEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AssetEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AssetEntry exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(AssetEntryPersistenceImpl.class);
	private static AssetEntry _nullAssetEntry = new AssetEntryImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<AssetEntry> toCacheModel() {
				return _nullAssetEntryCacheModel;
			}
		};

	private static CacheModel<AssetEntry> _nullAssetEntryCacheModel = new CacheModel<AssetEntry>() {
			public AssetEntry toEntityModel() {
				return _nullAssetEntry;
			}
		};
}