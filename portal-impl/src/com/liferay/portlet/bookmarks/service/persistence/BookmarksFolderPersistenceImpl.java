/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.bookmarks.service.persistence;

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
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.bookmarks.NoSuchFolderException;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.model.impl.BookmarksFolderImpl;
import com.liferay.portlet.bookmarks.model.impl.BookmarksFolderModelImpl;
import com.liferay.portlet.expando.service.persistence.ExpandoValuePersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the bookmarks folder service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BookmarksFolderPersistence
 * @see BookmarksFolderUtil
 * @generated
 */
public class BookmarksFolderPersistenceImpl extends BasePersistenceImpl<BookmarksFolder>
	implements BookmarksFolderPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link BookmarksFolderUtil} to access the bookmarks folder persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = BookmarksFolderImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	public static final FinderPath FINDER_PATH_FIND_BY_RESOURCEBLOCKID = new FinderPath(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderModelImpl.FINDER_CACHE_ENABLED,
			BookmarksFolderImpl.class, FINDER_CLASS_NAME_LIST,
			"findByResourceBlockId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_RESOURCEBLOCKID = new FinderPath(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByResourceBlockId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_UUID = new FinderPath(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderModelImpl.FINDER_CACHE_ENABLED,
			BookmarksFolderImpl.class, FINDER_CLASS_NAME_LIST, "findByUuid",
			new String[] {
				String.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByUuid",
			new String[] { String.class.getName() });
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderModelImpl.FINDER_CACHE_ENABLED,
			BookmarksFolderImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_GROUPID = new FinderPath(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderModelImpl.FINDER_CACHE_ENABLED,
			BookmarksFolderImpl.class, FINDER_CLASS_NAME_LIST, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_COMPANYID = new FinderPath(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderModelImpl.FINDER_CACHE_ENABLED,
			BookmarksFolderImpl.class, FINDER_CLASS_NAME_LIST,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByCompanyId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_BY_G_P = new FinderPath(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderModelImpl.FINDER_CACHE_ENABLED,
			BookmarksFolderImpl.class, FINDER_CLASS_NAME_LIST, "findByG_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			"java.lang.Integer", "java.lang.Integer",
				"com.liferay.portal.kernel.util.OrderByComparator"
			});
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P = new FinderPath(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countByG_P",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderModelImpl.FINDER_CACHE_ENABLED,
			BookmarksFolderImpl.class, FINDER_CLASS_NAME_LIST, "findAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST, "countAll", new String[0]);

	/**
	 * Caches the bookmarks folder in the entity cache if it is enabled.
	 *
	 * @param bookmarksFolder the bookmarks folder
	 */
	public void cacheResult(BookmarksFolder bookmarksFolder) {
		EntityCacheUtil.putResult(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderImpl.class, bookmarksFolder.getPrimaryKey(),
			bookmarksFolder);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				bookmarksFolder.getUuid(),
				Long.valueOf(bookmarksFolder.getGroupId())
			}, bookmarksFolder);

		bookmarksFolder.resetOriginalValues();
	}

	/**
	 * Caches the bookmarks folders in the entity cache if it is enabled.
	 *
	 * @param bookmarksFolders the bookmarks folders
	 */
	public void cacheResult(List<BookmarksFolder> bookmarksFolders) {
		for (BookmarksFolder bookmarksFolder : bookmarksFolders) {
			if (EntityCacheUtil.getResult(
						BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
						BookmarksFolderImpl.class,
						bookmarksFolder.getPrimaryKey()) == null) {
				cacheResult(bookmarksFolder);
			}
		}
	}

	/**
	 * Clears the cache for all bookmarks folders.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(BookmarksFolderImpl.class.getName());
		}

		EntityCacheUtil.clearCache(BookmarksFolderImpl.class.getName());
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
	}

	/**
	 * Clears the cache for the bookmarks folder.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(BookmarksFolder bookmarksFolder) {
		EntityCacheUtil.removeResult(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderImpl.class, bookmarksFolder.getPrimaryKey());

		FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL, FINDER_ARGS_EMPTY);

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				bookmarksFolder.getUuid(),
				Long.valueOf(bookmarksFolder.getGroupId())
			});
	}

	/**
	 * Creates a new bookmarks folder with the primary key. Does not add the bookmarks folder to the database.
	 *
	 * @param folderId the primary key for the new bookmarks folder
	 * @return the new bookmarks folder
	 */
	public BookmarksFolder create(long folderId) {
		BookmarksFolder bookmarksFolder = new BookmarksFolderImpl();

		bookmarksFolder.setNew(true);
		bookmarksFolder.setPrimaryKey(folderId);

		String uuid = PortalUUIDUtil.generate();

		bookmarksFolder.setUuid(uuid);

		return bookmarksFolder;
	}

	/**
	 * Removes the bookmarks folder with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the bookmarks folder
	 * @return the bookmarks folder that was removed
	 * @throws com.liferay.portal.NoSuchModelException if a bookmarks folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BookmarksFolder remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return remove(((Long)primaryKey).longValue());
	}

	/**
	 * Removes the bookmarks folder with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param folderId the primary key of the bookmarks folder
	 * @return the bookmarks folder that was removed
	 * @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a bookmarks folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BookmarksFolder remove(long folderId)
		throws NoSuchFolderException, SystemException {
		Session session = null;

		try {
			session = openSession();

			BookmarksFolder bookmarksFolder = (BookmarksFolder)session.get(BookmarksFolderImpl.class,
					Long.valueOf(folderId));

			if (bookmarksFolder == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + folderId);
				}

				throw new NoSuchFolderException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					folderId);
			}

			return bookmarksFolderPersistence.remove(bookmarksFolder);
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

	/**
	 * Removes the bookmarks folder from the database. Also notifies the appropriate model listeners.
	 *
	 * @param bookmarksFolder the bookmarks folder
	 * @return the bookmarks folder that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BookmarksFolder remove(BookmarksFolder bookmarksFolder)
		throws SystemException {
		return super.remove(bookmarksFolder);
	}

	@Override
	protected BookmarksFolder removeImpl(BookmarksFolder bookmarksFolder)
		throws SystemException {
		bookmarksFolder = toUnwrappedModel(bookmarksFolder);

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.delete(session, bookmarksFolder);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		BookmarksFolderModelImpl bookmarksFolderModelImpl = (BookmarksFolderModelImpl)bookmarksFolder;

		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				bookmarksFolderModelImpl.getUuid(),
				Long.valueOf(bookmarksFolderModelImpl.getGroupId())
			});

		EntityCacheUtil.removeResult(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderImpl.class, bookmarksFolder.getPrimaryKey());

		return bookmarksFolder;
	}

	@Override
	public BookmarksFolder updateImpl(
		com.liferay.portlet.bookmarks.model.BookmarksFolder bookmarksFolder,
		boolean merge) throws SystemException {
		bookmarksFolder = toUnwrappedModel(bookmarksFolder);

		boolean isNew = bookmarksFolder.isNew();

		BookmarksFolderModelImpl bookmarksFolderModelImpl = (BookmarksFolderModelImpl)bookmarksFolder;

		if (Validator.isNull(bookmarksFolder.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			bookmarksFolder.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			BatchSessionUtil.update(session, bookmarksFolder, merge);

			bookmarksFolder.setNew(false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

		EntityCacheUtil.putResult(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
			BookmarksFolderImpl.class, bookmarksFolder.getPrimaryKey(),
			bookmarksFolder);

		if (!isNew &&
				(!Validator.equals(bookmarksFolder.getUuid(),
					bookmarksFolderModelImpl.getOriginalUuid()) ||
				(bookmarksFolder.getGroupId() != bookmarksFolderModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					bookmarksFolderModelImpl.getOriginalUuid(),
					Long.valueOf(bookmarksFolderModelImpl.getOriginalGroupId())
				});
		}

		if (isNew ||
				(!Validator.equals(bookmarksFolder.getUuid(),
					bookmarksFolderModelImpl.getOriginalUuid()) ||
				(bookmarksFolder.getGroupId() != bookmarksFolderModelImpl.getOriginalGroupId()))) {
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
				new Object[] {
					bookmarksFolder.getUuid(),
					Long.valueOf(bookmarksFolder.getGroupId())
				}, bookmarksFolder);
		}

		return bookmarksFolder;
	}

	protected BookmarksFolder toUnwrappedModel(BookmarksFolder bookmarksFolder) {
		if (bookmarksFolder instanceof BookmarksFolderImpl) {
			return bookmarksFolder;
		}

		BookmarksFolderImpl bookmarksFolderImpl = new BookmarksFolderImpl();

		bookmarksFolderImpl.setNew(bookmarksFolder.isNew());
		bookmarksFolderImpl.setPrimaryKey(bookmarksFolder.getPrimaryKey());

		bookmarksFolderImpl.setUuid(bookmarksFolder.getUuid());
		bookmarksFolderImpl.setFolderId(bookmarksFolder.getFolderId());
		bookmarksFolderImpl.setGroupId(bookmarksFolder.getGroupId());
		bookmarksFolderImpl.setCompanyId(bookmarksFolder.getCompanyId());
		bookmarksFolderImpl.setUserId(bookmarksFolder.getUserId());
		bookmarksFolderImpl.setUserName(bookmarksFolder.getUserName());
		bookmarksFolderImpl.setCreateDate(bookmarksFolder.getCreateDate());
		bookmarksFolderImpl.setModifiedDate(bookmarksFolder.getModifiedDate());
		bookmarksFolderImpl.setResourceBlockId(bookmarksFolder.getResourceBlockId());
		bookmarksFolderImpl.setParentFolderId(bookmarksFolder.getParentFolderId());
		bookmarksFolderImpl.setName(bookmarksFolder.getName());
		bookmarksFolderImpl.setDescription(bookmarksFolder.getDescription());

		return bookmarksFolderImpl;
	}

	/**
	 * Returns the bookmarks folder with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the bookmarks folder
	 * @return the bookmarks folder
	 * @throws com.liferay.portal.NoSuchModelException if a bookmarks folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BookmarksFolder findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the bookmarks folder with the primary key or throws a {@link com.liferay.portlet.bookmarks.NoSuchFolderException} if it could not be found.
	 *
	 * @param folderId the primary key of the bookmarks folder
	 * @return the bookmarks folder
	 * @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a bookmarks folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BookmarksFolder findByPrimaryKey(long folderId)
		throws NoSuchFolderException, SystemException {
		BookmarksFolder bookmarksFolder = fetchByPrimaryKey(folderId);

		if (bookmarksFolder == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + folderId);
			}

			throw new NoSuchFolderException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				folderId);
		}

		return bookmarksFolder;
	}

	/**
	 * Returns the bookmarks folder with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the bookmarks folder
	 * @return the bookmarks folder, or <code>null</code> if a bookmarks folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BookmarksFolder fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the bookmarks folder with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param folderId the primary key of the bookmarks folder
	 * @return the bookmarks folder, or <code>null</code> if a bookmarks folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BookmarksFolder fetchByPrimaryKey(long folderId)
		throws SystemException {
		BookmarksFolder bookmarksFolder = (BookmarksFolder)EntityCacheUtil.getResult(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
				BookmarksFolderImpl.class, folderId);

		if (bookmarksFolder == _nullBookmarksFolder) {
			return null;
		}

		if (bookmarksFolder == null) {
			Session session = null;

			boolean hasException = false;

			try {
				session = openSession();

				bookmarksFolder = (BookmarksFolder)session.get(BookmarksFolderImpl.class,
						Long.valueOf(folderId));
			}
			catch (Exception e) {
				hasException = true;

				throw processException(e);
			}
			finally {
				if (bookmarksFolder != null) {
					cacheResult(bookmarksFolder);
				}
				else if (!hasException) {
					EntityCacheUtil.putResult(BookmarksFolderModelImpl.ENTITY_CACHE_ENABLED,
						BookmarksFolderImpl.class, folderId,
						_nullBookmarksFolder);
				}

				closeSession(session);
			}
		}

		return bookmarksFolder;
	}

	/**
	 * Returns all the bookmarks folders where resourceBlockId = &#63;.
	 *
	 * @param resourceBlockId the resource block ID
	 * @return the matching bookmarks folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<BookmarksFolder> findByResourceBlockId(long resourceBlockId)
		throws SystemException {
		return findByResourceBlockId(resourceBlockId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the bookmarks folders where resourceBlockId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param resourceBlockId the resource block ID
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @return the range of matching bookmarks folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<BookmarksFolder> findByResourceBlockId(long resourceBlockId,
		int start, int end) throws SystemException {
		return findByResourceBlockId(resourceBlockId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks folders where resourceBlockId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param resourceBlockId the resource block ID
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<BookmarksFolder> findByResourceBlockId(long resourceBlockId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				resourceBlockId,
				
				start, end, orderByComparator
			};

		List<BookmarksFolder> list = (List<BookmarksFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_RESOURCEBLOCKID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_BOOKMARKSFOLDER_WHERE);

			query.append(_FINDER_COLUMN_RESOURCEBLOCKID_RESOURCEBLOCKID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(BookmarksFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourceBlockId);

				list = (List<BookmarksFolder>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_RESOURCEBLOCKID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_RESOURCEBLOCKID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first bookmarks folder in the ordered set where resourceBlockId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param resourceBlockId the resource block ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks folder
	 * @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a matching bookmarks folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BookmarksFolder findByResourceBlockId_First(long resourceBlockId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		List<BookmarksFolder> list = findByResourceBlockId(resourceBlockId, 0,
				1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("resourceBlockId=");
			msg.append(resourceBlockId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last bookmarks folder in the ordered set where resourceBlockId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param resourceBlockId the resource block ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching bookmarks folder
	 * @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a matching bookmarks folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BookmarksFolder findByResourceBlockId_Last(long resourceBlockId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		int count = countByResourceBlockId(resourceBlockId);

		List<BookmarksFolder> list = findByResourceBlockId(resourceBlockId,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("resourceBlockId=");
			msg.append(resourceBlockId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the bookmarks folders before and after the current bookmarks folder in the ordered set where resourceBlockId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param folderId the primary key of the current bookmarks folder
	 * @param resourceBlockId the resource block ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next bookmarks folder
	 * @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a bookmarks folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BookmarksFolder[] findByResourceBlockId_PrevAndNext(long folderId,
		long resourceBlockId, OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		BookmarksFolder bookmarksFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			BookmarksFolder[] array = new BookmarksFolderImpl[3];

			array[0] = getByResourceBlockId_PrevAndNext(session,
					bookmarksFolder, resourceBlockId, orderByComparator, true);

			array[1] = bookmarksFolder;

			array[2] = getByResourceBlockId_PrevAndNext(session,
					bookmarksFolder, resourceBlockId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BookmarksFolder getByResourceBlockId_PrevAndNext(
		Session session, BookmarksFolder bookmarksFolder, long resourceBlockId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BOOKMARKSFOLDER_WHERE);

		query.append(_FINDER_COLUMN_RESOURCEBLOCKID_RESOURCEBLOCKID_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
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
			query.append(BookmarksFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(resourceBlockId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(bookmarksFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BookmarksFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the bookmarks folders where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching bookmarks folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<BookmarksFolder> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the bookmarks folders where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @return the range of matching bookmarks folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<BookmarksFolder> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks folders where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<BookmarksFolder> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, start, end, orderByComparator };

		List<BookmarksFolder> list = (List<BookmarksFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_UUID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_BOOKMARKSFOLDER_WHERE);

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
				query.append(BookmarksFolderModelImpl.ORDER_BY_JPQL);
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

				list = (List<BookmarksFolder>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_UUID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_UUID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first bookmarks folder in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks folder
	 * @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a matching bookmarks folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BookmarksFolder findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		List<BookmarksFolder> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last bookmarks folder in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching bookmarks folder
	 * @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a matching bookmarks folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BookmarksFolder findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		int count = countByUuid(uuid);

		List<BookmarksFolder> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the bookmarks folders before and after the current bookmarks folder in the ordered set where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param folderId the primary key of the current bookmarks folder
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next bookmarks folder
	 * @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a bookmarks folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BookmarksFolder[] findByUuid_PrevAndNext(long folderId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		BookmarksFolder bookmarksFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			BookmarksFolder[] array = new BookmarksFolderImpl[3];

			array[0] = getByUuid_PrevAndNext(session, bookmarksFolder, uuid,
					orderByComparator, true);

			array[1] = bookmarksFolder;

			array[2] = getByUuid_PrevAndNext(session, bookmarksFolder, uuid,
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

	protected BookmarksFolder getByUuid_PrevAndNext(Session session,
		BookmarksFolder bookmarksFolder, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BOOKMARKSFOLDER_WHERE);

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
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
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
			query.append(BookmarksFolderModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByValues(bookmarksFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BookmarksFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns the bookmarks folder where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.bookmarks.NoSuchFolderException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching bookmarks folder
	 * @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a matching bookmarks folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BookmarksFolder findByUUID_G(String uuid, long groupId)
		throws NoSuchFolderException, SystemException {
		BookmarksFolder bookmarksFolder = fetchByUUID_G(uuid, groupId);

		if (bookmarksFolder == null) {
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

		return bookmarksFolder;
	}

	/**
	 * Returns the bookmarks folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BookmarksFolder fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the bookmarks folder where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching bookmarks folder, or <code>null</code> if a matching bookmarks folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BookmarksFolder fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_BOOKMARKSFOLDER_WHERE);

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

			query.append(BookmarksFolderModelImpl.ORDER_BY_JPQL);

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

				List<BookmarksFolder> list = q.list();

				result = list;

				BookmarksFolder bookmarksFolder = null;

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					bookmarksFolder = list.get(0);

					cacheResult(bookmarksFolder);

					if ((bookmarksFolder.getUuid() == null) ||
							!bookmarksFolder.getUuid().equals(uuid) ||
							(bookmarksFolder.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, bookmarksFolder);
					}
				}

				return bookmarksFolder;
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
				return (BookmarksFolder)result;
			}
		}
	}

	/**
	 * Returns all the bookmarks folders where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching bookmarks folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<BookmarksFolder> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the bookmarks folders where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @return the range of matching bookmarks folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<BookmarksFolder> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks folders where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<BookmarksFolder> findByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId,
				
				start, end, orderByComparator
			};

		List<BookmarksFolder> list = (List<BookmarksFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_GROUPID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_BOOKMARKSFOLDER_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(BookmarksFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<BookmarksFolder>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_GROUPID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_GROUPID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first bookmarks folder in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks folder
	 * @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a matching bookmarks folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BookmarksFolder findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		List<BookmarksFolder> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last bookmarks folder in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching bookmarks folder
	 * @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a matching bookmarks folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BookmarksFolder findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		int count = countByGroupId(groupId);

		List<BookmarksFolder> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the bookmarks folders before and after the current bookmarks folder in the ordered set where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param folderId the primary key of the current bookmarks folder
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next bookmarks folder
	 * @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a bookmarks folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BookmarksFolder[] findByGroupId_PrevAndNext(long folderId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		BookmarksFolder bookmarksFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			BookmarksFolder[] array = new BookmarksFolderImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, bookmarksFolder,
					groupId, orderByComparator, true);

			array[1] = bookmarksFolder;

			array[2] = getByGroupId_PrevAndNext(session, bookmarksFolder,
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

	protected BookmarksFolder getByGroupId_PrevAndNext(Session session,
		BookmarksFolder bookmarksFolder, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BOOKMARKSFOLDER_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
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
			query.append(BookmarksFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(bookmarksFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BookmarksFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the bookmarks folders that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching bookmarks folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<BookmarksFolder> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the bookmarks folders that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @return the range of matching bookmarks folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<BookmarksFolder> filterFindByGroupId(long groupId, int start,
		int end) throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks folders that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<BookmarksFolder> filterFindByGroupId(long groupId, int start,
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

		query.append(_SQL_SELECT_BOOKMARKSFOLDER_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
				orderByComparator);
		}

		else {
			query.append(BookmarksFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BookmarksFolder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				_FILTER_ENTITY_TABLE_FILTER_USERID_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<BookmarksFolder>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the bookmarks folders before and after the current bookmarks folder in the ordered set of bookmarks folders that the user has permission to view where groupId = &#63;.
	 *
	 * @param folderId the primary key of the current bookmarks folder
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next bookmarks folder
	 * @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a bookmarks folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BookmarksFolder[] filterFindByGroupId_PrevAndNext(long folderId,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(folderId, groupId,
				orderByComparator);
		}

		BookmarksFolder bookmarksFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			BookmarksFolder[] array = new BookmarksFolderImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, bookmarksFolder,
					groupId, orderByComparator, true);

			array[1] = bookmarksFolder;

			array[2] = filterGetByGroupId_PrevAndNext(session, bookmarksFolder,
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

	protected BookmarksFolder filterGetByGroupId_PrevAndNext(Session session,
		BookmarksFolder bookmarksFolder, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BOOKMARKSFOLDER_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
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
			query.append(BookmarksFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BookmarksFolder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				_FILTER_ENTITY_TABLE_FILTER_USERID_COLUMN, groupId);

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(bookmarksFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BookmarksFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the bookmarks folders where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching bookmarks folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<BookmarksFolder> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the bookmarks folders where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @return the range of matching bookmarks folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<BookmarksFolder> findByCompanyId(long companyId, int start,
		int end) throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks folders where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<BookmarksFolder> findByCompanyId(long companyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] {
				companyId,
				
				start, end, orderByComparator
			};

		List<BookmarksFolder> list = (List<BookmarksFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_COMPANYID,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_BOOKMARKSFOLDER_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(BookmarksFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<BookmarksFolder>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_COMPANYID,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_COMPANYID,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first bookmarks folder in the ordered set where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks folder
	 * @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a matching bookmarks folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BookmarksFolder findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		List<BookmarksFolder> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last bookmarks folder in the ordered set where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching bookmarks folder
	 * @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a matching bookmarks folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BookmarksFolder findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		int count = countByCompanyId(companyId);

		List<BookmarksFolder> list = findByCompanyId(companyId, count - 1,
				count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the bookmarks folders before and after the current bookmarks folder in the ordered set where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param folderId the primary key of the current bookmarks folder
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next bookmarks folder
	 * @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a bookmarks folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BookmarksFolder[] findByCompanyId_PrevAndNext(long folderId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		BookmarksFolder bookmarksFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			BookmarksFolder[] array = new BookmarksFolderImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, bookmarksFolder,
					companyId, orderByComparator, true);

			array[1] = bookmarksFolder;

			array[2] = getByCompanyId_PrevAndNext(session, bookmarksFolder,
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

	protected BookmarksFolder getByCompanyId_PrevAndNext(Session session,
		BookmarksFolder bookmarksFolder, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BOOKMARKSFOLDER_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
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
			query.append(BookmarksFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(bookmarksFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BookmarksFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the bookmarks folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @return the matching bookmarks folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<BookmarksFolder> findByG_P(long groupId, long parentFolderId)
		throws SystemException {
		return findByG_P(groupId, parentFolderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the bookmarks folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @return the range of matching bookmarks folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<BookmarksFolder> findByG_P(long groupId, long parentFolderId,
		int start, int end) throws SystemException {
		return findByG_P(groupId, parentFolderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<BookmarksFolder> findByG_P(long groupId, long parentFolderId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				groupId, parentFolderId,
				
				start, end, orderByComparator
			};

		List<BookmarksFolder> list = (List<BookmarksFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_G_P,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_BOOKMARKSFOLDER_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_PARENTFOLDERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}

			else {
				query.append(BookmarksFolderModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentFolderId);

				list = (List<BookmarksFolder>)QueryUtil.list(q, getDialect(),
						start, end);
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_G_P,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_G_P,
						finderArgs, list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first bookmarks folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching bookmarks folder
	 * @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a matching bookmarks folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BookmarksFolder findByG_P_First(long groupId, long parentFolderId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		List<BookmarksFolder> list = findByG_P(groupId, parentFolderId, 0, 1,
				orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", parentFolderId=");
			msg.append(parentFolderId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the last bookmarks folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching bookmarks folder
	 * @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a matching bookmarks folder could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BookmarksFolder findByG_P_Last(long groupId, long parentFolderId,
		OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		int count = countByG_P(groupId, parentFolderId);

		List<BookmarksFolder> list = findByG_P(groupId, parentFolderId,
				count - 1, count, orderByComparator);

		if (list.isEmpty()) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", parentFolderId=");
			msg.append(parentFolderId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			throw new NoSuchFolderException(msg.toString());
		}
		else {
			return list.get(0);
		}
	}

	/**
	 * Returns the bookmarks folders before and after the current bookmarks folder in the ordered set where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param folderId the primary key of the current bookmarks folder
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next bookmarks folder
	 * @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a bookmarks folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BookmarksFolder[] findByG_P_PrevAndNext(long folderId, long groupId,
		long parentFolderId, OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		BookmarksFolder bookmarksFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			BookmarksFolder[] array = new BookmarksFolderImpl[3];

			array[0] = getByG_P_PrevAndNext(session, bookmarksFolder, groupId,
					parentFolderId, orderByComparator, true);

			array[1] = bookmarksFolder;

			array[2] = getByG_P_PrevAndNext(session, bookmarksFolder, groupId,
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

	protected BookmarksFolder getByG_P_PrevAndNext(Session session,
		BookmarksFolder bookmarksFolder, long groupId, long parentFolderId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BOOKMARKSFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTFOLDERID_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
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
			query.append(BookmarksFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(parentFolderId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(bookmarksFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BookmarksFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the bookmarks folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @return the matching bookmarks folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<BookmarksFolder> filterFindByG_P(long groupId,
		long parentFolderId) throws SystemException {
		return filterFindByG_P(groupId, parentFolderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the bookmarks folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @return the range of matching bookmarks folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<BookmarksFolder> filterFindByG_P(long groupId,
		long parentFolderId, int start, int end) throws SystemException {
		return filterFindByG_P(groupId, parentFolderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks folders that the user has permissions to view where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching bookmarks folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<BookmarksFolder> filterFindByG_P(long groupId,
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

		query.append(_SQL_SELECT_BOOKMARKSFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTFOLDERID_2);

		if (orderByComparator != null) {
			appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
				orderByComparator);
		}

		else {
			query.append(BookmarksFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BookmarksFolder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				_FILTER_ENTITY_TABLE_FILTER_USERID_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(parentFolderId);

			return (List<BookmarksFolder>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the bookmarks folders before and after the current bookmarks folder in the ordered set of bookmarks folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param folderId the primary key of the current bookmarks folder
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next bookmarks folder
	 * @throws com.liferay.portlet.bookmarks.NoSuchFolderException if a bookmarks folder with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BookmarksFolder[] filterFindByG_P_PrevAndNext(long folderId,
		long groupId, long parentFolderId, OrderByComparator orderByComparator)
		throws NoSuchFolderException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_PrevAndNext(folderId, groupId, parentFolderId,
				orderByComparator);
		}

		BookmarksFolder bookmarksFolder = findByPrimaryKey(folderId);

		Session session = null;

		try {
			session = openSession();

			BookmarksFolder[] array = new BookmarksFolderImpl[3];

			array[0] = filterGetByG_P_PrevAndNext(session, bookmarksFolder,
					groupId, parentFolderId, orderByComparator, true);

			array[1] = bookmarksFolder;

			array[2] = filterGetByG_P_PrevAndNext(session, bookmarksFolder,
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

	protected BookmarksFolder filterGetByG_P_PrevAndNext(Session session,
		BookmarksFolder bookmarksFolder, long groupId, long parentFolderId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BOOKMARKSFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTFOLDERID_2);

		if (orderByComparator != null) {
			String[] orderByFields = orderByComparator.getOrderByFields();

			if (orderByFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
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
			query.append(BookmarksFolderModelImpl.ORDER_BY_JPQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BookmarksFolder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				_FILTER_ENTITY_TABLE_FILTER_USERID_COLUMN, groupId);

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(parentFolderId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByValues(bookmarksFolder);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BookmarksFolder> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the bookmarks folders.
	 *
	 * @return the bookmarks folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<BookmarksFolder> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the bookmarks folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @return the range of bookmarks folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<BookmarksFolder> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the bookmarks folders.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of bookmarks folders
	 * @param end the upper bound of the range of bookmarks folders (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of bookmarks folders
	 * @throws SystemException if a system exception occurred
	 */
	public List<BookmarksFolder> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		Object[] finderArgs = new Object[] { start, end, orderByComparator };

		List<BookmarksFolder> list = (List<BookmarksFolder>)FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_BOOKMARKSFOLDER);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_BOOKMARKSFOLDER.concat(BookmarksFolderModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (orderByComparator == null) {
					list = (List<BookmarksFolder>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);
				}
				else {
					list = (List<BookmarksFolder>)QueryUtil.list(q,
							getDialect(), start, end);
				}
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (list == null) {
					FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL,
						finderArgs);
				}
				else {
					cacheResult(list);

					FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs,
						list);
				}

				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the bookmarks folders where resourceBlockId = &#63; from the database.
	 *
	 * @param resourceBlockId the resource block ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByResourceBlockId(long resourceBlockId)
		throws SystemException {
		for (BookmarksFolder bookmarksFolder : findByResourceBlockId(
				resourceBlockId)) {
			bookmarksFolderPersistence.remove(bookmarksFolder);
		}
	}

	/**
	 * Removes all the bookmarks folders where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (BookmarksFolder bookmarksFolder : findByUuid(uuid)) {
			bookmarksFolderPersistence.remove(bookmarksFolder);
		}
	}

	/**
	 * Removes the bookmarks folder where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUUID_G(String uuid, long groupId)
		throws NoSuchFolderException, SystemException {
		BookmarksFolder bookmarksFolder = findByUUID_G(uuid, groupId);

		bookmarksFolderPersistence.remove(bookmarksFolder);
	}

	/**
	 * Removes all the bookmarks folders where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (BookmarksFolder bookmarksFolder : findByGroupId(groupId)) {
			bookmarksFolderPersistence.remove(bookmarksFolder);
		}
	}

	/**
	 * Removes all the bookmarks folders where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (BookmarksFolder bookmarksFolder : findByCompanyId(companyId)) {
			bookmarksFolderPersistence.remove(bookmarksFolder);
		}
	}

	/**
	 * Removes all the bookmarks folders where groupId = &#63; and parentFolderId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_P(long groupId, long parentFolderId)
		throws SystemException {
		for (BookmarksFolder bookmarksFolder : findByG_P(groupId, parentFolderId)) {
			bookmarksFolderPersistence.remove(bookmarksFolder);
		}
	}

	/**
	 * Removes all the bookmarks folders from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (BookmarksFolder bookmarksFolder : findAll()) {
			bookmarksFolderPersistence.remove(bookmarksFolder);
		}
	}

	/**
	 * Returns the number of bookmarks folders where resourceBlockId = &#63;.
	 *
	 * @param resourceBlockId the resource block ID
	 * @return the number of matching bookmarks folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByResourceBlockId(long resourceBlockId)
		throws SystemException {
		Object[] finderArgs = new Object[] { resourceBlockId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_RESOURCEBLOCKID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_BOOKMARKSFOLDER_WHERE);

			query.append(_FINDER_COLUMN_RESOURCEBLOCKID_RESOURCEBLOCKID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourceBlockId);

				count = (Long)q.uniqueResult();
			}
			catch (Exception e) {
				throw processException(e);
			}
			finally {
				if (count == null) {
					count = Long.valueOf(0);
				}

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_RESOURCEBLOCKID,
					finderArgs, count);

				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of bookmarks folders where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching bookmarks folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_BOOKMARKSFOLDER_WHERE);

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
	 * Returns the number of bookmarks folders where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching bookmarks folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_UUID_G,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BOOKMARKSFOLDER_WHERE);

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
	 * Returns the number of bookmarks folders where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching bookmarks folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_BOOKMARKSFOLDER_WHERE);

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
	 * Returns the number of bookmarks folders that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching bookmarks folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_SQL_COUNT_BOOKMARKSFOLDER_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BookmarksFolder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				_FILTER_ENTITY_TABLE_FILTER_USERID_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

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
	 * Returns the number of bookmarks folders where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching bookmarks folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_COMPANYID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_BOOKMARKSFOLDER_WHERE);

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
	 * Returns the number of bookmarks folders where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @return the number of matching bookmarks folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_P(long groupId, long parentFolderId)
		throws SystemException {
		Object[] finderArgs = new Object[] { groupId, parentFolderId };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_G_P,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BOOKMARKSFOLDER_WHERE);

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
	 * Returns the number of bookmarks folders that the user has permission to view where groupId = &#63; and parentFolderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentFolderId the parent folder ID
	 * @return the number of matching bookmarks folders that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_P(long groupId, long parentFolderId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_P(groupId, parentFolderId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_SQL_COUNT_BOOKMARKSFOLDER_WHERE);

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTFOLDERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				BookmarksFolder.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN,
				_FILTER_ENTITY_TABLE_FILTER_USERID_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

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
	 * Returns the number of bookmarks folders.
	 *
	 * @return the number of bookmarks folders
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_BOOKMARKSFOLDER);

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
	 * Initializes the bookmarks folder persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.bookmarks.model.BookmarksFolder")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<BookmarksFolder>> listenersList = new ArrayList<ModelListener<BookmarksFolder>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<BookmarksFolder>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(BookmarksFolderImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
	}

	@BeanReference(type = BookmarksEntryPersistence.class)
	protected BookmarksEntryPersistence bookmarksEntryPersistence;
	@BeanReference(type = BookmarksFolderPersistence.class)
	protected BookmarksFolderPersistence bookmarksFolderPersistence;
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = ExpandoValuePersistence.class)
	protected ExpandoValuePersistence expandoValuePersistence;
	private static final String _SQL_SELECT_BOOKMARKSFOLDER = "SELECT bookmarksFolder FROM BookmarksFolder bookmarksFolder";
	private static final String _SQL_SELECT_BOOKMARKSFOLDER_WHERE = "SELECT bookmarksFolder FROM BookmarksFolder bookmarksFolder WHERE ";
	private static final String _SQL_COUNT_BOOKMARKSFOLDER = "SELECT COUNT(bookmarksFolder) FROM BookmarksFolder bookmarksFolder";
	private static final String _SQL_COUNT_BOOKMARKSFOLDER_WHERE = "SELECT COUNT(bookmarksFolder) FROM BookmarksFolder bookmarksFolder WHERE ";
	private static final String _FINDER_COLUMN_RESOURCEBLOCKID_RESOURCEBLOCKID_2 =
		"bookmarksFolder.resourceBlockId = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_1 = "bookmarksFolder.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "bookmarksFolder.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(bookmarksFolder.uuid IS NULL OR bookmarksFolder.uuid = ?)";
	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "bookmarksFolder.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "bookmarksFolder.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(bookmarksFolder.uuid IS NULL OR bookmarksFolder.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "bookmarksFolder.groupId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "bookmarksFolder.groupId = ?";
	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "bookmarksFolder.companyId = ?";
	private static final String _FINDER_COLUMN_G_P_GROUPID_2 = "bookmarksFolder.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_PARENTFOLDERID_2 = "bookmarksFolder.parentFolderId = ?";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "bookmarksFolder.folderId";
	private static final String _FILTER_ENTITY_TABLE_FILTER_USERID_COLUMN = "bookmarksFolder.userId";
	private static final String _ORDER_BY_ENTITY_ALIAS = "bookmarksFolder.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No BookmarksFolder exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No BookmarksFolder exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(BookmarksFolderPersistenceImpl.class);
	private static BookmarksFolder _nullBookmarksFolder = new BookmarksFolderImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<BookmarksFolder> toCacheModel() {
				return _nullBookmarksFolderCacheModel;
			}
		};

	private static CacheModel<BookmarksFolder> _nullBookmarksFolderCacheModel = new CacheModel<BookmarksFolder>() {
			public BookmarksFolder toEntityModel() {
				return _nullBookmarksFolder;
			}
		};
}